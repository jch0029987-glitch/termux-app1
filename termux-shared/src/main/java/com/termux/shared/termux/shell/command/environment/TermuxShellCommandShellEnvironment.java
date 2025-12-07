package com.termux.shared.termux.shell.command.environment;

import android.content.Context;

import androidx.annotation.NonNull;

import com.termux.shared.shell.command.ExecutionCommand;
import com.termux.shared.shell.command.environment.ShellCommandShellEnvironment;
import com.termux.shared.shell.command.environment.ShellEnvironmentUtils;
import com.termux.shared.termux.settings.preferences.LinuxLatorAppSharedPreferences;
import com.termux.shared.termux.shell.LinuxLatorShellManager;

import java.util.HashMap;

/**
 * Environment for LinuxLator {@link ExecutionCommand}.
 */
public class LinuxLatorShellCommandShellEnvironment extends ShellCommandShellEnvironment {

    /** Get shell environment containing info for LinuxLator {@link ExecutionCommand}. */
    @NonNull
    @Override
    public HashMap<String, String> getEnvironment(@NonNull Context currentPackageContext,
                                                  @NonNull ExecutionCommand executionCommand) {
        HashMap<String, String> environment = super.getEnvironment(currentPackageContext, executionCommand);

        LinuxLatorAppSharedPreferences preferences = LinuxLatorAppSharedPreferences.build(currentPackageContext);
        if (preferences == null) return environment;

        if (ExecutionCommand.Runner.APP_SHELL.equalsRunner(executionCommand.runner)) {
            ShellEnvironmentUtils.putToEnvIfSet(environment, ENV_SHELL_CMD__APP_SHELL_NUMBER_SINCE_BOOT,
                String.valueOf(preferences.getAndIncrementAppShellNumberSinceBoot()));
            ShellEnvironmentUtils.putToEnvIfSet(environment, ENV_SHELL_CMD__APP_SHELL_NUMBER_SINCE_APP_START,
                String.valueOf(LinuxLatorShellManager.getAndIncrementAppShellNumberSinceAppStart()));

        } else if (ExecutionCommand.Runner.TERMINAL_SESSION.equalsRunner(executionCommand.runner)) {
            ShellEnvironmentUtils.putToEnvIfSet(environment, ENV_SHELL_CMD__TERMINAL_SESSION_NUMBER_SINCE_BOOT,
                String.valueOf(preferences.getAndIncrementTerminalSessionNumberSinceBoot()));
            ShellEnvironmentUtils.putToEnvIfSet(environment, ENV_SHELL_CMD__TERMINAL_SESSION_NUMBER_SINCE_APP_START,
                String.valueOf(LinuxLatorShellManager.getAndIncrementTerminalSessionNumberSinceAppStart()));
        } else {
            return environment;
        }

        return environment;
    }

}
