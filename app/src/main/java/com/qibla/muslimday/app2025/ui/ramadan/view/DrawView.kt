package com.qibla.muslimday.app2025.ui.ramadan.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Cap
import android.graphics.Paint.Join
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.internal.view.SupportMenu
import com.qibla.muslimday.app2025.domain.draw.DataJSONConst
import com.qibla.muslimday.app2025.domain.draw.PathInfoClass
import com.qibla.muslimday.app2025.domain.draw.TouchDataClass
import com.qibla.muslimday.app2025.util.UtilsClass
import java.util.Vector
import kotlin.math.abs

class DrawView constructor(context: Context) : View(context), DataJSONConst {
    private val context: Context
    private var eraserSize: Float
    var floatVector: Vector<Float?> = Vector<Float?>()
    var isErase: Boolean = false
    var isPainting: Boolean = false
    private var mPaint: Paint
    var mPath: Path? = null
    private var mX: Float = 0f
    private var mY: Float = 0f
    private var paintSize: Float
    var eraserSizePercentage: Float = 20f

    var paintPercentageSize: Float
        private set

    @SuppressLint("RestrictedApi")
    private var seleectedColorCode: Int = SupportMenu.CATEGORY_MASK
    var smoothEdgeSize: Float
    var touchDataClassCurrent: TouchDataClass? = null
    private val vecPath: Vector<PathInfoClass?>
    private val vecPathRedo: Vector<PathInfoClass?>
    var vecTouchDataClassForPath: Vector<TouchDataClass?>
    var vecTouchDataClassForPathRedo: Vector<TouchDataClass?>
    private var ignoreActionsOnBackPress: Boolean = false

    init {

        this.context = context
        vecPath = Vector<PathInfoClass?>()
        vecPathRedo = Vector<PathInfoClass?>()
        vecTouchDataClassForPath = Vector<TouchDataClass?>()
        vecTouchDataClassForPathRedo = Vector<TouchDataClass?>()
        mPaint = Paint()
        paintPercentageSize = 20.0f
        eraserSizePercentage = 20.0f
        paintSize = 10f
        eraserSize = 10f
        smoothEdgeSize = 0.0f
        setLayerType(LAYER_TYPE_SOFTWARE, Paint())
    }


    fun setPaintingNew(z: Boolean) {
        isPainting = z
        if (z) {
            setPaintForPaint()
        }
    }

    fun checkUndoList(): Boolean {
        return vecTouchDataClassForPath.size > 0
    }

    fun checkRedoList(): Boolean {
        return vecTouchDataClassForPathRedo.size > 0
    }

    fun redo() {
        if (vecPathRedo.size > 0) {
            vecPath.add(vecPathRedo.removeAt(vecPathRedo.size - 1))
            vecTouchDataClassForPath.add(
                vecTouchDataClassForPathRedo.removeAt(
                    vecTouchDataClassForPathRedo.size - 1
                )
            )
            invalidate()
        }
    }

    fun clearAllPaths() {
        vecPath.clear()
        vecPathRedo.clear()
        vecTouchDataClassForPath.clear()
        vecTouchDataClassForPathRedo.clear()
        invalidate()
    }

    fun cancelView() {
        ignoreActionsOnBackPress = true
        invalidate()
    }

    fun undo() {
        if (vecPath.size > 0) {
            vecPathRedo.add(vecPath.removeAt(vecPath.size - 1))
            vecTouchDataClassForPathRedo.add(
                vecTouchDataClassForPath.removeAt(
                    vecTouchDataClassForPath.size - 1
                )
            )
            invalidate()
        }
    }


    fun setEraseNew(z: Boolean) {
        isErase = z
        if (z) {
            setPaintForErase()
        }
    }


    private fun setEraserSizePercentageUpDate(f: Float) {
        eraserSizePercentage = f
        eraserSize = updatebrushsize(eraserSizePercentage.toInt(), 2.0f)
    }

    fun updatebrushsize(currentbs: Int, scale: Float): Float {
        return (currentbs.toFloat()) / scale
    }

