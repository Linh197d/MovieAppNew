package com.qibla.muslimday.app2025.domain.draw

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Path
import androidx.annotation.Keep

@Keep
class PathInfoClass(path: Path?, paint: Paint?) : DataJSONConst {
    var paint: Paint? = null
    var path: Path? = null
    var magicBrushBitmap: Bitmap? = null
        private set
    var magicResID = 0
        private set

    fun setMagicBrushBitmap(bitmap: Bitmap?, i: Int) {
        magicBrushBitmap = bitmap
        magicResID = i
    }

    init {
        this.paint = paint
        this.path = path
    }
}