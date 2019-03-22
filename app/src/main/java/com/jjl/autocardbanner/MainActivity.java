package com.jjl.autocardbanner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jjl.autocardbanner.utils.ScreenUtil;
import com.jjl.autocardbanner.widget.loopindicator.AutoLoopViewPager;
import com.jjl.autocardbanner.widget.loopindicator.CirclePageIndicator;
import com.jjl.autocardbanner.widget.loopindicator.ScaleInTransformer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mSlideLy;
    private AutoLoopViewPager mAutoLoop;
    private CirclePageIndicator mIndy;
    private ImageAdapter mImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlideLy = findViewById(R.id.slideLy);
        mAutoLoop = findViewById(R.id.autoLoop);
        mIndy = findViewById(R.id.indy);

        initBanner();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAutoLoop != null) {
            mAutoLoop.startAutoScroll();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAutoLoop != null) {
            mAutoLoop.stopAutoScroll();
        }
    }

    private void initBanner() {
        List<Integer> bannerData = new ArrayList<>();
        bannerData.add(R.drawable.banner1);
        bannerData.add(R.drawable.banner2);
        bannerData.add(R.drawable.banner3);

        mImageAdapter = new ImageAdapter(this ,bannerData);
        mAutoLoop.setAdapter(mImageAdapter);
        mAutoLoop.setPageMargin(ScreenUtil.dpToPx(this ,8));
        mAutoLoop.setBoundaryCaching(true);
        mAutoLoop.setAutoScrollDurationFactor(4d);
        mAutoLoop.setInterval(2000);
        mAutoLoop.setOffscreenPageLimit(3);
        mAutoLoop.setPageTransformer(true, new ScaleInTransformer());
        mAutoLoop.startAutoScroll();
        mIndy.setViewPager(mAutoLoop);

        int width = ScreenUtil.getScreenWidth(this);
        int height = (int) (width / 2.5);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        mSlideLy.setLayoutParams(layoutParams);
    }
}
