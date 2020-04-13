package com.xxbb.utils;

import java.lang.reflect.Method;

/**
 * @author xxbb
 */
public class ReflectUtils {
    /**
     * 通过数据库字段名反射调用其po类对应的set方法
     *
     * @param object     po类对象
     * @param columnName 字段名
     * @param value      字段的值
     */
    public static void invokeSet(Object object, String columnName, Object value) {
        Class<?> clazz = object.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod("set" + StringUtils.columnNameToMethodName(columnName), value.getClass());
            method.invoke(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
