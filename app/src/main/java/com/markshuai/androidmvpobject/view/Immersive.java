package com.markshuai.androidmvpobject.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 作者：MarkShuai
 * 时间：2017/12/20 11:08
 * 邮箱：MarkShuai@163.com
 * 意图：沉浸式效果
 */

public class Immersive {
    /**
     * [沉浸状态栏]
     */
    public static void steepStatusBar(Activity activity) {
        //判断版本,如果[4.4,5.0)就设置状态栏和导航栏为透明
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                && android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置虚拟导航栏为透明
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 调用此方法设置沉浸式效果，向下兼容4.4
     *
     * @param toolbar                 顶部导航
     * @param bottomNavigationBar     底部导航
     * @param translucentPrimaryColor 沉浸式效果的主题颜色
     */
    @SuppressLint("NewApi")
    public static void setOrChangeTranslucentColor(Toolbar toolbar, View bottomNavigationBar, int translucentPrimaryColor, Activity activity) {
        //判断版本,如果[4.4,5.0)就设置状态栏和导航栏为透明
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                && android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (toolbar != null) {
                //先设置toolbar的高度
                ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                int statusBarHeight = getStatusBarHeight(activity);
                params.height += statusBarHeight;
                toolbar.setLayoutParams(params);
                toolbar.setPadding(
                        toolbar.getPaddingLeft(),
                        toolbar.getPaddingTop() + getStatusBarHeight(activity),
                        toolbar.getPaddingRight(),
                        toolbar.getPaddingBottom());
                toolbar.setBackgroundColor(translucentPrimaryColor);
            }
            if (bottomNavigationBar != null) {
                //解决低版本4.4+的虚拟导航栏的
                if (hasNavigationBarShow(activity.getWindowManager())) {
                    ViewGroup.LayoutParams p = bottomNavigationBar.getLayoutParams();
                    p.height += getNavigationBarHeight(activity);
                    bottomNavigationBar.setLayoutParams(p);
                    //设置底部导航栏的颜色
                    bottomNavigationBar.setBackgroundColor(translucentPrimaryColor);
                }
            }
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(translucentPrimaryColor);
            activity.getWindow().setStatusBarColor(translucentPrimaryColor);
        } else {
            //<4.4的，不做处理
        }
    }

    //获取虚拟导航栏高度
    private static int getNavigationBarHeight(Context context) {
        return getSystemComponentDimen(context, "navigation_bar_height");
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        return getSystemComponentDimen(context, "status_bar_height");
    }

    //获取状态栏的高度
    private static int getSystemComponentDimen(Context context, String dimenName) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField(dimenName).get(object).toString();
            int height = Integer.parseInt(heightStr);
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    //获取底部虚拟导航栏的高度
    @SuppressLint("NewApi")
    private static boolean hasNavigationBarShow(WindowManager wm) {
        Display display = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getRealMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;
        int widthPixels = outMetrics.widthPixels;
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int heightPixels2 = outMetrics.heightPixels;
        int widthPixels2 = outMetrics.widthPixels;
        int w = widthPixels - widthPixels2;
        int h = heightPixels - heightPixels2;
        return w > 0 || h > 0;//竖屏和横屏两种情况。
    }

}
