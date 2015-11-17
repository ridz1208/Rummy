package ca.mcgill.cs.comp303.rummy.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;

public class CardSet implements ICardSet,Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Set<Card> aSet;
	private int aValue;
	
	
	public CardSet(Set<Card> someSet)
	{
		aSet = someSet;
	}
	/**
	 * @param pCard A card to check
	 * @return True if pCard is in this set
	 */
	public boolean contains(Card pCard)
	{
		return aSet.contains(pCard);
	}

	/**
	 * @return The size of the group.
	 */
	public int size()
	{
		return aSet.size();
	}
	
	public int getValue()
	{
		return aValue;
	}
	
	/**
	 * @return true if the object represents a group.
	 */
	public boolean isGroup()
	{
		return true; //TODO
	}
	
	/**
	 * @return true if the object represents a run.
	 */
	public boolean isRun()
	{
		return true; //TODO
	}

	@Override
	public Iterator<Card> iterator()
	{
		Iterator <Card> itr = this.iterator();
		return itr;
	}
}