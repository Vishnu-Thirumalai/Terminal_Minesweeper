package model;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Arrays;

import display.Display;
import model.SquareState;

public class Minesweeper {
	private final int Crows; // to store custom values
	private final int Ccolumns;
	private final int Cbombs;

	private int rows; // to store the number of rows and columns each run
	private int columns;
	private int bombs;

	static private int wins = 0; // to store the number of runs, wins and losses across all objects									
	static private int losses = 0;
	static private int runs = 0;

	private boolean mineGrid[][]; // to store the locations of the mines
	private byte numGrid[][]; // to show the number of mines in proximity to each space
	
	private SquareState[][] playStateGrid; 
	private int remainingSpaces;
	private boolean playing;
	
	private boolean admin; // to activate admin mode
	private final Scanner scn;
	private boolean replay;

	private Display d;


	Minesweeper(int Crows, int Ccolumns, int Cbombs, Display d)// to accept values for the custom mode
	{
		this.Ccolumns = Ccolumns;
		this.Crows = Crows;
		this.Cbombs = Cbombs;

		rows = 0;
		columns = 0;
		bombs = 0;

		this.d = d;
		admin = false;
		scn = new Scanner(System.in);
	}

	
	public void start()// to start the program
	{
		admin = false;

		replay = true;
		while (replay) {

			replay = false;
			runs++;
			menu();
			d.displayReplayPrompt();
		}

		d.displayFarewell();

	}

	private void menu() // to allow choice of the size of the grid and activate
						// admin mode
	{

		d.displayMenu();
		int opt = 0;
		boolean valid = false;

		while (!valid) {
			try {
				opt = scn.nextInt();
				valid = true;

				switch (opt) {
				case 1:
					gridGenerate(5, 5, 5);
					break;

				case 2:
					gridGenerate(8, 8, 16);
					break;

				case 3:
					gridGenerate(12, 12, 48);
					break;

				case 4:
					gridGenerate(Ccolumns, Crows, Cbombs);
					break;

				case 63: // admin mode for easier testing
					admin = true;
					d.displayAdmin();

				default:
					
					valid = false;
					break;
				}
			} catch (InputMismatchException e) {
				d.displayOptionInputError();
				continue;
			}
		}

		mineGrid = new boolean[columns][rows];
		numGrid = new byte[columns][rows];
		gridFill();

		if (admin){
			d.revealMineField();
			d.revealNumGrid();
		}

		play();
	}

	private void gridGenerate(int rows, int columns, int bombs) {
		this.rows = rows;
		this.columns = columns;
		this.bombs = bombs;

		mineGrid = new boolean[columns][rows];
		numGrid = new byte[columns][rows];
		gridFill();
	}

	private void gridFill()// fills the grids with mines and numbers
	{

		int bombsPlaced = 0;
		int x = 0;
		int y = 0;
		while (bombsPlaced != bombs) {
			y = (int) (Math.random() * (rows));
			x = (int) (Math.random() * (columns));
			
			if (mineGrid[y][x])
				continue;
			bombInsert(x, y);
			bombsPlaced++;
		}
	}

	private void bombInsert(int x, int y) {
		mineGrid[y][x] = true; // 00 is the top corner, y increases downwards and x increases right

		if (y != 0)// If it's not the top row
		{
			numGrid[y - 1][x]++;
			if (x != 0) // If it's not the top left corner
			{
				numGrid[y - 1][x - 1]++;
			}

			if (x != columns - 1)// If it's not the top right corner
			{
				numGrid[y - 1][x + 1]++;

			}
		}

		if (x != 0)// If it's not the leftmost column
		{
			numGrid[y][x - 1]++;

			if (y != rows - 1)// If it's not the bottom left corner
			{
				numGrid[y + 1][x - 1]++;
			}
		}

		if (y != rows - 1)// If it's not the bottom row
		{
			numGrid[y + 1][x]++;
			if (x != columns - 1)// If it's not the bottom right corner
			{
				numGrid[y + 1][x + 1]++;
			}
		}

		if (x != columns - 1) // If it's not the rightmost column
		{
			numGrid[y][x + 1]++;
		}
	}


	private void playDisplay() {

		d.displayGrid();
		d.displayGamePrompt();
	}

