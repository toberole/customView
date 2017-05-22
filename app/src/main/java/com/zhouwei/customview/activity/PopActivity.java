package com.zhouwei.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhouwei.customview.R;

public class PopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        final Button btn_test = (Button) findViewById(R.id.btn_test);
        Button btn_shouPop = (Button) findViewById(R.id.btn_shouPop);
        btn_shouPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(PopActivity.this);
                    view = new TextView(PopActivity.this);
                    view.setText("N条未读信息");
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (popupWindow.isShowing()) {
                                popupWindow.dismiss();
                            }
                        }
                    });
                    popupWindow.setContentView(view);
                    popupWindow.setAnimationStyle(R.style.popwin_anim);
                }
                popupWindow.showAtLocation(view, Gravity.RIGHT | Gravity.BOTTOM, 0, btn_test.getHeight());
            }
        });
    }

    TextView view;
    PopupWindow popupWindow;
}
