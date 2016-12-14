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
import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.ClickHandler.ClickHandler;
import com.pheth.hasee.stickerhero.ClickHandler.DetailClickHandler;
import com.pheth.hasee.stickerhero.ClickHandler.TrendingClickHandler;
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
    private List<BaseData> featureImojis;
    public static final String NAMETAG = "trending_fragment";

    private FlexSpanAdapter adapter;

//    private ClickHandler clickHandler;

    private TrendingHolderAnimation holderAnimation;
    private ClickHandler clickHandler;

    public TrendingFragment() {
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


    private void setupRecyclerView(RecyclerView recyclerView) {

        featureImojis = new ArrayList<>();
        adapter = new FlexSpanAdapter(getContext(), featureImojis);
        adapter.setClickHandler(clickHandler);
        adapter.setAnimationHolder(holderAnimation);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

    }


    private void initView() {
        holderAnimation = new TrendingHolderAnimation();
        clickHandler = new TrendingClickHandler(DaoManager.getManager(),getContext());
    }

    private void initData() {

        imojiData = new ImojiData(getContext(), ImojiDataContainer.getFeatureImojiListList()) {
            @Override
            public void onPostExecute(List arrayList) {

                if (arrayList == null || arrayList.size() <= 0)
                    return;

                featureImojis.clear();
                featureImojis.addAll(arrayList);

                adapter.notifyDataSetChanged();
                Log.e(NAMETAG, "featureImojis size ：" + featureImojis.size());

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

        Log.e("Trending", "" + pos);

        //控制点击动画
        holderAnimation.setViewHolder(holder, pos);


        //控制点击的操
        BaseData imoji = featureImojis.get(pos);

        clickHandler.setViewHolder(holder,pos);
        clickHandler.setData(imoji);

//        if(imoji.getIsAnimateable()){
//            Log.e("Trending", "Animateable" + pos);
//        }else{
//            Log.e("Trending", "not Animateable" + pos);
//        }

    }
}
