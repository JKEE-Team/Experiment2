package demo.ioc.test;



import demo.ioc.iocfactory.ApplicationContext;
import demo.ioc.iocfactory_impl.ClassPathXMLApplicationContext;
import demo.ioc.stu.StudentService;
 
public class Test {//测试类
    public static void main(String[] args) { //直接在.xml中获取已注册的对象，并调用它的方法
    	 ApplicationContext context = new ClassPathXMLApplicationContext("application.xml");//加载xml文件
            StudentService  stuServ = (StudentService) context.getBean("stu1");
            stuServ.getStudent().selfIntroDuction();
	}
}