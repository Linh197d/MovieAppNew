package com.qibla.muslimday.app2025.ui.allah99name

import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.databinding.Allah99NameActivityBinding
import com.qibla.muslimday.app2025.model.getListAllah99Name
import com.qibla.muslimday.app2025.util.AdsInter

class Allah99NameActivity : BaseActivity<Allah99NameActivityBinding>() {

    companion object {
        private const val OFF_SET = 10_000
    }

    private val adapter by lazy { Allah99NameAdapter() }

    private var player: MediaPlayer? = null

    override fun setBinding(layoutInflater: LayoutInflater): Allah99NameActivityBinding {
        return Allah99NameActivityBinding.inflate(layoutInflater)
    }

    override fun initView() {
        showAdsNative()
        AdsInter.onAdsClick = {
            showAdsNative()
        }
        binding.rvAllah99Name.adapter = adapter
        binding.rvAllah99Name.layoutManager = LinearLayoutManager(this)
        adapter.addList(getListAllah99Name(this))
        binding.ivAllah99NameBack.setOnClickListener {
            finish()
        }
        binding.ivAllah99NamePlay.isSelected = false
        binding.ivAllah99NamePlay.setImageResource(R.drawable.ic_pause_allah)
        initializePlayer()
        binding.ivAllah99NameMinus10.setOnClickListener {
            if (player == null) return@setOnClickListener
            var current = player!!.currentPosition
            println("start = $current")
            if (current >= OFF_SET) {
                current -= OFF_SET
            }
            player!!.seekTo(current)
            println("end = $current -- ${player!!.currentPosition}")
        }

        binding.ivAllah99NamePlus10.setOnClickListener {
            if (player == null) return@setOnClickListener
            var current = player!!.currentPosition
            println("start = $current")
            if (current < player!!.duration - OFF_SET) {
                current += OFF_SET
            }
            player!!.seekTo(current)
            println("end = $current -- ${player!!.currentPosition}")
        }

        binding.flAllah99NamePlay.setOnClickListener {
            binding.flAllah99NamePlay.isSelected = !binding.flAllah99NamePlay.isSelected
            if (binding.flAllah99NamePlay.isSelected) {
                play()
            } else {
                stop()
            }
        }
    }

    private fun showAdsNative() {
        Log.d("ntt", "showAdsNativeQuran: ")
        AdsInter.pushNativeAll(
            context = this@Allah99NameActivity,
            view = binding.frAds,
            keyCheck = AdsInter.isLoadNativeAllah,
            scope = lifecycleScope
        )
    }

    override fun onStop() {
        super.onStop()
        stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun stop() {
        player?.pause()
        binding.ivAllah99NamePlay.setImageResource(R.drawable.ic_pause_allah)
    }

    private fun play() {
        binding.ivAllah99NamePlay.setImageResource(R.drawable.ic_play_yellow)
        player?.start()
    }

    private fun releasePlayer() {
        player?.stop()
        player?.reset()
        player?.release()
        player = null
    }


    private fun initializePlayer() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.namesofallah_99)
        }
        player!!.setOnCompletionListener {
            stop()
            player!!.seekTo(0)
        }
        play()
        binding.flAllah99NamePlay.isSelected = true
    }
}