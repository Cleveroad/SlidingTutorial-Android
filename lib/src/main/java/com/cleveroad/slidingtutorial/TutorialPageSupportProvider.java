package com.cleveroad.slidingtutorial;


import android.support.annotation.NonNull;

/**
 * {@link PageSupportFragment} instance provider
 */
public interface TutorialPageSupportProvider {

    /**
     * Provide {@link PageSupportFragment} by position.
     *
     * @param position current page position
     * @return returns {@link PageSupportFragment} instance
     */
    @NonNull
    PageSupportFragment providePage(int position);
}