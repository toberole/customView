package com.zhouwei.customview.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhouwei.customview.test.DataEngine;

import java.util.List;

/**
 * Created by zhouwei on 2017/6/8.
 */

public class DefaultAdapter extends BaseAdapter {
    List<DataEngine.Data> datas;

    public DefaultAdapter(List<DataEngine.Data> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
