# Banner

![](/Users/WangLu/Desktop/gif1.gif)

## 如何使用？

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}	
	
	--------------------
	
	dependencies {
	        compile 'com.github.wanglu1209:Banner:1.1'
	}
继承BannerPagerAdapter，重写构造和setView方法即可

在构造里传入context和数据，在setView方法中实现自己想要实现的效果

然后在使用的地方：

   	AdapterDemo ad = new AdapterDemo(this, data);
   	
    Banner banner = (Banner) findViewById(R.id.banner);

    /**
     * 关于这里的设置参数问题,是需要这样使用的
     * 在设置了小圆点之后才能设置适配器
     * 因为只有在适配器里才会根据一共多少条数据来适配
     * 最后需要调用开始轮播
     * 个人建议在onPause()/onDestroy()方法中来停止 -- stopAutoPlay()
     */
    banner. setDot(R.drawable.no_selected_dot, R.drawable.selected_dot).
        setDotGravity(Banner.CENTER).
        setAdapter(ad).
        setOnItemClickListener(new BannerPagerAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        }).
        startAutoPlay();





## 技术要点

- 无限轮播
- 触摸停止轮播，松开计时轮播
- 指示器的滑动效果



---


**其实写的时候各种坑，到现在写完了又不知道说啥了，就这样吧~**
