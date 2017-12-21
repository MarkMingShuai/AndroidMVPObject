package com.markshuai.androidmvpobject.activity.main;

import android.os.Handler;
import android.os.Looper;

/**
 * 作者：MarkShuai
 * 时间：2017/12/19 14:49
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MainModel implements MainContract.Model {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void loginNet(final LoginListener listener) {
        //模拟登陆场景
        new Thread(() -> {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.success("asdasd");
                }
            });

        }).start();
    }
}
