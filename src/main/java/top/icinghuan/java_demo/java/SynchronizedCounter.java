package top.icinghuan.java_demo.java;

/**
 * @author : xy
 * @date : 2018/7/25
 * Description :
 */
public class SynchronizedCounter {
    private int c = 0;

    public void inc() {
        c++;
    }

    public void dec() {
        c--;
    }

    public int getValue() {
        return c;
    }
}
