package com.cleveroad.slidingtutorial;

import android.support.annotation.NonNull;

/**
 * {@link PageFragment} instance provider
 */
public interface TutorialPageProvider {

    /**
     * Provide {@link PageFragment} by position.
     * @param position current page position
     * @return returns {@link PageFragment} instance
     */
    @NonNull
    PageFragment providePage(int position);
}
