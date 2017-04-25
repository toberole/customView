package com.zhouwei.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.zhouwei.customview.R;


/**
 * 贝塞尔曲线的加载图
 */
public class BesselLoadingView extends View {

    /**
     * 颜色
     */
    private int mColor;

    /**
     * 半径
     */
    private float mRadius;

    private float mRadiusFloat;

    /**
     * 持续时间
     */
    private int mDuration;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 路径
     */
    private Path mPath;
    private int[] mCirclesX;

    private int mCirClesY;
    private int mMinDistance;


    public BesselLoadingView(Context context) {
        this(context, null);
    }

    public BesselLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BesselLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获取自定义属性的值
         */
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BesselLoadingView);
        mColor = ta.getColor(R.styleable.BesselLoadingView_loading_color, Color.RED);
        mRadius = ta.getDimension(R.styleable.BesselLoadingView_loading_radius, 5);
        mDuration = ta.getInteger(R.styleable.BesselLoadingView_loading_duration, 5);

        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);

        mPath = new Path();

        mRadiusFloat = mRadius * 0.9f;

        mCirclesX = new int[3];
    }

    private float mFloatX;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mWidth = 0;
        int mHeight = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = width;
        } else {
            mWidth = getPaddingLeft() + 480 + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = height;
        } else {
            mHeight = getPaddingTop() + 100 + getPaddingBottom();
        }

        // 设置测量高度
        setMeasuredDimension(mWidth, mHeight);

        //计算x方向三个圆心
        int length = mWidth / 4;
        for (int i = 0; i < mCirclesX.length; i++) {
            mCirclesX[i] = length * (i + 1);
        }

        //计算三个圆心Y坐标
        mCirClesY = mHeight / 2;
        //三个初始圆的半径
        mRadius = mHeight / 3;
        mRadiusFloat = mRadius * 0.9f;

        if (mRadius >= length / 4) {
            // log("圆的半径大于间隙了,自动缩小");
            mRadius = length / 4;
            mRadiusFloat = mRadius * 0.9f;
        }

        mMinDistance = length;

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mRadius, mWidth - mRadius);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(mDuration);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFloatX = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画三个圆
        for (int i = 0; i < 3; i++) {
            canvas.drawCircle(mCirclesX[i], mCirClesY, mRadius, mPaint);
        }
        //画滑动圆
        canvas.drawCircle(mFloatX, mCirClesY, mRadiusFloat, mPaint);

        drawBesselLine(canvas);
    }

    /**
     * 绘制贝塞尔曲线与定点圆变大
     */
    private void drawBesselLine(Canvas canvas) {
        float minDis = mMinDistance;
        int minLocation = 0;
        for (int i = 0; i < 3; i++) {
            float dis = Math.abs((mFloatX - mCirclesX[i]));
            if (dis < minDis) {
                minDis = dis;
                minLocation = i;
            }
        }
        // log("最小距离为 " + minDis + "位置:" + minLocation);
        if (minDis < mMinDistance) {

            float middleX = (mCirclesX[minLocation] + mFloatX) / 2;
            //绘制上半部分贝塞尔曲线
            mPath.moveTo(mCirclesX[minLocation], mCirClesY + mRadius);
            mPath.quadTo(middleX, mCirClesY, mFloatX, mCirClesY + mRadiusFloat);

            mPath.lineTo(mFloatX, mCirClesY - mRadiusFloat);

            mPath.quadTo(middleX, mCirClesY, mCirclesX[minLocation], mCirClesY - mRadius);

            mPath.lineTo(mCirclesX[minLocation], mCirClesY + mRadius);
            mPath.close();

            canvas.drawPath(mPath, mPaint);
            mPath.reset();
            //浮动圆靠近固定圆变大
            float f = 1 + (mMinDistance - minDis * 2) / mMinDistance * 0.2f;
            // log("dis% : " + (mMinDistance - minDis) / mMinDistance + "  f = " + f);
            canvas.drawCircle(mCirclesX[minLocation], mCirClesY, mRadius * f, mPaint);
        }
    }
}
