package top.icinghuan.java_demo.javatest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : xy
 * @date : 2018/7/26
 * Description :
 */
public class SocketTest {

    SocketTest socketTest = new SocketTest();
    EchoServer echoServer = socketTest.new EchoServer();

    public class EchoServer {

    }

    public static void main(String[] args) {
        int portNumber = 5050;
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                System.out.println("resp:" + inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
