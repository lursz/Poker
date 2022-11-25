package org.cards.player;

import org.cards.object.*;
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
    final private String name_;
    private int balance_;
    private int bet_;
    private Hand hand_;
    private int roundsPlayed_;
    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public Player(String name, int balance) {
        this.name_ = name;
        this.balance_ = balance;
        this.hand_ = new Hand();
    }

    public String getName_() {
        return name_;
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

    /* -------------------------------------------------------------------------- */
    /*                                   Methods                                  */
    /* -------------------------------------------------------------------------- */
    public void bet(int bet_amount) throws BalanceTooLow {
        if (bet_amount > balance_)
            throw new BalanceTooLow(name_ + " has insufficient funds");

        bet_ = bet_amount;
        balance_ -= bet_amount;
    }


}
