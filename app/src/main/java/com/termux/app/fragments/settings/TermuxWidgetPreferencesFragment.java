package com.termux.app.fragments.settings;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.preference.PreferenceDataStore;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.termux.R;
import com.termux.shared.termux.settings.preferences.LinuxLatorWidgetAppSharedPreferences;

@Keep
public class LinuxLatorWidgetPreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = getContext();
        if (context == null) return;

        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.setPreferenceDataStore(LinuxLatorWidgetPreferencesDataStore.getInstance(context));

        setPreferencesFromResource(R.xml.termux_widget_preferences, rootKey);
    }

}

class LinuxLatorWidgetPreferencesDataStore extends PreferenceDataStore {

    private final Context mContext;
    private final LinuxLatorWidgetAppSharedPreferences mPreferences;

    private static LinuxLatorWidgetPreferencesDataStore mInstance;

    private LinuxLatorWidgetPreferencesDataStore(Context context) {
        mContext = context;
        mPreferences = LinuxLatorWidgetAppSharedPreferences.build(context, true);
    }

    public static synchronized LinuxLatorWidgetPreferencesDataStore getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LinuxLatorWidgetPreferencesDataStore(context);
        }
        return mInstance;
    }

}
