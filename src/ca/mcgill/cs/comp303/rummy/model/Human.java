package ca.mcgill.cs.comp303.rummy.model;
import java.util.concurrent.TimeUnit;

import ca.mcgill.cs.comp303.rummy.ai.*;

public class Human implements IPlayer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String aName;
	Hand aHand = new Hand();
	AIRobot aBot;
	private Card drewPile;
	transient GameEngine g = GameEngine.getInstance();
	public boolean notdone = true;
	
	
	
	public Human(String name)
	{
		aName=name;
	}
	public Human(String name,AIRobot robot)
	{
		aName=name;
		aBot=robot;
//		aHand=pHand;
	}
	public String getName()
	{
		return aName;
	}
	public Hand getHand()
	{
		return aHand;
	}
	public void startTurn()
	{
		if(aBot!=null)
		{
			pause();
			boolean deck=aBot.deckOrNot(aHand);
			if(deck)
			{
				drawDeck();
			}
			else
			{
				drawPile();
				
			}
			
			
			//aHand.autoMatch();
			
			
			pause();
			pause();
			if (deck)
			{
				discard(aBot.bestDiscard(aHand,null));
			}
			else
			{
				discard(aBot.bestDiscard(aHand,drewPile));
				drewPile=null;
			}
			
			if(aBot.KnockOrNot(aHand))
			{
				
			}
		}
		else 
		{
			// HOW TO MAKE THIS WAIT FOR THE ACTIONS OF THE UI INSTEAD OF JUST RETURNING
		}
	}
	@Override
	public void discard(Card pCard)
	{
		g.cardDiscarded(aHand.remove(pCard));
	}

	@Override
	public void drawDeck()
	{
		aHand.add(g.drawFromDeck(this));
	}

	@Override
	public void drawPile()
	{
		drewPile=g.drawFromPile();
		aHand.add(drewPile);
	}

	@Override
	public int getHandValue()
	{
		
		return aHand.score();
	}

	@Override
	public void knock()
	{
		g.terminate(this);
	}
	private void pause()
	{
		try
		{
			TimeUnit.MILLISECONDS.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
}