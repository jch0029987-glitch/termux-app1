package com.termux.app.terminal;

import android.app.Service;

import androidx.annotation.NonNull;

import com.termux.app.LinuxLatorService;
import com.termux.shared.termux.shell.command.runner.terminal.LinuxLatorSession;
import com.termux.shared.termux.terminal.LinuxLatorTerminalSessionClientBase;
import com.termux.terminal.TerminalSession;
import com.termux.terminal.TerminalSessionClient;

/** The {@link TerminalSessionClient} implementation that may require a {@link Service} for its interface methods. */
public class LinuxLatorTerminalSessionServiceClient extends LinuxLatorTerminalSessionClientBase {

    private static final String LOG_TAG = "LinuxLatorTerminalSessionServiceClient";

    private final LinuxLatorService mService;

    public LinuxLatorTerminalSessionServiceClient(LinuxLatorService service) {
        this.mService = service;
    }

    @Override
    public void setTerminalShellPid(@NonNull TerminalSession terminalSession, int pid) {
        LinuxLatorSession termuxSession = mService.getLinuxLatorSessionForTerminalSession(terminalSession);
        if (termuxSession != null)
            termuxSession.getExecutionCommand().mPid = pid;
    }

}
