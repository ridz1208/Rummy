package ca.mcgill.cs.comp303.rummy.model;

import java.io.Serializable;

import ca.mcgill.cs.comp303.rummy.model.GameEngine.Actions;


public interface Observer extends Serializable
{
	public void update(Actions action, IPlayer aPlayer,Card aCard);
}
