package com.zhouwei.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zhouwei.customview.R;

public class TestUseFul extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_use_full);

        Button btn_test = (Button) findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == false) {
                    flag = true;
                    Log.i("AAAA", "**************************");
                    v.callOnClick();
                } else {
                    if (count == 1) {
                        Log.i("AAAA", "====================");
                        count++;
                    }
                }
            }
        });
    }

    private boolean flag = false;
    private int count = 1;
}
