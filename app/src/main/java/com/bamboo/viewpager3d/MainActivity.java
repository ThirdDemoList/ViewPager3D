package com.bamboo.viewpager3d;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);

        fvp_pagers = (FlowViewPager) findViewById(R.id.fvp_pagers);
        fvp_pagers.setAdapter(pagerAdapter);

        fvp_pagers.setOffscreenPageLimit(imgIds.length);

//        fvp_pagers.getTransformer()
        FlowTransformer flowTransformer = new FlowTransformer(fvp_pagers);
        flowTransformer.setDoClip(false);
        flowTransformer.setDoRotationY(false);
        flowTransformer.setSpace(0);
        flowTransformer.setLocationTransformer(new LocationTransformer() {
            @Override
            public float getTransLation(float position) {
                if (position > 0) {
                    return -position * 0.7f;
                }
                return position;
            }
        });
//        flowTransformer.setScaleTransformer(new ScaleTransformer() {
//            @Override
//            public float getScale(float position) {
//                return 1f;
//            }
//
//            public float getTransLation(float position) {
//                return 0.9f;
//            }
//        });
        flowTransformer.setPageRound(0.1f);
        flowTransformer.setRotationTransformer(new RotationTransformer() {
            @Override
            public float getRotation(float position) {
                float rotationY = -position * 20;
                rotationY = rotationY >= 30 ? 30 : rotationY;
                rotationY = rotationY <= -30 ? -30 : rotationY;
                return rotationY;
            }
        });
        flowTransformer.setAlphaTransformer(new AlphaTransformer() {
            @Override
            public float getAlpha(float position) {
                return 1;
            }
        });
        fvp_pagers.setPageTransformer(true, flowTransformer);

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
//            return true;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ClipView clipView;
            if (imageViewList.containsKey(position)) {
                clipView = imageViewList.get(position);
            } else {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setImageResource(imgIds[position]);
                imageView.setAdjustViewBounds(false);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                ReflectView reflectView = new ReflectView(container.getContext(), imageView, 0.5f);
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
                }
            });
            return clipView;
        }
    };
}
