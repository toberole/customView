package com.zhouwei.customview.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zhouwei.customview.R;
import com.zhouwei.customview.adapter.MyAdapter;
import com.zhouwei.customview.test.TestData;
import com.zhouwei.customview.util.AESCipher;
import com.zhouwei.customview.util.HighLightTextUtil;

import static com.zhouwei.customview.util.AESCipher.aesDecryptString;

/**
 * wawa
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btntestParallaxListview;
    private Button btntestaes;
    private Button btntestSpannableString;
    private Button btntestBesselLoadingView;
    private Button btntestHighLightText;
    private android.widget.TextView tvtestHighLightText;
    private TextView tvtestHighLightText1;
    private android.widget.ListView lVtestdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.lVtestdata = (ListView) findViewById(R.id.lV_testdata);
        this.tvtestHighLightText1 = (TextView) findViewById(R.id.tv_test_HighLightText1);
        this.tvtestHighLightText = (TextView) findViewById(R.id.tv_test_HighLightText);

        this.btntestHighLightText = (Button) findViewById(R.id.btn_test_HighLightText);
        this.btntestBesselLoadingView = (Button) findViewById(R.id.btn_test_BesselLoadingView);
        this.btntestSpannableString = (Button) findViewById(R.id.btn_test_SpannableString);
        this.btntestaes = (Button) findViewById(R.id.btn_test_aes);
        this.btntestParallaxListview = (Button) findViewById(R.id.btn_test_ParallaxListview);

        btntestHighLightText.setOnClickListener(this);
        btntestParallaxListview.setOnClickListener(this);
        btntestaes.setOnClickListener(this);
        btntestSpannableString.setOnClickListener(this);
        btntestBesselLoadingView.setOnClickListener(this);

        MyAdapter adapter = new MyAdapter(MainActivity.this, TestData.datas);
        lVtestdata.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_test_ParallaxListview:
                intent = new Intent(MainActivity.this, ParallaxListviewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_test_aes:
//                StringBuilder sb = new StringBuilder();
//                sb.append(Constant.QRCODEHEAD).append("|").append(Constant.USERQRCODE).append("|").append("123").append("|").append("1232");
//
//                Log.i("AAAA", "origin text: " + sb.toString());
//
//                String enStrig = AESCrypto.encrypt(Constant.QRCODESALT, sb.toString());
//                Log.i("AAAA", "enStrig text: " + enStrig);
//
//                String deStrig = AESCrypto.decrypt(Constant.QRCODESALT, sb.toString());
//                Log.i("AAAA", "deStrig text: " + deStrig);
//                break;

                // Base64.decode()

                try {
//                    AES mAes = AES.getInstance(Constant.QRCODESALT);
//                    // StringBuilder sb = new StringBuilder();
//                    //sb.append(Constant.QRCODEHEAD).append("|").append(Constant.USERQRCODE).append("|").append("123").append("|").append("1232");
//                    String mString = "9%0#*&8*(6w@&ch*]6w*&$@[ch*a|user|1114|108";
//
//                    byte[] mBytes = mString.getBytes("UTF8");
//                    String enString = mAes.encrypt(mBytes);
//                    Log.i("AAAA", "enString:"+enString);
//                    String deString = mAes.decrypt(enString);
//                    Log.i("AAAA", deString);


                    String bs = AESCipher.aesEncryptString("xiaohon", "16BytesLengthKey");
                    Log.i("AAAA", "enString:" + bs);
                    String res = aesDecryptString(bs, "16BytesLengthKey");
                    Log.i("AAAA", "dnString:" + res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case R.id.btn_test_SpannableString:
                intent = new Intent(MainActivity.this, SpannableStrActivity.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.btn_test_BesselLoadingView:
                intent = new Intent(MainActivity.this, BesselLoadingViewActivity.class);
                MainActivity.this.startActivity(intent);
                break;

            case R.id.btn_test_HighLightText:
//                String str[] = new String[]{"hushfuihui哈哈哒哈哈fjksdj哈击碎ush安吉哦哈", "uishushi哈哈哒ush击碎安吉哦哈"};
//                String t = "ush";
//                List<SpannableStringBuilder> texts = HighLightTextUtil.batchHighLightText(Arrays.asList(str), t, Color.RED);
//                tvtestHighLightText1.setText(texts.get(0).toString());
//
//                tvtestHighLightText.setText(texts.get(1));

//                String hanzi = "上传数据出错";
//                String pinyin = PinyinHelper.hanZiToPinyin(hanzi);
//                Log.i("AAAA", hanzi + "-------------->" + pinyin);
//
//                String fl = HighLightTextUtil.getHanZiPinYinFirstLetter(hanzi);
//                Log.i("AAAA", hanzi + "-------------->" + fl);
                // String hanzi = "abababababababababababababababababababababababababababababababababababababababab哈哈abababababababababababababababababababababababababababababababababababababababab";
                String hanzi = "ab哈哈aba";

                // Log.i("AAAA", "measure: " + tvtestHighLightText1.getPaint().measureText("btn_test_1ssss") + "");
                //int maxw = tvtestHighLightText.getWidth();

                int maxw = tvtestHighLightText.getWidth();
                int leftpadding = tvtestHighLightText.getPaddingLeft();
                int rightPadding = tvtestHighLightText.getPaddingRight();

                Log.i("AAAA", "maxw:" + maxw);
                tvtestHighLightText.setTextColor(Color.BLACK);
                tvtestHighLightText.setTextSize(40);
                HighLightTextUtil.highLightText(hanzi, "哈哈", Color.RED, maxw,tvtestHighLightText);

//                int w = tvtestHighLightText1.getWidth();
//                int leftpadding = tvtestHighLightText1.getPaddingLeft();
//                int rightPadding = tvtestHighLightText1.getPaddingRight();
//                Log.i("AAAA", "width:" + w);
//                Log.i("AAAA", "leftpadding:" + leftpadding);
//                Log.i("AAAA", "rightPadding:" + rightPadding);
                break;


        }
    }
}
