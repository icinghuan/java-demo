package top.icinghuan.demo.javatest2;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : xy
 * @date : 2019/1/2
 * Description :
 */
public class LoadingCacheTest {

    private static AtomicInteger ai = new AtomicInteger(0);

    private static LoadingCache<Integer, Optional<Integer>> cache = CacheBuilder.newBuilder()
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            .expireAfterWrite(2, TimeUnit.SECONDS)
            .build(new CacheLoader<Integer, Optional<Integer>>() {
                @Override
                public Optional<Integer> load(Integer i) {
                    if (ai.get() <= 3) {
                        ai.getAndIncrement();
                        return Optional.of(1);
                    }
                    throw new RuntimeException();
                }

                @Override
                public ListenableFuture<Optional<Integer>> reload(Integer key, Optional<Integer> oldValue) {
                    System.out.println("refresh");
                    return Futures.immediateFuture(load(key));
                }
            });

    public static void main(String[] args) throws InterruptedException {
        System.out.println(cache.getUnchecked(1));

        // 正常refresh
        Thread.sleep(1000);
        System.out.println(cache.getUnchecked(1));

        // 正常refresh
        Thread.sleep(1000);
        System.out.println(cache.getUnchecked(1));

        // 正常refresh
        Thread.sleep(1000);
        System.out.println(cache.getUnchecked(1));

        // 距离上次刷新未满2秒，异常被吞掉
        Thread.sleep(1500);
        System.out.println(cache.getUnchecked(1));

        // 超时过期，缓存被回收，本次调用抛出异常
        Thread.sleep(500);
        System.out.println(cache.getUnchecked(1));
    }
}
