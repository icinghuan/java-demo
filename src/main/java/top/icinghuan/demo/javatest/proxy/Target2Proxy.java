package top.icinghuan.demo.javatest.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author : xy
 * @date : 2018/9/20
 * Description :
 */
public class Target2Proxy implements InvocationHandler {
    Target2 target;

    public Target2Proxy(Target2 target) {
        this.target = target;
    }

    public static Object bind(Target2 target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), new Target2Proxy(target));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object object = method.invoke(target, args);
        System.out.println("after");
        return object;
    }
}
