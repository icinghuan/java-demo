package top.icinghuan.java_demo.javatest;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : xy
 * @date : 2018/7/25
 * Description :
 */
public class ThreadTest extends Thread {

    private static final SynchronizedCounter counter = new SynchronizedCounter();

    private static final Lock lock = new ReentrantLock();

    private static final int MAXCOUNT = 500000;

    @Override
    public void run() {
        int c = 0;
        System.out.println(Thread.currentThread().getName());
        lock.lock();
        while (c < MAXCOUNT) {
            synchronized (counter) {
                counter.inc();
                //System.out.println(counter.getValue());
            }
            c++;
        }
        System.out.println(counter.getValue());
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(16, 32,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

//        for (int i = 0; i < 1000; ++i) {
//            singleThreadPool.execute(() -> System.out.println(Thread.currentThread().getName()));
//        }

//        List<Callable<Integer>> callers = new ArrayList<Callable<Integer>>();

        ThreadTest threadTest1 = new ThreadTest();
        singleThreadPool.execute(threadTest1);
        ThreadTest threadTest2 = new ThreadTest();
        singleThreadPool.execute(threadTest2);


        singleThreadPool.shutdown();

        sleep(1000);
//        singleThreadPool.invokeAll(callers);
        System.out.println(counter.getValue());

//        Thread thread1 = new Thread(new ThreadTest());
//        Thread thread2 = new Thread(new ThreadTest());

//        thread1.start();
//        thread2.start();
//
//        thread1.join();
//        thread2.join();
//        System.out.println(counter.getValue());
    }
}
