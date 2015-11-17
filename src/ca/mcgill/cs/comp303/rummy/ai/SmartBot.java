package ca.mcgill.cs.comp303.rummy.ai;

import java.util.Iterator;

import ca.mcgill.cs.comp303.rummy.model.*;
import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;

public class SmartBot implements AIRobot
{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	transient GameEngine g = GameEngine.getInstance();
	
	public SmartBot()
	{
//		aHand=pHand;
	}

	@Override
	public boolean KnockOrNot(Hand pHand)
	{
		if (pHand.score()<10)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean deckOrNot(Hand pHand)
	{
		Card tmp = g.topOfPile();
		
		Iterator<Card> itr = pHand.getUnmatchedCards().iterator();
		
		while (itr.hasNext())
		{
			Card nextC = itr.next();
			//System.out.println("Iterations deckornot");

			if (nextC.getSuit().equals(tmp.getSuit()) && (Math.abs(nextC.getRank().ordinal()-tmp.getRank().ordinal())<3))
				{
					return false;
				}
		}
		return true;
		
	}

	@Override
	public Card bestDiscard(Hand pHand,Card justDrawn)
	{
		Card tmp = new Card(Rank.ACE,Suit.CLUBS);
		for (Card card :pHand.getUnmatchedCards())
		{
			if (!(card.equals(justDrawn)) && tmp.getRank().ordinal() <= card.getRank().ordinal())
			{
				tmp=card;
			}
		}
		return tmp;
	}
	
}