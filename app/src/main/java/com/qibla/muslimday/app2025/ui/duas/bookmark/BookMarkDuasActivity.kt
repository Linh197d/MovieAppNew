package com.qibla.muslimday.app2025.ui.duas.bookmark

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
import com.qibla.muslimday.app2025.database.Duas.DuasEntity
import com.qibla.muslimday.app2025.databinding.ActivityBookMarkDuasBinding
import com.qibla.muslimday.app2025.extensions.visible
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.ui.duas.bookmark.adapter.BookMarkAdapter
import com.qibla.muslimday.app2025.ui.duas.bookmark.adapter.BookMarkChildAdapter
import com.qibla.muslimday.app2025.ui.share.ShareActivity
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Global.Companion.listDuas
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookMarkDuasActivity : BaseActivity<ActivityBookMarkDuasBinding>() {
    private val viewModel: BookMarkViewModel by viewModels()

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    lateinit var adapter: BookMarkAdapter
    override fun setBinding(layoutInflater: LayoutInflater): ActivityBookMarkDuasBinding {
        return ActivityBookMarkDuasBinding.inflate(layoutInflater)
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
                AdsInter.loadBanner(this@BookMarkDuasActivity, binding.frBanner, binding.root)
            }
        }
    }

    private fun loadAdsNativeNoMediaBookmark() {
        AdsInter.pushNativeAll(
            context = this@BookMarkDuasActivity,
            view = binding.frAds,
            keyCheck = AdsInter.isLoadNativeBookmark,
            isLoadNormal = false,
            scope = lifecycleScope
        )
    }


    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun initAdapter() {
        viewModel.getItemChecked().observe(this) {
            var group = it.groupBy { item -> item.nameId }.toList()
            if (group.isEmpty()) {
                group = listOf(
                    Pair(
                        10,
                        listOf(
                            DuasEntity(
                                999,
                                999,
                                999,
                                "َا حَوْلَ وَلَا قُوَّةَ إِلَّا بِالله",
                                "Laa hawla wa laa quwwata 'illaa billaah",
                                R.string.text_during_athan_1
                            )
                        )
                    )
                )
            }

            binding.rcvDuas.layoutManager = LinearLayoutManager(this)
            val bookMarkAdapter = BookMarkAdapter(
                this,
                group,
                listDuas,
                preferenceHelper,
                object : BookMarkChildAdapter.OnChildItemClickListener {
                    override fun onChildItemClick(childItem: DuasEntity) {
                        if (childItem.id == 999) {
                            Toast.makeText(
                                this@BookMarkDuasActivity,
                                getString(R.string.text_sample),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            childItem.isCheck = !childItem.isCheck
                            viewModel.updateIsCheck(childItem.id, childItem.isCheck)
                        }

                    }

                    override fun onChildItemShareClick(childItem: DuasEntity) {
                        if (childItem.id == 999) {
                            Toast.makeText(
                                this@BookMarkDuasActivity,
                                getString(R.string.text_sample),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val intent =
                                Intent(this@BookMarkDuasActivity, ShareActivity::class.java)
                            intent.putExtra("data", childItem)
                            intent.putExtra("type", "duas")
                            startActivity(intent)
                        }
                    }

                })

            binding.rcvDuas.adapter = bookMarkAdapter

        }
    }

}