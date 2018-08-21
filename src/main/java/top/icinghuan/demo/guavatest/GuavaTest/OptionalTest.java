package top.icinghuan.demo.guavatest.GuavaTest;

import com.google.common.base.Optional;

/**
 * @author : xy
 * @date : 2018/7/16
 * Description :
 */
public class OptionalTest {

    public static void optionalTest() {
        Optional<Integer> a = Optional.of(new Integer(10));
        Optional<Integer> b = Optional.fromNullable(null);
        System.out.println(sum(a, b));
    }

    private static Integer sum(Optional<Integer> a, Optional<Integer> b) {
        Integer value1 = a.or(new Integer(0));
        Integer value2 = b.or(new Integer(0));
        return value1 + value2;
    }
}
