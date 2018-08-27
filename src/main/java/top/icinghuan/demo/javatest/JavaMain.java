package top.icinghuan.demo.javatest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : xy
 * @date : 2018/8/10
 * Description :
 */
public class JavaMain {
    public static void main(String[] args) {
        System.out.println(LocalDate.ofYearDay(2018,151));
        List<Long> list = new ArrayList<>();
        list = null;
        System.out.println(list.size());
        System.out.println(list.get(0));
    }
}
