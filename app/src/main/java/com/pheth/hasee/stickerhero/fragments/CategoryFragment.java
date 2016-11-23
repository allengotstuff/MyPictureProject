package com.pheth.hasee.stickerhero.fragments;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheth.hasee.stickerhero.Adapter.CardViewAdapter;
import com.pheth.hasee.stickerhero.Activities.DetailViewActivity;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.ImojiCategoryData;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.ImojiDataContainer;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.RequestInfo;

import java.util.ArrayList;
import java.util.List;

import io.imoji.sdk.objects.Category;
import io.imoji.sdk.objects.Imoji;

/**
 * Created by allengotstuff on 10/30/2016.
 */
public class CategoryFragment extends BaseFragment implements CardViewAdapter.RecyclerViewOnitemClickListener {

    private ImojiCategoryData imojiCategoryData;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List mDataList;
    private CardViewAdapter adapter;

    public static final String NAMETAG = "category_fragment";

    @Override
    void clearReference() {
        imojiCategoryData.onCancel();
    }

    @Override
    public void updateFragment() {
        Log.e(NAMETAG, "updateFragment");
    }

    @Override
    void setActionName() {
        actionName = NAMETAG;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_fragment, container, false);
        initView(view);
        setupRecyclerView();
        return view;
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                imojiCategoryData.onRefresh();
            }
        });

        new Handler() {
        }.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mDataList == null || mDataList.size() == 0)
                    swipeRefreshLayout.setRefreshing(true);

            }
        }, 500);
    }


    private void setupRecyclerView() {
        mDataList = new ArrayList();
        recyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.recycleview_layout, null);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new ItemDecoration(20));
        adapter = new CardViewAdapter(getContext(), mDataList);
        adapter.setOnitemClickListener(this);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.addView(recyclerView);

        //requesting data
        RequestInfo requestInfo = new RequestInfo(Category.Classification.Trending);
        imojiCategoryData = new ImojiCategoryData(getContext().getApplicationContext(), ImojiDataContainer.getCategoryList()) {
            @Override
            public void onPostExecute(List arrayList) {

                if(mDataList!=null || arrayList!=null || arrayList.size()==0) {
                    mDataList.clear();
                    mDataList.addAll(arrayList);
                }

                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancel() {
                super.onCancel();
                swipeRefreshLayout.setRefreshing(false);
            }
        };
        imojiCategoryData.startRequest(requestInfo);
    }

    @Override
    public void onItemClick(CardViewAdapter.MyViewHolder holder, int pos, Imoji imoji, String categoryId ) {
        Log.e("onclikc",""+pos);
        Log.e("onclick_serachID",""+categoryId);

//        ViewCompat.setTransitionName(holder.categoryImage, getString(R.string.transition_one));
       Category category = (Category)mDataList.get(pos);

        Intent intent = new Intent(getActivity(), DetailViewActivity.class);
        intent.putExtra(DetailViewActivity.IMOJI,imoji);
        intent.putExtra(DetailViewActivity.SEARCH_ID,categoryId);
        intent.putExtra(DetailViewActivity.CATEGORY_TITLE,category.getTitle());

        Pair<View, String> pairValue_1 = Pair.create((View)holder.categoryImage,getString(R.string.transition_drawee));

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        pairValue_1);
//        startActivity(intent);

        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }


    class ItemDecoration extends RecyclerView.ItemDecoration {

        private int spacing;

        public ItemDecoration(int space) {
            spacing = space;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            int totalCount = parent.getAdapter().getItemCount();
            int pos = parent.getChildAdapterPosition(view);

            if (pos == 0 || pos % 3 == 0) {
                outRect.set(spacing, spacing, spacing / 2, 0);
                //add padding to the bottom row
                if (totalCount - pos <= 3) {
                    outRect.set(spacing, spacing, spacing / 2, spacing);
                }
            } else if (pos == 1 || pos % 3 == 1) {
                outRect.set(spacing / 2, spacing, spacing / 2, 0);

                if (totalCount - pos <= 2) {
                    outRect.set(spacing / 2, spacing, spacing / 2, spacing);
                }

            } else if (pos == 2 || pos % 3 == 2) {

                outRect.set(spacing / 2, spacing, spacing, 0);
                if (totalCount - pos <= 1) {
                    outRect.set(spacing / 2, spacing, spacing, spacing);
                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            super.getItemOffsets(outRect, itemPosition, parent);
        }
    }


}