    public override fun onSizeChanged(i: Int, i2: Int, i3: Int, i4: Int) {
        super.onSizeChanged(i, i2, i3, i4)
    }

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in vecPath.indices) {
            val pathInfoClass: PathInfoClass? = vecPath[i]
            if (pathInfoClass != null) {
                canvas.drawPath(pathInfoClass.path!!, pathInfoClass.paint!!)
            }
        }
        if (mPath == null) {
            return
        }

        canvas.drawPath(mPath!!, mPaint)

    }


    private fun touch_start(f: Float, f2: Float) {
        createPaint()
        if (isPainting) {
            mPaint.strokeWidth = paintSize
            if (smoothEdgeSize >= 0.5f) {
                mPaint.maskFilter = BlurMaskFilter(smoothEdgeSize, Blur.NORMAL)
            } else {
                mPaint.maskFilter = null
            }
        } else if (isErase) {
            mPaint.strokeWidth = eraserSize
            if (smoothEdgeSize >= 0.5f) {
                mPaint.maskFilter = BlurMaskFilter(smoothEdgeSize, Blur.NORMAL)
            } else {
                mPaint.maskFilter = null
            }
        }
        mPath = Path()
        mPath!!.moveTo(f, f2)
        mX = f
        mY = f2
    }

    private fun touch_move(f: Float, f2: Float) {
        val abs: Float = abs(f - mX)
        val abs2: Float = abs(f2 - mY)
        if (abs >= 4.0f || abs2 >= 4.0f) {
            mPath!!.quadTo(mX, mY, (mX + f) / 2.0f, (mY + f2) / 2.0f)
            mX = f
            mY = f2
        }
    }

    private fun touch_up() {
        createPaint()
        mPath!!.lineTo(mX, mY)
        val pathInfoClass: PathInfoClass = PathInfoClass(mPath, mPaint)
        vecPath.add(pathInfoClass)
        vecPathRedo.removeAllElements()
        if (isPainting) {
            setPaintForPaint()
        } else if (isErase) {
            setPaintForErase()
        }
        mPath = null
    }

    private fun setPaintForErase() {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Join.ROUND
        mPaint.strokeCap = Cap.ROUND
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        mPaint.color = 0
    }

    private fun setPaintForPaint() {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.color = seleectedColorCode
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Join.ROUND
        mPaint.strokeCap = Cap.ROUND
        mPaint.shader = null
    }

    public override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        val x: Float = motionEvent.x
        val y: Float = motionEvent.y
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
//                if (isPainting || isErase) {
//                    createPaint()
//                }
                touch_start(x, y)
                touchDataClassCurrent = TouchDataClass()
                touchDataClassCurrent!!.isErasing = isErase
                touchDataClassCurrent!!.isPainting = isPainting
                touchDataClassCurrent!!.brushSmoothnessSize = smoothEdgeSize
                touchDataClassCurrent!!.paintSizePercentage = paintPercentageSize
                touchDataClassCurrent!!.eraserSizePercentage = eraserSizePercentage
                touchDataClassCurrent!!.paintColor = seleectedColorCode
                floatVector.clear()
                floatVector.add(java.lang.Float.valueOf(x))
                floatVector.add(java.lang.Float.valueOf(y))
                invalidate()

                //draw vector
