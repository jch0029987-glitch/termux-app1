package com.termux.app.fragments.settings;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.preference.PreferenceDataStore;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.termux.R;
import com.termux.shared.termux.settings.preferences.LinuxLatorAppSharedPreferences;

@Keep
public class LinuxLatorPreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = getContext();
        if (context == null) return;

        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.setPreferenceDataStore(LinuxLatorPreferencesDataStore.getInstance(context));

        setPreferencesFromResource(R.xml.termux_preferences, rootKey);
    }

}

class LinuxLatorPreferencesDataStore extends PreferenceDataStore {

    private final Context mContext;
    private final LinuxLatorAppSharedPreferences mPreferences;

    private static LinuxLatorPreferencesDataStore mInstance;

    private LinuxLatorPreferencesDataStore(Context context) {
        mContext = context;
        mPreferences = LinuxLatorAppSharedPreferences.build(context, true);
    }

    public static synchronized LinuxLatorPreferencesDataStore getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LinuxLatorPreferencesDataStore(context);
        }
        return mInstance;
    }

}
