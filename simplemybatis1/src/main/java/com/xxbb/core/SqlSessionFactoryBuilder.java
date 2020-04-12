package com.xxbb.core;



import com.xxbb.bean.Configuration;
import com.xxbb.utils.XmlConfigBuilder;

import java.io.InputStream;

/**
 * @author xxbb
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream is){
        Configuration configuration=new XmlConfigBuilder().parse(is);
        
        return new SqlSessionFactory(configuration);
    }

}
