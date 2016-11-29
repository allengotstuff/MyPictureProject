package com.pheth.hasee.stickerhero.iemoji.ImojiNetwork;

import android.content.Context;
import android.util.Log;

import com.pheth.hasee.stickerhero.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.imoji.sdk.ApiTask;
import io.imoji.sdk.ImojiSDK;
import io.imoji.sdk.objects.Category;
import io.imoji.sdk.objects.CategoryFetchOptions;
import io.imoji.sdk.response.CategoriesResponse;

/**
 * *************************************************************************
 *
 * @版权所有: 北京云图微动科技有限公司 (C) 2016
 * @创建人: 孙旭光
 * @创建时间: xxx(2016-09-21 14:13)
 * @Email: 410073261@qq.com
 * <p>
 * 描述:com.fotoable.keyboard.emoji.ui.iemoji.ImojiNetwork-ImojiFragmentData
 * <p>
 * <p>
 * *************************************************************************
 */

public abstract class ImojiCategoryData implements ImojiBaseData {

    private static final String TAG = ImojiCategoryData.class.getName();
    private Context mContext;
    private List categoryList;
    private RequestInfo info;
    private ApiTask.WrappedAsyncTask apiTask;
    private List oneRequestList;

    /**
     *
     * @param context use the local base context.
     * @param list pass the static singleton date from ImojiDataContainer selection.
     */
    public ImojiCategoryData(Context context, List list){
        mContext = context.getApplicationContext();
        categoryList = list;
        oneRequestList = new ArrayList<Category>();
    }

    @Override
    public void startRequest(final RequestInfo info) {

        if(categoryList==null)
            return;

        this.info = info;
        if(categoryList!=null && categoryList.size()>0){
            Log.e(TAG, "local data");
            ImojiCategoryData.this.onPostExecute(categoryList);
        }else {
            Log.e(TAG, "Remote data");
            apiTask = new ApiTask.WrappedAsyncTask<CategoriesResponse>() {
                @Override
                protected void onPostExecute(CategoriesResponse imojisResponse) {
                    //Bind the results to an adapter of sorts


                    //记录下来每次刷新之后的数据
                    oneRequestList.clear();
                    oneRequestList.addAll(imojisResponse.getCategories());

                    //如果键盘界面和Imoji Fragment申请的话，防止重复添加数据
                    categoryList.clear();


                    for (int i = 0; i < 21; i++) {
                        categoryList.add(oneRequestList.remove(0));
                    }

                    ImojiCategoryData.this.onPostExecute(categoryList);
                }
            };

            ImojiSDK.getInstance()
                    .createSession(mContext)
                    .getImojiCategories(new CategoryFetchOptions(info.getClassification()))
                    .executeAsyncTaskOnExecutor(apiTask, MyApplication.getGlobelExector());
        }
    }

    @Override
    public void onRefresh() {
        categoryList.clear();

        //if local data from last requst is more than 21
       boolean islocal =  isFromLocal();

        if(this.info!=null && !islocal) {
            startRequest(info);
            Log.e(TAG,"onRefresh: refreshing data remotely");
        }
    }

    @Override
    public void onCancel() {
        if(apiTask==null)
            return;
        if(!apiTask.isCancelled()){
            apiTask.cancel(true);
        }

        mContext = null;
        categoryList = null;
    }

    private boolean isFromLocal(){
        if(oneRequestList.size()>22){

            for (int i = 0; i < 21; i++) {
                categoryList.add(oneRequestList.remove(0));
            }

            ImojiCategoryData.this.onPostExecute(categoryList);

            Log.e(TAG,"onRefresh: refreshing data local");
            return true;
        }
        return false;
    }
}
