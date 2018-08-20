package top.icinghuan.java_demo.javatest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author : xy
 * @date : 2018/7/25
 * Description :
 */
public class LambdaTest {

    public static void main(String[] args) {
        MathOperation addition = (int a, int b) -> a + b;
        MathOperation subtraction = (int a, int b) -> a - b;

        System.out.println(operate(10, 5, addition));
        System.out.println(operate(10, 5, subtraction));

        List<String> list = new ArrayList<String>(Arrays.asList("Google","Runoob","Taobao","Baidu","Sina"));
        list.add("Apple");
        list.forEach(s -> {
            System.out.println(s);
        });
        sort(list);
        System.out.println(list);
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    private static int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }

    private static void sort(List<String> names) {
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
    }
}
