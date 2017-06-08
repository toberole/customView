package com.zhouwei.customview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhouwei.customview.R;
import com.zhouwei.customview.view.MentionEditText;

import java.util.regex.Pattern;

/**
 * hehe
 */

/**
 * wawa
 */
public class MainActivity extends AppCompatActivity {

    private Button btntestParallaxListview;
    private Button btntestaes;
    private Button btntestSpannableString;
    private Button btntestBesselLoadingView;
    private Button btntestHighLightText;
    private android.widget.TextView tvtestHighLightText;
    private TextView tvtestHighLightText1;
    private android.widget.ListView lVtestdata;
    private Button btntestSwiplayout;
    private android.widget.EditText ettesttextchange;
    private MentionEditText mentionEditText;
    private MentionEditText editText;
    private EditText et_emoji;
    /////////////////////////////////////////////
    //匹配非表情符号的正则表达式
    private final String reg = "^([a-z]|[A-Z]|[0-9]|[\u2E80-\u9FFF]){3,}|@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?|[wap.]{4}|[www.]{4}|[blog.]{5}|[bbs.]{4}|[.com]{4}|[.cn]{3}|[.net]{4}|[.org]{4}|[http://]{7}|[ftp://]{6}$";

    private Pattern pattern = Pattern.compile(reg);
    //输入表情前的光标位置
    private int cursorPos;
    //输入表情前EditText中的文本
    private String tmp;
    //是否重置了EditText的内容
    private boolean resetText;
    ///////////////////////////////////////////////

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            int id = data.getIntExtra("id", 0);
            String name = data.getStringExtra("name");

            // mentionEditText.setText(getText() + name);
            if (TextUtils.isEmpty(name)) {
                name = "user====";
            }
            editText.mentionUser(id, name);

            // Log.i("AAAA", editText.convertMetionString());
        }

    }

    public void log(View v) {
        Log.i("AAAA", editText.convertMetionString());
    }

    private Handler handler;
    private HandlerThread handlerThread;

    private class ThreadHanlder implements Runnable {

        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Log.i("AAAA", "handler thread 测试");
                    // Looper.getMainLooper() == Looper.myLooper()
                    // handler.sendMessageDelayed(Message.obtain(), 2000);
                }
            };
            Looper.loop();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_emoji);





































//        EditText et_test_emoji = (EditText) findViewById(R.id.et_test_emoji);
//
//        et_test_emoji.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(!TextUtils.isEmpty(s)){
//                    if(StringUtil.isEmojiCharacter(s.charAt(0))){
//                        Log.i("AAAA","是Emoji字符");
//                    }else {
//                        Log.i("AAAA","不是Emoji字符");
//                    }
//                }
//
//            }
//        });


//        et_emoji = (EditText) findViewById(R.id.et_emoji);
//        et_emoji.addTextChangedListener(new TextWatcher() {
//
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!resetText) {
//                    Log.i("AAAA", "Emoji: " + s + " count: " + count);
//                    if (count >= 2) {//表情符号的字符长度最小为3
//                        Log.i("AAAA", "输入了Emoji");
//                        //提取输入的长度大于3的文本
//                        CharSequence input = s.subSequence(cursorPos, cursorPos + count);
//                        //正则匹配是否是表情符号
//                        Matcher matcher = pattern.matcher(input.toString());
//                        if (!matcher.matches()) {
//                            resetText = true;
//                            //是表情符号就将文本还原为输入表情符号之前的内容
//                            et_emoji.setText(tmp);
//                            et_emoji.invalidate();
//                            Toast.makeText(MainActivity.this,
//                                    "不支持表情输入", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                } else {
//                    resetText = false;
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if (!resetText) {
//                    cursorPos = et_emoji.getSelectionEnd();
//                    tmp = s.toString();//这里用s.toString()而不直接用s是因为如果用s，那么，tmp和s在内存中指向的是同一个地址，s改变了，tmp也就改变了，那么表情过滤就失败了
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });

        Looper.myLooper();

        new Thread(new ThreadHanlder()).start();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessage(0);
        Log.i("AAAA", "DDDDDDDDDDD 测试");

