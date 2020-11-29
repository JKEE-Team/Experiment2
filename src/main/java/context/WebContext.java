package context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * WebContext��Ҫ�������洢��ǰ�߳��е�HttpServletRequest��HttpServletResponse
 * ����ĵط���Ҫʹ��HttpServletRequest��HttpServletResponse���Ϳ���ͨ��requestHodler��responseHodler��ȡ
 **/
public class WebContext {

    public static ThreadLocal<HttpServletRequest> requestHodler = new ThreadLocal<HttpServletRequest>();
    public static ThreadLocal<HttpServletResponse> responseHodler = new ThreadLocal<HttpServletResponse>();

    public HttpServletRequest getRequest(){
           return requestHodler.get();
    }

    public HttpSession getSession(){
           return requestHodler.get().getSession();
    }

    public ServletContext getServletContext(){
           return requestHodler.get().getSession().getServletContext();
    }

    public HttpServletResponse getResponse(){
        return responseHodler.get();
    }
}