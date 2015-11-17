package ca.mcgill.cs.comp303.rummy.model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import ca.mcgill.cs.comp303.rummy.model.GameEngine.Actions;

public class LoggerFile implements Observer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FileHandler fl;
	private Logger log = Logger.getLogger("GameLog");
	
	
	@Override
	public void update(Actions action, IPlayer aPlayer, Card aCard)
	{
		//initializing the file handler
		try
		{
			fl = new FileHandler("GameLogger.html");
			log.addHandler(fl);
		}
		catch (SecurityException | IOException e)
		{
			e.printStackTrace();
		}
		
		if (action==Actions.NEW_PLAYER)
		{
			log.info("new player : " + aPlayer.getName());
		}
		if (action==Actions.NEW_GAME)
		{
			log.info("NEW GAME STARTED");
		}
		if (action==Actions.DRAW_DECK)
		{
			if(aPlayer==null){log.info("Card Distributed");}
			else{log.info(aPlayer.getName()+" drew a cardfrom the deck");}

		}
		if (action==Actions.DRAW_PILE)
		{
			log.info(aPlayer.getName()+" drew a cardfrom the pile a "+aCard);
		}
		if (action==Actions.DISCARD)
		{
			log.info (aPlayer.getName()+" discarded a "+aCard);
		}
		if (action==Actions.KNOCK)
		{
			log.info(aPlayer.getName()+" knocked");
		}
		if (action==Actions.TURN)
		{
			log.info("It is "+aPlayer.getName()+"'s turn");
		}
		
	}
	
}