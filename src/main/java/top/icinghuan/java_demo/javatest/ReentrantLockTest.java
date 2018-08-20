package top.icinghuan.java_demo.javatest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : xy
 * @date : 2018/8/16
 * Description :
 */
public class ReentrantLockTest {
    private int start = 1;

    private boolean flag = false;

    /**
     * 重入锁
     */
    private final static Lock LOCK = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();

        Thread t1 = new Thread(new OuNum(reentrantLockTest));
        t1.setName("t1");


        Thread t2 = new Thread(new JiNum(reentrantLockTest));
        t2.setName("t2");

        t1.start();
        t2.start();
    }

    /**
     * 偶数线程
     */
    public static class OuNum implements Runnable {

        private ReentrantLockTest number;

        public OuNum(ReentrantLockTest number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number.start <= 100) {

                if (number.flag) {
                    try {
                        LOCK.lock();
                        System.out.println(Thread.currentThread().getName() + "+-+" + number.start);
                        number.start++;
                        number.flag = false;


                    } finally {
                        LOCK.unlock();
                    }
                } else {
                    try {
                        //防止线程空转
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 奇数线程
     */
    public static class JiNum implements Runnable {

        private ReentrantLockTest number;

        public JiNum(ReentrantLockTest number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number.start <= 100) {

                if (!number.flag) {
                    try {
                        LOCK.lock();
                        System.out.println(Thread.currentThread().getName() + "+-+" + number.start);
                        number.start++;
                        number.flag = true;


                    } finally {
                        LOCK.unlock();
                    }
                } else {
                    try {
                        //防止线程空转
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
