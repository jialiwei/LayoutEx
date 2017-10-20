package com.huajiao.layoutex.android;

import com.huajiao.layoutex.GenField;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.util.List;

public class AndroidInitMethod {

    public static final String INIT_METHOD_NAME = "initView";

    private List<GenField> fields;

    public static final String FINDVIEW_STATEMENT = "%s = ($T)findViewById(R.id.%s)";
    public static final String CLICK_STATEMENT = "%s.setOnClickListener(this)";

    public AndroidInitMethod(List<GenField> fields) {
        this.fields = fields;
    }

    public MethodSpec getInitMethod() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(INIT_METHOD_NAME).addModifiers(Modifier.PRIVATE);
        for (GenField field : fields) {
            String statement = generateFindViewStatement(field);
            ClassName className = ClassName.bestGuess(field.getClassName());
            builder.addStatement(statement,className);
            if (field.isClickAble()) {
                String clickStatement = generateClickStatement(field);
                builder.addStatement(clickStatement);
            }
        }
        return builder.build();
    }

    private String generateFindViewStatement(GenField field) {
//        ClassName className = ClassName.bestGuess(field.getClassName());
//        String simpleName = className.simpleName();
        return String.format(FINDVIEW_STATEMENT,field.getFieldName(),field.getViewId());
    }

    private String generateClickStatement(GenField field) {
        return String.format(CLICK_STATEMENT,field.getFieldName());
    }


}
