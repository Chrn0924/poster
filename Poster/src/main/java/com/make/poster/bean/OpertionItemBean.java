package com.make.poster.bean;

import android.graphics.drawable.Drawable;

public class OpertionItemBean {

    Integer opertionImageId;
    String opertionTips;
    int opertionType;
    boolean openFunction;

    public boolean isOpenFunction() {
        return openFunction;
    }

    public void setOpenFunction(boolean openFunction) {
        this.openFunction = openFunction;
    }

    public Integer getOpertionImageId() {
        return opertionImageId;
    }

    public void setOpertionImageId(Integer opertionImageId) {
        this.opertionImageId = opertionImageId;
    }

    public String getOpertionTips() {
        return opertionTips;
    }

    public void setOpertionTips(String opertionTips) {
        this.opertionTips = opertionTips;
    }

    public int getOpertionType() {
        return opertionType;
    }

    public void setOpertionType(int opertionType) {
        this.opertionType = opertionType;
    }
}
