package org.cards.object;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    @Test
        public void testAddCard() {
        Hand hand = new Hand();
        // correct hand
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.ACE));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.KING));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.QUEEN));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.JACK));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        assertEquals(Card.ranks.ACE, hand.getCard(0).getRank());
        assertEquals(Card.ranks.KING, hand.getCard(1).getRank());
        assertEquals(Card.ranks.QUEEN, hand.getCard(2).getRank());
        assertEquals(Card.ranks.JACK, hand.getCard(3).getRank());
        assertEquals(Card.ranks._10, hand.getCard(4).getRank());
    }
    @Test
    public void testRemoveCard(){
        Hand hand = new Hand();
        // correct hand
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.ACE));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.KING));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.QUEEN));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.JACK));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        hand.removeCard(0);
        assertEquals(Card.ranks.KING, hand.getCard(0).getRank());
        assertEquals(Card.ranks.QUEEN, hand.getCard(1).getRank());
        assertEquals(Card.ranks.JACK, hand.getCard(2).getRank());
        assertEquals(Card.ranks._10, hand.getCard(3).getRank());
    }

    @Test
    public void testGetCard(){
        Hand hand = new Hand();
        // correct hand
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.ACE));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.KING));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.QUEEN));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.JACK));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        assertEquals(Card.ranks.ACE, hand.getCard(0).getRank());
        assertEquals(Card.ranks.KING, hand.getCard(1).getRank());
        assertEquals(Card.ranks.QUEEN, hand.getCard(2).getRank());
        assertEquals(Card.ranks.JACK, hand.getCard(3).getRank());
        assertEquals(Card.ranks._10, hand.getCard(4).getRank());


    }

    @Test
    public void testGetNumberOfCards(){
        Hand hand = new Hand();
        // correct hand
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.ACE));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.KING));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.QUEEN));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.JACK));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        assertEquals(5, hand.getNumberOfCards());
    }
    @Test
    public void testToString(){
        Hand hand = new Hand();
        // correct hand
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.ACE));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.KING));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.QUEEN));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.JACK));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        assertEquals("\n(0)_10 of CLUBS\n(1)JACK of CLUBS\n(2)QUEEN of CLUBS\n(3)KING of CLUBS\n(4)ACE of CLUBS\n", hand.toString());
    }
    @Test
    public void testIsBetterThan(){
        Hand hand = new Hand();
        // correct hand
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.ACE));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.KING));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.QUEEN));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.JACK));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        Hand hand2 = new Hand();
        // correct hand
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.ACE));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.KING));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.QUEEN));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.JACK));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        assertEquals(0, hand.isBetterThan(hand2));

        Hand hand3 = new Hand();
        // correct hand
        hand3.addCard(new Card(Card.suits.HEARTS, Card.ranks._2));
        hand3.addCard(new Card(Card.suits.SPADES, Card.ranks.QUEEN));
        hand3.addCard(new Card(Card.suits.DIAMONDS, Card.ranks._3));
        hand3.addCard(new Card(Card.suits.CLUBS, Card.ranks._8));
        hand3.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        assertEquals(1, hand.isBetterThan(hand3));

    }
    @Test
    public void testIsBetterThan2(){
        Hand hand = new Hand();
        // correct hand
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.ACE));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.KING));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.QUEEN));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.JACK));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        Hand hand2 = new Hand();
        // correct hand
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.ACE));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks._2));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.QUEEN));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.JACK));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks._3));

        assertEquals(1, hand.isBetterThan(hand2));

        Hand hand3 = new Hand();
        // correct hand
        hand3.addCard(new Card(Card.suits.HEARTS, Card.ranks._2));
        hand3.addCard(new Card(Card.suits.SPADES, Card.ranks.QUEEN));
        hand3.addCard(new Card(Card.suits.DIAMONDS, Card.ranks._3));
        hand3.addCard(new Card(Card.suits.CLUBS, Card.ranks._8));
        hand3.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        assertEquals(1, hand.isBetterThan(hand3));

    }
    @Test

    public void testIsBetterThan3(){
        Hand hand = new Hand();
        // correct hand
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks._2));
        hand.addCard(new Card(Card.suits.HEARTS, Card.ranks._2));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.QUEEN));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks.JACK));
        hand.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        Hand hand2 = new Hand();
        // correct hand
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.ACE));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.KING));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.QUEEN));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.JACK));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        assertEquals(-1, hand.isBetterThan(hand2));

        Hand hand3 = new Hand();
        // correct hand
        hand3.addCard(new Card(Card.suits.HEARTS, Card.ranks._2));
        hand3.addCard(new Card(Card.suits.SPADES, Card.ranks.QUEEN));
        hand3.addCard(new Card(Card.suits.DIAMONDS, Card.ranks._3));
        hand3.addCard(new Card(Card.suits.CLUBS, Card.ranks._8));
        hand3.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        assertEquals(-1, hand.isBetterThan(hand3));

    }




}