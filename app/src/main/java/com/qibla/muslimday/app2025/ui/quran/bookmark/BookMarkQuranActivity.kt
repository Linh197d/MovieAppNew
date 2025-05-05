package com.qibla.muslimday.app2025.ui.quran.bookmark

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.database.Quran.QuranEntity
import com.qibla.muslimday.app2025.databinding.ActivityBookMarkQuranBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.extensions.visible
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.ui.quran.adapter.BookMarkAdapter
import com.qibla.muslimday.app2025.ui.quran.adapter.IClickItemQuran
import com.qibla.muslimday.app2025.ui.quran.adapter.IOnClickItemQuranDetail
import com.qibla.muslimday.app2025.ui.quran.adapter.QuranDetailBookMarkAdapter
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Verse
import com.qibla.muslimday.app2025.ui.quran.quranDetail.QuranDetailActivity
import com.qibla.muslimday.app2025.ui.quran.quranDetail.QuranDetailViewModel
import com.qibla.muslimday.app2025.ui.share.ShareActivity
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Const
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class BookMarkQuranActivity : BaseActivity<ActivityBookMarkQuranBinding>(), IClickItemQuran,
    IOnClickItemQuranDetail {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val viewModel: BookMarkViewModel by viewModels()
    private val viewModelQuran: QuranDetailViewModel by viewModels()

    private lateinit var adapter: BookMarkAdapter
    private lateinit var adapterAyah: QuranDetailBookMarkAdapter
    private var selectList = 1

    private var mediaPlayer: MediaPlayer = MediaPlayer()

    private var currentPositionSong = 0

    private var mMediaState: Int = Const.MEDIA_QURAN_IDLE

    private var isAutoScrollAudio = true

    private var listVerse = mutableListOf<Verse>()


    override fun setBinding(layoutInflater: LayoutInflater): ActivityBookMarkQuranBinding {
        return ActivityBookMarkQuranBinding.inflate(layoutInflater)
    }

    override fun initView() {
        initAdapter()
        setListeners()

        isAutoScrollAudio = preferenceHelper.getIsScrollWithAudio()
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
                AdsInter.loadBanner(this@BookMarkQuranActivity, binding.frBanner, binding.root)
            }
        }

    }

    private fun loadAdsNativeNoMediaBookmark() {
        AdsInter.pushNativeAll(
            this@BookMarkQuranActivity,
            keyCheck = AdsInter.isLoadNativeBookmark && Admob.getInstance().isLoadFullAds,
            view = binding.frAds,
            scope = lifecycleScope
        )
    }


    private fun initAdapter() {
        if (selectList == 1) {
            viewModel.getItemChecked("surah").observe(this) {
                if (it.isEmpty()) {
                    binding.rl1.visibility = View.VISIBLE
                    binding.rcvQuran.visibility = View.GONE
                } else {
                    binding.rl1.visibility = View.GONE
                    binding.rcvQuran.visibility = View.VISIBLE
                }
                if (selectList == 1) {
                    adapter = BookMarkAdapter(it, this)
                    binding.rcvQuran.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.rcvQuran.adapter = adapter
                }

            }

        } else if (selectList == 2) {
            viewModel.getItemChecked("juz").observe(this) {
                if (it.isEmpty()) {
                    binding.rl1.visibility = View.VISIBLE
                    binding.rcvQuran.visibility = View.GONE
                } else {
                    binding.rl1.visibility = View.GONE
                    binding.rcvQuran.visibility = View.VISIBLE
                }
                if (selectList == 2) {
                    adapter = BookMarkAdapter(it, this)
                    binding.rcvQuran.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.rcvQuran.adapter = adapter
                }

            }
        } else if (selectList == 3) {
            viewModelQuran.getItemVerseChecked().observe(this) {
                if (it.isEmpty()) {
                    binding.rl1.visibility = View.VISIBLE
                    binding.rcvQuran.visibility = View.GONE
                } else {
                    binding.rl1.visibility = View.GONE
                    binding.rcvQuran.visibility = View.VISIBLE
                }
                listVerse.clear()
                listVerse.addAll(it)
                adapterAyah =
                    QuranDetailBookMarkAdapter(
                        this,
                        viewModelQuran,
                        preferenceHelper,
                        listVerse,
                        this
                    )
                binding.rcvQuran.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                binding.rcvQuran.adapter = adapterAyah

            }
        }
    }

    private fun setListeners() {
        binding.btnSurah.setOnClickListener {
            binding.layoutPlayerSound.visibility = View.GONE
            disableSound()
            selectList = 1
            binding.btnSurah.setBackgroundResource(R.drawable.bg_btn_surah_quran)
            binding.btnJuz.setBackgroundResource(R.color.transparent)
            binding.btnAyah.setBackgroundResource(R.color.transparent)
            initAdapter()
        }

        binding.btnJuz.setOnClickListener {
            binding.layoutPlayerSound.visibility = View.GONE
            disableSound()

            selectList = 2
            binding.btnJuz.setBackgroundResource(R.drawable.bg_btn_surah_quran)
            binding.btnSurah.setBackgroundResource(R.color.transparent)
            binding.btnAyah.setBackgroundResource(R.color.transparent)
            initAdapter()
        }
        binding.btnAyah.setOnClickListener {
            selectList = 3
            binding.btnAyah.setBackgroundResource(R.drawable.bg_btn_surah_quran)
            binding.btnJuz.setBackgroundResource(R.color.transparent)
            binding.btnSurah.setBackgroundResource(R.color.transparent)
            initAdapter()

            mediaPlayer = MediaPlayer()
            updateButtonPlayPause(mediaPlayer)

        }
        binding.btnBack.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.btnPrevious.setOnClickListener {
            previous()
        }

        binding.btnNext.setOnClickListener {
            next()
        }

        binding.btnPlay.setOnClickListener {
            listVerse[currentPositionSong].audio?.let { it1 -> playSound(it1.url) }
        }

        binding.rl1.setOnClickListener {
            binding.rl1.isEnabled = false
            Handler().postDelayed({
                binding.rl1.isEnabled = true
            }, 1000)
            Toast.makeText(this, getString(R.string.text_sample), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClickItemQuran(QuranEntity: QuranEntity) {
        val intent = Intent(this, QuranDetailActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("quran_model", QuranEntity)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onClickBookMarkItemQuran(QuranEntity: QuranEntity, position: Int) {
        QuranEntity.isBookMark = !QuranEntity.isBookMark
        adapter.notifyItemChanged(position)
        viewModel.updateIsCheck(QuranEntity.id, QuranEntity.isBookMark)
    }

    override fun onClickBookMarkItemQuranDetail(verse: Verse) {
        verse.isBookmark = !verse.isBookmark
        adapterAyah.notifyDataSetChanged()
        lifecycleScope.launch {
            viewModelQuran.updateIsCheckVerse(verse.id, verse.isBookmark)
        }
    }

    override fun onClickShareItemQuranDetail(verse: Verse) {
        val intent = Intent(this, ShareActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("verse", verse)
        bundle.putString("type", "bookmark")
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onClickItemQuranDetail(verse: Verse, position: Int) {
        if (this.hasNetworkConnection()) {
            binding.layoutPlayerSound.visibility = View.VISIBLE
            mMediaState = Const.MEDIA_QURAN_IDLE
            currentPositionSong = position
            verse.audio?.let { playSound(it.url) }
        } else {
            Toast.makeText(
                this,
                getString(R.string.string_please_connect_to_the_internet_to_play_audio),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun playSound(sound: String) {

        Log.d("ntt", currentPositionSong.toString())

        adapterAyah.resetChecked()

        listVerse.first {
            listVerse.indexOf(it) == currentPositionSong
        }.isPlaySound = true

        binding.rcvQuran.smoothScrollToPosition(currentPositionSong)

        adapterAyah.notifyItemChanged(currentPositionSong)


        when (mMediaState) {
            Const.MEDIA_QURAN_IDLE, Const.MEDIA_QURAN_STOP -> {
                try {
                    mediaPlayer.reset()

                    mediaPlayer.setDataSource("https://verses.quran.com/$sound")
                    mediaPlayer.prepare()

                    mediaPlayer.setOnPreparedListener {
                        it.start()
                        mMediaState = Const.MEDIA_QURAN_PLAYING
                        updateButtonPlayPause(mediaPlayer)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            Const.MEDIA_QURAN_PAUSE -> {
                mediaPlayer.start()
                mMediaState = Const.MEDIA_QURAN_PLAYING
                updateButtonPlayPause(mediaPlayer)
            }

            Const.MEDIA_QURAN_PLAYING -> {
                mediaPlayer.pause()
                mMediaState = Const.MEDIA_QURAN_PAUSE
                updateButtonPlayPause(mediaPlayer)
            }
        }

    }

    private fun next() {

        stop()
        if (currentPositionSong > listVerse.size - 2) {
            currentPositionSong = 0
        } else {
            currentPositionSong++
        }

        listVerse[currentPositionSong].audio?.let { playSound(it.url) }
    }

    private fun previous() {
        stop()
        if (currentPositionSong <= 0) {
            currentPositionSong = listVerse.size - 1
        } else {
            currentPositionSong--
        }

        listVerse[currentPositionSong].audio?.let { playSound(it.url) }
    }

    private fun stop() {
        if (mMediaState == Const.MEDIA_QURAN_IDLE) {
            return
        }
        mediaPlayer.stop()
        mMediaState = Const.MEDIA_QURAN_STOP

    }

    override fun onResume() {
        super.onResume()

        mediaPlayer = MediaPlayer()
        updateButtonPlayPause(mediaPlayer)
    }

    private fun updateButtonPlayPause(mediaPlayer: MediaPlayer) {
        if (mediaPlayer.isPlaying) {
            binding.btnPlay.setImageResource(R.drawable.ic_pause)
        } else {
            binding.btnPlay.setImageResource(R.drawable.ic_play)
        }
        mediaPlayer.setOnCompletionListener {
            binding.btnPlay.setImageResource(R.drawable.ic_play)
            mMediaState = Const.MEDIA_QURAN_STOP
            if (isAutoScrollAudio) {
                next()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        disableSound()
    }

    private fun disableSound() {
        if (mMediaState == Const.MEDIA_QURAN_PLAYING) {
            mediaPlayer.pause()
            mediaPlayer.stop()
            mMediaState = Const.MEDIA_QURAN_IDLE
            updateButtonPlayPause(mediaPlayer)
            mediaPlayer.release()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.layoutPlayerSound.visibility = View.GONE
    }

}