package com.pheth.hasee.stickerhero.Adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.iemoji.IemojiUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.imoji.sdk.objects.Category;
import io.imoji.sdk.objects.Imoji;
import io.imoji.sdk.objects.RenderingOptions;

/**
 * Created by allengotstuff on 10/29/2016.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<BaseData> dataList;
    private int[] colors;
    private RecyclerViewOnitemClickListener onitemClickListener;

    public CardViewAdapter(Context context, List categoryList) {
        mContext = context;
        this.dataList = (ArrayList<BaseData>) categoryList;
        colors = mContext.getResources().getIntArray(R.array.color_array);
    }

    public void setOnitemClickListener(RecyclerViewOnitemClickListener onitemClickListener){
        this.onitemClickListener = onitemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final BaseData baseData = dataList.get(position);
//        final Imoji imoji = tempCategory.getPreviewImoji();
//        RenderingOptions options = IemojiUtil.getRenderOption(imoji, RenderingOptions.Size.Thumbnail);
//        final Uri uri = imoji.urlForRenderingOption(options);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(baseData.getOnlineThumbUrl())
                .setAutoPlayAnimations(true)
                .build();

        holder.categoryImage.setController(controller);

        int colorCode = colors[position%5];
        holder.view_underBar.setBackgroundColor(colorCode);

        String title = baseData.getName();
        if(!TextUtils.isEmpty(title)) {
            holder.view_underBar.setText("#"+title);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                int colortext = position%5 + 1;
//                if(colortext>4) {
//                    colortext = 0;
//                }
//
//                holder.view_underBar.setTextColor(colors[colortext]);
//            }
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemClickListener.onItemClick(holder, position,baseData,baseData.getIdentifier());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView categoryImage;
        public CardView cardView;
        private TextView view_underBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryImage = (SimpleDraweeView) itemView.findViewById(R.id.iv_category);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            view_underBar = (TextView)itemView.findViewById(R.id.under_bar);
        }
    }


     public interface RecyclerViewOnitemClickListener{
        void onItemClick(MyViewHolder holder,int pos,BaseData baseData,String categoryID);
    }
}
