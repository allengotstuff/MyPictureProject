package com.pheth.hasee.stickerhero.iemoji;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.flurry.android.FlurryAgent;
import com.pheth.hasee.stickerhero.MyApplication;
import com.pheth.hasee.stickerhero.greendao.History;
import com.pheth.hasee.stickerhero.greendao.HistoryDao;
import com.pheth.hasee.stickerhero.utils.CommonUtils;
import com.pheth.hasee.stickerhero.utils.Constants;
import com.pheth.hasee.stickerhero.utils.MyGreenDaoUtils;
import com.pheth.hasee.stickerhero.utils.StaticConstant;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.imoji.sdk.objects.Imoji;
import io.imoji.sdk.objects.RenderingOptions;


/**
 * Created by hasee on 2016/8/2.
 */
public class IemojiUtil {

    public static final String GIFSUFFIX = ".gif";
    public static boolean IS_SHARE_IN_PROGRESS = false;


    private static Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 分享gif
     *
     * @param ctx
     * @param url
     */
    public static void shareGif(final Context ctx, final String url, final HistoryDao historyDao, final History historyItem) {

        IS_SHARE_IN_PROGRESS = true;
        ImageRequest imageRequest = ImageRequest.fromUri(url);
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                .getEncodedCacheKey(imageRequest, null);
        BinaryResource resource = ImagePipelineFactory.getInstance()
                .getMainDiskStorageCache().getResource(cacheKey);

        if (resource != null) {
            File cacheFile = ((FileBinaryResource) resource).getFile();
            String fileName = cacheFile.getName();
            int filePosi = fileName.lastIndexOf(".");
            String targetFileName = fileName.substring(0, filePosi)
                    + GIFSUFFIX;
            String targetFilePath = null;
            File targetFile = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                    && ctx != null) {

                if (ctx.getExternalFilesDir(null) != null
                        && ctx.getExternalFilesDir(null).getAbsolutePath() != null) {
//                        GifNewShowView.clearPrivateCache(ctx);
                    targetFilePath = ctx.getExternalFilesDir(null).getAbsolutePath() + "/share/gif/";
                    targetFile = new File(targetFilePath, targetFileName);
                }

                if (targetFile != null) {

                    if (!targetFile.exists()) {
                        try {
                            copyFile(cacheFile, targetFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            }

            //添加下载本地文件的url
            historyItem.setUrl_send_local(targetFile.getAbsolutePath());

           boolean isUpdate =  MyGreenDaoUtils.AddToHistory(historyDao,historyItem);

            if(isUpdate){
            //通知historylayout有分享内容更新的软件UI
            Intent intent = new Intent(Constants.UPDATE_HISTORY_LIST);
            intent.putExtra(Constants.UPDATE_HISTORY_LIST,true);
            ctx.sendBroadcast(intent);
                Log.e("IemojiUtil","add to database successfully");
            }

            sharedFile(ctx, targetFile);
            IS_SHARE_IN_PROGRESS = false;
        } else {

            Toast.makeText(ctx, "loading image...", Toast.LENGTH_SHORT).show();

            final ImagePipeline imagePipeline = Fresco.getImagePipeline();
            imagePipeline.prefetchToBitmapCache(imageRequest, null).subscribe(new DataSubscriber<Void>() {
                @Override
                public void onNewResult(DataSource<Void> dataSource) {
                    shareGif(ctx, url, historyDao, historyItem);
                    IS_SHARE_IN_PROGRESS = false;

                }

                @Override
                public void onFailure(DataSource<Void> dataSource) {
                    IS_SHARE_IN_PROGRESS = false;
                }

                @Override
                public void onCancellation(DataSource<Void> dataSource) {
                    IS_SHARE_IN_PROGRESS = false;
                }

                @Override
                public void onProgressUpdate(DataSource<Void> dataSource) {

                }
            }, MyApplication.getGlobelExector());

        }
    }

    public static void sharedFile(final Context ctx, File targetFile) {

        FlurryAgent.logEvent(StaticConstant.NUMBER_OF_TIME_SHARE_ON_SOCIAL);
        if(TextUtils.isEmpty(MyApplication.sharedPackage)){
            Toast.makeText(ctx,"package name is empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if (!CommonUtils.isPackageExist(ctx, MyApplication.sharedPackage)) {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ctx,"you don't have the selected social platform installed on your phone",Toast.LENGTH_SHORT).show();
                }
            });

            return;
        }

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.setPackage(MyApplication.sharedPackage);
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(targetFile));
        share.setType("image/gif");
        ctx.startActivity(share);
    }

    public static void cleanCache() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
        imagePipeline.clearDiskCaches();
        imagePipeline.clearCaches();
    }

    public static RenderingOptions getRenderOption(Imoji imoji, RenderingOptions.Size size) {
        RenderingOptions.ImageFormat imageFormat;

        if (imoji.hasAnimationCapability()) {
            imageFormat = RenderingOptions.ImageFormat.AnimatedGif;
        } else {
            imageFormat = RenderingOptions.ImageFormat.Png;
        }
        RenderingOptions mStickDisplayOptions = new RenderingOptions(
                RenderingOptions.BorderStyle.None,
                imageFormat,
                size);
        return mStickDisplayOptions;
    }

    public static void copyFile(File original, File targerFile) throws IOException {
        if (!targerFile.getParentFile().exists()) {
            targerFile.getParentFile().mkdirs();
        }
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(original);
            out = new FileOutputStream(targerFile);
            inBuff = new BufferedInputStream(in);
            outBuff = new BufferedOutputStream(out);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = inBuff.read(buf)) != -1) {
                outBuff.write(buf, 0, len);
            }

            outBuff.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inBuff != null) {
                inBuff.close();
            }
            if (outBuff != null) {
                outBuff.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
