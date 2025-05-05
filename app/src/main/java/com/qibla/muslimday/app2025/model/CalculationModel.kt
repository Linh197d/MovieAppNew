package com.qibla.muslimday.app2025.model

data class CalculationModel(
    var id: Int,
    var calculation: String,
    var description: String,
    var active: Boolean = false,
)