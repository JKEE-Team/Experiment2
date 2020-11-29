package my.ioc.support;



import my.ioc.annotation.Component;
import annotation.Controller;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinitionGenerator {

    public static List<BeanDefinition> generate(String className){
        try {
            Class clazz = Class.forName(className);
            String[] ids = generateIds(clazz);
            if(ids==null)return null;
            List<BeanDefinition> list = new ArrayList<BeanDefinition>();
            for (String id:ids){
                list.add(new BeanDefinition(id,clazz));
            }
            return list;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * ����id����
     * 1.����@Controller ע�⵫��ע��valueû��ֵ,@Controllerһ��û��
     * �ӿڶ���,�����ȫ����Ϊid����ids����Ϊ1 
     * 2.@Component û��value  ��ȡ���е�ʵ�ֵĽӿ�,�ӿ���Ϊid,����ids����
     * ������ʵ�ֵĽӿڸ���
     * 3.@Component ��value ����id=value
     * 4.��������Ҫʵ������ע��  null
     */
    private static String[] generateIds(Class clazz) {
        String[] ids = null;
        if (clazz.isAnnotationPresent(Controller.class)) {
            ids = new String[]{clazz.getName()};
        } else if (clazz.isAnnotationPresent(Component.class)) {
            Component component = (Component) clazz.getAnnotation(Component.class);
            String value = component.value();
            if (!"".equals(value)) {
                ids = new String[]{value};
            } else {
                Class<?>[] interfaces = clazz.getInterfaces();
                ids = new String[interfaces.length];
                //��������ʵ���˽ӿڣ����ýӿڵ�������Ϊid
                for (int i = 0; i < interfaces.length; i++){
                    ids[i] = interfaces[i].getName();
                }
                return ids;
            }
        }
        return ids;
    }

}
