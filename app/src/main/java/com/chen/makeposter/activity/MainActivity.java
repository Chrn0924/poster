package com.chen.makeposter.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.chen.makeposter.R;
import com.chen.makeposter.base.MPBaseActivity;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.make.poster.activity.MakePosterActivity;


import java.util.List;

public class MainActivity extends MPBaseActivity {

    //选择背景的requestcode
    final int REQUEST_CODE = 100000;

    private TextView addBackgroudImage;
    private TextView addText;
    private TextView addImage;


    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initLayoutId() {
        addBackgroudImage = findViewById(R.id.mp_button_child_add_backgroud);
        addText = findViewById(R.id.mp_button_child_add_textview);
        addImage = findViewById(R.id.mp_button_child_add_image);
//        colorPickerView = findViewById(R.id.colorPickerView);
//        brightnessSlideBar = findViewById(R.id.brightnessSlideBar);
//
//        colorPickerView.attachBrightnessSlider(brightnessSlideBar);

        startActivity(new Intent(this, MakePosterActivity.class));


    }

    @Override
    protected void initLayoutClick() {

//        colorPickerView.setColorListener(new ColorEnvelopeListener() {
//            @Override
//            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
////                linearLayout.setBackgroundColor(envelope.getColor());
////                textView.setText("#" + envelope.getHexCode());
//                MakePosterLog.e("#" + envelope.getHexCode());
//            }
//        });


        addBackgroudImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Phoenix.with()
                        .theme(PhoenixOption.THEME_BLUE)// 主题
                        .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                        .maxPickNumber(1)// 最大选择数量
                        .minPickNumber(0)// 最小选择数量
                        .spanCount(4)// 每行显示个数
                        .enablePreview(true)// 是否开启预览
                        .enableCamera(false)// 是否开启拍照
                        .enableAnimation(true)// 选择界面图片点击效果
                        .enableCompress(true)// 是否开启压缩
                        .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                        .thumbnailHeight(160)// 选择界面图片高度
                        .thumbnailWidth(160)// 选择界面图片宽度
                        .enableClickSound(false)// 是否开启点击声音
                        //如果是在Activity里使用就传Activity，如果是在Fragment里使用就传Fragment
                        .start(MainActivity.this, PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE);

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            //返回的数据
            List<MediaEntity> result = Phoenix.result(data);
            for(int i=0;i<result.size();i++){
                Log.e("chen","选择的数据 -> "+result.get(i).getLocalPath());
            }
        }
    }
}