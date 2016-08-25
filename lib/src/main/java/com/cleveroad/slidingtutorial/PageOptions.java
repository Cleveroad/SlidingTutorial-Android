/*
 *   The MIT License (MIT)
 *
 *   Copyright (c) 2016 Cleveroad
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */
package com.cleveroad.slidingtutorial;

import android.support.annotation.LayoutRes;

/**
 * Class contains configuration for creating {@link PageFragment} and {@link PageSupportFragment} instances.
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
