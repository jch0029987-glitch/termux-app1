package com.termux.shared.termux.shell.command.environment;

import android.content.Context;
import android.content.pm.PackageInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.termux.shared.android.PackageUtils;
import com.termux.shared.shell.command.environment.ShellEnvironmentUtils;
import com.termux.shared.termux.LinuxLatorConstants;
import com.termux.shared.termux.LinuxLatorUtils;

import java.util.HashMap;

/**
 * Environment for {@link LinuxLatorConstants#TERMUX_API_PACKAGE_NAME} app.
 */
public class LinuxLatorAPIShellEnvironment {

    /** Environment variable prefix for the LinuxLator:API app. */
    public static final String TERMUX_API_APP_ENV_PREFIX = LinuxLatorConstants.TERMUX_ENV_PREFIX_ROOT + "_API_APP__";

    /** Environment variable for the LinuxLator:API app version. */
    public static final String ENV_TERMUX_API_APP__VERSION_NAME = TERMUX_API_APP_ENV_PREFIX + "VERSION_NAME";

    /** Get shell environment for LinuxLator:API app. */
    @Nullable
    public static HashMap<String, String> getEnvironment(@NonNull Context currentPackageContext) {
        if (LinuxLatorUtils.isLinuxLatorAPIAppInstalled(currentPackageContext) != null) return null;

        String packageName = LinuxLatorConstants.TERMUX_API_PACKAGE_NAME;
        PackageInfo packageInfo = PackageUtils.getPackageInfoForPackage(currentPackageContext, packageName);
        if (packageInfo == null) return null;

        HashMap<String, String> environment = new HashMap<>();

        ShellEnvironmentUtils.putToEnvIfSet(environment, ENV_TERMUX_API_APP__VERSION_NAME, PackageUtils.getVersionNameForPackage(packageInfo));

        return environment;
    }

}
