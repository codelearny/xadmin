package com.enjoyu.admin.common.bundle;

import org.junit.jupiter.api.Test;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleTest {

    @Test
    public void test() {
        ResourceBundle res = ResourceBundle.getBundle("test.config");
        Enumeration<String> keys = res.getKeys();
        while (keys.hasMoreElements()) {
            String s = keys.nextElement();
            System.out.println("key - " + s);
        }
        String name = res.getString("NAME");
        System.out.println(name);
    }

    @Test
    public void testLocale() {
        ResourceBundle res = ResourceBundle.getBundle("test.config",Locale.CHINA);
        String name = res.getString("NAME");
        System.out.println(name);
    }

}
