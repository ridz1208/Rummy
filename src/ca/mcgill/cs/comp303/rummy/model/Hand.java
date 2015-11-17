 package ca.mcgill.cs.comp303.rummy.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;

/**
 * Models a hand of 10 cards. The hand is not sorted. Not threadsafe.
 * The hand is a set: adding the same card twice will not add duplicates
 * of the card.
 * @inv size() > 0
 * @inv size() <= HAND_SIZE
 */
public class Hand implements  Iterable<Card>,Serializable
{
	
	private final int HAND_SIZE = 11;
	private final Set<Card> aHand;
	private static Lock l = new ReentrantLock();
	public static Condition done = l.newCondition();
	/**
	 * Creates a new, empty hand.
	 */
	public Hand()
	{
		aHand = new HashSet<Card>();
	}
	
	/**
	 * Adds pCard to the list of unmatched cards.
	 * If the card is already in the hand, it is not added.
	 * @param pCard The card to add.
	 * @throws ArrayIndexOutOfBoundsException if the hand is complete.
	 * @throws ArrayIndexOutOfBoundsException if the card is already in the hand.
	 * @pre pCard != null
	 */
	public void add( Card pCard ) throws ArrayIndexOutOfBoundsException
	{
		l.lock();
		try{
		if (!aHand.contains(pCard) && this.size()<HAND_SIZE)
		{
			aHand.add(pCard);
		}
		else if(aHand.contains(pCard))
		{
			throw new ArrayIndexOutOfBoundsException("card in hand already");
		}
		else if (isComplete())
		{
			throw new ArrayIndexOutOfBoundsException("hand is full");
		}
		else
		{
			aHand.add(pCard);
		}
		}
		finally{
			l.unlock();
		}
		
	}
	
	/**
	 * Remove pCard from the hand and break any matched set
	 * that the card is part of. Does nothing if
	 * pCard is not in the hand.
	 * @param pCard The card to remove.
	 * @pre pCard != null
	 */
	public Card remove( Card pCard )
	{
		l.lock();
		
		Card tmp;
		if (aHand.contains(pCard))
		{
			tmp=pCard;
			aHand.remove(pCard);
			l.unlock();
			return tmp;
		}
		
		else 
		{
			l.unlock();
			return null;
		}
		
	}
	
	/**
	 * @return True if the hand is complete.
	 */
	public boolean isComplete()
	{
		return this.size()==HAND_SIZE;
	}
	
	/**
	 * Removes all the cards from the hand.
	 */
	public void clear()
	{
		aHand.clear();
	}
	
	/**
	 * @return A copy of the set of matched sets
	 */
	public Set<ICardSet> getMatchedSets()
	{
		return null; // TODO
	}
	
	/**
	 * @return A copy of the set of unmatched cards.
	 */
	public Set<Card> getUnmatchedCards()
	{
		return aHand; // TODO
	}
	
	/**
	 * @return The number of cards in the hand.
	 */
	public int size()
	{
		return aHand.size();
	}
	
	/**
	 * Determines if pCard is already in the hand, either as an
	 * unmatched card or as part of a set.
	 * @param pCard The card to check.
	 * @return true if the card is already in the hand.
	 * @pre pCard != null
	 */
	public boolean contains( Card pCard )
	{
		return aHand.contains(pCard);
	}
	
	/**
	 * @return The total point value of the unmatched cards in this hand.
	 */
	public int score()
	{
		Set<Card> unmatched = getUnmatchedCards();
		
		return getSetValue(unmatched);
	}
	
	/**
	 * Creates a group of cards of the same rank.
	 * @param pCards The cards to groups
	 * @pre pCards != null
	 * @throws ArrayIndexOutOfBoundsException If the cards in pCard are not all unmatched
	 * cards of the hand or if the group is not a valid group.
	 */
	public void createGroup( Set<Card> pCards )
	{
		Set<Card> unmatched = getUnmatchedCards();
		Iterator<Card> itr = pCards.iterator();
		Card tmpC = itr.next(); 
		Rank theRank = tmpC.getRank();
		for(;itr.hasNext();)
		{
			tmpC = itr.next();
			if (!(unmatched.contains(tmpC)) || !(tmpC.getRank().equals(theRank)))
			{
				throw new ArrayIndexOutOfBoundsException() ;
			}
		}
		
		
		
		// then wt ?? how to create the group ?
		
	}
	
	/**
	 * Creates a run of cards of the same suit.
	 * @param pCards The cards to group in a run
	 * @pre pCards != null
	 * @throws ArrayIndexOutOfBoundsException If the cards in pCard are not all unmatched
	 * cards of the hand or if the run is not a valid run.
	 */
	public void createRun( Set<Card> pCards )
	{
		Set<Card> unmatched = getUnmatchedCards();
		Iterator<Card> itr = pCards.iterator();
		Card tmpC = itr.next(); 
		Suit theSuit = tmpC.getSuit();
		for(;itr.hasNext();)
		{
			tmpC = itr.next();
			if (!(unmatched.contains(tmpC)) || !(tmpC.getSuit().equals(theSuit)))
			{
				throw new ArrayIndexOutOfBoundsException() ;
			}
		}
		
		
		// sort them ?? wt to do now ?
		
	}
	
