package com.xxbb.test;

import com.xxbb.smybatis.session.Configuration;
import com.xxbb.smybatis.session.SqlSession;
import com.xxbb.smybatis.session.SqlSessionFactory;
import com.xxbb.smybatis.session.SqlSessionFactoryBuilder;
import com.xxbb.smybatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;
import test.domain.User;
import test.mapper.UserMapper;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author xxbb
 */
public class TestMain {
    //sqlSession工厂类和数据库连接池类反序列化操作无法进行，类没有实现序列化接口
    // 执行该方法或报错：java.io.NotSerializableException: com.mysql.cj.jdbc.DatabaseMetaData
    @Test
    public void serializableTest() throws IOException, InterruptedException, ClassNotFoundException {
//        Configuration.pros.load(this.getClass().getClassLoader().getResourceAsStream("conf.properties"));
//        MyDataSource dataSource = MyDataSourceImpl.getInstance();
//        ObjectOutputStream oos = null;
//        ObjectInputStream ois = null;
//
//        oos = new ObjectOutputStream(new FileOutputStream(new File("d:/demo.text")));
//        ois = new ObjectInputStream(new FileInputStream(new File("d:/demo.text")));
//
//        oos.writeObject(dataSource);
//        oos.flush();
//
//        TimeUnit.SECONDS.sleep(1);
//        //不会调用该类的构造器
//        MyDataSource dataSource2 = (MyDataSourceImpl) ois.readObject();
//        //是否是同一个对象
//        System.out.println(System.identityHashCode(dataSource));
//        System.out.println(System.identityHashCode(dataSource2));
    }

    @Test
    public void ReflectTest() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Configuration.pros.load(this.getClass().getClassLoader().getResourceAsStream("conf.properties"));
        for (int i = 0; i < 5; i++) {
            Class<?> clazz = DefaultSqlSessionFactory.class;
            Constructor<?> constructor = clazz.getDeclaredConstructor(Configuration.class);
            constructor.setAccessible(true);
            Object obj = constructor.newInstance(new Configuration());
            System.out.println(obj);
        }
    }

    @Test
    public void createTest() {
        DefaultSqlSessionFactory factory = (DefaultSqlSessionFactory) new SqlSessionFactoryBuilder().build("conf.properties");
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 2100000000; i++) {
            factory.openSession();
        }
        long end1 = System.currentTimeMillis();
        System.out.println("new:" + (end1 - start1));
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 2100000000; i++) {
            //clone方法效率更低
            //factory.cloneSession();
        }
        long end2 = System.currentTimeMillis();
        System.out.println("clone:" + (end2 - start2));
    }

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
