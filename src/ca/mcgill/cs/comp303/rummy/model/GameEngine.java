package ca.mcgill.cs.comp303.rummy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import ca.mcgill.cs.comp303.rummy.ai.SmartBot;

/**
 * 
 */
public final class GameEngine implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static GameEngine aGame = new GameEngine();
	public transient ArrayList<Observer> observers = new ArrayList<Observer>();
	private Stack<Card> aPile;
	public enum Actions {DRAW_DECK , DRAW_PILE , DISCARD , KNOCK , TURN , NEW_PLAYER, NEW_GAME};
	
	private Deck aDeck;
	private Human hPlayer;
	private Comp cPlayer;
	private int scoreH;
	private int scoreC;
	private IPlayer currentP;
	private boolean knocked;
	private int countermax=0;
	

	
	private GameEngine()
	{}
	
	/**
	 * @return 
	 */
	
	public static GameEngine getInstance() 
	{
		return aGame;
	}

	/**
	 * @return
	 */
	public void addObserver(Observer observer)
	{
		observers.add(observer);
	}
	
	private void notifyObservers(Actions action, IPlayer aPlayer,Card aCard)
	{
		for(Observer observer : observers)
		{
			observer.update(action,aPlayer,aCard);
		}
	}
	
	
	public void addPlayer(IPlayer pPlayer)
	{
		if (pPlayer.getClass().equals(Human.class))
		{
			hPlayer = (Human) pPlayer;
			notifyObservers(Actions.NEW_PLAYER,hPlayer,null);
		}
		else
		{
			cPlayer = (Comp) pPlayer;
			notifyObservers(Actions.NEW_PLAYER,cPlayer,null);
		}
		
	}
	
	public Human getHumanPlayer()
	{
		return hPlayer;
	}
	public Comp getCompPlayer()
	{
		return cPlayer;
	}
	
	public void newGame(IPlayer h, IPlayer c) // throws NoPlayersException
	{
		aPile = new Stack<Card>();
		aDeck = new Deck();
		addPlayer(h);
		addPlayer(c);
		
		scoreH=0;
		scoreC=0;
		knocked=false;
		
		for(int i=0;i<10;i++)
		{
			hPlayer.drawDeck();
			cPlayer.drawDeck();
			
		}
		aPile.push(aDeck.draw());
		notifyObservers(Actions.NEW_GAME,null,aPile.peek());
		currentP=hPlayer;
		notifyObservers(Actions.TURN,currentP,null);

		
	}
	public Card drawFromDeck(IPlayer who)
	{
		Card tmp = aDeck.draw();
		notifyObservers(Actions.DRAW_DECK,who,tmp);
		return (tmp);
		
	}

	
	public Card drawFromPile()
	{
		notifyObservers(Actions.DRAW_PILE,currentP,aPile.peek());
		return aPile.pop();
	}
	
	public void cardDiscarded(Card pCard)
	{
		aPile.push(pCard);
		notifyObservers(Actions.DISCARD,currentP,pCard);
		
	}
	public Card topOfPile()
	{
		return aPile.peek();
	}
	
	public void switchPlayer()
	{
		if (currentP.getClass().equals(Comp.class))
		{
			currentP=hPlayer;
		}
		else
		{
			currentP=cPlayer;
		}
		
		notifyObservers(Actions.TURN,currentP,null);
	}
	public void autoPlay(IPlayer h, IPlayer c) //throws NoPlayersException
	{
		IPlayer hSmart = new Human(h.getName(), new SmartBot());
		newGame(hSmart,c);
		while(!knocked && countermax<5)
		{
			countermax++;
//			begin();
		}
		if(!knocked){terminate(hPlayer);}
	
	}
	public void terminate(IPlayer playerKnocked)
	{
		scoreH=hPlayer.getHandValue();
		scoreC=cPlayer.getHandValue();
		knocked=true;
		notifyObservers(Actions.KNOCK,playerKnocked,null);
//		System.out.println(scoreH+" "+scoreC);
	}
	
}
