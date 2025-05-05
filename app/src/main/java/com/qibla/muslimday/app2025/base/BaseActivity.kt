package com.qibla.muslimday.app2025.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.qibla.muslimday.app2025.util.SystemUtil

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: T

    var currentApiVersion = 0

    protected var isLoadNative = false

    override fun onCreate(savedInstanceState: Bundle?) {
        SystemUtil.setLanguage(this)
        currentApiVersion = Build.VERSION.SDK_INT
        val flags =
            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = flags
            val decorView = window.decorView
            decorView
                .setOnSystemUiVisibilityChangeListener { visibility ->
                    if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                        decorView.systemUiVisibility = flags
                    }
                }
        }
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(getInflatedLayout(layoutInflater))
        initView()
        println("currentScreen = ${this.javaClass.name}")
    }

    abstract fun setBinding(layoutInflater: LayoutInflater): T

    abstract fun initView()

    private fun getInflatedLayout(inflater: LayoutInflater): View {
        binding = setBinding(inflater)
        return binding.root
    }

    override fun onStop() {
        super.onStop()

        isLoadNative = true
    }

}