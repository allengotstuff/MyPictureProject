package com.pheth.hasee.stickerhero.BaseData.Factory;

import android.net.Uri;
import android.text.TextUtils;

import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;
import com.pheth.hasee.stickerhero.BaseData.Data.DataContainer;
import com.pheth.hasee.stickerhero.iemoji.IemojiUtil;

import io.imoji.sdk.objects.Category;
import io.imoji.sdk.objects.Imoji;
import io.imoji.sdk.objects.RenderingOptions;

/**
 * Created by allengotstuff on 12/8/2016.
 */
public class ImojiContainerFactory implements BaseFactory<BaseData, Imoji> {

    public ImojiContainerFactory() {
    }

    @Override
    public BaseData produce(Imoji imoji) {
        DataContainer container = new DataContainer();

        //set name
        if(imoji.getTags()!=null && imoji.getTags().size()>0) {
            String name = imoji.getTags().get(0);
            container.setName(name);
        }

        //set search id
        container.setIdentifier(imoji.getIdentifier());

        //set thumburl
        RenderingOptions options_thumb = IemojiUtil.getRenderOption(imoji, RenderingOptions.Size.Thumbnail);
        Uri uri_thumb = imoji.urlForRenderingOption(options_thumb);
        container.setOnlineThumbUrl(uri_thumb.toString());

        //set full url
        RenderingOptions options_full = IemojiUtil.getRenderOption(imoji, RenderingOptions.Size.Resolution320);
        Uri uri_full = imoji.urlForRenderingOption(options_full);
        container.setOnLineFullUrl(uri_full.toString());

        return container;

    }
}
