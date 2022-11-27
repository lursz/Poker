package org.cards.game;

import org.cards.object.*;
import org.cards.player.*;
import org.cards.exceptions.*;

import javax.print.attribute.standard.PrintQuality;
import java.nio.channels.SelectionKey;
import java.util.*;

public class Game {
    //Object of the game
    private Deck deck_;
    private int balance_ = 1000;

    private int gameState = 0;
    // 0 - waiting for players
    // 1 - first round bets
    // 2 - waiting for cards to change
    // 3 - second round bets
    // 4 - after showdown
    // 5 - game over

    private int currentPlayerIndex = 0;

    private int currentBet = 0;

    private int numberOfPlayers;
    //Ready?
    private int numberOfReadyPlayers;
    //Ready?

    private boolean initialized_;
    private int currentRoundsNumber;

    public ArrayList<Player> players_;
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
    /*                            Master Game Function                            */
    /* -------------------------------------------------------------------------- */

    public void nextPlayer() {
        do {
            currentPlayerIndex++;
            if (currentPlayerIndex == numberOfPlayers) {
                currentPlayerIndex = 0;
            }
        } while (players_.get(currentPlayerIndex).isFolded_());
    }

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
                if (gameState != 0) {
                    return new Pair("You can't change your username now", false);
                }

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
                if (gameState != 0) {
                    return new Pair("You cannot start the game now", false);
                }

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
                if (gameState == 0) {
                    return new Pair("Game hasn't started yet", false);
                }
                if (gameState == 5) {
                    return new Pair("Game is over", false);
                }
                if (player.isFolded_())
                    return new Pair("You folded, wait for next round", false);
                if (initialized_) {
                    return new Pair(player.getHand_().toString(), false);
                } else {
                    return new Pair("Game not started", false);
                }

            }
            //Check - to not bet without folding.
            case "/check": {

                if (gameState == 0) {
                    return new Pair("Game hasn't started yet", false);
                }
                if (gameState == 3) {
                    return new Pair("You can't check now", false);
                }
                if (gameState == 5) {
                    return new Pair("Game is over", false);
                }
                if (player.isFolded_())
                    return new Pair("You folded, wait for next round", false);

                if (currentPlayerIndex != players_.indexOf(player)) {
                    return new Pair("It's not your turn", false);
                }
                if (initialized_) {
                    player.setBet_(0);
                    nextPlayer();
                    String answer = player.getName_() + " checked";
                    return new Pair(answer, true);
                } else {
                    return new Pair("Game not started", false);
                }

            }
            case "/bet": {
                if (gameState == 0) {
                    return new Pair("Game hasn't started yet", false);
                }
                if (gameState == 3) {
                    return new Pair("You can't check now", false);
                }
                if (gameState == 5) {
                    return new Pair("Game is over", false);
                }
                if (player.isFolded_())
                    return new Pair("You folded, wait for next round", false);

                if (currentPlayerIndex != players_.indexOf(player)) {
                    return new Pair("It's not your turn", false);
                }
                if (initialized_) {
                    if (commandParts.length == 2) {
                        try {
                            int bet = Integer.parseInt(commandParts[1]);
                            if (bet > player.getBalance_()) {
                                return new Pair("Not enough money", false);
                            } else {
                                player.setBet(bet);
                                nextPlayer();
                                return new Pair("--Player " + player.getName_() + " bet --" + bet, true);
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
                if (gameState == 0) {
                    return new Pair("Game hasn't started yet", false);
                }
                if (gameState == 3) {
                    return new Pair("You can't check now", false);
                }
                if (gameState == 5) {
                    return new Pair("Game is over", false);
                }

                if (player.isFolded_())
                    return new Pair("You folded, wait for next round", false);

                if (currentPlayerIndex != players_.indexOf(player)) {
                    return new Pair("It's not your turn", false);
                }

                if (initialized_) {
                    if (commandParts.length == 1) {
                        player.setBet_(currentBet);
                        nextPlayer();
                        return new Pair("--Player " + player.getName_() + " called. Current bet is: " + currentBet, true);
                    } else {
                        return new Pair("Wrong command", false);
                    }
                } else {
                    return new Pair("Game not started", false);
                }

            }
            case "/raise": {
                if (gameState == 0) {
                    return new Pair("Game hasn't started yet", false);
                }
                if (gameState == 3) {
                    return new Pair("You can't check now", false);
                }
                if (gameState == 5) {
                    return new Pair("Game is over", false);
                }

                if (player.isFolded_())
                    return new Pair("You folded, wait for next round", false);

                if (currentPlayerIndex != players_.indexOf(player)) {
                    return new Pair("It's not your turn", false);
                }
                if (initialized_) {
                    if (commandParts.length == 2) {

                        int bet = Integer.parseInt(commandParts[1]);
                        if (bet > player.getBalance_()) {
                            return new Pair("Not enough money", false);
                        } else {
                            currentBet += bet;
                            player.setBet_(currentBet);
                            nextPlayer();
                            return new Pair("--Player " + player.getName_() + " raised --" + bet, true);
                        }

                    } else {
                        return new Pair("Wrong command", false);
                    }
                } else {
                    return new Pair("Game not started", false);
                }

            }
            case "/fold": {
                if (initialized_) {
                    player.incrementRoundsPlayed();
                    player.fold();
                    return new Pair("Fold", false);
                } else {
                    return new Pair("Game not started", false);
                }

            }

            default:
                return new Pair("Wrong command", false);

        }

    }
    /* -------------------------------------------------------------------------- */
    /*                                   Methods                                  */
    /* -------------------------------------------------------------------------- */


    void startGame() {
        deck_.shuffle();
        for (Player player : playerMap.values()) {
            deck_.deal(player, 5);
        }
        gameState = 1;
    }

    //showdown - send everyone's hands to everyone
    public Pair showdown() {
        String answer = "";
        for (Player player : playerMap.values()) {
            answer += player.getName_() + ": " + player.getHand_().toString() + "\n";
        }
        return new Pair(answer, true);
    }

    void calculateRoundWinner() {
        int numberOfPlayersLeft = 0;
        ArrayList<Player> playersLeft = new ArrayList<>();
        int pot = 0;
        for (Player player1 : playerMap.values()) {
            player1.score = 0;
            pot += player1.getBet_();
            if (!player1.isFolded_()) {
                numberOfPlayersLeft++;
                playersLeft.add(player1);
            } else {
                continue;
            }
            for (Player player2 : playerMap.values()) {
                if (player1 != player2 && !player2.isFolded_()) {
                    //ADDING 1 POINT FOR WINNING A COMPARISON
                    if (player1.getHand_().isBetterThan(player2.getHand_()) > 0) {
                        player1.score++;
                    }
                }
            }
        }

        playersLeft.sort(Comparator.comparingInt(o -> -o.score));

        int numberOfWinners = 1;
        int winnerScore = playersLeft.get(0).score;
        for (int i = 1; i < numberOfPlayersLeft; i++) {
            if (playersLeft.get(i).score == winnerScore) {
                numberOfWinners++;
            } else {
                break;
            }
        }

        int moneyPerWinner = pot / numberOfWinners;
        int moneyLeft = pot % numberOfWinners;

        for (int i = 0; i < numberOfWinners; i++) {
            playersLeft.get(i).setBalance_(playersLeft.get(i).getBalance_() + moneyPerWinner);
        }
    }
}
