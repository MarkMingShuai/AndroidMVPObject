package com.markshuai.androidmvpobject.activity.main;


import com.markshuai.androidmvpobject.base.BaseModel;
import com.markshuai.androidmvpobject.base.BasePresent;
import com.markshuai.androidmvpobject.base.BaseView;

/**
 * 作者：MarkShuai
 * 时间：2017/12/19 14:46
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public interface MainContract {

    interface View extends BaseView<Present> {
        //登陆成功
        void logInOk(String str);
        //登录失败
        void logInError(String code);
    }

    abstract class Present<T> extends BasePresent<View> {
        //跳转到普通下载更新
        abstract void jumpNormal();
        //使用服务下载更新
        abstract void jumpService();
        //登录方法
        abstract void login(T t);
    }

    interface Model extends BaseModel {
        //联网登录
        void loginNet(LoginListener listener);

        //登陆成功失败的监听
        interface LoginListener {
            void success(String str);

            void error(String code);
        }
    }

}
