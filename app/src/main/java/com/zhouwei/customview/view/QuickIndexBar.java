package com.zhouwei.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 快速索引的控件
 */
public class QuickIndexBar extends View {
    private final static String[] LETTERS = new String[]{
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    private Paint paint;
    private float cellWidth;
    private float cellHeight;

    private OnLetterUpdatelistener onLetterUpdatelistener;

    public void setOnLetterUpdatelistener(OnLetterUpdatelistener onLetterUpdatelistener) {
        this.onLetterUpdatelistener = onLetterUpdatelistener;
    }

    public interface OnLetterUpdatelistener {
        void onLetterUpdate(String letter);
    }

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setTextSize(40.0f);// 控制字体的大小
        paint.setTypeface(Typeface.DEFAULT_BOLD);// 圆滑粗体
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画字母
        for (int i = 0; i < LETTERS.length; i++) {
            int x = (int) (cellWidth / 2 - paint.measureText(LETTERS[i]) / 2);
            Rect bounds = new Rect();
            paint.getTextBounds(LETTERS[i], 0, LETTERS[i].length(), bounds);
            int y = (int) (cellHeight / 2.0f + bounds.height() / 2 + cellHeight * i);
            //被选择的设置为灰色
            if (i == touchIndex) {
                paint.setColor(Color.GRAY);
            } else {
                paint.setColor(Color.RED);
            }
            // 注意 画文字的时候(x,y)是指文字的左下角的坐标 比较特殊
            canvas.drawText(LETTERS[i], x, y, paint);
        }
    }

    private int touchIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                showTouchedLetter(event);
                break;
            case MotionEvent.ACTION_MOVE:
                showTouchedLetter(event);
                break;
            case MotionEvent.ACTION_UP:
                showTouchedLetter(event);
                break;
            default:
                break;
        }
        // 重绘界面 突出被选择的
        invalidate();
        return true;
    }

    private void showTouchedLetter(MotionEvent event) {
        int index = (int) (event.getY() / cellHeight);
        if (index >= 0 && index < LETTERS.length) {
            if (touchIndex != index) {
                touchIndex = index;

                // Log.i("MainActivity", LETTERS[index]);
                if (onLetterUpdatelistener != null) {
                    onLetterUpdatelistener.onLetterUpdate(LETTERS[index]);
                }
            }
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 每一个字母占据的高度
        cellHeight = getMeasuredHeight() * 1.0f / LETTERS.length;
        cellWidth = getMeasuredWidth();
    }
}
