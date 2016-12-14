package com.pheth.hasee.stickerhero.iemoji;

import android.content.Context;
import android.text.TextUtils;
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
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.greendao.History;
import com.pheth.hasee.stickerhero.utils.Constants;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by allengotstuff on 9/13/2016.
 */
public class HistoryImojiAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    private Context mContext;
    private ArrayList<History> mHistoryList;

    public HistoryImojiAdapter(Context context, ArrayList<History> list ){
        mContext = context;
        mHistoryList = list;
    }
    @Override
    public int getCount() {
        return mHistoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHistoryList.get(position);
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
            convertView = inflater.inflate(R.layout.history_single_item, parent, false);
            myHolder = new MyHolder(convertView);
        } else {
            myHolder = (MyHolder) convertView.getTag();
            myHolder.draweeView.setImageDrawable(null);
            myHolder.textView_category_title.setText("");
        }

        String uri = mHistoryList.get(position).getUrl_thumb_online();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        myHolder.draweeView.setController(controller);
        myHolder.textView_category_title.setText(mHistoryList.get(position).getName());

        convertView.setTag(myHolder);
        return convertView;
    }

    private long clickTime = 0;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(System.currentTimeMillis() - clickTime > 1000){
            clickTime = System.currentTimeMillis();


            History history = mHistoryList.get(position);
            String url = history.getUrl_send_local();

            if(TextUtils.isEmpty(url)){
                Toast.makeText(mContext, "local file is lost", Toast.LENGTH_SHORT).show();
                return;
            }

            File file = new File(url);
//            Log.e("History",url);
            IemojiUtil.sharedFile(mContext,file,null);

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
