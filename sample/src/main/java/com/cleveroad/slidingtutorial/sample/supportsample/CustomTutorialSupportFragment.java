package com.cleveroad.slidingtutorial.sample.supportsample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cleveroad.slidingtutorial.IndicatorOptions;
import com.cleveroad.slidingtutorial.OnTutorialPageChangeListener;
import com.cleveroad.slidingtutorial.TutorialOptions;
import com.cleveroad.slidingtutorial.TutorialPageOptionsProvider;
import com.cleveroad.slidingtutorial.TutorialPageProvider;
import com.cleveroad.slidingtutorial.TutorialSupportFragment;
import com.cleveroad.slidingtutorial.sample.R;
import com.cleveroad.slidingtutorial.sample.renderer.RhombusRenderer;

import static com.cleveroad.slidingtutorial.sample.supportsample.SampleSupportActivity.KEY_ROLLBACK;

public class CustomTutorialSupportFragment extends TutorialSupportFragment
        implements OnTutorialPageChangeListener {

    private static final String TAG = "CustomTutorialSFragment";
    private static final int TOTAL_PAGES = 6;

    private final View.OnClickListener mOnSkipClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Skip button clicked", Toast.LENGTH_SHORT).show();
        }
    };

    private final TutorialPageOptionsProvider mTutorialPageOptionsProvider = new CustomTutorialOptionsProvider();
    private final TutorialPageProvider<Fragment> mTutorialPageProvider = new CustomTutorialPageProvider();

    private int[] pagesColors;
    private boolean noRollback;

    public static CustomTutorialSupportFragment newInstance(boolean noRollback) {
        Bundle args = new Bundle();
        args.putBoolean(KEY_ROLLBACK, noRollback);
        CustomTutorialSupportFragment fragment = new CustomTutorialSupportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) noRollback = args.getBoolean(KEY_ROLLBACK, false);

        if (pagesColors == null) {
            Context requireContext = requireContext();
            pagesColors = new int[]{
                    ContextCompat.getColor(requireContext, android.R.color.holo_orange_dark),
                    ContextCompat.getColor(requireContext, android.R.color.holo_green_dark),
                    ContextCompat.getColor(requireContext, android.R.color.holo_blue_dark),
                    ContextCompat.getColor(requireContext, android.R.color.holo_red_dark),
                    ContextCompat.getColor(requireContext, android.R.color.holo_purple),
                    ContextCompat.getColor(requireContext, android.R.color.holo_blue_bright)
            };
        }
        addOnTutorialPageChangeListener(this);
    }

    @Override
    protected TutorialOptions provideTutorialOptions() {
        return newTutorialOptionsBuilder(requireContext())
                .setUseAutoRemoveTutorialFragment(true)
                .setUseInfiniteScroll(false)
                .setNoRollBack(noRollback)
                .setPagesColors(pagesColors)
                .setPagesCount(TOTAL_PAGES)
                .setIndicatorOptions(IndicatorOptions.newBuilder(requireContext())
                        .setElementSizeRes(R.dimen.indicator_size)
                        .setElementSpacingRes(R.dimen.indicator_spacing)
                        .setElementColorRes(android.R.color.darker_gray)
                        .setSelectedElementColor(Color.LTGRAY)
                        .setRenderer(RhombusRenderer.create())
                        .build())
                .setOnSkipClickListener(mOnSkipClickListener)
//                .setTutorialPageProvider(mTutorialPageOptionsProvider)
                .setTutorialPageProvider(mTutorialPageProvider)
                .build();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.custom_tutorial_layout;
    }

    @Override
    public void onPageChanged(int position) {
        Log.i(TAG, "onPageChanged: position = " + position);
        if (position == TutorialSupportFragment.EMPTY_FRAGMENT_POSITION) {
            Log.i(TAG, "onPageChanged: Empty fragment is visible");
        }
    }
}