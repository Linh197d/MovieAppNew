package com.qibla.muslimday.app2025.ui.ramadan.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.qibla.muslimday.app2025.R
import com.qibla.muslimday.app2025.customeview.ramadan.EditCharacterClass
import com.qibla.muslimday.app2025.customeview.ramadan.EditTextClass
import com.qibla.muslimday.app2025.customeview.ramadan.GestureHandlerClass
import com.qibla.muslimday.app2025.customeview.ramadan.ImageFactoryClass
import com.qibla.muslimday.app2025.domain.draw.BGClassInfo
import com.qibla.muslimday.app2025.domain.draw.BGGradientInfo
import com.qibla.muslimday.app2025.domain.draw.MagicData
import com.qibla.muslimday.app2025.domain.draw.StickersDataClass
import com.qibla.muslimday.app2025.domain.draw.TextDataClass
import com.qibla.muslimday.app2025.helper.AppPreference
import com.qibla.muslimday.app2025.ui.ramadan.model.FontModel
import com.qibla.muslimday.app2025.ui.ramadan.view.listenner.ObjRemovedListener
import com.qibla.muslimday.app2025.ui.ramadan.view.listenner.OnTxtEditorListener
import com.qibla.muslimday.app2025.util.Const
import com.qibla.muslimday.app2025.util.UtilsClass
import java.io.IOException
import java.io.InputStream
import java.lang.Math.pow
import java.lang.Math.toDegrees
import java.util.Collections
import java.util.Random
import java.util.Vector
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

@SuppressLint("WrongConstant")
open class FrameSetView : View, OnTouchListener {
    //    private var activity: RamadanActivity? = null
    var bgClassInfo: BGClassInfo? = BGClassInfo()
    var bgColorCode: Int = ViewCompat.MEASURED_SIZE_MASK
    private var bitmap: Bitmap? = null
    private var distanceOld: Double = 0.0
    private var downTextX4: Float = 0f
    private var downTouchY4: Float = 0f
    private var downX: Float = 0f
    private var downY: Float = 0f
    private var editListener: OnTxtEditorListener? = null
    private var fixedX: Double = 0.0
    private var fixedY: Double = 0.0
    private var frame: EditCharacterClass? = null
    private var gestureDetector: GestureDetector? = null
    private var isBGClass: Boolean = false
    private var isDelete: Boolean = false
    private var isFlip: Boolean = false
    private var isPrintLogo: Boolean = false
    var isPrintLogoSmall: Boolean = false
    private var isRotate: Boolean = false
    private var isScale: Boolean = false
    private var isToRemoveBackground: Boolean = true
    private var isToRepeat: Boolean = false
    private var isTouchDown: Boolean = false
    var isTouchEnable: Boolean = true
    private var objRemovedListener: ObjRemovedListener? = null
    private val line: Paint = Paint()
    private val pBg: Paint = Paint()
    private val paintLogo: Paint = Paint()
    private var previousRotate: Int = 0
    private var previousSelectedPosition: Int = -1
    private var previousX1: Float = 0f
    private var previousY1: Float = 0f
    private val random: Random = Random()
    private var selectedCharacterPosition: Int = -1
    private var size: Float = 0f
    val stickerClassesVector: Vector<StickersDataClass?> = Vector<StickersDataClass?>()
    val stickers: Vector<GestureHandlerClass?>? = Vector<GestureHandlerClass?>()
    val listId: ArrayList<Int> = ArrayList()
    private val tempPaint: Paint = Paint()
    val textDataClassesVector: Vector<TextDataClass?> = Vector<TextDataClass?>()
    private var toast: Toast? = null
    private var touchArea: Float = 0f
    var rotationXValue = 0f
    var rotationYValue = 0f
    private val x: Int = 0
    private val y: Int = 0

    var newSize = 0f
    var editCharacterClass: EditCharacterClass? = null
    var pair: Pair<Float, Float>? = null
    open fun itemDeslected() {}
    open fun itemSelected() {}
    open fun removeFocus() {}

    constructor(context: Context?) : super(context) {
//        if (context is RamadanActivity) {
//            activity = context
//        }
        setOnTouchListener(this)
        touchArea =
            (ImageFactoryClass.getBitmap(context!!, R.drawable.ic_control_btn)!!.width
                .toFloat()) + UtilsClass.dpToPixel(2.0f, getContext())
        gestureDetector = GestureDetector(getContext(), object : SimpleOnGestureListener() {
            public override fun onDoubleTapEvent(motionEvent: MotionEvent): Boolean {
                if (!isTouchEnable) {
                    return false
                }
                if ((motionEvent.action == 1)
                    && (editListener != null)
                    && (selectedCharacterPosition >= 0)
                    && (stickers!![selectedCharacterPosition] is EditTextClass)
                ) {
                    editListener!!.onTextEdit(stickers[selectedCharacterPosition] as EditTextClass?)
                }
                return super.onDoubleTapEvent(motionEvent)
            }

        })
    }

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet)
    constructor(context: Context?, attributeSet: AttributeSet?, i: Int) : super(
        context,
        attributeSet,
        i
    )

    fun isPrintLogo(): Boolean {
        return isPrintLogo
    }

