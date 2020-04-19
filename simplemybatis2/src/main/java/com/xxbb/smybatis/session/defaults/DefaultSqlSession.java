package com.xxbb.smybatis.session.defaults;

import com.xxbb.smybatis.executor.Executor;
import com.xxbb.smybatis.executor.SimpleExecutor;
import com.xxbb.smybatis.mapping.MappedStatement;
import com.xxbb.smybatis.session.Configuration;
import com.xxbb.smybatis.session.SqlSession;

import java.util.List;

/**
 * 默认的sql会话实现类
 *
 * @author xxbb
 */
public class DefaultSqlSession implements SqlSession {
    /**
     * 配置类
     */
    private final Configuration configuration;
    /**
     * 执行器
     */
    private final Executor executor;


    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        executor = new SimpleExecutor(configuration);
    }

    /**
     * 查询单条记录
     * @param statement 封装好sql语句的唯一id
     * @param parameter 参数
     * @param <T> 泛型
     * @return 单条记录
     */
    @Override
    public <T> T selectOne(String statement, Object parameter) {
        List<T> results = this.selectList(statement, parameter);
        if (results.size() == 1) {
            return results.get(0);
        } else {
            throw new RuntimeException("查询结果出错：查询出多条数据，结果集长度：" + results.size());
        }
    }

    /**
     *  查询多条数据
     * @param statement 封装好sql语句的唯一id
     * @param parameter 参数
     * @param <E> 泛型
     * @return 封装好的List集合
     */
    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        MappedStatement mappedStatement = this.configuration.getMappedStatement(statement);
        return this.executor.doQuery(mappedStatement, parameter);
    }

    /**
     * 更新,和以下的insert和delete方法存在冗余
     * @param statement 封装好sql语句的唯一id
     * @param parameter 参数
     * @return 受影响的行数
     */
    @Override
    public int update(String statement, Object parameter) {
        MappedStatement mappedStatement = this.configuration.getMappedStatement(statement);
        return this.executor.doUpdate(mappedStatement, parameter);
    }

    @Override
    public int insert(String statement, Object parameter) {
        MappedStatement mappedStatement = this.configuration.getMappedStatement(statement);
        return this.executor.doUpdate(mappedStatement, parameter);
    }

    @Override
    public int delete(String statement, Object parameter) {
        MappedStatement mappedStatement = this.configuration.getMappedStatement(statement);
        return this.executor.doUpdate(mappedStatement, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return this.configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }


}
