package org.cards;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open(); // Tworzymy selektor
        ServerSocketChannel serverSocket = ServerSocketChannel.open(); // Otwieramy socket
        InetSocketAddress serverAddr = new InetSocketAddress("localhost", 1234); // Ustawiamy adres

        serverSocket.bind(serverAddr); // Bindowanie socketu
        serverSocket.configureBlocking(false); // Ustawiamy socket na nieblokujący

        int ops = serverSocket.validOps(); // Pobieramy operacje, które możemy wykonać na sockecie
        SelectionKey selectionKey = serverSocket.register(selector, ops, null); // Rejestrujemy socket w selektorze

        while (true) {
            System.out.println("Czekam na połączenie...");

            selector.select(); // Czekamy na połączenie

            Set<SelectionKey> serverKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = serverKeys.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    // Połączenie zaakceptowane przez ServerSocketChannel
                    SocketChannel client = serverSocket.accept(); // Akceptujemy połączenie
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("Połączenie zaakceptowane: " + client);

//                  ****************************************
                    byte[] message = new String("Welcome to 7Poker.\n Type 'help' for the list of commands.").getBytes();
                    ByteBuffer buffer = ByteBuffer.wrap(message);
                    client.write(buffer);
//                  ****************************************


                } else if (key.isReadable()) { // czy kanał jest gotowy do odczytu
                    SocketChannel client = (SocketChannel) key.channel();

                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    client.read(buffer);
                    String result = new String(buffer.array()).trim();

                    System.out.println("Odebrano: " + result);

                    client.close();
                }

                iter.remove();
            }
        }
    }
}

