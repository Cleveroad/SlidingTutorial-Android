##SlidingTutorial [![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome) <img src="https://www.cleveroad.com/public/comercial/label-android.svg" height="20"> <a href="https://www.cleveroad.com/?utm_source=github&utm_medium=label&utm_campaign=contacts"><img src="https://www.cleveroad.com/public/comercial/label-cleveroad.svg" height="20"></a>

###Simple library that helps developers to create awesome sliding android app tutorial.

Read our <a href="https://www.cleveroad.com/blog/case-study-sliding-tutorial-for-android-by-cleveroad">Case Study: Sliding tutorial for Android by Cleveroad</a>

Applied parallax effects will make your product presentation look like Google apps tutorial.

All you need to do is:
<br>1. Create background designs for each screen of your tutorial (assistance with <a href="https://www.cleveroad.com/services/design/mobile-design">mobile design</a>)
<br>2. Create icons for each screen of your tutorial
<br>3. Follow the instructions below

##Using

First, add gradle dependency with command:<br>
```groovy
	dependencies {
	    compile 'com.cleveroad:slidingtutorial:0.9'
	}
``` 

After you have to create each fragment that must extend from PageFragment. Also you have to create your xml file with images.

```java
public class FirstCustomPageFragment extends PageFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_page_first;
    }

    @Override
    protected int getBackgroundColorResId() {
        return android.R.color.holo_orange_dark;
    }

    @Override
    public int getRootResId() {
        return R.id.rootFirstPage;
    }

    @Override
    protected TransformItem[] provideTransformItems() {
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
    protected List<? extends PageFragment> getPageFragments() {
        List<PageFragment> pageFragments = new ArrayList<>();
        pageFragments.add(new FirstCustomPageFragment());
        pageFragments.add(new SecondCustomPageFragment());
        pageFragments.add(new ThirdCustomPageFragment());
        return pageFragments;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_presentation;
    }

    @Override
    public int getViewPagerResId() {
        return R.id.viewPager;
    }

    @Override
    public int getIndicatorResId() {
        return R.id.indicator;
    }

    @Override
    public int getButtonSkipResId() {
        return R.id.tvSkip;
    }
}
```

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

        Copyright (c) 2015 Cleveroad

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
