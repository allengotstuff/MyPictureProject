package com.pheth.hasee.stickerhero.Animation;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.pheth.hasee.stickerhero.Adapter.DetailViewAdapter;
import com.pheth.hasee.stickerhero.Adapter.FlexSpanAdapter;

/**
 * Created by allengotstuff on 12/3/2016.
 */
public class TrendingHolderAnimation extends AdapterSelector<RecyclerView.ViewHolder>  {

    @Override
    public void startAnimation(RecyclerView.ViewHolder holder) {
        startDraweeAnimation(holder);

    }

    @Override
    public void cancelAnimation(RecyclerView.ViewHolder holder) {
        cancleDraweeAnimation(holder);

    }

    @Override
    void restoreHolder(RecyclerView.ViewHolder holder) {
        FlexSpanAdapter.MyHolder myHolder = (FlexSpanAdapter.MyHolder)holder;
        myHolder.detailImage.setScaleX(1);
        myHolder.detailImage.setScaleY(1);
        myHolder.detailImage.setTranslationY(0);
    }

    @Override
    void transformHolder(RecyclerView.ViewHolder holder) {
        FlexSpanAdapter.MyHolder myHolder = (FlexSpanAdapter.MyHolder)holder;
        myHolder.detailImage.setScaleX(0.44f);
        myHolder.detailImage.setScaleY(0.4f);
        myHolder.detailImage.setTranslationY(50f);
    }


    private void startDraweeAnimation(RecyclerView.ViewHolder holder){
        FlexSpanAdapter.MyHolder myHolder = (FlexSpanAdapter.MyHolder)holder;

        ObjectAnimator objectAnimator = ObjectAnimator
                .ofFloat(myHolder.detailImage, "scaleX", 0.4f)
                .setDuration(200);
        objectAnimator.setInterpolator(new OvershootInterpolator());
        objectAnimator.start();

        ObjectAnimator objectAnimator_02 = ObjectAnimator
                .ofFloat(myHolder.detailImage, "scaleY", 0.4f)
                .setDuration(200);
        objectAnimator_02.setInterpolator(new OvershootInterpolator());
        objectAnimator_02.start();

        ObjectAnimator objectAnimator_03 = ObjectAnimator
                .ofFloat(myHolder.detailImage, "translationY",50f)
                .setDuration(200);
        objectAnimator_03.setInterpolator(new OvershootInterpolator());
        objectAnimator_03.start();
    }

    private void cancleDraweeAnimation(RecyclerView.ViewHolder holder){
        FlexSpanAdapter.MyHolder myHolder = (FlexSpanAdapter.MyHolder)holder;

        ObjectAnimator objectAnimator = ObjectAnimator
                .ofFloat(myHolder.detailImage, "scaleX", 1f)
                .setDuration(200);
        objectAnimator.setInterpolator(new OvershootInterpolator());
        objectAnimator.start();

        ObjectAnimator objectAnimator_02 = ObjectAnimator
                .ofFloat(myHolder.detailImage, "scaleY", 1f)
                .setDuration(200);

        objectAnimator_02.setInterpolator(new OvershootInterpolator());
        objectAnimator_02.start();

        ObjectAnimator objectAnimator_03 = ObjectAnimator
                .ofFloat(myHolder.detailImage, "translationY", 0)
                .setDuration(200);
        objectAnimator_03.setInterpolator(new OvershootInterpolator());
        objectAnimator_03.start();
    }
}
