package com.cleveroad.slidingtutorial.sample;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.SimplePagerFragment;

public class CustomPresentationPagerFragment extends SimplePagerFragment {

	@Override
	protected int getPagesCount() {
		return 6;
	}

	@Override
	protected PageFragment getPage(int position) {
		position %= 3;
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
		if (position == 3)
			return ContextCompat.getColor(getContext(), android.R.color.holo_red_dark);
		if (position == 4)
			return ContextCompat.getColor(getContext(), android.R.color.holo_purple);
		if (position == 5)
			return ContextCompat.getColor(getContext(), android.R.color.darker_gray);
		return Color.TRANSPARENT;
	}

	@Override
	protected boolean isInfiniteScrollEnabled() {
		return true;
	}

	@Override
	protected boolean onSkipButtonClicked(View skipButton) {
		Toast.makeText(getContext(), "Skip button clicked", Toast.LENGTH_SHORT).show();
		return true;
	}
}
