package com.qibla.muslimday.app2025.customeview.ramadan

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.Rect
import android.graphics.Typeface
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.ui.ramadan.model.FontModel
import com.qibla.muslimday.app2025.util.UtilsClass
import java.util.Random
import kotlin.math.atan2

class EditTextClass constructor(
    id: Int,
    str: String?,
    i: Int,
    i2: Int,
    private val context: Context,
) :
    GestureHandlerClass(
        context
    ) {
    var idItem: Int = 0
    var color: Int = 0
    var colorStroke = -1
    var colorIndex: Int = -1
    private val deleteId: Int = R.drawable.ic_control_btn

    //    private var logoFontClass: LogoFontClass? = null
    private var fontModelClass: FontModel? = null
    var opacity: Int = 255
    var strokeOpacity: Int = 255
    private val paint: Paint = Paint()
    private val paintStroke: Paint = Paint()
    private val scaleId: Int = R.drawable.ic_control_btn_scale_img
    private val rotateId = R.drawable.ic_control_btn
    private val flipId = R.drawable.ic_control_btn

    var shadowColor: Int = 0
    var shadowRadius: Float = 0.0f
    var size: Float
    var text: String?
    var textPadding: Float
    var shadownXvalue = 25.0f
    var shadownYvalue = 25.0f
    var x3d: Float = 0.0f
    var y3d: Float = 0.0f
    var letterSpacing: Float = 0.0f
    var curvature: Float = 100.0f
    var strokeWidth = 0.0f
    var isBold = false
    var isItalic = false

    init {
        val random: Random = Random()
        idItem = id
        text = str
        size = UtilsClass.dpToPixel(25.0f, context) * 2.0f
        paint.textSize = size
        paint.isDither = true
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        xPos += if (i < (paint.measureText(str).toInt())) {
            (random.nextInt(UtilsClass.dpToPx(context, 40.0f))
                .toFloat()) + (paint.measureText(str) / 2.0f)
        } else {
            val abs: Int = Math.abs(i - (paint.measureText(str).toInt()))
            ((random.nextInt(if (abs <= 0) 1 else abs) + 1).toFloat()) + (((paint.measureText(
                str
            ) / 2.0f).toInt()).toFloat())
        }

        yPos += if (i2 >= (paint.textSize.toInt())) {
            val abs2: Int = Math.abs(i2 - (paint.textSize.toInt()))
            (((paint.textSize / 2.0f).toInt()).toFloat()) + (random.nextInt(if (abs2 <= 0) 1 else abs2)
                .toFloat())
        } else if (i2 <= 0) {
            random.nextInt(1).toFloat()
        } else {
            random.nextInt(i2).toFloat()
        }
//        color = ViewCompat.MEASURED_STATE_MASK
        textPadding = UtilsClass.dpToPixel(5.0f, context)

        paint.strokeWidth = UtilsClass.dpToPx(context, 2.0f).toFloat()

        setCornersPoints()
    }

    constructor(context: Context) : this(0, "", 0, 0, context)

    fun clone(context: Context): EditTextClass {
        return EditTextClass(context).apply {

        }
    }

    override var theWidth: Float
        get() {
            return (paint.measureText(text).toInt()).toFloat()
        }
        set(theWidth) {
            super.theWidth = theWidth
        }
    override var theHeight: Float
        get() {
            return (paint.textSize.toInt()).toFloat()
        }
        set(theHeight) {
            super.theHeight = theHeight
        }

    fun setWordSpacing(spacing: Float) {
        letterSpacing = spacing // Khoảng cách giữa các từ

    }

    fun setXPosNew(x: Float) {
        // Set chiều x cho ma trận biến đổi
        x3d = x
//        matrix.postRotate(0f,x, 1.0f) // 1.0f để giữ nguyên chiều y
//
//        matrix.postTranslate(xPos, yPos)
    }

    fun setYPosNew(y: Float) {
        y3d = y
//        matrix.setScale(x3d, y)
//        matrix.postTranslate(xPos, yPos)
    }

    fun setStrokeWidthNew(strokeW: Float) {
        strokeWidth = if (strokeW > 2.0f) {
            strokeW
        } else 0.0f
    }

    fun setCurvatureNew(curve: Float) {
        curvature = curve
    }

    fun setRotationX(rotationX: Float) {
        shadownXvalue = rotationX
    }

    fun setRotationY(rotationY: Float) {
        shadownYvalue = rotationY
    }

    fun setShadownX(rotationX: Float) {
//        rotationXValue = -rotationX
        shadownXvalue =
            if (rotationX.toInt() > 25) rotationX else (-(50 - rotationX.toInt()).toFloat())
    }

    fun setShadownY(rotationY: Float) {
//        rotationYValue =  -rotationY
        shadownYvalue =
            if (rotationY.toInt() > 25) rotationY else (-(50 - rotationY.toInt()).toFloat())
    }

    fun isInside(f: Float, f2: Float): Boolean {
        val width: Int = ImageFactoryClass.getBitmap(context, scaleId)!!.width / 2
        return areaOfTrianges(
            f,
            f2
        ) <= ((((((paint.measureText(text) + textPadding).toInt()) + 2) + width) * ((((paint.textSize + textPadding).toInt()) + 2) + width)).toDouble())
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
//        xyPtsSrc = floatArrayOf(
//            0.0f,
//            0.0f,
//            bitmap!!.width.toFloat(),
//            0.0f,
//            bitmap!!.width.toFloat(),
//            bitmap!!.height.toFloat(),
//            0.0f,
//            bitmap!!.height.toFloat()
//        )
    }

    fun updateCornersPoints(curver: Float, halfTextWidth: Float) {
        x1 = xPos - halfTextWidth - curver
        y1 = yPos - curver
        x2 = xPos + theWidth - halfTextWidth + curver
        y2 = yPos - curver
        x3 = xPos + theWidth - halfTextWidth + curver
        y3 = yPos + theHeight
        x4 = xPos - halfTextWidth - curver
        y4 = yPos + theHeight
    }

    fun draw(canvas: Canvas, context2: Context) {
        paintStroke.textSize = size
        paintStroke.isAntiAlias = true
        paintStroke.letterSpacing = letterSpacing
        paintStroke.alpha = opacity

        paint.textSize = size
        paint.isAntiAlias = true
        paint.letterSpacing = letterSpacing


        calculateCornerPoints()
        val halfTextWidth = paint.measureText(text) / 2
        val path: Path = Path()

        path.moveTo(x4, y4)

        //tính toán độ cong và các tham số
//        val newCurvar = curvature.toInt()
//        val direc = if (newCurvar >= 50) 1 else -1
//        val reversedCurvature = if (newCurvar > 50) curvature else 100f - curvature
//        val curver = if (newCurvar != 50) (reversedCurvature * direc) else 0f
        //di chuyển về giữa để tạo độ cong
        //tạo curve
        // path.quadTo(x4+ halfTextWidth, y4 - curver, x3, y3)
        //path.quadTo(x4, y4 - curver, x3, y3)


        path.lineTo(x3, y3)

        if (strokeWidth > 2.0f) {
            paintStroke.setShadowLayer(shadowRadius, shadownXvalue, shadownYvalue, shadowColor)
            paintStroke.textSize = size
            paintStroke.isAntiAlias = true
            paintStroke.letterSpacing = letterSpacing
            paintStroke.alpha = opacity
        } else paint.setShadowLayer(shadowRadius, shadownXvalue, shadownYvalue, shadowColor)

        paintStroke.alpha = opacity
        paint.color = color
        paint.alpha = opacity

        drawEditText(canvas, path, text, paint, paintStroke)

        if (isAnimate) {
            paint.style = Paint.Style.STROKE
            path.reset()

            path.moveTo(x1, y1)
            path.lineTo(x2, y2)
            path.lineTo(x3, y3)
            path.lineTo(x4, y4)
            path.lineTo(x1, y1)
            paint.color = context2.resources.getColor(R.color.color_bound)
            canvas.drawPath(path, paint)
            val bitmap: Bitmap? = ImageFactoryClass.getBitmap(context2, deleteId)
            val bitmap2: Bitmap? = ImageFactoryClass.getBitmap(context2, scaleId)
            val bitmap3: Bitmap? = ImageFactoryClass.getBitmap(context2, rotateId)
            val bitmap4: Bitmap? = ImageFactoryClass.getBitmap(context, flipId)


            val matrix: Matrix = Matrix()


            matrix.preTranslate(
                x1 - ((bitmap!!.width shr 1).toFloat()),
                y1 - ((bitmap.height shr 1).toFloat())
            )
            matrix.postRotate(rotate, x1, y1)
            canvas.drawBitmap((bitmap), matrix, null as Paint?)
            matrix.reset()

            matrix.preTranslate(
                x2 - ((bitmap3!!.width shr 1).toFloat()),
                y2 - ((bitmap3.height shr 1).toFloat())
            )
            matrix.postRotate(rotate, x2, y2)
            canvas.drawBitmap((bitmap3), matrix, null as Paint?)
            matrix.reset()


            matrix.preTranslate(
                x3 - ((bitmap2!!.width shr 1).toFloat()),
                y3 - ((bitmap2.height shr 1).toFloat())
            )
            matrix.postRotate(rotate, x3, y3)
            canvas.drawBitmap((bitmap2), matrix, null as Paint?)

            matrix.reset()
            matrix.preTranslate(
                x4 - ((bitmap4!!.width shr 1).toFloat()),
                y4 - ((bitmap4.height shr 1).toFloat())
            )
            matrix.postRotate(rotate, x4, y4)
            canvas.drawBitmap((bitmap4), matrix, null as Paint?)

            paint.style = Paint.Style.FILL_AND_STROKE
        }

    }

    @SuppressLint("SuspiciousIndentation")
    private fun drawEditText(
        canvas: Canvas,
        path: Path,
        str: String?,
        paint2: Paint,
        paintStroke1: Paint,
    ) {
        if (str!!.trim { it <= ' ' }.isNotEmpty()) {
            val pathMeasure: PathMeasure = PathMeasure(path, false)
            val fArr: FloatArray = FloatArray(2)
            val fArr2: FloatArray = FloatArray(2)
            var f: Float = 0.0f
            // Độ rộng của stroke
            val pathLength = pathMeasure.length

            pathMeasure.getPosTan(f, fArr, fArr2)

            canvas.save()
            canvas.translate(fArr[0] - 0.0f, fArr[1] - 0.0f)
            canvas.rotate(
                ((atan2(
                    fArr2[1].toDouble(),
                    fArr2[0].toDouble()
                ) * 180.0) / 3.141592653589793).toFloat(), 0.0f, 0.0f
            )

            // Thiết lập stroke cho văn bản
            if (strokeWidth > 2.0f) {
                paintStroke1.alpha = opacity
                paintStroke1.style = Paint.Style.STROKE
                paintStroke1.strokeWidth = strokeWidth

                if (colorStroke == -1) {
                    paintStroke1.color = Color.RED
                } else {
                    paintStroke1.color = colorStroke
                }
                // Vẽ stroke
                canvas.drawText(str, textPadding / 2.0f, -paintStroke1.descent(), paintStroke1)
            }

            // Khôi phục thuộc tính ban đầu của Paint
            paint2.ascent()
            paint2.descent()
            paint2.ascent()
            paint2.descent()
            if (color == -1) {
//                    paint2.color = AppConstant.RAINBOW_TEXT_COLOR_CODE[i % AppConstant.RAINBOW_TEXT_COLOR_CODE.size]
                paint2.color = Color.BLACK
            }
//                canvas.concat(matrix1);

            if (fontModelClass == null) {

                if (isBold && isItalic) {
                    paint2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC))
                } else if (isBold) {
                    paint2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
                } else if (isItalic) {
                    paint2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC))
                } else {
                    paint2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
                }
            } else {
                val textStyle = if (isBold && isItalic) {
                    Typeface.BOLD_ITALIC
                } else if (isBold) {
                    Typeface.BOLD
                } else if (isItalic) {
                    Typeface.ITALIC
                } else {
                    Typeface.NORMAL
                }

                val typeFont = Typeface.createFromAsset(
                    context.assets,
                    "fonts/${fontModelClass?.fontPath}"
                )

                val combinedTypeface = Typeface.create(typeFont, textStyle)

                paint2.typeface = combinedTypeface
            }

            canvas.drawText(str, textPadding / 2.0f, -paint2.descent(), paint2)

            canvas.restore()
