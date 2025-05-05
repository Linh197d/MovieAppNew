package com.qibla.muslimday.app2025.domain.draw

import androidx.annotation.Keep

@Keep
open interface DataJSONConst {
    companion object {
        val ART_INFO_JSON: String = "art_info"
        val BG_JSON_STRING: String = "bg_json"
        val FILTER_JSON: String = "filte_json"
        val INTENT_IS_PATH_AVAILABLE: String = "is path there"
        val INTENT_LOADED_FROM_ASSETS: String = "from_assets"
        val INTENT_PATH_STRING: String = "path_string"
        val STICKERS_JSON_ARRAY: String = "stickers_array"
        val TEXT_JSON_ARRAY: String = "text_array"
        val TOUCH_JSON_ARRAY: String = "touch_points_array"
        val INTENT_LOADED_FROM_ASSETS_LOGO: String = "from_assets_logo"
        val INTENT_NAME_FILE_LOGO: String = "INTENT_NAME_FILE_LOGO"
        val INTENT_IS_EDIT: String = "INTENT_IS_EDIT"
    }
}