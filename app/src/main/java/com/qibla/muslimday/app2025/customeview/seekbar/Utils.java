package com.qibla.muslimday.app2025.customeview.seekbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class Utils {
    private static final String TAG = "RangeSeekBar";

    public static void print(String log) {
        Log.d(TAG, log);
    }

    public static void print(Object... logs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object log : logs) {
            stringBuilder.append(log);
        }
        Log.d(TAG, stringBuilder.toString());
    }

    public static Bitmap drawableToBitmap(Context context, int width, int height, int drawableId) {
        if (context == null || width <= 0 || height <= 0 || drawableId == 0) {
            return null;
        }
        return drawableToBitmap(width, height, context.getResources().getDrawable(drawableId, (Resources.Theme) null));
    }

    public static Bitmap drawableToBitmap(int width, int height, Drawable drawable) {
        Bitmap bitmap;
        try {
            if (!(drawable instanceof BitmapDrawable) || (bitmap = ((BitmapDrawable) drawable).getBitmap()) == null || bitmap.getHeight() <= 0) {
                Bitmap bitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap2);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                return bitmap2;
            }
            Matrix matrix = new Matrix();
            matrix.postScale((((float) width) * 1.0f) / ((float) bitmap.getWidth()), (((float) height) * 1.0f) / ((float) bitmap.getHeight()));
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void drawNinePath(Canvas canvas, Bitmap bmp, Rect rect) {
        NinePatch.isNinePatchChunk(bmp.getNinePatchChunk());
        new NinePatch(bmp, bmp.getNinePatchChunk(), (String) null).draw(canvas, rect);
    }

    public static void drawBitmap(Canvas canvas, Paint paint, Bitmap bmp, Rect rect) {
        try {
            if (NinePatch.isNinePatchChunk(bmp.getNinePatchChunk())) {
                drawNinePath(canvas, bmp, rect);
                return;
            }
        } catch (Exception e) {
        }
        canvas.drawBitmap(bmp, (float) rect.left, (float) rect.top, paint);
    }

    public static int dp2px(Context context, float dpValue) {
        if (context == null || compareFloat(0.0f, dpValue) == 0) {
            return 0;
        }
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int compareFloat(float a, float b) {
        return Integer.compare(Math.round(a * 1000000.0f), Math.round(1000000.0f * b));
    }

    public static int compareFloat(float a, float b, int degree) {
        if (((double) Math.abs(a - b)) < Math.pow(0.1d, (double) degree)) {
            return 0;
        }
        if (a < b) {
            return -1;
        }
        return 1;
    }

    public static float parseFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    public static Rect measureText(String text, float textSize) {
        Paint paint = new Paint();
        Rect textRect = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), textRect);
        paint.reset();
        return textRect;
    }

    public static boolean verifyBitmap(Bitmap bitmap) {
        return bitmap != null && !bitmap.isRecycled() && bitmap.getWidth() > 0 && bitmap.getHeight() > 0;
    }

    public static int getColor(Context context, int colorId) {
        if (context != null) {
            return ContextCompat.getColor(context.getApplicationContext(), colorId);
        }
        return -1;
    }
}
