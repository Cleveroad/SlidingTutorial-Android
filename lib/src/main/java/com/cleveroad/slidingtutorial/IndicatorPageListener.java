package com.cleveroad.slidingtutorial;

interface IndicatorPageListener {

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position         Position index of the first page currently being displayed.
     *                         Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset   Value from [0, 1) indicating the offset from the page at position.
     * @param isInfiniteScroll flag for indicating infinite scroll
     */
    void onPageScrolled(int position, float positionOffset, boolean isInfiniteScroll);
}
