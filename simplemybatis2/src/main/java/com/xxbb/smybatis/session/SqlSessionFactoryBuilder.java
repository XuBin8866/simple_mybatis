package com.xxbb.smybatis.session;

import com.xxbb.smybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * 构建者模式创建sqlSession工厂对象
 *
 * @author xxbb
 */
public class SqlSessionFactoryBuilder {
    /**
     * 读取配置文件构建SqlSessionFactory工厂
     * 将配置文件的解析成输入流的工作也放到该方法中
     *
     * @param fileName 配置文件名
     * @return 工厂对象
     */
    public SqlSessionFactory build(String fileName) {
        InputStream is = SqlSessionFactoryBuilder.class.getClassLoader().getResourceAsStream(fileName);
        return build(is);
    }

    public SqlSessionFactory build(InputStream inputStream) {
        try {
            Configuration.pros.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DefaultSqlSessionFactory.getInstance(new Configuration());
    }


}
