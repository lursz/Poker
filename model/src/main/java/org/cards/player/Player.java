package org.cards.player;

import org.cards.exceptions.BalanceTooLow;
import org.cards.object.*;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;

/*
 * Class describing player
 * @param name - player's name
 * @param hand - player's hand
 * @param numberOfCards - size of player's hand
 */
public class Player {


    /* -------------------------------------------------------------------------- */
    /*                                 Arguements                                 */
    /* -------------------------------------------------------------------------- */
    private String name_;
    private SelectionKey key_;
    private Hand hand_;

    private int balance_;

    private int bet_;

    public int score = 0;

    private boolean wonThisRound_;

    //Folded?
    private boolean folded_ = false;

    public boolean isWinner_ = false;
    public boolean isFolded_() {
        return folded_;
    }
    public void fold() {
        this.folded_ = true;
    }
    public void unFold() {
        this.folded_ = false;
    }

    //How many rounds?
    private int roundsPlayed_;
    public boolean wereTheHandsChanged_ = false;

    public void incrementRoundsPlayed() {
        roundsPlayed_++;
    }
    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */

    public Player(String name, int balance, SelectionKey key) {
        this.name_ = name;
        this.balance_ = balance;
        this.hand_ = new Hand();
        this.key_ = key;
    }

    public void resetForNextRound() {
        this.hand_ = new Hand();
        this.folded_ = false;
        this.wonThisRound_ = false;
        this.bet_ = -1;
        this.isWinner_ = false;
    }
    public String getName_() {
        return name_;
    }

    /**
     * Give player a name
     * @param name_
     */
    public void setName_(String name_) {
        this.name_ = name_;
    }

    /**
     * Set balance of player's account
     * @param balance_
     */
    public void setBalance_(int balance_) {
        this.balance_ = balance_;
    }
    public int getBalance_() {
        return balance_;
    }
    public int getBet_() {
        return bet_;
    }
    public void setBet_(int bet_) {
        this.bet_ = bet_;
    }

    public Hand getHand_() {
        return hand_;
    }
    public int getRoundsPlayed_() {
        return roundsPlayed_;
    }

    /**
     * Return players key
     * @return
     */
    public SelectionKey getKey_() {
        return key_;
    }
    //Won this round
    public boolean getWonThisRound_() {
        return wonThisRound_;
    }
    public void setWonThisRound_(boolean wonThisRound_) {
        this.wonThisRound_ = wonThisRound_;
    }


    /* -------------------------------------------------------------------------- */
    /*                                   Methods                                  */
    /* -------------------------------------------------------------------------- */

    /**
     * Subtract
     * @param bet_amount
     * @throws BalanceTooLow
     */
    public void setBet(int bet_amount) throws BalanceTooLow {
        if (bet_amount > balance_)
            throw new BalanceTooLow(name_ + " has insufficient funds");

        if (bet_ == -1)
            balance_ -= bet_amount;
        else
            balance_ -= bet_amount - bet_;
        bet_ = bet_amount;
    }

    /**
     * Add value to players balance
     * @param amount
     */
    public void addBalance(int amount) {
        balance_ += amount;
    }




}
