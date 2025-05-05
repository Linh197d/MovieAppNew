package com.qibla.muslimday.app2025.ui.zakat

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.ActivityZakatBinding
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.util.AdsInter
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class ZakatActivity : BaseActivity<ActivityZakatBinding>() {
    private var unitCurrency = "$"

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun setBinding(layoutInflater: LayoutInflater): ActivityZakatBinding {
        return ActivityZakatBinding.inflate(layoutInflater)
    }

    override fun initView() {
        AdsInter.onAdsClick = {
            showAdsNative()
        }
        showAdsNative()
        val consentHelper = ConsentHelper.getInstance(this)
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        consentHelper.obtainConsentAndShow(this) {
            AdsInter.loadBanner(this@ZakatActivity, binding.frBanner, binding.root)
        }
        unitCurrency = preferenceHelper.getCurrency() ?: "$"
        setDataCurrency()
        eventChangeText()
        initEvent()
    }

    private fun showAdsNative() {
        AdsInter.pushNativeAll(
            context = this@ZakatActivity,
            view = binding.frAds,
            keyCheck = AdsInter.isLoadNativeZakat,
            isLoadNormal = false,
            scope = lifecycleScope
        )
    }

    private fun eventChangeText() {
        val numberTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) return

                val editText =
                    binding.root.findFocus() as? EditText ?: return // Lấy EditText đang nhập

                editText.removeTextChangedListener(this) // Xóa listener để tránh vòng lặp vô hạn

                val cleanString = s.toString().replace(",", "") // Xóa dấu phẩy cũ
                val number = cleanString.toDoubleOrNull() ?: return

                val formatted =
                    NumberFormat.getNumberInstance(Locale.US).format(number) // Định dạng số

                editText.setText(formatted)
                editText.setSelection(formatted.length) // Giữ con trỏ ở cuối

                editText.addTextChangedListener(this) // Gắn lại listener
            }
        }

// Gán TextWatcher cho nhiều EditText
        binding.edtMoney.addTextChangedListener(numberTextWatcher)
        binding.edtGold.addTextChangedListener(numberTextWatcher)
        binding.edtSilver.addTextChangedListener(numberTextWatcher)
        binding.edtMoney.addTextChangedListener(numberTextWatcher)
        binding.edtInvestment.addTextChangedListener(numberTextWatcher)
        binding.edtBusiness.addTextChangedListener(numberTextWatcher)
        binding.edtOther.addTextChangedListener(numberTextWatcher)
        binding.edtDebt.addTextChangedListener(numberTextWatcher)
        binding.edtNisab.addTextChangedListener(numberTextWatcher)
    }

    private fun initEvent() {
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.changeCurrency.setOnClickListener {
            startActivity(Intent(this, CurrencyUnitActivity::class.java))
        }
        binding.btnCalculate.setOnClickListener {
            calculateZakat()
        }
    }

    private fun calculateZakat() {
        val numGold = binding.edtGold.text.toString().replace(",", "").toFloatOrNull() ?: 0f
        val numSilver = binding.edtSilver.text.toString().replace(",", "").toFloatOrNull() ?: 0f
        val numMoney = binding.edtMoney.text.toString().replace(",", "").toFloatOrNull() ?: 0f
        val numInvestment =
            binding.edtInvestment.text.toString().replace(",", "").toFloatOrNull() ?: 0f
        val numBussiness =
            binding.edtBusiness.text.toString().replace(",", "").toFloatOrNull() ?: 0f
        val numOther = binding.edtOther.text.toString().replace(",", "").toFloatOrNull() ?: 0f
        val numDebt = binding.edtDebt.text.toString().replace(",", "").toFloatOrNull() ?: 0f
        val numNisab = binding.edtNisab.text.toString().replace(",", "").toFloatOrNull() ?: 0f
        val propertyCount = numGold + numSilver + numMoney + numInvestment + numBussiness + numOther
        val exchangeNum = propertyCount - numDebt
        if (exchangeNum <= numNisab) {
            binding.totalZakat.text = " 0 $unitCurrency"
        } else {
            val formattedAmount =
                NumberFormat.getNumberInstance(Locale.US).format(0.025 * exchangeNum)
            binding.totalZakat.text = "$formattedAmount $unitCurrency"
//            binding.totalZakat.text = (0.025 * exchangeNum).toString() + " $unitCurrency"
        }
        binding.totalZakat.isSelected = true  // Kích hoạt hiệu ứng marquee

    }

    override fun onResume() {
        super.onResume()
        unitCurrency = preferenceHelper.getCurrency() ?: "$"
        setDataCurrency()
    }

    private fun setDataCurrency() {
        Log.d("unitChange", unitCurrency)
        binding.unitGold.text = unitCurrency
        binding.unitSilver.text = unitCurrency
        binding.unitMoney.text = unitCurrency
        binding.unitInvestment.text = unitCurrency
        binding.unitBussinessAssest.text = unitCurrency
        binding.unitOther.text = unitCurrency
        binding.unitDebt.text = unitCurrency
        binding.unitNisab.text = unitCurrency
        binding.totalZakat.text = "0.00$unitCurrency"
    }
}