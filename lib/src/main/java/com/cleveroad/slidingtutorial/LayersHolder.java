package com.cleveroad.slidingtutorial;

import android.view.View;

/**
 * {@link TransformItem}'s holder, that implement a custom transformation
 * to the page views using animation properties.
 */
public class LayersHolder {
    TransformItem[] transformItems;

    public LayersHolder(View view, TransformItem[] transformItems) {
        this.transformItems = transformItems;

        for (TransformItem transformItem : transformItems) {
            transformItem.setView(view.findViewById(transformItem.getViewResId()));
        }
    }

    /**
     * Method that apply a custom transformation to the page views
     *
     * @param pageWidth pageWidth
     * @param position Position of page relative to the current front-and-center
     *                 position of the pager. 0 is front and center. 1 is one full
     *                 page position to the right, and -1 is one page position to the left.
     */
    public void transformPage(int pageWidth, float position) {
        for (TransformItem transformItem : transformItems) {
            float translationX = (position) * (pageWidth / transformItem.getShiftCoefficient());

            transformItem.getView().setTranslationX(transformItem.isReverseShift() ? -translationX : translationX);
        }
    }
}
