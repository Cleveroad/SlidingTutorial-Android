## SlidingTutorial [![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome) <img src="https://www.cleveroad.com/public/comercial/label-android.svg" height="20"> <a href="https://www.cleveroad.com/?utm_source=github&utm_medium=label&utm_campaign=contacts"><img src="https://www.cleveroad.com/public/comercial/label-cleveroad.svg" height="20"></a>
![Header image](/images/header.jpg)

## Migrations from v.1.0.5 to v.1.0.6
`TutorialOptions.Builder#onSkipClickListener(...)` was renamed to `TutorialOptions.Builder#setOnSkipClickListener(...)`.

## Migrations from v.0.9.5 to v.1.0.0
1. You must change creation [TransformItem] from `new TransformItem(R.id.ivFirstImage, true, 20)` to `TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f)`, where 2-nd parameter now is [Direction] of view translation and 3-rd parameter is *shiftCoefficient*.
2. Your fragment with tutorial must extend [TutorialFragment] instead of **PresentationPagerFragment**.
3. In your [TutorialFragment] successor fragment must implement [TutorialFragment#provideTutorialOptions()] method that returns [TutorialOptions] instance.
```java
import android.support.v4.app.Fragment;

public class CustomTutorialFragment extends TutorialSupportFragment {

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

    private int[] pagesColors = new int[] { Color.WHITE, Color.BLUE, Color.RED };

    @Override
    protected TutorialOptions provideTutorialOptions() {
        return newTutorialOptionsBuilder(getContext())
                .setUseAutoRemoveTutorialFragment(true)
                .setUseInfiniteScroll(false)
                .setPagesColors(pagesColors)
                .setPagesCount(TOTAL_PAGES)
                .setIndicatorOptions(IndicatorOptions.newBuilder(getContext())
                        .setElementSizeRes(R.dimen.indicator_size)
                        .setElementSpacingRes(R.dimen.indicator_spacing)
                        .setElementColorRes(android.R.color.darker_gray)
                        .setSelectedElementColor(Color.LTGRAY)
                        .setRenderer(DrawableRenderer.create(getContext()))
                        .build())
                .setTutorialPageProvider(mTutorialPageProvider)
                .onSkipClickListener(mOnSkipClickListener)
                .build();
    }
}
```
4. In [TutorialOptions.Builder#setTutorialPageProvider(TutorialPageProvider)] you must specify [TutorialPageProvider] instance. For example:
```java
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
```

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
#### CirclePageIndicator
This class is final now. Make sure you're not extending from it.

#### LayersHolder
This class is package-local now. Make sure you're not using it.

#### PageFragment
**getRootResId()** and **getBackgroundColorResId()** methods are deprecated. You can remove them now. To specify page's color use **PresentationPagerFragment.getPageColor(int)** method.

#### PresentationPagerFragment
**getPageFragments()** method is deprecated. You can remove it now. Use **getPagesCount()** and **getPage(int)** methods instead. 

Added **isInfinityScrollEnabled()** method. Override it and return `true` to enable this feature.

**NOTE:** make sure you're returning new fragment instance when displaying tutorial with infinite scroll enabled.


[migration manuals]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/MIGRATION.md
[changelog history]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/CHANGELOG.md
[PageFragment]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/PageFragment.java
[PageFragment#getLayoutResId()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/PageFragment.java#L84
[PageFragment#getTransformItems()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/PageFragment.java#L87
[PageSupportFragment]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/PageSupportFragment.java
[Direction]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/Direction.java
[IndicatorOptions]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/IndicatorOptions.java
[IndicatorOptions.Builder]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/IndicatorOptions.java#L116
[PageOptions]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/PageOptions.java
[Renderer]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/Renderer.java
[Renderer.Factory]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/Renderer.java#L46
[Renderer.Factory#newCircleRenderer()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/Renderer.java#L54
[Renderer.Factory#newSquareRenderer()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/Renderer.java#L75
[DrawableRenderer]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/sample/src/main/java/com/cleveroad/slidingtutorial/sample/renderer/DrawableRenderer.java
[RhombusRenderer]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/sample/src/main/java/com/cleveroad/slidingtutorial/sample/renderer/RhombusRenderer.java
[TransformItem]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TransformItem.java
[TransformItem#create(int,Direction,float)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TransformItem.java#L54
[TutorialFragment]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java
[TutorialFragment#provideTutorialOptions()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L239
[OnTutorialPageChangeListener]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/OnTutorialPageChangeListener.java
[TutorialFragment#addOnTutorialPageChangeListener(OnTutorialPageChangeListener)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L168
[TutorialFragment#removeOnTutorialPageChangeListener(OnTutorialPageChangeListener)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L178
[TutorialSupportFragment]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialSupportFragment.java
[TutorialOptions]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java
[TutorialOptions.Builder]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L113
[TutorialOptions.Builder#setTutorialPageProvider(TutorialPageOptionsProvider)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L239
[TutorialOptions.Builder#setTutorialPageProvider(TutorialPageProvider)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L264
[TutorialOptions.Builder#setOnSkipClickListener(OnClickListener)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L209
[TutorialOptions.Builder#setPagesColors(int array)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L198
[TutorialOptions.Builder#setPagesCount(int)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L187
[TutorialOptions.Builder#setUseInfiniteScroll(boolean)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L176
[TutorialOptions.Builder#setUseAutoRemoveTutorialFragment(boolean)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L164
[TutorialPageIndicator]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialPageIndicator.java
[TutorialPageOptionsProvider]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialPageOptionsProvider.java
[TutorialPageProvider]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialPageProvider.java