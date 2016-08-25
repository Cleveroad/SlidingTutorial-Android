package com.cleveroad.slidingtutorial;

/**
 * Callback interface for responding to changing state of the selected {@link PageFragment} page.
 */
public interface OnTutorialPageChangeListener {

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    void onPageChanged(int position);

}
