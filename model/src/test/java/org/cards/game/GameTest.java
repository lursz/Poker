package org.cards.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void setPot_() {
        Game game = new Game();
        game.setPot_(100);
        assertEquals(game.getPot_(), 100);
    }

    @Test
    void addNumberOfPlayers() {
        Game game = new Game();
        game.addNumberOfPlayers();
        assertEquals(1, game.getNumberOfPlayers());
    }

    //@Test
    void nextPlayer() {

    }




    //@Test
    void endOfStateWaitingForCardsChangePhase() {

    }

    //@Test
    void endOfSecondRoundBets() {
    }

    //@Test
    void endOfStateGameOverStartNextRound() {
    }
}