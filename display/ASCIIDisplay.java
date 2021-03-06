package display;

import java.util.HashMap;
import java.util.Scanner;

import model.SquareState;



public class ASCIIDisplay extends Display{

	private boolean [][] mineGrid = null;
	private byte [][] numGrid = null;
	private Scanner scn;
	
	public ASCIIDisplay()
	{
		super();
		scn = new Scanner(System.in);
	}
	
	@Override
	public void displayFarewell() {
		System.out.println("We hope you enjoyed your game.");
		System.out.println("See you again soon!" + (char) 61514);		
		try {
			Thread.sleep(2500);
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void displayMenu() {
		System.out.println("Welcome to Minesweeper!" + (char) 61517);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}

		System.out.println("Choose your difficulty level:\n"
				+ "1.Easy [5x5, anotherString5 mines]\n" + "2.Medium [8x8, 16 mines]\n"
				+ "3.Hard [12x12, 48 mines]\n" + "4.Custom[" + model.getCcolumns() + "x"
				+ model.getCrows() + ", " + model.getCbombs() + " mine(s)]\n"
				+ "Type in the option number below");
		
	}

	@Override
	public void displayLoss() {
		System.out.print("\f");
		System.out.println("     " + (char) 61613);
		System.out.println("  " + (char) 61613 + (char) 61613 + (char) 61613
				+ (char) 61613 + (char) 61613);
		System.out.println(" " + (char) 61613 + (char) 61613 + (char) 61613
				+ (char) 61517 + (char) 61613 + (char) 61613 + (char) 61613);
		System.out.println("  " + (char) 61613 + (char) 61613 + (char) 61613
				+ (char) 61613 + (char) 61613);
		System.out.println("     " + (char) 61613);
		System.out.println((char) 61517 + "KABOOM!!" + (char) 61517);
		System.out.println((char) 61518 + "You lost." + (char) 61518);
		System.out.println("Better luck next time!");
		
	}

	@Override
	public void displayWin() {
		
		System.out.println("\f");
		System.out.println((char) 61591 + " Congratulations !!" + (char) 61590);
		System.out.println("You won!!" + (char) 61505);
		
	}


	public void displayAdmin() {
		
		System.out.println("Admin mode activated");
		displayScore();
	}

	public void displayScore() {
		
		HashMap<String,Integer> data = model.getScoreData();//Runs,Wins,Losses
		System.out.print("Runs" + data.get("RUNS"));
		System.out.print(" Wins" + data.get("WINS"));
		System.out.println(" Losses" + data.get("LOSSES"));
	}

	@Override
	public void displayOptionInputError() {
		System.out.println("Please enter a valid number");
	}
	
	public void revealMineField(){
		
		if(mineGrid == null)
		{
			mineGrid = model.getMineGrid();
		}
		
		for (int i = 0; i < mineGrid.length; i++) {
			for (int j = 0; j < mineGrid[i].length; j++) {
				System.out.print(mineGrid[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
	}
	
	public void revealNumGrid(){
		
		if(numGrid == null)
		{
			numGrid = model.getNumGrid();
		}
		
		for (int i = 0; i < numGrid.length; i++) {
			for (int j = 0; j < numGrid[i].length; j++) {
				System.out.print(numGrid[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		
	}

	@Override
	public void displayCoordinateInputError() {
		
		System.out.println("Invalid co-ordinates");
		try {
			Thread.sleep(1750);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void displayGrid() {
		
		System.out.print("\f");
		if(numGrid == null)
		{
			numGrid = model.getNumGrid();
		}
		
		SquareState[][] stateGrid = model.getPlayStateGrid();
		
		System.out.print(" ");
		for (int idx = 0; idx < numGrid.length; idx++) {
			System.out.print(idx + " ");
		}
		System.out.println();

		for (int i = 0; i < numGrid.length; i++) {
			System.out.print(i+" ");
			for (int j = 0; j < numGrid[i].length; j++) {
				switch(stateGrid[i][j])
				{
					case REVEALED:
						System.out.print(numGrid[i][j]);
						break;
					case NORMAL:	
						System.out.print("X");
						break;
					default:
						System.out.print("H");
				}
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	@Override
	public void displayGamePrompt() {
		
		
		System.out.println(model.getRemainingSpaces() + " spaces remaining");
		
		System.out.print("Enter the column number:");
		int c = scn.nextInt();
		System.out.print("Enter the row number:");
		int r = scn.nextInt();
		System.out.print("Reveal(R), Flag (F), Question(Q), Unflag(U)?");
		String o = scn.next();
		
		model.selectSpace(r, c, SquareState.REVEALED);
	}

	@Override
	public void displayReplayPrompt() {		
		System.out.println("Play again? (Y/N)");
		String s;
		
		while(true)
		{
			s = scn.next();
		
			if(s.equalsIgnoreCase("Y"))
			{
				model.playAgain();
				break;
			}
			else if (s.equalsIgnoreCase("N"))
			{
				break;
			}
			System.out.println("Please enter Y or N");
		}
	}

}

