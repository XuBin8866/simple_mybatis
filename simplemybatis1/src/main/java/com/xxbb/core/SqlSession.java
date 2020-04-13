package com.xxbb.core;

import com.xxbb.bean.Configuration;
import com.xxbb.bean.MapperStatement;
import com.xxbb.executor.Executor;
import com.xxbb.proxy.MapperProxy;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author xxbb
 */
@SuppressWarnings("unchecked")
public class SqlSession {
    /**
     * 配置对象
     */
    private Configuration configuration;
    /**
     * 执行器对象
     */
    private Executor executor;

    public SqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T getMapper(Class<T> clazz) {
        MapperProxy mapperProxy = new MapperProxy(this);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, mapperProxy);
    }

    public <T> T selectOne(String statementKey, Object[] args) {
        //获取查询对象
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementKey);
        List<T> resultList = executor.query(mapperStatement, args);
        if (resultList.size() > 1) {
            throw new RuntimeException("get result number > 1");
        } else {
            return resultList.get(0);
        }
    }

    public <T> List<T> selectList(String statementKey, Object[] args) {
        //获取查询对象
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementKey);
        return executor.query(mapperStatement, args);
    }

    public <T> T selectMap(Object[] args) {
        return null;
    }
}
