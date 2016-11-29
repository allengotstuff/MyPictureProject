package com.pheth.hasee.stickerhero.Adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pheth.hasee.stickerhero.Animation.AdapterSelector;
import com.pheth.hasee.stickerhero.ClickHandler.ClickHandler;
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

    private OnHolderClickListener onHolderClickListener;

    private Handler handler = new Handler(){};

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            notifyDataSetChanged();
        }
    };

    private AdapterSelector cardHolderAnimation;

    private ClickHandler clickHandler;

    public DetailViewAdapter(Context context, Imoji imoji, String id) {
        mContext = context;
        baseImoji = imoji;
        search_id = id;

        mList = new ArrayList<>();

        if (baseImoji != null) {
            mList.add(baseImoji);
        }

        //start requestiong emojis
        initImojiRequest();
    }

    public void setClickHandler(ClickHandler clickHandler){
        this.clickHandler = clickHandler;
    }

    public void setHolderAnimation(AdapterSelector holderAnimation){
        cardHolderAnimation =holderAnimation;
    }

    public void setOnHolderClickListener(OnHolderClickListener listener) {
        onHolderClickListener = listener;
    }

    private void initImojiRequest() {
        RequestInfo info = new RequestInfo(search_id);
        imojiSerachData = new ImojiSerachData(mContext, this);
        imojiSerachData.startRequest(info);
    }

    public Imoji getPosImoji(int pos){

        return mList.get(pos);
    }


    @Override
    public DetailViewAdapter.DetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View holderView = LayoutInflater.from(mContext).inflate(R.layout.single_detail_row, parent, false);
        DetailHolder myViewHolder = new DetailHolder(holderView);

        return myViewHolder;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        return TYPE_BODY;
    }

    @Override
    public void onBindViewHolder(final DetailViewAdapter.DetailHolder holder, final int position) {

        if(cardHolderAnimation!=null) {
            cardHolderAnimation.prepareHolder(position, holder);
        }

        if(clickHandler!=null) {
            clickHandler.prepareHolder(holder, position);
        }

        int viewType = getItemViewType(position);
        setDraweeParam(holder, viewType);

        switch (viewType) {
            case TYPE_HEADER:
                DraweeController controller = CommonUtils.getController(baseImoji);
                holder.detailImage.setController(controller);
                holder.itemView.setOnClickListener(null);
                break;

            case TYPE_BODY:
                DraweeController controller_body = CommonUtils.getController(mList.get(position));
                holder.detailImage.setController(controller_body);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onHolderClickListener.onHolderClick(position, holder);
                    }
                });
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    private void setDraweeParam(DetailViewAdapter.DetailHolder holder, int option) {
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
//        tempDrawee.setLayoutParams(params_drawee);

    }

    @Override
    public void onPostExecute(List arrayList) {
        mList.addAll(arrayList);

        handler.postDelayed(runnable,500);
    }

    @Override
    public void onRequestCancle() {
        if (imojiSerachData != null) {
            imojiSerachData.onCancel();
        }
        onHolderClickListener = null;
        handler.removeCallbacks(runnable);
        handler = null;
        runnable = null;
    }

    public class DetailHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView detailImage;


        public ImageView favorite_function,share_function;

        public DetailHolder(View itemView) {
            super(itemView);
            detailImage = (SimpleDraweeView) itemView.findViewById(R.id.dv_detail_image);

            setupControllor(itemView);
        }

        private void setupControllor(View itemView) {
            favorite_function = (ImageView) itemView.findViewById(R.id.iamgeview_1);
            share_function = (ImageView) itemView.findViewById(R.id.iamgeview_2);
        }
    }

    public interface OnHolderClickListener {
        void onHolderClick(int pos, RecyclerView.ViewHolder holder);
    }
}
