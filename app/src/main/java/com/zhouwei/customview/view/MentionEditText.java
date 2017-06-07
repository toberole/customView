/*
 * Copyright 2016 Andy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhouwei.customview.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


/**
 * MentionEditText adds some useful features for mention string(@xxxx), such as highlight,
 * intelligent deletion, intelligent selection and '@' input detection, etc.
 *
 * @author Andy
 */
public class MentionEditText extends AppCompatEditText {
    private final String mMentionTextFormat = "[Mention:%s, %s]";
    private Runnable mAction;

    private int mMentionTextColor;

    private boolean mIsSelected;
    private Range mLastSelectedRange;
    private ArrayList<Range> mRangeArrayList;

    private OnMentionInputListener mOnMentionInputListener;

    public MentionEditText(Context context) {
        this(context,null);
    }

    public MentionEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MentionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new HackInputConnection(super.onCreateInputConnection(outAttrs), true, this);
    }

    @Override
    public void setText(final CharSequence text, TextView.BufferType type) {
        super.setText(text, type);
        //hack, put the cursor at the end of text after calling setText() method
        if (mAction == null) {
            mAction = new Runnable() {
                @Override
                public void run() {
                    setSelection(getText().length());
                }
            };
        }
        post(mAction);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        //avoid infinite recursion after calling setSelection()
        if (mLastSelectedRange != null && mLastSelectedRange.isEqual(selStart, selEnd)) {
            return;
        }

        //if user cancel a selection of mention string, reset the state of 'mIsSelected'
        Range closestRange = getRangeOfClosestMentionString(selStart, selEnd);
        if (closestRange != null && closestRange.to == selEnd) {
            mIsSelected = false;
        }

        Range nearbyRange = getRangeOfNearbyMentionString(selStart, selEnd);
        //if there is no mention string nearby the cursor, just skip
        if (nearbyRange == null) {
            return;
        }

        //forbid cursor located in the mention string.
        if (selStart == selEnd) {
            setSelection(nearbyRange.getAnchorPosition(selStart));
        } else {
            if (selEnd < nearbyRange.to) {
                setSelection(selStart, nearbyRange.to);
            }
            if (selStart > nearbyRange.from) {
                setSelection(nearbyRange.from, selEnd);
            }
        }
    }

    /**
     * set highlight color of mention string
     *
     * @param color value from 'getResources().getColor()' or 'Color.parseColor()' etc.
     */
    public void setMentionTextColor(int color) {
        mMentionTextColor = color;
    }

    /**
     * 插入mention string
     * 在调用该方法前，请先插入一个字符（如'@'），之后插入的name将会和该字符组成一个整体
     * @param uid 用户id
     * @param name 用户名字
     */
    public void mentionUser(int uid, String name) {
        Editable editable = getText();
        int start = getSelectionStart();
        int end = start + name.length();
        editable.insert(start, name);
        editable.setSpan(new ForegroundColorSpan(mMentionTextColor), start - 1, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mRangeArrayList.add(new Range(uid, name, start - 1, end));
    }

    /**
     * 将所有mention string以指定格式输出
     * @return 以指定格式输出的字符串
     */
    public String convertMetionString() {
        String text = getText().toString();
        if (mRangeArrayList.isEmpty()) {
            return text;
        }

        StringBuilder builder = new StringBuilder("");
        int lastRangeTo = 0;
        Collections.sort(mRangeArrayList);

        for (Range range : mRangeArrayList) {
            String newChar = String.format(mMentionTextFormat, range.id, range.name);
            builder.append(text.substring(lastRangeTo, range.from));
            builder.append(newChar);
            lastRangeTo = range.to;
        }

        clear();
        return builder.toString();
    }

    public void clear() {
        mRangeArrayList.clear();
        setText("");
    }

    /**
     * set listener for mention character('@')
     *
     * @param onMentionInputListener MentionEditText.OnMentionInputListener
     */
    public void setOnMentionInputListener(OnMentionInputListener onMentionInputListener) {
        mOnMentionInputListener = onMentionInputListener;
    }

    private void init() {
        mRangeArrayList = new ArrayList<>();
        mMentionTextColor = Color.RED;
        //disable suggestion
        setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        addTextChangedListener(new MentionTextWatcher());
    }

    private Range getRangeOfClosestMentionString(int selStart, int selEnd) {
        if (mRangeArrayList == null) {
            return null;
        }
        for (Range range : mRangeArrayList) {
            if (range.contains(selStart, selEnd)) {
                return range;
            }
        }
        return null;
    }

    private Range getRangeOfNearbyMentionString(int selStart, int selEnd) {
        if (mRangeArrayList == null) {
            return null;
        }
        for (Range range : mRangeArrayList) {
            if (range.isWrappedBy(selStart, selEnd)) {
                return range;
            }
        }
        return null;
    }

    private class MentionTextWatcher implements TextWatcher {
        //若从整串string中间插入字符，需要将插入位置后面的range相应地挪位
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Editable editable = getText();
            //在末尾增加就不需要处理了
            if (start >= editable.length()) {
                return;
            }

            int end = start + count;
            int offset = after - count;

            //清理start 到 start + count之间的span
            //如果range.from = 0，也会被getSpans(0,0,ForegroundColorSpan.class)获取到
            if (start != end && !mRangeArrayList.isEmpty()) {
                ForegroundColorSpan[] spans = editable.getSpans(start, end, ForegroundColorSpan.class);
                for (ForegroundColorSpan span : spans) {
                    editable.removeSpan(span);
                }
            }

            //清理arraylist中上面已经清理掉的range
            //将end之后的span往后挪offset个位置
            Iterator iterator = mRangeArrayList.iterator();
            while (iterator.hasNext()) {
                Range range = (Range) iterator.next();
                if (range.isWrapped(start, end)) {
                    iterator.remove();
                    continue;
                }

                if (range.from >= end) {
                    range.setOffset(offset);
                }
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int index, int i1, int count) {
            if (count == 1 && !TextUtils.isEmpty(charSequence)) {
                char mentionChar = charSequence.toString().charAt(index);
                if ('@' == mentionChar && mOnMentionInputListener != null) {
                    mOnMentionInputListener.onMentionCharacterInput();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    //handle the deletion action for mention string, such as '@test'
    private class HackInputConnection extends InputConnectionWrapper {
        private EditText editText;

        private HackInputConnection(InputConnection target, boolean mutable, MentionEditText editText) {
            super(target, mutable);
            this.editText = editText;
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                int selectionStart = editText.getSelectionStart();
                int selectionEnd = editText.getSelectionEnd();
                Range closestRange = getRangeOfClosestMentionString(selectionStart, selectionEnd);
                if (closestRange == null) {
                    mIsSelected = false;
                    return super.sendKeyEvent(event);
                }
                //if mention string has been selected or the cursor is at the beginning of mention string, just use default action(delete)
                if (mIsSelected || selectionStart == closestRange.from) {
                    mIsSelected = false;
                    return super.sendKeyEvent(event);
                } else {
                    //select the mention string
                    mIsSelected = true;
                    mLastSelectedRange = closestRange;
                    setSelection(closestRange.to, closestRange.from);
                }
                return true;
            }
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            if (beforeLength == 1 && afterLength == 0) {
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                        && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }
            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }

    //helper class to record the position of mention string in EditText
    private class Range implements Comparable{
        int id;
        String name;
        int from;
        int to;

        private Range(int id, String name, int from, int to) {
            this.id = id;
            this.name = name;
            this.from = from;
            this.to = to;
        }

        private boolean isWrapped(int start, int end) {
            return from >= start && to <= end;
        }

        private boolean isWrappedBy(int start, int end) {
            return (start > from && start < to) || (end > from && end < to);
        }

        private boolean contains(int start, int end) {
            return from <= start && to >= end;
        }

        private boolean isEqual(int start, int end) {
            return (from == start && to == end) || (from == end && to == start);
        }

        private int getAnchorPosition(int value) {
            if ((value - from) - (to - value) >= 0) {
                return to;
            } else {
                return from;
            }
        }

        private void setOffset(int offset) {
            from += offset;
            to += offset;
        }

        @Override
        public int compareTo(@NonNull Object o) {
            return from - ((Range)o).from;
        }
    }


    /**
     * Listener for '@' character
     */
    public interface OnMentionInputListener {
        /**
         * call when '@' character is inserted into EditText
         */
        void onMentionCharacterInput();
    }

}
