package com.cleveroad.slidingtutorial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Class contains configuration for {@link TutorialFragment}.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class TutorialOptions {

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
    public TutorialPageProvider getTutorialPageProvider() {
        return mTutorialPageProvider;
    }

    /**
     * Builder for creating {@link TutorialOptions} which using in {@link TutorialFragment}
     */
    public static class Builder {
        private boolean mAutoRemoveTutorialFragment;
        private boolean mUseInfiniteScroll;
        private int mPagesCount;
        private int[] mPagesColors;
        private View.OnClickListener mOnSkipClickListener;
        private IndicatorOptions mIndicatorOptions;
        private TutorialPageProvider mTutorialPageProvider;
        private Context mContext;

        public Builder(@NonNull Context context) {
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
         *
         * @param indicatorOptions {@link IndicatorOptions} instance, which
         */
        public Builder setIndicatorOptions(@NonNull IndicatorOptions indicatorOptions) {
            mIndicatorOptions = ValidationUtil.checkNotNull(indicatorOptions);
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
            ValidationUtil.checkNotNull(mContext, "Context can't be null.");
            if (mIndicatorOptions == null) {
                mIndicatorOptions = IndicatorOptions.provideDefault(mContext);
            }
            return TutorialOptions.create(this);
        }
    }

}