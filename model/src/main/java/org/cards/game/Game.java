package org.cards.game;

import org.cards.object.*;
import org.cards.player.*;
import org.cards.exceptions.*;

import java.util.ArrayList;

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



    /* -------------------------------------------------------------------------- */
    /*                                   Methods                                  */
    /* -------------------------------------------------------------------------- */

    public Pair receiveCommands(String command, Player player) {
        String[] commandParts = command.split(" ");
        switch (commandParts[0]) {
            //List of commands
            case "/help": {
                return new Pair("help - show the list of commands\n " +
                        "/ready - start the game\n " +
                        "<CARDS>\n " +
                        "/showCards - show cards\n " +
                        "/check - continue without placing a wager \n " +
                        "/bet <amount> - make a bet \n " +
                        "/call - match the wager \n " +
                        "/raise - raise the wager \n " +
                        "/fold - withdraw from the round\n " +
                        "<GAME>\n " +
                        "/exit - exit the game\n", true);
            }
            //Set username
            case "/usr": {
                if (commandParts.length == 2) {
                    if (initialized_) {
                        return new Pair("Game already started", false);
                    } else {
                        player.setName_(commandParts[1]);
                        System.out.println("Player " + player.getName_() + " joined the game.");
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
                            System.out.println("Game has begun");
                            startGame();
                            return new Pair("Game is starting now", true);
                        } else {
                            return new Pair("Waiting for other players", false);
                        }
                    }
                }

            }
            case "/showCards": {
                if (initialized_) {
                    return new Pair(player.getHand_().toString(), false);
                } else {
                    return new Pair("Game not started", false);
                }

            }
            case "/check": {
                if (initialized_) {
                    return new Pair("Check", true);
                } else {
                    return new Pair("Game not started", false);
                }

            }
            case "/bet": {
                if (initialized_) {
                    if (commandParts.length == 2) {
                        try {
                            int bet = Integer.parseInt(commandParts[1]);
                            if (bet > player.getBalance_()) {
                                return new Pair("Not enough money", false);
                            } else {
                                player.setBet(bet);
                                return new Pair("Bet " + bet, false);
                            }
                        } catch (NumberFormatException e) {
                            return new Pair("Wrong command", false);
                        } catch (BalanceTooLow e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        return new Pair("Wrong command", false);
                    }
                } else {
                    return new Pair("Game not started", false);
                }

            }
            case "/call": {
                if (initialized_) {
                    return new Pair("Call", false);
                } else {
                    return new Pair("Game not started", false);
                }

            }
            case "/raise": {
                if (initialized_) {
                    return new Pair("Raise", false);
                } else {
                    return new Pair("Game not started", false);
                }

            }
            case "/fold": {
                if (initialized_) {
                    return new Pair("Fold", false);
                } else {
                    return new Pair("Game not started", false);
                }

            }

            default:
                return new Pair("Wrong command", false);

        }

    }
    void startGame() {
        deck_.shuffle();
        for (int i = 0; i < numberOfPlayers; i++) {
//            deck_.dealCard();
        }
    }
//TODO: Przenies HashMape do klasy game, server i tak ma dostep
//    TODO: Napraw przesylanie


}