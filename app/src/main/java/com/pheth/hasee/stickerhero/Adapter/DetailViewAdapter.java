package com.pheth.hasee.stickerhero.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.utils.CommonUtils;

import java.util.ArrayList;

import io.imoji.sdk.objects.Imoji;


/**
 * Created by allengotstuff on 11/13/2016.
 */
public class DetailViewAdapter extends RecyclerView.Adapter<DetailViewAdapter.DetailHolder> {

    private Context mContext;

    private static final int TYPE_HEADER = 1;

    private static final int TYPE_BODY = 2;

    private final static String TAG = "DetailViewAdapter";

    private ArrayList<Imoji> mList;

    private Imoji baseImoji;

    public DetailViewAdapter(Context context, Imoji imoji) {
        mContext = context;
        baseImoji = imoji;
        mList = new ArrayList<>();
        mList.add(baseImoji);
    }


    @Override
    public DetailViewAdapter.DetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.single_detail_row, parent, false);
        DetailHolder myViewHolder = new DetailHolder(view);

        return myViewHolder;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        return TYPE_BODY;
    }

    @Override
    public void onBindViewHolder(DetailViewAdapter.DetailHolder holder, int position) {

        int viewType = getItemViewType(position);
        setDraweeParam(holder, viewType);


        switch (viewType) {
            case TYPE_HEADER:
                DraweeController controller = CommonUtils.getController(baseImoji);
                holder.detailImage.setController(controller);
                break;

            case TYPE_BODY:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    private void setDraweeParam(DetailViewAdapter.DetailHolder holder, int option) {
        SimpleDraweeView tempDrawee = holder.detailImage;
        CardView tempCardView = holder.cardView;

        ViewGroup.LayoutParams params = tempDrawee.getLayoutParams();

        switch (option) {
            case TYPE_HEADER:
                params.width = CommonUtils.dpToPx(200);
                params.height = CommonUtils.dpToPx(200);
                ViewCompat.setTransitionName(tempDrawee, mContext.getString(R.string.transition_drawee));
                ViewCompat.setTransitionName(tempCardView, mContext.getString(R.string.transition_cardview));
                break;

            case TYPE_BODY:
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = CommonUtils.dpToPx(100);
                ViewCompat.setTransitionName(tempDrawee, "");
                ViewCompat.setTransitionName(tempCardView,"");
                break;

            default:
                Log.e(TAG, "setViewParam option not recogonized");
                break;
        }
        tempDrawee.setLayoutParams(params);
    }

    public class DetailHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView detailImage;
        public CardView cardView;

        public DetailHolder(View itemView) {
            super(itemView);
            detailImage = (SimpleDraweeView) itemView.findViewById(R.id.dv_detail_image);
            cardView = (CardView) itemView.findViewById(R.id.cv_detail_container);
        }
    }
}
