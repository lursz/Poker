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

        assertEquals(hand.getCard(0).getRank(), Card.ranks.ACE);
        assertEquals(hand.getCard(1).getRank(), Card.ranks.KING);
        assertEquals(hand.getCard(2).getRank(), Card.ranks.QUEEN);
        assertEquals(hand.getCard(3).getRank(), Card.ranks.JACK);
        assertEquals(hand.getCard(4).getRank(), Card.ranks._10);
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
        assertEquals(hand.getCard(0).getRank(), Card.ranks.KING);
        assertEquals(hand.getCard(1).getRank(), Card.ranks.QUEEN);
        assertEquals(hand.getCard(2).getRank(), Card.ranks.JACK);
        assertEquals(hand.getCard(3).getRank(), Card.ranks._10);
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

        assertEquals(hand.getCard(0).getRank(), Card.ranks.ACE);
        assertEquals(hand.getCard(1).getRank(), Card.ranks.KING);
        assertEquals(hand.getCard(2).getRank(), Card.ranks.QUEEN);
        assertEquals(hand.getCard(3).getRank(), Card.ranks.JACK);
        assertEquals(hand.getCard(4).getRank(), Card.ranks._10);
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

        assertEquals(hand.getNumberOfCards(), 5);
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

        assertEquals(hand.isBetterThan(hand2), 0);

        Hand hand3 = new Hand();
        // correct hand
        hand3.addCard(new Card(Card.suits.HEARTS, Card.ranks._2));
        hand3.addCard(new Card(Card.suits.SPADES, Card.ranks.QUEEN));
        hand3.addCard(new Card(Card.suits.DIAMONDS, Card.ranks._3));
        hand3.addCard(new Card(Card.suits.CLUBS, Card.ranks._8));
        hand3.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        assertEquals(hand.isBetterThan(hand3), 1);

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
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.KING));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.QUEEN));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks.JACK));
        hand2.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        assertEquals(hand.isBetterThan(hand2), 0);

        Hand hand3 = new Hand();
        // correct hand
        hand3.addCard(new Card(Card.suits.HEARTS, Card.ranks._2));
        hand3.addCard(new Card(Card.suits.SPADES, Card.ranks.QUEEN));
        hand3.addCard(new Card(Card.suits.DIAMONDS, Card.ranks._3));
        hand3.addCard(new Card(Card.suits.CLUBS, Card.ranks._8));
        hand3.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        assertEquals(hand.isBetterThan(hand3), 1);

    }
    @Test

    public void testIsBetterThan3(){
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

        assertEquals(hand.isBetterThan(hand2), 0);

        Hand hand3 = new Hand();
        // correct hand
        hand3.addCard(new Card(Card.suits.HEARTS, Card.ranks._2));
        hand3.addCard(new Card(Card.suits.SPADES, Card.ranks.QUEEN));
        hand3.addCard(new Card(Card.suits.DIAMONDS, Card.ranks._3));
        hand3.addCard(new Card(Card.suits.CLUBS, Card.ranks._8));
        hand3.addCard(new Card(Card.suits.CLUBS, Card.ranks._10));

        assertEquals(hand.isBetterThan(hand3), 1);

    }




}