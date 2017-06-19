package com.zhouwei.customview.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhouwei.customview.R;
import com.zhouwei.customview.view.SuperTextView;

public class TestSuperTextView extends AppCompatActivity {
    private LinearLayout ll_container;
    private SuperTextView stv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.layout.test_title);
        setContentView(R.layout.activity_test_super_text_view);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        stv = (SuperTextView) findViewById(R.id.stv);
        String[] texts = getResources().getStringArray(R.array.texts);
        if (texts != null) {
            for (int i = 0; i < texts.length; i++) {
                TextView textView = new TextView(TestSuperTextView.this);
                textView.setText(texts[i] + " ");

                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(lp);
                textView.setTextColor(Color.RED);
                textView.setTextSize(25);
                stv.addView(textView);
            }
        }
    }
}
