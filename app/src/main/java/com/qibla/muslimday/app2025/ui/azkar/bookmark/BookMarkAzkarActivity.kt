package com.qibla.muslimday.app2025.ui.azkar.bookmark

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.database.Azkar.AzkarEntity
import com.qibla.muslimday.app2025.databinding.ActivityBookMarkAzkarBinding
import com.qibla.muslimday.app2025.extensions.visible
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.ui.azkar.bookmark.adapter.BookMarkAzkarAdapter
import com.qibla.muslimday.app2025.ui.azkar.bookmark.adapter.BookMarkAzkarChildAdapter
import com.qibla.muslimday.app2025.ui.share.ShareActivity
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Global.Companion.listAzkar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookMarkAzkarActivity : BaseActivity<ActivityBookMarkAzkarBinding>() {
    private val viewModel: BookMarkAzkarViewModel by viewModels()

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun setBinding(layoutInflater: LayoutInflater): ActivityBookMarkAzkarBinding {
        return ActivityBookMarkAzkarBinding.inflate(layoutInflater)
    }

    override fun initView() {
        initAdapter()
        setListeners()
        AdsInter.onAdsClick = {
            loadAdsNativeNoMediaBookmark()
        }
        val consentHelper = ConsentHelper.getInstance(this)
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        consentHelper.obtainConsentAndShow(this) {
            if (Admob.getInstance().isLoadFullAds) {
                binding.frBanner.visible(false)
                binding.layoutNative.visible(true)
                loadAdsNativeNoMediaBookmark()
            } else {
                binding.frBanner.visible(true)
                binding.layoutNative.visible(false)
                AdsInter.loadBanner(this@BookMarkAzkarActivity, binding.frBanner, binding.root)
            }
        }
    }

    private fun loadAdsNativeNoMediaBookmark() {
        AdsInter.pushNativeAll(
            context = this@BookMarkAzkarActivity,
            view = binding.frAds,
            keyCheck = AdsInter.isLoadNativeBookmark,
            isLoadNormal = false,
            scope = lifecycleScope
        )
    }

    private fun initAdapter() {
        viewModel.getItemChecked().observe(this) {
            var group = it.groupBy { item -> item.nameId }.toList()
            if (group.isEmpty()) {
                group = listOf(
                    Pair(
                        8,
                        listOf(
                            AzkarEntity(
                                999,
                                999,
                                999,
                                "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ . (مائة مرة)",
                                "Subhanal-lahi wabihamdih.",
                                R.string.text_morning_rememberence_14
                            )
                        )
                    )
                )
            }
            binding.rcvAzkar.layoutManager = LinearLayoutManager(this)
            val bookMarkAdapter = BookMarkAzkarAdapter(
                this,
                group,
                listAzkar,
                preferenceHelper,
                object : BookMarkAzkarChildAdapter.OnChildItemClickListener {
                    override fun onChildItemClick(childItem: AzkarEntity) {

                        if (childItem.id == 999) {
                            Toast.makeText(
                                this@BookMarkAzkarActivity,
                                getString(R.string.text_sample),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            childItem.isCheck = !childItem.isCheck
                            viewModel.updateIsCheck(childItem.id, childItem.isCheck)
                        }

                    }

                    override fun onChildItemShareClick(childItem: AzkarEntity) {
                        if (childItem.id == 999) {
                            Toast.makeText(
                                this@BookMarkAzkarActivity,
                                getString(R.string.text_sample),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val intent =
                                Intent(this@BookMarkAzkarActivity, ShareActivity::class.java)
                            intent.putExtra("data", childItem)
                            intent.putExtra("type", "azkar")
                            startActivity(intent)
                        }

                    }

                })

            binding.rcvAzkar.adapter = bookMarkAdapter

        }
    }


    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

    }
}