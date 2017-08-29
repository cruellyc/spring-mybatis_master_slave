package com.cache.dataSource;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.util.Assert;

/**
 * Abstract {@link javax.sql.DataSource} implementation that routes {@link #getConnection()}
 * calls to one of various target DataSources based on a lookup key. The latter is usually
 * (but not necessarily) determined through some thread-bound transaction context.
 *
 * @author Juergen Hoeller
 * @since 2.0.1
 * @see #setTargetDataSources
 * @see #setDefaultTargetDataSource
 * @see #determineCurrentLookupKey()
 */
public abstract class AbstractRoutingDataSource extends AbstractDataSource implements InitializingBean {

    private Map<Object, Object> targetDataSources;//所有数据源，在xml中有相应的配置
    private boolean lenientFallback;
    private Object defaultTargetDataSource;//默认数据源，在xml中有相应的配置
    private Map<Object, DataSource> resolvedDataSources;//将targetDataSources值传入其中，做值的传递
    private DataSource resolvedDefaultDataSource;//同targetDataSources
    private DataSourceLookup dataSourceLookup;


    /**
     * Specify the map of target DataSources, with the lookup key as key.
     * The mapped value can either be a corresponding {@link javax.sql.DataSource}
     * instance or a data source name String (to be resolved via a
     * {@link #setDataSourceLookup DataSourceLookup}).
     * <p>The key can be of arbitrary type; this class implements the
     * generic lookup process only. The concrete key representation will
     * be handled by {@link #resolveSpecifiedLookupKey(Object)} and
     * {@link #determineCurrentLookupKey()}.
     */
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        this.targetDataSources = targetDataSources;
    }

    /**
     * Specify the default target DataSource, if any.
     * <p>The mapped value can either be a corresponding {@link javax.sql.DataSource}
     * instance or a data source name String (to be resolved via a
     * {@link #setDataSourceLookup DataSourceLookup}).
     * <p>This DataSource will be used as target if none of the keyed
     * {@link #setTargetDataSources targetDataSources} match the
     * {@link #determineCurrentLookupKey()} current lookup key.
     */
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        this.defaultTargetDataSource = defaultTargetDataSource;
    }

    /**
     * Specify whether to apply a lenient fallback to the default DataSource
     * if no specific DataSource could be found for the current lookup key.
     * <p>Default is "true", accepting lookup keys without a corresponding entry
     * in the target DataSource map - simply falling back to the default DataSource
     * in that case.
     * <p>Switch this flag to "false" if you would prefer the fallback to only apply
     * if the lookup key was {@code null}. Lookup keys without a DataSource
     * entry will then lead to an IllegalStateException.
     * @see #setTargetDataSources
     * @see #setDefaultTargetDataSource
     * @see #determineCurrentLookupKey()
     */
    public void setLenientFallback(boolean lenientFallback) {
        this.lenientFallback = lenientFallback;
    }

    /**
     * Set the DataSourceLookup implementation to use for resolving data source
     * name Strings in the {@link #setTargetDataSources targetDataSources} map.
     * <p>Default is a {@link JndiDataSourceLookup}, allowing the JNDI names
     * of application server DataSources to be specified directly.
     */
    public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
        this.dataSourceLookup = (dataSourceLookup != null ? dataSourceLookup : new JndiDataSourceLookup());
    }


    @Override
    public void afterPropertiesSet() {
        if (this.targetDataSources == null) {
            throw new IllegalArgumentException("Property 'targetDataSources' is required");
        }
     //*** 将targetDataSources的值传递给resolvedDataSources
        this.resolvedDataSources = new HashMap<Object, DataSource>(this.targetDataSources.size());
        for (Map.Entry<Object, Object> entry : this.targetDataSources.entrySet()) {
            Object lookupKey = resolveSpecifiedLookupKey(entry.getKey());
            DataSource dataSource = resolveSpecifiedDataSource(entry.getValue());
            this.resolvedDataSources.put(lookupKey, dataSource);
        }if (this.defaultTargetDataSource != null) {
            this.resolvedDefaultDataSource = resolveSpecifiedDataSource(this.defaultTargetDataSource);
        }
     //***
    }

    /**
     * Resolve the given lookup key object, as specified in the
     * {@link #setTargetDataSources targetDataSources} map, into
     * the actual lookup key to be used for matching with the
     * {@link #determineCurrentLookupKey() current lookup key}.
     * <p>The default implementation simply returns the given key as-is.
     * @param lookupKey the lookup key object as specified by the user
     * @return the lookup key as needed for matching
     */
    protected Object resolveSpecifiedLookupKey(Object lookupKey) {
        return lookupKey;
    }

    /**
     * Resolve the specified data source object into a DataSource instance.
     * <p>The default implementation handles DataSource instances and data source
     * names (to be resolved via a {@link #setDataSourceLookup DataSourceLookup}).
     * @param dataSource the data source value object as specified in the
     * {@link #setTargetDataSources targetDataSources} map
     * @return the resolved DataSource (never {@code null})
     * @throws IllegalArgumentException in case of an unsupported value type
     */
    protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException {
        if (dataSource instanceof DataSource) {
            return (DataSource) dataSource;
        }
        else if (dataSource instanceof String) {
            return this.dataSourceLookup.getDataSource((String) dataSource);
        }
        else {
            throw new IllegalArgumentException(
                    "Illegal data source value - only [javax.sql.DataSource] and String supported: " + dataSource);
        }
    }



    /**
     * Retrieve the current target DataSource. Determines the
     * {@link #determineCurrentLookupKey() current lookup key}, performs
     * a lookup in the {@link #setTargetDataSources targetDataSources} map,
     * falls back to the specified
     * {@link #setDefaultTargetDataSource default target DataSource} if necessary.
     * @see #determineCurrentLookupKey()
     */

  //通过调用determineCurrentLookupKey()获取当前数据源名称，并匹配到相应的数据源返回；
  //此方法在本类的 getConnection() 方法中调用，获取当前数据源的连接Connection，从而进行数据库操作
    protected DataSource determineTargetDataSource() {
        Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
        Object lookupKey = determineCurrentLookupKey();
        DataSource dataSource = this.resolvedDataSources.get(lookupKey);
        if (dataSource == null && (this.lenientFallback || lookupKey == null)) {
            dataSource = this.resolvedDefaultDataSource;
        }
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }

    /**
     * Determine the current lookup key. This will typically be
     * implemented to check a thread-bound transaction context.
     * <p>Allows for arbitrary keys. The returned key needs
     * to match the stored lookup key type, as resolved by the
     * {@link #resolveSpecifiedLookupKey} method.
     */

  //获取当前数据源的名称；在本类的子类中需要重写此方法
    protected abstract Object determineCurrentLookupKey();

}