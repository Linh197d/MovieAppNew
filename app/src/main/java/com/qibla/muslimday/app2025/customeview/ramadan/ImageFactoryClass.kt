package com.qibla.muslimday.app2025.customeview.ramadan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

object ImageFactoryClass {
    private val map = HashMap<Int, Bitmap>()
    fun getBitmapFull(context: Context, i: Int): Bitmap? {
        if (map.containsKey(Integer.valueOf(i))) {
            return map[Integer.valueOf(i)]
        }
        val options = BitmapFactory.Options()
        val decodeResource = BitmapFactory.decodeResource(context.resources, i)
        map[Integer.valueOf(i)] = decodeResource
        return decodeResource
    }

    fun getBitmap(context: Context, i: Int): Bitmap? {
        if (map.containsKey(Integer.valueOf(i))) {
            return map[Integer.valueOf(i)]
        }
        val options = BitmapFactory.Options()
        val decodeResource =
            BitmapFactory.decodeResource(context.resources, i, options)
        map[Integer.valueOf(i)] = decodeResource
        return decodeResource
    }
}