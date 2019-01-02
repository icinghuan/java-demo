package top.icinghuan.demo.javatest2;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author : xy
 * @date : 2019/1/2
 * Description :
 */
public class LoadingCacheTest {

    private static Boolean flag = true;

    private static LoadingCache<Integer, Optional<Integer>> cache = CacheBuilder.newBuilder()
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            .build(new CacheLoader<Integer, Optional<Integer>>() {
                @Override
                public Optional<Integer> load(Integer i) {
                    if (flag) {
                        flag = false;
                        return Optional.of(1);
                    }
                    throw new RuntimeException();
                }
            });

    public static void main(String[] args) throws InterruptedException {
        System.out.println(cache.getUnchecked(1));
        Thread.sleep(2000);
        System.out.println(cache.getUnchecked(1));
    }
}
