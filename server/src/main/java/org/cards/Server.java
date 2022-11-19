package org.cards;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class Server {
    //    Attributes
    private ServerSocket serverSocket;

    //    Constructor
    private Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                System.out.println("Oczekiwanie na połączenie...");
                Socket client = serverSocket.accept(); // Oczekujemy na połączenie

                System.out.println("Połączono z klientem: " + client.getRemoteAddress());
                ClientHandler clientHandler = new ClientHandler(client);

//                  Creating new thread for each client
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Błąd podczas oczekiwania na połączenie");
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());

        }


    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null)){
                serverSocket.close();
            }
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();

    }

}


//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.lang.ClassNotFoundException;
//import java.net.ServerSocket;
//import java.net.Socket;
//
///**
// * This class implements java Socket server
// * @author pankaj
// *
// */
//public class Server {
//
//    //static ServerSocket variable
//    private static ServerSocket server;
//    //socket server port on which it will listen
//    private static int port = 9876;
//
//    public static void main(String args[]) throws IOException, ClassNotFoundException{
//        //create the socket server object
//        server = new ServerSocket(port);
//        //keep listens indefinitely until receives 'exit' call or program terminates
//        while(true){
//            System.out.println("Waiting for the client request");
//            //creating socket and waiting for client connection
//            Socket socket = server.accept();
//            //read from socket to ObjectInputStream object
//            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//            //convert ObjectInputStream object to String
//            String message = (String) ois.readObject();
//            System.out.println("Message Received: " + message);
//            //create ObjectOutputStream object
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            //write object to Socket
//            oos.writeObject("Hi Client "+message);
//            //close resources
//            ois.close();
//            oos.close();
//            socket.close();
//            //terminate the server if client sends exit request
//            if(message.equalsIgnoreCase("exit")) break;
//        }
//        System.out.println("Shutting down Socket server!!");
//        //close the ServerSocket object
//        server.close();
//    }
//}


//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.SelectionKey;
//import java.nio.channels.Selector;
//import java.nio.channels.ServerSocketChannel;
//import java.nio.channels.SocketChannel;
//import java.util.Iterator;
//import java.util.Set;
//
//public class Server {
//    public static void main(String[] args) throws IOException {
//        Selector selector = Selector.open(); // Tworzymy selektor
//        ServerSocketChannel serverSocket = ServerSocketChannel.open(); // Otwieramy socket
//        InetSocketAddress serverAddr = new InetSocketAddress("localhost", 1234); // Ustawiamy adres
//
//        serverSocket.bind(serverAddr); // Bindowanie socketu
//        serverSocket.configureBlocking(false); // Ustawiamy socket na nieblokujący
//
//        int ops = serverSocket.validOps(); // Pobieramy operacje, które możemy wykonać na sockecie
//        SelectionKey selectionKey = serverSocket.register(selector, ops, null); // Rejestrujemy socket w selektorze
//
//        while (true) {
//            System.out.println("Czekam na połączenie...");
//
//            selector.select(); // Czekamy na połączenie
//
//            Set<SelectionKey> serverKeys = selector.selectedKeys();
//            Iterator<SelectionKey> iter = serverKeys.iterator();
//
//            while (iter.hasNext()) {
//                SelectionKey key = iter.next();
//
//                if (key.isAcceptable()) {
//                    // Połączenie zaakceptowane przez ServerSocketChannel
//                    SocketChannel client = serverSocket.accept(); // Akceptujemy połączenie
//                    client.configureBlocking(false);
//                    client.register(selector, SelectionKey.OP_READ);
//                    System.out.println("Połączenie zaakceptowane: " + client);
//                } else if (key.isReadable()) { // czy kanał jest gotowy do odczytu
//                    SocketChannel client = (SocketChannel) key.channel();
//
//                    ByteBuffer buffer = ByteBuffer.allocate(256);
//                    client.read(buffer);
//                    String result = new String(buffer.array()).trim();
//
//                    System.out.println("Odebrano: " + result);
//
//                    client.close();
//                }
//
//                iter.remove();
//            }
//        }
//    }
//}

