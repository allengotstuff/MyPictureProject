package com.pheth.hasee.stickerhero.iemoji;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pheth.hasee.stickerhero.MyApplication;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.ImojiCategoryData;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.ImojiDataContainer;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.RequestInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.imoji.sdk.ApiTask;
import io.imoji.sdk.ImojiSDK;
import io.imoji.sdk.grid.components.SearchResult;
import io.imoji.sdk.objects.Category;
import io.imoji.sdk.objects.CategoryFetchOptions;
import io.imoji.sdk.objects.Imoji;
import io.imoji.sdk.response.CategoriesResponse;
import io.imoji.sdk.response.ImojisResponse;

/**
 * Created by hasee on 2016/8/2.
 */
public class ImojiCategoryLayout extends FrameLayout {

    private Context mContext;
    private FrameLayout frameLayout_category_container;
    private ArrayList<Category> categoryList;

    //this is the top level gridview displaying general tag contained imojis
    private GridView categoryGridviw;
    private CategoryStickAdapter myCategoryAdapter;

    //this is the bottom level gridview display each singl tag groups of imojis
    private GridView singleCategoryGridView;
    private MyStickerSearchAdapter myStickerSearchAdapter;

    private ApiTask.WrappedAsyncTask<ImojisResponse> singleClickApiTask;

    private boolean isInChildGridView = false;

    private TextView categoryTitle;
    private ProgressBar progressBar;
    private FrameLayout category_back_container;

    private ImojiCategoryData imojiCategoryData;


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

            imojiCategoryData.onRefresh();
        }

        @Override
        public GridView initFullEachCategoryGridView(String id) {

            progressBar.setVisibility(View.VISIBLE);

            //init and create the view first
            if (singleCategoryGridView == null) {
                singleCategoryGridView = (GridView) View.inflate(getContext(), R.layout.foto_gif_gridview, null);
            } else {
                singleCategoryGridView.setAdapter(null);
            }

            singleClickApiTask = new ApiTask.WrappedAsyncTask<ImojisResponse>() {
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

                    progressBar.setVisibility(View.GONE);
                }
            };

            //do asy network call
            ImojiSDK.getInstance()
                    .createSession(mContext.getApplicationContext())
                    .searchImojis(id)
                    .executeAsyncTaskOnExecutor(singleClickApiTask, MyApplication.getGlobelExector());

            return singleCategoryGridView;
        }

        @Override
        public void setCategoryTitle(String title) {
            categoryTitle.setText(title);
        }

    };

    //reset the view hireracy
    public void reset() {
        frameLayout_category_container.removeAllViews();

        if (singleCategoryGridView != null) {
            singleCategoryGridView.setAdapter(null);
        }

        if (categoryGridviw != null) {
            frameLayout_category_container.addView(categoryGridviw);
        }

        progressBar.setVisibility(View.GONE);
        exitChild();
    }

    public void enterChild() {
        isInChildGridView = true;
        category_back_container.setVisibility(View.VISIBLE);
    }

    public void exitChild() {
        isInChildGridView = false;
        category_back_container.setVisibility(View.GONE);
        if (singleClickApiTask != null && !singleClickApiTask.isCancelled()) {
            singleClickApiTask.cancel(true);
        }
    }

    public boolean isInChildGridView() {
        return isInChildGridView;
    }


//    public ImojiCategoryLayout(Context context, IemojiLayout iemojiLayout) {
//        super(context);
//        initView(context);
//        mIemojiLayout = iemojiLayout;
//        setUpCategory();
//    }

    public ImojiCategoryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        setUpCategory();
    }

    public ImojiCategoryLayout(Context context) {
        super(context);
        initView(context);
        setUpCategory();
    }

    private void initView(Context context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.layout_imoji_category, this);
        frameLayout_category_container = (FrameLayout) rootView.findViewById(R.id.fl_category_container);
        categoryTitle = (TextView) rootView.findViewById(R.id.tv_category_title);
        category_back_container = (FrameLayout) rootView.findViewById(R.id.category_back_container);
        progressBar = (ProgressBar) rootView.findViewById(R.id.loading_progress);
        progressBar.setVisibility(View.VISIBLE);
        category_back_container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }


    public void clearAllRequest() {
        if (singleClickApiTask != null && !singleClickApiTask.isCancelled()) {
            singleClickApiTask.cancel(true);
        }

        imojiCategoryData.onCancel();
    }


    //setting up the category gridview
    private void setUpCategory() {
        categoryGridviw = (GridView) View.inflate(getContext(), R.layout.foto_gif_gridview, null);
        frameLayout_category_container.addView(categoryGridviw);

        RequestInfo requestInfo = new RequestInfo(Category.Classification.Trending);
        imojiCategoryData = new ImojiCategoryData(getContext().getApplicationContext(), ImojiDataContainer.getCategoryList()) {
            @Override
            public void onPostExecute(List arrayList) {

                if (categoryList == null) {
                    categoryList = new ArrayList<Category>();
                } else {
                    categoryList.clear();
                }

                categoryList.addAll(arrayList);

                //第一次初始化
                if (myCategoryAdapter == null) {
                    myCategoryAdapter = new CategoryStickAdapter(mContext, categoryList);
                    myCategoryAdapter.setLayoutController(layoutController);

                    categoryGridviw.setAdapter(myCategoryAdapter);
                    categoryGridviw.setOnItemClickListener(myCategoryAdapter);
                    categoryGridviw.setOnItemLongClickListener(myCategoryAdapter);
                }

                myCategoryAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        };
        imojiCategoryData.startRequest(requestInfo);

    }

    public void destoryView() {

        clearAllRequest();

        frameLayout_category_container.removeAllViews();

        if (categoryGridviw != null) {
            categoryGridviw.setAdapter(null);

            //maybe remove listener as well
            myCategoryAdapter = null;
            categoryGridviw = null;
        }


        if (singleCategoryGridView != null) {
            singleCategoryGridView.setAdapter(null);
            myStickerSearchAdapter = null;
            singleCategoryGridView = null;
        }
        categoryList = null;
        imojiCategoryData = null;
    }
}
