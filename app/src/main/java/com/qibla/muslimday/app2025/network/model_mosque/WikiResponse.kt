package com.qibla.muslimday.app2025.network.model_mosque

data class WikiResponse(
    val claims: Map<String, List<Claim>>
)

data class Claim(
    val mainsnak: MainSnak
)

data class MainSnak(
    val datavalue: DataValue
)

data class DataValue(
    val value: String
)