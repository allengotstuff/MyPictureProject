package com.pheth.hasee.stickerhero;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.pheth.hasee.stickerhero.Adapter.FragmentPageAdapter;
import com.pheth.hasee.stickerhero.headchat.ChatHeadService;
import com.pheth.hasee.stickerhero.headchat.Utils;
import com.pheth.hasee.stickerhero.utils.CommonUtils;
import com.pheth.hasee.stickerhero.utils.StaticConstant;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends BaseActvity {
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;

    private TabLayout tabLayout;
    private ViewPager myViewPage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }



    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        myViewPage = (ViewPager) findViewById(R.id.viewpager);

        FragmentPageAdapter myAdapter = new FragmentPageAdapter(getSupportFragmentManager(), this);
        myViewPage.setAdapter(myAdapter);
        myViewPage.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(myViewPage);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void requestPermission(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }
}
