package top.icinghuan.demo.javatest;

import java.io.*;

/**
 * @author : xy
 * @date : 2018/8/8
 * Description :
 */
public class JavaReadFile {

    public static void main(String[] args) throws IOException {
        Long startTime = System.currentTimeMillis();
        File file = new File("/Users/tiger/Desktop/out.xml");
        Long fileLength = file.length();
        char[] filecontent = new char[fileLength.intValue()];
        FileReader in = new FileReader(file);
        in.read(filecontent);
        Long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        System.out.println(fileLength);
    }
}
