package com.pheth.hasee.stickerhero.ClickHandler;


/**
 * Created by allengotstuff on 11/21/2016.
 */
public interface BaseClickHandler{

    /**
     * this is the base interface for click handler,
     *
     * Define two function:
     * 1. add it to favorite;
     *
     * 2. share to social plation.
     */

    void addToFavorite();

    void shareAction();

    void handleAnimation();

}
