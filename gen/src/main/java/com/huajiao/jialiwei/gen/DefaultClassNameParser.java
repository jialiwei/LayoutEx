package com.huajiao.jialiwei.gen;

import com.huajiao.jialiwei.gen.android.AndroidConstants;

public class DefaultClassNameParser implements ClassNameParser {
    public String parseClassName(String tagName) {
        if (tagName.contains(".")) return tagName;
        return AndroidConstants.WIDGET_PACKAGE + "." + tagName;
    }
}
