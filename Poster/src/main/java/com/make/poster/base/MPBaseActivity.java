package com.make.poster.base;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;
import com.make.poster.BuildConfig;
import com.make.poster.R;
import com.make.poster.app.MPApplication;
import com.make.poster.utils.ABImmersiveUtils;


public abstract class MPBaseActivity extends FragmentActivity {

    private ProgressDialog mProgressDialog;

    public static final int READ_WRITE_STORAGE = 52;

    private TextView baseTitle,baseShare;
    private ImageView backImage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onCreateActivity();

    }

    protected void onCreateActivity(){
        //设置沉浸式状态栏
        setImmersive();

        setContentView(R.layout.base_activity);

        FrameLayout child = findViewById(R.id.basechild_framelayout);

        baseTitle = findViewById(R.id.base_activity_title);
        baseShare = findViewById(R.id.base_activity_share);
        backImage = findViewById(R.id.base_activity_back);


        View view = LayoutInflater.from(this).inflate(initLayout(),null);

        child.addView(view);

        initLayoutId();

        setTtitle(baseTitle);

        setShareContent(baseShare);

        baseShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareClickListener();
            }
        });

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backImageClick();
            }
        });

        backImage(backImage);

        initLayoutClick();

        initData();

        initOthers();

        if(BuildConfig.DEBUG && MPApplication.DEBUGTYPE == 1){
            initTestMethod();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onResumeMethod();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destoryInit();
    }

    private void setImmersive(){
        ABImmersiveUtils.immersive(this);
    }

    protected void setModel(boolean model){
        ABImmersiveUtils.darkMode(this,model);
    }

    //初始化布局
    protected abstract int initLayout();

    //初始化layoutId
    protected abstract void initLayoutId();

    //初始化click时间
    protected abstract void initLayoutClick();

    //初始化数据
    protected abstract void initData();

    //初始化一些别的东西
    protected abstract void initOthers();

    //onResume方法中走的
    protected abstract void onResumeMethod();

    //测试一些方法 启动会走 仅提供测试 且 仅在debug模式下会走
    protected abstract void initTestMethod();

    //设置title
    protected abstract void setTtitle(TextView val);

    //设置分享按钮文字
    protected abstract void setShareContent(TextView val);

    //设置右侧分享按钮点击事件
    protected abstract void shareClickListener();

    //返回事件
    protected abstract void backImage(ImageView backImage);

    protected abstract void backImageClick();

    //销毁一些东西
    protected abstract void destoryInit();

    protected void showLoading(@NonNull String message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    protected void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void showToast(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public boolean requestPermission(String permission) {
        boolean isGranted = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        if (!isGranted) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{permission},READ_WRITE_STORAGE);
        }
        return isGranted;
    }



    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_WRITE_STORAGE:
                isPermissionGranted(grantResults[0] == PERMISSION_GRANTED, permissions[0]);
                break;
        }
    }

    public void isPermissionGranted(boolean isGranted, String permission) {

    }


}
