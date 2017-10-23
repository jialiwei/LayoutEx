package com.huajiao.layoutex.android;

import com.huajiao.layoutex.GenClass;
import com.huajiao.layoutex.FieldNameConvertor;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.huajiao.layoutex.DefaultFieldNameConvert;
import com.huajiao.layoutex.GenField;
import com.squareup.javapoet.TypeSpec;

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

    public ClickSpec getClickMethod(GenClass genClass) {
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


//        ClassName listenerClassName = ClassName.bestGuess(genClass.getClassName() + ".Listener");
//
        ClassName className = ClassName.bestGuess(genClass.getClassName());

        ClassName listenerClassName = ClassName.get(className.packageName(), className.simpleName(), "Listener");
        TypeSpec.Builder listenerBuilder = TypeSpec.interfaceBuilder(listenerClassName)
                .addModifiers(Modifier.PUBLIC);



        for (GenField genField : genFields) {
            String viewId = genField.getViewId();
            String methodIdName = fieldNameConvertor.convert(viewId);
            char[] chars = methodIdName.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            methodIdName = new String(chars);
            builder.addStatement(String.format(CASE_TEMPLATE,viewId,methodIdName));

            MethodSpec.Builder clickMethodBuilder = MethodSpec.methodBuilder("on" + methodIdName + "Click");
            clickMethodBuilder.addModifiers(Modifier.PUBLIC);
            clickMethodBuilder.addModifiers(Modifier.ABSTRACT);
            ParameterSpec clickViewParameterSpec = ParameterSpec.builder(viewClass, "v").build();
            clickMethodBuilder.addParameter(clickViewParameterSpec);
            listenerBuilder.addMethod(clickMethodBuilder.build());
        }
        builder.endControlFlow();
        MethodSpec clickMethodSpec = builder.build();
        TypeSpec listenerSpec = listenerBuilder.build();
        ClickSpec clickSpec = new ClickSpec(clickMethodSpec,listenerSpec);
        return clickSpec;
    }

    public static class ClickSpec{
        private MethodSpec clickMethodSpec;
        private TypeSpec listenerSpec;

        public ClickSpec(MethodSpec clickMethodSpec, TypeSpec listenerSpec) {
            this.clickMethodSpec = clickMethodSpec;
            this.listenerSpec = listenerSpec;
        }

        public MethodSpec getClickMethodSpec() {
            return clickMethodSpec;
        }

        public void setClickMethodSpec(MethodSpec clickMethodSpec) {
            this.clickMethodSpec = clickMethodSpec;
        }

        public TypeSpec getListenerSpec() {
            return listenerSpec;
        }

        public void setListenerSpec(TypeSpec listenerSpec) {
            this.listenerSpec = listenerSpec;
        }
    }




}
