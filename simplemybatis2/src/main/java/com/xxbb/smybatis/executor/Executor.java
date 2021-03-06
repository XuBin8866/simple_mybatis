package com.xxbb.smybatis.executor;

import com.xxbb.smybatis.mapping.MappedStatement;

import java.util.List;

/**
 * 直接进行数据库操作的接口
 *
 * @author xxbb
 */
public interface Executor {
    /**
     * 查询操作
     *
     * @param <E>             泛型，和mappedStatement中的returnType有关
     * @param mappedStatement sql信息对象
     * @param parameter       参数
     * @return 封装好的结果集
     */
    <E> List<E> doQuery(MappedStatement mappedStatement, Object parameter);

    /**
     * 更新操作，增删改
     *
     * @param mappedStatement sql信息对象
     * @param parameter       参数
     * @return 返回值
     */
    int doUpdate(MappedStatement mappedStatement, Object parameter);
}
