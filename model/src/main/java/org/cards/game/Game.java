package org.cards.game;

import org.cards.object.*;
import org.cards.player.*;

import java.util.ArrayList;

import exceptions.GameAlreadyInitialized;
import exceptions.InvalidCommand;
import exceptions.BalanceTooLow;
import exceptions.InvalidCardCode;
import exceptions.PlayerInvalidCardsNumber;
import exceptions.CardsAlreadyThrown;
public class Game {
    private Deck deck_;
    private ArrayList<Player> players_;
    private int numberOfPlayers;
    private boolean isInitialized;
    private int currentPlayerRound;




}