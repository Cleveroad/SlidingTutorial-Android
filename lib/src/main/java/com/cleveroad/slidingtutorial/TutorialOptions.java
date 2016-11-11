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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Class contains configuration for {@link TutorialSupportFragment} and {@link TutorialFragment}.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class TutorialOptions {

    private boolean mAutoRemoveTutorialFragment;
    private boolean mUseInfiniteScroll;
    private int mPagesCount;
    private int[] mPagesColors;
    private View.OnClickListener mOnSkipClickListener;
    private IndicatorOptions mIndicatorOptions;
    private TutorialPageProvider mTutorialPageProvider;
    private ViewPager.PageTransformer mPageTransformer;

    @SuppressWarnings("unchecked")
    static TutorialOptions create(@NonNull Builder builder) {
        TutorialOptions tutorialOptions =  new TutorialOptions(builder.isUseAutoRemoveTutorialFragment(),
                builder.isUseInfiniteScroll(), builder.getPagesCount(), builder.getPagesColors(),
                builder.getOnSkipClickListener(), builder.getTutorialPageProvider(),
                builder.getIndicatorOptions());
        tutorialOptions.mPageTransformer = builder.mPageTransformer;
        return tutorialOptions;
    }

    private TutorialOptions(boolean autoRemoveTutorialFragment, boolean useInfiniteScroll,
                            int pagesCount, @NonNull int[] pagesColors,
                            @NonNull View.OnClickListener onSkipClickListener,
                            @NonNull TutorialPageProvider tutorialPageProvider,
                            @NonNull IndicatorOptions indicatorOptions) {
        mAutoRemoveTutorialFragment = autoRemoveTutorialFragment;
        mUseInfiniteScroll = useInfiniteScroll;
        mPagesCount = ValidationUtil.checkPagesCount(pagesCount);
        mPagesColors = pagesColors;
        mTutorialPageProvider = ValidationUtil.checkNotNull(tutorialPageProvider, "TutorialPageProvider can't be null");
        mIndicatorOptions = ValidationUtil.checkNotNull(indicatorOptions);
        mOnSkipClickListener = onSkipClickListener;
    }

    boolean isAutoRemoveTutorialFragment() {
        return mAutoRemoveTutorialFragment;
    }

    boolean isUseInfiniteScroll() {
        return mUseInfiniteScroll;
    }

    int getPagesCount() {
        return mPagesCount;
    }

    @Nullable
    View.OnClickListener getOnSkipClickListener() {
        return mOnSkipClickListener;
    }

    @Nullable
    int[] getPagesColors() {
        return mPagesColors;
    }

    @NonNull
    IndicatorOptions getIndicatorOptions() {
        return mIndicatorOptions;
    }

    @NonNull
    TutorialPageProvider getTutorialPageProvider() {
        return mTutorialPageProvider;
    }

    @Nullable
    ViewPager.PageTransformer getPageTransformer() {
        return mPageTransformer;
    }

    /**
     * Create new {@link TutorialOptions.Builder} instance.
     *
     * @param context {@link Context} instance
     * @return {@link TutorialOptions.Builder} instance
     */
    static <T> TutorialOptions.Builder<T> newTutorialOptionsBuilder(@NonNull Context context, Class<T> pClass) {
        ValidationUtil.checkNotNull(context, "Context can't be null.");
        return new TutorialOptions.Builder<>(context, pClass);
    }

    /**
     * Builder for creating {@link TutorialOptions} which using in {@link TutorialSupportFragment}
     */
    public final static class Builder<TFragment> {
        private final Class<TFragment> mClass;
        private boolean mAutoRemoveTutorialFragment;
        private boolean mUseInfiniteScroll;
        private int mPagesCount;
        private int[] mPagesColors;
        private View.OnClickListener mOnSkipClickListener;
        private IndicatorOptions mIndicatorOptions;
        private TutorialPageProvider<TFragment> mTutorialPageProvider;
        private Context mContext;
        private ViewPager.PageTransformer mPageTransformer;

        private Builder(@NonNull Context context, Class<TFragment> aClass) {
            mContext = ValidationUtil.checkNotNull(context).getApplicationContext();
            mClass = aClass;
        }

        boolean isUseAutoRemoveTutorialFragment() {
            return mAutoRemoveTutorialFragment;
        }

        boolean isUseInfiniteScroll() {
            return mUseInfiniteScroll;
        }

        int getPagesCount() {
            return mPagesCount;
        }

        int[] getPagesColors() {
            return mPagesColors;
        }

        TutorialPageProvider<TFragment> getTutorialPageProvider() {
            return mTutorialPageProvider;
        }

        IndicatorOptions getIndicatorOptions() {
            return mIndicatorOptions;
        }

        View.OnClickListener getOnSkipClickListener() {
            return mOnSkipClickListener;
        }

        /**
         * Set flag, which indicate does need to remove {@link TutorialSupportFragment} when end is reached.
         * By default is {@link Boolean#FALSE}.
         *
         * @param autoRemoveTutorialFragment boolean flag
         * @return current {@link Builder}
         */
        public Builder<TFragment> setUseAutoRemoveTutorialFragment(boolean autoRemoveTutorialFragment) {
            mAutoRemoveTutorialFragment = autoRemoveTutorialFragment;
            return this;
        }

        /**
         * Set flag, which indicate using infinite scroll.
         * By default is {@link Boolean#FALSE}.
         *
         * @param useInfiniteScroll boolean flag
         * @return current {@link Builder}
         */
        public Builder<TFragment> setUseInfiniteScroll(boolean useInfiniteScroll) {
            mUseInfiniteScroll = useInfiniteScroll;
            return this;
        }

        /**
         * Set pages amount.
         *
         * @param pagesCount pages count
         * @return current {@link Builder}
         */
        public Builder<TFragment> setPagesCount(int pagesCount) {
            mPagesCount = pagesCount;
            return this;
        }

        /**
         * Set pages color array with size equal to pages count.
         *
         * @param pagesColors pages colors array
         * @return current {@link Builder}
         */
        public Builder<TFragment> setPagesColors(@NonNull int[] pagesColors) {
            mPagesColors = pagesColors;
            return this;
        }

        /**
         * Set click listener for skip tutorial button.
         *
         * @param onSkipClickListener instance {@link android.view.View.OnClickListener}
         * @return current {@link Builder}
         */
        public Builder<TFragment> setOnSkipClickListener(@NonNull View.OnClickListener onSkipClickListener) {
            mOnSkipClickListener = onSkipClickListener;
            return this;
        }

        /**
         * Set configuration for indicator.
         * If any wasn't specify - use default configuration:
         * <ul>
         * <li>elementColor         : {@link android.graphics.Color#DKGRAY}</li>
         * <li>selectedElementColor : {@link android.graphics.Color#WHITE}</li>
         * <li>elementSize          : {@link com.cleveroad.slidingtutorial.R.dimen#st_indicator_size_default} = 4dp</li>
         * <li>setElementSpacing    : {@link com.cleveroad.slidingtutorial.R.dimen#st_indicator_spacing_default} = 4dp</li>
         * </ul>
         *
         * @param indicatorOptions {@link IndicatorOptions} instance with configuration.
         */
        public Builder<TFragment> setIndicatorOptions(@NonNull IndicatorOptions indicatorOptions) {
            mIndicatorOptions = ValidationUtil.checkNotNull(indicatorOptions, "IndicatorOptions can't be null.");
            return this;
        }

        /**
         * Set {@link TutorialPageOptionsProvider} for providing page options like page layout id, transform items.
         * Using this method override previous provider from {@link #setTutorialPageProvider(TutorialPageProvider)}.
         *
         * @param tutorialPageOptionsProvider pages options provider
         * @return current {@link Builder}
         * @see #setTutorialPageProvider(TutorialPageProvider)
         */
        public Builder<TFragment> setTutorialPageProvider(@NonNull final TutorialPageOptionsProvider tutorialPageOptionsProvider) {
            mTutorialPageProvider = new TutorialPageProvider<TFragment>() {
                @SuppressWarnings("unchecked")
                @NonNull
                @Override
                public TFragment providePage(int position) {
                    if (android.app.Fragment.class.equals(mClass)) {
                        return (TFragment) SimplePageFragment.newInstance(tutorialPageOptionsProvider.provide(position));
                    } else if (android.support.v4.app.Fragment.class.equals(mClass)) {
                        return (TFragment) SimplePageSupportFragment.newInstance(tutorialPageOptionsProvider.provide(position));
                    }
                    throw new IllegalArgumentException("Invalid type of fragment.");
                }
            };
            return this;
        }

        /**
         * Set {@link TutorialPageProvider} for providing support page fragments.
         * Using this method override previous provider from {@link #setTutorialPageProvider(TutorialPageOptionsProvider)}.
         *
         * @param tutorialPageProvider pages fragment provider
         * @return current {@link Builder}
         * @see #setTutorialPageProvider(TutorialPageOptionsProvider)
         */
        public Builder<TFragment> setTutorialPageProvider(@NonNull TutorialPageProvider<TFragment> tutorialPageProvider) {
            mTutorialPageProvider = tutorialPageProvider;
            return this;
        }

        /**
         * Set a {@link ViewPager.PageTransformer} that will be called for each attached page whenever
         * the scroll position is changed. This allows the application to apply custom property
         * transformations to each page, overriding the default sliding look and feel.
         *
         * <p><em>Note:</em> Prior to Android 3.0 the property animation APIs did not exist.
         * As a result, setting a PageTransformer prior to Android 3.0 (API 11) will have no effect.</p>
         * @param pageTransformer PageTransformer that will modify each page's animation properties
         * @return current {@link Builder}
         * @see ViewPager#setPageTransformer(boolean, ViewPager.PageTransformer)
         */
        public Builder<TFragment> setPageTransformer(ViewPager.PageTransformer pageTransformer) {
            mPageTransformer = pageTransformer;
            return this;
        }

        /**
         * Create {@link TutorialOptions} instance based on specified parameters in {@link Builder}.
         *
         * @return returns {@link TutorialOptions} instance
         */
        public TutorialOptions build() {
            if (mIndicatorOptions == null) {
                mIndicatorOptions = IndicatorOptions.newBuilder(mContext).build();
            }
            return TutorialOptions.create(this);
        }
    }

}