package com.zhouwei.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private com.zhouwei.customview.view.DragLayout dragLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }
}
