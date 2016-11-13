package com.pheth.hasee.stickerhero;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by allengotstuff on 11/11/2016.
 */
public class DetailViewActivity extends AppCompatActivity {

    public static final String URL = "url";
    private SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.FIT_CENTER,ScalingUtils.ScaleType.FIT_CENTER));
        }

        setContentView(R.layout.activity_detail_view);
        setupDrawee();
    }

    private void setupDrawee(){
        simpleDraweeView = (SimpleDraweeView)findViewById(R.id.detail_image);
        String url = getIntent().getStringExtra(URL);
        DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(url)
                .setAutoPlayAnimations(true)
                .build();
        simpleDraweeView.setController(controller);
    }
}
