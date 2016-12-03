package com.pheth.hasee.stickerhero.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.fragments.FavoritImojiFragment;

/**
 * Created by allengotstuff on 12/3/2016.
 */
public class FavoriteActivity extends BaseSecondaryActivity {

    private static final String TAG = "FavoriteActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.about_activity);
        title_textview.setText(R.string.navigation_favitie_imoji);

        setFragment();
    }


    private void setFragment(){
        FavoritImojiFragment fragment = new FavoritImojiFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.fragment_container,fragment,TAG).commit();
    }

}
