package com.github.liaomengge.mybatis.plugins;

import com.github.liaomengge.mybatis.utils.ElementUtil;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by liaomengge on 2019/9/19.
 */
public class ServicePlugin extends PluginAdapter {

    private String targetProject;
    private String targetPackage;

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String targetProject = this.properties.getProperty("targetProject");
        if (StringUtility.stringHasValue(targetProject)) {
            this.targetProject = targetProject;
        } else {
            throw new RuntimeException("targetProject 属性不能为空！");
        }
        String targetPackage = this.properties.getProperty("targetPackage");
        if (StringUtility.stringHasValue(targetPackage)) {
            this.targetPackage = targetPackage;
        } else {
            throw new RuntimeException("targetPackage 属性不能为空！");
        }
    }

    /**
     * @param introspectedTable
     * @return
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        String service = targetPackage + "." + domainObjectName + "Service";
        TopLevelClass topLevelClass = new TopLevelClass(new FullyQualifiedJavaType(service));
        topLevelClass.addImportedType(entityType);
        topLevelClass.addImportedType(new FullyQualifiedJavaType(service));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation" +
                ".Autowired"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("cn.mwee.service.base_framework.mysql.service" +
                ".BaseService"));
        topLevelClass.addAnnotation("@Service(\"" + firstLetterLowerCase(domainObjectName + "Service") + "\")");
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.setSuperClass(new FullyQualifiedJavaType("BaseService<" + entityType.getShortName() + ">"));
        setMapperField(introspectedTable, topLevelClass);
        ElementUtil.addAuthorTag(topLevelClass);
        return Arrays.asList(new GeneratedJavaFile(topLevelClass, targetProject, new DefaultJavaFormatter()));
    }

    /**
     * @param introspectedTable
     * @param clazz
     */
    private void setMapperField(IntrospectedTable introspectedTable, TopLevelClass clazz) {
        // 实体类的类名
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        // Mapper类所在包的包名
        String mapperPackage = introspectedTable.getContext().getJavaClientGeneratorConfiguration().getTargetPackage();
        Field mapperField = new Field("liaomengge", FullyQualifiedJavaType.getIntInstance());
        // 设置Field的注解
        mapperField.addAnnotation("@Autowired");
        mapperField.setVisibility(JavaVisibility.PRIVATE);
        // 设置Field的类型
        mapperField.setType(new FullyQualifiedJavaType(domainObjectName + "Mapper"));
        // 设置Field的名称
        mapperField.setName(firstLetterLowerCase(domainObjectName) + "Mapper");
        // 将Field添加到对应的类中
        clazz.addField(mapperField);
        // 对应的类需要import Mapper类(使用全限定类名)
        clazz.addImportedType(new FullyQualifiedJavaType(mapperPackage + "." + domainObjectName + "Mapper"));
    }

    private String firstLetterLowerCase(String name) {
        char c = name.charAt(0);
        if (c >= 'A' && c <= 'Z') {
            String temp = String.valueOf(c);
            return name.replaceFirst(temp, temp.toLowerCase());
        }
        return name;
    }
}
