package org.cards.object;

import org.cards.player.Player;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class describing a deck of cards
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

    /**
     * Function resets deck and returns it in order.
     * @param tempDeck
     * @return
     */
    public Deck reset(Deck tempDeck) {
        tempDeck.deck_.clear();
        for (Card.suits iSuit : Card.suits.values()) {
            for (Card.ranks iRank : Card.ranks.values()) {
                deck_.add(new Card(iSuit, iRank));
            }
        }
        return tempDeck;
    }

    /**
     * Function to shuffle deck
     */
    public void shuffle() {
        Collections.shuffle(deck_);
    }

    /**
     * Function to draw card from the deck
     * @return
     */
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

    /**
     * Function dealing cards to player's hands
     * @param player_
     * @param numberOfCardsToDeal
     */
    public void deal(Player player_, int numberOfCardsToDeal) {
        for (int i = 0; i < numberOfCardsToDeal; i++) {
                player_.getHand_().addCard(draw());
                cardsLeft_--;
            }
        }

    /**
     * Function printing deck
     */
    public void printDeck() {
        for (Card i : deck_) {
            System.out.println(i);
        }
    }


}
