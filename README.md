# ViewPager3D

**下面是效果图：**

![image1.png](https://github.com/bambootang/ViewPager3D/blob/master/imgs/img1.gif)

![image2.png](https://github.com/bambootang/ViewPager3D/blob/master/imgs/img2.gif)

![image3.png](https://github.com/bambootang/ViewPager3D/blob/master/imgs/img3.gif)

**使用方式**

    compile 'com.bambootang:viewpager3d:1.3'

代码内可以直接使用FlowViewPager

或者使用ViewPager.setPageTransformer(true,new FlowTransformer());

**如何控制中间页的大小**

通过代码

    viewPager.setPaddingLeft(paddingLeft);
    viewPager.setPaddingRight(paddingRight);
    
或者xml中

    android:paddingLeft="220dp"
    android:paddingRight="220dp"
    
原理是使用了View的clipChildren和clipToPadding的特性，为了使用方便，也为了节约代码量



详细的使用方式请查阅sample

**FlowTransformer支持3种transform自定义**

LocationTransformer   定义每页的transitionX变化

RotationTransformer   定义每页的RotationY变化

ScaleTransformer      定义每页的Scale变化

**支持**

1、翻页切换点（pageRoundFactor）自定义，可以随意定义滑动到0～1范围内的位置切换显示在最前端page

2、每一页的间隔（space）自定义，需要同时与doClip一起使用，控制每一页之间的间隔


灵活使用3种transform 以及 翻页切换点和space可以做出非常丰富的动画效果。

ScaleTransformer      定义每页的Scale变化所有

ScaleTransformer      定义每页的Scale变化


***为了大家扩展及在原基础上方便修改或重写某些流程，FlowTransforme类的几乎所有方法都设置成了protected作用域***

# 最后，欢迎大家star。
