package com.pheth.hasee.stickerhero.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.pheth.hasee.stickerhero.Activities.BaseSecondaryActivity;
import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.fragments.FavoritImojiFragment;
import com.pheth.hasee.stickerhero.greendao.Favorite;
import com.pheth.hasee.stickerhero.greendao.FavoriteCategory;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by allengotstuff on 11/27/2016.
 */
public class AboutActicity extends BaseSecondaryActivity {



    private static final String TAG = "AboutActicity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.about_activity);
        title_textview.setText(R.string.navigation_about);

    }


}
