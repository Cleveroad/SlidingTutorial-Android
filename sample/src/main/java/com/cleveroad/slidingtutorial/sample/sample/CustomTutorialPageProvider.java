package com.cleveroad.slidingtutorial.sample.sample;

import android.support.annotation.NonNull;
import android.app.Fragment;

import com.cleveroad.slidingtutorial.TutorialPageProvider;

public class CustomTutorialPageProvider implements TutorialPageProvider<Fragment> {

    private static final int ACTUAL_PAGES_COUNT = 3;

    @NonNull
    @Override
    public Fragment providePage(int position) {
        position %= ACTUAL_PAGES_COUNT;
        switch (position) {
            case 0:
                return new FirstCustomPageFragment();
            case 1:
                return new SecondCustomPageFragment();
            case 2:
                return new ThirdCustomPageFragment();
            default: {
                throw new IllegalArgumentException("Unknown position: " + position);
            }
        }
    }
}
