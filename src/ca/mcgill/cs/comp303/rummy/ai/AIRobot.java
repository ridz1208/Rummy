package ca.mcgill.cs.comp303.rummy.ai;

import java.io.Serializable;

import ca.mcgill.cs.comp303.rummy.model.*;

public interface AIRobot extends Serializable
{
	public boolean KnockOrNot(Hand pHand);
	public boolean deckOrNot(Hand pHand);
	public Card bestDiscard(Hand pHand,Card justDrawn);
}