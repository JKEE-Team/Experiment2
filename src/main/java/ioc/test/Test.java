package demo.ioc.test;



import demo.ioc.iocfactory.ApplicationContext;
import demo.ioc.iocfactory_impl.ClassPathXMLApplicationContext;
import demo.ioc.stu.StudentService;
 
public class Test {//������
    public static void main(String[] args) { //ֱ����.xml�л�ȡ��ע��Ķ��󣬲��������ķ���
    	 ApplicationContext context = new ClassPathXMLApplicationContext("application.xml");//����xml�ļ�
            StudentService  stuServ = (StudentService) context.getBean("stu1");
            stuServ.getStudent().selfIntroDuction();
	}
}