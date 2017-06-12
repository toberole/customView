package com.zhouwei.customview.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.zhouwei.customview.test.DataEngine;

import java.util.List;

/**
 * Created by zhouwei on 2017/6/8.
 */

public class SwipeLayoutTestAdapter extends DefaultAdapter {
    public SwipeLayoutTestAdapter(List<DataEngine.Data> datas) {
        super(datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

        }


        return super.getView(position, convertView, parent);
    }
}
