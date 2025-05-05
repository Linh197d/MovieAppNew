package com.cardmaker.business.optimum.creator.customview

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.MaskFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import com.qibla.muslimday.app2025.extensions.viewTransformation


@SuppressLint("ViewConstructor")
class CusTextView(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attrs, defStyleAttr) {
    private var externalOnTouchListener: OnTouchListener? = null
    private lateinit var blurMaskFilter: MaskFilter
    private var curvature: Float = 0f


    private var lineSpacingMultiplier = 1.0f

    private var curveAmount: Float = 0f
    var xText = 0f
    var yText = 0f

    //shadow
    private var shadowBlur: Float = 0f
    private var shadowRadius: Float = 0f
    private var shadowDx: Float = 0f
    private var shadowDy: Float = 0f
    private var shadowColor: Int = Color.RED

    private var originalTextSize: Float = 0f
    private var seekBarProgress: Int = 50

    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    private val path = Path()

    private var strokeColor: Int = Color.GREEN
    private var strokeWidth: Float = 0f
    private var strokeStyle: StrokeStyle = StrokeStyle.OUTSIDE


    private val textPath = Path()
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = currentTextColor
        style = Paint.Style.FILL
        textSize = this.textSize
    }

    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = shadowColor
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//        style = Paint.Style.STROKE
//        color = strokeColor
//        strokeWidth = this.strokeWidth
    }

    init {

        setLayerType(LAYER_TYPE_SOFTWARE, null)


//        paint.textSize = textSize
//        paint.color = currentTextColor
//        paint.typeface = typeface


        updateView()
    }


    fun setShadowColor(color: Int) {
        shadowColor = color
        shadowPaint.color = shadowColor
        invalidate() // Yêu cầu vẽ lại khi màu bóng thay đổi
    }

    fun setTextSizeAndColor(textSize: Float, color: Int) {
        shadowPaint.textSize = textSize
        setTextColor(color)
    }

    fun setShadowBlur(blur: Float) {
        shadowBlur = blur
//        updateShadow()
        updateView()
    }

    fun setCurvature(curvature: Float) {
        this.curvature = curvature
        invalidate()
    }

    fun setShadowDx(dx: Float) {
        shadowDx = dx
//        updateShadow()
        updateView()
    }

    fun setShadowDy(dy: Float) {
        shadowDy = dy
//        updateShadow()
        updateView()
    }


