package com.pheth.hasee.stickerhero.Activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.R;

import io.imoji.sdk.objects.Imoji;


/**
 * Created by allengotstuff on 11/19/2016.
 */
public class BaseSecondaryActivity extends AppCompatActivity {

    private ImageView backbutton;
    protected TextView title_textview;

    protected String search_id;
    protected BaseData baseData;

    protected String category_title;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        backbutton = (ImageView)findViewById(R.id.back_button);
        title_textview = (TextView)findViewById(R.id.tv_pageid);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
