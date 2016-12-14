package com.pheth.hasee.stickerhero.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.pheth.hasee.stickerhero.MyApplication;
import com.pheth.hasee.stickerhero.R;
import com.pheth.hasee.stickerhero.iemoji.IemojiUtil;

import io.imoji.sdk.objects.Imoji;
import io.imoji.sdk.objects.RenderingOptions;

/**
 * Created by allengotstuff on 9/24/2016.
 */
public class CommonUtils {

    public static boolean isPackageExist(String packagename,Context context) {
        PackageManager pm = context.getPackageManager();

        boolean installed;

        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);

            installed = true;

        } catch (PackageManager.NameNotFoundException e) {

            installed = false;
        }

        return installed;
    }


    public static void saveSocialSetting(Context context, String packageName) {

        String packageId = "";
        String[] socialArrayTitle = context.getResources().getStringArray(R.array.social_package_title);
        String[] socialArrayPackage = context.getResources().getStringArray(R.array.social_package_id);

        if (socialArrayTitle.length != socialArrayPackage.length) {
            Log.e("saveSocialSetting", "socialArrayTitle != socialArrayPackage is length");
            return;
        }

        for (int i = 0; i < socialArrayTitle.length; i++) {
            if (packageName.equals(socialArrayTitle[i])) {
                packageId = socialArrayPackage[i];
                break;
            }
        }

        SharedPreferences sp = context.getSharedPreferences(Constants.SOCIAL_PREFERENCE_SETTING, Context.MODE_PRIVATE);
        sp.edit().putString(Constants.PREFERENCE_SHARE_PACKAGE, packageId).commit();
        MyApplication.sharedPackage = packageId;
    }

    public static String getSocialShareOption(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SOCIAL_PREFERENCE_SETTING, Context.MODE_PRIVATE);
        return sp.getString(Constants.PREFERENCE_SHARE_PACKAGE, context.getResources().getString(R.string.whatsapp));
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }


    public static DraweeController getController(Imoji imojiItem) {

        RenderingOptions options = IemojiUtil.getRenderOption(imojiItem, RenderingOptions.Size.Thumbnail);
        final Uri uri = imojiItem.urlForRenderingOption(options);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        return controller;
    }
}
