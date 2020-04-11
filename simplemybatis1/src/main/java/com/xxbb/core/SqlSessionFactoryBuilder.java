package com.xxbb.core;

import com.xxbb.bean.Configuration;

import java.io.InputStream;

/**
 * @author xxbb
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream is){



        Configuration configuartion=null;
        return new SqlSessionFactory(configuartion);
    }
}
