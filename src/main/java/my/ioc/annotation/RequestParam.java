package my.ioc.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)//�����ڷ����Ĳ�������
@Documented
public @interface RequestParam {
    String value() default "";
}
