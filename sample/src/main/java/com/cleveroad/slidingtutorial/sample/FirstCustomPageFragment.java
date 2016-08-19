package com.cleveroad.slidingtutorial.sample;

import android.support.annotation.NonNull;

import com.cleveroad.slidingtutorial.Direction;
import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.TransformItem;

public class FirstCustomPageFragment extends PageFragment {

	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_page_first;
	}

	@NonNull
    @Override
	protected TransformItem[] getTransformItems() {
		return new TransformItem[]{
				TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 20/100f),
				TransformItem.create(R.id.ivSecondImage, Direction.RIGHT_TO_LEFT, 6/100f),
				TransformItem.create(R.id.ivThirdImage, Direction.LEFT_TO_RIGHT, 8/100f),
				TransformItem.create(R.id.ivFourthImage, Direction.RIGHT_TO_LEFT, 10/100f),
				TransformItem.create(R.id.ivFifthImage, Direction.RIGHT_TO_LEFT, 3/100f),
				TransformItem.create(R.id.ivSixthImage, Direction.RIGHT_TO_LEFT, 9/100f),
				TransformItem.create(R.id.ivSeventhImage, Direction.RIGHT_TO_LEFT, 14/100f),
				TransformItem.create(R.id.ivEighthImage, Direction.RIGHT_TO_LEFT, 7/100f)
		};
	}
}
