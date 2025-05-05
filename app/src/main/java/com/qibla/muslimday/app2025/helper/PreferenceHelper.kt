package com.qibla.muslimday.app2025.helper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface SharedPreferenceHelper {
    fun setString(key: String, value: String)
    fun getString(key: String): String?

    fun setInt(key: String, value: Int)
    fun getInt(key: String): Int?

    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean?

}

@Singleton
class PreferenceHelper @Inject constructor(
    @ApplicationContext context: Context,
) : SharedPreferenceHelper {
    companion object {
        const val APP_PREFS = "app_prefs"
        const val PREF_CURRENT_LANGUAGE = "pref_current_language"
        const val PREF_SHOWED_START_LANGUAGE = "pref_showed_start_language"

        const val CALCULATION_METHOD = "calculation_method"
        const val CALCULATION_METHOD_NAME = "calculation_method_name"

        const val JURISTIC_METHOD = "juristic_method"
        const val JURISTIC_METHOD_NAME = "juristic_method_name"

        const val PAGE_BACKGROUND_FAJR = "page_background_fajr"
        const val PAGE_BACKGROUND_SUNRISE = "page_background_sunrise"
        const val PAGE_BACKGROUND_DHUHR = "page_background_dhuhr"
        const val PAGE_BACKGROUND_ASR = "page_background_asr"
        const val PAGE_BACKGROUND_MAGHRIB = "page_background_maghrib"
        const val PAGE_BACKGROUND_ISHA = "page_background_isha"

        const val VIBRATE_FAJR = "vibrate_fajr"
        const val VIBRATE_SUNRISE = "vibrate_sunrise"
        const val VIBRATE_DHUHR = "vibrate_dhuhr"
        const val VIBRATE_ASR = "vibrate_asr"
        const val VIBRATE_MAGHRIB = "vibrate_maghrib"
        const val VIBRATE_ISHA = "vibrate_isha"

        const val SOUND_FAJR = "sound_fajr"
        const val SOUND_SUNRISE = "sound_sunrise"
        const val SOUND_DHUHR = "sound_dhuhr"
        const val SOUND_ASR = "sound_asr"
        const val SOUND_MAGHRIB = "sound_maghrib"
        const val SOUND_ISHA = "sound_isha"

        const val TIME_PRE_NOTIFICATION_FAJR = "time_pre_notification_fajr"
        const val TIME_PRE_NOTIFICATION_SUNRISE = "time_pre_notification_sunrise"
        const val TIME_PRE_NOTIFICATION_DHUHR = "time_pre_notification_dhuhr"
        const val TIME_PRE_NOTIFICATION_ASR = "time_pre_notification_asr"
        const val TIME_PRE_NOTIFICATION_MAGHRIB = "time_pre_notification_maghrib"
        const val TIME_PRE_NOTIFICATION_ISHA = "time_pre_notification_isha"

        const val IS_NOTIFY_PRAY_TIME_FAJR = "is_notify_fajr"
        const val IS_NOTIFY_PRAY_TIME_SUNRISE = "is_notify_sunrise"
        const val IS_NOTIFY_PRAY_TIME_DHUHR = "is_notify_dhuhr"
        const val IS_NOTIFY_PRAY_TIME_ASR = "is_notify_asr"
        const val IS_NOTIFY_PRAY_TIME_MAGHRIB = "is_notify_maghrib"
        const val IS_NOTIFY_PRAY_TIME_ISHA = "is_notify_isha"
        const val NOTIFY_APP_KEY = "NOTIFY_APP_KEY"

        const val PRAYING_TIME_ADJUSTMENT_FAJR = "praying_time_adjustment_fajr"
        const val PRAYING_TIME_ADJUSTMENT_SUNRISE = "praying_time_adjustment_sunrise"
        const val PRAYING_TIME_ADJUSTMENT_DHUHR = "praying_time_adjustment_dhuhr"
        const val PRAYING_TIME_ADJUSTMENT_ASR = "praying_time_adjustment_asr"
        const val PRAYING_TIME_ADJUSTMENT_MAGHRIB = "praying_time_adjustment_maghrib"
        const val PRAYING_TIME_ADJUSTMENT_ISHA = "praying_time_adjustment_isha"
        const val PRAYING_TIME_ADJUSTMENT_ALL = "praying_time_adjustment_all"

        const val IS_UPDATE_LOCATION_AND_TIME = "is_update_location_and_time"
        const val IS_FIXED_COUNTDOWN_ON_NOTIFICATION = "is_fixed_countdown_on_notification"
        const val IS_ENABLE_AUDIO = "is_enable_audio"
        const val IS_AUTO_SCROLL_WITH_AUDIO = "is_scroll_with_audio"

        const val IS_PHONETIC_TRANSLATION = "is_phonetic_translation"
        const val IS_LANGUAGE_TRANSLATION = "is_language_translation"

        const val RECITER = "reciter"
        const val TRANSLATION = "translation"

        const val NUMBER_OF_ROUNDS = "number_of_rounds"
        const val NUMBER_OF_ROUNDS_CUSTOMIZE = "number_of_customize"

        const val IS_SOUND_TASBIH = "is_sound_tasbih"

        const val FONT_SIZE = "font_size"
        const val FONT_STYLE = "font_style"
        const val ISLAMIC_CALENDAR = "islamic_calendar"

        const val NAME_CONTINUE_READING_SURAH = "name_continue_reading_surah"
        const val ID_CONTINUE_READING_SURAH = "id_continue_reading_surah"
        const val NAME_CONTINUE_READING_JUZ = "name_continue_reading_juz"
        const val ID_CONTINUE_READING_JUZ = "id_continue_reading_juz"

        const val IS_ENABLE_WIFI = "is_enable_wifi"
        const val DATE_DAY = "date_day"
        const val IS_CHECK_PERMISSION_FIRST = "IS_CHECK_PERMISSION_FIRST"

        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val TIME_ID = "time_id"


        const val COUNT_EXIT = "count_exit"
        const val COUNT_BACK_HOME = "count_back_home"

        const val IS_SHOW_DIALOG_TARAWIH = "is_show_dialog_tarawih"
        const val IS_CHANGE_CURRENCY = "is_change_currency"

    }

    private val sharedPreferences by lazy {
        context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
    }

    fun forceRated() {
        sharedPreferences.edit().putBoolean("rate", true).commit()
    }

    fun isRate(): Boolean {
        return sharedPreferences.getBoolean("rate", false)
    }

    fun hideLanguage() {
        sharedPreferences.edit().putBoolean("show_language", false).apply()
    }

    fun showLanguage(): Boolean {
        return sharedPreferences.getBoolean("show_language", true)
    }

    fun hidePermission() {
        sharedPreferences.edit().putBoolean("show_permission", false).apply()
    }

    fun showPermission(): Boolean {
        return sharedPreferences.getBoolean("show_permission", true)
    }

    fun hideDialogTarawih() {
        sharedPreferences.edit().putBoolean(IS_SHOW_DIALOG_TARAWIH, false).apply()
    }

    fun showDialogTarawih(): Boolean {
        return sharedPreferences.getBoolean(IS_SHOW_DIALOG_TARAWIH, true)
    }

    fun setCurrency(s: String) {
        sharedPreferences.edit().putString(IS_CHANGE_CURRENCY, s).apply()
    }

    fun getCurrency(): String? {
        return sharedPreferences.getString(IS_CHANGE_CURRENCY, "$")
    }

    fun hideInterIntro() {
        sharedPreferences.edit().putBoolean("show_inter_intro", false).apply()
    }

    fun showInterIntro(): Boolean {
        return sharedPreferences.getBoolean("show_inter_intro", true)
    }

    fun getCalculationMethod(): Int {
        return sharedPreferences.getInt(CALCULATION_METHOD, 3)
    }

    fun setCalculationMethod(calculationMethod: Int) {
        sharedPreferences.edit().putInt(CALCULATION_METHOD, calculationMethod)
            .apply()
    }

    fun getJuristicMethod(): Int {
        return sharedPreferences.getInt(JURISTIC_METHOD, 0)
    }

    fun setJuristicMethod(juristicMethod: Int) {
        sharedPreferences.edit().putInt(JURISTIC_METHOD, juristicMethod)
            .apply()
    }

    fun getCalculationMethodName(): String? {
        return sharedPreferences.getString(CALCULATION_METHOD_NAME, "MWL - Muslim World League.")
    }

    fun setCalculationMethodName(calculationMethodName: String) {
        sharedPreferences.edit().putString(CALCULATION_METHOD_NAME, calculationMethodName)
            .apply()
    }

    // Set is notification pray

    fun getNotifyState(): Boolean {
        return sharedPreferences.getBoolean(NOTIFY_APP_KEY, true)
    }

    fun setNotifyState(isNotify: Boolean) {
        sharedPreferences.edit().putBoolean(NOTIFY_APP_KEY, isNotify)
            .apply()
    }

    fun getIsNotifyPrayTimeFajr(): Boolean {
        return sharedPreferences.getBoolean(IS_NOTIFY_PRAY_TIME_FAJR, true)
    }

    fun setIsNotifyPrayTimeFajr(isNotify: Boolean) {
        sharedPreferences.edit().putBoolean(IS_NOTIFY_PRAY_TIME_FAJR, isNotify)
            .apply()
    }

    fun getIsNotifyPrayTimeSunrise(): Boolean {
        return sharedPreferences.getBoolean(IS_NOTIFY_PRAY_TIME_SUNRISE, true)
    }

    fun setIsNotifyPrayTimeSunrise(isNotify: Boolean) {
        sharedPreferences.edit().putBoolean(IS_NOTIFY_PRAY_TIME_SUNRISE, isNotify)
            .apply()
    }

    fun getIsNotifyPrayTimeDhuhr(): Boolean {
        return sharedPreferences.getBoolean(IS_NOTIFY_PRAY_TIME_DHUHR, true)
    }

    fun setIsNotifyPrayTimeDhuhr(isNotify: Boolean) {
        sharedPreferences.edit().putBoolean(IS_NOTIFY_PRAY_TIME_DHUHR, isNotify)
            .apply()
    }

    fun getIsNotifyPrayTimeAsr(): Boolean {
        return sharedPreferences.getBoolean(IS_NOTIFY_PRAY_TIME_ASR, true)
    }

    fun setIsNotifyPrayTimeAsr(isNotify: Boolean) {
        sharedPreferences.edit().putBoolean(IS_NOTIFY_PRAY_TIME_ASR, isNotify)
            .apply()
    }

    fun getIsNotifyPrayTimeMaghrib(): Boolean {
        return sharedPreferences.getBoolean(IS_NOTIFY_PRAY_TIME_MAGHRIB, true)
    }

    fun setIsNotifyPrayTimeMaghrib(isNotify: Boolean) {
        sharedPreferences.edit().putBoolean(IS_NOTIFY_PRAY_TIME_MAGHRIB, isNotify)
            .apply()
    }

    fun getIsNotifyPrayTimeIsha(): Boolean {
        return sharedPreferences.getBoolean(IS_NOTIFY_PRAY_TIME_ISHA, true)
    }

    fun setIsNotifyPrayTimeIsha(isNotify: Boolean) {
        sharedPreferences.edit().putBoolean(IS_NOTIFY_PRAY_TIME_ISHA, isNotify)
            .apply()
    }

    // Set vibration notification
    fun getIsVibratePrayTimeIsha(): Boolean {
        return sharedPreferences.getBoolean(VIBRATE_ISHA, false)
    }

    fun setIsVibratePrayTimeIsha(isVibrate: Boolean) {
        sharedPreferences.edit().putBoolean(VIBRATE_ISHA, isVibrate)
            .apply()
    }

    fun getIsVibratePrayTimeMaghrib(): Boolean {
        return sharedPreferences.getBoolean(VIBRATE_MAGHRIB, false)
    }

    fun setIsVibratePrayTimeMaghrib(isVibrate: Boolean) {
        sharedPreferences.edit().putBoolean(VIBRATE_MAGHRIB, isVibrate)
            .apply()
    }

    fun getIsVibratePrayTimeAsr(): Boolean {
        return sharedPreferences.getBoolean(VIBRATE_ASR, false)
    }

    fun setIsVibratePrayTimeAsr(isVibrate: Boolean) {
        sharedPreferences.edit().putBoolean(VIBRATE_ASR, isVibrate)
            .apply()
    }

    fun getIsVibratePrayTimeDhuhr(): Boolean {
        return sharedPreferences.getBoolean(VIBRATE_DHUHR, false)
    }

    fun setIsVibratePrayTimeDhuhr(isVibrate: Boolean) {
        sharedPreferences.edit().putBoolean(VIBRATE_DHUHR, isVibrate)
            .apply()
    }

    fun getIsVibratePrayTimeSunrise(): Boolean {
        return sharedPreferences.getBoolean(VIBRATE_SUNRISE, false)
    }

    fun setIsVibratePrayTimeSunrise(isVibrate: Boolean) {
        sharedPreferences.edit().putBoolean(VIBRATE_SUNRISE, isVibrate)
            .apply()
    }

    fun getIsVibratePrayTimeFajr(): Boolean {
        return sharedPreferences.getBoolean(VIBRATE_FAJR, false)
    }

    fun setIsVibratePrayTimeFajr(isVibrate: Boolean) {
        sharedPreferences.edit().putBoolean(VIBRATE_FAJR, isVibrate)
            .apply()
    }

    // Set background notification
    fun getPageBackgroundFajr(): Int {
        return sharedPreferences.getInt(PAGE_BACKGROUND_FAJR, 0)
    }

    fun setPageBackgroundFajr(pageBackground: Int) {
        sharedPreferences.edit().putInt(PAGE_BACKGROUND_FAJR, pageBackground)
            .apply()
    }

    fun getPageBackgroundSunrise(): Int {
        return sharedPreferences.getInt(PAGE_BACKGROUND_SUNRISE, 0)
    }

    fun setPageBackgroundSunrise(pageBackground: Int) {
        sharedPreferences.edit().putInt(PAGE_BACKGROUND_SUNRISE, pageBackground)
            .apply()
    }

    fun getPageBackgroundDhuhr(): Int {
        return sharedPreferences.getInt(PAGE_BACKGROUND_DHUHR, 0)
    }

    fun setPageBackgroundDhuhr(pageBackground: Int) {
        sharedPreferences.edit().putInt(PAGE_BACKGROUND_DHUHR, pageBackground)
            .apply()
    }

    fun getPageBackgroundAsr(): Int {
        return sharedPreferences.getInt(PAGE_BACKGROUND_ASR, 0)
    }

    fun setPageBackgroundAsr(pageBackground: Int) {
        sharedPreferences.edit().putInt(PAGE_BACKGROUND_ASR, pageBackground)
            .apply()
    }

    fun getPageBackgroundMaghrib(): Int {
        return sharedPreferences.getInt(PAGE_BACKGROUND_MAGHRIB, 0)
    }

    fun setPageBackgroundMaghrib(pageBackground: Int) {
        sharedPreferences.edit().putInt(PAGE_BACKGROUND_MAGHRIB, pageBackground)
            .apply()
    }

    fun getPageBackgroundIsha(): Int {
        return sharedPreferences.getInt(PAGE_BACKGROUND_ISHA, 0)
    }

    fun setPageBackgroundIsha(pageBackground: Int) {
        sharedPreferences.edit().putInt(PAGE_BACKGROUND_ISHA, pageBackground)
            .apply()
    }

    // Set sound notification
    fun getSoundFajr(): Int {
        return sharedPreferences.getInt(SOUND_FAJR, 2)
    }

    fun setSoundFajr(sound: Int) {
        sharedPreferences.edit().putInt(SOUND_FAJR, sound)
            .apply()
    }

    fun getSoundSunrise(): Int {
        return sharedPreferences.getInt(SOUND_SUNRISE, 2)
    }

    fun setSoundSunrise(sound: Int) {
        sharedPreferences.edit().putInt(SOUND_SUNRISE, sound)
            .apply()
    }

    fun getSoundDhuhr(): Int {
        return sharedPreferences.getInt(SOUND_DHUHR, 2)
    }

    fun setSoundDhuhr(sound: Int) {
        sharedPreferences.edit().putInt(SOUND_DHUHR, sound)
            .apply()
    }

    fun getSoundAsr(): Int {
        return sharedPreferences.getInt(SOUND_ASR, 2)
    }

    fun setSoundAsr(sound: Int) {
        sharedPreferences.edit().putInt(SOUND_ASR, sound)
            .apply()
    }

    fun getSoundMaghrib(): Int {
        return sharedPreferences.getInt(SOUND_MAGHRIB, 2)
    }

    fun setSoundMaghrib(sound: Int) {
        sharedPreferences.edit().putInt(SOUND_MAGHRIB, sound)
            .apply()
    }

    fun getSoundIsha(): Int {
        return sharedPreferences.getInt(SOUND_ISHA, 2)
    }

    fun setSoundIsha(sound: Int) {
        sharedPreferences.edit().putInt(SOUND_ISHA, sound)
            .apply()
    }

    // Time Pre Notification

    fun getPrayingTimeAdjustmentAll(): Int {
        return sharedPreferences.getInt(PRAYING_TIME_ADJUSTMENT_ALL, 0)
    }

    fun setPrayingTimeAdjustmentAll(prayingTimeAdjustment: Int) {
        sharedPreferences.edit().putInt(PRAYING_TIME_ADJUSTMENT_ALL, prayingTimeAdjustment)
            .apply()
    }

    fun getPrayingTimeAdjustmentFajr(): Int {
        return sharedPreferences.getInt(PRAYING_TIME_ADJUSTMENT_FAJR, 0)
    }

    fun setPrayingTimeAdjustmentFajr(prayingTimeAdjustment: Int) {
        sharedPreferences.edit().putInt(PRAYING_TIME_ADJUSTMENT_FAJR, prayingTimeAdjustment)
            .apply()
    }

    fun getPrayingTimeAdjustmentSunrise(): Int {
        return sharedPreferences.getInt(PRAYING_TIME_ADJUSTMENT_SUNRISE, 0)
    }

    fun setPrayingTimeAdjustmentSunrise(prayingTimeAdjustment: Int) {
        sharedPreferences.edit().putInt(PRAYING_TIME_ADJUSTMENT_SUNRISE, prayingTimeAdjustment)
            .apply()
    }

    fun getPrayingTimeAdjustmentDhuhr(): Int {
        return sharedPreferences.getInt(PRAYING_TIME_ADJUSTMENT_DHUHR, 0)
    }

    fun setPrayingTimeAdjustmentDhuhr(prayingTimeAdjustment: Int) {
        sharedPreferences.edit().putInt(PRAYING_TIME_ADJUSTMENT_DHUHR, prayingTimeAdjustment)
            .apply()
    }

    fun getPrayingTimeAdjustmentAsr(): Int {
        return sharedPreferences.getInt(PRAYING_TIME_ADJUSTMENT_ASR, 0)
    }

    fun setPrayingTimeAdjustmentAsr(prayingTimeAdjustment: Int) {
        sharedPreferences.edit().putInt(PRAYING_TIME_ADJUSTMENT_ASR, prayingTimeAdjustment)
            .apply()
    }

    fun getPrayingTimeAdjustmentMaghrib(): Int {
        return sharedPreferences.getInt(PRAYING_TIME_ADJUSTMENT_MAGHRIB, 0)
    }

    fun setPrayingTimeAdjustmentMaghrib(prayingTimeAdjustment: Int) {
        sharedPreferences.edit().putInt(PRAYING_TIME_ADJUSTMENT_MAGHRIB, prayingTimeAdjustment)
            .apply()
    }

    fun getPrayingTimeAdjustmentIsha(): Int {
        return sharedPreferences.getInt(PRAYING_TIME_ADJUSTMENT_ISHA, 0)
    }

    fun setPrayingTimeAdjustmentIsha(prayingTimeAdjustment: Int) {
        sharedPreferences.edit().putInt(PRAYING_TIME_ADJUSTMENT_ISHA, prayingTimeAdjustment)
            .apply()
    }

    // Set praying time adjustment
    fun getTimePreNotificationFajr(): Int {
        return sharedPreferences.getInt(TIME_PRE_NOTIFICATION_FAJR, 0)
    }

    fun setTimePreNotificationFajr(timePreNotification: Int) {
        sharedPreferences.edit().putInt(TIME_PRE_NOTIFICATION_FAJR, timePreNotification)
            .apply()
    }

    fun getTimePreNotificationSunrise(): Int {
        return sharedPreferences.getInt(TIME_PRE_NOTIFICATION_SUNRISE, 0)
    }

    fun setTimePreNotificationSunrise(timePreNotification: Int) {
        sharedPreferences.edit().putInt(TIME_PRE_NOTIFICATION_SUNRISE, timePreNotification)
            .apply()
    }

    fun getTimePreNotificationDhuhr(): Int {
        return sharedPreferences.getInt(TIME_PRE_NOTIFICATION_DHUHR, 0)
    }

    fun setTimePreNotificationDhuhr(timePreNotification: Int) {
        sharedPreferences.edit().putInt(TIME_PRE_NOTIFICATION_DHUHR, timePreNotification)
            .apply()
    }

    fun getTimePreNotificationAsr(): Int {
        return sharedPreferences.getInt(TIME_PRE_NOTIFICATION_ASR, 0)
    }

    fun setTimePreNotificationAsr(timePreNotification: Int) {
        sharedPreferences.edit().putInt(TIME_PRE_NOTIFICATION_ASR, timePreNotification)
            .apply()
    }

    fun getTimePreNotificationMaghrib(): Int {
        return sharedPreferences.getInt(TIME_PRE_NOTIFICATION_MAGHRIB, 0)
    }

    fun setTimePreNotificationMaghrib(timePreNotification: Int) {
        sharedPreferences.edit().putInt(TIME_PRE_NOTIFICATION_MAGHRIB, timePreNotification)
            .apply()
    }

    fun getTimePreNotificationIsha(): Int {
        return sharedPreferences.getInt(TIME_PRE_NOTIFICATION_ISHA, 0)
    }

    fun setTimePreNotificationIsha(timePreNotification: Int) {
        sharedPreferences.edit().putInt(TIME_PRE_NOTIFICATION_ISHA, timePreNotification)
            .apply()
    }


    //Set is update location and time
    fun getIsUpdateLocationAndTime(): Boolean {
        return sharedPreferences.getBoolean(IS_UPDATE_LOCATION_AND_TIME, false)
    }

    fun setIsUpdateLocationAndTime(isUpdate: Boolean) {
        sharedPreferences.edit().putBoolean(IS_UPDATE_LOCATION_AND_TIME, isUpdate)
            .apply()
    }

    //Set is fixed countdown on notification
    fun getIsFixedCountdownOnNotification(): Boolean {
        return sharedPreferences.getBoolean(IS_FIXED_COUNTDOWN_ON_NOTIFICATION, false)
    }

    fun setIsFixedCountdownOnNotification(isFixedCountdownOnNotification: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IS_FIXED_COUNTDOWN_ON_NOTIFICATION, isFixedCountdownOnNotification)
            .apply()
    }

    //Set is enable audio
    fun getIsEnableAudio(): Boolean {
        return sharedPreferences.getBoolean(IS_ENABLE_AUDIO, true)
    }

    fun setIsEnableAudio(isEnableAudio: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IS_ENABLE_AUDIO, isEnableAudio)
            .apply()
    }

    //Set is auto_scroll_with_audio
    fun getIsScrollWithAudio(): Boolean {
        return sharedPreferences.getBoolean(IS_AUTO_SCROLL_WITH_AUDIO, true)
    }

    fun setIsScrollWithAudio(isScrollWithAudio: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IS_AUTO_SCROLL_WITH_AUDIO, isScrollWithAudio)
            .apply()
    }

    //Set reciter
    fun getReciter(): Int {
        return sharedPreferences.getInt(RECITER, 1)
    }

    fun setReciter(reciter: Int) {
        sharedPreferences.edit()
            .putInt(RECITER, reciter)
            .apply()
    }

    //Set translation
    fun getTranslation(): Int {
        return sharedPreferences.getInt(TRANSLATION, 5)
    }

    fun setTranslation(translation: Int) {
        sharedPreferences.edit()
            .putInt(TRANSLATION, translation)
            .apply()
    }

    //Set is phonetic translation
    fun getIsPhoneticTranslation(): Boolean {
        return sharedPreferences.getBoolean(IS_PHONETIC_TRANSLATION, true)
    }

    fun setIsPhoneticTranslation(isPhoneticTranslation: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IS_PHONETIC_TRANSLATION, isPhoneticTranslation)
            .apply()
    }

    //Set is language translation
    fun getIsLanguageTranslation(): Boolean {
        return sharedPreferences.getBoolean(IS_LANGUAGE_TRANSLATION, false)
    }

    fun setIsLanguageTranslation(isLanguageTranslation: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IS_LANGUAGE_TRANSLATION, isLanguageTranslation)
            .apply()
    }

    //Set font size
    //Set font size
    fun getFontStyle(): Int {
        return sharedPreferences.getInt(FONT_STYLE, 0)
    }

    fun setFontStyle(fontStyle: Int) {
        sharedPreferences.edit()
            .putInt(FONT_STYLE, fontStyle)
            .apply()
    }


    // Set number of rounds
    fun getNumberOfRounds(): Int {
        return sharedPreferences.getInt(NUMBER_OF_ROUNDS, 99)
    }

    fun setNumberOfRounds(numberOfRounds: Int) {
        sharedPreferences.edit().putInt(NUMBER_OF_ROUNDS, numberOfRounds)
            .apply()
    }

    // Set number of rounds customize
    fun getNumberOfRoundCustomize(): Int {
        return sharedPreferences.getInt(NUMBER_OF_ROUNDS_CUSTOMIZE, -1)
    }

    fun setNumberOfRoundCustomize(numberOfRoundCustomize: Int) {
        sharedPreferences.edit().putInt(NUMBER_OF_ROUNDS_CUSTOMIZE, numberOfRoundCustomize)
            .apply()
    }

    // set is sound tasbih
    fun getIsSoundTasbih(): Boolean {
        return sharedPreferences.getBoolean(IS_SOUND_TASBIH, true)
    }

    fun setIsSoundTasbih(isSoundTasbih: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IS_SOUND_TASBIH, isSoundTasbih)
            .apply()
    }

    // set name continue reading surah
    fun getNameContinueReadingSurah(): String? {
        return sharedPreferences.getString(NAME_CONTINUE_READING_SURAH, "")
    }

    fun setNameContinueReadingSurah(nameContinueReadingSurah: String) {
        sharedPreferences.edit().putString(NAME_CONTINUE_READING_SURAH, nameContinueReadingSurah)
            .commit()

    }

    fun getIdContinueReadingSurah(): Int {
        return sharedPreferences.getInt(ID_CONTINUE_READING_SURAH, 0)
    }

    fun setIdContinueReadingSurah(nameContinueReadingSurah: Int) {
        sharedPreferences.edit().putInt(ID_CONTINUE_READING_SURAH, nameContinueReadingSurah)
            .commit()
    }

    // set name continue reading juz
    fun getNameContinueReadingJuz(): String? {
        return sharedPreferences.getString(NAME_CONTINUE_READING_JUZ, "")
    }

    fun setNameContinueReadingJuz(nameContinueReadingJuz: String) {
        sharedPreferences.edit().putString(NAME_CONTINUE_READING_JUZ, nameContinueReadingJuz)
            .commit()

    } // set name continue reading juz

    fun getIdContinueReadingJuz(): Int? {
        return sharedPreferences.getInt(ID_CONTINUE_READING_JUZ, 0)
    }

    fun setIdContinueReadingJuz(nameContinueReadingJuz: Int) {
        sharedPreferences.edit().putInt(ID_CONTINUE_READING_JUZ, nameContinueReadingJuz)
            .commit()
    }

    fun getLatitude(): String? {
        return sharedPreferences.getString(LATITUDE, "")
    }

    fun setLatitude(latitude: String) {
        sharedPreferences.edit().putString(LATITUDE, latitude)
            .apply()
    }

    fun getLongitude(): String? {
        return sharedPreferences.getString(LONGITUDE, "")
    }

    fun setLongitude(longitude: String) {
        sharedPreferences.edit().putString(LONGITUDE, longitude)
            .apply()
    }

    fun getTimeId(): String? {
        return sharedPreferences.getString(TIME_ID, "")
    }

    fun setTimeId(timeId: String) {
        sharedPreferences.edit().putString(TIME_ID, timeId)
            .apply()
    }

    fun getCountExitApp(): Int {
        return sharedPreferences.getInt(COUNT_EXIT, 1)
    }

    fun increaseCountExitApp() {
        sharedPreferences.edit()
            .putInt(COUNT_EXIT, sharedPreferences.getInt(COUNT_EXIT, 1) + 1)
            .commit()
    }

    fun getCountBackScreen(): Int {
        return sharedPreferences.getInt(COUNT_BACK_HOME, 0)
    }

    fun increaseCountBackScreen() {
        sharedPreferences.edit().putInt(
            COUNT_BACK_HOME,
            sharedPreferences.getInt(COUNT_BACK_HOME, 0) + 1
        ).apply()
    }

    override fun setString(key: String, value: String) {
        sharedPreferences
            .edit()
            .putString(key, value)
            .apply()
    }


    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun setInt(key: String, value: Int) {
        sharedPreferences
            .edit()
            .putInt(key, value)
            .apply()
    }

    override fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    override fun setBoolean(key: String, value: Boolean) {
        sharedPreferences
            .edit()
            .putBoolean(key, value)
            .apply()
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    //Set font size
    fun getFontSize(): Int {
        return sharedPreferences.getInt(FONT_SIZE, 20)
    }

    fun setFontSize(fontSize: Int) {
        sharedPreferences.edit()
            .putInt(FONT_SIZE, fontSize)
            .apply()
    }

    fun getIsEnableConnectWifi(): Boolean {
        return sharedPreferences.getBoolean(IS_ENABLE_WIFI, false)
    }

    fun setIsEnableConnectWifi(isEnableAudio: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IS_ENABLE_WIFI, isEnableAudio)
            .apply()
    }
}