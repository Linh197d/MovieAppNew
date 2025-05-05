//package com.qibla.muslimday.app2025.view;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.util.AttributeSet;
//import android.util.DisplayMetrics;
//import android.view.View;
//
//import com.qibla.muslimday.app2025.ui.qibla.QiblaActivity;
//
//
//public class CompassViewLocationInfo extends View {
//    private CompassDraw compassDraw;
//    private CompassDrawAtMap compassDrawAtMap;
//    private Handler handler;
//    private final Runnable runnable = new Runnable() {
//
//        public void run() {
//            ((QiblaActivity) CompassViewLocationInfo.this.getContext()).runOnUiThread(new Runnable() {
//
//                public final void run() {
//                    customOne();
//                }
//            });
//        }
//
//        public void customOne() {
//            CompassViewLocationInfo.this.invalidate();
//            CompassViewLocationInfo.this.handler.removeCallbacks(this);
//            CompassViewLocationInfo.this.handler.postDelayed(this, 16);
//        }
//    };
//    private boolean mIsPortrait;
//
//    public CompassViewLocationInfo(Context context) {
//        super(context);
//        init();
//    }
//
//    public CompassViewLocationInfo(Context context, AttributeSet attributeSet) {
//        super(context, attributeSet);
//        init();
//    }
//
//    public CompassViewLocationInfo(Context context, AttributeSet attributeSet, int i) {
//        super(context, attributeSet, i);
//        init();
//    }
//
//    private void init() {
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        this.mIsPortrait = ((float) displayMetrics.heightPixels) / ((float) displayMetrics.widthPixels) > 1.4f;
//        this.compassDraw = new CompassDraw(getContext());
//        this.compassDrawAtMap = new CompassDrawAtMap(getContext());
//        HandlerThread handlerThread = new HandlerThread("rotate");
//        handlerThread.start();
//        this.handler = new Handler(handlerThread.getLooper());
//    }
//
//
//    public void onMeasure(int i, int i2) {
//        super.onMeasure(i, i2);
//        int mode = MeasureSpec.getMode(i);
//        int mode2 = MeasureSpec.getMode(i2);
//        int size = MeasureSpec.getSize(i);
//        int size2 = MeasureSpec.getSize(i2);
//        float f = (float) size;
//        int i3 = (int) ((this.mIsPortrait ? 1.0f : 0.8f) * f);
//        int i4 = (int) (f * 0.86f);
//        if (mode != 1073741824) {
//            size = mode == Integer.MIN_VALUE ? Math.min(i3, size) : i3;
//        }
//        if (mode2 != 1073741824) {
//            size2 = mode2 == Integer.MIN_VALUE ? Math.min(i4, size2) : i4;
//        }
//        setMeasuredDimension(size, size2);
//    }
//
//
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        this.handler.post(this.runnable);
//    }
//
//
//    public void onDetachedFromWindow() {
//        this.handler.removeCallbacks(this.runnable);
//        super.onDetachedFromWindow();
//    }
//
//
//    public void onSizeChanged(int i, int i2, int i3, int i4) {
//        super.onSizeChanged(i, i2, i3, i4);
//        this.compassDraw.onSizeChanged(i, i2, i3, i4);
//        this.compassDrawAtMap.onSizeChanged(i, i2, i3, i4);
//    }
//
//
//    public void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        this.compassDraw.draw(canvas);
//        this.compassDrawAtMap.draw(canvas);
//    }
//
//    public SensorValue getSensorValue() {
//        return this.compassDraw.getSensorValue();
//    }
//
//    public SensorValue getSensorValueAtMap() {
//        return this.compassDrawAtMap.getSensorValue();
//    }
//
//    public String[] getSW_Value() {
//        return new String[]{this.compassDraw.getNumberSW(), this.compassDraw.getTextSW()};
//    }
//
//    public String[] getSW_Value_at_map() {
//        return new String[]{this.compassDrawAtMap.getNumberSW(), this.compassDrawAtMap.getTextSW()};
//    }
//
//    public String getMagneticValue() {
//        return this.compassDraw.getMagnetic();
//    }
//}
