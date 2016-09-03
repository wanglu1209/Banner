package com.github.wanglu1209.banner.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.wanglu1209.banner.Banner;
import com.github.wanglu1209.banner.BannerPagerAdapter;
import com.github.wanglu1209.banner.R;

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
        AdapterDemo ad = new AdapterDemo(this, data);

        Banner bv = (Banner) findViewById(R.id.bv);
        bv.
            setDot(R.drawable.no_selected_dot, R.drawable.selected_dot).
            setDotGravity(Banner.CENTER).
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
