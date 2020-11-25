package my.ioc.test;

import my.ioc.annotation.Component;

@Component("myservice")
public class MyService {
    public void say(String hello_world) {
        System.out.println(hello_world);
    }
}