//        EditText test_et = (EditText) findViewById(R.id.test_et);
//        test_et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.i("AAAA", "beforeTextChanged start: " + start + " count: " + count + " after: " + after);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.i("AAAA", "onTextChanged start: " + start + " before: " + before + " count: " + count);
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Log.i("AAAA", "afterTextChanged " + s.toString());
//            }
//        });
//
//        editText = ((MentionEditText) findViewById(R.id.me_test_et));
//        /// List<String> mentionList = editText.getMentionList(true); //get a list of mention string
//        editText.setMentionTextColor(Color.RED); //optional, set highlight color of mention string
//        //editText.setPattern("@[\\u4e00-\\u9fa5\\w\\-]+"); //optional, set regularExpression
//        editText.setOnMentionInputListener(new MentionEditText.OnMentionInputListener() {
//            @Override
//            public void onMentionCharacterInput() {
//                //call when '@' character is inserted into EditText
//                MainActivity.this.startActivityForResult(new Intent(MainActivity.this, AtActivity.class), 0);
//            }
//        });

//
//        Button btn_test_pop = (Button) findViewById(R.id.btn_test_pop);
//        btn_test_pop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, PopActivity.class);
//                MainActivity.this.startActivity(intent);
//            }
//        });
//        Log.i("AAAA", TimeUtil.secondsToChatTime((int) (SystemClock.currentThreadTimeMillis() / 1000 / 1000)));
//        // Log.i("AAAA", "width: " + getResources().getDimension(R.dimen.width));
//        // MediaStore.Images.Media.DATA
//        View v = new View(this);
//
//        // throws new android.os.DeadObjectException("");
//        // FloatEvaluator
//        this.ettesttextchange = (EditText) findViewById(R.id.et_test_textchange);
//        SpannableString spannableString = new SpannableString("测试一把 ");
//        BackgroundColorSpan span = new BackgroundColorSpan(Color.YELLOW);
//        spannableString.setSpan(span, 0, spannableString.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ettesttextchange.setText(spannableString);
//
//        // ettesttextchange.setSelection(spannableString.length() + 1);
//        Log.i("AAAA", ettesttextchange.getText().toString().length() + "");
//
//        this.btntestSwiplayout = (Button) findViewById(R.id.btn_test_Swiplayout);
//        this.lVtestdata = (ListView) findViewById(R.id.lV_testdata);
//        this.tvtestHighLightText1 = (TextView) findViewById(R.id.tv_test_HighLightText1);
//        this.tvtestHighLightText = (TextView) findViewById(R.id.tv_test_HighLightText);
//
//        this.btntestHighLightText = (Button) findViewById(R.id.btn_test_HighLightText);
//        this.btntestBesselLoadingView = (Button) findViewById(R.id.btn_test_BesselLoadingView);
//        this.btntestSpannableString = (Button) findViewById(R.id.btn_test_SpannableString);
//        this.btntestaes = (Button) findViewById(R.id.btn_test_aes);
//        this.btntestParallaxListview = (Button) findViewById(R.id.btn_test_ParallaxListview);
//
//        btntestHighLightText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("AAAA", "####################");
//            }
//        });
//        btntestParallaxListview.setOnClickListener(this);
//        btntestaes.setOnClickListener(this);
//        btntestSpannableString.setOnClickListener(this);
//        btntestBesselLoadingView.setOnClickListener(this);
//        btntestSwiplayout.setOnClickListener(this);
//
//        getWindow().getDecorView().post(new Runnable() {
//            @Override
//            public void run() {
//                HookViewClickUtil.hookView(btntestHighLightText);
//            }
//        });
//
//        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//
//            }
//        });
//
//        // tvtestHighLightText.animate().translationX(200).translationY(500).start();
//
//        ettesttextchange.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.i("AAAA", "===beforeTextChanged===");
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.i("AAAA", "===onTextChanged===");
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Log.i("AAAA", "===afterTextChanged===");
//            }
//        });
//
////        Dialog dialog = new Dialog(MainActivity.this);
////        TextView tgv = new TextView(MainActivity.this);
////        tgv.setText("huhuhuhu");
////        dialog.setContentView(tgv);
////        dialog.setCanceledOnTouchOutside(false);
////        dialog.show();
//
//        MyAdapter adapter = new MyAdapter(MainActivity.this, TestData.datas);
//        lVtestdata.setAdapter(adapter);
//
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        Intent intent = null;
//        switch (v.getId()) {
//            case R.id.btn_test_Swiplayout:
////                intent = new Intent(MainActivity.this, SwipeLayoutActivity.class);
////                startActivity(intent);
//                Log.i("AAAA", "**************");
//                tvtestHighLightText.animate().translationY(500).translationX(300).setDuration(3000).start();
//                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);
//                alphaAnimation.setFillAfter(true);
//                break;
//            case R.id.btn_test_ParallaxListview:
//                intent = new Intent(MainActivity.this, ParallaxListviewActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.btn_test_aes:
////                StringBuilder sb = new StringBuilder();
////                sb.append(Constant.QRCODEHEAD).append("|").append(Constant.USERQRCODE).append("|").append("123").append("|").append("1232");
////
////                Log.i("AAAA", "origin text: " + sb.toString());
////
////                String enStrig = AESCrypto.encrypt(Constant.QRCODESALT, sb.toString());
////                Log.i("AAAA", "enStrig text: " + enStrig);
////
////                String deStrig = AESCrypto.decrypt(Constant.QRCODESALT, sb.toString());
////                Log.i("AAAA", "deStrig text: " + deStrig);
////                break;
//
//                // Base64.decode()
//
//                try {
////                    AES mAes = AES.getInstance(Constant.QRCODESALT);
////                    // StringBuilder sb = new StringBuilder();
////                    //sb.append(Constant.QRCODEHEAD).append("|").append(Constant.USERQRCODE).append("|").append("123").append("|").append("1232");
////                    String mString = "9%0#*&8*(6w@&ch*]6w*&$@[ch*a|user|1114|108";
////
////                    byte[] mBytes = mString.getBytes("UTF8");
////                    String enString = mAes.encrypt(mBytes);
////                    Log.i("AAAA", "enString:"+enString);
////                    String deString = mAes.decrypt(enString);
////                    Log.i("AAAA", deString);
//
//
//                    String bs = AESCipher.aesEncryptString("xiaohon", "16BytesLengthKey");
//                    Log.i("AAAA", "enString:" + bs);
//                    String res = aesDecryptString(bs, "16BytesLengthKey");
//                    Log.i("AAAA", "dnString:" + res);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            case R.id.btn_test_SpannableString:
//                intent = new Intent(MainActivity.this, SpannableStrActivity.class);
//                MainActivity.this.startActivity(intent);
//                break;
//            case R.id.btn_test_BesselLoadingView:
//                intent = new Intent(MainActivity.this, BesselLoadingViewActivity.class);
//                MainActivity.this.startActivity(intent);
//                break;
//
//            case R.id.btn_test_HighLightText:
////                String str[] = new String[]{"hushfuihui哈哈哒哈哈fjksdj哈击碎ush安吉哦哈", "uishushi哈哈哒ush击碎安吉哦哈"};
////                String t = "ush";
////                List<SpannableStringBuilder> texts = HighLightTextUtil.batchHighLightText(Arrays.asList(str), t, Color.RED);
////                tvtestHighLightText1.setText(texts.get(0).toString());
////
////                tvtestHighLightText.setText(texts.get(1));
//
////                String hanzi = "上传数据出错";
////                String pinyin = PinyinHelper.hanZiToPinyin(hanzi);
////                Log.i("AAAA", hanzi + "-------------->" + pinyin);
////
////                String fl = HighLightTextUtil.getHanZiPinYinFirstLetter(hanzi);
////                Log.i("AAAA", hanzi + "-------------->" + fl);
//                // String hanzi = "abababababababababababababababababababababababababababababababababababababababab哈哈abababababababababababababababababababababababababababababababababababababababab";
//                String hanzi = "ab哈哈aba";
//
//                // Log.i("AAAA", "measure: " + tvtestHighLightText1.getPaint().measureText("btn_test_1ssss") + "");
//                //int maxw = tvtestHighLightText.getWidth();
//
//                int maxw = tvtestHighLightText.getWidth();
//                int leftpadding = tvtestHighLightText.getPaddingLeft();
//                int rightPadding = tvtestHighLightText.getPaddingRight();
//
//                Log.i("AAAA", "maxw:" + maxw);
//                tvtestHighLightText.setTextColor(Color.BLACK);
//                tvtestHighLightText.setTextSize(40);
//                HighLightTextUtil.highLightText(hanzi, "哈哈", Color.RED, maxw, tvtestHighLightText);
//
////                int w = tvtestHighLightText1.getWidth();
////                int leftpadding = tvtestHighLightText1.getPaddingLeft();
////                int rightPadding = tvtestHighLightText1.getPaddingRight();
////                Log.i("AAAA", "width:" + w);
////                Log.i("AAAA", "leftpadding:" + leftpadding);
////                Log.i("AAAA", "rightPadding:" + rightPadding);
//                break;
//
//
//        }
//    }
    }
}
