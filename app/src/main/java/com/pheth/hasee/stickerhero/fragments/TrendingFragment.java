package com.pheth.hasee.stickerhero.fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheth.hasee.stickerhero.Adapter.FlexSpanAdapter;
import com.pheth.hasee.stickerhero.Animation.TrendingHolderAnimation;
import com.pheth.hasee.stickerhero.ClickHandler.ClickHandler;
import com.pheth.hasee.stickerhero.ClickHandler.DetailClickHandler;
import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.ImojiData;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.ImojiDataContainer;

import java.util.ArrayList;
import java.util.List;

import io.imoji.sdk.objects.Imoji;

/**
 * Created by allengotstuff on 11/27/2016.
 */
public class TrendingFragment extends BaseFragment implements FlexSpanAdapter.OnItemClickListener {

    private ImojiData imojiData;
    private List featureImojis;
    public static final String NAMETAG = "trending_fragment";

    private FlexSpanAdapter adapter;

    private ClickHandler clickHandler;

    private TrendingHolderAnimation holderAnimation;

    public TrendingFragment(){
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initView();
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.recycleview_layout, null);
        setupRecyclerView(recyclerView);
        initData();
        return recyclerView;
    }


    private void setupRecyclerView(RecyclerView recyclerView){

        clickHandler = new DetailClickHandler(DaoManager.getManager(),getContext());

        adapter = new FlexSpanAdapter(getContext());
        adapter.setAnimationHolder(holderAnimation);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

    }


    private void initView(){
        holderAnimation = new TrendingHolderAnimation();
    }

    private void initData(){

        imojiData = new ImojiData(getContext(), ImojiDataContainer.getFeatureImojiListList()) {
            @Override
            public void onPostExecute(List arrayList) {

                if(featureImojis==null){
                    featureImojis = new ArrayList<Imoji>();
                }else{
                    featureImojis.clear();
                }
                featureImojis.addAll(arrayList);

                Log.e(NAMETAG,"featureImojis size ："+featureImojis.size() );
                adapter.setData(featureImojis);
            }
        };
        imojiData.startRequest(null);
    }

    @Override
    void clearReference() {
        imojiData.onCancel();
    }

    @Override
    public void updateFragment() {
        Log.e(NAMETAG, "updateFragment");
    }

    @Override
    void setActionName() {
        actionName = NAMETAG;
    }


    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, int pos) {

        Log.e("Trending", ""+pos);

        //控制点击动画
        holderAnimation.setViewHolder(holder, pos);
    }
}
