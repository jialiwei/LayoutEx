package com.huajiao.jialiwei.gen;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultFieldNameConvert implements FieldNameConvertor {


    Pattern pattern = Pattern.compile("_[a-z]");

    public String convert(String origin) {


        Matcher matcher = pattern.matcher(origin);
        List<Integer> indexes = new ArrayList<>();
        while (matcher.find()) {
            int end = matcher.end();
            indexes.add(end - 1);
        }
        char[] chars = origin.toCharArray();
        for (Integer index : indexes) {
            chars[index] = Character.toUpperCase(chars[index]);
        }
        String after = new String(chars);
        return after.replaceAll("_", "");
    }
}
