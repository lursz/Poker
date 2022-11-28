package org.cards;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;


public class Client {
    private static boolean isRunning = true;

    private static class ClientReadFromServerThread extends Thread {
        private final SocketChannel socketChannel;
        private ByteBuffer buffer;

        public ClientReadFromServerThread(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            while (isRunning) {
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
                    System.out.println("Server has closed the connection");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel client = null;
        try {
            InetSocketAddress serverAddr = new InetSocketAddress("localhost", 1234);
            //Create a socket channel
            client = SocketChannel.open(serverAddr);
            System.out.println("Connecting to the server: " + client.getRemoteAddress());

            Scanner scanner = new Scanner(System.in);

            ClientReadFromServerThread clientReadFromServerThread = new ClientReadFromServerThread(client);
            clientReadFromServerThread.start();

            ByteBuffer buffer;

            while (isRunning) {
                //Send message to server
                String message = scanner.nextLine();
                //Exit
                if (message.equals("/exit")) {
                    isRunning = false;
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
        } catch (IOException e) {
            System.out.println("Server is not running");
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }
}

