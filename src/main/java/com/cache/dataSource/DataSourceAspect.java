package com.cache.dataSource;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 数据源动态选择切面
 *
 */
public class DataSourceAspect {
    private Logger log = Logger.getLogger(DataSourceAspect.class);
    public void before(JoinPoint point){
        log.info("DataSourceAspect before() start");
        Object target = point.getTarget();
        String method = point.getSignature().getName();
        Class<?>[] classz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            if ( m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource data = m.getAnnotation(DataSource.class);//获取访问mapper中的注释
                DynamicDataSourceHolder.putDataSource(data.value());//获取注释中的value值，确定访问的数据源
                if(log.isDebugEnabled()){
                    log.debug("DataSourceAspect:======================="+data.value());
                }
            }
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
        }
    }
}