package com.qibla.muslimday.app2025.repository

import android.content.Context
import android.location.Location
import android.net.Uri
import android.util.Log
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.network.MosqueService
import com.qibla.muslimday.app2025.network.NominatimService
import com.qibla.muslimday.app2025.network.WikimediaApiService
import com.qibla.muslimday.app2025.network.model_mosque.MosqueWithImage
import javax.inject.Inject

class MosqueRepository @Inject constructor(
    private val overpassService: MosqueService,
    private val nominatimService: NominatimService,
    private val wikiService: WikimediaApiService
) {

    suspend fun getMosques(context: Context, lat: Double, lon: Double): List<MosqueWithImage> {
        val query =
            "[out:json];node[\"amenity\"=\"place_of_worship\"][\"religion\"=\"muslim\"](around:500000,$lat,$lon);out body;"
        val mosqueList = overpassService.getMosques(query).elements

        return mosqueList.map { mosque ->
            Log.e("wikiID", "wiki${mosque.tags?.wikidata}")
            Log.e("wikiID", "nameEn${mosque.tags?.nameEn}")
            Log.e("wikiID", "name${mosque.tags?.name}")

            val wikidataId = mosque.tags?.wikidata
            val imageUrl = wikidataId?.let { getWikiImage(it) }
            val address = getAddress(mosque.lat, mosque.lon)
            val distance1 = calculateDistance(lat, lon, mosque.lat, mosque.lon)
//            Log.e("wikiID","address$address")
            Log.e("wikiID", "imageUrl$imageUrl")
            MosqueWithImage(
                name = mosque.tags?.name ?: context.getString(R.string.str_unknown),
                lat = mosque.lat,
                lon = mosque.lon,
                address = address,
                imageUrl = imageUrl,
                distance = distance1
            )
        }
    }

    private suspend fun getWikiImage(wikidataId: String): String? {
        val response = wikiService.getMosqueImage(entityId = wikidataId)
        return response.claims["P18"]?.firstOrNull()?.mainsnak?.datavalue?.value?.let { imageName ->
            "https://commons.wikimedia.org/wiki/Special:FilePath/${Uri.encode(imageName)}"
        }
    }

    suspend fun getAddress(lat: Double, lon: Double): String {
        return nominatimService.getAddress(lat = lat, lon = lon).displayName ?: ""
    }

    fun calculateDistance(
        currentLat: Double, currentLon: Double,
        mosqueLat: Double, mosqueLon: Double
    ): String {
        val results = FloatArray(1)
        Location.distanceBetween(currentLat, currentLon, mosqueLat, mosqueLon, results)

        val distanceInMeters = results[0]
        val distanceInKm = distanceInMeters / 1000 // Đổi sang km
        return String.format("%.2f km", distanceInKm) // Hiển thị dạng `xx.xx km`
    }
}