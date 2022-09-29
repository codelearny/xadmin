package com.enjoyu.admin.component.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkDynamicProxy implements InvocationHandler {

    private final Object target;

    public JdkDynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk dynamic proxy before");
        Object invoke = method.invoke(target, args);
        System.out.println("jdk dynamic proxy after");
        return invoke;
    }
}
