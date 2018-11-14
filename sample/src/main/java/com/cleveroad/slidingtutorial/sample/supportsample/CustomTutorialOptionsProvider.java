package com.cleveroad.slidingtutorial.sample.supportsample;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.cleveroad.slidingtutorial.Direction;
import com.cleveroad.slidingtutorial.PageOptions;
import com.cleveroad.slidingtutorial.TransformItem;
import com.cleveroad.slidingtutorial.TutorialPageOptionsProvider;
import com.cleveroad.slidingtutorial.sample.R;

public class CustomTutorialOptionsProvider implements TutorialPageOptionsProvider {

    private static final int ACTUAL_PAGES_COUNT = 3;

    @NonNull
    @Override
    public PageOptions provide(int position) {
        @LayoutRes int pageLayoutResId;
        TransformItem[] tutorialItems;

        position %= ACTUAL_PAGES_COUNT;
        switch (position) {
            case 0: {
                pageLayoutResId = R.layout.fragment_page_first;
                tutorialItems = new TransformItem[]{
                        TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.2f),
                        TransformItem.create(R.id.ivSecondImage, Direction.RIGHT_TO_LEFT, 0.06f),
                        TransformItem.create(R.id.ivThirdImage, Direction.LEFT_TO_RIGHT, 0.08f),
                        TransformItem.create(R.id.ivFourthImage, Direction.RIGHT_TO_LEFT, 0.1f),
                        TransformItem.create(R.id.ivFifthImage, Direction.RIGHT_TO_LEFT, 0.03f),
                        TransformItem.create(R.id.ivSixthImage, Direction.RIGHT_TO_LEFT, 0.09f),
                        TransformItem.create(R.id.ivSeventhImage, Direction.RIGHT_TO_LEFT, 0.14f),
                        TransformItem.create(R.id.ivEighthImage, Direction.RIGHT_TO_LEFT, 0.07f)
                };
                break;
            }
            case 1: {
                pageLayoutResId = R.layout.fragment_page_second;
                tutorialItems = new TransformItem[]{
                        TransformItem.create(R.id.ivFirstImage, Direction.RIGHT_TO_LEFT, 0.2f),
                        TransformItem.create(R.id.ivSecondImage, Direction.LEFT_TO_RIGHT, 0.06f),
                        TransformItem.create(R.id.ivThirdImage, Direction.RIGHT_TO_LEFT, 0.08f),
                        TransformItem.create(R.id.ivFourthImage, Direction.LEFT_TO_RIGHT, 0.1f),
                        TransformItem.create(R.id.ivFifthImage, Direction.LEFT_TO_RIGHT, 0.03f),
                        TransformItem.create(R.id.ivSixthImage, Direction.LEFT_TO_RIGHT, 0.09f),
                        TransformItem.create(R.id.ivSeventhImage, Direction.LEFT_TO_RIGHT, 0.14f),
                        TransformItem.create(R.id.ivEighthImage, Direction.LEFT_TO_RIGHT, 0.07f)
                };
                break;
            }
            case 2: {
                pageLayoutResId = R.layout.fragment_page_third;
                tutorialItems = new TransformItem[]{
                        TransformItem.create(R.id.ivFirstImage, Direction.RIGHT_TO_LEFT, 0.2f),
                        TransformItem.create(R.id.ivSecondImage, Direction.LEFT_TO_RIGHT, 0.06f),
                        TransformItem.create(R.id.ivThirdImage, Direction.RIGHT_TO_LEFT, 0.08f),
                        TransformItem.create(R.id.ivFourthImage, Direction.LEFT_TO_RIGHT, 0.1f),
                        TransformItem.create(R.id.ivFifthImage, Direction.LEFT_TO_RIGHT, 0.03f),
                        TransformItem.create(R.id.ivSixthImage, Direction.LEFT_TO_RIGHT, 0.09f),
                        TransformItem.create(R.id.ivSeventhImage, Direction.LEFT_TO_RIGHT, 0.14f)
                };
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown position: " + position);
            }
        }

        return PageOptions.create(pageLayoutResId, position, tutorialItems);
    }
}
