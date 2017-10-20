package com.huajiao.layoutex.code;

import com.huajiao.layoutex.GenClass;
import com.huajiao.jialiwei.gen.android.*;
import com.huajiao.layoutex.android.*;
import com.huajiao.layoutex.util.StringUtils;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

public class SourceGenerator {

    public TypeSpec generate(GenClass genClass) {

        AndroidViewConstructor androidViewConstructor = new AndroidViewConstructor();
        List<MethodSpec> constructors = new ArrayList<>();
        constructors.add(androidViewConstructor.getConstructorSpec(AndroidViewConstructor.CONTEXT_CONSTRICTOR));
        constructors.add(androidViewConstructor.getConstructorSpec(AndroidViewConstructor.CONTEXT_ATTRIBUTE_CONSTRICTOR));
        constructors.add(androidViewConstructor.getConstructorSpec(AndroidViewConstructor.CONTEXT_ATTRIBUTE_STYLE_CONSTRICTOR));


        String superClassName = genClass.getSuperClassName();
        TypeName superType;
        if (StringUtils.isEmpty(superClassName)) {
            superType = ClassName.OBJECT;
        } else {
            superType = ClassName.bestGuess(superClassName);
        }

        AndroidInitMethod androidInitMethod = new AndroidInitMethod(genClass.getFields());
        MethodSpec initMethod = androidInitMethod.getInitMethod();

        AndroidField androidField = new AndroidField();
        List<FieldSpec> fieldSpecList = androidField.generateFieldSpec(genClass);

        ClassName className = ClassName.bestGuess(genClass.getClassName());
        TypeSpec.Builder builder= TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .superclass(superType)
                .addMethods(constructors)
                .addMethod(initMethod)
                .addFields(fieldSpecList);

        AndroidClickMethod clickMethod = new AndroidClickMethod();
        MethodSpec clickMethodSpec = clickMethod.getClickMethod(genClass);
        if (clickMethodSpec != null) {
            ClassName onClick = ClassName.bestGuess(AndroidConstants.ANDROID_VIEW_ONCLICK);
            builder.addSuperinterface(onClick);
            builder.addMethod(clickMethodSpec);
        }
        return builder.build();
    }
}
