package com.pheth.hasee.stickerhero.ClickHandler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.pheth.hasee.stickerhero.Adapter.FlexSpanAdapter;
import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.BaseData.Data.DataContainer;
import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.ShareOption.ShareOptionActivity;
import com.pheth.hasee.stickerhero.greendao.Favorite;
import com.pheth.hasee.stickerhero.greendao.FavoriteDao;
import com.pheth.hasee.stickerhero.utils.DataCollectionConstant;
import com.pheth.hasee.stickerhero.utils.MyGreenDaoUtils;

import java.util.Calendar;

/**
 * Created by allengotstuff on 12/13/2016.
 */
public class TrendingClickHandler implements ClickHandler<RecyclerView.ViewHolder, BaseData>, View.OnClickListener  {


    protected BaseData dataImoji;
    private FlexSpanAdapter.MyHolder myHolder;
    protected DaoManager daoManager;
    protected Context mContext;
    private int position;


    public TrendingClickHandler(DaoManager daoManager, Context context) {
        this.daoManager = daoManager;
        mContext = context;
        position = -999;
    }

    @Override
    public void setViewHolder(RecyclerView.ViewHolder holder, int pos) {
        myHolder = (FlexSpanAdapter.MyHolder)holder;
        position = pos;
        registerOnClick();
    }

    private void registerOnClick() {

        myHolder.favorite_function.setOnClickListener(this);

        myHolder.share_function.setOnClickListener(this);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder() {
        return myHolder;
    }

    @Override
    public void setData(BaseData data) {
        dataImoji = data;
    }

    @Override
    public BaseData getData() {
        return dataImoji;
    }

    @Override
    public void prepareHolder(RecyclerView.ViewHolder holder, int pos) {

        FlexSpanAdapter.MyHolder tempHolder = (FlexSpanAdapter.MyHolder)holder;
        if (position == pos) {
            tempHolder.favorite_function.setOnClickListener(this);
            tempHolder.share_function.setOnClickListener(this);
        } else {
            tempHolder.favorite_function.setOnClickListener(null);
            tempHolder.share_function.setOnClickListener(null);
        }
    }

    @Override
    public void addToFavorite() {
        daoManager.initFavoriteIndividualDao();
        FavoriteDao favoriteDao = daoManager.getFavoriteIndividualDao();

        Favorite favorite = mapFavoriteItem();

        boolean isSuccess = MyGreenDaoUtils.addSingleImojiFavorite(favoriteDao, favorite);
        if (isSuccess) {
            Toast.makeText(mContext, "Add to favorite", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "fail to add to favorite", Toast.LENGTH_SHORT).show();
        }
        FlurryAgent.logEvent(DataCollectionConstant.ADD_FAVORITE);
    }

    @Override
    public void shareAction() {
        Intent intent = new Intent(mContext, ShareOptionActivity.class);
        DataContainer dataContainer = (DataContainer)dataImoji;

        intent.putExtra(ShareOptionActivity.PASS_BASE_DATA,dataContainer);
        mContext.startActivity(intent);
        FlurryAgent.logEvent(DataCollectionConstant.INIT_SHARE_BUTTON);
    }

    @Override
    public void handleAnimation() {

    }

    protected Favorite mapFavoriteItem() {

        if (dataImoji == null) {
            throw new RuntimeException("ClickHandler: dataImoji can not be null");
        }

//        RenderingOptions options_thumb = IemojiUtil.getRenderOption(dataImoji, RenderingOptions.Size.Thumbnail);
//        Uri uri_thumb = dataImoji.urlForRenderingOption(options_thumb);
//
//        RenderingOptions option_full = IemojiUtil.getRenderOption(dataImoji, RenderingOptions.Size.Resolution320);
//        Uri uri_full = dataImoji.urlForRenderingOption(option_full);

        Favorite myFavorite = new Favorite();
        myFavorite.setIdentifier(dataImoji.getIdentifier());
        myFavorite.setAdd_date(Calendar.getInstance().getTime());
        myFavorite.setUrl_thumb(dataImoji.getOnlineThumbUrl());
        myFavorite.setUrl_full(dataImoji.getOnlineFullUrl());
        myFavorite.setIsAnimateable(dataImoji.getIsAnimateable());
        String name = dataImoji.getName();
        if(!TextUtils.isEmpty(name)){
            myFavorite.setName(name);
        }
        return myFavorite;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //add to favorite
            case R.id.iamgeview_1:
                addToFavorite();

                break;

            //share option.
            case R.id.iamgeview_2:
                shareAction();
//                Toast.makeText(mContext, "Share Action: " + position, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