	/**
	 * Calculates the matching of cards into groups and runs that
	 * results in the lowest amount of points for unmatched cards.
	 */
	public void autoMatch()
	{
		Card[] myHand = new Card[10];
		int i = 0;
		List<Set<Card>>  groupSets = new ArrayList<Set<Card>>();
		List<Set<Card>>  runSets = new ArrayList<Set<Card>>();
		int groupCount = 0;
		int runCount = 0;
		for (Card c : aHand)
		{
			myHand[i] = c;
			i++;
		}
		Set<Card> tmpSet = new HashSet<Card>();
		
		//check for groups and send to create
		sortForGroup(myHand);
		for(i=0;i<HAND_SIZE;i++)
		{
			if ((i+1)<HAND_SIZE && myHand[i].getRank() == myHand[i+1].getRank())
			{
				if ((i+2)<HAND_SIZE && myHand[i+1].getRank() == myHand[i+2].getRank())
				{
					if ((i+3)<HAND_SIZE && myHand[i+2].getRank() == myHand[i+3].getRank())
					{
						tmpSet.add(myHand[i]);
						tmpSet.add(myHand[i+1]);
						tmpSet.add(myHand[i+2]);
						tmpSet.add(myHand[i+3]);
						//createGroup(tmpSet);
						//tmpSet2 will be reset when gets out of scope after every loop iteration
						Set<Card> tmpSet2 = new HashSet<Card>();
						tmpSet2.addAll(tmpSet);
						groupSets.add(tmpSet2);
						tmpSet.clear();
						i++;
						
					}
					else
					{
						tmpSet.add(myHand[i]);
						tmpSet.add(myHand[i+1]);
						tmpSet.add(myHand[i+2]);
						//createGroup(tmpSet);
						//tmpSet2 will be reset when gets out of scope after every loop iteration
						Set<Card> tmpSet2 = new HashSet<Card>();
						tmpSet2.addAll(tmpSet);
						groupSets.add(tmpSet2);
						tmpSet.clear();
					}
					i++;
				}
				i++;
			}
		}
		
		// check for runs
		sortForRun(myHand);
		for(i=1;i<HAND_SIZE;i++)
		{
			while ( myHand[i].getSuit() == myHand[i-1].getSuit())
			{
				if(myHand[i].getRank().ordinal()-1 == myHand[i-1].getRank().ordinal())
				{
					if(!tmpSet.contains(myHand[i-1])){tmpSet.add(myHand[i-1]);}
					tmpSet.add(myHand[i]);
				}
				else if (tmpSet.size()>=3)
				{
					//tmpSet2 will be reset when gets out of scope after every loop iteration
					Set<Card> tmpSet2 = new HashSet<Card>();
					tmpSet2.addAll(tmpSet);
					runSets.add(tmpSet2);
					tmpSet.clear();
				}
				else {tmpSet.clear();}
				
				i++;
			}
		}
		
		// Overlap checking
		// check if any of the arraylists is empty => no overlap
		if(groupSets.isEmpty())
		{
			while(!runSets.isEmpty()){createRun(runSets.remove(runSets.size()-1));}
		}
		else if(runSets.isEmpty())
		{
			while(!groupSets.isEmpty()){createGroup(groupSets.remove(groupSets.size()-1));}
		}
		// if one of the lists is not empty, there could be an overlap
		else 
		{
			
		}
	}
	
	
	/**
	 * Sorts an array of card for finding a group, sort by rank
	 * sorting from low to high values
	 * @param a an array of cards
	 */
	public void sortForGroup(Card[] a)
	{
		//TODO
	}
	
	/**
	 * Sorts an array of Cards for finding a run, sort by suit and rank
	 * sorting from low to high values
	 * @param a an array of cards
	 */
	public void sortForRun(Card[] a)
	{
		//TODO
	}
	
	public int getSetValue(Set<Card> someSet)
	{
		Iterator<Card> itr = someSet.iterator();
		Card tmpC = itr.next();
		int tot = 0;
		
		while (itr.hasNext())
		{
			if (tmpC.getRank() == Rank.KING || tmpC.getRank() == Rank.QUEEN || tmpC.getRank() == Rank.JACK)
			{
				tot = tot + 10;
			}
			else
			{
				tot = tot + (tmpC.getRank().ordinal() +1);
			}
			tmpC = itr.next();
		}
		return tot;
	}
	
	public boolean overlap(Set<Card> set1,Set<Card> set2)
	{
		Iterator<Card> itr1 = set1.iterator();
		
		while(itr1.hasNext())
		{
			if(set2.contains(itr1.next()))
			{
				return true;
			}
		}
		return false;
	}
	
	public static Comparator<Hand> createByRankComparator(final Rank pRank)
	{
	    assert pRank != null;
	    return new Comparator<Hand>()
	    {
	        @Override
	        public int compare(Hand pHand1, Hand pHand2)
	        {
	            int total1 = 0;
	            for( Card card: pHand1 )
	            {
	                if( card.getRank() == pRank )
	                {
	                    total1++;
	                }
	            }
	            int total2 = 0;
	            for( Card card: pHand2 )
	            {
	                if( card.getRank() == pRank )
	                {
	                    total2++;
	                }
	            }
	            return total1 - total2;
	        }
	    };
	}

	@Override
	public  Iterator<Card> iterator()
	{
		
		return aHand.iterator();
	}
}
