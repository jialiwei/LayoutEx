package com.huajiao.jialiwei.gen.parse;

import com.huajiao.jialiwei.gen.*;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import idea.gen.*;
import com.huajiao.jialiwei.gen.android.AndroidAttributes;
import com.huajiao.jialiwei.gen.android.AndroidUtils;
import com.huajiao.jialiwei.gen.code.SourceGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jialiwei on 17-9-27.
 */
public class LayoutParser {

    private FieldNameConvertor fieldNameConvertor = new DefaultFieldNameConvert();

    private ClassNameParser classNameParser = new DefaultClassNameParser();

    public LayoutParser() {

    }

    public List<GenClass> parseLayout(File layoutDoc) throws LayoutParseException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(layoutDoc);
            Element documentElement = document.getDocumentElement();

            return parse(documentElement, null);
        } catch (Exception e) {
            throw new LayoutParseException("parse layout failed", e);
        }
    }

    public List<GenClass> parse(Element element, GenClass parentClass) {
        String idName = element.getAttribute(AndroidAttributes.ATTRIBUTE_ID);
        idName = AndroidUtils.cleanId(idName);
        String customerClassName = element.getAttribute(CustomerAttributes.ATTRIBUTE_CLASS_NAME);
        List<GenClass> result = new ArrayList<>();
        GenClass parentClassForSubElement;
        if ((customerClassName == null || customerClassName.length() == 0) && parentClass != null) {
            parentClassForSubElement = parentClass;
            if (idName != null && idName.length() > 0) {
                GenField genField = new GenField();
                genField.setFieldName(fieldNameConvertor.convert(idName));
                genField.setViewId(idName);
                genField.setClassName(classNameParser.parseClassName(element.getTagName()));
                genField.setClickAble(Boolean.parseBoolean(element.getAttribute(CustomerAttributes.CLICKABLE_NAME)));
                parentClass.appendField(genField);
            }

        } else {
            GenClass genClass = new GenClass();
            genClass.setClassName(customerClassName);
            genClass.setSuperClassName(classNameParser.parseClassName(element.getTagName()));
            result.add(0,genClass);
            parentClassForSubElement = genClass;
        }

        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() != Node.ELEMENT_NODE) continue;
            Element childElement = (Element) item;
            List<GenClass> childGenClasses = parse(childElement, parentClassForSubElement);
            result.addAll(0, childGenClasses);
        }
        return result;


    }


    public static void main(String[] args) throws IOException {
        LayoutParser layoutParser = new LayoutParser();
        try {
            List<GenClass> genClasses = layoutParser.parseLayout(new File("/home/jialiwei/projects/huajiao_android/trunk/huajiao_new/living_android/src/main/res/layout/activity_pengpeng_layout.xml"));

            SourceGenerator sourceGenerator = new SourceGenerator();
            File outFile = new File("/home/jialiwei/sourceGen");
            for (GenClass genClass : genClasses) {
                ClassName className = ClassName.bestGuess(genClass.getClassName());
                TypeSpec typeSpec = sourceGenerator.generate(genClass);
                JavaFile javaFile = JavaFile.builder(className.packageName(), typeSpec).build();
                javaFile.writeTo(outFile);
            }

        } catch (LayoutParseException e) {
            e.printStackTrace();
        }
    }

}
