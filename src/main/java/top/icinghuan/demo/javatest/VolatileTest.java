package top.icinghuan.demo.javatest;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author : xy
 * @date : 2018/9/10
 * Description :
 */
public class VolatileTest extends Thread {
    volatile static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 50000; j++) {
            i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("java-demo-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(16, 32, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        VolatileTest volatileTest1 = new VolatileTest();
        VolatileTest volatileTest2 = new VolatileTest();
        singleThreadPool.execute(volatileTest1);
        singleThreadPool.execute(volatileTest2);
        singleThreadPool.shutdown();
        while(!singleThreadPool.isTerminated()) {
            sleep(100);
        }
        System.out.println(i);
    }
}
