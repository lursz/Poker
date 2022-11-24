package org.cards.object;

import org.cards.object.Card;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Class describing a deck of cards
 * @param cards - list of cards
 * @param cardsLeft - number of cards left in the deck
 * @param size_ - size of the deck
 */
public class Deck {
    /* -------------------------------------------------------------------------- */
    /*                                 Arguements                                 */
    /* -------------------------------------------------------------------------- */
    private ArrayList<Card> deck_;
    private int size_;
    private int cardsLeft_;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public Deck() {
        deck_ = new ArrayList<Card>();
        this.size_ = 52;
        this.cardsLeft_ = 52;
        for (Card.suits iSuit : Card.suits.values()) {
            for (Card.ranks iRank : Card.ranks.values()) {
                deck_.add(new Card(iSuit, iRank));
            }
        }

    }

    /* -------------------------------------------------------------------------- */
    /*                                   Getters                                  */
    /* -------------------------------------------------------------------------- */
    public int getSize_() {
        return size_;
    }

    public int getCardsLeft_() {
        return cardsLeft_;
    }

   /* -------------------------------------------------------------------------- */
   /*                                   Methods                                  */
   /* -------------------------------------------------------------------------- */
    public Deck reset(Deck tempDeck) {
        tempDeck.deck_.clear();
        for (Card.suits iSuit : Card.suits.values()) {
            for (Card.ranks iRank : Card.ranks.values()) {
                deck_.add(new Card(iSuit, iRank));
            }
        }
        return tempDeck;
    }

    public Deck shuffle(Deck tempDeck) {
        Collections.shuffle(tempDeck.deck_);
        return tempDeck;
    }

    public Card draw() {
        if (cardsLeft_ > 0) {
            Card tempCard = deck_.get(0);
            deck_.remove(0);
            cardsLeft_--;
            return tempCard;
        } else {
            return null;
        }
    }

    public void printDeck() {
        for (Card i : deck_) {
            System.out.println(i);
        }
    }


}
