package com.qibla.muslimday.app2025.ui.azkar

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.MainActivity
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.ActivityAzkarBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.model.ItemAzkarCategoryModel
import com.qibla.muslimday.app2025.model.ItemAzkarTopicModel
import com.qibla.muslimday.app2025.ui.adapter.AzkarAdapter
import com.qibla.muslimday.app2025.ui.adapter.AzkarChildAdapter
import com.qibla.muslimday.app2025.ui.azkar.bookmark.BookMarkAzkarActivity
import com.qibla.muslimday.app2025.ui.azkar.daily.AzkarDetailActivity
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Global
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AzkarActivity : BaseActivity<ActivityAzkarBinding>() {
    private val viewModel: AzkarViewModel by viewModels()

    private lateinit var adapter: AzkarAdapter
    private val azkarList = ArrayList<ItemAzkarTopicModel>()
    override fun setBinding(layoutInflater: LayoutInflater): ActivityAzkarBinding {
        return ActivityAzkarBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewModel.initData()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.rcvAzkar.setHasFixedSize(true)
        binding.rcvAzkar.layoutManager = LinearLayoutManager(this)
        addDataToList()
        if (hasNetworkConnection() && AdsInter.isLoadNativeData1) {
            azkarList.add(
                2, ItemAzkarTopicModel(
                    "Ads",
                    R.drawable.ic_daily_dhikr,
                    arrayListOf(), 1
                )
            )
        }
        if (hasNetworkConnection() && AdsInter.isLoadNativeData2) {
            azkarList.add(
                7, ItemAzkarTopicModel(
                    "Ads",
                    R.drawable.ic_daily_dhikr,
                    arrayListOf(), 1
                )
            )
//            azkarList.add(
//                12, ItemAzkarTopicModel(
//                    "Ads",
//                    R.drawable.ic_daily_dhikr,
//                    arrayListOf(), 1
//                )
//            )
        }
        adapter =
            AzkarAdapter(
                lifecycleScope,
                this,
                azkarList,
                object : AzkarChildAdapter.OnChildItemClickListener {
                    override fun onChildItemClick(childItem: ItemAzkarCategoryModel) {
                        val intent = Intent(this@AzkarActivity, AzkarDetailActivity::class.java)
                        intent.putExtra("data", childItem.title)
                        intent.putExtra("id", childItem.categoryId)
                        startActivityResult.launch(intent)
                    }
                })
        adapter.seeSub = {
            val intent = Intent(this@AzkarActivity, AzkarSubActivity::class.java)
            intent.putExtra(AzkarSubActivity.DATA_KEY, it)
            startActivityResult.launch(intent)
        }
        binding.rcvAzkar.adapter = adapter

        binding.imgBookMark.setOnClickListener {
            val intent = Intent(this, BookMarkAzkarActivity::class.java)
            startActivityResult.launch(intent)
        }
        val consentHelper = ConsentHelper.getInstance(this)

        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        consentHelper.obtainConsentAndShow(this) {
            AdsInter.loadBanner(this@AzkarActivity, binding.frBanner, binding.root)
        }
        initData()
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

    val startActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { _ ->

    }

    override fun onResume() {
        super.onResume()

    }

//    private fun loadNativeAzkar() {
//        Admob.getInstance()
//            .loadNativeAd(
//                this,
//                getString(R.string.native_azkar),
//                object : NativeCallback() {
//                    override fun onNativeAdLoaded(nativeAd: NativeAd?) {
//                        Log.d("ntt", "onNativeAdLoaded: Load nativeAzkar")
//                        AdsInter.nativeAdsAzkar = nativeAd
//                    }
//
//                    override fun onAdImpression() {
//                        super.onAdImpression()
//                        Log.d("ntt", "onAdImpression: loadNativeAzkar")
//                        AdsInter.nativeAdsAzkar = null
//                    }
//                })
//    }
//
//    private fun loadNativeAzkar1() {
//        Admob.getInstance()
//            .loadNativeAd(
//                this,
//                getString(R.string.native_azkar1),
//                object : NativeCallback() {
//                    override fun onNativeAdLoaded(nativeAd: NativeAd?) {
//                        Log.d("ntt", "onNativeAdLoaded: Load nativeAzkar1")
//
//                        AdsInter.nativeAdsAzkar1 = nativeAd
//                    }
//
//                    override fun onAdImpression() {
//                        super.onAdImpression()
//                        Log.d("ntt", "onAdImpression: loadNativeAzkar1")
//                        AdsInter.nativeAdsAzkar1 = null
//                    }
//                })
//    }
//
//    private fun loadNativeAzkar2() {
//        Admob.getInstance()
//            .loadNativeAd(
//                this,
//                getString(R.string.native_azkar2),
//                object : NativeCallback() {
//                    override fun onNativeAdLoaded(nativeAd: NativeAd?) {
//                        Log.d("ntt", "onNativeAdLoaded: Load nativeAzkar2")
//
//                        AdsInter.nativeAdsAzkar2 = nativeAd
//                    }
//
//                    override fun onAdImpression() {
//                        super.onAdImpression()
//                        Log.d("ntt", "onAdImpression: loadNativeAzkar2")
//                        AdsInter.nativeAdsAzkar2 = null
//                    }
//                })
//    }

//    private fun showDialogWifi(b: Boolean) {
//        val dialogWifi  = DialogConnectionWifi(this,b)
//        dialogWifi.init(
//            this,object : DialogConnectionWifi.OnPress{
//                override fun cancel() {
//                    dialogWifi.dismiss()
//                    if(!hasNetworkConnection()){
//                        showDialogWifi(true)
//                    }
//                }
//
//                override fun save() {
//                    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
//                    startActivity(intent)
//                    Const.isCheckWifi = 2
//                    dialogWifi.dismiss()
//                }
//            }
//        )
//        try {
//            dialogWifi.show()
//        } catch (e: WindowManager.BadTokenException) {
//            e.printStackTrace()
//        }
//    }

    private fun addDataToList() {
        val listDailyDhikr = ArrayList<ItemAzkarCategoryModel>()
        listDailyDhikr.add(ItemAzkarCategoryModel(getString(R.string.string_daily_dhikr_1), 1, 1))
        listDailyDhikr.add(ItemAzkarCategoryModel(getString(R.string.string_daily_dhikr_2), 1, 2))
        listDailyDhikr.add(ItemAzkarCategoryModel(getString(R.string.string_daily_dhikr_3), 1, 3))
        listDailyDhikr.add(ItemAzkarCategoryModel(getString(R.string.string_daily_dhikr_4), 1, 4))
        listDailyDhikr.add(ItemAzkarCategoryModel(getString(R.string.string_daily_dhikr_5), 1, 5))
        listDailyDhikr.add(ItemAzkarCategoryModel(getString(R.string.string_daily_dhikr_6), 1, 6))
        listDailyDhikr.add(ItemAzkarCategoryModel(getString(R.string.string_daily_dhikr_7), 1, 7))
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_daily_dhikr),
                R.drawable.ic_daily_dhikr,
                listDailyDhikr, 1
            )
        )

        val listDailyRememberence = ArrayList<ItemAzkarCategoryModel>()
        listDailyRememberence.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_daily_rememberence_1),
                2,
                8
            )
        )
        listDailyRememberence.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_daily_rememberence_2),
                2,
                9
            )
        )
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_daily_rememberence),
                R.drawable.ic_daily_rememberence,
                listDailyRememberence, 2
            )
        )

        val listAfterPrayers = ArrayList<ItemAzkarCategoryModel>()
        listAfterPrayers.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_after_prayer_1),
                3,
                10
            )
        )
        listAfterPrayers.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_after_prayer_2),
                3,
                11
            )
        )
        listAfterPrayers.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_after_prayer_3),
                3,
                12
            )
        )
        listAfterPrayers.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_after_prayer_4),
                3,
                13
            )
        )
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_after_prayer),
                R.drawable.ic_after_prayers,
                listAfterPrayers, 3
            )
        )

        val listRizq = ArrayList<ItemAzkarCategoryModel>()
        listRizq.add(ItemAzkarCategoryModel(getString(R.string.string_Rizq_sustenance_1), 4, 14))
        listRizq.add(ItemAzkarCategoryModel(getString(R.string.string_Rizq_sustenance_2), 4, 15))
        listRizq.add(ItemAzkarCategoryModel(getString(R.string.string_Rizq_sustenance_3), 4, 16))
        listRizq.add(ItemAzkarCategoryModel(getString(R.string.string_Rizq_sustenance_4), 4, 17))
        listRizq.add(ItemAzkarCategoryModel(getString(R.string.string_Rizq_sustenance_5), 4, 18))
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_Rizq_sustenance),
                R.drawable.ic_rizq,
                listRizq, 4
            )
        )

        val listKnowledge = ArrayList<ItemAzkarCategoryModel>()
        listKnowledge.add(ItemAzkarCategoryModel(getString(R.string.string_knowledge_1), 5, 19))
        listKnowledge.add(ItemAzkarCategoryModel(getString(R.string.string_knowledge_2), 5, 20))
        listKnowledge.add(ItemAzkarCategoryModel(getString(R.string.string_knowledge_3), 5, 21))
        listKnowledge.add(ItemAzkarCategoryModel(getString(R.string.string_knowledge_4), 5, 22))
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_knowledge),
                R.drawable.ic_knowledge,
                listKnowledge, 5
            )
        )

        val listFaith = ArrayList<ItemAzkarCategoryModel>()
        listFaith.add(ItemAzkarCategoryModel(getString(R.string.string_faith_1), 6, 23))
        listFaith.add(ItemAzkarCategoryModel(getString(R.string.string_faith_2), 6, 24))
        listFaith.add(ItemAzkarCategoryModel(getString(R.string.string_faith_3), 6, 25))
        listFaith.add(ItemAzkarCategoryModel(getString(R.string.string_faith_4), 6, 26))
        listFaith.add(ItemAzkarCategoryModel(getString(R.string.string_faith_5), 6, 27))
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_faith),
                R.drawable.ic_faith,
                listFaith, 6
            )
        )

        val listJudgement = ArrayList<ItemAzkarCategoryModel>()
        listJudgement.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_day_of_judgement_1),
                7,
                28
            )
        )
        listJudgement.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_day_of_judgement_2),
                7,
                29
            )
        )
        listJudgement.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_day_of_judgement_3),
                7,
                30
            )
        )
        listJudgement.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_day_of_judgement_4),
                7,
                31
            )
        )
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_day_of_judgement),
                R.drawable.ic_day_of_judgement,
                listJudgement, 7
            )
        )

        val listForgiveness = ArrayList<ItemAzkarCategoryModel>()
        listForgiveness.add(ItemAzkarCategoryModel(getString(R.string.string_forgiveness_1), 8, 32))
        listForgiveness.add(ItemAzkarCategoryModel(getString(R.string.string_forgiveness_2), 8, 33))
        listForgiveness.add(ItemAzkarCategoryModel(getString(R.string.string_forgiveness_3), 8, 34))
        listForgiveness.add(ItemAzkarCategoryModel(getString(R.string.string_forgiveness_4), 8, 35))
        listForgiveness.add(ItemAzkarCategoryModel(getString(R.string.string_forgiveness_5), 8, 36))
        listForgiveness.add(ItemAzkarCategoryModel(getString(R.string.string_forgiveness_6), 8, 37))
        listForgiveness.add(ItemAzkarCategoryModel(getString(R.string.string_forgiveness_7), 8, 38))
        listForgiveness.add(ItemAzkarCategoryModel(getString(R.string.string_forgiveness_8), 8, 39))
        listForgiveness.add(ItemAzkarCategoryModel(getString(R.string.string_forgiveness_9), 8, 40))
        listForgiveness.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_forgiveness_10),
                8,
                41
            )
        )
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_forgiveness),
                R.drawable.ic_forgiveness,
                listForgiveness, 8
            )
        )

        val listPraisingAllah = ArrayList<ItemAzkarCategoryModel>()
        listPraisingAllah.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_praising_allah_1),
                9,
                42
            )
        )
        listPraisingAllah.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_praising_allah_2),
                9,
                43
            )
        )
        listPraisingAllah.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_praising_allah_3),
                9,
                44
            )
        )
        listPraisingAllah.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_praising_allah_4),
                9,
                45
            )
        )
        listPraisingAllah.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_praising_allah_5),
                9,
                46
            )
        )
        listPraisingAllah.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_praising_allah_6),
                9,
                47
            )
        )
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_praising_allah),
                R.drawable.ic_praising_allah,
                listJudgement, 9
            )
        )

        val listProtection = ArrayList<ItemAzkarCategoryModel>()
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_1), 10, 48))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_2), 10, 49))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_3), 10, 50))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_4), 10, 51))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_5), 10, 52))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_6), 10, 53))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_7), 10, 54))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_8), 10, 55))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_9), 10, 56))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_10), 10, 57))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_11), 10, 58))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_12), 10, 59))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_13), 10, 60))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_14), 10, 61))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_15), 10, 62))
        listProtection.add(ItemAzkarCategoryModel(getString(R.string.string_protection_16), 10, 63))
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_protection),
                R.drawable.ic_protection,
                listProtection, 10
            )
        )

        val listFamily = ArrayList<ItemAzkarCategoryModel>()
        listFamily.add(ItemAzkarCategoryModel(getString(R.string.string_family_1), 11, 64))
        listFamily.add(ItemAzkarCategoryModel(getString(R.string.string_family_2), 11, 65))
        listFamily.add(ItemAzkarCategoryModel(getString(R.string.string_family_3), 11, 66))
        listFamily.add(ItemAzkarCategoryModel(getString(R.string.string_family_4), 11, 67))
        listFamily.add(ItemAzkarCategoryModel(getString(R.string.string_family_5), 11, 68))
        listFamily.add(ItemAzkarCategoryModel(getString(R.string.string_family_6), 11, 69))
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_family),
                R.drawable.ic_family,
                listFamily, 11
            )
        )

        val listHealthLiness = ArrayList<ItemAzkarCategoryModel>()
        listHealthLiness.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_health_liness_1),
                12,
                70
            )
        )
        listHealthLiness.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_health_liness_2),
                12,
                71
            )
        )
        listHealthLiness.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_health_liness_3),
                12,
                72
            )
        )
        listHealthLiness.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_health_liness_4),
                12,
                73
            )
        )
        listHealthLiness.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_health_liness_5),
                12,
                74
            )
        )
        listHealthLiness.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_health_liness_6),
                12,
                75
            )
        )
        listHealthLiness.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_health_liness_7),
                12,
                76
            )
        )
        listHealthLiness.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_health_liness_8),
                12,
                77
            )
        )
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_health_liness),
                R.drawable.ic_heart_liness,
                listHealthLiness, 12
            )
        )

        val listLossFailure = ArrayList<ItemAzkarCategoryModel>()
        listLossFailure.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_loss_failure_1),
                13,
                78
            )
        )
        listLossFailure.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_loss_failure_2),
                13,
                79
            )
        )
        listLossFailure.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_loss_failure_3),
                13,
                80
            )
        )
        listLossFailure.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_loss_failure_4),
                13,
                81
            )
        )
        listLossFailure.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_loss_failure_5),
                13,
                82
            )
        )
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_loss_failure),
                R.drawable.ic_loss_failure,
                listLossFailure, 13
            )
        )

        val listSorrowJoy = ArrayList<ItemAzkarCategoryModel>()
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_1), 14, 83))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_2), 14, 84))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_3), 14, 85))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_4), 14, 86))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_5), 14, 87))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_6), 14, 88))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_7), 14, 89))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_8), 14, 90))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_9), 14, 91))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_10), 14, 92))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_11), 14, 93))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_12), 14, 94))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_13), 14, 95))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_14), 14, 96))
        listSorrowJoy.add(ItemAzkarCategoryModel(getString(R.string.string_sorrow_joy_15), 14, 97))
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_sorrow_joy),
                R.drawable.ic_sorrow_joy,
                listSorrowJoy, 14
            )
        )

        val listPattience = ArrayList<ItemAzkarCategoryModel>()
        listPattience.add(ItemAzkarCategoryModel(getString(R.string.string_patience_1), 15, 98))
        listPattience.add(ItemAzkarCategoryModel(getString(R.string.string_patience_2), 15, 99))
        listPattience.add(ItemAzkarCategoryModel(getString(R.string.string_patience_3), 15, 100))
        listPattience.add(ItemAzkarCategoryModel(getString(R.string.string_patience_4), 15, 101))
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_patience),
                R.drawable.ic_patience,
                listPattience, 15
            )
        )

        val listDebt = ArrayList<ItemAzkarCategoryModel>()
        listDebt.add(ItemAzkarCategoryModel(getString(R.string.string_debt_1), 16, 102))
        listDebt.add(ItemAzkarCategoryModel(getString(R.string.string_debt_2), 16, 103))
        listDebt.add(ItemAzkarCategoryModel(getString(R.string.string_debt_3), 16, 104))
        listDebt.add(ItemAzkarCategoryModel(getString(R.string.string_debt_4), 16, 105))
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_debt),
                R.drawable.ic_debt,
                listDebt,
                16
            )
        )

        val listDuringMenstruation = ArrayList<ItemAzkarCategoryModel>()
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_1),
                17,
                106
            )
        )
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_2),
                17,
                107
            )
        )
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_3),
                17,
                108
            )
        )
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_4),
                17,
                109
            )
        )
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_5),
                17,
                110
            )
        )
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_6),
                17,
                111
            )
        )
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_7),
                17,
                112
            )
        )
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_8),
                17,
                113
            )
        )
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_9),
                17,
                114
            )
        )
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_10),
                17,
                115
            )
        )
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_11),
                17,
                116
            )
        )
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_12),
                17,
                117
            )
        )
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_13),
                17,
                118
            )
        )
        listDuringMenstruation.add(
            ItemAzkarCategoryModel(
                getString(R.string.string_during_menstruation_14),
                17,
                119
            )
        )
        azkarList.add(
            ItemAzkarTopicModel(
                getString(R.string.string_during_menstruation),
                R.drawable.ic_during_menstruation,
                listDuringMenstruation, 17
            )
        )

