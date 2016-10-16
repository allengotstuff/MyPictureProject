package com.pheth.hasee.stickerhero.headchat;


import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.util.Log;

import com.pheth.hasee.stickerhero.utils.Constants;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Utils {
	public static String LogTag = "henrytest";
	public static String EXTRA_MSG = "extra_msg";
	public static String START_HEADCHAT = "start_head_chat";

	public static final String TAG = "get_running_activity";

	public static boolean canDrawOverlays(Context context){
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			return true;
		}else{
			return Settings.canDrawOverlays(context);
		}
	}

	public static void getCurrentRunningActivity(Context context)
	{
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		if(Build.VERSION.SDK_INT<21){
			Log.e(TAG,"android sdk is smaller than 21");

		List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(3);
		Log.d(TAG, "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClassName());
		ComponentName componentInfo = taskInfo.get(0).topActivity;
		Constants.SHARE_PACKAGE_NAME = componentInfo.getPackageName();
		for(int i = 0; i< taskInfo.size(); i++)
		{
			Log.e(TAG+i,taskInfo.get(i).topActivity.getPackageName());
		}
		}else{
			Log.e(TAG,"android sdk is greater than 21");
//			final Set<String> activePackages = new HashSet<String>();
//			final List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
//			Log.e(TAG," size"+processInfos.get(0).processName);
//			for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
//				if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//					activePackages.addAll(Arrays.asList(processInfo.pkgList));
//				}
//			}
//
//			String[] text = activePackages.toArray(new String[activePackages.size()]);
//
//			for(int i = 0; i <activePackages.size(); i++ ){
//				Log.e(TAG+i,text[i]);
//			}

			usageDate(context);
		}
	}

	public static void usageDate(Context context){

		if(Build.VERSION.SDK_INT>=21) {
			UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(context.USAGE_STATS_SERVICE);
			long time = System.currentTimeMillis();
			// We get usage stats for the last 10 seconds
			List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
			// Sort the stats by the last time used
			if (stats != null) {
				SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
				for (UsageStats usageStats : stats) {
					mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
				}
				if (!mySortedMap.isEmpty()) {
					String topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
					Log.e(TAG," usage data "+topPackageName);
				}
			}
		}
	}
}
