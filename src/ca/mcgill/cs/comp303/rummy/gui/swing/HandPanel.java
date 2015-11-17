package ca.mcgill.cs.comp303.rummy.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import ca.mcgill.cs.comp303.rummy.ai.SmartBot;
import ca.mcgill.cs.comp303.rummy.model.*;
import ca.mcgill.cs.comp303.rummy.model.GameEngine.Actions;

public class HandPanel extends JPanel implements Observer,ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color BACKGROUND_COLOR = new Color(0, 102, 0);
	private static final int CARD_HEIGHT = CardImages.getBack().getIconHeight();
	private static final int CARD_WIDTH = CardImages.getBack().getIconWidth();
	GameEngine g = GameEngine.getInstance();
	IPlayer me;
	Hand myH;
	Card[] handCards = new Card[11];
	JLabel[] jCards = new JLabel[11];
	JPanel opt;
	JPanel sel;
	JButton deck;
	JButton pile;
	JButton discard;
	JButton knock;
	JButton pass;
	JLabel info;
	JButton PLAY;
	int selected;
	
	
	
	public HandPanel()
	{
		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(0,150));
		setBackground(BACKGROUND_COLOR);
		g.addObserver(this);
		setLayout(new BorderLayout());
		
		opt = new JPanel();
		opt.setLayout(new GridLayout(1,4));
		opt.setBackground(BACKGROUND_COLOR);
		
		sel= new JPanel();
		sel.setBackground(BACKGROUND_COLOR);
		
		
		deck = new JButton("Deck");
		pile = new JButton("Pile");
		knock= new JButton("Knock");
		discard = new JButton("Discard Card");
		pass = new JButton("Pass");
		info = new JLabel ("Choose:    ");
		PLAY= new JButton("PLAY");
		
		
		deck.addActionListener(this);
		pile.addActionListener(this);
		discard.addActionListener(this);
		knock.addActionListener(this);
		pass.addActionListener(this);
		PLAY.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				me.startTurn();
				opt.remove(PLAY);
				g.switchPlayer();
			}
		});
		add(opt,BorderLayout.SOUTH);
		add(sel,BorderLayout.NORTH);
		setVisible(true);
	}
	
	public void setPlayer (IPlayer p)
	{
		me=p;
		myH = new Hand();
	}

	private void paintHand(int laps)
	{
		int j=0;
		Iterator<Card> itr= myH.iterator();
		while(itr.hasNext())
		{
			handCards[j++]=itr.next();
		}
		new Timer((laps),new ActionListener()
		{
			int k=0;
			public void actionPerformed(ActionEvent e)
			{
				if (k == 10) {((Timer)e.getSource()).stop();}
				else
				{ 
					JLabel tmp = new JLabel();
					tmp.setIcon(CardImages.getCard(handCards[k]));
					sel.add(tmp);
					jCards[k] = tmp;
					k++;
					revalidate();
					repaint();
				}
			}
		}).start();
	}
	@Override
	public void update(Actions action, IPlayer aPlayer, Card aCard)
	{
		if (action==Actions.DRAW_DECK)
		{
			if (aPlayer.getClass() == me.getClass())
			{
				myH.add(aCard);
				if (myH.size()>10)
				{
					JLabel tmp = new JLabel();
					tmp.setIcon(CardImages.getCard(aCard));
					jCards[10]=tmp;
					sel.add(tmp);
					revalidate();
					repaint();
				}
			}
		}
		if (action==Actions.DRAW_PILE)
		{
			if (aPlayer.getClass() == me.getClass())
			{
				myH.add(aCard);
				JLabel tmp = new JLabel();
				tmp.setIcon(CardImages.getCard(aCard));
				jCards[10]=tmp;
				sel.add(tmp);
				revalidate();
				repaint();
				
			}
		}
		if (action==Actions.NEW_GAME)
		{
			if (me.getClass() == Human.class){
				paintHand(600);
			}
			else 
			{
				pause(300); 
				paintHand(600);
			}
			
			
		}
		
		if (action==Actions.TURN)
		{
			if (aPlayer.getClass()==Human.class && me==aPlayer)
			{
				opt.add(info);
				opt.add(deck);
				opt.add(pile);
				
				addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e)
					{
						selected = getSelectionI(e.getX() , e.getY() );
						if(selected > 0)
						{
							jCards[selected].setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
						}
					}
				});
				
				
				
			}
			if (aPlayer.getClass()==Comp.class && me==aPlayer)
			{
				opt.add(PLAY);
				revalidate();
				repaint();
			}
		}
		
		
		if (action==Actions.DISCARD)
		{
			if(aPlayer == me)
			{
				sel.removeAll();
				myH.remove(aCard);
				paintHand(1);
				info.setText("");
			}
		}
		
		
	}
	
	public int getSelectionI(int x,int y)
	{
		if(jCards[0]==null) {System.out.println("null1");}
		if(jCards[10]==null) {System.out.println("null2");}
//		if() {System.out.println("null1");}
		if(y < jCards[0].getY() || y> jCards[0].getY()+CARD_HEIGHT || x<jCards[0].getX() || x>jCards[10].getX()+CARD_WIDTH)
		{
			return -1;
		}
		return (int)(x-jCards[0].getX())/CARD_WIDTH ;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(((JButton)e.getSource()).getText()=="Deck")
		{
			me.drawDeck();
			opt.remove(deck);
			opt.remove(pile);
			opt.add(discard);
			opt.add(knock);
			info.setText("Choose a card then... ");
			
			
		}
		else if(((JButton)e.getSource()).getText()=="Pile")
		{
			me.drawPile();
			opt.remove(deck);
			opt.remove(pile);
			opt.add(discard);
			opt.add(knock);
			info.setText("Choose a card then... ");
			repaint();
		}
		else if(((JButton)e.getSource()).getText()=="Discard Card")
		{
			me.discard(handCards[selected]);
			opt.remove(knock);
			opt.remove(discard);
			opt.add(pass);
			repaint();
		}
		else if(((JButton)e.getSource()).getText()=="Knock")
		{
			opt.remove(knock);
			opt.remove(discard);
			repaint();
			
		}
		else if(((JButton)e.getSource()).getText()=="Pass")
		{
			opt.remove(pass);
			repaint();
			g.switchPlayer();
			
		}
	}
	private void pause(int t)
	{
		try
		{
			TimeUnit.MILLISECONDS.sleep(t);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
}