package top.icinghuan.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import top.icinghuan.demo.model.tables.generated.User;

/**
 * @author : xy
 * @date : 2018/9/5
 * Description :
 */
@Aspect
@Component
public class MyAop {

    @Pointcut("execution(public * top.icinghuan.demo.dao.impl.*.get*(..))")
    private void aopTest() {

    }

    @Before("aopTest()")
    public void before() {
        System.out.println("before");
    }

    @After("aopTest()" + "&&args(user)")
    public void after(User user) {
        System.out.println(user.getUserName());
        System.out.println("after");
    }

    @Around("aopTest()")
    public void around(ProceedingJoinPoint pdj) {
        System.out.println("before around");
        try {
            pdj.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("after around");
    }
}
