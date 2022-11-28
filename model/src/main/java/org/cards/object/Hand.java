package org.cards.object;
import org.cards.object.Deck;

import java.util.*;
import java.util.stream.IntStream;

/**
 *Class managing Hand functions
 */
public class Hand {
    private ArrayList<Card> hand_;
    private int numberOfCards_;



    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */

    /**
     * Constructor
     */
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

    /**
     *
     * @param index
     * @return index
     */
    public Card getCard(int index) {
        return hand_.get(index);
    }

    @Override
    public String toString() {
        sortHand(this);
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < hand_.size(); i++) {
            sb.append("(").append(i).append(")").append(hand_.get(i).getRank().toString()).append(" of ").append(hand_.get(i).getSuit().toString()).append("\n");
//            sb.append("("+i+")"+ hand_.get(i).getRank().toString() + " of " + hand_.get(i).getSuit().toString() + "\n");

        }
        return sb.toString();
    }
    /* -------------------------------------------------------------------------- */
    /*                                   Methods                                  */
    /* -------------------------------------------------------------------------- */

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

    /**
     * Function used to let go of cards in hand, and collect cards from deck
     * @param indexOfCardsToExchange
     * @param deck
     */
    public void exchangeCards(int[] indexOfCardsToExchange, Deck deck) {
        for (int i = 0; i < indexOfCardsToExchange.length; i++) {
            hand_.set(indexOfCardsToExchange[i], deck.draw());
        }
    }

    /* -------------------------------------------------------------------------- */
    /*                               Hand Management                              */
    /* -------------------------------------------------------------------------- */
    /* -------- Static multiple cards arrays for deciding cards seniority ------- */
    static int[] pairArray = new int[]{2, 1, 1, 1, 0};
    static int[] twoPairsArray = new int[]{2, 2, 1, 0, 0};
    static int[] threeSameArray = new int[]{3, 1, 1, 0, 0};
    static int[] fullHouseArray = new int[]{3, 2, 0, 0, 0};
    static int[] fourSameArray = new int[]{4, 1, 0, 0, 0};

    /* --------------------------- Seniority constants -------------------------- */
    static int noSeniority = 0;
    static int pairSeniority = 1;
    static int twoPairsSeniority = 2;
    static int threeOfTheKindSeniority = 3;
    static int straightSeniority = 4;
    static int flushSeniority = 5;
    static int fullHouseSeniority = 6;
    static int fourOfTheKindSeniority = 7;

    // flushSeniority (5) + straightSeniority (4) = Straight Flush (9)
    static int[] noMultipleCardsSeniorities = new int[]{0, 4, 5, 9};



/* ------------------- Ultimate hand comparison function -------------------- */

    /**
     * Ultimate comparison function
     * Looks for the highest card (Cards ought to already be sorted)
     *         Loops through the hand and returns:
     *         -> 1:   if this hand has the highest card
     *         -> -1:  if the other hand has the highest card
     *         -> 0:   if they are equal
     * @param other
     * @return
     */
    public int isBetterThan(Hand other) {
        // 1 -> this hand is better than the other
        // 0 -> hands are equal
        // -1 -> this hand is worse than the other

        sortHand(this);
        sortHand(other);
        int thisSeniority = this.handSeniority();
        int otherSeniority = other.handSeniority();

        if (thisSeniority > otherSeniority) {
            return 1;
        }
        else if(thisSeniority < otherSeniority) {
            return -1;
        }
        //Same poker rankings

        //Checks for ranking with no repeating cards
        if(IntStream.of(noMultipleCardsSeniorities).anyMatch(x -> x == thisSeniority)) {
            //if no repeating cards (e.g. straight, flush, straight flush), compare the highest card
            return highestCardSearch(other);
        }
        //else, we have repeating cards (e.g. pair, two pairs, three of a kind, full house, four of a kind)
        return weightedHighestCardSearch(other);
    }

    //--------auxiliary---------

    /**
     * Auxiliary function, sorts hand for easier comparisons
     * @param toSort
     */
    private void sortHand(Hand toSort) {
        //Just sort the hand
        toSort.hand_.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Integer.compare(o1.getRankValue(), o2.getRankValue());
            }
        });
    }


/* --------------------- Layout WITH NO repeating cards --------------------- */

    /**
     * Kicker comparator when there are no repeating cards. Looks for the highest card (Cards ought to already be sorted)
     * @param other
     * @return
     */
    private int highestCardSearch(Hand other) {
        // Looks for the highest card (Cards ought to already be sorted)
        //Loops through the hand and returns:
        // -> 1:   if this hand has the highest card
        // -> -1:  if the other hand has the highest card
        // -> 0:   if they are equal

        for(int i=0; i<numberOfCards_; i++) {
            if(this.hand_.get(i).getRankValue() > other.hand_.get(i).getRankValue()) {
                return 1;
            } else if(this.hand_.get(i).getRankValue() < other.hand_.get(i).getRankValue()) {
                return -1;
            }
        }
        return 0;
    }



