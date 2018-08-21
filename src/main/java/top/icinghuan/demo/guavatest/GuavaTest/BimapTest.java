package top.icinghuan.demo.guavatest.GuavaTest;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author : xy
 * @date : 2018/7/16
 * Description :
 */
public class BimapTest {

    public static void bimapTest() {
        BiMap<Long, String> biMap = HashBiMap.create();

        biMap.put(new Long(101), "aaa");
        biMap.put(new Long(102), "bbb");
        biMap.put(new Long(103), "ccc");

        System.out.println(biMap.inverse().get("aaa"));

    }
}
