package com.pheth.hasee.stickerhero.Animation;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.pheth.hasee.stickerhero.Adapter.DetailViewAdapter;

/**
 * Created by allengotstuff on 11/19/2016.
 */
public class CardHolderAnimation {
    private boolean isExpand = false;

    private static final String TAG = "CardHolderAnimation";

    private DetailViewAdapter.DetailHolder last_click_holder;
    private int click_position = -999;

    private ImageView fuction_1, fuction_2, fuction_3;

    public CardHolderAnimation() {}


    public void setHolderPositon(DetailViewAdapter.DetailHolder holder, int pos) {

        if (click_position == -10){
            //第一次点击事件
        }else if (click_position != pos) {
            //有了点击事件之后， 点击和上一次不一样的位置

            //如果点击的位置不一样，但是是同一个holder
            if(holder==last_click_holder){
                return;
            }

            //对前一个点击的holder做属性恢复动画
            if (isExpand) {
                ObjectAnimator objectAnimator = ObjectAnimator
                        .ofFloat(last_click_holder.detailImage, "scaleX", 1f)
                        .setDuration(200);
                objectAnimator.setInterpolator(new OvershootInterpolator());
                objectAnimator.start();

                ObjectAnimator objectAnimator_02 = ObjectAnimator
                        .ofFloat(last_click_holder.detailImage, "scaleY", 1f)
                        .setDuration(200);

                objectAnimator_02.setInterpolator(new OvershootInterpolator());
                objectAnimator_02.start();

                isExpand = false;
            }
        }else{
            // 点击和上次一样的位置
        }

        last_click_holder = holder;
        click_position = pos;
    }

    public void startAnimation() {
        OvershootInterpolator interpolator = new OvershootInterpolator();
        if (!isExpand) {
            //进行开始动画

            ObjectAnimator objectAnimator = ObjectAnimator
                    .ofFloat(last_click_holder.detailImage, "scaleX", 0.4f)
                    .setDuration(200);
            objectAnimator.setInterpolator(interpolator);
            objectAnimator.start();

            ObjectAnimator objectAnimator_02 = ObjectAnimator
                    .ofFloat(last_click_holder.detailImage, "scaleY", 0.4f)
                    .setDuration(200);

            objectAnimator_02.setInterpolator(interpolator);
            objectAnimator_02.start();

        } else {
            //进行结束动画

            ObjectAnimator objectAnimator = ObjectAnimator
                    .ofFloat(last_click_holder.detailImage, "scaleX", 1f)
                    .setDuration(200);
            objectAnimator.setInterpolator(interpolator);
            objectAnimator.start();

            ObjectAnimator objectAnimator_02 = ObjectAnimator
                    .ofFloat(last_click_holder.detailImage, "scaleY", 1f)
                    .setDuration(200);

            objectAnimator_02.setInterpolator(interpolator);
            objectAnimator_02.start();
        }

        switchAnimation();
    }

    private boolean switchAnimation() {

        if (isExpand) {
            isExpand = false;
        } else {
            isExpand = true;
        }

        return isExpand;
    }

    public boolean isHolderNeedExpand(int pos,DetailViewAdapter.DetailHolder holder ) {

        if (pos == click_position && isExpand) {
            holder.detailImage.setScaleX(0.44f);
            holder.detailImage.setScaleY(0.4f);

            last_click_holder = holder;
            return true;
        }else{
            holder.detailImage.setScaleX(1);
            holder.detailImage.setScaleY(1);
            return false;
        }
    }


    public static void clearAnimation() {

    }

}
