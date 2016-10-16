package com.pheth.hasee.stickerhero;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.pheth.hasee.stickerhero.greendao.DaoMaster;
import com.pheth.hasee.stickerhero.greendao.DaoSession;
import com.pheth.hasee.stickerhero.greendao.FavoriteDao;
import com.pheth.hasee.stickerhero.iemoji.ImojiCategoryLayout;

/**
 * Created by allengotstuff on 9/3/2016.
 */
public class Category_Activity extends AppCompatActivity {

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private FavoriteDao favoriteDao;
    private ImojiCategoryLayout mImojiCategoryLayout;
    private FrameLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

//        initdatabase();
        initView();
    }

    private void initView()
    {
        container = (FrameLayout)findViewById(R.id.container) ;
        mImojiCategoryLayout = new ImojiCategoryLayout(this);
        container.addView(mImojiCategoryLayout);
    }

    @Override
    public void onBackPressed() {
        if(mImojiCategoryLayout.isInChildGridView())
        {
            mImojiCategoryLayout.reset();
        }else
        {
            super.onBackPressed();
        }
    }

//    private void initdatabase()
//    {
//        daoMaster = MyApplication.getDaoMaster();
//        if(daoMaster!=null)
//        {
//            daoSession = daoMaster.newSession();
//            favoriteDao = daoSession.getFavoriteDao();
//        }
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mImojiCategoryLayout.clearData();
        Log.e("Category_activity","ondestory");
    }
}
