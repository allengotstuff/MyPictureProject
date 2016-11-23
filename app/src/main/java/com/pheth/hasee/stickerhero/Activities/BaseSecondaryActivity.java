package com.pheth.hasee.stickerhero.Activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheth.hasee.stickerhero.R;

import io.imoji.sdk.objects.Imoji;


/**
 * Created by allengotstuff on 11/19/2016.
 */
public class BaseSecondaryActivity extends AppCompatActivity {

    private ImageView backbutton;
    private TextView title_textview;

    protected String search_id;
    protected Imoji baseImoji;

    protected String category_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backbutton = (ImageView)findViewById(R.id.back_button);
        title_textview = (TextView)findViewById(R.id.tv_pageid);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        title_textview.setText(category_title);
    }


}
