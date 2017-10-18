package com.huajiao.jialiwei.gen;

import java.util.ArrayList;
import java.util.List;

public class GenClass {

    private String className;
    private String superClassName;

    private List<GenField> fields = new ArrayList<GenField>();


    public void appendField(GenField genField) {
        genField.setGenClass(this);
        fields.add(genField);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<GenField> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("className:").append(className);
        for (GenField field : fields) {
            sb.append("\n\t ").append(field.getClassName()).append(":").append(field.getFieldName());
        }
        return sb.toString();
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }

    public List<GenField> clickAbleFields() {
        List<GenField> result = new ArrayList<>();
        for (GenField field : fields) {
            if (field.isClickAble()) {
                result.add(field);
            }
        }
        return result;
    }
}
