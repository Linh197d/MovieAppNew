package com.qibla.muslimday.app2025.customeview.seekbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import com.qibla.muslimday.app2025.R;

import java.text.DecimalFormat;

public class SeekBar {
    public static final int INDICATOR_ALWAYS_HIDE = 1;
    public static final int INDICATOR_ALWAYS_SHOW = 3;
    public static final int INDICATOR_ALWAYS_SHOW_AFTER_TOUCH = 2;
    public static final int INDICATOR_SHOW_WHEN_TOUCH = 0;
    public static final int WRAP_CONTENT = -1;
    ValueAnimator anim;
    int bottom;
    float currPercent;
    Path indicatorArrowPath = new Path();
    Bitmap indicatorBitmap;
    Rect indicatorRect = new Rect();
    DecimalFormat indicatorTextDecimalFormat;
    Rect indicatorTextRect = new Rect();
    String indicatorTextStringFormat;
    boolean isActivate = false;
    boolean isLeft;
    boolean isVisible = true;
    int left;
    float material = 0.0f;
    Paint paint = new Paint(1);
    RangeSeekBar rangeSeekBar;
    int right;
    int scaleThumbHeight;
    int scaleThumbWidth;
    Bitmap thumbBitmap;
    Bitmap thumbInactivatedBitmap;
    float thumbScaleRatio;
    int top;
    String userText2Draw;
    private int indicatorArrowSize;
    private int indicatorBackgroundColor;
    private int indicatorDrawableId;
    private int indicatorHeight;
    private int indicatorMargin;
    private int indicatorPaddingBottom;
    private int indicatorPaddingLeft;
    private int indicatorPaddingRight;
    private int indicatorPaddingTop;
    private float indicatorRadius;
    private int indicatorShowMode;
    private int indicatorTextColor;
    private int indicatorTextSize;
    private int indicatorWidth;
    private boolean isShowIndicator;
    private int thumbDrawableId;
    private int thumbHeight;
    private int thumbInactivatedDrawableId;
    private int thumbWidth;

