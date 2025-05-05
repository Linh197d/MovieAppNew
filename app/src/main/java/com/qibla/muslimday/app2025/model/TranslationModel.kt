package com.qibla.muslimday.app2025.model

data class TranslationModel(
    var id: Int,
    var image: Int,
    var name: String,
    var active: Boolean = false,
)