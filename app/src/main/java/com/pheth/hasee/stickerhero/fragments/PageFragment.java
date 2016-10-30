package com.pheth.hasee.stickerhero.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pheth.hasee.stickerhero.Adapter.CardViewAdapter;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.iemoji.CategoryStickAdapter;
import com.pheth.hasee.stickerhero.iemoji.ImojiCategoryLayout;
import com.pheth.hasee.stickerhero.iemoji.ImojiCategoryLayoutSuitable;
import com.pheth.hasee.stickerhero.iemoji.ImojiHistoryLayout;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.ImojiCategoryData;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.ImojiDataContainer;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.RequestInfo;

import java.util.ArrayList;
import java.util.List;

import io.imoji.sdk.objects.Category;

/**
 * Created by allengotstuff on 9/5/2016.
 */
public class PageFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private View myMainView;
    private static final String TAG = PageFragment.class.getName();
    private FrameLayout mainContainer;
    private SwipeRefreshLayout refreshLayout;


    private ImojiCategoryData imojiCategoryData;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        Log.e(TAG, "oncreate" + mPage);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_fragment, container, false);
        mainContainer = (FrameLayout) view.findViewById(R.id.container);

        switch (mPage) {
            case 0:
//                ImojiCategoryLayout imojiCategoryLayout = new ImojiCategoryLayout(getContext());
//                mainContainer.addView(imojiCategoryLayout);
//                myMainView = imojiCategoryLayout;
//                setupRecyclerView();
                break;

            case 1:
                ImojiCategoryLayoutSuitable favorite_Imoji = new ImojiCategoryLayoutSuitable(getContext());
                mainContainer.addView(favorite_Imoji);

                //record the view
                myMainView = favorite_Imoji;
                favorite_Imoji.registerRecevier();
                break;

            case 2:
                ImojiHistoryLayout historyLayout = new ImojiHistoryLayout(getContext());
                mainContainer.addView(historyLayout);

                historyLayout.registerReceiver();
                myMainView = historyLayout;
                break;
        }
        return view;
    }

    private void setupRecyclerView() {
        final RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.recycleview_layout, null);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mainContainer.addView(recyclerView);
        RequestInfo requestInfo = new RequestInfo(Category.Classification.Trending);
        imojiCategoryData = new ImojiCategoryData(getContext().getApplicationContext(), ImojiDataContainer.getCategoryList()) {
            @Override
            public void onPostExecute(List arrayList) {

                CardViewAdapter adapter = new CardViewAdapter(getContext(),arrayList);
                recyclerView.setAdapter(adapter);
            }
        };
        imojiCategoryData.startRequest(requestInfo);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myMainView instanceof ImojiCategoryLayoutSuitable) {
            ((ImojiCategoryLayoutSuitable) myMainView).destoryView();
            Log.e(TAG, "destory ImojiCategoryLayoutSuitable");
        }

        if (myMainView instanceof ImojiCategoryLayout) {
            Log.e(TAG, "destory ImojiCategoryLayout");
            ((ImojiCategoryLayout) myMainView).destoryView();
        }

        if (myMainView instanceof ImojiHistoryLayout) {
            Log.e(TAG, "destory ImojiHistoryLayout");

            ((ImojiHistoryLayout) myMainView).destoryView();
        }

        mainContainer.removeAllViews();
        myMainView = null;
    }
}
