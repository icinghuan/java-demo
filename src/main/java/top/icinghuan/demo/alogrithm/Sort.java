package top.icinghuan.demo.alogrithm;

import java.util.Arrays;
import java.util.Random;

/**
 * @author : xy
 * @date : 2018/10/24
 * Description :
 */
public class Sort {

    private static final int MAXN = 2 << 16;

    // 选择排序
    public static void sort1(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (less(a[j], a[i])) {
                    exch(a, i, j);
                }
            }
        }
    }

    // 插入排序
    public static void sort2(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (less(a[j], a[j - 1])) {
                    exch(a, j, j - 1);
                }
            }
        }
    }

    // 希尔排序
    public static void sort3(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h; j = j - h) {
                    if (less(a[j], a[j - h])) {
                        exch(a, j, j - h);
                    }
                }
            }
            h = h / 3;
        }
    }

    // 归并排序
    public static void sort4(Comparable[] a) {
        Comparable[] t = a;
        merge(a, t, 0, a.length - 1);
        a = t;
    }

    private static void merge(Comparable[] a, Comparable[] t, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = (l + r) >> 1;
        // 这里交换a和t是为了节省交换时间
        merge(t, a, l, mid);
        merge(t, a, mid + 1, r);
        int i = l;
        int j = mid + 1;
        int k = l;
        while (i <= mid || j <= r) {
            if (i <= mid) {
                if (j > r || lessOrEqual(a[i], a[j])) {
                    t[k] = a[i];
                    k++;
                    i++;
                }
            }
            if (j <= r) {
                if (i > mid || lessOrEqual(a[j], a[i])) {
                    t[k] = a[j];
                    k++;
                    j++;
                }
            }
        }
//        a = t;
    }

    // 冒泡排序
    public static void sort5(Comparable[] a) {
        boolean flag;
        do {
            flag = false;
            for (int j = 1; j < a.length; j++) {
                if (less(a[j], a[j - 1])) {
                    exch(a, j, j - 1);
                    flag = true;
                }
            }
        } while (flag);
    }

    // 快速排序
    public static void sort6(Comparable[] a) {
        quickSort(a, 0, a.length - 1);
    }

    private static void quickSort(Comparable[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = (l + r) >> 1;
        Comparable m = a[mid];
        int i = l;
        int j = r;
        while (i <= j) {
            while (less(a[i], m)) {
                i++;
            }
            while (more(a[j], m)) {
                j--;
            }
            if (i <= j) {
                exch(a, i, j);
                i++;
                j--;
            }
        }
        quickSort(a, l, i - 1);
        quickSort(a, i, r);
    }

    // 堆排序
    public static void sort7(Comparable[] a) {
        int len = a.length - 1;
        for (int i = len >> 2 - 1; i >= 0; i--) {
            heapAdjust(a, i, len);
        }
        while (len >= 0) {
            exch(a, 0, len);
            len--;
            heapAdjust(a, 0, len);
        }
    }

    private static void heapAdjust(Comparable[] a, int i, int len) {
        int l, r;
        while (i * 2 + 1 <= len) {
            l = i * 2 + 1;
            r = l + 1;
            if (r <= len && less(a[l], a[r])) {
                l = r;
            }
            if (less(a[i], a[l])) {
                exch(a, i, l);
            } else {
                break;
            }
            i = l;
        }
    }

    // 桶排序
    public static void sort8(Integer[] a) {
        int[] bucket = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            bucket[a[i]]++;
        }
        int r = 0;
        for (int i = 0; i < a.length; i++) {
            if (bucket[i] > 0) {
                for (int j = 0; j < bucket[i]; j++) {
                    a[r] = i;
                    r++;
                }
            }
        }
    }

    // 猴子排序
    public static void sort9(Comparable[] a) {
        Random random = new Random();
        while (!isSorted(a)) {
            int i = random.nextInt(a.length);
            int j = random.nextInt(a.length);
            exch(a, i, j);
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean more(Comparable v, Comparable w) {
        return v.compareTo(w) > 0;
    }

    private static boolean lessOrEqual(Comparable v, Comparable w) {
        return v.compareTo(w) <= 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            System.out.printf("%d ", a[i]);
        }
        System.out.println(a[a.length - 1]);
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[MAXN];
        Random random = new Random();
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(MAXN);
        }
        Long startTime = System.currentTimeMillis();
        Arrays.sort(a);
        Long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        System.out.println(isSorted(a));
//        show(a);
    }
}
