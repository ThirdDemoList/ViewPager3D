package com.bamboo.viewpager3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 倒影组件类
 * Created by tangshuai on 2017/9/14.
 */

public class ReflectView extends LinearLayout {

    private View view;

    private View reflectView;

    private float reflectHeight;

    private int space;

    public ReflectView(Context context, View view, float reflectHeight) {
        super(context);
        this.space = dip2px(4);
        this.view = view;
        this.reflectHeight = reflectHeight;
        setOrientation(LinearLayout.VERTICAL);
        LayoutParams viewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10);
        viewLayoutParams.weight = 10;
        this.addView(view, viewLayoutParams);
        this.setWeightSum(10 + reflectHeight * 10);

        reflectView = new View(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10);
        layoutParams.topMargin = space;
        layoutParams.weight = reflectHeight * 10;
        reflectView.setLayoutParams(layoutParams);
        reflectView.setId(android.R.id.text1);
        addView(reflectView);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setSpace(int space) {
        this.space = space;
        LayoutParams layoutParams = (LayoutParams) reflectView.getLayoutParams();
        layoutParams.topMargin = space;
        reflectView.setLayoutParams(layoutParams);
    }

    public void updateReflect() {
        Bitmap bm = ImageReflect.convertViewToBitmap(view);
        Bitmap reflectBitmap = ImageReflect.createReflectedImage(bm, reflectHeight);
        reflectView.setBackgroundDrawable(new BitmapDrawable(reflectBitmap));
    }


    int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(1, dpValue, getResources().getDisplayMetrics());
    }

}
