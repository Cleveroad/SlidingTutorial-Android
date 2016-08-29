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
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Array;

/**
 * Utils class to work with parcelables.
 */
class ParcelableUtils {

    /**
     * Returns the value associated with the given key, or null if no mapping of the desired type
     * exists for the given key or a null value is explicitly associated with the key.
     *
     * @param bundle     {@link Bundle} instance
     * @param key        key in bundle
     * @param classType  type of element in array
     * @param classArray type of elements array
     * @return array or null
     */
    @Nullable
    @SuppressWarnings("unchecked")
    static <T extends Parcelable> T[] getParcelableArray(@NonNull Bundle bundle, @NonNull String key, Class<T> classType, Class<T[]> classArray) {
        Parcelable[] parcelableArray = bundle.getParcelableArray(key);
        if (classArray.isInstance(parcelableArray)) {
            return (T[]) parcelableArray;
        } else if (parcelableArray != null) {
            int size = parcelableArray.length;
            T[] items = (T[]) Array.newInstance(classType, size);
            int index = 0;
            for (Parcelable parcelable : parcelableArray) {
                if (classType.isInstance(parcelable)) {
                    items[index++] = (T) parcelable;
                }
            }
            return items;
        }

        return null;
    }

}
