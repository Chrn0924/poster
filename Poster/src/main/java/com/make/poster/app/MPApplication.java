package com.make.poster.app;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.make.poster.R;
import com.make.poster.starter.Posterix;
import com.make.poster.utils.MakePosterLog;

public class MPApplication extends Application {

    public static final int DEBUGTYPE = 1;

    //配置操作svg格式图片颜色 直接修改全部
    public static int OPERTIONSVGCOLOR = R.color.blue;

    @Override
    public void onCreate() {
        super.onCreate();
        MakePosterLog.e("初始化appliacation");
        //初始化图片选择
        Phoenix.config().imageLoader(new ImageLoader() {
            @Override
            public void loadImage(Context context, ImageView imageView, String imagePath, int type) {
                Glide.with(context).load(imagePath).into(imageView);
            }
        });


    }
}
