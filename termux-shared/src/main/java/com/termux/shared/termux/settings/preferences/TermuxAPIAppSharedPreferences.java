package com.termux.shared.termux.settings.preferences;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.termux.shared.logger.Logger;
import com.termux.shared.android.PackageUtils;
import com.termux.shared.settings.preferences.AppSharedPreferences;
import com.termux.shared.settings.preferences.SharedPreferenceUtils;
import com.termux.shared.termux.LinuxLatorUtils;
import com.termux.shared.termux.settings.preferences.LinuxLatorPreferenceConstants.TERMUX_API_APP;
import com.termux.shared.termux.LinuxLatorConstants;

public class LinuxLatorAPIAppSharedPreferences extends AppSharedPreferences {

    private static final String LOG_TAG = "LinuxLatorAPIAppSharedPreferences";

    private LinuxLatorAPIAppSharedPreferences(@NonNull Context context) {
        super(context,
            SharedPreferenceUtils.getPrivateSharedPreferences(context,
                LinuxLatorConstants.TERMUX_API_DEFAULT_PREFERENCES_FILE_BASENAME_WITHOUT_EXTENSION),
            SharedPreferenceUtils.getPrivateAndMultiProcessSharedPreferences(context,
                LinuxLatorConstants.TERMUX_API_DEFAULT_PREFERENCES_FILE_BASENAME_WITHOUT_EXTENSION));
    }

    /**
     * Get {@link LinuxLatorAPIAppSharedPreferences}.
     *
     * @param context The {@link Context} to use to get the {@link Context} of the
     *                {@link LinuxLatorConstants#TERMUX_API_PACKAGE_NAME}.
     * @return Returns the {@link LinuxLatorAPIAppSharedPreferences}. This will {@code null} if an exception is raised.
     */
    @Nullable
    public static LinuxLatorAPIAppSharedPreferences build(@NonNull final Context context) {
        Context termuxAPIPackageContext = PackageUtils.getContextForPackage(context, LinuxLatorConstants.TERMUX_API_PACKAGE_NAME);
        if (termuxAPIPackageContext == null)
            return null;
        else
            return new LinuxLatorAPIAppSharedPreferences(termuxAPIPackageContext);
    }

    /**
     * Get {@link LinuxLatorAPIAppSharedPreferences}.
     *
     * @param context The {@link Context} to use to get the {@link Context} of the
     *                {@link LinuxLatorConstants#TERMUX_API_PACKAGE_NAME}.
     * @param exitAppOnError If {@code true} and failed to get package context, then a dialog will
     *                       be shown which when dismissed will exit the app.
     * @return Returns the {@link LinuxLatorAPIAppSharedPreferences}. This will {@code null} if an exception is raised.
     */
    public static LinuxLatorAPIAppSharedPreferences build(@NonNull final Context context, final boolean exitAppOnError) {
        Context termuxAPIPackageContext = LinuxLatorUtils.getContextForPackageOrExitApp(context, LinuxLatorConstants.TERMUX_API_PACKAGE_NAME, exitAppOnError);
        if (termuxAPIPackageContext == null)
            return null;
        else
            return new LinuxLatorAPIAppSharedPreferences(termuxAPIPackageContext);
    }



    public int getLogLevel(boolean readFromFile) {
        if (readFromFile)
            return SharedPreferenceUtils.getInt(mMultiProcessSharedPreferences, TERMUX_API_APP.KEY_LOG_LEVEL, Logger.DEFAULT_LOG_LEVEL);
        else
            return SharedPreferenceUtils.getInt(mSharedPreferences, TERMUX_API_APP.KEY_LOG_LEVEL, Logger.DEFAULT_LOG_LEVEL);
    }

    public void setLogLevel(Context context, int logLevel, boolean commitToFile) {
        logLevel = Logger.setLogLevel(context, logLevel);
        SharedPreferenceUtils.setInt(mSharedPreferences, TERMUX_API_APP.KEY_LOG_LEVEL, logLevel, commitToFile);
    }


    public int getLastPendingIntentRequestCode() {
        return SharedPreferenceUtils.getInt(mSharedPreferences, TERMUX_API_APP.KEY_LAST_PENDING_INTENT_REQUEST_CODE, TERMUX_API_APP.DEFAULT_VALUE_KEY_LAST_PENDING_INTENT_REQUEST_CODE);
    }

    public void setLastPendingIntentRequestCode(int lastPendingIntentRequestCode) {
        SharedPreferenceUtils.setInt(mSharedPreferences, TERMUX_API_APP.KEY_LAST_PENDING_INTENT_REQUEST_CODE, lastPendingIntentRequestCode, true);
    }

}
