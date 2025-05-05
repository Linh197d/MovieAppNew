package com.qibla.muslimday.app2025.ui.qibla

import android.Manifest
import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.qibla.muslimday.app2025.MainActivity
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseFragment
import com.qibla.muslimday.app2025.databinding.ActivityQiblaBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.ui.mosque.MosqueViewModel
import com.qibla.muslimday.app2025.ui.tasbih.TasbihActivity
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Const
import com.qibla.muslimday.app2025.view.DialogConnectionWifi
import com.qibla.muslimday.app2025.view.SensorListener
import com.qibla.muslimday.app2025.view.SensorUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Math.cos
import java.lang.Math.sin
import kotlin.math.abs

@AndroidEntryPoint
class QiblaFragment : BaseFragment<ActivityQiblaBinding>(), SensorListener.OnValueChangedListener {
    private var sensorListener: SensorListener? = null

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private var direction: Double = 0.0
    private var directionOriginal: Double = 0.0

    private val viewModelMosque: MosqueViewModel by viewModels()

    private val destinationLoc = Location("service Provider")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!requireContext().hasNetworkConnection()) {
            showDialogWifi(true)
        }

        binding.tvInfoLocation.isSelected = true

        destinationLoc.latitude = 21.422487 //kaaba latitude setting

        destinationLoc.longitude = 39.826206 //kaaba longitude setting

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        binding.llQiblaQuran.setOnClickListener {
            (requireActivity() as? MainActivity)?.quranSelect()
        }

        binding.llQiblaTasbih.setOnClickListener {
            val intent = Intent(requireActivity(), TasbihActivity::class.java)
            intent.putExtra("showBack", false)
            startActivity(intent)
        }

        getCurrentLocation()

        setUpSensor()
        AdsInter.onAdsClick = {
            loadAdsNativeNoMediaQibla()
        }
        loadAdsNativeNoMediaQibla()
    }

    private fun loadAdsNativeNoMediaQibla() {
        AdsInter.pushNativeAll(
            context = requireContext(),
            view = binding.frAds,
            keyCheck = AdsInter.isLoadNativeQibla,
            isLoadNormal = false,
            scope = lifecycleScope
        )
    }

    override fun onResume() {
        super.onResume()
        if (!requireContext().hasNetworkConnection() && Const.isCheckWifi == 2) {
            showDialogWifi(true)
            Const.isCheckWifi = 1
        }
    }

    private fun showDialogWifi(b: Boolean) {
        val dialogWifi = DialogConnectionWifi(requireContext(), b)
        dialogWifi.init(
            requireContext(), object : DialogConnectionWifi.OnPress {
                override fun cancel() {
                    dialogWifi.dismiss()
                    if (!requireContext().hasNetworkConnection()) {
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

    private fun hasSensor(): Boolean {
        return SensorUtil.hasAccelerometer(requireContext()) && SensorUtil.hasMagnetometer(
            requireContext()
        )
    }

    private fun setUpDirectionPrayer(latitude: Double, longitude: Double) {
        val fLat: Double = degreeToRadians(latitude)
        val fLong: Double = degreeToRadians(longitude)
        val tLat: Double = degreeToRadians(destinationLoc.latitude)
        val tLong: Double = degreeToRadians(destinationLoc.longitude)

        val dLon = tLong - fLong

        val degree: Double = radiansToDegree(
            Math.atan2(
                sin(dLon) * cos(tLat),
                cos(fLat) * sin(tLat) - sin(fLat) * cos(tLat) * cos(dLon)
            )
        )
        directionOriginal = degree
        direction = if (degree >= 0) {
            degree
        } else {
            360 + degree
        }

        binding.ivQiblaDestination.rotation = direction.toFloat()

        Log.d("ntt", "${direction.toFloat()}")

    }

    private fun degreeToRadians(latLong: Double): Double {
        return Math.PI * latLong / 180.0
    }

    private fun radiansToDegree(latLong: Double): Double {
        return latLong * 180.0 / Math.PI
    }

    override fun onStart() {
        super.onStart()
        sensorListener?.start()
    }

    override fun getViewBinding(): ActivityQiblaBinding {
        return ActivityQiblaBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }

    override fun onStop() {
        super.onStop()
        sensorListener?.stop()
    }

    override fun onMagneticFieldChanged(f: Float) {
    }

    override fun onRotationChanged(f: Float, f2: Float, f3: Float) {

        val positiveAngle = convertToPositiveAngle((f).toDouble())
        val directionStr = getDirection(positiveAngle)
        if (positiveAngle + abs(directionOriginal) >= (direction - 6f) && positiveAngle <= (direction + 6f)) {
            binding.tvQiblaDes.text = getString(R.string.success_destination)
            binding.tvQiblaDegree.isVisible = true
            binding.tvQiblaDegree.text = "$directionStr ${positiveAngle.toInt()}°"
        } else {
            binding.tvQiblaDegree.isInvisible = true
            binding.tvQiblaDes.text = getString(R.string.continue_rotate_title)
        }

        imageRotate(-f + direction.toFloat())
    }

    private fun setUpSensor() {
        if (hasSensor()) {
            Glide.with(this).load(R.drawable.img_compass2)
                .into(binding.imageCompassNew)
            val sensorListener2 = SensorListener(requireContext())
            sensorListener = sensorListener2
            sensorListener2.setOnValueChangedListener(this)
            return
        } else {
            val dlg = RadarDlg()
            dlg.action = {
                (requireActivity() as? MainActivity)?.homeSelect()
            }
            dlg.show(parentFragmentManager, "dlg")
            binding.layoutSensor.visibility = View.GONE
        }
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("bugANR", "continueCurrent  1")
            lifecycleScope.launch {
                if(Const.latitude!=0.0 &&Const.longitude!=0.0){
                    setUpDirectionPrayer(latitude, longitude)
                    Log.d("bugANR", "continueCurrent  2")
                    // Sử dụng latitude và longitude ở đây
                    displayLocationDetails(latitude, longitude)
                }else
                fusedLocationClient?.lastLocation?.addOnCompleteListener {
                    if (it.isSuccessful && it.result != null) {
                        val location: Location = it.result
                        latitude = location.latitude
                        longitude = location.longitude

                        setUpDirectionPrayer(latitude, longitude)
                        Log.d("bugANR", "continueCurrent  2")

                        // Sử dụng latitude và longitude ở đây
                        displayLocationDetails(latitude, longitude)
                        Log.d("bugANR", "continueCurrent  3")
                    }
                }
            }
//            fusedLocationClient?.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
//                ?.addOnSuccessListener { location ->
//                    if (location != null) {
//                        latitude = location.latitude
//                        longitude = location.longitude
//                        setUpDirectionPrayer(latitude, longitude)
//                        Log.d("bugANR","continueCurrent 2")
//
//                        // Chuyển xử lý nặng sang background thread
//                            displayLocationDetails(latitude, longitude)
//                        Log.d("bugANR","continueCurrent 3")
//                    } else {
//                        Log.d("bugANR", "Không lấy được vị trí")
//                    }
//                }
//                ?.addOnFailureListener { e ->
//                    Log.e("bugANR", "Lỗi lấy vị trí: ${e.message}")
//                }
        } else {
            binding.llInfoLocation.visibility = View.GONE
        }
    }

    private fun displayLocationDetails(latitude: Double, longitude: Double) {
        if(Const.address!=""){
            binding.tvInfoLocation.text = Const.address
        }else {
            try {
                binding.llInfoLocation.isVisible = true
                viewModelMosque.getDisplayNameFromLatLon(requireContext(), latitude, longitude)
                viewModelMosque.name.observe(viewLifecycleOwner) { address ->
                    if (address != null) {
                        binding.tvInfoLocation.text = Const.address
                        Const.address = address
                    }
                }
            } catch (e: Exception) {
                binding.llInfoLocation.isVisible = false
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun imageRotate(f: Float) {
        binding.flQiblaRotate.animate().rotation(f).setDuration(0)
            .setInterpolator(LinearInterpolator())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
                override fun onAnimationStart(animator: Animator) {}
            }).start()


    }


    private fun convertToPositiveAngle(angle: Double): Double {
        return if (angle < 0) angle + 360 else angle
    }

    private fun getDirection(angle: Double): String {
        val directions = listOf(
            getString(R.string.north_title),
            getString(R.string.northeast_title),
            getString(R.string.east_title),
            getString(R.string.southeast_title),
            getString(R.string.south_title),
            getString(R.string.southwest_title),
            getString(R.string.west_title),
            getString(R.string.northwest_title),
        )
        val index = ((angle + 22.5) / 45).toInt() % 8
        return directions[index]
    }
}