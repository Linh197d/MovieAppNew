package com.qibla.muslimday.app2025

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nlbn.ads.banner.BannerPlugin
import com.nlbn.ads.util.Admob
import com.nlbn.ads.util.ConsentHelper
import com.qibla.muslimday.app2025.base.BaseActivity
import com.qibla.muslimday.app2025.database.Quran.QuranEntity
import com.qibla.muslimday.app2025.databinding.ActivityMainBinding
import com.qibla.muslimday.app2025.extensions.hasNetworkConnection
import com.qibla.muslimday.app2025.helper.PreferenceHelper
import com.qibla.muslimday.app2025.ui.home.HomeFragment
import com.qibla.muslimday.app2025.ui.qibla.QiblaFragment
import com.qibla.muslimday.app2025.ui.quran.QuranFragment
import com.qibla.muslimday.app2025.ui.quran.QuranViewModel
import com.qibla.muslimday.app2025.ui.salah.SalahFragment
import com.qibla.muslimday.app2025.util.AdsInter
import com.qibla.muslimday.app2025.util.AdsInter.Companion.loadNativeData1
import com.qibla.muslimday.app2025.util.AdsInter.Companion.loadNativeData2
import com.qibla.muslimday.app2025.util.Const
import com.qibla.muslimday.app2025.util.Const.Companion.listJuz
import com.qibla.muslimday.app2025.util.Const.Companion.listSurah
import com.qibla.muslimday.app2025.util.EventBusHelper
import com.qibla.muslimday.app2025.util.UpdateEvent
import com.qibla.muslimday.app2025.view.ExitDialogNew
import com.qibla.muslimday.app2025.view.RatingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject
import kotlin.system.exitProcess


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var isLoading = false
    var lastClickTime = 0L
    private var currentFragment: Fragment? = null
    private val fragmentMap = SparseArray<Fragment>()

    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    private val viewModel: QuranViewModel by viewModels()

    // Color
    private var colorSelect = 0
    private var colorUnSelect = 0

    @DrawableRes
    private var drawableScreenHomeOne: Int = 0

    @DrawableRes
    private var drawableScreenHomeTwo: Int = 0

    @DrawableRes
    private var drawableSalahOne: Int = 0

    @DrawableRes
    private var drawableSalahTwo: Int = 0

    @DrawableRes
    private var drawableQuranOne: Int = 0

    @DrawableRes
    private var drawableQuranTwo: Int = 0

    @DrawableRes
    private var drawableSettingOne: Int = 0

    @DrawableRes
    private var drawableSettingTwo: Int = 0

    private var isClickQuran = true

    override fun setBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onBackPressed() {
//        super.onBackPressed()

        val countOpenApp = preferenceHelper.getCountExitApp()

        Log.d("ntt", countOpenApp.toString())

        if (!preferenceHelper.isRate() && (countOpenApp == 1 || countOpenApp == 3 || countOpenApp == 5 || countOpenApp == 7)) {
            showDialogRate(true)
        } else {
            showQuitDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBusHelper.register(this)
        }
        getAllJuz()
        getAllSurah()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBusHelper.unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: UpdateEvent) {
        if (event.name == "salah") {

            if (isLoading) {

                isClickQuran = true

                changeImageIconWhenTap(
                    drawableScreenHomeTwo,
                    drawableSalahOne,
                    drawableQuranTwo,
                    drawableSettingTwo
                )

                changeTextColorWhenTap(colorUnSelect, colorSelect, colorUnSelect, colorUnSelect)
                supportFragmentManager.findFragmentById(R.id.frame_layout)?.let {
                    if (it is SalahFragment) {

                    } else {
//                        replaceFragment(SalahFragment())
                        switchFragment(SalahFragment(), "salah")

                    }
                }
            }

        } else if (event.name == "hideBanner") {
            hideBanner()
        }

    }
    private fun getAllJuz() {
        listJuz.clear()
        listJuz.add(
            QuranEntity(
                1,
                "juz",
                1,
                "Alaf Lam Meem",
                "Surah Al-Faatiha 1:1 - Surah Al Baqarah 2:141"
            )
        )
        listJuz.add(
            QuranEntity(
                2,
                "juz",
                2,
                "Sayaqool",
                "Surah Al Baqarah 2:142 - Surah Al-Baqara 2:252"
            )
        )
        listJuz.add(
            QuranEntity(
                3,
                "juz",
                3,
                "Tilkal Rusull",
                "Surah Al-Baqara 2:253 - Surah Aal-i-Imraan 3:92"
            )
        )
        listJuz.add(
            QuranEntity(
                4,
                "juz",
                4,
                "Lan Tana Loo",
                "Surah Aal-i-Imraan 3:93 - Surah An-Nisaa 4:23"
            )
        )
        listJuz.add(
            QuranEntity(
                5,
                "juz",
                5,
                "Wal Mohsanat",
                "Surah An-Nisaa 4:24 - Surah An-Nisaa 4:147"
            )
        )
        listJuz.add(
            QuranEntity(
                6,
                "juz",
                6,
                "La Yuhibbullah",
                "Surah An-Nisaa 4:148 - Surah Al-Maaida 5:81"
            )
        )
        listJuz.add(
            QuranEntity(
                7,
                "juz",
                7,
                "Wa Iza Samiu",
                "Surah Al-Maaida 5:83 - Surah Al-An'aam 6:110"
            )
        )
        listJuz.add(
            QuranEntity(
                8,
                "juz",
                8,
                "Wa Lau Annana",
                "Surah Al-An'aam 6:111 - Surah Al-A'raaf 7:87"
            )
        )
        listJuz.add(
            QuranEntity(
                9,
                "juz",
                9,
                "Qalal Malao",
                "Surah Al-A'raaf 7:88 - Surah Al-Anfaal 8:40"
            )
        )
        listJuz.add(
            QuranEntity(
                10,
                "juz",
                10,
                "Wa A'lamu",
                "Surah Al-Anfaal 8:41 - Surah At-Tawba 9:92"
            )
        )
        listJuz.add(
            QuranEntity(
                11,
                "juz",
                11,
                "Yatazeroon",
                "Surah At-Tawba 9:93 - Surah Hud 11:5"
            )
        )
        listJuz.add(
            QuranEntity(
                12,
                "juz",
                12,
                "Wa Mamin Da'abat",
                "Surah Hud 11:6 - Surah Yusuf 12:52"
            )
        )
        listJuz.add(
            QuranEntity(
                13,
                "juz",
                13,
                "Wa Ma Ubrioo",
                "Surah Yusuf 12:53 - Surah Ibrahim 14:52"
            )
        )
        listJuz.add(
            QuranEntity(
                14,
                "juz",
                14,
                "Rubama",
                "Surah Al-Hijr 15:2 - Surah An-Nahl 16:128"
            )
        )
        listJuz.add(
            QuranEntity(
                15,
                "juz",
                15,
                "Subhanallazi",
                "Surah Al-Israa 17:1 - Surah Al-Kahf 18:74"
            )
        )
        listJuz.add(
            QuranEntity(
                16,
                "juz",
                16,
                "Qal Alam",
                "Surah Al-Kahf 18:75 - Surah Taa-Haa 20:135"
            )
        )
        listJuz.add(
            QuranEntity(
                17,
                "juz",
                17,
                "Aqtarabo",
                "Surah Al-Anbiyaa 21:21 - Surah Al-Hajj 22:78"
            )
        )
        listJuz.add(
            QuranEntity(
                18,
                "juz",
                18,
                "Qadd Aflaha",
                "Surah Al-Muminoon 23:1 - Surah Al-Furqaan 25:20"
            )
        )
        listJuz.add(
            QuranEntity(
                19,
                "juz",
                19,
                "Wa Qalallazina",
                "Surah Al-Furqaan 25:21 - Surah An-Naml 27:55"
            )
        )
        listJuz.add(
            QuranEntity(
                20,
                "juz",
                20,
                "A'man Khalaq",
                "Surah An-Naml 27:60 - Surah Al-Ankaboot 29:45"
            )
        )
        listJuz.add(
            QuranEntity(
                21,
                "juz",
                21,
                "Utlu Ma Oohi",
                "Surah Al-Ankaboot 29:45 - Surah Al-Ahzaab 33:30"
            )
        )
        listJuz.add(
            QuranEntity(
                22,
                "juz",
                22,
                "Wa Manyaqnut",
                "Surah Al-Ahzaab 33:31 - Surah Yaseen 36:27"
            )
        )
        listJuz.add(
            QuranEntity(
                23,
                "juz",
                23,
                "Wa Mali",
                "Surah Yaseen 36:22 - Surah Az-Zumar 39:31"
            )
        )
        listJuz.add(
            QuranEntity(
                24,
                "juz",
                24,
                "Faman Azlam",
                "Surah Az-Zumar 39:32- Surah Fussilat 41:46"
            )
        )
        listJuz.add(
            QuranEntity(
                25,
                "juz",
                25,
                "Elahe Yuruddo",
                "Surah Fussilat 41:47 - Surah Al-Jaathiya 45:37"
            )
        )
        listJuz.add(
            QuranEntity(
                26,
                "juz",
                26,
                "Ha'a Meem",
                "Surah Al-Ahqaf 46:1 - Surah Adh-Dhaariyat 51:30"
            )
        )
        listJuz.add(
            QuranEntity(
                27,
                "juz",
                27,
                "Qala Fama Khatbukum",
                "Surah Adh-Dhaariyat 51:31 - Surah Al-Hadid 57:29"
            )
        )
        listJuz.add(
            QuranEntity(
                28,
                "juz",
                28,
                "Qadd Sami Allah",
                "Surah Al-Mujaadila 58:1 - Surah At-Tahrim 66:12"
            )
        )
        listJuz.add(
            QuranEntity(
                29,
                "juz",
                29,
                "Tabarakallazi",
                "Surah Adh-Dhaariyat 51:31 - Surah Al-Hadid 57:29"
            )
        )
        listJuz.add(
            QuranEntity(
                30,
                "juz",
                30,
                "Amma Yatasa'aloon",
                "Surah An-Naba 78:1 - Surah An-Naas 114:6"
            )
        )

        listJuz.forEach {
            viewModel.insertQuran(it)
        }
    }

    private fun getAllSurah() {
        listSurah.clear()
        listSurah.add(QuranEntity(31, "surah", 1, "Al-Faatiha", "The Opening (7)"))
        listSurah.add(QuranEntity(32, "surah", 2, "Al-Baqara", "The Cow (286)"))
        listSurah.add(QuranEntity(33, "surah", 3, "Aal-i-Imraan", "The Family of Imraan (200)"))
        listSurah.add(QuranEntity(34, "surah", 4, "An-Nisaa", "The Women (176)"))
        listSurah.add(QuranEntity(35, "surah", 5, "Al-Maaida", "The Table (120)"))
        listSurah.add(QuranEntity(36, "surah", 6, "Al-An'aam", "The Cattle (165)"))
        listSurah.add(QuranEntity(37, "surah", 7, "Al-A'raaf", "The Heights (206)"))
        listSurah.add(QuranEntity(38, "surah", 8, "Al-Anfaal", "The Spoils of War (75)"))
        listSurah.add(QuranEntity(39, "surah", 9, "At-Tawba", "The Repentance (129)"))
        listSurah.add(QuranEntity(40, "surah", 10, "Yunus", "Jonas (109)"))
        listSurah.add(QuranEntity(41, "surah", 11, "Hud", "Hud (123)"))
        listSurah.add(QuranEntity(42, "surah", 12, "Yusuf", "Joseph (111)"))
        listSurah.add(QuranEntity(43, "surah", 13, "Ar-Ra'd", "The Thunder (43)"))
        listSurah.add(QuranEntity(44, "surah", 14, "Ibrahim", "Abraham (52)"))
        listSurah.add(QuranEntity(45, "surah", 15, "Al-Hijr", "The Rock (99)"))
        listSurah.add(QuranEntity(46, "surah", 16, "An-Nahl", "The Bee (128)"))
        listSurah.add(QuranEntity(47, "surah", 17, "Al-Israa", "The Night Journey (111)"))
        listSurah.add(QuranEntity(48, "surah", 18, "Al-Kahf", "The Cave (110)"))
        listSurah.add(QuranEntity(49, "surah", 19, "Maryam", "Mary (98)"))
        listSurah.add(QuranEntity(50, "surah", 20, "Taa-Haa", "Taa-Haa (135)"))
        listSurah.add(QuranEntity(51, "surah", 21, "Al-Anbiyaa", "The Prophets (112)"))
        listSurah.add(QuranEntity(52, "surah", 22, "Al-Hajj", "The Pilgrimage (78)"))
        listSurah.add(QuranEntity(53, "surah", 23, "Al-Muminoon", "The Believers (118)"))
        listSurah.add(QuranEntity(54, "surah", 24, "An-Noor", "The Light (64)"))
        listSurah.add(QuranEntity(55, "surah", 25, "Al-Furqaan", "The Criterion (77)"))
        listSurah.add(QuranEntity(56, "surah", 26, "Ash-Shu'araa", "The Poets (227)"))
        listSurah.add(QuranEntity(57, "surah", 27, "An-Naml", "The Ant (93)"))
        listSurah.add(QuranEntity(58, "surah", 28, "Al-Qasas", "The Stories (88)"))
        listSurah.add(QuranEntity(59, "surah", 29, "Al-Ankaboot", "The Spider (69)"))
        listSurah.add(QuranEntity(60, "surah", 30, "Ar-Room", "The Romans (60)"))
        listSurah.add(QuranEntity(61, "surah", 31, "Luqman", "Luqman (34)"))
        listSurah.add(QuranEntity(62, "surah", 32, "As-Sajda", "The Prostration (30)"))
        listSurah.add(QuranEntity(63, "surah", 33, "Al-Ahzaab", "The Clans (73)"))
        listSurah.add(QuranEntity(64, "surah", 34, "Saba", "Sheba (54)"))
        listSurah.add(QuranEntity(65, "surah", 35, "Faatir", "The Originator (45)"))
        listSurah.add(QuranEntity(66, "surah", 36, "Yaseen", "Yaseen (83)"))
        listSurah.add(QuranEntity(67, "surah", 37, "As-Saaffaat", "Those drawn up in Ranks (182)"))
        listSurah.add(QuranEntity(68, "surah", 38, "Saad", "The letter Saad (88)"))
        listSurah.add(QuranEntity(69, "surah", 39, "Az-Zumar", "The Groups (75)"))
        listSurah.add(QuranEntity(70, "surah", 40, "Ghafir", "The Forgiver (85)"))
        listSurah.add(QuranEntity(71, "surah", 41, "Fussilat", "Explained in detail (54)"))
        listSurah.add(QuranEntity(72, "surah", 42, "Ash-Shura", "Consultation (53)"))
        listSurah.add(QuranEntity(73, "surah", 43, "Az-Zukhruf", "Ornaments of gold (89)"))
        listSurah.add(QuranEntity(74, "surah", 44, "Ad-Dukhaan", "The Smoke (59)"))
        listSurah.add(QuranEntity(75, "surah", 45, "Al-Jaathiya", "Crouching (37)"))
        listSurah.add(QuranEntity(76, "surah", 46, "Al-Ahqaf", "The Dunes (35)"))
        listSurah.add(QuranEntity(77, "surah", 47, "Muhammad", "Muhammad (38)"))
        listSurah.add(QuranEntity(78, "surah", 48, "Al-Fath", "The Victory (29)"))
        listSurah.add(QuranEntity(79, "surah", 49, "Al-Hujuraat", "The Inner Apartments (18)"))
        listSurah.add(QuranEntity(80, "surah", 50, "Qaaf", "The letter Qaaf (45)"))
        listSurah.add(QuranEntity(81, "surah", 51, "Adh-Dhaariyat", "The Winnowing Winds (60)"))
        listSurah.add(QuranEntity(82, "surah", 52, "At-Tur", "The Mount (49)"))
        listSurah.add(QuranEntity(83, "surah", 53, "An-Najm", "The Star (62)"))
        listSurah.add(QuranEntity(84, "surah", 54, "Al-Qamar", "The Moon (55)"))
        listSurah.add(QuranEntity(85, "surah", 55, "Ar-Rahmaan", "The Beneficent (78)"))
        listSurah.add(QuranEntity(86, "surah", 56, "Al-Waaqia", "The Inevitable (96)"))
        listSurah.add(QuranEntity(87, "surah", 57, "Al-Hadid", "The Iron (29)"))
        listSurah.add(QuranEntity(88, "surah", 58, "Al-Mujaadila", "The Pleading Woman (22)"))
        listSurah.add(QuranEntity(89, "surah", 59, "Al-Hashr", "The Exile (24)"))
        listSurah.add(
            QuranEntity(
                90,
                "surah",
                60,
                "Al-Mumtahana",
                "She that is to be examined (13)"
            )
        )
        listSurah.add(QuranEntity(91, "surah", 61, "As-Saff", "The Ranks (14)"))
        listSurah.add(QuranEntity(92, "surah", 62, "Al-Jumu'a", "Friday (11)"))
        listSurah.add(QuranEntity(93, "surah", 63, "Al-Munaafiqoon", "The Hypocrites (11)"))
        listSurah.add(QuranEntity(94, "surah", 64, "At-Taghaabun", "Mutual Disillusion (18)"))
        listSurah.add(QuranEntity(95, "surah", 65, "At-Talaaq", "Divorce (12)"))
        listSurah.add(QuranEntity(96, "surah", 66, "At-Tahrim", "The Prohibition (12)"))
        listSurah.add(QuranEntity(97, "surah", 67, "Al-Mulk", "The Sovereignty (30)"))
        listSurah.add(QuranEntity(98, "surah", 68, "Al-Qalam", "The Pen (52)"))
        listSurah.add(QuranEntity(99, "surah", 69, "Al-Haaqqa", "The Reality (52)"))
        listSurah.add(QuranEntity(100, "surah", 70, "Al-Ma'aarij", "The Ascending Stairways (44)"))
        listSurah.add(QuranEntity(101, "surah", 71, "Nooh", "Noah (28)"))
        listSurah.add(QuranEntity(102, "surah", 72, "Al-Jinn", "The Jinn (28)"))
        listSurah.add(QuranEntity(103, "surah", 73, "Al-Muzzammil", "The Enshrouded One (20)"))
        listSurah.add(QuranEntity(104, "surah", 74, "Al-Muddaththir", "The Cloaked One (56)"))
        listSurah.add(QuranEntity(105, "surah", 75, "Al-Qiyaama", "The Resurrection (40)"))
        listSurah.add(QuranEntity(106, "surah", 76, "Al-Insaan", "Man (31)"))
        listSurah.add(QuranEntity(107, "surah", 77, "Al-Mursalaat", "The Emissaries (50)"))
        listSurah.add(QuranEntity(108, "surah", 78, "An-Naba", "The Announcement (40)"))
        listSurah.add(QuranEntity(109, "surah", 79, "An-Naazi'aat", "Those who drag forth (46)"))
        listSurah.add(QuranEntity(110, "surah", 80, "Abasa", "He frowned (42)"))
        listSurah.add(QuranEntity(111, "surah", 81, "At-Takwir", "The Overthrowing (29)"))
        listSurah.add(QuranEntity(112, "surah", 82, "Al-Infitaar", "The Cleaving (19)"))
        listSurah.add(QuranEntity(113, "surah", 83, "Al-Mutaffifin", "Defrauding (36)"))
        listSurah.add(QuranEntity(114, "surah", 84, "Al-Inshiqaaq", "The Splitting Open (25)"))
        listSurah.add(QuranEntity(115, "surah", 85, "Al-Burooj", "The Constellations (22)"))
        listSurah.add(QuranEntity(116, "surah", 86, "At-Taariq", "The Morning Star (17)"))
        listSurah.add(QuranEntity(117, "surah", 87, "Al-A'laa", "The Most High (19)"))
        listSurah.add(QuranEntity(118, "surah", 88, "Al-Ghaashiya", "The Overwhelming (26)"))
        listSurah.add(QuranEntity(119, "surah", 89, "Al-Fajr", "The Dawn (30)"))
        listSurah.add(QuranEntity(120, "surah", 90, "Al-Balad", "The City (20)"))
        listSurah.add(QuranEntity(121, "surah", 91, "Ash-Shams", "The Sun (15)"))
        listSurah.add(QuranEntity(122, "surah", 92, "Al-Lail", "The Night (21)"))
        listSurah.add(QuranEntity(123, "surah", 93, "Ad-Dhuhaa", "The Morning Hours (11)"))
        listSurah.add(QuranEntity(124, "surah", 94, "Ash-Sharh", "The Consolation (8)"))
        listSurah.add(QuranEntity(125, "surah", 95, "At-Tin", "The Fig (8)"))
        listSurah.add(QuranEntity(126, "surah", 96, "Al-Alaq", "The Clot (19)"))
        listSurah.add(QuranEntity(127, "surah", 97, "Al-Qadr", "The Power, Fate (5)"))
        listSurah.add(QuranEntity(128, "surah", 98, "Al-Bayyina", "The Evidence (8)"))
        listSurah.add(QuranEntity(129, "surah", 99, "Az-Zalzala", "The Earthquake (8)"))
        listSurah.add(QuranEntity(130, "surah", 100, "Al-Aadiyaat", "The Chargers (11)"))
        listSurah.add(QuranEntity(131, "surah", 101, "Al-Qaari'a", "The Calamity (11)"))
        listSurah.add(QuranEntity(132, "surah", 102, "At-Takaathur", "Competition (8)"))
        listSurah.add(QuranEntity(133, "surah", 103, "Al-Asr", "The Declining Day, Epoch (3)"))
        listSurah.add(QuranEntity(134, "surah", 104, "Al-Humaza", "The Traducer (9)"))
        listSurah.add(QuranEntity(135, "surah", 105, "Al-Fil", "The Elephant (5)"))
        listSurah.add(QuranEntity(136, "surah", 106, "Quraish", "Quraysh (4)"))
        listSurah.add(QuranEntity(137, "surah", 107, "Al-Maa'un", "Almsgiving (7)"))
        listSurah.add(QuranEntity(138, "surah", 108, "Al-Kaafiroon", "Abundance (3)"))
        listSurah.add(QuranEntity(139, "surah", 109, "Al-Kaafiroon", "The Disbelievers (6)"))
        listSurah.add(QuranEntity(140, "surah", 110, "An-Nasr", "Divine Support (3)"))
        listSurah.add(QuranEntity(141, "surah", 111, "Al-Masad", "The Palm Fibre (5)"))
        listSurah.add(QuranEntity(142, "surah", 112, "Al-Ikhlaas", "The Sincerity (4)"))
        listSurah.add(QuranEntity(143, "surah", 113, "Al-Falaq", "The Dawn (5)"))
        listSurah.add(QuranEntity(144, "surah", 114, "An-Naas", "Mankind (6)"))
        listSurah.forEach {
            viewModel.insertQuran(it)
        }

    }
    private fun showDialogRate(isExit: Boolean) {
        EventBus.getDefault().post(UpdateEvent("hide_ads"))

        val ratingDialog = RatingDialog(this)
        ratingDialog.init(this, object : RatingDialog.OnPress {
            override fun sendThank() {

                if (!isExit) {
                    preferenceHelper.forceRated()
                    ratingDialog.dismiss()

                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.string_thank_for_rate),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    preferenceHelper.forceRated()
                    ratingDialog.dismiss()

                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.string_thank_for_rate),
                        Toast.LENGTH_SHORT
                    ).show()

                    finishAffinity()

                    exitProcess(1)
                }

            }

            override fun rating() {
                val manager = ReviewManagerFactory.create(this@MainActivity)
                val request = manager.requestReviewFlow()
                request.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val reviewInfo = task.result
                        val flow = manager.launchReviewFlow(this@MainActivity, reviewInfo)
                        flow.addOnSuccessListener {
                            if (!isExit) {
                                preferenceHelper.forceRated()
                                ratingDialog.dismiss()
                            } else {
                                preferenceHelper.forceRated()
                                ratingDialog.dismiss()
                                finishAffinity()
                                exitProcess(1)
                            }
                        }
                    } else {
                        if (!isExit) {
                            preferenceHelper.forceRated()
                            ratingDialog.dismiss()
                        } else {
                            preferenceHelper.forceRated()
                            ratingDialog.dismiss()
                            finishAffinity()
                            exitProcess(1)
                        }
                    }
                }

            }

            override fun later() {
                if (isExit) {
                    ratingDialog.dismiss()
                    Log.d("ntt", "later1 ${preferenceHelper.getCountExitApp()}")
                    preferenceHelper.increaseCountExitApp()
                    Log.d("ntt", "later2 ${preferenceHelper.getCountExitApp()}")
                    finishAffinity()
                    exitProcess(1)
                } else {
                    ratingDialog.dismiss()
                }
            }
        })

        try {
            ratingDialog.show()
        } catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()

        }

        ratingDialog.setOnDismissListener {
            EventBus.getDefault().post(UpdateEvent("show_ads"))
            AdsInter.isShowRate = false
        }
    }

    override fun initView() {
        val consentHelper = ConsentHelper.getInstance(this)
        if (!consentHelper.canLoadAndShowAds()) {
            consentHelper.reset()
        }
        consentHelper.obtainConsentAndShow(this) {
            lifecycleScope.launch {
                loadBanner()
                AdsInter.loadNativeAll(this@MainActivity)
                loadNativeData1(this@MainActivity)
                loadNativeData2(this@MainActivity)
                EventBus.getDefault().postSticky(UpdateEvent("Ads"))
            }
        }


        drawableScreenHomeOne = R.drawable.ic_home
        drawableScreenHomeTwo = R.drawable.ic_home_not_select
        drawableSalahOne = R.drawable.ic_qibla_selected
        drawableSalahTwo = R.drawable.ic_qibla
        drawableQuranOne = R.drawable.ic_quran
        drawableQuranTwo = R.drawable.ic_quran_not_select
        drawableSettingOne = R.drawable.ic_prayer_selected
        drawableSettingTwo = R.drawable.ic_prayer

        /* color text */
        colorUnSelect = getColor(R.color.bottom_nav_text_color)
        colorSelect = getColor(R.color.color_main)

        nav()

    }

    private fun loadBanner() {
        if (this.hasNetworkConnection()) {
            if (AdsInter.isLoadBannerAll) {
                binding.frBanner.visibility = View.VISIBLE
                val config = BannerPlugin.Config()
                config.defaultAdUnitId = getString(R.string.banner_all)
                config.defaultBannerType = BannerPlugin.BannerType.Adaptive
                val cbFetchInterval =
                    FirebaseRemoteConfig.getInstance().getLong("cb_fetch_interval").toInt()
                config.defaultRefreshRateSec = cbFetchInterval
                config.defaultCBFetchIntervalSec = cbFetchInterval
                Admob.getInstance().loadBannerPlugin(
                    this,
                    findViewById<ViewGroup>(R.id.fr_banner),
                    findViewById<ViewGroup>(R.id.include),
                    config
                )
            } else {
                binding.frBanner.visibility = View.GONE
            }
        } else {
            binding.frBanner.visibility = View.GONE
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }



    private fun nav() {

        if (intent.hasExtra("start")) {

            val data = intent.getStringExtra("start")
            when (data) {
                "notification" -> {
                    isLoading = true

                    changeImageIconWhenTap(
                        drawableScreenHomeTwo,
                        drawableQuranOne,
                        drawableSalahTwo,
                        drawableSettingTwo
                    )

                    changeTextColorWhenTap(colorUnSelect, colorSelect, colorUnSelect, colorUnSelect)

//                    replaceFragment(QuranFragment())
                    switchFragment(QuranFragment(), "quran")

                }

                "notification_qibla" -> {
                    isLoading = true

                    changeImageIconWhenTap(
                        drawableScreenHomeTwo,
                        drawableQuranTwo,
                        drawableSalahOne,
                        drawableSettingTwo
                    )

                    changeTextColorWhenTap(colorUnSelect, colorUnSelect, colorSelect, colorUnSelect)

//                    replaceFragment(QiblaFragment())
                    switchFragment(QiblaFragment(), "qibla")

                }
            }


        } else {
//            replaceFragment(HomeFragment())
            switchFragment(HomeFragment(), "home")
        }

        binding.bottomNavMain.linearHome.setOnClickListener {
            if (System.currentTimeMillis() - lastClickTime < 500) return@setOnClickListener
            lastClickTime = System.currentTimeMillis()
            if (checkPermissionLocation() && checkPermissionNotification() && checkSystemAlertWindowPermission()) {

                isClickQuran = true

                if (isLoading) {
                    changeImageIconWhenTap(
                        drawableScreenHomeOne,
                        drawableQuranTwo,
                        drawableSalahTwo,
                        drawableSettingTwo
                    )

                    changeTextColorWhenTap(colorSelect, colorUnSelect, colorUnSelect, colorUnSelect)
                    supportFragmentManager.findFragmentById(R.id.frame_layout)?.let {
                        if (it is HomeFragment) {

                        } else {
                            AdsInter.loadInter(
                                context = this@MainActivity,
                                adsId = getString(R.string.inter_tab),
                                isShow = AdsInter.isLoadInterTab && Admob.getInstance().isLoadFullAds,
                                action = {
//                                    replaceFragment(HomeFragment())
                                    switchFragment(HomeFragment(), "home")
                                }
                            )

                            if (Const.isOpenTab) {
                                preferenceHelper.increaseCountBackScreen()

                                if (!preferenceHelper.isRate() && (preferenceHelper.getCountBackScreen() == 2 || preferenceHelper.getCountBackScreen() == 4 || preferenceHelper.getCountBackScreen() == 6)) {
                                    AdsInter.isShowRate = true
                                    showDialogRate(false)
                                }

                                Const.isOpenTab = false
                            }

                        }
                    }
                }
            } else {
                Log.d("ntt", "nav: post linear Home")
                EventBus.getDefault().post(UpdateEvent("linearHome"))
            }

        }

        binding.bottomNavMain.linearQibla.setOnClickListener {
            if (System.currentTimeMillis() - lastClickTime < 500) return@setOnClickListener
            lastClickTime = System.currentTimeMillis()
            if (checkPermissionLocation() && checkPermissionNotification() && checkSystemAlertWindowPermission()) {

                if (isLoading) {

                    if (isClickQuran) {
                        Log.d("ntt", "nav: logEvent: event_quran")
                        val bundle = Bundle()
//                        FirebaseAnalytics.getInstance(this).logEvent("event_quran", bundle)
                        isClickQuran = false
                    }

                    changeImageIconWhenTap(
                        drawableScreenHomeTwo,
                        drawableQuranTwo,
                        drawableSalahOne,
                        drawableSettingTwo
                    )

                    changeTextColorWhenTap(colorUnSelect, colorUnSelect, colorSelect, colorUnSelect)
                    supportFragmentManager.findFragmentById(R.id.frame_layout)?.let {
                        if (it is QiblaFragment) {

                        } else {
                            AdsInter.loadInter(
                                context = this@MainActivity,
                                adsId = getString(R.string.inter_tab),
                                isShow = AdsInter.isLoadInterTab && Admob.getInstance().isLoadFullAds,
                                action = {
//                                    replaceFragment(QiblaFragment())
                                    switchFragment(QiblaFragment(), "qibla")
                                }
                            )
                        }
                    }
                }

            } else {
                Log.d("ntt", "nav: post linear Quran")

                EventBus.getDefault().post(UpdateEvent("linearQuran"))

            }

        }

        binding.bottomNavMain.linearQuran.setOnClickListener {
            if (System.currentTimeMillis() - lastClickTime < 500) return@setOnClickListener
            lastClickTime = System.currentTimeMillis()
            if (checkPermissionLocation() && checkPermissionNotification() && checkSystemAlertWindowPermission()) {

                if (isLoading) {

                    if (isClickQuran) {
                        Log.d("ntt", "nav: logEvent: event_quran")
                        val bundle = Bundle()
//                        FirebaseAnalytics.getInstance(this).logEvent("event_quran", bundle)
                        isClickQuran = false
                    }

                    changeImageIconWhenTap(
                        drawableScreenHomeTwo,
                        drawableQuranOne,
                        drawableSalahTwo,
                        drawableSettingTwo
                    )

                    changeTextColorWhenTap(colorUnSelect, colorSelect, colorUnSelect, colorUnSelect)
                    supportFragmentManager.findFragmentById(R.id.frame_layout)?.let {
                        if (it is QuranFragment) {

                        } else {
                            AdsInter.loadInter(
                                context = this@MainActivity,
                                adsId = getString(R.string.inter_tab),
                                isShow = AdsInter.isLoadInterTab && Admob.getInstance().isLoadFullAds,
                                action = {
//                                    replaceFragment(QuranFragment())
                                    switchFragment(QuranFragment(), "quran")
                                }
                            )
                        }
                    }
                }

            } else {
                Log.d("ntt", "nav: post linear Quran")

                EventBus.getDefault().post(UpdateEvent("linearQuran"))

            }
        }

        binding.bottomNavMain.linearPrayer.setOnClickListener {
            if (System.currentTimeMillis() - lastClickTime < 500) return@setOnClickListener
            lastClickTime = System.currentTimeMillis()
            if (checkPermissionLocation() && checkPermissionNotification() && checkSystemAlertWindowPermission()) {

                if (isLoading) {

                    isClickQuran = true

                    changeImageIconWhenTap(
                        drawableScreenHomeTwo,
                        drawableQuranTwo,
                        drawableSalahTwo,
                        drawableSettingOne
                    )

                    changeTextColorWhenTap(colorUnSelect, colorUnSelect, colorUnSelect, colorSelect)
                    supportFragmentManager.findFragmentById(R.id.frame_layout)?.let {
                        if (it is SalahFragment) {

                        } else {
                            AdsInter.loadInter(
                                context = this@MainActivity,
                                adsId = getString(R.string.inter_tab),
                                isShow = AdsInter.isLoadInterTab && Admob.getInstance().isLoadFullAds,
                                action = {
//                                    replaceFragment(SalahFragment())
                                    switchFragment(SalahFragment(), "salah")
                                }
                            )
                        }
                    }
                }
            } else {
                Log.d("ntt", "nav: post linear Salah")
                EventBus.getDefault().post(UpdateEvent("linearSalah"))
            }
        }

    }

    private fun changeTextColorWhenTap(
        color1: Int,
        color2: Int,
        color3: Int,
        color4: Int,
    ) {
        binding.bottomNavMain.tvScreenHome.setTextColor(color1)
        binding.bottomNavMain.tvQuran.setTextColor(color2)
        binding.bottomNavMain.tvQibla.setTextColor(color3)
        binding.bottomNavMain.tvPrayer.setTextColor(color4)
    }

    /* change image bottom nav */
    private fun changeImageIconWhenTap(
        @DrawableRes image1: Int,
        @DrawableRes image2: Int,
        @DrawableRes image3: Int,
        @DrawableRes image4: Int,
    ) {
        binding.bottomNavMain.ivScreenHome.setImageResource(image1)
        binding.bottomNavMain.ivQuran.setImageResource(image2)
        binding.bottomNavMain.ivQibla.setImageResource(image3)
        binding.bottomNavMain.ivPrayer.setImageResource(image4)
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val currentFragment = fragmentManager.findFragmentByTag(fragment.tag)
        if (currentFragment == null) {
            fragmentTransaction.replace(R.id.frame_layout, fragment,tag)
            fragmentTransaction.addToBackStack(tag)
        } else {
            fragmentTransaction.replace(R.id.frame_layout, currentFragment)
        }
        fragmentTransaction.commit()
    }

    private fun switchFragment(fragment: Fragment, tag: String) {
//        replaceFragment(fragment, tag)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    fun showLoading() {
        binding.layoutLoading.layoutLoad.visibility = View.VISIBLE
        binding.llBottom.isClickable = false
        isLoading = false
    }

    fun hideLoading() {
        binding.layoutLoading.layoutLoad.visibility = View.GONE
        binding.llBottom.isClickable = true
        isLoading = true
    }


    fun showBanner() {
        binding.frBanner.visibility = View.VISIBLE
    }

    fun hideBanner() {
        Log.d("ntt", "hideBanner: hide Banner")
        binding.frBanner.visibility = View.GONE
    }

    fun checkPermissionNotification(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

    }

    fun checkPermissionLocation(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED

    }

    fun checkSystemAlertWindowPermission(): Boolean {
        return Settings.canDrawOverlays(this)
    }

    private fun showQuitDialog() {
        val dialog = ExitDialogNew(this)
        dialog.init(object : ExitDialogNew.OnPress {
            override fun yes() {
                dialog.dismiss()
                preferenceHelper.increaseCountExitApp()
                finishAffinity()
            }

            override fun no() {
                dialog.dismiss()
            }
        })

        binding.frBanner.visibility = View.GONE
        dialog.setOnDismissListener {
            if (Admob.getInstance().isLoadFullAds)
                binding.frBanner.visibility = View.VISIBLE
        }

        dialog.show()
    }

    fun quranSelect() {
        binding.bottomNavMain.linearQuran.performClick()
    }

    fun homeSelect() {
        binding.bottomNavMain.linearHome.performClick()
    }
}