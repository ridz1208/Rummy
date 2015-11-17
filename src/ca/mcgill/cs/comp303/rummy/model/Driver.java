package ca.mcgill.cs.comp303.rummy.model;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.google.gson.Gson;

import ca.mcgill.cs.comp303.rummy.ai.*;
;
public class Driver
{
	public static void main (String[] args)// throws NoPlayersException, FileNotFoundException, IOException
	{
		
//		GameEngine g=GameEngine.getInstance(new Human("Rida"),new Comp("Terminator"));
		GameEngine g = GameEngine.getInstance();
		
		
		
//		IPlayer h = new Human("Rida");
//		IPlayer c = new Comp("Terminator",new SmartBot());
		
		
		g.addObserver(new LoggerPrim());
//		g.addObserver(new LoggerFile());
//		g.addPlayer(h);
//		g.addPlayer(c);

		g.autoPlay(new Human("Rida",new SmartBot()),new Comp("Terminator",new SmartBot()));
		

		
		
	}
}