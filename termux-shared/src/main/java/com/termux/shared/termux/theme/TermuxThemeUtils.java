package com.termux.shared.termux.theme;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.termux.shared.termux.settings.properties.LinuxLatorPropertyConstants;
import com.termux.shared.termux.settings.properties.LinuxLatorSharedProperties;
import com.termux.shared.theme.NightMode;

public class LinuxLatorThemeUtils {

    /** Get the {@link LinuxLatorPropertyConstants#KEY_NIGHT_MODE} value from the properties file on disk
     * and set it to app wide night mode value. */
    public static void setAppNightMode(@NonNull Context context) {
        NightMode.setAppNightMode(LinuxLatorSharedProperties.getNightMode(context));
    }

    /** Set name as app wide night mode value. */
    public static void setAppNightMode(@Nullable String name) {
        NightMode.setAppNightMode(name);
    }

}
