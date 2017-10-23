package com.huajiao.layouex;

import com.huajiao.layoutex.GenClass;
import com.huajiao.layoutex.parse.LayoutParseException;
import com.huajiao.layoutex.parse.LayoutParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

public class LayoutParserTestor {

    @Test
    public void testParse() throws LayoutParseException {
        LayoutParser layoutParser = new LayoutParser();
        URL resource = LayoutParser.class.getClassLoader().getResource("activity_pengpeng_layout.xml");
        Assert.assertNotNull(resource);
        String fileName = resource.getFile();
        File file = new File(fileName);
        List<GenClass> genClasses = layoutParser.parseLayout(file);
        Assert.assertEquals(1,genClasses.size());
    }
}
