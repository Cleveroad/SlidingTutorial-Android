package com.cleveroad.slidingtutorial.sample.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

@SuppressWarnings("unused")
public class DrawableUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Bitmap getVectorDrawableBitmap(@NonNull VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    /**
     * Get bitmap from drawable
     * @param drawable drawable
     * @return {@link Bitmap} from drawable
     */
    public static Bitmap getBitmapByDrawable(@NonNull Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable) {
            return getVectorDrawableBitmap((VectorDrawable) drawable);
        } else {
            throw new IllegalArgumentException("Unsupported drawable type");
        }
    }

    /**
     * Get bitmap from drawable
     * @param context {@link Context} instance
     * @param drawableRes drawable resource id
     * @return {@link Bitmap} from drawable
     */
    public static Bitmap getBitmapByDrawable(@NonNull Context context, @DrawableRes int drawableRes) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableRes);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable) {
            return getVectorDrawableBitmap((VectorDrawable) drawable);
        } else {
            throw new IllegalArgumentException("Unsupported drawable type");
        }
    }

}
