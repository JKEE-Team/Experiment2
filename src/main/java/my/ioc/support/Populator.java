package my.ioc.support;


import java.lang.reflect.Field;
import java.util.Map;
import my.ioc.annotation.Autowire;

public class Populator {

    public Populator(){
    }

    public void populate(Map<String,Object> instanceMapping){
        //����Ҫ�ж�ioc��������û�ж���
        if(instanceMapping.isEmpty())return;

        //ѭ������ÿһ�������еö���
        for (Map.Entry<String,Object> entry:instanceMapping.entrySet()){
            //��ȡ������ֶ�
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field:fields){
                if(!field.isAnnotationPresent(Autowire.class))continue;
                Autowire autowire = field.getAnnotation(Autowire.class);
                //��ȥ�ֶ�Ҫע���id value  Ϊ��������  �ӿ����Զ�ע��
                String id = autowire.value();
                if("".equals(id))id = field.getType().getName();
                field.setAccessible(true);
                try {
                    //����ע��
                    field.set(entry.getValue(),instanceMapping.get(id));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

