package com.pheth.hasee.stickerhero.iemoji;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pheth.hasee.stickerhero.MyApplication;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.greendao.DaoMaster;
import com.pheth.hasee.stickerhero.greendao.DaoSession;
import com.pheth.hasee.stickerhero.greendao.FavoriteCategory;
import com.pheth.hasee.stickerhero.greendao.FavoriteCategoryDao;
import com.pheth.hasee.stickerhero.utils.Constants;

import java.util.ArrayList;

import io.imoji.sdk.ApiTask;
import io.imoji.sdk.ImojiSDK;
import io.imoji.sdk.grid.components.SearchResult;
import io.imoji.sdk.objects.Imoji;
import io.imoji.sdk.response.ImojisResponse;

/**
 * Created by allengotstuff on 9/1/2016.
 */
public class ImojiCategoryLayoutSuitable extends FrameLayout {


    private Context mContext;
    private FrameLayout frameLayout_category_container;
    private ArrayList<FavoriteCategory> mCategoryList;

    //this is the top level gridview displaying general tag contained imojis
    private GridView categoryGridviw;

    //this is the bottom level gridview display each singl tag groups of imojis
    private GridView singleCategoryGridView;

    private ApiTask.WrappedAsyncTask<ImojisResponse> apiTask;

    private boolean isInChildGridView = false;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private FavoriteCategoryDao favoriteCategoryDao;

    private CategoryStickAdapterSuitable myAdapter;

    private MyStickerSearchAdapter myStickerSearchAdapter;

    private TextView categoryTitle;

    private FrameLayout category_back_container;

    private ProgressBar mProgressbar;

    private TextView tv_explain;

    private LayouController layoutController = new LayouController() {

        @Override
        public void displaySingleCatgoryGridview(View v) {
            frameLayout_category_container.removeAllViews();
            if (v != null) {
                frameLayout_category_container.addView(singleCategoryGridView);
                enterChild();
            }
        }

        @Override
        public void refreshData(final CategoryStickAdapter adapter) {
//            ImojiSDK.getInstance()
//                    .createSession(getContext().getApplicationContext())
//                    .getImojiCategories(new CategoryFetchOptions(Category.Classification.Trending))
//                    .executeAsyncTaskOnExecutor(new ApiTask.WrappedAsyncTask<CategoriesResponse>() {
//                        @Override
//                        protected void onPostExecute(CategoriesResponse imojisResponse) {
//                            //Bind the results to an adapter of sorts
//
//
//                            Log.e("Refreshdate", imojisResponse.getCategories().size() + "");
//                            if (mCategoryList == null)
//                                return;
//
//                            mCategoryList.clear();
//                            List<Category> tempList = imojisResponse.getCategories();
//                            for (int i = 0; i < 21; i++) {
//                                mCategoryList.add(tempList.remove(new Random().nextInt(tempList.size() - 20)));
//                            }
//
//                            if (adapter != null) {
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//
//                    }, MyApplication.getGlobelExector());
        }

        @Override
        public GridView initFullEachCategoryGridView(String id) {

            mProgressbar.setVisibility(View.VISIBLE);
            //init and create the view first
            if (singleCategoryGridView == null) {
                singleCategoryGridView = (GridView) View.inflate(getContext(), R.layout.foto_gif_gridview, null);
            } else {
                singleCategoryGridView.setAdapter(null);
            }

            apiTask = new ApiTask.WrappedAsyncTask<ImojisResponse>() {
                @Override
                protected void onPostExecute(ImojisResponse imojisResponse) {
                    ArrayList<SearchResult> newResults = new ArrayList<SearchResult>();
                    for (Imoji imoji : imojisResponse.getImojis()) {
                        newResults.add(new SearchResult(imoji));
                    }
//                            if (!imojisResponse.getRelatedCategories().isEmpty()) {
//                                newResults.add(new SearchResult((Imoji) null));
//                            }
//                            for (Category c : imojisResponse.getRelatedCategories()) {
//                                newResults.add(new SearchResult(c));
//                            }

                    //setting up the adapter inside the asyk network call.
                    myStickerSearchAdapter = new MyStickerSearchAdapter(mContext, newResults);

//                    myStickerSearchAdapter.setIemojiLayout(mIemojiLayout);
                    singleCategoryGridView.setAdapter(myStickerSearchAdapter);
                    singleCategoryGridView.setOnItemClickListener(myStickerSearchAdapter);

                    mProgressbar.setVisibility(View.GONE);
                }
            };

            //do asy network call
            ImojiSDK.getInstance()
                    .createSession(mContext)
                    .searchImojis(id)
                    .executeAsyncTaskOnExecutor(apiTask, MyApplication.getGlobelExector());

            return singleCategoryGridView;
        }

        @Override
        public void setCategoryTitle(String title) {
            categoryTitle.setText(title);
        }

    };

