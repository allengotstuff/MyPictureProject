package com.pheth.hasee.stickerhero.Animation;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.OvershootInterpolator;

import com.pheth.hasee.stickerhero.Adapter.DetailViewAdapter;

/**
 * Created by allengotstuff on 11/25/2016.
 */
public class HolderAnimation extends AdapterSelector<RecyclerView.ViewHolder> {


    public HolderAnimation(){
        super();
    }

    @Override
    public void startAnimation(RecyclerView.ViewHolder holder) {

        DetailViewAdapter.DetailHolder myHolder = (DetailViewAdapter.DetailHolder)holder;

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
    }

    @Override
    public void cancelAnimation(RecyclerView.ViewHolder holder) {
        DetailViewAdapter.DetailHolder myHolder = (DetailViewAdapter.DetailHolder)holder;

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
    }

    @Override
    void restoreHolder(RecyclerView.ViewHolder holder) {
        DetailViewAdapter.DetailHolder myHolder = (DetailViewAdapter.DetailHolder)holder;
        myHolder.detailImage.setScaleX(1);
        myHolder.detailImage.setScaleY(1);
    }

    @Override
    void transformHolder(RecyclerView.ViewHolder holder) {
        DetailViewAdapter.DetailHolder myHolder = (DetailViewAdapter.DetailHolder)holder;
        myHolder.detailImage.setScaleX(0.44f);
        myHolder.detailImage.setScaleY(0.4f);
    }
}
