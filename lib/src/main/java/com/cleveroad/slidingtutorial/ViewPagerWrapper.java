package com.cleveroad.slidingtutorial;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Wrapper for ViewPager that has overridden {@link PagerAdapter}. In order page indicator
 * be without last page indicator.
 * {@link ViewPagerWrapper.PagerAdapterWrapper} has overridden
 * {@link PagerAdapter#getCount()} method.
 */
public class ViewPagerWrapper extends ViewPager {
    private ViewPager.OnPageChangeListener listener;
    private PagerAdapter pagerAdapter;
    private ViewPager delegate;

    public ViewPagerWrapper(Context context) {
        super(context);
    }

    public ViewPagerWrapper(Context context, ViewPager viewPager) {
        super(context);
        this.delegate = viewPager;
        delegate.addOnPageChangeListener(new OnPageChangeListenerWrapper());
    }

    @Override
    public PagerAdapter getAdapter() {
        if (pagerAdapter == null) {
            pagerAdapter = new PagerAdapterWrapper(delegate.getAdapter());
        }

        return pagerAdapter;
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.listener = listener;
    }

    private class OnPageChangeListenerWrapper implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int penultimatePosition = getAdapter().getCount() - 1;
            if (position >= penultimatePosition) {
                listener.onPageScrolled(penultimatePosition, 0, 0);
            } else {
                listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            listener.onPageSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            listener.onPageScrollStateChanged(state);
        }
    }

    /**
     * PagerAdapterWrapper that has overridden {@link PagerAdapter#getCount()} method, in order
     * to reduce numbers of pages by one.
     */
    class PagerAdapterWrapper extends PagerAdapter {
        PagerAdapter delegate;

        public PagerAdapterWrapper(PagerAdapter pagerAdapter) {
            this.delegate = pagerAdapter;
        }

        @Override
        public int getCount() {
            return delegate.getCount() - 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return delegate.isViewFromObject(view, object);
        }
    }
}
