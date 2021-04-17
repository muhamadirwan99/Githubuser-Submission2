package com.dicoding.picodiploma.githubusersubmission2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.picodiploma.githubusersubmission2.R
import com.dicoding.picodiploma.githubusersubmission2.ui.preference.SettingPreference

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportFragmentManager.beginTransaction().add(R.id.setting_holder, SettingPreference()).commit()

    }
}