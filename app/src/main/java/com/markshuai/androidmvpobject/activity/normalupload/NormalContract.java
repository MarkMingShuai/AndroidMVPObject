package com.markshuai.androidmvpobject.activity.normalupload;

import android.content.Context;

import com.markshuai.androidmvpobject.base.BaseModel;
import com.markshuai.androidmvpobject.base.BasePresent;
import com.markshuai.androidmvpobject.base.BaseView;


/**
 * 作者：MarkShuai
 * 时间：2017/12/19 15:36
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface NormalContract {
    interface View extends BaseView<Present> {
        void startDownLoad();
    }

    abstract class Present<T> extends BasePresent<View> {
        abstract void upDataApk(T t);
    }

    interface Model extends BaseModel {
        void upDataApk(Context context);
    }
}
