package com.huajiao.layoutex;

import com.huajiao.layoutex.android.AndroidConstants;

public class DefaultClassNameParser implements ClassNameParser {
    public String parseClassName(String tagName) {
        if (tagName.contains(".")) return tagName;
        String packageName = AndroidConstants.WIDGET_PACKAGE;
        if ("View".equals(tagName) || "ViewStub".equals(tagName)) {
            packageName = AndroidConstants.VIEW_PACKAGE;
        }
        return packageName + "." + tagName;
    }
}
