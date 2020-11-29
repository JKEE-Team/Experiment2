package UI;

import annotation.Controller;
import annotation.RequestMapping;
import view.View;
/**
 * ʹ��Controllerע���עLoginUI��
 */
@Controller
public class LoginUI {
    //ʹ��RequestMappingע��ָ��forward1�����ķ���·��
    @RequestMapping("LoginUI/Login2")
    public View forward1(){
        //ִ����forward1����֮�󷵻ص���ͼ
    	System.out.println("---forward1 excecuted---");
        return new View("/login2.jsp");
    }
    @RequestMapping("LoginUI/Login3")
    public View forward2(){
        //ִ����forward2����֮�󷵻ص���ͼ
    	System.out.println("---forward2 excecuted---");
        return new View("/login3.jsp");
    }
}