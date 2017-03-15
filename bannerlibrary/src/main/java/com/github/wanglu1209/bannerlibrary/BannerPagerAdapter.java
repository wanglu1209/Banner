package com.github.wanglu1209.bannerlibrary;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by WangLu on 16/9/1.
 */
public abstract class BannerPagerAdapter<T> extends PagerAdapter {

    /**
     * 把banner页的个数设置为100页,这样就达到了无限的效果
     */
    private static final int BANNER_SIZE = 100;
    /**
     * size为当前banner实际的页数
     */
    public int size = -1;
    private int position;

    public onItemClickListener l;


    public void setData(List<T> data){
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
        position %= size;   //通过这句话来确定实际的position
        this.position = position;
        /**
         * 和正常设置pagerAdapter一样的步骤
         */
        final int p = position;
        View view = setView(p);
        container.addView(view);
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
        /**
         * 这里获取到当前是第几页
         * 如果是第一页的话就给设置为实际大小的后面一页,比如说一共有4页,那么就设置为第5页,因为第5页是和第1页一样的
         * 如果是最后一页则设置为实际大小的最后一页
         */
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
