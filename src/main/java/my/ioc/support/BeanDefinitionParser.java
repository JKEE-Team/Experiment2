package my.ioc.support;


import my.ioc.BeanRegister;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class BeanDefinitionParser {
    //���õ�ɨ�����key
    public static final String SCAN_PACKAGE = "scanPackage";
    //����ע�����
    private BeanRegister register;
    public BeanDefinitionParser(BeanRegister register){
        this.register = register;
    }

    public void parse(Properties properties){
        //��ȡҪɨ��İ�
        String packageName = properties.getProperty(SCAN_PACKAGE);
        //ִ��ע��
        doRegister(packageName);
    }


    private void doRegister(String packageName) {
        //��ȡ�˰����µľ���·��
        URL url = getClass().getClassLoader().getResource("./"+packageName.replaceAll("\\.","/"));
        File dir = new File(url.getFile());
        //ѭ������  �ݹ��ҵ����е�java�ļ�
        for (File file:dir.listFiles()){
            if(file.isDirectory()){
                //�ļ���-->�ݹ����ִ��
                doRegister(packageName+"."+file.getName());
            }else {
                //�����ļ�������ȡ����  ����ʱ��ȡ������class�ļ�
                String className = packageName+"."+file.getName().replaceAll(".class","").trim();
                //����BeanDefinitionGenerator.generate(className)����,������
                //1.���������Ҫ�����ע��,�����id����BeanDefinition���Ϸ���
                //2.��������Ҫ�����ע��   ֱ�ӷ���null
                List<BeanDefinition> definitions = BeanDefinitionGenerator.generate(className);
                if(definitions == null)continue;
                //����������ע�᷽�������bean��Ϣ��ע��
                this.register.registBeanDefinition(definitions);
            }
        }

    }
}