//    private fun updateShadow() {
//        shadowPaint.setShadowLayer(shadowBlur, shadowDx, shadowDy, shadowColor)
//        invalidate()
//    }

    fun setCustomPadding(left: Int, top: Int, right: Int, bottom: Int) {
        setPadding(left, top, right, bottom)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {

//        xText = paddingLeft.toFloat() // Vị trí x của văn bản trong TextView
//        yText = paddingTop + textPaint.textSize


//        val layout = layout
//        xText =  layout?.getLineLeft(0)?.toFloat() ?: 0f
//        yText = layout?.getLineTop(0)?.toFloat() ?: 0f
//        Log.d("ziko","xtext = $xText , ytext = $yText")
//        Log.d("ziko","width = $width , height = $height , heght/2 = ${height/2}")
//        canvas.let {
//            val text = text.toString()
//            val x = width / 2f
//            val y = (height / 2 - (textPaint.descent() + textPaint.ascent()) / 2)
//            val textHeight = (paint.descent() - textPaint.ascent()) /2f
//
//            // Create text path based on curve amount
//            textPath.reset()
//            textPath.addArc(
//                RectF(x - width / 2f, y - height / 2f, x + width / 2f, y + height / 2f),
//                -curveAmount,
//                2 * curveAmount
//            )
//
//
//            // Draw text
////            textPaint.color = Color.WHITE
////            canvas.drawTextOnPath(text, textPath, 0f, 0f, textPaint)
//
//            // Draw shadow
//            shadowPaint.color = shadowColor
//            if (shadowBlur > 0) {
//                shadowPaint.maskFilter = BlurMaskFilter(shadowBlur, BlurMaskFilter.Blur.NORMAL)
//            } else {
//                shadowPaint.maskFilter = null
//            }
////            val yline = 0f - (textPaint.ascent())
//            shadowPaint.textSize = textSize
//            shadowPaint.color = shadowColor
//            canvas.drawTextOnPath(text, textPath, xText + shadowDx, height/2f- textHeight + shadowDy, shadowPaint)
//
//        super.onDraw(canvas)


        if (shadowBlur > 0) {
            shadowPaint.color = shadowColor
            if (shadowBlur > 0) {
                shadowPaint.maskFilter = BlurMaskFilter(shadowBlur, BlurMaskFilter.Blur.NORMAL)
            } else {
                shadowPaint.maskFilter = null
            }
            shadowPaint.textSize = textSize
            shadowPaint.color = shadowColor
            shadowPaint.typeface = typeface
            shadowPaint.letterSpacing = letterSpacing

            canvas.let {
                val text = text.toString()
                val x = width / 2f
                val y = (height / 2 - (textPaint.descent() + textPaint.ascent()) / 2)
                val textHeight = (textPaint.descent() - textPaint.ascent()) / 2f

                // Create text path based on curve amount
                textPath.reset()
                textPath.addArc(
                    RectF(x - width / 2f, y - height / 2f, x + width / 2f, y + height / 2f),
                    -curveAmount,
                    2 * curveAmount
                )

                canvas.drawTextOnPath(
                    text,
                    textPath,
                    xText + shadowDx,
                    height / 2f - textHeight + shadowDy,
                    shadowPaint
                )
            }

        }
        //set paint to fill mode
        val p: Paint = getPaint()
        p.style = Paint.Style.FILL
        //draw the fill part of text
        super.onDraw(canvas)
        //save the text color
        val currentTextColor = currentTextColor

        if (strokeWidth > 0) {
            //set paint to stroke mode and specify
            //stroke color and width
            p.style = Paint.Style.STROKE
            p.strokeWidth = strokeWidth
            setTextColor(strokeColor)
        }
//        else {
//            super.onDraw(canvas)
//        }


        //draw text stroke
        super.onDraw(canvas)
        //revert the color back to the one
        //initially specified
        setTextColor(currentTextColor)


        //tạo độ skew
//        canvas.let {
//            val textWidth = paint.measureText(text.toString())
//            val centerX = width / 2f
//            val centerY = height / 2f
//            val baseline = (height - paint.ascent() - paint.descent()) / 2f
//            // Độ cong (skew) theo chiều ngang
//            val skewX = calculateSkewX()
//            canvas.skew(skewX, 0f)
//
//            paint.textSize = textSize
//            paint.typeface = typeface
//            paint.color = currentTextColor
//
//            // Vẽ văn bản
//            canvas.drawText(text.toString(), centerX - textWidth / 2f, baseline, paint)
//        }


//        canvas.let {
//            path.reset()
//            val centerX = width / 2f
//            val centerY = height / 2f
//            val baseline = (height - paint.ascent() - paint.descent()) / 2f
//
//            // Độ cong dọc theo chiều dọc của văn bản
//            val curveHeight = calculateCurveHeight()
//
//            path.moveTo(centerX - textWidth() / 2f, centerY - baseline)
//            path.quadTo(centerX, centerY - baseline - curveHeight, centerX + textWidth() / 2f, centerY - baseline)
//            canvas.drawTextOnPath(text.toString(), path, 0f, 0f, paint)
//        }


        // Vẽ bóng màu đỏ dưới văn bản
//        canvas.save()
////        canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
//        for (i in 0 until textLayout.lineCount) {
//            val lineStart = textLayout.getLineStart(i)
//            val lineEnd = textLayout.getLineEnd(i)
//            val line = text.substring(lineStart, lineEnd)
//            val x = textLayout.getLineLeft(i)
//            val y = textLayout.getLineBottom(i).toFloat()
//
////            canvas.drawText(line, x, y, shadowPaint)
//            canvas.drawTextOnPath(line,path, 0f, 0f, shadowPaint)
//
//        }
//        canvas.restore()

//        drawCurvedText(canvas)
//        super.onDraw(canvas)
    }

    fun updateCurveAmount(amount: Float) {
        curveAmount = amount
        updateView()
    }


    private fun updateView() {
        // Invalidate view to trigger redraw
        invalidate()
    }

    private fun drawTextWithStroke(
        canvas: Canvas,
        textPath: Path,
        text: String,
        xStroke: Float,
        yStroke: Float
    ) {

        if (strokeWidth > 0f) {
            strokePaint.strokeWidth = strokeWidth
            strokePaint.style = Paint.Style.FILL_AND_STROKE
            strokePaint.strokeJoin = Paint.Join.ROUND
            strokePaint.strokeCap = Paint.Cap.ROUND

            // Thiết lập kiểu đường stroke
            when (strokeStyle) {
                StrokeStyle.OUTSIDE -> {
                    strokePaint.strokeMiter = strokeWidth / 2
                }

                StrokeStyle.INSIDE -> {
                    strokePaint.strokeMiter = -strokeWidth / 2
                }

                StrokeStyle.CENTER -> {
                    strokePaint.strokeMiter = 0f
                }
            }

            // Thiết lập màu đường stroke
            strokePaint.color = strokeColor
        }




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Sử dụng setShadowLayer để tạo đường viền
            strokePaint.setShadowLayer(strokeWidth, 0f, 0f, strokeColor)
        }

        canvas.drawTextOnPath(text, textPath, xStroke, yStroke, strokePaint)

    }


    fun updateStrokeColor(strokeColor: Int) {
        this.strokeColor = strokeColor
        if (strokeWidth == 0f) {
            this.strokeWidth = 2f
        }
        updateView()
    }

    fun updateStrokeWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
        updateView()
    }

    fun updateStrokeStyle(strokeStyle: StrokeStyle) {
        this.strokeStyle = strokeStyle
//        updateOutlineProvider()
        updateView()
    }

    private fun calculateCurveHeight(): Float {
        val maxCurveHeight = 100.0f // Giá trị tối đa cho độ cong
        return seekBarProgress / 100f * maxCurveHeight
    }

    // Hàm để cập nhật giá trị từ SeekBar
    fun updateCurveHeight(seekBarProgress: Int) {
        this.seekBarProgress = seekBarProgress
        invalidate() // Yêu cầu vẽ lại
    }

    // Hàm để lấy chiều rộng của văn bản

    private fun calculateSkewX(): Float {
        val maxSkew = 1.0f // Giá trị tối đa cho độ cong (skew)
        val skewValue = (seekBarProgress - 50) / 50f * maxSkew
        return skewValue
    }

    // Hàm để cập nhật giá trị từ SeekBar
    fun updateSkew(seekBarProgress: Int) {
        this.seekBarProgress = seekBarProgress
        invalidate() // Yêu cầu vẽ lại
    }

    fun rotateTextView(degrees: Float) {
        val rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", degrees)
        rotationAnimator.duration = 0 // Duration set to 0 to make it instantaneous
        rotationAnimator.start()
    }

    private fun calculatePath() {
        path.reset()

        val baseline = shadowPaint.fontMetrics.ascent - shadowPaint.fontMetrics.top
        val centerY = height / 2f

        val reversedCurvature = if (curvature > 50) 100 - curvature else curvature

        path.moveTo(0f, centerY)
        path.quadTo(width / 2f, centerY + reversedCurvature * baseline, width.toFloat(), centerY)

        // Điều chỉnh kích thước của văn bản
    }

    private fun drawCurvedText(canvas: Canvas) {
        val textToDraw = text.toString()
        val textWidth = shadowPaint.measureText(textToDraw)

        // Tính toán tỷ lệ kích thước để văn bản ôm bo theo đường cong
        val scale = (width - textWidth) / width

        // Đặt kích thước văn bản
        shadowPaint.textSize = textSize * scale

        // Vẽ văn bản ôm bo theo đường cong
        canvas.drawTextOnPath(textToDraw, path, 0f, 0f, shadowPaint)
    }

    fun setLineSpacingMultiplier(multiplier: Float) {
        lineSpacingMultiplier = multiplier
        updateLineSpacing()
        invalidate()
    }

    fun setFont(typeface: Typeface) {
        this.typeface = typeface
    }

    private fun updateLineSpacing() {
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.isAntiAlias = true
        textPaint.textSize = textSize
        textPaint.textScaleX = 1.0f
        textPaint.textSkewX = 0.0f
        textPaint.isLinearText = true
        textPaint.isSubpixelText = true

        // Tính toán khoảng cách giữa các dòng dựa trên lineSpacingMultiplier
        val lineSpacing = (lineSpacingMultiplier - 1) * textSize
        textPaint.textSize = textSize + lineSpacing
    }

    override fun setOnTouchListener(l: OnTouchListener?) {
        externalOnTouchListener = l

    }

    // Ghi đè phương thức onTouchEvent() để gọi hàm của biến nếu có
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        viewTransformation(this, event!!)
        return true
    }

    fun setTextSize() {
//        val cusTv = Application.selectedView.value as CusTextView
//        this.textSize = cusTv.textSize
    }
}

enum class StrokeStyle {
    OUTSIDE,
    INSIDE,
    CENTER
}