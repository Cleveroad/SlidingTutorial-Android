/*
 *   The MIT License (MIT)
 *
 *   Copyright (c) 2015 Cleveroad
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

/**
 * Base Fragment that contains {@link ViewPager} and where happens most logic like dispatching
 * transform event to child fragments, changing background color, and located page mPageIndicator.
 */
@SuppressWarnings("unused")
public abstract class TutorialFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private View mButtonSkip;
    private View mSeparator;
    private ViewPagerIndicator mPageIndicator;

    private final ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private TutorialOptions mTutorialOptions;

    public TutorialFragment() {
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
        mPageIndicator = (ViewPagerIndicator) view.findViewById(getIndicatorResId());
        mButtonSkip = view.findViewById(getButtonSkipResId());
        mSeparator = view.findViewById(getSeparatorResId());

        mViewPager.setAdapter(new TutorialAdapter(getChildFragmentManager()));
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setPageTransformer(true, new FragmentTransformer());
        ViewPager.OnPageChangeListener onPageChangeListener = new CustomPageChangeListener();
        mPageIndicator.initWith(mViewPager, onPageChangeListener, mTutorialOptions.getIndicatorOptions());

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

    /**
     * Get line mSeparator view.
     *
     * @return line mSeparator view
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

    /**
     * According to position and positionOffset changing background color. If last position then
     * change root view alpha.
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mTutorialOptions.isUseInfiniteScroll() && mTutorialOptions.getPagesCount() != 0) {
            position %= mTutorialOptions.getPagesCount();
        }
        int nextColorPosition = position + 1;
        if (nextColorPosition >= mTutorialOptions.getPagesCount()) {
            nextColorPosition %= mTutorialOptions.getPagesCount();
        }

        if (mTutorialOptions.isAutoRemoveTutorialFragment() && position == mTutorialOptions.getPagesCount() - 1) {
            mViewPager.setBackgroundColor(getPageColor(position));
            if (getView() != null) {
                getView().setAlpha(1 - positionOffset);
            }
        } else if (position < mTutorialOptions.getPagesCount()) {
            mViewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, getPageColor(position), getPageColor(nextColorPosition)));
        }
    }

    /**
     * When last position will be reached then remove fragment from the screen.
     */
    @Override
    public void onPageSelected(int position) {
        if (mTutorialOptions.isAutoRemoveTutorialFragment() && position == mTutorialOptions.getPagesCount()) {
            removeFragmentFromScreen();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        /* NOP */
    }

    private void removeFragmentFromScreen() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(TutorialFragment.this)
                .commitAllowingStateLoss();
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
    protected PageFragment getPage(int position) {
        position %= mTutorialOptions.getPagesCount();
        return mTutorialOptions.getTutorialPageProvider().providePage(position);
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
            if (mTutorialOptions.isUseInfiniteScroll()) {
                position %= mTutorialOptions.getPagesCount();
            }
            if (position < mTutorialOptions.getPagesCount()) {
                return getPage(position);
            } else if (position == mTutorialOptions.getPagesCount()) {
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
            if (obj instanceof PageFragment) {
                ((PageFragment) obj).transformPage(view.getWidth(), position);
            }
        }
    }

    public interface OnTutorialPageChangeListener {
        void onTutorialPageChanged(int position);
    }

    private class CustomPageChangeListener implements ViewPager.OnPageChangeListener {

        private ViewPager.OnPageChangeListener inner;

        public void setInner(ViewPager.OnPageChangeListener inner) {
            this.inner = inner;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mTutorialOptions.isUseInfiniteScroll()) {

            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}