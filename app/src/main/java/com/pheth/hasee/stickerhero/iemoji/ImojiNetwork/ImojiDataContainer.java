package com.pheth.hasee.stickerhero.iemoji.ImojiNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * *************************************************************************
 *
 * @版权所有: 北京云图微动科技有限公司 (C) 2016
 * @创建人: 孙旭光
 * @创建时间: xxx(2016-09-22 13:11)
 * @Email: 410073261@qq.com
 * <p>
 * 描述:com.fotoable.keyboard.emoji.ui.iemoji.ImojiNetwork-ImojiDataContainer
 * <p>
 * <p>
 * *************************************************************************
 */

public class ImojiDataContainer {

    /**
     * 在主界面给imoji fragment用的数据imoji category(分类的数据）
     */
    private static List categoryList;

    public static  List getCategoryList(){
        if (categoryList == null) {
            synchronized (ImojiDataContainer.class) {
                if (categoryList == null) {
                    categoryList = new ArrayList();
                }
            }
        }
        return categoryList;
    }


    private static List featureImojiList;

    public static  List getFeatureImojiListList(){
        if (featureImojiList == null) {
            synchronized (ImojiDataContainer.class) {
                if (featureImojiList == null) {
                    featureImojiList = new ArrayList();
                }
            }
        }
        return featureImojiList;
    }


}
