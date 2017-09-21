package com.bambootang.sample;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bambootang.viewpager3d.AlphaTransformer;
import com.bambootang.viewpager3d.ClipView;
import com.bambootang.viewpager3d.FlowTransformer;
import com.bambootang.viewpager3d.FlowViewPager;
import com.bambootang.viewpager3d.PowerAlphaTransformer;
import com.bambootang.viewpager3d.ReflectView;

import java.util.HashMap;

public class Sample3Activity extends AppCompatActivity {

    int[] imgIds = {R.drawable.img_001,
            R.drawable.img_002,
            R.drawable.img_003,
            R.drawable.img_004,
            R.drawable.img_005,
            R.drawable.img_006,
            R.drawable.img_007
    };

    FlowViewPager fvp_pagers;

    HashMap<Integer, ClipView> imageViewList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample3);

        fvp_pagers = (FlowViewPager) findViewById(R.id.fvp_pagers);
        fvp_pagers.setAdapter(pagerAdapter);

        fvp_pagers.setOffscreenPageLimit(imgIds.length);

//        fvp_pagers.getTransformer()
        FlowTransformer flowTransformer = new FlowTransformer(fvp_pagers);
        //执行切割
        flowTransformer.setDoClip(true);
        //执行旋转
        flowTransformer.setDoRotationY(true);
        //设置间隔为8
        flowTransformer.setSpace(dip2px(0));
        //在中间位置切换上下分页
        flowTransformer.setPageRoundFactor(0.5f);
        //设置倒影的递减因子
        flowTransformer.setAlphaTransformer(new PowerAlphaTransformer(0.5f));
        fvp_pagers.setPageTransformer(true, flowTransformer);

    }

    int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(1, dpValue, getResources().getDisplayMetrics());
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return imgIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ClipView clipView;
            if (imageViewList.containsKey(position)) {
                clipView = imageViewList.get(position);
            } else {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setImageResource(imgIds[position]);
                imageView.setAdjustViewBounds(false);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                ReflectView reflectView = new ReflectView(container.getContext());
                reflectView.setContentView(imageView, 0.5f);
                reflectView.updateReflect();
                clipView = new ClipView(container.getContext());
                clipView.setId(position + 1);
                clipView.addView(reflectView);
                imageViewList.put(position, clipView);
            }
            container.addView(clipView);
            clipView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "v.getId " + v.getId(), Toast.LENGTH_LONG).show();
                    fvp_pagers.setCurrentItem(position,true);
                }
            });
            return clipView;
        }
    };
}
