package com.cleveroad.slidingtutorial.sample;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.SimplePagerFragment;

public class CustomPresentationPagerFragment extends SimplePagerFragment {

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

	@Override
	protected boolean onSkipButtonClicked() {
		Toast.makeText(getContext(), "Skip button clicked", Toast.LENGTH_SHORT).show();
		return true;
	}
}
