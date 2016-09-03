package com.github.wanglu1209.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by WangLu on 16/9/1.
 */
public abstract class BannerPagerAdapter<T> extends PagerAdapter {

    private static final int BANNER_SIZE = 100;
    public int size = -1;
    private int position;

    public onItemClickListener l;

    public BannerPagerAdapter(Context context, List<T> data) {
        size = data.size();
    }

    @Override
    public int getCount() {
        return BANNER_SIZE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= size;
        this.position = position;
        View view = setView(position);
        container.addView(view);
        final int p = position;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(l != null){
                    l.onClick(p);
                }
            }
        });
        return view;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        ViewPager pager = (ViewPager) container;
        int p = pager.getCurrentItem();
        if (p == 0) {
            p = size;
            pager.setCurrentItem(p, false);
        } else if (p == BANNER_SIZE - 1) {
            p = size - 1;
            pager.setCurrentItem(p, false);
        }
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface onItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener l){
        this. l = l;
    }

    public abstract View setView(int position);

    public int getPosition(){
        return position;
    }
}
