package com.xxbb.smybatis.session;

import com.xxbb.smybatis.binding.MapperRegistry;
import com.xxbb.smybatis.mapping.MappedStatement;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * mybatis框架核心配置类
 *
 * @author xxbb
 */
public class Configuration {
    /**
     * 数据库连接配置
     */
    public static Properties pros = new Properties();
    /**
     * mapper代理注册器
     */
    protected final MapperRegistry mapperRegistry = new MapperRegistry();
    /**
     * mapper中的sql信息
     */
    protected final Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    /**
     * 获取配置文件属性(属性不存再则返回空字符串"")
     *
     * @param key 键名
     * @return 键值
     */
    public static String getProperty(String key) {
        return getProperty(key, "");
    }

    /**
     * 获取配置文件属性(可指定属性不存在时的返回值)
     *
     * @param key          键名
     * @param defaultValue 属性不存在时的返回值
     * @return 键值
     */
    public static String getProperty(String key, String defaultValue) {
        return pros.containsKey(key) ? pros.getProperty(key) : defaultValue;
    }
}
