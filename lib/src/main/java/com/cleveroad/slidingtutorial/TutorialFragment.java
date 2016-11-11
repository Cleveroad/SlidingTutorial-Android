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
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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
public abstract class TutorialFragment extends Fragment {

    /**
     * Position of empty fragment, which used for smooth move to content after tutorial.
     */
    public static int EMPTY_FRAGMENT_POSITION = TutorialImpl.EMPTY_FRAGMENT_POSITION;

    private final Fragment emptyFragment = new Fragment();
    private TutorialImpl.TutorialAdapterImpl<Fragment> mTutorialAdapterImpl;
    private TutorialImpl<Fragment> mTutorial;
    private final TutorialImpl.InternalFragment mInternalFragment = new TutorialImpl.InternalFragment() {
        @Override
        public View getView() {
            return TutorialFragment.this.getView();
        }

        @Override
        public TutorialOptions provideTutorialOptions() {
            return TutorialFragment.this.provideTutorialOptions();
        }

        @Override
        public void removeCurrentFragment() {
            getActivity().getFragmentManager()
                    .beginTransaction()
                    .remove(TutorialFragment.this)
                    .commitAllowingStateLoss();
        }

        @Override
        public int getLayoutResId() {
            return TutorialFragment.this.getLayoutResId();
        }

        @Override
        public PagerAdapter getPagerAdapter() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return new TutorialAdapter(getChildFragmentManager());
            } else {
                return new TutorialAdapter(getFragmentManager());
            }
        }

        @Override
        public int getViewPagerResId() {
            return TutorialFragment.this.getViewPagerResId();
        }

        @Override
        public int getIndicatorResId() {
            return TutorialFragment.this.getIndicatorResId();
        }

        @Override
        public int getButtonSkipResId() {
            return TutorialFragment.this.getButtonSkipResId();
        }

        @Override
        public int getSeparatorResId() {
            return TutorialFragment.this.getSeparatorResId();
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
        return new TutorialFragmentImpl(tutorialOptions);
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
    @SuppressWarnings("ConstantConditions")
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
     * Implementation of {@link FragmentPagerAdapter} that in addition add empty last fragment.
     */
    class TutorialAdapter extends FragmentPagerAdapter {

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

    public static final class TutorialFragmentImpl extends TutorialFragment {

        private TutorialOptions mTutorialOptions;

        public TutorialFragmentImpl() {
        }

        @SuppressLint("ValidFragment")
        private TutorialFragmentImpl(@NonNull TutorialOptions tutorialOptions) {
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