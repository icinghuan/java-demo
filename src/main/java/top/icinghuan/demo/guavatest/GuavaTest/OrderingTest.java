package top.icinghuan.demo.guavatest.GuavaTest;

import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : xy
 * @date : 2018/7/16
 * Description :
 */
public class OrderingTest {

    public static void ordingTest() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(new Integer(5));
        list.add(new Integer(2));
        list.add(new Integer(10));
        list.add(new Integer(6));
        list.add(new Integer(3));
        list.add(new Integer(50));
        list.add(new Integer(0));

        Ordering ordering = Ordering.natural();

        Collections.sort(list, ordering);

        System.out.println(list);
    }
}
