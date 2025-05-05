package com.qibla.muslimday.app2025.util

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator

class VibrationManager(private val context: Context) {

    private val vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private var isVibrating = false
    private val handler = Handler()
    private val vibrationInterval: Long = 1000 // 1000ms

    private val vibrationRunnable = object : Runnable {
        override fun run() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (isVibrating) {
                    vibrator.cancel()
                    isVibrating = false
                } else {
                    val effect = VibrationEffect.createOneShot(
                        vibrationInterval,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                    vibrator.vibrate(effect)
                    isVibrating = true
                }
            } else {
                if (isVibrating) {
                    vibrator.cancel()
                    isVibrating = false
                } else {
                    vibrator.vibrate(1000)
                    isVibrating = true
                }
            }
            handler.postDelayed(this, vibrationInterval)
        }
    }

    fun startVibrationCycle() {
        if (!isVibrating) {
            isVibrating = true
            handler.post(vibrationRunnable)
        }
    }

    fun stopVibrationCycle() {
        handler.removeCallbacks(vibrationRunnable)
        vibrator.cancel()
        isVibrating = false
    }


}