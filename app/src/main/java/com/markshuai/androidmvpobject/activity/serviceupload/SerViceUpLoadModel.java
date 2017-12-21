package com.markshuai.androidmvpobject.activity.serviceupload;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;


import com.markshuai.androidmvpobject.apkupload.service.DownloadService;
import com.markshuai.androidmvpobject.utils.ContentManager;
import com.markshuai.androidmvpobject.utils.SDCardHelper;
import com.markshuai.androidmvpobject.utils.ToastUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * 作者：MarkShuai
 * 时间：2017/12/20 13:40
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class SerViceUpLoadModel implements SerViceUpLoadContract.Model {

    private DownloadService.DownloadBinder mDownloadBinder;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mDownloadBinder = null;
        }
    };

    @Override
    public void bindModelService(Context context) {
        Intent intent = new Intent(context, DownloadService.class);
        context.startService(intent);
        context.bindService(intent, mConnection, BIND_AUTO_CREATE);//绑定服务
    }

    @Override
    public void startUpLoad(Context mContext, ProgressListener listener) {
        File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "test.apk");
        boolean isHave = SDCardHelper.fileIsHave(file.getPath());
        if (isHave) {
            ToastUtils.showToast("存在了");
        } else {
            if (mDownloadBinder != null) {
                long downloadId = mDownloadBinder.startDownload(ContentManager.APK_URL);
                startCheckProgress(downloadId, mContext, listener);
            }
        }
    }

    //开始监听进度
    private void startCheckProgress(long downloadId, Context mContext, ProgressListener listener) {
        Observable
                .interval(100, 200, TimeUnit.MILLISECONDS, Schedulers.io())//无限轮询,准备查询进度,在io线程执行
                .filter(time -> mDownloadBinder != null)
                .map(i -> mDownloadBinder.getProgress(downloadId))//获得下载进度
                .takeUntil(progress -> progress >= 100)//返回true就停止了,当进度>=100就是下载完成了
                .distinct()//去重复
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressObserver(mContext, listener));
    }

    //观察者
    private class ProgressObserver implements Observer<Integer> {
        private Context mContext;
        private ProgressListener listener;

        public ProgressObserver(Context mContext, ProgressListener listener) {
            this.mContext = mContext;
            this.listener = listener;
        }

        @Override
        public void onSubscribe(Disposable d) {
            listener.onSubscribeProgress(d);
        }

        @Override
        public void onNext(Integer progress) {
            listener.onNextProgress(progress);
        }

        @Override
        public void onError(Throwable throwable) {
            listener.onErrorProgress(throwable);

        }

        @Override
        public void onComplete() {
            listener.onCompleteProgress();

        }
    }


}
