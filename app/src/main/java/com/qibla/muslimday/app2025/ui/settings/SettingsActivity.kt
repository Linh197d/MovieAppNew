package com.qibla.muslimday.app2025.ui.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.play.core.review.ReviewManagerFactory
import com.nlbn.ads.util.AppOpenManager
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.BuildConfig
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.FragmentSettingsBinding
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.helper.PreferenceHelper.Companion.ISLAMIC_CALENDAR
import com.qibla.muslimday.app2025.ui.home.HomeFragment
import com.qibla.muslimday.app2025.ui.language.LanguageActivity
import com.qibla.muslimday.app2025.ui.salah.SalahFragment
import com.qibla.muslimday.app2025.ui.settings.prayingTimeAdjustment.PrayingTimeAdjustment
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Global
import com.qibla.muslimday.app2025.view.DialogAdjustIslamicCalendar
import com.qibla.muslimday.app2025.view.DialogJuristicMethod
import com.qibla.muslimday.app2025.view.RatingDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : BaseActivity<FragmentSettingsBinding>() {
    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private var juristicMethodSelect = 0

    private var isUpdateLocationAndTime = false

    private var isFixedCountdownOnNotification = false

    private var isRate = false

    private val intDay = MutableLiveData<Int>()
    private val intLiveDataDay: LiveData<Int>
        get() = intDay

    private var isShareDialogOpen = false

    override fun setBinding(layoutInflater: LayoutInflater): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun initView() {
        initData()

        val consentHelper = ConsentHelper.getInstance(this)
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        consentHelper.obtainConsentAndShow(this) {
            AdsInter.loadBanner(this@SettingActivity, binding.frBanner, binding.root)
        }
        if (preferenceHelper.getNotifyState()) {
            binding.ivSettingNotifyTurnOn.setImageResource(R.drawable.ic_switch_notify)
        } else {
            binding.ivSettingNotifyTurnOn.setImageResource(R.drawable.ic_switch_inactive)
        }

        binding.ivSettingNotifyTurnOn.isSelected = preferenceHelper.getNotifyState()

        binding.ivSettingNotifyTurnOn.setOnClickListener {
            binding.ivSettingNotifyTurnOn.isSelected = !binding.ivSettingNotifyTurnOn.isSelected
            if (binding.ivSettingNotifyTurnOn.isSelected) {
                binding.ivSettingNotifyTurnOn.setImageResource(R.drawable.ic_switch_notify)
            } else {
                binding.ivSettingNotifyTurnOn.setImageResource(R.drawable.ic_switch_inactive)
            }
            preferenceHelper.setNotifyState(binding.ivSettingNotifyTurnOn.isSelected)
        }

        binding.ivSettingBack.setOnClickListener {
            finish()
        }

        binding.layoutLanguage.setOnClickListener {
            onClickLayoutLanguage()
        }

        binding.layoutJuristicMethod.setOnClickListener {
            juristicMethodSelect = preferenceHelper.getJuristicMethod()
            onClickLayoutJuristicMethod(juristicMethodSelect)
        }

        binding.layoutPrayingTime.setOnClickListener {
            onClickLayoutPrayingTime()
        }


        binding.layoutAdjustIslamicCalender.setOnClickListener {
            onClickLayoutAdjustIslamicCalender()
        }

        binding.layoutShare.setOnClickListener {
            onClickLayoutShare()
        }

        binding.layoutRate.setOnClickListener {
            onClickLayoutRate()
        }

        binding.layoutPrivatePolicy.setOnClickListener {
            onClickLayoutPrivatePolicy()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun initData() {
        intDay.value = preferenceHelper.getInt(ISLAMIC_CALENDAR)
        intLiveDataDay.observe(this) { intDay ->
            if (intDay == 0) {
                binding.tvAdjustIslamicCalender.text = getString(R.string.string_not_adjusted)
            } else {
                if (intDay == 1 || intDay == -1) {
                    if (intDay == 1) {
                        binding.tvAdjustIslamicCalender.text =
                            "+$intDay " + getString(R.string.text_day)
                    } else {
                        binding.tvAdjustIslamicCalender.text =
                            "$intDay " + getString(R.string.text_day)
                    }

                } else if (intDay > 0) {
                    binding.tvAdjustIslamicCalender.text =
                        "+$intDay " + getString(R.string.text_days)
                } else {
                    binding.tvAdjustIslamicCalender.text =
                        "$intDay " + getString(R.string.text_days)
                }
            }
        }


        isRate = preferenceHelper.isRate()

        if (!isRate) {
            binding.layoutRate.visibility = View.VISIBLE
        } else {
            binding.layoutRate.visibility = View.GONE
        }

        isUpdateLocationAndTime = preferenceHelper.getIsUpdateLocationAndTime()

        isFixedCountdownOnNotification = preferenceHelper.getIsFixedCountdownOnNotification()

        binding.switchFixedCountdownOnNotification.isChecked = isFixedCountdownOnNotification

        binding.tvJuristicMethod.isSelected = true
        binding.tv1.isSelected = true

        juristicMethodSelect = preferenceHelper.getJuristicMethod()
        binding.tvJuristicMethod.text = when (juristicMethodSelect) {
            0 -> "Shafi/ Maliki/ Hanbali"
            1 -> "Hanafi"
            else -> ""
        }

        when (preferenceHelper.getString(PreferenceHelper.PREF_CURRENT_LANGUAGE)) {
            "en" -> binding.tvLanguage.text = getString(R.string.string_english)
            "in" -> binding.tvLanguage.text = getString(R.string.string_indonesian)
            "tr" -> binding.tvLanguage.text = getString(R.string.string_turkish)
            "ur" -> binding.tvLanguage.text = getString(R.string.string_urdu)
            "hi" -> binding.tvLanguage.text = getString(R.string.string_hindi)
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        Log.d("ntt", "onResume: ")
        AppOpenManager.getInstance().disableAppResumeWithActivity(SettingActivity::class.java)
    }

    private fun onClickLayoutPrivatePolicy() {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://sonnstudio.netlify.app/policy"))
        AppOpenManager.getInstance().disableAppResumeWithActivity(SettingActivity::class.java)
        startActivity(browserIntent)
    }

    private fun onClickLayoutRate() {
        if (!preferenceHelper.isRate()) {
            val ratingDialog = RatingDialog(this)
            ratingDialog.init(this, object : RatingDialog.OnPress {
                override fun sendThank() {
                    preferenceHelper.forceRated()
                    ratingDialog.dismiss()

                    binding.layoutRate.visibility = View.GONE

                    Toast.makeText(
                        this@SettingActivity,
                        getString(R.string.string_thank_for_rate),
                        Toast.LENGTH_SHORT
                    ).show()

                }

                override fun rating() {
                    val manager = ReviewManagerFactory.create(this@SettingActivity)
                    val request = manager.requestReviewFlow()
                    request.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val reviewInfo = task.result
                            val flow = manager.launchReviewFlow(this@SettingActivity, reviewInfo)
                            flow.addOnSuccessListener {
                                preferenceHelper.forceRated()
                                ratingDialog.dismiss()

                            }
                        } else {
                            preferenceHelper.forceRated()
                            ratingDialog.dismiss()
                        }
                    }

                    binding.layoutRate.visibility = View.GONE

                }

                override fun later() {
                    ratingDialog.dismiss()
                }
            })

            try {
                ratingDialog.show()
            } catch (e: WindowManager.BadTokenException) {
                e.printStackTrace()
            }
        }

    }

    private fun onClickLayoutShare() {
        try {
            if (!isShareDialogOpen) {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                var shareMessage =
                    "${getString(R.string.app_name)} ${getString(R.string.string_let_me_recommend_you_this_application)} ".trimIndent()
                shareMessage =
                    "${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}".trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)

                this.window!!.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

                AppOpenManager.getInstance()
                    .disableAppResumeWithActivity(SettingActivity::class.java)

                startActivityForResult(Intent.createChooser(shareIntent, "choose one"), 1900)

                isShareDialogOpen = true
            }
        } catch (e: Exception) {
            Log.d("ntt", "onClickLayoutShare: ${e.printStackTrace()}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1900) {
            isShareDialogOpen = false
        }
    }

    private fun onClickLayoutAdjustIslamicCalender() {
        val adjustIslamicCalendarDialog =
            DialogAdjustIslamicCalendar(this, preferenceHelper)
        adjustIslamicCalendarDialog.init(
            this,
            object : DialogAdjustIslamicCalendar.OnPress {
                override fun cancel() {
                    adjustIslamicCalendarDialog.dismiss()
                }

                override fun saveDate(day: Int) {
                    preferenceHelper.setInt(ISLAMIC_CALENDAR, day)
                    Global.intDay = day
                    intDay.value = day
                    adjustIslamicCalendarDialog.dismiss()
                }
            })

        try {
            adjustIslamicCalendarDialog.show()
        } catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()
        }
    }

    private fun onClickLayoutPrayingTime() {
//        startActivity(Intent(PrayingTimeAdjustment.newIntent(this)))
        if (AdsInter.isLoadNativePrayingtime) {
            startActivityNativeAll.launch(Intent(PrayingTimeAdjustment.newIntent(this)))
        } else {
            startActivity(Intent(PrayingTimeAdjustment.newIntent(this)))
        }
    }

    val startActivityNativeAll = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { _ ->
//        lifecycleScope.launch {
//            val loadNativeAll = async { loadNativeAll() }
//
//            awaitAll(
//                loadNativeAll,
//            )
//        }
    }

//    private fun loadNativeAll() {
//        Admob.getInstance()
//            .loadNativeAd(
//                this,
//                getString(R.string.native_all),
//                object : NativeCallback() {
//                    override fun onNativeAdLoaded(nativeAd: NativeAd?) {
//                        AdsInter.nativeAdsAll = nativeAd
//                    }
//
//                    override fun onAdImpression() {
//                        super.onAdImpression()
//                        AdsInter.nativeAdsAll = null
//                    }
//                })
//    }

    private fun onClickLayoutJuristicMethod(juristicMethodSelect: Int) {
        val juristicMethodDialog = DialogJuristicMethod(this)
        juristicMethodDialog.init(
            this,
            juristicMethodSelect,
            object : DialogJuristicMethod.OnPress {
                override fun cancel() {
                    juristicMethodDialog.dismiss()
                }

                override fun save(juristicMethod: Int) {
                    if (juristicMethod == 0) {
                        preferenceHelper.setJuristicMethod(0)
                        binding.tvJuristicMethod.text = "Shafi/ Maliki/ Hanbali"
                    } else if (juristicMethod == 1) {
                        preferenceHelper.setJuristicMethod(1)
                        binding.tvJuristicMethod.text = "Hanafi"
                    }
                    juristicMethodDialog.dismiss()

                    HomeFragment.isCheck = true
                    SalahFragment.isCheck = true
                }
            })

        try {
            juristicMethodDialog.show()
        } catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()
        }
    }

    private fun onClickLayoutLanguage() {
        startActivity(LanguageActivity.newIntent(this, false))
    }

    private fun setTurnOnOffFixedCountdownNotificationService(isActivate: Boolean) {
    }

}