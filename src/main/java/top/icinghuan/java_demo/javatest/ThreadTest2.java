package top.icinghuan.java_demo.javatest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : xy
 * @date : 2018/8/17
 * Description :
 */
public class ThreadTest2 implements Runnable {

    private static volatile AtomicInteger count = new AtomicInteger(0); //使用 volatile 修饰基本数据内存不能保证原子性

    //private static AtomicInteger count = new AtomicInteger() ;

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            count.getAndIncrement();
            //count.incrementAndGet() ;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadTest2 threadTest2 = new ThreadTest2();
        Thread t1 = new Thread(threadTest2, "t1");
        Thread t2 = new Thread(threadTest2, "t2");
        t1.start();
        //t1.join();

        t2.start();
        //t2.join();
        for (int i = 0; i < 10000; i++) {
            count.getAndIncrement();
            //count.incrementAndGet();
        }

        System.out.println("最终Count=" + count);
    }
}
