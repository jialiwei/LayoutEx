package com.huajiao.layoutex;

import com.huajiao.layoutex.android.AndroidConstants;

public class DefaultClassNameParser implements ClassNameParser {
    public String parseClassName(String tagName) {
        if (tagName.contains(".")) return tagName;
        return AndroidConstants.WIDGET_PACKAGE + "." + tagName;
    }
}
