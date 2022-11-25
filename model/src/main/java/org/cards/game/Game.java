package org.cards.game;

import org.cards.object.*;
import org.cards.player.*;
import org.cards.Server;
import org.cards.exceptions.*;

import java.util.ArrayList;
import org.apache.commons.lang3.tuple;

public class Game {
    private Deck deck_;

    private int numberOfPlayers;
    private int numberOfReadyPlayers;
    private boolean initialized_;
    private int balance_ = 1000;
    private int currentRoundsNumber;


    public static class Pair {
        public String answer;
        public boolean toAll;

        Pair(String answer, boolean toAll) {
            this.answer = answer;
            this.toAll = toAll;
        }
    }


    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public Game() {
        this.deck_ = new Deck();
        initialized_ = false;
        numberOfPlayers = 0;
    }
    public void addNumberOfPlayers() {
        numberOfPlayers++;
    }



    //Methods

    public Pair receiveCommands(String command, Player player) {
        String[] commandParts = command.split(" ");
        switch (commandParts[0]) {
            //List of commands
            case "/help": {
                return new Pair("/help - show the list of commands\n " +
                        "/ready - start the game\n " +
                        "<CARDS>\n " +
                        "/showCards - show cards\n " +
                        "/check - continue without placing a wager \n " +
                        "/bet <amount> - make a bet \n " +
                        "/call - match the wager \n " +
                        "/raise - raise the wager \n " +
                        "/fold - withdraw from the round\n " +
                        "<GAME>\n " +
                        "/exit - exit the game\n", false);
            }
            //Set username
            case "/usr": {
                if (commandParts.length == 2) {
                    if (initialized_) {
                        return new Pair("Game already started", false);
                    } else {
                        player.setName_(commandParts[1]);
                        return new Pair("Username set to " + commandParts[1], false);
                    }
                } else {
                    return new Pair("Wrong command", false);
                }
            }
            case "/ready": {
                if (initialized_) {
                    return new Pair("Game already started", false);
                } else {
                    if (player.getName_() == null) {
                        return new Pair("Set username first", false);
                    } else {
                        numberOfReadyPlayers++;
                        if (numberOfReadyPlayers == numberOfPlayers) {
                            initialized_ = true;
                            return new Pair("Game is starting now", true);
                        } else {
                            return new Pair("Waiting for other players", false);
                        }
                    }
                }
            }
            case "/showCards": {
                break;
            }
            case "/check": {
                break;
            }
            case "/bet": {

                break;
            }
            case "/call": {
                break;
            }
            case "/raise": {
                break;
            }
            case "/fold": {
                break;
            }

            default:
                return "Invalid command";
        }
        return "Invalid command";
    }


}