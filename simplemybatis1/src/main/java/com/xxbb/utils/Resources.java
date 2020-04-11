package com.xxbb.utils;

import java.io.InputStream;

/**
 * @author xxbb
 */
public class Resources {
    /**
     * 通过静态方法获取配置文件的输入流对象
     * @param xmlName 配置文件名称
     * @return 流对象
     */
    public static InputStream getResourcesAsStream(String xmlName){
        return Resources.class.getClassLoader().getResourceAsStream(xmlName);
    }

}
