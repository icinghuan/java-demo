package top.icinghuan.demo.guavatest.GuavaTest;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * @author : xy
 * @date : 2018/7/16
 * Description :
 */
public class MultiSetTest {

    public static void mutiSetTest() {
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("a");
        multiset.add("b");
        multiset.add("b");
        multiset.add("c");
        multiset.add("c");
        multiset.add("d");
        multiset.add("d");

        for (String s : multiset) {
            System.out.println(s);
        }
    }
}
