package com.qibla.muslimday.app2025.customeview.ramadan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.util.ColorFilterGenClass1
import com.qibla.muslimday.app2025.util.UtilsClass
import java.io.Serializable

class EditCharacterClass constructor(
    id: Int,
    context: Context,
    f: Float,
    f2: Float,
    bitmap2: Bitmap?,
    f3: Float,
    f4: Float,
    z: Boolean,
    val resID: Int,
    z2: Boolean
) : GestureHandlerClass(context), Serializable {
    var idItem: Int = 0
    var bitmap: Bitmap?
    var bitmapPath: String? = null
    public var colorize: Int = Color.WHITE
    private val deleteId: Int
    private val extraSpaceForTouch: Float
    var flipImage: Int = 1
    var flipImageVertical: Int = 1
    var isFrame: Boolean = false
    var isImageFromGallery: Boolean = false
    var isToColorize: Boolean = true
    var opacity: Int = 255
    var size: Float = 0f

    var logoTitle: String? = null
        private set
    var moduleName: String? = null
        private set
    private val paint: Paint
    private val paintHair: Paint

    private val rotateId: Int
    private val flipId: Int

    private val scaleId: Int
    private val tempMatrix: Matrix
    private val xyPtsDst: FloatArray
    private lateinit var xyPtsSrc: FloatArray

    var shadowColor: Int = 0
    var shadowRadius: Float = 0.0f
    var shadownXvalue = 25.0f
    var shadownYvalue = 25.0f
    var x3dValue = 25.0f
    var y3dValue = 25.0f

    @JvmOverloads
    constructor(
        id: Int,
        context: Context,
        f: Float,
        f2: Float,
        bitmap2: Bitmap?,
        z: Boolean,
        i: Int = 0
    ) : this(
        id,
        context,
        f,
        f2,
        bitmap2,
        UtilsClass.dpToPixel(70.0f, context),
        UtilsClass.dpToPixel(70.0f, context),
        z,
        i,
        true
    )

    constructor(
        id: Int,
        context: Context,
        f: Float,
        f2: Float,
        bitmap2: Bitmap?,
        z: Boolean,
        str: String?,
        str2: String?
    ) : this(id, context, f, f2, bitmap2, z, 0) {
        moduleName = str
        logoTitle = str2
    }

    init {
        idItem = id
        tempMatrix = Matrix()
        xyPtsDst = FloatArray(8)
        paint = Paint()
        scaleId = R.drawable.ic_control_btn_scale_img
        deleteId = R.drawable.ic_control_btn
        rotateId = R.drawable.ic_control_btn
        flipId = R.drawable.ic_control_btn
        paintHair = Paint()
        isToColorize = UtilsClass.isToShowColorSlider(resID)
        isTodrawBoundary = z
        theWidth = f3
        theHeight = f4
        if (!z2) {
            this.minWidth = theWidth * 0.2f
            this.minHeight = theHeight * 0.2f
        } else if (Math.max(
                Math.abs(bitmap2!!.width / bitmap2.height), Math.abs(
                    bitmap2.height / bitmap2.width
                )
            ) > 2
        ) {
            val f5: Float = theWidth * 3.0f
            theWidth = f5
            theHeight = f5
            if (bitmap2.width > bitmap2.height) {
                theHeight = ((bitmap2.height.toFloat()) * theWidth) / (bitmap2.width.toFloat())
            } else {
                theWidth = ((bitmap2.width.toFloat()) * theHeight) / (bitmap2.height.toFloat())
            }
            this.minWidth = theWidth * 0.15f
            this.minHeight = theHeight * 0.15f
        } else {
            theWidth = ((bitmap2.width.toFloat()) * theHeight) / (bitmap2.height.toFloat())
            this.minWidth = theWidth * 0.3f
            this.minHeight = theHeight * 0.3f
        }
        extraSpaceForTouch =
            (ImageFactoryClass.getBitmap(context, scaleId)!!.width / 2).toFloat()
        xPos = f
        yPos = f2
        bitmap = bitmap2
        scaleH = theHeight / (bitmap2!!.height.toFloat())
        scaleW = theWidth / (bitmap2.width.toFloat())
        prevScaleH = scaleH
        prevScaleW = scaleW
        paint.strokeWidth = UtilsClass.dpToPx(context, 1.0f).toFloat()
        paint.color = context.resources.getColor(R.color.color_bound)
        setCornersPoints()
    }

    fun setRotationX(rotationX: Float) {
        rolateX = rotationX
    }

    fun updateAlphaFromSeekBar(alphaValue: Int) {
        opacity = alphaValue
    }

    fun setRotationY(rotationY: Float) {
        rolateY = rotationY
    }

    fun setShadownX(rotationX: Float) {
        shadownXvalue =
            if (rotationX.toInt() > 25) rotationX else (-(50 - rotationX.toInt()).toFloat())
    }

    fun setShadownY(rotationY: Float) {
        shadownYvalue =
            if (rotationY.toInt() > 25) rotationY else (-(50 - rotationY.toInt()).toFloat())
    }

    fun setColorizeUpdate(i: Int) {
        colorize = i
        setColorFilterHue(i)
    }

    fun setColorFilterHue(i: Int) {
        colorize = i
        paintHair.colorFilter = ColorFilterGenClass1.adjustColor(0, 0, 0, i)
    }

    fun setColor(i: Int) {
        colorize = i
        if (i != 0) {
            val colorFilter = PorterDuffColorFilter(i, PorterDuff.Mode.MULTIPLY)

            paintHair.colorFilter = colorFilter
        }
    }

    fun setColorShadow(i: Int) {
        shadowColor = i
        paintHair.setShadowLayer(shadowRadius, shadownXvalue, shadownYvalue, shadowColor)

    }

    fun isTouchInside(f: Float, f2: Float): Boolean {
        val d: Double =
            ((theWidth + extraSpaceForTouch) * (theHeight + extraSpaceForTouch)).toDouble()
        val areaOfTrianges: Double = areaOfTrianges(f, f2)
        return areaOfTrianges >= (((theWidth - extraSpaceForTouch) * (theHeight - extraSpaceForTouch)).toDouble()) && areaOfTrianges <= d
    }

    fun setCornersPoints() {
        x1 = xPos
        y1 = yPos
        x2 = xPos + theWidth
        y2 = yPos
        x3 = xPos + theWidth
        y3 = yPos + theHeight
        x4 = xPos
        y4 = yPos + theHeight
        xyPtsSrc = floatArrayOf(
            0.0f,
            0.0f,
            bitmap!!.width.toFloat(),
            0.0f,
            bitmap!!.width.toFloat(),
            bitmap!!.height.toFloat(),
            0.0f,
            bitmap!!.height.toFloat()
        )
    }

    fun draw(canvas: Canvas, context: Context) {
        if (bitmap != null) {
            paintHair.isAntiAlias = true
            paintHair.isFilterBitmap = true
            paintHair.alpha = opacity
            paint.isAntiAlias = true
            paint.isFilterBitmap = true
            val matrix: Matrix = Matrix()
            if (handlingTwoFingerTouch) {
                matrix.preTranslate(xPos, yPos)
                if (isTodrawBoundary) {
                    matrix.postScale(
                        flipImage.toFloat(),
                        flipImageVertical.toFloat(),
                        xPos + ((bitmap!!.width / 2).toFloat()),
                        yPos + ((bitmap!!.height / 2).toFloat())
                    )
                    matrix.postScale(scaleW, scaleH, xPos, yPos)
                    matrix.postRotate(rotate, xPos + (theWidth / 2.0f), yPos + (theHeight / 2.0f))
                } else {
                    matrix.postScale(
                        flipImage.toFloat(),
                        flipImageVertical.toFloat(),
                        xPos + ((bitmap!!.width / 2).toFloat()),
                        yPos + ((bitmap!!.height / 2).toFloat())
                    )
                    matrix.postScale(scaleW, scaleH, sPivotX, sPivotY)
                    matrix.postRotate(rotate, sPivotX, sPivotY)
                }
            } else {
                if (isTodrawBoundary) {
                    matrix.preScale(
                        flipImage.toFloat(),
                        flipImageVertical.toFloat(),
                        (bitmap!!.width / 2).toFloat(),
                        (bitmap!!.height / 2).toFloat()
                    )
                    matrix.postScale(scaleW, scaleH)
                    matrix.postRotate(rotate, theWidth / 2.0f, theHeight / 2.0f)
                } else {
                    matrix.preScale(
                        flipImage.toFloat(),
                        flipImageVertical.toFloat(),
                        (bitmap!!.width / 2).toFloat(),
                        (bitmap!!.height / 2).toFloat()
                    )
                    matrix.postScale(scaleW, scaleH)
                    matrix.postRotate(rotate)
                    Log.d("ntt", "draw: " + scaleW + "  " + scaleH)
                }
                matrix.postTranslate(xPos, yPos)
            }

//            paintHair.setShadowLayer(shadowRadius, 0.0f, 0.0f, shadowColor)


//            paintHair.alpha = opacity
            canvas.drawBitmap(bitmap!!, matrix, paintHair)
            val matrix2: Matrix = Matrix()
            if (handlingTwoFingerTouch) {
                matrix2.preTranslate(xPos, yPos)
                if (isTodrawBoundary) {
                    matrix2.preScale(scaleW, scaleH)
                    matrix2.postRotate(rotate, xPos + (theWidth / 2.0f), yPos + (theHeight / 2.0f))
                } else {
                    matrix2.postScale(scaleW, scaleH, sPivotX, sPivotY)
                    matrix2.postRotate(rotate, sPivotX, sPivotY)
                }
            } else {
                if (isTodrawBoundary) {
                    matrix2.preScale(scaleW, scaleH)
                    matrix2.postRotate(rotate, theWidth / 2.0f, theHeight / 2.0f)
                } else {
                    matrix2.preScale(scaleW, scaleH)
                    matrix2.postRotate(rotate)
                }
                matrix2.postTranslate(xPos, yPos)
            }
            matrix2.mapPoints(xyPtsDst, xyPtsSrc)
            x1 = xyPtsDst.get(0)
            y1 = xyPtsDst.get(1)
            x2 = xyPtsDst.get(2)
            y2 = xyPtsDst.get(3)
            x3 = xyPtsDst.get(4)
            y3 = xyPtsDst.get(5)
            x4 = xyPtsDst.get(6)
            y4 = xyPtsDst.get(7)
            paint.style = Paint.Style.STROKE
            if (isAnimate) {
                matrix2.reset()
                if (isTodrawBoundary) {
                    val path: Path = Path()
                    path.moveTo(x1, y1)
                    path.lineTo(x2, y2)
                    path.lineTo(x3, y3)
                    path.lineTo(x4, y4)
                    path.lineTo(x1, y1)
                    canvas.drawPath(path, paint)
                    val bitmap2: Bitmap? = ImageFactoryClass.getBitmap(context, scaleId)
                    val bitmap3: Bitmap? = ImageFactoryClass.getBitmap(context, rotateId)
                    val bitmap4: Bitmap? = ImageFactoryClass.getBitmap(context, deleteId)
                    val bitmap5: Bitmap? = ImageFactoryClass.getBitmap(context, flipId)
                    val matrix3: Matrix = Matrix()

                    matrix3.preTranslate(
                        x3 - ((bitmap2!!.width shr 1).toFloat()),
                        y3 - ((bitmap2.height shr 1).toFloat())
                    )
                    matrix3.postRotate(rotate, x3, y3)
                    canvas.drawBitmap((bitmap2), matrix3, null as Paint?)

                    matrix3.reset()
                    matrix3.preTranslate(
                        x2 - ((bitmap3!!.width shr 1).toFloat()),
                        y2 - ((bitmap3.height shr 1).toFloat())
                    )
                    matrix3.postRotate(rotate, x2, y2)
                    canvas.drawBitmap((bitmap3), matrix3, null as Paint?)

                    matrix3.reset()
                    matrix3.preTranslate(
                        x1 - ((bitmap4!!.width shr 1).toFloat()),
                        y1 - ((bitmap4.height shr 1).toFloat())
                    )
                    matrix3.postRotate(rotate, x1, y1)
                    canvas.drawBitmap((bitmap4), matrix3, null as Paint?)

                    matrix3.reset()
                    matrix3.preTranslate(
                        x4 - ((bitmap5!!.width shr 1).toFloat()),
                        y4 - ((bitmap5.height shr 1).toFloat())
                    )
                    matrix3.postRotate(rotate, x4, y4)
                    canvas.drawBitmap((bitmap5), matrix3, null as Paint?)

                    Log.d("ntt", "draw: if (isTodrawBoundary) {")
                }
            }
        }
    }

    public override fun setWidth(f: Float) {
        super.setWidth(f)
        scaleW = theWidth / (bitmap!!.width.toFloat())
        prevScaleW = scaleW
    }

    public override fun setHeight(f: Float) {
        super.setHeight(f)
        scaleH = theHeight / (bitmap!!.height.toFloat())
        prevScaleH = scaleH
    }

    public override fun setScaleF(f: Float) {
        super.setWidth((bitmap!!.width.toFloat()) * prevScaleW * f)
        super.setHeight((bitmap!!.height.toFloat()) * prevScaleH * f)
        scaleW = theWidth / (bitmap!!.width.toFloat())
        scaleH = theHeight / (bitmap!!.height.toFloat())
        size = f
    }

    public override fun setScalePivotPoints(f: Float, f2: Float) {
        super.setScalePivotPoints(f, f2)
        if (!isTodrawBoundary) {
            tempMatrix.reset()
            val fArr: FloatArray = FloatArray(2)
            tempMatrix.preRotate(360.0f - rotate, f, f2)
            tempMatrix.postScale(1.0f / scaleW, 1.0f / scaleH, f, f2)
            tempMatrix.mapPoints(fArr, floatArrayOf(x1, y1))
            xPos = fArr[0]
            yPos = fArr[1]
            sPivotX = f
            sPivotY = f2
        }
    }

    fun updateScaleF(f: Float) {
        prevScaleW *= f
        prevScaleH *= f
    }

    public override fun resetPivotPoints() {
        if (!isTodrawBoundary) {
            sPivotX = 0.0f
            sPivotY = 0.0f
            xPos = x1
            yPos = y1
        }
    }

    fun flip() {
        flipImage *= -1
    }

    fun flipVertical() {
        flipImageVertical *= -1
    }

    companion object {
        private val serialVersionUID: Long = -7498541548L
    }
}