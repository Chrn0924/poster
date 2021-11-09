package com.make.poster.utils;

import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.make.poster.activity.MakePosterActivity;
import com.make.poster.base.MPBaseActivity;

import java.util.List;

public class PhoneixUtils {

    public static void phoneixSelect(MPBaseActivity activity,int REQUEST_CODE,int maxNum,int minNum,boolean pervireFlag,boolean openTakingPictures,boolean compressFlag){

        Phoenix.with()
                .theme(PhoenixOption.THEME_BLUE)// 主题
                .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                .maxPickNumber(maxNum)// 最大选择数量
                .minPickNumber(minNum)// 最小选择数量
                .spanCount(4)// 每行显示个数
                .enablePreview(pervireFlag)// 是否开启预览
                .enableCamera(openTakingPictures)// 是否开启拍照
                .enableAnimation(true)// 选择界面图片点击效果
                .enableCompress(compressFlag)// 是否开启压缩
                .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                .thumbnailHeight(160)// 选择界面图片高度
                .thumbnailWidth(160)// 选择界面图片宽度
                .enableClickSound(false)// 是否开启点击声音
                //如果是在Activity里使用就传Activity，如果是在Fragment里使用就传Fragment
                .start(activity, PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE);

    }


}
