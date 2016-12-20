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
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pheth.hasee.stickerhero.Adapter.FlexSpanAdapter;
import com.pheth.hasee.stickerhero.Animation.FavoriteAdapterAnimation;
import com.pheth.hasee.stickerhero.Animation.TrendingHolderAnimation;
import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.ClickHandler.ClickHandler;
import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.greendao.Favorite;
import com.pheth.hasee.stickerhero.utils.DataConverter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by allengotstuff on 12/3/2016.
 */
public class FavoritImojiFragment extends BaseFragment implements FlexSpanAdapter.OnItemClickListener  {

    private static final String TAG = "FavoritImojiFragment";
    private Context mContext;
    private DaoManager daoManager;
    private List<Favorite> favoriteImojiList;
    private List<BaseData> baseDataList;

    private FavoriteAdapterAnimation holderAnimation;
    private ClickHandler clickHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
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
        List databaseList = daoManager.getFavoriteIndividualDao().loadAll();

        if (databaseList == null)
            return;

        favoriteImojiList.addAll(databaseList);

        baseDataList = DataConverter.convertData(favoriteImojiList);

        Log.e(TAG, "favorite list size: " + favoriteImojiList.size());
    }

    private void init(){
        holderAnimation = new FavoriteAdapterAnimation();
    }

    private void setupRecyclerview(RecyclerView recyclerview) {

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerview.setHasFixedSize(true);

        FlexSpanAdapter adapter = new FavoriteAdapter(getContext(), baseDataList);
        adapter.setOnItemClickListener(this);
        adapter.setAnimationHolder(holderAnimation);
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

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, int pos) {
        Log.e(TAG, ""+pos);
        //控制点击动画
        holderAnimation.setViewHolder(holder, pos);

    }

    public static class FavoriteAdapter extends FlexSpanAdapter {

        public FavoriteAdapter(Context context, List<BaseData> datalist) {
            super(context, datalist);
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cell_favorite_adapter, parent, false);
            MyHolder myViewHolder = new MyHolder(holderView);
            return myViewHolder;
        }

        public static class MyHolder extends FlexSpanAdapter.MyHolder {

            public SimpleDraweeView detailImage;
            public TextView imojiTitle;

            public ImageView favorite_delete_function, share_function;

            public MyHolder(View itemView) {
                super(itemView);
                detailImage = (SimpleDraweeView) itemView.findViewById(R.id.dv_detail_image);
                imojiTitle = (TextView) itemView.findViewById(R.id.title_tv);
                setupControllor(itemView);
            }

            private void setupControllor(View itemView) {
                favorite_delete_function = (ImageView) itemView.findViewById(R.id.iamgeview_1);
                share_function = (ImageView) itemView.findViewById(R.id.iamgeview_2);
            }
        }

    }

}
