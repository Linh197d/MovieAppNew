package com.qibla.muslimday.app2025.ui.mosque

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.AppOpenManager
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.ActivityMosqueBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.network.model_mosque.MosqueWithImage
import com.qibla.muslimday.app2025.ui.adapter.MosqueAdapter
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Const
import com.qibla.muslimday.app2025.view.DialogConnectionWifi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MosqueActivity : BaseActivity<ActivityMosqueBinding>() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: MosqueViewModel by viewModels()
    private lateinit var mosqueAdapter: MosqueAdapter

    override fun setBinding(layoutInflater: LayoutInflater): ActivityMosqueBinding {
        return ActivityMosqueBinding.inflate(layoutInflater)
    }

    override fun initView() {
        // Hiển thị progress khi bắt đầu tải dữ liệu
        binding.progressBar.visibility = View.VISIBLE
        binding.tvLoading.visibility = View.VISIBLE
        binding.layoutNative.visibility = View.GONE
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getDataAllMosque()
        if (!hasNetworkConnection()) {
            showDialogWifi(true)
        }
        AdsInter.onAdsClick = {
            loadAdsNative()
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
//        binding.rlMosques.setOnClickListener {
//        }
//        if (ConsentHelper.getInstance(this).canRequestAds())
//            loadAdsNativeMap()
        mosqueAdapter =
            MosqueAdapter(this, emptyList(), object : MosqueAdapter.OnChildItemClickListener {
                override fun onChildItemClick(childItem: MosqueWithImage) {
//                    openGoogleMapsWithSearchQuery(childItem.name)
                    openGoogleMapsWithLocation(childItem.lat, childItem.lon)
                }
            })
        binding.rcvMosque.adapter = mosqueAdapter

        viewModel.mosques.observe(this) { mosques ->
            binding.progressBar.visibility = View.GONE  // Ẩn khi có dữ liệu
            binding.tvLoading.visibility = View.GONE  // Ẩn khi có dữ liệu
            mosques?.let {
                if (mosques.isNotEmpty()) loadAdsNative()
                mosqueAdapter.updateData(mosques)
            }
        }
    }

    private fun getDataAllMosque() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // requestr permission
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val lat = location.latitude
                val lng = location.longitude
                Log.d("IslamMapActivity", "Vị trí hiện tại: $lat, $lng")

                // 🔹 Gọi ViewModel để lấy danh sách mosque có ảnh
                viewModel.getMosquesWithImages(this@MosqueActivity, lat, lng)
            } else {
//                Toast.makeText(this, "Không lấy được vị trí hiện tại!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        AppOpenManager.getInstance().enableAppResumeWithActivity(MosqueActivity::class.java)

//        if (isLoadNative) {
//            isLoadNative = false
//
//            if (ConsentHelper.getInstance(this).canRequestAds())
//                loadAdsNativeMap()
//        }

        if (!hasNetworkConnection() && Const.isCheckWifi == 2) {
            showDialogWifi(true)
            Const.isCheckWifi = 1
        }
    }

    private fun showDialogWifi(b: Boolean) {
        val dialogWifi = DialogConnectionWifi(this, b)
        dialogWifi.init(
            this, object : DialogConnectionWifi.OnPress {
                override fun cancel() {
                    dialogWifi.dismiss()
                    if (!hasNetworkConnection()) {
                        showDialogWifi(true)
                    }
                }

                override fun save() {
                    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                    startActivity(intent)
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
    }

    fun openGoogleMapsWithSearchQuery(query: String) {
        val mapIntentUri = Uri.parse("geo:0,0?q=$query")
        val mapIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            val webIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$query")
            val webIntent = Intent(Intent.ACTION_VIEW, webIntentUri)
            AppOpenManager.getInstance().disableAppResumeWithActivity(MosqueActivity::class.java)
            startActivity(webIntent)
        }
    }

    fun openGoogleMapsWithLocation(lat: Double, lon: Double) {
        val mapIntentUri = Uri.parse("geo:$lat,$lon?q=$lat,$lon")
        val mapIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            val webIntentUri =
                Uri.parse("https://www.google.com/maps/search/?api=1&query=$lat,$lon")
            val webIntent = Intent(Intent.ACTION_VIEW, webIntentUri)
            AppOpenManager.getInstance().disableAppResumeWithActivity(MosqueActivity::class.java)
            startActivity(webIntent)
        }
    }

    private fun loadAdsNative() {
        binding.layoutNative.visibility = View.VISIBLE
        AdsInter.pushNativeAll(
            context = this,
            view = binding.frAds,
            keyCheck = AdsInter.isLoadNativeMosque,
            scope = lifecycleScope
        )
    }

    override fun onBackPressed() {
        AdsInter.loadInter(
            context = this@MosqueActivity,
            adsId = getString(R.string.inter_back),
            isShow = AdsInter.isLoadInterBack && Admob.getInstance().isLoadFullAds,
            action = {
                finish()
            }
        )
    }
}