package com.qibla.muslimday.app2025.util

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.Locale

object SystemUtil {
    private var myLocale: Locale? = null
    fun Int.dpToPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }

    /**
     * function reloads saved languages and changes them
     *
     */
    fun setLanguage(context: Context) {
        val language = getLanguage(context)
        if (language == "") {
            val configuration = Configuration()
            val locale = Locale.getDefault()
            Locale.setDefault(locale)
            configuration.locale = locale
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        } else {
            if (language != null) {
                changeLanguage(context, language)
            }
        }
    }

    /**
     * function change language
     *
     */
    private fun changeLanguage(context: Context, language: String) {
        if (language == "") {
            return
        } else {
            val preferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
            preferences.edit().putString("KEY_LANGUAGE", language).apply()
        }
        var mainCode = language
        var subCode = ""
        if (mainCode.split("-r").size > 1) {
            mainCode = language.split("-r")[0]
            subCode = language.split("-r")[1]
        }
        myLocale = Locale(mainCode, subCode)
        Locale.setDefault(myLocale)
        val config = Configuration()
        config.locale = myLocale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    /**
     * function change the selected language code
     *
     */
    fun setPreLanguage(context: Context, language: String) {
        if (language == "") {
            return
        } else {
            val preferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
            preferences.edit().putString("KEY_LANGUAGE", language).apply()
        }
    }

    /**
     * function get language
     *
     */
    fun getLanguage(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        Locale.getDefault().displayLanguage

        val language = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales[0].language
        } else {
            Resources.getSystem().configuration.locale.language
        }

        return if (getLanguageApp(context)
                .contains(language)
        ) {
            sharedPreferences.getString("KEY_LANGUAGE", "en")
        } else {
            sharedPreferences.getString("KEY_LANGUAGE", language)
        }
    }

    /**
     * function get language code app
     *
     */
    private fun getLanguageApp(context: Context): List<String> {
        val sharedPreferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        val openLanguage = sharedPreferences.getBoolean("openLanguage", false)
        val languages: MutableList<String> = ArrayList()
        if (openLanguage) {
            languages.add("en")
            languages.add("in")
            languages.add("ur")
            languages.add("hi")
            languages.add("tr")
        } else {
            languages.add("en")
            languages.add("in")
            languages.add("ur")
            languages.add("hi")
            languages.add("tr")
        }
        return languages
    }
}