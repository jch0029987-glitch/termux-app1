package com.termux.app.fragments.settings;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.preference.PreferenceDataStore;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.termux.R;
import com.termux.shared.termux.settings.preferences.LinuxLatorFloatAppSharedPreferences;

@Keep
public class LinuxLatorFloatPreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = getContext();
        if (context == null) return;

        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.setPreferenceDataStore(LinuxLatorFloatPreferencesDataStore.getInstance(context));

        setPreferencesFromResource(R.xml.termux_float_preferences, rootKey);
    }

}

class LinuxLatorFloatPreferencesDataStore extends PreferenceDataStore {

    private final Context mContext;
    private final LinuxLatorFloatAppSharedPreferences mPreferences;

    private static LinuxLatorFloatPreferencesDataStore mInstance;

    private LinuxLatorFloatPreferencesDataStore(Context context) {
        mContext = context;
        mPreferences = LinuxLatorFloatAppSharedPreferences.build(context, true);
    }

    public static synchronized LinuxLatorFloatPreferencesDataStore getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LinuxLatorFloatPreferencesDataStore(context);
        }
        return mInstance;
    }

}