//        if (hasNetworkConnection()) {
//
//            val listDuringMenstruation = arrayListOf<ItemAzkarCategoryModel>()
//
//
//            azkarList.add(
//                4, ItemAzkarTopicModel(
//                    "Ads",
//                    R.drawable.ic_during_menstruation,
//                    listDuringMenstruation, 17
//                )
//            )
//
//
//            azkarList.add(
//                9, ItemAzkarTopicModel(
//                    "Ads",
//                    R.drawable.ic_during_menstruation,
//                    listDuringMenstruation, 17
//                )
//            )
//
//
//            azkarList.add(
//                14, ItemAzkarTopicModel(
//                    "Ads",
//                    R.drawable.ic_during_menstruation,
//                    listDuringMenstruation, 17
//                )
//            )
//
//
////            var insertIndex = 9
////
////            while (insertIndex <= azkarList.size) {
////                azkarList.add(
////                    insertIndex, ItemAzkarTopicModel(
////                        "Ads",
////                        R.drawable.ic_during_menstruation,
////                        listDuringMenstruation, 17
////                    )
////                )
////                insertIndex += 6
////            }
//
//        }
    }

    private fun initData() {
        Global.listAzkar.add(getString(R.string.string_daily_dhikr_1))
        Global.listAzkar.add(getString(R.string.string_daily_dhikr_2))
        Global.listAzkar.add(getString(R.string.string_daily_dhikr_3))
        Global.listAzkar.add(getString(R.string.string_daily_dhikr_4))
        Global.listAzkar.add(getString(R.string.string_daily_dhikr_5))
        Global.listAzkar.add(getString(R.string.string_daily_dhikr_6))
        Global.listAzkar.add(getString(R.string.string_daily_dhikr_7))
        Global.listAzkar.add(getString(R.string.string_daily_rememberence_1))
        Global.listAzkar.add(getString(R.string.string_daily_rememberence_2))
        Global.listAzkar.add(getString(R.string.string_after_prayer_1))
        Global.listAzkar.add(getString(R.string.string_after_prayer_2))
        Global.listAzkar.add(getString(R.string.string_after_prayer_3))
        Global.listAzkar.add(getString(R.string.string_after_prayer_4))
        Global.listAzkar.add(getString(R.string.string_Rizq_sustenance_1))
        Global.listAzkar.add(getString(R.string.string_Rizq_sustenance_2))
        Global.listAzkar.add(getString(R.string.string_Rizq_sustenance_3))
        Global.listAzkar.add(getString(R.string.string_Rizq_sustenance_4))
        Global.listAzkar.add(getString(R.string.string_Rizq_sustenance_5))
        Global.listAzkar.add(getString(R.string.string_knowledge_1))
        Global.listAzkar.add(getString(R.string.string_knowledge_2))
        Global.listAzkar.add(getString(R.string.string_knowledge_3))
        Global.listAzkar.add(getString(R.string.string_knowledge_4))
        Global.listAzkar.add(getString(R.string.string_faith_1))
        Global.listAzkar.add(getString(R.string.string_faith_2))
        Global.listAzkar.add(getString(R.string.string_faith_3))
        Global.listAzkar.add(getString(R.string.string_faith_4))
        Global.listAzkar.add(getString(R.string.string_faith_5))
        Global.listAzkar.add(getString(R.string.string_day_of_judgement_1))
        Global.listAzkar.add(getString(R.string.string_day_of_judgement_2))
        Global.listAzkar.add(getString(R.string.string_day_of_judgement_3))
        Global.listAzkar.add(getString(R.string.string_day_of_judgement_4))
        Global.listAzkar.add(getString(R.string.string_forgiveness_1))
        Global.listAzkar.add(getString(R.string.string_forgiveness_2))
        Global.listAzkar.add(getString(R.string.string_forgiveness_3))
        Global.listAzkar.add(getString(R.string.string_forgiveness_4))
        Global.listAzkar.add(getString(R.string.string_forgiveness_5))
        Global.listAzkar.add(getString(R.string.string_forgiveness_6))
        Global.listAzkar.add(getString(R.string.string_forgiveness_7))
        Global.listAzkar.add(getString(R.string.string_forgiveness_8))
        Global.listAzkar.add(getString(R.string.string_forgiveness_9))
        Global.listAzkar.add(getString(R.string.string_forgiveness_10))
        Global.listAzkar.add(getString(R.string.string_praising_allah_1))
        Global.listAzkar.add(getString(R.string.string_praising_allah_2))
        Global.listAzkar.add(getString(R.string.string_praising_allah_3))
        Global.listAzkar.add(getString(R.string.string_praising_allah_4))
        Global.listAzkar.add(getString(R.string.string_praising_allah_5))
        Global.listAzkar.add(getString(R.string.string_praising_allah_6))
        Global.listAzkar.add(getString(R.string.string_protection_1))
        Global.listAzkar.add(getString(R.string.string_protection_2))
        Global.listAzkar.add(getString(R.string.string_protection_3))
        Global.listAzkar.add(getString(R.string.string_protection_4))
        Global.listAzkar.add(getString(R.string.string_protection_5))
        Global.listAzkar.add(getString(R.string.string_protection_6))
        Global.listAzkar.add(getString(R.string.string_protection_7))
        Global.listAzkar.add(getString(R.string.string_protection_8))
        Global.listAzkar.add(getString(R.string.string_protection_9))
        Global.listAzkar.add(getString(R.string.string_protection_10))
        Global.listAzkar.add(getString(R.string.string_protection_11))
        Global.listAzkar.add(getString(R.string.string_protection_12))
        Global.listAzkar.add(getString(R.string.string_protection_13))
        Global.listAzkar.add(getString(R.string.string_protection_14))
        Global.listAzkar.add(getString(R.string.string_protection_15))
        Global.listAzkar.add(getString(R.string.string_family_1))
        Global.listAzkar.add(getString(R.string.string_family_2))
        Global.listAzkar.add(getString(R.string.string_family_3))
        Global.listAzkar.add(getString(R.string.string_family_4))
        Global.listAzkar.add(getString(R.string.string_family_5))
        Global.listAzkar.add(getString(R.string.string_family_6))
        Global.listAzkar.add(getString(R.string.string_health_liness_1))
        Global.listAzkar.add(getString(R.string.string_health_liness_2))
        Global.listAzkar.add(getString(R.string.string_health_liness_3))
        Global.listAzkar.add(getString(R.string.string_health_liness_4))
        Global.listAzkar.add(getString(R.string.string_health_liness_5))
        Global.listAzkar.add(getString(R.string.string_health_liness_6))
        Global.listAzkar.add(getString(R.string.string_health_liness_7))
        Global.listAzkar.add(getString(R.string.string_health_liness_8))
        Global.listAzkar.add(getString(R.string.string_loss_failure_1))
        Global.listAzkar.add(getString(R.string.string_loss_failure_2))
        Global.listAzkar.add(getString(R.string.string_loss_failure_3))
        Global.listAzkar.add(getString(R.string.string_loss_failure_4))
        Global.listAzkar.add(getString(R.string.string_loss_failure_5))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_1))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_2))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_3))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_4))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_5))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_6))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_7))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_8))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_9))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_10))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_11))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_12))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_13))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_14))
        Global.listAzkar.add(getString(R.string.string_sorrow_joy_15))
        Global.listAzkar.add(getString(R.string.string_patience_1))
        Global.listAzkar.add(getString(R.string.string_patience_2))
        Global.listAzkar.add(getString(R.string.string_patience_3))
        Global.listAzkar.add(getString(R.string.string_patience_4))
        Global.listAzkar.add(getString(R.string.string_debt_1))
        Global.listAzkar.add(getString(R.string.string_debt_2))
        Global.listAzkar.add(getString(R.string.string_debt_3))
        Global.listAzkar.add(getString(R.string.string_debt_4))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_1))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_2))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_3))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_4))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_5))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_6))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_7))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_8))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_9))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_10))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_11))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_12))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_13))
        Global.listAzkar.add(getString(R.string.string_during_menstruation_14))
    }

    override fun onBackPressed() {
        AdsInter.loadInter(
            context = this@AzkarActivity,
            adsId = getString(R.string.inter_back),
            isShow = AdsInter.isLoadInterBack && Admob.getInstance().isLoadFullAds,
            action = {
                if (intent.getStringExtra("start") == "widget") {
                    val intentNew =
                        Intent(this@AzkarActivity, MainActivity::class.java)
                    intentNew.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intentNew)
                } else {
                    finish()
                }
            }
        )
    }
}