package com.pheth.hasee.stickerhero.ClickHandler;

import android.support.v7.widget.RecyclerView;


import io.imoji.sdk.objects.Imoji;

/**
 * Created by allengotstuff on 11/22/2016.
 */
public interface ClickHandler<V extends RecyclerView.ViewHolder> extends BaseClickHandler {

    void setViewHolder(V holder);

    V getViewHolder();

}
