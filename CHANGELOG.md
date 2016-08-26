## SlidingTutorial [![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome) <img src="https://www.cleveroad.com/public/comercial/label-android.svg" height="20"> <a href="https://www.cleveroad.com/?utm_source=github&utm_medium=label&utm_campaign=contacts"><img src="https://www.cleveroad.com/public/comercial/label-cleveroad.svg" height="20"></a>
![Header image](/images/header.jpg)

## Changelog

Version | Changes
---     | ---
v.1.0.0 | Library fully refactored. See full [1.0.0 Changelog](#100_Changelog)
v.0.9.5 | Added getters for views. Possible fix for manifest merging issues
v.0.9.4 | Renamed all attributes; all resources marked as private |
v.0.9.3 | Fixed issue with wrong page showed at startup if pages count not equals 3 
v.0.9.2 | Added onSkipButtonClicked method and SimplePagerFragment 
v.0.9.1 | Added infinite scroll behavior
v.0.9   | First public release

## 1.0.0 Changelog
* Renamed **PresentationPagerFragment** to [TutorialFragment].
* Created [SimplePageFragment] fragment with default implementaion for [PageFragment#getLayoutResId()] and [PageFragment#getTransformItems()].
* Removed capability to create new instance of [TransformItem] via `new`. Added fabric static method [TransformItem#create(int,Direction,float)].
* Created [OnTutorialPageChangeListener] to listen change page events.
* Use [TutorialFragment#addOnTutorialPageChangeListener()] and [TutorialFragment#removeOnTutorialPageChangeListener()] to add/remove listener. 
* Created [TutorialOptions] to configure [TutorialFragment].
* Created [TutorialPageOptionsProvider] and [PageOptions] to provide and configure [PageFragment] instances.
* Created [TutorialPageProvider] to provide [PageFragment] instances.
* Removed **CirclePageIndicator**.
* Created [TutorialPageIndicator] view.
* Created [IndicatorOptions] to configure [TutorialPageIndicator] view.
* Created [Renderer] interface that responds for drawing single indicator item. There are 2 default implementaion: [Renderer.Factory#newCircleRenderer()] and [Renderer.Factory#newSquareRenderer()].
* Created [TutorialSupportFragment] and [PageSupportFragment] for use with **AppCompat** library. [TutorialFragment] and [PageFragment] now using **android.app.Fragment**.

## Support
If you have any questions regarding the use of this tutorial, please contact us for support
at info@cleveroad.com (email subject: «Sliding android app tutorial. Support request.»)
<br>or
<br>Use our contacts:
<br><a href="https://www.cleveroad.com/?utm_source=github&utm_medium=link&utm_campaign=contacts">Cleveroad.com</a>
<br><a href="https://www.facebook.com/cleveroadinc">Facebook account</a>
<br><a href="https://twitter.com/CleveroadInc">Twitter account</a>
<br><a href="https://plus.google.com/+CleveroadInc/">Google+ account</a>

[migration manuals]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/MIGRATION.md
[changelog history]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/CHANGELOG.md
[PageFragment]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/PageFragment.java
[PageFragment#getLayoutResId()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/PageFragment.java#L123
[PageFragment#getTransformItems()]: hhttps://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/PageFragment.java#L128
[PageSupportFragment]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/PageSupportFragment.java
[PageFragment#getLayoutResId()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/PageFragment.java#L123
[PageFragment#getTransformItems()]: hhttps://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/PageFragment.java#L128
[Direction]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/Direction.java
[IndicatorOptions]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/IndicatorOptions.java
[IndicatorOptions.Builder]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/IndicatorOptions.java#L92
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
[TutorialFragment#provideTutorialOptions()]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L247
[TutorialFragment.OnTutorialPageChangeListener]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L315
[TutorialFragment#addOnTutorialPageChangeListener(OnTutorialPageChangeListener)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L173
[TutorialFragment#removeOnTutorialPageChangeListener(OnTutorialPageChangeListener)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialFragment.java#L186
[TutorialSupportFragment]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialSupportFragment.java
[TutorialOptions]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java
[TutorialOptions.Builder]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L112
[TutorialOptions.Builder#setTutorialPageProvider(TutorialPageOptionsProvider)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L237
[TutorialOptions.Builder#setTutorialPageProvider(TutorialPageProvider)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L256
[TutorialOptions.Builder#setTutorialSupportPageProvider(TutorialSupportPageProvider)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L256
[TutorialOptions.Builder#setOnSkipClickListener(OnClickListener)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L207
[TutorialOptions.Builder#setPagesColors(int array)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L196
[TutorialOptions.Builder#setPagesCount(int)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L185
[TutorialOptions.Builder#setUseInfiniteScroll(boolean)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L174
[TutorialOptions.Builder#setUseAutoRemoveTutorialFragment(boolean)]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialOptions.java#L162
[TutorialPageIndicator]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialPageIndicator.java
[TutorialPageOptionsProvider]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialPageOptionsProvider.java
[TutorialPageProvider]: https://github.com/Cleveroad/SlidingTutorial-Android/blob/master/lib/src/main/java/com/cleveroad/slidingtutorial/TutorialPageProvider.java