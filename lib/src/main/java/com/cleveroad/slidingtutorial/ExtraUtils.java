package com.cleveroad.slidingtutorial;

import android.support.annotation.NonNull;

class ExtraUtils {

    private static final String PACKAGE_NAME = BuildConfig.APPLICATION_ID;
    private static final String EXTRA_PREFIX = PACKAGE_NAME + "." + "EXTRA_";

    static String getExtra(@NonNull String extraName) {
        return EXTRA_PREFIX + extraName;
    }

}
