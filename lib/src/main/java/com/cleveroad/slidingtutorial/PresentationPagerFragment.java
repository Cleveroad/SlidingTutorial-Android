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

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Base Fragment that contains {@link ViewPager} and where happen most logic like dispatching
 * transform event to child fragment, changing background color, and located page indicator witch
 * associated with {@link ViewPagerWrapper}.
 */
public abstract class PresentationPagerFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

	private ViewPager viewPager;
	private View bSkip;
	private CirclePageIndicator indicator;

	private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

	public PresentationPagerFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutResId(), container, false);
		viewPager = (ViewPager) view.findViewById(getViewPagerResId());
		indicator = (CirclePageIndicator) view.findViewById(getIndicatorResId());
		bSkip = view.findViewById(getButtonSkipResId());
		return view;
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		viewPager.setAdapter(new PresentationAdapter(getChildFragmentManager()));
		if (isInfiniteScrollEnabled()) {
			viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
		}
		indicator.setViewPager(new ViewPagerWrapper(getContext(), viewPager), getPagesCount());
		viewPager.addOnPageChangeListener(this);
		viewPager.setPageTransformer(true, new FragmentTransformer());

		bSkip.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == getButtonSkipResId()) {
			if (!onSkipButtonClicked(v)) {
				removeFragmentFromScreen();
			}
		}
	}

	/**
	 * According to position and positionOffset changing background color. If last position then
	 * change root view alpha.
	 */
	@SuppressWarnings("ConstantConditions")
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		if (isInfiniteScrollEnabled() && getPagesCount() != 0) {
			position %= getPagesCount();
		}
		int nextColorPosition = position + 1;
		if (nextColorPosition >= getPagesCount()) {
			nextColorPosition = 0;
		}
		if (position < (viewPager.getAdapter().getCount() - 1)) {
			viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, getPageColor(position), getPageColor(nextColorPosition)));
		} else if (!isInfiniteScrollEnabled() && position == getPagesCount() - 1) {
			viewPager.setBackgroundColor(getPageColor(position));
			if (getView() != null) {
				getView().setAlpha(1 - positionOffset);
			}
		}
	}

	/**
	 * When last position will be reached then remove fragment from the screen.
	 */
	@Override
	public void onPageSelected(int position) {
		if (!isInfiniteScrollEnabled() && position == getPagesCount()) {
			removeFragmentFromScreen();
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	private void removeFragmentFromScreen() {
		getActivity().getSupportFragmentManager()
				.beginTransaction()
				.remove(PresentationPagerFragment.this)
				.commitAllowingStateLoss();
	}

	/**
	 * Obtain custom page fragment list for presentation view pager.
	 *
	 * @return list of custom page fragments.
	 */
	@Deprecated
	protected List<? extends PageFragment> getPageFragments() {
		return Collections.emptyList();
	}

	protected abstract int getLayoutResId();

	protected abstract int getViewPagerResId();

	protected abstract int getIndicatorResId();

	protected abstract int getButtonSkipResId();

	/**
	 * Enable or disable infinite scroll. Default value is false.
	 *
	 * @return true to enable infinite scroll, false otherwise.
	 */
	protected boolean isInfiniteScrollEnabled() {
		return false;
	}

	/**
	 * Get total pages count.
	 *
	 * @return pages count
	 */
	protected abstract int getPagesCount();

	/**
	 * Get specific page by position index.
	 *
	 * @param position index of page
	 * @return page at specified position
	 */
	protected abstract PageFragment getPage(int position);

	/**
	 * Get page color.
	 *
	 * @param position index of page
	 * @return color of page
	 */
	@ColorInt
	protected abstract int getPageColor(int position);

	/**
	 * Called when user pressed Skip button. Default behavior: remove presentation fragment.
	 * @param skipButton skip button
	 * @return true if you consumed click listener and implemented your own behavior, false otherwise
	 */
	protected boolean onSkipButtonClicked(View skipButton) {
		return false;
	}

	/**
	 * Implementation of {@link FragmentPagerAdapter} that in addition add empty last fragment.
	 */
	private class PresentationAdapter extends FragmentPagerAdapter {
		private final Fragment emptyFragment = new Fragment();

		public PresentationAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (isInfiniteScrollEnabled()) {
				position %= getPagesCount();
			}
			if (position < getPagesCount()) {
				return getPage(position);
			} else if (position == getPagesCount()) {
				return emptyFragment;
			} else {
				throw new IllegalArgumentException("Invalid position: " + position);
			}
		}

		@Override
		public int getCount() {
			if (getPagesCount() == 0)
				return 0;
			if (isInfiniteScrollEnabled())
				return Integer.MAX_VALUE;
			return getPagesCount() + 1;
		}
	}

	/**
	 * Implementation of {@link android.support.v4.view.ViewPager.PageTransformer} that dispatch
	 * transform page event whenever a visible/attached page is scrolled.
	 */
	private class FragmentTransformer implements ViewPager.PageTransformer {

		public void transformPage(View view, float position) {
			Object obj = view.getTag(R.id.page_fragment);
			if (obj instanceof PageFragment) {
				((PageFragment) obj).transformPage(view, position);
			}
		}
	}
}