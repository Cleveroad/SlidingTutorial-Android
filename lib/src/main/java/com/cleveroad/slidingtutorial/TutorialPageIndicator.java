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

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

/**
 * Indicator view.
 */
@SuppressWarnings("unused")
public final class TutorialPageIndicator extends View implements TutorialImpl.IndicatorPageListener {

    private static final float ANGLE_360 = 360f;

    static final int NO_VALUE = -1;
    static final int NO_COLOR = 1;
    @ColorInt
    static final int DEFAULT_ELEMENT_COLOR = Color.LTGRAY;
    @ColorInt
    static final int DEFAULT_SELECTED_ELEMENT_COLOR = Color.WHITE;

    private final RectF mClipBounds = new RectF();
    private final RectF mElementBounds = new RectF();
    private float mScrolledOffset;
    private int mSelectedPosition;
    private float mIndicatorElementSpacing;
    private float mIndicatorElementSize;
    private final Paint mIndicatorSelectedPaint = new Paint();
    private final Paint mIndicatorPaint = new Paint();
    private Renderer mRenderer;
    private int mPagesCount;
    private boolean mIsInfiniteScroll;

    public TutorialPageIndicator(Context context) {
        this(context, null);
    }

    public TutorialPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TutorialPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultSizes(context);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TutorialPageIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initDefaultSizes(context);
    }

    private void initDefaultSizes(@NonNull Context context) {
        mIndicatorElementSize = context.getResources().getDimension(R.dimen.st_indicator_size_default);
        mIndicatorElementSpacing = context.getResources().getDimension(R.dimen.st_indicator_spacing_default);
    }

    void initWith(@NonNull IndicatorOptions indicatorOptions, int pagesCount) {
        mIndicatorPaint.setAntiAlias(true);
        int elementColor;
        if (indicatorOptions.getElementColor() != NO_COLOR) {
            elementColor = indicatorOptions.getElementColor();
        } else {
            elementColor = TutorialPageIndicator.DEFAULT_ELEMENT_COLOR;
        }
        mIndicatorPaint.setColor(elementColor);

        mIndicatorSelectedPaint.setAntiAlias(true);
        int selectedElementColor;
        if (indicatorOptions.getSelectedElementColor() != NO_COLOR) {
            selectedElementColor = indicatorOptions.getSelectedElementColor();
        } else {
            selectedElementColor = TutorialPageIndicator.DEFAULT_SELECTED_ELEMENT_COLOR;
        }
        mIndicatorSelectedPaint.setColor(selectedElementColor);

        if (indicatorOptions.getElementSize() != NO_VALUE) {
            mIndicatorElementSize = indicatorOptions.getElementSize();
        }

        if (indicatorOptions.getElementSpacing() != NO_VALUE) {
            mIndicatorElementSpacing = indicatorOptions.getElementSpacing();
        }

        if (indicatorOptions.getRenderer() != null) {
            mRenderer = indicatorOptions.getRenderer();
        } else {
            mRenderer = Renderer.Factory.newCircleRenderer();
        }

        mPagesCount = pagesCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int halfSize = (int) (mIndicatorElementSize / 2f);
        int properHeight = (int) (mIndicatorElementSize + halfSize + mIndicatorElementSpacing * 2);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(height, properHeight);
        } else {
            height = properHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            updateClipBounds();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = mPagesCount;
        if (count == 0) {
            return;
        }
        float cx = canvas.getWidth() >> 1;
        float cy = canvas.getHeight() >> 1;
        int totalCount = 2 * count - 1;
        int halfCount = totalCount / 2;
        float itemWithOffset = mIndicatorElementSize + mIndicatorElementSpacing;
        float halfItemWithOffset = mIndicatorElementSize / 2 + mIndicatorElementSpacing;
        float halfSize = mIndicatorElementSize / 2f;
        float halfSpacing = mIndicatorElementSpacing / 2f;
        float startX = cx - halfCount * (mIndicatorElementSize + mIndicatorElementSpacing);
        float top = cy - halfSize;
        float bottom = cy + halfSize;
        // if we have odd elements - need to set indicators in center
        if (totalCount % 2 != 0) {
            startX -= halfSize + halfSpacing;
        }

        int i;
        for (i = 0; i < totalCount; i++) {
            // skip empty spaces
            if (i % 2 != 0) {
                continue;
            }
            float left = startX + (mIndicatorElementSize + mIndicatorElementSpacing) * i;
            float right = left + mIndicatorElementSize;
            mElementBounds.set(left, top, right, bottom);
            mRenderer.draw(canvas, mElementBounds, mIndicatorPaint, false);
        }

        // multiply on 2 because there are spaces between elements
        float activeItemOffset = (mSelectedPosition + mScrolledOffset) * 2;
        float left = startX + (mIndicatorElementSize + mIndicatorElementSpacing) * activeItemOffset;
        float right = left + mIndicatorElementSize;
        mElementBounds.set(left, top, right, bottom);

        canvas.clipRect(mClipBounds);

        canvas.save();
        canvas.rotate(ANGLE_360 * mScrolledOffset, mElementBounds.centerX(), mElementBounds.centerY());
        mRenderer.draw(canvas, mElementBounds, mIndicatorSelectedPaint, true);
        canvas.restore();

        if (mIsInfiniteScroll && mSelectedPosition == mPagesCount - 1) { // isInfinite && isEnd
            activeItemOffset = (1f - mScrolledOffset) * 2;
            left = mClipBounds.left - itemWithOffset * activeItemOffset + halfItemWithOffset;
            right = left + mIndicatorElementSize;
            mElementBounds.set(left, top, right, bottom);
            canvas.save();
            canvas.rotate(ANGLE_360 * mScrolledOffset, mElementBounds.centerX(), mElementBounds.centerY());
            mRenderer.draw(canvas, mElementBounds, mIndicatorSelectedPaint, true);
            canvas.restore();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, boolean isInfiniteScroll) {
        //Log.e("ViewPagerIndicator", "onPageScrolled: " + position);
        mSelectedPosition = position;
        mScrolledOffset = positionOffset;
        mIsInfiniteScroll = isInfiniteScroll;
        invalidate();
    }

    void setPagesCount(int pagesCount) {
        mPagesCount = pagesCount;
        updateClipBounds();
    }

    void updateClipBounds() {
        float cx = getWidth() >> 1;
        float cy = getHeight() >> 1;
        int totalCount = 2 * mPagesCount - 1;
        int halfCount = totalCount / 2;
        float halfSize = mIndicatorElementSize / 2f;
        float halfSpacing = mIndicatorElementSpacing / 2f;
        float startX = cx - halfCount * (mIndicatorElementSize + mIndicatorElementSpacing);
        float top = cy - halfSize - halfSpacing;
        float bottom = cy + halfSize + halfSpacing;
        // if we have odd elements - need to set indicators in center
        if (totalCount % 2 != 0) {
            startX -= halfSize + halfSpacing;
        }
        float itemWithOffset = mIndicatorElementSize + mIndicatorElementSpacing;
        float halfItemWithOffset = mIndicatorElementSize / 2f + mIndicatorElementSpacing;
        float left = startX - halfItemWithOffset;
        // multiply on 2 because we have offset at startX by 'halfItemWithOffset'
        float right = left + itemWithOffset * (totalCount - 1) + mIndicatorElementSize + halfItemWithOffset * 2;
        mClipBounds.set(left, top - halfSize, right, bottom + halfSize);
    }

}