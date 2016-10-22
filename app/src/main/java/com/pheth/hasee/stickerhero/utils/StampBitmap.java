package com.pheth.hasee.stickerhero.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.pheth.hasee.stickerhero.R;

import java.io.File;

/**
 * Created by allengotstuff on 10/16/2016.
 */
public class StampBitmap {

//    public static Bitmap stamp(File sourceFile, Context context){
//
//        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        Bitmap sourceBitmap = BitmapFactory.decodeFile(sourceFile.getAbsolutePath(),bmOptions);
//
//        int bitmapWidth = sourceBitmap.getWidth();
//        int bitmapHeight = sourceBitmap.getHeight();
//
//        Bitmap resultBitmap =  Bitmap.createBitmap(bitmapWidth,bitmapHeight,Bitmap.Config.ARGB_8888);
//        Bitmap stamp = BitmapFactory.decodeResource(context.getResources(), R.drawable.water_stamp_icon);
//
//        Canvas canvas = new Canvas(resultBitmap);
//        canvas.drawBitmap(sourceBitmap,0,0,mPaint);
//        canvas.drawBitmap(stamp,bitmapWidth-stamp.getWidth(),bitmapHeight- stamp.getHeight(),mPaint);
//
//        return resultBitmap;
//    }


    public static Bitmap stamp(Bitmap sourceBitmap, Context context){

        final int version = Build.VERSION.SDK_INT;
        int color;
        if (version >= 23) {
            color= ContextCompat.getColor(context, R.color.colorAccent);
        } else {
            color = context.getResources().getColor(R.color.colorAccent);
        }
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(color);
        textPaint.setStrokeWidth(3);
        textPaint.setTextSize(20);

        int stamp_size = 25;
        int bitmapWidth = sourceBitmap.getWidth();
        int bitmapHeight = sourceBitmap.getHeight();
        int bitmapHeight_adjust = bitmapHeight+ stamp_size;

        Bitmap stamp = BitmapFactory.decodeResource(context.getResources(), R.drawable.water_stamp_icon);

        Bitmap resultBitmap =  Bitmap.createBitmap(bitmapWidth,bitmapHeight_adjust,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(sourceBitmap,0,stamp_size,mPaint);

        RectF rectF = new RectF(0,0,stamp_size,stamp_size);
        canvas.drawBitmap(stamp,null,rectF,mPaint);
        canvas.drawText("StickerHero",stamp_size + 10,stamp_size - stamp_size/5 ,textPaint);
        return resultBitmap;
    }
}
