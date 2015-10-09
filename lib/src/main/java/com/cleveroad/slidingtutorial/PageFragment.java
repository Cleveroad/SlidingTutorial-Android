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
