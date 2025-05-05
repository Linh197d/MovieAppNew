package com.qibla.muslimday.app2025.ui.intro

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.nlbn.ads.util.Admob
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.base.BaseFragment
import com.qibla.muslimday.app2025.databinding.ActivityIntroBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.ui.intro.tab.FIELD_INTRO_NAVIATE_TYPE
import com.qibla.muslimday.app2025.ui.intro.tab.FIELD_NATIVE_FULL
import com.qibla.muslimday.app2025.ui.intro.tab.FieldIntroFragment
import com.qibla.muslimday.app2025.ui.intro.tab.FieldNativeIntroFullFragmentIntro
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.AdsInter.Companion.loadNativeHome
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>() {
    private var adapter: IntroAdapter? = null

    private val viewModel by viewModels<IntroViewModel>()

    override fun setBinding(layoutInflater: LayoutInflater): ActivityIntroBinding {
        return ActivityIntroBinding.inflate(layoutInflater)
    }

    override fun initView() {
        if (!Admob.getInstance().isLoadFullAds) binding.viewPager2.isUserInputEnabled = false
        val listFragment: MutableList<BaseFragment<*>> = arrayListOf()
        listFragment.add(FieldIntroFragment.getInstance(FIELD_INTRO_NAVIATE_TYPE.INTRO1.value))
        listFragment.add(FieldIntroFragment.getInstance(FIELD_INTRO_NAVIATE_TYPE.INTRO2.value))
        if (Admob.getInstance().isLoadFullAds && AdsInter.isLoadNativeFullIntro2 && hasNetworkConnection()) {
            listFragment.add(FieldNativeIntroFullFragmentIntro.getInstance(FIELD_NATIVE_FULL.INTRO_FULL_2.value))
        }
        listFragment.add(FieldIntroFragment.getInstance(FIELD_INTRO_NAVIATE_TYPE.INTRO3.value))
        if (Admob.getInstance().isLoadFullAds && AdsInter.isLoadNativeFullIntro3 && hasNetworkConnection()) {
            listFragment.add(FieldNativeIntroFullFragmentIntro.getInstance(FIELD_NATIVE_FULL.INTRO_FULL_3.value))
        }
        listFragment.add(FieldIntroFragment.getInstance(FIELD_INTRO_NAVIATE_TYPE.INTRO4.value))
        loadNativeHome(this@IntroActivity)
        viewModel.maxSize = listFragment.size - 1
        adapter = IntroAdapter(this, listFragment)
        binding.viewPager2.adapter = adapter
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.offscreenPageLimit = 3
        binding.viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(0))
        compositePageTransformer.addTransformer { view, position ->
            val r = 1 - abs(position)
            view.scaleY = 0.8f + r * 0.2f
            val absPosition = abs(position)
            view.alpha = 1.0f - (1.0f - 0.3f) * absPosition
        }
        binding.viewPager2.setPageTransformer(compositePageTransformer)

        binding.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                viewModel.currentItem = position
            }
        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.positionState.collect {
                    if (it != null) {
                        binding.viewPager2.currentItem = it
                    }
                    viewModel.resetPosition()
                }
            }
        }
    }


    private fun getData(): ArrayList<GuideModel> {
        val mHelpGuide: ArrayList<GuideModel> = ArrayList()
        mHelpGuide.add(
            GuideModel(
                R.drawable.bg_intro_1,
                getString(R.string.title_intro_1),
                getString(R.string.content_intro_1)
            )
        )
        mHelpGuide.add(
            GuideModel(
                if (Admob.getInstance().isLoadFullAds) R.drawable.bg_intro_2_full else R.drawable.bg_intro_2,
                getString(R.string.title_intro_2),
                getString(R.string.content_intro_2)
            )
        )
        mHelpGuide.add(
            GuideModel(
                R.drawable.bg_intro_3,
                getString(R.string.title_intro_3),
                getString(R.string.content_intro_3)
            )
        )
        mHelpGuide.add(
            GuideModel(
                if (Admob.getInstance().isLoadFullAds) R.drawable.bg_intro_4 else R.drawable.bg_intro_4_normal,
                getString(R.string.title_intro_4),
                getString(R.string.content_intro_4)
            )
        )
        return mHelpGuide
    }
}