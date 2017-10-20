package com.huajiao.layoutex.android;

import com.squareup.javapoet.ClassName;

public class AndroidUtils {



    public static ClassName getContextClassName() {
         return ClassName.bestGuess(AndroidConstants.ANDROID_CONTEXT);
    }


    public static ClassName getAttributeSetClassName() {
        return ClassName.bestGuess(AndroidConstants.ANDROID_ATTRIBUTESET);
    }

    public static String cleanId(String idName) {
        if (idName == null) return null;
        return idName.replaceAll("@\\+id/", "").replaceAll("@id/", "");
    }
}
