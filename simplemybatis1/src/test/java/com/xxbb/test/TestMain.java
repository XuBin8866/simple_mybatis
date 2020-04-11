package com.xxbb.test;

import java.io.InputStream;

/**
 * @author xxbb
 */
public class TestMain {
    public static void main(String[] args) {
        InputStream is=TestMain.class.getClassLoader().getResourceAsStream("mybatis_config.xml");

    }
}
