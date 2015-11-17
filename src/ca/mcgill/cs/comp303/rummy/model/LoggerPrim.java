package ca.mcgill.cs.comp303.rummy.model;

import ca.mcgill.cs.comp303.rummy.model.GameEngine.Actions;

public class LoggerPrim implements Observer
{

	@Override
	public void update(Actions action, IPlayer aPlayer, Card aCard)
	{
		if (action==Actions.NEW_PLAYER)
		{
			System.out.println("new player : " + aPlayer.getName());
		}
		if (action==Actions.NEW_GAME)
		{
			System.out.println("NEW GAME STARTED");
		}
		if (action==Actions.DRAW_DECK)
		{
			if(aPlayer==null){System.out.println("Card Distributed");}
			else{System.out.println(aPlayer.getName()+" drew a cardfrom the deck");}

		}
		if (action==Actions.DRAW_PILE)
		{
			System.out.println(aPlayer.getName()+" drew a cardfrom the pile a "+aCard);
		}
		if (action==Actions.DISCARD)
		{
			System.out.println (aPlayer.getName()+" discarded a "+aCard);
		}
		if (action==Actions.KNOCK)
		{
			System.out.println(aPlayer.getName()+" knocked");
		}
		if (action==Actions.TURN)
		{
			System.out.println("It is "+aPlayer.getName()+"'s turn");
		}
		
	}
	
}