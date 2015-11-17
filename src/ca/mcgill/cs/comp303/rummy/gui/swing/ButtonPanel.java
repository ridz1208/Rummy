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

public class ButtonPanel extends JPanel implements Observer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color BACKGROUND_COLOR = new Color(0, 102, 0);
	GameEngine g = GameEngine.getInstance();
	
	
	public ButtonPanel()
	{
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(BACKGROUND_COLOR);
		g.addObserver(this);
		
		setLayout(new GridLayout(5,1));
		
		JButton newgame = new JButton("New Game");
		JButton autogame = new JButton("Auto-Play Game");
		JButton save = new JButton("Save Game");
		JButton addplayer = new JButton ("Add New Player");
		JButton kill = new JButton ("KILL");
		
		
		add(newgame);
		add(autogame);
		add(save);
		add(addplayer);
		add(kill);
		
		newgame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				g.newGame(new Human("Rida"),new Comp("Terminator",new SmartBot()));
			}
		});
		
		setVisible(true);
	}

	@Override
	public void update(Actions action, IPlayer aPlayer, Card aCard)
	{
		
	}
	
}