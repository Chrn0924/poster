package com.chen.makeposter.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chen.makeposter.R;
import com.chen.makeposter.adapter.MainOpertionRecAdapter;
import com.chen.makeposter.bean.MainOpertionBean;
import com.chen.makeposter.listener.MainOpertionRecClickItemListener;
import com.make.poster.activity.MakePosterActivity;
import com.make.poster.base.MPBaseActivity;
import com.make.poster.starter.PosterShareListener;
import com.make.poster.starter.Posterix;
import com.make.poster.utils.MakePosterLog;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MPBaseActivity implements MainOpertionRecClickItemListener {

    private RecyclerView homeOpertionRec;

    @Override
    protected int initLayout() {
        setModel(true);
        return R.layout.activity_main;
    }

    @Override
    protected void initLayoutId() {
        homeOpertionRec = findViewById(R.id.main_home_opertion);
        GridLayoutManager manager = new GridLayoutManager(this,3);
        homeOpertionRec.setLayoutManager(manager);
    }

    @Override
    protected void initLayoutClick() {



    }

    @Override
    protected void initData() {

        ArrayList opertionList = new ArrayList();

        MainOpertionBean mainOpertionBean;

        mainOpertionBean = new MainOpertionBean();
        mainOpertionBean.setOpertionClass(MakePosterActivity.class);
        mainOpertionBean.setOpetionName("制作海报");
        mainOpertionBean.setOpetionIcon(R.drawable.main_opertion_poster);

        opertionList.add(mainOpertionBean);

        MainOpertionRecAdapter mainOpertionRecAdapter = new MainOpertionRecAdapter(this,this,opertionList);
        homeOpertionRec.setAdapter(mainOpertionRecAdapter);

    }

    @Override
    protected void initOthers() {

    }

    @Override
    protected void onResumeMethod() {

    }

    @Override
    protected void initTestMethod() {

    }

    @Override
    protected void setTtitle(TextView val) {
        val.setText("功能列表");
    }

    @Override
    protected void setShareContent(TextView val) {
        val.setVisibility(View.GONE);
    }

    @Override
    protected void shareClickListener() {

    }

    @Override
    protected void backImage(ImageView backImage) {
        backImage.setVisibility(View.GONE);
    }

    @Override
    protected void backImageClick() {

    }

    @Override
    protected void destoryInit() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void clickItemListener(Class mClass, int postion) {

        if(mClass == MakePosterActivity.class){
            Posterix.with().opertionColor(R.color.sky_blue_color_picker).start(this,100001);
        }

    }
}