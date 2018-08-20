package top.icinghuan.java_demo.javatest;

/**
 * @author : xy
 * @date : 2018/8/13
 * Description :
 */
public class JavaNumberChangeTest {

    public static void main(String[] args) {
        Integer i = 100;
        Long l = 100000000000L;
        Double d = 123456.7890d;
        String s= "1010101";

        System.out.println(Integer.parseInt(s));
        System.out.println(Long.parseLong(s));
        System.out.println(Double.parseDouble(s));

        System.out.println(i.longValue());
        System.out.println(i.doubleValue());

        System.out.println(l.intValue());
        System.out.println(l.doubleValue());

        System.out.println(d.intValue());
        System.out.println(d.longValue());
    }
}
