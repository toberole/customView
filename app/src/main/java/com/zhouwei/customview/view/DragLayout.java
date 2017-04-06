package com.zhouwei.customview.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by zhouwei on 2017/4/4.
 */
public class DragLayout extends FrameLayout {

    private ViewDragHelper viewDragHelper;
    private ViewGroup leftContent;
    private ViewGroup mainContent;
    private int width;
    private int height;

    /**
     * 控件可以滑动的范围
     */
    private int range;

    public DragLayout(@NonNull Context context) {
        this(context, null);
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        // a 解析拖拽的工具类
        viewDragHelper = ViewDragHelper.create(this, callback);
    }

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        /**
         * 当去尝试去抓view的時候回调
         * 根据返回结果 决定是否处理拖拽事件
         * @param child 被拖拽的view
         * @param pointerId 多点触摸的時候的view的id
         */
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        /**
         * 当view被捕获的时候调用
         */
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        /**
         * 返回拖拽的范围 不对拖拽产生正真的作用 只会影响动画执行的时间
         */
        public int getViewVerticalDragRange(View child) {
            return range;
        }

        @Override
        /**
         * 根据建议值修正将要移动的位置 此時view还没有移动
         * @param child Child view being dragged
         * @param top Attempted motion along the X axis
         * @param dy Proposed change in position for left
         */
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }

        @Override
        /**
         * 根据建议值修正将要移动的位置 此時view还没有移动
         * @param child Child view being dragged
         * @param left Attempted motion along the X axis
         * @param dx Proposed change in position for left
         */
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            // left = oldLeft + dx
            super.clampViewPositionHorizontal(child, left, dx);
            if (child == mainContent) {
                // 限制可以拖拽的范围
                left = fixLeft(left);
            }
            return left;
        }

        @Nullable
        private int fixLeft(int left) {
            if (left < 0) {
                return 0;
            } else if (left > range) {
                return range;
            }
            return left;
        }

        @Override
        /**
         * view 被拖动 位置变化的时候的回调
         */
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            int newLeft = left;
            if (changedView == leftContent) {
                // 转化当前的变化量 交给mainContent处理
                newLeft = mainContent.getLeft() + dx;
            }

            newLeft = fixLeft(newLeft);

            if (changedView == leftContent) {
                leftContent.layout(0, 0, 0 + width, 0 + height);
                mainContent.layout(newLeft, 0, newLeft + width, 0 + height);
            }

            // 更新状态 执行动画
            dispatchDragEvent(newLeft);

            // 为了兼容安卓10 以下的版本 每次位置移动之后 重绘界面
            invalidate();
        }

        /**
         * 更新状态 执行动画
         * @param newLeft
         */
        private void dispatchDragEvent(int newLeft) {
            float percent = (float) (newLeft * 1.0 / range);

            // 伴随动画 ViewHelper兼容安卓8 以下的手机 兼容包nineoldandroids-2.4.0.jar
            // leftContent.setScaleX(0.5f);
            ViewHelper.setScaleX(leftContent, evaluate(percent, 0.5f, 1.0f));//缩放
            ViewHelper.setScaleY(leftContent, evaluate(percent, 0.5f, 1.0f));//缩放

            // 从负的位置移动0
            ViewHelper.setTranslationX(leftContent, evaluate(percent, -width / 2.0f, 0));

            // 透明度
            ViewHelper.setAlpha(leftContent, evaluate(percent, 0.5f, 1.0f));

            // 主面板
            ViewHelper.setScaleX(mainContent, evaluate(percent, 1.0f, 0.8f));//缩放
            ViewHelper.setScaleY(mainContent, evaluate(percent, 1.0f, 0.8f));//缩放

            // 设置背景变化
            getBackground().setColorFilter((Integer) evaluateColor(percent, Color.BLACK, Color.TRANSPARENT), PorterDuff.Mode.SRC_OVER);
        }

        /**
         * This function returns the calculated in-between value for a color
         * given integers that represent the start and end values in the four
         * bytes of the 32-bit int. Each channel is separately linearly interpolated
         * and the resulting calculated values are recombined into the return value.
         *
         * @param fraction The fraction from the starting to the ending values
         * @param startValue A 32-bit int value representing colors in the
         * separate bytes of the parameter
         * @param endValue A 32-bit int value representing colors in the
         * separate bytes of the parameter
         * @return A value that is calculated to be the linearly interpolated
         * result, derived by separating the start and end values into separate
         * color channels and interpolating each one separately, recombining the
         * resulting values in the same way.
         */
        public Object evaluateColor(float fraction, Object startValue, Object endValue) {
            int startInt = (Integer) startValue;
            int startA = (startInt >> 24) & 0xff;
            int startR = (startInt >> 16) & 0xff;
            int startG = (startInt >> 8) & 0xff;
            int startB = startInt & 0xff;

            int endInt = (Integer) endValue;
            int endA = (endInt >> 24) & 0xff;
            int endR = (endInt >> 16) & 0xff;
            int endG = (endInt >> 8) & 0xff;
            int endB = endInt & 0xff;

            return (int) ((startA + (int) (fraction * (endA - startA))) << 24) |
                    (int) ((startR + (int) (fraction * (endR - startR))) << 16) |
                    (int) ((startG + (int) (fraction * (endG - startG))) << 8) |
                    (int) ((startB + (int) (fraction * (endB - startB))));
        }

        /**
         * 估值器 根据百分比 算出一个区间的值
         * @param fraction
         * @param startValue
         * @param endValue
         * @return
         */
        public Float evaluate(float fraction, Number startValue, Number endValue) {
            float startFloat = startValue.floatValue();
            return startFloat + fraction * (endValue.floatValue() - startFloat);
        }

        @Override
        /**
         * 当view被拖拽的view 被释放[松开手]的时候回调
         * @param xvel 被释放的时候水平方向的速度
         * @param yvel 被释放的时候垂直方向的速度
         */
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            if (xvel == 0 && mainContent.getLeft() > range * 0.5f) {
                open();
            } else if (xvel > 0) {
                open();
            } else {
                close();
            }
        }

    };

    public void close() {
        close(true);
    }

    /**
     * 关闭
     */
    public void close(boolean isSmooth) {
        int finalLeft = 0;
        if (true == isSmooth) {
            // 触发平滑的动画 返回值true表示还没有移动到指定的位置
            if (viewDragHelper.smoothSlideViewTo(mainContent, finalLeft, 0)) {
                // 刷新界面 刷新viewGroup导致其中的所有的孩子被刷新
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }
        mainContent.layout(finalLeft, 0, finalLeft + width, 0 + height);
    }

    /**
     * computeScroll调用Scroller，只要computeScroll调用连续，Scroller也会连续，
     * 实质上computeScroll的连续性又invalidate方法控制，scrollTo,scrollBy都会调用invalidate，
     * 而invalidate回去触发draw,从而computeScroll被连续调用，综上，Scroller也会被连续调用，
     * 除非invalidate停止调用
     */
    @Override
    public void computeScroll() {//invalidate刷新界面 会触发computeScroll view.invalidate(),会触发onDraw和computeScroll()
        super.computeScroll();
        // viewDragHelper.continueSettling(true) 判断是否持续平滑动画 返回true表示需要继续执行动画
        if (viewDragHelper.continueSettling(true)) {
            // 刷新界面
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void open() {
        open(true);
    }

    /**
     * 打开
     */
    public void open(boolean isSmooth) {
        int finalLeft = range;
        if (true == isSmooth) {
            // 触发平滑的动画 返回值true表示还没有移动到指定的位置
            if (viewDragHelper.smoothSlideViewTo(mainContent, finalLeft, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }
        mainContent.layout(finalLeft, 0, finalLeft + width, 0 + height);
    }

    // b 传递触摸事件
    @Override
    /**
     * 判断是否拦截事件
     */
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    /**
     * 处理触摸事件
     */
    public boolean onTouchEvent(MotionEvent event) {
        // viewDragHelpershijian 處理觸摸事件
        viewDragHelper.processTouchEvent(event);
        return true;// 消费掉事件
    }

    @Override
    /**
     * Finalize inflating a view from XML.  This is called as the last phase
     * of inflation, after all child views have been added.
     */
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() < 2) {
            throw new RuntimeException("you must have two children");
        }

        if (!(this.getChildAt(0) instanceof ViewGroup && this.getChildAt(1) instanceof ViewGroup)) {
            throw new RuntimeException("the child must viewgroup");
        }
        leftContent = (ViewGroup) this.getChildAt(0);
        mainContent = (ViewGroup) this.getChildAt(1);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        /**
         * 获取到屏幕的区域显示view宽度和高度[不包含title的高度]
         */
        width = this.getMeasuredWidth();
        height = this.getMeasuredHeight();

        range = (int) (width * 0.6);
    }
}
