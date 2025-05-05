package com.qibla.muslimday.app2025.ui.zakat

import android.view.LayoutInflater
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.ActivityCurrencyUnitBinding
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.model.CurrencyUnitModel
import com.qibla.muslimday.app2025.util.AdsInter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CurrencyUnitActivity : BaseActivity<ActivityCurrencyUnitBinding>() {
    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    @Inject
    lateinit var currencyAdapter: CurrencyAdapter
    val unitModel = mutableListOf<CurrencyUnitModel>()

    override fun setBinding(layoutInflater: LayoutInflater): ActivityCurrencyUnitBinding {
        return ActivityCurrencyUnitBinding.inflate(layoutInflater)
    }

    override fun initView() {
        initData()
        val consentHelper = ConsentHelper.getInstance(this)
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        consentHelper.obtainConsentAndShow(this) {
            AdsInter.loadBanner(this@CurrencyUnitActivity, binding.frBanner, binding.root)
        }
        currencyAdapter.submitList(unitModel)
        binding.rcvCurrencyUnit.adapter = currencyAdapter
        initEvent()
    }

    private fun initEvent() {
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.chooseCurrency.setOnClickListener {
            currencyAdapter.getSelectedLanguage()
                ?.let { it1 -> preferenceHelper.setCurrency(it1.code) }
            finish()
        }
    }

    private fun initData() {
        unitModel.add(CurrencyUnitModel("UK Pound", "GBP"))
        unitModel.add(CurrencyUnitModel("Euro", "EUR"))
        unitModel.add(CurrencyUnitModel("Australian Dollar", "AUD"))
        unitModel.add(CurrencyUnitModel("US Dollar", "USD"))
        unitModel.add(CurrencyUnitModel("Chinese Yuan", "CNY"))
        unitModel.add(CurrencyUnitModel("Indian Rupee", "INR"))
        unitModel.add(CurrencyUnitModel("VIetnamese Dong", "VND"))
        unitModel.add(CurrencyUnitModel("Thai Bath", "THB"))
        unitModel.add(CurrencyUnitModel("Indonesian Rupiah", "IDR"))
    }
}