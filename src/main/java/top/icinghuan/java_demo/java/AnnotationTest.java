package top.icinghuan.java_demo.java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author : xy
 * @date : 2018/7/25
 * Description :
 */
public class AnnotationTest {

    @MyAnnotation(name = "Hello World")
    public void execute() {
        System.out.println("do something");
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<AnnotationTest> clazz = AnnotationTest.class;
        Method method = clazz.getMethod("execute");
        if (method.isAnnotationPresent(MyAnnotation.class)) {
            MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
            AnnotationTest test = new AnnotationTest();
            method.invoke(test, new Object[]{});

            String name = myAnnotation.name();
            System.out.println(name);
        }
    }
}
