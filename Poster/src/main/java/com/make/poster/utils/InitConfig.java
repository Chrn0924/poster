package com.make.poster.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.picker.Phoenix;

public class InitConfig {

    public static void initPhoneix(){
        Phoenix.config().imageLoader(new ImageLoader() {
            @Override
            public void loadImage(Context context, ImageView imageView, String imagePath, int type) {
                Glide.with(context).load(imagePath).into(imageView);
            }
        });
    }

}
