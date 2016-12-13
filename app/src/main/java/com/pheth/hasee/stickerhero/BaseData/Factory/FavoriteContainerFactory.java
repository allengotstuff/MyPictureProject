package com.pheth.hasee.stickerhero.BaseData.Factory;

import android.text.TextUtils;

import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.BaseData.Data.DataContainer;
import com.pheth.hasee.stickerhero.greendao.Favorite;

/**
 * Created by allengotstuff on 12/13/2016.
 */
public class FavoriteContainerFactory implements BaseFactory<BaseData, Favorite> {


    @Override
    public BaseData produce(Favorite data) {
        DataContainer container = new DataContainer();

        String name = data.getName();
        if (!TextUtils.isEmpty(name))
            container.setName(name);

        container.setIdentifier(data.getIdentifier());

        container.setOnLineFullUrl(data.getUrl_full());
        container.setOnlineThumbUrl(data.getUrl_thumb());

        return container;
    }
}
