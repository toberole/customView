package com.zhouwei.customview.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 2017/4/27.
 */

public class TestData {
    public static List<String> datas = new ArrayList<>();

    static {
        for (int i = 0; i < 100; i++) {
            datas.add("==============: " + i);
        }
    }
}
