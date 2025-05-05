package com.qibla.muslimday.app2025.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nlbn.ads.callback.NativeCallback
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.AppOpenManager
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.MainActivity
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseFragment
import com.qibla.muslimday.app2025.database.Quran.QuranEntity
import com.qibla.muslimday.app2025.database.timePray.PrayTimeEntity
import com.qibla.muslimday.app2025.databinding.FragmentHomeBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.extensions.visible
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.helper.PreferenceHelper.Companion.DATE_DAY
import com.qibla.muslimday.app2025.helper.PreferenceHelper.Companion.ISLAMIC_CALENDAR
import com.qibla.muslimday.app2025.receiver.PrayBroadcastReceiver
import com.qibla.muslimday.app2025.ui.allah99name.Allah99NameActivity
import com.qibla.muslimday.app2025.ui.azkar.AzkarActivity
import com.qibla.muslimday.app2025.ui.duas.DuasActivity
import com.qibla.muslimday.app2025.ui.mosque.MosqueActivity
import com.qibla.muslimday.app2025.ui.mosque.MosqueViewModel
import com.qibla.muslimday.app2025.ui.quran.QuranViewModel
import com.qibla.muslimday.app2025.ui.ramadan.modelBackup.PrayerTimeBackup
import com.qibla.muslimday.app2025.ui.settings.SettingActivity
import com.qibla.muslimday.app2025.ui.tasbih.TasbihActivity
import com.qibla.muslimday.app2025.ui.zakat.ZakatActivity
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Const
import com.qibla.muslimday.app2025.util.EventBusHelper
import com.qibla.muslimday.app2025.util.Global
import com.qibla.muslimday.app2025.util.SystemUtil
import com.qibla.muslimday.app2025.util.TimezoneMapper
import com.qibla.muslimday.app2025.util.UpdateEvent
import com.qibla.muslimday.app2025.view.DialogConnectionWifi
import com.qibla.muslimday.app2025.view.DialogLocation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.chrono.HijrahChronology
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val viewModel: PrayTimeViewModel by viewModels()

    private val viewModelQuran: QuranViewModel by viewModels()
    private val viewModelMosque: MosqueViewModel by viewModels()

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var times: ArrayList<String> = arrayListOf()

    private lateinit var geocoder: Geocoder

    private var targetTimeMillis = 0L

    private var timeRemaining = 0L

    private var nextTime: String? = null
    private var minDifference = Long.MAX_VALUE
    private var position = -1

    private var address = ""
    private var isCheckShowDialogTarawih = true
    private var dateArabian = ""
    private var listPrayTime = mutableListOf<PrayTimeEntity>()

    private val listSurah = arrayListOf<QuranEntity>()

    private val listJuz = arrayListOf<QuranEntity>()

    private lateinit var alarmManager: AlarmManager

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private val REQUEST_CODE_SYSTEM_ALERT_WINDOW = 11

    private lateinit var imgSwitchLocation: ImageView
    private lateinit var imgSwitchNotification: ImageView
    private lateinit var imgSwitchAppearOnTOp: ImageView
    private lateinit var frAds: FrameLayout

    private var timmer: CountDownTimer? = null


    private var isLoading = false

    private lateinit var animation: AlphaAnimation

    private lateinit var handler: Handler

    private lateinit var runnable: Runnable
    private var open = ""
    private lateinit var btnLocation: AppCompatButton
    private lateinit var btnNotification: AppCompatButton
    private lateinit var btnAppearOnTop: AppCompatButton
    private lateinit var tvDesLocation: TextView
    private lateinit var tvDesNotification: TextView
    private lateinit var tvDesAppearOnTop: TextView
    private lateinit var tvDesLlLocation: TextView
    private lateinit var tvDesLlNotification: TextView
    private lateinit var tvDesLlAppearOnTop: TextView

    private lateinit var llLocationPermission: LinearLayout
    private lateinit var llNotificationPermission: LinearLayout
    private lateinit var llAppearOnTopPermission: LinearLayout

    private lateinit var llLocation: ConstraintLayout
    private lateinit var llNotification: ConstraintLayout
    private lateinit var llAppearOnTop: ConstraintLayout

    private lateinit var btnClose: ImageButton

    private var isRequestLocation = false
    private var isRequestNotification = false
    private var isRequestAppearOnTop = false

    companion object {
        var isCheck = false
    }

    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    val startActivityDuasResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { _ ->

        if (ConsentHelper.getInstance(context).canRequestAds()) {
            showAdsNativeHome()
        }
    }

    val startActivityAzkarResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { _ ->

        if (ConsentHelper.getInstance(context).canRequestAds()) {
            showAdsNativeHome()
        }
    }

    val startActivityNativeAll = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { _ ->

        if (ConsentHelper.getInstance(context).canRequestAds()) {
            showAdsNativeHome()
        }


    }

    val startActivityReloadNativeHome = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { _ ->
        if (ConsentHelper.getInstance(context).canRequestAds()) {
            showAdsNativeHome()
        }
    }

    override fun onResume() {
        super.onResume()
        if(AdsInter.isLoadNativeHome&&Admob.getInstance().isLoadFullAds){
            binding.frAds.visible(true)
        }else{
            binding.frAds.visible(false)

        }
        AppOpenManager.getInstance().enableAppResumeWithActivity(MainActivity::class.java)
        if (context?.hasNetworkConnection() == false && Const.isCheckWifi == 2) {
            showDialogWifi(true)
            Const.isCheckWifi = 1
        } else if (context?.hasNetworkConnection() == true) {
            initView()
            initDateArabian()
            Const.isCheckWifi = 1
        }

        if (isCheck) {
            initView()
            initDateArabian()
            isCheck = false
        }
        initData()
    }

    private fun isGratedPermissionAll(): Boolean {
        val act = (requireActivity() as? MainActivity)
        val isGrantedLocation = act?.checkPermissionLocation() ?: true
        val isGrantedOverlay = act?.checkSystemAlertWindowPermission() ?: true
        val isGrantedNotify = act?.checkPermissionNotification() ?: true
        return isGrantedLocation && isGrantedNotify && isGrantedOverlay
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        bottomSheetDialog = BottomSheetDialog(requireActivity(), R.style.CustomAlertBottomSheet)

        handler = Handler(Looper.getMainLooper())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animation =
            AlphaAnimation(1f, 0.2f) //to change visibility from visible to invisible

        animation.duration = 700 //1 second duration for each animation cycle

        animation.interpolator = LinearInterpolator()
        animation.repeatCount = Animation.INFINITE //repeating indefinitely

        animation.repeatMode = Animation.REVERSE //animation will start from end point once ended.
//        if (!preferenceHelper.showDialogTarawih())
//            showDialogTarawih()
        initView()
        initEvent()
        initDateArabian()
        initData()
    }

    private fun initEvent() {

        binding.lnAzkar.setOnClickListener {
            AdsInter.loadInter(
                context = requireActivity(),
                adsId = getString(R.string.inter_home),
                isShow = AdsInter.isLoadInterHome,
                action = {
                    startActivityAzkarResult.launch(
                        Intent(
                            requireActivity(),
                            AzkarActivity::class.java
                        )
                    )
                }
            )
        }

        binding.lnZakat.setOnClickListener {
            AdsInter.loadInter(
                context = requireActivity(),
                adsId = getString(R.string.inter_home),
                isShow = AdsInter.isLoadInterHome,
                action = {
                    startActivityNativeAll.launch(
                        Intent(
                            requireActivity(),
                            ZakatActivity::class.java
                        )
                    )
                }
            )
        }
        binding.lnDuas.setOnClickListener {
            AdsInter.loadInter(
                context = requireActivity(),
                adsId = getString(R.string.inter_home),
                isShow = AdsInter.isLoadInterHome,
                action = {
                    startActivityDuasResult.launch(
                        Intent(
                            requireActivity(),
                            DuasActivity::class.java
                        )
                    )
                }
            )
        }

        binding.lnAllah.setOnClickListener {
            AdsInter.loadInter(
                context = requireActivity(),
                adsId = getString(R.string.inter_home),
                isShow = AdsInter.isLoadInterHome,
                action = {
                    startActivityNativeAll.launch(
                        Intent(
                            requireActivity(),
                            Allah99NameActivity::class.java
                        )
                    )
                }
            )

        }


        binding.lnTasbih.setOnClickListener {
            AdsInter.loadInter(
                context = requireActivity(),
                adsId = getString(R.string.inter_home),
                isShow = AdsInter.isLoadInterHome,
                action = {
                    startActivityNativeAll.launch(
                        Intent(
                            requireActivity(),
                            TasbihActivity::class.java
                        )
                    )
                }
            )
        }

        binding.btnSetting.setOnClickListener {
            if (!isGratedPermissionAll()) {
                showBottomSheetPermission(
                    getString(R.string.string_des_location_home),
                    getString(R.string.string_des_notification_home),
                    getString(R.string.string_des_appear_on_top_home),
                    "home"
                )
                return@setOnClickListener
            }
            AdsInter.loadInter(
                context = requireActivity(),
                adsId = getString(R.string.inter_home),
                isShow = AdsInter.isLoadInterHome,
                action = {
                    startActivityNativeAll.launch(
                        Intent(
                            requireActivity(),
                            SettingActivity::class.java
                        )
                    )
                }
            )
        }

        binding.lnMosque.setOnClickListener {
            if (checkPermissionLocation()) {
                AdsInter.loadInter(
                    context = requireActivity(),
                    adsId = getString(R.string.inter_home),
                    isShow = AdsInter.isLoadInterHome,
                    action = {
                        startActivityNativeAll.launch(
                            Intent(
                                requireActivity(),
                                MosqueActivity::class.java
                            )
                        )
                    }
                )

            } else {
                open = "islam_map"
                showBottomSheetPermission(
                    getString(R.string.string_des_location_islam_map),
                    "",
                    "",
                    "islam_map"
                )
            }
        }

        binding.ctrSum.setOnClickListener {
            if (!checkPermissionLocation() || !checkPermissionNotification() || !checkSystemAlertWindowPermission()) {
                showBottomSheetPermission(
                    getString(R.string.string_des_location_home),
                    getString(R.string.string_des_notification_home),
                    getString(R.string.string_des_appear_on_top_home),
                    "home"
                )
            }
        }

        binding.imgLocationNoData.setOnClickListener {
            if (!checkPermissionLocation() || !checkPermissionNotification() || !checkSystemAlertWindowPermission()) {
                showBottomSheetPermission(
                    getString(R.string.string_des_location_home),
                    getString(R.string.string_des_notification_home),
                    getString(R.string.string_des_appear_on_top_home),
                    "home"
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBusHelper.register(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBusHelper.unregister(this)
        timmer?.cancel()
        timmer = null
        handler.removeCallbacksAndMessages(null)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: UpdateEvent) {
        Log.d("ntt", "onEvent: $event")
        if (event.name == "Ads") {
            if (ConsentHelper.getInstance(requireActivity()).canRequestAds()) {
                showAdsNativeHome()
            }

            if (AdsInter.isShowRate) {
                binding.frAds.visibility = View.GONE
            }
//            else {
//                binding.frAds.visibility = View.VISIBLE
//            }
        }

        if (event.name == "linearHome") {
            Log.d("ntt", "onEvent: Home")

            if (ConsentHelper.getInstance(requireActivity()).canRequestAds()) {
                showAdsNativeHome()
            }

            if (!checkPermissionLocation() || !checkPermissionNotification() || !checkSystemAlertWindowPermission()) {
                showBottomSheetPermission(
                    getString(R.string.string_des_location_home),
                    getString(R.string.string_des_notification_home),
                    getString(R.string.string_des_appear_on_top_home),
                    "home"
                )
            }
        } else if (event.name == "linearSalah") {
            Log.d("ntt", "onEvent: Salah")
            if (!checkPermissionLocation() || !checkPermissionNotification() || !checkSystemAlertWindowPermission()) {
                showBottomSheetPermission(
                    getString(R.string.string_des_location_home),
                    getString(R.string.string_des_notification_home),
                    getString(R.string.string_des_appear_on_top_home),
                    "home"
                )
            }
        } else if (event.name == "linearSetting") {
            Log.d("ntt", "onEvent: Setting")
            if (!checkPermissionLocation() || !checkPermissionNotification() || !checkSystemAlertWindowPermission()) {
                showBottomSheetPermission(
                    getString(R.string.string_des_location_home),
                    getString(R.string.string_des_notification_home),
                    getString(R.string.string_des_appear_on_top_home),
                    "home"
                )
            }
        } else if (event.name == "linearQuran") {
            Log.d("ntt", "onEvent: Quran")
            if (!checkPermissionLocation() || !checkPermissionNotification() || !checkSystemAlertWindowPermission()) {
                showBottomSheetPermission(
                    getString(R.string.string_des_location_home),
                    getString(R.string.string_des_notification_home),
                    getString(R.string.string_des_appear_on_top_home),
                    "home"
                )
            }
        }

        if (event.name == "hide_ads") {
            binding.frAds.visibility = View.GONE
        } else if (event.name == "show_ads") {
            binding.frAds.visibility = View.VISIBLE
            AdsInter.isShowRate = false
        }

    }


    override fun initView() {

        if (!preferenceHelper.getBoolean(PreferenceHelper.IS_CHECK_PERMISSION_FIRST)) {
            addAutoStartup()
            isCheckPermissionXiaomi()
            preferenceHelper.setBoolean(PreferenceHelper.IS_CHECK_PERMISSION_FIRST, true)
        }
        AdsInter.onAdsDataClick = {
            when (it) {
                AdsInter.CLICK_NATIVE_HOME -> showAdsNativeHome()
            }
        }
        Global.intDay = preferenceHelper.getInt(ISLAMIC_CALENDAR)

        binding.tvInfoLocation.isSelected = true

        activity?.let {
            geocoder = Geocoder(it, Locale.getDefault())
        }

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity());

        if (activity?.hasNetworkConnection() == true) {
            if (checkPermissionLocation() && checkPermissionNotification() && checkSystemAlertWindowPermission()) {
                getCurrentLocation()
            } else {
                binding.tvDayIslamic.text = ""
                binding.tvDaySolar.text = ""
                binding.tvTimeNextPray.text = ""

                binding.llInfoLocation.visibility = View.GONE
                binding.imgLocationNoData.visibility = View.VISIBLE

                if (!preferenceHelper.getBoolean(PreferenceHelper.IS_SHOW_DIALOG_TARAWIH)) {
                    if (activity != null) {
                        Log.d("ntt", "initView: Hide Banner")
//                        EventBus.getDefault().post(UpdateEvent("hideBanner"))
//                        (activity as MainActivity).hideBanner()
//                        showDialogTarawih()
                    }
                }

            }
        } else {
            (activity as MainActivity).hideLoading()
            if (!preferenceHelper.getIsEnableConnectWifi()) {
                showDialogWifi(false)
            } else {
                showDialogWifi(true)
            }

        }
    }


    private fun showBottomSheetPermission(
        desLocation: String,
        desNotification: String,
        desAppearOnTop: String,
        open: String,
    ) {
        val viewDialog: View =
            layoutInflater.inflate(R.layout.layout_bottom_sheet_permission, null)

        bottomSheetDialog.setContentView(viewDialog)

        bottomSheetDialog.setCanceledOnTouchOutside(false);

        val behavior = bottomSheetDialog.behavior

        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // Xử lý sự kiện thay đổi trạng thái của bottom sheet
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Xử lý khi bottom sheet được trượt
            }
        }
        bottomSheetDialog.behavior.addBottomSheetCallback(bottomSheetCallback)

        context?.let { SystemUtil.setLanguage(it) }

        binding.frAds.visibility = View.GONE

        btnLocation = viewDialog.findViewById(R.id.btn_location)
        btnNotification = viewDialog.findViewById(R.id.btn_notification)
        btnAppearOnTop = viewDialog.findViewById(R.id.btn_appear_on_top)

        tvDesLocation = viewDialog.findViewById(R.id.tv_des_location)
        tvDesNotification = viewDialog.findViewById(R.id.tv_des_notification)
        tvDesAppearOnTop = viewDialog.findViewById(R.id.tv_des_appear_on_top)

        tvDesLlLocation = viewDialog.findViewById(R.id.tv_des_ll_location)
        tvDesLlNotification = viewDialog.findViewById(R.id.tv_des_ll_notification)
        tvDesLlAppearOnTop = viewDialog.findViewById(R.id.tv_des_ll_appear_on_top)

        llLocationPermission =
            viewDialog.findViewById(R.id.ll_location_permission)
        llNotificationPermission =
            viewDialog.findViewById(R.id.ll_notification_permission)
        llAppearOnTopPermission =
            viewDialog.findViewById(R.id.ll_appear_on_top_permission)

        llLocation = viewDialog.findViewById(R.id.ll_location)
        llNotification = viewDialog.findViewById(R.id.ll_notification)
        llAppearOnTop = viewDialog.findViewById(R.id.ll_appear_on_top)

        imgSwitchLocation = viewDialog.findViewById(R.id.img_switch_location)
        imgSwitchNotification = viewDialog.findViewById(R.id.img_switch_notification)
        imgSwitchAppearOnTOp = viewDialog.findViewById(R.id.img_switch_appear_on_top)

        btnClose = viewDialog.findViewById(R.id.btn_close)

        btnLocation.isSelected = true
        btnNotification.isSelected = true
        btnAppearOnTop.isSelected = true

        tvDesLlLocation.isSelected = true
        tvDesLlNotification.isSelected = true
        tvDesLlAppearOnTop.isSelected = true

        frAds = viewDialog.findViewById(R.id.fr_ads)

        showAdsNativeDialogPermission(frAds)

        if (Build.VERSION.SDK_INT > 32) {
            btnNotification.visibility = View.VISIBLE
            isRequestNotification = true
//            llNotificationPermission.visibility = View.GONE
        } else {
            btnNotification.visibility = View.GONE
            isRequestNotification = false
//            llNotificationPermission.visibility = View.GONE
        }

        if (open == "quibla" || open == "islam_map") {

            tvDesLocation.text = desLocation

            isRequestLocation = true
            isRequestLocation = false
            isRequestAppearOnTop = false

            btnLocation.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)
            btnNotification.visibility = View.GONE
            btnAppearOnTop.visibility = View.GONE

            llLocationPermission.visibility = View.VISIBLE
            llNotificationPermission.visibility = View.GONE
            llAppearOnTopPermission.visibility = View.GONE

            llLocation.visibility = View.VISIBLE
            llNotification.visibility = View.GONE
            llAppearOnTop.visibility = View.GONE
        } else {

            isRequestLocation = true
            isRequestAppearOnTop = true

            tvDesLocation.text = desLocation
            tvDesNotification.text = desNotification
            tvDesAppearOnTop.text = desAppearOnTop

            btnLocation.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)
            btnNotification.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
            btnAppearOnTop.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)

            llLocationPermission.visibility = View.VISIBLE
            llNotificationPermission.visibility = View.GONE
            llAppearOnTopPermission.visibility = View.GONE

            llLocation.visibility = View.VISIBLE
            llNotification.visibility = View.GONE
            llAppearOnTop.visibility = View.GONE
        }

        runnable = Runnable {
            if (imgSwitchLocation.isEnabled && llLocationPermission.visibility == View.VISIBLE) {
                imgSwitchLocation.startAnimation(animation)
            } else if (imgSwitchNotification.isEnabled && llNotificationPermission.visibility == View.VISIBLE) {
                imgSwitchNotification.startAnimation(animation)
            } else if (imgSwitchAppearOnTOp.isEnabled && llAppearOnTopPermission.visibility == View.VISIBLE) {
                imgSwitchAppearOnTOp.startAnimation(animation)
            }
        }

        handler.postDelayed(runnable, 5000L)


        if (checkPermissionLocation()) {
            imgSwitchLocation.setImageResource(R.drawable.ic_switch_active)
            imgSwitchLocation.isEnabled = false

            btnLocation.visibility = View.GONE

            if (!checkPermissionNotification()) {
                imgSwitchNotification.clearAnimation()

                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 5000L)

                btnLocation.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                btnNotification.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)
                btnAppearOnTop.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)

                llLocationPermission.visibility = View.GONE
                llNotificationPermission.visibility = View.VISIBLE
                llAppearOnTopPermission.visibility = View.GONE

                llLocation.visibility = View.GONE
                llNotification.visibility = View.VISIBLE
                llAppearOnTop.visibility = View.GONE
            } else if (!checkSystemAlertWindowPermission()) {
                imgSwitchAppearOnTOp.clearAnimation()

                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 5000L)

                btnLocation.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                btnNotification.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                btnAppearOnTop.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)

                llLocationPermission.visibility = View.GONE
                llNotificationPermission.visibility = View.GONE
                llAppearOnTopPermission.visibility = View.VISIBLE

                llLocation.visibility = View.GONE
                llNotification.visibility = View.GONE
                llAppearOnTop.visibility = View.VISIBLE
            }
        } else {
            imgSwitchLocation.setImageResource(R.drawable.ic_switch_inactive)
            imgSwitchLocation.isEnabled = true
        }

        if (checkPermissionNotification()) {
            imgSwitchNotification.setImageResource(R.drawable.ic_switch_active)
            imgSwitchNotification.isEnabled = false

            btnNotification.visibility = View.GONE

            if (!checkPermissionLocation()) {
                imgSwitchLocation.clearAnimation()

                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 5000L)

                btnLocation.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)
                btnNotification.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                btnAppearOnTop.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)

                llLocationPermission.visibility = View.VISIBLE
                llNotificationPermission.visibility = View.GONE
                llAppearOnTopPermission.visibility = View.GONE

                llLocation.visibility = View.VISIBLE
                llNotification.visibility = View.GONE
                llAppearOnTop.visibility = View.GONE
            } else if (!checkSystemAlertWindowPermission()) {
                imgSwitchAppearOnTOp.clearAnimation()

                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 5000L)

                btnLocation.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                btnNotification.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                btnAppearOnTop.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)

                llLocationPermission.visibility = View.GONE
                llNotificationPermission.visibility = View.GONE
                llAppearOnTopPermission.visibility = View.VISIBLE

                llLocation.visibility = View.GONE
                llNotification.visibility = View.GONE
                llAppearOnTop.visibility = View.VISIBLE
            }
        } else {
            imgSwitchNotification.setImageResource(R.drawable.ic_switch_inactive)
            imgSwitchNotification.isEnabled = true
        }

        if (checkSystemAlertWindowPermission()) {
            imgSwitchAppearOnTOp.setImageResource(R.drawable.ic_switch_active)
            imgSwitchAppearOnTOp.isEnabled = false

            btnAppearOnTop.visibility = View.GONE

            if (!checkPermissionLocation()) {

                imgSwitchLocation.clearAnimation()

                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 5000L)

                btnLocation.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)
                btnNotification.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                btnAppearOnTop.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)

                llLocationPermission.visibility = View.VISIBLE
                llNotificationPermission.visibility = View.GONE
                llAppearOnTopPermission.visibility = View.GONE

                llLocation.visibility = View.VISIBLE
                llNotification.visibility = View.GONE
                llAppearOnTop.visibility = View.GONE
            } else if (!checkPermissionNotification()) {
                imgSwitchNotification.clearAnimation()

                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 5000L)

                btnLocation.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                btnNotification.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)
                btnAppearOnTop.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)

                llLocationPermission.visibility = View.GONE
                llNotificationPermission.visibility = View.VISIBLE
                llAppearOnTopPermission.visibility = View.GONE

                llLocation.visibility = View.GONE
                llNotification.visibility = View.VISIBLE
                llAppearOnTop.visibility = View.GONE
            }
        } else {
            imgSwitchAppearOnTOp.setImageResource(R.drawable.ic_switch_inactive)
            imgSwitchAppearOnTOp.isEnabled = true
        }

        btnLocation.setOnClickListener {
            Log.d("ntt", "showBottomSheetPermission: Click Location")

            imgSwitchLocation.clearAnimation()

            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 5000L)

            btnLocation.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)
            btnNotification.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
            btnAppearOnTop.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)

            llLocationPermission.visibility = View.VISIBLE
            llNotificationPermission.visibility = View.GONE
            llAppearOnTopPermission.visibility = View.GONE

            llLocation.visibility = View.VISIBLE
            llNotification.visibility = View.GONE
            llAppearOnTop.visibility = View.GONE
        }

        btnNotification.setOnClickListener {
            Log.d("ntt", "showBottomSheetPermission: Click Notification")

            imgSwitchNotification.clearAnimation()

            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 5000L)

            btnLocation.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
            btnNotification.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)
            btnAppearOnTop.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)

            llLocationPermission.visibility = View.GONE
            llNotificationPermission.visibility = View.VISIBLE
            llAppearOnTopPermission.visibility = View.GONE

            llLocation.visibility = View.GONE
            llNotification.visibility = View.VISIBLE
            llAppearOnTop.visibility = View.GONE
        }

        btnAppearOnTop.setOnClickListener {
            Log.d("ntt", "showBottomSheetPermission: Click AppearOnTOp")

            imgSwitchAppearOnTOp.clearAnimation()

            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 5000L)

            btnLocation.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
            btnNotification.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
            btnAppearOnTop.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)

            llLocationPermission.visibility = View.GONE
            llNotificationPermission.visibility = View.GONE
            llAppearOnTopPermission.visibility = View.VISIBLE

            llLocation.visibility = View.GONE
            llNotification.visibility = View.GONE
            llAppearOnTop.visibility = View.VISIBLE
        }

        imgSwitchLocation.setOnClickListener {
            imgSwitchLocation.clearAnimation()
            requestPermissionLocation()

        }

        imgSwitchNotification.setOnClickListener {
            imgSwitchNotification.clearAnimation()
            requestPermissionNotification()

        }

        imgSwitchAppearOnTOp.setOnClickListener {
            imgSwitchAppearOnTOp.clearAnimation()
            requestPermissionAlertWindow()

        }

        btnClose.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()

        bottomSheetDialog.setOnDismissListener {
            Log.d("ntt", "showBottomSheetPermission: $isLoading")
            if (!isLoading) {
                binding.frAds.visibility = View.VISIBLE
            }

            handler.removeCallbacks(runnable)
        }

    }

    private fun requestPermissionNotification() {

        binding.frAds.visibility = View.GONE

        if (this::frAds.isInitialized) {
            frAds.visibility = View.GONE
        }

        permissionNotificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    val permissionNotificationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        if (this::frAds.isInitialized) {
            frAds.visibility = View.VISIBLE
        }

        if (
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (this::imgSwitchNotification.isInitialized) {

                imgSwitchNotification.setImageResource(R.drawable.ic_switch_inactive)
                imgSwitchNotification.isEnabled = true

                if (this::runnable.isInitialized) {
                    handler.postDelayed(runnable, 5000L)
                }
            }
        } else {
            if (this::imgSwitchNotification.isInitialized) {

                imgSwitchNotification.setImageResource(R.drawable.ic_switch_active)
                imgSwitchNotification.isEnabled = false
            }

            if (this::bottomSheetDialog.isInitialized && bottomSheetDialog.isShowing) {

                if (isRequestLocation && !checkPermissionLocation()) {
                    imgSwitchLocation.clearAnimation()

                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable, 5000L)

                    btnLocation.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)
                    btnNotification.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                    btnAppearOnTop.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)

                    llLocationPermission.visibility = View.VISIBLE
                    llNotificationPermission.visibility = View.GONE
                    llAppearOnTopPermission.visibility = View.GONE

                    llLocation.visibility = View.VISIBLE
                    llNotification.visibility = View.GONE
                    llAppearOnTop.visibility = View.GONE
                } else if (isRequestAppearOnTop && !checkSystemAlertWindowPermission()) {
                    imgSwitchAppearOnTOp.clearAnimation()

                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable, 5000L)

                    btnLocation.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                    btnNotification.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                    btnAppearOnTop.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)

                    llLocationPermission.visibility = View.GONE
                    llNotificationPermission.visibility = View.GONE
                    llAppearOnTopPermission.visibility = View.VISIBLE

                    llLocation.visibility = View.GONE
                    llNotification.visibility = View.GONE
                    llAppearOnTop.visibility = View.VISIBLE
                }
            }

            if (checkPermissionNotification() && checkSystemAlertWindowPermission() && checkPermissionLocation()) {
                if (bottomSheetDialog.isShowing) {
                    bottomSheetDialog.dismiss()
                }
                getCurrentLocation()
            }
        }

    }

    val permissionLocationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->

