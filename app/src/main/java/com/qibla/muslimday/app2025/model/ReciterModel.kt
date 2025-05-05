package com.qibla.muslimday.app2025.model

data class ReciterModel(
    var id: Int,
    var image: Int,
    var name: String,
    var description: String,
    var active: Boolean = false,
)