package demo.ioc.iocfactory;

public interface ApplicationContext {
	
	public Object getBean(String name); //根据name获取bean
}
