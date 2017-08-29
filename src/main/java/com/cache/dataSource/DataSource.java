package com.cache.dataSource;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库annotation定义
 * @DataSource('master') / @DataSource('slave')
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSource {
    String value();//唯一值，所以注释中没有写@DataSource(value = 'master');也可以写成 String value() defalut "master";即默认访问主数据库
}
