package org.cards;


public class Client {
    //    Attributes
    private String host;
    private int port;

    //    Constructor
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startClient() {
        try {
            Socket socket = new Socket(host, port);
            System.out.println("Połączono z serwerem: " + socket.getRemoteAddress());
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.start();
        } catch (IOException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 1234);
        client.startClient();
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