//                f += paint2.measureText(str)
//            }
        }
    }


    private fun getNextLine(
        text: String,
        paint: Paint,
        textWidth: Float,
        remainingPathLength: Float,
        lineHeight: Float
    ): String {
        var width = 0f
        var index = 0
        while (index < text.length && width + paint.measureText(
                text.substring(
                    0,
                    index
                )
            ) < remainingPathLength
        ) {
            width += paint.measureText(text[index].toString())
            index++
        }
        if (width > textWidth) {
            return text.substring(0, index - 1)
        }
        return text.substring(0, index)
    }

//    private fun drawEditText(canvas: Canvas, path: Path, str: String?, paint2: Paint,paintStroke1 : Paint) {
//        if (str!!.trim { it <= ' ' }.isNotEmpty()) {
//            val pathMeasure: PathMeasure = PathMeasure(path, false)
//            val pos = FloatArray(2)
//            val tan = FloatArray(2)
//            val centerX = pos[0]
//            val centerY = pos[1]
//
//            val fArr: FloatArray = FloatArray(2)
//            val fArr2: FloatArray = FloatArray(2)
//
//            var f: Float = 0.0f
//            // Độ rộng của stroke
//            val pathLength = pathMeasure.length
//
//            // Đặt khoảng cách giữa các ký tự
//            val interval = pathLength / str.length
////            for (i in str.indices) {
//
//            pathMeasure.getPosTan(f, pos, tan)
////                pathMeasure.getPosTan((i * pathMeasure.length) / str.length, pos, tan)
////                pathMeasure.getPosTan(f, fArr, fArr2)
//            // Lưu trạng thái canvas trước khi áp dụng ma trận biến đổi
//            val centerXBefore = (x4 + x3) / 2
//            val centerYBefore = (y4 + y3) / 2
//
//            canvas.save()
////                canvas.translate(centerX - 0.0f, centerY - 0.0f)
////
////                val degree =
////                    (Math.atan2(tan[1].toDouble(), tan[0].toDouble()) * (180 / Math.PI)).toFloat()
//////                canvas.rotate(
//////                    ((atan2(
//////                        tan[0].toDouble(),
//////                        tan[1].toDouble()
//////                    ) * 180.0) / 3.141592653589793).toFloat(), 0f, 0f
//////                )
//////                canvas.rotate(
//////                    ((atan2(
//////                        centerX.toDouble(),
//////                        centerY.toDouble()
//////                    ) * 180.0) / 3.141592653589793).toFloat(), 0f, 0f
//////                )
//            canvas.translate(fArr[0] - 0.0f, fArr[1] - 0.0f)
//
//            canvas.rotate(
//                ((atan2(
//                    fArr2[1].toDouble(),
//                    fArr2[0].toDouble()
//                ) * 180.0) / 3.141592653589793).toFloat(), 0.0f, 0.0f
//
//            )
////                canvas.rotate(
////                    degree, 0f, 0f
////                )
//            // Thiết lập stroke cho văn bản
//            if (strokeWidth > 2.0f) {
//                paintStroke1.alpha = opacity
//                paintStroke1.style = Paint.Style.STROKE
//                paintStroke1.strokeWidth = strokeWidth
//
//                if (colorStroke == -1) {
//                    paintStroke1.color = Color.RED
//                } else {
//                    paintStroke1.color = colorStroke
//                }
//                // Vẽ stroke
//                canvas.drawText(str, textPadding / 2.0f, -paintStroke1.descent(), paintStroke1)
//            }
//
//            // Khôi phục thuộc tính ban đầu của Paint
//            paint2.ascent()
//            paint2.descent()
//            paint2.ascent()
//            paint2.descent()
//            if (color == -1) {
////                    paint2.color = AppConstant.RAINBOW_TEXT_COLOR_CODE[i % AppConstant.RAINBOW_TEXT_COLOR_CODE.size]
//                paint2.color = Color.BLACK
//            }
////                canvas.concat(matrix1);
//
//            canvas.drawText(str, textPadding / 2.0f, -paint2.descent(), paint2)
//            canvas.restore()
////                f += paint2.measureText(str[i].toString() + "")
////            }
//
//            //new
////            pathMeasure.getPosTan(pathLength/2, pos, tan)
////            // Lưu trạng thái canvas trước khi áp dụng ma trận biến đổi
////            val centerXBefore = (x4 + x3) / 2
////            val centerYBefore = (y4 + y3) / 2
////
////            canvas.save()
////            canvas.translate(centerX - 0.0f, centerY - 0.0f)
////            val angle = Math.toDegrees(Math.atan2(tan[1].toDouble(), tan[0].toDouble())).toFloat()
////            val angle1 = ((atan2(centerX.toDouble(), centerY.toDouble()) * 180.0) / 3.141592653589793).toFloat()
////
////
//////            canvas.rotate(angle1 ,0f,0f)
////            canvas.rotate(
////                    ((atan2(
////                        pos[0].toDouble(),
////                        pos[1].toDouble()
////                    ) * 180.0) / 3.141592653589793).toFloat(), 0.0f, 0.0f
////                )
//////            canvas.rotate(angle1 ,centerXBefore,centerYBefore)
////
////            // Thiết lập stroke cho văn bản
////            if (strokeWidth > 2.0f) {
////                paintStroke1.alpha = opacity
////                paintStroke1.style = Paint.Style.FILL_AND_STROKE
////                paintStroke1.strokeWidth = strokeWidth
////
////
////                if (colorStroke == -1) {
////                    paintStroke1.color = Color.RED
////                } else {
////                    paintStroke1.color = colorStroke
////                }
////
////                // Vẽ stroke
//////                canvas.drawTextOnPath(str, path, textPadding / 2.0f, -paintStroke1.descent(), paintStroke1)
////                canvas.drawText(str,textPadding / 2.0f, -paintStroke1.descent(), paintStroke1)
////
////            }
////
////            // Khôi phục thuộc tính ban đầu của Paint
////            paint2.ascent()
////            paint2.descent()
////            paint2.ascent()
////            paint2.descent()
////            if (color == -1) {
////                paint2.color = Color.BLACK
////            }
////
//////            canvas.drawTextOnPath(str, path, textPadding / 2.0f, -paint2.descent(), paint2)
////            canvas.drawText(str,textPadding / 2.0f, -paint2.descent(), paint2)
////            canvas.restore()
//
//
//        }
//    }

    private fun drawTextOnPathWith3DTransformation(
        canvas: Canvas,
        text: String,
        path: Path,
        paint: Paint,
        translationX: Float,
        translationY: Float,
    ) {
        val pathMeasure = PathMeasure(path, false)
        val textLength = text.length
        val pos = FloatArray(2)
        val tan = FloatArray(2)

        for (i in 0 until textLength) {
            val textWidth = paint.measureText(text[i].toString())
            val offset = (textWidth / 2).toInt()
            val hOffset = offset.toFloat()

            pathMeasure.getPosTan((i * pathMeasure.length) / textLength, pos, tan)

            val degree =
                (Math.atan2(tan[1].toDouble(), tan[0].toDouble()) * (180 / Math.PI)).toFloat()

            val matrix = Matrix()
            matrix.postTranslate(pos[0] - hOffset + translationX, pos[1] - translationY)
            matrix.postRotate(degree, pos[0], pos[1])

            canvas.save()
            canvas.setMatrix(matrix)
            canvas.drawText(text[i].toString(), 5.0f, 5.0f, paint)
            canvas.restore()
        }
    }


    fun initializeCornersPoints() {
        paint.getTextBounds(text, 0, text!!.length, Rect())
        x1 = xPos
        y1 = yPos + paint.descent()
        x2 = xPos
        y2 = yPos - paint.textSize
        x3 = xPos + paint.measureText(text)
        y3 = yPos - paint.textSize
        x4 = xPos + paint.measureText(text)
        y4 = yPos + paint.descent()
    }

    fun calculateCornerPoints() {
        val measureText: Int = (paint.measureText(text) + textPadding).toInt()
        val textSize: Int = (paint.textSize + textPadding).toInt()
        x1 = xPos
        y1 = yPos
        x2 = xPos
        y2 = yPos
        x3 = xPos
        y3 = yPos
        x4 = xPos
        y4 = yPos
        val f: Float = (measureText / 2).toFloat()
        val f2: Float = (textSize / 2).toFloat()
        val atan: Double = (Math.atan((f / f2).toDouble()) * 180.0) / 3.141592653589793
        val d: Double = rotate.toDouble()
        java.lang.Double.isNaN(d)
        val sin: Double = Math.sin((d + atan) * -0.017453293)
        val sqrt: Double =
            ((Math.sqrt(((measureText * measureText) + (textSize * textSize)).toDouble())
                .toInt()) shr 1).toDouble()
        java.lang.Double.isNaN(sqrt)
        val d2: Double = rotate.toDouble()
        java.lang.Double.isNaN(d2)
        val cos: Double = Math.cos((d2 + atan) * -0.017453293)
        java.lang.Double.isNaN(sqrt)
        x2 -= (sin * sqrt).toFloat()
        y2 -= (cos * sqrt).toFloat()
        val d3: Double = rotate.toDouble()
        java.lang.Double.isNaN(d3)
        val sin2: Double = Math.sin((d3 + atan + 180.0) * -0.017453293)
        java.lang.Double.isNaN(sqrt)
        val d4: Double = rotate.toDouble()
        java.lang.Double.isNaN(d4)
        val cos2: Double = Math.cos((d4 + atan + 180.0) * -0.017453293)
        java.lang.Double.isNaN(sqrt)
        x4 -= (sin2 * sqrt).toFloat()
        y4 -= (cos2 * sqrt).toFloat()
        val atan2: Double = (Math.atan((f2 / f).toDouble()) * 180.0) / 3.141592653589793
        val d5: Double = rotate.toDouble()
        java.lang.Double.isNaN(d5)
        val sin3: Double = Math.sin((d5 + atan2 + 90.0) * -0.017453293)
        java.lang.Double.isNaN(sqrt)
        val d6: Double = rotate.toDouble()
        java.lang.Double.isNaN(d6)
        val cos3: Double = Math.cos((d6 + atan2 + 90.0) * -0.017453293)
        java.lang.Double.isNaN(sqrt)
        x3 -= (sin3 * sqrt).toFloat()
        y3 -= (cos3 * sqrt).toFloat()
        val d7: Double = rotate.toDouble()
        java.lang.Double.isNaN(d7)
        val sin4: Double = Math.sin((d7 + atan2 + 270.0) * -0.017453293)
        java.lang.Double.isNaN(sqrt)
        val d8: Double = rotate.toDouble()
        java.lang.Double.isNaN(d8)
        val cos4: Double = Math.cos((d8 + atan2 + 270.0) * -0.017453293)
        java.lang.Double.isNaN(sqrt)
        x1 -= (sin4 * sqrt).toFloat()
        y1 -= (cos4 * sqrt).toFloat()
    }

    fun getSizeNew(): Float {
        return size
    }

    fun setSizeNew(f: Float) {
        size = f
    }

    fun setIsBold(isBold: Boolean) {
        this.isBold = isBold
    }

    fun setIsItalic(isItalic: Boolean) {
        this.isItalic = isItalic
    }

    fun setSizeForSave(f: Float) {
        size = f
    }

    public override fun setScaleF(f: Float) {
        setSizeNew(prevScaleW * f)
    }

//    var logoFont: LogoFontClass?
//        get() {
//            return logoFontClass
//        }
//        set(logoFontClass2) {
//            logoFontClass = logoFontClass2
//            paint.typeface = logoFontClass!!.getTypefaceObject(context)
//            paintStroke.typeface = logoFontClass!!.getTypefaceObject(context)
//        }

    var fontModel: FontModel?
        get() {
            return fontModelClass
        }
        set(logoFontClass2) {
            fontModelClass = logoFontClass2

            val textStyle = if (isBold && isItalic) {
                Typeface.BOLD_ITALIC
            } else if (isBold) {
                Typeface.BOLD
            } else if (isItalic) {
                Typeface.ITALIC
            } else {
                Typeface.NORMAL
            }

            val typeFont = Typeface.createFromAsset(
                context.assets,
                "fonts/${fontModelClass?.fontPath}"
            )

            val combinedTypeface = Typeface.create(typeFont, textStyle)

            paint.typeface = combinedTypeface
            paintStroke.typeface = combinedTypeface
        }

    companion object {
        private val OUTLINE_COLOR: Int = -5299729
        private val PADDING: Int = 5
    }
}