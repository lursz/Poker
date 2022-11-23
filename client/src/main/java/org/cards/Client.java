package org.cards;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main( String[] args ) throws IOException {
        InetSocketAddress serverAddr = new InetSocketAddress("localhost", 1234);

        SocketChannel client = SocketChannel.open(serverAddr); // open a connection to the server

        System.out.println("Łączenie z serwerem: " + client.getRemoteAddress());

        while(true) {
            try {
//
                ByteBuffer buffer = ByteBuffer.allocate(256);
                client.read(buffer);
                String result = new String(buffer.array()).trim();
                System.out.println(result);


                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String command = reader.readLine();
                byte[] message = new String(command).getBytes(); // Converting string to byte array
                ByteBuffer messageBuffer = ByteBuffer.wrap(message); // Wrapping byte array into ByteBuffer
                client.write(messageBuffer); // Sensing message to server
                System.out.println("Wysłano: " + command);
                buffer.clear();

            } catch(Exception e) {
                System.err.println(e.getMessage());
                client.close();
            }
        }

    }
}
































//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.SocketChannel;
//
//public class Client {
//    public static void main(String[] args) throws IOException, InterruptedException {
//        InetSocketAddress serverAddr = new InetSocketAddress("localhost", 1234);
//
//        SocketChannel client = SocketChannel.open(serverAddr); // Tworzymy socket i łączymy się z serwerem
//
//        System.out.println("Łączenie z serwerem: " + client.getRemoteAddress());
//
//        String messageToSend = "Hello from client!";
//
//        byte[] message = new String(messageToSend).getBytes(); // Konwertujemy wiadomość na tablicę bajtów
//        ByteBuffer buffer = ByteBuffer.wrap(message); // Wrappujemy tablicę bajtów w bufor
//        client.write(buffer); // Wysyłamy wiadomość
//
//        System.out.println("Wysłano: " + messageToSend);
//        buffer.clear();
//
//        client.close();
//    }
//}