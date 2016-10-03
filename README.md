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



## Using

First, add gradle dependency into your build.gradle:
```groovy
dependencies {
    compile 'com.cleveroad:slidingtutorial:1.0.5'
}
``` 

There are two common variants of using library: via [TutorialPageProvider] and via [TutorialPageOptionsProvider].
### Via TutorialPageProvider
You have to create page fragments where each fragment must extend from [PageFragment], override [PageFragment#getLayoutResId()] and [PageFragment#getTransformItems()]. Also you have to create your layout xml file with images.

```java
public class FirstCustomPageFragment extends PageFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_page_first;
    }

    @Override
    protected TransformItem[] provideTransformItems() {
        return new TransformItem[] {
                // TransformItem.create(view for transform, moving direction, shift coefficient)
                TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f),
                TransformItem.create(R.id.ivSecondImage, Direction.RIGHT_TO_LEFT, 0.06f),
                TransformItem.create(R.id.ivThirdImage, Direction.LEFT_TO_RIGHT, 0.08f),
                TransformItem.create(R.id.ivFourthImage, Direction.RIGHT_TO_LEFT, 0.1f),
                TransformItem.create(R.id.ivFifthImage, Direction.RIGHT_TO_LEFT, 0.03f),
                TransformItem.create(R.id.ivSixthImage, Direction.RIGHT_TO_LEFT, 0.09f),
                TransformItem.create(R.id.ivSeventhImage, Direction.RIGHT_TO_LEFT, 0.14f),
                TransformItem.create(R.id.ivEighthImage, Direction.RIGHT_TO_LEFT, 0.07f)
        };
    }
}
```

Pass [TutorialPageProvider] instance to [TutorialOptions.Builder#setTutorialPageProvider(TutorialPageProvider)].

```java
public class CustomTutorialFragment extends TutorialFragment {

    private static final int TOTAL_PAGES = 3;

    private final TutorialPageProvider<Fragment> mTutorialPageProvider = new TutorialPageProvider<Fragment>() {
        @NonNull
        @Override
        public Fragment providePage(int position) {
            switch (position) {
                case 0:
                    return new FirstCustomPageFragment();
                case 1:
                    return new SecondCustomPageFragment();
                case 2:
                    return new ThirdCustomPageFragment();
                default:
                    throw new IllegalArgumentException("Unknown position: " + position);
            }
        }
    };

    @Override
    protected TutorialOptions provideTutorialOptions() {
        return newTutorialOptionsBuilder(getContext())
                .setPagesCount(TOTAL_PAGES)
                .setTutorialPageProvider(mTutorialPageProvider)
                .build();
    }
}
```

### Via TutorialPageOptionsProvider
Or you can create [TutorialPageOptionsProvider] and pass it to [TutorialOptions.Builder#setTutorialPageProvider(TutorialPageOptionsProvider)]. It will automatically provide [PageFragment] instance with specified [PageOptions] configuration.
```java
public class CustomTutorialFragment extends TutorialFragment {

    private static final int TOTAL_PAGES = 3;

    private final TutorialPageOptionsProvider mTutorialPageOptionsProvider = new TutorialPageOptionsProvider() {
        @NonNull
        @Override
        public PageOptions provide(int position) {
            @LayoutRes int pageLayoutResId;
            TransformItem[] tutorialItems;
            switch (position) {
                case 0: {
                    pageLayoutResId = R.layout.fragment_page_first;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f),
                            TransformItem.create(R.id.ivSecondImage, Direction.RIGHT_TO_LEFT, 0.06f),
                            TransformItem.create(R.id.ivThirdImage, Direction.LEFT_TO_RIGHT, 0.08f),
                            TransformItem.create(R.id.ivFourthImage, Direction.RIGHT_TO_LEFT, 0.1f),
                            TransformItem.create(R.id.ivFifthImage, Direction.RIGHT_TO_LEFT, 0.03f),
                            TransformItem.create(R.id.ivSixthImage, Direction.RIGHT_TO_LEFT, 0.09f),
                            TransformItem.create(R.id.ivSeventhImage, Direction.RIGHT_TO_LEFT, 0.14f),
                            TransformItem.create(R.id.ivEighthImage, Direction.RIGHT_TO_LEFT, 0.07f)
                    };
                    break;
                }
                case 1: {
                    pageLayoutResId = R.layout.fragment_page_second;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.RIGHT_TO_LEFT, 0.2f),
                            TransformItem.create(R.id.ivSecondImage, Direction.LEFT_TO_RIGHT, 0.06f),
                            TransformItem.create(R.id.ivThirdImage, Direction.RIGHT_TO_LEFT, 0.08f),
                            TransformItem.create(R.id.ivFourthImage, Direction.LEFT_TO_RIGHT, 0.1f),
                            TransformItem.create(R.id.ivFifthImage, Direction.LEFT_TO_RIGHT, 0.03f),
                            TransformItem.create(R.id.ivSixthImage, Direction.LEFT_TO_RIGHT, 0.09f),
                            TransformItem.create(R.id.ivSeventhImage, Direction.LEFT_TO_RIGHT, 0.14f),
                            TransformItem.create(R.id.ivEighthImage, Direction.LEFT_TO_RIGHT, 0.07f)
                    };
                    break;
                }
                case 2: {
                    pageLayoutResId = R.layout.fragment_page_third;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.RIGHT_TO_LEFT, 0.2f),
                            TransformItem.create(R.id.ivSecondImage, Direction.LEFT_TO_RIGHT, 0.06f),
                            TransformItem.create(R.id.ivThirdImage, Direction.RIGHT_TO_LEFT, 0.08f),
                            TransformItem.create(R.id.ivFourthImage, Direction.LEFT_TO_RIGHT, 0.1f),
                            TransformItem.create(R.id.ivFifthImage, Direction.LEFT_TO_RIGHT, 0.03f),
                            TransformItem.create(R.id.ivSixthImage, Direction.LEFT_TO_RIGHT, 0.09f),
                            TransformItem.create(R.id.ivSeventhImage, Direction.LEFT_TO_RIGHT, 0.14f)
                    };
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown position: " + position);
                }
            }

            return PageOptions.create(pageLayoutResId, position, tutorialItems);
        }
    };

    @Override
    protected TutorialOptions provideTutorialOptions() {
        return newTutorialOptionsBuilder(getContext())
                .setPagesCount(TOTAL_PAGES)
                .setTutorialPageProvider(mTutorialPageOptionsProvider)
                .build();
    }
}
```
### Using with AppCompat library (Recommended way)
Here's the list of changes in code to use `SlidingTutorial` library with `AppCompat` library:
* Your fragment pages must extend [PageSupportFragment].
* Your tutorial fragment must extend [TutorialSupportFragment].
* [TutorialPageProvider] must provide **android.support.v4.app.Fragment** instances.
That's all.


## Customization
### Setup skip button click listener
You have to implement *View.OnClickListener* interface and provide it to [TutorialOptions.Builder#setOnSkipClickListener(OnClickListener)]. Example:
```java
public class CustomTutorialFragment extends TutorialFragment {
    @Override
    protected TutorialOptions provideTutorialOptions() {
        return newTutorialOptionsBuilder(getContext())
                .setOnSkipClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Skip button clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                // setup other configuration ...
                .build();
    }
}
```

### Setup pages colors 
Just provide array of color values to [TutorialOptions.Builder#setPagesColors(int array)]. The array with colors **must have length equal to pages count**.
```java
public class CustomTutorialFragment extends TutorialFragment {

    private static final int TOTAL_PAGES = 3;

    private int[] pagesColors = new int[] { Color.RED, Color.BLUE, Color.DKGRAY };

    @Override
    protected TutorialOptions provideTutorialOptions() {
        return newTutorialOptionsBuilder(getContext())
                .setPagesCount(TOTAL_PAGES)
                .setPagesColors(pagesColors)
                // setup other configuration ...
                .build();
    }
}
```
### Infinite scroll
To loop tutorial pages you have set [TutorialOptions.Builder#setUseInfiniteScroll(boolean)] to `true`.

### Auto remove TutorialFragment - scroll from last tutorial page to your content
If you want to provide smooth transition from last tutorial page to content - just setup [TutorialOptions.Builder#setUseAutoRemoveTutorialFragment(boolean)] to `true`.

### Indicator view customization
There is [IndicatorOptions] class for configuration indicator view. Here's example:
```java
public class CustomTutorialFragment extends TutorialFragment {
    @Override
    protected TutorialOptions provideTutorialOptions() {
        return newTutorialOptionsBuilder(getContext())
                .setIndicatorOptions(IndicatorOptions.newBuilder(getContext())
                        .setElementSizeRes(R.dimen.indicator_size)
                        .setElementSpacingRes(R.dimen.indicator_spacing)
                        .setElementColorRes(android.R.color.darker_gray)
                        .setSelectedElementColor(android.R.color.white)
                        .setRenderer(Drawable.create(getContext()))
                        .build())
                // setup other configuration ...
                .build();
    }
}
```
As you can see, you can specify *element size*, *element spacing* (aka padding), *element color*, *selected element color*, and implementation of [Renderer] interface. There are 2 default implementation inside [Renderer.Factory]:
* [Renderer.Factory#newCircleRenderer()] - draw indicators with circle shape
* [Renderer.Factory#newSquareRenderer()] - draw indicators with square shape

Also in sample module there are two implementations:
* [DrawableRenderer] - draw indicators with drawable background
* [RhombusRenderer] - draw indicators with rhombus shape

### Add OnTutorialPageChangeListener
You can listen change page events - just implement [OnTutorialPageChangeListener] and add listener via [TutorialFragment#addOnTutorialPageChangeListener(OnTutorialPageChangeListener)]. To remove listener use [TutorialFragment#removeOnTutorialPageChangeListener(OnTutorialPageChangeListener)]. In [OnTutorialPageChangeListener#onPageChanged(int)] method you will receive a page index every time page changes. If you enabled [TutorialOptions.Builder#setUseAutoRemoveTutorialFragment(boolean)] to true, you will receive `TutorialFragment.EMPTY_FRAGMENT_POSITION` (or `TutorialSupportFragment.EMPTY_FRAGMENT_POSITION` if you are using support library) as page index.
```java
public class CustomTutorialFragment extends TutorialFragment
        implements OnTutorialPageChangeListener {

    private static final String TAG = "CustomTutorialFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addOnTutorialPageChangeListener(this);
    }

    @Override
    public void onPageChanged(int position) {
        Log.i(TAG, "onPageChanged: position = " + position);
        if (position == TutorialFragment.EMPTY_FRAGMENT_POSITION) {
            Log.i(TAG, "onPageChanged: Empty fragment is visible");
        }
    }

}
``` 

### Add PageTransformer
You can apply your own property transformation to the given page - just implement `ViewPager.PageTransformer` interface and set it via [TutorialOptions.Builder#setPageTransformer(ViewPager.PageTransformer pageTransformer)].
```java
ViewPager.PageTransformer pageTransformer = new ViewPager.PageTransformer() {
    @Override
    public void transformPage(View page, float position) {
        //Implement your transformation here
    }
};

TutorialOptions tutorialOptions = newTutorialOptionsBuilder(getContext())
    // ...
    .setPageTransformer(pageTransformer)
    .build();
```

## Migrations
See all [migration manuals].

## Changelog
See [changelog history].

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

[migration manuals]: /MIGRATION.md
[changelog history]: /CHANGELOG.md
[PageFragment]: /lib/src/main/java/com/cleveroad/slidingtutorial/PageFragment.java
[PageFragment#getLayoutResId()]: /lib/src/main/java/com/cleveroad/slidingtutorial/PageFragment.java#L84
[PageFragment#getTransformItems()]: /lib/src/main/java/com/cleveroad/slidingtutorial/PageFragment.java#L87
[PageSupportFragment]: /lib/src/main/java/com/cleveroad/slidingtutorial/PageSupportFragment.java
[Direction]: /lib/src/main/java/com/cleveroad/slidingtutorial/Direction.java
[IndicatorOptions]: /lib/src/main/java/com/cleveroad/slidingtutorial/IndicatorOptions.java
[IndicatorOptions.Builder]: /lib/src/main/java/com/cleveroad/slidingtutorial/IndicatorOptions.java#L116
[PageOptions]: /lib/src/main/java/com/cleveroad/slidingtutorial/PageOptions.java
[Renderer]: /lib/src/main/java/com/cleveroad/slidingtutorial/Renderer.java
[Renderer.Factory]: /lib/src/main/java/com/cleveroad/slidingtutorial/Renderer.java#L46
[Renderer.Factory#newCircleRenderer()]: /lib/src/main/java/com/cleveroad/slidingtutorial/Renderer.java#L54
[Renderer.Factory#newSquareRenderer()]: /lib/src/main/java/com/cleveroad/slidingtutorial/Renderer.java#L75
[DrawableRenderer]: /sample/src/main/java/com/cleveroad/slidingtutorial/sample/renderer/DrawableRenderer.java
[RhombusRenderer]: /sample/src/main/java/com/cleveroad/slidingtutorial/sample/renderer/RhombusRenderer.java
[TransformItem]: /lib/src/main/java/com/cleveroad/slidingtutorial/TransformItem.java
[TransformItem#create(int,Direction,float)]: /lib/src/main/java/com/cleveroad/slidingtutorial/TransformItem.java#L54
[TutorialFragment]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java
[TutorialFragment#provideTutorialOptions()]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L239
[OnTutorialPageChangeListener]: /lib/src/main/java/com/cleveroad/slidingtutorial/OnTutorialPageChangeListener.java
[OnTutorialPageChangeListener#onPageChanged(int)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/feature/refactor/lib/src/main/java/com/cleveroad/slidingtutorial/OnTutorialPageChangeListener.java#L37
[TutorialFragment#addOnTutorialPageChangeListener(OnTutorialPageChangeListener)]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L168
[TutorialFragment#removeOnTutorialPageChangeListener(OnTutorialPageChangeListener)]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L178
[TutorialSupportFragment]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialSupportFragment.java
[TutorialOptions]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java
[TutorialOptions.Builder]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L113
[TutorialOptions.Builder#setTutorialPageProvider(TutorialPageOptionsProvider)]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L239
[TutorialOptions.Builder#setTutorialPageProvider(TutorialPageProvider)]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L264
[TutorialOptions.Builder#setOnSkipClickListener(OnClickListener)]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L209
[TutorialOptions.Builder#setPagesColors(int array)]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L198
[TutorialOptions.Builder#setPagesCount(int)]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L187
[TutorialOptions.Builder#setUseInfiniteScroll(boolean)]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L176
[TutorialOptions.Builder#setUseAutoRemoveTutorialFragment(boolean)]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L164
[TutorialPageIndicator]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialPageIndicator.java
[TutorialPageOptionsProvider]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialPageOptionsProvider.java
[TutorialPageProvider]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialPageProvider.java
[TutorialOptions.Builder#setPageTransformer(ViewPager.PageTransformer pageTransformer)]: /lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L290
