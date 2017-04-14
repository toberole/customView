package com.zhouwei.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zhouwei.customview.R;

/**
 * 类似钟表表盘的view
 */
public class TimeView extends View {
    private int mBorderColor;
    private float mBorderWidth;
    private Paint mPaint;
    private RectF mBounds;
    private int radius;
    private int smallLength;
    private int largeLength;
    private float width;
    private float height;

    public TimeView(Context context) {
        this(context, null);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TimeView, 0, 0);
        // 获取对应的属性的值
        mBorderColor = typedArray.getColor(R.styleable.TimeView_border_color, 0xFF0000);
        mBorderWidth = typedArray.getDimension(R.styleable.TimeView_border_width, 1);
        typedArray.recycle();
        init();
    }


    private void init() {
        mPaint = new Paint();
        // 设置颜色
        mPaint.setColor(mBorderColor);
        // 设置抗锯齿
        mPaint.setAntiAlias(true);
        // 设置宽度
        mPaint.setStrokeWidth(width);
        // 设置画笔风格
        mPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Log.i("AAAA", "==onFinishInflate==");
    }

    @Override
    /**
     * 该方法是在onFinishInflate之后调用
     */
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        // 绘制区域
        mBounds = new RectF(getLeft(), getTop(), getRight(), getBottom());
        width = mBounds.width();
        height = mBounds.height();

        if (w < h) {
            radius = (int) (w / 4);
        } else {
            radius = h / 4;
        }

        smallLength = 10;
        largeLength = 20;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(0xff000000);
        mPaint.setColor(0x66555555);
        canvas.drawRoundRect(new RectF(mBounds.centerX() - (float) 0.9 * width / 2, mBounds.centerY() - (float) 0.9 * height / 2, mBounds.centerX() + (float) 0.9 * width / 2, mBounds.centerY() + (float) 0.9 * height / 2), 30, 30, mPaint);
        mPaint.setColor(mBorderColor);
        canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), radius, mPaint);
        float start_x, start_y;
        float end_x, end_y;
        for (int i = 0; i < 60; ++i) {
            start_x = radius * (float) Math.cos(Math.PI / 180 * i * 6);
            start_y = radius * (float) Math.sin(Math.PI / 180 * i * 6);
            if (i % 5 == 0) {
                end_x = start_x + largeLength * (float) Math.cos(Math.PI / 180 * i * 6);
                end_y = start_y + largeLength * (float) Math.sin(Math.PI / 180 * i * 6);
            } else {
                end_x = start_x + smallLength * (float) Math.cos(Math.PI / 180 * i * 6);
                end_y = start_y + smallLength * (float) Math.sin(Math.PI / 180 * i * 6);
            }
            start_x += mBounds.centerX();
            end_x += mBounds.centerX();
            start_y += mBounds.centerY();
            end_y += mBounds.centerY();
            canvas.drawLine(start_x, start_y, end_x, end_y, mPaint);
        }
        canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), 20, mPaint);
        canvas.rotate(60, mBounds.centerX(), mBounds.centerY());
        canvas.drawLine(mBounds.centerX(), mBounds.centerY(), mBounds.centerX(), mBounds.centerY() - radius, mPaint);
    }
}
