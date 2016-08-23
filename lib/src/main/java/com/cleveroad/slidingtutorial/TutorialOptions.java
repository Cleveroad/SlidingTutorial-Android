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
import android.view.View;

/**
 * Class contains configuration for {@link TutorialFragment}.
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

    static TutorialOptions create(@NonNull Builder builder) {
        return new TutorialOptions(builder.isAutoRemoveTutorialFragment(),
                builder.isUseInfiniteScroll(), builder.getPagesCount(), builder.getPagesColors(),
                builder.getOnSkipClickListener(), builder.getTutorialPageProvider(),
                builder.getIndicatorOptions());
    }

    private TutorialOptions(boolean autoRemoveTutorialFragment, boolean useInfiniteScroll,
                            int pagesCount, @NonNull int[] pagesColors,
                            @NonNull View.OnClickListener onSkipClickListener,
                            @NonNull TutorialPageProvider tutorialPageProvider,
                            @NonNull IndicatorOptions indicatorOptions) {
        mAutoRemoveTutorialFragment = autoRemoveTutorialFragment;
        mUseInfiniteScroll = useInfiniteScroll;
        mPagesCount = ValidationUtil.checkPagesCount(pagesCount);
        ValidationUtil.checkNotNull(pagesColors, "Pages color array can't be null");
        mPagesColors = ValidationUtil.checkPagesColorsSize(pagesCount, pagesColors);
        mTutorialPageProvider = ValidationUtil.checkNotNull(tutorialPageProvider);
        mIndicatorOptions = ValidationUtil.checkNotNull(indicatorOptions);
        mOnSkipClickListener = ValidationUtil.checkNotNull(onSkipClickListener);
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

    @NonNull
    View.OnClickListener getOnSkipClickListener() {
        return mOnSkipClickListener;
    }

    @NonNull
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

    /**
     * Create new {@link Builder} instance.
     *
     * @param context {@link Context} instance
     * @return {@link Builder} instance
     */
    public static Builder newBuilder(@NonNull Context context) {
        ValidationUtil.checkNotNull(context, "Context can't be null.");
        return new Builder(context);
    }

    /**
     * Builder for creating {@link TutorialOptions} which using in {@link TutorialFragment}
     */
    public final static class Builder {
        private boolean mAutoRemoveTutorialFragment;
        private boolean mUseInfiniteScroll;
        private int mPagesCount;
        private int[] mPagesColors;
        private View.OnClickListener mOnSkipClickListener;
        private IndicatorOptions mIndicatorOptions;
        private TutorialPageProvider mTutorialPageProvider;
        private Context mContext;

        private Builder(@NonNull Context context) {
            mContext = ValidationUtil.checkNotNull(context);
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

        int[] getPagesColors() {
            return mPagesColors;
        }

        TutorialPageProvider getTutorialPageProvider() {
            return mTutorialPageProvider;
        }

        IndicatorOptions getIndicatorOptions() {
            return mIndicatorOptions;
        }

        @NonNull
        View.OnClickListener getOnSkipClickListener() {
            return mOnSkipClickListener;
        }

        /**
         * Set flag, which indicate does need to remove {@link TutorialFragment} when end is reached.
         * By default is {@link Boolean#FALSE}.
         *
         * @param autoRemoveTutorialFragment boolean flag
         * @return current {@link Builder}
         */
        public Builder isAutoRemoveTutorialFragment(boolean autoRemoveTutorialFragment) {
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
        public Builder isUseInfiniteScroll(boolean useInfiniteScroll) {
            mUseInfiniteScroll = useInfiniteScroll;
            return this;
        }

        /**
         * Set pages amount.
         *
         * @param pagesCount pages count
         * @return current {@link Builder}
         */
        public Builder setPagesCount(int pagesCount) {
            mPagesCount = pagesCount;
            return this;
        }

        /**
         * Set pages color array with size equal to pages count.
         *
         * @param pagesColors pages colors array
         * @return current {@link Builder}
         */
        public Builder setPagesColors(@NonNull int[] pagesColors) {
            mPagesColors = pagesColors;
            return this;
        }

        /**
         * Set click listener for skip tutorial button.
         *
         * @param onSkipClickListener instance {@link android.view.View.OnClickListener}
         * @return current {@link Builder}
         */
        public Builder onSkipClickListener(@NonNull View.OnClickListener onSkipClickListener) {
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
        public Builder setIndicatorOptions(@NonNull IndicatorOptions indicatorOptions) {
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
        public Builder setTutorialPageProvider(@NonNull final TutorialPageOptionsProvider tutorialPageOptionsProvider) {
            mTutorialPageProvider = new TutorialPageProvider() {
                @NonNull
                @Override
                public PageFragment providePage(int position) {
                    return PageFragment.newInstance(tutorialPageOptionsProvider.provide(position));
                }
            };
            return this;
        }

        /**
         * Set {@link TutorialPageProvider} for providing page fragment.
         * Using this method override previous provider from {@link #setTutorialPageProvider(TutorialPageOptionsProvider)}.
         *
         * @param tutorialPageProvider pages fragment provider
         * @return current {@link Builder}
         * @see #setTutorialPageProvider(TutorialPageOptionsProvider)
         */
        public Builder setTutorialPageProvider(@NonNull TutorialPageProvider tutorialPageProvider) {
            mTutorialPageProvider = tutorialPageProvider;
            return this;
        }

        /**
         * Create {@link TutorialOptions} instance based on specified parameters in {@link Builder}.
         *
         * @return returns {@link TutorialOptions} instance
         */
        public TutorialOptions build() {
            if (mIndicatorOptions == null) {
                mIndicatorOptions = IndicatorOptions.provideDefault(mContext);
            }
            return TutorialOptions.create(this);
        }
    }

}