package com.qibla.muslimday.app2025.ui.duas

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.ActivityDuasBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.model.ItemDuasChildModel
import com.qibla.muslimday.app2025.model.ItemDuasModel
import com.qibla.muslimday.app2025.ui.adapter.DuasAdapter
import com.qibla.muslimday.app2025.ui.adapter.DuasChildAdapter
import com.qibla.muslimday.app2025.ui.duas.bookmark.BookMarkDuasActivity
import com.qibla.muslimday.app2025.ui.duas.daily.DailyDuasActivity
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Global
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DuasActivity : BaseActivity<ActivityDuasBinding>() {
    private val viewModel: DuasViewModel by viewModels()
    private val dailyList = ArrayList<ItemDuasModel>()
    private val occasionalList = ArrayList<ItemDuasModel>()

    private lateinit var dailyAdapter: DuasAdapter

    private lateinit var occasionalAdapter: DuasAdapter
    override fun setBinding(layoutInflater: LayoutInflater): ActivityDuasBinding {
        return ActivityDuasBinding.inflate(layoutInflater)
    }

    override fun initView() {
//        if(!hasNetworkConnection()){
//            showDialogWifi(true)
//        }
        val consentHelper = ConsentHelper.getInstance(this)

        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        consentHelper.obtainConsentAndShow(this) {
            AdsInter.loadBanner(this@DuasActivity, binding.frBanner, binding.root)
        }
        viewModel.initData()
        binding.rcvDaily.setHasFixedSize(true)
        binding.rcvOccasional.setHasFixedSize(true)
        addDataToList()
        if (hasNetworkConnection() && AdsInter.isLoadNativeData1) {
            dailyList.add(
                2, ItemDuasModel(
                    "Ads",
                    R.drawable.ic_daily_dhikr,
                    arrayListOf()
                )
            )
        }
        if (hasNetworkConnection() && AdsInter.isLoadNativeData2) {
            dailyList.add(
                7, ItemDuasModel(
                    "Ads",
                    R.drawable.ic_daily_dhikr,
                    arrayListOf()
                )
            )
        }
        Log.d("PhucVH", "initView: " + dailyList.size)
        binding.rcvDaily.layoutManager = LinearLayoutManager(this)
        binding.rcvOccasional.layoutManager = LinearLayoutManager(this)
        dailyAdapter =
            DuasAdapter(
                lifecycleScope,
                this,
                dailyList,
                object : DuasChildAdapter.OnChildItemClickListener {
                    override fun onChildItemClick(childItem: ItemDuasChildModel) {

//                        lifecycleScope.launch {
//
//                            AdsInter.nativeAdsDuas = null
//                            AdsInter.nativeAdsDuas1 = null
//                            AdsInter.nativeAdsDuas2 = null
//
//                            val loadNativeDuas = async { loadNativeDuas() }
//                            val loadNativeDuas1 = async { loadNativeDuas1() }
//                            val loadNativeDuas2 = async { loadNativeDuas2() }
//
//                            awaitAll(
//                                loadNativeDuas,
//                                loadNativeDuas1,
//                                loadNativeDuas2
//                            )
//                        }

                        val intent = Intent(this@DuasActivity, DailyDuasActivity::class.java)
                        intent.putExtra("data", childItem.title)
                        intent.putExtra("id", childItem.id)
//                        startActivity(intent)
                        startActivityResult.launch(intent)

                    }

                })

        dailyAdapter.onClick = {
            val intent = Intent(this@DuasActivity, DuasSubAcitivy::class.java)
            intent.putExtra(DuasSubAcitivy.DUAS_DATA_KEY, it)
            startActivityResult.launch(intent)
        }

        occasionalAdapter =
            DuasAdapter(
                lifecycleScope,
                this,
                occasionalList,
                object : DuasChildAdapter.OnChildItemClickListener {
                    override fun onChildItemClick(childItem: ItemDuasChildModel) {


                        val intent = Intent(this@DuasActivity, DailyDuasActivity::class.java)
                        intent.putExtra("data", childItem.title)
                        intent.putExtra("id", childItem.id)
//                        startActivity(intent)

                        startActivityResult.launch(intent)
                    }

                })
        occasionalAdapter.onClick = {
            val intent = Intent(this@DuasActivity, DuasSubAcitivy::class.java)
            intent.putExtra(DuasSubAcitivy.DUAS_DATA_KEY, it)
            startActivityResult.launch(intent)
        }
        binding.rcvDaily.adapter = dailyAdapter
        binding.rcvOccasional.adapter = occasionalAdapter

        binding.imgBookMark.setOnClickListener {
            val intent = Intent(this, BookMarkDuasActivity::class.java)

            startActivityResult.launch(intent)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        initData()
        AdsInter.onAdsDataClick = {
            when (it) {
                AdsInter.CLICK_NATIVE_DATA1 -> {
                    if (this::dailyAdapter.isInitialized) {
                        dailyAdapter.notifyItemChanged(2)
                    }
                    Log.d("ttttt", "tttttt")
                }

                AdsInter.CLICK_NATIVE_DATA2 ->
                    if (this::dailyAdapter.isInitialized) {
                        dailyAdapter.notifyItemChanged(7)
                    }
            }
        }
    }


    override fun onResume() {
        super.onResume()

//        if (!hasNetworkConnection() && Const.isCheckWifi ==2){
//            showDialogWifi(true)
//            Const.isCheckWifi = 1
//        }

//        if (isLoadNative) {
//            isLoadNative = false
//
//            if (this::dailyAdapter.isInitialized && this::occasionalAdapter.isInitialized) {
//                dailyAdapter.notifyDataSetChanged()
//                occasionalAdapter.notifyDataSetChanged()
//            }
//        }
    }

    val startActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { _ ->

    }

    private fun addDataToList() {
        //add dailyList

        val listSleeping = ArrayList<ItemDuasChildModel>()
        listSleeping.add(ItemDuasChildModel(1, getString(R.string.string_sleeping_1)))
        listSleeping.add(ItemDuasChildModel(2, getString(R.string.string_sleeping_2)))
        dailyList.add(
            ItemDuasModel(
                getString(R.string.string_sleeping),
                R.drawable.ic_sleeping,
                listSleeping
            )
        )

        val listToilet = ArrayList<ItemDuasChildModel>()
        listToilet.add(ItemDuasChildModel(3, getString(R.string.string_toilet_1)))
        listToilet.add(ItemDuasChildModel(4, getString(R.string.string_toilet_2)))
        dailyList.add(
            ItemDuasModel(
                getString(R.string.string_toilet),
                R.drawable.ic_toilet,
                listToilet
            )
        )


        val listAblution = ArrayList<ItemDuasChildModel>()
        listAblution.add(ItemDuasChildModel(5, getString(R.string.string_ablution_1)))
        listAblution.add(ItemDuasChildModel(6, getString(R.string.string_ablution_2)))
        dailyList.add(
            ItemDuasModel(
                getString(R.string.string_ablution),
                R.drawable.ic_ablution,
                listAblution
            )
        )

        val listMosque = ArrayList<ItemDuasChildModel>()
        listMosque.add(ItemDuasChildModel(7, getString(R.string.string_mosque_1)))
        listMosque.add(ItemDuasChildModel(8, getString(R.string.string_mosque_2)))
        listMosque.add(ItemDuasChildModel(9, getString(R.string.string_mosque_3)))
        dailyList.add(
            ItemDuasModel(
                getString(R.string.string_mosque),
                R.drawable.ic_mosque,
                listMosque
            )
        )

        val listPrayer = ArrayList<ItemDuasChildModel>()
        listPrayer.add(ItemDuasChildModel(10, getString(R.string.string_prayer_1)))
        listPrayer.add(ItemDuasChildModel(11, getString(R.string.string_prayer_2)))
        listPrayer.add(ItemDuasChildModel(12, getString(R.string.string_prayer_3)))
        listPrayer.add(ItemDuasChildModel(13, getString(R.string.string_prayer_4)))
        listPrayer.add(ItemDuasChildModel(14, getString(R.string.string_prayer_5)))
        listPrayer.add(ItemDuasChildModel(15, getString(R.string.string_prayer_6)))
        listPrayer.add(ItemDuasChildModel(16, getString(R.string.string_prayer_7)))
        listPrayer.add(ItemDuasChildModel(17, getString(R.string.string_prayer_8)))
        listPrayer.add(ItemDuasChildModel(18, getString(R.string.string_prayer_9)))
        listPrayer.add(ItemDuasChildModel(19, getString(R.string.string_prayer_10)))
        listPrayer.add(ItemDuasChildModel(20, getString(R.string.string_prayer_11)))
        listPrayer.add(ItemDuasChildModel(21, getString(R.string.string_prayer_12)))
        dailyList.add(
            ItemDuasModel(
                getString(R.string.string_prayer),
                R.drawable.ic_prayer,
                listPrayer
            )
        )

        val listHome = ArrayList<ItemDuasChildModel>()
        listHome.add(ItemDuasChildModel(22, getString(R.string.string_home_1)))
        listHome.add(ItemDuasChildModel(23, getString(R.string.string_home_2)))
        listHome.add(ItemDuasChildModel(24, getString(R.string.string_home_3)))
        listHome.add(ItemDuasChildModel(25, getString(R.string.string_home_4)))
        dailyList.add(ItemDuasModel(getString(R.string.string_home), R.drawable.ic_home, listHome))

        val listGarment = ArrayList<ItemDuasChildModel>()
        listGarment.add(ItemDuasChildModel(26, getString(R.string.string_garment_1)))
        listGarment.add(ItemDuasChildModel(27, getString(R.string.string_garment_2)))
        listGarment.add(ItemDuasChildModel(28, getString(R.string.string_garment_3)))
        dailyList.add(
            ItemDuasModel(
                getString(R.string.string_garment),
                R.drawable.ic_garment,
                listGarment
            )
        )

        val listTravel = ArrayList<ItemDuasChildModel>()
        listTravel.add(ItemDuasChildModel(29, getString(R.string.string_travel_1)))
        listTravel.add(ItemDuasChildModel(30, getString(R.string.string_travel_2)))
        listTravel.add(ItemDuasChildModel(31, getString(R.string.string_travel_3)))
        listTravel.add(ItemDuasChildModel(32, getString(R.string.string_travel_4)))
        listTravel.add(ItemDuasChildModel(33, getString(R.string.string_travel_5)))
        dailyList.add(
            ItemDuasModel(
                getString(R.string.string_travel),
                R.drawable.ic_travel,
                listTravel
            )
        )

        val listFood = ArrayList<ItemDuasChildModel>()
        listFood.add(ItemDuasChildModel(34, getString(R.string.string_food_1)))
        listFood.add(ItemDuasChildModel(35, getString(R.string.string_food_2)))
        listFood.add(ItemDuasChildModel(36, getString(R.string.string_food_3)))
        listFood.add(ItemDuasChildModel(37, getString(R.string.string_food_4)))
        listFood.add(ItemDuasChildModel(38, getString(R.string.string_food_5)))
        listFood.add(ItemDuasChildModel(39, getString(R.string.string_food_6)))
        listFood.add(ItemDuasChildModel(40, getString(R.string.string_food_7)))
        listFood.add(ItemDuasChildModel(41, getString(R.string.string_food_8)))
        listFood.add(ItemDuasChildModel(42, getString(R.string.string_food_9)))
        dailyList.add(ItemDuasModel(getString(R.string.string_food), R.drawable.ic_food, listFood))

        //add occasional list
        val listDeceased = ArrayList<ItemDuasChildModel>()
        listDeceased.add(ItemDuasChildModel(43, getString(R.string.string_deceased_1)))
        listDeceased.add(ItemDuasChildModel(44, getString(R.string.string_deceased_2)))
        listDeceased.add(ItemDuasChildModel(45, getString(R.string.string_deceased_3)))
        listDeceased.add(ItemDuasChildModel(46, getString(R.string.string_deceased_4)))
        listDeceased.add(ItemDuasChildModel(47, getString(R.string.string_deceased_5)))
        listDeceased.add(ItemDuasChildModel(48, getString(R.string.string_deceased_6)))
        occasionalList.add(
            ItemDuasModel(
                getString(R.string.string_deceased),
                R.drawable.ic_deceased,
                listDeceased
            )
        )

        val listHajjUmbrah = ArrayList<ItemDuasChildModel>()
        listHajjUmbrah.add(ItemDuasChildModel(49, getString(R.string.string_haj_umrah_1)))
        listHajjUmbrah.add(ItemDuasChildModel(50, getString(R.string.string_haj_umrah_2)))
        listHajjUmbrah.add(ItemDuasChildModel(51, getString(R.string.string_haj_umrah_3)))
        listHajjUmbrah.add(ItemDuasChildModel(52, getString(R.string.string_haj_umrah_4)))
        listHajjUmbrah.add(ItemDuasChildModel(53, getString(R.string.string_haj_umrah_5)))
        listHajjUmbrah.add(ItemDuasChildModel(54, getString(R.string.string_haj_umrah_6)))
        listHajjUmbrah.add(ItemDuasChildModel(55, getString(R.string.string_haj_umrah_7)))
        listHajjUmbrah.add(ItemDuasChildModel(56, getString(R.string.string_haj_umrah_8)))
        listHajjUmbrah.add(ItemDuasChildModel(57, getString(R.string.string_haj_umrah_9)))
        occasionalList.add(
            ItemDuasModel(
                getString(R.string.string_haj_umrah),
                R.drawable.ic_hajj_umbrah,
                listHajjUmbrah
            )
        )

        val listRamadan = ArrayList<ItemDuasChildModel>()
        listRamadan.add(ItemDuasChildModel(58, getString(R.string.string_ramadan_1)))
        listRamadan.add(ItemDuasChildModel(59, getString(R.string.string_ramadan_2)))
        listRamadan.add(ItemDuasChildModel(60, getString(R.string.string_ramadan_3)))
        listRamadan.add(ItemDuasChildModel(61, getString(R.string.string_ramadan_4)))
        occasionalList.add(
            ItemDuasModel(
                getString(R.string.string_ramadan),
                R.drawable.ic_ramadan,
                listRamadan
            )
        )

        val listNature = ArrayList<ItemDuasChildModel>()
        listNature.add(ItemDuasChildModel(62, getString(R.string.string_nature_1)))
        listNature.add(ItemDuasChildModel(63, getString(R.string.string_nature_2)))
        listNature.add(ItemDuasChildModel(64, getString(R.string.string_nature_3)))
        listNature.add(ItemDuasChildModel(65, getString(R.string.string_nature_4)))
        listNature.add(ItemDuasChildModel(66, getString(R.string.string_nature_5)))
        listNature.add(ItemDuasChildModel(67, getString(R.string.string_nature_6)))
        occasionalList.add(
            ItemDuasModel(
                getString(R.string.string_nature),
                R.drawable.ic_nature,
                listNature
            )
        )

        val listEttiquete = ArrayList<ItemDuasChildModel>()
        listEttiquete.add(ItemDuasChildModel(68, getString(R.string.string_good_ettiquete_1)))
        listEttiquete.add(ItemDuasChildModel(69, getString(R.string.string_good_ettiquete_2)))
        listEttiquete.add(ItemDuasChildModel(70, getString(R.string.string_good_ettiquete_3)))
        listEttiquete.add(ItemDuasChildModel(71, getString(R.string.string_good_ettiquete_4)))
        listEttiquete.add(ItemDuasChildModel(72, getString(R.string.string_good_ettiquete_5)))
        listEttiquete.add(ItemDuasChildModel(73, getString(R.string.string_good_ettiquete_6)))
        listEttiquete.add(ItemDuasChildModel(74, getString(R.string.string_good_ettiquete_7)))
        listEttiquete.add(ItemDuasChildModel(75, getString(R.string.string_good_ettiquete_8)))
        listEttiquete.add(ItemDuasChildModel(76, getString(R.string.string_good_ettiquete_9)))
        occasionalList.add(
            ItemDuasModel(
                getString(R.string.string_good_ettiquete),
                R.drawable.ic_good_ettiquete,
                listEttiquete
            )
        )

        val listDecision = ArrayList<ItemDuasChildModel>()
        listDecision.add(ItemDuasChildModel(77, getString(R.string.string_decision_guidance_1)))
        listDecision.add(ItemDuasChildModel(78, getString(R.string.string_decision_guidance_2)))
        listDecision.add(ItemDuasChildModel(79, getString(R.string.string_decision_guidance_3)))
        listDecision.add(ItemDuasChildModel(80, getString(R.string.string_decision_guidance_4)))
        listDecision.add(ItemDuasChildModel(81, getString(R.string.string_decision_guidance_5)))
        listDecision.add(ItemDuasChildModel(82, getString(R.string.string_decision_guidance_6)))
        occasionalList.add(
            ItemDuasModel(
                getString(R.string.string_decision_guidance),
                R.drawable.ic_decision_guidance,
                listDecision
            )
        )

    }

    private fun initData() {
        if (Global.listDuas.isEmpty()) {
            Global.listDuas.add(getString(R.string.string_sleeping_1))
            Global.listDuas.add(getString(R.string.string_sleeping_2))
            Global.listDuas.add(getString(R.string.string_toilet_1))
            Global.listDuas.add(getString(R.string.string_toilet_2))
            Global.listDuas.add(getString(R.string.string_ablution_1))
            Global.listDuas.add(getString(R.string.string_ablution_2))
            Global.listDuas.add(getString(R.string.string_mosque_1))
            Global.listDuas.add(getString(R.string.string_mosque_2))
            Global.listDuas.add(getString(R.string.string_mosque_3))
            Global.listDuas.add(getString(R.string.string_prayer_1))
            Global.listDuas.add(getString(R.string.string_prayer_2))
            Global.listDuas.add(getString(R.string.string_prayer_3))
            Global.listDuas.add(getString(R.string.string_prayer_4))
            Global.listDuas.add(getString(R.string.string_prayer_5))
            Global.listDuas.add(getString(R.string.string_prayer_6))
            Global.listDuas.add(getString(R.string.string_prayer_7))
            Global.listDuas.add(getString(R.string.string_prayer_8))
            Global.listDuas.add(getString(R.string.string_prayer_9))
            Global.listDuas.add(getString(R.string.string_prayer_10))
            Global.listDuas.add(getString(R.string.string_prayer_11))
            Global.listDuas.add(getString(R.string.string_prayer_12))
            Global.listDuas.add(getString(R.string.string_home_1))
            Global.listDuas.add(getString(R.string.string_home_2))
            Global.listDuas.add(getString(R.string.string_home_3))
            Global.listDuas.add(getString(R.string.string_home_4))
            Global.listDuas.add(getString(R.string.string_garment_1))
            Global.listDuas.add(getString(R.string.string_garment_2))
            Global.listDuas.add(getString(R.string.string_garment_3))
            Global.listDuas.add(getString(R.string.string_travel_1))
            Global.listDuas.add(getString(R.string.string_travel_2))
            Global.listDuas.add(getString(R.string.string_travel_3))
            Global.listDuas.add(getString(R.string.string_travel_4))
            Global.listDuas.add(getString(R.string.string_travel_5))
            Global.listDuas.add(getString(R.string.string_food_1))
            Global.listDuas.add(getString(R.string.string_food_2))
            Global.listDuas.add(getString(R.string.string_food_3))
            Global.listDuas.add(getString(R.string.string_food_4))
            Global.listDuas.add(getString(R.string.string_food_5))
            Global.listDuas.add(getString(R.string.string_food_6))
            Global.listDuas.add(getString(R.string.string_food_7))
            Global.listDuas.add(getString(R.string.string_food_8))
            Global.listDuas.add(getString(R.string.string_food_9))
            Global.listDuas.add(getString(R.string.string_deceased_1))
            Global.listDuas.add(getString(R.string.string_deceased_2))
            Global.listDuas.add(getString(R.string.string_deceased_3))
            Global.listDuas.add(getString(R.string.string_deceased_4))
            Global.listDuas.add(getString(R.string.string_deceased_5))
            Global.listDuas.add(getString(R.string.string_deceased_6))
            Global.listDuas.add(getString(R.string.string_haj_umrah_1))
            Global.listDuas.add(getString(R.string.string_haj_umrah_2))
            Global.listDuas.add(getString(R.string.string_haj_umrah_3))
            Global.listDuas.add(getString(R.string.string_haj_umrah_4))
            Global.listDuas.add(getString(R.string.string_haj_umrah_5))
            Global.listDuas.add(getString(R.string.string_haj_umrah_6))
            Global.listDuas.add(getString(R.string.string_haj_umrah_7))
            Global.listDuas.add(getString(R.string.string_haj_umrah_8))
            Global.listDuas.add(getString(R.string.string_haj_umrah_9))
            Global.listDuas.add(getString(R.string.string_ramadan_1))
            Global.listDuas.add(getString(R.string.string_ramadan_2))
            Global.listDuas.add(getString(R.string.string_ramadan_3))
            Global.listDuas.add(getString(R.string.string_ramadan_4))
            Global.listDuas.add(getString(R.string.string_nature_1))
            Global.listDuas.add(getString(R.string.string_nature_2))
            Global.listDuas.add(getString(R.string.string_nature_3))
            Global.listDuas.add(getString(R.string.string_nature_4))
            Global.listDuas.add(getString(R.string.string_nature_5))
            Global.listDuas.add(getString(R.string.string_nature_6))
            Global.listDuas.add(getString(R.string.string_good_ettiquete_1))
            Global.listDuas.add(getString(R.string.string_good_ettiquete_2))
            Global.listDuas.add(getString(R.string.string_good_ettiquete_3))
            Global.listDuas.add(getString(R.string.string_good_ettiquete_4))
            Global.listDuas.add(getString(R.string.string_good_ettiquete_5))
            Global.listDuas.add(getString(R.string.string_good_ettiquete_6))
            Global.listDuas.add(getString(R.string.string_good_ettiquete_7))
            Global.listDuas.add(getString(R.string.string_good_ettiquete_8))
            Global.listDuas.add(getString(R.string.string_good_ettiquete_9))
            Global.listDuas.add(getString(R.string.string_decision_guidance_1))
            Global.listDuas.add(getString(R.string.string_decision_guidance_2))
            Global.listDuas.add(getString(R.string.string_decision_guidance_3))
            Global.listDuas.add(getString(R.string.string_decision_guidance_4))
            Global.listDuas.add(getString(R.string.string_decision_guidance_5))
            Global.listDuas.add(getString(R.string.string_decision_guidance_6))
        }

    }

    override fun onBackPressed() {
        AdsInter.loadInter(
            context = this@DuasActivity,
            adsId = getString(R.string.inter_back),
            isShow = AdsInter.isLoadInterBack && Admob.getInstance().isLoadFullAds,
            action = {
                finish()
            }
        )
    }
}