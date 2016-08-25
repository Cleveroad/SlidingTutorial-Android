package com.cleveroad.slidingtutorial;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

/**
 * Basic implementation of {@link PageSupportFragment}.
 */
public class SimplePageSupportFragment extends PageSupportFragment {

    private static final String EXTRA_PAGE_LAYOUT_RES = ExtraUtils.getExtra("PAGE_LAYOUT_RES");
    private static final String EXTRA_TRANSFORM_ITEMS = ExtraUtils.getExtra("TRANSFORM_ITEMS");

    public static PageSupportFragment newInstance(@NonNull PageOptions pageOptions) {
        return newInstance(pageOptions.getPageLayoutResId(), ValidationUtil.checkNotNull(pageOptions.getTransformItems()));
    }

    public static PageSupportFragment newInstance(@LayoutRes int pageLayoutRes, @NonNull TransformItem[] transformItems) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_PAGE_LAYOUT_RES, pageLayoutRes);
        args.putParcelableArray(EXTRA_TRANSFORM_ITEMS, ValidationUtil.checkNotNull(transformItems));
        PageSupportFragment fragment = new SimplePageSupportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SimplePageSupportFragment() {
    }

    @Override
    protected int getLayoutResId() {
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(EXTRA_PAGE_LAYOUT_RES)) {
                return args.getInt(EXTRA_PAGE_LAYOUT_RES);
            }
        }
        throw new IllegalArgumentException("Page layout resource id is not specified.");
    }

    @NonNull
    @Override
    protected TransformItem[] getTransformItems() {
        TransformItem transformItems[] = null;
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(EXTRA_TRANSFORM_ITEMS)) {
                transformItems = ParcelableUtils.getParcelableArray(args, EXTRA_TRANSFORM_ITEMS,
                        TransformItem.class, TransformItem[].class);
            }
        }

        if (transformItems == null) {
            throw new IllegalArgumentException("Transform items array is not specified.");
        }

        return transformItems;
    }
}
