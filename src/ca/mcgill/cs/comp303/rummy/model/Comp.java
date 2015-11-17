package ca.mcgill.cs.comp303.rummy.model;

import java.util.concurrent.TimeUnit;

import ca.mcgill.cs.comp303.rummy.ai.*;



public class Comp implements IPlayer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String aName;
	Hand aHand = new Hand();
	private Card drewPile;
	
	transient GameEngine g = GameEngine.getInstance();
	
	AIRobot aBot;
	
	public Comp(String name,AIRobot robot)
	{
		aName=name;
		aBot=robot;
	}
	public String getName()
	{
		return aName;
	}
	public void startTurn()
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
			knock();
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