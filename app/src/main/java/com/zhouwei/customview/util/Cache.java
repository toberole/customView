package com.zhouwei.customview.util;

import android.support.v4.util.LruCache;

/**
 * Created by zhouwei on 2017/4/27.
 */

public class Cache extends LruCache {
    public Cache(int maxSize) {
        super(maxSize);
    }
}
