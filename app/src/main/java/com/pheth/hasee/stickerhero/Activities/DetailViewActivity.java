package com.pheth.hasee.stickerhero.Activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.pheth.hasee.stickerhero.Adapter.DetailViewAdapter;
import com.pheth.hasee.stickerhero.Animation.HolderAnimation;
import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.ClickHandler.ClickHandler;
import com.pheth.hasee.stickerhero.ClickHandler.DetailClickHandler;
import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.R;

import io.imoji.sdk.objects.Imoji;


/**
 * Created by allengotstuff on 11/11/2016.
 */
public class DetailViewActivity extends BaseSecondaryActivity implements DetailViewAdapter.OnHolderClickListener{

    public static final String URL = "url";
    public static final String DATACONTAINER = "imoji";
    public static final String SEARCH_ID = "search_id";
    public static final String CATEGORY_TITLE = "category_title";

    private RecyclerView recyclerView;

    private Context mContext;

    private DetailViewAdapter adapter;

    private HolderAnimation holderAnimation;

    private ClickHandler clickHandler;

    private static final String TAG = "DetailViewActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.FIT_CENTER,ScalingUtils.ScaleType.FIT_CENTER));
            postponeEnterTransition();
        }

        setContentView(R.layout.activity_detail_view);
        initView();

        setRecyclerView();


        super.onCreate(savedInstanceState);
    }


    private void initView(){

        holderAnimation = new HolderAnimation();

        clickHandler = new DetailClickHandler(DaoManager.getManager(),getBaseContext());

        mContext = getBaseContext();
        baseData = getIntent().getParcelableExtra(DATACONTAINER);
        search_id = getIntent().getStringExtra(SEARCH_ID);
        category_title = getIntent().getStringExtra(CATEGORY_TITLE);

        title_textview.setText(category_title);
        recyclerView = (RecyclerView)findViewById(R.id.rv_detail);
    }



    private void setRecyclerView(){

        adapter = new DetailViewAdapter(getApplicationContext(),baseData,search_id);
        adapter.setOnHolderClickListener(this);
        adapter.setAnimationHolder(holderAnimation);
        adapter.setClickHandler(clickHandler);

        GridLayoutManager layoutManager = new GridLayoutManager(mContext,3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)){
                    case DetailViewAdapter.TYPE_HEADER:
                        return 3;

                    case DetailViewAdapter.TYPE_BODY:
                        return 1;

                    default:
                        return -1;
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);
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

        baseData = null;
        adapter.onRequestCancle();
    }

    @Override
    public void onHolderClick(int pos, RecyclerView.ViewHolder holder) {

        Log.e(TAG, "onclick"+pos );


        //控制点击动画
        holderAnimation.setViewHolder(holder, pos);

        //控制点击的操
        BaseData imoji = adapter.getPosImoji(pos);

        clickHandler.setViewHolder(holder,pos);
        clickHandler.setData(imoji);

    }

}
