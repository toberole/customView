package com.zhouwei.customview;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SpannableStringActivity extends AppCompatActivity {
    private TextView tvclickablespannablestring;
    private TextView tvhtmlspannablestring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_string);
        this.tvhtmlspannablestring = (TextView) findViewById(R.id.tv_html_spannable_string);
        this.tvclickablespannablestring = (TextView) findViewById(R.id.tv_clickable_spannable_string);

        tvclickablespannablestring.setTextSize(20);
        tvclickablespannablestring.setText(getClickedSpanningString());
        tvclickablespannablestring.setMovementMethod(LinkMovementMethod.getInstance());

        tvhtmlspannablestring.setText(transferBiaoQing("hello android",R.mipmap.ic_launcher));
    }

    public SpannableString getClickedSpanningString() {
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SpannableStringActivity.this, "Click Success",
                        Toast.LENGTH_SHORT).show();
            }
        };

        SpannableString spanableInfo = new SpannableString(
                "This is a test, Click Me");
        int start = 16;
        int end = spanableInfo.length();
        spanableInfo.setSpan(new Clickable(l), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanableInfo;
    }

    /**
     * 内部类，用于截获点击富文本后的事件
     *
     * @author pro
     */
    class Clickable extends ClickableSpan implements View.OnClickListener {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.RED);
            ds.setUnderlineText(false);    //去除超链接的下划线
        }
    }

    /**
     * 将富文本转成CharSequence
     *
     * @param commonStr 普通内容
     * @param bqId      表情图片
     * @return
     */
    public CharSequence transferBiaoQing(String commonStr, int bqId) {
        return Html.fromHtml(commonStr + "<img src=\"" + bqId + "\">", imageGetter, null);
    }

    private Html.ImageGetter imageGetter = new Html.ImageGetter() {

        @Override
        public Drawable getDrawable(String source) {
            int id = Integer.parseInt(source);
            // 根据id从资源文件中获取图片对象
            Drawable d = SpannableStringActivity.this.getApplicationContext().getResources().getDrawable(id);
            // 以此作为标志位，方便外部取出对应的资源id
            d.setState(new int[]{id});
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            return d;
        }
    };
}

