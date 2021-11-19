package com.make.poster.starter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import com.make.poster.R;
import com.make.poster.starter.PosterReflect;
import com.make.poster.starter.PosterStarter;
import com.make.poster.utils.MakePosterLog;

public class PosterOption implements Parcelable {

    public PosterOption() {

    }

    //是否开始画线功能
    private boolean openShape = true;

    //是否开启添加文本功能
    private boolean openText = true;

    //是否开始橡皮擦功能
    private boolean openEraser = true;

    //是否开启滤镜功能
    private boolean openFilter = true;

    //是否开启添加表情功能
    private boolean openEmoji = true;

    //是否开启添加图片功能
    private boolean openPicture = true;

    //是否开启编辑背景功能
    private boolean openBackgrond = true;

    //是否开启分享按钮功能
    private boolean openShare = false;

    //页面title
    private String pageTitle = "编辑";

    //svg格式按钮的颜色
    private int opertionColor = R.color.red_color_picker;

    //右侧分享按钮文字
    private String shareTitle = "分享";

    public boolean isOpenShape() {
        return openShape;
    }

    public boolean isOpenText() {
        return openText;
    }

    public boolean isOpenEraser() {
        return openEraser;
    }

    public boolean isOpenFilter() {
        return openFilter;
    }

    public boolean isOpenEmoji() {
        return openEmoji;
    }

    public boolean isOpenPicture() {
        return openPicture;
    }

    public boolean isOpenBackgrond() {
        return openBackgrond;
    }

    public boolean isOpenShare() {
        return openShare;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public int getOpertionColor() {
        return opertionColor;
    }

    public static Creator<PosterOption> getCREATOR() {
        return CREATOR;
    }

    public PosterOption openShape(boolean val) {
        openShape = val;
        return this;
    }

    public PosterOption openText(boolean val) {
        openText = val;
        return this;
    }

    public PosterOption openEraser(boolean val) {
        openEraser = val;
        return this;
    }

    public PosterOption openFilter(boolean val) {
        openFilter = val;
        return this;
    }

    public PosterOption openEmoji(boolean val) {
        openEmoji = val;
        return this;
    }

    public PosterOption openPicture(boolean val) {
        openPicture = val;
        return this;
    }

    public PosterOption openBackgrond(boolean val) {
        openBackgrond = val;
        return this;
    }

//    public PosterOption openShare(boolean val) {
//        openShare = val;
//        return this;
//    }

    public PosterOption pageTitle(String val) {
        pageTitle = val;
        return this;
    }

//    public PosterOption shareTitle(String val) {
//        shareTitle = val;
//        return this;
//    }


    public PosterOption opertionColor(int val) {
        opertionColor = val;
        return this;
    }


    public void start(Fragment fragment,  int requestCode) {
        PosterStarter starter = PosterReflect.loadStarter(PosterReflect.posterix);
        if (starter != null) {
            starter.start(fragment, this, requestCode);
        }
    }

    public void start(Activity activity,  int requestCode) {
        PosterStarter starter = PosterReflect.loadStarter(PosterReflect.posterix);
        if (starter != null) {
            starter.start(activity, this,  requestCode);
        }
    }

    protected PosterOption(Parcel in) {
        openShape = in.readByte() != 0;
        openText = in.readByte() != 0;
        openEraser = in.readByte() != 0;
        openFilter = in.readByte() != 0;
        openEmoji = in.readByte() != 0;
        openPicture = in.readByte() != 0;
        openBackgrond = in.readByte() != 0;
        openShare = in.readByte() != 0;
        pageTitle = in.readString();
        opertionColor = in.readInt();
        shareTitle = in.readString();
    }

    public static final Creator<PosterOption> CREATOR = new Creator<PosterOption>() {
        @Override
        public PosterOption createFromParcel(Parcel in) {
            return new PosterOption(in);
        }

        @Override
        public PosterOption[] newArray(int size) {
            return new PosterOption[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressLint("NewApi")
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBoolean(this.openShape);
        dest.writeBoolean(this.openText);
        dest.writeBoolean(this.openEraser);
        dest.writeBoolean(this.openFilter);
        dest.writeBoolean(this.openEmoji);
        dest.writeBoolean(this.openPicture);
        dest.writeBoolean(this.openBackgrond);
//        dest.writeBoolean(this.openShare);
        dest.writeString(this.pageTitle);
        dest.writeInt(this.opertionColor);
//        dest.writeString(this.shareTitle);
    }

    @Override
    public String toString() {
        return "PosterOption{" +
                "openShape=" + openShape +
                ", openText=" + openText +
                ", openEraser=" + openEraser +
                ", openFilter=" + openFilter +
                ", openEmoji=" + openEmoji +
                ", openPicture=" + openPicture +
                ", openBackgrond=" + openBackgrond +
                ", openShare=" + openShare +
                ", pageTitle='" + pageTitle + '\'' +
                '}';
    }
}
