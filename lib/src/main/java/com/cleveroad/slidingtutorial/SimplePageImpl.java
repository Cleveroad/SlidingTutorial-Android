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

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

class SimplePageImpl {

    private static final String EXTRA_PAGE_LAYOUT_RES = ExtraUtils.getExtra("PAGE_LAYOUT_RES");
    private static final String EXTRA_TRANSFORM_ITEMS = ExtraUtils.getExtra("TRANSFORM_ITEMS");

    static Bundle getArguments(@LayoutRes int pageLayoutRes, @NonNull TransformItem[] transformItems) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_PAGE_LAYOUT_RES, pageLayoutRes);
        args.putParcelableArray(EXTRA_TRANSFORM_ITEMS, ValidationUtil.checkNotNull(transformItems));
        return args;
    }

    private PageImpl.InternalFragment mInternalFragment;

    SimplePageImpl(@NonNull PageImpl.InternalFragment internalFragment) {
        mInternalFragment = internalFragment;
    }

    int getLayoutResId() {
        Bundle args = mInternalFragment.getArguments();
        if (args != null) {
            if (args.containsKey(EXTRA_PAGE_LAYOUT_RES)) {
                return args.getInt(EXTRA_PAGE_LAYOUT_RES);
            }
        }
        throw new IllegalArgumentException("Page layout resource id is not specified.");
    }

    @NonNull
    TransformItem[] getTransformItems() {
        TransformItem transformItems[] = null;
        Bundle args = mInternalFragment.getArguments();
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
