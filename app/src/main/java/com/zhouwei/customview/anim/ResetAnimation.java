package com.zhouwei.customview.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;

/**
 * 自定义animation
 */
public class ResetAnimation extends Animation {
    private View view;
    private int startHeight;
    private int endHeight;

    public ResetAnimation(View view, int startHeight, int endHeight) {
        this.view = view;
        this.startHeight = startHeight;
        this.endHeight = endHeight;
        setInterpolator(new OvershootInterpolator());
        setDuration(300);
    }

    @Override
    /**
     * 动画执行期间回调该方法
     * interpolatedTime = 0-1
     */
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float newHeight = evaluate(interpolatedTime, startHeight, endHeight);
        view.getLayoutParams().height = (int) newHeight;
        view.requestLayout();
        super.applyTransformation(interpolatedTime, t);

        // Log.i("AAAA","=======applyTransformation======="+interpolatedTime);
    }

    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }
}
