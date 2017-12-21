package com.markshuai.androidmvpobject.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.markshuai.androidmvpobject.utils.MPermissionUtils;
import com.markshuai.androidmvpobject.view.Immersive;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：MarkShuai
 * 时间：2017/12/19 14:24
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public abstract class BaseActivity<V, T extends BasePresent<V>> extends AppCompatActivity implements View.OnClickListener {

    /**
     * P层引用
     */
    protected T mPresent;

    /**
     * 退出程序的时间间隔
     */
    private long exitTime = 0;

    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = true;

    /**
     * 是否允许全屏,此处全屏是指将状态栏显示
     **/
    private boolean onlyShowStatusBar = false;

    /**
     * 是否允许全屏,此处全屏是指将状态栏都隐藏掉
     **/
    private boolean mAllowFullScreen = false;

    /**
     * 是否禁止旋转屏幕
     **/
    private boolean isAllowScreenRoate = false;

    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;

    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 上下文
     **/
    public Context mContext = this;

    private ActivityManager activityManager;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BaseActivityMVP-->onCreate()");
        //是否允许全屏
        if (mAllowFullScreen) {
            //隐藏状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        //判断是否允许全屏
        if (onlyShowStatusBar) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        //设置主布局
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);

        //判断是否设置了沉浸式效果
        if (isSetStatusBar) {
            steepStatusBar();
        }
        //加载主布局
        setContentView(mContextView);
        bind = ButterKnife.bind(this);
        //是否禁止屏幕旋转
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        //创建Presenter层
        mPresent = createPresent();
        //做绑定
        mPresent.attachView((V) this);

        //初始化数据之前获取一些数据的方法
        initDataBefore();
        //调用初始化控件的方法
        initView(mContextView);

        initDataAfter();

        //P层数据初始化逻辑
        mPresent.fetch();

        //Activity的管理，将Activity压入栈
        activityManager = ActivityManager.getScreenManager();
        activityManager.pushActivity(this);
        //设置监听
        setListener();
    }

    /**
     * [绑定视图]
     *
     * @return
     */
    public abstract View bindView();


    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        Immersive.steepStatusBar(this);
    }


    /**
     * 调用此方法设置沉浸式效果，向下兼容4.4
     *
     * @param toolbar                 顶部导航
     * @param bottomNavigationBar     底部导航
     * @param translucentPrimaryColor 沉浸式效果的主题颜色
     */
    public void setOrChangeTranslucentColor(Toolbar toolbar, View bottomNavigationBar, int translucentPrimaryColor) {
        Immersive.setOrChangeTranslucentColor(toolbar, bottomNavigationBar, translucentPrimaryColor, this);
    }

    /**
     * [初始化控件之前初始化数据]
     */
    public void initDataBefore() {
    }

    /**
     * [初始化控件]
     *
     * @param view
     */
    public abstract void initView(View view);

    /**
     * [初始化控件之后初始化数据]
     */
    public abstract void initDataAfter();

    /**
     * [设置监听]
     */
    public abstract void setListener();

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View view) {
        widgetClick(view);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }


    @Override
    protected void onDestroy() {
        mPresent.detach();
        activityManager.popActivity(this);
        bind.unbind();
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

    }

    /*
     * @params
     * @name 子类实现具体的构建过程
     * @data 2017/11/20 15:39
     * @author :MarkShuai
     */
    protected abstract T createPresent();

    /**
     * [是否允许全屏]
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * [是否设置沉浸状态栏]
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    /**
     * 描述：退出程序
     */
    protected void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            activityManager.popAllActivity();
        }
    }

    public void setOnlyShowStatusBar(boolean onlyShowStatusBar) {
        this.onlyShowStatusBar = onlyShowStatusBar;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
