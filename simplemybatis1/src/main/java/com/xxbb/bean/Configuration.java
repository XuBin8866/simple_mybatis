package com.xxbb.bean;

import java.util.Map;

/**
 * 封装配置文件和mapper文件的信息
 * @author xxbb
 */
public class Configuration {
    /**
     * 封装xml配置文件中的数据库连接信息
     */
    private Environment environment;
    /**
     * 封装mapper文件的信息
     */
    private Map<String,MapperStatement> mapperStatementMap;

    public Configuration() {
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Map<String, MapperStatement> getMapperStatementMap() {
        return mapperStatementMap;
    }

    public void setMapperStatementMap(Map<String, MapperStatement> mapperStatementMap) {
        this.mapperStatementMap = mapperStatementMap;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "environment=" + environment +
                ", mapperStatementMap=" + mapperStatementMap +
                '}';
    }
}