//        if (this::frAds.isInitialized) {
        frAds.visibility = View.VISIBLE
//        }

        if (
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (this::imgSwitchLocation.isInitialized) {

                imgSwitchLocation.setImageResource(R.drawable.ic_switch_inactive)
                imgSwitchLocation.isEnabled = true

                if (this::runnable.isInitialized) {
                    handler.postDelayed(runnable, 5000L)
                }

            }


        } else {
            if (this::imgSwitchLocation.isInitialized) {

                imgSwitchLocation.setImageResource(R.drawable.ic_switch_active)
                imgSwitchLocation.isEnabled = false
            }

            if (open == "islam_map" || open == "quibla") {
                if (bottomSheetDialog.isShowing) {
                    bottomSheetDialog.dismiss()
                }
            } else {

                if (this::bottomSheetDialog.isInitialized && bottomSheetDialog.isShowing) {
                    if (isRequestNotification && !checkPermissionNotification()) {
                        imgSwitchNotification.clearAnimation()

                        handler.removeCallbacks(runnable)
                        handler.postDelayed(runnable, 5000L)

                        btnLocation.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                        btnNotification.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)
                        btnAppearOnTop.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)

                        llLocationPermission.visibility = View.GONE
                        llNotificationPermission.visibility = View.VISIBLE
                        llAppearOnTopPermission.visibility = View.GONE

                        llLocation.visibility = View.GONE
                        llNotification.visibility = View.VISIBLE
                        llAppearOnTop.visibility = View.GONE
                    } else if (isRequestAppearOnTop && !checkSystemAlertWindowPermission()) {
                        imgSwitchAppearOnTOp.clearAnimation()

                        handler.removeCallbacks(runnable)
                        handler.postDelayed(runnable, 5000L)

                        btnLocation.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                        btnNotification.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                        btnAppearOnTop.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)

                        llLocationPermission.visibility = View.GONE
                        llNotificationPermission.visibility = View.GONE
                        llAppearOnTopPermission.visibility = View.VISIBLE

                        llLocation.visibility = View.GONE
                        llNotification.visibility = View.GONE
                        llAppearOnTop.visibility = View.VISIBLE
                    }
                }
            }

            if (checkPermissionNotification() && checkSystemAlertWindowPermission() && checkPermissionLocation()) {
                if (bottomSheetDialog.isShowing) {
                    bottomSheetDialog.dismiss()
                }
                getCurrentLocation()
            }
        }

    }

    val permissionAppearOnTopLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { isGranted ->
        if (this::imgSwitchAppearOnTOp.isInitialized) {
            if (checkSystemAlertWindowPermission()) {
                imgSwitchAppearOnTOp.setImageResource(R.drawable.ic_switch_active)
                imgSwitchAppearOnTOp.isEnabled = false

                if (this::bottomSheetDialog.isInitialized && bottomSheetDialog.isShowing) {

                    if (isRequestLocation && !checkPermissionLocation()) {
                        imgSwitchLocation.clearAnimation()

                        handler.removeCallbacks(runnable)
                        handler.postDelayed(runnable, 5000L)

                        btnLocation.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)
                        btnNotification.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                        btnAppearOnTop.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)

                        llLocationPermission.visibility = View.VISIBLE
                        llNotificationPermission.visibility = View.GONE
                        llAppearOnTopPermission.visibility = View.GONE

                        llLocation.visibility = View.VISIBLE
                        llNotification.visibility = View.GONE
                        llAppearOnTop.visibility = View.GONE
                    } else if (isRequestNotification && !checkPermissionNotification()) {
                        imgSwitchNotification.clearAnimation()

                        handler.removeCallbacks(runnable)
                        handler.postDelayed(runnable, 5000L)

                        btnLocation.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)
                        btnNotification.setBackgroundResourceEnable(R.drawable.shape_gradient_color_0e8c62_008040_corner_20)
                        btnAppearOnTop.setBackgroundResourceDisable(R.drawable.shape_bg_color_ebeff2_corner_16)

                        llLocationPermission.visibility = View.GONE
                        llNotificationPermission.visibility = View.VISIBLE
                        llAppearOnTopPermission.visibility = View.GONE

                        llLocation.visibility = View.GONE
                        llNotification.visibility = View.VISIBLE
                        llAppearOnTop.visibility = View.GONE
                    }
                }
            } else {
                imgSwitchAppearOnTOp.setImageResource(R.drawable.ic_switch_inactive)
                imgSwitchAppearOnTOp.isEnabled = true

                if (this::runnable.isInitialized) {
                    handler.postDelayed(runnable, 5000L)
                }
            }
        }

        if (checkPermissionNotification() && checkPermissionLocation() && checkSystemAlertWindowPermission()) {
            if (bottomSheetDialog.isShowing) {
                bottomSheetDialog.dismiss()
            }
            getCurrentLocation()
        }

    }

    private fun requestPermissionAlertWindow() {

        binding.frAds.visibility = View.GONE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {

                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${activity?.packageName}")
                )

                AppOpenManager.getInstance()
                    .disableAppResumeWithActivity(MainActivity::class.java)

                permissionAppearOnTopLauncher.launch(intent)

//                startActivityForResult(intent, REQUEST_CODE_SYSTEM_ALERT_WINDOW)
            }
        }
    }

    private fun AppCompatButton.setBackgroundResourceDisable(res: Int) {
        this.setBackgroundResource(res)
        this.setTextColor(requireActivity().getColor(R.color.color_8d99a6))
    }

    private fun AppCompatButton.setBackgroundResourceEnable(res: Int) {
        this.setBackgroundResource(res)
        this.setTextColor(requireActivity().getColor(R.color.white))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SYSTEM_ALERT_WINDOW && resultCode == RESULT_OK) {
            if (this::imgSwitchAppearOnTOp.isInitialized) {
                if (checkSystemAlertWindowPermission()) {
                    imgSwitchAppearOnTOp.setImageResource(R.drawable.ic_switch_active)
                    imgSwitchAppearOnTOp.isEnabled = false
                } else {
                    imgSwitchAppearOnTOp.setImageResource(R.drawable.ic_switch_inactive)
                    imgSwitchAppearOnTOp.isEnabled = true
                }
            }

            if (checkPermissionNotification() && checkPermissionLocation() && checkSystemAlertWindowPermission()) {
                getCurrentLocation()
            }
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        EventBusHelper.unregister(this)
//    }

    private fun initData() {
        val randomInt = Random.nextInt(1, 50)
        viewModel.getAzkarIdRandom(randomInt).observe(viewLifecycleOwner) {
            if (it == null) {
                Global.strAzkar =
                    "لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ ، سُبْحَانَ اللهِ وَالْحَمْدُ لِلهِ ، وَلَا إِلَهَ إِلَّا اللهُ وَاللهُ أَكْـبَرُ ، وَلَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللهِ الْعَلِيِّ الْعَظِيمِ رَبِّ اغْفِرْ لِي"
            } else {
                Global.strAzkar = it.textArabic
            }

        }
        viewModel.getDuasIdRandom(randomInt).observe(viewLifecycleOwner) {
            if (it == null) {
                Global.strDuas =
                    "لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ ، سُبْحَانَ اللهِ وَالْحَمْدُ لِلهِ ، وَلَا إِلَهَ إِلَّا اللهُ وَاللهُ أَكْـبَرُ ، وَلَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللهِ الْعَلِيِّ الْعَظِيمِ رَبِّ اغْفِرْ لِي"
            } else {
                Global.strDuas = it.fontName
            }

        }
    }

    private fun showDialogWifi(b: Boolean) {
        binding.frAds.visibility = View.GONE
        val dialogWifi = DialogConnectionWifi(requireActivity(), b)
        dialogWifi.init(
            requireActivity(), object : DialogConnectionWifi.OnPress {
                override fun cancel() {
                    dialogWifi.dismiss()
                    if (!requireActivity().hasNetworkConnection()) {
                        showDialogWifi(true)
                    }
                }

                override fun save() {
                    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                    AppOpenManager.getInstance()
                        .disableAppResumeWithActivity(MainActivity::class.java)
                    context?.startActivity(intent)
                    preferenceHelper.setIsEnableConnectWifi(true)
                    Const.isCheckWifi = 2
                    dialogWifi.dismiss()
                }
            }
        )
        try {
            dialogWifi.show()
        } catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()
        }

        dialogWifi.setOnDismissListener {
            binding.frAds.visibility = View.VISIBLE
        }
    }

    private fun checkPermissionNotification(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

    }

    private fun checkPermissionLocation(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED

    }

    private fun checkSystemAlertWindowPermission(): Boolean {
        return Settings.canDrawOverlays(requireActivity())
    }

    private fun showDialogLocation() {
        if (isAdded) {
            binding.frAds.visibility = View.GONE
            isCheckShowDialogTarawih = false
            val dialog = DialogLocation(requireActivity())
            dialog.init(
                requireActivity(), object : DialogLocation.OnPress {
                    override fun save() {
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        AppOpenManager.getInstance()
                            .disableAppResumeWithActivity(MainActivity::class.java)
                        startActivity(intent)
                        isCheck = true
                        dialog.dismiss()
                    }

                }

            )
            try {
                dialog.show()
            } catch (e: WindowManager.BadTokenException) {
                e.printStackTrace()
            }

            dialog.setOnDismissListener {
                binding.frAds.visibility = View.VISIBLE
            }
        }

    }

    private fun initDateArabian() {
        val currentDate = LocalDate.now().plusDays(Global.intDay.toLong())
        val hijriChronology = HijrahChronology.INSTANCE
        val hijriDate = HijrahDate.from(currentDate)
        // Định dạng ngày tháng Ả Rập thành chuỗi
        val formatter = DateTimeFormatter.ofPattern("yyyy M d", Locale("ar"))
        val arabicDate = hijriDate.format(formatter)
        val hijriMonth = resources.getString(
            resources.getIdentifier(
                "hijri_month_" + arabicDate.split(" ")[1],
                "string",
                requireActivity().packageName
            )
        )
        val hijriDay = arabicDate.split(" ")[2]
        val hijriYear = arabicDate.split(" ")[0]
        dateArabian = "$hijriMonth $hijriDay, $hijriYear"
        binding.tvDayIslamic.text = "($dateArabian)"
        preferenceHelper.setString(DATE_DAY, dateArabian)

    }

    private fun addAutoStartup() {
        try {
            val intent = Intent()
            val manufacturer = android.os.Build.MANUFACTURER
            when {
                "vivo".equals(manufacturer, ignoreCase = true) -> {
                    intent.component = ComponentName(
                        "com.vivo.permissionmanager",
                        "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                    )
                }

            }

            val list = requireActivity().packageManager.queryIntentActivities(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
            if (list.size > 0) {
                showGoSettingAutoStartDialog(intent)
            }
        } catch (e: Exception) {
            Log.e("exc", e.toString())
        }
    }

    private fun isActivityAvailable(
        context: Context,
        packageName: String,
        className: String
    ): Boolean {
        val intent = Intent()
        intent.component = ComponentName(packageName, className)
        val list =
            context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return list.isNotEmpty()
    }

    private fun isCheckPermissionXiaomi() {
        val packageName = "com.miui.securitycenter"
        val className = "com.miui.permcenter.permissions.PermissionsEditorActivity"
        if (isActivityAvailable(requireActivity(), packageName, className)) {
            try {
                val alertDialog =
                    androidx.appcompat.app.AlertDialog.Builder(requireActivity()).create()
                alertDialog.setCancelable(false)

                alertDialog.setMessage(getString(R.string.string_you_need_to_enable_permission_to_use_this_features))
                alertDialog.setButton(
                    -1,
                    getString(R.string.go_to_setting)
                ) { _, _ ->
                    val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
                    intent.setClassName(
                        "com.miui.securitycenter",
                        "com.miui.permcenter.permissions.PermissionsEditorActivity"
                    )
                    intent.putExtra("extra_pkgname", requireActivity().getPackageName())
                    try {
                        Log.d("ttn", "startActivity")
                        AppOpenManager.getInstance()
                            .disableAppResumeWithActivity(MainActivity::class.java)
                        startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.d("ttn", e.message.toString())
                    }
                    alertDialog.dismiss()
                }

                alertDialog.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun showGoSettingAutoStartDialog(intent: Intent) {
        val alertDialog = AlertDialog.Builder(requireActivity()).create()
        alertDialog.setCancelable(false)
        alertDialog.setMessage(getString(R.string.string_you_need_to_enable_permission_to_use_this_features))
        alertDialog.setButton(
            -1,
            getString(R.string.go_to_setting)
        ) { _, _ ->
            AppOpenManager.getInstance().disableAppResumeWithActivity(MainActivity::class.java)
            startActivity(intent)
            alertDialog.dismiss()
        }
        alertDialog.setOnDismissListener {
        }
        alertDialog.show()
    }

    private fun getCurrentLocation() {
        Log.d("ntt", "getCurrentLocation")
        (activity as MainActivity).showLoading()
        isLoading = true
        binding.llScroll.isEnabled = false
//        binding.frAds.visibility = View.GONE
        binding.llTimeNextPry.visibility = View.INVISIBLE

        binding.llInfoLocation.visibility = View.GONE
        binding.imgLocationNoData.visibility = View.VISIBLE
        setDataToday()
        if (isAdded && ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (Const.latitude != 0.0 && Const.longitude != 0.0) {
                setDataLocation(Const.latitude, Const.longitude)
            } else {
                fusedLocationClient?.lastLocation?.addOnCompleteListener {
                    if (it.isSuccessful && it.result != null) {
                        setDataLocation(it.result.latitude, it.result.longitude)
                    } else {
                        Log.d("ntt", " 1 Xử lý khi không có quyền truy cập vị trí")
                        if (activity != null && activity is MainActivity) {
                            (activity as MainActivity).hideLoading()
                        }
                        showDialogLocation()
                    }
                }
            }

        } else {
            Log.d("ntt", " 2 Xử lý khi không có quyền truy cập vị trí")

            requestPermissionLocation()
        }
    }

    private fun setDataLocation(latitude: Double, longitude: Double) {
        Const.latitude = latitude
        Const.longitude = longitude
        val date = LocalDate.now().plusDays(Global.intDay.toLong())
        // Sử dụng latitude và longitude ở đây

        binding.llInfoLocation.visibility = View.VISIBLE
        binding.imgLocationNoData.visibility = View.GONE
        displayLocationDetails(latitude, longitude)
        println("check =getAll List")
//                    getAllJuz()
//                    getAllSurah()
        if (Const.PrayTimeModel == null) {
            println("check ==== null")
            val timeId =
                TimezoneMapper.latLngToTimezoneString(
                    latitude,
                    longitude
                )

            Const.timeId = timeId
            Const.latitude = latitude
            Const.longitude = longitude

            preferenceHelper.setLatitude(latitude.toString())
            preferenceHelper.setLongitude(longitude.toString())
            preferenceHelper.setTimeId(timeId)

            viewModel.getPrayTimeNew(
                date.toString(),
                latitude,
                longitude,
                preferenceHelper.getCalculationMethod(),
                preferenceHelper.getJuristicMethod(),
            )
            activity?.let { activity ->
                viewModel.prayTimeNew.observe(viewLifecycleOwner) { prayTimeModel ->
                    lifecycleScope.launch {
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
                                listPrayTime.add(
                                    it2
                                )
                            }
                            Const.PrayTimeModelOld?.data?.timings?.Sunrise?.let { it1 ->
                                PrayTimeEntity(
                                    2,
                                    "Sunrise",
                                    it1
                                )
                            }?.let { it2 ->
                                listPrayTime.add(
                                    it2
                                )
                            }
                            Const.PrayTimeModelOld?.data?.timings?.Dhuhr?.let { it1 ->
                                PrayTimeEntity(
                                    3,
                                    "Dhuhr",
                                    it1
                                )
                            }?.let { it2 ->
                                listPrayTime.add(
                                    it2
                                )
                            }
                            Const.PrayTimeModelOld?.data?.timings?.Asr?.let { it1 ->
                                PrayTimeEntity(
                                    4,
                                    "Asr",
                                    it1
                                )
                            }?.let { it2 ->
                                listPrayTime.add(
                                    it2
                                )
                            }
                            Const.PrayTimeModelOld?.data?.timings?.Maghrib?.let { it1 ->
                                PrayTimeEntity(
                                    5,
                                    "Maghrib",
                                    it1
                                )
                            }?.let { it2 ->
                                listPrayTime.add(
                                    it2
                                )
                            }
                            Const.PrayTimeModelOld?.data?.timings?.Isha?.let { it1 ->
                                PrayTimeEntity(
                                    6,
                                    "Isha",
                                    it1
                                )
                            }?.let { it2 ->
                                listPrayTime.add(
                                    it2
                                )
                            }

//                            viewModel.getPrayTimeEntity().observe(viewLifecycleOwner) {
//                                if (it.isEmpty()) {
//                                    for (item in listPrayTime) {
//                                        viewModel.insertPrayTime(item)
//                                    }
//                                } else {
//                                    for (item in listPrayTime) {
//                                        viewModel.updateIsCheck(item.id, item.time)
//                                    }
//                                }
//                            }

                            val originalFajrTime =
                                Const.PrayTimeModel?.data?.timings?.Fajr
                            val updatedFajrTime = addMinutesToTime(
                                originalFajrTime,
                                preferenceHelper.getPrayingTimeAdjustmentFajr()
                            )

                            val originalSunriseTime =
                                Const.PrayTimeModel?.data?.timings?.Sunrise
                            val updatedSunriseTime = addMinutesToTime(
                                originalSunriseTime,
                                preferenceHelper.getPrayingTimeAdjustmentSunrise()
                            )

                            val originalDhuhrTime =
                                Const.PrayTimeModel?.data?.timings?.Dhuhr
                            val updatedDhuhrTime = addMinutesToTime(
                                originalDhuhrTime,
                                preferenceHelper.getPrayingTimeAdjustmentDhuhr()
                            )

                            val originalAsrTime =
                                Const.PrayTimeModel?.data?.timings?.Asr
                            val updatedAsrTime = addMinutesToTime(
                                originalAsrTime,
                                preferenceHelper.getPrayingTimeAdjustmentAsr()
                            )

                            val originalMaghribTime =
                                Const.PrayTimeModel?.data?.timings?.Maghrib
                            val updatedMaghribTime = addMinutesToTime(
                                originalMaghribTime,
                                preferenceHelper.getPrayingTimeAdjustmentMaghrib()
                            )

                            val originalIshaTime =
                                Const.PrayTimeModel?.data?.timings?.Isha
                            val updatedIshaTime = addMinutesToTime(
                                originalIshaTime,
                                preferenceHelper.getPrayingTimeAdjustmentIsha()
                            )

                            (activity as MainActivity).hideLoading()
                            binding.llScroll.isEnabled = true

                            Log.d("ntt", "getCurrentLocation: visible frAds")

                            isLoading = false

                            binding.llTimeNextPry.visibility = View.VISIBLE

                            if (updatedFajrTime != null) {
                                Const.PrayTimeModel?.data?.timings?.Fajr =
                                    updatedFajrTime
                            }
                            if (updatedSunriseTime != null) {
                                Const.PrayTimeModel?.data?.timings?.Sunrise =
                                    updatedSunriseTime
                            }
                            if (updatedDhuhrTime != null) {
                                Const.PrayTimeModel?.data?.timings?.Dhuhr =
                                    updatedDhuhrTime
                            }
                            if (updatedAsrTime != null) {
                                Const.PrayTimeModel?.data?.timings?.Asr = updatedAsrTime
                            }
                            if (updatedMaghribTime != null) {
                                Const.PrayTimeModel?.data?.timings?.Maghrib =
                                    updatedMaghribTime
                            }
                            if (updatedIshaTime != null) {
                                Const.PrayTimeModel?.data?.timings?.Isha =
                                    updatedIshaTime
                            }

                            Const.PrayTimeModel?.data?.timings?.Fajr?.let { it1 ->
                                times.add(
                                    it1
                                )
                            }
                            Const.PrayTimeModel?.data?.timings?.Sunrise?.let { it1 ->
                                times.add(
                                    it1
                                )
                            }
                            Const.PrayTimeModel?.data?.timings?.Dhuhr?.let { it1 ->
                                times.add(
                                    it1
                                )
                            }
                            Const.PrayTimeModel?.data?.timings?.Asr?.let { it1 ->
                                times.add(
                                    it1
                                )
                            }
                            Const.PrayTimeModel?.data?.timings?.Maghrib?.let { it1 ->
                                times.add(
                                    it1
                                )
                            }
                            Const.PrayTimeModel?.data?.timings?.Isha?.let { it1 ->
                                times.add(
                                    it1
                                )
                            }

                            calculateNextTime(times)

                            if (isAdded) {
                                setUpAlarmFajr()
                                setUpAlarmSunrise()
                                setUpAlarmDhuhr()
                                setUpAlarmAsr()
                                setUpAlarmMaghrib()
                                setUpAlarmIsha()
                            }
                        }
                    }
                }
            }

        } else {
            lifecycleScope.launch {


                println("check !@=== null")
                if (Const.isCheck) {
                    val originalFajrTime = Const.PrayTimeModelOld?.data?.timings?.Fajr
                    val updatedFajrTime = addMinutesToTime(
                        originalFajrTime,
                        preferenceHelper.getPrayingTimeAdjustmentFajr()
                    )

                    val originalSunriseTime =
                        Const.PrayTimeModelOld?.data?.timings?.Sunrise
                    val updatedSunriseTime = addMinutesToTime(
                        originalSunriseTime,
                        preferenceHelper.getPrayingTimeAdjustmentSunrise()
                    )

                    val originalDhuhrTime = Const.PrayTimeModelOld?.data?.timings?.Dhuhr
                    val updatedDhuhrTime = addMinutesToTime(
                        originalDhuhrTime,
                        preferenceHelper.getPrayingTimeAdjustmentDhuhr()
                    )

                    val originalAsrTime = Const.PrayTimeModelOld?.data?.timings?.Asr
                    val updatedAsrTime = addMinutesToTime(
                        originalAsrTime,
                        preferenceHelper.getPrayingTimeAdjustmentAsr()
                    )

                    val originalMaghribTime =
                        Const.PrayTimeModelOld?.data?.timings?.Maghrib
                    val updatedMaghribTime = addMinutesToTime(
                        originalMaghribTime,
                        preferenceHelper.getPrayingTimeAdjustmentMaghrib()
                    )

                    val originalIshaTime = Const.PrayTimeModelOld?.data?.timings?.Isha
                    val updatedIshaTime = addMinutesToTime(
                        originalIshaTime,
                        preferenceHelper.getPrayingTimeAdjustmentIsha()
                    )


                    if (updatedFajrTime != null) {
                        Const.PrayTimeModel?.data?.timings?.Fajr = updatedFajrTime
                    }
                    if (updatedSunriseTime != null) {
                        Const.PrayTimeModel?.data?.timings?.Sunrise = updatedSunriseTime
                    }
                    if (updatedDhuhrTime != null) {
                        Const.PrayTimeModel?.data?.timings?.Dhuhr = updatedDhuhrTime
                    }
                    if (updatedAsrTime != null) {
                        Const.PrayTimeModel?.data?.timings?.Asr = updatedAsrTime
                    }
                    if (updatedMaghribTime != null) {
                        Const.PrayTimeModel?.data?.timings?.Maghrib = updatedMaghribTime
                    }
                    if (updatedIshaTime != null) {
                        Const.PrayTimeModel?.data?.timings?.Isha = updatedIshaTime
                    }

                    Const.isCheck = false
                } else {
                    Const.PrayTimeModel = Const.PrayTimeModel
                }

                (activity as? MainActivity)?.hideLoading()

                binding.llTimeNextPry.visibility = View.VISIBLE
                isLoading = false

                Const.PrayTimeModel?.data?.timings?.Fajr?.let { it1 -> times.add(it1) }
                Const.PrayTimeModel?.data?.timings?.Sunrise?.let { it1 -> times.add(it1) }
                Const.PrayTimeModel?.data?.timings?.Dhuhr?.let { it1 -> times.add(it1) }
                Const.PrayTimeModel?.data?.timings?.Asr?.let { it1 -> times.add(it1) }
                Const.PrayTimeModel?.data?.timings?.Maghrib?.let { it1 -> times.add(it1) }
                Const.PrayTimeModel?.data?.timings?.Isha?.let { it1 -> times.add(it1) }

                calculateNextTime(times)

                if (isAdded) {
//                            setUpAlarmUpdateTimeFajr()
//                            setUpAlarmUpdateTimeSunrise()
//                            setUpAlarmUpdateTimeDhuhr()
//                            setUpAlarmUpdateTimeAsr()
//                            setUpAlarmUpdateTimeMaghrib()
//                            setUpAlarmUpdateTimeIsha()

                    setUpAlarmFajr()
                    setUpAlarmSunrise()
                    setUpAlarmDhuhr()
                    setUpAlarmAsr()
                    setUpAlarmMaghrib()
                    setUpAlarmIsha()
                }
            }

            if (!preferenceHelper.getBoolean(PreferenceHelper.IS_SHOW_DIALOG_TARAWIH)) {
                Log.d("ntt", "getCurrentLocation: Show dialog tarawih")
                if (activity != null) {
//                            (activity as MainActivity).hideBanner()
//                            showDialogTarawih()
                }
            }
        }
    }

    private fun setDataToday() {
        val gregorianCalendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH)
        val gregorianDate = dateFormat.format(gregorianCalendar.time)
        binding.tvDaySolar.text = gregorianDate
    }

    private fun requestPermissionLocation() {
        binding.frAds.visibility = View.GONE
        if (this::frAds.isInitialized) {
            frAds.visibility = View.GONE
        }
        permissionLocationLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }


    private fun getNamePrayTime(position: Int): String {
        return when (position) {
            0 -> "Fajr"
            1 -> "Sunrise"
            2 -> "Dhuhr"
            3 -> "Asr"
            4 -> "Maghrib"
            5 -> "Isha"
            else -> ""
        }
    }

    private fun convertToMinutes(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return hour * 60 + minute
    }

    @SuppressLint("SimpleDateFormat")
    private fun calculateNextTime(times: ArrayList<String>) {
        lifecycleScope.launch {
            val currentTime = Date()
            val formatter = SimpleDateFormat("HH:mm")

            // Chuyển thời gian hiện tại thành phút
            val currentTimeInMinutes = convertToMinutes(currentTime)

            // Tìm thời gian gần nhất sau thời gian hiện tại
            nextTime = null
            minDifference = Long.MAX_VALUE
            position = -1


            for ((index, time) in times.withIndex()) {
                val timeDate = formatter.parse(time)
                val timeInMinutes = convertToMinutes(timeDate)

                val difference = timeInMinutes - currentTimeInMinutes

                if (difference in 1 until minDifference) {
                    minDifference = difference.toLong()
                    nextTime = time
                    position = index
                }
            }

            val currentTimeMillis = System.currentTimeMillis()

            if (nextTime == null) {
                // Nếu không tìm thấy thời gian gần nhất, gán thời gian đầu tiên trong danh sách cho nextTime
                nextTime = times[0]
                position = 0
                targetTimeMillis = getTargetTimeMillisNext(nextTime!!)
                timeRemaining = targetTimeMillis - currentTimeMillis
            } else {
                targetTimeMillis = getTargetTimeMillis(nextTime!!)
                timeRemaining = targetTimeMillis - currentTimeMillis
            }
            withContext(Dispatchers.Main) {
                binding.tvTimeFair.text = Const.PrayTimeModel!!.data?.timings?.Fajr ?: ""
                binding.tvTimeSunrise.text = Const.PrayTimeModel!!.data?.timings?.Sunrise ?: ""
                binding.tvTimeDhuhr.text = Const.PrayTimeModel!!.data?.timings?.Dhuhr ?: ""
                binding.tvTimeAsr.text = Const.PrayTimeModel!!.data?.timings?.Asr ?: ""
                binding.tvTimeMaghrib.text = Const.PrayTimeModel!!.data?.timings?.Maghrib ?: ""
                binding.tvTimeIsha.text = Const.PrayTimeModel!!.data?.timings?.Isha ?: ""

                if (isAdded) {
                    when (position) {
                        0 -> {
                            setAllItemDefault()
                            binding.tvFair.setTextColor(resources.getColor(R.color.main_color))
                            binding.tvTimeFair.setTextColor(resources.getColor(R.color.main_color))
                            binding.imgFair.backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    requireActivity(),
                                    R.color.main_color
                                )
                            )
                        }

                        1 -> {
                            setAllItemDefault()
                            binding.tvSunrise.setTextColor(resources.getColor(R.color.main_color))
                            binding.tvTimeSunrise.setTextColor(resources.getColor(R.color.main_color))
                            binding.imgSunrise.backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    requireActivity(),
                                    R.color.color_bound
                                )
                            )
                        }

                        2 -> {
                            setAllItemDefault()
                            binding.tvDhuhr.setTextColor(resources.getColor(R.color.color_bound))
                            binding.tvTimeDhuhr.setTextColor(resources.getColor(R.color.color_bound))
                            binding.imgDhuhr.backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    requireActivity(),
                                    R.color.color_bound
                                )
                            )
                        }

                        3 -> {
                            setAllItemDefault()
                            binding.tvAsr.setTextColor(resources.getColor(R.color.color_bound))
                            binding.tvTimeAsr.setTextColor(resources.getColor(R.color.color_bound))
                            binding.imgAsr.backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    requireActivity(),
                                    R.color.color_bound
                                )
                            )
                        }

                        4 -> {
                            setAllItemDefault()
                            binding.tvMaghrib.setTextColor(resources.getColor(R.color.color_bound))
                            binding.tvTimeMaghrib.setTextColor(resources.getColor(R.color.color_bound))
                            binding.imgMaghrib.backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    requireActivity(),
                                    R.color.color_bound
                                )
                            )
                        }

                        5 -> {
                            setAllItemDefault()
                            binding.tvIsha.setTextColor(resources.getColor(R.color.color_bound))
                            binding.tvTimeIsha.setTextColor(resources.getColor(R.color.color_bound))
                            binding.imgIsha.backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    requireActivity(),
                                    R.color.color_bound
                                )
                            )
                        }
                    }
                }
            }

            // Tạo một CountDownTimer để đếm thời gian
            timmer?.cancel()
            timmer = object : CountDownTimer(timeRemaining, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val totalSeconds = millisUntilFinished / 1000
                    val hours = totalSeconds / 3600
                    val minutes = (totalSeconds % 3600) / 60
                    val seconds = totalSeconds % 60

                    val timeString =
                        String.format("%02d:%02d:%02d", hours, minutes, seconds)
                    binding.tvTimeNextPray.text = timeString
                }

                override fun onFinish() {
                    // Khi thời gian kết thúc, bạn có thể thực hiện hành động tương ứng
                    Log.d("ntt", "onFinish: " + times.toString())
                    calculateNextTime(times)
                }
            }.start()
        }
    }

    private fun setDefaultBgItem(tv1: TextView, tv2: TextView, img: ImageView) {
        tv1.setTextColor(resources.getColor(R.color.white))
        tv2.setTextColor(resources.getColor(R.color.white))
        img.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireActivity(),
                R.color.white
            )
        )
    }

    private fun setAllItemDefault() {
        setDefaultBgItem(binding.tvFair, binding.tvTimeFair, binding.imgFair)
        setDefaultBgItem(binding.tvSunrise, binding.tvTimeSunrise, binding.imgSunrise)
        setDefaultBgItem(binding.tvDhuhr, binding.tvTimeDhuhr, binding.imgDhuhr)
        setDefaultBgItem(binding.tvAsr, binding.tvTimeAsr, binding.imgAsr)
        setDefaultBgItem(binding.tvMaghrib, binding.tvTimeMaghrib, binding.imgMaghrib)
        setDefaultBgItem(binding.tvIsha, binding.tvTimeIsha, binding.imgIsha)
    }

    private fun getTargetTimeMillis(timeString: String): Long {
        // Chuyển đổi thời gian trong chuỗi ("HH:mm") thành mili giây từ nửa đêm
        val parts = timeString.split(":")
        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.timeInMillis
    }

    private fun getTargetTimeMillisNext(timeString: String): Long {
        // Chuyển đổi thời gian trong chuỗi ("HH:mm") thành mili giây từ nửa đêm
        val parts = timeString.split(":")
        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()

        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.timeInMillis
    }

    private fun displayLocationDetails(latitude: Double, longitude: Double) {
        viewModelMosque.getDisplayNameFromLatLon(requireContext(), latitude, longitude)
// Lắng nghe LiveData để cập nhật UI
        if (Const.address != "") {
            binding.tvInfoLocation.text = Const.address
        } else {
            try {
                viewModelMosque.name.observe(viewLifecycleOwner) { address ->
                    if (address != null) {
                        binding.tvInfoLocation.text = address
                        Const.address = address
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }


    private fun addMinutesToTime(timeString: String?, minutesToAdd: Int): String? {
        if (timeString == null) return null

        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = formatter.parse(timeString)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MINUTE, minutesToAdd)

        return formatter.format(calendar.time)
    }


    private fun setUpAlarmFajr() {
        if (preferenceHelper.getIsNotifyPrayTimeFajr()) {
            val timePrayFajr: String = Const.PrayTimeModel?.data?.timings?.Fajr.toString()

            val timePreNotification = preferenceHelper.getTimePreNotificationFajr()

            val timePrayFajrNew = calculateTimeBeforeMinutes(timePrayFajr, timePreNotification)

            val timeParts: ArrayList<String> = timePrayFajrNew.split(":") as ArrayList<String>
            if (timeParts.size == 2) {
                val hourString = timeParts[0] // Lấy giờ
                val minuteString = timeParts[1] // Lấy phút

                val hour = hourString.toInt()
                val minute = minuteString.toInt()

                val calendar = Calendar.getInstance()

                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)

                // Đặt lịch thông báo
                val intentNew = Intent(requireActivity(), PrayBroadcastReceiver::class.java)

                intentNew.putExtra("name_pray", "Fajr")
                intentNew.putExtra("time", timePrayFajr)

                val calendarNew = Calendar.getInstance()
                val currentHour = calendarNew.get(Calendar.HOUR_OF_DAY)
                val currentMinute = calendarNew.get(Calendar.MINUTE)

                if (hour < currentHour || (hour == currentHour && minute <= currentMinute)) {

                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, 0)
                    // Đặt lịch thông báo

                    val pendingIntent = PendingIntent.getBroadcast(
                        requireActivity(),
                        Const.REQUEST_CODE_ALARM_FAJR,
                        intentNew,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
                        pendingIntent
                    )

                } else {

                    // Đặt lịch thông báo
                    val pendingIntent = PendingIntent.getBroadcast(
                        requireActivity(),
                        Const.REQUEST_CODE_ALARM_FAJR,
                        intentNew,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
                        pendingIntent
                    )
                }

            } else {
                println("Chuỗi không hợp lệ.")
            }
        } else {
            val intentNew = Intent(requireActivity(), PrayBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                requireActivity(),
                Const.REQUEST_CODE_ALARM_FAJR,
                intentNew,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun setUpAlarmSunrise() {
        if (preferenceHelper.getIsNotifyPrayTimeSunrise()) {
            val timePraySunrise: String = Const.PrayTimeModel?.data?.timings?.Sunrise.toString()

            val timePreNotification = preferenceHelper.getTimePreNotificationSunrise()

            val timePraySunriseNew =
                calculateTimeBeforeMinutes(timePraySunrise, timePreNotification)

            val timeParts: ArrayList<String> =
                timePraySunriseNew.split(":") as ArrayList<String>
            if (timeParts.size == 2) {
                val hourString = timeParts[0] // Lấy giờ
                val minuteString = timeParts[1] // Lấy phút

                // Chuyển đổi thành số nguyên nếu cần
                val hour = hourString.toInt()
                val minute = minuteString.toInt()

                val calendar = Calendar.getInstance()

                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)

                // Đặt lịch thông báo
                val intentNew = Intent(requireActivity(), PrayBroadcastReceiver::class.java)

                intentNew.putExtra("name_pray", "Sunrise")
                intentNew.putExtra("time", timePraySunrise)

                val calendarNew = Calendar.getInstance()
                val currentHour = calendarNew.get(Calendar.HOUR_OF_DAY)
                val currentMinute = calendarNew.get(Calendar.MINUTE)

                if (hour < currentHour || (hour == currentHour && minute <= currentMinute)) {

                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, 0)
                    // Đặt lịch thông báo

                    val pendingIntent = PendingIntent.getBroadcast(
                        requireActivity(),
                        Const.REQUEST_CODE_ALARM_SUNRISE,
                        intentNew,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
                        pendingIntent
                    )

                } else {

                    // Đặt lịch thông báo
                    val pendingIntent = PendingIntent.getBroadcast(
                        requireActivity(),
                        Const.REQUEST_CODE_ALARM_SUNRISE,
                        intentNew,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
                        pendingIntent
                    )
                }

            } else {
                println("Chuỗi không hợp lệ.")
            }
        } else {
            val intent = Intent(requireActivity(), PrayBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                requireActivity(),
                Const.REQUEST_CODE_ALARM_SUNRISE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun setUpAlarmDhuhr() {
        if (preferenceHelper.getIsNotifyPrayTimeDhuhr()) {
            val timePrayDhuhr: String = Const.PrayTimeModel?.data?.timings?.Dhuhr.toString()

            val timePreNotification = preferenceHelper.getTimePreNotificationDhuhr()

            val timePrayDhuhrNew =
                calculateTimeBeforeMinutes(timePrayDhuhr, timePreNotification)

            val timeParts: ArrayList<String> = timePrayDhuhrNew.split(":") as ArrayList<String>
            if (timeParts.size == 2) {
                val hourString = timeParts[0] // Lấy giờ
                val minuteString = timeParts[1] // Lấy phút

                // Chuyển đổi thành số nguyên nếu cần
                val hour = hourString.toInt()
                val minute = minuteString.toInt()

                val calendar = Calendar.getInstance()

                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)

                // Đặt lịch thông báo
                val intentNew = Intent(requireActivity(), PrayBroadcastReceiver::class.java)

                intentNew.putExtra("name_pray", "Dhuhr")
                intentNew.putExtra("time", timePrayDhuhr)

                val calendarNew = Calendar.getInstance()
                val currentHour = calendarNew.get(Calendar.HOUR_OF_DAY)
                val currentMinute = calendarNew.get(Calendar.MINUTE)

                if (hour < currentHour || (hour == currentHour && minute <= currentMinute)) {

                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, 0)
                    // Đặt lịch thông báo

                    val pendingIntent = PendingIntent.getBroadcast(
                        requireActivity(),
                        Const.REQUEST_CODE_ALARM_DHUHR,
                        intentNew,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
                        pendingIntent
                    )

                } else {

                    // Đặt lịch thông báo
                    val pendingIntent = PendingIntent.getBroadcast(
                        requireActivity(),
                        Const.REQUEST_CODE_ALARM_DHUHR,
                        intentNew,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
                        pendingIntent
                    )
                }

            } else {
                println("Chuỗi không hợp lệ.")
            }
        } else {
            val intent = Intent(requireActivity(), PrayBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                requireActivity(),
                Const.REQUEST_CODE_ALARM_DHUHR,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun setUpAlarmAsr() {
        if (preferenceHelper.getIsNotifyPrayTimeAsr()) {
            val timePrayAsr: String = Const.PrayTimeModel?.data?.timings?.Asr.toString()

            val timePreNotification = preferenceHelper.getTimePreNotificationAsr()

            val timePrayAsrNew = calculateTimeBeforeMinutes(timePrayAsr, timePreNotification)

            val timeParts: ArrayList<String> = timePrayAsrNew.split(":") as ArrayList<String>
            if (timeParts.size == 2) {
                val hourString = timeParts[0] // Lấy giờ
                val minuteString = timeParts[1] // Lấy phút

                // Chuyển đổi thành số nguyên nếu cần
                val hour = hourString.toInt()
                val minute = minuteString.toInt()

                val calendar = Calendar.getInstance()

                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)

                // Đặt lịch thông báo
                val intentNew = Intent(requireActivity(), PrayBroadcastReceiver::class.java)

                intentNew.putExtra("name_pray", "Asr")
                intentNew.putExtra("time", timePrayAsr)

                val calendarNew = Calendar.getInstance()
                val currentHour = calendarNew.get(Calendar.HOUR_OF_DAY)
                val currentMinute = calendarNew.get(Calendar.MINUTE)

                if (hour < currentHour || (hour == currentHour && minute <= currentMinute)) {

                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, 0)
                    // Đặt lịch thông báo

                    val pendingIntent = PendingIntent.getBroadcast(
                        requireActivity(),
                        Const.REQUEST_CODE_ALARM_ASR,
                        intentNew,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
                        pendingIntent
                    )

                } else {

                    // Đặt lịch thông báo
                    val pendingIntent = PendingIntent.getBroadcast(
                        requireActivity(),
                        Const.REQUEST_CODE_ALARM_ASR,
                        intentNew,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
                        pendingIntent
                    )
                }

            } else {
                println("Chuỗi không hợp lệ.")
            }
        } else {
            val intent = Intent(requireActivity(), PrayBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                requireActivity(),
                Const.REQUEST_CODE_ALARM_ASR,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun setUpAlarmMaghrib() {
        if (preferenceHelper.getIsNotifyPrayTimeMaghrib()) {

            val timePrayMaghrib: String = Const.PrayTimeModel?.data?.timings?.Maghrib.toString()

            val timePreNotification = preferenceHelper.getTimePreNotificationMaghrib()

            val timePrayMaghribNew =
                calculateTimeBeforeMinutes(timePrayMaghrib, timePreNotification)

            val timeParts: ArrayList<String> =
                timePrayMaghribNew.split(":") as ArrayList<String>
            if (timeParts.size == 2) {
                val hourString = timeParts[0] // Lấy giờ
                val minuteString = timeParts[1] // Lấy phút

                // Chuyển đổi thành số nguyên nếu cần
                val hour = hourString.toInt()
                val minute = minuteString.toInt()

                val calendar = Calendar.getInstance()

                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)

                // Đặt lịch thông báo
                val intentNew = Intent(requireActivity(), PrayBroadcastReceiver::class.java)

                intentNew.putExtra("name_pray", "Maghrib")
                intentNew.putExtra("time", timePrayMaghrib)

                val calendarNew = Calendar.getInstance()
                val currentHour = calendarNew.get(Calendar.HOUR_OF_DAY)
                val currentMinute = calendarNew.get(Calendar.MINUTE)

                if (hour < currentHour || (hour == currentHour && minute <= currentMinute)) {

                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, 0)
                    // Đặt lịch thông báo

                    val pendingIntent = PendingIntent.getBroadcast(
                        requireActivity(),
                        Const.REQUEST_CODE_ALARM_MAGHRIB,
                        intentNew,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
                        pendingIntent
                    )

                } else {

                    // Đặt lịch thông báo
                    val pendingIntent = PendingIntent.getBroadcast(
                        requireActivity(),
                        Const.REQUEST_CODE_ALARM_MAGHRIB,
                        intentNew,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
                        pendingIntent
                    )
                }

            } else {
                println("Chuỗi không hợp lệ.")
            }
        } else {
            val intent = Intent(requireActivity(), PrayBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                requireActivity(),
                Const.REQUEST_CODE_ALARM_MAGHRIB,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun setUpAlarmIsha() {
        if (preferenceHelper.getIsNotifyPrayTimeIsha()) {
            val timePrayIsha: String = Const.PrayTimeModel?.data?.timings?.Isha.toString()

            val timePreNotification = preferenceHelper.getTimePreNotificationIsha()

            val timePrayIshaNew = calculateTimeBeforeMinutes(timePrayIsha, timePreNotification)

            val timeParts: ArrayList<String> = timePrayIshaNew.split(":") as ArrayList<String>
            if (timeParts.size == 2) {
                val hourString = timeParts[0] // Lấy giờ
                val minuteString = timeParts[1] // Lấy phút

                // Chuyển đổi thành số nguyên nếu cần
                val hour = hourString.toInt()
                val minute = minuteString.toInt()

                val calendar = Calendar.getInstance()

                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)

                // Đặt lịch thông báo
                val intentNew = Intent(requireActivity(), PrayBroadcastReceiver::class.java)

                intentNew.putExtra("name_pray", "Isha")
                intentNew.putExtra("time", timePrayIsha)

                val calendarNew = Calendar.getInstance()
                val currentHour = calendarNew.get(Calendar.HOUR_OF_DAY)
                val currentMinute = calendarNew.get(Calendar.MINUTE)

                if (hour < currentHour || (hour == currentHour && minute <= currentMinute)) {

                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, 0)
                    // Đặt lịch thông báo

                    val pendingIntent = PendingIntent.getBroadcast(
                        requireActivity(),
                        Const.REQUEST_CODE_ALARM_ISHA,
                        intentNew,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
                        pendingIntent
                    )

                } else {

                    // Đặt lịch thông báo
                    val pendingIntent = PendingIntent.getBroadcast(
                        requireActivity(),
                        Const.REQUEST_CODE_ALARM_ISHA,
                        intentNew,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
                        pendingIntent
                    )
                }

            } else {
                println("Chuỗi không hợp lệ.")
            }
        } else {
            val intent = Intent(requireActivity(), PrayBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                requireActivity(),
                Const.REQUEST_CODE_ALARM_ISHA,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }


    private fun calculateTimeBeforeMinutes(inputTime: String, minutesToSubtract: Int): String {
        val timeParts = inputTime.split(":")
        if (timeParts.size != 2) {
            return "Invalid time format"
        }

        val currentHour = timeParts[0].toIntOrNull() ?: 0
        val currentMinute = timeParts[1].toIntOrNull() ?: 0

        val totalMinutes = currentHour * 60 + currentMinute - minutesToSubtract

        var newHour = totalMinutes / 60
        var newMinute = totalMinutes % 60

        if (newMinute < 0) {
            newHour -= 1
            newMinute += 60
        }

        val formattedHour = String.format("%02d", newHour)
        val formattedMinute = String.format("%02d", newMinute)

        return "$formattedHour:$formattedMinute"
    }

    private fun showAdsNativeHome() {
        lifecycleScope.launch {
            if (AdsInter.isLoadNativeHome && Admob.getInstance().isLoadFullAds) {

                if (AdsInter.nativeAdsHome != null) {
                    AdsInter.nativeAdsHome?.let {
                        val adView = LayoutInflater.from(requireActivity())
                            .inflate(R.layout.layout_ads_native_update, null)
                        val nativeAdView = adView as NativeAdView
                        binding.frAds.removeAllViews()
                        binding.frAds.addView(adView)

                        Admob.getInstance().pushAdsToViewCustom(it, nativeAdView)
                    }

                } else {
                    Admob.getInstance().loadNativeAdWithAutoReloadWhenClick(
                        context,
                        getString(R.string.native_home),
                        object : NativeCallback() {
                            override fun onNativeAdLoaded(nativeAd: NativeAd) {
                                val adView = LayoutInflater.from(context)
                                    .inflate(R.layout.layout_ads_native_update, null)
                                val nativeAdView = adView as NativeAdView
                                binding.frAds.removeAllViews()
                                binding.frAds.addView(adView)
                                Admob.getInstance().pushAdsToViewCustom(nativeAd, nativeAdView)
                            }

                            override fun onAdFailedToLoad() {
                                binding.frAds.removeAllViews()
                            }

                        }
                    )
                }
            } else {
                binding.frAds.removeAllViews()
                binding.frAds.visibility = View.GONE
            }
        }
    }

    private fun showAdsNativeDialogPermission(frAds: FrameLayout) {
        lifecycleScope.launch {
            if (AdsInter.isLoadNativePermissionOpen && Admob.getInstance().isLoadFullAds) {
                Admob.getInstance().loadNativeAdWithAutoReloadWhenClick(
                    context,
                    getString(R.string.native_popup_permission),
                    object : NativeCallback() {
                        override fun onNativeAdLoaded(nativeAd: NativeAd) {
                            val adView = LayoutInflater.from(requireActivity())
                                .inflate(R.layout.layout_ads_native_update, null)
                            val nativeAdView = adView as NativeAdView
                            frAds.removeAllViews()
                            frAds.addView(adView)

                            Admob.getInstance().pushAdsToViewCustom(nativeAd, nativeAdView)

//                            AdsInter.nativeAdsPermission = null

                        }

                        override fun onAdFailedToLoad() {
                            frAds.removeAllViews()
                        }

//                        override fun onAdImpression() {
//                            super.onAdImpression()
//
//                            AdsInter.nativeAdsPermission = null
//
//                            lifecycleScope.launch {
//                                if (isAdded) {
//                                    AdsInter.loadNativePermission(requireActivity())
//                                }
//                            }
//                        }
                    }
                )

            } else {
                frAds.removeAllViews()
                frAds.visibility = View.GONE
            }
        }
        Log.d("ntt", "showAdsNativeDialogPermission: ")

        Log.d("ntt", "showAdsNativeDialogPermission: ${AdsInter.nativeAdsPermission}")
    }

    private fun getAllJuz() {
//            listJuz.clear()
            listJuz.add(
                QuranEntity(
                    1,
                    "juz",
                    1,
                    "Alaf Lam Meem",
                    "Surah Al-Faatiha 1:1 - Surah Al Baqarah 2:141"
                )
            )
            listJuz.add(
                QuranEntity(
                    2,
                    "juz",
                    2,
                    "Sayaqool",
                    "Surah Al Baqarah 2:142 - Surah Al-Baqara 2:252"
                )
            )
            listJuz.add(
                QuranEntity(
                    3,
                    "juz",
                    3,
                    "Tilkal Rusull",
                    "Surah Al-Baqara 2:253 - Surah Aal-i-Imraan 3:92"
                )
            )
            listJuz.add(
                QuranEntity(
                    4,
                    "juz",
                    4,
                    "Lan Tana Loo",
                    "Surah Aal-i-Imraan 3:93 - Surah An-Nisaa 4:23"
                )
            )
            listJuz.add(
                QuranEntity(
                    5,
                    "juz",
                    5,
                    "Wal Mohsanat",
                    "Surah An-Nisaa 4:24 - Surah An-Nisaa 4:147"
                )
            )
            listJuz.add(
                QuranEntity(
                    6,
                    "juz",
                    6,
                    "La Yuhibbullah",
                    "Surah An-Nisaa 4:148 - Surah Al-Maaida 5:81"
                )
            )
            listJuz.add(
                QuranEntity(
                    7,
                    "juz",
                    7,
                    "Wa Iza Samiu",
                    "Surah Al-Maaida 5:83 - Surah Al-An'aam 6:110"
                )
            )
            listJuz.add(
                QuranEntity(
                    8,
                    "juz",
                    8,
                    "Wa Lau Annana",
                    "Surah Al-An'aam 6:111 - Surah Al-A'raaf 7:87"
                )
            )
            listJuz.add(
                QuranEntity(
                    9,
                    "juz",
                    9,
                    "Qalal Malao",
                    "Surah Al-A'raaf 7:88 - Surah Al-Anfaal 8:40"
                )
            )
            listJuz.add(
                QuranEntity(
                    10,
                    "juz",
                    10,
                    "Wa A'lamu",
                    "Surah Al-Anfaal 8:41 - Surah At-Tawba 9:92"
                )
            )
            listJuz.add(
                QuranEntity(
                    11,
                    "juz",
                    11,
                    "Yatazeroon",
                    "Surah At-Tawba 9:93 - Surah Hud 11:5"
                )
            )
            listJuz.add(
                QuranEntity(
                    12,
                    "juz",
                    12,
                    "Wa Mamin Da'abat",
                    "Surah Hud 11:6 - Surah Yusuf 12:52"
                )
            )
            listJuz.add(
                QuranEntity(
                    13,
                    "juz",
                    13,
                    "Wa Ma Ubrioo",
                    "Surah Yusuf 12:53 - Surah Ibrahim 14:52"
                )
            )
            listJuz.add(
                QuranEntity(
                    14,
                    "juz",
                    14,
                    "Rubama",
                    "Surah Al-Hijr 15:2 - Surah An-Nahl 16:128"
                )
            )
            listJuz.add(
                QuranEntity(
                    15,
                    "juz",
                    15,
                    "Subhanallazi",
                    "Surah Al-Israa 17:1 - Surah Al-Kahf 18:74"
                )
            )
            listJuz.add(
                QuranEntity(
                    16,
                    "juz",
                    16,
                    "Qal Alam",
                    "Surah Al-Kahf 18:75 - Surah Taa-Haa 20:135"
                )
            )
            listJuz.add(
                QuranEntity(
                    17,
                    "juz",
                    17,
                    "Aqtarabo",
                    "Surah Al-Anbiyaa 21:21 - Surah Al-Hajj 22:78"
                )
            )
            listJuz.add(
                QuranEntity(
                    18,
                    "juz",
                    18,
                    "Qadd Aflaha",
                    "Surah Al-Muminoon 23:1 - Surah Al-Furqaan 25:20"
                )
            )
            listJuz.add(
                QuranEntity(
                    19,
                    "juz",
                    19,
                    "Wa Qalallazina",
                    "Surah Al-Furqaan 25:21 - Surah An-Naml 27:55"
                )
            )
            listJuz.add(
                QuranEntity(
                    20,
                    "juz",
                    20,
                    "A'man Khalaq",
                    "Surah An-Naml 27:60 - Surah Al-Ankaboot 29:45"
                )
            )
            listJuz.add(
                QuranEntity(
                    21,
                    "juz",
                    21,
                    "Utlu Ma Oohi",
                    "Surah Al-Ankaboot 29:45 - Surah Al-Ahzaab 33:30"
                )
            )
            listJuz.add(
                QuranEntity(
                    22,
                    "juz",
                    22,
                    "Wa Manyaqnut",
                    "Surah Al-Ahzaab 33:31 - Surah Yaseen 36:27"
                )
            )
            listJuz.add(
                QuranEntity(
                    23,
                    "juz",
                    23,
                    "Wa Mali",
                    "Surah Yaseen 36:22 - Surah Az-Zumar 39:31"
                )
            )
            listJuz.add(
                QuranEntity(
                    24,
                    "juz",
                    24,
                    "Faman Azlam",
                    "Surah Az-Zumar 39:32 - Surah Fussilat 41:46"
                )
            )
            listJuz.add(
                QuranEntity(
                    25,
                    "juz",
                    25,
                    "Elahe Yuruddo",
                    "Surah Fussilat 41:47 - Surah Al-Jaathiya 45:37"
                )
            )
            listJuz.add(
                QuranEntity(
                    26,
                    "juz",
                    26,
                    "Ha'a Meem",
                    "Surah Al-Ahqaf 46:1 - Surah Adh-Dhaariyat 51:30"
                )
            )
            listJuz.add(
                QuranEntity(
                    27,
                    "juz",
                    27,
                    "Qala Fama Khatbukum",
                    "Surah Adh-Dhaariyat 51:31 - Surah Al-Hadid 57:29"
                )
            )
            listJuz.add(
                QuranEntity(
                    28,
                    "juz",
                    28,
                    "Qadd Sami Allah",
                    "Surah Al-Mujaadila 58:1 - Surah At-Tahrim 66:12"
                )
            )
            listJuz.add(
                QuranEntity(
                    29,
                    "juz",
                    29,
                    "Tabarakallazi",
                    "Surah Adh-Dhaariyat 51:31 - Surah Al-Hadid 57:29"
                )
            )
            listJuz.add(
                QuranEntity(
                    30,
                    "juz",
                    30,
                    "Amma Yatasa'aloon",
                    "Surah An-Naba 78:1 - Surah An-Naas 114:6"
                )
            )
                if (isAdded) {
//                    viewModelQuran.clearAllQuran()
                    listJuz.forEach {
                        viewModelQuran.insertQuran(it)
                    }
                }



    }

    private fun getAllSurah() {
//            listSurah.clear()
            listSurah.add(QuranEntity(31, "surah", 1, "Al-Faatiha", "The Opening (7)"))
            listSurah.add(QuranEntity(32, "surah", 2, "Al-Baqara", "The Cow (286)"))
            listSurah.add(QuranEntity(33, "surah", 3, "Aal-i-Imraan", "The Family of Imraan (200)"))
            listSurah.add(QuranEntity(34, "surah", 4, "An-Nisaa", "The Women (176)"))
            listSurah.add(QuranEntity(35, "surah", 5, "Al-Maaida", "The Table (120)"))
            listSurah.add(QuranEntity(36, "surah", 6, "Al-An'aam", "The Cattle (165)"))
            listSurah.add(QuranEntity(37, "surah", 7, "Al-A'raaf", "The Heights (206)"))
            listSurah.add(QuranEntity(38, "surah", 8, "Al-Anfaal", "The Spoils of War (75)"))
            listSurah.add(QuranEntity(39, "surah", 9, "At-Tawba", "The Repentance (129)"))
            listSurah.add(QuranEntity(40, "surah", 10, "Yunus", "Jonas (109)"))
            listSurah.add(QuranEntity(41, "surah", 11, "Hud", "Hud (123)"))
            listSurah.add(QuranEntity(42, "surah", 12, "Yusuf", "Joseph (111)"))
            listSurah.add(QuranEntity(43, "surah", 13, "Ar-Ra'd", "The Thunder (43)"))
            listSurah.add(QuranEntity(44, "surah", 14, "Ibrahim", "Abraham (52)"))
            listSurah.add(QuranEntity(45, "surah", 15, "Al-Hijr", "The Rock (99)"))
            listSurah.add(QuranEntity(46, "surah", 16, "An-Nahl", "The Bee (128)"))
            listSurah.add(QuranEntity(47, "surah", 17, "Al-Israa", "The Night Journey (111)"))
            listSurah.add(QuranEntity(48, "surah", 18, "Al-Kahf", "The Cave (110)"))
            listSurah.add(QuranEntity(49, "surah", 19, "Maryam", "Mary (98)"))
            listSurah.add(QuranEntity(50, "surah", 20, "Taa-Haa", "Taa-Haa (135)"))
            listSurah.add(QuranEntity(51, "surah", 21, "Al-Anbiyaa", "The Prophets (112)"))
            listSurah.add(QuranEntity(52, "surah", 22, "Al-Hajj", "The Pilgrimage (78)"))
            listSurah.add(QuranEntity(53, "surah", 23, "Al-Muminoon", "The Believers (118)"))
            listSurah.add(QuranEntity(54, "surah", 24, "An-Noor", "The Light (64)"))
            listSurah.add(QuranEntity(55, "surah", 25, "Al-Furqaan", "The Criterion (77)"))
            listSurah.add(QuranEntity(56, "surah", 26, "Ash-Shu'araa", "The Poets (227)"))
            listSurah.add(QuranEntity(57, "surah", 27, "An-Naml", "The Ant (93)"))
            listSurah.add(QuranEntity(58, "surah", 28, "Al-Qasas", "The Stories (88)"))
            listSurah.add(QuranEntity(59, "surah", 29, "Al-Ankaboot", "The Spider (69)"))
            listSurah.add(QuranEntity(60, "surah", 30, "Ar-Room", "The Romans (60)"))
            listSurah.add(QuranEntity(61, "surah", 31, "Luqman", "Luqman (34)"))
            listSurah.add(QuranEntity(62, "surah", 32, "As-Sajda", "The Prostration (30)"))
            listSurah.add(QuranEntity(63, "surah", 33, "Al-Ahzaab", "The Clans (73)"))
            listSurah.add(QuranEntity(64, "surah", 34, "Saba", "Sheba (54)"))
            listSurah.add(QuranEntity(65, "surah", 35, "Faatir", "The Originator (45)"))
            listSurah.add(QuranEntity(66, "surah", 36, "Yaseen", "Yaseen (83)"))
            listSurah.add(
                QuranEntity(
                    67,
                    "surah",
                    37,
                    "As-Saaffaat",
                    "Those drawn up in Ranks (182)"
                )
            )
            listSurah.add(QuranEntity(68, "surah", 38, "Saad", "The letter Saad (88)"))
            listSurah.add(QuranEntity(69, "surah", 39, "Az-Zumar", "The Groups (75)"))
            listSurah.add(QuranEntity(70, "surah", 40, "Ghafir", "The Forgiver (85)"))
            listSurah.add(QuranEntity(71, "surah", 41, "Fussilat", "Explained in detail (54)"))
            listSurah.add(QuranEntity(72, "surah", 42, "Ash-Shura", "Consultation (53)"))
            listSurah.add(QuranEntity(73, "surah", 43, "Az-Zukhruf", "Ornaments of gold (89)"))
            listSurah.add(QuranEntity(74, "surah", 44, "Ad-Dukhaan", "The Smoke (59)"))
            listSurah.add(QuranEntity(75, "surah", 45, "Al-Jaathiya", "Crouching (37)"))
            listSurah.add(QuranEntity(76, "surah", 46, "Al-Ahqaf", "The Dunes (35)"))
            listSurah.add(QuranEntity(77, "surah", 47, "Muhammad", "Muhammad (38)"))
            listSurah.add(QuranEntity(78, "surah", 48, "Al-Fath", "The Victory (29)"))
            listSurah.add(QuranEntity(79, "surah", 49, "Al-Hujuraat", "The Inner Apartments (18)"))
            listSurah.add(QuranEntity(80, "surah", 50, "Qaaf", "The letter Qaaf (45)"))
            listSurah.add(QuranEntity(81, "surah", 51, "Adh-Dhaariyat", "The Winnowing Winds (60)"))
            listSurah.add(QuranEntity(82, "surah", 52, "At-Tur", "The Mount (49)"))
            listSurah.add(QuranEntity(83, "surah", 53, "An-Najm", "The Star (62)"))
            listSurah.add(QuranEntity(84, "surah", 54, "Al-Qamar", "The Moon (55)"))
            listSurah.add(QuranEntity(85, "surah", 55, "Ar-Rahmaan", "The Beneficent (78)"))
            listSurah.add(QuranEntity(86, "surah", 56, "Al-Waaqia", "The Inevitable (96)"))
            listSurah.add(QuranEntity(87, "surah", 57, "Al-Hadid", "The Iron (29)"))
            listSurah.add(QuranEntity(88, "surah", 58, "Al-Mujaadila", "The Pleading Woman (22)"))
            listSurah.add(QuranEntity(89, "surah", 59, "Al-Hashr", "The Exile (24)"))
            listSurah.add(
                QuranEntity(
                    90,
                    "surah",
                    60,
                    "Al-Mumtahana",
                    "She that is to be examined (13)"
                )
            )
            listSurah.add(QuranEntity(91, "surah", 61, "As-Saff", "The Ranks (14)"))
            listSurah.add(QuranEntity(92, "surah", 62, "Al-Jumu'a", "Friday (11)"))
            listSurah.add(QuranEntity(93, "surah", 63, "Al-Munaafiqoon", "The Hypocrites (11)"))
            listSurah.add(QuranEntity(94, "surah", 64, "At-Taghaabun", "Mutual Disillusion (18)"))
            listSurah.add(QuranEntity(95, "surah", 65, "At-Talaaq", "Divorce (12)"))
            listSurah.add(QuranEntity(96, "surah", 66, "At-Tahrim", "The Prohibition (12)"))
            listSurah.add(QuranEntity(97, "surah", 67, "Al-Mulk", "The Sovereignty (30)"))
            listSurah.add(QuranEntity(98, "surah", 68, "Al-Qalam", "The Pen (52)"))
            listSurah.add(QuranEntity(99, "surah", 69, "Al-Haaqqa", "The Reality (52)"))
            listSurah.add(
                QuranEntity(
                    100,
                    "surah",
                    70,
                    "Al-Ma'aarij",
                    "The Ascending Stairways (44)"
                )
            )
            listSurah.add(QuranEntity(101, "surah", 71, "Nooh", "Noah (28)"))
            listSurah.add(QuranEntity(102, "surah", 72, "Al-Jinn", "The Jinn (28)"))
            listSurah.add(QuranEntity(103, "surah", 73, "Al-Muzzammil", "The Enshrouded One (20)"))
            listSurah.add(QuranEntity(104, "surah", 74, "Al-Muddaththir", "The Cloaked One (56)"))
            listSurah.add(QuranEntity(105, "surah", 75, "Al-Qiyaama", "The Resurrection (40)"))
            listSurah.add(QuranEntity(106, "surah", 76, "Al-Insaan", "Man (31)"))
            listSurah.add(QuranEntity(107, "surah", 77, "Al-Mursalaat", "The Emissaries (50)"))
            listSurah.add(QuranEntity(108, "surah", 78, "An-Naba", "The Announcement (40)"))
            listSurah.add(
                QuranEntity(
                    109,
                    "surah",
                    79,
                    "An-Naazi'aat",
                    "Those who drag forth (46)"
                )
            )
            listSurah.add(QuranEntity(110, "surah", 80, "Abasa", "He frowned (42)"))
            listSurah.add(QuranEntity(111, "surah", 81, "At-Takwir", "The Overthrowing (29)"))
            listSurah.add(QuranEntity(112, "surah", 82, "Al-Infitaar", "The Cleaving (19)"))
            listSurah.add(QuranEntity(113, "surah", 83, "Al-Mutaffifin", "Defrauding (36)"))
            listSurah.add(QuranEntity(114, "surah", 84, "Al-Inshiqaaq", "The Splitting Open (25)"))
            listSurah.add(QuranEntity(115, "surah", 85, "Al-Burooj", "The Constellations (22)"))
            listSurah.add(QuranEntity(116, "surah", 86, "At-Taariq", "The Morning Star (17)"))
            listSurah.add(QuranEntity(117, "surah", 87, "Al-A'laa", "The Most High (19)"))
            listSurah.add(QuranEntity(118, "surah", 88, "Al-Ghaashiya", "The Overwhelming (26)"))
            listSurah.add(QuranEntity(119, "surah", 89, "Al-Fajr", "The Dawn (30)"))
            listSurah.add(QuranEntity(120, "surah", 90, "Al-Balad", "The City (20)"))
            listSurah.add(QuranEntity(121, "surah", 91, "Ash-Shams", "The Sun (15)"))
            listSurah.add(QuranEntity(122, "surah", 92, "Al-Lail", "The Night (21)"))
            listSurah.add(QuranEntity(123, "surah", 93, "Ad-Dhuhaa", "The Morning Hours (11)"))
            listSurah.add(QuranEntity(124, "surah", 94, "Ash-Sharh", "The Consolation (8)"))
            listSurah.add(QuranEntity(125, "surah", 95, "At-Tin", "The Fig (8)"))
            listSurah.add(QuranEntity(126, "surah", 96, "Al-Alaq", "The Clot (19)"))
            listSurah.add(QuranEntity(127, "surah", 97, "Al-Qadr", "The Power, Fate (5)"))
            listSurah.add(QuranEntity(128, "surah", 98, "Al-Bayyina", "The Evidence (8)"))
            listSurah.add(QuranEntity(129, "surah", 99, "Az-Zalzala", "The Earthquake (8)"))
            listSurah.add(QuranEntity(130, "surah", 100, "Al-Aadiyaat", "The Chargers (11)"))
            listSurah.add(QuranEntity(131, "surah", 101, "Al-Qaari'a", "The Calamity (11)"))
            listSurah.add(QuranEntity(132, "surah", 102, "At-Takaathur", "Competition (8)"))
            listSurah.add(QuranEntity(133, "surah", 103, "Al-Asr", "The Declining Day, Epoch (3)"))
            listSurah.add(QuranEntity(134, "surah", 104, "Al-Humaza", "The Traducer (9)"))
            listSurah.add(QuranEntity(135, "surah", 105, "Al-Fil", "The Elephant (5)"))
            listSurah.add(QuranEntity(136, "surah", 106, "Quraish", "Quraysh (4)"))
            listSurah.add(QuranEntity(137, "surah", 107, "Al-Maa'un", "Almsgiving (7)"))
            listSurah.add(QuranEntity(138, "surah", 108, "Al-Kaafiroon", "Abundance (3)"))
            listSurah.add(QuranEntity(139, "surah", 109, "Al-Kaafiroon", "The Disbelievers (6)"))
            listSurah.add(QuranEntity(140, "surah", 110, "An-Nasr", "Divine Support (3)"))
            listSurah.add(QuranEntity(141, "surah", 111, "Al-Masad", "The Palm Fibre (5)"))
            listSurah.add(QuranEntity(142, "surah", 112, "Al-Ikhlaas", "The Sincerity (4)"))
            listSurah.add(QuranEntity(143, "surah", 113, "Al-Falaq", "The Dawn (5)"))
            listSurah.add(QuranEntity(144, "surah", 114, "An-Naas", "Mankind (6)"))
                if (isAdded) {
//                    viewModelQuran.clearAllQuran()
                    listSurah.forEach {
                        viewModelQuran.insertQuran(it)
                    }
                }
    }

}