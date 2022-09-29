package com.enjoyu.admin.component.proxy;

public class HelloServiceImpl implements HelloService {
    @Override
    public void say() {
        System.out.println("hello");
        echo();
    }

    public void echo() {
        System.out.println("echo");
    }
}
