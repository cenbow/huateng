package com.huateng.p3.hub.accountcore.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * User: dongpeiji
 * Date: 14-9-23
 * Time: 下午1:33
 */
public class IntegralClientTest {
    static Socket server;
    public static void main(String[] args) throws Exception {
        server = new Socket(InetAddress.getLocalHost(), 8821);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                server.getInputStream()));
        PrintWriter out = new PrintWriter(server.getOutputStream());
        BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String str = wt.readLine();
            out.println(str);
            out.flush();
            if (str.equals("end")) {
                break;
            }
            System.out.println(in.readLine());
        }
        server.close();
    }
}
