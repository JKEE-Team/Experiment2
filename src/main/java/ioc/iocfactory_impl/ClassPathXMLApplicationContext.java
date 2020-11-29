package demo.ioc.iocfactory_impl;


import org.jdom2.input.SAXBuilder;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.Element;
import org.jdom2.xpath.XPath;
 
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
 
import demo.ioc.iocfactory.*;
//采用Jdom对application.xml文档进行解析
public class ClassPathXMLApplicationContext implements ApplicationContext{
	
	
	    private File file;
	    private Map map = new HashMap(); //用于存储创建的实例
	    //Key值就是配置文件的<bean>标签“id”,value值就是对应的实例
	    
	    public ClassPathXMLApplicationContext(String config_file) {
	        URL url = this.getClass().getClassLoader().getResource(config_file);
 
	        try {
				file = new File(url.toURI());
				XMLParsing();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	        
	    }
 
            private void XMLParsing() throws Exception {     
	    	SAXBuilder builder = new SAXBuilder();
	        Document doc = builder.build(file);
	   
			XPath xpath = XPath.newInstance("//bean");
	        List beans = xpath.selectNodes(doc);
	        Iterator i = beans.iterator();
	        while (i.hasNext()) {
	            Element bean = (Element) i.next();
	            String id = bean.getAttributeValue("id");//获取id
	            String cls = bean.getAttributeValue("class");//获取类名
	            Object obj = Class.forName(cls).newInstance();//利用java反射技术创建该类的实例
	            Method[] method = obj.getClass().getDeclaredMethods();
	            List<Element> list = bean.getChildren("property");
	            for (Element el : list) {
	                for (int n = 0; n < method.length; n++) {
	                    String name = method[n].getName();
	                    String temp = null;
	                    if (name.startsWith("set")) {
	                        temp = name.substring(3, name.length()).toLowerCase();
	                        if (el.getAttribute("name") != null) {
	                            if (temp.equals(el.getAttribute("name").getValue())) {
	                                method[n].invoke(obj, el.getAttribute("value").getValue());
	                            }
	                        } else {
	                            method[n].invoke(obj,map.get(el.getAttribute("ref").getValue()));
	                        }
	                    }
	                }
	            }
	            map.put(id, obj);
	        }
	    }
 
	    public Object getBean(String name) { //返回实例对象,继承自ApplicationContext
	        return map.get(name);
	    }
 
}