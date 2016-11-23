package com.pheth.hasee.stickerhero.ClickHandler;
import android.net.Uri;

import com.pheth.hasee.stickerhero.Adapter.DetailViewAdapter;
import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
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
public class DetailClickHandler implements ClickHandler<DetailViewAdapter.DetailHolder> {

    private Imoji dataImoji;
    private DetailViewAdapter.DetailHolder myHolder;
    private DaoManager daoManager;
    private Favorite myFavorite;


    public DetailClickHandler(Imoji imoji, DaoManager daoManager) {
        this.daoManager = daoManager;
        dataImoji = imoji;
    }

    @Override
    public void setViewHolder(DetailViewAdapter.DetailHolder holder) {
        myHolder = holder;
    }

    @Override
    public DetailViewAdapter.DetailHolder getViewHolder() {
        return myHolder;
    }



    @Override
    public void addToFavorite() {

        daoManager.initFavoriteIndividualDao();
        FavoriteDao favoriteDao = daoManager.getFavoriteIndividualDao();
        mapFavoriteItem();

        MyGreenDaoUtils.addSingleImojiFavorite(favoriteDao,myFavorite);
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

        RenderingOptions options_thumb = IemojiUtil.getRenderOption(dataImoji, RenderingOptions.Size.Thumbnail);
        Uri uri_thumb = dataImoji.urlForRenderingOption(options_thumb);

        RenderingOptions option_full = IemojiUtil.getRenderOption(dataImoji, RenderingOptions.Size.Resolution320);
        Uri uri_full = dataImoji.urlForRenderingOption(option_full);

        myFavorite = new Favorite();
        myFavorite.setIdentifier(dataImoji.getIdentifier());
        myFavorite.setAdd_date(Calendar.getInstance().getTime());
        myFavorite.setUrl_thumb(uri_thumb.toString());
        myFavorite.setUrl_full(uri_full.toString());
        return myFavorite;
    }
}
