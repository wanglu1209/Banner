package com.github.wanglu1209.banner.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.github.wanglu1209.banner.R;
import com.github.wanglu1209.bannerlibrary.BannerPagerAdapter;

import java.util.List;

/**
 * Created by WangLu on 16/9/1.
 */
public class AdapterDemo extends BannerPagerAdapter {

    private Context mContext;
    private List<Integer> data;

    public AdapterDemo(Context context) {
        mContext = context;
    }

    @Override
    public void setData(List data) {
        super.setData(data);
        this.data = data;
    }

    /**
     * 只需要重写构造和这个方法即可
     * 在这里可以设置自己的View,使用自己的图片加载库
     */
    @Override
    public View setView(int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.test, null);
        ImageView iv = (ImageView) v.findViewById(R.id.iv);
        iv.setImageResource(data.get(position));
        return v;
    }
}
