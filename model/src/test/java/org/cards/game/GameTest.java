package org.cards.game;

import org.cards.player.Player;
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

    @Test
    void testReceiveCommands() {
        // given
        Game game = new Game();
        Game.Pair pair = game.receiveCommands("/ready", new Player("test", 100, null));

        //will retur Set username first
        assertEquals(pair.answer, "Waiting for other players");
    }

    @Test
    void testReceiveCommands2() {
        // given
        Game game = new Game();
        Game.Pair pair = game.receiveCommands("/hand", new Player("test", 100, null));

        //will retur Set username first
        assertEquals(pair.answer, "Game hasn't started yet");
    }


    @Test
    void testReceiveCommands3() {
        // given
        Game game = new Game();
        Game.Pair pair = game.receiveCommands("/check", new Player("test", 100, null));

        //will retur Set username first
        assertEquals(pair.answer, "Game hasn't started yet");
    }


    @Test
    void testReceiveCommands4() {
        // given
        Game game = new Game();
        Game.Pair pair = game.receiveCommands("/call", new Player("test", 100, null));

        //will retur Set username first
        assertEquals(pair.answer, "Game hasn't started yet");
    }

    @Test
    void testReceiveCommands5() {
        // given
        Game game = new Game();
        Game.Pair pair = game.receiveCommands("/raise", new Player("test", 100, null));

        //will retur Set username first
        assertEquals(pair.answer, "Game hasn't started yet");
    }

    @Test
    void testReceiveCommands6() {
        // given
        Game game = new Game();
        Game.Pair pair = game.receiveCommands("/raise", new Player("test", 100, null));

        //will retur Set username first
        assertEquals(pair.answer, "Game hasn't started yet");
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