package test;

import com.xxbb.smybatis.session.SqlSession;
import com.xxbb.smybatis.session.SqlSessionFactory;
import com.xxbb.smybatis.session.SqlSessionFactoryBuilder;
import test.mapper.UserMapper;

/**
 * 测试类
 *
 * @author xxbb
 */
public class TestMain {
    public static void main(String[] args) {
        //构建sql工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
        SqlSession session = factory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        System.out.println(userMapper.getAll());
        int res = userMapper.updateUser("xxbb", 1);
        System.out.println(res);
    }
}
