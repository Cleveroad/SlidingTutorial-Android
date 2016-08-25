/*
 *   The MIT License (MIT)
 *
 *   Copyright (c) 2016 Cleveroad
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */
package com.cleveroad.slidingtutorial;

import android.animation.ArgbEvaluator;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Fragment that contains {@link ViewPager} and where happens most logic like dispatching
 * transform event to child fragments, changing background color, and located page indicator.
 */
@SuppressWarnings({"unused", "SimplifiableIfStatement"})
public abstract class TutorialSupportFragment extends Fragment {

    /**
     * Position of empty fragment, which used for smooth move to content after tutorial.
     */
    public static int EMPTY_FRAGMENT_POSITION = -1;

    private ViewPager mViewPager;
    private View mButtonSkip;
    private View mSeparator;
    private TutorialPageIndicator mPageIndicator;
    private TutorialAdapter mTutorialAdapter;
    private final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private TutorialOptions mTutorialOptions;
    private List<OnTutorialPageChangeListener> mOnTutorialPageChangeListeners = new ArrayList<>();

    private final DataSetObserver mDataSetObservable = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            mPageIndicator.setPagesCount(mTutorialAdapter.getRealPagesCount());
            mPageIndicator.postInvalidate();
        }
    };

    public TutorialSupportFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);

        mTutorialOptions = provideTutorialOptions();

        mViewPager = (ViewPager) view.findViewById(getViewPagerResId());
        mPageIndicator = (TutorialPageIndicator) view.findViewById(getIndicatorResId());
        mButtonSkip = view.findViewById(getButtonSkipResId());
        mSeparator = view.findViewById(getSeparatorResId());

        mTutorialAdapter = new TutorialAdapter(getChildFragmentManager());
        mTutorialAdapter.registerDataSetObserver(mDataSetObservable);
        mViewPager.setAdapter(mTutorialAdapter);
        mViewPager.setPageTransformer(true, new FragmentTransformer());
        mViewPager.addOnPageChangeListener(new InternalHelperPageChangeDecorator());
        mPageIndicator.initWith(mTutorialOptions.getIndicatorOptions(), mTutorialOptions.getPagesCount());

        mButtonSkip.setOnClickListener(mTutorialOptions.getOnSkipClickListener());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mTutorialOptions.isUseInfiniteScroll()) {
            int pos = Integer.MAX_VALUE / 2;
            pos -= pos % mTutorialOptions.getPagesCount();
            mViewPager.setCurrentItem(pos);
        }
    }

    @Override
    public void onDestroyView() {
        mTutorialAdapter.unregisterDataSetObserver(mDataSetObservable);
        mViewPager.clearOnPageChangeListeners();
        mOnTutorialPageChangeListeners.clear();
        super.onDestroyView();
    }

    /**
     * Get line separator view.
     *
     * @return line separator view
     */
    public View getSeparator() {
        return mSeparator;
    }

    /**
     * Get view pager.
     *
     * @return view pager
     */
    public ViewPager getViewPager() {
        return mViewPager;
    }

    /**
     * Get skip button.
     *
     * @return skip button
     */
    public View getSkipButton() {
        return mButtonSkip;
    }

    /**
     * Get view pager mPageIndicator.
     *
     * @return view pager mPageIndicator
     */
    public View getPagerIndicator() {
        return mPageIndicator;
    }

    private void removeFragmentFromScreen() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(TutorialSupportFragment.this)
                .commitAllowingStateLoss();
    }

    /**
     * Add {@link OnTutorialPageChangeListener} to listen page change events.
     *
     * @param listener implementation of  {@link OnTutorialPageChangeListener} to add
     * @return is 'listener' parameter was added
     */
    public boolean addOnTutorialPageChangeListener(@NonNull OnTutorialPageChangeListener listener) {
        if (!mOnTutorialPageChangeListeners.contains(listener)) {
            return mOnTutorialPageChangeListeners.add(listener);
        }
        return false;
    }

    /**
     * Remove {@link OnTutorialPageChangeListener}.
     *
     * @param listener {@link OnTutorialPageChangeListener} to remove
     * @return is 'listener' parameter was removed
     */
    public boolean removeOnTutorialPageChangeListener(@NonNull OnTutorialPageChangeListener listener) {
        return mOnTutorialPageChangeListeners.remove(listener);
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.st_fragment_presentation;
    }

    @IdRes
    protected int getViewPagerResId() {
        return R.id.viewPager;
    }

    @IdRes
    protected int getIndicatorResId() {
        return R.id.indicator;
    }

    @IdRes
    protected int getButtonSkipResId() {
        return R.id.tvSkip;
    }

    @IdRes
    protected int getSeparatorResId() {
        return R.id.separator;
    }

    /**
     * Get current {@link TutorialOptions} configuration.
     *
     * @return {@link TutorialOptions} instance
     */
    @NonNull
    public TutorialOptions getTutorialOptions() {
        return mTutorialOptions;
    }

    /**
     * Get specific page by position index.
     *
     * @param position index of page
     * @return page at specified position
     */
    @SuppressWarnings("ConstantConditions")
    protected PageSupportFragment getPage(int position) {
        position %= mTutorialOptions.getPagesCount();
        return mTutorialOptions.getTutorialPageSupportProvider().providePage(position);
    }

    /**
     * Get page color.
     *
     * @param position index of page
     * @return color of page
     */
    @ColorInt
    protected int getPageColor(int position) {
        return mTutorialOptions.getPagesColors()[position];
    }

    protected abstract TutorialOptions provideTutorialOptions();

    /**
     * Implementation of {@link FragmentPagerAdapter} that in addition add empty last fragment.
     */
    class TutorialAdapter extends FragmentPagerAdapter {
        private final Fragment emptyFragment = new Fragment();

        private TutorialAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int realPagesCount = getRealPagesCount();
            if (mTutorialOptions.isUseInfiniteScroll()) {
                position %= realPagesCount;
            }
            if (position < realPagesCount) {
                return getPage(position);
            } else if (mTutorialOptions.isAutoRemoveTutorialFragment() &&
                    !mTutorialOptions.isUseInfiniteScroll() &&
                    position >= realPagesCount) {
                return emptyFragment;
            } else {
                throw new IllegalArgumentException("Invalid position: " + position);
            }
        }

        int getRealPagesCount() {
            return mTutorialOptions.getPagesCount();
        }

        @Override
        public int getCount() {
            if (mTutorialOptions.getPagesCount() == 0) {
                return 0;
            }

            if (mTutorialOptions.isUseInfiniteScroll()) {
                return Integer.MAX_VALUE;
            }

            if (mTutorialOptions.isAutoRemoveTutorialFragment()) {
                return mTutorialOptions.getPagesCount() + 1;
            }

            return mTutorialOptions.getPagesCount();
        }
    }

    /**
     * Implementation of {@link android.support.v4.view.ViewPager.PageTransformer} that dispatch
     * transform page event whenever a visible/attached page is scrolled.
     */
    private class FragmentTransformer implements ViewPager.PageTransformer {

        public void transformPage(View view, float position) {
            Object obj = view.getTag(R.id.st_page_fragment);
            if (obj instanceof PageSupportFragment) {
                ((PageSupportFragment) obj).transformPage(view.getWidth(), position);
            }
        }
    }

    private class InternalHelperPageChangeDecorator implements ViewPager.OnPageChangeListener {

        private InternalHelperPageChangeDecorator() {
            //no instance
        }

        /**
         * According to position and positionOffset changing background color. If last position then
         * change root view alpha, also forward callback to ViewPagerIndicator.
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Color change
            int tempPos = position;
            if (mTutorialOptions.isUseInfiniteScroll() && mTutorialOptions.getPagesCount() != 0) {
                tempPos %= mTutorialOptions.getPagesCount();
            }
            int nextColorPosition = tempPos + 1;
            if (nextColorPosition >= mTutorialOptions.getPagesCount()) {
                nextColorPosition %= mTutorialOptions.getPagesCount();
            }
            if (!mTutorialOptions.isUseInfiniteScroll() &&
                    mTutorialOptions.isAutoRemoveTutorialFragment() &&
                    tempPos == mTutorialOptions.getPagesCount() - 1) {
                mViewPager.setBackgroundColor(getPageColor(tempPos));
                if (getView() != null) {
                    getView().setAlpha(1f - positionOffset);
                }
            } else if (tempPos < mTutorialOptions.getPagesCount()) {
                mViewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, getPageColor(tempPos), getPageColor(nextColorPosition)));
            }

            // ViewPageIndicator callback forward
            mPageIndicator.onPageScrolled(position % mTutorialOptions.getPagesCount(),
                    positionOffset, mTutorialOptions.isUseInfiniteScroll());
        }

        /**
         * When last position will be reached then remove fragment from the screen, also forward
         * callback to all OnTutorialPageChangeListeners.
         */
        @Override
        public void onPageSelected(int position) {
            // Forward callback to all OnTutorialPageChangeListeners
            int pos = position == mTutorialOptions.getPagesCount() ? EMPTY_FRAGMENT_POSITION : position;
            for (OnTutorialPageChangeListener onTutorialPageChangeListener : mOnTutorialPageChangeListeners) {
                onTutorialPageChangeListener.onPageChanged(pos);
            }
            // If we reach end of tutorial and flag isUseAutoRemoveTutorialFragment is true - remove TutorialSupportFragment
            if (mTutorialOptions.isAutoRemoveTutorialFragment() && position == mTutorialOptions.getPagesCount()) {
                removeFragmentFromScreen();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            /* NOP */
        }
    }
}