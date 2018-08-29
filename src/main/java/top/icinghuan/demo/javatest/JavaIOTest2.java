package top.icinghuan.demo.javatest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author : xy
 * @date : 2018/8/28
 * Description :
 */
public class JavaIOTest2 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader("src/main/resources/in.txt")));
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
