package com.huajiao.layouex;

import com.huajiao.layoutex.GenClass;
import com.huajiao.layoutex.android.AndroidClickMethod;
import com.huajiao.layoutex.code.CodeConfig;
import com.huajiao.layoutex.code.SourceGenerator;
import com.huajiao.layoutex.parse.LayoutParseException;
import com.huajiao.layoutex.parse.LayoutParser;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class SourceGeneratorTestor {

    @Test
    public void testGenerate() throws LayoutParseException, IOException {
        LayoutParser layoutParser = new LayoutParser();
        ClassLoader classLoader = LayoutParser.class.getClassLoader();
        URL resource = classLoader.getResource("activity_pengpeng_layout.xml");
        Assert.assertNotNull(resource);
        String fileName = resource.getFile();
        File file = new File(fileName);
        List<GenClass> genClasses = layoutParser.parseLayout(file);
        SourceGenerator sg = new SourceGenerator();
        File outDir = new File("/home/jialiwei/projects/github/LayoutEx/src/test/resources/out");
        CodeConfig codeConfig = new CodeConfig("com.huajiao");

        for (GenClass genClass : genClasses) {
            ClassName className = ClassName.bestGuess(genClass.getClassName());
            TypeSpec typeSpec = sg.generate(genClass,codeConfig);
            JavaFile javaFile = JavaFile.builder(className.packageName(), typeSpec).build();
            javaFile.writeTo(outDir);
        }
    }
}
