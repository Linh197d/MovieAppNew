package com.qibla.muslimday.app2025.ui.ramadan.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.text.TextUtils
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.customeview.ramadan.EditCharacterClass
import com.qibla.muslimday.app2025.customeview.ramadan.ImageFactoryClass
import com.qibla.muslimday.app2025.ui.ramadan.view.listenner.PickupImgListener
import com.qibla.muslimday.app2025.util.UtilsClass
import java.util.Random
import java.util.Vector

class MannualCollageImgView : View, OnTouchListener {
    private val collageItems: Vector<EditCharacterClass>? = Vector()
    private var downX: Float = 0f
    private var downY: Float = 0f
    private var fixedX: Float = 0f
    private var fixedY: Float = 0f
    private var gestureDetector: GestureDetector? = null
    private var isRotate: Boolean = false
    private var isScale: Boolean = false
    private var isToDrawPath: Boolean = true
    private var isdelete: Boolean = false
    private val outMetrics: DisplayMetrics = DisplayMetrics()
    private var padding: Float = 0f
    private val paint: Paint = Paint()
    var pickupImgListener: PickupImgListener? = null
    private var previousRotate: Float = 0f
    private var previousX1: Float = 0f
    private var previousY1: Float = 0f
    private val random: Random = Random()
    private val roundedBoundaryClipPath: Path = Path()
    private var roundedCornersRadius: Float = 0f
    var selectedImageIndex: Int = -1
    private var systemService: WindowManager? = null
    private var touchArea: Float = 0f
    private var touchX: Float = 0f
    private var touchY: Float = 0f
    fun setOnImagePickListener(pickupImgListener2: PickupImgListener?) {
        pickupImgListener = pickupImgListener2
    }

    fun setImage(bitmap: Bitmap?) {
        val i: Int
        val i2: Int
        if (bitmap != null) {
            var abs: Int = Math.abs(measuredWidth - bitmap.width)
            var abs2: Int = Math.abs(measuredHeight - bitmap.height)
            if (abs <= 0) {
                abs = 100
            }
            if (abs2 <= 0) {
                abs2 = 100
            }
            i2 = random.nextInt(abs)
            i = random.nextInt(abs2)
        } else {
            i2 = 0
            i = 0
        }
        val editCharacterClass: EditCharacterClass =
            EditCharacterClass(0, context, i2.toFloat(), i.toFloat(), bitmap, true)
        editCharacterClass.isFrame = (false)
        val measuredHeight: Float = (measuredHeight.toFloat()) / 4.0f
        editCharacterClass.setWidth(
            (bitmap!!.width.toFloat()) * (measuredHeight / (bitmap.height.toFloat()))
        )
        editCharacterClass.setHeight(measuredHeight)
        editCharacterClass.setCornersPoints()
        collageItems!!.add(editCharacterClass)
        invalidate()
    }

    fun setImage(bitmap: Bitmap?, str: String?) {
        val i: Int
        val i2: Int
        if (bitmap != null) {
            var abs: Int = Math.abs(measuredWidth - bitmap.width)
            var abs2: Int = Math.abs(measuredHeight - bitmap.height)
            if (abs <= 0) {
                abs = 100
            }
            if (abs2 <= 0) {
                abs2 = 100
            }
            i2 = random.nextInt(abs)
            i = random.nextInt(abs2)
        } else {
            i2 = 0
            i = 0
        }
        val editCharacterClass: EditCharacterClass =
            EditCharacterClass(0, context, i2.toFloat(), i.toFloat(), bitmap, true)
        editCharacterClass.isFrame = (false)
        editCharacterClass.bitmapPath = (str)
        val measuredHeight: Float = (measuredHeight.toFloat()) / 4.0f
        editCharacterClass.setWidth(
            (bitmap!!.width.toFloat()) * (measuredHeight / (bitmap.height.toFloat()))
        )
        editCharacterClass.setHeight(measuredHeight)
        editCharacterClass.setCornersPoints()
        collageItems!!.add(editCharacterClass)
        invalidate()
    }

