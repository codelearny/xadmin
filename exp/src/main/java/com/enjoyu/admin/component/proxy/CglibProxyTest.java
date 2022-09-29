package com.enjoyu.admin.component.proxy;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class CglibProxyTest {
    public static void main(String[] args) {
        saveProxyFile();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloServiceImpl.class);
        enhancer.setCallback(new CglibProxy());
        HelloServiceImpl hs = (HelloServiceImpl) enhancer.create();
        hs.say();
    }

    /**
     * 生成代理类文件在项目根目录下，对应类路径
     */
    public static void saveProxyFile() {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./cglib");
    }
}
