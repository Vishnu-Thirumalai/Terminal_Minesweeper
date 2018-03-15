package display;

import model.Minesweeper;


public abstract class Display {
	
	protected Minesweeper model;
	
	public void setModel(Minesweeper m)
	{
		model = m;
	}
	
	public abstract void displayMenu();
	public abstract void displayFarewell();
	
	public abstract void displayLoss();
	public abstract void displayWin();
}

