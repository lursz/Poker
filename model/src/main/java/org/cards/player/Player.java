package org.cards.player;

import org.cards.object.*;
import java.util.ArrayList;

/*
 * Class describing player
 * @param name - player's name
 * @param hand - player's hand
 * @param numberOfCards - size of player's hand
 */
public class Player {
    private String name_;
    private Hand hand_;

    // Constructor
    public Player(String name) {
        this.name_ = name;
        this.hand_ = new Hand();
    }

    // Methods


}
