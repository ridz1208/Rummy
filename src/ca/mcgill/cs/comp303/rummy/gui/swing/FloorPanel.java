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

import ca.mcgill.cs.comp303.rummy.ai.SmartBot;
import ca.mcgill.cs.comp303.rummy.model.*;
import ca.mcgill.cs.comp303.rummy.model.GameEngine.Actions;

public class FloorPanel extends JPanel implements Observer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color BACKGROUND_COLOR = new Color(0, 102, 0);
	GameEngine g = GameEngine.getInstance();
	JLabel centerdeck;
	JLabel centerpile;

	
	public FloorPanel()
	{
		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(0,150));
		setBackground(BACKGROUND_COLOR);
		setLayout(new BorderLayout());
		
		g.addObserver(this);
		
	}

	@Override
	public void update(Actions action, IPlayer aPlayer, Card aCard)
	{
		if (action==Actions.TURN && aPlayer.getClass()==Human.class)
		{
			
		}
		if (action==Actions.NEW_GAME)
		{
			centerdeck = new JLabel();
			centerdeck.setIcon(CardImages.getBack());
			add(centerdeck,BorderLayout.WEST);
			
			centerpile = new JLabel();
			centerpile.setIcon(CardImages.getCard(g.topOfPile()));
			add(centerpile,BorderLayout.CENTER);
			repaint();
		}
		if (action==Actions.DISCARD)
		{
			centerpile.setIcon(CardImages.getCard(g.topOfPile()));
			repaint();
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