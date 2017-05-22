package com.zhouwei.customview.util;

/**
 * Created by zhouwei on 2017/5/19.
 */

public class TimeUtil {

    public static String secondsToChatTime(int seconds) {
        int second = seconds % 60;
        int minute = seconds / 60;
        int hour = minute / 60;
        if (hour == 0) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                } else {
                    return "0" + minute + ":" + second;
                }
            } else {
                if (second < 10) {
                    return minute + ":0" + second;
                } else {
                    return minute + ":" + second;
                }
            }
        } else {
            if (hour < 10) {
                if (minute < 10) {
                    if (second < 10) {
                        return "0" + hour + ":0" + minute + ":0" + second;
                    } else {
                        return "0" + hour + ":0" + minute + ":" + second;
                    }
                } else {
                    if (second < 10) {
                        return "0" + hour + ":" + minute + ":0" + second;
                    } else {
                        return "0" + hour + ":" + minute + ":" + second;
                    }
                }
            } else {
                if (minute < 10) {
                    if (second < 10) {
                        return hour + ":0" + minute + ":0" + second;
                    } else {
                        return hour + ":0" + minute + ":" + second;
                    }
                } else {
                    if (second < 10) {
                        return hour + ":" + minute + ":0" + second;
                    } else {
                        return hour + ":" + minute + ":" + second;
                    }
                }
            }
        }
    }
}
