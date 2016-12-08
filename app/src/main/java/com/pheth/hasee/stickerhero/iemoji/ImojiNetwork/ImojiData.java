package com.pheth.hasee.stickerhero.iemoji.ImojiNetwork;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.MyApplication;
import com.pheth.hasee.stickerhero.utils.DataConverter;

import java.util.List;

import io.imoji.sdk.ApiTask;
import io.imoji.sdk.ImojiSDK;
import io.imoji.sdk.objects.Imoji;
import io.imoji.sdk.response.ImojisResponse;

/**
 * Created by allengotstuff on 11/27/2016.
 */
public abstract class ImojiData implements ImojiBaseData {

    private static final String TAG = ImojiData.class.getName();
    private Context mContext;
    private List imojiList;
    private RequestInfo info;
    private ApiTask.WrappedAsyncTask apiTask;


    public ImojiData(Context context, List list) {
        mContext = context.getApplicationContext();
        imojiList = list;
    }

    @Override
    public void startRequest(@Nullable RequestInfo info) {
        if (imojiList == null) {
            throw new RuntimeException("Imoji Data: imojiList shouldn't be null");
        }

        if (imojiList != null && imojiList.size() > 0) {
            Log.e(TAG, "local data");
            ImojiData.this.onPostExecute(imojiList);
        } else {
            Log.e(TAG, "remote data");

            apiTask = new ApiTask.WrappedAsyncTask<ImojisResponse>() {
                @Override
                protected void onPostExecute(ImojisResponse imojisResponse) {
                    //Bind the results to an adapter of sorts

                    List<Imoji> tempList = imojisResponse.getImojis();
                    if(tempList==null || tempList.size()<=0)
                        return;


                    imojiList.clear();
                    imojiList.addAll(tempList);

                    //转化数据
                    List<BaseData> convertData = DataConverter.convertData(imojiList);

                    ImojiData.this.onPostExecute(convertData);
                }
            };

            ImojiSDK.getInstance()
                    .createSession(mContext).getFeaturedImojis(30)
                    .executeAsyncTaskOnExecutor(apiTask, MyApplication.getGlobelExector());
        }

    }


    @Override
    public void onCancel() {
        if (apiTask == null)
            return;
        if (!apiTask.isCancelled()) {
            apiTask.cancel(true);
        }

        mContext = null;
        imojiList = null;
    }

    @Override
    public void onRefresh() {
        //current doesn't support onRefresh function now

        imojiList.clear();

        startRequest(info);
        Log.e(TAG, "onRefresh: refreshing data remotely");
    }
}
