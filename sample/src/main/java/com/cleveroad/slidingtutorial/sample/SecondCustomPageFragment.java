package com.cleveroad.slidingtutorial.sample;

import android.support.annotation.NonNull;

import com.cleveroad.slidingtutorial.Direction;
import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.TransformItem;

public class SecondCustomPageFragment extends PageFragment {

	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_page_second;
	}

	@NonNull
    @Override
	protected TransformItem[] getTransformItems() {
		return new TransformItem[]{
				TransformItem.create(R.id.ivFirstImage, Direction.RIGHT_TO_LEFT, 20/100f),
				TransformItem.create(R.id.ivSecondImage, Direction.LEFT_TO_RIGHT, 6/100f),
				TransformItem.create(R.id.ivThirdImage, Direction.RIGHT_TO_LEFT, 8/100f),
				TransformItem.create(R.id.ivFourthImage, Direction.LEFT_TO_RIGHT, 10/100f),
				TransformItem.create(R.id.ivFifthImage, Direction.LEFT_TO_RIGHT, 3/100f),
				TransformItem.create(R.id.ivSixthImage, Direction.LEFT_TO_RIGHT, 9/100f),
				TransformItem.create(R.id.ivSeventhImage, Direction.LEFT_TO_RIGHT, 14/100f),
				TransformItem.create(R.id.ivEighthImage, Direction.LEFT_TO_RIGHT, 7/100f)
		};
	}
}
