package org.cards.object;

import java.util.*;
import java.util.stream.IntStream;

public class Hand {
    private ArrayList<Card> hand_;
    private int numberOfCards_;

    /* ------------ Weighted sorting for multiple cards combinations ------------ */
    static void weightedSorting(int arr[][]) {
        Arrays.sort(arr, new Comparator<int[]>() {
            public int compare(int[] first, int[] second) {
                if(first[1]*100+first[0] < second[1]*100+second[0]) {
                    return 1;
                }
                else return -1;
            }
        });
    }

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
    static int threeSameSeniority = 3;
    static int fullHouseSeniority = 6;
    static int fourSameSeniority = 7;
    static int consecutiveSeniority = 4;
    static int colorSeniority = 5;
    static int[] noMultipleCardsSeniorities = new int[]{0, 4, 5, 9};

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

    /* ---------------------------------- Sort ---------------------------------- */
    private void sortHand(Hand toSort) {
        toSort.hand_.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Integer.compare(o1.getRankValue(), o2.getRankValue());
            }
        });
    }

    /* ------------------------------ Compare hands ----------------------------- */

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

        if(IntStream.of(noMultipleCardsSeniorities).anyMatch(x -> x == thisSeniority)) {
            return highestCardSearch(other);
        }
        return weightedHighestCardSearch(other);
    }

/* ------------------- Comparing hands - helping utilities ------------------ */

    private int weightedHighestCardSearch(Hand other) {
        int[][] thisHand = weightedHand(this);
        int[][] otherHand = weightedHand(other);

        weightedSorting(thisHand);
        weightedSorting(otherHand);

        for(int i=0; i<numberOfCards_; i++) {
            if(thisHand[i][0] > otherHand[i][0]) {
                return 1;
            } else if(thisHand[i][0] < otherHand[i][0]) {
                return -1;
            }
        }
        return 0;
    }


    private int[][] weightedHand(Hand toConvert) {
        /*
        The representation is an array of cards in hand with their weights.
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
        int[][] handQuantity = new int[5][2];

        for (int i = 0; i < numberOfCards_; i++) {
            cards[toConvert.hand_.get(i).getRankValue()-2] += 1;
        }

        int subIter = 0;
        for(int i=0; i<13; i++) {
            if(cards[i] > 0) {
                handQuantity[subIter][0] = i;
                handQuantity[subIter++][1] = cards[i];
            }
        }
        return handQuantity;
    }


    private int highestCardSearch(Hand other) {
        // Checks the highest card
        // (cards should be alredy sorted)

        for(int i=0; i<numberOfCards_; i++) {
            if(this.hand_.get(i).getRankValue() > other.hand_.get(i).getRankValue()) {
                return 1;
            } else if(this.hand_.get(i).getRankValue() < other.hand_.get(i).getRankValue()) {
                return -1;
            }
        }
        return 0;
    }

    /* ---------------------- Seniority deciding functions ---------------------- */

    private int handSeniority() {
        return sameColor() + consecutiveRanks() + multipleCards();
    }

    private int sameColor() {
        for (Card i : hand_) {
            if (hand_.get(0).getSuit() != i.getSuit())
                return noSeniority;

        }
        return colorSeniority;
    }

    private int consecutiveRanks() {
        for (int i = 1; i < numberOfCards_; i++) {
            if (hand_.get(i-1).getRankValue()-1 != hand_.get(i).getRankValue()) {
                return noSeniority;
            }
        }
        return consecutiveSeniority;
    }

    private int multipleCards() {
        int[] cards = new int[13];
        int[] handQuantity = new int[5];

        for (int i = 0; i < numberOfCards_; i++) {
            cards[hand_.get(i).getRankValue()-2] += 1;
        }

        int subIter = 0;
        for(int i=0; i<13; i++) {
            if(cards[i] > 0) {
                handQuantity[subIter++] = cards[i];
            }
        }

        Arrays.sort(handQuantity);

        if (Arrays.equals(handQuantity, pairArray)) {
            return pairSeniority;
        }
        else if(Arrays.equals(handQuantity, twoPairsArray)) {
            return twoPairsSeniority;
        }
        else if(Arrays.equals(handQuantity, threeSameArray)) {
            return threeSameSeniority;
        } else if(Arrays.equals(handQuantity, fullHouseArray)) {
            return fullHouseSeniority;
        }
        else if(Arrays.equals(handQuantity, fourSameArray)) {
            return fourSameSeniority;
        }
        return noSeniority;
    }
}