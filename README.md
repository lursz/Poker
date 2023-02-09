# Poker
## Run 
In order to run th game you need to have Java installed on your computer.  
You can download it:
- from [java.com](https://www.java.com/download/ie_manual.jsp)
- or via chosen package manager (e.g. `sudo apt install openjdk-17-jdk` on Ubuntu)  

Then you can run the game by typing the following command in the terminal:<br>
`java -jar poker-server-1.0-SNAPSHOT-jar-with-dependencies.jar <amount of $>`  
and  
`java -jar poker-client-1.0-SNAPSHOT-jar-with-dependencies.jar`

## Screenshot
![poker](https://user-images.githubusercontent.com/93160829/217714762-25d72f60-e52b-4967-9ce6-b3ad376613a1.png)

## Rules of Poker 
In Draw Poker, each player is dealt five cards, and a round of betting ensues. The remaining players then may attempt to improve their hands by trading as many as three cards for a new three from the deck. 

The rounds of betting work like this: Starting to the left of the dealer, each player has four options:
- Raise — A player who thinks he has a good hand (or who wants the other players to think he has a good hand) may increase the wager required to continue playing.  
- Fold — A player who thinks his hand is not good enough to win and who does not want to wager the increased amount may lay down his cards. He cannot win the hand, but he also will not lose any more chips.  
- Call — Once a player has raised the stakes, each player must decide whether to raise the stakes again, to give in and fold his hand, or to call, which means to equal the amount wagered by the player who raised.  
- Check — If no one has increased the wager required to continue, a player may stand pat by checking, or passing on his option to bet.


Typically, five or seven cards are dealt to each player. Players attempt to form the best five-card poker hand possible (see below). For every poker game, the same hierarchy of hands exists, and the better hands are rarer and more difficult to achieve than the lesser hands.

Individual cards are ranked from best to worst. The rank of a card often breaks the tie if two players achieve the same hand. The Ace is the most valuable card. From there, it goes in descending order: King, Queen, Jack, 10, 9, 8, 7, 6, 5, 4, 3, 2.

The ranking of hands, from lowest to highest value:
- High card. If no combination can be made, then a player’s hand is valued at the highest single card. If two players have the same high card, then the second highest card would break the tie.  
Example: 5♣ 8♦ 10♠ Q♥ A♠
- One Pair. A pair is formed when you have two of any of the same cards.  
Example: 9♠ 9♦ 5♣ 8♣ K♥
- Two Pairs. When more than one player has two pairs, the player with the highest pair wins.  
Example: 9♠ 9♦ 5♣ 5♥ 8♥
- Three of a Kind.  
Example: 9♠ 9♦ 9♥ 5♣ 8♣
- Straight. A straight is a five-card hand consisting of a running sequence of cards, regardless of suit. If two players have straights, the straight of the higher card wins.  
Example: 9♠ 10♠ J♦ Q♥ K♦
- Flush. When all five cards in a hand are of the same suit, it is a flush. If two players have a flush, the person with the highest card in that suit wins.  
Example: 9♠ 5♠ Q♠ K♠ 7♠
- Full House. When a player has three-of-a-kind and a pair in the same hand, it is called a Full House.  
Example: 9♠ 9♦ 9♥ 5♣ 5♥
- Four of a Kind. If you are lucky enough to have all four of a given number, then you have a very powerful hand.  
Example: 9♠ 9♦ 9♥ 9♣ 5♣
- Straight Flush. Even rarer than four of a kind, a straight flush is made up of five consecutive cards, all from the same suit.  
Example: 9♠ 10♠ J♠ Q♠ K♠
- Royal Flush. The best hand of them all is this famous combination, formed by a Straight Flush that runs to the Ace, making it unbeatable. Odds of being dealt this hand can be as high as 1 in 650,000 deals.  
Example: 10♥ J♥ Q♥ K♥ A♥
