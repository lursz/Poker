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


    private boolean wonThisRound_;
    private int roundsPlayed_;
    private boolean folded;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public Player(String name, int balance, SelectionKey key) {
        this.name_ = name;
        this.balance_ = balance;
        this.hand_ = new Hand();
        this.key_ = key;
    }

    public String getName_() {
        return name_;
    }
    public void setName_(String name_) {
        this.name_ = name_;
    }
    public int getBalance_() {
        return balance_;
    }
    public int getBet_() {
        return bet_;
    }

    public Hand getHand_() {
        return hand_;
    }
    public int getRoundsPlayed_() {
        return roundsPlayed_;
    }

    public SelectionKey getKey_() {
        return key_;
    }
    public boolean getWonThisRound_() {
        return wonThisRound_;
    }

    public void setWonThisRound_(boolean wonThisRound_) {
        this.wonThisRound_ = wonThisRound_;
    }


    /* -------------------------------------------------------------------------- */
    /*                                   Methods                                  */
    /* -------------------------------------------------------------------------- */
    public void setBet(int bet_amount) throws BalanceTooLow {
        if (bet_amount > balance_)
            throw new BalanceTooLow(name_ + " has insufficient funds");

        bet_ = bet_amount;
        balance_ -= bet_amount;
    }
    public void addBalance(int amount) {
        balance_ += amount;
    }


}
