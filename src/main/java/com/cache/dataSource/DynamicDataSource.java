package com.cache.dataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源，动态获取数据源的实现
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
    
    /**
     * 用户返回当且切换到的数据库
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSource();//DynamicDataSourceHolder有获取和设置当前数据库的方法get & put
    }

}