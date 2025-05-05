package com.qibla.muslimday.app2025.ui.azkar

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.AzkarSubActivityBinding
import com.qibla.muslimday.app2025.model.ItemAzkarCategoryModel
import com.qibla.muslimday.app2025.model.ItemAzkarTopicModel
import com.qibla.muslimday.app2025.repository.AzkarRepository
import com.qibla.muslimday.app2025.ui.adapter.AzkarChildAdapter
import com.qibla.muslimday.app2025.ui.adapter.AzkarChildAdapter.OnChildItemClickListener
import com.qibla.muslimday.app2025.ui.azkar.bookmark.BookMarkAzkarActivity
import com.qibla.muslimday.app2025.ui.azkar.daily.AzkarDetailActivity
import com.qibla.muslimday.app2025.util.AdsInter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AzkarSubActivity : BaseActivity<AzkarSubActivityBinding>() {

    companion object {
        const val DATA_KEY = "DATA_KEY"
    }

    private var itemAzkar: ItemAzkarTopicModel? = null

    private var childAdapter: AzkarChildAdapter? = null

    @Inject
    lateinit var azkarRepository: AzkarRepository

    private val startActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { _ ->

    }

    override fun setBinding(layoutInflater: LayoutInflater): AzkarSubActivityBinding {
        return AzkarSubActivityBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val consentHelper = ConsentHelper.getInstance(this)
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        consentHelper.obtainConsentAndShow(this) {
            AdsInter.loadBanner(this@AzkarSubActivity, binding.frBanner, binding.root)
        }
        itemAzkar = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(DATA_KEY, ItemAzkarTopicModel::class.java)
        } else {
            intent?.getParcelableExtra(DATA_KEY)
        }

        binding.tvAzkarSubTitle.text = itemAzkar?.title



        childAdapter = AzkarChildAdapter(getListAzkarChild(), object :
            OnChildItemClickListener {
            override fun onChildItemClick(childItem: ItemAzkarCategoryModel) {
                val intent = Intent(this@AzkarSubActivity, AzkarDetailActivity::class.java)
                intent.putExtra(AzkarDetailActivity.TITLE_KEY, itemAzkar?.title)
                intent.putExtra("data", childItem.title)
                intent.putExtra("id", childItem.categoryId)
                startActivityResult.launch(intent)
            }
        })
        binding.rcvAzkarSub.adapter = childAdapter
        binding.rcvAzkarSub.layoutManager = LinearLayoutManager(this)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.imgBookMark.setOnClickListener {
            val intent = Intent(this, BookMarkAzkarActivity::class.java)
            startActivityResult.launch(intent)
        }
    }

    private fun getListAzkarChild(): List<ItemAzkarCategoryModel> {
        val list: MutableList<ItemAzkarCategoryModel> = arrayListOf()
        val currentList = itemAzkar?.listAzkar ?: arrayListOf()
        currentList.forEachIndexed { _, data ->
            val listData = azkarRepository.getAzkarListId(data.categoryId)
            if (listData.isNotEmpty()) {
                list.add(data)
            }
        }
        return list
    }
}