package com.termux.app.fragments.settings;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.preference.PreferenceDataStore;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.termux.R;
import com.termux.shared.termux.settings.preferences.LinuxLatorAPIAppSharedPreferences;

@Keep
public class LinuxLatorAPIPreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = getContext();
        if (context == null) return;

        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.setPreferenceDataStore(LinuxLatorAPIPreferencesDataStore.getInstance(context));

        setPreferencesFromResource(R.xml.termux_api_preferences, rootKey);
    }

}

class LinuxLatorAPIPreferencesDataStore extends PreferenceDataStore {

    private final Context mContext;
    private final LinuxLatorAPIAppSharedPreferences mPreferences;

    private static LinuxLatorAPIPreferencesDataStore mInstance;

    private LinuxLatorAPIPreferencesDataStore(Context context) {
        mContext = context;
        mPreferences = LinuxLatorAPIAppSharedPreferences.build(context, true);
    }

    public static synchronized LinuxLatorAPIPreferencesDataStore getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LinuxLatorAPIPreferencesDataStore(context);
        }
        return mInstance;
    }

}