	private void play()// the actual game coding
	{
		/*
GB

		 * String gridDisplay[][] = new String[rows + 2][columns + 2];// the 2D
		 * // matrix // which is // diplayed for (int i = 0; i < rows + 2;
		 * i++)// fills all non-playing areas with // line numbers and
		 * seperators { for (int j = 0; j < columns + 2; j++) {
		 * gridDisplay[i][j] = " "; if (j == 1 && i == columns - 1) {
		 * gridDisplboolean[][] revealed ay[i][j] = "|"; } else if (j == 0 && i != columns) {
		 * gridDisplay[i][j] = String.valueOf(i + 1); } else if (i == columns &&
		 * j != 0 && j < rows + 1) { if (j == 1) { gridDisplay[i][j] = "    " +
		 * String.valueOf(j); } else { gridDisplay[i][j] = "" +
		 * String.valueOf(j); } } else if (j == 1) { gridDisplay[i][j] = "|"; }
		 * else if (i == rows - 1 && j < columns + 2) { gridDisplay[i][j] = "_";
		 * } } }
		 **/

		playStateGrid = new SquareState[columns][rows];
		
		for(SquareState[] col: playStateGrid)
		{
			Arrays.fill(col,SquareState.NORMAL);
		}
		
		remainingSpaces = (columns * rows) - bombs;
		playing = true;

		while(playing)
		{
			playDisplay();
		}
	}
	
	public void selectSpace(int r, int c, SquareState newState)
	{
		if(playing)
		{
			if (c >= columns || r >= rows || c < 0 || r < 0)// if the player gives invalid co-ordinates
			{
				d.displayCoordinateInputError();
			}
			else
			{
				if (mineGrid[r][c] == true)// when the player hits a mine
				{
					endLoss();
					playing = false;
				} else if (playStateGrid[r][c] != SquareState.REVEALED) // to show the number of mines next to that space from next time if not already revealed (and not a mine)
				{
					spaceReveal(r,c);
					if(numGrid[r][c] == 0)
					{
						cascade(r,c);
					}
				}
			}
			
			if (remainingSpaces == 0)// if the player fills every non-mine space
			{
				endWin();
				playing = false;
			}
		}
		
			/**
			 * for (int i = 0; i < columns + 1; i++)// diplays the screen { for
			 * (int j = 0; j < rows + 2; j++) { if (j != rows)
			 * System.out.print(gridDisplay[i][j] + "   "); else
			 * System.out.print(gridDisplay[i][j] + "  "); }
			 * System.out.println("\n"); }
			 **/
	}
	
	private void cascade(int r, int c) //Only called from inside revealSpace, so no need for checks
	{
		System.out.println(r+""+c);
		if(r>0 && checkSpaceForCascade(r-1,c))
		{
			spaceReveal(r-1,c);
			if(numGrid[r-1][c] == 0)
				cascade(r-1,c);

		}
		if(c>0 && checkSpaceForCascade(r,c-1))
		{
			spaceReveal(r,c-1);
			if(numGrid[r][c-1] == 0)
				cascade(r,c-1);

		}
		if(r<rows-1 && checkSpaceForCascade(r+1,c))
		{
			spaceReveal(r+1,c);
			if(numGrid[r+1][c] == 0)
				cascade(r+1,c);

		}
		if(c<columns-1 && checkSpaceForCascade(r,c+1))
		{
			spaceReveal(r,c+1);
			if(numGrid[r][c+1] == 0)
				cascade(r,c+1);
		}
	}

	private boolean checkSpaceForCascade(int r, int c)
	{
		return playStateGrid[r][c]==SquareState.NORMAL && !mineGrid[r][c];
	}
	
	private void spaceReveal(int r, int c)
	{
		playStateGrid[r][c] = SquareState.REVEALED;
		remainingSpaces--;
	}
	
	private void endLoss() {

		d.displayLoss();
		losses++;
	}

	private void endWin() {

		d.displayWin();
		wins++;
	}

	public void playAgain()
	{
		replay = true;
	}
	
	public int getCcolumns() {
		return Ccolumns;
	}
	
	public int getCrows() {
		return Crows;
	}

	public int getCbombs() {
		return Cbombs;
	}
	
	public HashMap<String,Integer> getScoreData(){
		
		HashMap<String,Integer> ret = new HashMap<>(3);
		ret.put("RUNS",runs);
		ret.put("WINS", wins);
		ret.put("LOSSES", losses);
		
		return ret;
	}

	public boolean[][] getMineGrid() {
		return Arrays.copyOf(mineGrid,mineGrid.length);
	}

	public byte[][] getNumGrid() {
		return Arrays.copyOf(numGrid,numGrid.length);
	}
	
	public SquareState[][] getPlayStateGrid(){
		return Arrays.copyOf(playStateGrid, playStateGrid.length);
	}
	
	public boolean getPlaying(){
		return playing;
	}
	
	public int getRemainingSpaces(){
		return remainingSpaces;
	}
	

}



