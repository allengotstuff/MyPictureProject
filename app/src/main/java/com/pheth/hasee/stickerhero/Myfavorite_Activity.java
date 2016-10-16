package com.pheth.hasee.stickerhero;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.pheth.hasee.stickerhero.greendao.DaoMaster;
import com.pheth.hasee.stickerhero.greendao.DaoSession;
import com.pheth.hasee.stickerhero.greendao.Favorite;
import com.pheth.hasee.stickerhero.greendao.FavoriteCategory;
import com.pheth.hasee.stickerhero.greendao.FavoriteCategoryDao;
import com.pheth.hasee.stickerhero.greendao.FavoriteDao;
import com.pheth.hasee.stickerhero.iemoji.ImojiCategoryLayoutSuitable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.imoji.sdk.objects.Category;

/**
 * Created by allengotstuff on 9/3/2016.
 */
public class Myfavorite_Activity extends AppCompatActivity{

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private FavoriteCategoryDao favoriteCategoryDao;

    private FrameLayout container;
    private ImojiCategoryLayoutSuitable favorite_Imoji;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initdatabase();

//        initView();

    }

//    private void initView()
//    {
//        container = (FrameLayout)findViewById(R.id.container);
//        favorite_Imoji = new ImojiCategoryLayoutSuitable(this);
//        favorite_Imoji.setData(getFavoite());
//        container.addView(favorite_Imoji);
//    }


    private ArrayList<FavoriteCategory> getFavoite()
    {
       ArrayList<FavoriteCategory> list = new ArrayList<FavoriteCategory>();

        list.addAll(favoriteCategoryDao.loadAll());
        return list;
    }

    private void initdatabase()
    {
        daoMaster = MyApplication.getDaoMaster();
        if(daoMaster!=null)
        {
            daoSession = daoMaster.newSession();
            favoriteCategoryDao = daoSession.getFavoriteCategoryDao();
        }
    }

    @Override
    public void onBackPressed() {
        if(favorite_Imoji.isInChildGridView())
        {
            favorite_Imoji.reset();
        }else {
            super.onBackPressed();
        }
    }

}
