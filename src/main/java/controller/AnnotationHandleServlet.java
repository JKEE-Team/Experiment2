package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import annotation.Controller;
import annotation.RequestMapping;
import util.BeanUtils;
import util.RequestMapingMap;
import util.ScanClassUtil;
import context.WebContext;
import view.DispatchActionConstant;
import view.View;

/**
 * <p>ClassName: AnnotationHandleServlet<p>
 * <p>Description: AnnotationHandleServlet��Ϊ�Զ���ע��ĺ��Ĵ������Լ��������Ŀ��ҵ�񷽷������û�����<p>
 * @version 1.0 V
 */
@WebServlet(urlPatterns="*.do",initParams = {
        @WebInitParam(name = "basePackage", value = "controller,UI")
        },loadOnStartup=1)
public class AnnotationHandleServlet extends HttpServlet {

    private String pareRequestURI(HttpServletRequest request){
        String path = request.getContextPath()+"/";
        String requestUri = request.getRequestURI();
        String midUrl = requestUri.replaceFirst(path, "");
        String lasturl = midUrl.substring(0, midUrl.lastIndexOf("."));
        return lasturl;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.excute(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.excute(request, response);
    }

    private void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //����ǰ�߳���HttpServletRequest����洢��ThreadLocal�У��Ա���Controller����ʹ��
        WebContext.requestHodler.set(request);
        //����ǰ�߳���HttpServletResponse����洢��ThreadLocal�У��Ա���Controller����ʹ��
        WebContext.responseHodler.set(response);
        //����url
        String lasturl = pareRequestURI(request);
        //��ȡҪʹ�õ���
        Class<?> clazz = RequestMapingMap.getRequesetMap().get(lasturl);
        //�������ʵ��
        Object classInstance = BeanUtils.instanceClass(clazz);
        //��ȡ���ж���ķ���
        Method [] methods = BeanUtils.findDeclaredMethods(clazz);
        Method method = null;
        for(Method m:methods){//ѭ����������ƥ��ķ�������ִ��
            if(m.isAnnotationPresent(RequestMapping.class)){
                String anoPath = m.getAnnotation(RequestMapping.class).value();
                if(anoPath!=null && !"".equals(anoPath.trim()) && lasturl.equals(anoPath.trim())){
                    //�ҵ�Ҫִ�е�Ŀ�귽��
                    method = m;
                    break;
                }
            }
        }
        try {
            if(method!=null){
                //ִ��Ŀ�귽�������û�����
                Object retObject = method.invoke(classInstance);
                System.out.println("---��תjsp!---");
                //��������з���ֵ����ô�ͱ�ʾ�û���Ҫ������ͼ
                if (retObject!=null) {
                    View view = (View)retObject;
                    //�ж�Ҫʹ�õ���ת��ʽ
                    if(view.getDispathAction().equals(DispatchActionConstant.FORWARD)){
                        //ʹ�÷���������ת��ʽ
                    	System.out.println("---s����ת!---");
                    	System.out.println(view.getUrl());
                        request.getRequestDispatcher(view.getUrl()).forward(request, response);
                    }else if(view.getDispathAction().equals(DispatchActionConstant.REDIRECT)){
                        //ʹ�ÿͻ�����ת��ʽ
                    	System.out.println("---c����ת!---");
                        response.sendRedirect(request.getContextPath()+view.getUrl());
                    }else{
                        request.getRequestDispatcher(view.getUrl()).forward(request, response);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        /**
         * ��д��Servlet��init������һ��Ҫ�ǵõ��ø����init������
         * ������service/doGet/doPost������ʹ��getServletContext()������ȡServletContext����ʱ
         * �ͻ����java.lang.NullPointerException�쳣
         */
        super.init(config);
        System.out.println("---��ʼ����ʼ---");
        //��ȡweb.xml�����õ�Ҫɨ��İ�
        String basePackage = config.getInitParameter("basePackage");
        //��������˶���������磺<param-value>me.gacl.web.controller,me.gacl.web.UI</param-value>
        if (basePackage.indexOf(",")>0) {
        	System.out.println("---ɨ��ɹ�---");
            //�����Ž��зָ�
            String[] packageNameArr = basePackage.split(",");
            for (String packageName : packageNameArr) {
                initRequestMapingMap(packageName);
            }
        }else {
            initRequestMapingMap(basePackage);
        }
        System.out.println("----��ʼ������---");
    }

    
    private void initRequestMapingMap(String packageName){
        Set<Class<?>> setClasses =  ScanClassUtil.getClasses(packageName);
        for (Class<?> clazz :setClasses) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                Method [] methods = BeanUtils.findDeclaredMethods(clazz);
                for(Method m:methods){//ѭ����������ƥ��ķ�������ִ��
                    if(m.isAnnotationPresent(RequestMapping.class)){
                        String anoPath = m.getAnnotation(RequestMapping.class).value();
                        if(anoPath!=null && !"".equals(anoPath.trim())){
                            if (RequestMapingMap.getRequesetMap().containsKey(anoPath)) {
                            	System.out.print(" duplicated mapping: ");
                            	System.out.println(RequestMapingMap.getRequesetMap());
                                throw new RuntimeException("RequestMappingӳ��ĵ�ַ�������ظ���");
                            }
                            RequestMapingMap.put(anoPath, clazz);
                            System.out.println(clazz);
                        }
                    }
                }
            }
        }
    }
}
