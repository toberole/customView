package com.zhouwei.customview.view;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

import com.zhouwei.customview.anim.ResetAnimation;

/**
 * 带有视差效果的listview
 */
public class ParallaxListview extends ListView {
    private ImageView parallaxImageView;
    private int height;
    private int originHeight;

    public ParallaxListview(Context context) {
        this(context, null);
    }

    public ParallaxListview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxListview(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ParallaxListview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setParallaxImageView(ImageView parallaxImageView) {
        this.parallaxImageView = parallaxImageView;
        // 初始展示的高度 和mesasure的高度一样 parallaxImageView.getMeasuredHeight()
        height = parallaxImageView.getHeight();
        // 图片的原始高度
        originHeight = parallaxImageView.getDrawable().getIntrinsicHeight();
    }

    @Override
    /**
     * @param isTouchEvent 手指滑动为ture 惯性为false
     */
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        // 手指拉动 并且下拉
        if (isTouchEvent && deltaY < 0) {
            // 除以3 是为了不等比移动 造成视差效果
            int newHeight = (int) (Math.abs(deltaY) / 3.0f + parallaxImageView.getHeight());
            if (newHeight <= originHeight) {
                parallaxImageView.getLayoutParams().height = newHeight;
                parallaxImageView.requestLayout();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                final int startHeight = parallaxImageView.getHeight();
                final int endHeight = height;
                // 执行会弹动画 方式一
                // valueAnimator(startHeight, endHeight);

                // 方式二 自定义动画
                ResetAnimation animation = new ResetAnimation(parallaxImageView, startHeight, endHeight);
                startAnimation(animation);
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void valueAnimator(final int startHeight, final int endHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                float newHeight = evaluate(fraction, startHeight, endHeight);
                parallaxImageView.getLayoutParams().height = (int) newHeight;
                parallaxImageView.requestLayout();
            }
        });
        // 回弹
        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(500);
        animator.start();
    }

    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }
}
