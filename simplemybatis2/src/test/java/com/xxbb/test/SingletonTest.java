package com.xxbb.test;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xxbb
 */
public class SingletonTest {
    public static void main(String[] args) throws IOException {
        Singleton s = Singleton.getInstance("Hello");
        System.out.println("Wolf对象创建完成");

        Singleton s2;

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream("a.txt"));
            ois = new ObjectInputStream(new FileInputStream("a.txt"));

            oos.writeObject(s);
            oos.flush();

            TimeUnit.SECONDS.sleep(1);

            //不会调用该类的构造器
            s2 = (Singleton) ois.readObject();

            //是否是同一个对象
            System.out.println(System.identityHashCode(s));
            System.out.println(System.identityHashCode(s2));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
    }
}
