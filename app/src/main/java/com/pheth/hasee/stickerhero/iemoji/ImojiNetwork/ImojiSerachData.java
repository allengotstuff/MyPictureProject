package com.pheth.hasee.stickerhero.iemoji.ImojiNetwork;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.pheth.hasee.stickerhero.MyApplication;
import java.util.ArrayList;
import java.util.List;

import io.imoji.sdk.ApiTask;
import io.imoji.sdk.ImojiSDK;
import io.imoji.sdk.objects.Imoji;
import io.imoji.sdk.response.ImojisResponse;

/**
 * Created by allengotstuff on 11/15/2016.
 */
public class ImojiSerachData implements ImojiBaseData{

    private static final String TAG = ImojiSerachData.class.getName();
    private Context mContext;
    private ApiTask.WrappedAsyncTask<ImojisResponse> singleClickApiTask;
    private ImojiSearchListener imojiListener;

    public ImojiSerachData (Context context,ImojiSearchListener listener){
        mContext = context;
        imojiListener = listener;
    }

    @Override
    public void startRequest(RequestInfo info) {

        String search_id = info.getSearchId();
        if(TextUtils.isEmpty(search_id) || search_id.equals("")){
            Log.e(TAG,"Search id is empty");
            return;
        }
        Log.e(TAG,"Search id is "+ search_id);

        singleClickApiTask = new ApiTask.WrappedAsyncTask<ImojisResponse>() {
            @Override
            protected void onPostExecute(ImojisResponse imojisResponse) {
                if(imojisResponse==null || imojisResponse.getImojis().size()<=0)
                    return;

                ArrayList<Imoji> newResults = new ArrayList<Imoji>();
                for (Imoji imoji : imojisResponse.getImojis()) {
                    newResults.add(imoji);
                }

                //传出数据
                ImojiSerachData.this.onPostExecute(newResults);

            }
        };
        //do asy network call
        ImojiSDK.getInstance()
                .createSession(mContext.getApplicationContext())
                .searchImojis(search_id)
                .executeAsyncTaskOnExecutor(singleClickApiTask, MyApplication.getGlobelExector());
    }

    @Override
    public void onPostExecute(List arrayList) {

        if(arrayList==null || arrayList.size() ==0){

            if(arrayList ==null) {
                Log.e(TAG, " on post execute result null result");
            }else{
                Log.e(TAG, " on post execute result is zero");
            }
            return;
        }

        imojiListener.onPostExecute(arrayList);
    }

    @Override
    public void onRefresh() {
        Log.e(TAG,"imoji search call doesn't support refresh yet");
    }

    @Override
    public void onCancel() {

        if(singleClickApiTask!=null && !singleClickApiTask.isCancelled()){
            singleClickApiTask.cancel(true);
        }

        singleClickApiTask = null;
        mContext = null;
        imojiListener = null;
    }
}
