package com.zhouwei.customview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.zhouwei.customview.R;
import com.zhouwei.customview.view.DragLayout;
import com.zhouwei.customview.view.ParallaxListview;

/**
 * Created by zhouwei on 2017/4/16.
 */

public class ParallaxListviewActivity extends Activity {
    private String datas[] = new String[20];
    private DragLayout dragLayout;
    private ParallaxListview lvParallaxListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_parallaxlistview_listview);

        for (int i = 0; i < datas.length; i++) {
            datas[i] = "test" + i;
        }

        this.lvParallaxListview = (ParallaxListview) findViewById(R.id.lv_ParallaxListview);

        final View view = View.inflate(ParallaxListviewActivity.this, R.layout.view_header, null);
        final ImageView iv_head = (ImageView) view.findViewById(R.id.iv_head);

        //view数绘制结束的时候回调
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                lvParallaxListview.setParallaxImageView(iv_head);
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        lvParallaxListview.addHeaderView(view);
        lvParallaxListview.setAdapter(new ArrayAdapter<String>(ParallaxListviewActivity.this, android.R.layout.simple_list_item_1, datas));
    }
}
