package com.qibla.muslimday.app2025.network.model_mosque

data class MosqueWithImage(
    val name: String,
    val lat: Double,
    val lon: Double,
    val address: String,
    val imageUrl: String?,
    val distance: String = ""
)