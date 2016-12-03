package com.pheth.hasee.stickerhero.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.fragments.FavoritImojiFragment;

/**
 * Created by allengotstuff on 11/8/2016.
 */
public class BaseActvity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
//        setStatusBarColor();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setupToolbar();
        setupDrawerNav();
    }

    private void setStatusBarColor() {
        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            int color;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = getBaseContext().getColor(R.color.colorAccent);
            } else {
                color = getResources().getColor(R.color.colorAccent);
            }
            window.setStatusBarColor(color);
        }

    }

    private void setupDrawerNav() {
        mNavigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                Log.e("Drawer", "homeClick");
                                break;

                            case R.id.nav_about:
                                Intent intent = new Intent(getBaseContext(), AboutActicity.class);
                                startActivity(intent);
                                break;

                            case R.id.nav_favorite_imoji:
                                Intent intent_favorite = new Intent(getBaseContext(), FavoriteActivity.class);
                                startActivity(intent_favorite);
                                break;

                        }
                        return true;
                    }
                });
        mNavigationView.setCheckedItem(R.id.nav_home);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu_icon);
//            actionBar.setDisplayShowTitleEnabled(true);
//            actionBar.setTitle("StickerHero");
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
