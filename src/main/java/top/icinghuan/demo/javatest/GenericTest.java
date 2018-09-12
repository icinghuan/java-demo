package top.icinghuan.demo.javatest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : xy
 * @date : 2018/9/12
 * Description :
 */
public class GenericTest {

    private static <T extends Number> void kk(T a) {
        System.out.println(a);
    }

    public static void main(String[] args) {
        List<? extends Number> list = Arrays.asList(1,2,3,4,5);
        list.forEach(a -> {
            kk(a);
        });
        System.out.println(list);
    }
}
