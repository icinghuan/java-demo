package top.icinghuan.demo.guavatest;

import com.google.common.primitives.Longs;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : xy
 * @date : 2018/7/17
 * Description :
 */
public class LongsTest {

    @Test
    public void longsTest() {
        long[] longArray = {1L, 2L, 3L, 4L, 5L};

        List<Long> longList = Longs.asList(longArray);
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        list.add(4L);
        list.add(5L);
//        System.out.println(longList);
//        System.out.println(list);
//        System.out.println(longList.equals(list));
        assert (longList.equals(list));
    }
}
