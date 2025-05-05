package com.qibla.muslimday.app2025.util.multitouch

import android.content.Context
import android.util.Log
import android.view.MotionEvent

class RotateGestDetect(context: Context, private val mListener: OnRotateGestureListener) :
    TwoFingerGestDetect(context) {
    private var mSloppyGesture = false
    var pivotX = 0f
        private set
    var pivotY = 0f
        private set

    interface OnRotateGestureListener {
        fun onRotate(rotateGestureDetector: RotateGestDetect): Boolean
        fun onRotateBegin(rotateGestureDetector: RotateGestDetect): Boolean
        fun onRotateEnd(rotateGestureDetector: RotateGestDetect?)
    }

    class SimpleOnRotateGestureListener : OnRotateGestureListener {
        override fun onRotate(rotateGestureDetector: RotateGestDetect): Boolean {
            return false
        }

        override fun onRotateBegin(rotateGestureDetector: RotateGestDetect): Boolean {
            return true
        }

        override fun onRotateEnd(rotateGestureDetector: RotateGestDetect?) {}
    }

    override fun handleStartProgressEvent(i: Int, motionEvent: MotionEvent) {
        if (i != 2) {
            when (i) {
                5 -> {
                    resetState()
                    mPrevEvent = MotionEvent.obtain(motionEvent)
                    mTimeDelta = 0
                    pivotX = (motionEvent.getX(1) + motionEvent.getX(0)) / 2.0f
                    pivotY = (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f
                    Log.d(
                        "DEBUG",
                        "handleStartProgressEvent Pivot Calculated : " + pivotX + "   " + pivotY
                    )
                    updateStateByEvent(motionEvent)
                    mSloppyGesture = isSloppyGesture(motionEvent)
                    if (!mSloppyGesture) {
                        mGestureInProgress = mListener.onRotateBegin(this)
                        return
                    }
                    return
                }

                6 -> {
                    val z = mSloppyGesture
                    return
                }

                else -> return
            }
        } else if (mSloppyGesture) {
            mSloppyGesture = isSloppyGesture(motionEvent)
            if (!mSloppyGesture) {
                mGestureInProgress = mListener.onRotateBegin(this)
            }
        }
    }

    override fun handleInProgressEvent(i: Int, motionEvent: MotionEvent?) {
        if (i != 6) {
            when (i) {
                2 -> {
                    updateStateByEvent(motionEvent!!)
                    if (mCurrPressure / mPrevPressure > 0.67f && mListener.onRotate(this)) {
                        mPrevEvent!!.recycle()
                        mPrevEvent = MotionEvent.obtain(motionEvent)
                        return
                    }
                    return
                }

                3 -> {
                    if (!mSloppyGesture) {
                        mListener.onRotateEnd(this)
                    }
                    resetState()
                    return
                }

                else -> return
            }
        } else {
            updateStateByEvent(motionEvent!!)
            if (!mSloppyGesture) {
                mListener.onRotateEnd(this)
            }
            resetState()
        }
    }

    override fun resetState() {
        super.resetState()
        mSloppyGesture = false
    }

    val rotationDegreesDelta: Float
        get() = ((Math.atan2(mPrevFingerDiffY.toDouble(), mPrevFingerDiffX.toDouble()) - Math.atan2(
            mCurrFingerDiffY.toDouble(), mCurrFingerDiffX.toDouble()
        )) * 180.0 / 3.141592653589793).toFloat()
}