package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket server;
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter; //Out

    //Constructor...
    public Server(){
        try {
            server = new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("waiting...");
            socket = server.accept();

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            printWriter = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void startReading() {
        // thread - read karke data rahega
        Runnable r1 = ()->{
            System.out.println("reader started...");
            try {
                while (true) {
                    String msg = bufferedReader.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Client : " + msg);
                }
            }catch (IOException e) {
                   // e.printStackTrace();
                System.out.println("connection closed");
            }
        };
        new Thread(r1).start();
    }

    private void startWriting() {
        // thread - data user lega and the send karega client tak
        Runnable r2 = ()->{
            System.out.println("Writer Started...");
            try {
            while (!socket.isClosed()){
                    BufferedReader bufferedReader1 =  new BufferedReader(new InputStreamReader(System.in));
                    String content = bufferedReader1.readLine();
                    printWriter.println(content);
                    printWriter.flush();

                    if (content.equals("exit")){
                        socket.close();
                        break;
                    }
                }
            }catch (Exception e){
               // e.printStackTrace();
                System.out.println("connection closed");
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
	// write your code here
        System.out.println("this is server.. going to start server");
        new Server();

    }
}
