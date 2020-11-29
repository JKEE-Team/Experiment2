package my.ioc.test;

import annotation.Controller;
import my.ioc.ApplicationContext;
import my.ioc.annotation.Autowire;
import my.ioc.support.AnnotationApplicationContext;

@Controller
public class MyController {
    @Autowire("myservice")
    private MyService myService;

    public void test(){
        myService.say("hello world");
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationApplicationContext("applicationContext.properties");
        MyController controller = context.getBean("my.ioc.test.MyController",MyController.class);
        controller.test();
    }
}


