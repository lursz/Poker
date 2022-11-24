package org.cards;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;




public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        InetSocketAddress serverAddr = new InetSocketAddress("localhost", 1234);

        SocketChannel client = SocketChannel.open(serverAddr); // Tworzymy socket i łączymy się z serwerem

        System.out.println("Łączenie z serwerem: " + client.getRemoteAddress());

        String messageToSend = "Hello from client!";

        byte[] message = new String(messageToSend).getBytes(); // Konwertujemy wiadomość na tablicę bajtów
        ByteBuffer buffer = ByteBuffer.wrap(message); // Wrappujemy tablicę bajtów w bufor
        client.write(buffer); // Wysyłamy wiadomość

        System.out.println("Wysłano: " + messageToSend);
        buffer.clear();

        client.close();
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