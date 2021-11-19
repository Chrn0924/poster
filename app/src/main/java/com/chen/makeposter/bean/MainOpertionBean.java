package com.chen.makeposter.bean;

public class MainOpertionBean {

    private Class opertionClass;
    private Integer opetionIcon;
    private String opetionName;


    public Class getOpertionClass() {
        return opertionClass;
    }

    public void setOpertionClass(Class opertionClass) {
        this.opertionClass = opertionClass;
    }

    public Integer getOpetionIcon() {
        return opetionIcon;
    }

    public void setOpetionIcon(Integer opetionIcon) {
        this.opetionIcon = opetionIcon;
    }

    public String getOpetionName() {
        return opetionName;
    }

    public void setOpetionName(String opetionName) {
        this.opetionName = opetionName;
    }

    @Override
    public String toString() {
        return "MainOpertionBean{" +
                "opertionClass=" + opertionClass +
                ", opetionIcon='" + opetionIcon + '\'' +
                ", opetionName='" + opetionName + '\'' +
                '}';
    }
}
