package com.zhouwei.customview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 可以侧拉的布局
 */
public class SwipeLayout extends FrameLayout {

    private ViewDragHelper viewDragHelper;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        viewDragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }


    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }
}
