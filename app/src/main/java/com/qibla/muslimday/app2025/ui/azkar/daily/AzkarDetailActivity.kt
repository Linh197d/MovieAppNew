package com.qibla.muslimday.app2025.ui.azkar.daily

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.database.Azkar.AzkarEntity
import com.qibla.muslimday.app2025.databinding.ActivityAzkarDetailBinding
import com.qibla.muslimday.app2025.extensions.visible
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.ui.azkar.daily.adapter.AzkarDetailAdapter
import com.qibla.muslimday.app2025.ui.azkar.daily.adapter.IonClickItemAzkarDetail
import com.qibla.muslimday.app2025.ui.share.ShareActivity
import com.qibla.muslimday.app2025.util.AdsInter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AzkarDetailActivity : BaseActivity<ActivityAzkarDetailBinding>(), IonClickItemAzkarDetail {

    companion object {
        const val TITLE_KEY = "TITLE_KEY"
    }

    private val viewModel: AzkarDetailViewModel by viewModels()

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private lateinit var adapter: AzkarDetailAdapter
    override fun setBinding(layoutInflater: LayoutInflater): ActivityAzkarDetailBinding {
        return ActivityAzkarDetailBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val consentHelper = ConsentHelper.getInstance(this)
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        AdsInter.onAdsClick = {
            showAdsNativeDetail()
        }
        consentHelper.obtainConsentAndShow(this) {
            if (Admob.getInstance().isLoadFullAds) {
                binding.frBanner.visible(false)
                binding.frAds.visible(true)
                showAdsNativeDetail()
            } else {
                binding.frBanner.visible(true)
                binding.frAds.visible(false)
                AdsInter.loadBanner(this@AzkarDetailActivity, binding.frBanner, binding.root)
            }
        }
        initAdapter()
        setListeners()
    }

    override fun onClickBookMarkItem(azkarEntity: AzkarEntity) {
        val bundle = Bundle()

        azkarEntity.isCheck = !azkarEntity.isCheck

//        FirebaseAnalytics.getInstance(this).logEvent("event_azkarbookmark", bundle)

        viewModel.updateIsCheck(azkarEntity.id, azkarEntity.isCheck)
    }

    override fun onClickShareItem(azkarEntity: AzkarEntity) {
        val intent = Intent(this, ShareActivity::class.java)
        intent.putExtra("data", azkarEntity)
        intent.putExtra("type", "azkar")
        startActivity(intent)
    }

    private fun initAdapter() {
        val data = intent.getStringExtra("data")
        val id = intent.getIntExtra("id", 1)
        val title = intent.getStringExtra(TITLE_KEY)
        binding.tvAzkarDetailTitle.text = title
//        binding.tvAzkarDetailTitle.isSelected = true
//        binding.tvAzkarSubDetail.isSelected = true
        if (data != null) {
            binding.tvAzkarSubDetail.text = data
            viewModel.getAzkarId(id).observe(this) {
                val layoutManager = LinearLayoutManager(this)
                binding.rcvDuas.layoutManager = layoutManager
                adapter = AzkarDetailAdapter(this, preferenceHelper, it, this)
                binding.rcvDuas.adapter = adapter

            }
        }
    }

    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showAdsNativeDetail() {
        Log.d("ntt", "showAdsNativeQuran: ")
        AdsInter.pushNativeAll(
            context = this@AzkarDetailActivity,
            view = binding.frAds,
            keyCheck = AdsInter.isLoadNativeDetail,
            isLoadNormal = false,
            scope = lifecycleScope
        )
    }

    override fun onBackPressed() {
        AdsInter.loadInter(
            context = this@AzkarDetailActivity,
            adsId = getString(R.string.inter_detail),
            isShow = AdsInter.isLoadInterDetail,
            action = {
                finish()
            }
        )
    }
}