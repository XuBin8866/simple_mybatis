package com.xxbb.test;

import com.xxbb.bean.Configuration;
import com.xxbb.utils.Resources;
import com.xxbb.utils.XmlConfigBuilder;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author xxbb
 */
public class TestMain {
    @Test
    public void testXml() {
        InputStream is= Resources.getResourcesAsStream("mybatis_config.xml");
        System.out.println(is);
        XmlConfigBuilder configBuilder=new XmlConfigBuilder();
        Configuration configuration=configBuilder.parse(is);
        System.out.println(configuration);

    }
}
