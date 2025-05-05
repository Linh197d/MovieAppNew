package com.qibla.muslimday.app2025.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.service.PrayService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PrayBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun onReceive(context: Context, intent: Intent) {

        Log.d("ntt", "Receiver")
        Log.d("ntt", "${intent.data}")
        Log.d("ntt", "Receiver: ${intent.getStringExtra("name_pray")}")
        Log.d("ntt", "Receiver: ${intent.getStringExtra("time")}")
        if (!preferenceHelper.getNotifyState()) return
        val intentNew = Intent(context, PrayService::class.java)
        intentNew.putExtra("name_pray_service", intent.getStringExtra("name_pray"))
        intentNew.putExtra("time_pray_service", intent.getStringExtra("time"))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentNew)
        } else {
            context.startService(
                intentNew
            )
        }
    }

}