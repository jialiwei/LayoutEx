package com.huajiao.layoutex.android;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

import static com.huajiao.layoutex.android.AndroidInitMethod.INIT_METHOD_NAME;

public class AndroidViewConstructor {


    public static final int CONTEXT_CONSTRICTOR = 1;
    public static final int CONTEXT_ATTRIBUTE_CONSTRICTOR = 2;
    public static final int CONTEXT_ATTRIBUTE_STYLE_CONSTRICTOR = 3;

    public static final String CONTEXT_PARAMETER_NAME = "context";
    public static final String ATTRIBUTESET_PARAMETER_NAME = "attrs";
    public static final String DEFSTYLEATTR_PARAMETER_NAME = "defStyleAttr";

    public static final String CONSTRUCTOR_CONTEXT_BODY = String.format("super(%s)",CONTEXT_PARAMETER_NAME);
    public static final String CONSTRUCTOR_CONTEXT_ATTRIBUTE_BODY = String.format("super(%s,%s)",CONTEXT_PARAMETER_NAME,ATTRIBUTESET_PARAMETER_NAME);
    public static final String CONSTRUCTOR_CONTEXT_ATTRIBUTE_STYPE_BODY = String.format("super(%s,%s,%s)",CONTEXT_PARAMETER_NAME,ATTRIBUTESET_PARAMETER_NAME,DEFSTYLEATTR_PARAMETER_NAME);


    public static final String INIT_STATE = String.format(INIT_METHOD_NAME+"(%s)",CONTEXT_PARAMETER_NAME);

    public MethodSpec getConstructorSpec(int type) {

        ParameterSpec contextParam = ParameterSpec
                .builder(AndroidUtils.getContextClassName(), CONTEXT_PARAMETER_NAME).build();
        ParameterSpec attributeSetParam = ParameterSpec
                .builder(AndroidUtils.getAttributeSetClassName(), ATTRIBUTESET_PARAMETER_NAME).build();
        ParameterSpec styleSetParam = ParameterSpec
                .builder(ClassName.INT, DEFSTYLEATTR_PARAMETER_NAME).build();
        MethodSpec.Builder builder = MethodSpec.constructorBuilder();
//        builder.addAnnotation(Override.class);
        builder.addModifiers(Modifier.PUBLIC);
        if (type == CONTEXT_CONSTRICTOR) {
            return builder.addParameter(contextParam).addStatement(CONSTRUCTOR_CONTEXT_BODY).addStatement(INIT_STATE).build();
        }

        if (type ==CONTEXT_ATTRIBUTE_CONSTRICTOR){
            return builder.addParameter(contextParam).addParameter(attributeSetParam).addStatement(CONSTRUCTOR_CONTEXT_ATTRIBUTE_BODY).addStatement(INIT_STATE).build();
        }

        if (type == CONTEXT_ATTRIBUTE_STYLE_CONSTRICTOR){
            return builder.addParameter(contextParam).addParameter(attributeSetParam).addParameter(styleSetParam).addStatement(CONSTRUCTOR_CONTEXT_ATTRIBUTE_STYPE_BODY).addStatement(INIT_STATE).build();
        }
        return null;
    }


    public List<MethodSpec> getConstructors() {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        methodSpecs.add(getConstructorSpec(CONTEXT_CONSTRICTOR));
        methodSpecs.add(getConstructorSpec(CONTEXT_ATTRIBUTE_CONSTRICTOR));
        methodSpecs.add(getConstructorSpec(CONTEXT_ATTRIBUTE_STYLE_CONSTRICTOR));
        return methodSpecs;
    }
}
