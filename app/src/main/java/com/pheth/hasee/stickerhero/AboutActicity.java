package com.pheth.hasee.stickerhero;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.pheth.hasee.stickerhero.Activities.BaseSecondaryActivity;
import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.greendao.Favorite;
import com.pheth.hasee.stickerhero.greendao.FavoriteCategory;

import java.util.ArrayList;

/**
 * Created by allengotstuff on 11/27/2016.
 */
public class AboutActicity extends BaseSecondaryActivity {

    private DaoManager daoManager;

    private static final String TAG = "AboutActicity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.about_activity);

        initdatabase();
        int size = getFavoite();

        Log.e(TAG,"favorite size "+ size);
    }




    private void initdatabase() {
        daoManager = DaoManager.getManager();
        daoManager.initFavoriteIndividualDao();
    }

    private int getFavoite() {
        ArrayList<Favorite> list = new ArrayList<Favorite>();
        list.addAll(daoManager.getFavoriteIndividualDao().loadAll());

        return list.size();
    }
}
