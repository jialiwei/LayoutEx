package com.huajiao.layoutex.android;

import com.huajiao.layoutex.GenClass;
import com.huajiao.layoutex.GenField;
import com.huajiao.layoutex.code.CodeConfig;
import com.huajiao.layoutex.util.StringUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import javax.lang.model.element.Modifier;
import java.util.List;

public class AndroidInitMethod {

    public static final String INIT_METHOD_NAME = "initView";


    public static final String FIND_VIEW_STATEMENT = "%s = ($T)findViewById($T.id.%s)";
    public static final String CLICK_STATEMENT = "%s.setOnClickListener(this)";
    public static final String INFLATE_STATEMENT = "$T.from(%s).inflate(R.layout.%s, this)";

    public AndroidInitMethod() {
    }

    public MethodSpec getInitMethod(GenClass genClass, CodeConfig config) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(INIT_METHOD_NAME).addModifiers(Modifier.PRIVATE);

        ParameterSpec contextParam = ParameterSpec
                .builder(AndroidUtils.getContextClassName(), AndroidViewConstructor.CONTEXT_PARAMETER_NAME).build();

        builder.addParameter(contextParam);

        String layoutFileName = genClass.getLayoutFileName().replaceAll("\\.xml","");
        if (!StringUtils.isEmpty(layoutFileName)) {
            String inflateStatement = String.format(INFLATE_STATEMENT, AndroidViewConstructor.CONTEXT_PARAMETER_NAME, layoutFileName);
            ClassName layoutInflater = ClassName.bestGuess(AndroidConstants.ANDROID_VIEW_LAYOUTINFLATER);
            builder.addStatement(inflateStatement,layoutInflater);
        }
        List<GenField> fields = genClass.getFields();
        ClassName Rname = ClassName.bestGuess(config.getPackageName() + ".R");
        for (GenField field : fields) {
            String statement = generateFindViewStatement(field);
            String fieldClassName = field.getClassName();
            if (AndroidConstants.ANDROID_VIEW.equals(fieldClassName)) {
                String viewStatement = statement.replaceFirst("\\(\\$T\\)", "");
                builder.addStatement(viewStatement, Rname);
            } else {
                ClassName className = ClassName.bestGuess(fieldClassName);
                builder.addStatement(statement, className,Rname);
            }
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
        return String.format(FIND_VIEW_STATEMENT, field.getFieldName(), field.getViewId());
    }

    private String generateClickStatement(GenField field) {
        return String.format(CLICK_STATEMENT, field.getFieldName());
    }


}
