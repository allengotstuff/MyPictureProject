package com.pheth.hasee.stickerhero.iemoji.ImojiNetwork;
import io.imoji.sdk.objects.Category;


/**
 * *************************************************************************
 *
 * @版权所有: 北京云图微动科技有限公司 (C) 2016
 * @创建人: 孙旭光
 * @创建时间: xxx(2016-09-21 17:01)
 * @Email: 410073261@qq.com
 * <p>
 * 描述:com.fotoable.keyboard.emoji.ui.iemoji.ImojiNetwork-RequestInfo
 * <p>
 * <p>
 * *************************************************************************
 */

public class RequestInfo {

    private String search_id;
    private Category.Classification  option;
    private  int numberOfResutl;

    public enum RequestType{
        ImojisResponse,
        CategoriesResponse
    }

    public RequestInfo(Category.Classification op){
        option = op;
    }

    public String getSearchId(){
      return search_id;
    }

    public Category.Classification getClassification(){
        return  option;
    }

    public int getNumberOfResult(){
        return numberOfResutl;
    }

}
