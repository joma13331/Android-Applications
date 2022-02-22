package com.example.pennydrop.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.DropDownPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.pennydrop.R

class SettingsFragment:PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val themePreference = findPreference<DropDownPreference?>("theme")
        val themeModePreference = findPreference<ListPreference>("themeMode")

        themePreference?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener{
                _,_-> activity?.recreate()
        true
        }
        themeModePreference?.setDefaultValue(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        themeModePreference?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener{
            _, newValue->
            val nightMode = when(newValue?.toString()){
                "Light"-> AppCompatDelegate.MODE_NIGHT_NO
                "Dark"-> AppCompatDelegate.MODE_NIGHT_YES
                else-> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }

            AppCompatDelegate.setDefaultNightMode(nightMode)

            true
        }
    }
}