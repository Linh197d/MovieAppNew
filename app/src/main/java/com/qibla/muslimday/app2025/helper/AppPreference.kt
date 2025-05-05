package com.qibla.muslimday.app2025.helper

import android.content.Context
import android.content.SharedPreferences

class AppPreference {

    private var sharedPreferences: SharedPreferences? = null

    companion object {
        const val FAKE_CHATS = "FAKE_CHATS"
        const val FIRST_TIME_IN_APP = "FIRST_TIME_IN_APP"
        const val IS_RATE = "IS_RATE"
        const val IS_DONE_FUN = "IS_DONE_FUN"
        const val IS_DONE_FUN_FIRST = "IS_DONE_FUN_FIRST"
        const val IS_FROM_SAVE_SCREEN = "IS_FROM_SAVE_SCREEN"
        const val INTRO_NOT_DONE = "INTRO_NOT_DONE"
        const val RATE = "RATE"
        const val NEW_ID_VIDEO = "NEW_ID_VIDEO"

        const val COUNT_RATE = "COUNT_RATE"
        const val COUNT_RATE_HOME = "COUNT_RATE_HOME"
        const val ID_ITEM = "ID_ITEM"
        const val FORCE_UPDATE_FIREBASE = "FORCE_UPDATE_FIREBASE"


        //graphic
        const val ACTION_ADD = "ACTION_ADD"
        const val ACTION_DELETE = "ACTION_DELETE"
        const val ACTION_SIZE = "ACTION_SIZE"
        const val ACTION_COLOR = "ACTION_COLOR"
        const val ACTION_ROTATION = "ACTION_ROTATION"
        const val ACTION_TRANS = "ACTION_TRANS"
        const val ACTION_SHADOW = "ACTION_SHADOW"
        const val ACTION_SHADOW_COLOR = "ACTION_SHADOW_COLOR"
        const val ACTION_SHADOW_RADIUS = "ACTION_SHADOW_RADIUS"
        const val ACTION_SHADOW_X = "ACTION_SHADOW_X"
        const val ACTION_SHADOW_Y = "ACTION_SHADOW_Y"
        const val ACTION_3D = "ACTION_3D"
        const val ACTION_3DX = "ACTION_3DX"
        const val ACTION_3DY = "ACTION_3DY"
        const val ACTION_FLIP = "ACTION_FLIP"

        //text
        const val ACTION_EDIT = "ACTION_EDIT"
        const val ACTION_FONT = "ACTION_FONT"
        const val ACTION_CHARACTER = "ACTION_CHARACTER"
        const val ACTION_LETTER_SPACING = "ACTION_LETTER_SPACING"
        const val ACTION_STROKE = "ACTION_STROKE"
        const val ACTION_STROKE_COLOR = "ACTION_STROKE_COLOR"
        const val ACTION_STROKE_SIZE = "ACTION_STROKE_SIZE"
        const val ACTION_CURVE = "ACTION_CURVE"
        const val ACTION_BOLD = "ACTION_BOLD"
        const val ACTION_ITALIC = "ACTION_ITALIC"


        const val ACTION_COPY = "ACTION_COPY"
        const val ACTION_GRADIENT_COLOR = "ACTION_GRADIENT_COLOR"
        const val ACTION_URI_BACKGROUND = "ACTION_URI_BACKGROUND"


        const val TIME_SHOW_ADS_INTER_PROJECT = "TIME_SHOW_ADS_INTER_PROJECT"
        const val TIME_SHOW_ADS_INTER_HOME = "TIME_SHOW_ADS_INTER_HOME"

        const val TIME_SHOW_ADS_INTER_CREATE = "TIME_SHOW_ADS_INTER_CREATE"
        const val TIME_SHOW_ADS_INTER_EDIT = "TIME_SHOW_ADS_INTER_EDIT"


        private var instance: AppPreference? = null
        fun init(context: Context) {
            instance = AppPreference()
            instance!!.sharedPreferences =
                context.getSharedPreferences(PreferenceHelper.APP_PREFS, Context.MODE_PRIVATE)
        }

        private fun getInstance(): AppPreference? {
            if (instance == null) instance = AppPreference()
            return instance
        }

        fun setMode(key: String, value: Int) {
            getInstance()!!.sharedPreferences!!.edit().putInt(key, value).apply()
        }

        fun getMode(key: String, def: Int = 0): Int {
            return getInstance()!!.sharedPreferences!!.getInt(key, def)
        }

        fun forceRated() {
            getInstance()!!.sharedPreferences!!.edit().putBoolean(IS_RATE, true).apply()
        }

        fun isRated(def: Boolean = false): Boolean {
            return getInstance()!!.sharedPreferences!!.getBoolean(IS_RATE, def)
        }

        fun haveDoneFun() {
            getInstance()!!.sharedPreferences!!.edit().putBoolean(IS_DONE_FUN, true).apply()
        }

        fun isDoneFun(def: Boolean = false): Boolean {
            return getInstance()!!.sharedPreferences!!.getBoolean(IS_DONE_FUN, def)
        }

        fun haveDoneFunFirst() {
            getInstance()!!.sharedPreferences!!.edit().putBoolean(IS_DONE_FUN_FIRST, true).apply()
        }

        fun isDoneFunFirst(def: Boolean = false): Boolean {
            return getInstance()!!.sharedPreferences!!.getBoolean(IS_DONE_FUN_FIRST, def)
        }

        fun increaseCountItemId() {
            getInstance()!!.sharedPreferences!!.edit().putInt(
                ID_ITEM,
                getInstance()!!.sharedPreferences!!.getInt(ID_ITEM, 0) + 1
            ).apply()
        }

        fun getCountItemId(): Int {
            return getInstance()!!.sharedPreferences!!.getInt(ID_ITEM, 0)
        }

        fun increaseCountRate() {
            getInstance()!!.sharedPreferences!!.edit().putInt(
                COUNT_RATE,
                getInstance()!!.sharedPreferences!!.getInt(COUNT_RATE, 0) + 1
            ).apply()
        }

        fun getCountRate(): Int {
            return getInstance()!!.sharedPreferences!!.getInt(COUNT_RATE, 0)
        }

        fun increaseCountRateHome() {
            getInstance()!!.sharedPreferences!!.edit().putInt(
                COUNT_RATE_HOME,
                getInstance()!!.sharedPreferences!!.getInt(COUNT_RATE_HOME, 0) + 1
            ).apply()
        }

        fun getCountRateHome(): Int {
            return getInstance()!!.sharedPreferences!!.getInt(COUNT_RATE_HOME, 0)
        }

        fun setStringPreference(key: String, value: String) {
            getInstance()!!.sharedPreferences!!.edit().putString(key, value).apply()
        }

        fun getStringPreference(key: String, def: String = ""): String {
            return getInstance()!!.sharedPreferences!!.getString(key, def)!!
        }

        fun setIntPreference(key: String, value: Int) {
            getInstance()!!.sharedPreferences!!.edit().putInt(key, value).apply()
        }

        fun getIntPreference(key: String, def: Int = 0): Int {
            return getInstance()!!.sharedPreferences!!.getInt(key, def)
        }

        fun setLongPreference(key: String, value: Long) {
            getInstance()!!.sharedPreferences!!.edit().putLong(key, value).apply()
        }

        fun getLongPreference(key: String, def: Long = 0): Long {
            return getInstance()!!.sharedPreferences!!.getLong(key, def)
        }

        fun setBooleanPreference(key: String, value: Boolean) {
            getInstance()!!.sharedPreferences!!.edit().putBoolean(key, value).apply()
        }

        fun getBooleanPreference(key: String, def: Boolean = true): Boolean {
            return getInstance()!!.sharedPreferences!!.getBoolean(key, def)
        }


    }
}