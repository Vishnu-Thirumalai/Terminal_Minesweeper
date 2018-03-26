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
	
	public abstract void displayAdmin();
	public abstract void displayScore();	
	
	public abstract void displayGrid();
	public abstract void revealMineField();
	public abstract void revealNumGrid();
	
	public abstract void displayGamePrompt();
	public abstract void displayLoss();
	public abstract void displayWin();
	
	public abstract void displayOptionInputError();
	public abstract void displayCoordinateInputError();

	public abstract void displayReplayPrompt();
}

