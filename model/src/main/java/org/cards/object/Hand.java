package org.cards.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Hand {
    private ArrayList<Card> hand_;
    private int numberOfCards_;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */

    public Hand() {
        this.hand_ = new ArrayList<Card>();
        this.numberOfCards_ = 0;
    }
    /* -------------------------------------------------------------------------- */
    /*                                   Getters                                  */
    /* -------------------------------------------------------------------------- */

    public ArrayList<Card> getHand_() {
        return hand_;
    }

    public int getNumberOfCards() {
        return numberOfCards_;
    }

    public Card getCard(int index) {
        return hand_.get(index);
    }

    /* -------------------------------------------------------------------------- */
    /*                                   Methods                                  */
    /* -------------------------------------------------------------------------- */
    /* ------------------------------ Evaluate Hand ----------------------------- */
    public void evaluateHand() {
        // Sort hand
        hand_.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.getRank().compareTo(o2.getRank());
            }
        });



    }

    /* ------------------------------ Hand rankings ----------------------------- */

    public boolean royalFlush() {
        return hand_.get(0).getRank() == Card.ranks._10 && hand_.get(1).getRank() == Card.ranks.JACK
                && hand_.get(2).getRank() == Card.ranks.QUEEN && hand_.get(3).getRank() == Card.ranks.KING
                && hand_.get(4).getRank() == Card.ranks.ACE;

    }

    // checks for a straight flush
    public boolean straightFlush() {
        for (int i = 0; i < 4; i++) {
            if (hand_.get(0).getRank() != hand_.get(i).getRank()) {
                return false;
            }
            if (hand_.get(0).getSuit() != hand_.get(i).getSuit()) {
                return false;
            }
        }
        return true;
    }

    // checks for four of a kind
    public boolean fourOfaKind() {
        int card_of_kind_first_counter = 1;
        int card_of_kind_second_counter = 1;
        for (int i = 1; i < 5; i++) {
            if (hand_.get(0).getRank() == hand_.get(i).getRank()) {
                card_of_kind_first_counter++;
            }
            if (hand_.get(1).getRank() == hand_.get(i).getRank()) {
                card_of_kind_second_counter++;
            }
        }
        return card_of_kind_first_counter == 4 || card_of_kind_second_counter == 4;


    }


    // checks for full house
    public boolean fullHouse() {
        int card_of_kind_first_counter = 1;
        int card_of_kind_second_counter = 1;
        for (int i = 1; i < 5; i++) {
            if (hand_.get(0).getRank() == hand_.get(i).getRank()) {
                card_of_kind_first_counter++;
            }
            if (hand_.get(1).getRank() == hand_.get(i).getRank()) {
                card_of_kind_second_counter++;
            }
        }
        return card_of_kind_first_counter == 3 && card_of_kind_second_counter == 2
                || card_of_kind_first_counter == 2 && card_of_kind_second_counter == 3;
    }


    // checks for flush
    public boolean flush() {
        for (int i = 0; i < 4; i++) {
            if (hand_.get(0).getSuit() != hand_.get(i).getSuit()) {
                return false;
            }
        }
        return true;
    }


    // check for straight
    public boolean straight() {
        for (int i = 0; i < 4; i++) {
            if (hand_.get(i).getRank().ordinal() != hand_.get(i + 1).getRank().ordinal() + 1) {
                return false;
            }
        }
        return true;
    }


    // checks for triple
    public boolean triple() {
        int card_of_kind_first_counter = 1;
        int card_of_kind_second_counter = 1;
        int card_of_kind_third_counter = 1;
        for (int i = 1; i < 5; i++) {
            if (hand_.get(0).getRank() == hand_.get(i).getRank()) {
                card_of_kind_first_counter++;
            }
            if (hand_.get(1).getRank() == hand_.get(i).getRank()) {
                card_of_kind_second_counter++;
            }
            if (hand_.get(2).getRank() == hand_.get(i).getRank()) {
                card_of_kind_third_counter++;
            }
        }
        return card_of_kind_first_counter == 3 || card_of_kind_second_counter == 3
                || card_of_kind_third_counter == 3;
    }


    // checks for two pairs
    public boolean twoPairs() {
        int pairs_counter = 0;
        for (int i = 0; i < 4; i++) {
            if (hand_.get(i).getRank() == hand_.get(i + 1).getRank()) {
                pairs_counter++;
                i++;
            }

        }
        return pairs_counter == 2;
    }


    // check for pair
    public boolean pair() {
        int pairs_counter = 0;
        for (int i = 0; i < 4; i++) {
            if (hand_.get(i).getRank() == hand_.get(i + 1).getRank()) {
                pairs_counter++;
                i++;
            }

        }
        return pairs_counter == 1;
    }


    // find highest card
    public boolean highCard() {
        return true;
    }

    /* ------------------------------- Manage hand ------------------------------ */

    public void addCard(Card card) {
        hand_.add(card);
        numberOfCards_++;
    }

    public void removeCard(Card card) {
        hand_.remove(card);
        numberOfCards_--;
    }

    public void removeCard(int index) {
        hand_.remove(index);
        numberOfCards_--;
    }

    public void clearHand() {
        hand_.clear();
        numberOfCards_ = 0;
    }

    public void printHand() {
        for (Card i : hand_) {
            System.out.println(i);
        }
    }

}