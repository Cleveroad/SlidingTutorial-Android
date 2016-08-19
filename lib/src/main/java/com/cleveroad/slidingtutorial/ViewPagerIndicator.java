package com.cleveroad.slidingtutorial;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

// Remove ViewPager and ViewPagerAdapter
// Create custom listener ViewPageIndicator с аргументами: position, offset, isInfiniteScroll
public final class ViewPagerIndicator extends View implements ViewPager.OnPageChangeListener {

    static final int NO_VALUE = 0;
    static final int DEFAULT_VALUE = Color.TRANSPARENT;

    private RectF mElementBounds;
    private float mScrolledOffset;
    private int mSelectedPosition;
    private float mIndicatorElementSpacing;
    private float mIndicatorElementSize;
    private Paint mIndicatorSelectedPaint;
    private Paint mIndicatorPaint;
    private Renderer mRenderer;

    private TutorialFragment.TutorialAdapter mPagerAdapter;
    private final DataSetObserver mDataSetObservable = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            postInvalidate();
        }
    };

    public ViewPagerIndicator(Context context) {
        super(context);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    void initWith(@NonNull ViewPager viewPager, @NonNull IndicatorOptions indicatorOptions) {
        mElementBounds = new RectF();

        mIndicatorPaint = new Paint();
        mIndicatorPaint.setAntiAlias(true);
        int elementColor;
        if (indicatorOptions.getElementColor() != NO_VALUE) {
            elementColor = indicatorOptions.getElementColor();
        } else {
            elementColor = ContextCompat.getColor(getContext(), android.R.color.transparent);
        }
        mIndicatorPaint.setColor(elementColor);

        mIndicatorSelectedPaint = new Paint();
        mIndicatorSelectedPaint.setAntiAlias(true);
        int selectedElementColor;
        if (indicatorOptions.getSelectedElementColor() != NO_VALUE) {
            selectedElementColor = indicatorOptions.getSelectedElementColor();
        } else {
            selectedElementColor = ContextCompat.getColor(getContext(), android.R.color.transparent);
        }
        mIndicatorSelectedPaint.setColor(selectedElementColor);


        if (indicatorOptions.getElementSize() != NO_VALUE) {
            mIndicatorElementSize = indicatorOptions.getElementSize();
        } else {
            mIndicatorElementSize = DEFAULT_VALUE;
        }

        if (indicatorOptions.getElementSpacing() != NO_VALUE) {
            mIndicatorElementSpacing = indicatorOptions.getElementSpacing();
        } else {
            mIndicatorElementSpacing = DEFAULT_VALUE;
        }

        if (indicatorOptions.getRenderer() != null) {
            mRenderer = indicatorOptions.getRenderer();
        } else {
            mRenderer = Renderer.Factory.provideCircleRenderer();
        }

        viewPager.addOnPageChangeListener(this);
        mPagerAdapter = (TutorialFragment.TutorialAdapter) viewPager.getAdapter();
        if (mPagerAdapter == null) {
            throw new IllegalStateException("ViewPager.getAdapter() can't return null.");
        }
        mPagerAdapter.registerDataSetObserver(mDataSetObservable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int properHeight = (int) (mIndicatorElementSize + 2 * mIndicatorElementSpacing);
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
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = mPagerAdapter == null ? 0 : mPagerAdapter.getRealPagesCount();
        if (count == 0) {
            return;
        }
        float cx = canvas.getWidth() >> 1;
        float cy = canvas.getHeight() >> 1;
        int totalCount = 2 * count - 1;
        int halfCount = totalCount >> 1;
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
        canvas.save();

        // multiply on 2 because there are spaces between elements
        float activeItemOffset = (mSelectedPosition + mScrolledOffset) * 2;
        float left = startX + (mIndicatorElementSize + mIndicatorElementSpacing) * activeItemOffset;
        float right = left + mIndicatorElementSize;
        mElementBounds.set(left, top, right, bottom);
        canvas.rotate(180 * mScrolledOffset, mElementBounds.centerX(), mElementBounds.centerY());
        mRenderer.draw(canvas, mElementBounds, mIndicatorSelectedPaint, true);
        canvas.restore();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mSelectedPosition = position % mPagerAdapter;
        mScrolledOffset = positionOffset;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        /* NOP */
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        /* NOP */
    }
}