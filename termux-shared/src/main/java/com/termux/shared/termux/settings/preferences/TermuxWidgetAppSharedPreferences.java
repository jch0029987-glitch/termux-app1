package com.termux.shared.termux.settings.preferences;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.termux.shared.logger.Logger;
import com.termux.shared.android.PackageUtils;
import com.termux.shared.settings.preferences.AppSharedPreferences;
import com.termux.shared.settings.preferences.SharedPreferenceUtils;
import com.termux.shared.termux.LinuxLatorUtils;
import com.termux.shared.termux.settings.preferences.LinuxLatorPreferenceConstants.TERMUX_WIDGET_APP;
import com.termux.shared.termux.LinuxLatorConstants;

import java.util.UUID;

public class LinuxLatorWidgetAppSharedPreferences extends AppSharedPreferences {

    private static final String LOG_TAG = "LinuxLatorWidgetAppSharedPreferences";

    private LinuxLatorWidgetAppSharedPreferences(@NonNull Context context) {
        super(context,
            SharedPreferenceUtils.getPrivateSharedPreferences(context,
                LinuxLatorConstants.TERMUX_WIDGET_DEFAULT_PREFERENCES_FILE_BASENAME_WITHOUT_EXTENSION),
            SharedPreferenceUtils.getPrivateAndMultiProcessSharedPreferences(context,
                LinuxLatorConstants.TERMUX_WIDGET_DEFAULT_PREFERENCES_FILE_BASENAME_WITHOUT_EXTENSION));
    }

    /**
     * Get {@link LinuxLatorWidgetAppSharedPreferences}.
     *
     * @param context The {@link Context} to use to get the {@link Context} of the
     *                {@link LinuxLatorConstants#TERMUX_WIDGET_PACKAGE_NAME}.
     * @return Returns the {@link LinuxLatorWidgetAppSharedPreferences}. This will {@code null} if an exception is raised.
     */
    @Nullable
    public static LinuxLatorWidgetAppSharedPreferences build(@NonNull final Context context) {
        Context termuxWidgetPackageContext = PackageUtils.getContextForPackage(context, LinuxLatorConstants.TERMUX_WIDGET_PACKAGE_NAME);
        if (termuxWidgetPackageContext == null)
            return null;
        else
            return new LinuxLatorWidgetAppSharedPreferences(termuxWidgetPackageContext);
    }

    /**
     * Get the {@link LinuxLatorWidgetAppSharedPreferences}.
     *
     * @param context The {@link Context} to use to get the {@link Context} of the
     *                {@link LinuxLatorConstants#TERMUX_WIDGET_PACKAGE_NAME}.
     * @param exitAppOnError If {@code true} and failed to get package context, then a dialog will
     *                       be shown which when dismissed will exit the app.
     * @return Returns the {@link LinuxLatorWidgetAppSharedPreferences}. This will {@code null} if an exception is raised.
     */
    public static LinuxLatorWidgetAppSharedPreferences build(@NonNull final Context context, final boolean exitAppOnError) {
        Context termuxWidgetPackageContext = LinuxLatorUtils.getContextForPackageOrExitApp(context, LinuxLatorConstants.TERMUX_WIDGET_PACKAGE_NAME, exitAppOnError);
        if (termuxWidgetPackageContext == null)
            return null;
        else
            return new LinuxLatorWidgetAppSharedPreferences(termuxWidgetPackageContext);
    }



    public static String getGeneratedToken(@NonNull Context context) {
        LinuxLatorWidgetAppSharedPreferences preferences = LinuxLatorWidgetAppSharedPreferences.build(context, true);
        if (preferences == null) return null;
        return preferences.getGeneratedToken();
    }

    public String getGeneratedToken() {
        String token =  SharedPreferenceUtils.getString(mSharedPreferences, TERMUX_WIDGET_APP.KEY_TOKEN, null, true);
        if (token == null) {
            token = UUID.randomUUID().toString();
            SharedPreferenceUtils.setString(mSharedPreferences, TERMUX_WIDGET_APP.KEY_TOKEN, token, true);
        }
        return token;
    }



    public int getLogLevel(boolean readFromFile) {
        if (readFromFile)
            return SharedPreferenceUtils.getInt(mMultiProcessSharedPreferences, TERMUX_WIDGET_APP.KEY_LOG_LEVEL, Logger.DEFAULT_LOG_LEVEL);
        else
            return SharedPreferenceUtils.getInt(mSharedPreferences, TERMUX_WIDGET_APP.KEY_LOG_LEVEL, Logger.DEFAULT_LOG_LEVEL);
    }

    public void setLogLevel(Context context, int logLevel, boolean commitToFile) {
        logLevel = Logger.setLogLevel(context, logLevel);
        SharedPreferenceUtils.setInt(mSharedPreferences, TERMUX_WIDGET_APP.KEY_LOG_LEVEL, logLevel, commitToFile);
    }

}
