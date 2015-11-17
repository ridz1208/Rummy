package ca.mcgill.cs.comp303.rummy.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

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

public class RunRummy extends JFrame implements ActionListener,Observer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color BACKGROUND_COLOR = new Color(0, 102, 0);

	GameEngine g = GameEngine.getInstance();
	JLabel[] compJL;
	JLabel[] humanJL;
	ButtonPanel buttonP;
	HandPanel cHandP;
	HandPanel hHandP;
	FloorPanel floorP;
	
	
	public static void main(String[] args)
	{
		new RunRummy();
	}
	
	public RunRummy()
	{
		super.setTitle("GIN RUMMY GAME");
		
		buttonP = new ButtonPanel();
		hHandP = new HandPanel();
		cHandP = new HandPanel();
		floorP = new FloorPanel();
		
		g.addObserver(new LoggerPrim());
		g.addObserver(this);
		
		setLayout(new BorderLayout());
		add(buttonP,BorderLayout.WEST);
		add(cHandP,BorderLayout.NORTH);
		add(hHandP,BorderLayout.SOUTH);
		add(floorP,BorderLayout.CENTER);
		
		//SET LABELS IN PANELS
//		compJL = new JLabel[11];
//		humanJL = new JLabel[11];
		
		
		setBackground(BACKGROUND_COLOR);
		setSize(1100,800);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void update(Actions action, IPlayer aPlayer, Card aCard)
	{
		
		if (action==Actions.NEW_PLAYER)
		{
			if (aPlayer.getClass() == Human.class)
			{
				hHandP.setPlayer(aPlayer);
			}
			else if (aPlayer.getClass() == Comp.class)
					{
				cHandP.setPlayer(aPlayer);
			}	
		}
		if (action==Actions.NEW_GAME)
		{
			
		}
		if (action==Actions.DRAW_DECK)
		{
			
		}
		if (action==Actions.DRAW_PILE)
		{
		}
		if (action==Actions.DISCARD)
		{
			
		}
		if (action==Actions.KNOCK)
		{
		}
		if (action==Actions.TURN)
		{
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}