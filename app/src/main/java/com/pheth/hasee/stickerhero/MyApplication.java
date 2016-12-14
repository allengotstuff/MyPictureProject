package com.pheth.hasee.stickerhero;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.flurry.android.FlurryAgent;
import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.greendao.DaoMaster;
import com.pheth.hasee.stickerhero.iemoji.ImagePipelineConfigFactory;
import com.pheth.hasee.stickerhero.utils.CommonUtils;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.imoji.sdk.ImojiSDK;
import io.imoji.sdk.objects.Category;
import io.imoji.sdk.objects.RenderingOptions;

/**
 * Created by hasee on 2016/8/21.
 */
public class MyApplication extends Application {

    private static ThreadPoolExecutor threadPoolExecutor;
    private RenderingOptions mStickDisplayOptions;


    private static String TAG = "MyApplication";

    public static String sharedPackage = "";
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
//        Fabric.with(this, new Crashlytics());
        FlurryAgent.setLogEnabled(false);
        FlurryAgent.init(this, "R9C94Z9PXMNDKY6N7J23");

        //记录Share package setting
        sharedPackage = CommonUtils.getSocialShareOption(getApplicationContext());

        //初始化数据库·
        DaoManager.getManager().init(this);


        ImagePipelineConfig pipelineConfig = ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(this);
        Fresco.initialize(this, pipelineConfig);

        threadPoolExecutor =  new ThreadPoolExecutor(20, 35, 20, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

        initImojiSdk();

    }


    private void initImojiSdk() {
        ImojiSDK.getInstance()
                .setCredentials(
                        UUID.fromString("ba875e28-3a34-4917-b4bd-fc1791d8314c"), "U2FsdGVkX1+6yEC89I+nAtnznDRpmOl4N3jk/pEJ4yYEIWTOei0rV6TTOSVNyF8F"
                );
        mStickDisplayOptions =new RenderingOptions(
                RenderingOptions.BorderStyle.None,
                RenderingOptions.ImageFormat.Png,
                RenderingOptions.Size.Thumbnail
        );
    }


    public static ThreadPoolExecutor getGlobelExector()
    {
        return threadPoolExecutor;
    }

}
