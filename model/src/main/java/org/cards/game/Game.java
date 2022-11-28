package org.cards.game;

import org.cards.object.*;
import org.cards.player.*;
import org.cards.exceptions.*;

import javax.print.attribute.standard.PrintQuality;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.*;


public class Game {
    //Object of the game
    private Deck deck_;

    public static final ArrayList<Player> players_ = new ArrayList<>();
    public static final Map<SelectionKey, Player> playerMap = new HashMap<>();

    private int pot_;



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

    void setPot_(int pot_) {
        this.pot_ = pot_;
    }
    int getPot_() {
        return this.pot_;
    }
    int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void addNumberOfPlayers() {
        numberOfPlayers++;
    }

    /* -------------------------------------------------------------------------- */
    /*                            Master Game Function                            */
    /* -------------------------------------------------------------------------- */

    /**
     * Change the current player to next player
     */
    public void nextPlayer() {
        do {
            currentPlayerIndex++;
            if (currentPlayerIndex == numberOfPlayers) {
                currentPlayerIndex = 0;
            }
        } while (players_.get(currentPlayerIndex).isFolded_());
    }

    /**
     * Function communicating with the server
     * @param command
     * @param player
     * @return
     */
    public Pair receiveCommands(String command, Player player) {
        String[] commandParts = command.split(" ");
        switch (commandParts[0]) {
            //List of commands
            case "/help": {
                return new Pair("help - show the list of commands\n " +
                        "/ready - start the game\n " +
                        "<CARDS>\n " +
                        "/hand - show cards and balance and pot\n " +
                        "/check - continue without placing a wager \n " +
                        "/call - match the wager \n " +
                        "/raise <amount> - raise the wager \n " +
                        "/fold - withdraw from the round\n " +
                        "/exchange - exchange your cards during exchange phase. Syntax is: /exchange 1,2,5 or '-' for no exchange.\n " +
                        "<GAME>\n " +
                        "/exit - exit the game\n", false);
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
                            return new Pair("Game is starting now, use /hand to view cards.", true);
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
                    //show hand and balance and pot
                    return new Pair("Your cards: " + player.getHand_().toString() + "\n" +
                            "Your balance: " + player.getBalance_() + "\n" +
                            "Pot: " + pot_, false);

                } else {
                    return new Pair("Game not started", false);
                }

            }
            //Check - to not bet without folding.
            case "/check": {

                if (gameState == 0) {
                    return new Pair("Game hasn't started yet", false);
                }
                if (gameState == 2) {
                    return new Pair("You can't check now", false);
                }
                if (gameState == 5) {
                    return new Pair("Game is over", false);
                }
                if (player.isFolded_()) {
                    return new Pair("You folded, wait for next round", false);
                }
                if (currentPlayerIndex != players_.indexOf(player)) {
                    return new Pair("It's not your turn", false);
                }
                if (currentBet > 0) {
                    return new Pair("You can't check now, the bet is greater than 0", false);
                }
                if (initialized_) {
                    player.setBet_(0);
                    currentBet = 0;
                    nextPlayer();
                    String answer = "--"+player.getName_() + " checked--";
                    endFirstRoundBets();
                    endOfSecondRoundBets();
                    if (gameState == 4) {
                        return new Pair(answer + "\nShowdown time! Are you ready? Type /showdown to see the results appear on everyone's screen.", true);
                    } else if (gameState == 2) {
                        return new Pair(answer +"\nIt's time to exchange your cards using /exchange <number, [number]>. Then proceed with next betting round.", true);
                    }
                    return new Pair(answer, true);
                } else {
                    return new Pair("Game not started", false);
                }

            }
            case "/bet": {
                if (gameState == 0) {
                    return new Pair("Game hasn't started yet", false);
                }
                if (gameState == 2) {
                    return new Pair("You can't bet now", false);
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
                            if (bet < 0) {
                                return new Pair("Bet must be positive", false);
                            }
                            if (bet < currentBet) {
                                return new Pair("Bet must be greater than current bet", false);
                            }
                            if (bet > player.getBalance_()) {
                                return new Pair("Not enough money", false);
                            } else {
                                if(player.getBet_() == -1) {
                                    player.setBet_(0);
                                }
                                pot_ += bet-player.getBet_();
                                player.setBalance_(player.getBalance_()-(bet-player.getBet_()));
                                player.setBet(bet);

                                currentBet = bet;
                                String answer = "--"+player.getName_() + " bet " + bet + "--";
                                nextPlayer();
                                endFirstRoundBets();
                                endOfSecondRoundBets();
                                if (gameState == 4) {
                                    return new Pair(answer + "\nShowdown time! Are you ready? Type /showdown to see the results appear on everyone's screen.", true);
                                } else if (gameState == 2) {
                                    return new Pair(answer +"\nIt's time to exchange your cards using /exchange <number,[number]> (no comma). Then proceed with next betting round.", true);
                                }
                                return new Pair(answer, true);
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
                if (gameState == 2) {
                    return new Pair("You can't call now", false);
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
                        if(player.getBet_() == -1) {
                            player.setBet_(0);
                        }
                        pot_ += currentBet-player.getBet_();
                        player.setBalance_(player.getBalance_()-(currentBet-player.getBet_()));
                        player.setBet_(currentBet);
                        String answer = "--"+player.getName_() + " called. The current bet is: "+currentBet+" --";
                        nextPlayer();
                        endFirstRoundBets();
                        endOfSecondRoundBets();
                        if (gameState == 4) {
                            return new Pair(answer + "\nShowdown time! Are you ready? Type /showdown to see the results appear on everyone's screen.", true);
                        } else if (gameState == 2) {
                            return new Pair(answer +"\nIt's time to exchange your cards using /exchange <number, [number]>. Then proceed with next betting round.", true);
                        }
                        return new Pair(answer, true);
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
                if (gameState == 2) {
                    return new Pair("You can't raise now", false);
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
                        if (bet < 0) {
                            return new Pair("Bet must be positive", false);
                        }
                        if (bet > player.getBalance_()) {
                            return new Pair("Not enough money", false);
                        } else {
                            if(player.getBet_() == -1) {
                                player.setBet_(0);
                            }
                            currentBet += bet;
                            pot_ += bet;
                            player.setBalance_(player.getBalance_()-bet);
                            player.setBet_(currentBet);
                            String answer = "--"+player.getName_() + " raised to: " + currentBet + "--";
                            nextPlayer();
                            endFirstRoundBets();
                            endOfSecondRoundBets();
                            if (gameState == 4) {
                                return new Pair(answer + "\nShowdown time! Are you ready? Type /showdown to see the results appear on everyone's screen.", true);
                            } else if (gameState == 2) {
                                return new Pair(answer +"\nIt's time to exchange your cards using /exchange <number, [number]>. Then proceed with next betting round.", true);
                            }
                            return new Pair(answer, true);
                        }

                    } else {
                        return new Pair("Wrong command", false);
                    }
                } else {
                    return new Pair("Game not started", false);
                }

            }
            case "/fold": {

                if (currentPlayerIndex != players_.indexOf(player)) {
                    return new Pair("It's not your turn", false);
                }
                if (player.isFolded_())
                    return new Pair("You folded, wait for next round", false);
                if (initialized_) {
                    player.incrementRoundsPlayed();
                    player.fold();
                    endOfSecondRoundBets();
                    return new Pair("Player " + player.getName_() + "folded. He is out of the game.", true);
                } else {
                    return new Pair("Game not started", false);
                }

            }
            case "/exchange": {
                if (player.isFolded_())
                    return new Pair("You folded, wait for next round", false);
                if (gameState == 0) {
                    return new Pair("Game hasn't started yet", false);
                }

                if (gameState == 1 || gameState == 3 || gameState == 4) {
                    return new Pair("Can't exchange in this part of the game.", false);
                }
                if (gameState == 5) {
                    return new Pair("Game is over", false);
                }
                if (player.wereTheHandsChanged_) {
                    return new Pair("You already changed your cards", false);
                }
                if (initialized_) {
                    if (commandParts.length == 2) {
                        if (commandParts[1].equals("-")) {
                            player.wereTheHandsChanged_ = true;
                            endOfStateWaitingForCardsChangePhase();
                            return new Pair("No cards were exchanged", false);
                        }
                        String[] splittedCards = commandParts[1].split(",");
                        int[] indexOfCardsToExchange = new int[splittedCards.length];
                        for (int i = 0; i < splittedCards.length; i++) {
                            indexOfCardsToExchange[i] = Integer.parseInt(splittedCards[i]);
                        }

                        player.getHand_().exchangeCards(indexOfCardsToExchange, deck_);
                        player.wereTheHandsChanged_ = true;
                        endOfStateWaitingForCardsChangePhase();


                        return new Pair("Cards exchanged.\nYour cards: " + player.getHand_().toString() + "\n" + "Your balance: " + player.getBalance_() + "\n" + "Pot: " + pot_, false);

                    } else {
                        return new Pair("Wrong command", false);
                    }
                } else {
                    return new Pair("Game not started", false);
                }

            }
            case "/showdown": {
                if (initialized_) {
                    if (gameState == 4) {
                        if (commandParts.length == 1) {
                            String answer = "";
                            for (Player player_i : playerMap.values()) {
                                answer += player_i.getName_() + ": " + player_i.getHand_().toString() + "\n";
                            }
                            //show nickname of the winner
                            Player winner = calculateRoundWinner();
                            answer += "Winner: " + winner.getName_() + "\n";
                            endOfStateGameOverStartNextRound();
                            return new Pair(answer, true);
                        } else {
                            return new Pair("Wrong command", false);
                        }
                    } else {
                        return new Pair("Can't use the command", false);
                    }
                }
                return new Pair("Can't use the command", false);

            }

            default:
                return new Pair("Wrong command", false);

        }

    }
    /* -------------------------------------------------------------------------- */
    /*                                   Methods                                  */
    /* -------------------------------------------------------------------------- */

    /**
     * Function to send a message to client without a request
     * @param client
     * @param response
     */
    private void sendResponse(SocketChannel client, String response) {
        try {

            ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
            client.write(responseBuffer);
        } catch(Exception error) {
            System.out.println("Something went wrong while sending a message");
        }

    }

    /**
     * Function to show everyone's cards to everyone
     * @return
     */
    public Pair showdown() {
        //showdown - send everyone's hands to everyone
        String answer = "";
        for (Player player : playerMap.values()) {
            answer += player.getName_() + ": " + player.getHand_().toString() + "\n";
        }
        return new Pair(answer, true);
    }

    /**
     * Function to trigger calculating the winning hand and grant points
     * @return
     */
    Player calculateRoundWinner() {
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
        }
        playersLeft.sort(new Comparator<Player>() {
            @Override
            public int compare(Player player, Player t1) {
                return t1.getHand_().isBetterThan(player.getHand_());
            }
        });

        int numberOfWinners = 1;
        int winnerScore = playersLeft.get(0).score;
        for (int i = 1; i < numberOfPlayersLeft; i++) {
            if (playersLeft.get(i).getHand_().isBetterThan(playersLeft.get(0).getHand_())==0) {
                numberOfWinners++;
            } else {
                break;
            }
        }

        int moneyPerWinner = pot / numberOfWinners;
        int moneyLeft = pot % numberOfWinners;

        for (int i = 0; i < numberOfWinners; i++) {
            playersLeft.get(i).setBalance_(playersLeft.get(i).getBalance_() + moneyPerWinner);
            playersLeft.get(i).isWinner_ = true;
        }
        return playersLeft.get(0);
    }

