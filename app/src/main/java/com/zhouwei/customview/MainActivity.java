package com.zhouwei.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zhouwei.customview.util.AESCipher;

import static com.zhouwei.customview.util.AESCipher.aesDecryptString;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btntestParallaxListview;
    private Button btntestaes;
    private Button btntestSpannableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btntestSpannableString = (Button) findViewById(R.id.btn_test_SpannableString);
        this.btntestaes = (Button) findViewById(R.id.btn_test_aes);
        this.btntestParallaxListview = (Button) findViewById(R.id.btn_test_ParallaxListview);

        btntestParallaxListview.setOnClickListener(this);
        btntestaes.setOnClickListener(this);
        btntestSpannableString.setOnClickListener(this);
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

        }
    }
}
