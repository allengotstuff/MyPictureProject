package com.pheth.hasee.stickerhero.fragments;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheth.hasee.stickerhero.Adapter.CardViewAdapter;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.ImojiCategoryData;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.ImojiDataContainer;
import com.pheth.hasee.stickerhero.iemoji.ImojiNetwork.RequestInfo;

import java.util.List;

import io.imoji.sdk.objects.Category;

/**
 * Created by allengotstuff on 10/30/2016.
 */
public class CategoryFragment extends BaseFragment {

    private ImojiCategoryData imojiCategoryData;
    private RecyclerView recyclerView;

    public static final String NAMETAG = "category_fragment";

    @Override
    void clearReference() {
        imojiCategoryData.onCancel();
    }

    @Override
    public void updateFragment() {

    }

    @Override
    void initView() {
        setupRecyclerView();
    }

    @Override
    void setActionName() {
        actionName = NAMETAG;
    }


    private void setupRecyclerView() {
        recyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.recycleview_layout, null);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new ItemDecoration(30));

        mainView.addView(recyclerView);
        RequestInfo requestInfo = new RequestInfo(Category.Classification.Trending);
        imojiCategoryData = new ImojiCategoryData(getContext().getApplicationContext(), ImojiDataContainer.getCategoryList()) {
            @Override
            public void onPostExecute(List arrayList) {

                CardViewAdapter adapter = new CardViewAdapter(getContext(), arrayList);
                recyclerView.setAdapter(adapter);
            }
        };
        imojiCategoryData.startRequest(requestInfo);
    }

    class ItemDecoration extends RecyclerView.ItemDecoration{

        private int spacing;

        public ItemDecoration(int space){
            spacing = space;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

           int pos =  parent.getChildAdapterPosition(view);

            if(pos==0 || pos%3==0){
                outRect.set(spacing,spacing,spacing/2,0);
            }

            if(pos==1 || pos%3==1){
                outRect.set(spacing/2,spacing,spacing/2,0);
            }

            if(pos==2 || pos%3==2){
                outRect.set(spacing/2,spacing,spacing,0);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            super.getItemOffsets(outRect, itemPosition, parent);
        }
    }


}
