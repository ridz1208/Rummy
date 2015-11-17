package ca.mcgill.cs.comp303.rummy.ai;


import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import ca.mcgill.cs.comp303.rummy.model.*;
import ca.mcgill.cs.comp303.rummy.model.Card.*;
//import ca.mcgill.cs.comp303.rummy.model.Card;
//import ca.mcgill.cs.comp303.rummy.model.Comp;
//import ca.mcgill.cs.comp303.rummy.model.GameEngine;
//import ca.mcgill.cs.comp303.rummy.model.Hand;
//import ca.mcgill.cs.comp303.rummy.model.Human;
//import ca.mcgill.cs.comp303.rummy.model.IPlayer;
//import ca.mcgill.cs.comp303.rummy.model.LoggerPrim;
//import ca.mcgill.cs.comp303.rummy.model.NoPlayersException;

public class TestSmartBot 
{
	SmartBot aBot = new SmartBot();
	Hand aHand = new Hand();
	GameEngine g = GameEngine.getInstance();
	
	@Before
	public void setup() //throws NoPlayersException
	{		
//		IPlayer h = new Human("Rida");
//		IPlayer c = new Comp("Terminator",new SmartBot());
//		
//		
////		g.addObserver(new LoggerPrim());
////		g.addObserver(new LoggerFile());
//		g.addPlayer(h);
//		g.addPlayer(c);
//		g.newGame();
		
		aHand.add(new Card(Rank.ACE,Suit.CLUBS));
		aHand.add(new Card(Rank.THREE,Suit.CLUBS));
		aHand.add(new Card(Rank.SEVEN,Suit.DIAMONDS));
		aHand.add(new Card(Rank.TEN,Suit.CLUBS));
		aHand.add(new Card(Rank.ACE,Suit.HEARTS));
	}
	@Test
	public void testKnockOrNot()
	{
		
		
		assertFalse(aBot.KnockOrNot(aHand));
		aHand.remove(new Card(Rank.ACE,Suit.CLUBS));
		aHand.remove(new Card(Rank.THREE,Suit.CLUBS));
		aHand.remove(new Card(Rank.TEN,Suit.CLUBS));
		aHand.remove(new Card(Rank.ACE,Suit.HEARTS));
		assertTrue(aBot.KnockOrNot(aHand));
//		setup();

	}
	
	@Test
	public void testBestDiscard()
	{
//		aHand.remove(new Card(Rank.TEN,Suit.CLUBS));
		assertEquals(new Card(Rank.TEN,Suit.CLUBS),aBot.bestDiscard(aHand, new Card(Rank.TWO,Suit.DIAMONDS)));
//		assertEquals(new Card(Rank.SEVEN,Suit.DIAMONDS),aBot.bestDiscard(aHand, new Card(Rank.TEN,Suit.CLUBS)));

	}
	
	
}