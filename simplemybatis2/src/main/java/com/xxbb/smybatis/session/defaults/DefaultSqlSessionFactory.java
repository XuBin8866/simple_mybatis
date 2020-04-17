package com.xxbb.smybatis.session.defaults;

import com.xxbb.smybatis.constants.Constant;
import com.xxbb.smybatis.session.Configuration;
import com.xxbb.smybatis.session.SqlSession;
import com.xxbb.smybatis.session.SqlSessionFactory;
import com.xxbb.smybatis.utils.CommonUtils;
import com.xxbb.smybatis.utils.XmlParseUtils;

import java.io.File;
import java.util.Objects;

/**
 * @author xxbb
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    /**
     * 配置对象
     */
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;

    }

    @Override
    public SqlSession openSession() {
        return null;
    }

    /**
     * 解析mapper.xml中的信息封装进Configuration对象的mappedStatementMap中
     *
     * @param dirName
     */
    public void loadMappersInfo(String dirName) {
        String resource = Objects.requireNonNull
                (DefaultSqlSessionFactory.class.getClassLoader().getResource(dirName)).getPath();
        File mapperDir = new File(resource);
        //判断该路径是否为文件夹
        if (mapperDir.isDirectory()) {
            //获取文件夹下所有文件
            File[] mappers = mapperDir.listFiles();
            //非空判断
            if (CommonUtils.isNotEmpty(mappers)) {
                for (File file : mappers) {
                    //如果还存在文件夹则继续获取
                    if (file.isDirectory()) {
                        loadMappersInfo(dirName + "/" + file.getName());
                    } else if (file.getName().endsWith(Constant.MAPPER_FILE_SUFFIX)) {
                        //获取以.xml为后缀的文件,存入Configuration对象的mappedStatementMap中
                        //并注册一个该mapper接口类的代理工厂
                        XmlParseUtils.mapperParser(file, this.configuration);
                    }
                }
            }

        }
    }
}
