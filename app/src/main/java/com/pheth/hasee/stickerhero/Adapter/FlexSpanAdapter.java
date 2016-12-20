package com.pheth.hasee.stickerhero.Adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.imoji.sdk.objects.Imoji;

/**
 * Created by allengotstuff on 11/29/2016.
 */
public class FlexSpanAdapter extends AnimationAdapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_BODY = 2;

    //    private GridLayoutManager layoutManager;
    private Context mContext;
    private HashSet<Integer> spanPositionSet;
    private static final String TAG = "FlexSpanAdapter";
    private ArrayList<BaseData> imojiList;

    private OnItemClickListener onItemClickListener;


    public FlexSpanAdapter(Context context, List data) {
        mContext = context;
        imojiList = (ArrayList<BaseData>) data;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }


    @Override
    public int getItemViewType(int position) {

//        if(spanPositionSet!=null && spanPositionSet.contains(position)){
//            return TYPE_HEADER;
//        }
        return TYPE_BODY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(mContext).inflate(R.layout.single_detail_row_trending, parent, false);
        MyHolder myViewHolder = new MyHolder(holderView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        super.onBindViewHolder(viewHolder, position);

        final MyHolder holder = (MyHolder)viewHolder;

        int viewType = getItemViewType(position);
        setDraweeParam(holder, viewType);

        switch (viewType) {
            case TYPE_HEADER:
                BaseData posImoji = imojiList.get(position);
//                DraweeController controller = CommonUtils.getController(baseImoji);
//                holder.detailImage.setController(controller);
                holder.itemView.setOnClickListener(null);
                break;

            case TYPE_BODY:

                BaseData imoji = imojiList.get(position);

                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(imoji.getOnlineThumbUrl())
                        .setAutoPlayAnimations(true)
                        .build();

                holder.detailImage.setController(controller);

                String title = imoji.getName();

                holder.imojiTitle.setText(title);

                if(onItemClickListener!=null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(holder, position);
                        }
                    });
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return imojiList.size();
    }

    private void setDraweeParam(MyHolder holder, int option) {
        SimpleDraweeView tempDrawee = holder.detailImage;
        ViewGroup.LayoutParams params_drawee = tempDrawee.getLayoutParams();

        switch (option) {
            case TYPE_HEADER:
                params_drawee.width = CommonUtils.dpToPx(120);
                params_drawee.height = CommonUtils.dpToPx(120);
                ViewCompat.setTransitionName(tempDrawee, mContext.getString(R.string.transition_drawee));
                break;

            case TYPE_BODY:
                params_drawee.width = CommonUtils.dpToPx(60);
                params_drawee.height = CommonUtils.dpToPx(60);
                ViewCompat.setTransitionName(tempDrawee, "");
                break;

            default:
                Log.e(TAG, "setViewParam option not recogonized");
                break;
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView detailImage;
        public TextView imojiTitle;

        public ImageView favorite_function, share_function;

        public MyHolder(View itemView) {
            super(itemView);
            detailImage = (SimpleDraweeView) itemView.findViewById(R.id.dv_detail_image);
            imojiTitle = (TextView) itemView.findViewById(R.id.title_tv);
            setupControllor(itemView);
        }

        private void setupControllor(View itemView) {
            favorite_function = (ImageView) itemView.findViewById(R.id.iamgeview_1);
            share_function = (ImageView) itemView.findViewById(R.id.iamgeview_2);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder holder, int pos);
    }
}
