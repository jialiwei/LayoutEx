package com.huajiao.jialiwei.gen;

import java.util.List;

/**
 * Created by jialiwei on 17-9-27.
 */
public class ClassElement {

    public static final String ANDROID_WIDGET = "android.widget";

    public ClassElement(String superClassName, List<FieldElement> fieldNodes) {
        this.superClassName = superClassName;
        this.fieldNodes = fieldNodes;
    }

    private String superClassName;


    private List<FieldElement> fieldNodes;


}
