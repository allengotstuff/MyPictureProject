package com.pheth.hasee.stickerhero.iemoji;

import android.view.View;
import android.widget.GridView;

/**
 * Created by hasee on 2016/8/4.
 * This the the absrast controller to general view, and refresh the view's data
 */
public interface LayouController {

//    GridView initPreviewEachCategoryGridview(List<Imoji> list);
    void displaySingleCatgoryGridview(View v);
    void refreshData(CategoryStickAdapter adapter);
    GridView initFullEachCategoryGridView(String id);
    void setCategoryTitle(String title);
}
