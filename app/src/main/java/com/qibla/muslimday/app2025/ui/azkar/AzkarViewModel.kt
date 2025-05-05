package com.qibla.muslimday.app2025.ui.azkar

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.database.Azkar.AzkarEntity
import com.qibla.muslimday.app2025.repository.AzkarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AzkarViewModel @Inject constructor(
    private val azkarRepository: AzkarRepository,
    private val context: Context
) : ViewModel() {

    fun initData() {
        viewModelScope.launch {
            val listAzkar = mutableListOf<AzkarEntity>()
            // Daily Dhikr
            // Darood-e-ibraheemi
            listAzkar.add(
                AzkarEntity(
                    1,
                    1,
                    1,
                    "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ كَمَا صَلَّيْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ، إِنَّكَ حَمِيدٌ مَجِيدٌ، اللَّهُمَّ بَارِكَ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ كَمَا بَارَكْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ، إِنَّكَ حَمِيدٌ مَجِيدٌ",
                    "Allaahumma salli 'alaa Muhammadin wa 'alaa 'aali Muhammadin, kamaa sallayta 'alaa 'Ibraaheema wa 'alaa 'aali 'Ibraaheema, 'innaka Hameedun Majeed. Allaahumma baarik 'alaa Muhammadin wa 'alaa 'aali Muhammadin, kamaa baarakta 'alaa 'Ibraaheema wa 'alaa 'aali 'Ibraaheema, 'innaka Hameedun Majeed",
                    R.string.text_e_ibraheemi_1
                )
            )
            //after salah (preyer) tasbeeh
            listAzkar.add(
                AzkarEntity(
                    2,
                    1,
                    2,
                    "سُبْحَانَ اللَّه (33 times) اَلْحَمْدُ لِلَّه (33 times) اَللَّهُ أَكْبَر (34 times)",
                    "Subhana-Allah (33 times) Alhamdulillah (33 times) Allahu Akbar (34 times)",
                    R.string.text_tasbeeh_1
                )
            )
            //everyday duas
            listAzkar.add(
                AzkarEntity(
                    3,
                    1,
                    3,
                    "اللَّهُمَّ إِنِّي أَسْأَلُكَ عِلْماً نَافِعاً، وَرِزْقاً طَيِّباً، وَعَمَلاً مُتَقَبَّلاً",
                    "Allaahumma 'innee 'as'aluka 'ilman naafi'an, wa rizqan tayyiban, wa 'amalan mutaqabbalan",
                    R.string.text_duas_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    4,
                    1,
                    3,
                    "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْجَنَّةَ وَأَعُوذُ بِكَ مِنَ النَّارِ",
                    "Allaahumma 'innee 'as'alukal-jannata wa 'a'oothu bika minan-naar",
                    R.string.text_duas_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    5,
                    1,
                    3,
                    "سُبْحَانَ اللهِ ، والْحَمْدُ للهِ ، وَاللهُ أَكْبَرُ",
                    "Subhaanallaahi, Walhamdu lillaahi, Wallaahu 'Akbar",
                    R.string.text_duas_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    6,
                    1,
                    3,
                    "لاَ إِلَهَ إِلَّا أنْـت سُـبْحانَكَ إِنِّي كُنْـتُ مِنَ الظّـالِميـن",
                    "Laa 'ilaaha 'illaa 'Anta subhaanaka 'innee kuntu minadh-dhaalimeen",
                    R.string.text_duas_4
                )
            )
            //before starting anything
            listAzkar.add(
                AzkarEntity(
                    7,
                    1,
                    4,
                    "بِسْمِ اللّٰہِ الرَّحْمٰنِ الرَّحِيْمِ",
                    "Bismi-llāhi r-raḥmāni r-raḥīm",
                    R.string.text_starting_anything_1
                )
            )
            //for this word and the aakhira
            listAzkar.add(
                AzkarEntity(
                    8,
                    1,
                    5,
                    "رَبَّنَآ اٰتِنَا فِي الدُّنْيَا حَسَنَةً وَّفِي الْاٰخِرَةِ حَسَـنَةً وَّقِنَا عَذَابَ النَّارِ",
                    "Rabbanaaa Aatinaa Fiddunyaa H’asanata Wa Fil Aakhirati H’asanata Wa Qinaa A’d’aaban Naar",
                    R.string.text_the_aakhira_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    9,
                    1,
                    5,
                    "رَبَّنَا إِنَّكَ جَامِعُ النَّاسِ لِيَوْمٍ لاَّ رَيْبَ فِيهِ إِنَّ اللّهَ لاَ يُخْلِفُ الْمِيعَادَ",
                    "Rabbana innaka jami'unnasi li-Yawmil la rayba fi innAllaha la yukhliful mi'aad",
                    R.string.text_the_aakhira_2
                )
            )
            //for protection and help from allah
            listAzkar.add(
                AzkarEntity(
                    10,
                    1,
                    6,
                    "اللَّهُمَّ مُنْزِلَ اْلِكتَابِ ، سَرِيْعَ الْحِسَابِ ،اِهْزِمِ الإْحْزَابَ ،اللَّهُمَّ اِهْزِمْهُمْ وَزَلْزِلْهُمْ",
                    "Allaahumma munzilal-kitaabi, saree'al-hisaabi, ihzimil-'ahzaaba, Allaahumma ihzimhum wa zalzilhum",
                    R.string.text_help_from_allah_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    11,
                    1,
                    6,
                    "قُلْ هُوَ اللهُ أَحَدٌ اللهُ الصَّمَدُ لَمْ يَلِدْ وَلَمْ يُولَدْ وَلَمْ يَكُنْ لَهُ كُفُوًا أَحَدٌ",
                    "Bismillaahir-Rahmaanir-Raheem. Qul Huwallaahu 'Ahad. Allaahus-Samad. Lam yalid wa lam yoolad. Wa lam yakun lahu kufuwan 'ahad",
                    R.string.text_help_from_allah_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    12,
                    1,
                    6,
                    "اللهُ لَا إِلَهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ لَهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ مَنْ ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلَّا بِإِذْنِهِ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ وَلَا يُحِيطُونَ بِشَيْءٍ مِنْ عِلْمِهِ إِلَّا بِمَا شَاءَ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ وَلَا يَئُودُهُ حِفْظُهُمَا وَهُوَ الْعَلِيُّ الْعَظِيمُ",
                    "Allaahu laa 'ilaaha 'illaa Huwal-Hayyul-Qayyoom, laa ta'khuthuhu sinatun wa laa nawm, lahu maa fis-samaawaati wa maafil-'ardh, man thal-lathee yashfa'u 'indahu 'illaa bi'ithnih, ya'lamu maa bayna 'aydeehim wa maa khalfahum, wa laa yuheetoona bishay'im-min 'ilmihi 'illaa bimaa shaa'a, wasi'a kursiyyuhus-samaawaati wal'ardh, wa laa ya'ooduhu hifdhuhumaa, wa Huwal-'Aliyyul- 'Adheem",
                    R.string.text_help_from_allah_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    13,
                    1,
                    6,
                    "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ مِنْ شَرِّ مَا خَلَقَ وَمِنْ شَرِّ غَاسِقٍ إِذَا وَقَبَ وَمِنْ شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ وَمِنْ شَرِّ حَاسِدٍ إِذَا حَسَدَ",
                    "Bismillaahir-Rahmaanir-Raheem. Qul 'a'oothu birabbil-falaq. Min sharri maa khalaq. Wa min sharri ghaasiqin 'ithaa waqab. Wa min sharrin-naffaathaati fil-'uqad. Wa min sharri haasidin 'ithaa hasad",
                    R.string.text_help_from_allah_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    14,
                    1,
                    6,
                    "قُلْ أَعُوذُ بِرَبِّ النَّاسِ مَلِكِ النَّاسِ إِلَهِ النَّاسِ مِنْ شَرِّ الْوَسْوَاسِ الْخَنَّاسِ الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ مِنَ الْجِنَّةِ وَالنَّاسِ",
                    "Bismillaahir-Rahmaanir-Raheem . Qul 'a'oothu birabbin-naas . Malikin-naas . 'Ilaahin-naas . Min sharril-waswaasil-khannaas. Allathee yuwaswisu fee sudoorin-naas. Minal-jinnati wannaas",
                    R.string.text_help_from_allah_5
                )
            )
            listAzkar.add(
                AzkarEntity(
                    15,
                    1,
                    6,
                    "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنْ عَذَابِ الْقَبْرِ، وَأَعُوذُ بِكَ مِنْ فِتْنَةِ الْمَسِيحِ الدَّجَّالِ، وَأَعُوذُ بِكَ مِنْ فِتْنَةِ الْمَحْيَا وَالْمَمَاتِ. اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنْ الْمَأْثَمِ وَالْمَغْرَمِ",
                    "Allaahumma 'innee 'a'oothu bika min 'athaabil-qabri, wa 'a'oothu bika min fitnatil-maseehid-dajjaali, wa 'a'oothu bika min fitnatil-mahyaa walmamaati. Allaahumma 'innee 'a'oothu bika minal-ma'thami walmaghrami",
                    R.string.text_help_from_allah_6
                )
            )
            listAzkar.add(
                AzkarEntity(
                    16,
                    1,
                    6,
                    "اللَّهُمَّ قِنِي عَذَابَكَ يَوْمَ تَبْعَثُ عِبَادَكَ",
                    "Allaahumma qinee 'athaabaka yawma tab'athu 'ibaadaka",
                    R.string.text_help_from_allah_7
                )
            )
            listAzkar.add(
                AzkarEntity(
                    17,
                    1,
                    6,
                    "اللَّهُمَّ عَالِمَ الْغَيْبِ وَالشَّهَادَةِ فَاطِرَ السَّماوَاتِ وَالْأَرْضِ، رَبَّ كُلِّ شَيْءٍ وَمَلِيكَهُ، أَشْهَدُ أَنْ لَا إِلَهَ إِلَّا أَنْتَ، أَعُوذُ بِكَ مِنْ شَرِّ نَفْسِي، وَمِنْ شَرِّ الشَّيْطَانِ وَشِرْكِهِ، وَأَنْ أَقْتَرِفَ عَلَى نَفْسِي سُوءاً، أَوْ أَجُرَّهُ إِلَى مُسْلِمٍ",
                    "Allaahumma 'Aalimal-ghaybi wash-shahaadati faatiras-samaawaati wal'ardhi, Rabba kulli shay 'in wa maleekahu, 'ash-hadu 'an laa 'ilaaha 'illaa 'Anta, 'a'oothu bika min sham nafsee, wa min sharrish-shaytaani wa shirkihi, wa 'an 'aqtarifa 'alaa nafsee soo'an, 'aw 'ajurrahu 'ilaa Muslimin",
                    R.string.text_help_from_allah_8
                )
            )
            listAzkar.add(
                AzkarEntity(
                    18,
                    1,
                    6,
                    "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي الدُّنْيَا وَالْآخِرَةِ، اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي دِينِي وَدُنْيَايَ وَأَهْللِي، وَمَالِي، اللَّهُمَّ اسْتُرْ عَوْرَاتِي، وَآمِنْ رَوْعَاتِي، اللَّهُمَّ احْفَظْنِي مِنْ بَيْنِ يَدَيَّ، وَمِنْ خَلْفِي، وَعَنْ يَمِينِي، وَعَنْ شِمَالِي، وَمِنْ فَوْقِي، وَأَعُوذُ بِعَظَمَتِكَ أَنْ أُغْتَالَ مِنْ تَحْتِي",
                    "Allaahumma 'innee 'as'alukal-'afwa wal'aafiyata fid-dunyaa wal'aakhirati, Allaahumma 'innee 'as'alukal-'afwa wal'aafiyata fee deenee wa dunyaaya wa 'ahlee, wa maalee , Allaahum-mastur 'awraatee, wa 'aamin raw'aatee, Allaahum-mahfadhnee min bayni yadayya, wa min khalfee, wa 'an yameenee, wa 'an shimaalee, wa min fawqee, wa 'a'oothu bi'adhamatika 'an 'ughtaala min tahtee",
                    R.string.text_help_from_allah_9
                )
            )
            listAzkar.add(
                AzkarEntity(
                    19,
                    1,
                    6,
                    "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ للهِ وَالْحَمْدُ للهِ، لَا إِلَهَ إِلَّا اللهُ وَحَدْهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ، ربِّ أَسْأَلُكَ خَيْرَ مَا فِي هَذَا الْيَومِ وَخَيْرَ مَا بَعْدَهُ، وَأَعُوذُ بِكَ مِنْ شَرِّ مَا فِي هَذَا الْيَومِ وَشَرِّ مَا بَعْدَهُ، رَبِّ أَعُوذُ بِكَ مِنَ الْكَسَلِ، وَسُوءِ الكِبَرِ، رَبِّ أَعُوذُ بِكَ مِنْ عَذَابٍ فِي النَّارِ وَعَذَابٍ فِي الْقَبْرِ",
                    "Asbahnaa wa 'asbahal-mulku lillaahi walhamdu lillaahi, laa 'ilaaha 'illallaahu wahdahu laa shareeka lahu, lahul-mulku wa lahul-hamdu wa Huwa 'alaa kutti shay'in Qadeer. Rabbi 'as'aluka khayra maa fee haathal-yawmi wa khayra maa ba'dahu wa 'a'oothu bika min sharri maa fee haathal-yawmi wa sharri maa ba'dahu, Rabbi 'a'oothu bika minal-kasali, wa soo'il-kibari, Rabbi 'a'oothu bika min 'athaabin fin-naari wa 'athaabin fil-qabri",
                    R.string.text_help_from_allah_10
                )
            )
            listAzkar.add(
                AzkarEntity(
                    20,
                    1,
                    6,
                    "أَنَّ اللَّهَ مَوْلَاكُمْ ۚ نِعْمَ الْمَوْلَىٰ وَنِعْمَ النَّصِيرُ",
                    "Anna Allaha mawlakum ni'mal mawla wa ni'man naseer",
                    R.string.text_help_from_allah_11
                )
            )
            //reward of one-third of quran
            listAzkar.add(
                AzkarEntity(
                    325,
                    1,
                    7,
                    "[سورة الإخلاص] قل هو الله أحد الله الصمد لم يلد ولم يولد ولم يكن له كفوا أحد",
                    "[surat al'iikhlasi] qul hu allah 'ahad allah alsamad lam yalid walam yulad walam yakun lah kufuan 'ahad",
                    R.string.text_of_quran_1
                )
            )

            //Daily Rememberence
            //morning rememberence
            listAzkar.add(
                AzkarEntity(
                    21,
                    2,
                    8,
                    "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ وَالْحَمْدُ لِلَّهِ، لَا إِلَهَ إِلَّّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ، رَبِّ أَسْأَلُكَ خَيْرَ مَا فِيْ هَذَا الْيَوْمِ وَخَيْرَ مَا بَعْدَهُ، وَأَعُوْذُ بِكَ مِنْ شَرِّ مَا فِي هَذَا الْيَوْمِ وَشَرِّ مَا بَعْدَهُ، رَبِّ أَعُوْذُ بِكَ مِنَ الْكَسَلِ، وَسُوءِ الْكِبَرِ، رَبِّ أَعُوْذُ بِكَ مِنْ عَذَابٍ فِيْ النَّارِ وَعَذَابٍ فِيْ الْقَبْرِ",
                    "Asbahna wa-asbahal-mulku lillah walhamdu lillah la ilaha illal-lah, wahdahu la shareeka lah, lahul-mulku walahul-hamd, wahuwa AAala kulli shayin qadeer, rabbi as-aluka khayra ma fee hatha-alyawmi, wakhayra ma baAAdaho, wa-aAAoothu bika min sharri hatha-alyawmi, washarri ma baAAdaho, rabbi aAAoothu bika minal-kasal, wasoo-il kibar, rabbi aAAoothu bika min AAathabin fin-nar, waAAathabin fil-qabr.",
                    R.string.text_morning_rememberence_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    22,
                    2,
                    8,
                    "اللَّهُمَّ بِكَ أَصْبَحْنَا، وَبِكَ أَمْسَيْنَا، وَبِكَ نَحْيَا، وَبِكَ نَمُوتُ، وَإِلَيْكَ النُّشُورُ.",
                    "Allahumma bika asbahna wabika amsayna, wabika nahya ,wabika namootu wa-ilaykan-nushoor.",
                    R.string.text_morning_rememberence_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    23,
                    2,
                    8,
                    "اللَّهُمَّ أَنْتَ رَبِّي لَّا إِلَهَ إِلَّا أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ، وَأَبُوءُ بِذَنْبِي فَاغْفِر لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ.",
                    "Allahumma anta rabbee la ilaha illa ant, khalaqtanee wa-ana AAabduk, wa-ana AAala AAahdika wawaAAdika mas-tataAAt, aAAoothu bika min sharri ma sanaAAt, aboo-o laka biniAAmatika AAalay, wa-aboo-o bithanbee, faghfir lee fa-innahu la yaghfiruth-thunooba illa ant.",
                    R.string.text_morning_rememberence_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    24,
                    2,
                    8,
                    "اللَّهُمَّ إِنِّي أَصْبَحْتُ أُشْهِدُكَ وَأُشْهِدُ حَمَلَةَ عَرْشِكَ، وَمَلَائِكَتَكَ وَجَمِيعَ خَلْقِكَ، أَنَّكَ أَنْتَ اللَّهُ لَا إِلَهَ إِلَّا أَنْتَ وَحْدَكَ لَا شَرِيكَ لَكَ، وَأَنَّ مُحَمَّداً عَبْدُكَ وَرَسُولُكَ (أربع مرات)",
                    "Allahumma innee asbahtu oshhiduk, wa-oshhidu hamalata AAarshik, wamala-ikatak, wajameeAAa khalqik, annaka antal-lahu la ilaha illa ant, wahdaka la shareeka lak, wa-anna Muhammadan AAabduka warasooluk (four times).",
                    R.string.text_morning_rememberence_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    25,
                    2,
                    8,
                    "اللَّهُمَّ مَا أَصْبَحَ بِي مِنْ نِعْمَةٍ أَوْ بِأَحَدٍ مِنْ خَلْقِكَ فَمِنْكَ وَحْدَكَ لَا شَرِيكَ لَكَ، فَلَكَ الْحَمْدُ وَلَكَ الشُّكْرُ.",
                    "Allahumma ma asbaha bee min niAAmatin, aw bi-ahadin min khalqik, faminka wahdaka la shareeka lak, falakal-hamdu walakash-shukr.",
                    R.string.text_morning_rememberence_5
                )
            )
            listAzkar.add(
                AzkarEntity(
                    26,
                    2,
                    8,
                    "اللَّهُمَّ عَافِـني فِي بَدَنِي، اللَّهُمَّ عَافِـنِي فِي سَمْعِي، اللَّهُمَّ عَافِنِي فِي بَصَرِي، لَا إِلَهَ إلاَّ أَنْتَ.(ثلاثاً) اللَّهُمَّ إِنِّي أَعُوذُبِكَ مِنَ الْكُفْر، وَالفَقْرِ، وَأَعُوذُبِكَ مِنْ عَذَابِ الْقَبْرِ ، لَا إلَهَ إلاَّ أَنْتَ. (ثلاثاً).",
                    "Allahumma AAafinee fee badanee, allahumma AAafinee fee samAAee, allahumma AAafinee fee basaree, la ilaha illa ant.(three times). Allahumma innee aAAoothu bika minal-kufr, walfaqr, wa-aAAoothu bika min AAathabil-qabr, la ilaha illa ant (three times).",
                    R.string.text_morning_rememberence_6
                )
            )
            listAzkar.add(
                AzkarEntity(
                    27,
                    2,
                    8,
                    "حَسْبِيَ اللَّهُ لَآ إِلَهَ إِلَّا هُوَ عَلَيْهِ تَوَكَّلْتُ وَهُوَ رَبُّ الْعَرْشِ الْعَظِيمِ. (سبع مَرّات حينَ يصْبِح وَيمسي)",
                    "Hasbiyal-lahu la ilaha illa huwa, AAalayhi tawakkalt, wahuwa rabbul-AAarshil-AAatheem",
                    R.string.text_morning_rememberence_7
                )
            )
            listAzkar.add(
                AzkarEntity(
                    28,
                    2,
                    8,
                    "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ مِنْ شَرِّ مَا خَلَقَ. (ثلاثاً إِذا أمسى)",
                    "aAAoothu bikalimatil-lahit-tammati min sharri ma khalaq.",
                    R.string.text_morning_rememberence_8
                )
            )
            listAzkar.add(
                AzkarEntity(
                    29,
                    2,
                    8,
                    "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي الدُّنْيَا وَالْآخِرَةِ، اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي دِينِي، وَدُنْيَايَ، وَأَهْلِي، وَمَالِي، اللَّهُمَّ اسْتُرْ عَوْرَاتِي، وَآمِنْ رَوْعَاتِي، اللَّهُمَّ احْفَظْنِي مِنْ بَيْنِ يَدَيَّ، وَمِنْ خَلْفِي، وَعَنْ يَمِينِي، وَعَنْ شِمَالِي، وَمِنْ فَوْقِي، وَأَعُوذُ بِعَظَمَتِكَ أَنْ أُغْتَالَ مِنْ تَحْتِيَ.",
                    "Allahumma innee as-alukal-AAafwa walAAafiyah, fid-dunya wal-akhirah, allahumma innee as-alukal-AAafwa walAAafiyah fee deenee, wadunyaya wa-ahlee, wamalee, allahummas-tur AAawratee, wa-amin rawAAatee, allahummah-fathnee min bayni yaday, wamin khalfee, waAAan yameenee, waAAan shimalee, wamin fawqee, wa-aAAoothu biAAathamatika an oghtala min tahtee.",
                    R.string.text_morning_rememberence_9
                )
            )
            listAzkar.add(
                AzkarEntity(
                    30,
                    2,
                    8,
                    "اللَّهُمَّ عَالِمَ الْغَيْبِ وَالشَّهَادَةِ فَاطِرَ السَّماوَاتِ وَالْأَرْضِ، رَبَّ كُلِّ شَيْءٍ وَمَلِيكَهُ، أَشْهَدُ أَنْ لَا إِلَهَ إِلَّا أَنْتَ، أَعُوذُ بِكَ مِنْ شَرِّ نَفْسِي، وَمِنْ شَرِّ الشَّيْطَانِ وَشِرْكِهِ، وَأَنْ أَقْتَرِفَ عَلَى نَفْسِي سُوءاً أَوْ أَجُرَّهُ إِلَى مُسْلِمٍ.",
                    "Allahumma AAalimal-ghaybi washshahadah, fatiras-samawati wal-ard, rabba kulli shayin wamaleekah, ashhadu an la ilaha illa ant, aAAoothu bika min sharri nafsee wamin sharrish-shaytani washirkih, waan aqtarifa AAala nafsee soo-an aw ajurrahu ila muslim.",
                    R.string.text_morning_rememberence_10
                )
            )
            listAzkar.add(
                AzkarEntity(
                    31,
                    2,
                    8,
                    "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الَْأرْضِ وَلَا فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ. (ثلاثاً)",
                    "Bismil-lahil-lathee la yadurru maAAas-mihi shay-on fil-ardi wala fis-sama-i wahuwas-sameeAAul-AAaleem.",
                    R.string.text_morning_rememberence_11
                )
            )
            listAzkar.add(
                AzkarEntity(
                    32,
                    2,
                    8,
                    "رَضِيتُ باللَّهِ رَبًّا، وَبِالْإِسْلَامِ دِيناً، وَبِمُحَمَّدٍ صَلَى اللَّهُ عَلِيهِ وَسَلَّمَ نَبِيَّاً. (ثلاثاً)",
                    "Radeetu billahi rabban wabil-islami deenan wabiMuhammadin peace be upon to him nabiyya.",
                    R.string.text_morning_rememberence_12
                )
            )
            listAzkar.add(
                AzkarEntity(
                    33,
                    2,
                    8,
                    "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ عَدَدَ خَلْقِهِ، وَرِضَا نَفْسِهِ، وَزِنَةَ عَرْشِهِ وَمِدَادَ كَلِمَاتِهِ . (ثلاثاً)",
                    "Subhanal-lahi wabihamdih, AAadada khalqihi warida nafsih, wazinata AAarshih, wamidada kalimatih.",
                    R.string.text_morning_rememberence_13
                )
            )
            listAzkar.add(
                AzkarEntity(
                    34,
                    2,
                    8,
                    "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ . (مائة مرة)",
                    "Subhanal-lahi wabihamdih.",
                    R.string.text_morning_rememberence_14
                )
            )
            listAzkar.add(
                AzkarEntity(
                    35,
                    2,
                    8,
                    "يَاحَيُّ، يَا قَيُّومُ، بِرَحْمَتِكَ أَسْتَغِيثُ، أَصْلِحْ لِي شَأْنِي كُلَّهُ، وَلَا تَكِلْنِي إِلَى نَفْسِي طَرْفَةَ عَيْنٍ.",
                    "Ya hayyu ya qayyoom, birahmatika astagheeth, aslih lee sha/nee kullah, wala takilnee ila nafsee tarfata AAayn.",
                    R.string.text_morning_rememberence_15
                )
            )
            listAzkar.add(
                AzkarEntity(
                    36,
                    2,
                    8,
                    "لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ . (مائة مرة)",
                    "La ilaha illal-lah, wahdahu la shareeka lah, lahul-mulku walahul-hamd, wahuwa AAala kulli shay-in qadeer.",
                    R.string.text_morning_rememberence_16
                )
            )
            listAzkar.add(
                AzkarEntity(
                    37,
                    2,
                    8,
                    "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ رَبِّ الْعَالَمِينَ، اللَّهُمَّ إِنِّـي أَسْأَلُكَ خَـيْرَ هَذَا الْـيَوْمِ ، فَتْحَهُ، وَنَصْرَهُ، وَنُورَهُ وَبَرَكَتَهُ، وَهُدَاهُ، وَأَعُوذُ بِكَ مِنْ شَرِّ مَا فِيهِ وَشَرِّ مَا بَعْدَهُ.",
                    "Asbahna wa-asbahal-mulku lillahi rabbil-AAalameen, allahumma innee as-aluka khayra hathal-yawm, fat-hahu, wanasrahu, wanoorahu, wabarakatahu, wahudahu, wa-aAAoothu bika min sharri ma feehi, washarri ma baAAdah.",
                    R.string.text_morning_rememberence_17
                )
            )
            //evening rememberence
            listAzkar.add(
                AzkarEntity(
                    38,
                    2,
                    9,
                    "أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ وَالْحَمْدُ لِلَّهِ لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَىْءٍ قَدِيرٌ رَبِّ أَسْأَلُكَ خَيْرَ مَا فِي هَذِهِ اللَّيْلَةِ وَخَيْرَ مَا بَعْدَهَا وَأَعُوذُ بِكَ مِنْ شَرِّ مَا فِي هَذِهِ اللَّيْلَةِ وَشَرِّ مَا بَعْدَهَا رَبِّ أَعُوذُ بِكَ مِنَ الْكَسَلِ وَسُوءِ الْكِبَرِ رَبِّ أَعُوذُ بِكَ مِنْ عَذَابٍ فِي النَّارِ وَعَذَابٍ فِي الْقَبْرِ",
                    "Amsayna wa-amsal-mulku lillah walhamdu lillah la ilaha illal-lah, wahdahu la shareeka lah, lahul-mulku walahul-hamd, wahuwa AAala kulli shayin qadeer, rabbi as-aluka khayra ma fee hathihil-laylah, wakhayra ma baAAdaha, wa-aAAoothu bika min sharri hathihil-laylah, washarri ma baAAdaha, rabbi aAAoothu bika minal-kasal, wasoo-il kibar, rabbi aAAoothu bika min AAathabin fin-nar, waAAathabin fil-qabr.",
                    R.string.text_evening_rememberence_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    39,
                    2,
                    9,
                    "اللَّهُمَّ بِكَ أَمْسَيْنَا، وَبِكَ أَصْبَحْنَا، وَبِكَ نَحْيَا، وَبِكَ نَمُوتُ وَإِلَـيْكَ المَصِيْر.",
                    "Allahumma bika amsayna, wabika asbahna, wabika nahya wabika namootu wa-ilaykal-maseer.",
                    R.string.text_evening_rememberence_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    40,
                    2,
                    9,
                    "اللَّهُمَّ أَنْتَ رَبِّي لَّا إِلَهَ إِلَّا أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ، وَأَبُوءُ بِذَنْبِي فَاغْفِر لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ.",
                    "Allahumma anta rabbee la ilaha illa ant, khalaqtanee wa-ana AAabduk, wa-ana AAala AAahdika wawaAAdika mas-tataAAt, aAAoothu bika min sharri ma sanaAAt, aboo-o laka biniAAmatika AAalay, wa-aboo-o bithanbee, faghfir lee fa-innahu la yaghfiruth-thunooba illa ant.",
                    R.string.text_evening_rememberence_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    41,
                    2,
                    9,
                    "اللَّهُمَّ أَمْسَيْنَا نُشْهِدُكَ وَنُشْهِدُ حَمَلَةَ عَرْشِكَ وَمَلاَئِكَتَكَ وَجَمِيعَ خَلْقِكَ بِأَنَّكَ الله لاَ إِلَهَ إِلاَّ أَنْتَ وَحْدَكَ لاَ شَرِيكَ لَكَ وَأَنَّ مُحَمَّدًا عَبْدُكَ وَرَسُولُكَ",
                    "Allahumma amsaina nush-hiduka wa nush-hidu ḥamalata arshika wa mala’ikataka wa jami`a khalqika bi-annaka Allah, la ilaha illa anta, waḥdaka la sharika laka, wa anna Muḥammadan abduka wa rasuluk",
                    R.string.text_evening_rememberence_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    42,
                    2,
                    9,
                    "اللَّهُمَّ مَا أََمْسَ بِي مِنْ نِعْمَةٍ أَوْ بِأَحَدٍ مِنْ خَلْقِكَ فَمِنْكَ وَحْدَكَ لَا شَرِيكَ لَكَ، فَلَكَ الْحَمْدُ وَلَكَ الشُّكْرُ.",
                    "Allahumma ma amsa bee min niAAmatin, aw bi-ahadin min khalqik, faminka wahdaka la shareeka lak, falakal-hamdu walakash-shukr.",
                    R.string.text_evening_rememberence_5
                )
            )
            listAzkar.add(
                AzkarEntity(
                    43,
                    2,
                    9,
                    "اللَّهُمَّ عَافِـني فِي بَدَنِي، اللَّهُمَّ عَافِـنِي فِي سَمْعِي، اللَّهُمَّ عَافِنِي فِي بَصَرِي، لَا إِلَهَ إلاَّ أَنْتَ.(ثلاثاً) اللَّهُمَّ إِنِّي أَعُوذُبِكَ مِنَ الْكُفْر، وَالفَقْرِ، وَأَعُوذُبِكَ مِنْ عَذَابِ الْقَبْرِ ، لَا إلَهَ إلاَّ أَنْتَ. (ثلاثاً).",
                    "Allahumma AAafinee fee badanee, allahumma AAafinee fee samAAee, allahumma AAafinee fee basaree, la ilaha illa ant.(three times). Allahumma innee aAAoothu bika minal-kufr, walfaqr, wa-aAAoothu bika min AAathabil-qabr, la ilaha illa ant (three times).",
                    R.string.text_evening_rememberence_6
                )
            )
            listAzkar.add(
                AzkarEntity(
                    44,
                    2,
                    9,
                    "حَسْبِيَ اللَّهُ لَآ إِلَهَ إِلَّا هُوَ عَلَيْهِ تَوَكَّلْتُ وَهُوَ رَبُّ الْعَرْشِ الْعَظِيمِ. (سبع مَرّات حينَ يصْبِح وَيمسي)",
                    "Hasbiyal-lahu la ilaha illa huwa, AAalayhi tawakkalt, wahuwa rabbul-AAarshil-AAatheem",
                    R.string.text_evening_rememberence_7
                )
            )
            listAzkar.add(
                AzkarEntity(
                    45,
                    2,
                    9,
                    "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ مِنْ شَرِّ مَا خَلَقَ. (ثلاثاً إِذا أمسى)",
                    "aAAoothu bikalimatil-lahit-tammati min sharri ma khalaq.",
                    R.string.text_evening_rememberence_8
                )
            )
            listAzkar.add(
                AzkarEntity(
                    46,
                    2,
                    9,
                    "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي الدُّنْيَا وَالْآخِرَةِ، اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي دِينِي، وَدُنْيَايَ، وَأَهْلِي، وَمَالِي، اللَّهُمَّ اسْتُرْ عَوْرَاتِي، وَآمِنْ رَوْعَاتِي، اللَّهُمَّ احْفَظْنِي مِنْ بَيْنِ يَدَيَّ، وَمِنْ خَلْفِي، وَعَنْ يَمِينِي، وَعَنْ شِمَالِي، وَمِنْ فَوْقِي، وَأَعُوذُ بِعَظَمَتِكَ أَنْ أُغْتَالَ مِنْ تَحْتِيَ.",
                    "Allahumma innee as-alukal-AAafwa walAAafiyah, fid-dunya wal-akhirah, allahumma innee as-alukal-AAafwa walAAafiyah fee deenee, wadunyaya wa-ahlee, wamalee, allahummas-tur AAawratee, wa-amin rawAAatee, allahummah-fathnee min bayni yaday, wamin khalfee, waAAan yameenee, waAAan shimalee, wamin fawqee, wa-aAAoothu biAAathamatika an oghtala min tahtee.",
                    R.string.text_evening_rememberence_9
                )
            )
            listAzkar.add(
                AzkarEntity(
                    47,
                    2,
                    9,
                    "اللَّهُمَّ عَالِمَ الْغَيْبِ وَالشَّهَادَةِ فَاطِرَ السَّماوَاتِ وَالْأَرْضِ، رَبَّ كُلِّ شَيْءٍ وَمَلِيكَهُ، أَشْهَدُ أَنْ لَا إِلَهَ إِلَّا أَنْتَ، أَعُوذُ بِكَ مِنْ شَرِّ نَفْسِي، وَمِنْ شَرِّ الشَّيْطَانِ وَشِرْكِهِ، وَأَنْ أَقْتَرِفَ عَلَى نَفْسِي سُوءاً أَوْ أَجُرَّهُ إِلَى مُسْلِمٍ.",
                    "Allahumma AAalimal-ghaybi washshahadah, fatiras-samawati wal-ard, rabba kulli shayin wamaleekah, ashhadu an la ilaha illa ant, aAAoothu bika min sharri nafsee wamin sharrish-shaytani washirkih, waan aqtarifa AAala nafsee soo-an aw ajurrahu ila muslim.",
                    R.string.text_evening_rememberence_10
                )
            )
            listAzkar.add(
                AzkarEntity(
                    48,
                    2,
                    9,
                    "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الَْأرْضِ وَلَا فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ. (ثلاثاً)",
                    "Bismil-lahil-lathee la yadurru maAAas-mihi shay-on fil-ardi wala fis-sama-i wahuwas-sameeAAul-AAaleem.",
                    R.string.text_evening_rememberence_11
                )
            )
            listAzkar.add(
                AzkarEntity(
                    49,
                    2,
                    9,
                    "رَضِيتُ باللَّهِ رَبًّا، وَبِالْإِسْلَامِ دِيناً، وَبِمُحَمَّدٍ صَلَى اللَّهُ عَلِيهِ وَسَلَّمَ نَبِيَّاً. (ثلاثاً)",
                    "Radeetu billahi rabban wabil-islami deenan wabiMuhammadin peace be upon to him nabiyya.",
                    R.string.text_evening_rememberence_12
                )
            )
            listAzkar.add(
                AzkarEntity(
                    50,
                    2,
                    9,
                    "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ عَدَدَ خَلْقِهِ، وَرِضَا نَفْسِهِ، وَزِنَةَ عَرْشِهِ وَمِدَادَ كَلِمَاتِهِ . (ثلاثاً)",
                    "Subhanal-lahi wabihamdih, AAadada khalqihi warida nafsih, wazinata AAarshih, wamidada kalimatih.",
                    R.string.text_evening_rememberence_13
                )
            )
            listAzkar.add(
                AzkarEntity(
                    51,
                    2,
                    9,
                    "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ . (مائة مرة)",
                    "Subhanal-lahi wabihamdih.",
                    R.string.text_evening_rememberence_14
                )
            )
            listAzkar.add(
                AzkarEntity(
                    52,
                    2,
                    9,
                    "يَاحَيُّ، يَا قَيُّومُ، بِرَحْمَتِكَ أَسْتَغِيثُ، أَصْلِحْ لِي شَأْنِي كُلَّهُ، وَلَا تَكِلْنِي إِلَى نَفْسِي طَرْفَةَ عَيْنٍ.",
                    "Ya hayyu ya qayyoom, birahmatika astagheeth, aslih lee sha/nee kullah, wala takilnee ila nafsee tarfata AAayn.",
                    R.string.text_evening_rememberence_15
                )
            )
            listAzkar.add(
                AzkarEntity(
                    53,
                    2,
                    9,
                    "لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ . (مائة مرة)",
                    "La ilaha illal-lah, wahdahu la shareeka lah, lahul-mulku walahul-hamd, wahuwa AAala kulli shay-in qadeer.",
                    R.string.text_evening_rememberence_16
                )
            )
            listAzkar.add(
                AzkarEntity(
                    54,
                    2,
                    9,
                    "أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ رَبِّ الْعَالَمِينَ اللَّهُمَّ إِنِّي أَسْأَلُكَ خَيْرَ هَذِهِ اللَّيْلَةَ فَتْحَهَا وَنَصْرَهَا وَنُورَهَا وَبَرَكَتَهَا وَهُدَاهَا وَأَعُوذُ بِكَ مِنْ شَرِّ مَا فِيهَا وَشَرِّ مَا بَعْدَهَا.",
                    "Amsayna wa-amsal-mulku lillahi rabbil-AAalameen, allahumma innee as-aluka khayra hathal-yawm, fat-hahu, wanasrahu, wanoorahu, wabarakatahu, wahudahu, wa-aAAoothu bika min sharri ma feehi, washarri ma baAAdah.",
                    R.string.text_evening_rememberence_17
                )
            )


            //After Prayers
            //after salah (prayer) tasbeeh
            listAzkar.add(
                AzkarEntity(
                    55,
                    3,
                    10,
                    "سُبْحَانَ اللَّه (33 times) اَلْحَمْدُ لِلَّه (33 times) اَللَّهُ أَكْبَر (34 times)",
                    "Subhana-Allah (33 times) Alhamdulillah (33 times) Allahu Akbar (34 times)",
                    R.string.text_tasbeeh_1
                )
            )
            //after prayer remembrances
            listAzkar.add(
                AzkarEntity(
                    56,
                    3,
                    11,
                    "أَسْتَغْفِرُ اللَّهَ . (ثَلاثاً) اللَّهُمَّ أَنْتَ السَّلاَمُ، وَمِنْكَ السَّلَامُ، تَبَارَكْتَ يَا ذَا الْجَلَالِ وَالإِكْرَامِ .",
                    "Astaghfirul-lah (three times) Allahumma antas-salam waminkas-salam, tabarakta ya thal-jalali wal-ikram.",
                    R.string.text_remembrances_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    57,
                    3,
                    11,
                    "لَا إلَهَ إِلاَّ اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ، اللَّهُمَّ لَا مَانِعَ لِمَا أَعْطَيْتَ، وَلَا مُعْطِيَ لِمَا مَنَعْتَ، وَلَا يَنْفَعُ ذَا الْجَدِّ مِنْكَ الْجَدُّ.",
                    "La ilaha illal-lahu wahdahu la shareeka lah, lahul-mulku walahul-hamd, wahuwa AAala kulli shayin qadeer, allahumma la maniAAa lima aAAtayt, wala muAAtiya lima manaAAt, wala yanfaAAu thal-jaddi minkal-jad.",
                    R.string.text_remembrances_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    58,
                    3,
                    11,
                    "سُبْحَانَ اللَّهِ، وَالْحَمْدُ لِلَّهِ، وَاللَّهُ أَكْبَرُ. (ثلاثاً وثلاثين) لَا إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ .",
                    "Subhanal-lah walhamdu lillah, wallahu akbar (thirty-three times). La ilaha illal-lahu wahdahu la shareeka lah, lahul-mulku walahul-hamd, wahuwa AAala kulli shayin qadeer.",
                    R.string.text_remembrances_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    59,
                    3,
                    11,
                    "قُلْ هُوَ اللهُ أَحَدٌ. اللهُ الصَّمَدُ. لَمْ يَلِدْ وَلَمْ يُولَدْ. وَلَمْ يَكُنْ لَهُ كُفُوًا أَحَدٌ. [ الإِخْـلاصْ ] قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ. مِنْ شَرِّ مَا خَلَقَ. وَمِنْ شَرِّ غَاسِقٍ إِذَا وَقَبَ. وَمِنْ شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ. وَمِنْ شَرِّ حَاسِدٍ إِذَا حَسَدَ [ الفَلَـقْ ] قُلْ أَعُوذُ بِرَبِّ النَّاسِ. مَلِكِ النَّاسِ. إِلَهِ النَّاسِ. مِنْ شَرِّ الْوَسْوَاسِ الْخَنَّاسِ. الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ. مِنَ الْجِنَّةِ وَالنَّاسِ [ الـنّاس ] (ثلاث مرات بعد صلاتي الفجر والمغرب. ومرة بعد الصلوات الأخرى)",
                    "Qul Huwa-llahu ‘Ahad\\nAllahus-Samad\\nLam Yalid Wa Lam Yulad\\nWalam Yakul-La-Hu-Kufuwan ‘Ahad [Al-Ikhlas] Qul ‘A’udhu Bi-Rabbil-Falaq\\nMin Sharri Ma Khalaq\\nWa Min Sharri Ghasiqin ‘Idha Waqab\\nWa Min Sharri-Naffathati Fil-‘Uqadi\\nWa Min Sharri Hasidin ‘Idha Hasad [Al-Falaq] Qul ‘A’udhu Bi-Rabbin-Nas\\nMalikin-Nas\\nIlahin-Nas\\nMin-Sharril-Waswasil-Khan-Nas\\nAl-Ladhi Yuwas-wisu Fee Sudurin-Nas\\nMina Al-Jinnati Wan-Nas [An-Nas]",
                    R.string.text_remembrances_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    60,
                    3,
                    11,
                    "اللَّهُ لاَ إِلَهَ إِلاَّ هُوَ الْحَيُّ الْقَيُّومُ لاَ تَأْخُذُهُ سِنَةٌ وَلاَ نَوْمٌ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الأَرْضِ مَن ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلاَّ بِإِذْنِهِ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ وَلاَ يُحِيطُونَ بِشَىْءٍ مِّنْ عِلْمِهِ إِلاَّ بِمَا شَاءَ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالأَرْضَ وَلاَ يَؤُودُهُ حِفْظُهُمَا وَهُوَ الْعَلِيُّ الْعَظِيمُ",
                    "Allaahu laa 'ilaaha 'illaa Huwal-Hayyul-Qayyoom, laa ta'khuthuhu sinatun wa laa nawm, lahu maa fis-samaawaati wa maafil-'ardh, man thal-lathee yashfa'u 'indahu 'illaa bi'ithnih, ya'lamu maa bayna 'aydeehim wa maa khalfahum, wa laa yuheetoona bishay'im-min 'ilmihi 'illaa bimaa shaa'a, wasi'a kursiyyuhus-samaawaati wal'ardh, wa laa ya'ooduhu hifdhuhumaa, wa Huwal-'Aliyyul- 'Adheem.",
                    R.string.text_remembrances_5
                )
            )
            listAzkar.add(
                AzkarEntity(
                    61,
                    3,
                    11,
                    "لَا إِلَهَ إِلَّا اللَّهُ وَحَدْهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ يُحْيِي وَيُمِيتُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ . (عَشْر مَرّات بَعْدَ المَغْرِب وَالصّـبْح )",
                    "La ilaha illal-lahu wahdahu la shareeka lah, lahul-mulku walahul-hamd, yuhyee wayumeet, wahuwa AAala kulli shayin qadeer.(ten times after the maghrib & fajr prayers)",
                    R.string.text_remembrances_6
                )
            )
            listAzkar.add(
                AzkarEntity(
                    62,
                    3,
                    11,
                    "اللَّهُمَّ إِنِّي أَسْأَلُكَ عِلْماً نَافِعاً وَرِزْقاً طَيِّباً، وَعَمَلاً مُتَقَبَّلاً . (بَعْد السّلامِ من صَلاةِ الفَجْر )",
                    "Allahumma innee as-aluka AAilman nafiAAan, warizqan tayyiban, waAAamalan mutaqabbalan.(after salam from fajr prayer).",
                    R.string.text_remembrances_7
                )
            )
            //offer salah regularly
            listAzkar.add(
                AzkarEntity(
                    63,
                    3,
                    12,
                    "إِنَّ رَبِّي لَسَمِيعُ الدُّعَاءِ رَبِّ اجْعَلْنِي مُقِيمَ الصَّلَاةِ وَمِنْ ذُرِّيَّتِي ۚ رَبَّنَا وَتَقَبَّلْ دُعَاءِ",
                    "Inna Rabbee la samee ud duaa. Rabbij alnee muqeemas salaati wa min thurriyyatee rabbana wa taqabbal duaa",
                    R.string.text_regularly_1
                )
            )
            //for protection and help from allah
            listAzkar.add(
                AzkarEntity(
                    64,
                    3,
                    13,
                    "اللَّهُمَّ مُنْزِلَ اْلِكتَابِ ، سَرِيْعَ الْحِسَابِ ،اِهْزِمِ الإْحْزَابَ ،اللَّهُمَّ اِهْزِمْهُمْ وَزَلْزِلْهُمْ",
                    "Allaahumma munzilal-kitaabi, saree'al-hisaabi, ihzimil-'ahzaaba, Allaahumma ihzimhum wa zalzilhum",
                    R.string.text_help_from_allah_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    65,
                    3,
                    13,
                    "قُلْ هُوَ اللهُ أَحَدٌ اللهُ الصَّمَدُ لَمْ يَلِدْ وَلَمْ يُولَدْ وَلَمْ يَكُنْ لَهُ كُفُوًا أَحَدٌ",
                    "Bismillaahir-Rahmaanir-Raheem. Qul Huwallaahu 'Ahad. Allaahus-Samad. Lam yalid wa lam yoolad. Wa lam yakun lahu kufuwan 'ahad",
                    R.string.text_help_from_allah_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    66,
                    3,
                    13,
                    "اللهُ لَا إِلَهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ لَهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ مَنْ ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلَّا بِإِذْنِهِ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ وَلَا يُحِيطُونَ بِشَيْءٍ مِنْ عِلْمِهِ إِلَّا بِمَا شَاءَ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ وَلَا يَئُودُهُ حِفْظُهُمَا وَهُوَ الْعَلِيُّ الْعَظِيمُ",
                    "Allaahu laa 'ilaaha 'illaa Huwal-Hayyul-Qayyoom, laa ta'khuthuhu sinatun wa laa nawm, lahu maa fis-samaawaati wa maafil-'ardh, man thal-lathee yashfa'u 'indahu 'illaa bi'ithnih, ya'lamu maa bayna 'aydeehim wa maa khalfahum, wa laa yuheetoona bishay'im-min 'ilmihi 'illaa bimaa shaa'a, wasi'a kursiyyuhus-samaawaati wal'ardh, wa laa ya'ooduhu hifdhuhumaa, wa Huwal-'Aliyyul- 'Adheem",
                    R.string.text_help_from_allah_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    67,
                    3,
                    13,
                    "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ مِنْ شَرِّ مَا خَلَقَ وَمِنْ شَرِّ غَاسِقٍ إِذَا وَقَبَ وَمِنْ شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ وَمِنْ شَرِّ حَاسِدٍ إِذَا حَسَدَ",
                    "Bismillaahir-Rahmaanir-Raheem. Qul 'a'oothu birabbil-falaq. Min sharri maa khalaq. Wa min sharri ghaasiqin 'ithaa waqab. Wa min sharrin-naffaathaati fil-'uqad. Wa min sharri haasidin 'ithaa hasad",
                    R.string.text_help_from_allah_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    68,
                    3,
                    13,
                    "قُلْ أَعُوذُ بِرَبِّ النَّاسِ مَلِكِ النَّاسِ إِلَهِ النَّاسِ مِنْ شَرِّ الْوَسْوَاسِ الْخَنَّاسِ الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ مِنَ الْجِنَّةِ وَالنَّاسِ",
                    "Bismillaahir-Rahmaanir-Raheem . Qul 'a'oothu birabbin-naas . Malikin-naas . 'Ilaahin-naas . Min sharril-waswaasil-khannaas. Allathee yuwaswisu fee sudoorin-naas. Minal-jinnati wannaas",
                    R.string.text_help_from_allah_5
                )
            )
            listAzkar.add(
                AzkarEntity(
                    69,
                    3,
                    13,
                    "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنْ عَذَابِ الْقَبْرِ، وَأَعُوذُ بِكَ مِنْ فِتْنَةِ الْمَسِيحِ الدَّجَّالِ، وَأَعُوذُ بِكَ مِنْ فِتْنَةِ الْمَحْيَا وَالْمَمَاتِ. اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنْ الْمَأْثَمِ وَالْمَغْرَمِ",
                    "Allaahumma 'innee 'a'oothu bika min 'athaabil-qabri, wa 'a'oothu bika min fitnatil-maseehid-dajjaali, wa 'a'oothu bika min fitnatil-mahyaa walmamaati. Allaahumma 'innee 'a'oothu bika minal-ma'thami walmaghrami",
                    R.string.text_help_from_allah_6
                )
            )
            listAzkar.add(
                AzkarEntity(
                    70,
                    3,
                    13,
                    "اللَّهُمَّ قِنِي عَذَابَكَ يَوْمَ تَبْعَثُ عِبَادَكَ",
                    "Allaahumma qinee 'athaabaka yawma tab'athu 'ibaadaka",
                    R.string.text_help_from_allah_7
                )
            )
            listAzkar.add(
                AzkarEntity(
                    71,
                    3,
                    13,
                    "اللَّهُمَّ عَالِمَ الْغَيْبِ وَالشَّهَادَةِ فَاطِرَ السَّماوَاتِ وَالْأَرْضِ، رَبَّ كُلِّ شَيْءٍ وَمَلِيكَهُ، أَشْهَدُ أَنْ لَا إِلَهَ إِلَّا أَنْتَ، أَعُوذُ بِكَ مِنْ شَرِّ نَفْسِي، وَمِنْ شَرِّ الشَّيْطَانِ وَشِرْكِهِ، وَأَنْ أَقْتَرِفَ عَلَى نَفْسِي سُوءاً، أَوْ أَجُرَّهُ إِلَى مُسْلِمٍ",
                    "Allaahumma 'Aalimal-ghaybi wash-shahaadati faatiras-samaawaati wal'ardhi, Rabba kulli shay 'in wa maleekahu, 'ash-hadu 'an laa 'ilaaha 'illaa 'Anta, 'a'oothu bika min sham nafsee, wa min sharrish-shaytaani wa shirkihi, wa 'an 'aqtarifa 'alaa nafsee soo'an, 'aw 'ajurrahu 'ilaa Muslimin",
                    R.string.text_help_from_allah_8
                )
            )
            listAzkar.add(
                AzkarEntity(
                    72,
                    3,
                    13,
                    "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي الدُّنْيَا وَالْآخِرَةِ، اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي دِينِي وَدُنْيَايَ وَأَهْللِي، وَمَالِي، اللَّهُمَّ اسْتُرْ عَوْرَاتِي، وَآمِنْ رَوْعَاتِي، اللَّهُمَّ احْفَظْنِي مِنْ بَيْنِ يَدَيَّ، وَمِنْ خَلْفِي، وَعَنْ يَمِينِي، وَعَنْ شِمَالِي، وَمِنْ فَوْقِي، وَأَعُوذُ بِعَظَمَتِكَ أَنْ أُغْتَالَ مِنْ تَحْتِي",
                    "Allaahumma 'innee 'as'alukal-'afwa wal'aafiyata fid-dunyaa wal'aakhirati, Allaahumma 'innee 'as'alukal-'afwa wal'aafiyata fee deenee wa dunyaaya wa 'ahlee, wa maalee , Allaahum-mastur 'awraatee, wa 'aamin raw'aatee, Allaahum-mahfadhnee min bayni yadayya, wa min khalfee, wa 'an yameenee, wa 'an shimaalee, wa min fawqee, wa 'a'oothu bi'adhamatika 'an 'ughtaala min tahtee",
                    R.string.text_help_from_allah_9
                )
            )
            listAzkar.add(
                AzkarEntity(
                    73,
                    3,
                    13,
                    "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ للهِ وَالْحَمْدُ للهِ، لَا إِلَهَ إِلَّا اللهُ وَحَدْهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ، ربِّ أَسْأَلُكَ خَيْرَ مَا فِي هَذَا الْيَومِ وَخَيْرَ مَا بَعْدَهُ، وَأَعُوذُ بِكَ مِنْ شَرِّ مَا فِي هَذَا الْيَومِ وَشَرِّ مَا بَعْدَهُ، رَبِّ أَعُوذُ بِكَ مِنَ الْكَسَلِ، وَسُوءِ الكِبَرِ، رَبِّ أَعُوذُ بِكَ مِنْ عَذَابٍ فِي النَّارِ وَعَذَابٍ فِي الْقَبْرِ",
                    "Asbahnaa wa 'asbahal-mulku lillaahi walhamdu lillaahi, laa 'ilaaha 'illallaahu wahdahu laa shareeka lahu, lahul-mulku wa lahul-hamdu wa Huwa 'alaa kutti shay'in Qadeer. Rabbi 'as'aluka khayra maa fee haathal-yawmi wa khayra maa ba'dahu wa 'a'oothu bika min sharri maa fee haathal-yawmi wa sharri maa ba'dahu, Rabbi 'a'oothu bika minal-kasali, wa soo'il-kibari, Rabbi 'a'oothu bika min 'athaabin fin-naari wa 'athaabin fil-qabri",
                    R.string.text_help_from_allah_10
                )
            )
            listAzkar.add(
                AzkarEntity(
                    74,
                    3,
                    13,
                    "أَنَّ اللَّهَ مَوْلَاكُمْ ۚ نِعْمَ الْمَوْلَىٰ وَنِعْمَ النَّصِيرُ",
                    "Anna Allaha mawlakum ni'mal mawla wa ni'man naseer",
                    R.string.text_help_from_allah_11
                )
            )


            //Rizq (Sustenance)
            //for rizq (sustenance)
            listAzkar.add(
                AzkarEntity(
                    75,
                    4,
                    14,
                    "اللَّهُمَّ إِنِّي أَسْأَلُكَ عِلْماً نَافِعاً، وَرِزْقاً طَيِّباً، وَعَمَلاً مُتَقَبَّلاً",
                    "Allaahumma 'innee 'as'aluka 'ilman naafi'an, wa rizqan tayyiban, wa 'amalan mutaqabbalan",
                    R.string.text_rizq_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    76,
                    4,
                    14,
                    "اللَّهُمَّ اغْفِرْ لِي، وَارْحَمْنِي، وَاهْدِنِي، وَاجْبُرْنِي، وَعَافِنِي، وَارْزُقْنِي، وَارْفَعْنِي",
                    "Allaahum-maghfir lee, warhamnee, wahdinee, wajburnee, wa 'aafinee, warzuqnee, warfa'nee",
                    R.string.text_rizq_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    77,
                    4,
                    14,
                    "اللَّهُمَّ إَنِّي أَسْأَلُكَ مِنْ فَضْلِكَ",
                    "Allaahumma 'innee 'as'aluka min fadhlika",
                    R.string.text_rizq_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    78,
                    4,
                    14,
                    "اللَّهُ لَطِيفٌ بِعِبَادِهِ يَرْزُقُ مَن يَشَاءُ ۖ وَهُوَ الْقَوِيُّ الْعَزِيزُ",
                    "Allahu lateefun biAAibadihiyarzuqu man yashao wahuwa alqawiyyu alAAazeez",
                    R.string.text_rizq_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    79,
                    4,
                    14,
                    "رَبَّنَا أَنزِلْ عَلَيْنَا مَآئِدَةً مِّنَ السَّمَاء تَكُونُ لَنَا عِيداً لِّأَوَّلِنَا وَآخِرِنَا وَآيَةً مِّنكَ وَارْزُقْنَا وَأَنتَ خَيْرُ الرَّازِقِينَ",
                    "Rabbana anzil 'alayna ma'idatam minas-Samai tuknu lana 'idal li-awwa-lina wa aakhirna wa ayatam-minka war-zuqna wa anta Khayrul-Raziqeen",
                    R.string.text_rizq_5
                )
            )
            //when you are in need
            listAzkar.add(
                AzkarEntity(
                    80,
                    4,
                    15,
                    "رَبِّ إِنِّي لِمَا أَنزَلْتَ إِلَيَّ مِنْ خَيْرٍ فَقِيرٌ",
                    "Rabbi innee lima anzalta ilayya min khayrin faqeer",
                    R.string.text_need_1
                )
            )
            //for forgiveness and allah’s mercy
            listAzkar.add(
                AzkarEntity(
                    81,
                    4,
                    16,
                    "أَنْتَ وَلِيُّنَا فَاغْفِرْ لَنَا وَارْحَمْنَا ۖ وَأَنْتَ خَيْرُ الْغَافِرِينَ وَاكْتُبْ لَنَا فِي هَٰذِهِ الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ إِنَّا هُدْنَا إِلَيْكَ",
                    "Anta waliyyuna faghfirlana warhamna waanta khayru alghafireen. Waktub lana fee hathihi ddunya hasanatan wafee al-akhiratiinna hudna ilayk",
                    R.string.text_allah_mercy_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    82,
                    4,
                    16,
                    "رَّبِّ اغْفِرْ وَارْحَمْ وَاَنْتَ خَيْرُ الرّٰحِمِيْنَ",
                    "Rabbighfir Warh’am Wa Anta Khayrur Raah’imeen",
                    R.string.text_allah_mercy_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    83,
                    4,
                    16,
                    "رَبَّنَا ظَلَمْنَا أَنفُسَنَا وَإِن لَّمْ تَغْفِرْ لَنَا وَتَرْحَمْنَا لَنَكُونَنَّ مِنَ الْخَاسِرِينَ",
                    "Rabbana zalamna anfusina wa il lam taghfir lana wa tarhamna lana kunan minal-khasireen",
                    R.string.text_allah_mercy_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    84,
                    4,
                    16,
                    "رَبَّنَا آتِنَا مِن لَّدُنكَ رَحْمَةً وَهَيِّئْ لَنَا مِنْ أَمْرِنَا رَشَدًا",
                    "Rabbana 'atina mil-ladunka Rahmataw wa hayyi lana min amrina rashada",
                    R.string.text_allah_mercy_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    85,
                    4,
                    16,
                    "رَبَّنَآ اٰمَنَّا فَاغْفِرْ لَنَا وَارْحَمْنَا وَاَنْتَ خَيْرُ الرّٰحِمِيْنَ",
                    "Rabbanaa Aamannaa Faghfir Lanaa Warh’amnaa Wa Anta Khayrur Raah’imeen",
                    R.string.text_allah_mercy_5
                )
            )
            //for praising allah
            listAzkar.add(
                AzkarEntity(
                    86,
                    4,
                    17,
                    "اللَّهُمَّ مَالِكَ الْمُلْكِ تُؤْتِي الْمُلْكَ مَنْ تَشَاءُ وَتَنْزِعُ الْمُلْكَ مِمَّنْ تَشَاءُ وَتُعِزُّ مَنْ تَشَاءُ وَتُذِلُّ مَنْ تَشَاءُ ۖ بِيَدِكَ الْخَيْرُ ۖ إِنَّكَ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ تُولِجُ اللَّيْلَ فِي النَّهَارِ وَتُولِجُ النَّهَارَ فِي اللَّيْلِ ۖ وَتُخْرِجُ الْحَيَّ مِنَ الْمَيِّتِ وَتُخْرِجُ الْمَيِّتَ مِنَ الْحَيِّ ۖ وَتَرْزُقُ مَنْ تَشَاءُ بِغَيْرِ حِسَابٍ",
                    "allahumma malika almulkitu/tee almulka man tashao watanziAAu almulka mimman tashaowatuAAizzu man tashao watuthillu man tashaobiyadika alkhayru innaka AAala kulli shay-in qadeer. Tooliju allayla fee annahariwatooliju annahara fee allayli watukhriju alhayyamina almayyiti watukhriju almayyita mina alhayyi watarzuquman tashao bighayri hisab.",
                    R.string.text_praising_allah_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    87,
                    4,
                    17,
                    "فَسُبْحَانَ اللَّهِ حِينَ تُمْسُونَ وَحِينَ تُصْبِحُونَ وَلَهُ الْحَمْدُ فِي السَّمَاوَاتِ وَالْأَرْضِ وَعَشِيًّا وَحِينَ تُظْهِرُونَ يُخْرِجُ الْحَيَّ مِنَ الْمَيِّتِ وَيُخْرِجُ الْمَيِّتَ مِنَ الْحَيِّ وَيُحْيِي الْأَرْضَ بَعْدَ مَوْتِهَا ۚ وَكَذَٰلِكَ تُخْرَجُونَ",
                    "Fasubhana Allahi heenatumsoona waheena tusbihoon. Walahu alhamdu fee assamawatiwal-ardi waAAashiyyan waheena tuthhiroon. Yukhriju alhayya mina almayyitiwayukhriju almayyita mina alhayyi wayuhyee al-ardabaAAda mawtiha wakathalika tukhrajoon.",
                    R.string.text_praising_allah_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    88,
                    4,
                    17,
                    "سُبْـحَانَ اللهِ وَبِحَمْدِهِ سُبْـحَانَ اللهِ العَظِيمِ",
                    "Subhaanal-laahi wa bihamdihi, Subhaanal-laahil-'Adheem.",
                    R.string.text_praising_allah_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    89,
                    4,
                    17,
                    "سُبْحَانَ اللهِ، والحَمْدُ للهِ، وَ لاَ إِلَهَ إلاَّ اللهُ واللهُ أَكْبَرُ",
                    "Subhaanallaahi, walhamdu lillaahi, wa laa 'ilaaha 'illallaahu, wallaahu 'Akbar.",
                    R.string.text_praising_allah_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    90,
                    4,
                    17,
                    "سُبْحَانَ اللهِ",
                    "Subhaanallah",
                    R.string.text_praising_allah_5
                )
            )
            listAzkar.add(
                AzkarEntity(
                    91,
                    4,
                    17,
                    "لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللهِ",
                    "Laa hawla wa laa quwwata 'illaa billaah",
                    R.string.text_praising_allah_6
                )
            )
            listAzkar.add(
                AzkarEntity(
                    92,
                    4,
                    17,
                    "سُبْحَانَ اللهِ العَظِيمِ وبِحَمْدِهِ",
                    "Subhaanallaahil-'Adheemi wa bihamdihi.",
                    R.string.text_praising_allah_7
                )
            )
            listAzkar.add(
                AzkarEntity(
                    93,
                    4,
                    17,
                    "هُوَ اللَّهُ الَّذِي لَا إِلَٰهَ إِلَّا هُوَ ۖ عَالِمُ الْغَيْبِ وَالشَّهَادَةِ ۖ هُوَ الرَّحْمَٰنُ الرَّحِيمُ هُوَ اللَّهُ الَّذِي لَا إِلَٰهَ إِلَّا هُوَ الْمَلِكُ الْقُدُّوسُ السَّلَامُ الْمُؤْمِنُ الْمُهَيْمِنُ الْعَزِيزُ الْجَبَّارُ الْمُتَكَبِّرُ ۚ سُبْحَانَ اللَّهِ عَمَّا يُشْرِكُونَ هُوَ اللَّهُ الْخَالِقُ الْبَارِئُ الْمُصَوِّرُ ۖ لَهُ الْأَسْمَاءُ الْحُسْنَىٰ ۚ يُسَبِّحُ لَهُ مَا فِي السَّمَاوَاتِ وَالْأَرْضِ ۖ وَهُوَ الْعَزِيزُ الْحَكِيمُ",
                    "Huwa Allahu allathee lailaha illa huwa AAalimu alghaybi washshahadatihuwa arrahmanu arraheem. Huwa Allahu allathee lailaha illa huwa almaliku alquddoosu assalamualmu/minu almuhayminu alAAazeezu aljabbaru almutakabbirusubhana Allahi AAamma yushrikoon. Huwa Allahu alkhaliqu albari-oalmusawwiru lahu al-asmao alhusnayusabbihu lahu ma fee assamawatiwal-ardi wahuwa alAAazeezu alhakeem.",
                    R.string.text_praising_allah_8
                )
            )
            listAzkar.add(
                AzkarEntity(
                    94,
                    4,
                    17,
                    "مَا شَاءَ اللَّهُ لَا قُوَّةَ إِلَّا بِاللَّهِ",
                    "Ma shaa Allahu la quwwata illabillahi",
                    R.string.text_praising_allah_9
                )
            )
            //for success
            listAzkar.add(
                AzkarEntity(
                    95,
                    4,
                    18,
                    "وَمَا تَوْفِيقِي إِلَّا بِاللَّهِ ۚ عَلَيْهِ تَوَكَّلْتُ وَإِلَيْهِ أُنِيبُ",
                    "Wama tawfeeqee illa billahiAAalayhi tawakkaltu wa-ilayhi oneeb",
                    R.string.text_for_success_4
                )
            )


            //Knowledge
            //increase in knowledge
            listAzkar.add(
                AzkarEntity(
                    96,
                    5,
                    19,
                    "رَّبِّ زِدْنِيْ عِلْمًا",
                    "Rabbi Zidnee I’lmaa.",
                    R.string.text_knowledge_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    97,
                    5,
                    19,
                    "اللَّهُمَّ إِنِّي أَسْأَلُكَ عِلْماً نَافِعاً، وَرِزْقاً طَيِّباً، وَعَمَلاً مُتَقَبَّلاً",
                    "Allaahumma 'innee 'as'aluka 'ilman naafi'an, wa rizqan tayyiban, wa 'amalan mutaqabbalan",
                    R.string.text_knowledge_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    98,
                    5,
                    19,
                    "رَبِّ هَبْ لِي حُكْمًا وَأَلْحِقْنِي بِالصَّالِحِينَ وَاجْعَل لِّي لِسَانَ صِدْقٍ فِي الْآخِرِينَ وَاجْعَلْنِي مِن وَرَثَةِ جَنَّةِ النَّعِيمِ وَاغْفِرْ لِأَبِي إِنَّهُ كَانَ مِنَ الضَّالِّينَ وَلَا تُخْزِنِي يَوْمَ يُبْعَثُونَ يَوْمَ لَا يَنفَعُ مَالٌ وَلَا بَنُونَ إِلَّا مَنْ أَتَى اللَّهَ بِقَلْبٍ سَلِيمٍ",
                    "Rabbi hab lee hukman wa alhiqnee bis saliheen. Waj'al lee lisana sidqin fil akhireen. Waj'al nee min warathati jannatin na'eem. Waghfir li-abee innahu kanamina addalleen. Wala tukhzinee yawma yub'asoon. Yawma la yanfa'u malun walaa banoon. Illa man ata Allah bi qalbin saleem.",
                    R.string.text_knowledge_3
                )
            )
            //grant me confidence and eloquence
            listAzkar.add(
                AzkarEntity(
                    99,
                    5,
                    20,
                    "رَبِّ اشْرَحْ لِيْ صَدْرِيْ. وَيَسِّرْ لِيْٓ اَمْرِيْ. وَاحْلُلْ عُقْدَةً مِّنْ لِّسَانِيْ. يَفْقَـــهُوْا قَوْلِيْ.",
                    "Rabbishrah’ Lee S’adree wa Yassir Leee Amree Wah’–Lul U’qdatam Mil Lisaanee Yafqahoo Qawlee",
                    R.string.text_eloquence_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    100,
                    5,
                    20,
                    "سُبْحَانَكَ لَا عِلْمَ لَنَا إِلَّا مَا عَلَّمْتَنَا ۖ إِنَّكَ أَنْتَ الْعَلِيمُ الْحَكِيمُ",
                    "Subhanaka laAAilma lana illa ma AAallamtanainnaka anta alAAaleemu alhakeem",
                    R.string.text_eloquence_2
                )
            )
            //for protection from ignorance
            listAzkar.add(
                AzkarEntity(
                    101,
                    5,
                    21,
                    "أَعُوذُ بِاللَّهِ أَنْ أَكُونَ مِنَ الْجَاهِلِينَ",
                    "Aoothu billahi an akoona minal jahileen",
                    R.string.text_ignorance_1
                )
            )
            //protection from foolishness
            listAzkar.add(
                AzkarEntity(
                    102,
                    5,
                    22,
                    "الَّهُمَّ إِنِّي أَعُوذُ بِكَ أَنْ أَضِلَّ، أّوْ أُضَلَّ، أَوْ أَزِلَّ، أَوْ أُزَلَّ، أَوْ أَظْلِمَ، أَوْ أُظْلَمَ، أَوْ أَجْهَلَ، أَوْ يُجْهَلَ عَلَيَّ",
                    "Allaahumma 'innee 'a'oothu bika 'an 'adhilla, 'aw 'udhalla, 'aw 'azilla, 'aw 'uzalla, 'aw 'adhlima, 'aw 'udhlama, 'aw 'ajhala 'aw yujhala 'alayya",
                    R.string.text_foolishness_1
                )
            )


            //Faith
            //strengthen your imaan
            listAzkar.add(
                AzkarEntity(
                    103,
                    6,
                    23,
                    "رَضِيتُ باللَّهِ رَبًّا، وَبِالْإِسْلَامِ دِيناً، وَبِمُحَمَّدٍ صَلَى اللَّهُ عَلِيهِ وَسَلَّمَ نَبِيَّاً. (ثلاثاً)",
                    "Radeetu billahi rabban wabil-islami deenan wabiMuhammadin peace be upon to him nabiyya.",
                    R.string.text_strengthen_your_imaan_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    104,
                    6,
                    23,
                    "َعُوْذُ بِاللهِ",
                    "`A'udhu billah",
                    R.string.text_strengthen_your_imaan_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    105,
                    6,
                    23,
                    "آمَنْـتُ بِاللهِ وَرُسُـلِه",
                    "Aamantu billaahi wa Rusulihi",
                    R.string.text_strengthen_your_imaan_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    106,
                    6,
                    23,
                    "رَبَّنَا لَا تُزِغْ قُلُوْبَنَا بَعْدَ اِذْ ھَدَيْتَنَا وَھَبْ لَنَا مِنْ لَّدُنْكَ رَحْمَةً ۚ اِنَّكَ اَنْتَ الْوَھَّابُ",
                    "Rabbanaa Laa Tuzigh Quloobanaa Ba’-Da Id’hadaytanaa Wa Hab Lanaa Mil Ladunka Rah’mah Innaka Antal Wahaab",
                    R.string.text_strengthen_your_imaan_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    107,
                    6,
                    23,
                    "رَبَّنَا اغْفِرْ لَنَا ذُنُوْبَنَا وَ اِسْرَافَنَا فِيْٓ اَمْرِنَا وَثَبِّتْ اَقْدَامَنَا وَانْصُرْنَا عَلَي الْقَوْمِ الْكٰفِرِيْنَ",
                    "Rabbanaghfir lanaa d’unoobanaa wa israafanaa fee amrinaa wa thabbit aqdaamanaa wan s’urnaa a’lal qawmil kaafireen",
                    R.string.text_strengthen_your_imaan_5
                )
            )
            //to make us practicing muslims
            listAzkar.add(
                AzkarEntity(
                    108,
                    6,
                    24,
                    "رَبَّنَآ ءَامَنَّا بِمَآ أَنزَلۡتَ وَٱتَّبَعۡنَا ٱلرَّسُولَ فَٱكۡتُبۡنَا مَعَ ٱلشَّٰهِدِينَ",
                    "Rabbana amanna bima anzalta wattaba 'nar-Rusula fak-tubna ma'ash-Shahideen",
                    R.string.text_practicing_muslims_1
                )
            )
            //seek forgiveness and strengthen your imaan
            listAzkar.add(
                AzkarEntity(
                    306,
                    6,
                    25,
                    "ۭرَبَّنَا لَا تُؤَاخِذْنَآ اِنْ نَّسِيْنَآ اَوْ اَخْطَاْنَا ۚ رَبَّنَا وَلَا تَحْمِلْ عَلَيْنَآ اِصْرًا كَمَا حَمَلْتَهٗ عَلَي الَّذِيْنَ مِنْ قَبْلِنَا ۚ رَبَّنَا وَلَا تُحَمِّلْنَا مَا لَا طَاقَةَ لَنَا بِهٖ ۚ وَاعْفُ عَنَّا ۪ وَاغْفِرْ لَنَا ۪ وَارْحَمْنَا ۪ اَنْتَ مَوْلٰىنَا فَانْــصُرْنَا عَلَي الْقَوْمِ الْكٰفِرِيْنَ",
                    "Rabbanaa Laa Too-Akhid’naaa In-Naseenaa Aw Akht’aanaa Rabbanaa wa Laa Tah’mil A’laynaaa Is’ran Kamaa Ha’maltahoo A’lal Lad’eena min Qablinaa Rabbanaa wa Laa Tuh’ammilnaa Maa Laa T’aaqata Lanaa Bih Wa’-Fu A’nnaa Waghfirlanaa Warh’amnaa Anta Mawlaanaa Fans’urnaa A’lal Qawmil Kaafireen",
                    R.string.text_and_strengthen_your_imaan_1
                )
            )
            //pledge to allah
            listAzkar.add(
                AzkarEntity(
                    109,
                    6,
                    26,
                    "إِنِّي وَجَّهْتُ وَجْهِيَ لِلَّذِي فَطَرَ السَّمَاوَاتِ وَالْأَرْضَ حَنِيفًا ۖ وَمَا أَنَا مِنَ الْمُشْرِكِينَ",
                    "Innee wajjahtu wajhiya lillathee fataraassamawati wal-arda haneefanwama ana mina almushrikeen",
                    R.string.text_to_allah_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    110,
                    6,
                    26,
                    "إِنَّ صَلَاتِي وَنُسُكِي وَمَحْيَايَ وَمَمَاتِي لِلَّهِ رَبِّ الْعَالَمِينَ لَا شَرِيكَ لَهُ ۖ وَبِذَٰلِكَ أُمِرْتُ وَأَنَا أَوَّلُ الْمُسْلِمِينَ",
                    "inna salatee wanusukee wamahyaya wamamatee lillahi rabbil aalameen. La shareeka lahu wa bithalika omirtu wa ana aw walul muslimeen",
                    R.string.text_to_allah_2
                )
            )
            //for righteous company
            listAzkar.add(
                AzkarEntity(
                    111,
                    6,
                    27,
                    "فَاطِرَ السَّمَاوَاتِ وَالْأَرْضِ أَنْتَ وَلِيِّي فِي الدُّنْيَا وَالْآخِرَةِ ۖ تَوَفَّنِي مُسْلِمًا وَأَلْحِقْنِي بِالصَّالِحِينَ",
                    "fatira assamawatiwal-ardi anta waliyyee fee addunyawal-akhirati tawaffanee musliman waalhiqneebissaliheen",
                    R.string.text_righteous_company_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    112,
                    6,
                    27,
                    "رَبَّنَا آمَنَّا فَاكْتُبْنَا مَعَ الشَّاهِدِينَ",
                    "Rabbana aamana faktubna ma' ash-shahideen",
                    R.string.text_righteous_company_2
                )
            )


            //Day of judgement
            //for this world and the aakhira
            listAzkar.add(
                AzkarEntity(
                    113,
                    7,
                    28,
                    "رَبَّنَآ اٰتِنَا فِي الدُّنْيَا حَسَنَةً وَّفِي الْاٰخِرَةِ حَسَـنَةً وَّقِنَا عَذَابَ النَّارِ",
                    "Rabbanaaa Aatinaa Fiddunyaa H’asanata Wa Fil Aakhirati H’asanata Wa Qinaa A’d’aaban Naar",
                    R.string.text_the_aakhira_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    114,
                    7,
                    28,
                    "رَبَّنَا إِنَّكَ جَامِعُ النَّاسِ لِيَوْمٍ لاَّ رَيْبَ فِيهِ إِنَّ اللّهَ لاَ يُخْلِفُ الْمِيعَادَ",
                    "Rabbana innaka jami'unnasi li-Yawmil la rayba fi innAllaha la yukhliful mi'aad",
                    R.string.text_the_aakhira_2
                )
            )
            //seek forgiveness and protection from hell
            listAzkar.add(
                AzkarEntity(
                    115,
                    7,
                    29,
                    "رَبَّنَا وَسِعْتَ كُلَّ شَيْءٍ رَّحْمَةً وَعِلْمًا فَاغْفِرْ لِلَّذِينَ تَابُوا وَاتَّبَعُوا سَبِيلَكَ وَقِهِمْ عَذَابَ الْجَحِيمِ",
                    "Rabbana wasi'ta kulla sha'ir Rahmatanw wa 'ilman faghfir lilladhina tabu wattaba'u sabilaka waqihim 'adhabal-Jahiim",
                    R.string.text_from_hell_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    116,
                    7,
                    29,
                    "رَبَّنَآ اِنَّنَآ اٰمَنَّا فَاغْفِرْ لَنَا ذُنُوْبَنَا وَقِنَا عَذَابَ النَّارِ",
                    "Rabbana innana amanna faghfir lana dhunuubana wa qinna 'adhaban-Naar",
                    R.string.text_from_hell_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    117,
                    7,
                    29,
                    "رَبَّنَا مَا خَلَقْتَ هٰذَا بَاطِلًا ۚ سُبْحٰنَكَ فَقِنَا عَذَابَ النَّارِ. رَبَّنَآ اِنَّكَ مَنْ تُدْخِلِ النَّارَ فَقَدْ اَخْزَيْتَهٗ ۭ وَمَا لِلظّٰلِمِيْنَ مِنْ اَنْصَارٍ. رَبَّنَآ اِنَّنَا سَمِعْنَا مُنَادِيًا يُّنَادِيْ لِلْاِيْمَانِ اَنْ اٰمِنُوْا بِرَبِّكُمْ فَاٰمَنَّا ۚ رَبَّنَا فَاغْفِرْ لَنَا ذُنُوْبَنَا وَكَفِّرْ عَنَّا سَيِّاٰتِنَا وَتَوَفَّنَا مَعَ الْاَبْرَارِ. رَبَّنَا وَاٰتِنَا مَا وَعَدْتَّنَا عَلٰي رُسُلِكَ وَلَا تُخْزِنَا يَوْمَ الْقِيٰمَةِ ۭاِنَّكَ لَا تُخْلِفُ الْمِيْعَادَ.",
                    "Rabbana ma khalaqta hadha batila Subhanaka faqina 'adhaban-Naar. Rabbana innaka man tudkhilin nara faqad akhzaytah wa ma liDh-dhalimeena min ansar. Rabbanaa Innanaa Sami’-Naa Munaadiyay Yunaadee Lil – Eemaani An Aaminoo Birabbikum Fa- Aamannaa Rabbanaa Faghfir Lanaa D’unoobanaa Wa Kaffir A’nnaaa Sayyi- Aatinaa Wa Tawaffanaa Ma-A’l Abraaar. Rabbanaa Wa Aatinaa Maa Wa-A’ttanaa A’laa Rusulika Wa Laa Tukhzinaa Yawal Qiyaamah Innak Laa Tukhliful Mee’a’ad.",
                    R.string.text_from_hell_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    118,
                    7,
                    29,
                    "رَبَّنَا لَا تُؤَاخِذْنَآ اِنْ نَّسِيْنَآ اَوْ اَخْطَاْنَا ۚ رَبَّنَا وَلَا تَحْمِلْ عَلَيْنَآ اِصْرًا كَمَا حَمَلْتَهٗ عَلَي الَّذِيْنَ مِنْ قَبْلِنَا ۚ رَبَّنَا وَلَا تُحَمِّلْنَا مَا لَا طَاقَةَ لَنَا بِهٖ ۚ وَاعْفُ عَنَّا ۪ وَاغْفِرْ لَنَا ۪ وَارْحَمْنَا ۪ اَنْتَ مَوْلٰىنَا فَانْــصُرْنَا عَلَي الْقَوْمِ الْكٰفِرِيْنَ",
                    "Rabbanaa Laa Too-Akhid’naaa In-Naseenaa Aw Akht’aanaa Rabbanaa wa Laa Tah’mil A’laynaaa Is’ran Kamaa Ha’maltahoo A’lal Lad’eena min Qablinaa Rabbanaa wa Laa Tuh’ammilnaa Maa Laa T’aaqata Lanaa Bih Wa’-Fu A’nnaa Waghfirlanaa Warh’amnaa Anta Mawlaanaa Fans’urnaa A’lal Qawmil Kaafireen",
                    R.string.text_from_hell_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    119,
                    7,
                    29,
                    "رَبَّنَا أَتْمِمْ لَنَا نُورَنَا وَاغْفِرْ لَنَا إِنَّكَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
                    "Rabbana atmim lana nurana waighfir lana innaka 'ala kulli shay-in qadir",
                    R.string.text_from_hell_5
                )
            )
            //for forgiveness for your parent and all muslims
            listAzkar.add(
                AzkarEntity(
                    120,
                    7,
                    30,
                    "رَبَّنَا اغْفِرْ لِي وَلِوَالِدَيَّ وَلِلْمُؤْمِنِينَ يَوْمَ يَقُومُ الْحِسَابُ",
                    "Rabbana ghfir li wa li wallidayya wa lil Mu'mineena yawma yaqumul hisaab",
                    R.string.text_all_muslims_1
                )
            )
            //for jannah
            listAzkar.add(
                AzkarEntity(
                    121,
                    7,
                    31,
                    "رَبَّنَا اصْرِفْ عَنَّا عَذَابَ جَهَنَّمَ ۖ إِنَّ عَذَابَهَا كَانَ غَرَامًا إِنَّهَا سَاءَتْ مُسْتَقَرًّا وَمُقَامًا",
                    "Rabbanas-rif 'anna 'adhaba jahannama inna 'adhabaha kana gharama innaha sa'at musta-qarranw wa muqama",
                    R.string.text_for_jannah_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    122,
                    7,
                    31,
                    "رَبَّنَا وَأَدْخِلْهُمْ جَنَّاتِ عَدْنٍ الَّتِي وَعَدتَّهُمْ وَمَن صَلَحَ مِنْ آبَائِهِمْ وَأَزْوَاجِهِمْ وَذُرِّيَّاتِهِمْ ۚ إِنَّكَ أَنتَ الْعَزِيزُ الْحَكِيمُ وَقِهِمُ السَّيِّئَاتِ ۚ وَمَن تَقِ السَّيِّئَاتِ يَوْمَئِذٍ فَقَدْ رَحِمْتَهُ ۚ وَذَٰلِكَ هُوَ الْفَوْزُ الْعَظِيمُ",
                    "Rabbana wa adhkhilhum Jannati 'adninil-lati wa'attahum wa man salaha min aba'ihim wa azwajihim wa dhuriyyatihim innaka antal 'Azizul-Hakim, waqihimus sayyi'at wa man taqis-sayyi'ati yawma'idhin faqad rahimatahu wa dhalika huwal fawzul-'Adheem",
                    R.string.text_for_jannah_2
                )
            )


            //Forgiveness
            //seek forgiveness and strengthen your imaan
            listAzkar.add(
                AzkarEntity(
                    307,
                    8,
                    32,
                    "ۭرَبَّنَا لَا تُؤَاخِذْنَآ اِنْ نَّسِيْنَآ اَوْ اَخْطَاْنَا ۚ رَبَّنَا وَلَا تَحْمِلْ عَلَيْنَآ اِصْرًا كَمَا حَمَلْتَهٗ عَلَي الَّذِيْنَ مِنْ قَبْلِنَا ۚ رَبَّنَا وَلَا تُحَمِّلْنَا مَا لَا طَاقَةَ لَنَا بِهٖ ۚ وَاعْفُ عَنَّا ۪ وَاغْفِرْ لَنَا ۪ وَارْحَمْنَا ۪ اَنْتَ مَوْلٰىنَا فَانْــصُرْنَا عَلَي الْقَوْمِ الْكٰفِرِيْنَ",
                    "Rabbanaa Laa Too-Akhid’naaa In-Naseenaa Aw Akht’aanaa Rabbanaa wa Laa Tah’mil A’laynaaa Is’ran Kamaa Ha’maltahoo A’lal Lad’eena min Qablinaa Rabbanaa wa Laa Tuh’ammilnaa Maa Laa T’aaqata Lanaa Bih Wa’-Fu A’nnaa Waghfirlanaa Warh’amnaa Anta Mawlaanaa Fans’urnaa A’lal Qawmil Kaafireen",
                    R.string.text_and_strengthen_your_imaan_1
                )
            )
            //for forgiveness
            listAzkar.add(
                AzkarEntity(
                    123,
                    8,
                    33,
                    "رَبِّ اِنِّىْٓ اَعُوْذُ بِكَ اَنْ اَسْـــَٔـلَكَ مَا لَيْسَ لِيْ بِهٖ عِلْمٌ ۭ وَاِلَّا تَغْفِرْ لِيْ وَتَرْحَمْنِيْٓ اَكُنْ مِّنَ الْخٰسِرِيْنَ",
                    "Rabbi Inneee A-o’od’u Bika An As-alaka Maa Laysa Lee Bihee I’lm Wa Illaa Taghfirlee Wa Tarh’amneee Akum Minal Khaasireen",
                    R.string.text_forgiveness_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    124,
                    8,
                    33,
                    "رَبِّ اغْفِرْ لِي رَبِّ اغْفِرْ لِي",
                    "Rabbighfir lee, Rabbighfir lee",
                    R.string.text_forgiveness_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    125,
                    8,
                    33,
                    "اللَّهُمَّ اغْفِرْ لِي، وَارْحَمْنِي، وَاهْدِنِي، وَاجْبُرْنِي، وَعَافِنِي، وَارْزُقْنِي، وَارْفَعْنِي",
                    "Allaahum-maghfir lee, warhamnee, wahdinee, wajburnee, wa 'aafinee, warzuqnee, warfa'nee",
                    R.string.text_forgiveness_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    126,
                    8,
                    33,
                    "اللَّهُمَّ اغْفِرْ لِي ذَنْبِي كُلَّهُ، دِقَّهُ وَجِلَّهُ، وَأَوَّلَهُ وَآخِرَهُ وَعَلَانِيَتَهُ وَسِرَّهُ",
                    "Allaahum-maghfir lee thanbee kullahu, diqqahu wa jillahu, wa 'awwalahu wa 'aakhirahu wa 'alaaniyata hu wa sirrahu",
                    R.string.text_forgiveness_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    127,
                    8,
                    33,
                    "اللَّهُمَّ إِنِّي أَعُوذُ بِرِضَاكَ مِنْ سَخَطِكَ، وَبِمُعَافَاتِكَ مَنْ عُقُوبَتِكَ، وَاَعُوذُ بِكَ مِنْكَ، لَا أُحصِي ثَنَاءً عَلَيْكَ أَنْتَ كَمَا أَثْنَيْتَ عَلَى نَفْسِكَ",
                    "Allaahumma 'innee 'a'oothu biridhaaka min sakhatika, wa bimu'aafaatika min 'uqoobatika wa 'a'oothu bika minka, laa 'uhsee thanaa'an 'alayka 'Anta kamaa 'athnayta 'alaa nafsika",
                    R.string.text_forgiveness_5
                )
            )
            listAzkar.add(
                AzkarEntity(
                    128,
                    8,
                    33,
                    "أَسْتَغْفِرُ اللهَ , أَسْتَغْفِرُ اللهَ , أَسْتَغْفِرُ اللهَ وَأَتُوبُ إِلَيْهِ",
                    "Astaghfirullaah, Astaghfirullaah, Astaghfirullaaha wa 'atoobu 'ilayhi",
                    R.string.text_forgiveness_6
                )
            )
            listAzkar.add(
                AzkarEntity(
                    129,
                    8,
                    33,
                    "اللَّهُمَّ إِنِّي ظَلَمْتُ نَفْسِي ظُلْماً كَثِيراً، وَلَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ، فَاغْفِرْ لِي مَغْفِرَةً مِنْ عِنْدِكَ وَارْحَمْنِي إِنَّكَ أَنْتَ الْغَفُورُ الرَّحِيمُ",
                    "Allaahumma 'innee dhalamtu nafsee dhulman katheeran, wa laa yaghfiruth-thunooba 'illaa 'Anta, faghfir lee maghfiratan min 'indika warhamnee 'innaka 'Antal-Ghafoorur-Raheem",
                    R.string.text_forgiveness_7
                )
            )
            listAzkar.add(
                AzkarEntity(
                    130,
                    8,
                    33,
                    "اللَّهُمَّ أَنْتَ رَبِّي لَّا إِلَهَ إِلَّا أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ، وَأَبُوءُ بِذَنْبِي فَاغْفِر لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ.",
                    "Allahumma anta rabbee la ilaha illa ant, khalaqtanee wa-ana AAabduk, wa-ana AAala AAahdika wawaAAdika mas-tataAAt, aAAoothu bika min sharri ma sanaAAt, aboo-o laka biniAAmatika AAalay, wa-aboo-o bithanbee, faghfir lee fa-innahu la yaghfiruth-thunooba illa ant.",
                    R.string.text_forgiveness_8
                )
            )
            listAzkar.add(
                AzkarEntity(
                    131,
                    8,
                    33,
                    "اللّهُـمَّ إِنَّـي أَسْـأَلُـكَ بِرَحْمَـتِكَ الّتي وَسِـعَت كُلَّ شيء، أَنْ تَغْـفِرَ لِي",
                    "Allaahumma 'innee 'as'aluka birahmatikal-latee wasi'at kulla shay'in 'an taghfira lee",
                    R.string.text_forgiveness_9
                )
            )
            listAzkar.add(
                AzkarEntity(
                    132,
                    8,
                    33,
                    "رَبِّ اغْفِـرْ لي، وَتُبْ عَلَـيَّ، إِنَّكَ أَنْـتَ التَّـوّابُ الغَـفُورُ",
                    "Rabbighfir lee wa tub 'alayya 'innaka 'Antat-Tawwaabul-Ghafoor",
                    R.string.text_forgiveness_10
                )
            )
            //for forgiveness and allah’s mercy
            listAzkar.add(
                AzkarEntity(
                    133,
                    8,
                    34,
                    "أَنْتَ وَلِيُّنَا فَاغْفِرْ لَنَا وَارْحَمْنَا ۖ وَأَنْتَ خَيْرُ الْغَافِرِينَ وَاكْتُبْ لَنَا فِي هَٰذِهِ الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ إِنَّا هُدْنَا إِلَيْكَ",
                    "Anta waliyyuna faghfirlana warhamna waanta khayru alghafireen. Waktub lana fee hathihi ddunya hasanatan wafee al-akhiratiinna hudna ilayk",
                    R.string.text_allah_mercy_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    134,
                    8,
                    34,
                    "رَّبِّ اغْفِرْ وَارْحَمْ وَاَنْتَ خَيْرُ الرّٰحِمِيْنَ",
                    "Rabbighfir Warh’am Wa Anta Khayrur Raah’imeen",
                    R.string.text_allah_mercy_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    135,
                    8,
                    34,
                    "رَبَّنَا ظَلَمْنَا أَنفُسَنَا وَإِن لَّمْ تَغْفِرْ لَنَا وَتَرْحَمْنَا لَنَكُونَنَّ مِنَ الْخَاسِرِينَ",
                    "Rabbana zalamna anfusina wa il lam taghfir lana wa tarhamna lana kunan minal-khasireen",
                    R.string.text_allah_mercy_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    136,
                    8,
                    34,
                    "رَبَّنَا آتِنَا مِن لَّدُنكَ رَحْمَةً وَهَيِّئْ لَنَا مِنْ أَمْرِنَا رَشَدًا",
                    "Rabbana 'atina mil-ladunka Rahmataw wa hayyi lana min amrina rashada",
                    R.string.text_allah_mercy_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    137,
                    8,
                    34,
                    "رَبَّنَآ اٰمَنَّا فَاغْفِرْ لَنَا وَارْحَمْنَا وَاَنْتَ خَيْرُ الرّٰحِمِيْنَ",
                    "Rabbanaa Aamannaa Faghfir Lanaa Warh’amnaa Wa Anta Khayrur Raah’imeen",
                    R.string.text_allah_mercy_5
                )
            )
            //seek forgiveness and protection from hell
            listAzkar.add(
                AzkarEntity(
                    138,
                    8,
                    35,
                    "رَبَّنَا وَسِعْتَ كُلَّ شَيْءٍ رَّحْمَةً وَعِلْمًا فَاغْفِرْ لِلَّذِينَ تَابُوا وَاتَّبَعُوا سَبِيلَكَ وَقِهِمْ عَذَابَ الْجَحِيمِ",
                    "Rabbana wasi'ta kulla sha'ir Rahmatanw wa 'ilman faghfir lilladhina tabu wattaba'u sabilaka waqihim 'adhabal-Jahiim",
                    R.string.text_from_hell_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    139,
                    8,
                    35,
                    "رَبَّنَآ اِنَّنَآ اٰمَنَّا فَاغْفِرْ لَنَا ذُنُوْبَنَا وَقِنَا عَذَابَ النَّارِ",
                    "Rabbana innana amanna faghfir lana dhunuubana wa qinna 'adhaban-Naar",
                    R.string.text_from_hell_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    140,
                    8,
                    35,
                    "رَبَّنَا مَا خَلَقْتَ هٰذَا بَاطِلًا ۚ سُبْحٰنَكَ فَقِنَا عَذَابَ النَّارِ. رَبَّنَآ اِنَّكَ مَنْ تُدْخِلِ النَّارَ فَقَدْ اَخْزَيْتَهٗ ۭ وَمَا لِلظّٰلِمِيْنَ مِنْ اَنْصَارٍ. رَبَّنَآ اِنَّنَا سَمِعْنَا مُنَادِيًا يُّنَادِيْ لِلْاِيْمَانِ اَنْ اٰمِنُوْا بِرَبِّكُمْ فَاٰمَنَّا ۚ رَبَّنَا فَاغْفِرْ لَنَا ذُنُوْبَنَا وَكَفِّرْ عَنَّا سَيِّاٰتِنَا وَتَوَفَّنَا مَعَ الْاَبْرَارِ. رَبَّنَا وَاٰتِنَا مَا وَعَدْتَّنَا عَلٰي رُسُلِكَ وَلَا تُخْزِنَا يَوْمَ الْقِيٰمَةِ ۭاِنَّكَ لَا تُخْلِفُ الْمِيْعَادَ.",
                    "Rabbana ma khalaqta hadha batila Subhanaka faqina 'adhaban-Naar. Rabbana innaka man tudkhilin nara faqad akhzaytah wa ma liDh-dhalimeena min ansar. Rabbanaa Innanaa Sami’-Naa Munaadiyay Yunaadee Lil – Eemaani An Aaminoo Birabbikum Fa- Aamannaa Rabbanaa Faghfir Lanaa D’unoobanaa Wa Kaffir A’nnaaa Sayyi- Aatinaa Wa Tawaffanaa Ma-A’l Abraaar. Rabbanaa Wa Aatinaa Maa Wa-A’ttanaa A’laa Rusulika Wa Laa Tukhzinaa Yawal Qiyaamah Innak Laa Tukhliful Mee’a’ad.",
                    R.string.text_from_hell_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    141,
                    8,
                    35,
                    "رَبَّنَا لَا تُؤَاخِذْنَآ اِنْ نَّسِيْنَآ اَوْ اَخْطَاْنَا ۚ رَبَّنَا وَلَا تَحْمِلْ عَلَيْنَآ اِصْرًا كَمَا حَمَلْتَهٗ عَلَي الَّذِيْنَ مِنْ قَبْلِنَا ۚ رَبَّنَا وَلَا تُحَمِّلْنَا مَا لَا طَاقَةَ لَنَا بِهٖ ۚ وَاعْفُ عَنَّا ۪ وَاغْفِرْ لَنَا ۪ وَارْحَمْنَا ۪ اَنْتَ مَوْلٰىنَا فَانْــصُرْنَا عَلَي الْقَوْمِ الْكٰفِرِيْنَ",
                    "Rabbanaa Laa Too-Akhid’naaa In-Naseenaa Aw Akht’aanaa Rabbanaa wa Laa Tah’mil A’laynaaa Is’ran Kamaa Ha’maltahoo A’lal Lad’eena min Qablinaa Rabbanaa wa Laa Tuh’ammilnaa Maa Laa T’aaqata Lanaa Bih Wa’-Fu A’nnaa Waghfirlanaa Warh’amnaa Anta Mawlaanaa Fans’urnaa A’lal Qawmil Kaafireen",
                    R.string.text_from_hell_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    142,
                    8,
                    35,
                    "رَبَّنَا أَتْمِمْ لَنَا نُورَنَا وَاغْفِرْ لَنَا إِنَّكَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
                    "Rabbana atmim lana nurana waighfir lana innaka 'ala kulli shay-in qadir",
                    R.string.text_from_hell_5
                )
            )
            //for forgiveness for your parent and all muslims
            listAzkar.add(
                AzkarEntity(
                    143,
                    8,
                    36,
                    "رَبَّنَا اغْفِرْ لِي وَلِوَالِدَيَّ وَلِلْمُؤْمِنِينَ يَوْمَ يَقُومُ الْحِسَابُ",
                    "Rabbana ghfir li wa li wallidayya wa lil Mu'mineena yawma yaqumul hisaab",
                    R.string.text_all_muslims_1
                )
            )
            //forgiveness for your siblings
            listAzkar.add(
                AzkarEntity(
                    144,
                    8,
                    37,
                    "رَبِّ اغْفِرْ لِي وَلِأَخِي وَأَدْخِلْنَا فِي رَحْمَتِكَ ۖ وَأَنْتَ أَرْحَمُ الرَّاحِمِينَ",
                    "Rabbi ighfir lee wali akhee wa adkhilna fee rahmatika wa anta arhamur rahimeen",
                    R.string.text_siblings_1
                )
            )
            //For asking forgiveness for yourself and anyone who enters your house
            listAzkar.add(
                AzkarEntity(
                    145,
                    8,
                    38,
                    "رَّبِّ اغْفِرْ لِي وَلِوَالِدَيَّ وَلِمَن دَخَلَ بَيْتِيَ مُؤْمِنًا وَلِلْمُؤْمِنِينَ وَالْمُؤْمِنَاتِ وَلَا تَزِدِ الظَّالِمِينَ إِلَّا تَبَارًا",
                    "Rabbi ighfir lee waliwalidayyawaliman dakhala baytiya mu'minan walil mu'mineena wal mu'minati wala tazidi aththalimeena illatabaran",
                    R.string.text_your_house_1
                )
            )
            //for repentance
            listAzkar.add(
                AzkarEntity(
                    146,
                    8,
                    39,
                    "أَسْتَغْفِرُ اللهَ الَّذِي لاَ إِلَهَ إلاَّ هُوَ الحَيُّ القَيّوُمُ وأَتُوبُ إِلَيهِ",
                    "Astaghfirullaah alladhee laa ilaaha illaa huwal hayyul qayyoomu wa atoobu ilayhi",
                    R.string.text_repentance_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    147,
                    8,
                    39,
                    "ذَٰلِكُمُ اللَّهُ رَبِّي عَلَيْهِ تَوَكَّلْتُ وَإِلَيْهِ أُنِيبُ",
                    "Dhalikumu Allahu rabbee alayhi tawakkaltu wa-ilayhi uneeb",
                    R.string.text_repentance_2
                )
            )
            //strengthen your imaan
            listAzkar.add(
                AzkarEntity(
                    148,
                    8,
                    40,
                    "رَضِيتُ باللَّهِ رَبًّا، وَبِالْإِسْلَامِ دِيناً، وَبِمُحَمَّدٍ صَلَى اللَّهُ عَلِيهِ وَسَلَّمَ نَبِيَّاً. (ثلاثاً)",
                    "Radeetu billahi rabban wabil-islami deenan wabiMuhammadin peace be upon to him nabiyya.",
                    R.string.text_strengthen_your_imaan_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    149,
                    8,
                    40,
                    "َعُوْذُ بِاللهِ",
                    "`A'udhu billah",
                    R.string.text_strengthen_your_imaan_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    150,
                    8,
                    40,
                    "آمَنْـتُ بِاللهِ وَرُسُـلِه",
                    "Aamantu billaahi wa Rusulihi",
                    R.string.text_strengthen_your_imaan_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    151,
                    8,
                    40,
                    "رَبَّنَا لَا تُزِغْ قُلُوْبَنَا بَعْدَ اِذْ ھَدَيْتَنَا وَھَبْ لَنَا مِنْ لَّدُنْكَ رَحْمَةً ۚ اِنَّكَ اَنْتَ الْوَھَّابُ",
                    "Rabbanaa Laa Tuzigh Quloobanaa Ba’-Da Id’hadaytanaa Wa Hab Lanaa Mil Ladunka Rah’mah Innaka Antal Wahaab",
                    R.string.text_strengthen_your_imaan_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    152,
                    8,
                    40,
                    "رَبَّنَا اغْفِرْ لَنَا ذُنُوْبَنَا وَ اِسْرَافَنَا فِيْٓ اَمْرِنَا وَثَبِّتْ اَقْدَامَنَا وَانْصُرْنَا عَلَي الْقَوْمِ الْكٰفِرِيْنَ",
                    "Rabbanaghfir lanaa d’unoobanaa wa israafanaa fee amrinaa wa thabbit aqdaamanaa wan s’urnaa a’lal qawmil kaafireen",
                    R.string.text_strengthen_your_imaan_5
                )
            )
            //seek allah’s satisfaction
            listAzkar.add(
                AzkarEntity(
                    153,
                    8,
                    41,
                    "رَبَّنَا تَقَبَّلْ مِنَّا إِنَّكَ أَنْتَ السَّمِيعُ العَلِيمُ",
                    "Rabbana taqabbal minna innaka antas Sameeaul Aleem",
                    R.string.text_allah_satisfaction_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    154,
                    8,
                    41,
                    "رَبَّنَا وَاجْعَلْنَا مُسْلِمَيْنِ لَكَ وَمِن ذُرِّيَّتِنَا أُمَّةً مُّسْلِمَةً لَّكَ وَأَرِنَا مَنَاسِكَنَا وَتُبْ عَلَيْنَآ إِنَّكَ أَنتَ التَّوَّابُ الرَّحِيمُ",
                    "Rabbana wa-j'alna Muslimayni laka wa min Dhurriyatina 'Ummatan Muslimatan laka wa 'Arina Manasikana wa tub 'alayna 'innaka 'antat-Tawwabu-Raheem",
                    R.string.text_allah_satisfaction_2
                )
            )


            //Praising Allah
            //for praising and glorifying allah
            listAzkar.add(
                AzkarEntity(
                    308,
                    9,
                    42,
                    "اللَّهُمَّ مَالِكَ الْمُلْكِ تُؤْتِي الْمُلْكَ مَنْ تَشَاءُ وَتَنْزِعُ الْمُلْكَ مِمَّنْ تَشَاءُ وَتُعِزُّ مَنْ تَشَاءُ وَتُذِلُّ مَنْ تَشَاءُ ۖ بِيَدِكَ الْخَيْرُ ۖ إِنَّكَ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ تُولِجُ اللَّيْلَ فِي النَّهَارِ وَتُولِجُ النَّهَارَ فِي اللَّيْلِ ۖ وَتُخْرِجُ الْحَيَّ مِنَ الْمَيِّتِ وَتُخْرِجُ الْمَيِّتَ مِنَ الْحَيِّ ۖ وَتَرْزُقُ مَنْ تَشَاءُ بِغَيْرِ حِسَابٍ",
                    "allahumma malika almulkitu/tee almulka man tashao watanziAAu almulka mimman tashaowatuAAizzu man tashao watuthillu man tashaobiyadika alkhayru innaka AAala kulli shay-in qadeer. Tooliju allayla fee annahariwatooliju annahara fee allayli watukhriju alhayyamina almayyiti watukhriju almayyita mina alhayyi watarzuquman tashao bighayri hisab.",
                    R.string.text_praising_allah_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    309,
                    9,
                    42,
                    "فَسُبْحَانَ اللَّهِ حِينَ تُمْسُونَ وَحِينَ تُصْبِحُونَ وَلَهُ الْحَمْدُ فِي السَّمَاوَاتِ وَالْأَرْضِ وَعَشِيًّا وَحِينَ تُظْهِرُونَ يُخْرِجُ الْحَيَّ مِنَ الْمَيِّتِ وَيُخْرِجُ الْمَيِّتَ مِنَ الْحَيِّ وَيُحْيِي الْأَرْضَ بَعْدَ مَوْتِهَا ۚ وَكَذَٰلِكَ تُخْرَجُونَ",
                    "Fasubhana Allahi heenatumsoona waheena tusbihoon. Walahu alhamdu fee assamawatiwal-ardi waAAashiyyan waheena tuthhiroon. Yukhriju alhayya mina almayyitiwayukhriju almayyita mina alhayyi wayuhyee al-ardabaAAda mawtiha wakathalika tukhrajoon.",
                    R.string.text_praising_allah_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    310,
                    9,
                    42,
                    "سُبْـحَانَ اللهِ وَبِحَمْدِهِ سُبْـحَانَ اللهِ العَظِيمِ",
                    "Subhaanal-laahi wa bihamdihi, Subhaanal-laahil-'Adheem.",
                    R.string.text_praising_allah_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    311,
                    9,
                    42,
                    "سُبْحَانَ اللهِ، والحَمْدُ للهِ، وَ لاَ إِلَهَ إلاَّ اللهُ واللهُ أَكْبَرُ",
                    "Subhaanallaahi, walhamdu lillaahi, wa laa 'ilaaha 'illallaahu, wallaahu 'Akbar.",
                    R.string.text_praising_allah_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    312,
                    9,
                    42,
                    "سُبْحَانَ اللهِ",
                    "Subhaanallah",
                    R.string.text_praising_allah_5
                )
            )
            listAzkar.add(
                AzkarEntity(
                    313,
                    9,
                    42,
                    "لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللهِ",
                    "Laa hawla wa laa quwwata 'illaa billaah",
                    R.string.text_praising_allah_6
                )
            )
            listAzkar.add(
                AzkarEntity(
                    314,
                    9,
                    42,
                    "سُبْحَانَ اللهِ العَظِيمِ وبِحَمْدِهِ",
                    "Subhaanallaahil-'Adheemi wa bihamdihi.",
                    R.string.text_praising_allah_7
                )
            )
            listAzkar.add(
                AzkarEntity(
                    315,
                    9,
                    42,
                    "هُوَ اللَّهُ الَّذِي لَا إِلَٰهَ إِلَّا هُوَ ۖ عَالِمُ الْغَيْبِ وَالشَّهَادَةِ ۖ هُوَ الرَّحْمَٰنُ الرَّحِيمُ هُوَ اللَّهُ الَّذِي لَا إِلَٰهَ إِلَّا هُوَ الْمَلِكُ الْقُدُّوسُ السَّلَامُ الْمُؤْمِنُ الْمُهَيْمِنُ الْعَزِيزُ الْجَبَّارُ الْمُتَكَبِّرُ ۚ سُبْحَانَ اللَّهِ عَمَّا يُشْرِكُونَ هُوَ اللَّهُ الْخَالِقُ الْبَارِئُ الْمُصَوِّرُ ۖ لَهُ الْأَسْمَاءُ الْحُسْنَىٰ ۚ يُسَبِّحُ لَهُ مَا فِي السَّمَاوَاتِ وَالْأَرْضِ ۖ وَهُوَ الْعَزِيزُ الْحَكِيمُ",
                    "Huwa Allahu allathee lailaha illa huwa AAalimu alghaybi washshahadatihuwa arrahmanu arraheem. Huwa Allahu allathee lailaha illa huwa almaliku alquddoosu assalamualmu/minu almuhayminu alAAazeezu aljabbaru almutakabbirusubhana Allahi AAamma yushrikoon. Huwa Allahu alkhaliqu albari-oalmusawwiru lahu al-asmao alhusnayusabbihu lahu ma fee assamawatiwal-ardi wahuwa alAAazeezu alhakeem.",
                    R.string.text_praising_allah_8
                )
            )
            listAzkar.add(
                AzkarEntity(
                    316,
                    9,
                    42,
                    "مَا شَاءَ اللَّهُ لَا قُوَّةَ إِلَّا بِاللَّهِ",
                    "Ma shaa Allahu la quwwata illabillahi",
                    R.string.text_praising_allah_9
                )
            )

            //for thanking allah
            listAzkar.add(
                AzkarEntity(
                    155,
                    9,
                    43,
                    "لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدَ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ. سُبْحَانَ اللهِ، وَالْحَمْدُ للهِ، ولَا إِلَهَ إِلَّا اللهُ، وَاللهُ أَكْبَرُ، وَلَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللهِ العَلِيِّ الْعَظيِمِ، ربِّ اغْفِرلِي",
                    "Laa 'illaha 'illallahu wahdahu la shareeka lahu, lahul-mulku wa lahul-hamdu, wa Huwa 'alaa kulli shay'in Qadeer Subhaanallahi, walhamdu lillaahi, wa laa 'ilaha 'illallahu, wallaahu 'akbar, wa laa hawla wa laa Quwwata 'illaa billaahil-'Aliyyil-'Adheem, Rabbighfir lee",
                    R.string.text_thanking_allah_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    156,
                    9,
                    43,
                    "سُبْحَانَ رَبِّيَ الْعَظِيمِ",
                    "Subhaana Rabbiyal-'Adheem",
                    R.string.text_thanking_allah_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    157,
                    9,
                    43,
                    "سُبْحَانَكَ اللَّهُمَّ رَبَّنَا وَبِحَمْدِكَ اللَّهُمَّ اغْفِرْ لِي",
                    "Subhaanaka Allaahumma Rabbanaa wa bihamdika Allaahum-maghfir lee",
                    R.string.text_thanking_allah_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    158,
                    9,
                    43,
                    "سُبُّوحٌ، قُدُوسٌ، رَبُّ الْمَلَائِكَةِ وَالرُّوحِ",
                    "Subboohun, Quddoosun, Rabbul-malaa'ikati warrooh",
                    R.string.text_thanking_allah_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    159,
                    9,
                    43,
                    "سُبْحَانَ ذِي الْجَبَرُوتِ، وَالْمَلَكُوتِ، وَالْكِبْرِيَاءِ، وَالْعَظَمَةِ",
                    "Subhaana thil-jabarooti, walmalakooti, walkibriyaa'i, wal'adhamati",
                    R.string.text_thanking_allah_5
                )
            )
            listAzkar.add(
                AzkarEntity(
                    160,
                    9,
                    43,
                    "لَا إِلَهَ إِلَّا اللهُ وَحَدْهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ يُحْيِي وَيُمِيتُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
                    "Laa 'ilaaha 'illallaahu wahdahu laa shareeka lahu, lahul-mulku wa lahul-hamdu yuhyee wa yumeetu wa Huwa 'alaa kulli shay'in Qadeer",
                    R.string.text_thanking_allah_6
                )
            )
            listAzkar.add(
                AzkarEntity(
                    161,
                    9,
                    43,
                    "لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ، لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللهِ، لَا إِلَهَ إِلَّا اللهُ، وَلَا نَعْبُدُ إِلَّا إِيَّاهُ، لَه النِّعْمَةُ وَلَهُ الْفَضْلُ وَلَهُ الثَّنَاءُ الْحَسَنُ، لَا إِلَهَ إِلَّا اللهُ مُخْلِصِينَ لَهُ الدِّينَ وَلَوْ كَرِهَ الْكَافِرُونَ",
                    "Laa 'ilaaha 'illallaahu wahdahu laa shareeka lahu, lahul-mulku, wa lahul-hamdu wa Huwa 'alaa kulli shay 'in Qadeer. Laa hawla wa laa quwwata 'illaa billaahi, laa 'ilaaha 'illallaahu, wa laa na'budu 'illaa 'iyyaahu, lahun-ni'matu wa lahul-fadhlu wa lahuth-thanaa'ul-hasanu, laa 'ilaaha 'illallaahu mukhliseena lahud-deena wa law karihal-kaafiroon",
                    R.string.text_thanking_allah_7
                )
            )
            listAzkar.add(
                AzkarEntity(
                    162,
                    9,
                    43,
                    "سُبْحَانَ اللهِ وَبِحَمْدِهِ عَدَدَ خَلْقِهِ، وَرِضَا نَفْسِهِ، وَزِنَةَ عَرْشِهِ وَمِدَادَ كَلِمَاتِهِ",
                    "Subhaanallaahi wa bihamdihi 'Adada khalqihi, wa ridhaa nafsihi, wa zinata 'arshihi wa midaada kalimaatihi",
                    R.string.text_thanking_allah_8
                )
            )
            listAzkar.add(
                AzkarEntity(
                    163,
                    9,
                    43,
                    "سُبْحَانَ اللهِ ، والْحَمْدُ للهِ ، وَاللهُ أَكْبَرُ",
                    "Subhaanallaahi, Walhamdu lillaahi, Wallaahu 'Akbar",
                    R.string.text_thanking_allah_9
                )
            )
            listAzkar.add(
                AzkarEntity(
                    164,
                    9,
                    43,
                    "الْحَمْدُ للهِ الَّذِي أَطْعَمَنَا وَسَقَانَا، وَكَفَانَا، وَآوَانَا، فَكَمْ مِمَّنْ لَا كَافِيَ لَهُ وَلَا مُؤْويَ",
                    "Alhamdu lillaahil-lathee 'at'amanaa wa saqaanaa, wa kafaanaa, wa 'aawaanaa, fakam mimman laa kaafiya lahu wa laa mu'wiya",
                    R.string.text_thanking_allah_10
                )
            )
            listAzkar.add(
                AzkarEntity(
                    165,
                    9,
                    43,
                    "الْحَمْـدُ للهِ حَمْـداً كَثـيراً طَيِّـباً مُبـارَكاً فيه، غَيْرَ مَكْفِيٍّ وَلا مُوَدَّعٍ وَلا مُسْتَغْـنىً عَنْـهُ رَبُّـنا",
                    "Alhamdu lillahi hamdan katheeran tayyiban mubarakan feeh, ghayra makfiyyin wala muwaddaAAin, wala mustaghnan AAanhu rabbuna",
                    R.string.text_thanking_allah_11
                )
            )
            //for being grateful to allah
            listAzkar.add(
                AzkarEntity(
                    166,
                    9,
                    44,
                    "رَبِّ أَوْزِعْنِي أَنْ أَشْكُرَ نِعْمَتَكَ الَّتِي أَنْعَمْتَ عَلَيَّ وَعَلَىٰ وَالِدَيَّ وَأَنْ أَعْمَلَ صَالِحًا تَرْضَاهُ وَأَصْلِحْ لِي فِي ذُرِّيَّتِي ۖ إِنِّي تُبْتُ إِلَيْكَ وَإِنِّي مِنَ الْمُسْلِمِينَ",
                    "Rabbi awzi'nee an ashkura ni'mataka allatee anAAamta AAalayya waAAalawalidayya waan aAAmala salihan tardahuwaaslih lee fee thurriyyatee innee tubtuilayka wa-innee mina almuslimeen",
                    R.string.text_grateful_to_allah_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    167,
                    9,
                    44,
                    "رَبِّ أَوْزِعْنِي أَنْ أَشْكُرَ نِعْمَتَكَ الَّتِي أَنْعَمْتَ عَلَيَّ وَعَلَىٰ وَالِدَيَّ وَأَنْ أَعْمَلَ صَالِحًا تَرْضَاهُ وَأَدْخِلْنِي بِرَحْمَتِكَ فِي عِبَادِكَ الصَّالِحِينَ",
                    "Rabbi awziAAnee an ashkura niAAmataka allateeanAAamta AAalayya waAAala walidayya waan aAAmala salihantardahu waadkhilnee birahmatika fee AAibadikaassaliheen",
                    R.string.text_grateful_to_allah_2
                )
            )
            //on hearing good news
            listAzkar.add(
                AzkarEntity(
                    168,
                    9,
                    45,
                    "الْحَمْدُ لِلَّهِ مَاشَاء اللَّهُ تبارک اللهُ",
                    "Alhamdhulillahi Masha Allah Thabarak 'Allah",
                    R.string.text_good_new_1
                )
            )
            //for rizq (sustenance)
            listAzkar.add(
                AzkarEntity(
                    169,
                    9,
                    46,
                    "اللَّهُمَّ إِنِّي أَسْأَلُكَ عِلْماً نَافِعاً، وَرِزْقاً طَيِّباً، وَعَمَلاً مُتَقَبَّلاً",
                    "Allaahumma 'innee 'as'aluka 'ilman naafi'an, wa rizqan tayyiban, wa 'amalan mutaqabbalan",
                    R.string.text_rizq_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    170,
                    9,
                    46,
                    "اللَّهُمَّ اغْفِرْ لِي، وَارْحَمْنِي، وَاهْدِنِي، وَاجْبُرْنِي، وَعَافِنِي، وَارْزُقْنِي، وَارْفَعْنِي",
                    "Allaahum-maghfir lee, warhamnee, wahdinee, wajburnee, wa 'aafinee, warzuqnee, warfa'nee",
                    R.string.text_rizq_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    171,
                    9,
                    46,
                    "اللَّهُمَّ إَنِّي أَسْأَلُكَ مِنْ فَضْلِكَ",
                    "Allaahumma 'innee 'as'aluka min fadhlika",
                    R.string.text_rizq_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    172,
                    9,
                    46,
                    "اللَّهُ لَطِيفٌ بِعِبَادِهِ يَرْزُقُ مَن يَشَاءُ ۖ وَهُوَ الْقَوِيُّ الْعَزِيزُ",
                    "Allahu lateefun biAAibadihiyarzuqu man yashao wahuwa alqawiyyu alAAazeez",
                    R.string.text_rizq_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    173,
                    9,
                    46,
                    "رَبَّنَا أَنزِلْ عَلَيْنَا مَآئِدَةً مِّنَ السَّمَاء تَكُونُ لَنَا عِيداً لِّأَوَّلِنَا وَآخِرِنَا وَآيَةً مِّنكَ وَارْزُقْنَا وَأَنتَ خَيْرُ الرَّازِقِينَ",
                    "Rabbana anzil 'alayna ma'idatam minas-Samai tuknu lana 'idal li-awwa-lina wa aakhirna wa ayatam-minka war-zuqna wa anta Khayrul-Raziqeen",
                    R.string.text_rizq_5
                )
            )
            //for leaving everything in the hands of allah
            listAzkar.add(
                AzkarEntity(
                    174,
                    9,
                    47,
                    "قُلِ اللَّهُمَّ فَاطِرَ السَّمَاوَاتِ وَالْأَرْضِ عَالِمَ الْغَيْبِ وَالشَّهَادَةِ أَنتَ تَحْكُمُ بَيْنَ عِبَادِكَ فِي مَا كَانُوا فِيهِ يَخْتَلِفُونَ",
                    "Quli allahumma fatira assamawatiwal-ardi AAalima alghaybi washshahadatianta tahkumu bayna AAibadika fee ma kanoofeehi yakhtalifoon",
                    R.string.text_rizq_2
                )
            )


            //Protection
            //protection and help from allah
            listAzkar.add(
                AzkarEntity(
                    175,
                    10,
                    48,
                    "اللَّهُمَّ مُنْزِلَ اْلِكتَابِ ، سَرِيْعَ الْحِسَابِ ،اِهْزِمِ الإْحْزَابَ ،اللَّهُمَّ اِهْزِمْهُمْ وَزَلْزِلْهُمْ",
                    "Allaahumma munzilal-kitaabi, saree'al-hisaabi, ihzimil-'ahzaaba, Allaahumma ihzimhum wa zalzilhum",
                    R.string.text_help_from_allah_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    176,
                    10,
                    48,
                    "قُلْ هُوَ اللهُ أَحَدٌ اللهُ الصَّمَدُ لَمْ يَلِدْ وَلَمْ يُولَدْ وَلَمْ يَكُنْ لَهُ كُفُوًا أَحَدٌ",
                    "Bismillaahir-Rahmaanir-Raheem. Qul Huwallaahu 'Ahad. Allaahus-Samad. Lam yalid wa lam yoolad. Wa lam yakun lahu kufuwan 'ahad",
                    R.string.text_help_from_allah_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    177,
                    10,
                    48,
                    "اللهُ لَا إِلَهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ لَهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ مَنْ ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلَّا بِإِذْنِهِ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ وَلَا يُحِيطُونَ بِشَيْءٍ مِنْ عِلْمِهِ إِلَّا بِمَا شَاءَ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ وَلَا يَئُودُهُ حِفْظُهُمَا وَهُوَ الْعَلِيُّ الْعَظِيمُ",
                    "Allaahu laa 'ilaaha 'illaa Huwal-Hayyul-Qayyoom, laa ta'khuthuhu sinatun wa laa nawm, lahu maa fis-samaawaati wa maafil-'ardh, man thal-lathee yashfa'u 'indahu 'illaa bi'ithnih, ya'lamu maa bayna 'aydeehim wa maa khalfahum, wa laa yuheetoona bishay'im-min 'ilmihi 'illaa bimaa shaa'a, wasi'a kursiyyuhus-samaawaati wal'ardh, wa laa ya'ooduhu hifdhuhumaa, wa Huwal-'Aliyyul- 'Adheem",
                    R.string.text_help_from_allah_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    178,
                    10,
                    48,
                    "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ مِنْ شَرِّ مَا خَلَقَ وَمِنْ شَرِّ غَاسِقٍ إِذَا وَقَبَ وَمِنْ شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ وَمِنْ شَرِّ حَاسِدٍ إِذَا حَسَدَ",
                    "Bismillaahir-Rahmaanir-Raheem. Qul 'a'oothu birabbil-falaq. Min sharri maa khalaq. Wa min sharri ghaasiqin 'ithaa waqab. Wa min sharrin-naffaathaati fil-'uqad. Wa min sharri haasidin 'ithaa hasad",
                    R.string.text_help_from_allah_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    179,
                    10,
                    48,
                    "قُلْ أَعُوذُ بِرَبِّ النَّاسِ مَلِكِ النَّاسِ إِلَهِ النَّاسِ مِنْ شَرِّ الْوَسْوَاسِ الْخَنَّاسِ الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ مِنَ الْجِنَّةِ وَالنَّاسِ",
                    "Bismillaahir-Rahmaanir-Raheem . Qul 'a'oothu birabbin-naas . Malikin-naas . 'Ilaahin-naas . Min sharril-waswaasil-khannaas. Allathee yuwaswisu fee sudoorin-naas. Minal-jinnati wannaas",
                    R.string.text_help_from_allah_5
                )
            )
            listAzkar.add(
                AzkarEntity(
                    180,
                    10,
                    48,
                    "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنْ عَذَابِ الْقَبْرِ، وَأَعُوذُ بِكَ مِنْ فِتْنَةِ الْمَسِيحِ الدَّجَّالِ، وَأَعُوذُ بِكَ مِنْ فِتْنَةِ الْمَحْيَا وَالْمَمَاتِ. اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنْ الْمَأْثَمِ وَالْمَغْرَمِ",
                    "Allaahumma 'innee 'a'oothu bika min 'athaabil-qabri, wa 'a'oothu bika min fitnatil-maseehid-dajjaali, wa 'a'oothu bika min fitnatil-mahyaa walmamaati. Allaahumma 'innee 'a'oothu bika minal-ma'thami walmaghrami",
                    R.string.text_help_from_allah_6
                )
            )
            listAzkar.add(
                AzkarEntity(
                    181,
                    10,
                    48,
                    "اللَّهُمَّ قِنِي عَذَابَكَ يَوْمَ تَبْعَثُ عِبَادَكَ",
                    "Allaahumma qinee 'athaabaka yawma tab'athu 'ibaadaka",
                    R.string.text_help_from_allah_7
                )
            )
            listAzkar.add(
                AzkarEntity(
                    182,
                    10,
                    48,
                    "اللَّهُمَّ عَالِمَ الْغَيْبِ وَالشَّهَادَةِ فَاطِرَ السَّماوَاتِ وَالْأَرْضِ، رَبَّ كُلِّ شَيْءٍ وَمَلِيكَهُ، أَشْهَدُ أَنْ لَا إِلَهَ إِلَّا أَنْتَ، أَعُوذُ بِكَ مِنْ شَرِّ نَفْسِي، وَمِنْ شَرِّ الشَّيْطَانِ وَشِرْكِهِ، وَأَنْ أَقْتَرِفَ عَلَى نَفْسِي سُوءاً، أَوْ أَجُرَّهُ إِلَى مُسْلِمٍ",
                    "Allaahumma 'Aalimal-ghaybi wash-shahaadati faatiras-samaawaati wal'ardhi, Rabba kulli shay 'in wa maleekahu, 'ash-hadu 'an laa 'ilaaha 'illaa 'Anta, 'a'oothu bika min sham nafsee, wa min sharrish-shaytaani wa shirkihi, wa 'an 'aqtarifa 'alaa nafsee soo'an, 'aw 'ajurrahu 'ilaa Muslimin",
                    R.string.text_help_from_allah_8
                )
            )
            listAzkar.add(
                AzkarEntity(
                    183,
                    10,
                    48,
                    "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي الدُّنْيَا وَالْآخِرَةِ، اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي دِينِي وَدُنْيَايَ وَأَهْللِي، وَمَالِي، اللَّهُمَّ اسْتُرْ عَوْرَاتِي، وَآمِنْ رَوْعَاتِي، اللَّهُمَّ احْفَظْنِي مِنْ بَيْنِ يَدَيَّ، وَمِنْ خَلْفِي، وَعَنْ يَمِينِي، وَعَنْ شِمَالِي، وَمِنْ فَوْقِي، وَأَعُوذُ بِعَظَمَتِكَ أَنْ أُغْتَالَ مِنْ تَحْتِي",
                    "Allaahumma 'innee 'as'alukal-'afwa wal'aafiyata fid-dunyaa wal'aakhirati, Allaahumma 'innee 'as'alukal-'afwa wal'aafiyata fee deenee wa dunyaaya wa 'ahlee, wa maalee , Allaahum-mastur 'awraatee, wa 'aamin raw'aatee, Allaahum-mahfadhnee min bayni yadayya, wa min khalfee, wa 'an yameenee, wa 'an shimaalee, wa min fawqee, wa 'a'oothu bi'adhamatika 'an 'ughtaala min tahtee",
                    R.string.text_help_from_allah_9
                )
            )
            listAzkar.add(
                AzkarEntity(
                    184,
                    10,
                    48,
                    "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ للهِ وَالْحَمْدُ للهِ، لَا إِلَهَ إِلَّا اللهُ وَحَدْهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ، ربِّ أَسْأَلُكَ خَيْرَ مَا فِي هَذَا الْيَومِ وَخَيْرَ مَا بَعْدَهُ، وَأَعُوذُ بِكَ مِنْ شَرِّ مَا فِي هَذَا الْيَومِ وَشَرِّ مَا بَعْدَهُ، رَبِّ أَعُوذُ بِكَ مِنَ الْكَسَلِ، وَسُوءِ الكِبَرِ، رَبِّ أَعُوذُ بِكَ مِنْ عَذَابٍ فِي النَّارِ وَعَذَابٍ فِي الْقَبْرِ",
                    "Asbahnaa wa 'asbahal-mulku lillaahi walhamdu lillaahi, laa 'ilaaha 'illallaahu wahdahu laa shareeka lahu, lahul-mulku wa lahul-hamdu wa Huwa 'alaa kutti shay'in Qadeer. Rabbi 'as'aluka khayra maa fee haathal-yawmi wa khayra maa ba'dahu wa 'a'oothu bika min sharri maa fee haathal-yawmi wa sharri maa ba'dahu, Rabbi 'a'oothu bika minal-kasali, wa soo'il-kibari, Rabbi 'a'oothu bika min 'athaabin fin-naari wa 'athaabin fil-qabri",
                    R.string.text_help_from_allah_10
                )
            )
            listAzkar.add(
                AzkarEntity(
                    185,
                    10,
                    48,
                    "أَنَّ اللَّهَ مَوْلَاكُمْ ۚ نِعْمَ الْمَوْلَىٰ وَنِعْمَ النَّصِيرُ",
                    "Anna Allaha mawlakum ni'mal mawla wa ni'man naseer",
                    R.string.text_help_from_allah_11
                )
            )
            //seek forgiveness and protection from hell
            listAzkar.add(
                AzkarEntity(
                    186,
                    10,
                    49,
                    "رَبَّنَا وَسِعْتَ كُلَّ شَيْءٍ رَّحْمَةً وَعِلْمًا فَاغْفِرْ لِلَّذِينَ تَابُوا وَاتَّبَعُوا سَبِيلَكَ وَقِهِمْ عَذَابَ الْجَحِيمِ",
                    "Rabbana wasi'ta kulla sha'ir Rahmatanw wa 'ilman faghfir lilladhina tabu wattaba'u sabilaka waqihim 'adhabal-Jahiim",
                    R.string.text_from_hell_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    187,
                    10,
                    49,
                    "رَبَّنَآ اِنَّنَآ اٰمَنَّا فَاغْفِرْ لَنَا ذُنُوْبَنَا وَقِنَا عَذَابَ النَّارِ",
                    "Rabbana innana amanna faghfir lana dhunuubana wa qinna 'adhaban-Naar",
                    R.string.text_from_hell_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    188,
                    10,
                    49,
                    "رَبَّنَا مَا خَلَقْتَ هٰذَا بَاطِلًا ۚ سُبْحٰنَكَ فَقِنَا عَذَابَ النَّارِ. رَبَّنَآ اِنَّكَ مَنْ تُدْخِلِ النَّارَ فَقَدْ اَخْزَيْتَهٗ ۭ وَمَا لِلظّٰلِمِيْنَ مِنْ اَنْصَارٍ. رَبَّنَآ اِنَّنَا سَمِعْنَا مُنَادِيًا يُّنَادِيْ لِلْاِيْمَانِ اَنْ اٰمِنُوْا بِرَبِّكُمْ فَاٰمَنَّا ۚ رَبَّنَا فَاغْفِرْ لَنَا ذُنُوْبَنَا وَكَفِّرْ عَنَّا سَيِّاٰتِنَا وَتَوَفَّنَا مَعَ الْاَبْرَارِ. رَبَّنَا وَاٰتِنَا مَا وَعَدْتَّنَا عَلٰي رُسُلِكَ وَلَا تُخْزِنَا يَوْمَ الْقِيٰمَةِ ۭاِنَّكَ لَا تُخْلِفُ الْمِيْعَادَ.",
                    "Rabbana ma khalaqta hadha batila Subhanaka faqina 'adhaban-Naar. Rabbana innaka man tudkhilin nara faqad akhzaytah wa ma liDh-dhalimeena min ansar. Rabbanaa Innanaa Sami’-Naa Munaadiyay Yunaadee Lil – Eemaani An Aaminoo Birabbikum Fa- Aamannaa Rabbanaa Faghfir Lanaa D’unoobanaa Wa Kaffir A’nnaaa Sayyi- Aatinaa Wa Tawaffanaa Ma-A’l Abraaar. Rabbanaa Wa Aatinaa Maa Wa-A’ttanaa A’laa Rusulika Wa Laa Tukhzinaa Yawal Qiyaamah Innak Laa Tukhliful Mee’a’ad.",
                    R.string.text_from_hell_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    189,
                    10,
                    49,
                    "رَبَّنَا لَا تُؤَاخِذْنَآ اِنْ نَّسِيْنَآ اَوْ اَخْطَاْنَا ۚ رَبَّنَا وَلَا تَحْمِلْ عَلَيْنَآ اِصْرًا كَمَا حَمَلْتَهٗ عَلَي الَّذِيْنَ مِنْ قَبْلِنَا ۚ رَبَّنَا وَلَا تُحَمِّلْنَا مَا لَا طَاقَةَ لَنَا بِهٖ ۚ وَاعْفُ عَنَّا ۪ وَاغْفِرْ لَنَا ۪ وَارْحَمْنَا ۪ اَنْتَ مَوْلٰىنَا فَانْــصُرْنَا عَلَي الْقَوْمِ الْكٰفِرِيْنَ",
                    "Rabbanaa Laa Too-Akhid’naaa In-Naseenaa Aw Akht’aanaa Rabbanaa wa Laa Tah’mil A’laynaaa Is’ran Kamaa Ha’maltahoo A’lal Lad’eena min Qablinaa Rabbanaa wa Laa Tuh’ammilnaa Maa Laa T’aaqata Lanaa Bih Wa’-Fu A’nnaa Waghfirlanaa Warh’amnaa Anta Mawlaanaa Fans’urnaa A’lal Qawmil Kaafireen",
                    R.string.text_from_hell_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    190,
                    10,
                    49,
                    "رَبَّنَا أَتْمِمْ لَنَا نُورَنَا وَاغْفِرْ لَنَا إِنَّكَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
                    "Rabbana atmim lana nurana waighfir lana innaka 'ala kulli shay-in qadir",
                    R.string.text_from_hell_5
                )
            )
            //for protection from satan/shaytan
            listAzkar.add(
                AzkarEntity(
                    191,
                    10,
                    50,
                    "وَقُلْ رَّبِّ اَعُوْذُ بِكَ مِنْ هَمَزٰتِ الشَّيٰطِيْنِ. وَاَعُوْذُ بِكَ رَبِّ اَنْ يَّحْضُرُوْنِ.",
                    "Rabbi ‘a`outhubika min hamazaatish-shayaateeni, wa ‘a`outhu bika rabbi ‘ay-yahdhuroon",
                    R.string.text_shaytan_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    192,
                    10,
                    50,
                    "أَعُوذُ بِاللَّهِ مِنَ الشَّيْطانِ الرَّجِيْمِ",
                    "A'oothu billaahi minash-Shaytaanir-rajeem",
                    R.string.text_shaytan_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    193,
                    10,
                    50,
                    "أَعُوذُ بِكَلِمَاتِ اللهِ التَّامَّاتِ مِنْ شَرِّ مَا خَلَقَ",
                    "A'oothu bikalimaatil-laahit-taammaati min sharri maa khalaqa",
                    R.string.text_shaytan_3
                )
            )
            //for protection from the evil eye
            listAzkar.add(
                AzkarEntity(
                    317,
                    10,
                    51,
                    " أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ مِنْ شَرِّ مَا خَلَقَ",
                    "'A'udhu bikalimat-illahit-tammati min sharri ma khalaqa",
                    R.string.text_evil_eye_1
                )
            )
            //to keep your family safe from transgressors
            listAzkar.add(
                AzkarEntity(
                    194,
                    10,
                    52,
                    "رَبِّ نَجِّنِي وَأَهْلِي مِمَّا يَعْمَلُونَ",
                    "Rabbi najjinee waahlee mimma ya'maloon.",
                    R.string.text_transgressors_1
                )
            )
            //for your child’s protection
            listAzkar.add(
                AzkarEntity(
                    195,
                    10,
                    53,
                    "أُعيـذُكُمـا بِكَلِـماتِ اللهِ التّـامَّة، مِنْ كُلِّ شَيْـطانٍ وَهـامَّة، وَمِنْ كُـلِّ عَـيْنٍ لامَّـة",
                    "U'eethukumaa bikalimaatil-laahit-taammati min kulli shaytaanin wa haammatin, wa min kulli 'aynin laammatin",
                    R.string.text_protection_1
                )
            )
            //for protection from oppressors
            listAzkar.add(
                AzkarEntity(
                    196,
                    10,
                    54,
                    "رَبَّنَا أَخْرِجْنَا مِنْ هَٰذِهِ الْقَرْيَةِ الظَّالِمِ أَهْلُهَا وَاجْعَلْ لَنَا مِنْ لَدُنْكَ وَلِيًّا وَاجْعَلْ لَنَا مِنْ لَدُنْكَ نَصِيرًا",
                    "rabbana akhrijna min hathihialqaryati aththalimi ahluhawajAAal lana min ladunka waliyyan wajAAallana min ladunka naseera",
                    R.string.text_oppressors_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    197,
                    10,
                    54,
                    "اللّهُـمَّ أَنْتَ عَضُـدي، وَأَنْتَ نَصـيري، بِكَ أَجـولُ وَبِكَ أَصـولُ وَبِكَ أُقـاتِل",
                    "Allaahumma 'Anta 'adhudee, wa 'Anta naseeree, bika 'ajoolu, wa bika 'asoolu, wa bika 'uqaatilu",
                    R.string.text_oppressors_2
                )
            )
            //for protection against enemies
            listAzkar.add(
                AzkarEntity(
                    318,
                    10,
                    55,
                    "اللَّهُمَّ مُنْزِلَ اْلِكتَابِ ، سَرِيْعَ الْحِسَابِ ،اِهْزِمِ الإْحْزَابَ ،اللَّهُمَّ اِهْزِمْهُمْ وَزَلْزِلْهُمَْ",
                    "Allaahumma munzilal-kitaabi, saree'al-hisaabi, ihzimil-'ahzaaba, Allaahumma ihzimhum wa zalzilhum",
                    R.string.text_against_enemies_1
                )
            )
            //for curbing fear
            listAzkar.add(
                AzkarEntity(
                    198,
                    10,
                    56,
                    "اللَّهُمَّ اكْفِنِيهِمْ بِماَ شِئْتَ",
                    "Allaahummak-fineehim bimaa shi'ta",
                    R.string.text_fear_1
                )
            )
            //for protection from the wrongdoers
            listAzkar.add(
                AzkarEntity(
                    199,
                    10,
                    57,
                    "عَلَى اللَّهِ تَوَكَّلْنَا رَبَّنَا لَا تَجْعَلْنَا فِتْنَةً لِلْقَوْمِ الظَّالِمِينَ وَنَجِّنَا بِرَحْمَتِكَ مِنَ الْقَوْمِ الْكَافِرِينَ",
                    "Alal Allahi thawakkalna rabbana la taj'alna fitnatal lil-qawmidh-Dhalimeen. Wa najjina bi-Rahmatika minal qawmil kafireen.",
                    R.string.text_wrongdoers_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    200,
                    10,
                    57,
                    "رَبَّنَا إِنَّكَ تَعْلَمُ مَا نُخْفِي وَمَا نُعْلِنُ وَمَا يَخْفَى عَلَى اللّهِ مِن شَيْءٍ فَي الأَرْضِ وَلاَ فِي السَّمَاء",
                    "Rabbana innaka ta'lamu ma nukhfi wa ma nu'lin wa ma yakhfa 'alal-lahi min shay'in fil-ardi wa la fis-Sama'",
                    R.string.text_wrongdoers_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    201,
                    10,
                    57,
                    "رَبَّنَا إِنَّنَا نَخَافُ أَن يَفْرُطَ عَلَيْنَا أَوْ أَن يَطْغَى",
                    "Rabbana innana nakhafu any-yafruta 'alayna aw any-yatgha",
                    R.string.text_wrongdoers_3
                )
            )
            //for parents
            listAzkar.add(
                AzkarEntity(
                    202,
                    10,
                    58,
                    "رَبِّ اجْعَلْنِيْ مُقِيْمَ الصَّلٰوةِ وَمِنْ ذُرِّيَّتِيْ رَبَّنَا وَتَقَبَّلْ دُعَاۗءِ",
                    "Rabbij-A’lnee Muqeemas’ S’alaati Wa Min D’urrriyyatee Rabbanaa Wa Taqabbal Du-A’aa",
                    R.string.text_parents_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    203,
                    10,
                    58,
                    "رَّبِّ ارْحَمْهُمَا كَمَا رَبَّيَانِي صَغِيرًا",
                    "Rabbi irhamhuma kama rabbayani sagheera",
                    R.string.text_parents_2
                )
            )
            //for fear of committing shirk
            listAzkar.add(
                AzkarEntity(
                    204,
                    10,
                    59,
                    "اللّهُـمَّ إِنّـي أَعـوذُبِكَ أَنْ أُشْـرِكَ بِكَ وَأَنا أَعْـلَمْ، وَأَسْتَـغْفِرُكَ لِما لا أَعْـلَم",
                    "Allaahumma 'innee 'a'oothu bika 'an 'ushrika bika wa 'anaa 'a'lamu, wa 'astaghfiruka limaa laa 'a'lamu",
                    R.string.text_shirk_1
                )
            )
            //for this wolrd and the aakhira
            listAzkar.add(
                AzkarEntity(
                    205,
                    10,
                    60,
                    "رَبَّنَآ اٰتِنَا فِي الدُّنْيَا حَسَنَةً وَّفِي الْاٰخِرَةِ حَسَـنَةً وَّقِنَا عَذَابَ النَّارِ",
                    "Rabbanaaa Aatinaa Fiddunyaa H’asanata Wa Fil Aakhirati H’asanata Wa Qinaa A’d’aaban Naar",
                    R.string.text_aakhira_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    206,
                    10,
                    60,
                    "رَبَّنَا إِنَّكَ جَامِعُ النَّاسِ لِيَوْمٍ لاَّ رَيْبَ فِيهِ إِنَّ اللّهَ لاَ يُخْلِفُ الْمِيعَادَ",
                    "Rabbana innaka jami'unnasi li-Yawmil la rayba fi innAllaha la yukhliful mi'aad",
                    R.string.text_aakhira_2
                )
            )
            //to be a pious muslim
            listAzkar.add(
                AzkarEntity(
                    207,
                    10,
                    61,
                    "رَبَّنَا لاَ تَجْعَلْنَا مَعَ الْقَوْمِ الظَّالِمِينَ",
                    "Rabbanaa Laa Taj-a’lnaa Ma – A’l Qawmiz’ Z’aalimeen",
                    R.string.text_pious_muslim_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    208,
                    10,
                    61,
                    "رَبِّ فَلَا تَجْعَلْنِي فِي الْقَوْمِ الظَّالِمِينَ",
                    "Rabbi fala tajAAalnee fee alqawmi aththalimeen",
                    R.string.text_pious_muslim_2
                )
            )
            //for righteous company
            listAzkar.add(
                AzkarEntity(
                    209,
                    10,
                    62,
                    "فَاطِرَ السَّمَاوَاتِ وَالْأَرْضِ أَنْتَ وَلِيِّي فِي الدُّنْيَا وَالْآخِرَةِ ۖ تَوَفَّنِي مُسْلِمًا وَأَلْحِقْنِي بِالصَّالِحِينَ",
                    "fatira assamawatiwal-ardi anta waliyyee fee addunyawal-akhirati tawaffanee musliman waalhiqneebissaliheen",
                    R.string.text_righteous_company_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    210,
                    10,
                    62,
                    "رَبَّنَا آمَنَّا فَاكْتُبْنَا مَعَ الشَّاهِدِينَ",
                    "Rabbana aamana faktubna ma' ash-shahideen",
                    R.string.text_righteous_company_2
                )
            )
            //protection from natural disasters & calamities
            listAzkar.add(
                AzkarEntity(
                    319,
                    10,
                    63,
                    " بِسـمِ اللهِ الذي لا يَضُـرُّ مَعَ اسمِـهِ شَيءٌ في الأرْضِ وَلا في السّمـاءِ وَهـوَ السّمـيعُ العَلـيم . (ثلاث مرات) َْ",
                    "Bismil-lahil-lathee la yadurru ma‘as-mihi shay-on fil-ardi wala fis-sama-i wahuwas-samee‘ul-‘aleem",
                    R.string.text_calamities_1
                )
            )

            //Family
            //for parent
            listAzkar.add(
                AzkarEntity(
                    211,
                    11,
                    64,
                    "رَبِّ اجْعَلْنِيْ مُقِيْمَ الصَّلٰوةِ وَمِنْ ذُرِّيَّتِيْ رَبَّنَا وَتَقَبَّلْ دُعَاۗءِ",
                    "Rabbij-A’lnee Muqeemas’ S’alaati Wa Min D’urrriyyatee Rabbanaa Wa Taqabbal Du-A’aa",
                    R.string.text_parents_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    212,
                    11,
                    64,
                    "رَّبِّ ارْحَمْهُمَا كَمَا رَبَّيَانِي صَغِيرًا",
                    "Rabbi irhamhuma kama rabbayani sagheera",
                    R.string.text_parents_2
                )
            )
            //for forgiveness for your parents and all muslims
            listAzkar.add(
                AzkarEntity(
                    213,
                    11,
                    65,
                    "رَبَّنَا اغْفِرْ لِي وَلِوَالِدَيَّ وَلِلْمُؤْمِنِينَ يَوْمَ يَقُومُ الْحِسَابُ",
                    "Rabbana ghfir li wa li wallidayya wa lil Mu'mineena yawma yaqumul hisaab",
                    R.string.text_and_all_muslims_1
                )
            )
            //forgiviness for you siblings
            listAzkar.add(
                AzkarEntity(
                    320,
                    11,
                    66,
                    "رَبِّ اغْفِرْ لِي وَلِأَخِي وَأَدْخِلْنَا فِي رَحْمَتِكَ ۖ وَأَنْتَ أَرْحَمُ الرَّاحِمِينََْ",
                    "Rabbi ighfir lee wali akhee wa adkhilna fee rahmatika wa anta arhamur rahimeen",
                    R.string.text_you_siblings_1
                )
            )

            //for a blissfull family
            listAzkar.add(
                AzkarEntity(
                    214,
                    11,
                    67,
                    "رَبَّنَا هَبْ لَنَا مِنْ أَزْوَاجِنَا وَذُرِّيَّاتِنَا قُرَّةَ أَعْيُنٍ وَاجْعَلْنَا لِلْمُتَّقِينَ إِمَامًا",
                    "Rabbana Hablana min azwaajina wadhurriy-yatina, qurrata 'ayioni wa-jalna lil-muttaqeena Imaama",
                    R.string.text_family_1
                )
            )
            //asking allah to grant you a child
            listAzkar.add(
                AzkarEntity(
                    215,
                    11,
                    68,
                    "رَبِّ ھَبْ لِيْ مِنْ لَّدُنْكَ ذُرِّيَّةً طَيِّبَةً ۚ اِنَّكَ سَمِيْعُ الدُّعَاۗءِ",
                    "Rabbi Hab Lee Mil Ladunka D’urriyyatan T’ayyibah Innaka Samee-u’d Du’aa",
                    R.string.text_a_child_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    216,
                    11,
                    68,
                    "رَبِّ لَا تَذَرْنِيْ فَرْدًا وَّاَنْتَ خَيْرُ الْوٰرِثِيْنَ",
                    "Rabbi Laa Tad’arnee Fardaw wa Anta Khayrul Waaritheen",
                    R.string.text_a_child_2
                )
            )
            //for your child’s protection
            listAzkar.add(
                AzkarEntity(
                    217,
                    11,
                    69,
                    "أُعيـذُكُمـا بِكَلِـماتِ اللهِ التّـامَّة، مِنْ كُلِّ شَيْـطانٍ وَهـامَّة، وَمِنْ كُـلِّ عَـيْنٍ لامَّـة",
                    "U'eethukumaa bikalimaatil-laahit-taammati min kulli shaytaanin wa haammatin, wa min kulli 'aynin laammatin",
                    R.string.text_protection_1
                )
            )


            //Health / Iiness
            //for a healthy life
            listAzkar.add(
                AzkarEntity(
                    218,
                    12,
                    70,
                    "اللّهُـمَّ عافِـني في بَدَنـي ، اللّهُـمَّ عافِـني في سَمْـعي ، اللّهُـمَّ عافِـني في بَصَـري ، لا إلهَ إلاّ أَنْـتَ. اللّهُـمَّ إِنّـي أَعـوذُبِكَ مِنَ الْكُـفر ، وَالفَـقْر ، وَأَعـوذُبِكَ مِنْ عَذابِ القَـبْر ، لا إلهَ إلاّ أَنْـتَ",
                    "Allaahumma 'aafinee fee badanee, Allaahumma 'aafinee fee sam'ee, Allaahumma 'aafinee fee basaree, laa 'ilaaha 'illaa 'Anta. Allaahumma 'innee 'a'oothu bika minal-kufri, walfaqri, wa 'a'oothu bika min 'athaabil-qabri, laa 'ilaaha 'illaa 'Anta.",
                    R.string.text_healthy_life_1
                )
            )
            //when visiting the sick
            listAzkar.add(
                AzkarEntity(
                    219,
                    12,
                    71,
                    "لَا بَأْسَ طَهُورٌ إِنْ شَاءَ اللَّهُ",
                    "Laa ba'sa tahoorun 'inshaa'Allaah.",
                    R.string.text_the_sick_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    220,
                    12,
                    71,
                    "أَسْأَلُ اللَّهَ الْعَظِيمَ رَبَّ الْعَرْشِ الْعَظِيمِ أَنْ يَشْفِيَكَ",
                    "As'alullaahal-'Adheema Rabbal-'Arshil-'Adheemi 'an yashfiyaka.",
                    R.string.text_the_sick_2
                )
            )
            //to alleviate pain
            listAzkar.add(
                AzkarEntity(
                    221,
                    12,
                    72,
                    "بِسْمِ اللهِ , بِسْمِ اللهِ , بِسْمِ اللهِ أَعُوذُ باللهِ وَقُدْرَتِهِ مَنْ شَرِّ مَا أَجِدُ وَأُحَاذِرُ",
                    "Bismillaah, Bismillaah, Bismillaah 'A'oothu billaahi wa qudratihi min sharri maa 'ajidu wa 'uhaathiru",
                    R.string.text_alleviate_pain_1
                )
            )
            //during illness
            listAzkar.add(
                AzkarEntity(
                    321,
                    12,
                    73,
                    "[سورة الإخلاص] قل هو الله أحد الله الصمد لم يلد ولم يولد ولم يكن له كفوا أحد. [سورة الفلق] قل أعوذ برب الفلق من شر ما خلق ومن شر غاسق إذا وقب ومن شر النفاثات في العقد ومن شر النفاثات في العقد ومن شر النفاثات في العقد من الحاسد عندما يحسد. [سورة الإخلاص] قل أعوذ برب الناس ملك الناس إله الناس من شر الوسواس الخناس الذي يوسوس في صدور الناس من الجن والبشريةَْ",
                    "[surat al'iikhlasi] qul hu allah 'ahad allah alsamad lam yalid walam yulad walam yakun lah kufwan 'ahad. [surat alfalaqa] qul 'aeudh birabi alfalaq min shari ma khalaq wamin shari ghasiq 'iidha waqab wamin shari alnafaathat fi aleaqd wamin shari alnafaathat fi aleaqd wamin shari alnafaathat fi aleaqd min alhasid eindama yahsadu. [surat al'iikhlasi] qul 'aeudh birabi alnaas malik alnaas 'iilah alnaas min shari alwaswas alkhanaas aladhi yuaswis fi sudur alnaas min aljini walbasharia",
                    R.string.text_during_illness_1
                )
            )
            //upon seeing someone going through a trial or tribulation
            listAzkar.add(
                AzkarEntity(
                    222,
                    12,
                    74,
                    "الْحَمْـدُ للهِ الّذي عافاني مِمّا ابْتَـلاكَ بِهِ، وَفَضَّلَـني عَلى كَثيـرٍ مِمَّنْ خَلَـقَ تَفْضـيلا",
                    "Alhamdu lillaahil-lathee 'aafaanee mimmab-talaaka bihi wa fadhdhalanee 'alaa katheerin mimman khalaqa tafdheela",
                    R.string.text_tribulation_1
                )
            )
            //if you fall on hard times
            listAzkar.add(
                AzkarEntity(
                    223,
                    12,
                    75,
                    "أَنِّي مَسَّنِيَ الضُّرُّ وَأَنتَ أَرْحَمُ الرَّاحِمِينَ",
                    "Annee massaniya addurru waanta arhamuarrahimeen",
                    R.string.text_hard_times_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    224,
                    12,
                    75,
                    "لا إلهَ إلاّ اللّهُ وَاللّهُ أَكْبَـر، لا إلهَ إلاّ اللّهُ وحْـدَهُ , لا إلهَ إلاّ اللّهُ وحْـدَهُ لا شَريكَ لهُ، لا إلهَ إلاّ اللّهُ لهُ المُلكُ ولهُ الحَمْد، لا إلهَ إلاّ اللّهُ وَلا حَـوْلَ وَلا قُـوَّةَ إِلاّ بِالله.",
                    "Laa 'ilaaha 'illallaahu wallaahu 'Akbar, laa 'ilaaha 'illallaahu wahdahu, laa 'ilaaha 'illallaahu wahdahu laa shareeka lahu, laa 'ilaaha 'illallaahu lahul-mulku wa lahul-hamdu, laa 'ilaaha 'illallaahu wa laa hawla wa laa quwwata 'illaa billaah",
                    R.string.text_hard_times_2
                )
            )
            //when you are nearing your death
            listAzkar.add(
                AzkarEntity(
                    225,
                    12,
                    76,
                    "لا إلهَ إلاّ اللّه",
                    "Laa 'ilaaha 'illallaahu",
                    R.string.text_nearing_your_death_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    226,
                    12,
                    76,
                    "أَشْهَدُ أَنْ لَا إِلَهَ إلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، وَأَشْهَدُ أَنَّ مُحَمَّداً عَبْدُهُ وَرَسُولُهُ",
                    "Ash-hadu 'an laa 'ilaaha 'illallaahu wahdahu laa shareeka lahu wa 'ash-hadu 'anna Muhammadan 'abduhu wa Rasooluhu.",
                    R.string.text_nearing_your_death_2
                )
            )
            //when visiting graves
            listAzkar.add(
                AzkarEntity(
                    227,
                    12,
                    77,
                    "السَّلَامُ عَلَيْكُمُ أَهْلَ الدِّيَارِ مِنَ الْمُؤْمِنِينَ وَالْمُسْلِمِينَ وَإِنَّا إِنْ شَاءَ اللَّهُ بِكُمْ لَاحِقُونَ نَسْأَلُ اللَّهَ لَنَا وَلَكُمُ الْعَافِيَةَ",
                    "Assalaamu 'alaykum 'ahlad-diyaari, minal-mu'mineena walmuslimeena, wa 'innaa 'in shaa' Allaahu bikum laahiqoona [wa yarhamullaahul-mustaqdimeena minnaa walmusta'khireena] 'as'alullaaha lanaa wa lakumul- 'aafiyata.",
                    R.string.text_visiting_graves_1
                )
            )


            //Loss / Failure
            //when you fail or lose at something
            listAzkar.add(
                AzkarEntity(
                    228,
                    13,
                    78,
                    "قَدَّرَ اللهُ وَما شـاءَ فَعَـلَ",
                    "Qadarullaahu wa maa shaa'afa'ala",
                    R.string.text_something_1
                )
            )
            //when a loss occurs
            listAzkar.add(
                AzkarEntity(
                    229,
                    13,
                    79,
                    "إِنَّا لِلّهِ وَإِنَّـا إِلَيْهِ رَاجِعون",
                    "Inna lillahi wa inna ilayhi raajioon",
                    R.string.text_occurs_1
                )
            )
            //when stricken by tragedy
            listAzkar.add(
                AzkarEntity(
                    230,
                    13,
                    80,
                    "إِنّا للهِ وَإِنَا إِلَـيْهِ رَاجِِعُـونَ ، اللهُـمِّ اْجُـرْني فِي مُصِـيبَتي، وَاخْلُـفْ لِي خَيْـراً مِنْـها",
                    "Innaa lillaahi wa 'innaa 'ilayhi raaji'oon, Allaahumma'-jurni fee museebatee wa 'akhliflee khayran minhaa",
                    R.string.text_tragedy_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    231,
                    13,
                    80,
                    "حَسْبُـنا اللهُ وَنِعْـمَ الوَكـيل",
                    "Hasbunallaahu wa ni'amal-wakeel",
                    R.string.text_tragedy_2
                )
            )
            //upon seeing someone going through a trial or tribulation
            listAzkar.add(
                AzkarEntity(
                    232,
                    13,
                    81,
                    "الْحَمْـدُ للهِ الّذي عافاني مِمّا ابْتَـلاكَ بِهِ، وَفَضَّلَـني عَلى كَثيـرٍ مِمَّنْ خَلَـقَ تَفْضـيلا",
                    "Alhamdu lillaahil-lathee 'aafaanee mimmab-talaaka bihi wa fadhdhalanee 'alaa katheerin mimman khalaqa tafdheela",
                    R.string.text_tribulation_1
                )
            )
            //for seeking allah’s help
            listAzkar.add(
                AzkarEntity(
                    233,
                    13,
                    82,
                    "حَسْبُـنا اللهُ وَنِعْـمَ الوَكـيل",
                    "Hasbunallaahu wa ni'amal-wakeel",
                    R.string.text_allah_help_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    234,
                    13,
                    82,
                    "إِنَّمَا أَشْكُو بَثِّي وَحُزْنِي إِلَى اللَّهِ وَأَعْلَمُ مِنَ اللَّهِ مَا لَا تَعْلَمُونَ",
                    "Innama ashkoo baththee wahuzneeila Allahi waaAAlamu mina Allahi ma lataAAlamoon",
                    R.string.text_allah_help_2
                )
            )


            //Sorrow / Joy
            //at time of worry and sorry
            listAzkar.add(
                AzkarEntity(
                    235,
                    14,
                    83,
                    "اللّهُـمَّ إِنِّي أَعْوذُ بِكَ مِنَ الهَـمِّ وَ الْحُـزْنِ، والعًجْـزِ والكَسَلِ والبُخْـلِ والجُـبْنِ، وضَلْـعِ الـدَّيْنِ وغَلَبَـةِ الرِّجال",
                    "Allaahumma 'innee 'a'oothu bika minal-hammi walhazani, wal'ajzi walkasali, walbukhli waljubni, wa dhala'id-dayni wa ghalabatir-rijaal",
                    R.string.text_worry_sorry_1
                )
            )
            //when in distress
            listAzkar.add(
                AzkarEntity(
                    236,
                    14,
                    84,
                    "لَّآ اِلٰهَ اِلَّآ اَنْتَ سُبْحٰــنَكَ اِنِّىْ كُنْتُ مِنَ الظّٰلِمِيْنَ",
                    "Laa Ilaha Illaaa Anta Subh’aanaka Innee Kuntu Minaz” Z’aalimeen",
                    R.string.text_in_distress_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    237,
                    14,
                    84,
                    "اللّهُـمَّ رَحْمَتَـكَ أَرْجـوفَلا تَكِلـني إِلى نَفْـسي طَـرْفَةَ عَـيْن، وَأَصْلِـحْ لي شَأْنـي كُلَّـه لَا إِلَهَ إِلَّا أنْـت",
                    "Allaahumma rahmataka 'arjoo falaa takilnee 'ilaa nafsee tarfata 'aynin, wa 'aslih lee sha'nee kullahu, laa'ilaaha 'illaa 'Anta",
                    R.string.text_in_distress_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    238,
                    14,
                    84,
                    "لا إلَهَ إلاَّ اللَّهُ الْعَظـيمُ الْحَلِـيمْ، لا إلَهَ إلاَّ اللَّهُ رَبُّ العَـرْشِ العَظِيـمِ، لا إلَهَ إلاَّ اللَّهُ رَبُّ السَّمَـوّاتِ ورّبُّ الأَرْضِ ورَبُّ العَرْشِ الكَـريم",
                    "Laa 'ilaaha 'illallaahul-'Adheemul-Haleem, laa 'ilaaha 'illallaahu Rabbul-'Arshil-'Adheem, laa 'ilaaha 'illallaahu Rabbus-samaawaati wa Rabbul-'ardhi wa Rabbul-'Arshil-Kareem",
                    R.string.text_in_distress_3
                )
            )
            //for overcoming your weaknesses
            listAzkar.add(
                AzkarEntity(
                    239,
                    14,
                    85,
                    "اللَّهُمَّ إنِّي أعُوذُ بِكَ مِنَ الْهَمِّ وَ الْحَزَنِ وَ الْعَجْزِ وَ الْكَسَلِ وَ الْبُخْلِ وَ الْجُبْنِ وَ ضَلَعِ الدِّيْنِ وَ غَلَبَةِ الرِّجَالِ",
                    "Allahumma inni a'udhubika minal hammi wal hazan, wal 'ajzi wal kasal, wal bukhli wal jubn, wa dhala'id-dayni wa ghalabatir rijaal",
                    R.string.text_weaknesses_1
                )
            )
            //when striken by tragedy
            listAzkar.add(
                AzkarEntity(
                    240,
                    14,
                    86,
                    "إِنّا للهِ وَإِنَا إِلَـيْهِ رَاجِِعُـونَ ، اللهُـمِّ اْجُـرْني فِي مُصِـيبَتي، وَاخْلُـفْ لِي خَيْـراً مِنْـها",
                    "Innaa lillaahi wa 'innaa 'ilayhi raaji'oon, Allaahumma'-jurni fee museebatee wa 'akhliflee khayran minhaa",
                    R.string.text_by_tragedy_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    241,
                    14,
                    86,
                    "حَسْبُـنا اللهُ وَنِعْـمَ الوَكـيل",
                    "Hasbunallaahu wa ni'amal-wakeel",
                    R.string.text_by_tragedy_2
                )
            )
            //upon seeing someone going through a trial or tribulation
            listAzkar.add(
                AzkarEntity(
                    242,
                    14,
                    87,
                    "الْحَمْـدُ للهِ الّذي عافاني مِمّا ابْتَـلاكَ بِهِ، وَفَضَّلَـني عَلى كَثيـرٍ مِمَّنْ خَلَـقَ تَفْضـيلا",
                    "Alhamdu lillaahil-lathee 'aafaanee mimmab-talaaka bihi wa fadhdhalanee 'alaa katheerin mimman khalaqa tafdheela",
                    R.string.text_tribulation_1
                )
            )
            //on hearing a bad news
            listAzkar.add(
                AzkarEntity(
                    243,
                    14,
                    88,
                    "الْحَمْـدُ للهِ على كُـلِّ حَالٍ",
                    "Alhamdu lillaahi 'alaa kulli haal",
                    R.string.text_a_bad_1
                )
            )
            //if you fall on hard times
            listAzkar.add(
                AzkarEntity(
                    244,
                    14,
                    89,
                    "أَنِّي مَسَّنِيَ الضُّرُّ وَأَنتَ أَرْحَمُ الرَّاحِمِينَ",
                    "Annee massaniya addurru waanta arhamuarrahimeen",
                    R.string.text_hard_times_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    245,
                    14,
                    89,
                    "لا إلهَ إلاّ اللّهُ وَاللّهُ أَكْبَـر، لا إلهَ إلاّ اللّهُ وحْـدَهُ , لا إلهَ إلاّ اللّهُ وحْـدَهُ لا شَريكَ لهُ، لا إلهَ إلاّ اللّهُ لهُ المُلكُ ولهُ الحَمْد، لا إلهَ إلاّ اللّهُ وَلا حَـوْلَ وَلا قُـوَّةَ إِلاّ بِالله.",
                    "Laa 'ilaaha 'illallaahu wallaahu 'Akbar, laa 'ilaaha 'illallaahu wahdahu, laa 'ilaaha 'illallaahu wahdahu laa shareeka lahu, laa 'ilaaha 'illallaahu lahul-mulku wa lahul-hamdu, laa 'ilaaha 'illallaahu wa laa hawla wa laa quwwata 'illaa billaah",
                    R.string.text_hard_times_2
                )
            )
            //for patience during difficult times
            listAzkar.add(
                AzkarEntity(
                    322,
                    14,
                    90,
                    "رَبَّنَا أَفْرِغْ عَلَيْناَ صَبْراً وَتَوَفَّنَا مُسْلِمِينََْ",
                    "Rabbana afrigh 'alaina sabran wa tawaffana Muslimin",
                    R.string.text_difficult_times_1
                )
            )
            //when someone insults you
            listAzkar.add(
                AzkarEntity(
                    246,
                    14,
                    91,
                    "رَبِّ احْكُم بِالْحَقِّ ۗ وَرَبُّنَا الرَّحْمَٰنُ الْمُسْتَعَانُ عَلَىٰ مَا تَصِفُونَ",
                    "Rabbi ohkum bilhaqqi wa rabbuna arrahmanul musta'aanu alaa ma tasifoon",
                    R.string.text_insults_you_1
                )
            )
            //when feeling frightened
            listAzkar.add(
                AzkarEntity(
                    247,
                    14,
                    92,
                    "لا إلهَ إلاّ اللّه",
                    "Laa 'ilaaha 'illallaahu",
                    R.string.text_frightened_1
                )
            )
            //for protection from the wrongdoers
            listAzkar.add(
                AzkarEntity(
                    248,
                    14,
                    93,
                    "عَلَى اللَّهِ تَوَكَّلْنَا رَبَّنَا لَا تَجْعَلْنَا فِتْنَةً لِلْقَوْمِ الظَّالِمِينَ وَنَجِّنَا بِرَحْمَتِكَ مِنَ الْقَوْمِ الْكَافِرِينَ",
                    "Alal Allahi thawakkalna rabbana la taj'alna fitnatal lil-qawmidh-Dhalimeen. Wa najjina bi-Rahmatika minal qawmil kafireen.",
                    R.string.text_wrongdoers_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    249,
                    14,
                    93,
                    "رَبَّنَا إِنَّكَ تَعْلَمُ مَا نُخْفِي وَمَا نُعْلِنُ وَمَا يَخْفَى عَلَى اللّهِ مِن شَيْءٍ فَي الأَرْضِ وَلاَ فِي السَّمَاء",
                    "Rabbana innaka ta'lamu ma nukhfi wa ma nu'lin wa ma yakhfa 'alal-lahi min shay'in fil-ardi wa la fis-Sama'",
                    R.string.text_wrongdoers_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    250,
                    14,
                    93,
                    "رَبَّنَا إِنَّنَا نَخَافُ أَن يَفْرُطَ عَلَيْنَا أَوْ أَن يَطْغَى",
                    "Rabbana innana nakhafu any-yafruta 'alayna aw any-yatgha",
                    R.string.text_wrongdoers_3
                )
            )
            //for seeking allah’s help
            listAzkar.add(
                AzkarEntity(
                    251,
                    14,
                    94,
                    "حَسْبُـنا اللهُ وَنِعْـمَ الوَكـيل",
                    "Hasbunallaahu wa ni'amal-wakeel",
                    R.string.text_allah_help_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    252,
                    14,
                    94,
                    "إِنَّمَا أَشْكُو بَثِّي وَحُزْنِي إِلَى اللَّهِ وَأَعْلَمُ مِنَ اللَّهِ مَا لَا تَعْلَمُونَ",
                    "Innama ashkoo baththee wahuzneeila Allahi waaAAlamu mina Allahi ma lataAAlamoon",
                    R.string.text_allah_help_2
                )
            )
            //to trust upon allah
            listAzkar.add(
                AzkarEntity(
                    253,
                    14,
                    95,
                    "حَسْبِيَ اللّٰهُ لا إِلَـهَ إِلاَّ هُوَ عَلَيْهِ تَوَكَّلْتُ وَهُوَ رَبُّ الْعَرْشِ الْعَظِيمِ",
                    "H’asbiyallaah Laaa Ilaaha Illaa – Huw A’layhi Tawakkaltu Wa Huwa Rabbul A’rshil A’z’eem",
                    R.string.text_upon_allah_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    254,
                    14,
                    95,
                    "وَأُفَوِّضُ أَمْرِي إِلَى اللَّهِ ۚ إِنَّ اللَّهَ بَصِيرٌ بِالْعِبَادِ",
                    "wa ufawwidhu amree ila Allahi innallaha baseerun bil ibaad",
                    R.string.text_upon_allah_2
                )
            )
            //on hearing good news
            listAzkar.add(
                AzkarEntity(
                    255,
                    14,
                    96,
                    "الْحَمْدُ لِلَّهِ مَاشَاء اللَّهُ تبارک اللهُ",
                    "Alhamdhulillahi Masha Allah Thabarak 'Allah",
                    R.string.text_good_new_1
                )
            )
            //when someone praises you
            listAzkar.add(
                AzkarEntity(
                    256,
                    14,
                    97,
                    "اللَّهُمّ لاَ تُؤاَخِذْنِي ِبَما يَقُولُونُ ، وَاغْفِرْ لِي مَالا يَعْلَمُونَ وَاجْعَلْنِي خَيْراً مِمَّا َيظُنُّونَ",
                    "Allaahumma laa tu'aakhithnee bimaa yaqooloona, waghfir lee maa laa ya'lamoona [waj'alnee khayram-mimmaa yadhunnoon]",
                    R.string.text_praises_you_1
                )
            )


            //Patience
            //for patience
            listAzkar.add(
                AzkarEntity(
                    257,
                    15,
                    98,
                    "رَبَّنَا أَفْرِغْ عَلَيْنَا صَبْراً وَثَبِّتْ أَقْدَامَنَا وَانصُرْنَا عَلَى القَوْمِ الكَافِرِينَ",
                    "Rabbana afrigh 'alayna sabran wa thabbit aqdamana wansurna 'alal-qawmil-kafirin",
                    R.string.text_for_patience_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    258,
                    15,
                    98,
                    "رَبَّنَا أَفْرِغْ عَلَيْنَا صَبْرًا وَتَوَفَّنَا مُسْلِمِينَ",
                    "Rabbana afrigh 'alayna sabraw wa tawaffana Muslimeen",
                    R.string.text_for_patience_2
                )
            )
            //for patience during difficult times
            listAzkar.add(
                AzkarEntity(
                    323,
                    15,
                    99,
                    "رَبَّنَا أَفْرِغْ عَلَيْناَ صَبْراً وَتَوَفَّنَا مُسْلِمِينََْ",
                    "Rabbana afrigh 'alaina sabran wa tawaffana Muslimin",
                    R.string.text_difficult_times_1
                )
            )

            //for controlling your anger
            listAzkar.add(
                AzkarEntity(
                    259,
                    15,
                    100,
                    "أَعُوذُ بِاللَّهِ مِنَ الشَّيْطانِ الرَّجِيْمِ",
                    "A'oothu billaahi minash-Shaytaanir-rajeem",
                    R.string.text_your_anger_1
                )
            )
            //when someone insults you
            listAzkar.add(
                AzkarEntity(
                    260,
                    15,
                    101,
                    "رَبِّ احْكُم بِالْحَقِّ ۗ وَرَبُّنَا الرَّحْمَٰنُ الْمُسْتَعَانُ عَلَىٰ مَا تَصِفُونَ",
                    "Rabbi ohkum bilhaqqi wa rabbuna arrahmanul musta'aanu alaa ma tasifoon",
                    R.string.text_insults_you_1
                )
            )


            //Debt
            //settle a debt
            listAzkar.add(
                AzkarEntity(
                    261,
                    16,
                    102,
                    "اللّهُـمَّ اكْفِـني بِحَلالِـكَ عَنْ حَـرامِـك، وَأَغْنِـني بِفَضْـلِكِ عَمَّـنْ سِـواك",
                    "Allaahummak-finee bihalaalika 'an haraamika wa 'aghninee bifadhlika 'amman siwaaka",
                    R.string.text_a_debt_1
                )
            )
            //for someone who lens you money
            listAzkar.add(
                AzkarEntity(
                    262,
                    16,
                    103,
                    "بارَكَ اللهُ لَكَ في أَهْلِكَ وَمالِك، إِنَّما جَـزاءُ السَّلَفِ الْحَمْدُ والأَدَاء",
                    "Baarakallaahu laka fee 'ahlika wa maalika, 'innamaa jazaa'us-salafil-hamdu wal'adaa'",
                    R.string.text_you_money_1
                )
            )
            //for success
            listAzkar.add(
                AzkarEntity(
                    263,
                    16,
                    104,
                    "وَمَا تَوْفِيقِي إِلَّا بِاللَّهِ ۚ عَلَيْهِ تَوَكَّلْتُ وَإِلَيْهِ أُنِيبُ",
                    "Wama tawfeeqee illa billahiAAalayhi tawakkaltu wa-ilayhi oneeb",
                    R.string.text_for_success_4
                )
            )
            //for rizq (sustenance)
            listAzkar.add(
                AzkarEntity(
                    264,
                    16,
                    105,
                    "اللَّهُمَّ إِنِّي أَسْأَلُكَ عِلْماً نَافِعاً، وَرِزْقاً طَيِّباً، وَعَمَلاً مُتَقَبَّلاً",
                    "Allaahumma 'innee 'as'aluka 'ilman naafi'an, wa rizqan tayyiban, wa 'amalan mutaqabbalan",
                    R.string.text_rizq_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    265,
                    16,
                    105,
                    "اللَّهُمَّ اغْفِرْ لِي، وَارْحَمْنِي، وَاهْدِنِي، وَاجْبُرْنِي، وَعَافِنِي، وَارْزُقْنِي، وَارْفَعْنِي",
                    "Allaahum-maghfir lee, warhamnee, wahdinee, wajburnee, wa 'aafinee, warzuqnee, warfa'nee",
                    R.string.text_rizq_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    266,
                    16,
                    105,
                    "اللَّهُمَّ إَنِّي أَسْأَلُكَ مِنْ فَضْلِكَ",
                    "Allaahumma 'innee 'as'aluka min fadhlika",
                    R.string.text_rizq_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    267,
                    16,
                    105,
                    "اللَّهُ لَطِيفٌ بِعِبَادِهِ يَرْزُقُ مَن يَشَاءُ ۖ وَهُوَ الْقَوِيُّ الْعَزِيزُ",
                    "Allahu lateefun biAAibadihiyarzuqu man yashao wahuwa alqawiyyu alAAazeez",
                    R.string.text_rizq_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    268,
                    16,
                    105,
                    "رَبَّنَا أَنزِلْ عَلَيْنَا مَآئِدَةً مِّنَ السَّمَاء تَكُونُ لَنَا عِيداً لِّأَوَّلِنَا وَآخِرِنَا وَآيَةً مِّنكَ وَارْزُقْنَا وَأَنتَ خَيْرُ الرَّازِقِينَ",
                    "Rabbana anzil 'alayna ma'idatam minas-Samai tuknu lana 'idal li-awwa-lina wa aakhirna wa ayatam-minka war-zuqna wa anta Khayrul-Raziqeen",
                    R.string.text_rizq_5
                )
            )


            //During Menstruation
            //darood-e-ibraheemi
            listAzkar.add(
                AzkarEntity(
                    269,
                    17,
                    106,
                    "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ كَمَا صَلَّيْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ، إِنَّكَ حَمِيدٌ مَجِيدٌ، اللَّهُمَّ بَارِكَ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ كَمَا بَارَكْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ، إِنَّكَ حَمِيدٌ مَجِيدٌ",
                    "Allaahumma salli 'alaa Muhammadin wa 'alaa 'aali Muhammadin, kamaa sallayta 'alaa 'Ibraaheema wa 'alaa 'aali 'Ibraaheema, 'innaka Hameedun Majeed. Allaahumma baarik 'alaa Muhammadin wa 'alaa 'aali Muhammadin, kamaa baarakta 'alaa 'Ibraaheema wa 'alaa 'aali 'Ibraaheema, 'innaka Hameedun Majeed",
                    R.string.text_e_ibraheemi_1
                )
            )
            //for this world and the aakhira
            listAzkar.add(
                AzkarEntity(
                    270,
                    17,
                    107,
                    "رَبَّنَآ اٰتِنَا فِي الدُّنْيَا حَسَنَةً وَّفِي الْاٰخِرَةِ حَسَـنَةً وَّقِنَا عَذَابَ النَّارِ",
                    "Rabbanaaa Aatinaa Fiddunyaa H’asanata Wa Fil Aakhirati H’asanata Wa Qinaa A’d’aaban Naar",
                    R.string.text_the_aakhira_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    271,
                    17,
                    107,
                    "رَبَّنَا إِنَّكَ جَامِعُ النَّاسِ لِيَوْمٍ لاَّ رَيْبَ فِيهِ إِنَّ اللّهَ لاَ يُخْلِفُ الْمِيعَادَ",
                    "Rabbana innaka jami'unnasi li-Yawmil la rayba fi innAllaha la yukhliful mi'aad",
                    R.string.text_the_aakhira_2
                )
            )
            //for forgiveness and allah’s mercy
            listAzkar.add(
                AzkarEntity(
                    272,
                    17,
                    108,
                    "أَنْتَ وَلِيُّنَا فَاغْفِرْ لَنَا وَارْحَمْنَا ۖ وَأَنْتَ خَيْرُ الْغَافِرِينَ وَاكْتُبْ لَنَا فِي هَٰذِهِ الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ إِنَّا هُدْنَا إِلَيْكَ",
                    "Anta waliyyuna faghfirlana warhamna waanta khayru alghafireen. Waktub lana fee hathihi ddunya hasanatan wafee al-akhiratiinna hudna ilayk",
                    R.string.text_allah_mercy_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    273,
                    17,
                    108,
                    "رَّبِّ اغْفِرْ وَارْحَمْ وَاَنْتَ خَيْرُ الرّٰحِمِيْنَ",
                    "Rabbighfir Warh’am Wa Anta Khayrur Raah’imeen",
                    R.string.text_allah_mercy_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    274,
                    17,
                    108,
                    "رَبَّنَا ظَلَمْنَا أَنفُسَنَا وَإِن لَّمْ تَغْفِرْ لَنَا وَتَرْحَمْنَا لَنَكُونَنَّ مِنَ الْخَاسِرِينَ",
                    "Rabbana zalamna anfusina wa il lam taghfir lana wa tarhamna lana kunan minal-khasireen",
                    R.string.text_allah_mercy_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    275,
                    17,
                    108,
                    "رَبَّنَا آتِنَا مِن لَّدُنكَ رَحْمَةً وَهَيِّئْ لَنَا مِنْ أَمْرِنَا رَشَدًا",
                    "Rabbana 'atina mil-ladunka Rahmataw wa hayyi lana min amrina rashada",
                    R.string.text_allah_mercy_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    276,
                    17,
                    108,
                    "رَبَّنَآ اٰمَنَّا فَاغْفِرْ لَنَا وَارْحَمْنَا وَاَنْتَ خَيْرُ الرّٰحِمِيْنَ",
                    "Rabbanaa Aamannaa Faghfir Lanaa Warh’amnaa Wa Anta Khayrur Raah’imeen",
                    R.string.text_allah_mercy_5
                )
            )
            //strengthen your imaan
            listAzkar.add(
                AzkarEntity(
                    277,
                    17,
                    109,
                    "رَضِيتُ باللَّهِ رَبًّا، وَبِالْإِسْلَامِ دِيناً، وَبِمُحَمَّدٍ صَلَى اللَّهُ عَلِيهِ وَسَلَّمَ نَبِيَّاً. (ثلاثاً)",
                    "Radeetu billahi rabban wabil-islami deenan wabiMuhammadin peace be upon to him nabiyya.",
                    R.string.text_strengthen_your_imaan_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    278,
                    17,
                    109,
                    "َعُوْذُ بِاللهِ",
                    "`A'udhu billah",
                    R.string.text_strengthen_your_imaan_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    279,
                    17,
                    109,
                    "آمَنْـتُ بِاللهِ وَرُسُـلِه",
                    "Aamantu billaahi wa Rusulihi",
                    R.string.text_strengthen_your_imaan_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    280,
                    17,
                    109,
                    "رَبَّنَا لَا تُزِغْ قُلُوْبَنَا بَعْدَ اِذْ ھَدَيْتَنَا وَھَبْ لَنَا مِنْ لَّدُنْكَ رَحْمَةً ۚ اِنَّكَ اَنْتَ الْوَھَّابُ",
                    "Rabbanaa Laa Tuzigh Quloobanaa Ba’-Da Id’hadaytanaa Wa Hab Lanaa Mil Ladunka Rah’mah Innaka Antal Wahaab",
                    R.string.text_strengthen_your_imaan_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    281,
                    17,
                    109,
                    "رَبَّنَا اغْفِرْ لَنَا ذُنُوْبَنَا وَ اِسْرَافَنَا فِيْٓ اَمْرِنَا وَثَبِّتْ اَقْدَامَنَا وَانْصُرْنَا عَلَي الْقَوْمِ الْكٰفِرِيْنَ",
                    "Rabbanaghfir lanaa d’unoobanaa wa israafanaa fee amrinaa wa thabbit aqdaamanaa wan s’urnaa a’lal qawmil kaafireen",
                    R.string.text_strengthen_your_imaan_5
                )
            )
            //to make us practicing muslims
            listAzkar.add(
                AzkarEntity(
                    282,
                    17,
                    110,
                    "رَبَّنَآ ءَامَنَّا بِمَآ أَنزَلۡتَ وَٱتَّبَعۡنَا ٱلرَّسُولَ فَٱكۡتُبۡنَا مَعَ ٱلشَّٰهِدِينَ",
                    "Rabbana amanna bima anzalta wattaba 'nar-Rusula fak-tubna ma'ash-Shahideen",
                    R.string.text_practicing_muslims_1
                )
            )
            //for thanking allah
            listAzkar.add(
                AzkarEntity(
                    283,
                    17,
                    111,
                    "لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدَ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ. سُبْحَانَ اللهِ، وَالْحَمْدُ للهِ، ولَا إِلَهَ إِلَّا اللهُ، وَاللهُ أَكْبَرُ، وَلَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللهِ العَلِيِّ الْعَظيِمِ، ربِّ اغْفِرلِي",
                    "Laa 'illaha 'illallahu wahdahu la shareeka lahu, lahul-mulku wa lahul-hamdu, wa Huwa 'alaa kulli shay'in Qadeer Subhaanallahi, walhamdu lillaahi, wa laa 'ilaha 'illallahu, wallaahu 'akbar, wa laa hawla wa laa Quwwata 'illaa billaahil-'Aliyyil-'Adheem, Rabbighfir lee",
                    R.string.text_thanking_allah_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    284,
                    17,
                    111,
                    "سُبْحَانَ رَبِّيَ الْعَظِيمِ",
                    "Subhaana Rabbiyal-'Adheem",
                    R.string.text_thanking_allah_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    285,
                    17,
                    111,
                    "سُبْحَانَكَ اللَّهُمَّ رَبَّنَا وَبِحَمْدِكَ اللَّهُمَّ اغْفِرْ لِي",
                    "Subhaanaka Allaahumma Rabbanaa wa bihamdika Allaahum-maghfir lee",
                    R.string.text_thanking_allah_3
                )
            )
            listAzkar.add(
                AzkarEntity(
                    286,
                    17,
                    111,
                    "سُبُّوحٌ، قُدُوسٌ، رَبُّ الْمَلَائِكَةِ وَالرُّوحِ",
                    "Subboohun, Quddoosun, Rabbul-malaa'ikati warrooh",
                    R.string.text_thanking_allah_4
                )
            )
            listAzkar.add(
                AzkarEntity(
                    287,
                    17,
                    111,
                    "سُبْحَانَ ذِي الْجَبَرُوتِ، وَالْمَلَكُوتِ، وَالْكِبْرِيَاءِ، وَالْعَظَمَةِ",
                    "Subhaana thil-jabarooti, walmalakooti, walkibriyaa'i, wal'adhamati",
                    R.string.text_thanking_allah_5
                )
            )
            listAzkar.add(
                AzkarEntity(
                    288,
                    17,
                    111,
                    "لَا إِلَهَ إِلَّا اللهُ وَحَدْهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ يُحْيِي وَيُمِيتُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
                    "Laa 'ilaaha 'illallaahu wahdahu laa shareeka lahu, lahul-mulku wa lahul-hamdu yuhyee wa yumeetu wa Huwa 'alaa kulli shay'in Qadeer",
                    R.string.text_thanking_allah_6
                )
            )
            listAzkar.add(
                AzkarEntity(
                    289,
                    17,
                    111,
                    "لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ، لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللهِ، لَا إِلَهَ إِلَّا اللهُ، وَلَا نَعْبُدُ إِلَّا إِيَّاهُ، لَه النِّعْمَةُ وَلَهُ الْفَضْلُ وَلَهُ الثَّنَاءُ الْحَسَنُ، لَا إِلَهَ إِلَّا اللهُ مُخْلِصِينَ لَهُ الدِّينَ وَلَوْ كَرِهَ الْكَافِرُونَ",
                    "Laa 'ilaaha 'illallaahu wahdahu laa shareeka lahu, lahul-mulku, wa lahul-hamdu wa Huwa 'alaa kulli shay 'in Qadeer. Laa hawla wa laa quwwata 'illaa billaahi, laa 'ilaaha 'illallaahu, wa laa na'budu 'illaa 'iyyaahu, lahun-ni'matu wa lahul-fadhlu wa lahuth-thanaa'ul-hasanu, laa 'ilaaha 'illallaahu mukhliseena lahud-deena wa law karihal-kaafiroon",
                    R.string.text_thanking_allah_7
                )
            )
            listAzkar.add(
                AzkarEntity(
                    290,
                    17,
                    111,
                    "سُبْحَانَ اللهِ وَبِحَمْدِهِ عَدَدَ خَلْقِهِ، وَرِضَا نَفْسِهِ، وَزِنَةَ عَرْشِهِ وَمِدَادَ كَلِمَاتِهِ",
                    "Subhaanallaahi wa bihamdihi 'Adada khalqihi, wa ridhaa nafsihi, wa zinata 'arshihi wa midaada kalimaatihi",
                    R.string.text_thanking_allah_8
                )
            )
            listAzkar.add(
                AzkarEntity(
                    291,
                    17,
                    111,
                    "سُبْحَانَ اللهِ ، والْحَمْدُ للهِ ، وَاللهُ أَكْبَرُ",
                    "Subhaanallaahi, Walhamdu lillaahi, Wallaahu 'Akbar",
                    R.string.text_thanking_allah_9
                )
            )
            listAzkar.add(
                AzkarEntity(
                    292,
                    17,
                    111,
                    "الْحَمْدُ للهِ الَّذِي أَطْعَمَنَا وَسَقَانَا، وَكَفَانَا، وَآوَانَا، فَكَمْ مِمَّنْ لَا كَافِيَ لَهُ وَلَا مُؤْويَ",
                    "Alhamdu lillaahil-lathee 'at'amanaa wa saqaanaa, wa kafaanaa, wa 'aawaanaa, fakam mimman laa kaafiya lahu wa laa mu'wiya",
                    R.string.text_thanking_allah_10
                )
            )
            listAzkar.add(
                AzkarEntity(
                    293,
                    17,
                    111,
                    "الْحَمْـدُ للهِ حَمْـداً كَثـيراً طَيِّـباً مُبـارَكاً فيه، غَيْرَ مَكْفِيٍّ وَلا مُوَدَّعٍ وَلا مُسْتَغْـنىً عَنْـهُ رَبُّـنا",
                    "Alhamdu lillahi hamdan katheeran tayyiban mubarakan feeh, ghayra makfiyyin wala muwaddaAAin, wala mustaghnan AAanhu rabbuna",
                    R.string.text_thanking_allah_11
                )
            )
            //to be pious muslims
            listAzkar.add(
                AzkarEntity(
                    294,
                    17,
                    112,
                    "رَبَّنَا لاَ تَجْعَلْنَا مَعَ الْقَوْمِ الظَّالِمِينَ",
                    "Rabbanaa Laa Taj-a’lnaa Ma – A’l Qawmiz’ Z’aalimeen",
                    R.string.text_pious_muslims_1
                )
            )
            //for a healthy life
            listAzkar.add(
                AzkarEntity(
                    295,
                    17,
                    113,
                    "اللّهُـمَّ عافِـني في بَدَنـي ، اللّهُـمَّ عافِـني في سَمْـعي ، اللّهُـمَّ عافِـني في بَصَـري ، لا إلهَ إلاّ أَنْـتَ. اللّهُـمَّ إِنّـي أَعـوذُبِكَ مِنَ الْكُـفر ، وَالفَـقْر ، وَأَعـوذُبِكَ مِنْ عَذابِ القَـبْر ، لا إلهَ إلاّ أَنْـتَ",
                    "Allaahumma 'aafinee fee badanee, Allaahumma 'aafinee fee sam'ee, Allaahumma 'aafinee fee basaree, laa 'ilaaha 'illaa 'Anta. Allaahumma 'innee 'a'oothu bika minal-kufri, walfaqri, wa 'a'oothu bika min 'athaabil-qabri, laa 'ilaaha 'illaa 'Anta.",
                    R.string.text_healthy_life_1
                )
            )
            //to alleviate pain
            listAzkar.add(
                AzkarEntity(
                    296,
                    17,
                    114,
                    "بِسْمِ اللهِ , بِسْمِ اللهِ , بِسْمِ اللهِ أَعُوذُ باللهِ وَقُدْرَتِهِ مَنْ شَرِّ مَا أَجِدُ وَأُحَاذِرُ",
                    "Bismillaah, Bismillaah, Bismillaah 'A'oothu billaahi wa qudratihi min sharri maa 'ajidu wa 'uhaathiru",
                    R.string.text_alleviate_pain_1
                )
            )
            //if you fall on hard times
            listAzkar.add(
                AzkarEntity(
                    297,
                    17,
                    115,
                    "أَنِّي مَسَّنِيَ الضُّرُّ وَأَنتَ أَرْحَمُ الرَّاحِمِينَ",
                    "Annee massaniya addurru waanta arhamuarrahimeen",
                    R.string.text_hard_times_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    298,
                    17,
                    115,
                    "لا إلهَ إلاّ اللّهُ وَاللّهُ أَكْبَـر، لا إلهَ إلاّ اللّهُ وحْـدَهُ , لا إلهَ إلاّ اللّهُ وحْـدَهُ لا شَريكَ لهُ، لا إلهَ إلاّ اللّهُ لهُ المُلكُ ولهُ الحَمْد، لا إلهَ إلاّ اللّهُ وَلا حَـوْلَ وَلا قُـوَّةَ إِلاّ بِالله.",
                    "Laa 'ilaaha 'illallaahu wallaahu 'Akbar, laa 'ilaaha 'illallaahu wahdahu, laa 'ilaaha 'illallaahu wahdahu laa shareeka lahu, laa 'ilaaha 'illallaahu lahul-mulku wa lahul-hamdu, laa 'ilaaha 'illallaahu wa laa hawla wa laa quwwata 'illaa billaah",
                    R.string.text_hard_times_2
                )
            )
            //for seeking allah’s help
            listAzkar.add(
                AzkarEntity(
                    299,
                    17,
                    116,
                    "حَسْبُـنا اللهُ وَنِعْـمَ الوَكـيل",
                    "Hasbunallaahu wa ni'amal-wakeel",
                    R.string.text_allah_help_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    300,
                    17,
                    116,
                    "إِنَّمَا أَشْكُو بَثِّي وَحُزْنِي إِلَى اللَّهِ وَأَعْلَمُ مِنَ اللَّهِ مَا لَا تَعْلَمُونَ",
                    "Innama ashkoo baththee wahuzneeila Allahi waaAAlamu mina Allahi ma lataAAlamoon",
                    R.string.text_allah_help_2
                )
            )
            //when in distress
            listAzkar.add(
                AzkarEntity(
                    301,
                    17,
                    117,
                    "لَّآ اِلٰهَ اِلَّآ اَنْتَ سُبْحٰــنَكَ اِنِّىْ كُنْتُ مِنَ الظّٰلِمِيْنَ",
                    "Laa Ilaha Illaaa Anta Subh’aanaka Innee Kuntu Minaz” Z’aalimeen",
                    R.string.text_in_distress_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    302,
                    17,
                    117,
                    "اللّهُـمَّ رَحْمَتَـكَ أَرْجـوفَلا تَكِلـني إِلى نَفْـسي طَـرْفَةَ عَـيْن، وَأَصْلِـحْ لي شَأْنـي كُلَّـه لَا إِلَهَ إِلَّا أنْـت",
                    "Allaahumma rahmataka 'arjoo falaa takilnee 'ilaa nafsee tarfata 'aynin, wa 'aslih lee sha'nee kullahu, laa'ilaaha 'illaa 'Anta",
                    R.string.text_in_distress_2
                )
            )
            listAzkar.add(
                AzkarEntity(
                    303,
                    17,
                    117,
                    "لا إلَهَ إلاَّ اللَّهُ الْعَظـيمُ الْحَلِـيمْ، لا إلَهَ إلاَّ اللَّهُ رَبُّ العَـرْشِ العَظِيـمِ، لا إلَهَ إلاَّ اللَّهُ رَبُّ السَّمَـوّاتِ ورّبُّ الأَرْضِ ورَبُّ العَرْشِ الكَـريم",
                    "Laa 'ilaaha 'illallaahul-'Adheemul-Haleem, laa 'ilaaha 'illallaahu Rabbul-'Arshil-'Adheem, laa 'ilaaha 'illallaahu Rabbus-samaawaati wa Rabbul-'ardhi wa Rabbul-'Arshil-Kareem",
                    R.string.text_in_distress_3
                )
            )
            //for patience during difficult times
            listAzkar.add(
                AzkarEntity(
                    324,
                    17,
                    118,
                    "رَبَّنَا أَفْرِغْ عَلَيْناَ صَبْراً وَتَوَفَّنَا مُسْلِمِينََْ",
                    "Rabbana afrigh 'alaina sabran wa tawaffana Muslimin",
                    R.string.text_difficult_times_1
                )
            )

            //to trust upon allah
            listAzkar.add(
                AzkarEntity(
                    304,
                    17,
                    119,
                    "حَسْبِيَ اللّٰهُ لا إِلَـهَ إِلاَّ هُوَ عَلَيْهِ تَوَكَّلْتُ وَهُوَ رَبُّ الْعَرْشِ الْعَظِيمِ",
                    "H’asbiyallaah Laaa Ilaaha Illaa – Huw A’layhi Tawakkaltu Wa Huwa Rabbul A’rshil A’z’eem",
                    R.string.text_upon_allah_1
                )
            )
            listAzkar.add(
                AzkarEntity(
                    305,
                    17,
                    119,
                    "وَأُفَوِّضُ أَمْرِي إِلَى اللَّهِ ۚ إِنَّ اللَّهَ بَصِيرٌ بِالْعِبَادِ",
                    "wa ufawwidhu amree ila Allahi innallaha baseerun bil ibaad",
                    R.string.text_upon_allah_2
                )
            )


            listAzkar.forEach {
                insertAzkar(it)
            }
        }
    }

    private fun insertAzkar(azkarEntity: AzkarEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            azkarRepository.insertAzkar(azkarEntity)
        }
    }

}