package com.termux.app.fragments.settings;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.preference.PreferenceDataStore;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.termux.R;
import com.termux.shared.termux.settings.preferences.LinuxLatorTaskerAppSharedPreferences;

@Keep
public class LinuxLatorTaskerPreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = getContext();
        if (context == null) return;

        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.setPreferenceDataStore(LinuxLatorTaskerPreferencesDataStore.getInstance(context));

        setPreferencesFromResource(R.xml.termux_tasker_preferences, rootKey);
    }

}

class LinuxLatorTaskerPreferencesDataStore extends PreferenceDataStore {

    private final Context mContext;
    private final LinuxLatorTaskerAppSharedPreferences mPreferences;

    private static LinuxLatorTaskerPreferencesDataStore mInstance;

    private LinuxLatorTaskerPreferencesDataStore(Context context) {
        mContext = context;
        mPreferences = LinuxLatorTaskerAppSharedPreferences.build(context, true);
    }

    public static synchronized LinuxLatorTaskerPreferencesDataStore getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LinuxLatorTaskerPreferencesDataStore(context);
        }
        return mInstance;
    }

}
