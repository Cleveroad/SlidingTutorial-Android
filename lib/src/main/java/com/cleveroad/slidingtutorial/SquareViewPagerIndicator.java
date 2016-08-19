package com.cleveroad.slidingtutorial;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Implementation of view pager indicator with squares.
 */
public class SquareViewPagerIndicator extends View implements ViewPager.OnPageChangeListener {

    private final RectF rectF = new RectF();
    private PagerAdapter adapter;
    private float scrolledOffset;
    private int selectedPosition;
    private Paint squaresPaint;
    private Paint indicatorPaint;
    private float squareSize;
    private float spacing;
    private int debugCount;


    private final DataSetObserver datasetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            postInvalidate();
        }
    };

    public SquareViewPagerIndicator(Context context) {
        this(context, null);
    }

    public SquareViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        squaresPaint = new Paint();
        squaresPaint.setAntiAlias(true);
        indicatorPaint = new Paint();
        indicatorPaint.setAntiAlias(true);
//        if (attrs != null) {
//            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SquareViewPagerIndicator);
//            try {
//                squaresPaint.setColor(
//                        array.getColor(R.styleable.SquareViewPagerIndicator_trans_squareColor,
//                                ContextCompat.getColor(context, R.color.trans_square_color)));
//                indicatorPaint.setColor(
//                        array.getColor(R.styleable.SquareViewPagerIndicator_trans_squareIndicatorColor,
//                                ContextCompat.getColor(context, R.color.trans_selected_square_color)));
//                squareSize = array.getDimension(R.styleable.SquareViewPagerIndicator_trans_squareSize,
//                        context.getResources().getDimension(R.dimen.trans_square_size));
//                spacing = array.getDimension(R.styleable.SquareViewPagerIndicator_trans_squareSpacing,
//                        context.getResources().getDimension(R.dimen.trans_spacing));
//                debugCount = array.getInteger(R.styleable.SquareViewPagerIndicator_trans_debugItemsCount, 3);
//            } finally {
//                array.recycle();
//            }
//        } else {
//            squaresPaint.setColor(ContextCompat.getColor(context, R.color.trans_square_color));
//            indicatorPaint.setColor(ContextCompat.getColor(context, R.color.trans_selected_square_color));
//            squareSize = context.getResources().getDimension(R.dimen.trans_square_size);
//            spacing = context.getResources().getDimension(R.dimen.trans_spacing);
//            debugCount = 3;
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int properHeight = (int) (squareSize + 2 * spacing);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h;
        if (hMode == MeasureSpec.EXACTLY) {
            h = hSize;
        } else if (hMode == MeasureSpec.AT_MOST) {
            h = Math.min(hSize, properHeight);
        } else {
            h = properHeight;
        }
        setMeasuredDimension(w, h);
    }

    /**
     * Set square size in pixels. Default value: 10dp.
     *
     * @param squareSize square size in pixels
     */
    public void squareSize(float squareSize) {
        this.squareSize = squareSize;
        invalidate();
    }

    /**
     * Set spacing size between squares in pixels. Default value: 2dp.
     *
     * @param spacing spacing size between squares in pixels
     */
    public void spacing(float spacing) {
        this.spacing = spacing;
        invalidate();
    }

    /**
     * Set squares color.
     *
     * @param color squares color
     */
    public void squaresColor(@ColorInt int color) {
        squaresPaint.setColor(color);
        invalidate();
    }

    /**
     * Set indicator color.
     *
     * @param color indicator color.
     */
    public void indicatorColor(@ColorInt int color) {
        indicatorPaint.setColor(color);
        invalidate();
    }

    /**
     * Initialize indicator with view pager.
     *
     * @param viewPager some view pager.
     * @throws IllegalArgumentException if view pager doesn't have adapter
     */
    public void initializeWith(@NonNull ViewPager viewPager) {
        viewPager.addOnPageChangeListener(this);
        adapter = viewPager.getAdapter();
        if (adapter == null)
            throw new IllegalArgumentException("You must set adapter to ViewPager first");
        adapter.registerDataSetObserver(datasetObserver);
    }

    /**
     * Clear all references to view pager's adapter created in {@link #initializeWith(ViewPager)} method.
     */
    public void reset() {
        adapter.unregisterDataSetObserver(datasetObserver);
        adapter = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = adapter == null ? 0 : adapter.getCount();
        if (count == 0) {
            if (isInEditMode()) {
                count = debugCount;
            } else {
                return;
            }
        }
        float cx, cy;
        if (isInEditMode()) {
            cx = getWidth() >> 1;
            cy = getHeight() >> 1;
        } else {
            cx = canvas.getWidth() >> 1;
            cy = canvas.getHeight() >> 1;
        }
        int totalCount = 2 * count - 1;
        int halfCount = totalCount >> 1;
        float halfSize = squareSize / 2;
        float halfSpacing = spacing / 2;
        float startX = cx - halfCount * (squareSize + spacing);
        float top = cy - halfSize;
        float bottom = cy + halfSize;
        if (totalCount % 2 != 0) {
            startX -= halfSize + halfSpacing;
        }

        for (int i = 0; i < totalCount; i++) {
            // skip empty spaces
            if (i % 2 != 0) {
                continue;
            }
            float l = startX + (squareSize + spacing) * i;
            float r = l + squareSize;
            rectF.set(l, top, r, bottom);
            canvas.drawRect(rectF, squaresPaint);
        }
        canvas.save();
        float offset = scrolledOffset * 2;
        int i = selectedPosition * 2;
        float l = startX + (squareSize + spacing) * (i + offset);
        float r = l + squareSize;
        rectF.set(l, top, r, bottom);
        canvas.rotate(180 * scrolledOffset, rectF.centerX(), rectF.centerY());
        // canvas.drawCustomShape(bounds);
        canvas.drawRect(rectF, indicatorPaint);
        canvas.restore();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        selectedPosition = position;
        scrolledOffset = positionOffset;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
