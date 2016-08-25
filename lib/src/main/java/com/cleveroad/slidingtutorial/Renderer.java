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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

/**
 * Class responds for drawing indicator
 */
public interface Renderer {

    /**
     * Draw single indicator on {@link Canvas}.
     *
     * @param canvas   canvas for drawing
     * @param paint    paint for drawing
     * @param isActive is current indicator item activated
     */
    void draw(@NonNull Canvas canvas, @NonNull RectF elementBounds, @NonNull Paint paint, boolean isActive);

    class Factory {

        /**
         * Provide {@link Renderer} implementation for drawing circle shape indicators.
         * Used by default in {@link IndicatorOptions#provideDefault(Context)}.
         *
         * @return {@link Renderer} implementation.
         */
        public static Renderer newCircleRenderer() {
            return new Renderer() {
                @Override
                public void draw(@NonNull Canvas canvas, @NonNull RectF elementBounds, @NonNull Paint paint, boolean isActive) {
                    float radius = Math.min(elementBounds.width(), elementBounds.height());
                    radius /= 2f;
                    canvas.drawCircle(elementBounds.centerX(), elementBounds.centerY(), radius, paint);
                }
            };
        }

        /**
         * Provide {@link Renderer} implementation for drawing square shape indicators.
         *
         * @return {@link Renderer} implementation.
         */
        public static Renderer newSquareRenderer() {
            return new Renderer() {
                @Override
                public void draw(@NonNull Canvas canvas, @NonNull RectF elementBounds, @NonNull Paint paint, boolean isActive) {
                    canvas.drawRect(elementBounds, paint);
                }
            };
        }

    }
}
