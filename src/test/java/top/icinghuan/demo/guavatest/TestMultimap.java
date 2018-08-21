package top.icinghuan.demo.guavatest;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;

/**
 * @author : xy
 * @date : 2018/7/17
 * Description :
 */
public class TestMultimap {

    @Test
    public void testMultimap() {
        Multimap<String, String> multimap = ArrayListMultimap.create();

        multimap.put("lower", "a");
        multimap.put("lower", "b");
        multimap.put("lower", "c");
        multimap.put("lower", "d");

        multimap.put("upper", "A");
        multimap.put("upper", "B");
        multimap.put("upper", "C");
        multimap.put("upper", "D");

//        System.out.println(multimap);
        assert (4 == multimap.get("lower").size());
    }
}