    public SeekBar(RangeSeekBar rangeSeekBar2, AttributeSet attrs, boolean isLeft2) {
        this.rangeSeekBar = rangeSeekBar2;
        this.isLeft = isLeft2;
        initAttrs(attrs);
        initBitmap();
        initVariables();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.RangeSeekBar);
        if (t != null) {
            this.indicatorMargin = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_margin, 0.0f);
            this.indicatorDrawableId = t.getResourceId(R.styleable.RangeSeekBar_rsb_indicator_drawable, 0);
            this.indicatorShowMode = t.getInt(R.styleable.RangeSeekBar_rsb_indicator_show_mode, 1);
            this.indicatorHeight = t.getLayoutDimension(R.styleable.RangeSeekBar_rsb_indicator_height, -1);
            this.indicatorWidth = t.getLayoutDimension(R.styleable.RangeSeekBar_rsb_indicator_width, -1);
            this.indicatorTextSize = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_text_size, (float) Utils.dp2px(getContext(), 14.0f));
            this.indicatorTextColor = t.getColor(R.styleable.RangeSeekBar_rsb_indicator_text_color, -1);
            this.indicatorBackgroundColor = t.getColor(R.styleable.RangeSeekBar_rsb_indicator_background_color, ContextCompat.getColor(getContext(), R.color.colorAccent));
            this.indicatorPaddingLeft = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_padding_left, 0.0f);
            this.indicatorPaddingRight = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_padding_right, 0.0f);
            this.indicatorPaddingTop = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_padding_top, 0.0f);
            this.indicatorPaddingBottom = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_padding_bottom, 0.0f);
            this.indicatorArrowSize = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_arrow_size, 0.0f);
            this.thumbDrawableId = t.getResourceId(R.styleable.RangeSeekBar_rsb_thumb_drawable, R.drawable.rsb_default_thumb);
            this.thumbInactivatedDrawableId = t.getResourceId(R.styleable.RangeSeekBar_rsb_thumb_inactivated_drawable, 0);
            this.thumbWidth = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_thumb_width, (float) Utils.dp2px(getContext(), 26.0f));
            this.thumbHeight = (int) t.getDimension(R.styleable.RangeSeekBar_rsb_thumb_height, (float) Utils.dp2px(getContext(), 26.0f));
            this.thumbScaleRatio = t.getFloat(R.styleable.RangeSeekBar_rsb_thumb_scale_ratio, 1.0f);
            this.indicatorRadius = t.getDimension(R.styleable.RangeSeekBar_rsb_indicator_radius, 0.0f);
            t.recycle();
        }
    }

    public void initVariables() {
        this.scaleThumbWidth = this.thumbWidth;
        this.scaleThumbHeight = this.thumbHeight;
        if (this.indicatorHeight == -1) {
            this.indicatorHeight = Utils.measureText("8", (float) this.indicatorTextSize).height() + this.indicatorPaddingTop + this.indicatorPaddingBottom;
        }
        if (this.indicatorArrowSize <= 0) {
            this.indicatorArrowSize = this.thumbWidth / 4;
        }
    }

    public Context getContext() {
        return this.rangeSeekBar.getContext();
    }

    public Resources getResources() {
        if (getContext() != null) {
            return getContext().getResources();
        }
        return null;
    }

    private void initBitmap() {
        setIndicatorDrawableId(this.indicatorDrawableId);
        setThumbDrawableId(this.thumbDrawableId, this.thumbWidth, this.thumbHeight);
        setThumbInactivatedDrawableId(this.thumbInactivatedDrawableId, this.thumbWidth, this.thumbHeight);
    }

    public void onSizeChanged(int x, int y) {
        initVariables();
        initBitmap();
        this.left = (int) (((float) x) - (getThumbScaleWidth() / 2.0f));
        this.right = (int) (((float) x) + (getThumbScaleWidth() / 2.0f));
        this.top = y - (getThumbHeight() / 2);
        this.bottom = (getThumbHeight() / 2) + y;
    }

    public void scaleThumb() {
        this.scaleThumbWidth = (int) getThumbScaleWidth();
        this.scaleThumbHeight = (int) getThumbScaleHeight();
        int y = this.rangeSeekBar.getProgressBottom();
        int i = this.scaleThumbHeight;
        this.top = y - (i / 2);
        this.bottom = (i / 2) + y;
        setThumbDrawableId(this.thumbDrawableId, this.scaleThumbWidth, i);
    }

    public void resetThumb() {
        this.scaleThumbWidth = getThumbWidth();
        this.scaleThumbHeight = getThumbHeight();
        int y = this.rangeSeekBar.getProgressBottom();
        int i = this.scaleThumbHeight;
        this.top = y - (i / 2);
        this.bottom = (i / 2) + y;
        setThumbDrawableId(this.thumbDrawableId, this.scaleThumbWidth, i);
    }

    public float getRawHeight() {
        return ((float) (getIndicatorHeight() + getIndicatorArrowSize() + getIndicatorMargin())) + getThumbScaleHeight();
    }

    public void draw(Canvas canvas) {
        if (this.isVisible) {
            canvas.save();
            canvas.translate((float) ((int) (((float) this.rangeSeekBar.getProgressWidth()) * this.currPercent)), 0.0f);
            canvas.translate((float) this.left, 0.0f);
            if (this.isShowIndicator) {
                onDrawIndicator(canvas, this.paint, formatCurrentIndicatorText(this.userText2Draw));
            }
            onDrawThumb(canvas);
            canvas.restore();
        }
    }

    public void onDrawThumb(Canvas canvas) {
        Bitmap bitmap = this.thumbInactivatedBitmap;
        if (bitmap == null || this.isActivate) {
            Bitmap bitmap2 = this.thumbBitmap;
            if (bitmap2 != null) {
                canvas.drawBitmap(bitmap2, 0.0f, ((float) this.rangeSeekBar.getProgressTop()) + (((float) (this.rangeSeekBar.getProgressHeight() - this.scaleThumbHeight)) / 2.0f), (Paint) null);
                return;
            }
            return;
        }
        canvas.drawBitmap(bitmap, 0.0f, ((float) this.rangeSeekBar.getProgressTop()) + (((float) (this.rangeSeekBar.getProgressHeight() - this.scaleThumbHeight)) / 2.0f), (Paint) null);
    }

    public String formatCurrentIndicatorText(String text2Draw) {
        SeekBarState[] states = this.rangeSeekBar.getRangeSeekBarState();
        if (TextUtils.isEmpty(text2Draw)) {
            if (this.isLeft) {
                DecimalFormat decimalFormat = this.indicatorTextDecimalFormat;
                if (decimalFormat != null) {
                    text2Draw = decimalFormat.format((double) states[0].value);
                } else {
                    text2Draw = states[0].indicatorText;
                }
            } else {
                DecimalFormat decimalFormat2 = this.indicatorTextDecimalFormat;
                if (decimalFormat2 != null) {
                    text2Draw = decimalFormat2.format((double) states[1].value);
                } else {
                    text2Draw = states[1].indicatorText;
                }
            }
        }
        String str = this.indicatorTextStringFormat;
        if (str == null) {
            return text2Draw;
        }
        return String.format(str, new Object[]{text2Draw});
    }

    public void onDrawIndicator(Canvas canvas, Paint paint2, String text2Draw) {
        int tx;
        int ty;
        if (text2Draw != null) {
            paint2.setTextSize((float) this.indicatorTextSize);
            paint2.setStyle(Paint.Style.FILL);
            paint2.setColor(this.indicatorBackgroundColor);
            paint2.getTextBounds(text2Draw, 0, text2Draw.length(), this.indicatorTextRect);
            int realIndicatorWidth = this.indicatorTextRect.width() + this.indicatorPaddingLeft + this.indicatorPaddingRight;
            if (this.indicatorWidth > realIndicatorWidth) {
                realIndicatorWidth = this.indicatorWidth;
            }
            int realIndicatorHeight = this.indicatorTextRect.height() + this.indicatorPaddingTop + this.indicatorPaddingBottom;
            if (this.indicatorHeight > realIndicatorHeight) {
                realIndicatorHeight = this.indicatorHeight;
            }
            this.indicatorRect.left = (int) ((((float) this.scaleThumbWidth) / 2.0f) - (((float) realIndicatorWidth) / 2.0f));
            this.indicatorRect.top = ((this.bottom - realIndicatorHeight) - this.scaleThumbHeight) - this.indicatorMargin;
            Rect rect = this.indicatorRect;
            rect.right = rect.left + realIndicatorWidth;
            Rect rect2 = this.indicatorRect;
            rect2.bottom = rect2.top + realIndicatorHeight;
            if (this.indicatorBitmap == null) {
                int ax = this.scaleThumbWidth / 2;
                int ay = this.indicatorRect.bottom;
                int i = this.indicatorArrowSize;
                int by = ay - i;
                this.indicatorArrowPath.reset();
                this.indicatorArrowPath.moveTo((float) ax, (float) ay);
                this.indicatorArrowPath.lineTo((float) (ax - i), (float) by);
                this.indicatorArrowPath.lineTo((float) (i + ax), (float) by);
                this.indicatorArrowPath.close();
                canvas.drawPath(this.indicatorArrowPath, paint2);
                this.indicatorRect.bottom -= this.indicatorArrowSize;
                this.indicatorRect.top -= this.indicatorArrowSize;
            }
            int defaultPaddingOffset = Utils.dp2px(getContext(), 1.0f);
            int leftOffset = (((this.indicatorRect.width() / 2) - ((int) (((float) this.rangeSeekBar.getProgressWidth()) * this.currPercent))) - this.rangeSeekBar.getProgressLeft()) + defaultPaddingOffset;
            int rightOffset = (((this.indicatorRect.width() / 2) - ((int) (((float) this.rangeSeekBar.getProgressWidth()) * (1.0f - this.currPercent)))) - this.rangeSeekBar.getProgressPaddingRight()) + defaultPaddingOffset;
            if (leftOffset > 0) {
                this.indicatorRect.left += leftOffset;
                this.indicatorRect.right += leftOffset;
            } else if (rightOffset > 0) {
                this.indicatorRect.left -= rightOffset;
                this.indicatorRect.right -= rightOffset;
            }
            Bitmap bitmap = this.indicatorBitmap;
            if (bitmap != null) {
                Utils.drawBitmap(canvas, paint2, bitmap, this.indicatorRect);
            } else if (this.indicatorRadius > 0.0f) {
                RectF rectF = new RectF(this.indicatorRect);
                float f = this.indicatorRadius;
                canvas.drawRoundRect(rectF, f, f, paint2);
            } else {
                canvas.drawRect(this.indicatorRect, paint2);
            }
            if (this.indicatorPaddingLeft > 0) {
                tx = this.indicatorRect.left + this.indicatorPaddingLeft;
            } else if (this.indicatorPaddingRight > 0) {
                tx = (this.indicatorRect.right - this.indicatorPaddingRight) - this.indicatorTextRect.width();
            } else {
                tx = this.indicatorRect.left + ((realIndicatorWidth - this.indicatorTextRect.width()) / 2);
            }
            if (this.indicatorPaddingTop > 0) {
                ty = this.indicatorRect.top + this.indicatorTextRect.height() + this.indicatorPaddingTop;
            } else if (this.indicatorPaddingBottom > 0) {
                ty = (this.indicatorRect.bottom - this.indicatorTextRect.height()) - this.indicatorPaddingBottom;
            } else {
                ty = (this.indicatorRect.bottom - ((realIndicatorHeight - this.indicatorTextRect.height()) / 2)) + 1;
            }
            paint2.setColor(this.indicatorTextColor);
            canvas.drawText(text2Draw, (float) tx, (float) ty, paint2);
        }
    }

    public boolean collide(float x, float y) {
        int offset = (int) (((float) this.rangeSeekBar.getProgressWidth()) * this.currPercent);
        return x > ((float) (this.left + offset)) && x < ((float) (this.right + offset)) && y > ((float) this.top) && y < ((float) this.bottom);
    }

    public void slide(float percent) {
        if (percent < 0.0f) {
            percent = 0.0f;
        } else if (percent > 1.0f) {
            percent = 1.0f;
        }
        this.currPercent = percent;
    }

    public void setShowIndicatorEnable(boolean isEnable) {
        switch (this.indicatorShowMode) {
            case 0:
                this.isShowIndicator = isEnable;
                return;
            case 1:
                this.isShowIndicator = false;
                return;
            case 2:
            case 3:
                this.isShowIndicator = true;
                return;
            default:
                return;
        }
    }

    public void materialRestore() {
        ValueAnimator valueAnimator = this.anim;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.material, 0.0f});
        this.anim = ofFloat;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                SeekBar.this.material = ((Float) animation.getAnimatedValue()).floatValue();
                if (SeekBar.this.rangeSeekBar != null) {
                    SeekBar.this.rangeSeekBar.invalidate();
                }
            }
        });
        this.anim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                SeekBar.this.material = 0.0f;
                if (SeekBar.this.rangeSeekBar != null) {
                    SeekBar.this.rangeSeekBar.invalidate();
                }
            }
        });
        this.anim.start();
    }

    public void setIndicatorText(String text) {
        this.userText2Draw = text;
    }

    public DecimalFormat getIndicatorTextDecimalFormat() {
        return this.indicatorTextDecimalFormat;
    }

    public void setIndicatorTextDecimalFormat(String formatPattern) {
        this.indicatorTextDecimalFormat = new DecimalFormat(formatPattern);
    }

    public void setIndicatorTextStringFormat(String formatPattern) {
        this.indicatorTextStringFormat = formatPattern;
    }

    public int getIndicatorDrawableId() {
        return this.indicatorDrawableId;
    }

    public void setIndicatorDrawableId(int indicatorDrawableId2) {
        if (indicatorDrawableId2 != 0) {
            this.indicatorDrawableId = indicatorDrawableId2;
            this.indicatorBitmap = BitmapFactory.decodeResource(getResources(), indicatorDrawableId2);
        }
    }

    public int getIndicatorArrowSize() {
        return this.indicatorArrowSize;
    }

    public void setIndicatorArrowSize(int indicatorArrowSize2) {
        this.indicatorArrowSize = indicatorArrowSize2;
    }

    public int getIndicatorPaddingLeft() {
        return this.indicatorPaddingLeft;
    }

    public void setIndicatorPaddingLeft(int indicatorPaddingLeft2) {
        this.indicatorPaddingLeft = indicatorPaddingLeft2;
    }

    public int getIndicatorPaddingRight() {
        return this.indicatorPaddingRight;
    }

    public void setIndicatorPaddingRight(int indicatorPaddingRight2) {
        this.indicatorPaddingRight = indicatorPaddingRight2;
    }

    public int getIndicatorPaddingTop() {
        return this.indicatorPaddingTop;
    }

    public void setIndicatorPaddingTop(int indicatorPaddingTop2) {
        this.indicatorPaddingTop = indicatorPaddingTop2;
    }

    public int getIndicatorPaddingBottom() {
        return this.indicatorPaddingBottom;
    }

    public void setIndicatorPaddingBottom(int indicatorPaddingBottom2) {
        this.indicatorPaddingBottom = indicatorPaddingBottom2;
    }

    public int getIndicatorMargin() {
        return this.indicatorMargin;
    }

    public void setIndicatorMargin(int indicatorMargin2) {
        this.indicatorMargin = indicatorMargin2;
    }

    public int getIndicatorShowMode() {
        return this.indicatorShowMode;
    }

    public void setIndicatorShowMode(int indicatorShowMode2) {
        this.indicatorShowMode = indicatorShowMode2;
    }

    public void showIndicator(boolean isShown) {
        this.isShowIndicator = isShown;
    }

    public boolean isShowIndicator() {
        return this.isShowIndicator;
    }

    public int getIndicatorRawHeight() {
        int i = this.indicatorHeight;
        if (i > 0) {
            if (this.indicatorBitmap != null) {
                return i + this.indicatorMargin;
            }
            return i + this.indicatorArrowSize + this.indicatorMargin;
        } else if (this.indicatorBitmap != null) {
            return Utils.measureText("8", (float) this.indicatorTextSize).height() + this.indicatorPaddingTop + this.indicatorPaddingBottom + this.indicatorMargin;
        } else {
            return Utils.measureText("8", (float) this.indicatorTextSize).height() + this.indicatorPaddingTop + this.indicatorPaddingBottom + this.indicatorMargin + this.indicatorArrowSize;
        }
    }

    public int getIndicatorHeight() {
        return this.indicatorHeight;
    }

    public void setIndicatorHeight(int indicatorHeight2) {
        this.indicatorHeight = indicatorHeight2;
    }

    public int getIndicatorWidth() {
        return this.indicatorWidth;
    }

    public void setIndicatorWidth(int indicatorWidth2) {
        this.indicatorWidth = indicatorWidth2;
    }

    public int getIndicatorTextSize() {
        return this.indicatorTextSize;
    }

    public void setIndicatorTextSize(int indicatorTextSize2) {
        this.indicatorTextSize = indicatorTextSize2;
    }

    public int getIndicatorTextColor() {
        return this.indicatorTextColor;
    }

    public void setIndicatorTextColor(int indicatorTextColor2) {
        this.indicatorTextColor = indicatorTextColor2;
    }

    public int getIndicatorBackgroundColor() {
        return this.indicatorBackgroundColor;
    }

    public void setIndicatorBackgroundColor(int indicatorBackgroundColor2) {
        this.indicatorBackgroundColor = indicatorBackgroundColor2;
    }

    public int getThumbInactivatedDrawableId() {
        return this.thumbInactivatedDrawableId;
    }

    public void setThumbInactivatedDrawableId(int thumbInactivatedDrawableId2, int width, int height) {
        if (thumbInactivatedDrawableId2 != 0 && getResources() != null) {
            this.thumbInactivatedDrawableId = thumbInactivatedDrawableId2;
            this.thumbInactivatedBitmap = Utils.drawableToBitmap(width, height, getResources().getDrawable(thumbInactivatedDrawableId2, (Resources.Theme) null));
        }
    }

    public int getThumbDrawableId() {
        return this.thumbDrawableId;
    }

    public void setThumbDrawableId(int thumbDrawableId2) {
        if (this.thumbWidth <= 0 || this.thumbHeight <= 0) {
            throw new IllegalArgumentException("please set thumbWidth and thumbHeight first!");
        } else if (thumbDrawableId2 != 0 && getResources() != null) {
            this.thumbDrawableId = thumbDrawableId2;
            this.thumbBitmap = Utils.drawableToBitmap(this.thumbWidth, this.thumbHeight, getResources().getDrawable(thumbDrawableId2, (Resources.Theme) null));
        }
    }

    public void setThumbDrawableId(int thumbDrawableId2, int width, int height) {
        if (thumbDrawableId2 != 0 && getResources() != null && width > 0 && height > 0) {
            this.thumbDrawableId = thumbDrawableId2;
            this.thumbBitmap = Utils.drawableToBitmap(width, height, getResources().getDrawable(thumbDrawableId2, (Resources.Theme) null));
        }
    }

    public int getThumbWidth() {
        return this.thumbWidth;
    }

    public void setThumbWidth(int thumbWidth2) {
        this.thumbWidth = thumbWidth2;
    }

    public float getThumbScaleHeight() {
        return ((float) this.thumbHeight) * this.thumbScaleRatio;
    }

    public float getThumbScaleWidth() {
        return ((float) this.thumbWidth) * this.thumbScaleRatio;
    }

    public int getThumbHeight() {
        return this.thumbHeight;
    }

    public void setThumbHeight(int thumbHeight2) {
        this.thumbHeight = thumbHeight2;
    }

    public float getIndicatorRadius() {
        return this.indicatorRadius;
    }

    public void setIndicatorRadius(float indicatorRadius2) {
        this.indicatorRadius = indicatorRadius2;
    }

    public boolean getActivate() {
        return this.isActivate;
    }

    public void setActivate(boolean activate) {
        this.isActivate = activate;
    }

    public void setTypeface(Typeface typeFace) {
        this.paint.setTypeface(typeFace);
    }

    public float getThumbScaleRatio() {
        return this.thumbScaleRatio;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public float getProgress() {
        return this.rangeSeekBar.getMinProgress() + (this.currPercent * (this.rangeSeekBar.getMaxProgress() - this.rangeSeekBar.getMinProgress()));
    }

    public @interface IndicatorModeDef {
    }
}
