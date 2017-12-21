package com.markshuai.androidmvpobject.activity.normalupload;

import android.content.Context;

/**
 * 作者：MarkShuai
 * 时间：2017/12/19 15:44
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class NormalPresent<T> extends NormalContract.Present<NormalContract.View> {

    private Context mContext;
    private NormalContract.Model mModel;

    public NormalPresent(Context mContext) {
        this.mContext = mContext;
        mModel = new NormalModel();
    }

    @Override
    public void fetch() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    void upDataApk(NormalContract.View view) {
        mModel.upDataApk(mContext);
        view.startDownLoad();
    }
}
