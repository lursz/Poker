package org.cards.game;

import org.cards.object.*;
import org.cards.player.*;
import org.cards.exceptions.*;

import java.util.ArrayList;

public class Game implements Runnable {
    private Deck deck_;
    private ArrayList<Player> players_;
    private int numberOfPlayers;
    private boolean initialized_;
    private int currentRoundsNumber;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public Game() {
        this.deck_ = new Deck();
        this.players_ = new ArrayList<Player>();
        initialized_ = false;
    }



    /* -------------------------------------------------------------------------- */
    /*                                  Game flow                                 */
    /* -------------------------------------------------------------------------- */


    /*
    @Override
    public void run() {
        while (true) {
            try {
                //this.init();
                //this.startNewRound();
                while (this.currentRoundsNumber < this.numberOfPlayers) {
                    //this.getCurrentPlayer().bet(10);
                    this.currentRoundsNumber++;
                }
            } catch (PlayerInvalidCardsNumber | BalanceTooLow e) {
                e.printStackTrace();
                break;
            }
        }

    }


    ///* -------------------------------------------------------------------------- */
    ///*                            Server Communication                            */
    ///* -------------------------------------------------------------------------- */
    public String receiveCommands(String[] command) {

        try {
            switch (command[0]) {
                case "help":
                    return "/help - show the list of commands\n " +
                            "/ready - start the game\n " +
                            "<MONEY>\n " +
                            "/showCards - show cards\n " +
                            "/set <amount> - make a bet \n " +
                            "/deal - deal cards to all players\n " +
                            "<GAME>\n " +
                            "/exit - exit the game\n";
                break;
                case "ShowCards":

                    break;

                default:
                    return "Invalid command";
            }
        } catch (InvalidCommand e) {
            return "Invalid command";
        }
    }




    /* ----------------------------- Begin the game ----------------------------- */
    private void init(int playersQuantity, int startingBalance) throws GameAlreadyInitialized {
        if (this.initialized_) throw new GameAlreadyInitialized("Game has already been initialized");

        this.numberOfPlayers = playersQuantity;
        this.players_ = new ArrayList<>();

        for (int i = 0; i < this.numberOfPlayers; i++) {
            System.out.println("Player" + (i + 1) + " : ");
            this.players_.add(new Player(playerName, startingBalance));
        }
        this.initialized_ = true;
    }

    private void startNewRound() throws PlayerInvalidCardsNumber {
        this.deck_ = new Deck();
        this.deck_.shuffle();
        for (int i = 0; i < this.numberOfPlayers; i++) {
            this.players_.get(i).setHand(this.deck.popCards(5));
        }
        this.currentRoundsNumber = 0;
    }

    private String getCurrentPlayerState() {
        Player currentPlayer = this.getCurrentPlayer();
        ArrayList<Card> currentPlayerCards = currentPlayer.getCards();
        String userHand = "" + currentPlayer.getName();
        for (Card card : currentPlayerCards) {
            userHand += " " + card.getCode();
        }
        return userHand + " " + currentPlayer.getBalance();
    }

*/
}