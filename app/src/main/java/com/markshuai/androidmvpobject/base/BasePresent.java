package com.markshuai.androidmvpobject.base;

import java.lang.ref.WeakReference;

/**
 * Created by MarkShuai on 2017/11/18.
 */

public abstract class BasePresent<T> {

    /**
     * 持有UI接口的弱引用
     */
    protected WeakReference<T> mViewRef;

    /**
     * 获取数据方法
     */
    public abstract void fetch();

    /**
     * 绑定的方法
     * 在onCreate()中调用
     *
     * @param view
     */
    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    /**
     * 解绑
     * 在onDestroy方法中调用，防止内存泄漏
     */
    public void detach() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }

        onDestroy();
    }

    //释放资源处理
    public abstract void onDestroy();

}
