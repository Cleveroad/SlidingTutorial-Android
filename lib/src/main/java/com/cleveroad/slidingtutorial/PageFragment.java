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

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Single page for {@link TutorialFragment}.
 */
public abstract class PageFragment extends Fragment {

    private PageImpl mPage;
    private PageImpl.InternalFragment mInternalFragment = new PageImpl.InternalFragment() {
        @Override
        public int getLayoutResId() {
            return PageFragment.this.getLayoutResId();
        }

        @Override
        public TransformItem[] getTransformItems() {
            return PageFragment.this.getTransformItems();
        }

        @Override
        public Bundle getArguments() {
            return PageFragment.this.getArguments();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = new PageImpl(mInternalFragment);
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mPage.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        mPage.onDestroyView();
        super.onDestroyView();
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    @NonNull
    protected abstract TransformItem[] getTransformItems();
}