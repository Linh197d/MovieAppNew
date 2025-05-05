package com.qibla.muslimday.app2025.ui.notification

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.qibla.muslimday.app2025.MainActivity
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.database.timePray.PrayTimeEntity
import com.qibla.muslimday.app2025.databinding.ActivityNotificationBinding
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.service.PrayService
import com.qibla.muslimday.app2025.ui.home.PrayTimeViewModel
import com.qibla.muslimday.app2025.ui.ramadan.modelBackup.PrayerTimeBackup
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Const
import com.qibla.muslimday.app2025.util.Global
import com.qibla.muslimday.app2025.util.TimezoneMapper
import com.qibla.muslimday.app2025.util.VibrationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

enum class NAVIGATE_TYPE {
    PRAY_NOW, QIBLA, QURAN
}

@AndroidEntryPoint
class NotificationActivity : BaseActivity<ActivityNotificationBinding>() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private lateinit var vibrationManager: VibrationManager

    private val viewModel: PrayTimeViewModel by viewModels()

    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var alarmManager: AlarmManager

    private var type: NAVIGATE_TYPE? = null

    private var isVibration = false
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var prayName: String = ""
    private var prayTime: String = ""

    private var timePreNotification: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showAdsNative()
        turnScreenOnAndKeyguardOff()
        getCurrentLocation()
        binding.btnClose.setOnClickListener {
            stopService(
                Intent(
                    this@NotificationActivity,
                    PrayService::class.java
                )
            )
            val intent =
                Intent(this@NotificationActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)


            startActivity(intent)
            if (this::mediaPlayer.isInitialized) {
                mediaPlayer.pause()
                mediaPlayer.stop()
            }
            if (isVibration) {
                vibrationManager.stopVibrationCycle()
            }

            finishAffinity()
        }

        binding.btnPrayNow.setOnClickListener {
            if (Const.PrayTimeModel == null) {
                type = NAVIGATE_TYPE.PRAY_NOW
                getCurrentLocation()
                return@setOnClickListener
            }
            stopService(
                Intent(
                    this@NotificationActivity,
                    PrayService::class.java
                )
            )

            if (this::mediaPlayer.isInitialized) {
                mediaPlayer.pause()
                mediaPlayer.stop()
            }
            if (isVibration) {
                vibrationManager.stopVibrationCycle()
            }


            val intent =
                Intent(this@NotificationActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
            finish()
        }

        binding.layoutQuran.setOnClickListener {
            if (Const.PrayTimeModel == null) {
                type = NAVIGATE_TYPE.QURAN
                getCurrentLocation()
                return@setOnClickListener
            }
            val intent = MainActivity.newIntent(this)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("start", "notification")
            startActivity(intent)
            finishAffinity()

        }

        binding.layoutQibla.setOnClickListener {
            if (Const.PrayTimeModel == null) {
                type = NAVIGATE_TYPE.QIBLA
                getCurrentLocation()
                return@setOnClickListener
            }
            stopService(
                Intent(
                    this@NotificationActivity,
                    PrayService::class.java
                )
            )

            if (this::mediaPlayer.isInitialized) {
                mediaPlayer.pause()
                mediaPlayer.stop()
            }
            if (isVibration) {
                vibrationManager.stopVibrationCycle()
            }

            val intent = MainActivity.newIntent(this)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("start", "notification_qibla")
            startActivity(intent)
            finishAffinity()
        }
        AdsInter.onAdsClick = {
            showAdsNative()
        }
    }

    private fun showAdsNative() {
        AdsInter.pushNativeAll(
            context = this@NotificationActivity,
            view = binding.frAds,
            keyCheck = AdsInter.isLoadNativePrayerTime, isNotify = true,
            scope = lifecycleScope
        )
    }

    private fun getCurrentLocation() {
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this);
        Log.d("ntt", "getCurrentLocation")
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            println("adlasbdjkasbdjkas 1111")
            fusedLocationClient?.lastLocation?.addOnCompleteListener {
                lifecycleScope.launch {
                    if (it.isSuccessful && it.result != null) {
                        println("adlasbdjkasbdjkas 2222")

                        val location: Location = it.result
                        val date = LocalDate.now().plusDays(Global.intDay.toLong())
                        // Sử dụng latitude và longitude ở đây

                        if (Const.PrayTimeModel == null) {
                            println("adlasbdjkasbdjkas 3333")

                            val timeId =
                                TimezoneMapper.latLngToTimezoneString(
                                    location.latitude,
                                    location.longitude
                                )

                            Const.timeId = timeId
                            Const.latitude = location.latitude
                            Const.longitude = location.longitude
                            preferenceHelper.setLatitude(location.latitude.toString())
                            preferenceHelper.setLongitude(location.longitude.toString())
                            preferenceHelper.setTimeId(timeId)

                            viewModel.getPrayTimeNew(
                                date.toString(),
                                location.latitude,
                                location.longitude,
                                preferenceHelper.getCalculationMethod(),
                                preferenceHelper.getJuristicMethod(),
                            )
                            this.let { activity ->
                                viewModel.prayTimeNew.observe(this@NotificationActivity) { prayTimeModel ->
                                    println("check timming = ${prayTimeModel.data?.timings}")
                                    if (prayTimeModel.data?.timings != null) {

                                        Const.PrayTimeModel = PrayerTimeBackup(
                                            code = prayTimeModel.code,
                                            data = prayTimeModel.data.copy(),
                                            status = prayTimeModel.status
                                        )

                                        Const.PrayTimeModelOld = prayTimeModel.copy()

                                        Const.PrayTimeModelOld?.data?.timings?.Fajr?.let { it1 ->
                                            PrayTimeEntity(
                                                1,
                                                "Fajr",
                                                it1
                                            )
                                        }?.let { it2 ->
                                        }
                                        Const.PrayTimeModelOld?.data?.timings?.Sunrise?.let { it1 ->
                                            PrayTimeEntity(
                                                2,
                                                "Sunrise",
                                                it1
                                            )
                                        }?.let { it2 ->

                                        }
                                        Const.PrayTimeModelOld?.data?.timings?.Dhuhr?.let { it1 ->
                                            PrayTimeEntity(
                                                3,
                                                "Dhuhr",
                                                it1
                                            )
                                        }?.let { it2 ->

                                        }
                                        Const.PrayTimeModelOld?.data?.timings?.Asr?.let { it1 ->
                                            PrayTimeEntity(
                                                4,
                                                "Asr",
                                                it1
                                            )
                                        }?.let { it2 ->
                                        }
                                        Const.PrayTimeModelOld?.data?.timings?.Maghrib?.let { it1 ->
                                            PrayTimeEntity(
                                                5,
                                                "Maghrib",
                                                it1
                                            )
                                        }?.let { it2 ->

                                        }
                                        Const.PrayTimeModelOld?.data?.timings?.Isha?.let { it1 ->
                                            PrayTimeEntity(
                                                6,
                                                "Isha",
                                                it1
                                            )
                                        }?.let { it2 ->

                                        }
                                    }
                                }
                            }
                        } else {
                            println("adlasbdjkasbdjkas 4444")
                        }

                        when (type) {
                            NAVIGATE_TYPE.QIBLA -> binding.layoutQibla.performClick()
                            NAVIGATE_TYPE.PRAY_NOW -> binding.btnPrayNow.performClick()
                            NAVIGATE_TYPE.QURAN -> binding.layoutQuran.performClick()
                            else -> {}
                        }
                        type = null


                    }

                }
            }

        }
    }

    override fun setBinding(layoutInflater: LayoutInflater): ActivityNotificationBinding {
        return ActivityNotificationBinding.inflate(layoutInflater)
    }

    override fun initView() {

        Log.d("ntt", "Activity Notify")
        Log.d("ntt", "Activity Notify: ${intent.getStringExtra("name_pray_notify")}")
        Log.d("ntt", "Activity Notify: ${intent.getStringExtra("time_pray_notify")}")



        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        prayName = intent.getStringExtra("name_pray_notify").toString()
        prayTime = intent.getStringExtra("time_pray_notify").toString()

        binding.tvNamePray.text = prayName
        binding.tvTimePray.text = prayTime

        vibrationManager = VibrationManager(this)

        when (prayName) {
            "Fajr" -> {
                val pageBackground = preferenceHelper.getPageBackgroundFajr()
                val sound = preferenceHelper.getSoundFajr()
                isVibration = preferenceHelper.getIsVibratePrayTimeFajr()
                timePreNotification = preferenceHelper.getTimePreNotificationFajr()
                setTextPreNotification(timePreNotification)
                setBackgroundLayout(pageBackground)
                setSound(sound)
                setIsVibration(isVibration)

            }

            "Sunrise" -> {
                val pageBackground = preferenceHelper.getPageBackgroundFajr()
                val sound = preferenceHelper.getSoundSunrise()
                isVibration = preferenceHelper.getIsVibratePrayTimeSunrise()
                setBackgroundLayout(pageBackground)
                setSound(sound)
                setIsVibration(isVibration)
                timePreNotification = preferenceHelper.getTimePreNotificationSunrise()
                setTextPreNotification(timePreNotification)
            }

            "Dhuhr" -> {
                val pageBackground = preferenceHelper.getPageBackgroundFajr()
                val sound = preferenceHelper.getSoundDhuhr()
                isVibration = preferenceHelper.getIsVibratePrayTimeDhuhr()
                setBackgroundLayout(pageBackground)
                setSound(sound)
                setIsVibration(isVibration)
                timePreNotification = preferenceHelper.getTimePreNotificationDhuhr()
                setTextPreNotification(timePreNotification)
            }

            "Asr" -> {
                val pageBackground = preferenceHelper.getPageBackgroundFajr()
                val sound = preferenceHelper.getSoundAsr()
                isVibration = preferenceHelper.getIsVibratePrayTimeAsr()
                setBackgroundLayout(pageBackground)
                setSound(sound)
                setIsVibration(isVibration)
                timePreNotification = preferenceHelper.getTimePreNotificationAsr()
                setTextPreNotification(timePreNotification)
            }

            "Maghrib" -> {
                val pageBackground = preferenceHelper.getPageBackgroundFajr()
                val sound = preferenceHelper.getSoundMaghrib()
                isVibration = preferenceHelper.getIsVibratePrayTimeMaghrib()
                setBackgroundLayout(pageBackground)
                setSound(sound)
                setIsVibration(isVibration)
                timePreNotification = preferenceHelper.getTimePreNotificationMaghrib()
                setTextPreNotification(timePreNotification)
            }

            "Isha" -> {
                val pageBackground = preferenceHelper.getPageBackgroundFajr()
                val sound = preferenceHelper.getSoundIsha()
                isVibration = preferenceHelper.getIsVibratePrayTimeIsha()
                setBackgroundLayout(pageBackground)
                setSound(sound)
                setIsVibration(isVibration)
                timePreNotification = preferenceHelper.getTimePreNotificationIsha()
                setTextPreNotification(timePreNotification)
            }
        }
    }

    private fun setTextPreNotification(timePreNotification: Int) {
        when (timePreNotification) {
            0 -> {
                binding.tvPreNotification.visibility = View.GONE
            }

            1 -> {
                binding.tvPreNotification.visibility = View.VISIBLE
                binding.tvPreNotification.text =
                    getString(
                        R.string.string_prayer_time_will_begin,
                        timePreNotification.toString()
                    )
            }

            else -> {
                binding.tvPreNotification.visibility = View.VISIBLE
                binding.tvPreNotification.text =
                    getString(
                        R.string.string_prayer_time_will_begin_s,
                        timePreNotification.toString()
                    )
            }
        }
    }

    private fun setIsVibration(vibration: Boolean) {
        if (vibration) {
            vibrationManager.startVibrationCycle()
        }
    }

    private fun setSound(sound: Int) {
        mediaPlayer =
            when (sound) {
                1 -> MediaPlayer.create(this, R.raw.sound_default)
                2 -> MediaPlayer.create(this, R.raw.sound_athan)
                3 -> MediaPlayer.create(this, R.raw.sound_azanislamic)
                4 -> MediaPlayer.create(this, R.raw.sound_azan_ki)
                5 -> MediaPlayer.create(this, R.raw.sound_shia_azan)
                6 -> MediaPlayer.create(this, R.raw.sound_surah_baqarah_islamic)
                7 -> MediaPlayer.create(this, R.raw.sound_surah_rehman_with_tarjuma)
                else -> MediaPlayer()
            }

        mediaPlayer.isLooping = true
        mediaPlayer.start()

    }

    private fun setBackgroundLayout(pageBackground: Int) {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        stopService(
            Intent(
                this@NotificationActivity,
                PrayService::class.java
            )
        )

        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.pause()
            mediaPlayer.stop()
        }
        if (isVibration) {
            vibrationManager.stopVibrationCycle()
        }

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        stopService(
            Intent(
                this@NotificationActivity,
                PrayService::class.java
            )
        )

        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.pause()
            mediaPlayer.stop()
        }
        if (isVibration) {
            vibrationManager.stopVibrationCycle()
        }

    }

    fun Activity.turnScreenOnAndKeyguardOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
            )
        }
    }
}