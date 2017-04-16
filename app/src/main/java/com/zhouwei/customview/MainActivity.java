package com.zhouwei.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.zhouwei.customview.view.DragLayout;
import com.zhouwei.customview.view.ParallaxListview;

public class MainActivity extends AppCompatActivity {
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

        final View view = View.inflate(MainActivity.this, R.layout.view_header, null);
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
        /*
        this.dragLayout = (DragLayout) findViewById(R.id.dragLayout);
        //设置drag监听
        dragLayout.setOnDragStatusChangeListener(new DragLayout.OnDragStatusChangeListener() {
            @Override
            public void onClose() {
                Log.i("MainActivity", "onClose");
            }

            @Override
            public void onOpen() {
                Log.i("MainActivity", "onOpen");
            }

            @Override
            public void onDraging(float percent) {
                Log.i("MainActivity", "onDraging");
            }
        });
        */
        lvParallaxListview.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, datas));
    }
}
