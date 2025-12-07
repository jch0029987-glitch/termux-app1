package com.termux.shared.termux.settings.preferences;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.termux.shared.android.PackageUtils;
import com.termux.shared.settings.preferences.AppSharedPreferences;
import com.termux.shared.settings.preferences.SharedPreferenceUtils;
import com.termux.shared.termux.LinuxLatorConstants;
import com.termux.shared.termux.LinuxLatorUtils;
import com.termux.shared.termux.settings.preferences.LinuxLatorPreferenceConstants.TERMUX_TASKER_APP;
import com.termux.shared.logger.Logger;

public class LinuxLatorTaskerAppSharedPreferences extends AppSharedPreferences {

    private static final String LOG_TAG = "LinuxLatorTaskerAppSharedPreferences";

    private  LinuxLatorTaskerAppSharedPreferences(@NonNull Context context) {
        super(context,
            SharedPreferenceUtils.getPrivateSharedPreferences(context,
                LinuxLatorConstants.TERMUX_TASKER_DEFAULT_PREFERENCES_FILE_BASENAME_WITHOUT_EXTENSION),
            SharedPreferenceUtils.getPrivateAndMultiProcessSharedPreferences(context,
                LinuxLatorConstants.TERMUX_TASKER_DEFAULT_PREFERENCES_FILE_BASENAME_WITHOUT_EXTENSION));
    }

    /**
     * Get {@link LinuxLatorTaskerAppSharedPreferences}.
     *
     * @param context The {@link Context} to use to get the {@link Context} of the
     *                {@link LinuxLatorConstants#TERMUX_TASKER_PACKAGE_NAME}.
     * @return Returns the {@link LinuxLatorTaskerAppSharedPreferences}. This will {@code null} if an exception is raised.
     */
    @Nullable
    public static LinuxLatorTaskerAppSharedPreferences build(@NonNull final Context context) {
        Context termuxTaskerPackageContext = PackageUtils.getContextForPackage(context, LinuxLatorConstants.TERMUX_TASKER_PACKAGE_NAME);
        if (termuxTaskerPackageContext == null)
            return null;
        else
            return new LinuxLatorTaskerAppSharedPreferences(termuxTaskerPackageContext);
    }

    /**
     * Get {@link LinuxLatorTaskerAppSharedPreferences}.
     *
     * @param context The {@link Context} to use to get the {@link Context} of the
     *                {@link LinuxLatorConstants#TERMUX_TASKER_PACKAGE_NAME}.
     * @param exitAppOnError If {@code true} and failed to get package context, then a dialog will
     *                       be shown which when dismissed will exit the app.
     * @return Returns the {@link LinuxLatorTaskerAppSharedPreferences}. This will {@code null} if an exception is raised.
     */
    public static  LinuxLatorTaskerAppSharedPreferences build(@NonNull final Context context, final boolean exitAppOnError) {
        Context termuxTaskerPackageContext = LinuxLatorUtils.getContextForPackageOrExitApp(context, LinuxLatorConstants.TERMUX_TASKER_PACKAGE_NAME, exitAppOnError);
        if (termuxTaskerPackageContext == null)
            return null;
        else
            return new LinuxLatorTaskerAppSharedPreferences(termuxTaskerPackageContext);
    }



    public int getLogLevel(boolean readFromFile) {
        if (readFromFile)
            return SharedPreferenceUtils.getInt(mMultiProcessSharedPreferences, TERMUX_TASKER_APP.KEY_LOG_LEVEL, Logger.DEFAULT_LOG_LEVEL);
        else
            return SharedPreferenceUtils.getInt(mSharedPreferences, TERMUX_TASKER_APP.KEY_LOG_LEVEL, Logger.DEFAULT_LOG_LEVEL);
    }

    public void setLogLevel(Context context, int logLevel, boolean commitToFile) {
        logLevel = Logger.setLogLevel(context, logLevel);
        SharedPreferenceUtils.setInt(mSharedPreferences, TERMUX_TASKER_APP.KEY_LOG_LEVEL, logLevel, commitToFile);
    }



    public int getLastPendingIntentRequestCode() {
        return SharedPreferenceUtils.getInt(mSharedPreferences, TERMUX_TASKER_APP.KEY_LAST_PENDING_INTENT_REQUEST_CODE, TERMUX_TASKER_APP.DEFAULT_VALUE_KEY_LAST_PENDING_INTENT_REQUEST_CODE);
    }

    public void setLastPendingIntentRequestCode(int lastPendingIntentRequestCode) {
        SharedPreferenceUtils.setInt(mSharedPreferences, TERMUX_TASKER_APP.KEY_LAST_PENDING_INTENT_REQUEST_CODE, lastPendingIntentRequestCode, false);
    }

}
