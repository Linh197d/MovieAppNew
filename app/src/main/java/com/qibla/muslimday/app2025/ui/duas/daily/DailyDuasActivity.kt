package com.qibla.muslimday.app2025.ui.duas.daily

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.database.Duas.DuasEntity
import com.qibla.muslimday.app2025.databinding.ActivityDailyDuasBinding
import com.qibla.muslimday.app2025.extensions.visible
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.ui.duas.daily.adapter.DailyDuasAdapter
import com.qibla.muslimday.app2025.ui.duas.daily.adapter.IonClickItemDailyDuas
import com.qibla.muslimday.app2025.ui.share.ShareActivity
import com.qibla.muslimday.app2025.util.AdsInter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DailyDuasActivity : BaseActivity<ActivityDailyDuasBinding>(), IonClickItemDailyDuas {

    companion object {
        const val TITLE_KEY = "TITLE_KEY"
    }

    private val viewModel: DailyDuasViewModel by viewModels()

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private lateinit var adapter: DailyDuasAdapter

    private var title: String? = null

    override fun setBinding(layoutInflater: LayoutInflater): ActivityDailyDuasBinding {
        return ActivityDailyDuasBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val consentHelper = ConsentHelper.getInstance(this)
        AdsInter.onAdsClick = {
            showAdsNativeDetail()
        }
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        consentHelper.obtainConsentAndShow(this) {
            if (Admob.getInstance().isLoadFullAds) {
                binding.frBanner.visible(false)
                binding.frAds.visible(true)
                showAdsNativeDetail()
            } else {
                binding.frBanner.visible(true)
                binding.frAds.visible(false)
                AdsInter.loadBanner(this@DailyDuasActivity, binding.frBanner, binding.root)

            }
        }
        initAdapter()
        setListeners()
        binding.tvDailyDes.isSelected = true
    }

    private fun initAdapter() {
        val data = intent.getStringExtra("data")
        val id = intent.getIntExtra("id", 1)
        title = intent.getStringExtra(TITLE_KEY)
        binding.tv1.text = title
        binding.tvDailyDes.text = data
        if (data != null) {
            binding.tvName.text = data
            viewModel.getDailyDuasId(id).observe(this) {
                val layoutManager = LinearLayoutManager(this)
                binding.rcvDuas.layoutManager = layoutManager
                adapter = DailyDuasAdapter(this, preferenceHelper, it, this)
                binding.rcvDuas.adapter = adapter
            }
        }
    }

    override fun onClickBookMarkItemDailyDuas(dailyDuasEntity: DuasEntity) {
        dailyDuasEntity.isCheck = !dailyDuasEntity.isCheck
        viewModel.updateIsCheck(dailyDuasEntity.id, dailyDuasEntity.isCheck)
    }

    override fun onClickShareItemDailyDuas(dailyDuasEntity: DuasEntity) {
        val intent = Intent(this, ShareActivity::class.java)
        intent.putExtra("data", dailyDuasEntity)
        intent.putExtra("type", "duas")
        startActivity(intent)
    }


    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showAdsNativeDetail() {
        Log.d("ntt", "showAdsNativeQuran: ")
        AdsInter.pushNativeAll(
            context = this@DailyDuasActivity,
            view = binding.frAds,
            keyCheck = AdsInter.isLoadNativeDetail,
            isLoadNormal = false,
            scope = lifecycleScope
        )
    }

    override fun onBackPressed() {
        AdsInter.loadInter(
            context = this@DailyDuasActivity,
            adsId = getString(R.string.inter_detail),
            isShow = AdsInter.isLoadInterDetail,
            action = {
                finish()
            }
        )
    }

}