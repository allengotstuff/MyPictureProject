package com.pheth.hasee.stickerhero.Animation;

/**
 * Created by allengotstuff on 11/24/2016.
 *
 * this is a class to help you keep track of which item is been selected from adapter data in adapter related classes,
 * such as recyclerview, listview and gridview.
 *
 *  caution: this should be on main thread.
 */
public abstract class AdapterSelector<T>  {

    private static final String TAG = "AdapterSelector";

    private boolean selected;

    private T myHolder;

    private int lastClickPos;

    public abstract void startAnimation(T holder);

    public abstract void cancelAnimation(T holder);



    abstract void restoreHolder(T holder);
    abstract void transformHolder(T holder);



    public AdapterSelector(){
        selected = false;
        lastClickPos = -999;
    }

    private void setupAnimation(){

        if(myHolder==null) {
            throw new RuntimeException(TAG + " myHolder is null");
        }

        if(!selected){
            startAnimation(myHolder);
        }else{
            cancelAnimation(myHolder);
        }
    }


    /**
     *  This method should be use in Recyclerview's onClick to bind animation control with viewHolder.
     * @param holder
     * @param pos
     */
    public void setViewHolder(T holder, int pos){

        if (lastClickPos == -999){
            //第一次点击事件

        }else if (lastClickPos != pos) {
            //有了点击事件之后， 点击和上一次不一样的位置

//            //如果点击的位置不一样，但是是同一个holder
//            if(holder==myHolder){
//                startAnimation(myHolder);
//                switchAnimation();
//                return;
//            }

            //对前一个点击的holder做属性恢复动画
            if (selected) {
                cancelAnimation(myHolder);

                selected = false;
            }

        }else{
            // 点击和上次一样的位置
        }

        myHolder = holder;
        lastClickPos = pos;

        //开始动画
        setupAnimation();

        //记录动画是否开始
        switchAnimation();
    }

    private boolean switchAnimation() {

        if (selected) {
            selected = false;
        } else {
            selected = true;
        }
        return selected;
    }


    /**
     * This method should be called in onbindholder or getView in recyclerview or listview.
     * @param pos
     * @param holder
     * @return
     */
    public boolean prepareHolder(int pos , T holder){
        if (pos == lastClickPos && selected) {

            transformHolder(holder);
            myHolder = holder;

            return true;
        }else{

            restoreHolder(holder);
            return false;
        }
    }


}
