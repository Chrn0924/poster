package com.chen.makeposter.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.chen.makeposter.utils.ABImmersiveUtils;

public abstract class MPBaseActivity extends Activity {

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


}
