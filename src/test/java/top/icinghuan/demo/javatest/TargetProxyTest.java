package top.icinghuan.demo.javatest;

import org.junit.Test;
import top.icinghuan.demo.javatest.proxy.Target2;
import top.icinghuan.demo.javatest.proxy.Target2Impl;
import top.icinghuan.demo.javatest.proxy.Target2Proxy;

import java.lang.reflect.Proxy;

/**
 * @author : xy
 * @date : 2018/9/20
 * Description :
 */
public class TargetProxyTest {
    @Test
    public void test() {
        Target2 target = new Target2Impl();
        target = (Target2) Target2Proxy.bind(target);
        target.execute();
    }
}
