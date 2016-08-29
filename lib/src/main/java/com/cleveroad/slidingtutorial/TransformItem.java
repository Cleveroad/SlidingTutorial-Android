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

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Class that represents the item that will be moved by associated characteristics like
 * {@link TransformItem#getDirection()} ()} and {@link TransformItem#getShiftCoefficient()}.
 * <p/>
 * Also contains {@link TransformItem#getViewResId()} and {@link TransformItem#getView()} that will be moved;
 */
@SuppressWarnings("WeakerAccess")
public final class TransformItem implements Parcelable {

    @IdRes
    private int mViewResId;
    private Direction mDirection;
    private float mShiftCoefficient;
    private View mView;

    /**
     * Create new {@link TransformItem} instance.
     *
     * @param viewResId        resource view id
     * @param direction        {@link Direction} of translation
     * @param shiftCoefficient speed translation coefficient
     */
    public static TransformItem create(@IdRes int viewResId, @NonNull Direction direction, float shiftCoefficient) {
        return new TransformItem(viewResId, direction, shiftCoefficient);
    }

    private TransformItem(@IdRes int viewResId, Direction direction, float shiftCoefficient) {
        this.mViewResId = viewResId;
        this.mDirection = direction;
        this.mShiftCoefficient = shiftCoefficient;
    }

    int getViewResId() {
        return mViewResId;
    }

    Direction getDirection() {
        return mDirection;
    }

    float getShiftCoefficient() {
        return mShiftCoefficient;
    }

    View getView() {
        return mView;
    }

    void setView(View view) {
        mView = view;
    }

    protected TransformItem(Parcel in) {
        mViewResId = in.readInt();
        mDirection = Direction.valueOf(in.readString());
        mShiftCoefficient = in.readFloat();
    }

    public static final Creator<TransformItem> CREATOR = new Creator<TransformItem>() {
        @Override
        public TransformItem createFromParcel(Parcel in) {
            return new TransformItem(in);
        }

        @Override
        public TransformItem[] newArray(int size) {
            return new TransformItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mViewResId);
        dest.writeString(mDirection.name());
        dest.writeFloat(mShiftCoefficient);
    }

}
