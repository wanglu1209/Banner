package com.github.wanglu1209.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by WangLu on 16/9/2.
 */
public class Banner extends FrameLayout {

    public static final int CENTER = 1;
    public static final int RIGHT = 5;

    private Context mContext;
    private int[] mDot = new int[2];
    private LinearLayout mDotGroup;
    private BannerViewPager mView;
    private BannerPagerAdapter mAdapter;
    private FrameLayout mFrameLayout;
    private View mSelectedDot;


    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mView = new BannerViewPager(mContext);
        addView(mView);
        mFrameLayout = new FrameLayout(mContext);
        mDotGroup = new LinearLayout(mContext);
        mDotGroup.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        mFrameLayout.addView(mDotGroup, params);
        addView(mFrameLayout, params);
        post(new Runnable() {
            @Override
            public void run() {
                ImageView iv = new ImageView(mContext);
                iv.setImageDrawable(mContext.getDrawable(mDot[1]));
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = (int) mDotGroup.getChildAt(0).getX();
                params.gravity = Gravity.BOTTOM;
                mFrameLayout.addView(iv, params);
                mSelectedDot = mFrameLayout.getChildAt(1);
            }
        });
        mView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                position %= mAdapter.size;
                if (mSelectedDot != null && position != mAdapter.size -1) {
                    float dx = mDotGroup.getChildAt(1).getX() - mDotGroup.getChildAt(0).getX();
                    mSelectedDot.setTranslationX((position * dx) + positionOffset * dx);
                }


            }

            @Override
            public void onPageSelected(int position) {
                position %= mAdapter.size;
                if (mSelectedDot != null && position == mAdapter.size - 1) {
                    float dx = mDotGroup.getChildAt(1).getX() - mDotGroup.getChildAt(0).getX();
                    mSelectedDot.setTranslationX(position * dx);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public Banner setAdapter(BannerPagerAdapter adapter) {
        mAdapter = adapter;
        mView.setAdapter(mAdapter);
        LinearLayout.LayoutParams dotParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        dotParams.rightMargin = 10;
        for (int i = 0; i < mAdapter.size; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setImageDrawable(mContext.getResources().getDrawable(mDot[0]));
            iv.setLayoutParams(dotParams);
            mDotGroup.addView(iv);
        }

        return this;
    }

    public Banner setOnItemClickListener(BannerPagerAdapter.onItemClickListener l) {
        mAdapter.setOnItemClickListener(l);
        return this;
    }

    public Banner startAutoPlay() {
        mView.startAutoPlay();
        return this;
    }

    public void stopAutoPlay() {
        mView.stopAutoPlay();
    }

    public Banner setTime(int time) {
        mView.setTime(time);
        return this;
    }

    public Banner setDot(int... dots) {
        mDot[0] = dots[0];
        mDot[1] = dots[1];
        return this;
    }

    public Banner setDotGravity(int gravity) {
        mDotGroup.setGravity(gravity | Gravity.BOTTOM);
        if(gravity == CENTER){
            mFrameLayout.setPadding(0, 0, 0, 10);
        }else{
            mFrameLayout.setPadding(0, 0, 10, 10);
        }
        return this;
    }
}
