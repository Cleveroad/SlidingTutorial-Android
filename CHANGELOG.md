
## SlidingTutorial [![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome) <img src="https://www.cleveroad.com/public/comercial/label-android.svg" height="20"> <a href="https://www.cleveroad.com/?utm_source=github&utm_medium=label&utm_campaign=contacts"><img src="https://www.cleveroad.com/public/comercial/label-cleveroad.svg" height="20"></a>
![Header image](/images/header.jpg)

## Changelog

Version | Changes
---     | ---
v.1.0.6 | <ul><li>Added new simple way of creation of tutorial</li><li>Fixed memory leaks issues</li><li>Updated default sizes of page indicator's elements</li><li>Updated default colors of page indicator</li><li>Updated versions of dependencies</li><li>Removed unused or unnecessary methods</li></ul>
v.1.0.5 | <ul><li>Fixed NullPointerException & IndexOutOfBoundsException inside ViewPager.</li><li>Added SlidingTutorialViewPager</li></ul>
v.1.0.4 | Added ability to set PageTransformer
v.1.0.3 | Fixed issues with layout customizations. Fixed issues with translate animations
v.1.0.2 | Minor fixes
v.1.0.1 | Changed gradle plugin version to stable `2.1.0`
v.1.0.0 | Library fully refactored. See full [1.0.0 Changelog](#100_Changelog)
v.0.9.5 | Added getters for views. Possible fix for manifest merging issues
v.0.9.4 | Renamed all attributes; all resources marked as private
v.0.9.3 | Fixed issue with wrong page showed at startup if pages count not equals 3 
v.0.9.2 | Added onSkipButtonClicked method and SimplePagerFragment 
v.0.9.1 | Added infinite scroll behavior
v.0.9   | First public release

## 1.0.2 Changelog
* Fixed issue with not using [TutorialFragment#getLayoutResId()], [TutorialFragment#getViewPagerResId()], [TutorialFragment#getIndicatorResId()],  [TutorialFragment#getButtonSkipResId()], [TutorialFragment#getSeparatorResId()].
* Fixed issue with not using [TutorialSupportFragment#getLayoutResId()], [TutorialSupportFragment#getViewPagerResId()], [TutorialSupportFragment#getIndicatorResId()], [TutorialSupportFragment#getButtonSkipResId()], [TutorialSupportFragment#getSeparatorResId()].

## 1.0.1 Changelog
Changed gradle plugin version to stable `2.1.0`.

## 1.0.0 Changelog
* Renamed **PresentationPagerFragment** to [TutorialFragment].
* Created [SimplePageFragment] fragment with default implementation for [PageFragment#getLayoutResId()] and [PageFragment#getTransformItems()].
* Removed capability to create new instance of [TransformItem] via `new`. Added fabric static method [TransformItem#create(int,Direction,float)].
* Created [OnTutorialPageChangeListener] to listen change page events.
* Use [TutorialFragment#addOnTutorialPageChangeListener(OnTutorialPageChangeListener)] and [TutorialFragment#removeOnTutorialPageChangeListener(OnTutorialPageChangeListener)] to add/remove listener.
* Created [TutorialOptions] to configure [TutorialFragment].
* Created [TutorialPageOptionsProvider] and [PageOptions] to provide and configure [PageFragment] instances.
* Created [TutorialPageProvider] to provide [PageFragment] instances.
* Removed **CirclePageIndicator**.
* Created [TutorialPageIndicator] view.
* Created [IndicatorOptions] to configure [TutorialPageIndicator] view.
* Created [Renderer] interface that responds for drawing single indicator item. There are 2 default implementation: [Renderer.Factory#newCircleRenderer()] and [Renderer.Factory#newSquareRenderer()].
* Created [TutorialSupportFragment] and [PageSupportFragment] for use with **AppCompat** library. [TutorialFragment] and [PageFragment] now using **android.app.Fragment**.

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
[TutorialFragment#getLayoutResId()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/bugfix/custom_layout/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L208
[TutorialFragment#getViewPagerResId()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/bugfix/custom_layout/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L213
[TutorialFragment#getIndicatorResId()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/bugfix/custom_layout/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L218
[TutorialFragment#getButtonSkipResId()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/bugfix/custom_layout/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L223
[TutorialFragment#getSeparatorResId()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/bugfix/custom_layout/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L228
[TutorialFragment#provideTutorialOptions()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L239
[OnTutorialPageChangeListener]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/OnTutorialPageChangeListener.java
[TutorialFragment#addOnTutorialPageChangeListener(OnTutorialPageChangeListener)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L168
[TutorialFragment#removeOnTutorialPageChangeListener(OnTutorialPageChangeListener)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L178
[TutorialSupportFragment]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialSupportFragment.java
[TutorialSupportFragment#getLayoutResId()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/bugfix/custom_layout/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialSupportFragment.java#L204
[TutorialSupportFragment#getViewPagerResId()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/bugfix/custom_layout/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialSupportFragment.java#L209
[TutorialSupportFragment#getIndicatorResId()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/bugfix/custom_layout/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialSupportFragment.java#L214
[TutorialSupportFragment#getButtonSkipResId()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/bugfix/custom_layout/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialSupportFragment.java#L219
[TutorialSupportFragment#getSeparatorResId()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/bugfix/custom_layout/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialSupportFragment.java#L224
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
