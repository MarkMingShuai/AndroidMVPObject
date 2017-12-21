package com.markshuai.androidmvpobject.base;

/**
 * 作者：MarkShuai
 * 时间：2017/12/19 14:43
 * 邮箱：MarkShuai@163.com
 * 意图：View层基类
 */

public interface BaseView<T> {
    //显示进度框
    void showDialog();

    //隐藏进度框
    void dismissDialog();
}
