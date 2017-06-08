package com.zhouwei.customview.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 2017/4/27.
 */

public class DataEngine {
    public static class Data {
        public String stringData;
        public int intData;

        public Data(String stringData, int intData) {
            this.stringData = stringData;
            this.intData = intData;
        }
    }

    public static List<Data> datas = new ArrayList<>();

    private DataEngine() {
    }

    private static DataEngine instance;


    public static DataEngine getInstance() {
        if (instance == null) {
            synchronized (DataEngine.class) {
                if (instance == null) {
                    instance = new DataEngine();
                    for (int i = 0; i < 100; i++) {
                        datas.add(new Data("Test data " + i, i));
                    }
                }
            }
        }
        return instance;
    }

    public List<Data> getDatas() {
        return datas;
    }

}
