Slidingtutorial
==============

##Simple library that helps developers to create sliding android app tutorial.

<img src="https://www.cleveroad.com/public/comercial/SlidingTutorial.gif" />

Applied parallax effects will make your product presentation look like Google apps tutorial.

All you need to do is:
<br>1. Create background designs for each screen of your tutorial (assistance with <a href="https://www.cleveroad.com/services/design/mobile-design">mobile design</a>)
<br>2. Create icons for each screen of your tutorial
<br>3. Follow the instructions below

Using
======

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

## License

        Copyright (С) 2015 Cleveroad

        This program is free software; you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation; either version 2 of the License, or
        (at your option) any later version.
    
        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

            http://choosealicense.com/licenses/gpl-2.0/

        You should have received a copy of the GNU General Public License along
        with this program; if not, write to the Free Software Foundation, Inc.,
        51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
