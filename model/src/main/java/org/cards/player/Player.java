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
    private String name_;
    private ArrayList<Card> hand_;
    private int numberOfCards_;

    // Constructor
    public Player(String name) {
        this.name_ = name;
        this.hand_ = new ArrayList<Card>();
        this.numberOfCards_ = 0;
    }

    // Methods
    public void addCard(Card card) {
        hand_.add(card);
        numberOfCards_++;
    }

    public void printHand() {
        for (Card i : hand_) {
            System.out.println(i);
        }
    }


}
