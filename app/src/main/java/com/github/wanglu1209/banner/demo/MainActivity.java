package com.github.wanglu1209.banner.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.wanglu1209.banner.R;
import com.github.wanglu1209.bannerlibrary.Banner;
import com.github.wanglu1209.bannerlibrary.BannerPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Integer> data = new ArrayList<>();
        data.add(R.mipmap.a1);
        data.add(R.mipmap.a2);
        data.add(R.mipmap.a3);
        data.add(R.mipmap.a4);


        AdapterDemo ad = new AdapterDemo(this);

        Banner banner = (Banner) findViewById(R.id.banner);

        /**
         * 关于这里的设置参数问题,是需要这样使用的
         * 在设置了小圆点之后才能设置适配器
         * 因为只有在适配器里才会根据一共多少条数据来适配
         * 最后需要调用开始轮播
         * 个人建议在onPause()/onDestroy()方法中来停止 -- stopAutoPlay()
         */
        ad.setData(data);
        banner.setDotGravity(Banner.CENTER).
                setDot(R.drawable.no_selected_dot, R.drawable.selected_dot).
                setAdapter(ad).
                setOnItemClickListener(new BannerPagerAdapter.onItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                    }
                }).
                startAutoPlay();
      
    }
}