//                val paint = Paint().apply {
//                    color = Color.BLACK // Màu vẽ
//                    strokeWidth = 5f // Độ dày đường vẽ
//                    isAntiAlias = true // Có thể thay đổi tùy theo yêu cầu
//                    style = Paint.Style.STROKE
//                    // Các thuộc tính vẽ khác
//                }
//
//                val touchPoints = mutableListOf(Pair(x, y))
//
//                // Vẽ khi có sự chạm
//                drawPathByMovingTouch(touchPoints, paint)
            }

            MotionEvent.ACTION_UP -> {
                touch_up()
                val fArr: FloatArray = FloatArray(floatVector.size)
                var i: Int = 0
                while (i < fArr.size) {
                    fArr[i] = (floatVector.get(i)!!)
                    i++
                }
                floatVector.clear()
                touchDataClassCurrent!!.touchPoints = fArr
                vecTouchDataClassForPath.add(touchDataClassCurrent)
                vecTouchDataClassForPathRedo.removeAllElements()
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                touch_move(x, y)
                floatVector.add(java.lang.Float.valueOf(x))
                floatVector.add(java.lang.Float.valueOf(y))
                invalidate()


//                //draw vector
//                vecPath.lastOrNull()?.let { lastPathInfo ->
//                    lastPathInfo.path?.let { lastPath ->
//                        lastPath.lineTo(x, y)
//                        invalidate()
//                    }
//                }
            }
        }
        if (isPainting || isErase) {
            return true

        }
        return false
    }

    fun setSize(f: Float) {
        if (isErase) {
            setEraserSizePercentageUpDate(f)
        } else if (isPainting) {
            setPaintSizePercentage(f)
        }
    }

    private fun setPaintSizePercentage(f: Float) {
        paintPercentageSize = f
        paintSize = updatebrushsize(paintPercentageSize.toInt(), 2.0f)
        val stringBuilder: StringBuilder = StringBuilder()
        stringBuilder.append("setPaintSizePercentage: Width: ")
        stringBuilder.append(width)
        stringBuilder.append("  Height: ")
        stringBuilder.append(height)
        stringBuilder.append("paint size ")
        stringBuilder.append(paintPercentageSize)
        Log.d("NAME_ART", stringBuilder.toString())
    }


    fun setPaintColor(i: Int) {
        seleectedColorCode = i
        mPaint.color = i
    }

    fun setSmoothEdgePosition(f: Float) {
        smoothEdgeSize = UtilsClass.range(0.0f, 10.0f, f)
        val stringBuilder: StringBuilder = StringBuilder()
        stringBuilder.append(smoothEdgeSize)
        stringBuilder.append("")
        Log.d("smooth size ", stringBuilder.toString())
    }

    val jSONPaintData: Array<TouchDataClass?>
        get() {
            val touchDataClassArr: Array<TouchDataClass?> = arrayOfNulls(
                vecTouchDataClassForPath.size
            )
            val it: Iterator<*> = vecTouchDataClassForPath.iterator()
            var i: Int = 0
            while (it.hasNext()) {
                val i2: Int = i + 1
                touchDataClassArr[i] = it.next() as TouchDataClass?
                i = i2
            }
            return touchDataClassArr
        }

    fun setPaintDataFromJSON(touchDataClassArr: Array<TouchDataClass?>?) {
        vecPath.clear()
        vecPathRedo.clear()
        vecTouchDataClassForPath.removeAllElements()
        for (add: TouchDataClass? in touchDataClassArr!!) {
            vecTouchDataClassForPath.add(add)
        }
        val it: Iterator<*> = vecTouchDataClassForPath.iterator()
        while (it.hasNext()) {
            val touchDataClass: TouchDataClass = it.next() as TouchDataClass
            setEraseNew(touchDataClass.isErasing)
            setPaintingNew(touchDataClass.isPainting)
            setPaintColor(touchDataClass.paintColor)
            setPaintSizePercentage(touchDataClass.paintSizePercentage)
            setEraserSizePercentageUpDate(touchDataClass.eraserSizePercentage)
            smoothEdgeSize = touchDataClass.brushSmoothnessSize
            if (touchDataClass.touchPoints != null) {
                var i: Int = 2
                if (touchDataClass.touchPoints!!.size > 2) {
                    touch_start(
                        touchDataClass.touchPoints!![0],
                        touchDataClass.touchPoints!![1]
                    )
                    while (i < touchDataClass.touchPoints!!.size - 1) {
                        touch_move(
                            touchDataClass.touchPoints!![i],
                            touchDataClass.touchPoints!![i + 1]
                        )
                        i += 2
                    }
                    touch_up()
                }
            }
        }
        setEraseNew(false)
        setPaintingNew(false)
    }

    private fun createPaint() {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Join.ROUND
        mPaint.strokeCap = Cap.ROUND

        // Nếu đang vẽ thì đặt kích thước cho Paint
        if (isPainting) {
            mPaint.color = seleectedColorCode
            mPaint.strokeWidth = paintSize
            if (smoothEdgeSize >= 0.5f) {
                mPaint.maskFilter = BlurMaskFilter(smoothEdgeSize, Blur.NORMAL)
            } else {
                mPaint.maskFilter = null
            }
        } else if (isErase) {
            // Nếu đang xóa thì đặt kích thước cho Eraser
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            mPaint.color = 0
            mPaint.strokeWidth = eraserSize
            if (smoothEdgeSize >= 0.5f) {
                mPaint.maskFilter = BlurMaskFilter(smoothEdgeSize, Blur.NORMAL)
            } else {
                mPaint.maskFilter = null
            }
        }
    }

    companion object {
        var PAINT_MAX_SIZE: Float = -1.0f
        var PAINT_MINIMUM_SIZE: Float = -1.0f

        fun dpToPx(c: Context, dp: Int): Int {
            val f: Float = dp.toFloat()
            c.resources
            return (f * Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}