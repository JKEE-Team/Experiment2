package my.ioc.support;


import my.ioc.ApplicationContext;
import my.ioc.BeanRegister;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class AnnotationApplicationContext implements ApplicationContext, BeanRegister {

    //����create������ʵ��
    private Map<String,Object> instanceMapping = new ConcurrentHashMap<String, Object>();

    //��������bean����Ϣ,��Ҫ����bean������  id����Ϣ
    private List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
    //�����ļ���config,����Ϊ�˼�����ʹ��properties�ļ�
    private Properties config = new Properties();

    public AnnotationApplicationContext(String location){
        InputStream is = null;
        try{
            //1����λ
            is = this.getClass().getClassLoader().getResourceAsStream(location);

            //2�����������ļ�
            System.out.println("hhhhhhhhh");
            config.load(is);
            System.out.println("1111");
            //3��ע�� ��ע����Ϣ����this.beanDefinitions������Controller��AutoWireע��
            register();

            //4��ʵ���� ��ʵ������this.instanceMapping
            createBean();

            //5��ע�� ����AutoWireע�ͻ�ȡ��Ҫע����࣬����instanceMapping��kvע��
            populate();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ���þ���ί�ɵ�ע�������ע��
     */
    private void populate() {
        Populator populator = new Populator();
        populator.populate(instanceMapping);
    }

    /**
     * ���þ���Ĵ������󴴽�bean
     */
    private void createBean() {
        BeanCreater creater = new BeanCreater(this);
        creater.create(beanDefinitions);
    }

    /**
     * ���þ����ע�����ע��bean��Ϣ
     */
    private void register() {
        BeanDefinitionParser parser = new BeanDefinitionParser(this);
        parser.parse(config);
    }

    public Properties getConfig() {
        return this.config;
    }
//    ApplicationContext
    public Object getBean(String id) {
        return instanceMapping.get(id);
    }

    public <T> T getBean(String id, Class<T> clazz) {
        return (T)instanceMapping.get(id);
    }

    public Map<String, Object> getBeans() {
        return instanceMapping;
    }

//      BeanRegister
    public void registBeanDefinition(List<BeanDefinition> bds) {
        beanDefinitions.addAll(bds);
    }

    public void registInstanceMapping(String id, Object instance) {
        instanceMapping.put(id,instance);
    }

}

