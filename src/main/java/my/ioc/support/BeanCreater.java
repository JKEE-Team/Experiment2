package my.ioc.support;


import my.ioc.BeanRegister;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class BeanCreater {

    private BeanRegister register;
    public BeanCreater(BeanRegister register){
        this.register = register;
    }

    public void create(List<BeanDefinition> bds){
        for (BeanDefinition bd:bds){
            doCreate(bd);
        }
    }

    private void doCreate(BeanDefinition bd) {
        Object instance = bd.getInstance();
        this.register.registInstanceMapping(bd.getId(),instance);
    }
}

