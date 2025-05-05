package com.qibla.muslimday.app2025.ui.quran

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlbn.ads.util.Admob
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseFragment
import com.qibla.muslimday.app2025.database.Quran.QuranEntity
import com.qibla.muslimday.app2025.databinding.FragmentQuranBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.ui.quran.adapter.IClickItemQuran
import com.qibla.muslimday.app2025.ui.quran.adapter.QuranAdapter
import com.qibla.muslimday.app2025.ui.quran.bookmark.BookMarkQuranActivity
import com.qibla.muslimday.app2025.ui.quran.quranDetail.QuranDetailActivity
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Const
import com.qibla.muslimday.app2025.util.Const.Companion.listJuz
import com.qibla.muslimday.app2025.util.Const.Companion.listSurah
import com.qibla.muslimday.app2025.util.EventBusHelper
import com.qibla.muslimday.app2025.util.UpdateEvent
import com.qibla.muslimday.app2025.view.DialogConnectionWifi
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


@AndroidEntryPoint
class QuranFragment : BaseFragment<FragmentQuranBinding>(), IClickItemQuran {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val viewModel: QuranViewModel by viewModels()

    private lateinit var adapter: QuranAdapter
    private var listQuran = arrayListOf<QuranEntity>()
    private var itemQuranContinueSurah: QuranEntity? = null
    private var itemQuranContinueJuz: QuranEntity? = null

    private var selectList = 1
    private var selectListNew = 1

    override fun getViewBinding(): FragmentQuranBinding {
        return FragmentQuranBinding.inflate(layoutInflater)
    }

    override fun initView() {
        AdsInter.onAdsDataClick = {
            when (it) {
                AdsInter.CLICK_NATIVE_DATA1 -> {
                    if (this::adapter.isInitialized) {
                        adapter.notifyItemChanged(2)
                    }
                    Log.d("ttttt", "tttttt")
                }

                AdsInter.CLICK_NATIVE_DATA2 ->
                    if (this::adapter.isInitialized) {
                        adapter.notifyItemChanged(7)
                    }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!requireActivity().hasNetworkConnection()) {
            showDialogWifi(true)
        }

        setUpContinueReading()

        setUpListQuran()
        viewModel.updateStatus.observe(viewLifecycleOwner) { (id, isBookMark) ->
            val position = listSurah.indexOfFirst { it.id == id }
            if (position != -1) {
                listSurah[position].isBookMark = isBookMark
            }
        }
        initEvent()

    }

    private fun initEvent() {
        binding.btnSurah.setOnClickListener {
            selectList = 1
            selectListNew = 1
            binding.btnSurah.setBackgroundResource(R.drawable.bg_btn_surah_quran)
            binding.btnJuz.setBackgroundResource(R.color.transparent)
            setUpListQuran()
            setUpContinueReading()
        }

        binding.btnJuz.setOnClickListener {
            selectList = 2
            selectListNew = 2
            binding.btnJuz.setBackgroundResource(R.drawable.bg_btn_surah_quran)
            binding.btnSurah.setBackgroundResource(R.color.transparent)
            setUpListQuran()
            setUpContinueReading()
        }

//        viewModel.getAllDataSurahQuran()
//
//        viewModel.surahQuran.observe(requireActivity()) { it ->
//            adapter = QuranAdapter(it, this)
//            binding.rcvQuran.layoutManager =
//                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//            binding.rcvQuran.adapter = adapter
//        }
//

        binding.btnSearch.setOnClickListener {
            binding.search.queryHint = getString(R.string.string_search);
            binding.layoutToolbar.visibility = View.GONE
            binding.search.visibility = View.VISIBLE
        }

        binding.search.findViewById<View>(R.id.search_mag_icon)
            .setOnClickListener {
                // Write your code here for the actions you need on click of searchIcon
                binding.layoutToolbar.visibility = View.VISIBLE
                binding.search.visibility = View.GONE
            }

        binding.search.findViewById<TextView>(R.id.search_src_text)
            .setTextColor(Color.WHITE)

        binding.search.findViewById<TextView>(R.id.search_src_text)
            .setHintTextColor(Color.parseColor("#307973"))

        binding.search.findViewById<ImageView>(R.id.search_close_btn)
            .setImageResource(R.drawable.ic_close)

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        binding.btnBookmark.setOnClickListener {
            bookMarkQuranActivity.launch(
                Intent(
                    requireActivity(),
                    BookMarkQuranActivity::class.java
                )
            )
        }
        binding.tvContinueReading.setOnClickListener {
            if (requireActivity().hasNetworkConnection()) {
                AdsInter.loadInter(
                    context = requireActivity(),
                    adsId = getString(R.string.inter_quran),
                    isShow = AdsInter.isLoadInterQuran && Admob.getInstance().isLoadFullAds,
                    action = {
                        showAdsInter()
                    }
                )
            } else {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.string_please_connect_to_the_internet_to_use_this_feature),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

//        KeyboardVisibilityEvent.setEventListener(requireActivity()) {
//            if (it) {
//                binding.frAds.visibility = View.GONE
//            } else {
//                binding.frAds.visibility = View.VISIBLE
//            }
//        }

    }

    private fun showAdsInter() {
        val intent = Intent(requireActivity(), QuranDetailActivity::class.java)
        val bundle = Bundle()
        if (selectList == 1)
            bundle.putSerializable("quran_model", itemQuranContinueSurah)
        else
            bundle.putSerializable("quran_model", itemQuranContinueJuz)

        intent.putExtras(bundle)
        Const.isOpenTab = true
        startActivityResult.launch(intent)
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
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: UpdateEvent) {
    }


    private val bookMarkQuranActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                setUpListQuran()
                selectList = selectListNew
            }


        }


