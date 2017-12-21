package com.markshuai.androidmvpobject.base;

import android.app.Activity;


import com.markshuai.androidmvpobject.R;

import java.util.Iterator;
import java.util.Stack;

/**
 * 作者：MarkShuai
 * 时间：2017/12/19 14:25
 * 邮箱：MarkShuai@163.com
 * 意图：Activity管理工具
 */
public class ActivityManager {

    private static Stack<Activity> activityStack;

    private static ActivityManager instance;


    private ActivityManager() {
    }

    public static ActivityManager getScreenManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void popActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activity.finish();
            activity.overridePendingTransition(R.anim.fade, R.anim.hold);
            activity = null;
        }
    }

    //将Activity全部弹出栈
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activity.overridePendingTransition(R.anim.fade, R.anim.hold);
            activityStack.remove(activity);
            activity = null;
        }
    }


    public Activity currentActivity() {
        if (activityStack.size() == 0) {
            return null;
        }
        Activity activity = activityStack.lastElement();

        return activity;
    }

    //将Activity压入栈
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }

        activityStack.add(activity);
    }

    //将Activity全部弹出栈
    public void popAllActivityExceptOne(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    //将Activity全部弹出栈
    public void popAllActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            popActivity(activity);
        }
    }

    public void removeCurrentAC(Activity ac) {
        if (ac != null) {
            activityStack.remove(ac);
        }

    }

    //将Activity弹出栈
    public void popActivity(Class<?> cls) {
        Iterator var3 = this.activityStack.iterator();
        while (var3.hasNext()) {
            Activity activity = (Activity) var3.next();
            if (cls.equals(activity.getClass())) {
                activity.finish();
            }
        }
    }
}
