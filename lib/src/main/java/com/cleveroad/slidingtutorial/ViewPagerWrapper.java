/*
 *   The MIT License (MIT)
 *
 *   Copyright (c) 2015 Cleveroad
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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Wrapper for ViewPager that has overridden {@link PagerAdapter}. In order page indicator
 * be without last page indicator.
 * {@link ViewPagerWrapper.PagerAdapterWrapper} has overridden
 * {@link PagerAdapter#getCount()} method.
 */
class ViewPagerWrapper extends ViewPager {

	private PagerAdapterWrapper pagerAdapter;
	private ViewPager delegate;

	public ViewPagerWrapper(Context context) {
		super(context);
	}

	public ViewPagerWrapper(Context context, ViewPager viewPager) {
		super(context);
		this.delegate = viewPager;
	}

	@Override
	public PagerAdapter getAdapter() {
		if (pagerAdapter == null) {
			pagerAdapter = new PagerAdapterWrapper(delegate.getAdapter());
		}

		return pagerAdapter;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
		delegate.addOnPageChangeListener(listener);
	}

	@Override
	public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
		delegate.addOnPageChangeListener(listener);
	}

	@Override
	public void removeOnPageChangeListener(OnPageChangeListener listener) {
		delegate.removeOnPageChangeListener(listener);
	}

	@Override
	public void clearOnPageChangeListeners() {
		delegate.clearOnPageChangeListeners();
	}

	/**
	 * PagerAdapterWrapper that has overridden {@link PagerAdapter#getCount()} method, in order
	 * to reduce numbers of pages by one.
	 */
	private static class PagerAdapterWrapper extends PagerAdapter {
		private PagerAdapter delegate;

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

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			return delegate.instantiateItem(container, position);
		}

		@SuppressWarnings("deprecation")
		@Override
		public Object instantiateItem(View container, int position) {
			return delegate.instantiateItem(container, position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			delegate.destroyItem(container, position, object);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void destroyItem(View container, int position, Object object) {
			delegate.destroyItem(container, position, object);
		}
	}
}
