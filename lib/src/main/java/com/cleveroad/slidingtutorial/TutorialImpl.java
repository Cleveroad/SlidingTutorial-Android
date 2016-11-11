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
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedParameters")
final class TutorialImpl<TFragment> {

    static final int EMPTY_FRAGMENT_POSITION = -1;

    private ViewPager mViewPager;
    @Nullable
    private View mButtonSkip;
    @Nullable
    private View mSeparator;
    @Nullable
    private TutorialPageIndicator mPageIndicator;
    private final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private PagerAdapter mTutorialAdapter;
    private TutorialOptions mTutorialOptions;
    private List<OnTutorialPageChangeListener> mOnTutorialPageChangeListeners = new ArrayList<>();

    private InternalFragment mInternalFragment;

    private final DataSetObserver mDataSetObservable = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            if (mPageIndicator != null) {
                mPageIndicator.setPagesCount(mTutorialOptions.getPagesCount());
                mPageIndicator.postInvalidate();
            }
        }
    };

    TutorialImpl(@NonNull InternalFragment fragment) {
        mInternalFragment = fragment;
    }

    View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(mInternalFragment.getLayoutResId(), container, false);

        mViewPager = (ViewPager) view.findViewById(mInternalFragment.getViewPagerResId());
        mPageIndicator = (TutorialPageIndicator) view.findViewById(mInternalFragment.getIndicatorResId());
        mButtonSkip = view.findViewById(mInternalFragment.getButtonSkipResId());
        mSeparator = view.findViewById(mInternalFragment.getSeparatorResId());

        mViewPager.setPageTransformer(true, new FragmentTransformer());
        mViewPager.addOnPageChangeListener(new InternalHelperPageChangeDecorator());

        return view;
    }

    void onViewCreated(View view, Bundle savedInstanceState) {
        mTutorialOptions = mInternalFragment.provideTutorialOptions();

        mTutorialAdapter = mInternalFragment.getPagerAdapter();
        mTutorialAdapter.registerDataSetObserver(mDataSetObservable);
        mViewPager.setAdapter(mTutorialAdapter);
        if (mPageIndicator != null) {
            mPageIndicator.initWith(mTutorialOptions.getIndicatorOptions(), mTutorialOptions.getPagesCount());
        }
        if (mButtonSkip != null) {
            mButtonSkip.setOnClickListener(mTutorialOptions.getOnSkipClickListener());
        }

        if (mTutorialOptions.isUseInfiniteScroll()) {
            int pos = Integer.MAX_VALUE / 2;
            // to avoid java.lang.ArithmeticException: divide by zero when user specify pages count = 0
            if (mTutorialOptions.getPagesCount() != 0) {
                pos -= pos % mTutorialOptions.getPagesCount();
            } else {
                pos = 0;
            }
            mViewPager.setCurrentItem(pos);
        }
    }

    void onDestroyView() {
        if (mTutorialAdapter != null) {
            mTutorialAdapter.unregisterDataSetObserver(mDataSetObservable);
        }
        mViewPager.clearOnPageChangeListeners();
        mOnTutorialPageChangeListeners.clear();
    }

    /**
     * Get line separator view.
     *
     * @return line separator view
     */
    @SuppressWarnings("NullableProblems")
    View getSeparator() {
        return mSeparator;
    }

    /**
     * Get view pager.
     *
     * @return view pager
     */
    ViewPager getViewPager() {
        return mViewPager;
    }

    /**
     * Get skip button.
     *
     * @return skip button
     */
    View getSkipButton() {
        return mButtonSkip;
    }

    /**
     * Get view pager mPageIndicator.
     *
     * @return view pager mPageIndicator
     */
    View getPagerIndicator() {
        return mPageIndicator;
    }

    /**
     * Add {@link OnTutorialPageChangeListener} to listen page change events.
     *
     * @param listener implementation of  {@link OnTutorialPageChangeListener} to add
     * @return is 'listener' parameter was added
     */
    @SuppressWarnings("SimplifiableIfStatement")
    boolean addOnTutorialPageChangeListener(@NonNull OnTutorialPageChangeListener listener) {
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
    boolean removeOnTutorialPageChangeListener(@NonNull OnTutorialPageChangeListener listener) {
        return mOnTutorialPageChangeListeners.remove(listener);
    }

    @LayoutRes
    int getDefaultLayoutResId() {
        return R.layout.st_fragment_presentation;
    }

    @IdRes
    int getDefaultViewPagerResId() {
        return R.id.viewPager;
    }

    @IdRes
    int getDefaultIndicatorResId() {
        return R.id.indicator;
    }

    @IdRes
    int getDefaultButtonSkipResId() {
        return R.id.tvSkip;
    }

    @IdRes
    int getDefaultSeparatorResId() {
        return R.id.separator;
    }

    /**
     * Get current {@link TutorialOptions} configuration.
     *
     * @return {@link TutorialOptions} instance
     */
    @NonNull
    TutorialOptions getTutorialOptions() {
        return mTutorialOptions;
    }

    /**
     * Get specific page by position index.
     *
     * @param position index of page
     * @return page at specified position
     */
    @SuppressWarnings({"ConstantConditions", "unchecked"})
    TFragment getPage(int position) {
        position %= mTutorialOptions.getPagesCount();
        return (TFragment) mTutorialOptions.getTutorialPageProvider().providePage(position);
    }

    /**
     * Get page color.
     *
     * @param position index of page
     * @return color of page
     */
    @ColorInt
    int getPageColor(int position) {
        if (mTutorialOptions.getPagesColors() != null && position <= mTutorialOptions.getPagesColors().length - 1) {
            return mTutorialOptions.getPagesColors()[position];
        }
        return Color.TRANSPARENT;
    }

    /**
     * Abstract adapter implementation
     */
    static abstract class TutorialAdapterImpl<TFragment> {

        private TutorialImpl<TFragment> mTutorial;

        abstract TFragment getEmptyFragment();

        TutorialAdapterImpl(TutorialImpl<TFragment> tutorial) {
            mTutorial = tutorial;
        }

        TFragment getItem(int position) {
            int realPagesCount = getRealPagesCount();
            if (mTutorial.getTutorialOptions().isUseInfiniteScroll()) {
                position %= realPagesCount;
            }
            if (position < realPagesCount) {
                return mTutorial.getPage(position);
            } else if (mTutorial.getTutorialOptions().isAutoRemoveTutorialFragment() &&
                    !mTutorial.getTutorialOptions().isUseInfiniteScroll() &&
                    position >= realPagesCount) {
                return getEmptyFragment();
            } else {
                throw new IllegalArgumentException("Invalid position: " + position);
            }
        }

        int getRealPagesCount() {
            return mTutorial.getTutorialOptions().getPagesCount();
        }

        int getCount() {
            int pagesCount = mTutorial.getTutorialOptions().getPagesCount();
            if (pagesCount == 0) {
                return 0;
            }

            if (mTutorial.getTutorialOptions().isUseInfiniteScroll()) {
                return Integer.MAX_VALUE;
            }

            if (mTutorial.getTutorialOptions().isAutoRemoveTutorialFragment()) {
                return pagesCount + 1;
            }

            return pagesCount;
        }
    }

    /**
     * Implementation of {@link android.support.v4.view.ViewPager.PageTransformer} that dispatch
     * transform page event whenever a visible/attached page is scrolled.
     */
    private class FragmentTransformer implements ViewPager.PageTransformer {

        public void transformPage(View view, float position) {
            Object obj = view.getTag(R.id.st_page_fragment);
            if (obj instanceof PageImpl) {
                ((PageImpl) obj).transformPage(view.getWidth(), position);
            }

            ViewPager.PageTransformer userPageTransformer = mTutorialOptions.getPageTransformer();
            if(userPageTransformer != null) {
                userPageTransformer.transformPage(view, position);
            }
        }
    }

    interface InternalFragment {
        View getView();

        TutorialOptions provideTutorialOptions();

        PagerAdapter getPagerAdapter();

        void removeCurrentFragment();

        @LayoutRes
        int getLayoutResId();

        @IdRes
        int getViewPagerResId();

        @IdRes
        int getIndicatorResId();

        @IdRes
        int getButtonSkipResId();

        @IdRes
        int getSeparatorResId();
    }

    @SuppressWarnings("unused")
    interface IndicatorPageListener {

        /**
         * This method will be invoked when the current page is scrolled, either as part
         * of a programmatically initiated smooth scroll or a user initiated touch scroll.
         *
         * @param position         Position index of the first page currently being displayed.
         *                         Page position+1 will be visible if positionOffset is nonzero.
         * @param positionOffset   Value from [0, 1) indicating the offset from the page at position.
         * @param isInfiniteScroll flag for indicating infinite scroll
         */
        void onPageScrolled(int position, float positionOffset, boolean isInfiniteScroll);
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
                if (mInternalFragment.getView() != null) {
                    mInternalFragment.getView().setAlpha(1f - positionOffset);
                }
            } else if (tempPos < mTutorialOptions.getPagesCount()) {
                mViewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, getPageColor(tempPos), getPageColor(nextColorPosition)));
            }

            // ViewPageIndicator callback forward
            if (mPageIndicator != null) {
                mPageIndicator.onPageScrolled(position % mTutorialOptions.getPagesCount(),
                        positionOffset, mTutorialOptions.isUseInfiniteScroll());
            }
        }

        /**
         * When last position will be reached then remove fragment from the screen, also forward
         * callback to all OnTutorialPageChangeListeners.
         */
        @Override
        public void onPageSelected(int position) {
            // Forward callback to all OnTutorialPageChangeListeners
            int pos = position == mTutorialOptions.getPagesCount() ? EMPTY_FRAGMENT_POSITION : position;
            pos %= mTutorialOptions.getPagesCount();
            for (OnTutorialPageChangeListener onTutorialPageChangeListener : mOnTutorialPageChangeListeners) {
                onTutorialPageChangeListener.onPageChanged(pos);
            }
            // If we reach end of tutorial and flag isUseAutoRemoveTutorialFragment is true - remove TutorialSupportFragment
            if (mTutorialOptions.isAutoRemoveTutorialFragment() && position == mTutorialOptions.getPagesCount()) {
                mInternalFragment.removeCurrentFragment();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            /* NOP */
        }
    }
}
