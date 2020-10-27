package com.github.liaomengge.mybatis.utils;

import org.mybatis.generator.api.dom.java.JavaElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by liaomengge on 2019/9/19.
 */
public final class ElementUtil {

    public static void addAuthorTag(JavaElement element) {
        // 获取当前时间
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        element.addJavaDocLine("/**");
        element.addJavaDocLine(" * @author liaomengge");
        element.addJavaDocLine(" * @date " + now);
        element.addJavaDocLine(" */");
    }
}
