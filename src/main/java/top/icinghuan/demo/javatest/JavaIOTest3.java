package top.icinghuan.demo.javatest;

import java.io.Console;
import java.util.Arrays;

/**
 * @author : xy
 * @date : 2018/8/28
 * Description :
 */
public class JavaIOTest3 {
    /**
     * 这里需要在命令行运行
     * 编译为字节码后
     * 在src/main/java下运行java top.icinghuan.demo.javatest.JavaIOTest3
     *
     * @param args
     */
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.out.println("No Console.");
            System.exit(1);
        }

        String login = console.readLine("Enter your login: ");
        char[] oldPassword = console.readPassword("Enter your old password: ");

        if (verify(login, oldPassword)) {
            boolean noMatch;
            do {
                char[] newPassword1 = console.readPassword("Enter your new password: ");
                char[] newPassword2 = console.readPassword("Enter new password again: ");
                noMatch = !Arrays.equals(newPassword1, newPassword2);
                if (noMatch) {
                    console.format("Passwords don't match.Try again.%n");
                } else {
                    change(login, newPassword1);
                    console.format("Password for %s changed.%n", login);
                }
                Arrays.fill(newPassword1, ' ');
                Arrays.fill(newPassword2, ' ');
            } while (noMatch);
        }

        Arrays.fill(oldPassword, ' ');
    }

    static boolean verify(String login, char[] password) {
        return true;
    }

    static void change(String login, char[] password) {

    }
}