    /*
    Player calculateRoundWinner() {
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
            playersLeft.get(i).isWinner_ = true;
        }
        return playersLeft.get(0);
    }

     */

    /**
     * Function checking for end of round of betting
     * @return
     */
    boolean isItEndOfBetting() {
        int numberOfUnfoldedPlayers = 0;
        boolean everyPlayer = true;
        for (Player player : players_) {
            System.out.println(player.getBet_());
            if (player.getBet_() != currentBet && !player.isFolded_())
                everyPlayer = false;
            if (!player.isFolded_()) {
                numberOfUnfoldedPlayers++;
            }
        }
        if (numberOfUnfoldedPlayers == 1) {
            return true;
        }
        return everyPlayer;


    }


    /* -------------------------------------------------------------------------- */
    /*                                 Game States                                */
    /* -------------------------------------------------------------------------- */

    /**
     * Triggered at the start of the game
     */
    void startGame() {
        deck_.shuffle();
        for (Player player : playerMap.values()) {
            deck_.deal(player, 5);
            player.setBet_(-1);
        }
        nextPlayer();
        gameState = 1;
    }
    /**
     * Triggered at the end of first found of bets
     */
    boolean endFirstRoundBets() {
        if (gameState != 1 || !isItEndOfBetting()) {
            return false;
        }

        gameState = 2;
        for (Player player : players_) {
            player.setBet_(-1);
        }
        currentBet = 0;
        currentPlayerIndex = 0;

        return true;
    }
    /**
     * Triggered at the end of exchange phase
     */
    boolean endOfStateWaitingForCardsChangePhase() {
        if (gameState != 2)
            return false;
        boolean everyPlayer = true;
        for (Player player : players_) {
            if (!player.wereTheHandsChanged_)
                return false;
        }
        gameState = 3;
        for (Player player : players_) {
            player.setBet_(-1);
        }

        currentBet = 0;
        currentPlayerIndex = 0;

        return true;
    }
    /**
     * Triggered at the end of second round of bets
     */
    void endOfSecondRoundBets() {
        if (gameState != 3 || !isItEndOfBetting())
            return;
        gameState = 4;
        for (Player player : players_) {
            player.setBet_(0);
        }
        currentBet = 0;
        currentPlayerIndex = 0;
        calculateRoundWinner();
        showdown();
    }
    /**
     * Triggered at the very end of the round
     */
    void endOfStateGameOverStartNextRound() {
        if (gameState != 5) {
            return;
        }
        gameState = 0;

        for (Player player : players_) {
            player.resetForNextRound();
        }
    }
}

