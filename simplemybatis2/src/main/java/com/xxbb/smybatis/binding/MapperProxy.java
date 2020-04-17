package com.xxbb.smybatis.binding;

import com.xxbb.smybatis.constants.Constant;
import com.xxbb.smybatis.mapping.MappedStatement;
import com.xxbb.smybatis.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 代理类
 *
 * @author xxbb
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = -7861758496991319661L;

    private final SqlSession sqlSession;

    private final Class<T> mapperInterface;


    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return this.execute(method, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object execute(Method method, Object[] args) {
        //sql语句的唯一id
        String statementId = this.mapperInterface.getName() + "." + method.getName();
        //获取sql信息
        MappedStatement mappedStatement = this.sqlSession.getConfiguration().getMappedStatement(statementId);
        Object result = null;
        //根据mappedStatement提供的方法选择对应的CRUD方法
        //查询
        if (Constant.SqlType.SELECT.value().equals(mappedStatement.getSqlType())) {
            Class<?> returnType = method.getReturnType();
            if (!mappedStatement.getReturnType().equals(returnType.getName())) {
                throw new RuntimeException("方法需要的返回值和mappedStatement提供的返回值不同---->方法需要："
                        + returnType.getName() + "  mappedStatement提供：" + mappedStatement.getReturnType());
            }
            //返回值为集合类型
            if (Collection.class.isAssignableFrom(returnType)) {
                result = sqlSession.selectList(statementId, mappedStatement);
            } else {
                result = sqlSession.selectOne(statementId, mappedStatement);
            }

        }
        //更新
        if (Constant.SqlType.UPDATE.value().equals(mappedStatement.getSqlType())) {
            result = sqlSession.update(statementId, mappedStatement);
        }
        //插入
        if (Constant.SqlType.INSERT.value().equals(mappedStatement.getSqlType())) {
            result = sqlSession.insert(statementId, mappedStatement);
        }
        //删除
        if (Constant.SqlType.DELETE.value().equals(mappedStatement.getSqlType())) {
            result = sqlSession.delete(statementId, mappedStatement);
        }


        return result;
    }
}
