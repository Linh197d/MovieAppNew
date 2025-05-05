package com.qibla.muslimday.app2025.util.multitouch

import android.content.Context
import android.view.MotionEvent

abstract class BaseGestDetect(protected val mContext: Context) {
    protected var mCurrEvent: MotionEvent? = null
    protected var mCurrPressure = 0f
    var mGestureInProgress = false
        protected set
    protected var mPrevEvent: MotionEvent? = null
    protected var mPrevPressure = 0f
    var mTimeDelta: Long = 0
        protected set

    abstract fun handleInProgressEvent(i: Int, motionEvent: MotionEvent?)
    abstract fun handleStartProgressEvent(i: Int, motionEvent: MotionEvent)
    fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        val action = motionEvent.action and 255
        if (!mGestureInProgress) {
            handleStartProgressEvent(action, motionEvent)
            return true
        }
        handleInProgressEvent(action, motionEvent)
        return true
    }

    open fun updateStateByEvent(motionEvent: MotionEvent) {
        val motionEvent2 = mPrevEvent
        if (mCurrEvent != null) {
            mCurrEvent!!.recycle()
            mCurrEvent = null
        }
        mCurrEvent = MotionEvent.obtain(motionEvent)
        mTimeDelta = motionEvent.eventTime - motionEvent2!!.eventTime
        mCurrPressure = motionEvent.getPressure(motionEvent.actionIndex)
        mPrevPressure = motionEvent2.getPressure(motionEvent2.actionIndex)
    }

    open fun resetState() {
        if (mPrevEvent != null) {
            mPrevEvent!!.recycle()
            mPrevEvent = null
        }
        if (mCurrEvent != null) {
            mCurrEvent!!.recycle()
            mCurrEvent = null
        }
        mGestureInProgress = false
    }

    val eventTime: Long
        get() = mCurrEvent!!.eventTime

    companion object {
        protected const val PRESSURE_THRESHOLD = 0.67f
    }
}