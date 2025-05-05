package com.qibla.muslimday.app2025.ui.intro

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class IntroViewModel : ViewModel() {

    private var _positionState = MutableStateFlow<Int?>(0)
    val positionState = _positionState.asStateFlow()

    var currentItem = 0
    var maxSize = 4

    init {

    }

    fun setPosition(position: Int) {
        _positionState.value = position
    }

    fun resetPosition() {
        _positionState.value = null
    }


}