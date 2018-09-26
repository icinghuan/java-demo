package top.icinghuan.demo.javatest;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

/**
 * @author : xy
 * @date : 2018/9/26
 * Description :
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(16, 32,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        Semaphore semaphore = new Semaphore(5);

        Runnable longRunningTask = () -> {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("Semaphore acquired");
                    sleep(2000);
                } else {
                    System.out.println("Could not acquire semaphore");
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                if (permit) {
                    semaphore.release();
                }
            }
        };

        IntStream.range(0, 10)
                .forEach(i -> singleThreadPool.submit(longRunningTask));

        singleThreadPool.shutdown();
    }
}
