package org.cards.object;
import java.lang.*;
/**
 * Class describing a card
 */
public class Card {
     /* ------------------------------ Enum template ----------------------------- */
     enum suits {
        CLUBS, HEARTS, SPADES, DIAMONDS
    }

    enum ranks {
        ACE(14), KING(13), QUEEN(12), JACK(11), _10(10), _9(9), _8(8), _7(7), _6(6), _5(5), _4(4), _3(3), _2(2);
        private int rankValue;
        ranks(int i) {
            this.rankValue = i;
        }
        public int getRankValue() {
            return rankValue;
        }
    }


    /* -------------------------------------------------------------------------- */
    /*                                 Arguements                                 */
    /* -------------------------------------------------------------------------- */
    private final suits suit;
    private final ranks rank;


     /* -------------------------------------------------------------------------- */
     /*                                Constructors                                */
     /* -------------------------------------------------------------------------- */
    public Card(Card.suits suit, Card.ranks rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /* -------------------------------------------------------------------------- */
    /*                                   Getters                                  */
    /* -------------------------------------------------------------------------- */
    public suits getSuit() {
        return suit;
    }
    public ranks getRank() {
        return rank;
    }
    public int getRankValue() {
        return rank.getRankValue();
    }


    /* -------------------------------------------------------------------------- */
    /*                               OverriddenMethods                            */
    /* -------------------------------------------------------------------------- */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (suit != other.suit)
            return false;
        if (rank != other.rank)
            return false;
        return true;
    }


}

