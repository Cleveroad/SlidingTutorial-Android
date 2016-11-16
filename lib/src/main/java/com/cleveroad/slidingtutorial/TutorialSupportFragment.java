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

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Base Fragment that contains {@link ViewPager} and where happens most logic like dispatching
 * transform event to child fragments, changing background color, and located page indicator.
 */
@SuppressWarnings({"unused"})
public abstract class TutorialSupportFragment extends Fragment {

    /**
     * Position of empty fragment, which used for smooth move to content after tutorial.
     */
    public static int EMPTY_FRAGMENT_POSITION = TutorialImpl.EMPTY_FRAGMENT_POSITION;

    private final Fragment emptyFragment = new Fragment();
    private TutorialImpl<Fragment> mTutorial;
    private TutorialImpl.TutorialAdapterImpl<Fragment> mTutorialAdapterImpl;
    private final TutorialImpl.InternalFragment mInternalFragment = new TutorialImpl.InternalFragment() {
        @Override
        public View getView() {
            return TutorialSupportFragment.this.getView();
        }

        @Override
        public TutorialOptions provideTutorialOptions() {
            return TutorialSupportFragment.this.provideTutorialOptions();
        }

        @Override
        public void removeCurrentFragment() {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .remove(TutorialSupportFragment.this)
                    .commitAllowingStateLoss();
        }

        @Override
        public PagerAdapter getPagerAdapter() {
            return new TutorialAdapter(getChildFragmentManager());
        }

        @Override
        public int getLayoutResId() {
            return TutorialSupportFragment.this.getLayoutResId();
        }

        @Override
        public int getViewPagerResId() {
            return TutorialSupportFragment.this.getViewPagerResId();
        }

        @Override
        public int getIndicatorResId() {
            return TutorialSupportFragment.this.getIndicatorResId();
        }

        @Override
        public int getButtonSkipResId() {
            return TutorialSupportFragment.this.getButtonSkipResId();
        }

        @Override
        public int getSeparatorResId() {
            return TutorialSupportFragment.this.getSeparatorResId();
        }

    };

    /**
     * Create new {@link TutorialOptions.Builder} instance.
     *
     * @param context {@link Context} instance
     * @return {@link TutorialOptions.Builder} instance
     */
    public static TutorialOptions.Builder<Fragment> newTutorialOptionsBuilder(@NonNull Context context) {
        ValidationUtil.checkNotNull(context, "Context can't be null.");
        return TutorialOptions.newTutorialOptionsBuilder(context, Fragment.class);
    }

    public static TutorialFragment newInstance(@NonNull TutorialOptions tutorialOptions) {
        return new TutorialSupportFragmentImpl(tutorialOptions);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mTutorial = new TutorialImpl<>(mInternalFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mTutorial.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTutorialAdapterImpl = new TutorialImpl.TutorialAdapterImpl<Fragment>(mTutorial) {
            @Override
            Fragment getEmptyFragment() {
                return emptyFragment;
            }
        };
        mTutorial.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        mTutorial.onDestroyView();
        super.onDestroyView();
    }

    /**
     * Get line separator view.
     *
     * @return line separator view
     */
    public View getSeparator() {
        return mTutorial.getSeparator();
    }

    /**
     * Get view pager.
     *
     * @return view pager
     */
    public ViewPager getViewPager() {
        return mTutorial.getViewPager();
    }

    /**
     * Get skip button.
     *
     * @return skip button
     */
    public View getSkipButton() {
        return mTutorial.getSkipButton();
    }

    /**
     * Get view pager mPageIndicator.
     *
     * @return view pager mPageIndicator
     */
    public View getPagerIndicator() {
        return mTutorial.getPagerIndicator();
    }

    /**
     * Add {@link OnTutorialPageChangeListener} to listen page change events.
     *
     * @param listener implementation of  {@link OnTutorialPageChangeListener} to add
     * @return is 'listener' parameter was added
     */
    public boolean addOnTutorialPageChangeListener(@NonNull OnTutorialPageChangeListener listener) {
        return mTutorial.addOnTutorialPageChangeListener(listener);
    }

    /**
     * Remove {@link OnTutorialPageChangeListener}.
     *
     * @param listener {@link OnTutorialPageChangeListener} to remove
     * @return is 'listener' parameter was removed
     */
    public boolean removeOnTutorialPageChangeListener(@NonNull OnTutorialPageChangeListener listener) {
        return mTutorial.removeOnTutorialPageChangeListener(listener);
    }

    @LayoutRes
    protected int getLayoutResId() {
        return mTutorial.getDefaultLayoutResId();
    }

    @IdRes
    protected int getViewPagerResId() {
        return mTutorial.getDefaultViewPagerResId();
    }

    @IdRes
    protected int getIndicatorResId() {
        return mTutorial.getDefaultIndicatorResId();
    }

    @IdRes
    protected int getButtonSkipResId() {
        return mTutorial.getDefaultButtonSkipResId();
    }

    @IdRes
    protected int getSeparatorResId() {
        return mTutorial.getDefaultSeparatorResId();
    }

    /**
     * Get current {@link TutorialOptions} configuration.
     *
     * @return {@link TutorialOptions} instance
     */
    @NonNull
    public TutorialOptions getTutorialOptions() {
        return mTutorial.getTutorialOptions();
    }

    /**
     * Get specific page by position index.
     *
     * @param position index of page
     * @return page at specified position
     */
    protected Fragment getPage(int position) {
        return mTutorial.getPage(position);
    }

    /**
     * Get page color.
     *
     * @param position index of page
     * @return color of page
     */
    @ColorInt
    protected int getPageColor(int position) {
        return mTutorial.getPageColor(position);
    }

    protected abstract TutorialOptions provideTutorialOptions();

    /**
     * Implementation of {@link android.support.v4.app.FragmentPagerAdapter} that in addition add empty last fragment.
     */
    class TutorialAdapter extends android.support.v4.app.FragmentPagerAdapter {

        private TutorialAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mTutorialAdapterImpl.getItem(position);
        }

        @Override
        public int getCount() {
            return mTutorialAdapterImpl.getCount();
        }
    }

    public static final class TutorialSupportFragmentImpl extends TutorialFragment {

        private TutorialOptions mTutorialOptions;

        public TutorialSupportFragmentImpl() {
        }

        @SuppressLint("ValidFragment")
        private TutorialSupportFragmentImpl(@NonNull TutorialOptions tutorialOptions) {
            mTutorialOptions = tutorialOptions;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        protected TutorialOptions provideTutorialOptions() {
            return mTutorialOptions;
        }
    }
}