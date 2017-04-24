package com.zhouwei.customview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 贝塞尔曲线的加载图
 */
public class BesselLoadingView extends View {
    public BesselLoadingView(Context context) {
        this(context,null);
    }

    public BesselLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BesselLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
