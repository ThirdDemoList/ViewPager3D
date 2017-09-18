package com.bambootang.viewpager3d;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.lang.reflect.Field;

/**
 * Created by tangshuai on 2017/9/14.
 */

public class FlowViewPager extends ViewPager {

    private PageTransformer transformer;

    public FlowViewPager(Context context) {
        super(context);
        init();
    }

    public FlowViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {
        this.setPageTransformer(true, new FlowTransformer(this), LAYER_TYPE_HARDWARE);
    }

    /**
     * 重写setPageTransformer方法，强制使用DRAW_ORDER_REVERSE排序方法
     *
     * @param reverseDrawingOrder
     * @param transformer
     * @param pageLayerType
     */
    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer, int pageLayerType) {
        super.setPageTransformer(false, transformer, pageLayerType);
        this.transformer = transformer;
    }

    public PageTransformer getTransformer() {
        return this.transformer;
    }

    //    因为滑动过程中getCurrentItem不会变化，所以无法方便的在滑动过程中调整排序,在order为,这里重写，只是为了以防万一霸蛮要把mDrawingOrder设置为DRAW_ORDER_FORWARD
    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        Field mDrawingOrder = null;
        try {
            mDrawingOrder = ViewPager.class.getDeclaredField("mDrawingOrder");
            mDrawingOrder.setAccessible(true);
            int order = (int) mDrawingOrder.get(this);
            if (order != 2) {
                int currentItem = getCurrentItem();
                int[] sorts = new int[childCount];
                for (int index = 0; index <= currentItem; index++) {
                    sorts[index] = index;
                }
                sorts[currentItem] = 6;
                for (int index = 0; index < childCount - currentItem; index++) {
                    sorts[childCount - 1 - index] = index + currentItem;
                }
                int sort = sorts[i];
                return sort;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return super.getChildDrawingOrder(childCount, i);

    }


}
