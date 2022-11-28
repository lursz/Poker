package org.cards;
import org.cards.game.Game;
import org.cards.player.Player;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Server {

    private static Game game = new Game();

    private static boolean isRunning = true;
/* ----------------------------- Add new player ----------------------------- */

    /**
     * Function adds new player to the game
     * @param player
     */
    public static void addPlayer(Player player) {
       Game.playerMap.put(player.getKey_(), player);
       Game.players_.add(player);
    }


    public static void main(String[] args) {
        //Load pot and initial balance at the beginning of the game
        if (args.length < 1) {
            System.out.println("Usage: java Server <initial balance>");
            return;
        }

        int balance = Integer.parseInt(args[0]);


        /* --------------- Socket configuration ---------------- */
        //Create selector
        Selector selector = null;
        ServerSocketChannel serverSocket = null;

        try {
            selector = Selector.open();
            //Open socket channel
            serverSocket = ServerSocketChannel.open();
            //Set an address
            InetSocketAddress serverAddr = new InetSocketAddress("localhost", 1234);
            //Bind the server socket to the specified address and port
            serverSocket.bind(serverAddr);
            //Set non-blocking mode
            serverSocket.configureBlocking(false);
            //Determine which operations the socket is ready to perform
            int ops = serverSocket.validOps();
            //Register the server socket channel with the selector
            SelectionKey selectionKey = serverSocket.register(selector, ops, null);

            /* ----------------- Sending / Receiving part ----------------- */
            while (isRunning) {
                System.out.println("Czekam na połączenie...");
                selector.select();
                Set<SelectionKey> serverKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = serverKeys.iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    //SENDING
                    if (key.isAcceptable()) {
                        // Connection accepted
                        SocketChannel client = serverSocket.accept();
                        client.configureBlocking(false);
                        SelectionKey selectionKey2 = client.register(selector, SelectionKey.OP_READ);
                        System.out.println("Połączenie zaakceptowane: " + client);
                        //Add player to Playermap

                        if (Game.players_.size() < 5) {
                            Player player = new Player("", balance, selectionKey2);
                            addPlayer(player);
                            game.addNumberOfPlayers();
                        } else {
                            System.out.println("Max capacity has been reached");
                        }


                        //Welcoming message
                        byte[] message = new String("Welcome to 7Poker.\n Type: '/usr <username>' to set your name, then type '/ready' and wait for other players.\nType '/help' anytime to get list of other commands.").getBytes();
                        //Wrap the message in a buffer
                        ByteBuffer buffer = ByteBuffer.wrap(message);
                        //Send the message to the client
                        client.write(buffer);

                        //RECEIVING
                    } else if (key.isReadable()) { // czy kanał jest gotowy do odczytu
                        receiveMessageFromClient(key);
                    }
                    iter.remove();

                }
                //break the loop

            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
                if (selector != null) {
                    selector.close();
                }
            } catch (IOException e) {
                System.out.println("Server exception: " + e.getMessage());
            }
        }
    }
        /* --------------------------------- Sending -------------------------------- */

    /**
     * Send message to client
     * @param message
     * @param client
     * @throws IOException
     */
    public static void sendMessageToClient(String message, SocketChannel client) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //Convert the message to bytes
        byte[] messageBytes = message.getBytes(); 
        //Wrap the message in a buffer
        buffer = ByteBuffer.wrap(messageBytes); 
        //Send the message to the client
        int write = client.write(buffer);
        buffer.clear();
        
    }

    /**
     * Send message to all clients
     * @param message
     * @throws IOException
     */
    public static void sendMessageToEveryone(String message) throws IOException {
        for (SelectionKey key : Game.playerMap.keySet()) {
            SocketChannel client = (SocketChannel) key.channel();
            sendMessageToClient(message, client);
        }
    }
    /* -------------------------------- Receiving ------------------------------- */

    /**
     * Receive message from client
     * @param key
     * @throws IOException
     */
    public static void receiveMessageFromClient(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(256);
        //Read the message from client
        client.read(buffer);
        String result = new String(buffer.array()).trim();

        //Process the command
        System.out.println("Odebrano: " + result);
        Game.Pair answer = game.receiveCommands(result, Game.playerMap.get(key));
        if (result.equals("/exit")) {
            isRunning = false;
        }

        if(answer.toAll) {
            sendMessageToEveryone(answer.answer);
        } else {
            sendMessageToClient(answer.answer, client);
        }




    }
}

