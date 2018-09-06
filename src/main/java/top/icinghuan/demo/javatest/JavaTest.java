package top.icinghuan.demo.javatest;

/**
 * @author : xy
 * @date : 2018/9/6
 * Description :
 */
public class JavaTest {
    public static void main(String[] args) {
        // 自动装箱
        Long sum = 0L;
        // long sum = 0L;
        Long startTime = System.currentTimeMillis();
        for (Long i = 0L; i < Integer.MAX_VALUE; ++i) {
            sum += i;
        }
        Long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        System.out.println(sum);
    }
}
