package com.markshuai.androidmvpobject.activity.normalupload;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.markshuai.androidmvpobject.utils.ContentManager;


/**
 * 作者：MarkShuai
 * 时间：2017/12/19 15:49
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class NormalModel implements NormalContract.Model {
    @Override
    public void upDataApk(Context mContext) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(ContentManager.APK_URL);
        intent.setData(content_url);
        mContext.startActivity(intent);
    }
}
