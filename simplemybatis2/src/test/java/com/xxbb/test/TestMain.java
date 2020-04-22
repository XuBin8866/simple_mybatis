package com.xxbb.test;

import com.xxbb.smybatis.session.SqlSession;
import com.xxbb.smybatis.session.SqlSessionFactory;
import com.xxbb.smybatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import test.domain.User;
import test.mapper.UserMapper;

/**
 * @author xxbb
 */
public class TestMain {

    @Test
    public void testUpdate() {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
        SqlSession session = factory.openSession();
        User u = new User();
        u.setId(1);
        u.setUsername("xxbb");
        System.out.println(session.update(u));
    }

    @Test
    public void testInsert() {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
        SqlSession session = factory.openSession();
        User u = new User();
        u.setId(24);
        u.setUsername("zzxx");
        u.setPassword("123456");
        u.setIfFreeze(1);
        System.out.println(session.insert(u));
    }

    @Test
    public void testDelete() {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
        SqlSession session = factory.openSession();
        User u = new User();
        u.setId(24);
        System.out.println(session.delete(u));
    }

    @Test
    public void testMain() {
        //构建sql工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
        SqlSession session = factory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        System.out.println(userMapper.getAll());
        System.out.println("update：" + userMapper.updateUser("xxbb", 1));
        System.out.println("insert:" + userMapper.insertUser(24, "zzxx", "123456", 1));
        System.out.println("delete: " + userMapper.deleteUser(24));

    }


}
