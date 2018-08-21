package top.icinghuan.demo.guavatest;

import com.google.common.math.IntMath;
import org.junit.Test;

/**
 * @author : xy
 * @date : 2018/7/17
 * Description :
 */
public class TestIntMath {

    @Test
    public void testIntMath() {
        Integer a = new Integer(100);
        Integer b = new Integer(150);

//        int a = 100;
//        int b = 150;

        assert (IntMath.gcd(a, b) == 50);
    }
}
