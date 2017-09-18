package com.bamboo.viewpager3d;


import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.view.View;

/**
 * 创建图片倒影的工具类
 *
 * @author
 */
public class ImageReflect {

    //转化为bitmap
    public static Bitmap convertViewToBitmap(View paramView) {
        paramView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        paramView.layout(0, 0, paramView.getMeasuredWidth(), paramView.getMeasuredHeight());
        paramView.setDrawingCacheEnabled(true);
        paramView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        paramView.buildDrawingCache();
        Bitmap bm = paramView.getDrawingCache();
        Bitmap tmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Config.ARGB_4444);
        new Canvas(tmp).drawBitmap(bm, 0, 0, new Paint());
        paramView.destroyDrawingCache();
        paramView.setDrawingCacheEnabled(false);
        return tmp;
    }


    /**
     * 根据图片生成该图片的倒影
     *
     * @param originalImage 原图
     * @param percent       倒影的比例，0～1
     * @return
     */
    public static Bitmap createReflectedImage(Bitmap originalImage, float percent) {

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(1, -1);

        //翻转图片的下半部分
        final Bitmap bitmapWithReflection = Bitmap.createBitmap(originalImage, 0, (int) (height - percent * height), width, (int) (percent * height), matrix, true);

        Canvas canvas = new Canvas(bitmapWithReflection);

        Paint defaultPaint = new Paint();
        defaultPaint.setAntiAlias(true);


        Paint paint = new Paint();

        LinearGradient shader = new LinearGradient(0,
                0, 0, bitmapWithReflection.getHeight()
                , 0xFFffffff, 0x80ffffff,
                TileMode.MIRROR);

        paint.setShader(shader);

        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

        canvas.drawRect(0, 0, width, bitmapWithReflection.getHeight()
                , paint);

//        originalImage.recycle();

        return bitmapWithReflection;
    }


    public static Bitmap createReflectedImage(Bitmap originalImage) {
        return createReflectedImage(originalImage, 1);
    }
}
