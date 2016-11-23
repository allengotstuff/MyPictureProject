package com.pheth.hasee.stickerhero.Activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.pheth.hasee.stickerhero.Adapter.DetailViewAdapter;
import com.pheth.hasee.stickerhero.Animation.CardHolderAnimation;
import com.pheth.hasee.stickerhero.ClickHandler.BaseClickHandler;
import com.pheth.hasee.stickerhero.ClickHandler.DetailClickHandler;
import com.pheth.hasee.stickerhero.R;


/**
 * Created by allengotstuff on 11/11/2016.
 */
public class DetailViewActivity extends BaseSecondaryActivity implements DetailViewAdapter.OnHolderClickListener{

    public static final String URL = "url";
    public static final String IMOJI = "imoji";
    public static final String SEARCH_ID = "search_id";
    public static final String CATEGORY_TITLE = "category_title";

    private RecyclerView recyclerView;

    private Context mContext;

    private DetailViewAdapter adapter;

    private CardHolderAnimation animationHolder;

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
        animationHolder = new CardHolderAnimation();

        mContext = getBaseContext();
        baseImoji = getIntent().getParcelableExtra(IMOJI);
        search_id = getIntent().getStringExtra(SEARCH_ID);
        category_title = getIntent().getStringExtra(CATEGORY_TITLE);

        recyclerView = (RecyclerView)findViewById(R.id.rv_detail);
    }



    private void setRecyclerView(){

//        animation = new CardHolderAnimation();

        adapter = new DetailViewAdapter(getApplicationContext(),baseImoji,search_id);
        adapter.setOnHolderClickListener(this);
        adapter.setHolderAnimation(animationHolder);

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

        baseImoji = null;
        adapter.onRequestCancle();
    }

    @Override
    public void onHolderClick(int pos, RecyclerView.ViewHolder holder) {

        Log.e(TAG, "onclick"+pos );
        animationHolder.setHolderPositon((DetailViewAdapter.DetailHolder)holder,pos);
        animationHolder.startAnimation();
    }

}
