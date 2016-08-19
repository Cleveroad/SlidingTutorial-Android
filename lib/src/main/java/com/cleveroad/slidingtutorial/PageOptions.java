package com.cleveroad.slidingtutorial;

import android.support.annotation.LayoutRes;

/**
 * Class contains configuration for creating {@link PageFragment} instances.
 */
public class PageOptions {

    @LayoutRes
    private int mPageLayoutResId;

    private int mPosition;

    private TransformItem[] mTransformItems;

    /**
     * Create {@link PageOptions} instance.
     *
     * @param pageLayoutResId layout resource id of page layout
     * @param position        position in view pages
     * @param transformItems  array of {@link TransformItem}
     * @return returns new {@link PageOptions} instance
     */
    public static PageOptions create(@LayoutRes int pageLayoutResId, int position, TransformItem... transformItems) {
        return new PageOptions(pageLayoutResId, position, transformItems);
    }

    private PageOptions(@LayoutRes int pageLayoutResId, int position, TransformItem... transformItems) {
        mPageLayoutResId = pageLayoutResId;
        mPosition = ValidationUtil.checkPosition(position);
        mTransformItems = transformItems;
    }

    int getPageLayoutResId() {
        return mPageLayoutResId;
    }

    int getPosition() {
        return mPosition;
    }

    TransformItem[] getTransformItems() {
        return mTransformItems;
    }

}
