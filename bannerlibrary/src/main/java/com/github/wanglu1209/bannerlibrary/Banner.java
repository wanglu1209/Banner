package com.github.wanglu1209.bannerlibrary;

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

    /**
     * 确定小圆点的位置
     * 中间or右边
     */
    public static final int CENTER = 1;
    public static final int RIGHT = 5;

    private Context mContext;
    /**
     * 小圆点的drawable
     * 下标0的为没有被选中的
     * 下标1的为已经被选中的
     */
    private int[] mDot = new int[2];
    /**
     * 存放小圆点的Group
     */
    private LinearLayout mDotGroup;
    /**
     * 存放没有被选中的小圆点Group和已经被选中小圆点
     */
    private FrameLayout mFrameLayout;
    /**
     * 选中的小圆点
     */
    private View mSelectedDot;
    private BannerViewPager mPager;
    private BannerPagerAdapter mAdapter;


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
        mPager = new BannerViewPager(mContext);
        addView(mPager);
        /**
         * 实例化两个Group
         */
        mFrameLayout = new FrameLayout(mContext);
        mDotGroup = new LinearLayout(mContext);
        /**
         * 设置小圆点Group的方向为水平
         */
        mDotGroup.setOrientation(LinearLayout.HORIZONTAL);
        /**
         * 如果不设置则小圆点在中间
         */
        mDotGroup.setGravity(CENTER | Gravity.BOTTOM);
        /**
         * 两个Group的大小都为match_parent
         */
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        /**
         * 首先添加小圆点的Group
         */
        mFrameLayout.addView(mDotGroup, params);
        /**
         * 然后添加包含的Group（f**k,表达能力有限）
         */
        addView(mFrameLayout, params);


        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /**
                 * 获取到当前position
                 */
                position %= mAdapter.size;
                /**
                 * 判断如果当前的position不是最后一个,那么就设置偏移量来实现被选中小圆点的滑动效果
                 */
                if (mSelectedDot != null && position != mAdapter.size - 1 && mAdapter.size != 1) {
                    float dx = mDotGroup.getChildAt(1).getX() - mDotGroup.getChildAt(0).getX();
                    mSelectedDot.setTranslationX((position * dx) + positionOffset * dx);
                }
            }

            @Override
            public void onPageSelected(int position) {
                position %= mAdapter.size;
                /**
                 * 如果已经是最后一个,那么则直接把小圆点定位到那,不然滑动效果会出错
                 */
                if (mSelectedDot != null && position == mAdapter.size - 1 && mAdapter.size != 1) {
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
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(mAdapter.size);

        if (mDotGroup.getChildCount() != 0)
            mDotGroup.removeAllViews();
        LinearLayout.LayoutParams dotParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        /**
         * 未选中小圆点的间距
         */
        dotParams.rightMargin = 12;

        /**
         * 创建未选中的小圆点
         */
        for (int i = 0; i < mAdapter.size; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setImageDrawable(mContext.getResources().getDrawable(mDot[0]));
            iv.setLayoutParams(dotParams);
            mDotGroup.addView(iv);
        }


        /**
         * 添加到任务栈,当前所有任务完成之后添加已经选中的小圆点
         */
        post(new Runnable() {
            @Override
            public void run() {
                if (mSelectedDot == null) {
                    ImageView iv = new ImageView(mContext);
                    iv.setImageDrawable(mContext.getResources().getDrawable(mDot[1]));
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    /**
                     * 设置选中小圆点的左边距
                     */
                    params.leftMargin = (int) mDotGroup.getChildAt(0).getX();
                    params.gravity = Gravity.BOTTOM;
                    mFrameLayout.addView(iv, params);
                    mSelectedDot = mFrameLayout.getChildAt(1);
                }
            }
        });
        return this;
    }

    public Banner setOnItemClickListener(BannerPagerAdapter.onItemClickListener l) {
        mAdapter.setOnItemClickListener(l);
        return this;
    }

    public Banner startAutoPlay() {
        if (!mPager.isAutoPlay())
            mPager.startAutoPlay();
        return this;
    }

    public void stopAutoPlay() {
        mPager.stopAutoPlay();
    }

    public Banner setTime(int time) {
        mPager.setTime(time);
        return this;
    }

    public Banner setDot(int... dots) {
        mDot[0] = dots[0];
        mDot[1] = dots[1];

        return this;
    }

    public void setCurrentPager(int page) {
        mPager.setCurrentItem(page);
    }

    public void setCurrentPager(int page, boolean isSmooth) {
        mPager.setCurrentItem(page, isSmooth);
    }

    public Banner setScrollDuration(int duration) {
        mPager.setScrollDuration(duration);
        return this;
    }

    public Banner setDotGravity(int gravity) {
        mDotGroup.setGravity(gravity | Gravity.BOTTOM);
        float density = mContext.getResources().getDisplayMetrics().density;
        if (gravity == CENTER) {
            mFrameLayout.setPadding(0, 0, 0, (int) (density * 10));
        } else {
            mFrameLayout.setPadding(0, 0, 10, (int) (density * 10));
        }
        return this;
    }
}
