package com.cleveroad.slidingtutorial;

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

        public static Renderer provideCircleRenderer() {
            return new Renderer() {
                @Override
                public void draw(@NonNull Canvas canvas, @NonNull RectF elementBounds, @NonNull Paint paint, boolean isActive) {
                    canvas.drawCircle(elementBounds.centerX(), elementBounds.centerY(), elementBounds.width(), paint);
                }
            };
        }

        public static Renderer provideSquareRenderer() {
            return new Renderer() {
                @Override
                public void draw(@NonNull Canvas canvas, @NonNull RectF elementBounds, @NonNull Paint paint, boolean isActive) {
                    canvas.drawRect(elementBounds, paint);
                }
            };
        }

    }
}
