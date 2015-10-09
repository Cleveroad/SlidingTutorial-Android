package com.tutorial.sample;


import com.cleveroad.custompagerpresentation.R;
import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.PresentationPagerFragment;

import java.util.ArrayList;
import java.util.List;

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
