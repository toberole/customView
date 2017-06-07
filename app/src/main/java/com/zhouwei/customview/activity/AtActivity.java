package com.zhouwei.customview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zhouwei.customview.R;

import java.util.ArrayList;
import java.util.List;

public class AtActivity extends AppCompatActivity {
    private class User {
        public int id;
        public String userName;
    }

    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at);

        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.id = i;
            user.userName = "user_" + i;
            users.add(user);
        }


        ListView lv_users = (ListView) findViewById(R.id.lv_users);
        MyAdapter adapter = new MyAdapter();
        lv_users.setAdapter(adapter);

        lv_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = users.get(position);
                Intent intent = new Intent();
                intent.putExtra("name", user.userName);
                intent.putExtra("id", id);
                setResult(RESULT_OK, intent);
                AtActivity.this.finish();
            }
        });

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public Object getItem(int position) {
            return users.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = View.inflate(AtActivity.this, R.layout.lv_item, null);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_test);
                convertView.setTag(holder);
            }

            holder = (ViewHolder) convertView.getTag();
            holder.tv_name.setText(users.get(position).userName);
            return convertView;
        }

        private class ViewHolder {
            public TextView tv_name;
        }
    }
}
