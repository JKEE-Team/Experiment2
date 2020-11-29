package my.ioc;


import java.util.Map;

public interface ApplicationContext {
    /**
     * ����id��ȡbean
     * @param id
     * @return
     */
    Object getBean(String id);

    /**
     * ����id��ȡ�ض����͵�bean,���ǿת
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    <T>T getBean(String id,Class<T> clazz);

    /**
     * ��ȡ�����ڵ�����bean����
     * @return
     */
    Map<String,Object> getBeans();
}

