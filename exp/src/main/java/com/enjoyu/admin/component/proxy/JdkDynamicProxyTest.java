package com.enjoyu.admin.component.proxy;

import java.lang.reflect.Proxy;

public class JdkDynamicProxyTest {
    public static void main(String[] args) {
        saveProxyFile();
        HelloServiceImpl helloService = new HelloServiceImpl();
        Class<? extends HelloServiceImpl> serviceClass = helloService.getClass();
        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(helloService);
        HelloService hs = (HelloService) Proxy.newProxyInstance(serviceClass.getClassLoader(), serviceClass.getInterfaces(), jdkDynamicProxy);
        hs.say();
    }

    /**
     * 生成代理类文件在项目根目录下，对应类路径
     */
    public static void saveProxyFile() {
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
    }

}
