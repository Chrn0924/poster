package com.make.poster.starter;

import android.app.Activity;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import com.make.poster.activity.MakePosterActivity;
import com.make.poster.app.MPApplication;


public class Posterix implements PosterStarter{

    public Posterix() {

    }

    public static PosterOption with(){
        return new PosterOption();
    }


    @Override
    public void start(Fragment fragment, PosterOption option,int requestCode) {
        startFromFragment(fragment,option,requestCode);
    }

    @Override
    public void start(Activity activity, PosterOption option,  int requestCode) {
        startFromActivity(activity, option, requestCode);
    }


    private void startFromFragment(Fragment fragment, PosterOption option, int requestCode) {
        MPApplication.OPERTIONSVGCOLOR = option.getOpertionColor();
        Intent intent = new Intent(fragment.getActivity(), MakePosterActivity.class);
        intent.putExtra(PosterConstant.POSTER_OPTION, option);
        fragment.getActivity().startActivity(intent);
    }

    private void startFromActivity(Activity activity, PosterOption option, int requestCode) {
        MPApplication.OPERTIONSVGCOLOR = option.getOpertionColor();
        Intent intent = new Intent(activity, MakePosterActivity.class);
        intent.putExtra(PosterConstant.POSTER_OPTION, option);
        activity.startActivity(intent);
    }


}
