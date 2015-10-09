package com.cleveroad.slidingtutorial;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * Class that represents the item that will be moved by associated characteristics like
 * {@link TransformItem#isReverseShift()} and {@link TransformItem#getShiftCoefficient()}.

 * Also contains {@link TransformItem#getViewResId()} and {@link TransformItem#getView()} that will be moved;
 */
public class TransformItem {
    @IdRes
    private int viewResId;
    private boolean reverseShift;
    private int shiftCoefficient;
    private View view;

    public TransformItem(int viewResId, boolean reverseShift, int shiftCoefficient) {
        this.viewResId = viewResId;
        this.reverseShift = reverseShift;
        this.shiftCoefficient = shiftCoefficient;
    }

    public int getViewResId() {
        return viewResId;
    }

    public boolean isReverseShift() {
        return reverseShift;
    }

    public int getShiftCoefficient() {
        return shiftCoefficient;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
