package com.qibla.muslimday.app2025.ui.ramadan

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.ActivityBackgroundRamadanBinding
import com.qibla.muslimday.app2025.ui.ramadan.adapter.BackgroundRamadanAdapter
import com.qibla.muslimday.app2025.util.Const

class BackgroundRamadanActivity : BaseActivity<ActivityBackgroundRamadanBinding>() {

    private lateinit var backgroundRamadanAdapter: BackgroundRamadanAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setBinding(layoutInflater: LayoutInflater): ActivityBackgroundRamadanBinding {
        return ActivityBackgroundRamadanBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.rcvBackgroundRamadan.apply {
            setHasFixedSize(true)

            layoutManager = GridLayoutManager(context, 2)

            backgroundRamadanAdapter = BackgroundRamadanAdapter(
                this@BackgroundRamadanActivity,
                Const.listBackgroundRamadan,
                onClickItemBackground = { data, position ->

                    intent.putExtra("position", position)
                    setResult(RESULT_OK, intent)
                    finish()

                })

            adapter = backgroundRamadanAdapter
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}