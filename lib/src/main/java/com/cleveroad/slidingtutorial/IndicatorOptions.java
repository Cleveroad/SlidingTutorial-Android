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
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

/**
 * Class contains configuration for {@link TutorialPageIndicator}.
 */
@SuppressWarnings("WeakerAccess")
public final class IndicatorOptions {

    @ColorInt
    private int mElementColor;

    @ColorInt
    private int mSelectedElementColor;

    private float mElementSize;

    private float mElementSpacing;

    private Renderer mRenderer;

    private IndicatorOptions(@NonNull Builder builder) {
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

    /**
     * Create new {@link IndicatorOptions.Builder} instance.
     *
     * @param context {@link Context} instance
     * @return {@link IndicatorOptions.Builder} instance
     */
    public static Builder newBuilder(@NonNull Context context) {
        ValidationUtil.checkNotNull(context, "Context can't be null.");
        return new Builder(context);
    }

    public final static class Builder {
        @ColorInt
        private int mSelectedElementColor = TutorialPageIndicator.NO_COLOR;
        @ColorInt
        private int mElementColor = TutorialPageIndicator.NO_COLOR;
        private float mElementSize = TutorialPageIndicator.NO_VALUE;
        private float mElementSpacing = TutorialPageIndicator.NO_VALUE;
        private Renderer mRenderer = null;
        private Context mContext;

        private Builder(@NonNull Context context) {
            mContext = context;
        }

        /**
         * Set indicator selected element color by color resource.
         *
         * @param indicatorColorResource color resource id
         * @return returns current {@link Builder}
         * @see #setSelectedElementColorRes(int)
         */
        public Builder setSelectedElementColorRes(@ColorRes int indicatorColorResource) {
            return setSelectedElementColor(ContextCompat.getColor(mContext, indicatorColorResource));
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
            return setElementColor(ContextCompat.getColor(mContext, indicatorColorResource));
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
