package com.enjoyu.admin.common.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 命名数据源动态切换
 */
public class NamingDynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return NamingDataSourceHolder.getDataSourceName();
    }

    static class NamingDataSourceHolder {
        private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

        public static void setDataSourceName(String dataSourceName) {
            contextHolder.set(dataSourceName);
        }

        public static String getDataSourceName() {
            return contextHolder.get();
        }

    }

}
