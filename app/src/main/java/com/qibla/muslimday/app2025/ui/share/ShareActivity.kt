package com.qibla.muslimday.app2025.ui.share

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.nlbn.ads.util.AppOpenManager
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.database.Azkar.AzkarEntity
import com.qibla.muslimday.app2025.database.Duas.DuasEntity
import com.qibla.muslimday.app2025.database.Quran.QuranEntity
import com.qibla.muslimday.app2025.databinding.ActivityShareBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.extensions.visible
import com.qibla.muslimday.app2025.ui.quran.modelDetail.Verse
import com.qibla.muslimday.app2025.ui.quran.quranDetail.QuranDetailViewModel
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.Global.Companion.listAzkar
import com.qibla.muslimday.app2025.util.Global.Companion.listDuas
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class ShareActivity : BaseActivity<ActivityShareBinding>() {

    private val viewModel: QuranDetailViewModel by viewModels()

    private var isShareDialogOpen = false

    private var nameSurah = ""
    private var nameJuz = ""

    private var type = ""

    private val storageActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                shareImage()
            } else {

            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val consentHelper = ConsentHelper.getInstance(this)
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        consentHelper.obtainConsentAndShow(this) {
            AdsInter.loadBanner(this@ShareActivity, binding.frBanner, binding.root)
        }
        binding.btnShareImage.setOnClickListener {
//            if (!checkStoragePermission()
//            ) {
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                    storageActivityResultLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
//                } else {
//                    //Android is below 13(R)
//                    ActivityCompat.requestPermissions(
//                        this,
//                        arrayOf(
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        ),
//                        101
//                    )
//                }
//            } else {
//                shareImage()
//            }
            shareImage()

        }

        binding.btnBack.setOnClickListener {
            finish()
        }


    }


    override fun setBinding(layoutInflater: LayoutInflater): ActivityShareBinding {
        return ActivityShareBinding.inflate(layoutInflater)
    }

    private fun checkStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED

        } else {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }

    }

    override fun initView() {
        type = intent.getStringExtra("type").toString()

        when (type) {
            "quran" -> {
                val verse: Verse = intent.getSerializableExtra("verse") as Verse

                val data: QuranEntity = intent.getSerializableExtra("data") as QuranEntity

                binding.tvName.text = getString(R.string.string_quran)

                binding.tvNum.visibility = View.VISIBLE
                binding.tvTitle.text = verse.text_uthmani
                binding.tvNum.text = verse.id.toString()

                Log.d("ntt", verse.surahNumber.toString())
                Log.d("ntt", verse.juz_number.toString())

                when (data.type) {
                    "surah" -> {
                        nameSurah = data.name

                        viewModel.getNameByNum("juz", verse.juz_number).observe(this) {

//                            nameJuz = it.name

                            binding.tvDescription.text = "$nameSurah - $nameJuz"
                        }

                    }

                    "juz" -> {
                        nameJuz = data.name

                        val parts = verse.verse_key.split(":")

                        viewModel.getNameByNum("surah", parts[0].toInt()).observe(this) {

//                            nameSurah = it.name

                            binding.tvDescription.text = "$nameSurah - $nameJuz"
                        }
                    }
                }

                val translationText = StringBuilder()

                val phoneticText = StringBuilder()

                for (word in verse.words!!) {
                    val translation = word.translation?.text ?: ""
                    translationText.append(translation).append(" ")

                    val phonetic = word.transliteration?.text ?: ""
                    phoneticText.append(phonetic).append(" ")
                }

                binding.tvLanguageTranslation.text = translationText.trim()

                binding.tvPhoneticTranslation.text = phoneticText.trim()

            }

            "bookmark" -> {
                val verse: Verse = intent.getSerializableExtra("verse") as Verse
                binding.tvName.text = getString(R.string.string_quran)

                binding.tvNum.visibility = View.VISIBLE
                binding.tvTitle.text = verse.text_uthmani

                val translationText = StringBuilder()

                val phoneticText = StringBuilder()

                CoroutineScope(Dispatchers.IO).launch {
                    val listWords = viewModel.getWordByIdVerse(verse.id)
                    val audio = viewModel.getAudioByIdVerse(verse.id)
                    for (word in listWords) {

                        val translation = viewModel.getTranslationByIdWords(word.id)
                        val transliteration = viewModel.getTransliterationByIdWords(word.id)

                        if (translation != null) {
                            translationText.append(translation.text).append(" ")
                        }

                        if (transliteration != null) {
                            phoneticText.append(transliteration.text).append(" ")
                        }
                    }

                    withContext(Dispatchers.Main) {
                        binding.tvLanguageTranslation.text = translationText.trim()

                        verse.audio = audio

                        binding.tvPhoneticTranslation.text = phoneticText.trim()

                        binding.tvNum.text = verse.verse_number.toString()

                    }
                }
            }

            "duas" -> {
                val duas: DuasEntity = intent.getSerializableExtra("data") as DuasEntity

                binding.tvName.text = getString(R.string.string_duas)

                binding.tvTitle.text = duas.fontName
                binding.tvDescription.text = listDuas[duas.nameId - 1]

                binding.tvLanguageTranslation.text = getString(duas.textEnglish)

                binding.tvPhoneticTranslation.text = duas.textIndia
            }

            "azkar" -> {
                val azkar: AzkarEntity = intent.getSerializableExtra("data") as AzkarEntity

                binding.tvName.text = getString(R.string.string_azkar)

                binding.tvTitle.text = azkar.textArabic
                binding.tvDescription.text = listAzkar[azkar.nameId - 1]

                binding.tvLanguageTranslation.text = getString(azkar.textEnglish)

                binding.tvPhoneticTranslation.text = azkar.textIndia
            }

        }

    }

    override fun onResume() {
        super.onResume()
        AppOpenManager.getInstance().disableAppResumeWithActivity(ShareActivity::class.java)
        if (hasNetworkConnection() && AdsInter.isLoadBannerAll)
            binding.frBanner.visible(true)
    }

    private fun shareImage() {
        // Tạo bitmap từ layout
        if (!isShareDialogOpen) {
            val downloadLink =
                "https://play.google.com/store/apps/details?id=com.qibla.muslimday.app2025"

            binding.llImageShare.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(binding.llImageShare.drawingCache)
            binding.llImageShare.isDrawingCacheEnabled = false

            val shareText = getString(R.string.string_click_here_to_download_the_app, downloadLink)

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"
            shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(this, bitmap))

            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.string_app_download_link))
            if (hasNetworkConnection() && AdsInter.isLoadBannerAll)
                binding.frBanner.visible(false)
            AppOpenManager.getInstance().disableAppResumeWithActivity(ShareActivity::class.java)

//            startActivity(Intent.createChooser(shareIntent, "Share Image"))

            startActivityForResult(
                Intent.createChooser(
                    shareIntent,
                    getString(R.string.string_share_image)
                ), 1909
            )

            isShareDialogOpen = true
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1909) {
            isShareDialogOpen = false
        }
    }


    private fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val currentTime = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val currentTimeString = dateFormat.format(currentTime.time)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            bitmap,
            "Muslim $currentTimeString",
            null
        )
        return Uri.parse(path)
    }
}