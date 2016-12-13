package com.pheth.hasee.stickerhero.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheth.hasee.stickerhero.Adapter.FavoriteAdapter;
import com.pheth.hasee.stickerhero.Adapter.FlexSpanAdapter;
import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.BaseData.Data.DataContainer;
import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.greendao.Favorite;
import com.pheth.hasee.stickerhero.utils.DataConverter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by allengotstuff on 12/3/2016.
 */
public class FavoritImojiFragment extends BaseFragment {

    private static final String TAG = "FavoritImojiFragment";
    private Context mContext;
    private DaoManager daoManager;
    private List<Favorite> favoriteImojiList;
    private List<BaseData> baseDataList;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initdatabase();
        getFavoite();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getContext();
        RecyclerView myRecyclerview = (RecyclerView) inflater.inflate(R.layout.recycleview_layout, container, false);
        setupRecyclerview(myRecyclerview);
        return myRecyclerview;
    }


    private void initdatabase() {
        daoManager = DaoManager.getManager();
        daoManager.initFavoriteIndividualDao();
    }

    private void getFavoite() {
        favoriteImojiList = new ArrayList<Favorite>();
        favoriteImojiList.addAll(daoManager.getFavoriteIndividualDao().loadAll());

        baseDataList = DataConverter.convertData(favoriteImojiList);

        Log.e(TAG, "favorite list size: "+ favoriteImojiList.size());
    }

    private void setupRecyclerview(RecyclerView recyclerview) {

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerview.setHasFixedSize(true);

        FlexSpanAdapter adapter = new FlexSpanAdapter(getContext(), baseDataList);
        recyclerview.setAdapter(adapter);
        Log.e(TAG, "setup recyclerview");
    }

    @Override
    void clearReference() {

    }

    @Override
    public void updateFragment() {

    }

    @Override
    void setActionName() {
        actionName = TAG;
    }

}
