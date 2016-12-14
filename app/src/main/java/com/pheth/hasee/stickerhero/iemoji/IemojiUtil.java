package com.pheth.hasee.stickerhero.iemoji;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.flurry.android.FlurryAgent;
import com.pheth.hasee.stickerhero.MyApplication;
import com.pheth.hasee.stickerhero.greendao.History;
import com.pheth.hasee.stickerhero.greendao.HistoryDao;
import com.pheth.hasee.stickerhero.utils.CommonUtils;
import com.pheth.hasee.stickerhero.utils.Constants;
import com.pheth.hasee.stickerhero.utils.MyGreenDaoUtils;
import com.pheth.hasee.stickerhero.utils.StampBitmap;
import com.pheth.hasee.stickerhero.utils.StaticConstant;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

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
    public static void shareGif(final Context ctx, final String url,@Nullable final String packageName, @Nullable final HistoryDao historyDao, @Nullable final History historyItem) {

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

            if(historyDao!=null ||historyItem!=null ) {
                //添加下载本地文件的url
                historyItem.setUrl_send_local(targetFile.getAbsolutePath());

                boolean isUpdate = MyGreenDaoUtils.AddToHistory(historyDao, historyItem);

                if (isUpdate) {
                    //通知historylayout有分享内容更新的软件UI
                    Intent intent = new Intent(Constants.UPDATE_HISTORY_LIST);
                    intent.putExtra(Constants.UPDATE_HISTORY_LIST, true);
                    ctx.sendBroadcast(intent);
                    Log.e("IemojiUtil", "add to database successfully");
                }
            }

            sharedFile(ctx, targetFile,packageName);

            IS_SHARE_IN_PROGRESS = false;
        } else {

            Toast.makeText(ctx, "loading image...", Toast.LENGTH_SHORT).show();

            // below code is execute on worker thread
            final ImagePipeline imagePipeline = Fresco.getImagePipeline();
            imagePipeline.prefetchToBitmapCache(imageRequest, null).subscribe(new DataSubscriber<Void>() {
                @Override
                public void onNewResult(DataSource<Void> dataSource) {
                    shareGif(ctx, url,packageName, historyDao, historyItem);
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

    public static void sharedFile(final Context ctx, File targetFile,@Nullable String packageName) {

        FlurryAgent.logEvent(StaticConstant.NUMBER_OF_TIME_SHARE_ON_SOCIAL);
        if (TextUtils.isEmpty(MyApplication.sharedPackage)) {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ctx, "package name is empty", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        if (!CommonUtils.isPackageExist(MyApplication.sharedPackage,ctx)) {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ctx, "you don't have the selected social platform installed on your phone", Toast.LENGTH_SHORT).show();
                }
            });

            return;
        }

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(!TextUtils.isEmpty(packageName)) {
            share.setPackage(packageName);
        }else{

        }
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(targetFile));
        share.setType("image/gif");
        ctx.startActivity(share);
    }


    public static void getBitmap(final Context ctx, final String url,@Nullable final String packageName, final HistoryDao historyDao, final History historyItem) {
        Toast.makeText(ctx, "loading image...", Toast.LENGTH_SHORT).show();
        IS_SHARE_IN_PROGRESS = true;
        ImageRequest imageRequest = ImageRequest.fromUri(url);
        final ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSubscriber dataSubscriber = new BaseBitmapDataSubscriber() {

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                IS_SHARE_IN_PROGRESS = false;
            }

            @Override
            protected void onNewResultImpl(Bitmap bitmap) {

                if (bitmap == null) {
                    Log.e("IemojiUtil", "bitmap is null ");
                    IS_SHARE_IN_PROGRESS = false;
                    return;
                }

                if (bitmap.isRecycled()) {
                    Log.e("IemojiUtil", "bitmap is recycled ");
                    IS_SHARE_IN_PROGRESS = false;
                    return;
                }

                Bitmap stampedBitmap = StampBitmap.stamp(bitmap, ctx);

                File destinationFile = convertBitmapToFile(stampedBitmap, ctx);

                if (stampedBitmap == null) {
                    Log.e("IemojiUtil", "bitmap convert to file has error, return null file");
                    return;
                }

                if (destinationFile == null) {
                    Log.e("IemojiUtil", "saved file is null");
                    return;
                }

                //添加下载本地文件的url
                historyItem.setUrl_send_local(destinationFile.getAbsolutePath());

                boolean isUpdate = MyGreenDaoUtils.AddToHistory(historyDao, historyItem);

                if (isUpdate) {
                    //通知historylayout有分享内容更新的软件UI
                    Intent intent = new Intent(Constants.UPDATE_HISTORY_LIST);
                    intent.putExtra(Constants.UPDATE_HISTORY_LIST, true);
                    ctx.sendBroadcast(intent);
                    Log.e("IemojiUtil", "add to database successfully");
                }

                IS_SHARE_IN_PROGRESS = false;

                sharedFile(ctx, destinationFile,packageName);

            }
        };
        imagePipeline.fetchDecodedImage(imageRequest, null).subscribe(dataSubscriber, MyApplication.getGlobelExector());
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

    public static File convertBitmapToFile(Bitmap bitmap, Context ctx) {

        File root = null;
        File targerFile = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                && ctx != null) {

            if (ctx.getExternalFilesDir(null) != null
                    && ctx.getExternalFilesDir(null).getAbsolutePath() != null) {

                root = new File(ctx.getExternalFilesDir(null).getAbsolutePath(), "Share");
                if (!root.exists()) {
                    root.mkdirs();
                }
                String targetFileName = UUID.randomUUID().toString() + String.valueOf(System.currentTimeMillis()) + GIFSUFFIX;

                targerFile = new File(root, targetFileName);
            }

            if (targerFile != null) {

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

                //write the bytes in file
                FileOutputStream fos = null;

                try {
                    fos = new FileOutputStream(targerFile);
                    fos.write(bitmapdata);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }

        }

        return targerFile;
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
