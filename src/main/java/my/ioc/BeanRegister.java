package my.ioc;


import my.ioc.support.BeanDefinition;

import java.util.List;

public interface BeanRegister {
    /**
     * �򹤳���ע��BeanDefinition
     * @param bds
     */
    void registBeanDefinition(List<BeanDefinition> bds);

    /**
     * �򹤳���ע��beanʵ������
     * @param id
     * @param instance
     */
    void registInstanceMapping(String id,Object instance);
}

