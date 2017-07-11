package com.toocms.freeman.digitaltimer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 数字 时钟计时器
 * 注：使用时，请setBaseTime
 *
 * @Author LXS
 * @Data 2017/7/8 10:09
 */

public class DigitalTimer extends LinearLayout {
    private static final float DEFAULT_TEXT_SIZE = 12;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_BG = Color.parseColor("#969696");
    private static final float DEFAULT_TIME_COLON_SIZE = 1;
    private static final float DEFAULT_TEXT_BG_PADDING = 1;
    private TextView h1;
    private TextView h2;
    private TextView hc;
    private TextView m1;
    private TextView m2;
    private TextView mc;
    private TextView s1;
    private TextView s2;
    /**
     * 基准时间
     **/
    private long baseTime;
    /**
     * 改变的时间
     **/
    private long changTime;
    private float textSize;
    private int textColor;
    private int textBgRes;


    /**
     * 改变时间冒号
     */
    private float timeColonPadding;
    private static final int REFRESH_DELAY = 1000;


    /**
     * 时间文字 内边距 左右
     */
    private float textBgPaddingLR;
    /**
     * 时间文字 内边距 上下
     */
    private float textBgPaddingTB;


    /**
     * 用于显示文字的控件的集合
     **/
    private ArrayList<TextView> textViews;
    private final Handler mHandler = new Handler();
    private final Runnable mTimeRefresher = new Runnable() {
        @Override
        public void run() {
            changTime += 1000;
            reSetTime();
            mHandler.postDelayed(this, REFRESH_DELAY);
        }
    };

    public DigitalTimer(Context context) {
        super(context);
        init(context);
    }

    public DigitalTimer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DigitalTimer);
        textSize = a.getDimension(R.styleable.DigitalTimer_textSize, DEFAULT_TEXT_SIZE);
        textColor = a.getColor(R.styleable.DigitalTimer_textColor, DEFAULT_TEXT_COLOR);
        textBgRes = a.getResourceId(R.styleable.DigitalTimer_textBgRes, DEFAULT_TEXT_BG);
        timeColonPadding = a.getDimension(R.styleable.DigitalTimer_timeColonPadding, DEFAULT_TIME_COLON_SIZE);
        textBgPaddingLR = a.getDimension(R.styleable.DigitalTimer_textBgPaddingLR, DEFAULT_TEXT_BG_PADDING);
        textBgPaddingTB = a.getDimension(R.styleable.DigitalTimer_textBgPaddingTB, DEFAULT_TEXT_BG_PADDING);
        a.recycle();
        init(context);
    }


    private void init(Context context) {
        this.setOrientation(LinearLayout.HORIZONTAL);
        textViews = new ArrayList<TextView>();
//        textViews.add(h1 = new TextView(context));
        textViews.add(h2 = new TextView(context));
        textViews.add(hc = new TextView(context));
//        textViews.add(m1 = new TextView(context));
        textViews.add(m2 = new TextView(context));
        textViews.add(mc = new TextView(context));
//        textViews.add(s1 = new TextView(context));
        textViews.add(s2 = new TextView(context));

        //设置view宽高
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0);
        lp.setMargins(2, 0, 2, 0);

//        h1.setLayoutParams(lp);
        h2.setLayoutParams(lp);
//        m1.setLayoutParams(lp);
        m2.setLayoutParams(lp);
//        s1.setLayoutParams(lp);
        s2.setLayoutParams(lp);
        for (TextView view : textViews) {
            view.setText("00");//设置字内容
            view.setPadding((int) textBgPaddingLR, (int) textBgPaddingTB, (int) textBgPaddingLR
                    , (int) textBgPaddingTB);
            view.setBackgroundResource(textBgRes);//设置背景
            view.setTextColor(textColor);//设置字颜色
            view.setTextSize(textSize);
            view.setGravity(Gravity.CENTER);//设置内容居中
            this.addView(view);//添加view
        }
        hc.setText(":");
        hc.setPadding((int) timeColonPadding, 0, (int) timeColonPadding, 0);
        hc.setBackgroundColor(Color.TRANSPARENT);
        mc.setText(":");
        mc.setPadding((int) timeColonPadding, 0, (int) timeColonPadding, 0);
        mc.setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * 设置传入的时间
     *
     * @param baseTime 更新时间
     */
    public void setBaseTime(String baseTime) {
        this.baseTime = dataOne(baseTime);
    }

    /***
     * 输入时间返回时间戳
     * @param time 时间格式 xxxx-xx-xx xx:xx:xx
     *              注： 年份与时间中间有个空格
     * @return
     */
    private static long dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Long.parseLong(times);
    }

    /**
     * 开始计时
     */
    public void start() {
        changTime += (baseTime - System.currentTimeMillis());//计算计时开始时间
        mHandler.post(mTimeRefresher);
    }

    /**
     * 停止计时
     */
    public void stop() {
        mHandler.removeCallbacks(mTimeRefresher);
    }

    /**
     * 重置时间
     */
    private void reSetTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
        calendar.setTimeInMillis(changTime);
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));
        setText(hour, h2);
        setText(minute, m2);
        setText(second, s2);
    }

    /**
     * 将相应的时间设置的控件上去
     *
     * @param text
     * @param textView1
     * @param textView2
     */
    private void setText(String text, TextView textView1, TextView textView2) {
        if (text.length() == 2) {
            textView1.setText(text.substring(0, 1));
            textView2.setText(text.substring(1, 2));
        } else {
            textView1.setText("0");
            textView2.setText(text);
        }
    }

    private void setText(String text, TextView textView) {
        if (text.length() == 2) {
            textView.setText(text);
        } else {
            textView.setText("0" + text);
        }
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextBgRes() {
        return textBgRes;
    }

    public void setTextBgRes(int textBgRes) {
        this.textBgRes = textBgRes;
    }

    public float gettimeColonPadding() {
        return timeColonPadding;
    }

    public void settimeColonPadding(float timeColonPadding) {
        this.timeColonPadding = timeColonPadding;
    }

    public float gettextBgPaddingLR() {
        return textBgPaddingLR;
    }

    public void settextBgPaddingLR(float textBgPaddingLR) {
        this.textBgPaddingLR = textBgPaddingLR;
    }

    public float getTextBgPaddingTB() {
        return textBgPaddingTB;
    }

    public void setTextBgPaddingTB(float textBgPaddingTB) {
        this.textBgPaddingTB = textBgPaddingTB;
    }
}
