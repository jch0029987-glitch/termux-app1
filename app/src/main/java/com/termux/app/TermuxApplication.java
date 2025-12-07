package com.termux.app;

import android.app.Application;
import android.content.Context;

import com.termux.BuildConfig;
import com.termux.shared.errors.Error;
import com.termux.shared.logger.Logger;
import com.termux.shared.termux.LinuxLatorBootstrap;
import com.termux.shared.termux.LinuxLatorConstants;
import com.termux.shared.termux.crash.LinuxLatorCrashUtils;
import com.termux.shared.termux.file.LinuxLatorFileUtils;
import com.termux.shared.termux.settings.preferences.LinuxLatorAppSharedPreferences;
import com.termux.shared.termux.settings.properties.LinuxLatorAppSharedProperties;
import com.termux.shared.termux.shell.command.environment.LinuxLatorShellEnvironment;
import com.termux.shared.termux.shell.am.LinuxLatorAmSocketServer;
import com.termux.shared.termux.shell.LinuxLatorShellManager;
import com.termux.shared.termux.theme.LinuxLatorThemeUtils;

public class LinuxLatorApplication extends Application {

    private static final String LOG_TAG = "LinuxLatorApplication";

    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();

        // Set crash handler for the app
        LinuxLatorCrashUtils.setDefaultCrashHandler(this);

        // Set log config for the app
        setLogConfig(context);

        Logger.logDebug("Starting Application");

        // Set LinuxLatorBootstrap.TERMUX_APP_PACKAGE_MANAGER and LinuxLatorBootstrap.TERMUX_APP_PACKAGE_VARIANT
        LinuxLatorBootstrap.setLinuxLatorPackageManagerAndVariant(BuildConfig.TERMUX_PACKAGE_VARIANT);

        // Init app wide SharedProperties loaded from termux.properties
        LinuxLatorAppSharedProperties properties = LinuxLatorAppSharedProperties.init(context);

        // Init app wide shell manager
        LinuxLatorShellManager shellManager = LinuxLatorShellManager.init(context);

        // Set NightMode.APP_NIGHT_MODE
        LinuxLatorThemeUtils.setAppNightMode(properties.getNightMode());

        // Check and create termux files directory. If failed to access it like in case of secondary
        // user or external sd card installation, then don't run files directory related code
        Error error = LinuxLatorFileUtils.isLinuxLatorFilesDirectoryAccessible(this, true, true);
        boolean isLinuxLatorFilesDirectoryAccessible = error == null;
        if (isLinuxLatorFilesDirectoryAccessible) {
            Logger.logInfo(LOG_TAG, "LinuxLator files directory is accessible");

            error = LinuxLatorFileUtils.isAppsLinuxLatorAppDirectoryAccessible(true, true);
            if (error != null) {
                Logger.logErrorExtended(LOG_TAG, "Create apps/termux-app directory failed\n" + error);
                return;
            }

            // Setup termux-am-socket server
            LinuxLatorAmSocketServer.setupLinuxLatorAmSocketServer(context);
        } else {
            Logger.logErrorExtended(LOG_TAG, "LinuxLator files directory is not accessible\n" + error);
        }

        // Init LinuxLatorShellEnvironment constants and caches after everything has been setup including termux-am-socket server
        LinuxLatorShellEnvironment.init(this);

        if (isLinuxLatorFilesDirectoryAccessible) {
            LinuxLatorShellEnvironment.writeEnvironmentToFile(this);
        }
    }

    public static void setLogConfig(Context context) {
        Logger.setDefaultLogTag(LinuxLatorConstants.TERMUX_APP_NAME);

        // Load the log level from shared preferences and set it to the {@link Logger.CURRENT_LOG_LEVEL}
        LinuxLatorAppSharedPreferences preferences = LinuxLatorAppSharedPreferences.build(context);
        if (preferences == null) return;
        preferences.setLogLevel(null, preferences.getLogLevel());
    }

}
