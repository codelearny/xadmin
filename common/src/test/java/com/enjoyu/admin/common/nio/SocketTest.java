package com.enjoyu.admin.common.nio;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest {

    @Test
    public void testServer() throws IOException {
        ServerSocket ss = new ServerSocket(7890);
        Socket conn = ss.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        String s;
        while ((s = br.readLine()) != null) {
            System.out.println(s);
            bw.write(s);
            bw.flush();
        }

        br.close();
        bw.close();
        conn.close();
    }

    @Test
    public void testClient() throws IOException {
        Socket conn = new Socket("127.0.0.1", 7890);
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write("hello");
        bw.flush();
        String s = br.readLine();
        System.out.println("echo:" + s);

        br.close();
        bw.close();
        conn.close();
    }
}
