package org.cards;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        InetSocketAddress serverAddr = new InetSocketAddress("localhost", 1234);
        //Create a socket channel
        SocketChannel client = SocketChannel.open(serverAddr); 
        System.out.println("Łączenie z serwerem: " + client.getRemoteAddress());
        ByteBuffer buffer = ByteBuffer.allocate(1024);



        //Read the message from server
        //If received bytes > 0
        if (client.read(buffer) > 0) {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            System.out.println(new String(bytes));
        }

        //Send message to server
        Scanner scanner = new Scanner(System.in);

        while(true) {
            //Read message from console
            String message = scanner.nextLine();
            //Exit if message is "exit"
            if (message.equals("exit")) {
                break;
            }
            //Convert message to bytes
            byte[] messageBytes = message.getBytes(); 
            //Put bytes to buffer
            buffer = ByteBuffer.wrap(messageBytes); 
            //Send message to server
            int write = client.write(buffer);
            buffer.clear();
        }
        client.close();
    }
}

