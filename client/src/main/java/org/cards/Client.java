package org.cards;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;


public class Client {
    private static class ClientReadFromServerThread extends Thread {
        private final SocketChannel socketChannel;
        private ByteBuffer buffer;

        public ClientReadFromServerThread(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            while (true) {
                this.buffer = ByteBuffer.allocate(1024);

                try {
                    buffer.clear();
                    socketChannel.read(buffer);
                    buffer.flip();
                    String output = new String(buffer.array()).trim();
                    if (output.length() > 0) {
                        System.out.println(output);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        InetSocketAddress serverAddr = new InetSocketAddress("localhost", 1234);
        //Create a socket channel
        SocketChannel client = SocketChannel.open(serverAddr); 
        System.out.println("Connecting to the server: " + client.getRemoteAddress());

        Scanner scanner = new Scanner(System.in);

        ClientReadFromServerThread clientReadFromServerThread = new ClientReadFromServerThread(client);
        clientReadFromServerThread.start();

        ByteBuffer buffer;

        while(true) {
            //Send message to server
            String message = scanner.nextLine();
            //Exit
            if (message.equals("/exit")) {
                break;
            }
            //Convert message to bytes
            byte[] messageBytes = message.getBytes(); 
            //Put bytes to buffer
            buffer = ByteBuffer.wrap(messageBytes); 
            //Send message to server
            client.write(buffer);
            buffer.clear();
        }
        client.close();
    }
}

