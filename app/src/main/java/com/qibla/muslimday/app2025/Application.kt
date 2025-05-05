package com.qibla.muslimday.app2025

import android.app.Application
import android.graphics.Color
import android.util.Log
import com.qibla.muslimday.app2025.helper.AppPreference
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber


@HiltAndroidApp
class Application : Application() {


    override fun onCreate() {
        super.onCreate()
        AppPreference.init(applicationContext)
        initRemoteConfig()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


    fun initRemoteConfig() {
        var index = BuildConfig.Minimum_Fetch
        Log.e("TAG", "initRemoteConfig: " + index)
    }
}