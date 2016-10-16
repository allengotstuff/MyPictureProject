package com.pheth.hasee.stickerhero.headchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.pheth.hasee.stickerhero.R;

/**
 * Created by allengotstuff on 8/27/2016.
 */
public class MyShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        setContentView(R.layout.activity_main);
        getWindow().setLayout(200,200);
        getWindow().setLayout(200, 200);

    }

}