    private void cleanRequst() {
        if (apiTask != null && !apiTask.isCancelled()) {
            apiTask.cancel(true);
        }
    }

    //reset the view hireracy
    public void reset() {
        frameLayout_category_container.removeAllViews();

        if (singleCategoryGridView != null) {
            singleCategoryGridView.setAdapter(null);
        }

        if (categoryGridviw != null) {
            frameLayout_category_container.addView(categoryGridviw);
        }

        mProgressbar.setVisibility(View.GONE);
        exitChild();
    }

    public void enterChild() {
        isInChildGridView = true;
        category_back_container.setVisibility(View.VISIBLE);
    }

    public void exitChild() {
        isInChildGridView = false;
        category_back_container.setVisibility(View.GONE);
        cleanRequst();
    }


    public boolean isInChildGridView() {
        return isInChildGridView;
    }

    public void cancleRequest() {
        apiTask.cancel(true);
    }


    public ImojiCategoryLayoutSuitable(Context context, AttributeSet attrs) {
        super(context, attrs);
        initdatabase();
        initView(context);

        setData();
    }

    public ImojiCategoryLayoutSuitable(Context context) {
        super(context);
        initdatabase();
        initView(context);

        setData();
    }


    public void setData() {
        mCategoryList = getFavoite();
        setUpCategory();
    }

    private void initView(Context context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.layout_imoji_category, this);
        frameLayout_category_container = (FrameLayout) rootView.findViewById(R.id.fl_category_container);
        categoryTitle = (TextView)rootView.findViewById(R.id.tv_category_title);
        category_back_container = (FrameLayout)rootView.findViewById(R.id.category_back_container);
        tv_explain = (TextView) rootView.findViewById(R.id.tv_explain);
        mProgressbar = (ProgressBar)rootView.findViewById(R.id.loading_progress);
        category_back_container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               reset();
            }
        });
    }

    //setting up the category gridview
    private void setUpCategory() {
        categoryGridviw = (GridView) View.inflate(getContext(), R.layout.foto_gif_gridview, null);
        frameLayout_category_container.addView(categoryGridviw);

        myAdapter = new CategoryStickAdapterSuitable(mContext, mCategoryList);
        myAdapter.setLayoutController(layoutController);

        categoryGridviw.setAdapter(myAdapter);
        categoryGridviw.setOnItemClickListener(myAdapter);
        categoryGridviw.setOnItemLongClickListener(myAdapter);
    }

    public void notifyDataSetChanged() {
        if (mCategoryList == null)
            return;

        mCategoryList.clear();
        mCategoryList.addAll(favoriteCategoryDao.loadAll());

        if(mCategoryList.size()==0){
            tv_explain.setVisibility(View.VISIBLE);
        }else{
            tv_explain.setVisibility(View.GONE);
        }

        myAdapter.notifyDataSetChanged();
    }

    private ArrayList<FavoriteCategory> getFavoite() {
        ArrayList<FavoriteCategory> list = new ArrayList<FavoriteCategory>();
        list.addAll(favoriteCategoryDao.loadAll());

        if(list.size()==0){
            tv_explain.setVisibility(View.VISIBLE);
        }else{
            tv_explain.setVisibility(View.GONE);
        }
        return list;
    }

    private void initdatabase() {
        daoMaster = MyApplication.getDaoMaster();
        if (daoMaster != null) {
            daoSession = daoMaster.newSession();
            favoriteCategoryDao = daoSession.getFavoriteCategoryDao();
        }
    }

    public class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getBooleanExtra(Constants.UPDATE_FAVORITE_LIST, false) && intent.getAction() == Constants.UPDATE_FAVORITE_LIST) {
                //update the favorite list adapter
                notifyDataSetChanged();
                Log.e("UpdateReceiver", "on receive");
            }
        }
    }

    private UpdateReceiver registerReceiver;

    public void registerRecevier() {
        if (registerReceiver == null) {
            registerReceiver = new UpdateReceiver();

        }
        IntentFilter intentFilter = new IntentFilter(Constants.UPDATE_FAVORITE_LIST);
        mContext.registerReceiver(registerReceiver, intentFilter);
    }

    private void unRegisterReceiver() {
        if (registerReceiver != null) {
            mContext.unregisterReceiver(registerReceiver);
        }
    }

    public void destoryView() {
        unRegisterReceiver();
        cleanRequst();

        frameLayout_category_container.removeAllViews();

        if(categoryGridviw!=null) {
            categoryGridviw.setAdapter(null);
            myAdapter = null;
            categoryGridviw = null;
        }

        if(singleCategoryGridView!=null)
        {
            singleCategoryGridView.setAdapter(null);
            myStickerSearchAdapter = null;
            singleCategoryGridView = null;
        }
        mCategoryList = null;
    }
}

