package com.markshuai.androidmvpobject.activity.main;

import android.Manifest;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.markshuai.androidmvpobject.R;
import com.markshuai.androidmvpobject.base.BaseActivity;
import com.markshuai.androidmvpobject.utils.MPermissionUtils;
import com.markshuai.androidmvpobject.utils.ToastUtils;
import com.markshuai.androidmvpobject.view.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainContract.View, MainPresent<MainContract.View>> implements MainContract.View {

    @BindView(R.id.bt_upload1)
    Button mButtonNormalUpLoad;
    @BindView(R.id.bt_upload2)
    Button mButtonServiceUpload;
    @BindView(R.id.bt_login)
    Button mButtonLogin;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void showDialog() {
        LoadingDialog.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialog.dismiss(mContext);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null, getResources().getColor(R.color.primss));
    }

    @Override
    public void initDataAfter() {
        MPermissionUtils.requestPermissionsResult(this, 2, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new MPermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied() {
                MPermissionUtils.showTipsDialog(mContext);
            }
        });
    }

    @Override
    public void setListener() {
        mButtonNormalUpLoad.setOnClickListener(this);
        mButtonServiceUpload.setOnClickListener(this);
    }


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.bt_upload1:
                mPresent.jumpNormal();
                break;
            case R.id.bt_upload2:
                mPresent.jumpService();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected MainPresent<MainContract.View> createPresent() {
        return new MainPresent<>(mContext);
    }

    @Override
    public void logInOk(String str) {
        ToastUtils.showToast("登录成功");
    }

    @Override
    public void logInError(String code) {
        ToastUtils.showToast("登录失败");
    }

    @OnClick(R.id.bt_login)
    public void loginClick() {
        mPresent.login(this);
    }

}
