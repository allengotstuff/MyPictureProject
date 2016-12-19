package com.pheth.hasee.stickerhero.iemoji;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.MyApplication;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.greendao.DaoMaster;
import com.pheth.hasee.stickerhero.greendao.DaoSession;
import com.pheth.hasee.stickerhero.greendao.History;
import com.pheth.hasee.stickerhero.greendao.HistoryDao;
import com.pheth.hasee.stickerhero.utils.MyGreenDaoUtils;

import java.util.ArrayList;
import java.util.Date;

import io.imoji.sdk.grid.components.SearchResult;
import io.imoji.sdk.objects.Imoji;
import io.imoji.sdk.objects.RenderingOptions;

/**
 * Created by hasee on 2016/8/4.
 */
public class MyStickerSearchAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private ArrayList<SearchResult> mList;
    private Context mContext;

//    private DaoMaster daoMaster;
//    private HistoryDao historyDao;
//    private DaoSession daoSession;
    private DaoManager daoManager;

    public MyStickerSearchAdapter(Context context, ArrayList<SearchResult> imojis) {
        mContext = context;
        mList = imojis;
        daoManager = DaoManager.getManager();
        daoManager.initHistoryDao();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_category_imoji_gridview, parent, false);
            myHolder = new MyHolder(convertView);
        } else {
            myHolder = (MyHolder) convertView.getTag();
            myHolder.draweeView.setImageDrawable(null);
            myHolder.textView_category_title.setText("");
        }

        Imoji imoji = mList.get(position).getImoji();
        RenderingOptions options = IemojiUtil.getRenderOption(imoji, RenderingOptions.Size.Thumbnail);
        Uri uri = imoji.urlForRenderingOption(options);

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();

        myHolder.draweeView.setController(controller);

        convertView.setTag(myHolder);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        SearchResult searchResult = (SearchResult) parent.getAdapter().getItem(position);

        Imoji imoji = searchResult.getImoji();
        RenderingOptions renderingOptions = IemojiUtil.getRenderOption(imoji,RenderingOptions.Size.Resolution320);
        Uri uri = imoji.urlForRenderingOption(renderingOptions);
        String imojiName = imoji.getTags().get(0);

        //get the thumb nail online to store in database
        RenderingOptions thumbOptions = IemojiUtil.getRenderOption(imoji,RenderingOptions.Size.Thumbnail);
        Uri thumbUri = imoji.urlForRenderingOption(thumbOptions);

        Date date = new Date();
        History mHistory = new History(null,imojiName,null,uri.toString(),null,thumbUri.toString(),imoji.getIdentifier(),imoji.hasAnimationCapability(),date);


        if(imoji.hasAnimationCapability()){
            IemojiUtil.shareGif(mContext, uri.toString(),null,daoManager.getHistoryDao(),mHistory);
        }else {
            IemojiUtil.getBitmap(mContext, uri.toString(),null, daoManager.getHistoryDao(), mHistory);
        }
    }


    class MyHolder {
        SimpleDraweeView draweeView;

        TextView textView_category_title;

        MyHolder(View v) {
            draweeView = (SimpleDraweeView) v.findViewById(R.id.iv_single_sticker);
            textView_category_title = (TextView) v.findViewById(R.id.tv_category_title);
        }
    }
}
