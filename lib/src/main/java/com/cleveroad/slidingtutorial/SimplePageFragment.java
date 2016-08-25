package com.cleveroad.slidingtutorial;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.cleveroad.slidingtutorial.ValidationUtil.checkNotNull;

/**
 * Basic implementation of {@link PageFragment}.
 */
public class SimplePageFragment extends PageFragment {

    private static final String EXTRA_PAGE_LAYOUT_RES = ExtraUtils.getExtra("PAGE_LAYOUT_RES");
    private static final String EXTRA_TRANSFORM_ITEMS = ExtraUtils.getExtra("TRANSFORM_ITEMS");

    @LayoutRes
    private int pageLayoutResId = 0;
    private TransformItem[] transformItems;

    public static PageFragment newInstance(@NonNull PageOptions pageOptions) {
        return newInstance(pageOptions.getPageLayoutResId(), checkNotNull(pageOptions.getTransformItems()));
    }

    public static PageFragment newInstance(@LayoutRes int pageLayoutRes, @NonNull TransformItem[] transformItems) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_PAGE_LAYOUT_RES, pageLayoutRes);
        args.putParcelableArray(EXTRA_TRANSFORM_ITEMS, checkNotNull(transformItems));
        PageFragment fragment = new SimplePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SimplePageFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(EXTRA_PAGE_LAYOUT_RES)) {
                pageLayoutResId = args.getInt(EXTRA_PAGE_LAYOUT_RES);
            }
            if (args.containsKey(EXTRA_TRANSFORM_ITEMS)) {
                transformItems = ParcelableUtils.getParcelableArray(args, EXTRA_TRANSFORM_ITEMS,
                        TransformItem.class, TransformItem[].class);
            }
        } else {
            pageLayoutResId = getLayoutResId();
            transformItems = getTransformItems();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (pageLayoutResId == 0 || transformItems == null || transformItems.length == 0) {
            throw new IllegalArgumentException("Page layout id or transform items not specified");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return pageLayoutResId;
    }

    @NonNull
    @Override
    protected TransformItem[] getTransformItems() {
        return transformItems;
    }
}
