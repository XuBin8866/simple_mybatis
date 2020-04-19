package com.xxbb.test;

import com.xxbb.smybatis.session.SqlSession;
import com.xxbb.smybatis.session.SqlSessionFactory;
import com.xxbb.smybatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import test.mapper.UserMapper;

/**
 * @author xxbb
 */
public class TestMain {
    @Test
    public void test1() {
    }

    @Test
    public void testMain() {
        //构建sql工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
        SqlSession session = factory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        System.out.println(userMapper.getAll());
        int res = userMapper.updateUser("xxbb", 1);
        System.out.println(res);
    }


}
