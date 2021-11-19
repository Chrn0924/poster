package com.make.poster.starter;

import android.app.Activity;

import androidx.fragment.app.Fragment;


public interface PosterStarter {

    void start(Fragment fragment, PosterOption option,  int requestCode);
    void start(Activity activity, PosterOption option,  int requestCode);

}
