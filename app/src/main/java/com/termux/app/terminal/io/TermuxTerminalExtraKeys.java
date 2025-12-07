package com.termux.app.terminal.io;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.termux.app.LinuxLatorActivity;
import com.termux.app.terminal.LinuxLatorTerminalSessionActivityClient;
import com.termux.app.terminal.LinuxLatorTerminalViewClient;
import com.termux.shared.logger.Logger;
import com.termux.shared.termux.extrakeys.ExtraKeysConstants;
import com.termux.shared.termux.extrakeys.ExtraKeysInfo;
import com.termux.shared.termux.settings.properties.LinuxLatorPropertyConstants;
import com.termux.shared.termux.settings.properties.LinuxLatorSharedProperties;
import com.termux.shared.termux.terminal.io.TerminalExtraKeys;
import com.termux.view.TerminalView;

import org.json.JSONException;

public class LinuxLatorTerminalExtraKeys extends TerminalExtraKeys {

    private ExtraKeysInfo mExtraKeysInfo;

    final LinuxLatorActivity mActivity;
    final LinuxLatorTerminalViewClient mLinuxLatorTerminalViewClient;
    final LinuxLatorTerminalSessionActivityClient mLinuxLatorTerminalSessionActivityClient;

    private static final String LOG_TAG = "LinuxLatorTerminalExtraKeys";

    public LinuxLatorTerminalExtraKeys(LinuxLatorActivity activity, @NonNull TerminalView terminalView,
                                   LinuxLatorTerminalViewClient termuxTerminalViewClient,
                                   LinuxLatorTerminalSessionActivityClient termuxTerminalSessionActivityClient) {
        super(terminalView);

        mActivity = activity;
        mLinuxLatorTerminalViewClient = termuxTerminalViewClient;
        mLinuxLatorTerminalSessionActivityClient = termuxTerminalSessionActivityClient;

        setExtraKeys();
    }


    /**
     * Set the terminal extra keys and style.
     */
    private void setExtraKeys() {
        mExtraKeysInfo = null;

        try {
            // The mMap stores the extra key and style string values while loading properties
            // Check {@link #getExtraKeysInternalPropertyValueFromValue(String)} and
            // {@link #getExtraKeysStyleInternalPropertyValueFromValue(String)}
            String extrakeys = (String) mActivity.getProperties().getInternalPropertyValue(LinuxLatorPropertyConstants.KEY_EXTRA_KEYS, true);
            String extraKeysStyle = (String) mActivity.getProperties().getInternalPropertyValue(LinuxLatorPropertyConstants.KEY_EXTRA_KEYS_STYLE, true);

            ExtraKeysConstants.ExtraKeyDisplayMap extraKeyDisplayMap = ExtraKeysInfo.getCharDisplayMapForStyle(extraKeysStyle);
            if (ExtraKeysConstants.EXTRA_KEY_DISPLAY_MAPS.DEFAULT_CHAR_DISPLAY.equals(extraKeyDisplayMap) && !LinuxLatorPropertyConstants.DEFAULT_IVALUE_EXTRA_KEYS_STYLE.equals(extraKeysStyle)) {
                Logger.logError(LinuxLatorSharedProperties.LOG_TAG, "The style \"" + extraKeysStyle + "\" for the key \"" + LinuxLatorPropertyConstants.KEY_EXTRA_KEYS_STYLE + "\" is invalid. Using default style instead.");
                extraKeysStyle = LinuxLatorPropertyConstants.DEFAULT_IVALUE_EXTRA_KEYS_STYLE;
            }

            mExtraKeysInfo = new ExtraKeysInfo(extrakeys, extraKeysStyle, ExtraKeysConstants.CONTROL_CHARS_ALIASES);
        } catch (JSONException e) {
            Logger.showToast(mActivity, "Could not load and set the \"" + LinuxLatorPropertyConstants.KEY_EXTRA_KEYS + "\" property from the properties file: " + e.toString(), true);
            Logger.logStackTraceWithMessage(LOG_TAG, "Could not load and set the \"" + LinuxLatorPropertyConstants.KEY_EXTRA_KEYS + "\" property from the properties file: ", e);

            try {
                mExtraKeysInfo = new ExtraKeysInfo(LinuxLatorPropertyConstants.DEFAULT_IVALUE_EXTRA_KEYS, LinuxLatorPropertyConstants.DEFAULT_IVALUE_EXTRA_KEYS_STYLE, ExtraKeysConstants.CONTROL_CHARS_ALIASES);
            } catch (JSONException e2) {
                Logger.showToast(mActivity, "Can't create default extra keys",true);
                Logger.logStackTraceWithMessage(LOG_TAG, "Could create default extra keys: ", e);
                mExtraKeysInfo = null;
            }
        }
    }

    public ExtraKeysInfo getExtraKeysInfo() {
        return mExtraKeysInfo;
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onTerminalExtraKeyButtonClick(View view, String key, boolean ctrlDown, boolean altDown, boolean shiftDown, boolean fnDown) {
        if ("KEYBOARD".equals(key)) {
            if(mLinuxLatorTerminalViewClient != null)
                mLinuxLatorTerminalViewClient.onToggleSoftKeyboardRequest();
        } else if ("DRAWER".equals(key)) {
            DrawerLayout drawerLayout = mLinuxLatorTerminalViewClient.getActivity().getDrawer();
            if (drawerLayout.isDrawerOpen(Gravity.LEFT))
                drawerLayout.closeDrawer(Gravity.LEFT);
            else
                drawerLayout.openDrawer(Gravity.LEFT);
        } else if ("PASTE".equals(key)) {
            if(mLinuxLatorTerminalSessionActivityClient != null)
                mLinuxLatorTerminalSessionActivityClient.onPasteTextFromClipboard(null);
        }  else if ("SCROLL".equals(key)) {
            TerminalView terminalView = mLinuxLatorTerminalViewClient.getActivity().getTerminalView();
            if (terminalView != null && terminalView.mEmulator != null)
                terminalView.mEmulator.toggleAutoScrollDisabled();
        } else {
            super.onTerminalExtraKeyButtonClick(view, key, ctrlDown, altDown, shiftDown, fnDown);
        }
    }

}
