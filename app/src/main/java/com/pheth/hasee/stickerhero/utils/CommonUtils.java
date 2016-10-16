package com.pheth.hasee.stickerhero.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.pheth.hasee.stickerhero.MyApplication;
import com.pheth.hasee.stickerhero.R;

/**
 * Created by allengotstuff on 9/24/2016.
 */
public class CommonUtils {

    public static boolean isPackageExist(Context context, String packagename) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
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
}
