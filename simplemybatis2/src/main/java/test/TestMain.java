package test;

import com.xxbb.smybatis.session.SqlSession;
import com.xxbb.smybatis.session.SqlSessionFactory;
import com.xxbb.smybatis.session.SqlSessionFactoryBuilder;
import test.domain.User;
import test.mapper.UserMapper;

/**
 * 测试类
 *
 * @author xxbb
 */
public class TestMain {
    public static void main(String[] args) {
        //测试，可以看控制台打印结果
//        TestMain t = new TestMain();
//        //读取mapper文件的CRUD测试
//        //t.testMain();
//        //自动生成增删改语句并执行的测试
//        t.testUpdate();
//        t.testInsert();
//        t.testDelete();
    }

    public void testUpdate() {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
        SqlSession session = factory.openSession();
        User u = new User();
        u.setId(1);
        u.setUsername("xxbb");
        System.out.println("testUpdate：" + session.update(u));
    }

    public void testInsert() {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
        SqlSession session = factory.openSession();
        User u = new User();
        u.setId(24);
        u.setUsername("zzxx");
        u.setPassword("123456");
        u.setIfFreeze(1);
        System.out.println("testInsert：" + session.insert(u));
    }

    public void testDelete() {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
        SqlSession session = factory.openSession();
        User u = new User();
        u.setId(24);
        System.out.println("testDelete:" + session.delete(u));
    }

    public void testMain() {
        //构建sql工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
        SqlSession session = factory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        System.out.println("testMain.select：" + userMapper.getAll());
        System.out.println("testMain.update：" + userMapper.updateUser("xxbb", 1));
        System.out.println("testMain.insert:" + userMapper.insertUser(24, "zzxx", "123456", 1));
        System.out.println("testMain.delete: " + userMapper.deleteUser(24));

    }
}