    private fun showDialogWifi(b: Boolean) {
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
                    context?.startActivity(intent)
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

    override fun onResume() {
        super.onResume()
        Log.d("linhd", "OnreReumse")
        setUpContinueReading()

        if (isLoadNative) {
            isLoadNative = false

            if (this::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            }
        }

        if (!requireActivity().hasNetworkConnection() && Const.isCheckWifi == 2) {
            showDialogWifi(true)
            Const.isCheckWifi = 1
        }
    }

    override fun onStop() {
        super.onStop()
//        if (requireActivity().hasNetworkConnection()) {
//
//            if (ConsentHelper.getInstance(requireActivity()).canRequestAds()) {
//
//                lifecycleScope.launch {
//
//                    AdsInter.nativeAdsJuz = null
//                    AdsInter.nativeAdsJuz1 = null
//                    AdsInter.nativeAdsJuz2 = null
//
//                    AdsInter.nativeAdsSurah = null
//                    AdsInter.nativeAdsSurah1 = null
//                    AdsInter.nativeAdsSurah2 = null
//
//                    val loadNativeJuz = async { loadNativeJuz() }
//                    val loadNativeJuz1 = async { loadNativeJuz1() }
//                    val loadNativeJuz2 = async { loadNativeJuz2() }
//                    val loadNativeSurah = async { loadNativeSurah() }
//                    val loadNativeSurah1 = async { loadNativeSurah1() }
//                    val loadNativeSurah2 = async { loadNativeSurah2() }
//
//                    awaitAll(
//                        loadNativeJuz,
//                        loadNativeJuz1,
//                        loadNativeJuz2,
//                        loadNativeSurah,
//                        loadNativeSurah1,
//                        loadNativeSurah2
//                    )
//                }
//            }
//        }
    }

    private fun setUpContinueReading() {
        if (selectList == 1) {
            if (preferenceHelper.getNameContinueReadingSurah().isNullOrEmpty()) {
                binding.tvContinueReading.visibility = View.GONE
            } else {
                binding.tvContinueReading.visibility = View.VISIBLE
                binding.tvContinueReading.text = getString(
                    R.string.string_continue_reading,
                    preferenceHelper.getNameContinueReadingSurah()
                )
                Log.d("linhd", "id" + preferenceHelper.getIdContinueReadingSurah())
                itemQuranContinueSurah =
                    listSurah.firstOrNull { it.id == preferenceHelper.getIdContinueReadingSurah() }
                Log.d("linhd", itemQuranContinueSurah?.name + "11")
            }
        } else if (selectList == 2) {
            if (preferenceHelper.getNameContinueReadingJuz().isNullOrEmpty()) {
                binding.tvContinueReading.visibility = View.GONE
            } else {
                binding.tvContinueReading.visibility = View.VISIBLE
                binding.tvContinueReading.text = getString(
                    R.string.string_continue_reading,
                    preferenceHelper.getNameContinueReadingJuz()
                )
                itemQuranContinueJuz =
                    listJuz.firstOrNull { it.name == preferenceHelper.getNameContinueReadingJuz() }

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpListQuran() {
        Log.d("fffff", "" + selectList)
        if (selectList == 1) {
            viewModel.getQuranType("surah").observe(viewLifecycleOwner) {
                if (selectList == 1) {
                    if (!it.isNullOrEmpty()) {
                        listQuran.clear()
                        listQuran.addAll(it)

                        if (requireActivity().hasNetworkConnection() && AdsInter.isLoadNativeData1) {
                            if (listQuran.size > 2)
                                listQuran.add(2, QuranEntity(0, "Ads", 1, "", ""))
                        }
                        if (requireActivity().hasNetworkConnection() && AdsInter.isLoadNativeData2) {
                            if (listQuran.size > 7)
                                listQuran.add(7, QuranEntity(0, "Ads", 1, "", ""))

//                            listQuran.add(12, QuranEntity(0, "Ads", 1, "", ""))
                        }

                        adapter = QuranAdapter(requireActivity(), 1, listQuran, this)
                        adapter.onListFiltered = { isEmpty ->
                            binding.tvNodata.visibility = if (isEmpty) View.VISIBLE else View.GONE
                            binding.rcvQuran.visibility = if (isEmpty) View.GONE else View.VISIBLE
                        }

                        val layoutManager = LinearLayoutManager(requireActivity())
                        binding.rcvQuran.layoutManager = layoutManager
                        binding.rcvQuran.adapter = adapter
                    }
                }

            }

        } else if (selectList == 2) {
            viewModel.getQuranType("juz").observe(viewLifecycleOwner) {
                if (selectList == 2) {

                    listQuran.clear()
                    listQuran.addAll(it)

                    if (requireActivity().hasNetworkConnection() && AdsInter.isLoadNativeData1) {
                        if (listQuran.size > 2)
                        listQuran.add(2, QuranEntity(0, "Ads", 1, "", ""))
                    }
                    if (requireActivity().hasNetworkConnection() && AdsInter.isLoadNativeData2) {
                        if (listQuran.size > 7)
                        listQuran.add(7, QuranEntity(0, "Ads", 1, "", ""))
//                        listQuran.add(12, QuranEntity(0, "Ads", 1, "", ""))
                    }

                    adapter = QuranAdapter(requireActivity(), 2, listQuran, this)
                    val layoutManager = LinearLayoutManager(requireActivity())
                    binding.rcvQuran.layoutManager = layoutManager
                    binding.rcvQuran.adapter = adapter
                }

            }
        }
    }




    override fun onClickItemQuran(QuranEntity: QuranEntity) {
        if (requireActivity().hasNetworkConnection()) {

//            if (ConsentHelper.getInstance(requireActivity()).canRequestAds()) {
//
//                lifecycleScope.launch {
//
//                    AdsInter.nativeAdsJuz = null
//                    AdsInter.nativeAdsJuz1 = null
//                    AdsInter.nativeAdsJuz2 = null
//
//                    AdsInter.nativeAdsSurah = null
//                    AdsInter.nativeAdsSurah1 = null
//                    AdsInter.nativeAdsSurah2 = null
//
//                    val loadNativeJuz = async { loadNativeJuz() }
//                    val loadNativeJuz1 = async { loadNativeJuz1() }
//                    val loadNativeJuz2 = async { loadNativeJuz2() }
//                    val loadNativeSurah = async { loadNativeSurah() }
//                    val loadNativeSurah1 = async { loadNativeSurah1() }
//                    val loadNativeSurah2 = async { loadNativeSurah2() }
//
//                    awaitAll(
//                        loadNativeJuz,
//                        loadNativeJuz1,
//                        loadNativeJuz2,
//                        loadNativeSurah,
//                        loadNativeSurah1,
//                        loadNativeSurah2
//                    )
//                }
//            }

            if (QuranEntity.type == "surah") {
                itemQuranContinueSurah =
                    listSurah.firstOrNull { it.id == QuranEntity.id }
                preferenceHelper.setNameContinueReadingSurah(QuranEntity.name)
                preferenceHelper.setIdContinueReadingSurah(QuranEntity.id)
            } else if (QuranEntity.type == "juz") {
                itemQuranContinueJuz =
                    listJuz.firstOrNull { it.id == QuranEntity.id }
                preferenceHelper.setNameContinueReadingJuz(QuranEntity.name)
                preferenceHelper.setIdContinueReadingJuz(QuranEntity.id)

            }

            AdsInter.loadInter(
                context = requireActivity(),
                adsId = getString(R.string.inter_quran),
                isShow = AdsInter.isLoadInterQuran && Admob.getInstance().isLoadFullAds,
                action = {
                    showAdsInter()
                }
            )

//            startActivity(intent)
        } else {
            Toast.makeText(
                requireActivity(),
                getString(R.string.string_please_connect_to_the_internet_to_use_this_feature),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    val startActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { _ ->
//        if (this::adapter.isInitialized) {
//            adapter.notifyDataSetChanged()
//        }

//        if (ConsentHelper.getInstance(requireActivity()).canRequestAds()) {
//            showAdsNativeQuran()
//        }
    }

//    private fun loadNativeSurah() {
//        if (AdsInter.isLoadNativeSurah) {
//            Admob.getInstance()
//                .loadNativeAd(
//                    requireActivity(),
//                    getString(R.string.native_surah),
//                    object : NativeCallback() {
//                        override fun onNativeAdLoaded(nativeAd: NativeAd?) {
//                            AdsInter.nativeAdsSurah = nativeAd
//                        }
//
//                        override fun onAdImpression() {
//                            super.onAdImpression()
//                        }
//                    })
//        }
//    }
//
//    private fun loadNativeSurah1() {
//        if (AdsInter.isLoadNativeSurah1) {
//            Admob.getInstance()
//                .loadNativeAd(
//                    requireActivity(),
//                    getString(R.string.native_surah1),
//                    object : NativeCallback() {
//                        override fun onNativeAdLoaded(nativeAd: NativeAd?) {
//                            AdsInter.nativeAdsSurah1 = nativeAd
//                        }
//
//                        override fun onAdImpression() {
//                            super.onAdImpression()
//                        }
//                    })
//        }
//    }
//
//    private fun loadNativeSurah2() {
//        if (AdsInter.isLoadNativeSurah2) {
//            Admob.getInstance()
//                .loadNativeAd(
//                    requireActivity(),
//                    getString(R.string.native_surah2),
//                    object : NativeCallback() {
//                        override fun onNativeAdLoaded(nativeAd: NativeAd?) {
//                            AdsInter.nativeAdsSurah2 = nativeAd
//                        }
//
//                        override fun onAdImpression() {
//                            super.onAdImpression()
//                        }
//                    })
//        }
//    }
//
//    private fun loadNativeJuz() {
//        if (AdsInter.isLoadNativeJuz) {
//            Admob.getInstance()
//                .loadNativeAd(
//                    requireActivity(),
//                    getString(R.string.native_juz),
//                    object : NativeCallback() {
//                        override fun onNativeAdLoaded(nativeAd: NativeAd?) {
//                            AdsInter.nativeAdsJuz = nativeAd
//                        }
//
//                        override fun onAdImpression() {
//                            super.onAdImpression()
//                        }
//                    })
//        }
//    }
//
//    private fun loadNativeJuz1() {
//        if (AdsInter.isLoadNativeJuz1) {
//            Admob.getInstance()
//                .loadNativeAd(
//                    requireActivity(),
//                    getString(R.string.native_juz1),
//                    object : NativeCallback() {
//                        override fun onNativeAdLoaded(nativeAd: NativeAd?) {
//                            AdsInter.nativeAdsJuz1 = nativeAd
//                        }
//
//                        override fun onAdImpression() {
//                            super.onAdImpression()
//                        }
//                    })
//        }
//    }
//
//    private fun loadNativeJuz2() {
//        if (AdsInter.isLoadNativeJuz2) {
//            Admob.getInstance()
//                .loadNativeAd(
//                    requireActivity(),
//                    getString(R.string.native_juz2),
//                    object : NativeCallback() {
//                        override fun onNativeAdLoaded(nativeAd: NativeAd?) {
//                            AdsInter.nativeAdsJuz2 = nativeAd
//                        }
//
//                        override fun onAdImpression() {
//                            super.onAdImpression()
//                        }
//                    })
//        }
//    }

    override fun onClickBookMarkItemQuran(quranEntity: QuranEntity, position: Int) {
        quranEntity.isBookMark = !quranEntity.isBookMark
        // Cập nhật UI
        adapter.notifyItemChanged(position)
        // Cập nhật database hoặc ViewModel
        viewModel.updateIsCheck(quranEntity.id, quranEntity.isBookMark)
    }
}