package com.markshuai.androidmvpobject.base;

import android.app.Application;

/**
 * 作者：MarkShuai
 * 时间：2017/12/19 15:29
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ExampleApplication extends Application {


    public static ExampleApplication exampleApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        exampleApplication = this;
    }

    public static ExampleApplication getInstance() {
        return exampleApplication;
    }
}
