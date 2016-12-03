package com.pheth.hasee.stickerhero.Adapter;
import android.support.v7.widget.RecyclerView;
import com.pheth.hasee.stickerhero.Animation.AdapterSelector;
import com.pheth.hasee.stickerhero.ClickHandler.ClickHandler;

/**
 * Created by allengotstuff on 12/3/2016.
 */
public abstract class AnimationAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected AdapterSelector animationHolder;
    protected ClickHandler myClickHandler;

    public void setAnimationHolder(AdapterSelector animationHolder) {
        this.animationHolder = animationHolder;
    }


    public void setClickHandler(ClickHandler clickHandler) {
        myClickHandler = clickHandler;
    }


    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (animationHolder != null) {
            animationHolder.prepareHolder(position, holder);
        }

        if (myClickHandler != null) {
            myClickHandler.prepareHolder(holder, position);
        }
    }

}
