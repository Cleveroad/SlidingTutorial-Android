package com.cleveroad.slidingtutorial.sample.supportsample;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.cleveroad.slidingtutorial.TutorialPageProvider;

public class CustomTutorialPageProvider implements TutorialPageProvider<Fragment> {

    private static final int ACTUAL_PAGES_COUNT = 3;

    @NonNull
    @Override
    public Fragment providePage(int position) {
        position %= ACTUAL_PAGES_COUNT;
        switch (position) {
            case 0:
                Fragment fr1 = new FirstCustomPageSupportFragment();
                return fr1;
            case 1:
                Fragment fr2 = new SecondCustomPageSupportFragment();
                return fr2;
            case 2:
                Fragment fr3 = new ThirdCustomPageSupportFragment();
                return fr3;
            default: {
                throw new IllegalArgumentException("Unknown position: " + position);
            }
        }
    }
}
