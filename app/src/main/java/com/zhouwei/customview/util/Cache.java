package com.zhouwei.customview.util;

import android.support.v4.util.LruCache;

/**
 * Created by zhouwei on 2017/4/27.
 */

public class Cache extends LruCache {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public Cache(int maxSize) {
        super(maxSize);
    }


}
