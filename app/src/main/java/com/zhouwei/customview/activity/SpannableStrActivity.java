package com.zhouwei.customview.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.zhouwei.customview.R;

public class SpannableStrActivity extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_str);

        this.tv5 = (TextView) findViewById(R.id.tv5);
        this.tv4 = (TextView) findViewById(R.id.tv4);
        this.tv3 = (TextView) findViewById(R.id.tv3);
        this.tv2 = (TextView) findViewById(R.id.tv2);
        this.tv1 = (TextView) findViewById(R.id.tv1);

        SpannableString tvsStr = new SpannableString("我是第一个富文本啊  设置前景色");
        tvsStr.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 3, 7, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv1.setText(tvsStr);

        SpannableString tv2Str = new SpannableString("我是第二个富文本  设置背景色");
        tv2Str.setSpan(new BackgroundColorSpan(Color.parseColor("#00FF00")), 3, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv2.setText(tv2Str);

        SpannableString tv3Str = new SpannableString("我是设置相对大小的文本 设置相对大小");
        tv3Str.setSpan(new RelativeSizeSpan(1.5f), 3, 6, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv3.setText(tv3Str);

        SpannableString tv4Str = new SpannableString("我是设置删除线的富文本 设置删除线");
        tv4Str.setSpan(new StrikethroughSpan(), 3, 6, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv4.setText(tv4Str);

        SpannableString tv5Str = new SpannableString("我是设置下划线的富文本 设置下划线");
        tv5Str.setSpan(new UnderlineSpan(),3,6,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv5.setText(tv5Str);



    }
}
