package com.huajiao.jialiwei.gen;

public class GenField {

    private String className;
    private String fieldName;

    private GenClass genClass;
    private boolean clickAble = false;
    private String viewId;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public GenClass getGenClass() {
        return genClass;
    }

    public void setGenClass(GenClass genClass) {
        this.genClass = genClass;
    }

    public boolean isClickAble() {
        return clickAble;
    }

    public void setClickAble(boolean clickAble) {
        this.clickAble = clickAble;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }
}
