package ca.mcgill.cs.comp303.rummy.model;

import java.io.Serializable;

public interface IPlayer extends Serializable
{
	public String getName();
	public void startTurn();
	public void discard(Card pCard);
	public void drawDeck();
	public void drawPile();
	public int getHandValue();
	public void knock();
}