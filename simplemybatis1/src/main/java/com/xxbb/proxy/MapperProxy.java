package com.xxbb.proxy;

import com.xxbb.core.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * @author xxbb
 */
public class MapperProxy implements InvocationHandler {
    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //判断返回值类型
        Class<?> clazz = method.getReturnType();

        if (Collection.class.isAssignableFrom(clazz)) {
            String statementKey = method.getDeclaringClass().getName() + "." + method.getName();
            return sqlSession.selectList(statementKey, args);
            //返回值为集合类型
        } else if (Map.class.isAssignableFrom(clazz)) {
            //返回值为Map类型
            return null;
        } else {
            //返回值为单条数据
            String statementKey = method.getDeclaringClass().getName() + "." + method.getName();
            return sqlSession.selectOne(statementKey, args);
        }
    }
}
