# customView

# 自定义view

## 侧滑删除

viewdraghelper 谷歌推出的解决控件拖拽的问题

touchslop 敏感度 值越小 越敏感



## 快速索引


## 侧拉删除


## 视差效果

## 粘性控件

事件处理


测试一下啊

测试一下A



<pre>

    禁止用户输入Emoji

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







et_emoji.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!resetText) {
                    Log.i("AAAA", "Emoji: " + s + " count: " + count);
                    if (count >= 2) {//表情符号的字符长度最小为3
                        Log.i("AAAA", "输入了Emoji");
                        //提取输入的长度大于3的文本
                        CharSequence input = s.subSequence(cursorPos, cursorPos + count);
                        //正则匹配是否是表情符号
                        Matcher matcher = pattern.matcher(input.toString());
                        if (!matcher.matches()) {
                            resetText = true;
                            //是表情符号就将文本还原为输入表情符号之前的内容
                            et_emoji.setText(tmp);
                            et_emoji.invalidate();
                            Toast.makeText(MainActivity.this,
                                    "不支持表情输入", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    resetText = false;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!resetText) {
                    cursorPos = et_emoji.getSelectionEnd();
                    tmp = s.toString();//这里用s.toString()而不直接用s是因为如果用s，那么，tmp和s在内存中指向的是同一个地址，s改变了，tmp也就改变了，那么表情过滤就失败了
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



</pre>



