package com.markshuai.androidmvpobject.activity.serviceupload;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


import com.markshuai.androidmvpobject.R;
import com.markshuai.androidmvpobject.base.BaseActivity;
import com.markshuai.androidmvpobject.view.LoadingDialog;

import butterknife.BindView;

public class SerViceUpLoadActivity extends BaseActivity<SerViceUpLoadContract.View, SerViceUpLoadPresent<SerViceUpLoadContract.View>> implements SerViceUpLoadContract.View {


    @BindView(R.id.bt_upload)
    Button mButtonUpLoad;
    @BindView(R.id.down_progress)
    ProgressBar mDownLoadProgress;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ser_vice_up_load;
    }

    @Override
    public void initView(View view) {
        //绑定下载服务
        mPresent.bindPresentService();
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void setListener() {
        mButtonUpLoad.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.bt_upload:
                mPresent.startUpLoadAPK(this);
                break;
        }
    }

    @Override
    protected SerViceUpLoadPresent<SerViceUpLoadContract.View> createPresent() {
        return new SerViceUpLoadPresent<>(this);
    }

    @Override
    public void showDialog() {
        LoadingDialog.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialog.dismiss(mContext);
    }

    @Override
    public void setProgress(Integer progress) {
        mDownLoadProgress.setProgress(progress);
    }
}
