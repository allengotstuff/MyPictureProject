package com.pheth.hasee.stickerhero;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.pheth.hasee.stickerhero.Activities.BaseActvity;
import com.pheth.hasee.stickerhero.Adapter.FragmentPageAdapter;

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
