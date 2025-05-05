package com.qibla.muslimday.app2025.ui.duas

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.database.Duas.DuasEntity
import com.qibla.muslimday.app2025.repository.DuasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuasViewModel @Inject constructor(
    private val duasRepository: DuasRepository,
    private val context: Context
) : ViewModel() {

    fun initData() {
        viewModelScope.launch {
            val listDuas = mutableListOf<DuasEntity>()
            //When you wake up
            listDuas.add(
                DuasEntity(
                    1,
                    1,
                    1,
                    "اَلْحَمْدُ لِلَّهِ الَّذِي عَافَانِي فِي جَسَدِي، وَرَدَّ عَلَيَّ رُوحِي، وَأَذِنَ لِي بِذِكْرِهِ.",
                    "Alhamdu lillahil-lathee AAafanee fee jasadee waradda AAalayya roohee wa-athina lee bithikrih.",
                    R.string.text_wake_up_1
                )
            )
            listDuas.add(
                DuasEntity(
                    2,
                    1,
                    1,
                    "لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ ، سُبْحَانَ اللهِ وَالْحَمْدُ لِلهِ ، وَلَا إِلَهَ إِلَّا اللهُ وَاللهُ أَكْـبَرُ ، وَلَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللهِ الْعَلِيِّ الْعَظِيمِ رَبِّ اغْفِرْ لِي",
                    "La ilaha illal-lahu wahdahu la shareeka lah, lahul-mulku walahul-hamd, wahuwa AAala kulli shay-in qadeer, subhanal-lah, walhamdu lillah, wala ilaha illal-lah wallahu akbar, wala hawla wala quwwata illa billahil-AAaliyyil AAatheem. Rabbigh-fir lee",
                    R.string.text_wake_up_2
                )
            )
            listDuas.add(
                DuasEntity(
                    3,
                    1,
                    1,
                    "اَلْحَمْدُ لِلَّهِ الَّذي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ.",
                    "Alhamdu lillahil-lathee ahyana baAAda ma amatana wa-ilayhin-nushoor.",
                    R.string.text_wake_up_3
                )
            )
            //Before  sleeping
            listDuas.add(
                DuasEntity(
                    4,
                    1,
                    2,
                    "بِاسْمِكَ رَبِّي وَضَعْتُ جَنْبِي، وَبِكَ أَرْفَعُهُ، فَإِنْ أَمْسَكْتَ نَفْسِي فَارْحَمْهَا، وَإِنْ أَرْسَلْتَهَا فَاحْفَظْهَا، بِمَا تَحْفَظُ بِهِ عِبَادَكَ الصَّالِحِينَ.",
                    "Bismika rabbee wadaAAtu janbee wabika arfaAAuh, fa-in amsakta nafsee farhamha, wa-in arsaltaha fahfathha bima tahfathu bihi AAibadakas-saliheen",
                    R.string.text_sleeping_1
                )
            )
            listDuas.add(
                DuasEntity(
                    5,
                    1,
                    2,
                    "اللَّهمَّ إِنَّكَ خَلَقْتَ نَفْسِي وَأَنْتَ تَوَفَّاهَا لَكَ مَمَاتُهَا وَمَحْيَاهَا، إِنْ أَحْيَيْتَهَا فَاحْفَظْهَا، وَإِنْ أَمَتَّهَا فَاغْفِرْ لَهَا. اللَّهمَّ إِنِّي أَسْأَلُكَ العَافِيَةَ.",
                    "Allahumma innaka khalaqta nafsee wa-anta tawaffaha, laka mamatuha wamahyaha in ahyaytaha fahfathha, wa-in amattaha faghfir laha. Allahumma innee as-alukal-AAafiyah.",
                    R.string.text_sleeping_2
                )
            )
            listDuas.add(
                DuasEntity(
                    6,
                    1,
                    2,
                    "اللَّهُمَّ قِنِي عَذَابَكَ يَوْمَ تَبْعَثُ عِبَادَكَ.",
                    "Allahumma qinee AAathabaka yawma tabAAathu AAibadak.",
                    R.string.text_sleeping_3
                )
            )
            listDuas.add(
                DuasEntity(
                    7,
                    1,
                    2,
                    "اللَّهُمَّ بِاسْـمِكَ أَمُوتُ وَأَحْيَا",
                    "Bismikal-lahumma amootu wa-ahya.",
                    R.string.text_sleeping_4
                )
            )
            listDuas.add(
                DuasEntity(
                    8,
                    1,
                    2,
                    "اَلْحَمْدُ للهِ الَّذِي أَطْعَمَنَا وَسَقَانَا، وَكَفَانَا، وَآوَانَا، فَكَمْ مِمَّنْ لا كَافِيَ لَه ُُ وَلا مُؤْوِي.",
                    "Alhamdu lillahil-lathee atAAamana wasaqana, wakafana, wa-awana, fakam mimman la kafiya lahu wala mu/wee.",
                    R.string.text_sleeping_5
                )
            )
            listDuas.add(
                DuasEntity(
                    9,
                    1,
                    2,
                    "اللَّهمَّ عَالِمَ الغَيْبِ وَالشَّهَادَةِ فَاطِرَ السَّمَاوَاتِ وَالأَرْضِ رَبَّ كُلِّ شَيء ٍ وَمَلِيْكَهُ، أَشْهَدُ أَنْ لا إِلَهََ إِلاَّ أَنْتَ، أَعُوْذُ بِكَ مِنْ شَرِّ نَفْسِي، وَمِنْ شَرِّ الشَّيْطَانِ وَشِرْكِهِ، وَأَنْ أَقْتَرِفَ عَلَى نَفْسِي سُوْءاً أَوْ أَجُرَّهُ~ُ إِلَى مُسْلِم.",
                    "Allahumma AAalimal-ghaybi washshahadah, fatiras-samawati wal-ard, rabba kulli shayin wamaleekah, ashhadu an la ilaha illa ant, aAAoothu bika min sharri nafsee wamin sharrish-shaytani washirkih, wa-an aqtarifa AAala nafsee soo-an aw ajurrahu ila muslim.",
                    R.string.text_sleeping_6
                )
            )
            listDuas.add(
                DuasEntity(
                    10,
                    1,
                    2,
                    "اللَّهُمَّ أَسْلَمْتُ نَفْسِي إِلَيْكَ، وَفَوَّضْتُ أَمْرِي إِلَيْكَ، وَوَجَّهْتُ وَجْهِي إِلَيْكَ، وَأَلْجَأْتُ ظَهْرِي إِلَيْكَ، رَغْبَةً وَرَهْبَةً إِلَيْكَ، لَا مَلْجَأَ وَلّا مَنْجَا مِنْكَ إِلَّا إِلَيْكَ، آمَنْتُ بِكِتَابِكَ وَبِنَبِيِّكَ الَّذِي أَرْسَلْتَ.",
                    "Allahumma aslamtu nafsee ilayk, wafawwadtu amree ilayk, wawajjahtu wajhee ilayk, wa-alja/tu thahree ilayk, raghbatan warahbatan ilayk, la maljaa wala manja minka illa ilayk, amantu bikitabikal-lathee anzalt, wabinabiyyikal-lathee arsalt.",
                    R.string.text_sleeping_7
                )
            )
            listDuas.add(
                DuasEntity(
                    11,
                    1,
                    2,
                    "اللهُ لَا إِلَهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ لَهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ مَنْ ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلَّا بِإِذْنِهِ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ وَلَا يُحِيطُونَ بِشَيْءٍ مِنْ عِلْمِهِ إِلَّا بِمَا شَاءَ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ وَلَا يَئُودُهُ حِفْظُهُمَا وَهُوَ الْعَلِيُّ الْعَظِيمُ",
                    "Allaahu laa 'ilaaha 'illaa Huwal-Hayyul-Qayyoom, laa ta'khuthuhu sinatun wa laa nawm, lahu maa fis-samaawaati wa maafil-'ardh, man thal-lathee yashfa'u 'indahu 'illaa bi'ithnih, ya'lamu maa bayna 'aydeehim wa maa khalfahum, wa laa yuheetoona bishay'im-min 'ilmihi 'illaa bimaa shaa'a, wasi'a kursiyyuhus-samaawaati wal'ardh, wa laa ya'ooduhu hifdhuhumaa, wa Huwal-'Aliyyul- 'Adheem",
                    R.string.text_sleeping_8
                )
            )
            //Toilet
            //Before entering toilet
            listDuas.add(
                DuasEntity(
                    12,
                    2,
                    3,
                    "بِاسْمِكَ رَبِّي وَضَعْتُ جَنْبِي، وَبِكَ أَرْفَعُهُ، فَإِنْ أَمْسَكْتَ نَفْسِي فَارْحَمْهَا، وَإِنْ أَرْسَلْتَهَا فَاحْفَظْهَا، بِمَا تَحْفَظُ بِهِ عِبَادَكَ الصَّالِحِينَ.",
                    "(Bismil-lah) allahumma innee aAAoothu bika minal-khubthi wal-khaba-ith",
                    R.string.text_entering_toilet_1
                )
            )
            //After leaving toilet
            listDuas.add(
                DuasEntity(
                    13,
                    2,
                    4,
                    "غُفْرَانَكَ.",
                    "Ghufranak",
                    R.string.text_leaving_toilet_1
                )
            )

            //Ablution
            //When starting ablution
            listDuas.add(
                DuasEntity(
                    14,
                    3,
                    5,
                    "بِسْمِ اللّهِ.",
                    "Bismil-lah",
                    R.string.text_starting_ablution_1
                )
            )
            //when completing ablution
            listDuas.add(
                DuasEntity(
                    15,
                    3,
                    6,
                    "أَشْهَدُ أَنْ لَا إِلَـهَ إِلاَّ اللّهُ وَحْدَهُ لَا شَريـكَ لَـهُ وَأَشْهَدُ أَنَّ مُحَمَّـداً عَبْـدُهُ وَرَسُـولُـهُ.",
                    "Ashhadu an la ilaha illal-lahu wahdahu la shareeka lah, wa-ashhadu anna Muhammadan AAabduhu warasooluh.",
                    R.string.text_completing_ablution_1
                )
            )
            listDuas.add(
                DuasEntity(
                    16,
                    3,
                    6,
                    "اَللَّهُـمَّ اجْعَلْنِـي مِنَ التَّـوَّابِينَ وَاجْعَـلْنِي مِنَ الْمُتَطَهِّـرِينَ.",
                    "Allahummaj-AAalnee minat-tawwabeena wajAAalnee minal-mutatahhireen.",
                    R.string.text_completing_ablution_2
                )
            )
            listDuas.add(
                DuasEntity(
                    17,
                    3,
                    6,
                    "سُبْحـَانَكَ اللَّهُـمَّ وَبِحَمْدِكَ، أَشْهَـدُ أَنْ لاَ إِلَهَ إِلاَّ أَنْتَ، أَسْتَغْفِرُكَ وَأَتُوبُ إِلَـيْكَ.",
                    "Subhanakal-lahumma wabihamdika ashhadu an la ilaha illa anta astaghfiruka wa-atoobu ilayk.",
                    R.string.text_completing_ablution_3
                )
            )

            //Mosque
            //When going to Mosque
            listDuas.add(
                DuasEntity(
                    18,
                    4,
                    7,
                    "اللّهُـمَّ اجْعَـلْ في قَلْبـي نورا ، وَفي لِسـاني نورا، وَاجْعَـلْ في سَمْعي نورا، وَاجْعَـلْ في بَصَري نورا، وَاجْعَـلْ مِنْ خَلْفي نورا، وَمِنْ أَمامـي نورا، وَاجْعَـلْ مِنْ فَوْقـي نورا ، وَمِن تَحْتـي نورا .اللّهُـمَّ أَعْطِنـي نورا.",
                    "Allahumma ijAAal fee qalbee noora, wafee lisanee noora, wajAAal fee samAAee noora,wajAAal fee basaree noora, wajAAal min khalfee noora, wamin amamee noora ,wajAAal min fawqee noora, wamin tahtee noora, allahumma aAAtinee noora.",
                    R.string.text_going_to_mosque_1
                )
            )
            //Upon entering Mosque
            listDuas.add(
                DuasEntity(
                    19,
                    4,
                    8,
                    "أَعوذُ بِاللّهِ العَظِيـمِ، وَبِوَجْهِـهِ الكَرِيـمِ وَسُلْطـَانِه القَدِيـمِ، مِنَ الشَّيْـطَانِ الرَّجِـيمِ، [ بِسْـمِ اللّهِ وَالصَّلَاةُ ] [وَالسَّلامُ عَلَى رَسُولِ اللّهِ]، اَللَّهُـمَّ افْتَـحْ لِي أَبْوَابَ رَحْمَتـِكَ.",
                    "aAAoothu billahil-AAatheem wabiwajhihil-kareem wasultanihil-qadeem minash-shaytanir-rajeem, [bismil-lah, wassalatu] [wassalamu AAala rasoolil-lah] , allahumma iftah lee abwaba rahmatik.",
                    R.string.text_entering_osque1
                )
            )
            //Upon Leaving Mosque
            listDuas.add(
                DuasEntity(
                    20,
                    4,
                    9,
                    "بِسْمِ اللّهِ وَالصَّلاَةُ وَالسَّلاَمُ عَلَى رَسُولِ اللّهِ، اَللَّهُـمَّ إِنِّي أَسْأَلُكَ مِنْ فَضْـلِكَ، اَللَّهُـمَّ اعْصِمْنِـي مِنَ الشَّيْـطَانِ الرَّجِـيمِ.",
                    "Bismil-lah wassalatu wassalamu AAala rasoolil-lah, allahumma innee as-aluka min fadlik, allahumma iAAsimnee minash-shaytanir-rajeem.",
                    R.string.text_leaving_mosque_1
                )
            )

            //Prayer
            //During athan
            listDuas.add(
                DuasEntity(
                    21,
                    5,
                    10,
                    "لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللهِ",
                    "Laa hawla wa laa quwwata 'illaa billaah",
                    R.string.text_during_athan_1
                )
            )
            //after listening athan
            listDuas.add(
                DuasEntity(
                    22,
                    5,
                    11,
                    "اللَّهُمَّ رَبَّ هَذِهِ الدَّعْوَةِ التَّامَّةِ، وَالصَّلَاةِ الْقَائِمَةِ، آتِ مُحَمَّداً الْوَسِيلَةَ وَالْفَضِيلَةَ، وَابْعَثْهُ مَقَاماً مَحْمُوداً الَّذِي وَعَدْتَهُ، إَنَّكَ لَا تُخْلِفُ الْمِيعَادَ.",
                    "Allaahumma Rabba haathid - da'watit - taammati wassalaatil-qaa'imati, 'aati Muhammadanil-waseelata walfadheelata, wab 'ath-hu maqaamam-mahmoodanil-lathee wa'adtahu, 'innaka laa tukhliful-mee'aad",
                    R.string.text_listening_athan_1
                )
            )
            //After Takbeer
            listDuas.add(
                DuasEntity(
                    23,
                    5,
                    12,
                    "وَجَّهْتُ وَجْهِيَ لِلَّذِي فَطَرَ السَّموَاتِ وَالْأَرْضَ حَنِيفاً وَمَا أَنَا مِنَ الْمُشْرِكِينَ، إِنَّ صَلَاتِي، وَنُسُكِي، وَمَحْيَايَ، وَمَمَاتِي للهِ رَبِّ الْعَالَمِينَ، لَا شَرِيكَ لَهُ وَبِذَلِكَ أُمِرْتُ وَأَنَا مِنَ الْمُسْلِمِينَ. اللَّهُمَّ أَنْتَ الْمَلِكُ لَا إِلَهَ إِلَّا أَنْتَ، أَنْتَ رَبِّي وَأَنَا عَبْدُكَ، ظَلَمْتُ نَفْسِي وَاعْتَرَفْتُ بَذَنْبِي فَاغْفِرْ لِي ذُنُوبِي جَمِيعاً إِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ، وَاهْدِنِي لأّحْسَنِ الْأَخْلَاقِ لَا يَهْدِي لأَحْسَنِهَا إِلَّا أَنْتَ، وَاصْرِفْ عَنِّي سَيِّئَهَا لَا يَصْرِفُ عَنِّي سَيِّئَهَا إِلَّا أَنْتَ، لَبَّيْكَ وَسَعْدَيْكَ، وَالْخَيْرُ كُلُّهُ بِيَدَيْكَ، وَالشَّرُّ لَيْسَ إِلَيْكَ، أَنَا بِكَ وَإِلَيْكَ، تَبَارَكْتَ وَتَعَالَيْتَ، أَسْتَغْفِرُكَ وَأَتُوبُ إِلَيْكَ",
                    "Wajjahtu wajhiya lillathee fataras-samawati wal-arda haneefan wama ana minal-mushrikeen, inna salatee wanusukee wamahyaya wamamatee lillahi rabbil-AAalameen, la shareeka lahu wabithalika omirtu wa-ana minal-muslimeen. Allahumma antal-maliku la ilaha illa ant. anta rabbee wa-ana AAabduk, thalamtu nafsee waAAtaraftu bithanbee faghfir lee thunoobee jameeAAan innahu la yaghfiruth-thunooba illa ant.wahdinee li-ahsanil-akhlaqi la yahdee li-ahsaniha illa ant, wasrif AAannee sayyi-aha la yasrifu AAannee sayyi-aha illa ant, labbayka wasaAAdayk,walkhayru kulluhu biyadayk, washsharru laysa ilayk, ana bika wa-ilayk, tabarakta wataAAalayt, astaghfiruka wa-atoobu ilayk",
                    R.string.text_after_takbeer_1
                )
            )
            listDuas.add(
                DuasEntity(
                    24,
                    5,
                    12,
                    "اللَّهُمَّ بَاعِدْ بَيْنِي وَبَيْنَ خَطَايَايَ كَمَا بَاعَدْتَ بَيْنَ الْمَشْرِقِ وَالْمَغْرِبِ، اللَّهُمَّ نَقِّنِي مِنْ خَطَايَايَ، كَمَا يُنَقَّى الثَّوْبُ الْأَ بْيَضُ مِنَ الدَّنَسِ، اللَّهُمَّ اغْسِلْنِي مِنْ خَطَايَايَ بِالثَّلْجِ وّالْمَاءِ وَالْبَرَدِ",
                    "Allaahumma baa'id baynee wa bayna khataayaaya kamaa baa'adta baynal-mashriqi walmaghribi, Allaahumma naqqinee min khataayaaya kamaa yunaqqath-thawbul-'abyadhu minad-danasi, Allaahum-maghsilnee min khataayaaya, bith-thalji walmaa'i walbarad",
                    R.string.text_after_takbeer_2
                )
            )
            listDuas.add(
                DuasEntity(
                    25,
                    5,
                    12,
                    "سُبْحَانَكَ اللَّهُمَّ وَبِحَمْدِكَ، وَتَبَارَكَ اسْمُكَ، وَتَعَالَى جَدُّكَ، وَلَا إِلَهَ غَيْرُكَ",
                    "Subhaanaka Allaahumma wa bihamdika, wa tabaarakasmuka, wa ta'aalaa jadduka, wa laa 'ilaaha ghayruka",
                    R.string.text_after_takbeer_3
                )
            )
            //During Ruku
            listDuas.add(
                DuasEntity(
                    26,
                    5,
                    13,
                    "سُبْحَانَ رَبِّيَ الْعَظِيمِ",
                    "Subhaana Rabbiyal-'Adheem",
                    R.string.text_during_ruku_1
                )
            )
            listDuas.add(
                DuasEntity(
                    27,
                    5,
                    13,
                    "سُبْحَانَكَ اللَّهُمَّ رَبَّنَا وَبِحَمْدِكَ اللَّهُمَّ اغْفِرْ لِي",
                    "Subhaanaka Allaahumma Rabbanaa wa bihamdika Allaahum-maghfir lee",
                    R.string.text_during_ruku_2
                )
            )
            listDuas.add(
                DuasEntity(
                    28,
                    5,
                    13,
                    "سُبُّوحٌ، قُدُوسٌ، رَبُّ الْمَلَائِكَةِ وَالرُّوحِ",
                    "Subboohun, Quddoosun, Rabbul-malaa'ikati warrooh",
                    R.string.text_during_ruku_3
                )
            )
            listDuas.add(
                DuasEntity(
                    29,
                    5,
                    13,
                    "اللَّهُمَّ لَكَ رَكَعْتُ، وَبِكَ آمَنْتُ، وَلَكَ أَسْلَمْتُ خَشَعَ لَكَ سَمْعِي، وَبَصَرِي وَمُخِّي، وَعَظْمِي، وَعَصَبِي، وَمَا اسْتَقَلَّ بِهِ قَدَمِي",
                    "Allaahumma laka raka'tu, wa bika 'aamantu, wa laka 'aslamtu khasha'a laka sam'ee, wa basaree, wa mukhkhee, wa 'adhmee, wa 'asabee, wa mastaqalla bihi qadamee",
                    R.string.text_during_ruku_4
                )
            )
            listDuas.add(
                DuasEntity(
                    30,
                    5,
                    13,
                    "سُبْحَانَ ذِي الْجَبَرُوتِ، وَالْمَلَكُوتِ، وَالْكِبْرِيَاءِ، وَالْعَظَمَةِ",
                    "Subhaana thil-jabarooti, walmalakooti, walkibriyaa'i, wal'adhamati",
                    R.string.text_during_ruku_5
                )
            )
            //While prostrating before Allah
            listDuas.add(
                DuasEntity(
                    31,
                    5,
                    14,
                    "سُبْحَانَ رَبِّيَ الأَعْلَى",
                    "Subhaana Rabbiyal-A 'laa",
                    R.string.text_before_allah_1
                )
            )
            listDuas.add(
                DuasEntity(
                    32,
                    5,
                    14,
                    "سُبْحَانَكَ اللَّهُمَّ رَبَّنَا وَبِحَمْدِكَ اللَّهُمَّ اغْفِرْ لِي",
                    "Subhaanaka Allaahumma Rabbanaa wa bihamdika Allaahum-maghfir lee",
                    R.string.text_before_allah_2
                )
            )
            listDuas.add(
                DuasEntity(
                    33,
                    5,
                    14,
                    "سُبُّوحٌ، قُدُوسٌ، رَبُّ الْمَلَائِكَةِ وَالرُّوحِ",
                    "Subboohun, Quddoosun, Rabbul-malaa'ikati warrooh",
                    R.string.text_before_allah_3
                )
            )
            listDuas.add(
                DuasEntity(
                    34,
                    5,
                    14,
                    "اللَّهُمَّ لَكَ سَجَدْتُ وَبِكَ آمَنْتُ، وَلَكَ أَسْلَمْتُ، سَجَدَ وَجْهِيَ لِلَّذِي خَلَقَهُ، وَصَوَّرَهُ، وَشَقَّ سَمْعَهُ وَبَصَرَهُ، تَبَارَكَ اللهُ أَحْسَنُ الْخَالِقِينَ",
                    "Allaahumma laka sajadtu wa bika 'aamantu, wa laka 'aslamtu, sajada wajhiya lillathee khalaqahu, wa sawwarahu, wa shaqqa sam'ahu wa basarahu, tabaarakallaahu 'ahsanul-khaaliqeen",
                    R.string.text_before_allah_4
                )
            )
            listDuas.add(
                DuasEntity(
                    35,
                    5,
                    14,
                    "سُبْحَانَ ذِي الْجَبَرُوتِ، وَالْمَلَكُوتِ، وَالْكِبْرِيَاءِ، وَالْعَظَمَةِ",
                    "Subhaana thil-jabarooti, walmalakooti, walkibriyaa'i, wal'adhamati",
                    R.string.text_before_allah_5
                )
            )
            listDuas.add(
                DuasEntity(
                    36,
                    5,
                    14,
                    "اللَّهُمَّ إِنِّي أَعُوذُ بِرِضَاكَ مِنْ سَخَطِكَ، وَبِمُعَافَاتِكَ مَنْ عُقُوبَتِكَ، وَاَعُوذُ بِكَ مِنْكَ، لَا أُحصِي ثَنَاءً عَلَيْكَ أَنْتَ كَمَا أَثْنَيْتَ عَلَى نَفْسِكََ",
                    "Allaahumma 'innee 'a'oothu biridhaaka min sakhatika, wa bimu'aafaatika min 'uqoobatika wa 'a'oothu bika minka, laa 'uhsee thanaa'an 'alayka 'Anta kamaa 'athnayta 'alaa nafsika",
                    R.string.text_before_allah_6
                )
            )
            listDuas.add(
                DuasEntity(
                    37,
                    5,
                    14,
                    "اللَّهُمَّ اغْفِرْ لِي ذَنْبِي كُلَّهُ، دِقَّهُ وَجِلَّهُ، وَأَوَّلَهُ وَآخِرَهُ وَعَلَانِيَتَهُ وَسِرَّهُ",
                    "Allaahum-maghfir lee thanbee kullahu, diqqahu wa jillahu, wa 'awwalahu wa 'aakhirahu wa 'alaaniyata hu wa sirrahu",
                    R.string.text_before_allah_7
                )
            )
            //When sitting between two Sujood
            listDuas.add(
                DuasEntity(
                    38,
                    5,
                    15,
                    "رَبِّ اغْفِرْ لِي رَبِّ اغْفِرْ لِي",
                    "Rabbighfir lee, Rabbighfir lee",
                    R.string.text_two_sujood_1
                )
            )
            listDuas.add(
                DuasEntity(
                    39,
                    5,
                    15,
                    "اللَّهُمَّ اغْفِرْ لِي، وَارْحَمْنِي، وَاهْدِنِي، وَاجْبُرْنِي، وَعَافِنِي، وَارْزُقْنِي، وَارْفَعْنِي",
                    "Allaahum-maghfir lee, warhamnee, wahdinee, wajburnee, wa 'aafinee, warzuqnee, warfa'nee",
                    R.string.text_two_sujood_2
                )
            )
            //prostration during quran recitation
            listDuas.add(
                DuasEntity(
                    106,
                    5,
                    16,
                    "سَجَدَ وَجْهِيَ لِلَّذِي خَلَقَهُ، وَشَقَّ سَمْعَهُ وَبَصَرَهُ، بِحَوْلِهِ وَقُوَّتِهِ، فَتَبَارَكَ اللَّهُ أَحسَنُ الْخَالِقِينَ",
                    "Sajada wajhiya lillathee khalaqahu, wa shaqqa sam'ahu wa basarahu bihawlihi wa quwwatihi. Fatabaarakallaahu 'ahsanul-khaaliqeen.",
                    R.string.text_quran_recitation_1
                )
            )
            //when sitting in preyer (tashahhud)
            listDuas.add(
                DuasEntity(
                    40,
                    5,
                    17,
                    "التَّحِيَّاتُ للهِ، وَالصَّلَوَاتُ، وَالطَّيِّبَاتُ، السَّلَامُ عَلَيْكَ أَيُّهَا النَّبِيُّ وَرَحْمَةُ اللهِ وَبَرَكَاتُهُ، السَّلَامُ عَلَيْنَا وَعَلَى عِبَادِ اللهِ الصَّالِحِينَ. أّشَْدُ أَنْ لَا إِلَهَ إِلَّا اللهُ وَأَشْهَدُ أَنَّ مُحَمَّداً عَبْدُهُ وَرَسُولُهُ",
                    "Attahiyyaatu lillaahi wassalawaatu , wattayyibaatu , assalaamu 'alayka 'ayyuhan-Nabiyyu wa rahmatullaahi wa barakaatuhu, assalaamu 'alaynaa wa 'alaa 'ibaadillaahis-saaliheen. 'Ash-hadu 'an laa 'ilaaha 'illallaahu wa 'ash-hadu 'anna Muhammadan 'abduhu wa Rasooluhu",
                    R.string.text_in_preyer_1
                )
            )
            //darood-e-ibraheemi
            listDuas.add(
                DuasEntity(
                    41,
                    5,
                    18,
                    "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ كَمَا صَلَّيْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ، إِنَّكَ حَمِيدٌ مَجِيدٌ، اللَّهُمَّ بَارِكَ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ كَمَا بَارَكْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ، إِنَّكَ حَمِيدٌ مَجِيدٌ",
                    "Allaahumma salli 'alaa Muhammadin wa 'alaa 'aali Muhammadin, kamaa sallayta 'alaa 'Ibraaheema wa 'alaa 'aali 'Ibraaheema, 'innaka Hameedun Majeed. Allaahumma baarik 'alaa Muhammadin wa 'alaa 'aali Muhammadin, kamaa baarakta 'alaa 'Ibraaheema wa 'alaa 'aali 'Ibraaheema, 'innaka Hameedun Majeed",
                    R.string.text_ibraheemi_1
                )
            )
            //After tashahhud
            listDuas.add(
                DuasEntity(
                    42,
                    5,
                    19,
                    "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنْ عَذَابِ الْقَبْرِ، وَأَعُوذُ بِكَ مِنْ فِتْنَةِ الْمَسِيحِ الدَّجَّالِ، وَأَعُوذُ بِكَ مِنْ فِتْنَةِ الْمَحْيَا وَالْمَمَاتِ. اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنْ الْمَأْثَمِ وَالْمَغْرَمِ",
                    "Allaahumma 'innee 'a'oothu bika min 'athaabil-qabri, wa 'a'oothu bika min fitnatil-maseehid-dajjaali, wa 'a'oothu bika min fitnatil-mahyaa walmamaati. Allaahumma 'innee 'a'oothu bika minal-ma'thami walmaghrami",
                    R.string.text_after_tashahhud_1
                )
            )
            listDuas.add(
                DuasEntity(
                    43,
                    5,
                    19,
                    "اللَّهُمَّ إِنِّي ظَلَمْتُ نَفْسِي ظُلْماً كَثِيراً، وَلَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ، فَاغْفِرْ لِي مَغْفِرَةً مِنْ عِنْدِكَ وَارْحَمْنِي إِنَّكَ أَنْتَ الْغَفُورُ الرَّحِيمُ",
                    "Allaahumma 'innee dhalamtu nafsee dhulman katheeran, wa laa yaghfiruth-thunooba 'illaa 'Anta, faghfir lee maghfiratan min 'indika warhamnee 'innaka 'Antal-Ghafoorur-Raheem",
                    R.string.text_after_tashahhud_2
                )
            )
            listDuas.add(
                DuasEntity(
                    44,
                    5,
                    19,
                    "اللَّهُمَّ اغْفِرْ لِي مَا قَدَّمْتُ وَمَا أَخَّرْتُ، وَمَا أَسْرَرْتُ، وَمَا أَعْلَنْتُ، وَمَا أَسْرَفْتُ، وَمَا أَنْتَ أَعْلَمُ بِهِ مِنِّي. أَنْتَ الْمُقَدِّمُ، وَأَنْتَ الْمُؤَخِّرُ لَا إِلهَ إِلَاَّ أَنْتَ",
                    "Allaahum-maghfir lee maa qaddamtu, wa maa 'akhkhartu, wa maa 'asrartu, wa maa 'a'lantu, wa maa 'asraftu, wa maa 'Anta 'a'lamu bihi minnee. 'Antal-Muqaddimu, wa 'Antal-Mu'akhkhiru laa 'ilaaha 'illaa 'Anta",
                    R.string.text_after_tashahhud_3
                )
            )
            //Dua e qunoot (recited in witr prayer)
            listDuas.add(
                DuasEntity(
                    45,
                    5,
                    20,
                    "اَللَّهُمَّ اِنَّا نَسۡتَعِيۡنُكَ وَنَسۡتَغْفِرُكَ وَنُؤۡمِنُ بِكَ وَنَتَوَكَّلُ عَلَيۡكَ وَنُثۡنِىۡ عَلَيۡكَ ٱلۡخَيۡرَ وَنَشۡكُرُكَ وَلَا نَكۡفُرُكَ وَنَخۡلَعُ وَنَتۡرُكُ مَنۡ يَّفۡجُرُكَ. اَللَّهُمَّ اِيَّاكَ نَعۡبُدُ وَلَكَ نُصَلِّئ وَنَسۡجُدُ وَاِلَيۡكَ نَسۡعٰى ونَحۡفِدُ ونَرۡجُوۡا رَحۡمَتَكَ وَنَخۡشٰى عَذَابَكَ اِنَّ عَذَابَكَ بِالۡكُفَّارِ مُلۡحِقٌٌ",
                    "Allah humma inna nasta-eenoka wa nastaghfiruka wa nu'minu bika wa natawakkalu alaika wa nusni alaikal khair, wa nashkuruka wala nakfuruka wa nakhla-oo wa natruku mai yafjuruka, Allah humma iyyaka na'budu wa laka nusalli wa nasjud wa ilaika nas aaa wa nahfizu wa narju rahma taka wa nakhshaa azaabaka inna azaabaka bil kuffari mulhik",
                    R.string.text_e_qunoot_1
                )
            )
            //after fajr prayer
            listDuas.add(
                DuasEntity(
                    115,
                    5,
                    21,
                    "اللهم إني أسألك علماً نافعاً، ورزقاً طيباً، وعملاً متقبلاً",
                    "اللهم إني أسألك علماً نافعاً، ورزقاً طيباً، وعملاً متقبلاً",
                    R.string.text_fajr_prayer_1
                )
            )
            //Home
            //when leaving home
            listDuas.add(
                DuasEntity(
                    46,
                    6,
                    22,
                    "بِسْمِ اللَّهِ تَوَكَّلْـتُ عَلَى اللَّهِ، وَلاَ حَوْلَ وَلاَ قُـوَّةَ إِلاَّ بِاللَّهِ.",
                    "Bismil-lah, tawakkaltu AAalal-lah, wala hawla wala quwwata illa billah.",
                    R.string.text_leaving_home_1
                )
            )
            listDuas.add(
                DuasEntity(
                    47,
                    6,
                    22,
                    "اَللَّهُـمَّ إِنِّي أَعُـوذُ بِكَ أَنْ أَضِـلَّ أَوْ أُضَـلَّ، أَوْ أَزِلَّ أَوْ أُزَلَّ، أَوْ أَظْلِـمَ أَوْ أَُظْلَـمَ، أَوْ أَجْهَلَ أَوْ يُـجْهَلَ عَلَـيَّ .",
                    "Allahumma innee aAAoothu bika an adilla aw odal, aw azilla aw ozall, aw athlima aw othlam, aw ajhala aw yujhala AAalay.",
                    R.string.text_leaving_home_2
                )
            )
            //upon entering home
            listDuas.add(
                DuasEntity(
                    48,
                    6,
                    23,
                    "بِسْـمِ اللّهِ وَلَجْنـَا، وَبِسْـمِ اللّهِ خَـرَجْنـَا، وَعَلَـى رَبِّنـَا تَوَكّلْـنَا.",
                    "Bismil-lahi walajna, wabismil-lahi kharajna, waAAala rabbina tawakkalna.",
                    R.string.text_entering_home_1
                )
            )
            //when going up the stairs / ascending a place
            listDuas.add(
                DuasEntity(
                    116,
                    6,
                    24,
                    "الله أكبر",
                    "الله أكبر",
                    R.string.text_the_stairs_2
                )
            )
            //when coming down of stairs / descending from high place
            listDuas.add(
                DuasEntity(
                    117,
                    6,
                    25,
                    "سبحان الله",
                    "سبحان الله",
                    R.string.text_the_stairs_3
                )
            )
            //Garment
            //while wearing clothes
            listDuas.add(
                DuasEntity(
                    107,
                    7,
                    26,
                    "اَلْحَمْدُ لِلّهِ الَّذِي كَسَانِي هَذَا (الثَّوْبَ) وَرَزَقَنِيهِ مِنْ غَـيـْرِ حَوْلٍ مِنِّي وَلَا قُـوَّةٍ.",
                    "Alhamdu lillahil-lathee kasanee hatha (aththawb) warazaqaneehi min ghayri hawlin minnee wala quwwah.",
                    R.string.text_the_stairs_1
                )
            )
            //when wearing new clothes
            listDuas.add(
                DuasEntity(
                    56,
                    7,
                    27,
                    "اللَّهُمَّ لَكَ الْحَمْدُ أَنْتَ كَسَوْتَنِيهِ، أَسْأَلُكَ مِنْ خَيْرِهِ وَخَيْرِ مَا صُنِعَ لَهُ، وَأَعُوذُ بِكَ مِنْ شَرِّهِ وَشَرِّ مَا صُنِعَ لَهُ",
                    "Allaahumma lakal-hamdu 'Anta kasawtaneehi, 'as'aluka min khayrihi wa khayri maa suni'a lahu, wa 'a'oothu bika min sharrihi wa sharri ma suni'a lahu",
                    R.string.text_new_clothes_1
                )
            )
            //when looking into the mirror
            listDuas.add(
                DuasEntity(
                    57,
                    7,
                    28,
                    "اللَّهُمَّ أَنْتَ حَسَّنْتَ خَلْقِي فَحَسِّنْ خُلُقِي",
                    "Allahumma antha hasantha khalqi fa hasintha khulqi",
                    R.string.text_the_mirror_1
                )
            )
            //Travel
            //for travel
            listDuas.add(
                DuasEntity(
                    58,
                    8,
                    29,
                    "سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَذَا وَمَا كُنَّا لَهُ مُقْرِنِينَ وَإِنَّا إِلَى رَبِّنَا لَمُنْقَلِبُونَ.",
                    "Subhana-alladhi sakh-khara la-na hadha wa ma kunna la-hu muqrinin. Wa inna ila Rabbi-na la munqalibun.",
                    R.string.text_for_travel_1
                )
            )
            listDuas.add(
                DuasEntity(
                    59,
                    8,
                    29,
                    "رَّبِّ اَدْخِلْنِيْ مُدْخَلَ صِدْقٍ وَّاَخْرِجْنِيْ مُخْــرَجَ صِدْقٍ وَّاجْعَلْ لِّيْ مِنْ لَّدُنْكَ سُلْطٰنًا نَّصِيْرًا",
                    "Rabbi Adkhilnee Mudkhala S’idqiw Wa Akhrijnee Mukhraja S’diqiw Waj-a’l Lee Mil Ladunkaa Sult’aanan Nas’eeraa",
                    R.string.text_for_travel_2
                )
            )
            //while returning form travel
            listDuas.add(
                DuasEntity(
                    60,
                    8,
                    30,
                    "ا للهُ أَكْـبَر، ا للهُ أَكْـبَر، ا للهُ أَكْـبَر،لا إلهَ إلاّ اللّهُ وَحْـدَهُ لا شريكَ لهُ، لهُ الملكُ ولهُ الحَمْد، وهُوَ على كُلّ شَيءٍ قَـدير، آيِبـونَ تائِبـونَ عابِـدونَ لِرَبِّـنا حـامِـدون، صَدَقَ اللهُ وَعْـدَه، وَنَصَـرَ عَبْـدَه، وَهَزَمَ الأَحْـزابَ وَحْـدَه.",
                    "Allahu Akbar, Allahu Akbar, Allahu Akbar, Laa ilaaha illallaahu wahdahu laa shareeka lahu, lahul-mulku, wa lahul-hamdu, wa Huwa alaa kulli shay in Qadeer, aaiboona, taaiboona, aabidoona, lirabbinaa haamidoona, sadaqallaahu wadahu, wa nasara abdahu, wa hazamal-ahzaaba wahdahu.",
                    R.string.text_form_travel_1
                )
            )
            //when night approaches during travel
            listDuas.add(
                DuasEntity(
                    108,
                    8,
                    31,
                    "يا أرض، ربي وربك الله، أعوذ بالله من شرك، وشر ما فيك، وشر ما خلق فيك، وشر ما يدب عليك، أعوذ بالله من الليوسن، ومن الحيات السوداء الكبيرة، ومن الحيات الأخرى، ومن العقارب، ومن شر الجن الذي يسكن الديار، ومن والد وذريته",
                    "ya 'arda, rabiy warabik allahu, 'aeudh biallah min sharka, washari ma fika, washari ma khalaq fika, washara ma yadibu ealayki, 'aeudh biallah min alliyusun, wamin alhayaat alsawda' alkabirati, wamin alhayat al'ukhraa, wamin aleaqaribi, wamin shari aljini aladhi",
                    R.string.text_approaches_during_travel_1
                )
            )
            //for accommodation
            listDuas.add(
                DuasEntity(
                    61,
                    8,
                    32,
                    "رَّبِّ أَنزِلْنِي مُنزَلًا مُّبَارَكًا وَأَنتَ خَيْرُ الْمُنزِلِينَ",
                    "Rabbi anzilnee munzalan mubarakanwaanta khayru almunzileen",
                    R.string.text_accommodation_1
                )
            )
            //before sleeping at night during travel
            listDuas.add(
                DuasEntity(
                    109,
                    8,
                    33,
                    "وَقُلْ رَّبِّ اَعُوْذُ بِكَ مِنْ هَمَزٰتِ الشَّيٰطِيْنِ. وَاَعُوْذُ بِكَ رَبِّ اَنْ يَّحْضُرُوْنِ.",
                    "Rabbi ‘a`outhubika min hamazaatish-shayaateeni, wa ‘a`outhu bika rabbi ‘ay-yahdhuroon",
                    R.string.text_during_travel_1
                )
            )
            listDuas.add(
                DuasEntity(
                    110,
                    8,
                    33,
                    "أَعُوذُ بِاللَّهِ مِنَ الشَّيْطانِ الرَّجِيْمِ",
                    "A'oothu billaahi minash-Shaytaanir-rajeem",
                    R.string.text_during_travel_2
                )
            )
            listDuas.add(
                DuasEntity(
                    111,
                    8,
                    33,
                    "أَعُوذُ بِكَلِمَاتِ اللهِ التَّامَّاتِ مِنْ شَرِّ مَا خَلَقَ",
                    "A'oothu bikalimaatil-laahit-taammaati min sharri maa khalaqa",
                    R.string.text_during_travel_3
                )
            )

            //Food
            //when beginning a meal
            listDuas.add(
                DuasEntity(
                    59,
                    9,
                    34,
                    "بِسْمِ اللّٰہِ الرَّحْمٰنِ الرَّحِيْمِ",
                    "Bismi-llāhi r-raḥmāni r-raḥīm",
                    R.string.text_beginning_a_meal_1
                )
            )
            //if one forget to mention allah at the beginning
            listDuas.add(
                DuasEntity(
                    118,
                    9,
                    35,
                    "بسم الله البداية والنهاية",
                    "bism allah albidayat walnihaya",
                    R.string.text_the_beginning_1
                )
            )
            //after finishing a meal
            listDuas.add(
                DuasEntity(
                    60,
                    9,
                    36,
                    "الْحَمْدُ لِلَّهِ الَّذِى اطْعَمَنَا وَسَقَانَا ، وَجَعَلنَا مُسْلِمِينَ",
                    "Alhamdulilahil ladhi at’amana, wasaqana, waj’alna min-al Muslimeen",
                    R.string.text_finishing_a_meal_1
                )
            )
            //when given a drink
            listDuas.add(
                DuasEntity(
                    61,
                    9,
                    37,
                    "اللَّهُمَّ اطْعِمَّ مَنْ اطْعَمَنِي وَاسْقِِِ ِ مَنْ سَقَانِيِِ",
                    "Allahumma at’im man at’amani, wasqi man saqani",
                    R.string.text_a_drink_1
                )
            )
            //upon drinking milk
            listDuas.add(
                DuasEntity(
                    62,
                    9,
                    38,
                    "اللَّهُمَّ بَارِِكْ لَنا فِيهِ وَزِدْنَا مِنْهُ",
                    "Allahumma baarik lana feehi wa zidna minhu",
                    R.string.text_drinking_milk_1
                )
            )
            //when dining at someone’s house
            listDuas.add(
                DuasEntity(
                    63,
                    9,
                    39,
                    "اللَّهُمَّ بَارِكْ لَهُمْ فِيمَا رَزَقْتَهُمْ ، وَاغْفِرْ لَهُمْ وَارْحَمْهُمْ",
                    "Allahumma barik lahum feema razaqtahum, waghfir lahum, war hamhum",
                    R.string.text_house_1
                )
            )
            //for someone who offers you a meal
            listDuas.add(
                DuasEntity(
                    64,
                    9,
                    40,
                    "اللّهُـمَّ أَطْعِمْ مَن أَطْعَمَني، وَاسْقِ مَن سَقَانِي",
                    "Allaahumma 'at'im man 'at'amanee wasqi man saqaanee",
                    R.string.text_you_a_meal_1
                )
            )
            //when openning your fast
            listDuas.add(
                DuasEntity(
                    65,
                    9,
                    41,
                    "اللَّهُمَّ اِنِّى لَكَ صُمْتُ وَبِكَ امنْتُ وَعَليْكَ تَوَكّلتُ وَ عَلى رِزْقِكَ اَفْطَرْتُ",
                    "Allahumma inni laka sumtu wa bika aamantu wa alayka tawakkaltu wa ala rizq-ika-aftartu",
                    R.string.text_your_fast_1
                )
            )
            //For someone who provides you with Iftar
            listDuas.add(
                DuasEntity(
                    66,
                    9,
                    42,
                    "أَفْطَـرَ عِنْدَكُم الصّـائِمونَ وَأَكَلَ طَعامَـكُمُ الأبْـرار، وَصَلَّـتْ عَلَـيْكُمُ الملائِكَـة",
                    "Aftara 'indakumus-saa'imoona, wa 'akala ta'aamakumul-'abraaru , wa sallat 'alaykumul-malaa'ikatu",
                    R.string.text_with_iftar_1
                )
            )
            //Deceased
            //when a loss occurs
            listDuas.add(
                DuasEntity(
                    67,
                    10,
                    43,
                    "إِنَّا لِلّهِ وَإِنَّـا إِلَيْهِ رَاجِعونَ",
                    "Inna lillahi wa inna ilayhi raajioon",
                    R.string.text_loss_occurs_1
                )
            )
            //when visiting graves
            listDuas.add(
                DuasEntity(
                    68,
                    10,
                    44,
                    "السَّلَامُ عَلَيْكُمُ أَهْلَ الدِّيَارِ مِنَ الْمُؤْمِنِينَ وَالْمُسْلِمِينَ وَإِنَّا إِنْ شَاءَ اللَّهُ بِكُمْ لَاحِقُونَ نَسْأَلُ اللَّهَ لَنَا وَلَكُمُ الْعَافِيَةَ",
                    "Assalaamu 'alaykum 'ahlad-diyaari, minal-mu'mineena walmuslimeena, wa 'innaa 'in shaa' Allaahu bikum laahiqoona [wa yarhamullaahul-mustaqdimeena minnaa walmusta'khireena] 'as'alullaaha lanaa wa lakumul- 'aafiyata.",
                    R.string.text_visiting_graves_1
                )
            )
            //when offering condolenca
            listDuas.add(
                DuasEntity(
                    69,
                    10,
                    45,
                    "إِنَّ للهِ ما أَخَذ، وَلَهُ ما أَعْـطـى، وَكُـلُّ شَيءٍ عِنْـدَهُ بِأَجَلٍ مُسَـمَّى.فَلْتَصْـبِر وَلْتَحْـتَسِب",
                    "Inna lillaahi maa 'akhatha, wa lahu maa 'a'taa, wa kullu shay'in 'indahu bi'ajalin musamman. Faltasbir waltahtasib",
                    R.string.text_offering_condolenca_1
                )
            )
            //when closing the eyes of the deceased
            listDuas.add(
                DuasEntity(
                    119,
                    10,
                    46,
                    "اللهمّ اغفر لـ [اسم المتوفى] وارفع مقامه في المهديين. ابعثه على صراط السابقين واغفر لنا وله يا رب العالمين. ووسع له قبره ونور له فيه",
                    "allhm aghfir la [asm almutawafia] warfae maqamah fi almahdiiyna. abeathah ealaa sirat alsaabiqin waghfir lana walah ya rabi alealamina. wawasae lah qabruh wanur lah fih",
                    R.string.text_e_qunoot_1
                )
            )
            //For the deceased at the funeral prayer
            listDuas.add(
                DuasEntity(
                    70,
                    10,
                    47,
                    "اللهُـمِّ اغْفِـرْ لَهُ وَارْحَمْـه ، وَعافِهِ وَاعْفُ عَنْـه ، وَأَكْـرِمْ نُزُلَـه ، وَوَسِّـعْ مُدْخَـلَه ، وَاغْسِلْـهُ بِالْمـاءِ وَالثَّـلْجِ وَالْبَـرَدْ ، وَنَقِّـهِ مِنَ الْخطـايا كَما نَـقّيْتَ الـثَّوْبُ الأَبْيَـضُ مِنَ الدَّنَـسْ ، وَأَبْـدِلْهُ داراً خَـيْراً مِنْ دارِه ، وَأَهْلاً خَـيْراً مِنْ أَهْلِـه ، وَزَوْجَـاً خَـيْراً مِنْ زَوْجِه، وَأَدْخِـلْهُ الْجَـنَّة ، وَأَعِـذْهُ مِنْ عَذابِ القَـبْر وَعَذابِ النّـار",
                    "Allaahum-maghfir lahu warhamhu, wa 'aafihi, wa'fu 'anhu, wa 'akrim nuzulahu, wa wassi' mudkhalahu, waghsilhu bilmaa'i waththalji walbaradi, wa naqqihi minal-khataayaa kamaa naqqaytath-thawbal-'abyadha minad-danasi, wa 'abdilhu daaran khayran min daarihi, wa 'ahlan khayran min 'ahlihi, wa zawjan khayran min zawjihi, wa 'adkhilhul-jannata, wa. 'a'ithhu min 'athaabil-qabri[wa 'athaabin-naar]",
                    R.string.text_funeral_prayer_1
                )
            )
            listDuas.add(
                DuasEntity(
                    71,
                    10,
                    47,
                    "اللهُـمِّ اغْفِـرْ لِحَيِّـنا وَمَيِّتِـنا وَشـاهِدِنا ، وَغائِبِـنا ، وَصَغيـرِنا وَكَبيـرِنا ، وَذَكَـرِنا وَأُنْثـانا . اللهُـمِّ مَنْ أَحْيَيْـتَهُ مِنّا فَأَحْيِـهِ عَلى الإِسْلام ،وَمَنْ تَوَفَّـيْتََهُ مِنّا فَتَوَفَّـهُ عَلى الإِيـمان ، اللهُـمِّ لا تَحْـرِمْنـا أَجْـرَه ، وَلا تُضِـلَّنا بَعْـدَهُ",
                    "Allaahum-maghfir lihayyinaa, wa mayyitinaa, wa shaahidinaa, wa ghaa'ibinaa, wa sagheerinaa wa kabeerinaa, wa thakarinaa wa 'unthaanaa. Allaahumma man 'ahyaytahu minnaa fa'ahyihi 'alal-'Islaami, wa man tawaffaytahu minnaa fatawaffahu 'alal-'eemaani, Allaahumma laa tahrimnaa 'ajrahu wa laa tudhillanaa ba'dahu",
                    R.string.text_funeral_prayer_2
                )
            )
            //When placing the deceased in the grave
            listDuas.add(
                DuasEntity(
                    71,
                    10,
                    48,
                    "بِسْـمِ اللهِ وَعَلـى سُـنَّةِ رَسـولِ الله",
                    "Bismillaahi wa 'alaa sunnati Rasoolillaahi",
                    R.string.text_the_graver_1
                )
            )

            //Hajj / Umrah
            //tabihya
            listDuas.add(
                DuasEntity(
                    112,
                    11,
                    49,
                    "لَبَّيْكَ اللَّهُمَّ لَبَّيْكَ، لَبَّيْكَ لَا شَرِيكَ لَكَ لَبَّيْكَ، انَّالْحَمْدَ، وَالنِّعْمَةَ، لَكَ وَالْمُلْكَ، لا شَرِيكَ لَكَ",
                    "Labbayk Allaahumma labbayk, labbayk laa shareeka laka labbayk, 'innal-hamda, wanni'mata, laka walmulk, laa shareeka laka.",
                    R.string.text_tabihya_1
                )
            )
            //takbeer when passing the black stone
            listDuas.add(
                DuasEntity(
                    71,
                    11,
                    50,
                    "اللهُ أكْبَر",
                    "Allaahu 'Akbar",
                    R.string.text_black_stone_1
                )
            )
            //upon reaching the yemeni corner during tawaf
            listDuas.add(
                DuasEntity(
                    72,
                    11,
                    51,
                    "رَبَّنآ ءَاتِنَا فِى اْلدُّنْيَا حَسَنَةً وَ فى الاخِرَةِ حَسَنَةً وَ قِنَا عَذَابَ اْلنَّارِ",
                    "Rabbana aatina fid dunniyya hasanatan wa fil akhirati hasanatan wa qina ‘adhaban nar",
                    R.string.text_during_tawaf_1
                )
            )
            //while going to mount safa
            listDuas.add(
                DuasEntity(
                    73,
                    11,
                    52,
                    "أَبْدَأُ بِمَا بَدَأَ اللهُ بِهِ. لَا إِلهَ إِلَّا اللهُ وَ اللهُ أَكْـبَر لَا إِلهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ المُلْكُ وَلَهُ الحَمْدُ وهُوَ عَلى كُلِّ شَيءٍ قَديرٌ، لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ أَنْجَزَ وَعْدَهُ، وَنَصَرَ عَبْدَهُ وَهَزَمَ الأَحْزَابَ وَحْدَهُ",
                    "Abda’u bi maa bada’-allaahu bih allaahu akbar, allaahu akbar, allaahu akbar laa ilaaha ill-allaahu waḥdahu laa shareeka lah, lahul-mulku wa lahul-ḥamdu wa huwa ‛alaa kulli shay’in qadeer, laa ilaaha ill-allaahu waḥdahu, anjaza wa‛dahu, wa naṣara ‛abdahu, wa hazam-al-’aḥzaaba waḥdah",
                    R.string.text_mount_safa_1
                )
            )
            //When at Mount Safa and Mount Marwah
            listDuas.add(
                DuasEntity(
                    74,
                    11,
                    53,
                    "أَبْدَأُ بِمَا بَدَأَ اللهُ بِهِ. لَا إِلهَ إِلَّا اللهُ وَ اللهُ أَكْـبَر لَا إِلهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ المُلْكُ وَلَهُ الحَمْدُ وهُوَ عَلى كُلِّ شَيءٍ قَديرٌ، لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ أَنْجَزَ وَعْدَهُ، وَنَصَرَ عَبْدَهُ وَهَزَمَ الأَحْزَابَ وَحْدَهُ",
                    "Abda’u bi maa bada’-allaahu bih allaahu akbar, allaahu akbar, allaahu akbar laa ilaaha ill-allaahu waḥdahu laa shareeka lah, lahul-mulku wa lahul-ḥamdu wa huwa ‛alaa kulli shay’in qadeer, laa ilaaha ill-allaahu waḥdahu, anjaza wa‛dahu, wa naṣara ‛abdahu, wa hazam-al-’aḥzaaba waḥdah",
                    R.string.text_mount_marwah_1
                )
            )
            //for reciting at al-mash’ar al-haraam (muzdalifah)
            listDuas.add(
                DuasEntity(
                    75,
                    11,
                    54,
                    "أَللّهُ أَكْبَرُ أَللّهُ أَحَدٌ لاَإِلَهَ إِلاَّ اللهُ",
                    "Allaahu 'Akbar Allaahu 'Ahad Laa 'ilaaha 'illallaah",
                    R.string.text_al_haraam_1
                )
            )
            //when throwing stone at jamaraat
            listDuas.add(
                DuasEntity(
                    76,
                    11,
                    55,
                    "اللهُ أكْبَر",
                    "Allaahu 'Akbar",
                    R.string.text_at_jamaraat_1
                )
            )
            //at arafat
            listDuas.add(
                DuasEntity(
                    77,
                    11,
                    56,
                    "لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٍ",
                    "Laa ilaaha ill-allaahu, waḥdahu laa shareeka lah, lahul-mulku wa lahul-ḥamdu, wa huwa ‛alaa kulli shay’in qadeer",
                    R.string.text_at_arafat_1
                )
            )
            //When sacrificing an animal
            listDuas.add(
                DuasEntity(
                    78,
                    11,
                    57,
                    "بِسْمِ اللهِ واللهُ أَكْبَرُ اللَّهُمَّ مِنْكَ ولَكَ اللَّهُمَّ تَقَبَّلْ مِنِّي",
                    "Bismillaahi wallaahu 'Akbar [Allaahumma minka wa laka] Allaahumma taqabbal minnee",
                    R.string.text_an_animal_1
                )
            )

            //Ramadan
            //When openning your fast
            listDuas.add(
                DuasEntity(
                    79,
                    12,
                    58,
                    "اللَّهُمَّ اِنِّى لَكَ صُمْتُ وَبِكَ امنْتُ وَعَليْكَ تَوَكّلتُ وَ عَلى رِزْقِكَ اَفْطَرْتُ",
                    "Allahumma inni laka sumtu wa bika aamantu wa alayka tawakkaltu wa ala rizq-ika-aftartu",
                    R.string.text_openning_your_fast_1
                )
            )
            //For the night of destiny
            listDuas.add(
                DuasEntity(
                    80,
                    12,
                    59,
                    "اَللَّهُمَّ اِنَّكَ عَفُوٌّ ، تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي",
                    "Allahumma innaka `afuwwun tuhibbul `afwa fa`fu `annee",
                    R.string.text_of_destiny_1
                )
            )
            //For someone who provides you with Iftar
            listDuas.add(
                DuasEntity(
                    81,
                    12,
                    60,
                    "أَفْطَـرَ عِنْدَكُم الصّـائِمونَ وَأَكَلَ طَعامَـكُمُ الأبْـرار، وَصَلَّـتْ عَلَـيْكُمُ الملائِكَـة",
                    "Aftara 'indakumus-saa'imoona, wa 'akala ta'aamakumul-'abraaru , wa sallat 'alaykumul-malaa'ikatu",
                    R.string.text_you_with_iftar_1
                )
            )
            //on the sighting of the crescent moon
            listDuas.add(
                DuasEntity(
                    82,
                    12,
                    61,
                    "اللهُ أَكْـبَر، اللّهُمَّ أَهِلَّـهُ عَلَيْـنا بِالأمْـنِ وَالإيمـان، والسَّلامَـةِ والإسْلامِ، وَالتَّـوْفيـقِ لِما تُحِـبُّ رَبُّنـا وَتَـرْضَـى، رَبُّنـا وَرَبُّكَ اللهُ",
                    "Allaahu 'Akbar, Allaahumma 'ahillahu 'alayna bil'amni wal'eemaani, wassalaamati wal-'Islaami, wattawfeeqi limaa tuhibbu Rabbanaa wa tardhaa, Rabbunaa wa Rabbukallaahu.",
                    R.string.text_crescent_moon_1
                )
            )

            //Nature
            //For rain
            listDuas.add(
                DuasEntity(
                    83,
                    13,
                    62,
                    "اللّهُمَّ اَسْقِـنا غَيْـثاً مُغيـثاً مَريئاً مُريـعاً، نافِعـاً غَيْـرَ ضَّارٌ، عاجِـلاً غَـيْرَ آجِلٍ",
                    "Allaahumma 'asqinaa ghaythan mugheethan maree'an maree'an, naafi'an ghayradhaarrin, 'aajilan ghayra 'aajilin.",
                    R.string.text_for_rain_1
                )
            )
            //when it rains
            listDuas.add(
                DuasEntity(
                    113,
                    13,
                    63,
                    "اللّهُمَّ صَيِّـباً نافِـعاً",
                    "Allaahumma sayyiban naafi'an.",
                    R.string.text_it_rains_1
                )
            )
            //after rainfall
            listDuas.add(
                DuasEntity(
                    84,
                    13,
                    64,
                    "مُطِرْنَا بِفَضْلِ اللهِ وَ رَحْمَتِهِ",
                    "Mutirnaa bifadhlillaahi wa rahmatihi.",
                    R.string.text_after_rainfall_1
                )
            )
            listDuas.add(
                DuasEntity(
                    85,
                    13,
                    64,
                    "اللّهُمَّ صَيِّـباً نافِـعاً",
                    "Allaahumma sayyiban naafi'an.",
                    R.string.text_after_rainfall_2
                )
            )
            listDuas.add(
                DuasEntity(
                    86,
                    13,
                    64,
                    "اللّهُمَّ حَوالَيْنا وَلا عَلَيْـنَا، اللّهُمَّ عَلى الآكَـامِ وَالظِّـرابِ، وَبُطُـونِِ الأوْدِيةِ، وَمَنـابِِتِ الشَّجَـرِ",
                    "Allaahumma hawaalaynaa wa laa 'alaynaa. Allaahumma 'alal-'aakaami wadh-dhiraabi, wa butoonil-'awdiyati, wa manaabitish-shajari",
                    R.string.text_after_rainfall_3
                )
            )
            //when there is excessive rain
            listDuas.add(
                DuasEntity(
                    114,
                    13,
                    65,
                    "اللّهُمَّ حَوالَيْنا وَلا عَلَيْـنَا، اللّهُمَّ عَلى الآكَـامِ وَالظِّـرابِ، وَبُطُـونِِ الأوْدِيةِ، وَمَنـابِِتِ الشَّجَـرِ",
                    "Allaahumma hawaalaynaa wa laa 'alaynaa. Allaahumma 'alal-'aakaami wadh-dhiraabi, wa butoonil-'awdiyati, wa manaabitish-shajari",
                    R.string.text_after_rainfall_3
                )
            )
            //Upon hearing thunder
            listDuas.add(
                DuasEntity(
                    87,
                    13,
                    66,
                    "سُبْـحانَ الّذي يُسَبِّـحُ الـرَّعْدُ بِحَمْـدِهِ، وَالملائِكـةُ مِنْ خيـفَته",
                    "Subhaanal-lathee yusabbihur-ra'du bihamdihi walmalaa'ikatu min kheefatihi",
                    R.string.text_hearing_thunder_1
                )
            )
            //During a windstorm
            listDuas.add(
                DuasEntity(
                    88,
                    13,
                    67,
                    "اللّهُـمَّ إِنَّـي أَسْـأَلُـكَ خَيْـرَها، وَأَعـوذُ بِكَ مِنْ شَـرِّها",
                    "Allaahumma 'innee 'as'aluka khayrahaa, wa 'a'oothu bika min sharrihaa",
                    R.string.text_a_windstorm_1
                )
            )

            //Good ettiquete
            //For greeting people
            listDuas.add(
                DuasEntity(
                    88,
                    14,
                    68,
                    "السلام عليكم ورحمة الله وبركاته",
                    "Assalamu ‘alaikum wa rahmatullahi wa barakatuh",
                    R.string.text_greeting_people_1
                )
            )
            //When responding to Salam
            listDuas.add(
                DuasEntity(
                    88,
                    14,
                    69,
                    "و عليكم السلام والرحمة الله وبركاته",
                    "Wa’alaikum assalam wa rahmatullahi wa barakatuh",
                    R.string.text_to_Salam_1
                )
            )
            //For thanking someone
            listDuas.add(
                DuasEntity(
                    88,
                    14,
                    70,
                    "جَزاكَ اللهُ خَـيْراً",
                    "Jazaakallaahu khayran",
                    R.string.text_thanking_someone_1
                )
            )
            //upon hearing someone sneeze
            listDuas.add(
                DuasEntity(
                    120,
                    14,
                    71,
                    "يَرْحَمُكَ اللَّهُ",
                    "Yar hamu kal-laahu.",
                    R.string.text_someone_sneeze_1
                )
            )
            //Upon sneezing
            listDuas.add(
                DuasEntity(
                    89,
                    14,
                    72,
                    "اَلْحَمْدُ لِلَّهِ",
                    "Alhamdu lillaahi",
                    R.string.text_Upon_sneezing_1
                )
            )
            //Sneezers replies back
            listDuas.add(
                DuasEntity(
                    90,
                    14,
                    73,
                    "يَهْدِيكُمُ اللَّهُ وَيُصْلِحُ بَالَكُمْ",
                    "Yahdeekumul-lahu wayuslihu balakum",
                    R.string.text_replies_back_1
                )
            )
            //At the end of a Gathering/Majlis
            listDuas.add(
                DuasEntity(
                    91,
                    14,
                    74,
                    "سُبْحـانَكَ اللّهُـمَّ وَبِحَمدِك، أَشْهَـدُ أَنْ لا إِلهَ إِلاّ أَنْتَ أَسْتَغْفِرُكَ وَأَتوبُ إِلَـيْك",
                    "Subhaanaka Allaahumma wa bihamdika, 'ash-hadu 'an laa 'ilaaha 'illaa 'Anta, 'astaghfiruka wa 'atoobu 'ilayka",
                    R.string.text_a_Gathering_1
                )
            )
            //When entering market
            listDuas.add(
                DuasEntity(
                    92,
                    14,
                    75,
                    "لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ يُحْيِي وَيُمِيتُ وَهُوَ حَيٌّ لَا يَمُوتُ بِيَدِهِ الْخُيْرُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٍ",
                    "Laa ilaaha illallaahu wahdahu laa shareeka lahu, lahul-mulku wa lahul-hamdu, yuhyee wa yumeetu, wa Huwa hayyun laa yamootu, biyadihil-khayru, wa Huwa alaa kulli shayin Qadeer.",
                    R.string.text_entering_market_1
                )
            )
            //When someone praises you
            listDuas.add(
                DuasEntity(
                    93,
                    14,
                    76,
                    "اللَّهُمّ لاَ تُؤاَخِذْنِي ِبَما يَقُولُونُ ، وَاغْفِرْ لِي مَالا يَعْلَمُونَ وَاجْعَلْنِي خَيْراً مِمَّا َيظُنُّونَ",
                    "Allaahumma laa tu'aakhithnee bimaa yaqooloona, waghfir lee maa laa ya'lamoona [waj'alnee khayram-mimmaa yadhunnoon]",
                    R.string.text_praises_you_1
                )
            )

            //decision / guidance
            //For Istikhara
            listDuas.add(
                DuasEntity(
                    94,
                    15,
                    77,
                    "اللَّهُمَّ إِنِّي أَسْتَخِيرُكَ بِعِلْمَكَ، وَأَسْتَقْدِرُكَ بِقُدْرَتِكَ، وَأَسْأَلُكَ مِنْ فَضْلِكَ الْعَظِيمِ، فَإِنَّكَ تَقْدِرُ وَلَا أَقْدِرُ، وَتَعْلَمُ، وَلَا أَعْلَمُ، وَأَنْتَ عَلَّامُ الْغُيُوبِ، اللَّهُمَّ إِنْ كُنْتَ تَعْلَمُ أَنَّ هَذَا الْأَمْرَ- خَيْرٌ لِي فِي دِينِي وَمَعَاشِي وَعَاقِبَةِ أَمْرِي- عَاجِلِهِ وَآجِلِهِ- فَاقْدُرْهُ لِي وَيَسِّرْهُ لِي ثُمَّ بَارِكْ لِي فِيهِ، وَإِنْ كُنْتَ تَعْلَمُ أَنَّ هَذَا الْأَمْرَ شَرٌّ لِي فِي دِينِي وَمَعَاشِي وَعَاقِبَةِ أَمْرِي- عَاجِلِهِ وَآجِلِهِ- فَاصْرِفْهُ عَنِّي وَاصْرِفْنِي عَنْهُ وَاقْدُرْ لِيَ الْخَيْرَ حَيْثُ كَانَ ثُمَّ أَرْضِنِي بِهِ",
                    "Allaahumma 'innee 'astakheeruka bi'ilmika, wa 'astaqdiruka biqudratika, wa 'as'aluka min fadhtikal-'Adheemi, fa'innaka taqdiru wa laa 'aqdiru, wa ta'lamu, wa laa 'a'lamu, wa 'Anta 'Allaamul-Ghuyoobi, Allaahumma 'in kunta ta'lamu 'anna haathal-'amra-[then mention the thing to be decided] Khayrun lee fee deenee wa ma'aashee wa 'aaqibati 'amree - [or say] 'Aajilihi wa 'aajilihi - Faqdurhu lee wa yassirhu lee thumma baarik lee feehi, wa 'in kunta ta'lamu 'anna haathal-'amra sharrun lee fee deenee wa ma'aashee wa 'aaqibati 'amree - [or say] 'Aajilihi wa 'aajilihi - Fasrifhu 'annee wasrifnee 'anhu waqdur liyal-khayra haythu kaana thumma 'ardhinee bihi",
                    R.string.text_For_Istikhara_1
                )
            )
            //For Justice
            listDuas.add(
                DuasEntity(
                    95,
                    15,
                    78,
                    "رَبَّنَا افْتَحْ بَيْنَنَا وَبَيْنَ قَوْمِنَا بِالْحَقِّ وَأَنتَ خَيْرُ الْفَاتِحِينَ",
                    "Rabbanaf-tah baynana wa bayna qawmina bil haqqi wa anta Khayrul Fatiheen",
                    R.string.text_For_Justice_1
                )
            )
            //Seek Allah's guidance
            listDuas.add(
                DuasEntity(
                    96,
                    15,
                    79,
                    "الْحَمْدُ لِلَّهِ الَّذِي هَدَانَا لِهَٰذَا وَمَا كُنَّا لِنَهْتَدِيَ لَوْلَا أَنْ هَدَانَا اللَّهُ",
                    "Alhamdu lillahi allathee hadaana lihatha wama kunna linahtadiya lawlaa an hadaana Allahu",
                    R.string.text_guidance_1
                )
            )
            listDuas.add(
                DuasEntity(
                    97,
                    15,
                    79,
                    "فَاللَّهُ خَيْرٌ حَافِظًا ۖ وَهُوَ أَرْحَمُ الرَّاحِمِينَ",
                    "fallahu khayrun hafithan wahuwa arhamu arrahimeen",
                    R.string.text_guidance_2
                )
            )
            //Seek Allah's satisfaction
            listDuas.add(
                DuasEntity(
                    98,
                    15,
                    80,
                    "رَبَّنَا تَقَبَّلْ مِنَّا إِنَّكَ أَنْتَ السَّمِيعُ العَلِيمُ",
                    "Rabbana taqabbal minna innaka antas Sameeaul Aleem",
                    R.string.text_Upon_sneezing_1
                )
            )
            listDuas.add(
                DuasEntity(
                    99,
                    15,
                    80,
                    "رَبَّنَا وَاجْعَلْنَا مُسْلِمَيْنِ لَكَ وَمِن ذُرِّيَّتِنَا أُمَّةً مُّسْلِمَةً لَّكَ وَأَرِنَا مَنَاسِكَنَا وَتُبْ عَلَيْنَآ إِنَّكَ أَنتَ التَّوَّابُ الرَّحِيمُ",
                    "Rabbana wa-j'alna Muslimayni laka wa min Dhurriyatina 'Ummatan Muslimatan laka wa 'Arina Manasikana wa tub 'alayna 'innaka 'antat-Tawwabu-Raheem",
                    R.string.text_satisfaction_2
                )
            )
            //For success
            listDuas.add(
                DuasEntity(
                    100,
                    15,
                    81,
                    "وَمَا تَوْفِيقِي إِلَّا بِاللَّهِ ۚ عَلَيْهِ تَوَكَّلْتُ وَإِلَيْهِ أُنِيبُ",
                    "Wama tawfeeqee illa billahiAAalayhi tawakkaltu wa-ilayhi oneeb",
                    R.string.text_for_success_1
                )
            )
            //strengthen your imaan
            listDuas.add(
                DuasEntity(
                    101,
                    15,
                    82,
                    "رَضِيتُ باللَّهِ رَبًّا، وَبِالْإِسْلَامِ دِيناً، وَبِمُحَمَّدٍ صَلَى اللَّهُ عَلِيهِ وَسَلَّمَ نَبِيَّاً. (ثلاثاً)",
                    "Radeetu billahi rabban wabil-islami deenan wabiMuhammadin peace be upon to him nabiyya.",
                    R.string.text_your_imaan_1
                )
            )
            listDuas.add(
                DuasEntity(
                    102,
                    15,
                    82,
                    "َعُوْذُ بِاللهِ",
                    "`A'udhu billah",
                    R.string.text_your_imaan_2
                )
            )
            listDuas.add(
                DuasEntity(
                    103,
                    15,
                    82,
                    "آمَنْـتُ بِاللهِ وَرُسُـلِه",
                    "Aamantu billaahi wa Rusulihi",
                    R.string.text_your_imaan_3
                )
            )
            listDuas.add(
                DuasEntity(
                    104,
                    15,
                    82,
                    "رَبَّنَا لَا تُزِغْ قُلُوْبَنَا بَعْدَ اِذْ ھَدَيْتَنَا وَھَبْ لَنَا مِنْ لَّدُنْكَ رَحْمَةً ۚ اِنَّكَ اَنْتَ الْوَھَّابُ",
                    "Rabbanaa Laa Tuzigh Quloobanaa Ba’-Da Id’hadaytanaa Wa Hab Lanaa Mil Ladunka Rah’mah Innaka Antal Wahaab",
                    R.string.text_your_imaan_4
                )
            )
            listDuas.add(
                DuasEntity(
                    105,
                    15,
                    82,
                    "رَبَّنَا اغْفِرْ لَنَا ذُنُوْبَنَا وَ اِسْرَافَنَا فِيْٓ اَمْرِنَا وَثَبِّتْ اَقْدَامَنَا وَانْصُرْنَا عَلَي الْقَوْمِ الْكٰفِرِيْنَ",
                    "Rabbanaghfir lanaa d’unoobanaa wa israafanaa fee amrinaa wa thabbit aqdaamanaa wan s’urnaa a’lal qawmil kaafireen",
                    R.string.text_your_imaan_5
                )
            )

            listDuas.forEach {
                insertDailyDuas(it)
            }
        }

    }


    private fun insertDailyDuas(dailyDuasEntity: DuasEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            duasRepository.insertDuas(dailyDuasEntity)
        }
    }
}