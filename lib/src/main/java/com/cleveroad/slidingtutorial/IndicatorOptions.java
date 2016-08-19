package com.cleveroad.slidingtutorial;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Class contains configuration for {@link ViewPagerIndicator}.
 */
public class IndicatorOptions {

    @ColorInt
    private int mElementColor;

    @ColorInt
    private int mSelectedElementColor;

    private float mElementSize;

    private float mElementSpacing;

    private Renderer mRenderer;

    static IndicatorOptions provideDefault(@NonNull Context context) {
        return new Builder(context)
                .setElementColorRes(android.R.color.secondary_text_dark_nodisable)
                .setSelectedElementColorRes(android.R.color.white)
                .setElementSizeRes(R.dimen.st_indicator_size_default)
                .setElementSpacingRes(R.dimen.st_indicator_spacing_default)
                .build();
    }

    private IndicatorOptions(Builder builder) {
        mElementColor = builder.mElementColor;
        mSelectedElementColor = builder.mSelectedElementColor;
        mElementSize = builder.mElementSize;
        mElementSpacing = builder.mElementSpacing;
        mRenderer = builder.mRenderer;
    }

    @ColorInt
    int getElementColor() {
        return mElementColor;
    }

    @ColorInt
    int getSelectedElementColor() {
        return mSelectedElementColor;
    }

    float getElementSize() {
        return mElementSize;
    }

    float getElementSpacing() {
        return mElementSpacing;
    }

    @Nullable
    Renderer getRenderer() {
        return mRenderer;
    }

    public static class Builder {
        @ColorInt
        private int mSelectedElementColor = ViewPagerIndicator.NO_VALUE;
        @ColorInt
        private int mElementColor = ViewPagerIndicator.NO_VALUE;
        private float mElementSize = ViewPagerIndicator.NO_VALUE;
        private float mElementSpacing = ViewPagerIndicator.NO_VALUE;
        private Renderer mRenderer = null;
        private Context mContext;

        public Builder(@NonNull Context context) {
            mContext = ValidationUtil.checkNotNull(context, "Context can't be null.");
        }

        /**
         * Set indicator selected element color by color resource.
         *
         * @param indicatorColorResource color resource id
         * @return returns current {@link Builder}
         * @see #setSelectedElementColorRes(int)
         */
        public Builder setSelectedElementColorRes(@ColorRes int indicatorColorResource) {
            return setSelectedElementColor(getColor(mContext, indicatorColorResource));
        }

        /**
         * Set indicator selected element color.
         *
         * @param indicatorElementColor indicator color value
         * @return returns current {@link Builder}
         * @see #setSelectedElementColorRes(int)
         */
        public Builder setSelectedElementColor(@ColorInt int indicatorElementColor) {
            mSelectedElementColor = indicatorElementColor;
            return this;
        }

        /**
         * Set indicator element color by color resource.
         *
         * @param indicatorColorResource color resource id
         * @return returns current {@link Builder}
         * @see #setSelectedElementColorRes(int)
         */
        public Builder setElementColorRes(@ColorRes int indicatorColorResource) {
            return setElementColor(getColor(mContext, indicatorColorResource));
        }

        /**
         * Set indicator element color.
         *
         * @param indicatorElementColor indicator color value
         * @return returns current {@link Builder}
         * @see #setElementColorRes(int)
         */
        public Builder setElementColor(@ColorInt int indicatorElementColor) {
            mElementColor = indicatorElementColor;
            return this;
        }

        /**
         * Set indicator element size by dimension resource.
         *
         * @param indicatorDimensionResId dimension resource id
         * @return returns current {@link Builder}
         * @see #setElementSize(float)
         */
        public Builder setElementSizeRes(@DimenRes int indicatorDimensionResId) {
            return setElementSize(mContext.getResources().getDimension(indicatorDimensionResId));
        }

        /**
         * Set indicator element size.
         *
         * @param indicatorElementSize indicator size
         * @return returns current {@link Builder}
         * @see #setElementSizeRes(int)
         */
        public Builder setElementSize(float indicatorElementSize) {
            mElementSize = indicatorElementSize;
            return this;
        }

        /**
         * Set indicator element spacing dimension resource.
         *
         * @param indicatorElementSpacingDimesResId indicator spacing dimension resource
         * @return returns current {@link Builder}
         * @see #setElementSpacing(float)
         */
        public Builder setElementSpacingRes(@DimenRes int indicatorElementSpacingDimesResId) {
            return setElementSpacing(mContext.getResources().getDimension(indicatorElementSpacingDimesResId));
        }

        /**
         * Set indicator element spacing.
         *
         * @param indicatorElementSpacing spacing value
         * @return returns current {@link Builder}
         * @see #setElementSpacingRes(int)
         */
        public Builder setElementSpacing(float indicatorElementSpacing) {
            mElementSpacing = indicatorElementSpacing;
            return this;
        }

        /**
         * Set renderer for rendering indicator.
         *
         * @param renderer {@link Renderer} implementation
         * @return returns current {@link Builder}
         */
        public Builder setRenderer(@NonNull Renderer renderer) {
            mRenderer = ValidationUtil.checkNotNull(renderer);
            return this;
        }

        @SuppressWarnings("deprecation")
        private static int getColor(@NonNull Context context, @ColorRes int colorResource) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return context.getColor(colorResource);
            } else {
                return context.getResources().getColor(colorResource);
            }
        }

        /**
         * Build {@link IndicatorOptions} instance with specified configuration.
         *
         * @return {@link IndicatorOptions} instance
         */
        public IndicatorOptions build() {
            return new IndicatorOptions(this);
        }
    }
}
