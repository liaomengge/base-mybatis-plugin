Base Mybatis Plugin
======================================

[![github stars](https://img.shields.io/github/stars/liaomengge/base-mybatis-plugin.svg)](https://github.com/liaomengge/base-mybatis-plugin/stargazers)
[![github forks](https://img.shields.io/github/forks/liaomengge/base-mybatis-plugin.svg)](https://github.com/liaomengge/base-mybatis-plugin/network)
[![maven center](https://img.shields.io/maven-central/v/com.github.liaomengge/base-mybatis-plugin.svg)](https://search.maven.org/search?q=g:com.github.liaomengge%20AND%20a:base-mybatis-plugin)
[![github license](https://img.shields.io/github/license/liaomengge/base-mybatis-plugin.svg)](https://github.com/liaomengge/base-mybatis-plugin/blob/master/LICENSE)

1. 概述

   针对mybatis自动生成新增lombok插件，service插件（自动生成mapper对应的service），以及针对数据库UNSIGNED类型的字段，对应Java属性字段类型自动升级（避免插入字段值溢出）

2. 使用

   **Maven Generator**

   > - 引用如下maven plugin即可
   >
   > ```xml
   > <build>
   >         <plugin>
   >             <groupId>org.mybatis.generator</groupId>
   >             <artifactId>mybatis-generator-maven-plugin</artifactId>
   >             <version>1.4.0</version>
   >             <configuration>
   >                 <!--mybatis generator插件配置文件位置，默认值${basedir}/src/main/resources/generatorConfig.xml-->
   >                 <configurationFile>src/main/resources/mybatis/generator/generatorConfig.xml</configurationFile>
   >                 <overwrite>true</overwrite>
   >                 <verbose>true</verbose>
   >             </configuration>
   >             <dependencies>
   >                 <dependency>
   >                     <groupId>org.mybatis.generator</groupId>
   >                     <artifactId>mybatis-generator-core</artifactId>
   >                     <version>1.4.0</version>
   >                 </dependency>
   >                 <dependency>
   >                     <groupId>mysql</groupId>
   >                     <artifactId>mysql-connector-java</artifactId>
   >                     <version>${mysql-connector-java.version}</version>
   >                 </dependency>
   >                 <dependency>
   >                     <groupId>com.github.liaomengge</groupId>
   >                     <artifactId>base-mybatis-plugin</artifactId>
   >                     <version>1.1.0</version>
   >                 </dependency>
   >                 <dependency>
   >                     <groupId>tk.mybatis</groupId>
   >                     <artifactId>mapper</artifactId>
   >                     <version>4.1.5</version>
   >                 </dependency>
   >             </dependencies>
   >         </plugin>
   >     </plugins>
   > </build>
   > ```

   **Gradle Generator**

   > - 直接使用[base-mybatis-generator-plugin](https://github.com/liaomengge/base-mybatis-generator-plugin)插件即可