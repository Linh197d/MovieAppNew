package com.qibla.muslimday.app2025.network.model_mosque

import com.google.gson.annotations.SerializedName

data class NominatimResponse(
    @SerializedName("display_name") val displayName: String?
)