# Banner

![banner](http://i1.piimg.com/567571/282c68fa5261a4aa.gif)

## 如何使用？

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}	
	
	--------------------
	
	dependencies {
	        compile 'com.github.wanglu1209:Banner:1.19'
	}
继承BannerPagerAdapter，重写构造和setView方法即可

在构造里传入context和数据，在setView方法中实现自己想要实现的效果

然后在使用的地方调用方法即可，具体的可以查看『demo』文件夹下的两个文件~

**源码的话在bannerlibrary里，自行查找一下就好~**

**注释已写**

**如果想要查看细节的话，可以移步[简书 -- 自定义banner实现](http://www.jianshu.com/p/0046c079f528)**

   	

## 技术要点

- 无限轮播
- 触摸停止轮播，松开计时轮播
- 指示器的滑动效果



---


**其实写的时候各种坑，到现在写完了又不知道说啥了，就这样吧~**
