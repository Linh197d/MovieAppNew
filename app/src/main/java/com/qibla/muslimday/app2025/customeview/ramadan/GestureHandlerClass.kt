package com.qibla.muslimday.app2025.customeview.ramadan

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import com.qibla.muslimday.app2025.util.multitouch.RotateGestDetect
import java.io.Serializable

abstract class GestureHandlerClass constructor(context: Context) : Serializable, Comparable<Any?> {
    var handlingTwoFingerTouch: Boolean = false
    open var theHeight: Float = 0f
        protected set
    var isAnimate: Boolean = false
    var isItemLocked: Boolean = false
        protected set
    var isItemVisible: Boolean = true
        protected set
    protected var isTodrawBoundary: Boolean = false
    open var minHeight: Float = 0f
    open var minWidth: Float = 0f
    var pivotSet: Boolean = false
    var position: Int = 0
    protected var prevScaleH: Float = 1.0f
    var prevScaleW: Float = 1.0f
    var rotate: Float = 0f
    protected var rotateGestureDetector: RotateGestDetect
    protected var sPivotX: Float = 0.0f
    protected var sPivotY: Float = 0.0f
    protected var scaleGestureDetector: ScaleGestureDetector
    protected var scaleH: Float = 1.0f
    protected var scaleW: Float = 1.0f
    var rolateX: Float = 0.0f
    var rolateY: Float = 0.0f
    open var theWidth: Float = 0f
        protected set
    var x1: Float = 0f
        protected set
    var x2: Float = 0f
        protected set
    var x3: Float = 0f
        protected set
    var x4: Float = 0f
        protected set
    protected var xPos: Float = 0f
    var y1: Float = 0f
        protected set
    var y2: Float = 0f
        protected set
    var y3: Float = 0f
        protected set
    var y4: Float = 0f
        protected set
    protected var yPos: Float = 0f
    open fun resetPivotPoints() {}
    abstract fun setScaleF(f: Float)
    open fun setScalePivotPoints(f: Float, f2: Float) {}

    init {
        rotateGestureDetector = RotateGestDetect(context, object :
            RotateGestDetect.OnRotateGestureListener {
            var start: Float = 0.0f
            public override fun onRotateEnd(rotateGestureDetector: RotateGestDetect?) {
                start = 0.0f
                if (pivotSet) {
                    pivotSet = false
                    val unused: Boolean = pivotSet
                }
                Log.d("DEBUG", "onRotateEnd")
            }

            public override fun onRotateBegin(rotateGestureDetector: RotateGestDetect): Boolean {
                start = rotateGestureDetector.rotationDegreesDelta
                if (!pivotSet) {
                    setScalePivotPoints(
                        rotateGestureDetector.pivotX,
                        rotateGestureDetector.pivotY
                    )
                    pivotSet = true
                    val unused: Boolean = pivotSet
                }
                Log.d("DEBUG", "onRotateBegin ")
                return handlingTwoFingerTouch
            }

            public override fun onRotate(rotateGestureDetector: RotateGestDetect): Boolean {
                addRotate(-(rotateGestureDetector.rotationDegreesDelta - start))
                start = rotateGestureDetector.rotationDegreesDelta
                return false
            }
        })
        scaleGestureDetector = ScaleGestureDetector(context, object : OnScaleGestureListener {
            public override fun onScaleEnd(scaleGestureDetector: ScaleGestureDetector) {
                prevScaleW *= scaleGestureDetector.scaleFactor
                prevScaleH *= scaleGestureDetector.scaleFactor
                if (pivotSet) {
                    pivotSet = false
                    val unused: Boolean = pivotSet
                }
            }

            public override fun onScaleBegin(scaleGestureDetector: ScaleGestureDetector): Boolean {
                if (!pivotSet) {
                    pivotSet = true
                    val unused: Boolean = pivotSet
                    setScalePivotPoints(
                        scaleGestureDetector.focusX,
                        scaleGestureDetector.focusY
                    )
                }
                Log.d(
                    "DEBUG",
                    "onScaleBegin Scale Pivot Points : " + scaleGestureDetector.getFocusX() + "  " + scaleGestureDetector.getFocusY()
                )
                return handlingTwoFingerTouch
            }

            public override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
                setScaleF(scaleGestureDetector.scaleFactor)
                return false
            }
        })
    }

    open fun setWidth(f: Float) {
        if (f >= minWidth) {
            theWidth = f
        }
    }

    open fun setHeight(f: Float) {
        if (f >= minHeight) {
            theHeight = f
        }
    }

    fun getxPos(): Float {
        return xPos
    }

    fun getyPos(): Float {
        return yPos
    }

    fun setxPos(f: Float) {
        if (!handlingTwoFingerTouch) {
            xPos = f
        }
    }

    fun setyPos(f: Float) {
        if (!handlingTwoFingerTouch) {
            yPos = f
        }
    }

    fun addRotate(f: Float) {
        rotate += f
    }

    fun areaOfTrianges(f: Float, f2: Float): Double {
        val abs: Double = Math.abs(((y1 - y2) * f) + (x1 * (y2 - f2)) + (x2 * (f2 - y1))).toDouble()
        java.lang.Double.isNaN(abs)
        val abs2: Double =
            Math.abs(((y2 - y3) * f) + (x2 * (y3 - f2)) + (x3 * (f2 - y2))).toDouble()
        java.lang.Double.isNaN(abs2)
        val abs3: Double =
            Math.abs(((y3 - y4) * f) + (x3 * (y4 - f2)) + (x4 * (f2 - y3))).toDouble()
        java.lang.Double.isNaN(abs3)
        val abs4: Double =
            Math.abs((f * (y4 - y1)) + (x4 * (y1 - f2)) + (x1 * (f2 - y4))).toDouble()
        java.lang.Double.isNaN(abs4)
        return (abs * 0.5) + (abs2 * 0.5) + (abs3 * 0.5) + (abs4 * 0.5)
    }

    fun onTouch(motionEvent: MotionEvent) {
        if (motionEvent.getMetaState() == 5363534) {
            isAnimate = false
            Log.d("DEBUG", "Handling My custom Action")
            return
        }
        if ((motionEvent.getAction() and 5) == 5) {
            handlingTwoFingerTouch = true
            Log.d("DEBUG", "onTouch Gesture Handler Handling Two Finger Touch now..")
        }
        rotateGestureDetector.onTouchEvent(motionEvent)
        scaleGestureDetector.onTouchEvent(motionEvent)
        if (motionEvent.getAction() == 1) {
            resetPivotPoints()
            handlingTwoFingerTouch = false
            Log.d("DEBUG", "onTouch Gesture Handler Done with two scaling and rotating..")
        }
    }

    fun setItemVisibility(z: Boolean) {
        isItemVisible = z
    }

    fun setLockState(z: Boolean) {
        isItemLocked = z
    }

    public override fun compareTo(obj: Any?): Int {
        val gestureHandlerClass: GestureHandlerClass? = obj as GestureHandlerClass?
        if (gestureHandlerClass!!.position == position) {
            return 0
        }
        return if (gestureHandlerClass.position > position) -1 else 1
    }

    companion object {
        private val serialVersionUID: Long = 17545554
    }
}