    fun setImagePath(str: String?) {
        if (!TextUtils.isEmpty(str)) {
            setImage(
                UtilsClass.getImageBigThanRequiredHavingPath(
                    str,
                    ((outMetrics.widthPixels.toFloat()) * 0.5f).toInt(),
                    ((outMetrics.heightPixels.toFloat()) * 0.5f).toInt()
                )
            )
        }
    }

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context?, attributeSet: AttributeSet?, i: Int) : super(
        context,
        attributeSet,
        i
    ) {
        init()
    }

    val colllageImagesCount: Int
        get() {
            return collageItems!!.size
        }

    @SuppressLint("WrongConstant")
    private fun init() {
        systemService = context.getSystemService("window") as WindowManager?
        systemService!!.defaultDisplay.getMetrics(outMetrics)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2.0f
        paint.pathEffect = DashPathEffect(floatArrayOf(10.0f, 5.0f), 0.0f)
        padding = UtilsClass.dpToPixel(10.0f, context)
        touchArea =
            (ImageFactoryClass.getBitmap(context, R.drawable.ic_control_btn)!!.width
                .toFloat()) + UtilsClass.dpToPixel(2.0f, context)
        roundedCornersRadius = UtilsClass.dpToPixel(0.0f, context)
        gestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
            public override fun onDoubleTapEvent(motionEvent: MotionEvent): Boolean {
                if (motionEvent.getAction() == 1 && selectedImageIndex == -1) {
                    pickupImgListener!!.pickImage(
                        getX() + motionEvent.getX(),
                        getY() + motionEvent.getY()
                    )
                }
                return super.onDoubleTapEvent(motionEvent)
            }
        })
    }

    fun setBoundaryColor(i: Int) {
        paint.setColor(i)
        invalidate()
    }

    public override fun onMeasure(i: Int, i2: Int) {
        super.onMeasure(i, i2)
    }

    public override fun layout(i: Int, i2: Int, i3: Int, i4: Int) {
        super.layout(i, i2, i3, i4)
    }

    public override fun onDraw(canvas: Canvas) {
        roundedBoundaryClipPath.reset()
        if (isToDrawPath) {
            canvas.drawPath(roundedBoundaryClipPath, paint)
        }
        for (i in collageItems!!.indices) {
            collageItems.get(i).draw(canvas, getContext())
        }
    }

    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setOnTouchListener(this)
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        setOnTouchListener(null as OnTouchListener?)
    }

    public override fun dispatchTouchEvent(motionEvent: MotionEvent): Boolean {
        isToDrawPath = motionEvent.getMetaState() != 5363534
        if (collageItems != null && collageItems.size > 0) {
            Log.d("DEBUG", "Mannual Collage has " + collageItems.size + "  items")
        }
        return super.dispatchTouchEvent(motionEvent)
    }

    public override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val motionEvent2: MotionEvent = motionEvent
        gestureDetector!!.onTouchEvent(motionEvent2)
        touchX = motionEvent.x
        touchY = motionEvent.y
        if ((collageItems != null) && (selectedImageIndex >= 0) && (selectedImageIndex < collageItems.size)) {
            collageItems[selectedImageIndex].onTouch(motionEvent2)
        }
        if (motionEvent.action == 0) {
            downX = motionEvent.x
            downY = motionEvent.y
            fixedX = downX
            fixedY = downY
            if (selectedImageIndex >= 0) {
                collageItems!![selectedImageIndex].isAnimate = (false)
            }
            selectedImageIndex = getFocussedImageIndex(downX, downY)
            if (selectedImageIndex >= 0) {
                setImageToTopAtIndex(selectedImageIndex)
                collageItems!![selectedImageIndex].isAnimate = (true)
                val editCharacterClass: EditCharacterClass = collageItems[selectedImageIndex]
                previousX1 = editCharacterClass.x1
                previousY1 = editCharacterClass.y1
                previousRotate = editCharacterClass.rotate
                isScale =
                    !((downX > editCharacterClass.x3 + touchArea) || (downX < editCharacterClass.x3 - touchArea) || (downY > editCharacterClass.y3 + touchArea) || (downY < editCharacterClass.y3 - touchArea))
                isRotate =
                    !(isScale || (downX > editCharacterClass.x2 + touchArea) || (downX < editCharacterClass.x2 - touchArea) || (downY > editCharacterClass.y2 + touchArea) || (downY < editCharacterClass.y2 - touchArea))
                isdelete =
                    !(isScale || isRotate || (downX > editCharacterClass.x1 + touchArea) || (downX < editCharacterClass.x1 - touchArea) || (downY > editCharacterClass.y1 + touchArea) || (downY < editCharacterClass.y1 - touchArea))
            }
        }
        if (motionEvent.getAction() == 2) {
            val f: Float = touchX - downX
            val f2: Float = touchY - downY
            if (selectedImageIndex >= 0) {
                val editCharacterClass2: EditCharacterClass = collageItems!!.get(
                    selectedImageIndex
                )
                if (isdelete) {
                    isdelete =
                        !((touchX > editCharacterClass2.x1 + touchArea) || (touchX < editCharacterClass2.x1 - touchArea) || (touchY > editCharacterClass2.y1 + touchArea) || (touchY < editCharacterClass2.y1 - touchArea))
                } else if (isScale) {
                    val f3: Float = (previousX1 + touchX) / 2.0f
                    val f4: Float = (previousY1 + touchY) / 2.0f
                    val d: Double = previousRotate.toDouble()
                    java.lang.Double.isNaN(d)
                    val sin: Double = Math.sin(d * -0.017453293)
                    val sqrt: Double = ((Math.sqrt(
                        Math.pow(
                            (touchX - previousX1).toDouble(),
                            2.0
                        ) + Math.pow((touchY - previousY1).toDouble(), 2.0)
                    ).toFloat()) / 2.0f).toDouble()
                    java.lang.Double.isNaN(sqrt)
                    val d2: Double = previousRotate.toDouble()
                    java.lang.Double.isNaN(d2)
                    val cos: Double = Math.cos(d2 * -0.017453293)
                    java.lang.Double.isNaN(sqrt)
                    val d3: Double = sqrt
                    val f5: Float = f3
                    val degrees: Double = 180.0 - Math.toDegrees(
                        UtilsClass.angleBetweenTwoPointsWithFixedPoint(
                            (f3 - ((sin * sqrt).toFloat())).toDouble(),
                            (f4 - ((cos * sqrt).toFloat())).toDouble(),
                            touchX.toDouble(),
                            touchY.toDouble(),
                            f3.toDouble(),
                            f4.toDouble()
                        )
                    )
                    val d4: Double = (previousRotate + 180.0f).toDouble()
                    java.lang.Double.isNaN(d4)
                    val d5: Double = (d4 + degrees) * -0.017453293
                    val sin2: Double = Math.sin(d5)
                    java.lang.Double.isNaN(d3)
                    val cos2: Double = Math.cos(d5)
                    java.lang.Double.isNaN(d3)
                    val f6: Float = f5 - ((sin2 * d3).toFloat())
                    val f7: Float = f4 - ((cos2 * d3).toFloat())
                    val sqrt2: Float = Math.sqrt(
                        Math.pow(
                            (previousX1 - f6).toDouble(),
                            2.0
                        ) + Math.pow((previousY1 - f7).toDouble(), 2.0)
                    ).toFloat()
                    val sqrt3: Float = Math.sqrt(
                        Math.pow(
                            (touchX - f6).toDouble(),
                            2.0
                        ) + Math.pow((touchY - f7).toDouble(), 2.0)
                    ).toFloat()
                    val d6: Double = (-degrees) * -0.017453293
                    val sin3: Double = Math.sin(d6)
                    java.lang.Double.isNaN(d3)
                    val cos3: Double = Math.cos(d6)
                    java.lang.Double.isNaN(d3)
                    val f8: Float = f5 - ((sin3 * d3).toFloat())
                    val f9: Float = f4 - ((cos3 * d3).toFloat())
                    if (sqrt3 > editCharacterClass2.minWidth) {
                        editCharacterClass2.setWidth(sqrt3)
                        editCharacterClass2.setxPos(f8)
                        editCharacterClass2.setyPos(f9)
                    }
                    if (sqrt2 > editCharacterClass2.minHeight) {
                        editCharacterClass2.setHeight(sqrt2)
                        editCharacterClass2.setxPos(f8)
                        editCharacterClass2.setyPos(f9)
                    }
                } else if (isRotate) {
                    editCharacterClass2.rotate = (
                            ((previousRotate.toInt()) + (Math.toDegrees(
                                UtilsClass.angleBetweenTwoPointsWithFixedPoint(
                                    fixedX.toDouble(),
                                    fixedY.toDouble(),
                                    touchX.toDouble(),
                                    touchY.toDouble(),
                                    ((editCharacterClass2.getxPos() + (editCharacterClass2.theWidth / 2.0f)).toInt()).toDouble(),
                                    ((editCharacterClass2.getyPos() + (editCharacterClass2.theHeight / 2.0f)).toInt()).toDouble()
                                )
                            ).toInt())).toFloat()
                            )
                } else {
                    editCharacterClass2.setxPos(editCharacterClass2.getxPos() + f)
                    editCharacterClass2.setyPos(editCharacterClass2.getyPos() + f2)
                }
            }
            downX = touchX
            downY = touchY
        }
        if (motionEvent.getAction() == 1) {
            if (isdelete) {
                collageItems!!.removeAt(selectedImageIndex)
                selectedImageIndex = -1
            }
            previousRotate = 0.0f
            isdelete = false
            isScale = false
            isRotate = false
        }
        invalidate()
        return true
    }

    private fun setImageToTopAtIndex(i: Int) {
        if (i >= 0) {
            collageItems!!.removeAt(i)
            collageItems.add(collageItems.get(selectedImageIndex))
            selectedImageIndex = collageItems.size - 1
        }
    }

    private fun getFocussedImageIndex(f: Float, f2: Float): Int {
        for (size in collageItems!!.indices.reversed()) {
            val editCharacterClass: EditCharacterClass = collageItems[size]
            if (editCharacterClass.isTouchInside(f, f2) && !editCharacterClass.isFrame) {
                return size
            }
        }
        return -1
    }

    fun setCornersRadius(f: Float) {
        roundedCornersRadius = UtilsClass.dpToPixel(f / 2.0f, getContext())
        invalidate()
    }

    fun setGap(f: Float) {
        padding = UtilsClass.dpToPixel(f / 2.0f, getContext())
        invalidate()
    }
}