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
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressWarnings({"UnusedParameters"})
class PageImpl {

    private InternalFragment mInternalFragment;

    @LayoutRes
    private int mLayoutResId;
    private TransformItem[] mTransformItems;

    PageImpl(@NonNull InternalFragment internalFragment) {
        mInternalFragment = internalFragment;
    }

    @Nullable
    View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutResId = mInternalFragment.getLayoutResId();
        mTransformItems = mInternalFragment.getTransformItems();

        if (mLayoutResId == 0 || mTransformItems == null || mTransformItems.length == 0) {
            throw new IllegalArgumentException("Page layout id or transform items not specified");
        }

        View view = inflater.inflate(mLayoutResId, container, false);
        view.setTag(R.id.st_page_fragment, this);

        for (TransformItem transformItem : mTransformItems) {
            View transformView = view.findViewById(transformItem.getViewResId());
            if (transformView == null) {
                throw new IllegalArgumentException("View by TransformItem#getViewResId() not found.");
            }
            transformItem.setView(transformView);
        }

        return view;
    }

    void onDestroyView() {
        for (TransformItem transformItem : mTransformItems) {
            transformItem.setView(null);
        }
    }

    /**
     * Method that apply a custom transformation to the page views
     *
     * @param pageWidth pageWidth
     * @param position  Position of page relative to the current front-and-center
     *                  position of the pager. 0 is front and center. 1 is one full
     *                  page position to the right, and -1 is one page position to the left.
     */
    final void transformPage(int pageWidth, float position) {
        for (TransformItem transformItem : mTransformItems) {
            float translationX = position * pageWidth * transformItem.getShiftCoefficient();
            if (transformItem.getDirection() == Direction.RIGHT_TO_LEFT) {
                translationX = -translationX;
            }
            transformItem.getView().setTranslationX(translationX);
        }
    }

    interface InternalFragment {
        @LayoutRes
        int getLayoutResId();

        TransformItem[] getTransformItems();

        Bundle getArguments();
    }

}
