package com.pheth.hasee.stickerhero;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.pheth.hasee.stickerhero.Adapter.DetailViewAdapter;

import io.imoji.sdk.objects.Imoji;

/**
 * Created by allengotstuff on 11/11/2016.
 */
public class DetailViewActivity extends AppCompatActivity {

    public static final String URL = "url";
    public static final String IMOJI = "imoji";

    private RecyclerView recyclerView;
    private Imoji baseImoji;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.FIT_CENTER,ScalingUtils.ScaleType.FIT_CENTER));
        }
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }
        setContentView(R.layout.activity_detail_view);
        initView();

//        setupDrawee();
        setRecyclerView();
    }


    private void initView(){
        mContext = getBaseContext();
        baseImoji = getIntent().getParcelableExtra(IMOJI);
        recyclerView = (RecyclerView)findViewById(R.id.rv_detail);
    }



    private void setRecyclerView(){

        DetailViewAdapter adapter = new DetailViewAdapter(getApplicationContext(),baseImoji);

        recyclerView.setLayoutManager(new GridLayoutManager(mContext,1));
        recyclerView.setAdapter(adapter);

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startPostponedEnterTransition();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        baseImoji = null;
    }
}
