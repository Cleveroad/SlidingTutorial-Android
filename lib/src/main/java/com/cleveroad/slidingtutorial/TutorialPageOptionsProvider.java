package com.cleveroad.slidingtutorial;

import android.support.annotation.NonNull;

/**
 * {@link PageOptions} provider
 */
public interface TutorialPageOptionsProvider {

    /**
     * Provide {@link PageOptions} by position.
     * @param position current page position
     * @return returns {@link PageOptions} instance
     */
    @NonNull
    PageOptions provide(int position);
}
