package com.qibla.muslimday.app2025.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.network.PrayTimeService
import com.qibla.muslimday.app2025.ui.notification.NotificationActivity
import com.qibla.muslimday.app2025.ui.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class PrayService : Service() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    @Inject
    lateinit var prayTimeService: PrayTimeService

    private var wakeLock: PowerManager.WakeLock? = null

    private lateinit var notificationManager: NotificationManager

    private lateinit var notification: Notification

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification(this, intent)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun showNotification(context: Context, intent: Intent?) {

        if (intent != null) {
            val notificationIntent = Intent(context, SplashActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            requestAutoStartPermission(context)
            notificationManager = getSystemService(NotificationManager::class.java)

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                    "com.qibla.muslimday.app2025",
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_LOW
                )
                notificationChannel.setSound(null, null)
                notificationManager.createNotificationChannel(notificationChannel)
            }

            notification = NotificationCompat.Builder(
                this,
                "com.qibla.muslimday.app2025"
            ).setContentTitle(getString(R.string.app_name))
                .setSmallIcon(R.drawable.img_icon_app)
                .setContentText(intent.getStringExtra("name_pray_service"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            startForeground(1999, notification)
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            wakeLock = powerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "YourApp:WakeLockTag"
            )
            wakeLock?.acquire(1000)
            notificationManager.notify(1999, notification)
            wakeLock?.release()

            println("tunglv === checkNavigate to Navigation")
            val dialogIntent =
                Intent(context, NotificationActivity::class.java)
            dialogIntent.putExtra("name_pray_notify", intent.getStringExtra("name_pray_service"))
            dialogIntent.putExtra("time_pray_notify", intent.getStringExtra("time_pray_service"))
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            this.startActivity(dialogIntent)
        }

    }

    fun requestAutoStartPermission(context: Context) {
        try {
            val intent = Intent()
            val manufacturer = Build.MANUFACTURER.lowercase(Locale.getDefault())

            when (manufacturer) {
                "xiaomi" -> intent.setComponent(
                    ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity"
                    )
                )

                "oppo" -> intent.setComponent(
                    ComponentName(
                        "com.coloros.safecenter",
                        "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                    )
                )

                "vivo" -> intent.setComponent(
                    ComponentName(
                        "com.vivo.permissionmanager",
                        "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                    )
                )

                "huawei" -> intent.setComponent(
                    ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
                    )
                )

                "samsung" -> intent.setComponent(
                    ComponentName(
                        "com.samsung.android.lool",
                        "com.samsung.android.sm.ui.battery.BatteryActivity"
                    )
                )

                "oneplus" -> intent.setComponent(
                    ComponentName(
                        "com.oneplus.security",
                        "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"
                    )
                )

                else -> {
                    // Không hỗ trợ hãng này
//                    Toast.makeText(
//                        context,
//                        "Thiết bị không hỗ trợ AutoStart Permission",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    return
                }
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
//            Toast.makeText(context, "Không mở được AutoStart Permission", Toast.LENGTH_SHORT).show()
        }
    }
}