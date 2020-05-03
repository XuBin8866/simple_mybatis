package com.xxbb.test;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * @author xxbb
 */
public class Singleton implements Serializable {

    private static final long serialVersionUID = -7357258499538582501L;

    private static volatile Singleton instance;
    private String name;

    private Singleton() {
        //防止使用反射的方式创建实例
        if (null != instance) {
            throw new RuntimeException("");
        }
    }

    private Singleton(String name) {
        //防止使用反射的方式创建实例
        if (null != instance) {
            throw new RuntimeException("");
        }

        System.out.println("调用有参数的构造器");
        this.name = name;
    }

    public static Singleton getInstance(String name) {
        if (null == instance) {
            synchronized (Singleton.class) {
                if (null == instance) {
                    instance = new Singleton(name);
                }
            }
        }
        return instance;
    }

    /**
     * 提供readResolve()方法
     * 当JVM反序列化地恢复一个新对象时，
     * 系统会自动调用这个readResolve()方法返回指定好的对象，
     * 从而保证系统通过反序列化机制不会产生多个java对象
     *
     * @return 单例对象
     * @throws ObjectStreamException 异常
     */
    protected Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
