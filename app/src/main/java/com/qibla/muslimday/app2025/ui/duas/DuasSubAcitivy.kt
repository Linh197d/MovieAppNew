package com.qibla.muslimday.app2025.ui.duas

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.DuasSubActivityBinding
import com.qibla.muslimday.app2025.model.ItemDuasChildModel
import com.qibla.muslimday.app2025.model.ItemDuasModel
import com.qibla.muslimday.app2025.repository.DuasRepository
import com.qibla.muslimday.app2025.ui.adapter.DuasChildAdapter
import com.qibla.muslimday.app2025.ui.adapter.DuasChildAdapter.OnChildItemClickListener
import com.qibla.muslimday.app2025.ui.duas.bookmark.BookMarkDuasActivity
import com.qibla.muslimday.app2025.ui.duas.daily.DailyDuasActivity
import com.qibla.muslimday.app2025.util.AdsInter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DuasSubAcitivy : BaseActivity<DuasSubActivityBinding>() {

    companion object {
        const val DUAS_DATA_KEY = "DUAS_DATA_KEY"
    }

    private var adapter: DuasChildAdapter? = null

    private var itemDuasModel: ItemDuasModel? = null

    @Inject
    lateinit var dailyDuasRepository: DuasRepository

    override fun setBinding(layoutInflater: LayoutInflater): DuasSubActivityBinding {
        return DuasSubActivityBinding.inflate(layoutInflater)
    }

    override fun initView() {
        itemDuasModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(DUAS_DATA_KEY)
        } else {
            intent?.getParcelableExtra(DUAS_DATA_KEY)
        }
        val consentHelper = ConsentHelper.getInstance(this)
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        consentHelper.obtainConsentAndShow(this) {
            AdsInter.loadBanner(this@DuasSubAcitivy, binding.frBanner, binding.root)
        }
        binding.tvDuasSubTitle.text = itemDuasModel?.title
        binding.ivDuasSubBack.setOnClickListener {
            finish()
        }

        binding.ivDuasSubBookMark.setOnClickListener {
            val intent = Intent(this, BookMarkDuasActivity::class.java)
            startActivity(intent)
        }

        adapter = DuasChildAdapter(getListDuasChild(), object :
            OnChildItemClickListener {
            override fun onChildItemClick(childItem: ItemDuasChildModel) {
                val intent = Intent(this@DuasSubAcitivy, DailyDuasActivity::class.java)
                intent.putExtra("data", childItem.title)
                intent.putExtra("id", childItem.id)
                intent.putExtra(DailyDuasActivity.TITLE_KEY, itemDuasModel?.title)
//                        startActivity(intent)

                startActivity(intent)
            }
        })

        binding.rvDuasSub.adapter = adapter
        binding.rvDuasSub.layoutManager = LinearLayoutManager(this)
    }

    private fun getListDuasChild(): List<ItemDuasChildModel> {
        val list: MutableList<ItemDuasChildModel> = arrayListOf()
        val currentList = itemDuasModel?.listDuas ?: arrayListOf()
        currentList.forEachIndexed { _, itemDuasChildModel ->
            val listData = dailyDuasRepository.getDuasListId(itemDuasChildModel.id)
            if (listData.isNotEmpty()) {
                list.add(itemDuasChildModel)
            }
        }
        return list
    }
}