package com.pheth.hasee.stickerhero.ClickHandler;

import android.support.v7.widget.RecyclerView;


import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;

import io.imoji.sdk.objects.Imoji;

/**
 * Created by allengotstuff on 11/22/2016.
 */
public interface ClickHandler<V extends RecyclerView.ViewHolder,T extends BaseData> extends BaseClickHandler {

    void setViewHolder(V holder, int pos);

    V getViewHolder();

    void setData(T data);

    T getData();

    void prepareHolder(V holder, int pos);

}
