package com.markshuai.androidmvpobject.activity.main;

import android.content.Context;
import android.content.Intent;

import com.markshuai.androidmvpobject.activity.normalupload.NormalActivity;
import com.markshuai.androidmvpobject.activity.serviceupload.SerViceUpLoadActivity;


/**
 * 作者：MarkShuai
 * 时间：2017/12/19 14:48
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MainPresent<T> extends MainContract.Present<MainContract.View> {


    private MainContract.Model mModel;
    private Context mContext;

    public MainPresent(Context mContext) {
        this.mContext = mContext;
        mModel = new MainModel();
    }

    @Override
    protected void jumpNormal() {
        Intent intent = new Intent(mContext, NormalActivity.class);
        mContext.startActivity(intent);
    }


    @Override
    protected void jumpService() {
        Intent intent = new Intent(mContext, SerViceUpLoadActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    void login(final MainContract.View view) {
        view.showDialog();
        mModel.loginNet(new MainContract.Model.LoginListener() {
            @Override
            public void success(String str) {
                view.logInOk(str);
                view.dismissDialog();
            }

            @Override
            public void error(String code) {
                view.logInError(code);
                view.dismissDialog();
            }
        });
    }


    @Override
    public void fetch() {

    }

    @Override
    public void onDestroy() {

    }
}
