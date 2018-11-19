package com.cleveroad.slidingtutorial.sample.sample;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cleveroad.slidingtutorial.IndicatorOptions;
import com.cleveroad.slidingtutorial.OnTutorialPageChangeListener;
import com.cleveroad.slidingtutorial.TutorialFragment;
import com.cleveroad.slidingtutorial.TutorialOptions;
import com.cleveroad.slidingtutorial.TutorialPageOptionsProvider;
import com.cleveroad.slidingtutorial.TutorialPageProvider;
import com.cleveroad.slidingtutorial.TutorialSupportFragment;
import com.cleveroad.slidingtutorial.sample.R;
import com.cleveroad.slidingtutorial.sample.renderer.DrawableRenderer;

public class CustomTutorialFragment extends TutorialFragment
        implements OnTutorialPageChangeListener {

    private static final String TAG = "CustomTutorialFragment";
    private static final int TOTAL_PAGES = 6;

    private final View.OnClickListener mOnSkipClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Skip button clicked", Toast.LENGTH_SHORT).show();
        }
    };

    private final TutorialPageOptionsProvider mTutorialPageOptionsProvider = new CustomTutorialOptionsProvider();
    private final TutorialPageProvider<Fragment> mTutorialPageProvider = new CustomTutorialPageProvider();

    private int[] pagesColors;

    public static CustomTutorialFragment newInstance() {
        Bundle args = new Bundle();
        CustomTutorialFragment fragment = new CustomTutorialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (pagesColors == null) {
            Activity activity = getActivity();
            pagesColors = new int[]{
                    ContextCompat.getColor(activity, android.R.color.darker_gray),
                    ContextCompat.getColor(activity, android.R.color.holo_green_dark),
                    ContextCompat.getColor(activity, android.R.color.holo_red_dark),
                    ContextCompat.getColor(activity, android.R.color.holo_blue_dark),
                    ContextCompat.getColor(activity, android.R.color.holo_purple),
                    ContextCompat.getColor(activity, android.R.color.holo_orange_dark),
            };
        }
        addOnTutorialPageChangeListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.custom_tutorial_layout_ids_example;
    }

    @Override
    protected int getIndicatorResId() {
        return R.id.indicatorCustom;
    }

    @Override
    protected int getSeparatorResId() {
        return R.id.separatorCustom;
    }

    @Override
    protected int getButtonSkipResId() {
        return R.id.tvSkipCustom;
    }

    @Override
    protected int getViewPagerResId() {
        return R.id.viewPagerCustom;
    }

    @Override
    protected TutorialOptions provideTutorialOptions() {
        return newTutorialOptionsBuilder(getActivity())
                .setUseAutoRemoveTutorialFragment(true)
                .setUseInfiniteScroll(true)
                .setPagesColors(pagesColors)
                .setPagesCount(TOTAL_PAGES)
                .setIndicatorOptions(IndicatorOptions.newBuilder(getActivity())
                        .setElementSizeRes(R.dimen.indicator_size)
                        .setElementSpacingRes(R.dimen.indicator_spacing)
                        .setElementColorRes(android.R.color.darker_gray)
                        .setSelectedElementColor(Color.LTGRAY)
                        .setRenderer(DrawableRenderer.create(getActivity()))
                        .build())
                //.setTutorialPageProvider(mTutorialPageOptionsProvider)
                .setTutorialPageProvider(mTutorialPageProvider)
                .setOnSkipClickListener(mOnSkipClickListener)
                .build();
    }

    @Override
    public void onPageChanged(int position) {
        Log.i(TAG, "onPageChanged: position = " + position);
        if (position == TutorialSupportFragment.EMPTY_FRAGMENT_POSITION) {
            Log.i(TAG, "onPageChanged: Empty fragment is visible");
        }
    }
}
