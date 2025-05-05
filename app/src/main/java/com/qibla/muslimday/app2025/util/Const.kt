package com.qibla.muslimday.app2025.util



class Const {
    companion object {

        val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"

        @JvmField
        val REQUEST_CODE_ALARM_FAJR = 100

        @JvmField
        val REQUEST_CODE_ALARM_SUNRISE = 101

        @JvmField
        val REQUEST_CODE_ALARM_DHUHR = 102

        @JvmField
        val REQUEST_CODE_ALARM_ASR = 103

        @JvmField
        val REQUEST_CODE_ALARM_MAGHRIB = 104

        @JvmField
        val REQUEST_CODE_ALARM_ISHA = 105

        @JvmField
        val REQUEST_CODE_ALARM_UPDATE_FAJR = 200

        @JvmField
        val REQUEST_CODE_ALARM_UPDATE_SUNRISE = 201

        @JvmField
        val REQUEST_CODE_ALARM_UPDATE_DHUHR = 202

        @JvmField
        val REQUEST_CODE_ALARM_UPDATE_ASR = 203

        @JvmField
        val REQUEST_CODE_ALARM_UPDATE_MAGHRIB = 204

        @JvmField
        val REQUEST_CODE_ALARM_UPDATE_ISHA = 205

        val MEDIA_IDLE = 0
        val MEDIA_PLAYING = 1
        val MEDIA_STOP = 2
        var isCheck = false
        var isCheckWifi: Int = 0
        var address = ""

        @JvmField
        var timeId = ""

        @JvmField
        var latitude: Double = 0.0

        @JvmField
        var longitude: Double = 0.0

        const val MEDIA_QURAN_IDLE = 3
        const val MEDIA_QURAN_PLAYING = 4
        const val MEDIA_QURAN_STOP = 5
        const val MEDIA_QURAN_PAUSE = 6


        val NOT_COLORIZE = IntArray(0)

        const val TAG = "RAMADAN_MAKER"
        const val LOGO_WITH_APP = "Basic Shapes"


    }
}