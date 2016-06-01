##SlidingTutorial [![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome) <img src="https://www.cleveroad.com/public/comercial/label-android.svg" height="20"> <a href="https://www.cleveroad.com/?utm_source=github&utm_medium=label&utm_campaign=contacts"><img src="https://www.cleveroad.com/public/comercial/label-cleveroad.svg" height="20"></a>
![Header image](/images/header.jpg)

## Cleveroad introduces Sliding Tutorial Library for Android

Hey guys, hope you haven’t started developing a tutorial for your Android app yet, as we have already completed a part of your job. Don’t worry, we act from good motives only. Our aim is to help you create a sliding tutorial in a fast and simple manner. So we’ve done some work and voila!. A simple Android Sliding Tutorial library is at your service.

![Demo image](/images/demo.gif)
###### Also you can watch the animation of the <strong><a target="_blank" href="https://www.youtube.com/watch?v=lJSGIk4Zh9s&feature=youtu.be">Sliding Tutorial for Android on YouTube</a></strong> in HD quality.

The invention is going to ease the problem of structural design but not to limit a stretch of your imagination at the same time. We took care of the suitability aspect. So, your app is not going to look alien among other Android elements. 

Read our <strong><a href="https://www.cleveroad.com/blog/case-study-sliding-tutorial-for-android-by-cleveroad">Case Study: Sliding tutorial for Android by Cleveroad</a></strong> to make sure that you don’t miss a detail:

[![Article image](/images/article.png)](https://www.cleveroad.com/blog/case-study-sliding-tutorial-for-android-by-cleveroad)
<br/>

Applied parallax effects will make your product presentation look like Google apps tutorial.

All you need to do is:
<br>1. Create background designs for each screen of your tutorial (assistance with <a href="https://www.cleveroad.com/services/design/mobile-design">mobile design</a>)
<br>2. Create icons for each screen of your tutorial
<br>3. Follow the instructions below

[![Awesome](/images/logo-footer.png)](https://www.cleveroad.com/?utm_source=github&utm_medium=label&utm_campaign=contacts)



##Using

First, add gradle dependency with command:<br>
```groovy
dependencies {
    compile 'com.cleveroad:slidingtutorial:0.9.5'
}
``` 

After you have to create each fragment that must extend from PageFragment. Also you have to create your xml file with images.

```java
public class FirstCustomPageFragment extends PageFragment {

    @Override
    protected int getLayoutResId() {
        // layout id of fragment
        return R.layout.fragment_page_first;
    }

    @Override
    protected TransformItem[] provideTransformItems() {
        // list of transformation items
        return new TransformItem[]{
                new TransformItem(R.id.ivFirstImage, true, 20),
                new TransformItem(R.id.ivSecondImage, false, 6),
                new TransformItem(R.id.ivThirdImage, true, 8),
                new TransformItem(R.id.ivFourthImage, false, 10),
                new TransformItem(R.id.ivFifthImage, false, 3),
                new TransformItem(R.id.ivSixthImage, false, 9),
                new TransformItem(R.id.ivSeventhImage, false, 14),
                new TransformItem(R.id.ivEighthImage, false, 7)
        };
    }
}
```

Then you should provide these fragments in main slidingtutroial fragment

```java
public class CustomPresentationPagerFragment extends PresentationPagerFragment {

    @Override
    public int getLayoutResId() {
        // layout id of fragment
        return R.layout.fragment_presentation;
    }

    @Override
    public int getViewPagerResId() {
        // id of view pager
        return R.id.viewPager;
    }

    @Override
    public int getIndicatorResId() {
        // id of circular indicator
        return R.id.indicator;
    }

    @Override
    public int getButtonSkipResId() {
        // id of skip button
        return R.id.tvSkip;
    }

    @Override
    protected int getPagesCount() {
        // total number of pages
        return 3;
    }

    @Override
    protected PageFragment getPage(int position) {
        // get page for position
        if (position == 0)
            return new FirstCustomPageFragment();
        if (position == 1)
            return new SecondCustomPageFragment();
        if (position == 2)
            return new ThirdCustomPageFragment();
        throw new IllegalArgumentException("Unknown position: " + position);
    }

    @ColorInt
    @Override
    protected int getPageColor(int position) {
        // get color of page
        if (position == 0)
            return ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark);
        if (position == 1)
            return ContextCompat.getColor(getContext(), android.R.color.holo_green_dark);
        if (position == 2)
            return ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark);
        return Color.TRANSPARENT;
    }

    @Override
    protected boolean isInfiniteScrollEnabled() {
        // enable/disable infinite scroll behavior
        return true;
    }
    
    @Override
    protected boolean onSkipButtonClicked() {
        // your own behavior goes here
        // ...
        // return true to consume click event, false otherwise
        return true;
    }
}
```

## Changelog

| Version | Changes                         |
| --- | --- |
| v.0.9.5 | Added getters for views. Possible fix for manifest merging issues |
| v.0.9.4 | Renamed all attributes; all resources marked as private |
| v.0.9.3 | Fixed issue with wrong page showed at startup if pages count not equals 3 |
| v.0.9.2 | Added onSkipButtonClicked method and SimplePagerFragment |
| v.0.9.1 | Added infinite scroll behavior  |
| v.0.9   | First public release            |

## Migrations from v.0.9.3 to v.0.9.4
* All resources marked as private. Make sure you're not using any resource (strings, dimens, etc) from this library.
* All attributes were renamed, prefix `st_` added. Add this prefix to any custom XML attribute you used. Example:
```XML
    <com.cleveroad.slidingtutorial.CirclePageIndicator
        android:id="@+id/indicator"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        app:st_fillColor="@android:color/white"
        app:st_pageColor="@android:color/secondary_text_light_nodisable"
        app:st_radius="4dp"
        app:st_strokeColor="#00000000"
        app:st_strokeWidth="0dp"/>
```

## Migrations from v.0.9 to v.0.9.1
####CirclePageIndicator
This class is final now. Make sure you're not extending from it.

####LayersHolder
This class is package-local now. Make sure you're not using it.

####PageFragment
**getRootResId()** and **getBackgroundColorResId()** methods are deprecated. You can remove them now. To specify page's color use **PresentationPagerFragment.getPageColor(int)** method.

####PresentationPagerFragment
**getPageFragments()** method is deprecated. You can remove it now. Use **getPagesCount()** and **getPage(int)** methods instead. 

Added **isInfinityScrollEnabled()** method. Override it and return `true` to enable this feature.

**NOTE:** make sure you're returning new fragment instance when displaying tutorial with infinite scroll enabled.

## Support
If you have any questions regarding the use of this tutorial, please contact us for support
at info@cleveroad.com (email subject: «Sliding android app tutorial. Support request.»)
<br>or
<br>Use our contacts:
<br><a href="https://www.cleveroad.com/?utm_source=github&utm_medium=link&utm_campaign=contacts">Cleveroad.com</a>
<br><a href="https://www.facebook.com/cleveroadinc">Facebook account</a>
<br><a href="https://twitter.com/CleveroadInc">Twitter account</a>
<br><a href="https://plus.google.com/+CleveroadInc/">Google+ account</a>

## License


        The MIT License (MIT)

        Copyright (c) 2015-2016 Cleveroad

        Permission is hereby granted, free of charge, to any person obtaining a copy
        of this software and associated documentation files (the "Software"), to deal
        in the Software without restriction, including without limitation the rights
        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
        copies of the Software, and to permit persons to whom the Software is
        furnished to do so, subject to the following conditions:

        The above copyright notice and this permission notice shall be included in all
        copies or substantial portions of the Software.

        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
        SOFTWARE.
