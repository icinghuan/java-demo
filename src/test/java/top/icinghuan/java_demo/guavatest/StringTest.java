package top.icinghuan.java_demo.guavatest;

import com.google.common.base.CaseFormat;
import org.junit.Test;

/**
 * @author : xy
 * @date : 2018/7/17
 * Description :
 */

public class StringTest {

    @Test
    public void stringTest() {
        String s = "upperCamel";
//        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, s));
        assert (CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, s).equals("UpperCamel"));
    }


}
