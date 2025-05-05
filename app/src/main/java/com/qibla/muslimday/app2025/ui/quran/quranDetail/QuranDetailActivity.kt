package com.qibla.muslimday.app2025.ui.quran.quranDetail

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.database.Quran.QuranEntity
import com.qibla.muslimday.app2025.databinding.ActivityQuranDetailBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.extensions.visible
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.ui.quran.adapter.IOnClickItemQuranDetail
import com.qibla.muslimday.app2025.ui.quran.adapter.QuranDetailAdapter
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Audio
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Translation
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Transliteration
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Verse
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Word
import com.qibla.muslimday.app2025.ui.share.ShareActivity
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Const
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


@AndroidEntryPoint
class QuranDetailActivity : BaseActivity<ActivityQuranDetailBinding>(),
    IOnClickItemQuranDetail {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val viewModel: QuranDetailViewModel by viewModels()

    private var reciter = 1

    private var languageTranslation = 5

    private var isEnableAudio = true

    private var isAutoScrollAudio = true

    private lateinit var data: QuranEntity

    private var mediaPlayer: MediaPlayer = MediaPlayer()

    private var currentPositionSong = 0

    private var mMediaState: Int = Const.MEDIA_QURAN_IDLE

    private var listVerse = mutableListOf<Verse>()

    private var currentPage = 1

    private var totalPage = 1

    private lateinit var adapter: QuranDetailAdapter

    private var numJuz = 0

    private val listVerseChecked = mutableListOf<Verse>()

    private var listIdVerseChecked = setOf<Int>()

    private var deferredList = mutableListOf<Deferred<Unit>>()

    private var isLoading = false

    companion object {
        var isChangeData = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun setBinding(layoutInflater: LayoutInflater): ActivityQuranDetailBinding {
        return ActivityQuranDetailBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val consentHelper = ConsentHelper.getInstance(this)
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        consentHelper.obtainConsentAndShow(this) {
            if (Admob.getInstance().isLoadFullAds) {
                binding.frBanner.visible(false)
                binding.layoutNative.visible(true)
                showAdsNativeQuran()
            } else {
                binding.frBanner.visible(true)
                binding.layoutNative.visible(false)
                AdsInter.loadBanner(this@QuranDetailActivity, binding.frBanner, binding.root)
            }
        }
        AdsInter.onAdsClick = {
            showAdsNativeQuran()
        }
        reciter = preferenceHelper.getReciter()

        isEnableAudio = preferenceHelper.getIsEnableAudio()

        isAutoScrollAudio = preferenceHelper.getIsScrollWithAudio()

        languageTranslation = preferenceHelper.getTranslation()

        data = intent.getSerializableExtra("quran_model") as QuranEntity

        binding.typeQuran.text = data.type
        binding.tvNum.text = data.num.toString()
        binding.nameQuran.text = data.name
        binding.nameQuranBot.text = data.name
        binding.nameQuranDetail.text = data.translation

        binding.rcvDetailQuran.layoutManager =
            LinearLayoutManager(
                this@QuranDetailActivity,
                LinearLayoutManager.VERTICAL,
                false
            )

        adapter = QuranDetailAdapter(
            this@QuranDetailActivity, viewModel,
            preferenceHelper, listVerse, this@QuranDetailActivity
        )

        binding.rcvDetailQuran.adapter = adapter


        if (this.hasNetworkConnection()) {

            showLoading(true)

            setUpData()

            getData(currentPage)

            mediaPlayer = MediaPlayer()


            binding.rcvDetailQuran.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                    // Kiểm tra khi người dùng kéo đến cuối
                    if (!isLoading && (visibleItemCount + firstVisibleItem) >= totalItemCount) {

                        loadNextPage()

                    }
                }
            })

        } else {

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


        viewModel.getItemVerseChecked()
            .observe(this@QuranDetailActivity) { list ->
                listVerseChecked.clear()
                listVerseChecked.addAll(list)
                listIdVerseChecked = listVerseChecked.map { it.id }.toSet()

            }

    }

    private fun loadNextPage() {
        isLoading = true

        currentPage++

        showLoading(true)

        if (currentPage <= totalPage) {

            getData(currentPage)

        } else {
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbLoading.visibility = View.VISIBLE
        } else {
            binding.pbLoading.visibility = View.GONE
        }
    }


    private fun setUpData() {

        if (isEnableAudio) {
            binding.btnPlay.isClickable = true
            binding.btnPrevious.isClickable = true
            binding.btnNext.isClickable = true
        } else {
            binding.btnPlay.isClickable = false
            binding.btnPrevious.isClickable = false
            binding.btnNext.isClickable = false
        }

        when (data.type) {
            "juz" -> {
                viewModel.getPaginationJuzById(
                    data.num,
                    setLanguageTranslate(languageTranslation),
                    "true",
                    page = 1,
                    10,
                    setSoundReciter(reciter),
                    "text_uthmani"
                )

                viewModel.paginationJuzById.observe(this) {
                    totalPage = it.total_pages
                }

                binding.rlName.visibility = View.VISIBLE

                binding.tvName.text = "${data.num} - ${data.name}"

            }

            "surah" -> {
                viewModel.getPaginationSurahById(
                    data.num,
                    setLanguageTranslate(languageTranslation),
                    "true",
                    page = 1,
                    10,
                    setSoundReciter(reciter),
                    "text_uthmani"
                )

                viewModel.paginationSurahById.observe(this) {
                    totalPage = it.total_pages
                }


                binding.rlName.visibility = View.GONE
            }
        }

    }

    private fun getData(page: Int) {

        when (data.type) {

            "juz" -> {

                lifecycleScope.launch(Dispatchers.IO) {
                    val listJuzNewDeferred = async {
                        viewModel.getAllJuzById(
                            data.num,
                            setLanguageTranslate(languageTranslation),
                            "true",
                            page = page,
                            10,
                            setSoundReciter(reciter),
                            "text_uthmani"
                        )
                    }

                    val listJuzNew = listJuzNewDeferred.await()

                    deferredList = mutableListOf<Deferred<Unit>>()

                    for (verse in listJuzNew) {
                        val deferred = async {
                            if (verse.id in listIdVerseChecked) {
                                viewModel.insertVerse(
                                    verse = Verse(
                                        id = verse.id,
                                        juz_number = data.num,
                                        surahNumber = verse.surahNumber,
                                        text_uthmani = verse.text_uthmani,
                                        verse_number = verse.verse_number,
                                        isBookmark = true
                                    )
                                )
                            } else {
                                viewModel.insertVerse(
                                    verse = Verse(
                                        id = verse.id,
                                        juz_number = data.num,
                                        text_uthmani = verse.text_uthmani,
                                        verse_number = verse.verse_number,
                                        isBookmark = false
                                    )
                                )
                            }

                            verse.audio?.let { viewModel.insertAudio(it) }

                            for (words in verse.words!!) {
                                viewModel.insertWord(
                                    Word(
                                        position = words.position,
                                        idVerse = verse.id,
                                        id = words.id
                                    )
                                )
                                words.translation?.let {
                                    viewModel.insertTranslation(
                                        Translation(
                                            language_name = it.language_name,
                                            text = it.text,
                                            idWords = words.id,
                                            idVerse = verse.id
                                        )
                                    )
                                }
                                words.transliteration?.let {
                                    viewModel.insertTransliteration(
                                        Transliteration(
                                            language_name = it.language_name,
                                            text = it.text,
                                            idWords = words.id,
                                            idVerse = verse.id
                                        )
                                    )
                                }
                            }

                            verse.audio?.let {
                                viewModel.insertAudio(
                                    Audio(
                                        url = it.url,
                                        verseId = verse.id
                                    )
                                )
                            }

                            if (verse.id in listIdVerseChecked) {
                                verse.isBookmark = true
                            }
                        }
                        deferredList.add(deferred)
                    }

                    // Đợi cho tất cả các coroutine con hoàn thành
                    deferredList.awaitAll()

                    withContext(Dispatchers.Main) {

                        isLoading = false

                        val startPosition = listVerse.size

                        listVerse.addAll(listJuzNew)

                        showLoading(false)

//                        adapter = QuranDetailAdapter(
//                            this@QuranDetailActivity, viewModel,
//                            preferenceHelper, listVerse, this@QuranDetailActivity
//                        )
//                        binding.rcvDetailQuran.adapter = adapter

                        adapter.notifyItemRangeInserted(startPosition, listJuzNew.size)

                    }
                }
            }

            "surah" -> {

                lifecycleScope.launch(Dispatchers.IO) {

                    val listSurahNew = viewModel.getAllSurahById(
                        data.num,
                        setLanguageTranslate(languageTranslation),
                        "true",
                        page = page,
                        10,
                        setSoundReciter(reciter),
                        "text_uthmani"
                    )

                    deferredList = mutableListOf<Deferred<Unit>>()

                    for (verse in listSurahNew) {
                        val deferred = async {


                            if (verse.id in listIdVerseChecked) {
                                viewModel.insertVerse(
                                    verse = Verse(
                                        id = verse.id,
                                        surahNumber = data.num,
                                        juz_number = verse.juz_number,
                                        text_uthmani = verse.text_uthmani,
                                        verse_number = verse.verse_number,
                                        isBookmark = true,
                                    )
                                )
                            } else {
                                viewModel.insertVerse(
                                    verse = Verse(
                                        id = verse.id,
                                        surahNumber = data.num,
                                        juz_number = verse.juz_number,
                                        text_uthmani = verse.text_uthmani,
                                        verse_number = verse.verse_number,
                                        isBookmark = false
                                    )
                                )
                            }

                            verse.audio?.let { viewModel.insertAudio(it) }

                            for (words in verse.words!!) {
                                viewModel.insertWord(
                                    Word(
                                        position = words.position,
                                        idVerse = verse.id,
                                        id = words.id
                                    )
                                )
                                words.translation.let {
                                    if (it != null) {
                                        viewModel.insertTranslation(
                                            Translation(
                                                language_name = it.language_name,
                                                text = it.text,
                                                idWords = words.id,
                                                idVerse = verse.id
                                            )
                                        )
                                    }
                                }
                                words.transliteration.let {
                                    if (it != null) {
                                        viewModel.insertTransliteration(
                                            Transliteration(
                                                language_name = it.language_name,
                                                text = it.text,
                                                idWords = words.id,
                                                idVerse = verse.id
                                            )
                                        )
                                    }
                                }
                            }

                            verse.audio?.let {
                                viewModel.insertAudio(
                                    Audio(
                                        url = it.url,
                                        verseId = verse.id
                                    )
                                )
                            }

                            if (verse.id in listIdVerseChecked) {
                                verse.isBookmark = true
                            }

                        }

                        deferredList.add(deferred)

                    }

                    deferredList.awaitAll()

                    for (ver in listSurahNew) {
                        if (ver.juz_number != numJuz) {
                            ver.isShowTitle = true
                            numJuz = ver.juz_number  // Cập nhật biến cục bộ
                        }
                    }

                    withContext(Dispatchers.Main) {

                        isLoading = false

                        val startPosition = listVerse.size

                        listVerse.addAll(listSurahNew)

                        showLoading(false)

//                        adapter = QuranDetailAdapter(
//                            this@QuranDetailActivity, viewModel,
//                            preferenceHelper, listVerse, this@QuranDetailActivity
//                        )
//
//                        binding.rcvDetailQuran.adapter = adapter

                        adapter.notifyItemRangeInserted(startPosition, listSurahNew.size)

                    }
                }

            }
        }
    }

    private fun playSound(sound: String) {

        Log.d("ntt", currentPositionSong.toString())

        adapter.resetChecked()

        listVerse.first {
            listVerse.indexOf(it) == currentPositionSong
        }.isPlaySound = true

        if (isAutoScrollAudio) {
            binding.rcvDetailQuran.smoothScrollToPosition(currentPositionSong)
        }


//        binding.rcvDetailQuran.scrollToPosition(currentPositionSong)

        adapter.notifyItemChanged(currentPositionSong)


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

    private fun showAdsNativeQuran() {
        Log.d("ntt", "showAdsNativeQuran: ")
        AdsInter.pushNativeAll(
            context = this@QuranDetailActivity,
            view = binding.frAds,
            keyCheck = AdsInter.isLoadNativeDetail,
            isLoadNormal = false,
            scope = lifecycleScope
        )
    }

    override fun onResume() {
        super.onResume()
        binding.layoutPlayerSound.visibility = View.VISIBLE
        if (this::adapter.isInitialized) {
            adapter.notifyDataSetChanged()
        }

        reciter = preferenceHelper.getReciter()

        isEnableAudio = preferenceHelper.getIsEnableAudio()

        isAutoScrollAudio = preferenceHelper.getIsScrollWithAudio()

        languageTranslation = preferenceHelper.getTranslation()

        if (isChangeData) {

            isChangeData = false

            Log.d("ntt", "Change")

            numJuz = 0

            currentPositionSong = 0

            binding.rcvDetailQuran.layoutManager =
                LinearLayoutManager(
                    this@QuranDetailActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )

            listVerse.clear()


            currentPage = 1

            setUpData()

            getData(currentPage)

        }

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
            next()

        }
    }

    override fun onPause() {
        super.onPause()
        if (mMediaState == Const.MEDIA_QURAN_PLAYING) {
            mediaPlayer.pause()
            mediaPlayer.stop()
            mMediaState = Const.MEDIA_QURAN_IDLE
            updateButtonPlayPause(mediaPlayer)
            mediaPlayer.release()
        }

        binding.layoutPlayerSound.visibility = View.GONE
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.layoutPlayerSound.visibility = View.GONE
    }

    private fun setSoundReciter(reciter: Int): Int {
        when (reciter) {
            1 -> {
                return 1
            }

            2 -> {
                return 7
            }

            3 -> {
                return 3
            }

            else -> return 1
        }
    }

    private fun setLanguageTranslate(translation: Int): String {
        if (translation == 1) {
            return "hi"
        } else if (translation == 2) {
            return "ur"
        } else if (translation == 3) {
            return "tr"
        } else if (translation == 4) {
            return "id"
        } else if (translation == 5) {
            return "en"
        }
        return "en"
    }

    override fun onClickBookMarkItemQuranDetail(verse: Verse) {
        for (data in listVerse) {
            if (data.id == verse.id) {
                data.isBookmark = !data.isBookmark
                lifecycleScope.launch {
                    viewModel.updateIsCheckVerse(verse.id, data.isBookmark)
                    withContext(Dispatchers.Main) {

                        Log.d("ntt", "logEvent: event_quranbookmark")

                        val bundle = Bundle()

//                        FirebaseAnalytics.getInstance(this@QuranDetailActivity)
//                            .logEvent("event_quranbookmark", bundle)

                        adapter.notifyItemChanged(listVerse.indexOf(data))
                    }
                }

                return

            }
        }
    }

    override fun onClickShareItemQuranDetail(verse: Verse) {
        val intent = Intent(this, ShareActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("verse", verse)
        bundle.putSerializable("data", data)
        bundle.putString("type", "quran")
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onClickItemQuranDetail(verse: Verse, position: Int) {

        if (this.hasNetworkConnection()) {

            mMediaState = Const.MEDIA_QURAN_IDLE
            currentPositionSong = position
            if (isEnableAudio) {
                binding.layoutPlayerSound.visibility = View.VISIBLE
                verse.audio?.let { playSound(it.url) }
            } else {
                adapter.resetChecked()

                listVerse.first {
                    listVerse.indexOf(it) == currentPositionSong
                }.isPlaySound = true

                binding.rcvDetailQuran.smoothScrollToPosition(currentPositionSong)

                adapter.notifyItemChanged(currentPositionSong)
            }
        } else {
            Toast.makeText(
                this,
                getString(R.string.string_please_connect_to_the_internet_to_play_audio),
                Toast.LENGTH_SHORT
            )
                .show()
        }

    }
}
