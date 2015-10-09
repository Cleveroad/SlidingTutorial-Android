package com.cleveroad.slidingtutorial;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
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

    private List<? extends PageFragment> pageFragments;
    private List<Integer> colors = new ArrayList<>();
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    public PresentationPagerFragment() {
    }

    public abstract int getLayoutResId();

    public abstract int getViewPagerResId();

    public abstract int getIndicatorResId();

    public abstract int getButtonSkipResId();

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
        pageFragments = getPageFragments();
        viewPager.setAdapter(new PresentationAdapter(getChildFragmentManager(), pageFragments));

        indicator.setViewPager(new ViewPagerWrapper(getContext(), viewPager));

        viewPager.addOnPageChangeListener(this);
        viewPager.setPageTransformer(true, new FragmentTransformer());

        bSkip.setOnClickListener(this);

        setUpColors();
    }

    /**
     * Set up background colors list.
     */
    private void setUpColors() {
        for (PageFragment fragment : pageFragments) {
            int backgroundColor = fragment.getBackgroundColor(getActivity());

            if (!colors.contains(backgroundColor)) {
                colors.add(backgroundColor);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == getButtonSkipResId()) {
            removeFragmentFromScreen();
        }
    }

    /**
     * According to position and positionOffset changing background color. If last position then
     * change root view alpha.
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position < (viewPager.getAdapter().getCount() - 1) && position < (colors.size() - 1)) {
            viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors.get(position), colors.get(position + 1)));
        } else if (position == pageFragments.size() - 1) {
            viewPager.setBackgroundColor(colors.get(colors.size() - 1));
            if (getView() != null) getView().setAlpha(1 - positionOffset);
        }
    }

    /**
     * When last position will be reached then remove fragment from the screen.
     */
    @Override
    public void onPageSelected(int position) {
        if (position == pageFragments.size()) {
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
    protected abstract List<? extends PageFragment> getPageFragments();


    /**
     * Implementation of {@link FragmentPagerAdapter} that in addition add empty last fragment.
     */
    private class PresentationAdapter extends FragmentPagerAdapter {
        private final Fragment emptyFragment = new Fragment();
        private List<? extends PageFragment> pageFragments;

        public PresentationAdapter(FragmentManager fm, List<? extends PageFragment> pageFragments) {
            super(fm);
            this.pageFragments = pageFragments;
        }

        @Override
        public Fragment getItem(int position) {
            if (position < pageFragments.size()) {
                return pageFragments.get(position);
            } else if (position == pageFragments.size()) {
                return emptyFragment;
            } else {
                throw new IllegalArgumentException("Position does not found");
            }
        }

        @Override
        public int getCount() {
            return pageFragments.size() + 1;
        }
    }

    /**
     * Implementation of {@link android.support.v4.view.ViewPager.PageTransformer} that dispatch
     * transform page event whenever a visible/attached page is scrolled.
     */
    class FragmentTransformer implements ViewPager.PageTransformer {

        public void transformPage(View view, float position) {
            if (position <= 1 && pageFragments != null) {
                for (PageFragment pageFragment : pageFragments) {
                    if (pageFragment.getRootResId() == view.getId()) {
                        pageFragment.transformPage(view, position);
                        break;
                    }
                }
            }
        }
    }
}