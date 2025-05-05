package com.qibla.muslimday.app2025.customeview.seekbar;

import static android.view.View.MeasureSpec.EXACTLY;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.qibla.muslimday.app2025.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class RangeSeekBar extends View {
    public static final int SEEKBAR_MODE_RANGE = 2;
    public static final int SEEKBAR_MODE_SINGLE = 1;
    public static final int TICK_MARK_GRAVITY_CENTER = 1;
    public static final int TICK_MARK_GRAVITY_LEFT = 0;
    public static final int TICK_MARK_GRAVITY_RIGHT = 2;
    public static final int TRICK_MARK_MODE_NUMBER = 0;
    public static final int TRICK_MARK_MODE_OTHER = 1;
    SeekBar currTouchSB;
    boolean isScaleThumb;
    SeekBar leftSB;
    Paint paint;
    Bitmap progressBitmap;
    Bitmap progressDefaultBitmap;
    RectF progressDefaultDstRect;
    RectF progressDstRect;
    Rect progressSrcRect;
    float reservePercent;
    SeekBar rightSB;
    RectF stepDivRect;
    List<Bitmap> stepsBitmaps;
    Rect tickMarkTextRect;
    float touchDownX;
    float touchDownY;
    private OnRangeChangedListener callback;
    private boolean enableThumbOverlap;
    private int gravity;
    private boolean isEnable;
    private float maxProgress;
    private float minInterval;
    private float minProgress;
    private int progressBottom;
    private int progressColor;
    private int progressDefaultColor;
    private int progressDefaultDrawableId;
    private int progressDrawableId;
    private int progressHeight;
    private int progressLeft;
    private int progressPaddingRight;
    private float progressRadius;
    private int progressRight;
    private int progressTop;
    private int progressWidth;
    private int seekBarMode;
    private int steps;
    private boolean stepsAutoBonding;
    private int stepsColor;
    private int stepsDrawableId;
    private float stepsHeight;
    private float stepsRadius;
    private float stepsWidth;
    private int tickMarkGravity;
    private int tickMarkInRangeTextColor;
    private int tickMarkLayoutGravity;
    private int tickMarkMode;
    private CharSequence[] tickMarkTextArray;
    private int tickMarkTextColor;
    private int tickMarkTextMargin;
    private int tickMarkTextSize;

    public RangeSeekBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public RangeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isEnable = true;
        this.isScaleThumb = false;
        this.paint = new Paint();
        this.progressDefaultDstRect = new RectF();
        this.progressDstRect = new RectF();
        this.progressSrcRect = new Rect();
        this.stepDivRect = new RectF();
        this.tickMarkTextRect = new Rect();
        this.stepsBitmaps = new ArrayList();
        initAttrs(attrs);
        initPaint();
        initSeekBar(attrs);
        initStepsBitmap();
    }

    private void initProgressBitmap() {
        if (this.progressBitmap == null) {
            this.progressBitmap = Utils.drawableToBitmap(getContext(), this.progressWidth, this.progressHeight, this.progressDrawableId);
        }
        if (this.progressDefaultBitmap == null) {
            this.progressDefaultBitmap = Utils.drawableToBitmap(getContext(), this.progressWidth, this.progressHeight, this.progressDefaultDrawableId);
        }
    }

    private boolean verifyStepsMode() {
        return this.steps >= 1 && this.stepsHeight > 0.0f && this.stepsWidth > 0.0f;
    }

    private void initStepsBitmap() {
        if (verifyStepsMode() && this.stepsDrawableId != 0 && this.stepsBitmaps.isEmpty()) {
            Bitmap bitmap = Utils.drawableToBitmap(getContext(), (int) this.stepsWidth, (int) this.stepsHeight, this.stepsDrawableId);
            for (int i = 0; i <= this.steps; i++) {
                this.stepsBitmaps.add(bitmap);
            }
        }
    }

    private void initSeekBar(AttributeSet attrs) {
        boolean z = true;
        this.leftSB = new SeekBar(this, attrs, true);
        SeekBar seekBar = new SeekBar(this, attrs, false);
        this.rightSB = seekBar;
        if (this.seekBarMode == 1) {
            z = false;
        }
        seekBar.setVisible(z);
    }

    private void initAttrs(AttributeSet attrs) {
        try {
            TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.RangeSeekBar);
            this.seekBarMode = t.getInt(R.styleable.RangeSeekBar_rsb_mode, 2);
            this.minProgress = t.getFloat(R.styleable.RangeSeekBar_rsb_min, 0.0f);
            this.maxProgress = t.getFloat(R.styleable.RangeSeekBar_rsb_max, 100.0f);
            this.minInterval = t.getFloat(R.styleable.RangeSeekBar_rsb_min_interval, 0.0f);
            this.gravity = t.getInt(0, 0);
            this.progressColor = t.getColor(R.styleable.RangeSeekBar_rsb_progress_color, -11806366);
            this.progressRadius = (float) ((int) t.getDimension(R.styleable.RangeSeekBar_rsb_progress_radius, -1.0f));
            this.progressDefaultColor = t.getColor(R.styleable.RangeSeekBar_rsb_progress_default_color, -2631721);
            this.progressDrawableId = t.getResourceId(R.styleable.RangeSeekBar_rsb_progress_drawable, 0);
            this.progressDefaultDrawableId = t.getResourceId(R.styleable.RangeSeekBar_rsb_progress_drawable_default, 0);
            this.progressHeight = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_progress_height, (float) Utils.dp2px(getContext(), 2.0f));
            this.tickMarkMode = t.getInt(R.styleable.RangeSeekBar_rsb_tick_mark_mode, 0);
            this.tickMarkGravity = t.getInt(R.styleable.RangeSeekBar_rsb_tick_mark_gravity, 1);
            this.tickMarkLayoutGravity = t.getInt(R.styleable.RangeSeekBar_rsb_tick_mark_layout_gravity, 0);
            this.tickMarkTextArray = t.getTextArray(R.styleable.RangeSeekBar_rsb_tick_mark_text_array);
            this.tickMarkTextMargin = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_tick_mark_text_margin, (float) Utils.dp2px(getContext(), 7.0f));
            this.tickMarkTextSize = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_tick_mark_text_size, (float) Utils.dp2px(getContext(), 12.0f));
            this.tickMarkTextColor = t.getColor(R.styleable.RangeSeekBar_rsb_tick_mark_text_color, this.progressDefaultColor);
            this.tickMarkInRangeTextColor = t.getColor(R.styleable.RangeSeekBar_rsb_tick_mark_in_range_text_color, this.progressColor);
            this.steps = t.getInt(R.styleable.RangeSeekBar_rsb_steps, 0);
            this.stepsColor = t.getColor(R.styleable.RangeSeekBar_rsb_step_color, -6447715);
            this.stepsRadius = t.getDimension(R.styleable.RangeSeekBar_rsb_step_radius, 0.0f);
            this.stepsWidth = t.getDimension(R.styleable.RangeSeekBar_rsb_step_width, 0.0f);
            this.stepsHeight = t.getDimension(R.styleable.RangeSeekBar_rsb_step_height, 0.0f);
            this.stepsDrawableId = t.getResourceId(R.styleable.RangeSeekBar_rsb_step_drawable, 0);
            this.stepsAutoBonding = t.getBoolean(R.styleable.RangeSeekBar_rsb_step_auto_bonding, true);
            t.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasureProgress(int w, int h) {
        int viewHeight = (h - getPaddingBottom()) - getPaddingTop();
        if (h > 0) {
            int i = this.gravity;
            if (i == 0) {
                float maxIndicatorHeight = 0.0f;
                if (!(this.leftSB.getIndicatorShowMode() == 1 && this.rightSB.getIndicatorShowMode() == 1)) {
                    maxIndicatorHeight = (float) Math.max(this.leftSB.getIndicatorRawHeight(), this.rightSB.getIndicatorRawHeight());
                }
                float thumbHeight = Math.max(this.leftSB.getThumbScaleHeight(), this.rightSB.getThumbScaleHeight());
                int i2 = this.progressHeight;
                float thumbHeight2 = thumbHeight - (((float) i2) / 2.0f);
                this.progressTop = (int) (((thumbHeight2 - ((float) i2)) / 2.0f) + maxIndicatorHeight);
                if (this.tickMarkTextArray != null && this.tickMarkLayoutGravity == 0) {
                    this.progressTop = (int) Math.max((float) getTickMarkRawHeight(), ((thumbHeight2 - ((float) this.progressHeight)) / 2.0f) + maxIndicatorHeight);
                }
                this.progressBottom = this.progressTop + this.progressHeight;
            } else if (i == 1) {
                if (this.tickMarkTextArray == null || this.tickMarkLayoutGravity != 1) {
                    this.progressBottom = (int) ((((float) viewHeight) - (Math.max(this.leftSB.getThumbScaleHeight(), this.rightSB.getThumbScaleHeight()) / 2.0f)) + (((float) this.progressHeight) / 2.0f));
                } else {
                    this.progressBottom = viewHeight - getTickMarkRawHeight();
                }
                this.progressTop = this.progressBottom - this.progressHeight;
            } else {
                int i3 = this.progressHeight;
                int i4 = (viewHeight - i3) / 2;
                this.progressTop = i4;
                this.progressBottom = i4 + i3;
            }
            int maxThumbWidth = (int) Math.max(this.leftSB.getThumbScaleWidth(), this.rightSB.getThumbScaleWidth());
            this.progressLeft = (maxThumbWidth / 2) + getPaddingLeft();
            int paddingRight = (w - (maxThumbWidth / 2)) - getPaddingRight();
            this.progressRight = paddingRight;
            this.progressWidth = paddingRight - this.progressLeft;
            this.progressDefaultDstRect.set((float) getProgressLeft(), (float) getProgressTop(), (float) getProgressRight(), (float) getProgressBottom());
            this.progressPaddingRight = w - this.progressRight;
            if (this.progressRadius <= 0.0f) {
                this.progressRadius = (float) ((int) (((float) (getProgressBottom() - getProgressTop())) * 0.45f));
            }
            initProgressBitmap();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize;
        int heightNeeded;
        int heightSize2 = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == 1073741824) {
            heightSize = MeasureSpec.makeMeasureSpec(heightSize2, EXACTLY);
        } else if (heightMode == Integer.MIN_VALUE && (getParent() instanceof ViewGroup) && heightSize2 == -1) {
            heightSize = MeasureSpec.makeMeasureSpec(((ViewGroup) getParent()).getMeasuredHeight(), MeasureSpec.AT_MOST);
        } else {
            if (this.gravity != 2) {
                heightNeeded = (int) getRawHeight();
            } else if (this.tickMarkTextArray == null || this.tickMarkLayoutGravity != 1) {
                heightNeeded = (int) ((getRawHeight() - (Math.max(this.leftSB.getThumbScaleHeight(), this.rightSB.getThumbScaleHeight()) / 2.0f)) * 2.0f);
            } else {
                heightNeeded = (int) ((getRawHeight() - ((float) getTickMarkRawHeight())) * 2.0f);
            }
            heightSize = MeasureSpec.makeMeasureSpec(heightNeeded, EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightSize);
    }

    /* access modifiers changed from: protected */
    public int getTickMarkRawHeight() {
        CharSequence[] charSequenceArr = this.tickMarkTextArray;
        if (charSequenceArr == null || charSequenceArr.length <= 0) {
            return 0;
        }
        return this.tickMarkTextMargin + Utils.measureText(String.valueOf(charSequenceArr[0]), (float) this.tickMarkTextSize).height() + 3;
    }

    /* access modifiers changed from: protected */
    public float getRawHeight() {
        if (this.seekBarMode == 1) {
            float rawHeight = this.leftSB.getRawHeight();
            if (this.tickMarkLayoutGravity != 1 || this.tickMarkTextArray == null) {
                return rawHeight;
            }
            return (rawHeight - (this.leftSB.getThumbScaleHeight() / 2.0f)) + (((float) this.progressHeight) / 2.0f) + Math.max((this.leftSB.getThumbScaleHeight() - ((float) this.progressHeight)) / 2.0f, (float) getTickMarkRawHeight());
        }
        float rawHeight2 = Math.max(this.leftSB.getRawHeight(), this.rightSB.getRawHeight());
        if (this.tickMarkLayoutGravity != 1 || this.tickMarkTextArray == null) {
            return rawHeight2;
        }
        float thumbHeight = Math.max(this.leftSB.getThumbScaleHeight(), this.rightSB.getThumbScaleHeight());
        return (rawHeight2 - (thumbHeight / 2.0f)) + (((float) this.progressHeight) / 2.0f) + Math.max((thumbHeight - ((float) this.progressHeight)) / 2.0f, (float) getTickMarkRawHeight());
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        onMeasureProgress(w, h);
        setRange(this.minProgress, this.maxProgress, this.minInterval);
        int lineCenterY = (getProgressBottom() + getProgressTop()) / 2;
        this.leftSB.onSizeChanged(getProgressLeft(), lineCenterY);
        if (this.seekBarMode == 2) {
            this.rightSB.onSizeChanged(getProgressLeft(), lineCenterY);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawTickMark(canvas, this.paint);
        onDrawProgressBar(canvas, this.paint);
        onDrawSteps(canvas, this.paint);
        onDrawSeekBar(canvas);
    }

    /* access modifiers changed from: protected */
    public void onDrawTickMark(Canvas canvas, Paint paint2) {
        float num;
        float y;
        CharSequence[] charSequenceArr = this.tickMarkTextArray;
        if (charSequenceArr != null) {
            int trickPartWidth = this.progressWidth / (charSequenceArr.length - 1);
            int i = 0;
            while (true) {
                CharSequence[] charSequenceArr2 = this.tickMarkTextArray;
                if (i < charSequenceArr2.length) {
                    String text2Draw = charSequenceArr2[i].toString();
                    if (!TextUtils.isEmpty(text2Draw)) {
                        paint2.getTextBounds(text2Draw, 0, text2Draw.length(), this.tickMarkTextRect);
                        paint2.setColor(this.tickMarkTextColor);
                        if (this.tickMarkMode == 1) {
                            int i2 = this.tickMarkGravity;
                            if (i2 == 2) {
                                num = (float) ((getProgressLeft() + (i * trickPartWidth)) - this.tickMarkTextRect.width());
                            } else if (i2 == 1) {
                                num = ((float) (getProgressLeft() + (i * trickPartWidth))) - (((float) this.tickMarkTextRect.width()) / 2.0f);
                            } else {
                                num = (float) (getProgressLeft() + (i * trickPartWidth));
                            }
                        } else {
                            float x = Utils.parseFloat(text2Draw);
                            SeekBarState[] states = getRangeSeekBarState();
                            if (!(Utils.compareFloat(x, states[0].value) == -1 || Utils.compareFloat(x, states[1].value) == 1 || this.seekBarMode != 2)) {
                                paint2.setColor(this.tickMarkInRangeTextColor);
                            }
                            float f = this.minProgress;
                            num = (((float) getProgressLeft()) + ((((float) this.progressWidth) * (x - f)) / (this.maxProgress - f))) - (((float) this.tickMarkTextRect.width()) / 2.0f);
                        }
                        if (this.tickMarkLayoutGravity == 0) {
                            y = (float) (getProgressTop() - this.tickMarkTextMargin);
                        } else {
                            y = (float) (getProgressBottom() + this.tickMarkTextMargin + this.tickMarkTextRect.height());
                        }
                        canvas.drawText(text2Draw, num, y, paint2);
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDrawProgressBar(Canvas canvas, Paint paint2) {
        if (Utils.verifyBitmap(this.progressDefaultBitmap)) {
            canvas.drawBitmap(this.progressDefaultBitmap, (Rect) null, this.progressDefaultDstRect, paint2);
        } else {
            paint2.setColor(this.progressDefaultColor);
            RectF rectF = this.progressDefaultDstRect;
            float f = this.progressRadius;
            canvas.drawRoundRect(rectF, f, f, paint2);
        }
        if (this.seekBarMode == 2) {
            this.progressDstRect.top = (float) getProgressTop();
            this.progressDstRect.left = ((float) this.leftSB.left) + (this.leftSB.getThumbScaleWidth() / 2.0f) + (((float) this.progressWidth) * this.leftSB.currPercent);
            this.progressDstRect.right = ((float) this.rightSB.left) + (this.rightSB.getThumbScaleWidth() / 2.0f) + (((float) this.progressWidth) * this.rightSB.currPercent);
            this.progressDstRect.bottom = (float) getProgressBottom();
        } else {
            this.progressDstRect.top = (float) getProgressTop();
            this.progressDstRect.left = ((float) this.leftSB.left) + (this.leftSB.getThumbScaleWidth() / 2.0f);
            this.progressDstRect.right = ((float) this.leftSB.left) + (this.leftSB.getThumbScaleWidth() / 2.0f) + (((float) this.progressWidth) * this.leftSB.currPercent);
            this.progressDstRect.bottom = (float) getProgressBottom();
        }
        if (Utils.verifyBitmap(this.progressBitmap)) {
            this.progressSrcRect.top = 0;
            this.progressSrcRect.bottom = this.progressBitmap.getHeight();
            int bitmapWidth = this.progressBitmap.getWidth();
            if (this.seekBarMode == 2) {
                this.progressSrcRect.left = (int) (((float) bitmapWidth) * this.leftSB.currPercent);
                this.progressSrcRect.right = (int) (((float) bitmapWidth) * this.rightSB.currPercent);
            } else {
                this.progressSrcRect.left = 0;
                this.progressSrcRect.right = (int) (((float) bitmapWidth) * this.leftSB.currPercent);
            }
            canvas.drawBitmap(this.progressBitmap, this.progressSrcRect, this.progressDstRect, (Paint) null);
            return;
        }
        paint2.setColor(this.progressColor);
        RectF rectF2 = this.progressDstRect;
        float f2 = this.progressRadius;
        canvas.drawRoundRect(rectF2, f2, f2, paint2);
    }

    /* access modifiers changed from: protected */
    public void onDrawSteps(Canvas canvas, Paint paint2) {
        if (verifyStepsMode()) {
            int stepMarks = getProgressWidth() / this.steps;
            float extHeight = (this.stepsHeight - ((float) getProgressHeight())) / 2.0f;
            for (int k = 0; k <= this.steps; k++) {
                float x = ((float) (getProgressLeft() + (k * stepMarks))) - (this.stepsWidth / 2.0f);
                this.stepDivRect.set(x, ((float) getProgressTop()) - extHeight, this.stepsWidth + x, ((float) getProgressBottom()) + extHeight);
                if (this.stepsBitmaps.isEmpty() || this.stepsBitmaps.size() <= k) {
                    paint2.setColor(this.stepsColor);
                    RectF rectF = this.stepDivRect;
                    float f = this.stepsRadius;
                    canvas.drawRoundRect(rectF, f, f, paint2);
                } else {
                    canvas.drawBitmap(this.stepsBitmaps.get(k), (Rect) null, this.stepDivRect, paint2);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDrawSeekBar(Canvas canvas) {
        if (this.leftSB.getIndicatorShowMode() == 3) {
            this.leftSB.setShowIndicatorEnable(true);
        }
        this.leftSB.draw(canvas);
        if (this.seekBarMode == 2) {
            if (this.rightSB.getIndicatorShowMode() == 3) {
                this.rightSB.setShowIndicatorEnable(true);
            }
            this.rightSB.draw(canvas);
        }
    }

    private void initPaint() {
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(this.progressDefaultColor);
        this.paint.setTextSize((float) this.tickMarkTextSize);
    }

    private void changeThumbActivateState(boolean hasActivate) {
        SeekBar seekBar;
        boolean z = false;
        if (!hasActivate || (seekBar = this.currTouchSB) == null) {
            this.leftSB.setActivate(false);
            if (this.seekBarMode == 2) {
                this.rightSB.setActivate(false);
                return;
            }
            return;
        }
        SeekBar seekBar2 = this.leftSB;
        boolean state = seekBar == seekBar2;
        seekBar2.setActivate(state);
        if (this.seekBarMode == 2) {
            SeekBar seekBar3 = this.rightSB;
            if (!state) {
                z = true;
            }
            seekBar3.setActivate(z);
        }
    }

    /* access modifiers changed from: protected */
    public float getEventX(MotionEvent event) {
        return event.getX();
    }

    /* access modifiers changed from: protected */
    public float getEventY(MotionEvent event) {
        return event.getY();
    }

    private void scaleCurrentSeekBarThumb() {
        SeekBar seekBar = this.currTouchSB;
        if (seekBar != null && seekBar.getThumbScaleRatio() > 1.0f && !this.isScaleThumb) {
            this.isScaleThumb = true;
            this.currTouchSB.scaleThumb();
        }
    }

    private void resetCurrentSeekBarThumb() {
        SeekBar seekBar = this.currTouchSB;
        if (seekBar != null && seekBar.getThumbScaleRatio() > 1.0f && this.isScaleThumb) {
            this.isScaleThumb = false;
            this.currTouchSB.resetThumb();
        }
    }

    /* access modifiers changed from: protected */
    public float calculateCurrentSeekBarPercent(float touchDownX2) {
        if (this.currTouchSB == null) {
            return 0.0f;
        }
        float percent = (touchDownX2 - ((float) getProgressLeft())) / ((float) this.progressWidth);
        if (touchDownX2 < ((float) getProgressLeft())) {
            percent = 0.0f;
        } else if (touchDownX2 > ((float) getProgressRight())) {
            percent = 1.0f;
        }
        if (this.seekBarMode != 2) {
            return percent;
        }
        SeekBar seekBar = this.currTouchSB;
        SeekBar seekBar2 = this.leftSB;
        if (seekBar == seekBar2) {
            if (percent > this.rightSB.currPercent - this.reservePercent) {
                return this.rightSB.currPercent - this.reservePercent;
            }
            return percent;
        } else if (seekBar != this.rightSB || percent >= seekBar2.currPercent + this.reservePercent) {
            return percent;
        } else {
            return this.leftSB.currPercent + this.reservePercent;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean z = true;
        if (!this.isEnable) {
            return true;
        }
        float f = 1.0f;
        boolean z2 = false;
        switch (event.getAction()) {
            case 0:
                this.touchDownX = getEventX(event);
                this.touchDownY = getEventY(event);
                if (this.seekBarMode != 2) {
                    this.currTouchSB = this.leftSB;
                    scaleCurrentSeekBarThumb();
                } else if (this.rightSB.currPercent >= 1.0f && this.leftSB.collide(getEventX(event), getEventY(event))) {
                    this.currTouchSB = this.leftSB;
                    scaleCurrentSeekBarThumb();
                } else if (this.rightSB.collide(getEventX(event), getEventY(event))) {
                    this.currTouchSB = this.rightSB;
                    scaleCurrentSeekBarThumb();
                } else {
                    float performClick = (this.touchDownX - ((float) getProgressLeft())) / ((float) this.progressWidth);
                    if (Math.abs(this.leftSB.currPercent - performClick) < Math.abs(this.rightSB.currPercent - performClick)) {
                        this.currTouchSB = this.leftSB;
                    } else {
                        this.currTouchSB = this.rightSB;
                    }
                    this.currTouchSB.slide(calculateCurrentSeekBarPercent(this.touchDownX));
                }
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                OnRangeChangedListener onRangeChangedListener = this.callback;
                if (onRangeChangedListener != null) {
                    if (this.currTouchSB == this.leftSB) {
                        z2 = true;
                    }
                    onRangeChangedListener.onStartTrackingTouch(this, z2);
                }
                changeThumbActivateState(true);
                return true;
            case 1:
                if (verifyStepsMode() && this.stepsAutoBonding) {
                    float percent = calculateCurrentSeekBarPercent(getEventX(event));
                    float stepPercent = 1.0f / ((float) this.steps);
                    this.currTouchSB.slide(((float) new BigDecimal((double) (percent / stepPercent)).setScale(0, RoundingMode.HALF_UP).intValue()) * stepPercent);
                }
                if (this.seekBarMode == 2) {
                    this.rightSB.setShowIndicatorEnable(false);
                }
                this.leftSB.setShowIndicatorEnable(false);
                this.currTouchSB.materialRestore();
                resetCurrentSeekBarThumb();
                if (this.callback != null) {
                    SeekBarState[] states = getRangeSeekBarState();
                    this.callback.onRangeChanged(this, states[0].value, states[1].value, false);
                }
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                OnRangeChangedListener onRangeChangedListener2 = this.callback;
                if (onRangeChangedListener2 != null) {
                    if (this.currTouchSB != this.leftSB) {
                        z = false;
                    }
                    onRangeChangedListener2.onStopTrackingTouch(this, z);
                }
                changeThumbActivateState(false);
                break;
            case 2:
                float x = getEventX(event);
                if (this.seekBarMode == 2 && this.leftSB.currPercent == this.rightSB.currPercent) {
                    this.currTouchSB.materialRestore();
                    OnRangeChangedListener onRangeChangedListener3 = this.callback;
                    if (onRangeChangedListener3 != null) {
                        onRangeChangedListener3.onStopTrackingTouch(this, this.currTouchSB == this.leftSB);
                    }
                    if (x - this.touchDownX > 0.0f) {
                        SeekBar seekBar = this.currTouchSB;
                        if (seekBar != this.rightSB) {
                            seekBar.setShowIndicatorEnable(false);
                            resetCurrentSeekBarThumb();
                            this.currTouchSB = this.rightSB;
                        }
                    } else {
                        SeekBar seekBar2 = this.currTouchSB;
                        if (seekBar2 != this.leftSB) {
                            seekBar2.setShowIndicatorEnable(false);
                            resetCurrentSeekBarThumb();
                            this.currTouchSB = this.leftSB;
                        }
                    }
                    OnRangeChangedListener onRangeChangedListener4 = this.callback;
                    if (onRangeChangedListener4 != null) {
                        onRangeChangedListener4.onStartTrackingTouch(this, this.currTouchSB == this.leftSB);
                    }
                }
                scaleCurrentSeekBarThumb();
                SeekBar seekBar3 = this.currTouchSB;
                if (seekBar3.material < 1.0f) {
                    f = this.currTouchSB.material + 0.1f;
                }
                seekBar3.material = f;
                this.touchDownX = x;
                this.currTouchSB.slide(calculateCurrentSeekBarPercent(x));
                this.currTouchSB.setShowIndicatorEnable(true);
                if (this.callback != null) {
                    SeekBarState[] states2 = getRangeSeekBarState();
                    this.callback.onRangeChanged(this, states2[0].value, states2[1].value, true);
                }
                invalidate();
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                changeThumbActivateState(true);
                break;
            case 3:
                if (this.seekBarMode == 2) {
                    this.rightSB.setShowIndicatorEnable(false);
                }
                SeekBar seekBar4 = this.currTouchSB;
                if (seekBar4 == this.leftSB) {
                    resetCurrentSeekBarThumb();
                } else if (seekBar4 == this.rightSB) {
                    resetCurrentSeekBarThumb();
                }
                this.leftSB.setShowIndicatorEnable(false);
                if (this.callback != null) {
                    SeekBarState[] states3 = getRangeSeekBarState();
                    this.callback.onRangeChanged(this, states3[0].value, states3[1].value, false);
                }
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                changeThumbActivateState(false);
                break;
        }
        return super.onTouchEvent(event);
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.minValue = this.minProgress;
        ss.maxValue = this.maxProgress;
        ss.rangeInterval = this.minInterval;
        SeekBarState[] results = getRangeSeekBarState();
        ss.currSelectedMin = results[0].value;
        ss.currSelectedMax = results[1].value;
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        try {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            setRange(ss.minValue, ss.maxValue, ss.rangeInterval);
            setProgress(ss.currSelectedMin, ss.currSelectedMax);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnRangeChangedListener(OnRangeChangedListener listener) {
        this.callback = listener;
    }

    public void setProgress(float value) {
        setProgress(value, this.maxProgress);
    }

    public void setProgress(float leftValue, float rightValue) {
        float leftValue2 = Math.min(leftValue, rightValue);
        float rightValue2 = Math.max(leftValue2, rightValue);
        float f = this.minInterval;
        if (rightValue2 - leftValue2 < f) {
            if (leftValue2 - this.minProgress > this.maxProgress - rightValue2) {
                leftValue2 = rightValue2 - f;
            } else {
                rightValue2 = leftValue2 + f;
            }
        }
        float f2 = this.minProgress;
        if (leftValue2 >= f2) {
            float f3 = this.maxProgress;
            if (rightValue2 <= f3) {
                float range = f3 - f2;
                this.leftSB.currPercent = Math.abs(leftValue2 - f2) / range;
                if (this.seekBarMode == 2) {
                    this.rightSB.currPercent = Math.abs(rightValue2 - this.minProgress) / range;
                }
                OnRangeChangedListener onRangeChangedListener = this.callback;
                if (onRangeChangedListener != null) {
                    onRangeChangedListener.onRangeChanged(this, leftValue2, rightValue2, false);
                }
                invalidate();
                return;
            }
            throw new IllegalArgumentException("setProgress() max > (preset max - offsetValue) . #max:" + rightValue2 + " #preset max:" + rightValue2);
        }
        throw new IllegalArgumentException("setProgress() min < (preset min - offsetValue) . #min:" + leftValue2 + " #preset min:" + rightValue2);
    }

    public void setRange(float min, float max) {
        setRange(min, max, this.minInterval);
    }

    public void setRange(float min, float max, float minInterval2) {
        if (max <= min) {
            throw new IllegalArgumentException("setRange() max must be greater than min ! #max:" + max + " #min:" + min);
        } else if (minInterval2 < 0.0f) {
            throw new IllegalArgumentException("setRange() interval must be greater than zero ! #minInterval:" + minInterval2);
        } else if (minInterval2 < max - min) {
            this.maxProgress = max;
            this.minProgress = min;
            this.minInterval = minInterval2;
            this.reservePercent = minInterval2 / (max - min);
            if (this.seekBarMode == 2) {
                if (this.leftSB.currPercent + this.reservePercent <= 1.0f && this.leftSB.currPercent + this.reservePercent > this.rightSB.currPercent) {
                    this.rightSB.currPercent = this.leftSB.currPercent + this.reservePercent;
                } else if (this.rightSB.currPercent - this.reservePercent >= 0.0f && this.rightSB.currPercent - this.reservePercent < this.leftSB.currPercent) {
                    this.leftSB.currPercent = this.rightSB.currPercent - this.reservePercent;
                }
            }
            invalidate();
        } else {
            throw new IllegalArgumentException("setRange() interval must be less than (max - min) ! #minInterval:" + minInterval2 + " #max - min:" + (max - min));
        }
    }

    public SeekBarState[] getRangeSeekBarState() {
        SeekBarState leftSeekBarState = new SeekBarState();
        leftSeekBarState.value = this.leftSB.getProgress();
        leftSeekBarState.indicatorText = String.valueOf(leftSeekBarState.value);
        if (Utils.compareFloat(leftSeekBarState.value, this.minProgress) == 0) {
            leftSeekBarState.isMin = true;
        } else if (Utils.compareFloat(leftSeekBarState.value, this.maxProgress) == 0) {
            leftSeekBarState.isMax = true;
        }
        SeekBarState rightSeekBarState = new SeekBarState();
        if (this.seekBarMode == 2) {
            rightSeekBarState.value = this.rightSB.getProgress();
            rightSeekBarState.indicatorText = String.valueOf(rightSeekBarState.value);
            if (Utils.compareFloat(this.rightSB.currPercent, this.minProgress) == 0) {
                rightSeekBarState.isMin = true;
            } else if (Utils.compareFloat(this.rightSB.currPercent, this.maxProgress) == 0) {
                rightSeekBarState.isMax = true;
            }
        }
        return new SeekBarState[]{leftSeekBarState, rightSeekBarState};
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.isEnable = enabled;
    }

    public void setIndicatorText(String progress) {
        this.leftSB.setIndicatorText(progress);
        if (this.seekBarMode == 2) {
            this.rightSB.setIndicatorText(progress);
        }
    }

    public void setIndicatorTextDecimalFormat(String formatPattern) {
        this.leftSB.setIndicatorTextDecimalFormat(formatPattern);
        if (this.seekBarMode == 2) {
            this.rightSB.setIndicatorTextDecimalFormat(formatPattern);
        }
    }

    public void setIndicatorTextStringFormat(String formatPattern) {
        this.leftSB.setIndicatorTextStringFormat(formatPattern);
        if (this.seekBarMode == 2) {
            this.rightSB.setIndicatorTextStringFormat(formatPattern);
        }
    }

    public SeekBar getLeftSeekBar() {
        return this.leftSB;
    }

    public SeekBar getRightSeekBar() {
        return this.rightSB;
    }

    public int getProgressTop() {
        return this.progressTop;
    }

    public void setProgressTop(int progressTop2) {
        this.progressTop = progressTop2;
    }

    public int getProgressBottom() {
        return this.progressBottom;
    }

    public void setProgressBottom(int progressBottom2) {
        this.progressBottom = progressBottom2;
    }

    public int getProgressLeft() {
        return this.progressLeft;
    }

    public void setProgressLeft(int progressLeft2) {
        this.progressLeft = progressLeft2;
    }

    public int getProgressRight() {
        return this.progressRight;
    }

    public void setProgressRight(int progressRight2) {
        this.progressRight = progressRight2;
    }

    public int getProgressPaddingRight() {
        return this.progressPaddingRight;
    }

    public int getProgressHeight() {
        return this.progressHeight;
    }

    public void setProgressHeight(int progressHeight2) {
        this.progressHeight = progressHeight2;
    }

    public float getMinProgress() {
        return this.minProgress;
    }

    public float getMaxProgress() {
        return this.maxProgress;
    }

    public void setProgressColor(int progressDefaultColor2, int progressColor2) {
        this.progressDefaultColor = progressDefaultColor2;
        this.progressColor = progressColor2;
    }

    public int getTickMarkTextColor() {
        return this.tickMarkTextColor;
    }

    public void setTickMarkTextColor(int tickMarkTextColor2) {
        this.tickMarkTextColor = tickMarkTextColor2;
    }

    public int getTickMarkInRangeTextColor() {
        return this.tickMarkInRangeTextColor;
    }

    public void setTickMarkInRangeTextColor(int tickMarkInRangeTextColor2) {
        this.tickMarkInRangeTextColor = tickMarkInRangeTextColor2;
    }

    public int getSeekBarMode() {
        return this.seekBarMode;
    }

    public void setSeekBarMode(int seekBarMode2) {
        this.seekBarMode = seekBarMode2;
        SeekBar seekBar = this.rightSB;
        boolean z = true;
        if (seekBarMode2 == 1) {
            z = false;
        }
        seekBar.setVisible(z);
    }

    public int getTickMarkMode() {
        return this.tickMarkMode;
    }

    public void setTickMarkMode(int tickMarkMode2) {
        this.tickMarkMode = tickMarkMode2;
    }

    public int getTickMarkTextMargin() {
        return this.tickMarkTextMargin;
    }

    public void setTickMarkTextMargin(int tickMarkTextMargin2) {
        this.tickMarkTextMargin = tickMarkTextMargin2;
    }

    public int getTickMarkTextSize() {
        return this.tickMarkTextSize;
    }

    public void setTickMarkTextSize(int tickMarkTextSize2) {
        this.tickMarkTextSize = tickMarkTextSize2;
    }

    public int getTickMarkGravity() {
        return this.tickMarkGravity;
    }

    public void setTickMarkGravity(int tickMarkGravity2) {
        this.tickMarkGravity = tickMarkGravity2;
    }

    public CharSequence[] getTickMarkTextArray() {
        return this.tickMarkTextArray;
    }

    public void setTickMarkTextArray(CharSequence[] tickMarkTextArray2) {
        this.tickMarkTextArray = tickMarkTextArray2;
    }

    public float getMinInterval() {
        return this.minInterval;
    }

    public float getProgressRadius() {
        return this.progressRadius;
    }

    public void setProgressRadius(float progressRadius2) {
        this.progressRadius = progressRadius2;
    }

    public int getProgressColor() {
        return this.progressColor;
    }

    public void setProgressColor(int progressColor2) {
        this.progressColor = progressColor2;
    }

    public int getProgressDefaultColor() {
        return this.progressDefaultColor;
    }

    public void setProgressDefaultColor(int progressDefaultColor2) {
        this.progressDefaultColor = progressDefaultColor2;
    }

    public int getProgressDrawableId() {
        return this.progressDrawableId;
    }

    public void setProgressDrawableId(int progressDrawableId2) {
        this.progressDrawableId = progressDrawableId2;
        this.progressBitmap = null;
        initProgressBitmap();
    }

    public int getProgressDefaultDrawableId() {
        return this.progressDefaultDrawableId;
    }

    public void setProgressDefaultDrawableId(int progressDefaultDrawableId2) {
        this.progressDefaultDrawableId = progressDefaultDrawableId2;
        this.progressDefaultBitmap = null;
        initProgressBitmap();
    }

    public int getProgressWidth() {
        return this.progressWidth;
    }

    public void setProgressWidth(int progressWidth2) {
        this.progressWidth = progressWidth2;
    }

    public void setTypeface(Typeface typeFace) {
        this.paint.setTypeface(typeFace);
    }

    public boolean isEnableThumbOverlap() {
        return this.enableThumbOverlap;
    }

    public void setEnableThumbOverlap(boolean enableThumbOverlap2) {
        this.enableThumbOverlap = enableThumbOverlap2;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps2) {
        this.steps = steps2;
    }

    public int getStepsColor() {
        return this.stepsColor;
    }

    public void setStepsColor(int stepsColor2) {
        this.stepsColor = stepsColor2;
    }

    public float getStepsWidth() {
        return this.stepsWidth;
    }

    public void setStepsWidth(float stepsWidth2) {
        this.stepsWidth = stepsWidth2;
    }

    public float getStepsHeight() {
        return this.stepsHeight;
    }

    public void setStepsHeight(float stepsHeight2) {
        this.stepsHeight = stepsHeight2;
    }

    public float getStepsRadius() {
        return this.stepsRadius;
    }

    public void setStepsRadius(float stepsRadius2) {
        this.stepsRadius = stepsRadius2;
    }

    public int getTickMarkLayoutGravity() {
        return this.tickMarkLayoutGravity;
    }

    public void setTickMarkLayoutGravity(int tickMarkLayoutGravity2) {
        this.tickMarkLayoutGravity = tickMarkLayoutGravity2;
    }

    public int getGravity() {
        return this.gravity;
    }

    public void setGravity(int gravity2) {
        this.gravity = gravity2;
    }

    public boolean isStepsAutoBonding() {
        return this.stepsAutoBonding;
    }

    public void setStepsAutoBonding(boolean stepsAutoBonding2) {
        this.stepsAutoBonding = stepsAutoBonding2;
    }

    public int getStepsDrawableId() {
        return this.stepsDrawableId;
    }

    public void setStepsDrawableId(int stepsDrawableId2) {
        this.stepsBitmaps.clear();
        this.stepsDrawableId = stepsDrawableId2;
        initStepsBitmap();
    }

    public List<Bitmap> getStepsBitmaps() {
        return this.stepsBitmaps;
    }

    public void setStepsBitmaps(List<Bitmap> stepsBitmaps2) {
        if (stepsBitmaps2 == null || stepsBitmaps2.isEmpty() || stepsBitmaps2.size() <= this.steps) {
            throw new IllegalArgumentException("stepsBitmaps must > steps !");
        }
        this.stepsBitmaps.clear();
        this.stepsBitmaps.addAll(stepsBitmaps2);
    }

    public void setStepsDrawable(List<Integer> stepsDrawableIds) {
        if (stepsDrawableIds == null || stepsDrawableIds.isEmpty() || stepsDrawableIds.size() <= this.steps) {
            throw new IllegalArgumentException("stepsDrawableIds must > steps !");
        } else if (verifyStepsMode()) {
            List<Bitmap> stepsBitmaps2 = new ArrayList<>();
            for (int i = 0; i < stepsDrawableIds.size(); i++) {
                stepsBitmaps2.add(Utils.drawableToBitmap(getContext(), (int) this.stepsWidth, (int) this.stepsHeight, stepsDrawableIds.get(i).intValue()));
            }
            setStepsBitmaps(stepsBitmaps2);
        } else {
            throw new IllegalArgumentException("stepsWidth must > 0, stepsHeight must > 0,steps must > 0 First!!");
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GravityDef {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SeekBarModeDef {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TickMarkGravityDef {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TickMarkLayoutGravityDef {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TickMarkModeDef {
    }

    public static class Gravity {
        public static final int BOTTOM = 1;
        public static final int CENTER = 2;
        public static final int TOP = 0;
    }
}
