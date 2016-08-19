package com.cleveroad.slidingtutorial;


import android.support.annotation.Nullable;

final class ValidationUtil {

    private ValidationUtil() {
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference    an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *                     string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * Checks on equals setPagesCount to pagesColor array size
     *
     * @param pagesCount  count of pages
     * @param pagesColors array of pages colors
     * @return pagesColor array
     * @throws IllegalArgumentException when setPagesColors array has different from setPagesCount value size
     */
    static int[] checkPagesColorsSize(int pagesCount, int[] pagesColors) {
        checkPagesCount(pagesCount);
        if (pagesColors.length < pagesCount || pagesColors.length > pagesCount) {
            throw new IllegalArgumentException("Pages color array size must be equal to pages count.");
        }
        return pagesColors;
    }

    /**
     * Checks setPagesCount value to be greater that 0
     *
     * @param pagesCount count of pages
     * @return pages count
     * @throws IllegalArgumentException when setPagesColors array has different from setPagesCount value size
     */
    static int checkPagesCount(int pagesCount) {
        if (pagesCount < 0) {
            throw new IllegalArgumentException("Pages count can't be less than 0.");
        }
        return pagesCount;
    }

    /**
     * Checks position value to be greater that 0
     *
     * @param position page position
     * @return position value
     * @throws IllegalArgumentException when position less than 0
     */
    static int checkPosition(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Position can't be less than 0.");
        }
        return position;
    }
}