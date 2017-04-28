package com.zhouwei.customview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhouwei.customview.R;

import java.util.List;

/**
 * Created by zhouwei on 2017/4/27.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<String> datas;

    private boolean scrollState;

    public void setScrollState(boolean scrollState) {
        this.scrollState = scrollState;
    }

    public MyAdapter(Context context, List datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.lv_item, null);
            holder.tv = (TextView) convertView.findViewById(R.id.tv_test);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        if (!scrollState) {
            holder.tv.setText(datas.get(position));
        } else {
            holder.tv.setText("加载中...");
        }

        return convertView;
    }

    private static class ViewHolder {
        public TextView tv;
    }
}
