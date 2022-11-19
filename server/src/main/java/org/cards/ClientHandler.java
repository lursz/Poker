package org.cards;

import java.io.*;
import java.util.ArrayList;
import java.net.Socket;

public class ClientHandler implements Runnable {
    //    Attributes
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;


    //    Constructor
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            //Byte streams: OutputStream
            //Words streams: OutputStreamWriter
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("Użytkownik " + clientUsername + " dołączył do gry");


        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }


    @Override
    public void run() {
       String messageFromClient;

       while (socket.isConnected()) {
           try {
               //Blocking operation, halting till first contact
               messageFromClient = bufferedReader.readLine();
               broadcastMessage(clientUsername + ": " + messageFromClient);
           } catch (IOException e) {
               closeEverything(socket, bufferedReader, bufferedWriter);
               break;
           }
       }
    }





}

