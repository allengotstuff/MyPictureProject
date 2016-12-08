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
 * Created by allengotstuff on 12/6/2016.
 */
public class CategoryContainerFactory implements BaseFactory<BaseData, Category> {


    public CategoryContainerFactory() {
    }

    @Override
    public BaseData produce(Category category) {

        Imoji previewImoji = category.getPreviewImoji();
        DataContainer container = new DataContainer();

        //set name
        String name = category.getTitle();
        if (!TextUtils.isEmpty(name))
            container.setName(name);

        //set search id
        container.setIdentifier(category.getIdentifier());

        //set thumburl
        RenderingOptions options_thumb = IemojiUtil.getRenderOption(previewImoji, RenderingOptions.Size.Thumbnail);
        Uri uri_thumb = previewImoji.urlForRenderingOption(options_thumb);
        container.setOnlineThumbUrl(uri_thumb.toString());

        //set full url
        RenderingOptions options_full = IemojiUtil.getRenderOption(previewImoji, RenderingOptions.Size.Resolution320);
        Uri uri_full = previewImoji.urlForRenderingOption(options_full);
        container.setOnLineFullUrl(uri_full.toString());

        return container;
    }
}
