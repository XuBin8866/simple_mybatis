package com.xxbb.smybatis.session.defaults;

import com.xxbb.smybatis.session.Configuration;
import com.xxbb.smybatis.session.SqlSession;

import java.sql.Connection;
import java.util.List;

/**
 * @author xxbb
 */
public class DefaultSqlSession implements SqlSession {
    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        return null;
    }

    @Override
    public int update(String statement, Object parameter) {
        return 0;
    }

    @Override
    public int insert(String statement, Object parameter) {
        return 0;
    }

    @Override
    public int delete(String statement, Object parameter) {
        return 0;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return null;
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public Connection getConnection() {
        return null;
    }
}
