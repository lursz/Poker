package org.cards.object;

/*
 * Class describing a card
 * @param suit - suit of the card
 * @param rank - rank of the card
 * @param value - value of the card
 */
public class Card {
    // Enum template
    enum suits {
        CLUBS, HEARTS, SPADES, DIAMONDS
    }

    enum ranks {
        ACE, KING, QUEEN, JACK, _10, _9, _8, _7, _6, _5, _4, _3, _2
    }

    // Variables
    private final suits suit;
    private final ranks rank;
    private int value;

    // Constructors
    public Card(Card.suits suit, Card.ranks rank) {
        this.suit = suit;
        this.rank = rank;
    }

    // Getters
    public suits getSuit() {
        return suit;
    }
    public ranks getRank() {
        return rank;
    }
    public int getValue() {
        return value;
    }

    // Methods
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
        result = prime * result + value;
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
        if (value != other.value)
            return false;
        return true;
    }


}

