package com.dicoding.picodiploma.githubusersubmission2.ui.preference

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.picodiploma.githubusersubmission2.R

class SettingPreference: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var LANGUAGE: String
    private lateinit var languagePreference: Preference

    private lateinit var ALARM: String
    private lateinit var alarmPreference: SwitchPreference

    private lateinit var alarmReceiver: AlarmReceiver


    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)

        LANGUAGE = resources.getString(R.string.key_language)
        ALARM = resources.getString(R.string.key_alarm)

        languagePreference = findPreference<Preference>(LANGUAGE) as Preference
        alarmPreference = findPreference<SwitchPreference>(ALARM) as SwitchPreference

        languagePreference.setOnPreferenceClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
            true
        }
        alarmReceiver = AlarmReceiver()

        setSummaries()

    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences

        alarmPreference.isChecked = sh.getBoolean(ALARM, false)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

        if (key == ALARM) {
            alarmPreference.isChecked = sharedPreferences.getBoolean(ALARM, false)
            if (alarmPreference.isChecked){
                val repeatTime = "09:00"
                val repeatMessage = "Let's find popular user in github"
                context?.let {
                    alarmReceiver.setRepeatingAlarm(
                        it.applicationContext, AlarmReceiver.TYPE_REPEATING,
                        repeatTime, repeatMessage)
                }
                Toast.makeText(context, "Reminder on", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Reminder off", Toast.LENGTH_SHORT).show()
            }
        }
    }


}



