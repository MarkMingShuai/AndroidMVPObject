package com.markshuai.androidmvpobject.activity.serviceupload;

import android.content.Context;


import com.markshuai.androidmvpobject.base.BaseModel;
import com.markshuai.androidmvpobject.base.BasePresent;
import com.markshuai.androidmvpobject.base.BaseView;

import io.reactivex.disposables.Disposable;

/**
 * 作者：MarkShuai
 * 时间：2017/12/20 13:40
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface SerViceUpLoadContract {
    interface View extends BaseView<Present> {
        void setProgress(Integer progress);
    }

    abstract class Present<T> extends BasePresent<View> {
        //绑定服务
        abstract void bindPresentService();
        //开始更新APK
        abstract void startUpLoadAPK(T t);
    }


    interface Model extends BaseModel {
        //绑定服务
        void bindModelService(Context context);

        //开始下载
        void startUpLoad(Context mContext, ProgressListener listener);

        //观察者的监听接口回调
        interface ProgressListener {

            void onSubscribeProgress(Disposable d);

            void onNextProgress(Integer progress);

            void onErrorProgress(Throwable throwable);

            void onCompleteProgress();
        }
    }
}
