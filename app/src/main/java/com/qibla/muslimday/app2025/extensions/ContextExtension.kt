package com.qibla.muslimday.app2025.extensions

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.ConnectivityManager
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.sqrt

fun Context.hasNetworkConnection(): Boolean {
    var haveConnectedWifi = false
    var haveConnectedMobile = false
    val cm =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.allNetworkInfo
    for (ni in netInfo) {
        if (ni.typeName.equals("WIFI", ignoreCase = true))
            if (ni.isConnected) haveConnectedWifi = true
        if (ni.typeName.equals("MOBILE", ignoreCase = true))
            if (ni.isConnected) haveConnectedMobile = true
    }
    return haveConnectedWifi || haveConnectedMobile
}

fun getCurrentTime(): Long {
    return System.currentTimeMillis() / 1000
}

fun createCircleDrawable(color: Int): Drawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.shape = GradientDrawable.OVAL
    gradientDrawable.setColor(color)
    return gradientDrawable
}

var lastEvent: FloatArray? = null
var d = 0f
var newRot = 0f
var isZoomAndRotate = false
var isOutSide = false
val NONE = 0
val DRAG = 1
val ZOOM = 2
var mode = NONE
val start = PointF()
val mid = PointF()
var oldDist = 1f
var xCoOrdinate = 0f
var yCoOrdinate = 0f
var initialX = 0f
var initialY = 0f
var rotationAngle = 0f

fun viewTransformation(view: View, event: MotionEvent) {
    when (event.action and MotionEvent.ACTION_MASK) {
        MotionEvent.ACTION_DOWN -> {
            xCoOrdinate = view.x - event.rawX
            yCoOrdinate = view.y - event.rawY
            start[event.x] = event.y
            isOutSide = false
            mode = DRAG
            lastEvent = null
        }

        MotionEvent.ACTION_POINTER_DOWN -> {
            if (oldDist > 10f) {
                midPoint(mid, event)
                mode = ZOOM
            }
            lastEvent = FloatArray(4)
            lastEvent!![0] = event.getX(0)
            lastEvent!![1] = event.getX(1)
            lastEvent!![2] = event.getY(0)
            lastEvent!![3] = event.getY(1)
            d = rotation(event)

            if (event.x < view.width / 4 && event.y < view.height / 4) {
                initialX = event.rawX
                initialY = event.rawY
                rotationAngle = view.rotation
            }
        }

        MotionEvent.ACTION_UP -> {
            isZoomAndRotate = false
            if (mode == DRAG) {
                val x = event.x
                val y = event.y
            }
            isOutSide = true
            mode = NONE
            lastEvent = null
            mode = NONE
            lastEvent = null
        }

        MotionEvent.ACTION_OUTSIDE -> {
            isOutSide = true
            mode = NONE
            lastEvent = null
            mode = NONE
            lastEvent = null
        }

        MotionEvent.ACTION_POINTER_UP -> {
            mode = NONE
            lastEvent = null
        }

        MotionEvent.ACTION_MOVE -> if (!isOutSide) {
            if (mode == DRAG) {
                isZoomAndRotate = false
                view.animate().x(event.rawX + xCoOrdinate).y(event.rawY + yCoOrdinate)
                    .setDuration(0).start()
            }
            if (mode == ZOOM && event.pointerCount == 2) {
                val newDist1 = spacing(event)
                if (newDist1 > 10f) {
                    val scale = newDist1 / oldDist * view.scaleX
                    view.scaleX = scale
                    view.scaleY = scale
                }
                if (lastEvent != null) {
                    newRot = rotation(event)
                    view.rotation = (view.rotation + (newRot - d))
                }
            }
            if (initialX != 0f && initialY != 0f) {
                val deltaX = event.rawX - initialX
                val deltaY = event.rawY - initialY
                val newRotationAngle = rotationAngle + (deltaX + deltaY) * 0.1f
                view.rotation = newRotationAngle
            }
        }
    }
}

fun rotation(event: MotionEvent): Float {
    val delta_x = (event.getX(0) - event.getX(1)).toDouble()
    val delta_y = (event.getY(0) - event.getY(1)).toDouble()
    val radians = atan2(delta_y, delta_x)
    return Math.toDegrees(radians).toFloat()
}

fun spacing(event: MotionEvent): Float {
    val x = event.getX(0) - event.getX(1)
    val y = event.getY(0) - event.getY(1)
    return sqrt((x * x + y * y).toDouble()).toInt().toFloat()
}

fun midPoint(point: PointF, event: MotionEvent) {
    val x = event.getX(0) + event.getX(1)
    val y = event.getY(0) + event.getY(1)
    point[x / 2] = y / 2
}

fun createDocumentIntent(types: Array<String>, allowedMultiple: Boolean): Intent {
    return Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = if (types.isNotEmpty()) {
            putExtra(Intent.EXTRA_MIME_TYPES, types)
            types[0]
        } else "*/*"
        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowedMultiple)
        addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }
}

fun Class<*>.isMyServiceRunning(context: Context): Boolean {
    val manager = context.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (this.name == service.service.className) {
            return true
        }
    }
    return false
}

fun View.visible(isShow: Boolean) {
    visibility = if (isShow) View.VISIBLE else View.GONE
}

private var myLocale: Locale? = null
fun Context.setLocale(language: String?) {
    val configuration = resources.configuration
    if (language.isNullOrEmpty()) {
        val config = Configuration()
        val locale = Locale.getDefault()
        Locale.setDefault(locale)
        config.locale = locale
        resources
            .updateConfiguration(config, resources.displayMetrics)
    } else {
        if (language.equals("", ignoreCase = true)) return
        var mainCode = language
        var subCode = ""
        if (mainCode.split("-r").size > 1) {
            mainCode = language.split("-r")[0]
            subCode = language.split("-r")[1]
        }
        myLocale = Locale(mainCode, subCode)
        Locale.setDefault(myLocale)
        val config = Configuration()
        config.locale = myLocale
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}


fun setUpGradient(
    values: IntArray,
    radius: Float? = null,
    strokeColor: Int? = null,
    strokeWidth: Int? = null
): Drawable {
    return GradientDrawable().apply {
        colors = values
        orientation = GradientDrawable.Orientation.TOP_BOTTOM
        gradientType = GradientDrawable.LINEAR_GRADIENT
        shape = GradientDrawable.RECTANGLE
        cornerRadius = radius ?: 0f
        if (strokeColor != null && strokeWidth != null) {
            setStroke(strokeWidth, strokeColor)
        }
    }
}