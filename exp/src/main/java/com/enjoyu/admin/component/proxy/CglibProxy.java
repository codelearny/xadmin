package com.enjoyu.admin.component.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib proxy before");
        Object invoke = methodProxy.invokeSuper(o, objects);
        System.out.println("cglib proxy after");
        return invoke;
    }
}
