package com.huajiao.jialiwei.gen.android;

import com.huajiao.jialiwei.gen.GenClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.huajiao.jialiwei.gen.DefaultFieldNameConvert;
import com.huajiao.jialiwei.gen.FieldNameConvertor;
import com.huajiao.jialiwei.gen.GenField;

import javax.lang.model.element.Modifier;
import java.util.List;

public class AndroidClickMethod {

    public static final String LISTENER_NAME = "listener";

    public static final String VIEW_VARIABLE_NAME = "v";

    public static final String CASE_TEMPLATE = "case R.id.%s:\n"
            + "if("+LISTENER_NAME+" != null){\n"
            +  "\t" +  LISTENER_NAME+ ".on%sClick("+VIEW_VARIABLE_NAME+");\n"
            + "}\n"
            + "break";

    private FieldNameConvertor fieldNameConvertor = new DefaultFieldNameConvert();

    public MethodSpec getClickMethod(GenClass genClass) {
        if (genClass == null ) return null;
        List<GenField> genFields = genClass.clickAbleFields();
        if (genFields == null ||genFields.size() == 0) return null;

        ClassName viewClass = ClassName.bestGuess(AndroidConstants.ANDROID_VIEW);
        ParameterSpec parameterSpec = ParameterSpec.builder(viewClass, VIEW_VARIABLE_NAME).build();
        MethodSpec.Builder builder = MethodSpec.methodBuilder("onClick")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(parameterSpec);
        builder.beginControlFlow("switch(v.getId())");
        for (GenField genField : genFields) {
            String viewId = genField.getViewId();
            String methodIdName = fieldNameConvertor.convert(viewId);
            builder.addStatement(String.format(CASE_TEMPLATE,viewId,methodIdName));
        }
        builder.endControlFlow();
        return builder.build();
    }
}
