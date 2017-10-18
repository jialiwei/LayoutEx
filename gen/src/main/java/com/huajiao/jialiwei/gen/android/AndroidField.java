package com.huajiao.jialiwei.gen.android;

import com.huajiao.jialiwei.gen.GenClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.huajiao.jialiwei.gen.GenField;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

public class AndroidField {

    public List<FieldSpec> generateFieldSpec(GenClass genClass) {
        List<GenField> fields = genClass.getFields();
        List<FieldSpec> fieldSpecList = new ArrayList<>();
        for (GenField field : fields) {
            ClassName className = ClassName.bestGuess(field.getClassName());
            FieldSpec fieldSpec = FieldSpec.builder(className, field.getFieldName(), Modifier.PRIVATE).build();
            fieldSpecList.add(fieldSpec);
        }
        return fieldSpecList;
    }
}
