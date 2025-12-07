package com.termux.shared.termux.settings.properties;

import android.content.Context;

import androidx.annotation.NonNull;

import com.termux.shared.termux.LinuxLatorConstants;

public class LinuxLatorAppSharedProperties extends LinuxLatorSharedProperties {

    private static LinuxLatorAppSharedProperties properties;


    private LinuxLatorAppSharedProperties(@NonNull Context context) {
        super(context, LinuxLatorConstants.TERMUX_APP_NAME,
            LinuxLatorConstants.TERMUX_PROPERTIES_FILE_PATHS_LIST, LinuxLatorPropertyConstants.TERMUX_APP_PROPERTIES_LIST,
            new LinuxLatorSharedProperties.SharedPropertiesParserClient());
    }

    /**
     * Initialize the {@link #properties} and load properties from disk.
     *
     * @param context The {@link Context} for operations.
     * @return Returns the {@link LinuxLatorAppSharedProperties}.
     */
    public static LinuxLatorAppSharedProperties init(@NonNull Context context) {
        if (properties == null)
            properties = new LinuxLatorAppSharedProperties(context);

        return properties;
    }

    /**
     * Get the {@link #properties}.
     *
     * @return Returns the {@link LinuxLatorAppSharedProperties}.
     */
    public static LinuxLatorAppSharedProperties getProperties() {
        return properties;
    }

}
