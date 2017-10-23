package com.huajiao.layoutex.code;

import com.huajiao.layoutex.GenClass;
import com.huajiao.layoutex.android.*;
import com.huajiao.layoutex.util.StringUtils;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

public class SourceGenerator {

    public TypeSpec generate(GenClass genClass,CodeConfig config) {

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


        ClassName className = ClassName.bestGuess(genClass.getClassName());
        TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .superclass(superType)
                .addMethods(constructors);



        AndroidInitMethod androidInitMethod = new AndroidInitMethod();
        MethodSpec initMethod = androidInitMethod.getInitMethod(genClass,config);
        builder.addMethod(initMethod);

        AndroidField androidField = new AndroidField();
        List<FieldSpec> fieldSpecList = androidField.generateFieldSpec(genClass);
        builder.addFields(fieldSpecList);


        AndroidClickMethod clickMethod = new AndroidClickMethod();
        AndroidClickMethod.ClickSpec clickSpec = clickMethod.getClickMethod(genClass);
        MethodSpec clickMethodSpec =  clickSpec.getClickMethodSpec();
        if (clickMethodSpec != null) {
            ClassName onClick = ClassName.bestGuess(AndroidConstants.ANDROID_VIEW_ONCLICK);
            builder.addSuperinterface(onClick);
            builder.addMethod(clickMethodSpec);

            TypeSpec listenerSpec = clickSpec.getListenerSpec();
            builder.addType(listenerSpec);

            ClassName listenerClassName = ClassName.bestGuess(listenerSpec.name);

            FieldSpec listenerField = FieldSpec.builder(listenerClassName, AndroidClickMethod.LISTENER_NAME, Modifier.PRIVATE).build();
            builder.addField(listenerField);
        }
        return builder.build();
    }
}
