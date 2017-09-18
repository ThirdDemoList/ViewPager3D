package com.bambootang.viewpager3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by tangshuai on 2017/9/18.
 */

public class ClipView extends LinearLayout implements Clipable {


    private Rect rect = null;

    public ClipView(Context context) {
        super(context);
    }

    public ClipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ClipView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setClipBound(Rect rect) {
        this.rect = rect;
        postInvalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (rect != null)
            canvas.clipRect(rect, Region.Op.INTERSECT);
        super.dispatchDraw(canvas);
    }
}