//    fun setUpViewModel(viewModel: CardViewModel, undoRedoManagernew: UndoRedoManager) {
//        viewModelCreate = viewModel
//        undoRedoManager = undoRedoManagernew
//
//    }

    fun setBackgroundClass(z: Boolean) {
        isBGClass = z
    }

    fun setPrintLogo(z: Boolean) {
        isPrintLogo = z
        postInvalidate()
    }

    fun setOnTextEditListener(onTxtEditorListener: OnTxtEditorListener?) {
        editListener = onTxtEditorListener
    }

    val isBitmapSet: Boolean
        get() {
            return frame != null
        }

    fun removeBackgroundOrFrame(z: Boolean) {
        isToRemoveBackground = z
        if (frame != null) {
            if (frame!!.bitmap != null) {
                frame!!.bitmap?.recycle()
                frame!!.bitmap = (null)
            }
            frame = null
        }
        invalidate()
    }

    fun setBGGradient(gradientDrawable: GradientDrawable?, bGGradientInfo: BGGradientInfo?) {
        removeBackgroundOrFrame(false)
        setBackgroundColor(0)
        background = gradientDrawable
        if (isBGClass) {
            bgClassInfo!!.type = 4
            bgClassInfo!!.gradient = bGGradientInfo
        }
    }

    fun setFrameOrBackground(str: String?, i: Int, z: Boolean, bitmapImg: Bitmap?) {
        isToRepeat = z
        isToRemoveBackground = false
        if (!TextUtils.isEmpty(str)) {
            if (isBGClass) {
                bgClassInfo!!.type = 3
//                bgClassInfo!!.path = str
            }

            Log.d("ntt", "setFrameOrBackground: $str")
            if (bitmapImg == null) {
                bitmap = loadImageFromAssets(context, str)
            } else {
                bitmap = bitmapImg
            }

            Log.d("ntt", "setFrameOrBackground: bitmap - $bitmap")

            if (bitmap != null) {
                val newId = AppPreference.getCountItemId()
                frame = EditCharacterClass(newId, context, 0.0f, 0.0f, bitmap, false, i)
                frame!!.setWidth(measuredWidth.toFloat())
                frame!!.setHeight(measuredHeight.toFloat())
                frame!!.isFrame = (true)
                AppPreference.increaseCountItemId()
            } else {
                Toast.makeText(
                    context,
                    "unable load image",
                    0
                ).show()
            }
        } else if (i != 0) {
            if (isBGClass) {
                bgClassInfo!!.type = 2
                bgClassInfo!!.f85id = MagicData.getBGIDForResID(i)
            }
            bitmap = UtilsClass.getImageSmallThanRequired(
                resources,
                i,
                measuredWidth,
                measuredHeight
            )
            val newId = AppPreference.getCountItemId()
            frame = EditCharacterClass(newId, context, 0.0f, 0.0f, bitmap, false, i)
            val stringBuilder: StringBuilder = StringBuilder()
            stringBuilder.append("setFrameOrBackground: ")
            stringBuilder.append(width)
            stringBuilder.append("  ")
            stringBuilder.append(width)
            Log.d("NAME ART", stringBuilder.toString())
            frame!!.setWidth(measuredWidth.toFloat())
            frame!!.setHeight(measuredHeight.toFloat())
            frame!!.isFrame = (true)
            AppPreference.increaseCountItemId()
        } else {
            if (isBGClass) {
                bgClassInfo!!.type = 3
                /*
                                bgClassInfo!!.path = EditCardActivity.imagePath
                */
            }
            val newId = AppPreference.getCountItemId()
            /*frame = EditCharacterClass(
                newId,
                context,
                0.0f,
                0.0f,
                EditCardActivity.bitmapImage,
                false,
                i
            )*/
            frame!!.setWidth(measuredWidth.toFloat())
            frame!!.setHeight(measuredHeight.toFloat())
            frame!!.isFrame = (true)
            AppPreference.increaseCountItemId()
        }
        invalidate()
    }

    fun loadImageFromAssets(context: Context, imagePath: String?): Bitmap? {
        val assetManager = context.assets
        return try {
            // Mở tệp ảnh từ thư mục 'assets'
            val inputStream: InputStream = assetManager.open(imagePath!!)

            // Giải mã dữ liệu ảnh thành bitmap

            // Trả về bitmap
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun addStickers(editCharacterClass: EditCharacterClass?) {
        stickers!!.add(editCharacterClass)
        invalidate()
    }

    fun copyStickers(id: Int, editCharacterClassOld: EditCharacterClass?, isRedo: Boolean) {
        var abs: Int =
            abs(measuredWidth - (UtilsClass.dpToPixel(70.0f, context).toInt()))
        var abs2: Int =
            abs(measuredHeight - (UtilsClass.dpToPixel(70.0f, context).toInt()))
        if (abs <= 0) {
            abs = 100
        }
        if (abs2 <= 0) {
            abs2 = 100
        }

        val dataToCopy = if (isRedo) {
            editCharacterClassOld
        } else seletectedSticker
        if (dataToCopy != null) {
            val newId = if (isRedo) id else AppPreference.getCountItemId()
            val copySticker = EditCharacterClass(
                newId,
                context,
                random.nextInt(abs).toFloat(),
                random.nextInt(abs2).toFloat(),
                dataToCopy?.bitmap,
                true,
                dataToCopy?.moduleName,
                dataToCopy?.logoTitle
            )
            copySticker.setItemVisibility(dataToCopy.isItemVisible)
            copySticker.setLockState(dataToCopy.isItemLocked)
            copySticker.setColor(dataToCopy.colorize)
            copySticker.opacity = dataToCopy.opacity
            copySticker.rotate = dataToCopy.rotate
            copySticker.isImageFromGallery = dataToCopy.isImageFromGallery
            copySticker.bitmapPath = dataToCopy.bitmapPath
            copySticker.bitmap = dataToCopy.bitmap
            copySticker.setWidth(dataToCopy.theWidth)
            copySticker.setHeight(dataToCopy.theHeight)
            copySticker.flipImage = dataToCopy.flipImage
            copySticker.shadowRadius = dataToCopy.shadowRadius
            copySticker.setColorShadow(dataToCopy.shadowColor)
            copySticker.shadownXvalue = dataToCopy.shadownXvalue
            copySticker.shadownYvalue = dataToCopy.shadownYvalue
            copySticker.x3dValue = dataToCopy.x3dValue
            copySticker.y3dValue = dataToCopy.y3dValue
            copySticker.flipImage = dataToCopy.flipImage
            copySticker.flipImageVertical = dataToCopy.flipImageVertical

            AppPreference.increaseCountItemId()
            stickers!!.add(copySticker)
            invalidate()
        }
    }

    fun copyText(id: Int, editTextClass: EditTextClass?, isRedo: Boolean) {
        var abs: Int =
            abs(measuredWidth - (UtilsClass.dpToPixel(70.0f, context).toInt()))
        var abs2: Int =
            abs(measuredHeight - (UtilsClass.dpToPixel(70.0f, context).toInt()))
        if (abs <= 0) {
            abs = 100
        }
        if (abs2 <= 0) {
            abs2 = 100
        }
        val dataToCopy = if (isRedo) {
            editTextClass
        } else seletectedTextView
        val newId = if (isRedo) id else AppPreference.getCountItemId()
        val editTextClassCopy = EditTextClass(
            newId,
            dataToCopy?.text,
            width,
            height,
            context
        )
        if (dataToCopy != null) {
            editTextClassCopy.shadowColor = dataToCopy.shadowColor
            editTextClassCopy.shadowRadius = dataToCopy.shadowRadius
            editTextClassCopy.shadownXvalue = dataToCopy.shadownXvalue
            editTextClassCopy.shadownYvalue = dataToCopy.shadownYvalue
            editTextClassCopy.letterSpacing = dataToCopy.letterSpacing
            editTextClassCopy.curvature = dataToCopy.curvature
            editTextClassCopy.strokeWidth = dataToCopy.strokeWidth
            editTextClassCopy.x3d = dataToCopy.x3d
            editTextClassCopy.y3d = dataToCopy.y3d
            editTextClassCopy.opacity = dataToCopy.opacity.toInt()
            editTextClassCopy.fontModel = dataToCopy.fontModel
            editTextClassCopy.colorIndex = dataToCopy.colorIndex
            editTextClassCopy.color = dataToCopy.color
            editTextClassCopy.colorStroke = dataToCopy.colorStroke
            editTextClassCopy.size = dataToCopy.size
            editTextClassCopy.rotate = dataToCopy.rotate
            editTextClassCopy.isBold = dataToCopy.isBold
            editTextClassCopy.isItalic = dataToCopy.isItalic
        }

        AppPreference.increaseCountItemId()
        stickers!!.add(editTextClassCopy)
        invalidate()
    }


    fun addLogo(newIdL: Int, assetManager: AssetManager, str: String, str2: String?) {
        var str: String = str
        var str3: String? = Const.TAG
        var stringBuilder: StringBuilder = StringBuilder()
        stringBuilder.append("addLogo: Logo category name : ")
        stringBuilder.append(str)
        Log.d(str3, stringBuilder.toString())
        str3 = Const.TAG
        stringBuilder = StringBuilder()
        stringBuilder.append("addLogo: Logo Name : ")
        stringBuilder.append(str2)
        Log.d(str3, stringBuilder.toString())
        try {
            val bitmapFromAsset: Bitmap? =
                UtilsClass.getBitmapFromAsset(assetManager, str + str2, null)
            if (bitmapFromAsset == null) {
                Log.d(
                    Const.TAG,
                    "addLogo: Unable to load the bitmap.. Returning from Add Logo"
                )
                return
            }
            var abs: Int =
                abs(measuredWidth - (UtilsClass.dpToPixel(70.0f, context).toInt()))
            var abs2: Int =
                abs(measuredHeight - (UtilsClass.dpToPixel(70.0f, context).toInt()))
            if (abs <= 0) {
                abs = 100
            }
            if (abs2 <= 0) {
                abs2 = 100
            }

            stickers!!.add(
                EditCharacterClass(
                    newIdL,
                    context,
                    (abs / 2).toFloat(),
                    (abs2 / 2).toFloat(),
                    bitmapFromAsset,
                    true,
                    str,
                    str2
                )
            )

            invalidate()
        } catch (e: Exception) {
            str = Const.TAG
            val stringBuilder2: StringBuilder = StringBuilder()
            stringBuilder2.append("addLogo: Exception while decoding Bitmap from Asset ")
            stringBuilder2.append(e)
            Log.e(str, stringBuilder2.toString())
        }
    }

    fun addStickers(i: Int) {
        val bitmap: Bitmap? = UtilsClass.getBitmap(getContext(), i)
        var abs: Int =
            Math.abs(getMeasuredWidth() - (UtilsClass.dpToPixel(70.0f, getContext()).toInt()))
        var abs2: Int =
            Math.abs(getMeasuredHeight() - (UtilsClass.dpToPixel(70.0f, getContext()).toInt()))
        if (abs <= 0) {
            abs = 100
        }
        if (abs2 <= 0) {
            abs2 = 100
        }
        stickers!!.add(
            EditCharacterClass(
                AppPreference.getCountItemId(),
                context,
                random.nextInt(abs).toFloat(),
                random.nextInt(abs2).toFloat(),
                bitmap,
                true,
                i
            )
        )
        AppPreference.increaseCountItemId()
        invalidate()
    }

    fun addTexts(editTextClass: EditTextClass?) {
        stickers!!.add(editTextClass)
        invalidate()
    }

    fun setBgColorCodeNew(i: Int) {
        bgColorCode = i
        setBackgroundColor(i)
        if (isBGClass) {
            bgClassInfo!!.type = 1
            bgClassInfo!!.color = bgColorCode
        }
    }

    @SuppressLint("DrawAllocation")
    public override fun onDraw(canvas: Canvas) {
//        rotationX = rotationXValue
//        rotationY = rotationYValue

        var dpToPx: Int
        var width: Int
        if (!isBGClass && isTouchDown) {
            dpToPx = UtilsClass.dpToPx(context, 2.0f)
            val f: Float = dpToPx.toFloat()
            line.strokeWidth = f
            line.color = -6710887
            val dpToPx2: Int = UtilsClass.dpToPx(context, 8.0f)
            var height: Int = height / 2
            while (height > 0) {
                val f2: Float = height.toFloat()
                canvas.drawLine(0.0f, f2, getWidth().toFloat(), f2, line)
                line.strokeWidth = (dpToPx shr 2).toFloat()
                line.color = -5592406
                height -= dpToPx2
            }
            height = getHeight() / 2
            while (true) {
                height += dpToPx2
                if (height >= getHeight()) {
                    break
                }
                val f3: Float = height.toFloat()
                canvas.drawLine(0.0f, f3, getWidth().toFloat(), f3, line)
            }
            line.strokeWidth = f
            line.color = -6710887
            width = getWidth() / 2
            while (width > 0) {
                val f4: Float = width.toFloat()
                canvas.drawLine(f4, 0.0f, f4, getHeight().toFloat(), line)
                line.strokeWidth = (dpToPx shr 2).toFloat()
                line.color = -5592406
                width -= dpToPx2
            }
            dpToPx = getWidth() / 2
            while (true) {
                dpToPx += dpToPx2
                if (dpToPx >= getWidth()) {
                    break
                }
                val f5: Float = dpToPx.toFloat()
                canvas.drawLine(f5, 0.0f, f5, getHeight().toFloat(), line)
            }
        }
        if (isToRemoveBackground) {
            pBg.color = bgColorCode
            canvas.drawRect(0.0f, 0.0f, getWidth().toFloat(), height.toFloat(), pBg)
            pBg.color = -1
        }
        dpToPx = 0
        while (dpToPx < stickers!!.size) {
            if (stickers[dpToPx] is EditTextClass) {
                val editTextClass: EditTextClass? = stickers[dpToPx] as EditTextClass?

                if (editTextClass!!.isItemVisible) {
                    editTextClass.draw(canvas, context)
                }
            } else if (stickers[dpToPx] is EditCharacterClass) {
                val editCharacterClass: EditCharacterClass? =
                    stickers[dpToPx] as EditCharacterClass?

                if (editCharacterClass!!.isItemVisible) {

                    editCharacterClass.draw(canvas, context)

                }
            }
            dpToPx++
        }
        if (frame != null) {
            if (isToRepeat) {
                pBg.color = -1
                canvas.drawRect(0.0f, 0.0f, getWidth().toFloat(), height.toFloat(), pBg)
                val bitmap: Bitmap? = bitmap
                width = ((getWidth().toFloat()) * 0.025f).toInt()
                val height2: Int = ((height.toFloat()) * 0.025f).toInt()
                val width2: Double = getWidth().toDouble()
                java.lang.Double.isNaN(width2)
                canvas.drawBitmap(
                    (bitmap)!!,
                    null,
                    Rect(
                        width,
                        height2,
                        (width2 * 0.975).toInt(),
                        ((height.toFloat()) * 0.975f).toInt()
                    ),
                    null
                )
            } else {
                frame!!.draw(canvas, context)
            }
        }
        paintLogo.isFilterBitmap = true
        paintLogo.isAntiAlias = true
        val z: Boolean = isPrintLogo
    }

    public override fun dispatchTouchEvent(motionEvent: MotionEvent): Boolean {
        if (isTouchEnable) {
            val x: Float = motionEvent.getX()
            val y: Float = motionEvent.getY()
            if ((stickers != null) && (stickers.size > selectedCharacterPosition) && (selectedCharacterPosition >= 0)) {
                stickers[selectedCharacterPosition]!!.onTouch(motionEvent)
            }
            if (motionEvent.action == 0) {
                previousSelectedPosition = selectedCharacterPosition
                selectedCharacterPosition = getselectedCharacterPosition(x, y)
                if (selectedCharacterPosition >= 0) {
                    onTouch(this, motionEvent)
                    removeFocus()
                    itemSelected()
                    return true
                }
                deselectCharacters()
            }
            if (motionEvent.action != 2 && motionEvent.action != 1) {
                return false
            }
            onTouch(this, motionEvent)
            return true
        }
        deselectCharacters()
        return false
    }

    val selectedItem: GestureHandlerClass?
        get() {
            return if ((stickers!!.size <= selectedCharacterPosition || selectedCharacterPosition < 0)) null else stickers[selectedCharacterPosition]
        }

    private fun getselectedCharacterPosition(f: Float, f2: Float): Int {
        deselectCharacters()
        for (size in stickers!!.indices.reversed()) {
            if (stickers[size] is EditTextClass) {
                val editTextClass: EditTextClass? = stickers[size] as EditTextClass?
                if (editTextClass!!.isItemVisible && !editTextClass.isItemLocked && editTextClass.isInside(
                        f,
                        f2
                    )
                ) {
                    editTextClass.isAnimate = (true)
                    return size
                }
            } else if (stickers.get(size) is EditCharacterClass) {
                val editCharacterClass: EditCharacterClass? =
                    stickers.get(size) as EditCharacterClass?
                if (editCharacterClass!!.isItemVisible && !editCharacterClass.isItemLocked && editCharacterClass.isTouchInside(
                        f,
                        f2
                    )
                ) {
                    editCharacterClass.isAnimate = (true)
                    return size
                }
            } else {
                continue
            }
        }
        return -1
    }

    val seletectedSticker: EditCharacterClass?
        get() {
            var i: Int = 0

            if (stickers!!.size == 2) {
                (stickers?.get(i) as EditCharacterClass?)!!.isAnimate = true

                if ((stickers[i] is EditCharacterClass) && stickers[i]!!.isAnimate) {
                    rotationX = stickers[i]!!.rolateX
                    rotationY = stickers[i]!!.rolateY
                    return stickers[i] as EditCharacterClass?
                }
            } else {
                while (i < stickers!!.size) {
                    if ((stickers[i] is EditCharacterClass) && stickers[i]!!.isAnimate) {
                        rotationX = stickers[i]!!.rolateX
                        rotationY = stickers[i]!!.rolateY
                        return stickers[i] as EditCharacterClass?
                    }
                    i++
                }
            }
            return null
        }
    val seletectedTextView: EditTextClass?
        get() {

            var i: Int = 0

//            (stickers?.get(i) as EditTextClass?)!!.isAnimate = true

//            while (i < stickers!!.size) {
//                if ((stickers?.get(i) is EditTextClass) && (stickers[i] as EditTextClass?)!!.isAnimate) {
//                    return stickers[i] as EditTextClass?
//                }
//                i++
//            }
//            return null

            if (stickers!!.size == 2) {
                i = 1
                (stickers?.get(i) as EditTextClass?)!!.isAnimate = true

                if ((stickers?.get(i) is EditTextClass) && (stickers?.get(i) as EditTextClass?)!!.isAnimate) {
                    return stickers[i] as EditTextClass?
                }
            } else {
                while (i < stickers!!.size) {
                    if ((stickers?.get(i) is EditTextClass) && (stickers[i] as EditTextClass?)!!.isAnimate) {
                        return stickers[i] as EditTextClass?
                    }
                    i++
                }
            }

//            if ((stickers?.get(i) is EditTextClass) && (stickers?.get(i) as EditTextClass?)!!.isAnimate) {
//                return stickers[i] as EditTextClass?
//            }
//
            return null
        }


    fun deselectCharacters() {
        itemDeslected()
        var i: Int = 0
        while (i < stickers!!.size) {
            if (stickers.get(i) is EditTextClass) {
                if ((stickers.get(i) as EditTextClass?)!!.isAnimate) {
                    (stickers.get(i) as EditTextClass?)!!.isAnimate = (false)
                }
            } else if ((stickers.get(i) is EditCharacterClass) && (stickers.get(i) as EditCharacterClass?)!!.isAnimate) {
                (stickers.get(i) as EditCharacterClass?)!!.isAnimate = (false)
            }
            i++
        }

        invalidate()
        selectedCharacterPosition = -1
    }

    fun setItemRemovedListener(objRemovedListener: ObjRemovedListener?) {
        this.objRemovedListener = objRemovedListener
    }

    public override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (!isTouchEnable) {
            return false
        }
        var editTextClass: EditTextClass?
        gestureDetector!!.onTouchEvent(motionEvent)
        var x: Float = motionEvent.getX()
        var y: Float = motionEvent.getY()
        if (motionEvent.action == 0) {
            isTouchDown = true
            downX = motionEvent.getX()
            downY = motionEvent.getY()
            fixedX = downX.toDouble()
            fixedY = downY.toDouble()
            if ((selectedCharacterPosition >= 0) && (previousSelectedPosition >= 0) && (previousSelectedPosition == selectedCharacterPosition)) {
                if (stickers!!.get(selectedCharacterPosition) is EditTextClass) {
                    editTextClass = stickers.get(selectedCharacterPosition) as EditTextClass?
                    previousRotate = editTextClass!!.rotate.toInt()
                    downTextX4 = editTextClass.x1
                    downTouchY4 = editTextClass.y1
                    distanceOld = Math.sqrt(
                        pow(
                            (editTextClass.x4 - editTextClass.x2).toDouble(),
                            2.0
                        ) + pow(
                            (editTextClass.y4 - editTextClass.y2).toDouble(),
                            2.0
                        )
                    )
                    size = editTextClass!!.size
                    pair = Pair(size, size)
                    isScale =
                        !((downX > editTextClass.x3 + touchArea) || (downX < editTextClass.x3 - touchArea) || (downY > editTextClass.y3 + touchArea) || (downY < editTextClass.y3 - touchArea))
                    isRotate =
                        !(isScale || (downX > editTextClass.x2 + touchArea) || (downX < editTextClass.x2 - touchArea) || (downY > editTextClass.y2 + touchArea) || (downY < editTextClass.y2 - touchArea))
                    isDelete =
                        !(isScale || isRotate || (downX > editTextClass.x1 + touchArea) || (downX < editTextClass.x1 - touchArea) || (downY > editTextClass.y1 + touchArea) || (downY < editTextClass.y1 - touchArea))
                } else if ((selectedCharacterPosition >= 0) && (previousSelectedPosition >= 0) && (previousSelectedPosition == selectedCharacterPosition) && (stickers.get(
                        selectedCharacterPosition
                    ) is EditCharacterClass)
                ) {
                    val editCharacterClass: EditCharacterClass? = stickers.get(
                        selectedCharacterPosition
                    ) as EditCharacterClass?
                    previousX1 = editCharacterClass!!.x1
                    previousY1 = editCharacterClass.y1
                    previousRotate = editCharacterClass.rotate.toInt()
                    isScale =
                        !((downX > editCharacterClass.x3 + touchArea) || (downX < editCharacterClass.x3 - touchArea) || (downY > editCharacterClass.y3 + touchArea) || (downY < editCharacterClass.y3 - touchArea))
                    isRotate =
                        !(isScale || (downX > editCharacterClass.x2 + touchArea) || (downX < editCharacterClass.x2 - touchArea) || (downY > editCharacterClass.y2 + touchArea) || (downY < editCharacterClass.y2 - touchArea))
                    isDelete =
                        !(isScale || isRotate || (downX > editCharacterClass.x1 + touchArea) || (downX < editCharacterClass.x1 - touchArea) || (downY > editCharacterClass.y1 + touchArea) || (downY < editCharacterClass.y1 - touchArea))
                    isFlip =
                        !(isScale || isRotate || isDelete || (downX > editCharacterClass.x4 + touchArea) || (downX < editCharacterClass.x4 - touchArea) || (downY > editCharacterClass.y4 + touchArea) || (downY < editCharacterClass.y4 - touchArea))
                }
            }
//            if (activity != null) {
////                activity!!.hideLayers(null)
//            }
        }
        if ((motionEvent.action == 2) && (selectedCharacterPosition >= 0) && !stickers!!.get(
                selectedCharacterPosition
            )!!.handlingTwoFingerTouch
        ) {
            var f: Float
            val i: Int = (x - downX).toInt()
            val i2: Int = (y - downY).toInt()
            if (selectedCharacterPosition >= 0) {
                val sqrt: Double
                val theWidth: Double
                if (stickers.get(index = selectedCharacterPosition) is EditTextClass) {
                    if (isDelete) {
                        editTextClass = stickers[selectedCharacterPosition] as EditTextClass?
                        isDelete =
                            !((x > editTextClass!!.x1 + touchArea) || (x < editTextClass.x1 - touchArea) || (y > editTextClass.y1 + touchArea) || (y < editTextClass.y1 - touchArea))
                    } else if (isScale) {
                        editTextClass = stickers[selectedCharacterPosition] as EditTextClass?
                        val d: Double = size.toDouble()
                        sqrt = sqrt(
                            pow(
                                (x - downTextX4).toDouble(),
                                2.0
                            ) + pow((y - downTouchY4).toDouble(), 2.0)
                        ) - distanceOld
                        java.lang.Double.isNaN(d)
                        newSize = (d + sqrt).toFloat()
                        editTextClass!!.setSizeNew((d + sqrt).toFloat())
                    } else if (isRotate) {
                        Log.d("ntt", "onTouch: Rotate")
                        theWidth =
                            (((stickers[selectedCharacterPosition] as EditTextClass?)!!.getxPos()
                                .toInt()) - (((stickers[selectedCharacterPosition] as EditTextClass?)?.theWidth?.toInt())?.shr(
                                1
                            )!!)).toDouble()
                        (stickers[selectedCharacterPosition] as EditTextClass?)!!.rotate =
                            (previousRotate + (toDegrees(
                                UtilsClass.angleBetweenTwoPointsWithFixedPoint(
                                    fixedX,
                                    fixedY,
                                    x.toDouble(),
                                    y.toDouble(),
                                    theWidth,
                                    (((stickers[selectedCharacterPosition] as EditTextClass?)!!.getyPos()
                                        .toInt()) + (((stickers[selectedCharacterPosition] as EditTextClass?)?.theHeight?.toInt())?.shr(
                                        1
                                    )!!)).toDouble()
                                )
                            ).toInt())).toFloat()

                        val oldEditText: EditTextClass? =
                            stickers?.get(selectedCharacterPosition) as EditTextClass?
                        val pair = Pair(oldEditText, oldEditText)
//                        if (viewModelCreate != null) {
//                            makeAction(
//                                viewModelCreate!!,
//                                undoRedoManager!!,
//                                0,
//                                oldEditText?.idItem ?: 0,
//                                1,
//                                AppPreference.ACTION_ROTATION,
//                                null, null,
//                                null, null, pair
//                            )
//                        }
                    } else {
                        (stickers[selectedCharacterPosition] as EditTextClass?)!!.setxPos(
                            (((stickers[selectedCharacterPosition] as EditTextClass?)!!.getxPos()
                                .toInt()) + i).toFloat()
                        )
                        (stickers[selectedCharacterPosition] as EditTextClass?)!!.setyPos(
                            (((stickers[selectedCharacterPosition] as EditTextClass?)!!.getyPos()
                                .toInt()) + i2).toFloat()
                        )
                        if (isDelete && ((i.toFloat()) > UtilsClass.dpToPixel(
                                5.0f,
                                context
                            ) || (i2.toFloat()) > UtilsClass.dpToPixel(5.0f, context))
                        ) {
                            isDelete = false
                        }
                    }
                } else if (stickers[selectedCharacterPosition] is EditCharacterClass) {
                    var editCharacterClass2: EditCharacterClass? =
                        stickers[selectedCharacterPosition] as EditCharacterClass?
                    if (!isDelete) {
                        var d2: Double
                        val d3: Double
                        val f2: Float
                        val f3: Float
                        if (isScale) {
                            f = (previousX1 + x) / 2.0f
                            var f4: Float = (previousY1 + y) / 2.0f
                            val sqrt2: Float = (sqrt(
                                (x - previousX1).toDouble().pow(2.0) + (y - previousY1).toDouble()
                                    .pow(2.0)
                            ).toFloat()) / 2.0f
                            d2 = previousRotate.toDouble()
                            java.lang.Double.isNaN(d2)
                            d2 = sin(d2 * -0.017453293)
                            d3 = sqrt2.toDouble()
                            java.lang.Double.isNaN(d3)
                            val f5: Float = (d2 * d3).toFloat()
                            var d4: Double = previousRotate.toDouble()
                            java.lang.Double.isNaN(d4)
                            d4 = cos(d4 * -0.017453293)
                            java.lang.Double.isNaN(d3)
                            f2 = x
                            val editCharacterClass3: EditCharacterClass? = editCharacterClass2
                            val d5: Double = d3
                            val f6: Float = f
                            f3 = y
                            val toDegrees: Double = 180.0 - toDegrees(
                                UtilsClass.angleBetweenTwoPointsWithFixedPoint(
                                    (f - f5).toDouble(),
                                    (f4 - ((d4 * d3).toFloat())).toDouble(),
                                    x.toDouble(),
                                    y.toDouble(),
                                    f.toDouble(),
                                    f4.toDouble()
                                )
                            )
                            var d6: Double = (previousRotate + 180).toDouble()
                            java.lang.Double.isNaN(d6)
                            d6 = (d6 + toDegrees) * -0.017453293
                            sqrt = sin(d6)
                            java.lang.Double.isNaN(d5)
                            var f7: Float = (sqrt * d5).toFloat()
                            d6 = cos(d6)
                            java.lang.Double.isNaN(d5)
                            val f8: Float = f6 - f7
                            x = f4 - ((d6 * d5).toFloat())
                            f7 = sqrt(
                                (previousX1 - f8).toDouble().pow(2.0) + (previousY1 - x).toDouble()
                                    .pow(2.0)
                            ).toFloat()
                            x = sqrt(
                                pow(
                                    (f2 - f8).toDouble(),
                                    2.0
                                ) + pow((f3 - x).toDouble(), 2.0)
                            ).toFloat()
                            var d7: Double = (-toDegrees) * -0.017453293
                            theWidth = sin(d7)
                            java.lang.Double.isNaN(d5)
                            y = (theWidth * d5).toFloat()
                            d7 = cos(d7)
                            java.lang.Double.isNaN(d5)
                            f = f6 - y
                            f4 -= (d7 * d5).toFloat()
                            if (x > editCharacterClass3!!.minWidth) {
                                editCharacterClass2 = editCharacterClass3
                                editCharacterClass2!!.setWidth(x)
                                editCharacterClass2.setxPos(f)
                                editCharacterClass2.setyPos(f4)
                            } else {
                                editCharacterClass2 = editCharacterClass3
                            }
                            if (f7 > editCharacterClass2.minHeight) {
                                editCharacterClass2!!.setHeight(f7)
                                editCharacterClass2.setxPos(f)
                                editCharacterClass2.setyPos(f4)
                            }
                            x = f2
                            f = f3

                            val oldEditText: EditCharacterClass? =
                                stickers[selectedCharacterPosition] as EditCharacterClass?
                            val pair = Pair(oldEditText, oldEditText)

//                            if (viewModelCreate != null) {
//                                makeAction(
//                                    viewModelCreate!!,
//                                    undoRedoManager!!,
//                                    0,
//                                    oldEditText?.idItem ?: 0,
//                                    1,
//                                    AppPreference.ACTION_SIZE,
//                                    null, null,
//                                    null, null, null, pair
//                                )
//                            }
                        } else {
                            f2 = x
                            f3 = y
                            if (isRotate) {
                                val f9: Float = f2
                                val f10: Float = f3
                                d2 =
                                    ((editCharacterClass2!!.getxPos() + (editCharacterClass2.theWidth / 2.0f)).toInt()).toDouble()
                                val f11: Float = f9
                                d3 =
                                    ((editCharacterClass2.getyPos() + (editCharacterClass2.theHeight / 2.0f)).toInt()).toDouble()
                                x = f11
                                f = f10
                                editCharacterClass2.rotate = (
                                        (previousRotate + (toDegrees(
                                            UtilsClass.angleBetweenTwoPointsWithFixedPoint(
                                                fixedX,
                                                fixedY,
                                                f9.toDouble(),
                                                f10.toDouble(),
                                                d2,
                                                d3
                                            )
                                        ).toInt())).toFloat()
                                        )

                                val oldEditText: EditCharacterClass? =
                                    stickers[selectedCharacterPosition] as EditCharacterClass?
                                val pair = Pair(oldEditText, oldEditText)

//                                if (viewModelCreate != null) {
//                                    makeAction(
//                                        viewModelCreate!!,
//                                        undoRedoManager!!,
//                                        0,
//                                        oldEditText?.idItem ?: 0,
//                                        1,
//                                        AppPreference.ACTION_ROTATION,
//                                        null, null,
//                                        null, null, null, pair
//                                    )
//                                }
                            } else {
                                x = f2
                                f = f3
                                editCharacterClass2!!.setxPos(editCharacterClass2.getxPos() + (i.toFloat()))
                                editCharacterClass2.setyPos(editCharacterClass2.getyPos() + (i2.toFloat()))
                            }
                        }
                        downX = x
                        downY = f
                    } else isDelete =
                        !((x > editCharacterClass2!!.x1 + touchArea) || (x < editCharacterClass2.x1 - touchArea) || (y > editCharacterClass2.y1 + touchArea) || (y < editCharacterClass2.y1 - touchArea))
                }
            }
            f = y
            downX = x
            downY = f
        }
        if (motionEvent.action == 1) {
            isTouchDown = false
            if (isDelete) {
                Log.d("ntt", "onTouch: delete")
//                if (stickers?.get(selectedCharacterPosition) is EditCharacterClass) {
//                    val oldEditText: EditCharacterClass? =
//                        stickers[selectedCharacterPosition] as EditCharacterClass?
//                    val pair = Pair(oldEditText, oldEditText)
//
//                } else {
//                    val oldEditText: EditTextClass? =
//                        stickers?.get(selectedCharacterPosition) as EditTextClass?
//                    val pair = Pair(oldEditText, oldEditText)
//
//                }
//                stickers!!.removeAt(selectedCharacterPosition)
//                objRemovedListener!!.onItemRemoved()
//                selectedCharacterPosition = -1
//                previousSelectedPosition = -1
//                itemDeslected()
            } else if (isScale && (stickers!![selectedCharacterPosition] is EditCharacterClass)) {
//                (stickers[selectedCharacterPosition] as EditCharacterClass?)!!.flip()

            } else if (isScale && (stickers!![selectedCharacterPosition] is EditTextClass)) {
                val oldEditText: EditTextClass? =
                    stickers[selectedCharacterPosition] as EditTextClass?
                pair = pair?.copy(second = newSize)

            }
            previousRotate = 0
            isFlip = false
            isDelete = false
            isScale = false
            isRotate = false
        }
        invalidate()
        return true
    }


    fun showAToast() {
        try {
            if (!toast!!.getView()!!.isShown) {
                toast = Toast.makeText(context, "Please Select any image for filp.", 0)
                toast!!.setGravity(16, 0, 0)
                toast!!.show()
            }
        } catch (unused: Exception) {
            toast = Toast.makeText(context, "Please Select any image for filp.", 0)
            toast!!.setGravity(16, 0, 0)
            toast!!.show()

        }
    }

    var bgClass: BGClassInfo?
        get() {
            return bgClassInfo
        }
        set(bGClassInfo) {
            bgClassInfo = bGClassInfo
            bgColorCode = ViewCompat.MEASURED_SIZE_MASK
            bitmap = null
            frame = null
            background = null
            removeBackgroundOrFrame(true)
            when (bGClassInfo!!.type) {
                1 -> {
                    setFrameOrBackground(bGClassInfo.path, 0, false, null)
                    return
                }

                2 -> {
                    setFrameOrBackground(
                        null,
                        MagicData.getBGResIDForId(bGClassInfo.f85id),
                        true,
                        null
                    )
                    return
                }

                3 -> {
                    setFrameOrBackground(bGClassInfo.path, 0, false, null)
                    return
                }

                4 -> {
                    isToRemoveBackground = true
                    val bGGradientInfo: BGGradientInfo? = bGClassInfo.gradient
                    val gradientDrawable: GradientDrawable = GradientDrawable()
                    gradientDrawable.setSize(width, height)
                    gradientDrawable.colors = bGGradientInfo!!.colors
                    gradientDrawable.gradientType = bGGradientInfo.style
                    gradientDrawable.orientation = GradientDrawable.Orientation.valueOf(
                        bGGradientInfo.direction
                    )
                    gradientDrawable.gradientRadius = bGGradientInfo.radius * (width.toFloat())
                    gradientDrawable.setGradientCenter(
                        bGGradientInfo.centerX,
                        bGGradientInfo.centerY
                    )
                    background = gradientDrawable
                    return
                }

                else -> return
            }
        }

    val stickersClassArray: Array<StickersDataClass?>
        get() {
            stickerClassesVector.clear()
            var i: Int = 0
            for (i2 in stickers!!.indices) {
                if (stickers[i2] is EditCharacterClass) {
                    val editCharacterClass: EditCharacterClass? =
                        stickers[i2] as EditCharacterClass?
                    val stickersDataClass: StickersDataClass = StickersDataClass()
                    stickersDataClass.position = i2
                    stickersDataClass.isVisible = editCharacterClass!!.isItemVisible
                    stickersDataClass.isLocked = editCharacterClass.isItemLocked
                    stickersDataClass.stickerModule = editCharacterClass.moduleName
                    stickersDataClass.stickerName = editCharacterClass.logoTitle
                    stickersDataClass.colorize = editCharacterClass.colorize
                    stickersDataClass.opacity = editCharacterClass.opacity
                    stickersDataClass.angle = editCharacterClass.rotate
                    stickersDataClass.stickerId =
                        MagicData.getStickerIDForResID(editCharacterClass.resID)
                    stickersDataClass.isFromGallery = editCharacterClass.isImageFromGallery
                    stickersDataClass.imagePath = editCharacterClass.bitmapPath
                    stickersDataClass.xPos = editCharacterClass.getxPos()
                    stickersDataClass.yPos = editCharacterClass.getyPos()
                    stickersDataClass.shadowColor = editCharacterClass.shadowColor
                    stickersDataClass.shadownXvalue = editCharacterClass.shadownXvalue
                    stickersDataClass.shadownYvalue = editCharacterClass.shadownYvalue
                    stickersDataClass.shadowRadius = editCharacterClass.shadowRadius
                    stickersDataClass.x3dvalue = editCharacterClass.x3dValue
                    stickersDataClass.y3dvalue = editCharacterClass.y3dValue
                    stickersDataClass.width = editCharacterClass.theWidth
                    stickersDataClass.height = editCharacterClass.theHeight
                    stickersDataClass.isFlipped = editCharacterClass.flipImage
                    stickersDataClass.isFlippedVertical = editCharacterClass.flipImageVertical
                    stickerClassesVector.add(stickersDataClass)
                }
            }
            val stickersDataClassArr: Array<StickersDataClass?> = arrayOfNulls(
                stickerClassesVector.size
            )
            val it: Iterator<*> = stickerClassesVector.iterator()
            while (it.hasNext()) {
                val i3: Int = i + 1
                stickersDataClassArr[i] = it.next() as StickersDataClass?
                i = i3
            }

            Log.d("ntt", "stickerClassesVector : ${stickerClassesVector.size}")
            return stickersDataClassArr
        }
    val textClassArray: Array<TextDataClass?>
        get() {
            textDataClassesVector.clear()
            var i: Int = 0
            for (i2 in stickers!!.indices) {
                if (stickers.get(i2) is EditTextClass) {
                    val editTextClass: EditTextClass? = stickers.get(i2) as EditTextClass?
                    val textDataClass: TextDataClass = TextDataClass()
                    textDataClass.position = i2
                    textDataClass.isVisible = editTextClass!!.isItemVisible
                    textDataClass.isLocked = editTextClass.isItemLocked
                    textDataClass.angle = editTextClass.rotate
                    textDataClass.xPos = editTextClass.getxPos()
                    textDataClass.yPos = editTextClass.getyPos()
                    textDataClass.color = editTextClass.color
                    textDataClass.strokeColor = editTextClass.colorStroke
                    textDataClass.strokeWith = editTextClass.strokeWidth
                    textDataClass.letterSpacing = editTextClass.letterSpacing
                    textDataClass.shadowColor = editTextClass.shadowColor
                    textDataClass.shadowRadius = editTextClass.shadowRadius
                    textDataClass.shadownXvalue = editTextClass.shadownXvalue
                    textDataClass.shadownYvalue = editTextClass.shadownYvalue
                    textDataClass.x3dvalue = editTextClass.x3d
                    textDataClass.y3dvalue = editTextClass.y3d
                    textDataClass.curvature = editTextClass.curvature
                    textDataClass.opacity = editTextClass.opacity
                    textDataClass.text = editTextClass.text
                    textDataClass.fontName = editTextClass.fontModel?.fontName
                    textDataClass.logoFontClass = editTextClass.fontModel
                    textDataClass.size = editTextClass.size
                    textDataClass.isBold = editTextClass.isBold
                    textDataClass.isItalic = editTextClass.isItalic
                    textDataClassesVector.add(textDataClass)
                }
            }
            val textDataClassArr: Array<TextDataClass?> = arrayOfNulls(
                textDataClassesVector.size
            )
            val it: Iterator<*> = textDataClassesVector.iterator()
            while (it.hasNext()) {
                val i3: Int = i + 1
                textDataClassArr[i] = it.next() as TextDataClass?
                i = i3
            }
            return textDataClassArr
        }

    fun setStickersAndTextClassArray(
        stickersDataClassArr: Array<StickersDataClass?>?,
        textDataClassArr: Array<TextDataClass?>?,
    ) {
        val stickersDataClassArr2: Array<StickersDataClass?>? = stickersDataClassArr
        val textDataClassArr2: Array<TextDataClass?>? = textDataClassArr
        Log.d("ntt", "setStickersAndTextClassArray: ")
        stickerClassesVector.clear()
        stickers!!.clear()
        var i: Int = 0
        for (obj: StickersDataClass? in stickersDataClassArr2!!) {
            if (obj != null) {
                stickerClassesVector.add(obj)
                if (obj.isFromGallery) {
                    val decodeFile: Bitmap? = BitmapFactory.decodeFile(obj.imagePath)

                    Log.d("ntt", "setStickersAndTextClassArray: $decodeFile")
                    Log.d("ntt", "setStickersAndTextClassArray imagePath: ${obj.imagePath}")

                    if (decodeFile != null) {
                        editCharacterClass =
                            EditCharacterClass(
                                AppPreference.getCountItemId(),
                                context,
                                obj.xPos,
                                obj.yPos,
                                decodeFile,
                                true
                            )
                        editCharacterClass!!.isFrame = (false)
                        editCharacterClass!!.bitmapPath = (obj.imagePath)
                        editCharacterClass!!.bitmap = decodeFile
                        editCharacterClass!!.isImageFromGallery = (true)

                        if (editCharacterClass != null) {
                            editCharacterClass!!.position = obj.position
                            editCharacterClass!!.setItemVisibility(obj.isVisible)
                            editCharacterClass!!.setLockState(obj.isLocked)
                            editCharacterClass!!.setWidth(obj.width)
                            editCharacterClass!!.setHeight(obj.height)
                            editCharacterClass!!.rotate = (obj.angle)
                            editCharacterClass!!.setColor(obj.colorize)
                            editCharacterClass!!.opacity = obj.opacity
                            editCharacterClass!!.shadowRadius = obj.shadowRadius
                            editCharacterClass!!.shadowColor = obj.shadowColor
                            editCharacterClass!!.shadownXvalue = obj.shadownXvalue
                            editCharacterClass!!.shadownYvalue = obj.shadownYvalue
                            editCharacterClass!!.x3dValue = obj.x3dvalue
                            editCharacterClass!!.y3dValue = obj.y3dvalue
                            editCharacterClass!!.flipImage = (obj.isFlipped)
                            editCharacterClass!!.flipImageVertical = (obj.isFlippedVertical)
                            editCharacterClass!!.bitmapPath = (obj.imagePath)
                            var str2: String? = Const.TAG
                            var stringBuilder2: StringBuilder = StringBuilder()
                            stringBuilder2.append("setStickersAndTextClassArray: Adding Sticker : ")
                            stringBuilder2.append(obj.stickerName)
                            Log.d(str2, stringBuilder2.toString())
                            str2 = Const.TAG
                            stringBuilder2 = StringBuilder()
                            stringBuilder2.append("setStickersAndTextClassArray: of Dimension : ")
                            stringBuilder2.append(obj.width)
                            stringBuilder2.append(" x ")
                            stringBuilder2.append(obj.height)
                            Log.d(str2, stringBuilder2.toString())
                            str2 = Const.TAG
                            stringBuilder2 = StringBuilder()
                            stringBuilder2.append("setStickersAndTextClassArray: at position : ")
                            stringBuilder2.append(obj.xPos)
                            stringBuilder2.append(" - ")
                            stringBuilder2.append(obj.yPos)
                            Log.d(str2, stringBuilder2.toString())
                            stickers.add(editCharacterClass)
                            AppPreference.increaseCountItemId()
                        }

                        AppPreference.increaseCountItemId()
                    } else {
                        Toast.makeText(
                            context,
                            "unable load image",
                            0
                        ).show()
                    }
                } else {
                    if (obj.stickerId == 0) {
                        try {
                            var assets: AssetManager =
//                                if ((obj.stickerModule == Const.LOGO_WITH_APP)) {
                                context.assets
//                                } else {
//                                    SplitCompat.install(
//                                        context.createPackageContext(
//                                            context.packageName,
//                                            0
//                                        )
//                                    )
//                                    context.createPackageContext(
//                                        context.packageName,
//                                        0
//                                    ).assets
//                                }

                            editCharacterClass = EditCharacterClass(
                                AppPreference.getCountItemId(),
                                context,
                                obj.xPos,
                                obj.yPos,
                                UtilsClass.getBitmapFromAsset(
                                    assets,
                                    "${obj.stickerModule}${obj.stickerName}",
                                    null
                                ),
                                true,
                                obj.stickerModule,
                                "${obj.stickerName}"
                            )
                            AppPreference.increaseCountItemId()

                            val i = editCharacterClass
                        } catch (unused: Exception) {

                            var str: String? = Const.TAG
                            var stringBuilder: StringBuilder = StringBuilder()
                            stringBuilder.append("setStickersAndTextClassArray: Module Name : ")
                            stringBuilder.append(obj.stickerModule)
                            Log.d(str, stringBuilder.toString())
                            str = Const.TAG
                            stringBuilder = StringBuilder()
                            stringBuilder.append("setStickersAndTextClassArray: Logo Name : ")
                            stringBuilder.append(obj.stickerName)
                            Log.d(str, stringBuilder.toString())
                            Log.e(
                                "ntt",
                                "setStickersAndTextClassArray: Exception while loading Bitmap from asset"
                            )
                        }
                    } else {
                        val stickerResIdForId: Int = MagicData.getStickerResIdForId(obj.stickerId)
                        editCharacterClass = EditCharacterClass(
                            AppPreference.getCountItemId(),
                            context,
                            obj.xPos,
                            obj.yPos,
                            UtilsClass.getImageSmallThanRequired(
                                resources,
                                stickerResIdForId,
                                measuredWidth,
                                measuredHeight
                            ),
                            true,
                            stickerResIdForId
                        )
                        AppPreference.increaseCountItemId()
                    }
                    if (editCharacterClass != null) {
                        editCharacterClass!!.position = obj.position
                        editCharacterClass!!.setItemVisibility(obj.isVisible)
                        editCharacterClass!!.setLockState(obj.isLocked)
                        editCharacterClass!!.setWidth(obj.width)
                        editCharacterClass!!.setHeight(obj.height)
                        editCharacterClass!!.rotate = (obj.angle)
//                        editCharacterClass!!.setColorizeUpdate(obj.colorize)
                        editCharacterClass!!.setColor(obj.colorize)
                        editCharacterClass!!.opacity = obj.opacity
                        editCharacterClass!!.shadowRadius = obj.shadowRadius
                        editCharacterClass!!.shadowColor = obj.shadowColor
                        editCharacterClass!!.shadownXvalue = obj.shadownXvalue
                        editCharacterClass!!.shadownYvalue = obj.shadownYvalue
                        editCharacterClass!!.x3dValue = obj.x3dvalue
                        editCharacterClass!!.y3dValue = obj.y3dvalue
                        editCharacterClass!!.flipImage = (obj.isFlipped)
                        editCharacterClass!!.flipImageVertical = (obj.isFlippedVertical)
                        editCharacterClass!!.bitmapPath = (obj.imagePath)
                        var str2: String? = Const.TAG
                        var stringBuilder2: StringBuilder = StringBuilder()
                        stringBuilder2.append("setStickersAndTextClassArray: Adding Sticker : ")
                        stringBuilder2.append(obj.stickerName)
                        Log.d(str2, stringBuilder2.toString())
                        str2 = Const.TAG
                        stringBuilder2 = StringBuilder()
                        stringBuilder2.append("setStickersAndTextClassArray: of Dimension : ")
                        stringBuilder2.append(obj.width)
                        stringBuilder2.append(" x ")
                        stringBuilder2.append(obj.height)
                        Log.d(str2, stringBuilder2.toString())
                        str2 = Const.TAG
                        stringBuilder2 = StringBuilder()
                        stringBuilder2.append("setStickersAndTextClassArray: at position : ")
                        stringBuilder2.append(obj.xPos)
                        stringBuilder2.append(" - ")
                        stringBuilder2.append(obj.yPos)
                        Log.d(str2, stringBuilder2.toString())
                        stickers.add(editCharacterClass)
                        AppPreference.increaseCountItemId()
                    }
                }
            }
        }
        val length: Int = textDataClassArr2!!.size
        while (i < length) {
            val obj2: TextDataClass? = textDataClassArr2.get(i)
            if (!((obj2 == null) || (obj2.text == null) || (obj2.text!!.trim { it <= ' ' }
                    .isEmpty()))) {
                val editTextClass: EditTextClass = if (measuredWidth <= 0 || measuredHeight <= 0) {
                    EditTextClass(AppPreference.getCountItemId(), obj2.text, 300, 300, context)
                } else {
                    EditTextClass(
                        AppPreference.getCountItemId(),
                        obj2.text,
                        measuredWidth,
                        measuredHeight,
                        context
                    )
                }
                AppPreference.increaseCountItemId()
                editTextClass.position = obj2.position
                editTextClass.rotate = (obj2.angle)
                editTextClass.opacity = (obj2.opacity)
                editTextClass.color = (obj2.color)
                editTextClass.isBold = (obj2.isBold)
                editTextClass.isItalic = (obj2.isItalic)
                editTextClass.colorStroke = obj2.strokeColor
                editTextClass.strokeWidth = obj2.strokeWith
                editTextClass.letterSpacing = obj2.letterSpacing
                editTextClass.shadownXvalue = obj2.shadownXvalue
                editTextClass.shadownYvalue = obj2.shadownYvalue
                editTextClass.shadowColor = (obj2.shadowColor)
                editTextClass.shadowRadius = (obj2.shadowRadius)
                editTextClass.x3d = (obj2.x3dvalue)
                editTextClass.y3d = (obj2.y3dvalue)
                editTextClass.curvature = (obj2.curvature)
                editTextClass.setItemVisibility(obj2.isVisible)
                editTextClass.setLockState(obj2.isLocked)
                if (obj2.size >= UtilsClass.dpToPixel(25.0f, context)) {
                    editTextClass.setSizeNew(obj2.size)
                } else {
                    editTextClass.setSizeNew(UtilsClass.dpToPixel(25.0f, context))
                }
                editTextClass.setSizeNew(obj2.size)
                editTextClass.setxPos(obj2.xPos)
                editTextClass.setyPos(obj2.yPos)
                if (obj2.logoFontClass == null) {
                    try {
                        editTextClass.fontModel = (obj2.fontName?.let {
                            FontModel(
                                fontName = it,
                                fontPath = "roboto_regular.ttf"
                            )
                        })
                    } catch (e: Exception) {
                        Log.e(Const.TAG, "setStickersAndTextClassArray: ", e)
                    }
                } else {
//                    editTextClass.fontModel = (obj2.logoFontClass)
                }
                stickers.add(editTextClass)
            }
            i++
        }

        Log.d("ntt", "setStickersAndTextClassArray: stickers: ${stickers.size}")
        Collections.sort(stickers)
    }

    private fun getFont(fontName: String): String {
        return when (fontName) {
            "Default" -> "inter.ttf"
            "Inter" -> "roboto_regular.ttf"
            "Comfortaa" -> "comfortaa.ttf"
            "Strait" -> "strait_regular.ttf"
            "Stick No Bills" -> "stick_no_bills.ttf"
            "STIX Two Text" -> "stix_two_text.ttf"
            "Stylish" -> "stylish_regular.ttf"
            "Supermercado" -> "supermercado_one_regular.ttf"
            "Walter Turncoat" -> "walter_turncoat_regular.ttf"
            else -> "yusei_magic_regular.ttf"
        }
    }

    fun setOnlyTextArray(
        stickersDataClassArr: Array<StickersDataClass?>?,
        textDataClassArr: Array<TextDataClass?>?,
        strArr: Array<String?>,
    ) {
        var strArr2: Array<String?>
        val stickersDataClassArr2: Array<StickersDataClass?>? = stickersDataClassArr
        val textDataClassArr2: Array<TextDataClass?>? = textDataClassArr
        val strArr3: Array<String?> = strArr
        if (textDataClassArr2!!.size != strArr3.size) {
            var stringBuilder: StringBuilder
            when (strArr3.size) {
                2 -> {
                    if (textDataClassArr2.size == 1) {
                        strArr2 = arrayOfNulls(1)
                        stringBuilder = StringBuilder()
                        stringBuilder.append(strArr3.get(0))
                        stringBuilder.append(" ")
                        stringBuilder.append(strArr3.get(1))
                        strArr2[0] = stringBuilder.toString()
                        return
                    }
                    when (textDataClassArr2.size) {
                        1 -> {
                            strArr2 = arrayOfNulls(1)
                            stringBuilder = StringBuilder()
                            stringBuilder.append(strArr3.get(0))
                            stringBuilder.append(" ")
                            stringBuilder.append(strArr3.get(1))
                            stringBuilder.append(" ")
                            stringBuilder.append(strArr3.get(2))
                            strArr2[0] = stringBuilder.toString()
                        }

                        2 -> {
                            val stringBuilder2: StringBuilder = StringBuilder()
                            stringBuilder2.append(strArr3.get(0))
                            stringBuilder2.append(strArr3.get(1))
                            val stringBuilder3: String = stringBuilder2.toString()
                            stringBuilder = StringBuilder()
                            stringBuilder.append(strArr3.get(1))
                            stringBuilder.append(strArr3.get(2))
                            if (getBoundOfText(
                                    textDataClassArr2.get(0)!!.text,
                                    stringBuilder3,
                                    textDataClassArr2.get(0)!!.size,
                                    textDataClassArr2.get(0)!!.fontName
                                ) - textDataClassArr2.get(0)!!.size <= getBoundOfText(
                                    textDataClassArr2.get(1)!!.text,
                                    stringBuilder.toString(),
                                    textDataClassArr2.get(1)!!.size,
                                    textDataClassArr2.get(1)!!.fontName
                                ) - textDataClassArr2.get(1)!!.size
                            ) {
                                strArr2 = arrayOfNulls(2)
                                stringBuilder = StringBuilder()
                                stringBuilder.append(strArr3[0])
                                stringBuilder.append(" ")
                                stringBuilder.append(strArr3.get(1))
                                strArr2[0] = stringBuilder.toString()
                                strArr2[1] = strArr3.get(2)
                                return
                            }
                            strArr2 = arrayOfNulls(2)
                            strArr2[0] = strArr3[0]
                            stringBuilder = StringBuilder()
                            stringBuilder.append(strArr3[1])
                            stringBuilder.append(" ")
                            stringBuilder.append(strArr3[2])
                            strArr2[1] = stringBuilder.toString()
                        }
                    }
                }

                3 -> when (textDataClassArr2.size) {
                    1 -> {
                        strArr2 = arrayOfNulls(1)
                        stringBuilder = StringBuilder()
                        stringBuilder.append(strArr3.get(0))
                        stringBuilder.append(" ")
                        stringBuilder.append(strArr3.get(1))
                        stringBuilder.append(" ")
                        stringBuilder.append(strArr3.get(2))
                        strArr2[0] = stringBuilder.toString()
                    }

                    2 -> {
                        val stringBuilder2: StringBuilder = StringBuilder()
                        stringBuilder2.append(strArr3.get(0))
                        stringBuilder2.append(strArr3.get(1))
                        val stringBuilder3: String = stringBuilder2.toString()
                        stringBuilder = StringBuilder()
                        stringBuilder.append(strArr3.get(1))
                        stringBuilder.append(strArr3.get(2))
                        if (getBoundOfText(
                                textDataClassArr2.get(0)!!.text,
                                stringBuilder3,
                                textDataClassArr2.get(0)!!.size,
                                textDataClassArr2.get(0)!!.fontName
                            ) - textDataClassArr2.get(0)!!.size <= getBoundOfText(
                                textDataClassArr2.get(1)!!.text,
                                stringBuilder.toString(),
                                textDataClassArr2.get(1)!!.size,
                                textDataClassArr2.get(1)!!.fontName
                            ) - textDataClassArr2.get(1)!!.size
                        ) {
                            strArr2 = arrayOfNulls(2)
                            stringBuilder = StringBuilder()
                            stringBuilder.append(strArr3.get(0))
                            stringBuilder.append(" ")
                            stringBuilder.append(strArr3.get(1))
                            strArr2[0] = stringBuilder.toString()
                            strArr2[1] = strArr3.get(2)
                            return
                        }
                        strArr2 = arrayOfNulls(2)
                        strArr2[0] = strArr3.get(0)
                        stringBuilder = StringBuilder()
                        stringBuilder.append(strArr3.get(1))
                        stringBuilder.append(" ")
                        stringBuilder.append(strArr3.get(2))
                        strArr2[1] = stringBuilder.toString()
                    }
                }
            }
        }
        strArr2 = strArr3
        textDataClassesVector.clear()
        stickerClassesVector.clear()
        stickers!!.clear()
        var i2: Int = 0
        var i: Int = 0
        while (i < textDataClassArr2.size) {
            textDataClassesVector.add(textDataClassArr2.get(i))
            i2++
            var stringBuilder4: StringBuilder
            when (i2) {
                1 -> {
                    textDataClassArr2.get(i)!!.size = getBoundOfText(
                        textDataClassArr2.get(i)!!.text,
                        strArr2.get(0),
                        textDataClassArr2.get(i)!!.size,
                        textDataClassArr2.get(i)!!.fontName
                    )
                    stringBuilder4 = StringBuilder()
                    stringBuilder4.append("setOnlyTextArray: ")
                    stringBuilder4.append(textDataClassArr2.get(i)!!.size)
                    Log.e("GOT_SIZE", stringBuilder4.toString())
                    textDataClassArr2.get(i)!!.text = strArr2.get(0)
                }

                2 -> {
                    if (strArr2.size < 2) {
                        textDataClassArr2.get(i)!!.text = ""
                        break
                    }
                    textDataClassArr2.get(i)!!.size = getBoundOfText(
                        textDataClassArr2.get(i)!!.text,
                        strArr2.get(1),
                        textDataClassArr2.get(i)!!.size,
                        textDataClassArr2.get(i)!!.fontName
                    )
                    stringBuilder4 = StringBuilder()
                    stringBuilder4.append("setOnlyTextArray: ")
                    stringBuilder4.append(textDataClassArr2.get(i)!!.size)
                    Log.e("GOT_SIZE", stringBuilder4.toString())
                    textDataClassArr2.get(i)!!.text = strArr2.get(1)
                }

                3 -> {
                    if (strArr2.size != 3) {
                        textDataClassArr2.get(i)!!.text = ""
                        break
                    }
                    textDataClassArr2.get(i)!!.size = getBoundOfText(
                        textDataClassArr2.get(i)!!.text,
                        strArr2.get(2),
                        textDataClassArr2.get(i)!!.size,
                        textDataClassArr2.get(i)!!.fontName
                    )
                    stringBuilder4 = StringBuilder()
                    stringBuilder4.append("setOnlyTextArray: ")
                    stringBuilder4.append(textDataClassArr2.get(i)!!.size)
                    Log.e("GOT_SIZE", stringBuilder4.toString())
                    textDataClassArr2[i]!!.text = strArr2[2]
                }
            }
            var editTextClass: EditTextClass = if (measuredWidth <= 0 || measuredHeight <= 0) {
                EditTextClass(
                    AppPreference.getCountItemId(),
                    textDataClassArr2[i]!!.text,
                    300,
                    300,
                    context
                )
            } else {
                EditTextClass(
                    AppPreference.getCountItemId(),
                    textDataClassArr2[i]!!.text,
                    measuredWidth,
                    measuredHeight,
                    context
                )
            }
            AppPreference.increaseCountItemId()
            editTextClass.rotate = (textDataClassArr2[i]!!.angle)
            editTextClass.opacity = (textDataClassArr2[i]!!.opacity)
            editTextClass.color = (textDataClassArr2[i]!!.color)
            editTextClass.isBold = (textDataClassArr2[i]!!.isBold)
            editTextClass.isItalic = (textDataClassArr2[i]!!.isItalic)
            editTextClass.colorStroke = textDataClassArr2[i]!!.strokeColor
            editTextClass.strokeWidth = textDataClassArr2[i]!!.strokeWith
            editTextClass.letterSpacing = textDataClassArr2[i]!!.letterSpacing
            editTextClass.shadownXvalue = textDataClassArr2[i]!!.shadownXvalue
            editTextClass.shadownYvalue = textDataClassArr2[i]!!.shadownYvalue
            editTextClass.shadowColor = (textDataClassArr2[i]!!.shadowColor)
            editTextClass.shadowRadius = (textDataClassArr2[i]!!.shadowRadius)
            editTextClass.x3d = (textDataClassArr2[i]!!.x3dvalue)
            editTextClass.y3d = (textDataClassArr2[i]!!.y3dvalue)
            editTextClass.curvature = (textDataClassArr2[i]!!.curvature)
            if (textDataClassArr2[i]!!.size >= UtilsClass.dpToPixel(25.0f, context)) {
                size = size
                editTextClass.setSizeNew(textDataClassArr2[i]!!.size)
            } else {
                editTextClass.setSizeNew(UtilsClass.dpToPixel(25.0f, context))
            }
            editTextClass.setxPos(textDataClassArr2[i]!!.xPos)
            editTextClass.setyPos(textDataClassArr2[i]!!.yPos)
            if (textDataClassArr2[i]!!.logoFontClass == null) {
//                editTextClass.fontModel = (FontModel(textDataClassArr2[i]!!.fontName, true,-1))

                editTextClass.fontModel = (textDataClassArr2[i]?.fontName?.let {
                    FontModel(
                        fontName = it,
                        fontPath = "roboto_regular.ttf"
                    )
                })


            } else {
                editTextClass.fontModel = (textDataClassArr2[i]!!.logoFontClass)
            }
            if (editTextClass.text != "") {
                stickers.add(editTextClass)
            }
            i++
        }
        for (obj: StickersDataClass? in stickersDataClassArr2!!) {
            if (obj != null) {
                stickerClassesVector.add(obj)
                val options: BitmapFactory.Options? = null
                var editCharacterClass: EditCharacterClass? = null
                if (obj.isFromGallery) {
                    val decodeFile: Bitmap? = BitmapFactory.decodeFile(obj.imagePath)
                    if (decodeFile != null) {
                        editCharacterClass =
                            EditCharacterClass(
                                AppPreference.getCountItemId(),
                                context, obj.xPos, obj.yPos, decodeFile, true
                            )
                        editCharacterClass.isFrame = (false)
                        editCharacterClass.bitmapPath = (obj.imagePath)
                        editCharacterClass.isImageFromGallery = (true)
                        AppPreference.increaseCountItemId()
                    } else {
                        Toast.makeText(
                            context,
                            "unable_to_load_image",
                            0
                        ).show()
                    }
                } else {
                    if (obj.stickerId == 0) {
                        try {
                            var assets: AssetManager =
                                if ((obj.stickerModule == Const.LOGO_WITH_APP)) {
                                    context.assets
                                } else {
                                    context.createPackageContext(
                                        context.packageName,
                                        0
                                    ).assets
                                }
                            editCharacterClass = EditCharacterClass(
                                AppPreference.getCountItemId(),
                                context,
                                obj.xPos,
                                obj.yPos,
                                UtilsClass.getBitmapFromAsset(assets, obj.stickerName, null),
                                true,
                                obj.stickerModule,
                                obj.stickerName
                            )
                            AppPreference.increaseCountItemId()
                        } catch (unused: Exception) {
                            val activity: Activity = getContext() as Activity
                            val stringBuilder5: StringBuilder = StringBuilder()
                            stringBuilder5.append("Exception when loading logo image for category : ")
                            stringBuilder5.append(obj.stickerModule)
                            val create: AlertDialog = AlertDialog.Builder(activity).create()
                            create.setMessage(stringBuilder5.toString())
                            create.setButton(
                                -1, "Ok"
                            ) { dialogInterface, i -> dialogInterface.dismiss() }
                            create.show()
                        }
                    } else {
                        val stickerResIdForId: Int = MagicData.getStickerResIdForId(obj.stickerId)
                        editCharacterClass = EditCharacterClass(
                            AppPreference.getCountItemId(),
                            context,
                            obj.xPos,
                            obj.yPos,
                            UtilsClass.getImageSmallThanRequired(
                                resources,
                                stickerResIdForId,
                                measuredWidth,
                                measuredHeight
                            ),
                            true,
                            stickerResIdForId
                        )
                        AppPreference.increaseCountItemId()
                    }
                    if (editCharacterClass == null) {
                        editCharacterClass?.idItem = AppPreference.getCountItemId()
                        editCharacterClass?.setWidth(obj.width)
                        editCharacterClass?.setHeight(obj.height)
                        editCharacterClass?.rotate = (obj.angle)
                        editCharacterClass?.opacity = (obj.opacity)
                        editCharacterClass?.colorize = (obj.colorize)
                        editCharacterClass?.shadowColor = (obj.shadowColor)
                        editCharacterClass?.shadowRadius = (obj.shadowRadius)
                        editCharacterClass?.shadownXvalue = (obj.shadownXvalue)
                        editCharacterClass?.shadownYvalue = (obj.shadownYvalue)
                        editCharacterClass?.x3dValue = (obj.x3dvalue)
                        editCharacterClass?.y3dValue = (obj.y3dvalue)
                        editCharacterClass?.flipImage = (obj.isFlipped)
                        editCharacterClass?.flipImageVertical = (obj.isFlippedVertical)
                        stickers.add(editCharacterClass)
                        AppPreference.increaseCountItemId()
                    }
                }
            }
        }
    }

    private fun getBoundOfText(str: String?, str2: String?, f: Float, str3: String?): Float {
        if (str3 != null) {
            tempPaint.textSize = f
            tempPaint.typeface = Typeface.createFromAsset(context.assets, str3)
        } else {
            tempPaint.typeface = null
        }
        val rect: Rect = Rect()
        tempPaint.getTextBounds(str, 0, str!!.length, rect)
        val rect2: Rect = Rect()
        var f2: Float = 1.0f
        val dpToPixel: Float = UtilsClass.dpToPixel(1.0f, this.getContext())
        if (dpToPixel >= 1.0f) {
            f2 = dpToPixel
        }
        do {
            tempPaint.getTextBounds(str2, 0, str2!!.length, rect2)
            tempPaint.textSize = tempPaint.getTextSize() - f2
            if (tempPaint.textSize < UtilsClass.dpToPixel(25.0f, context)) {
                break
            }
        } while (rect2.width() > rect.width())
        val stringBuilder: StringBuilder = StringBuilder()
        stringBuilder.append("Text : ")
        stringBuilder.append(str2)
        Log.d("NAME_ART", stringBuilder.toString())
        val stringBuilder2: StringBuilder = StringBuilder()
        stringBuilder2.append("BOUNDS: OLD : ")
        stringBuilder2.append(rect.width())
        stringBuilder2.append(" ")
        stringBuilder2.append(rect.height())
        stringBuilder2.append("    NEW : ")
        stringBuilder2.append(rect2.width())
        stringBuilder2.append("  ")
        stringBuilder2.append(rect2.height())
        Log.d("NAME_ART", stringBuilder2.toString())
        return tempPaint.getTextSize() + f2
    }
}