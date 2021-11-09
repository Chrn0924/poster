package com.make.poster.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.make.poster.BuildConfig;
import com.make.poster.app.MPApplication;
import com.make.poster.utils.ABImmersiveUtils;


public abstract class MPBaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onCreateActivity();

    }

    protected void onCreateActivity(){
        //设置沉浸式状态栏
        setImmersive();

        setContentView(initLayout());

        initLayoutId();

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

    //销毁一些东西
    protected abstract void destoryInit();




}