/* ----------------------- Layout WITH repeating cards ---------------------- */

    /**
     * Kicker comparator when there are repeating cards. Looks for the highest card (Cards ought to already be sorted)
     * @param other
     * @return
     */
    private int weightedHighestCardSearch(Hand other) {
        int[][] thisHand = handTo2dArray(this);
        int[][] otherHand = handTo2dArray(other);

        for(int i=0; i<numberOfCards_; i++) {
            if(thisHand[i][0] > otherHand[i][0]) {
                return 1;
            } else if(thisHand[i][0] < otherHand[i][0]) {
                return -1;
            }
        }
        return 0;
    }

    //    --------auxiliary---------

    /**
     * Auxiliary function counting occurances and weights of cards and putting it in 2d array
     * @param other
     * @return
     */
    private int[][] handTo2dArray(Hand other) {
        /*
        Returns given Hand in form of an array of cards with their weights -> [cardValue, weight].
        For example if we have a hand "A J 4 4 2" the representation is:
        [4, 2]
        [15, 1]
        [11, 1]
        [2, 1]
        [0, 0]
        There are always 5 rows and 2 columns (numbers in column 1 always add up to 5).
        Example "Q Q Q 7 7":
        [12, 3]
        [7, 2]
        [0, 0]
        [0, 0]
        [0, 0]
         */
        int[] cards = new int[13];
        int[][] result = new int[5][2];

        for (int i = 0; i < numberOfCards_; i++) {
            cards[other.hand_.get(i).getRankValue()-2] += 1;
        }


        for(int i=0, j=0; i<13; i++) {
            //if there is a card with value i+2
            if(cards[i] > 0) {
                result[j][0] = i;
                result[j++][1] = cards[i];
            }
        }
        RowsSort(result);
        return result;
    }

    /**
     * Auxiliary function sorting 2d card arrays
     * @param arr
     */
    static void RowsSort(int arr[][]) {
        Arrays.sort(arr, new Comparator<int[]>() {
            public int compare(int[] first, int[] second) {
                if(first[1]*100+first[0] < second[1]*100+second[0]) {
                    return 1;
                }
                else return -1;
            }
        });
    }


    /* ---------------------- Seniority deciding functions ---------------------- */
    //Hand seniority returns int - meaning poker hand rankings

    /**
     * Hand seniority returns int - meaning poker hand rankings
     * Function looks for known card layouts. First thing we ought to start when comparing two hands.
     * @return
     */
    private int handSeniority() {
        return allCardsInTheSameColor() + allCardsInConsecutiveOrder() + multipleCards();
    }

    //    --------auxiliary---------
    private int allCardsInTheSameColor() {
        //Checks if all cards are of the same color
        for (Card i : hand_) {
            if (hand_.get(0).getSuit() != i.getSuit())
                return noSeniority;
        }
        return flushSeniority;
    }
    private int allCardsInConsecutiveOrder() {
        //Checks if all cards are in 'straight' order (one after another)
        for (int i = 1; i < numberOfCards_; i++) {
            if (hand_.get(i-1).getRankValue()-1 != hand_.get(i).getRankValue()) {
                return noSeniority;
            }
        }
        return straightSeniority;
    }
    private int multipleCards() {
        int[] cards = new int[13];
        int[] givenHandSeniority = new int[5];

        for (int i = 0; i < numberOfCards_; i++) {
            cards[hand_.get(i).getRankValue()-2] += 1;
        }

        for(int i=0, j=0; i<13; i++) {
            if(cards[i] > 0) {
                givenHandSeniority[j++] = cards[i];
            }
        }

        Arrays.sort(givenHandSeniority);

        if (Arrays.equals(givenHandSeniority, pairArray)) {
            return pairSeniority;
        }
        else if(Arrays.equals(givenHandSeniority, twoPairsArray)) {
            return twoPairsSeniority;
        }
        else if(Arrays.equals(givenHandSeniority, threeSameArray)) {
            return threeOfTheKindSeniority;
        } else if(Arrays.equals(givenHandSeniority, fullHouseArray)) {
            return fullHouseSeniority;
        }
        else if(Arrays.equals(givenHandSeniority, fourSameArray)) {
            return fourOfTheKindSeniority;
        }
        return noSeniority;
    }
}