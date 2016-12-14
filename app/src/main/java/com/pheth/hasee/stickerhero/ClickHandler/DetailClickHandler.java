package com.pheth.hasee.stickerhero.ClickHandler;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.pheth.hasee.stickerhero.Adapter.DetailViewAdapter;
import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.greendao.Favorite;
import com.pheth.hasee.stickerhero.greendao.FavoriteDao;
import com.pheth.hasee.stickerhero.iemoji.IemojiUtil;
import com.pheth.hasee.stickerhero.utils.MyGreenDaoUtils;

import java.util.Calendar;

import io.imoji.sdk.objects.Imoji;
import io.imoji.sdk.objects.RenderingOptions;

/**
 * Created by allengotstuff on 11/21/2016.
 */
public class DetailClickHandler implements ClickHandler<DetailViewAdapter.DetailHolder, BaseData>, View.OnClickListener {

    private BaseData dataImoji;
    private DetailViewAdapter.DetailHolder myHolder;
    private DaoManager daoManager;
    private Context mContext;
    private int position;


    public DetailClickHandler(DaoManager daoManager, Context context) {
        this.daoManager = daoManager;
        mContext = context;
        position = -999;
    }

    private void registerOnClick() {

        myHolder.favorite_function.setOnClickListener(this);

        myHolder.share_function.setOnClickListener(this);
    }

    //need to called this in onbindview recyclerview adapter
    @Override
    public void prepareHolder(DetailViewAdapter.DetailHolder holder, int pos) {
        if (position == pos) {
            holder.favorite_function.setOnClickListener(this);
            holder.share_function.setOnClickListener(this);
        } else {
            holder.favorite_function.setOnClickListener(null);
            holder.share_function.setOnClickListener(null);
        }
    }

    @Override
    public void setViewHolder(DetailViewAdapter.DetailHolder holder, int pos) {
        myHolder = holder;
        position = pos;
        registerOnClick();
    }

    @Override
    public DetailViewAdapter.DetailHolder getViewHolder() {
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
    public void addToFavorite() {

        daoManager.initFavoriteIndividualDao();
        FavoriteDao favoriteDao = daoManager.getFavoriteIndividualDao();

        Favorite favorite = mapFavoriteItem();

        boolean isSuccess = MyGreenDaoUtils.addSingleImojiFavorite(favoriteDao, favorite);
        if (isSuccess) {
            Toast.makeText(mContext, "Add to favorite: " + position, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "fail to add to favorite", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void handleAnimation() {
        if (myHolder == null) {
            throw new RuntimeException("ClickHandler: myHolder can not be null");
        }


    }

    @Override
    public void shareAction() {

    }

    private Favorite mapFavoriteItem() {

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
