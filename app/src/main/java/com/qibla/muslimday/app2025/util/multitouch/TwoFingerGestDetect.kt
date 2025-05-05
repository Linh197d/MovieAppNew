package com.qibla.muslimday.app2025.util.multitouch

import android.content.Context
import android.view.MotionEvent
import android.view.ViewConfiguration

abstract class TwoFingerGestDetect(context: Context) : BaseGestDetect(context) {
    private val mBottomSlopEdge = 0f
    protected var mCurrFingerDiffX = 0f
    protected var mCurrFingerDiffY = 0f
    private var mCurrLen = 0f
    private val mEdgeSlop: Float
    protected var mPrevFingerDiffX = 0f
    protected var mPrevFingerDiffY = 0f
    private var mPrevLen = 0f
    private val mRightSlopEdge = 0f
    abstract override fun handleInProgressEvent(i: Int, motionEvent: MotionEvent?)
    abstract override fun handleStartProgressEvent(i: Int, motionEvent: MotionEvent)
    fun isSloppyGesture(motionEvent: MotionEvent?): Boolean {
        return false
    }

    init {
        mEdgeSlop = ViewConfiguration.get(context).scaledEdgeSlop.toFloat()
    }

    override fun updateStateByEvent(motionEvent: MotionEvent) {
        super.updateStateByEvent(motionEvent)
        val motionEvent2 = mPrevEvent
        mCurrLen = -1.0f
        mPrevLen = -1.0f
        val x = motionEvent2!!.getX(0)
        val y = motionEvent2.getY(0)
        mPrevFingerDiffX = motionEvent2.getX(1) - x
        mPrevFingerDiffY = motionEvent2.getY(1) - y
        val x2 = motionEvent.getX(0)
        val y2 = motionEvent.getY(0)
        mCurrFingerDiffX = motionEvent.getX(1) - x2
        mCurrFingerDiffY = motionEvent.getY(1) - y2
    }

    val currentSpan: Float
        get() {
            if (mCurrLen == -1.0f) {
                val f = mCurrFingerDiffX
                val f2 = mCurrFingerDiffY
                mCurrLen = Math.sqrt((f * f + f2 * f2).toDouble()).toFloat()
            }
            return mCurrLen
        }
    val previousSpan: Float
        get() {
            if (mPrevLen == -1.0f) {
                val f = mPrevFingerDiffX
                val f2 = mPrevFingerDiffY
                mPrevLen = Math.sqrt((f * f + f2 * f2).toDouble()).toFloat()
            }
            return mPrevLen
        }

    companion object {
        protected fun getRawX(motionEvent: MotionEvent, i: Int): Float {
            val x = motionEvent.x - motionEvent.rawX
            return if (i < motionEvent.pointerCount) {
                motionEvent.getX(i) + x
            } else 0.0f
        }

        protected fun getRawY(motionEvent: MotionEvent, i: Int): Float {
            val y = motionEvent.y - motionEvent.rawY
            return if (i < motionEvent.pointerCount) {
                motionEvent.getY(i) + y
            } else 0.0f
        }
    }
}