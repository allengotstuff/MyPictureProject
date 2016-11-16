package com.pheth.hasee.stickerhero.Adapter;

import android.content.Context;
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
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.ImojiSearchListener;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.ImojiSerachData;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.RequestInfo;
import com.pheth.hasee.stickerhero.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import io.imoji.sdk.objects.Imoji;


/**
 * Created by allengotstuff on 11/13/2016.
 */
public class DetailViewAdapter extends RecyclerView.Adapter<DetailViewAdapter.DetailHolder> implements ImojiSearchListener {

    private Context mContext;

    public static final int TYPE_HEADER = 1;

    public static final int TYPE_BODY = 2;

    private final static String TAG = "DetailViewAdapter";

    private ArrayList<Imoji> mList;

    private Imoji baseImoji;

    private String search_id;
    private ImojiSerachData imojiSerachData;

    public DetailViewAdapter(Context context, Imoji imoji,String id) {
        mContext = context;
        baseImoji = imoji;
        search_id = id;

        mList = new ArrayList<>();

        if(baseImoji!=null) {
            mList.add(baseImoji);
        }

        //start requestiong emojis
        initImojiRequest();
    }

    private void initImojiRequest(){
        RequestInfo info = new RequestInfo(search_id);
        imojiSerachData = new ImojiSerachData(mContext,this);
        imojiSerachData.startRequest(info);
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
                DraweeController controller_body = CommonUtils.getController(mList.get(position));
                holder.detailImage.setController(controller_body);
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

        ViewGroup.LayoutParams params_drawee = tempDrawee.getLayoutParams();
        ViewGroup.LayoutParams params_cardview = tempCardView.getLayoutParams();

        switch (option) {
            case TYPE_HEADER:
                params_drawee.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params_drawee.height = CommonUtils.dpToPx(200);
                ViewCompat.setTransitionName(tempDrawee, mContext.getString(R.string.transition_drawee));
                ViewCompat.setTransitionName(tempCardView, mContext.getString(R.string.transition_cardview));

                params_cardview.width = ViewGroup.LayoutParams.MATCH_PARENT;
                break;

            case TYPE_BODY:
                params_drawee.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params_drawee.height = CommonUtils.dpToPx(100);
                ViewCompat.setTransitionName(tempDrawee, "");
                ViewCompat.setTransitionName(tempCardView,"");
                break;

            default:
                Log.e(TAG, "setViewParam option not recogonized");
                break;
        }
        tempDrawee.setLayoutParams(params_drawee);
        tempCardView.setLayoutParams(params_cardview);
    }

    @Override
    public void onPostExecute(List arrayList) {
        mList.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public void onRequestCancle() {
        if(imojiSerachData!=null){
            imojiSerachData.onCancel();
        }
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
