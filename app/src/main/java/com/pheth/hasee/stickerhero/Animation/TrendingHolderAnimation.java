package com.pheth.hasee.stickerhero.Animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
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

        myHolder.favorite_function.setTranslationX(0f);
        myHolder.favorite_function.setVisibility(View.GONE);

        myHolder.share_function.setTranslationX(0f);
        myHolder.share_function.setVisibility(View.GONE);
    }

    @Override
    void transformHolder(RecyclerView.ViewHolder holder) {
        FlexSpanAdapter.MyHolder myHolder = (FlexSpanAdapter.MyHolder)holder;
        myHolder.detailImage.setScaleX(0.44f);
        myHolder.detailImage.setScaleY(0.4f);
        myHolder.detailImage.setTranslationY(50f);

        myHolder.favorite_function.setVisibility(View.VISIBLE);
        myHolder.favorite_function.setTranslationX(-90f);

        myHolder.share_function.setVisibility(View.VISIBLE);
        myHolder.share_function.setTranslationX(90f);
    }


    private void startDraweeAnimation(RecyclerView.ViewHolder holder){
        FlexSpanAdapter.MyHolder myHolder = (FlexSpanAdapter.MyHolder)holder;
        OvershootInterpolator overshootInterpolator = new OvershootInterpolator();

        ObjectAnimator objectAnimator = ObjectAnimator
                .ofFloat(myHolder.detailImage, "scaleX", 0.4f)
                .setDuration(200);
        objectAnimator.setInterpolator(overshootInterpolator);
        objectAnimator.start();

        ObjectAnimator objectAnimator_02 = ObjectAnimator
                .ofFloat(myHolder.detailImage, "scaleY", 0.4f)
                .setDuration(200);
        objectAnimator_02.setInterpolator(overshootInterpolator);
        objectAnimator_02.start();

        ObjectAnimator objectAnimator_03 = ObjectAnimator
                .ofFloat(myHolder.detailImage, "translationY",50f)
                .setDuration(200);
        objectAnimator_03.setInterpolator(overshootInterpolator);
        objectAnimator_03.start();

        myHolder.favorite_function.setVisibility(View.VISIBLE);
        myHolder.share_function.setVisibility(View.VISIBLE);

        ObjectAnimator objectAnimator_04 = ObjectAnimator
                .ofFloat(myHolder.favorite_function, "translationX",-90f)
                .setDuration(200);
        objectAnimator_04.setInterpolator(overshootInterpolator);
        objectAnimator_04.start();

        ObjectAnimator objectAnimator_05 = ObjectAnimator
                .ofFloat(myHolder.share_function, "translationX",90f)
                .setDuration(200);
        objectAnimator_05.setInterpolator(overshootInterpolator);
        objectAnimator_05.start();
    }

    private void cancleDraweeAnimation(RecyclerView.ViewHolder holder){
        final FlexSpanAdapter.MyHolder myHolder = (FlexSpanAdapter.MyHolder)holder;
        OvershootInterpolator overshootInterpolator = new OvershootInterpolator();

        ObjectAnimator objectAnimator = ObjectAnimator
                .ofFloat(myHolder.detailImage, "scaleX", 1f)
                .setDuration(200);
        objectAnimator.setInterpolator(overshootInterpolator);
        objectAnimator.start();

        ObjectAnimator objectAnimator_02 = ObjectAnimator
                .ofFloat(myHolder.detailImage, "scaleY", 1f)
                .setDuration(200);

        objectAnimator_02.setInterpolator(overshootInterpolator);
        objectAnimator_02.start();

        ObjectAnimator objectAnimator_03 = ObjectAnimator
                .ofFloat(myHolder.detailImage, "translationY", 0)
                .setDuration(100);
        objectAnimator_03.setInterpolator(overshootInterpolator);
        objectAnimator_03.start();

        // share click handler icon animation

        myHolder.favorite_function.setTranslationX(0f);
        myHolder.favorite_function.setVisibility(View.GONE);

        myHolder.share_function.setTranslationX(0f);
        myHolder.share_function.setVisibility(View.GONE);

    }
}
