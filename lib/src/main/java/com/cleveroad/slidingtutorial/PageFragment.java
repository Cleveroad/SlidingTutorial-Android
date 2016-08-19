/*
 *   The MIT License (MIT)
 *
 *   Copyright (c) 2015 Cleveroad
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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.cleveroad.slidingtutorial.ValidationUtil.checkNotNull;

public class PageFragment extends Fragment {

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
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
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
                transformItems = (TransformItem[]) args.getParcelableArray(EXTRA_TRANSFORM_ITEMS);
            }
        } else {
            pageLayoutResId = getLayoutResId();
            transformItems = getTransformItems();
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (pageLayoutResId == 0) {
            throw new IllegalStateException("Page layout resource id not specified.");
        }
        if (transformItems == null) {
            throw new IllegalStateException("Transform items array not specified.");
        }

        View view = inflater.inflate(pageLayoutResId, container, false);
		view.setTag(R.id.st_page_fragment, this);

        for (TransformItem transformItem : transformItems) {
            transformItem.setView(view.findViewById(transformItem.getViewResId()));
        }

		return view;
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
        for (TransformItem transformItem : transformItems) {
            float translationX = position * pageWidth * transformItem.getShiftCoefficient();
            if (transformItem.getDirection() == Direction.RIGHT_TO_LEFT) {
                translationX = - translationX;
            }
            transformItem.getView().setTranslationX(translationX);
        }
    }

    @LayoutRes
    protected int getLayoutResId() {
        return pageLayoutResId;
    }

    @NonNull
    protected TransformItem[] getTransformItems() {
        return transformItems;
    }
}
