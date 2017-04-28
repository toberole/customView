package com.zhouwei.customview.util;

import android.text.SpannableString;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhouwei on 2017/4/27.
 */

public class AsyncClipTextTask implements Runnable {
    private int maxWidth;
    private String keyWord;
    private String text;
    private TextView tv;
    private int color;

    // 搞个缓存 存储计算结果
    private static int maxCount = 1000;// 缓存的最大结果个数
    public static HashMap<String, SpannableString> cache = new HashMap<>();


    public AsyncClipTextTask(int color, int maxWidth, String keyWord, String text, TextView tv) {
        this.color = color;
        this.maxWidth = maxWidth;
        this.keyWord = keyWord;
        this.text = text;
        this.tv = tv;
    }

    @Override
    public void run() {
        // 先从缓存中取
        // 计算缓存结果
        if (cache.size() < maxCount) {
        } else {
            synchronized (AsyncClipTextTask.class) {
                Iterator<Map.Entry<String, SpannableString>> iter = cache.entrySet().iterator();
                iter.hasNext();
                iter.remove();
            }
        }
    }
}
