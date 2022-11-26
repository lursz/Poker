package org.cards.game;

import org.cards.object.*;
import org.cards.player.*;
import org.cards.exceptions.*;

import java.nio.channels.SelectionKey;
import java.util.*;

public class Game {
    //Object of the game
    private Deck deck_;
    private int balance_ = 1000;
    //Ready?
    private int numberOfPlayers;
    private int numberOfReadyPlayers;
    private boolean initialized_;
    private int currentRoundsNumber;

    //private ArrayList<Player> players_;
    public static Map<SelectionKey, Player> playerMap = new HashMap<>();

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
                        "/hand - show cards\n " +
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
            case "/hand": {
                if (initialized_) {
                    return new Pair(player.getHand_().toString(), false);
                } else {
                    return new Pair("Game not started", false);
                }

            }
            //Check - to not bet without folding.
            case "/check": {
                if (initialized_) {
                    player.incrementRoundsPlayed();
                    String answer = player.getName_() + " checked";
                    return new Pair(answer, true);
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
            //Call - a bet that is the equal amount to the bet made prior.
            case "/call": {
                if (initialized_) {
                    player.incrementRoundsPlayed();

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
                    player.incrementRoundsPlayed();
                    player.
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
        for (Player player : playerMap.values()) {
            deck_.deal(player, 5);
        }
    }

    void calculateRoundWinner() {
        //Class ScorePair( Player, int)
        class scorePair {
            public Player player;
            public int score;

            public scorePair(Player player, int score) {
                this.player = player;
                this.score = score;
            }
        }
        Vector<scorePair> playerScores = new Vector<>();

        for (Player player1 : playerMap.values()) {
            for (Player player2 : playerMap.values()) {
                if (player1 != player2)
                    //ADDING 1 POINT FOR WINNING A COMPARISON
                    if (playerMap.get(player1).getHand_().isBetterThan(playerMap.get(player2).getHand_()) == 1)
                        //If player1 already in playerScores
                        if (playerScores.stream().anyMatch(p -> p.player == player1))
                            playerScores.stream().filter(p -> p.player == player1).findFirst().get().score++;
                        else
                            playerScores.add(new scorePair(player1, 1));

                //DIVIDING A POT BETWEEN PLAYERS WITH THE SAME SCORE
                //if only one player has the highest score
                if (playerScores.stream().filter(p -> p.score == playerScores.stream().max(Comparator.comparingInt(p -> p.score)).get().score).count() == 1)
                    playerScores.stream().filter(p -> p.score == playerScores.stream().max(Comparator.comparingInt(p -> p.score)).get().score).findFirst().get().player.setBalance_(playerScores.stream().filter(p -> p.score == playerScores.stream().max(Comparator.comparingInt(p -> p.score)).get().score).findFirst().get().player.getBalance_() + balance_);
                else
                    //if more than one player has the highest score
                    for (scorePair scorePair : playerScores) {
                        if (scorePair.score == playerScores.stream().max(Comparator.comparingInt(p -> p.score)).get().score)
                            scorePair.player.setBalance_(scorePair.player.getBalance_() + balance_ / playerScores.stream().filter(p -> p.score == playerScores.stream().max(Comparator.comparingInt(p -> p.score)).get().score).count());
                    }
            }
        }
    }




}
