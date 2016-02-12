package com.cleveroad.slidingtutorial.sample;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;

import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.PresentationPagerFragment;

public class CustomPresentationPagerFragment extends PresentationPagerFragment {

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_presentation;
	}

	@Override
	public int getViewPagerResId() {
		return R.id.viewPager;
	}

	@Override
	public int getIndicatorResId() {
		return R.id.indicator;
	}

	@Override
	public int getButtonSkipResId() {
		return R.id.tvSkip;
	}

	@Override
	protected int getPagesCount() {
		return 3;
	}

	@Override
	protected PageFragment getPage(int position) {
		if (position == 0)
			return new FirstCustomPageFragment();
		if (position == 1)
			return new SecondCustomPageFragment();
		if (position == 2)
			return new ThirdCustomPageFragment();
		throw new IllegalArgumentException("Unknown position: " + position);
	}

	@ColorInt
	@Override
	protected int getPageColor(int position) {
		if (position == 0)
			return ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark);
		if (position == 1)
			return ContextCompat.getColor(getContext(), android.R.color.holo_green_dark);
		if (position == 2)
			return ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark);
		return Color.TRANSPARENT;
	}

	@Override
	protected boolean isInfiniteScrollEnabled() {
		return true;
	}
}
