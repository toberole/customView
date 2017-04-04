package com.zhouwei.customview.view;

import android.content.Context;
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

            // 为了兼容安卓10 一下的版本 每次位置移动之后 重绘界面
            invalidate();
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
            // 触发平滑的动画
            if (viewDragHelper.smoothSlideViewTo(mainContent, finalLeft, 0)) {
                // 返回值true表示还没有移动到指定的位置
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }
        mainContent.layout(finalLeft, 0, finalLeft + width, 0 + height);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (viewDragHelper.continueSettling(true)) {
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
            // 触发平滑的动画
            if (viewDragHelper.smoothSlideViewTo(mainContent, finalLeft, 0)) {
                // 返回值true表示还没有移动到指定的位置
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
