package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter; //Out


    public Client(){
        try {
            System.out.println("Sending request to server...");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("Connection done..");


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
            while (true){

                String msg = bufferedReader.readLine();
                    if (msg.equals("exit")){
                        System.out.println("Server terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Server : "+msg);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        new Thread(r1).start();
    }

    private void startWriting() {
        // thread - data user lega and the send karega client tak
        Runnable r2 = () -> {
            System.out.println("Writer Started...");
            try {
                while (true) {

                    BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = bufferedReader1.readLine();
                    printWriter.println(content);
                    printWriter.flush();

                    if (content.equals("exit")){
                        socket.close();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            };
            new Thread(r2).start();
        }

    public static void main(String[] args) {
        System.out.println("this is client...");
        new Client();
    }
}
