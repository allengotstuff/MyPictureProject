package com.pheth.hasee.stickerhero.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
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
    private ArrayList<Category> categoryList;
    private int[] colors;
    private RecyclerViewOnitemClickListener onitemClickListener;

    public CardViewAdapter(Context context, List categoryList) {
        mContext = context;
        this.categoryList = (ArrayList<Category>) categoryList;
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

        Imoji imoji = categoryList.get(position).getPreviewImoji();
        RenderingOptions options = IemojiUtil.getRenderOption(imoji, RenderingOptions.Size.Thumbnail);
        Uri uri = imoji.urlForRenderingOption(options);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();

        holder.categoryImage.setController(controller);
        int colorCode = colors[new Random().nextInt(5)];
        holder.cardView.setCardBackgroundColor(colorCode);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemClickListener.onItemClick(holder, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView categoryImage;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryImage = (SimpleDraweeView) itemView.findViewById(R.id.iv_category);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
        }
    }


     public interface RecyclerViewOnitemClickListener{
        void onItemClick(RecyclerView.ViewHolder holder,int pos);
    }
}
