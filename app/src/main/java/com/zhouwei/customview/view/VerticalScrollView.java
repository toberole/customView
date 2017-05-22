package com.zhouwei.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/*
垂直的跑马灯
通过继承ScrollView来写这个控件的，为什么选择它，因为ScrollView有一个smoothSrollTo()方法，可以使内部的布局滑动到一个特定的位置，从而实现图片里面的滑动效果。有了这个想法以后，我们要知道ScrollView里面一般只能有一个子控件，显然我们需要一个LinearLayout，然后根据数据的条数，主动渲染行布局，将生成的View逐个添加入LinearLayout就可以了。

可是还有一个问题，怎么实现无限的自动滚动呢？一开始我思路是创建一个子线程，里面有一个死循环，每隔一定时间，就调用smoothSrollTo()去移动LinearLayout的位置，这样就实现了无限循环。(注意你不能再UI线程里面写死循环啊，所以只能另起一个子线程了)。

后来觉得这样写会造成内存泄露，因为这个线程没有合适的终止条件，也就说我们跳转到另外一个Activity以后，这个线程也不会结束，这样的话内存就无法释放。

解决办法是，通过handler来实现死循环，我们知道调用handler的sendEmptyMessageDelayed()方法，可以延长一段时间以后发送一个信息。

然后在handleMessage里面会接收到这个信息，如果我们在handleMessage的最后，又再一次调用sendEmptyMessageDelayed()方法，这样就可以不断循环了。
 */
public class VerticalScrollView extends ScrollView {

//    private LinearLayout mLinearLayout;
//    private int curIndex;
//    private int dieection;
//    private int intervalTime;
//    private final static int SCROLL_WHAT = 110;
//    private CustomDurationScroller scroller = null;
//    private double mScrollFactor = 1d;
//
//    private ScrollHandler scrollHandler;
//
//
    public VerticalScrollView(Context context) {
        this(context, null);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //init(context);
    }
//
//    private void init(Context context) {
//        mLinearLayout = new LinearLayout(context);
//        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
//        addView(mLinearLayout, new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
//        scrollHandler = new ScrollHandler();
//        setViewPagerScroller();
//    }
//
//    /**
//     * 开启滚动，必须主动调用
//     */
//    public void startAutoScroll() {
//        scrollHandler.sendEmptyMessage(SCROLL_WHAT);
//    }
//
//    private void setViewPagerScroller() {
//
//    }
//
//    private ScrollHandler handler = new ScrollHandler();
//
//    private class ScrollHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case SCROLL_WHAT:// 滚动
//                    SetItemImeediate();
//                    setCurrentItem(curIndex + direction);
//                    scrollHandler.removeMessages(SCROLL_WHAT);
//                    scrollHandler.sendEmptyMessageDelayed(SCROLL_WHAT, intervalTime);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    private void SetItemImeediate() {
//        if (direction == 1 && curIndex == mTabAdapter.getSize() - 1) {
//            //如果向下滚动并且滚动到最后一条，瞬间跳跃到第二条，实现循环效果
//            View v = mLinearLayout.getChildAt(1);
//            scrollTo((int) v.getX(), (int) v.getY());
//            curIndex = 1;
//        } else if (direction == -1 && curIndex == 0) {
//            //如果向上滚动并且滚动到第一条，瞬间跳跃到倒数第二条，实现循环效果
//            View v = mLinearLayout.getChildAt(mTabAdapter.getSize() - 2);
//            scrollTo((int) v.getX(), (int) v.getY());
//            curIndex = mTabAdapter.getSize() - 2;
//        }
//    }
//
//    public void setCurrentItem(int index) {
//        View v = mLinearLayout.getChildAt(index);
//        smoothScrollTo((int) v.getX(), (int) v.getY());
//        curIndex = index;
//    }
//
//    public abstract class VerticalScrollViewAdapter {
//        final int mResId;
//        private ArrayList<String> textArray = new ArrayList<String>();
//        private ArrayList<String> iconArray = new ArrayList<String>();
//
//        public VerticalScrollViewAdapter(int resId, String[] texts) {
//            mResId = resId;
//            setTexts(texts);
//        }
//
//        public VerticalScrollViewAdapter(int resId, String[] texts, String[] icons) {
//            mResId = resId;
//            setIconTexts(texts, icons);
//        }
//
//        /**
//         * 返回index对应文字
//         */
//        public String getText(int index) {
//            return textArray.get(index);
//        }
//
//        /**
//         * 返回index对应icon地址
//         */
//        public String getIcon(int index) {
//            return iconArray.isEmpty() ? "" : iconArray.get(index);
//        }
//
//        /**
//         * 设置文字，不设置图标
//         */
//        private void setTexts(String[] texts) {
//            setIconTexts(texts, null);
//        }
//
//        /**
//         * 设置文字和图标
//         * 要求texts必须不为空
//         */
//        private void setIconTexts(String[] texts, String[] icons) {
//            if (texts == null || texts.length <= 0) return;
//            if (icons != null) {
//                if (icons.length <= 0 || texts.length != icons.length) return;
//            }
//
//            textArray.clear();
//            textArray.add(texts[texts.length - 1]);
//            textArray.addAll(Arrays.asList(texts));
//            textArray.add(texts[0]);
//
//            iconArray.clear();
//            if (icons != null && icons.length != 0) {
//                iconArray.add(icons[icons.length - 1]);
//                iconArray.addAll(Arrays.asList(icons));
//                iconArray.add(icons[0]);
//            }
//        }
//
//        /**
//         * 返回数据条目总数
//         */
//        public int getSize() {
//            return textArray.size();
//        }
//
//        /**
//         * 必须继承该方法，在该方法内可以自定义布局文字，图标等
//         */
//        public abstract void getView(View v, String text, String icon);
//    }
//
//       try {
//        Field scrollerField = ScrollView.class.getDeclaredField("mScroller");
//        scrollerField.setAccessible(true);
//        scroller = new CustomDurationScroller(getContext());
//        scroller.setScrollDurationFactor(mScrollFactor);
//        scrollerField.set(this, scroller);
//    } catch (Exception e) {
//        e.printStackTrace();
//    }

}
