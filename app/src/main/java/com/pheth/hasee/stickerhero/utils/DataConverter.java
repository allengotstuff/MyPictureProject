package com.pheth.hasee.stickerhero.utils;

import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.BaseData.Factory.CategoryContainerFactory;
import com.pheth.hasee.stickerhero.BaseData.Factory.ImojiContainerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.imoji.sdk.objects.Category;
import io.imoji.sdk.objects.Imoji;

/**
 * Created by allengotstuff on 12/6/2016.
 */
public class DataConverter {

    public static List<BaseData> convertData(List list){


        ArrayList<BaseData> result = new ArrayList<>();

        if(list.get(0) instanceof Category){
            CategoryContainerFactory factory = new CategoryContainerFactory();
            Iterator iterator = list.iterator();
            while(iterator.hasNext()){
                Category category = (Category) iterator.next();

                result.add(factory.produce(category));
            }
        }else if(list.get(0) instanceof Imoji){

            ImojiContainerFactory factory = new ImojiContainerFactory();
            Iterator iterator = list.iterator();
            while(iterator.hasNext()){
                Imoji imoji = (Imoji) iterator.next();

                result.add(factory.produce(imoji));
            }
        }

        return result ;
    }
}
