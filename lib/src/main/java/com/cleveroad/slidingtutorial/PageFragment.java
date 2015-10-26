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
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Implementation of {@link android.support.v4.app.Fragment} that contains {@link LayersHolder} and
 * implement create view functionality. Also provide {@link PageFragment#getBackgroundColorResId()}
 * and {@link PageFragment#getRootResId()} for {@link PresentationPagerFragment}
 */
public abstract class PageFragment extends Fragment {

    protected LayersHolder holder;

    protected abstract TransformItem[] provideTransformItems();

    @LayoutRes
    protected abstract int getLayoutResId();

    @IdRes
    public abstract int getRootResId();

    @ColorRes
    protected abstract int getBackgroundColorResId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        holder = provideLayersHolder(view);
        return view;
    }

    public int getBackgroundColor(Context context) {
        return ContextCompat.getColor(context, getBackgroundColorResId());
    }

    protected void transformPage(View view, float position) {
        holder.transformPage(view.getWidth(), position);
    }

    private LayersHolder provideLayersHolder(View view) {
        return new LayersHolder(view, provideTransformItems());
    }
}
