import java.util.Scanner;
import java.util.InputMismatchException;

public class Minesweeper {
	private final int Crows; // to store custom values
	private final int Ccolumns;
	private final int Cbombs;

	private int rows; // to store the number of rows and columns each run
	private int columns;
	private int bombs;

	static private int wins = 0; // to store the number of runs, wins and losses
									// across all objects
	static private int losses = 0;
	static private int runs = 0;

	private boolean grid[][]; // to store the locations of the mines
	private byte answerGrid[][]; // to show the number of mines in proximity to
									// each space

	private boolean admin; // to activate admin mode
	private final Scanner scn;

	public static void main(String args[]) {

		int r = 0;// Rows
		int c = 0;// Columns
		int b = 0;// Bombs

		try {
			r = Integer.parseInt(args[0]);
			c = Integer.parseInt(args[1]);
			b = Integer.parseInt(args[2]);

			if (b >= r * c) {
				throw new Exception("Impossible layout");
			}
		} catch (Exception e) {
			System.out
					.println("Given dimensions could not be used, using default values.");
			r = 7;
			b = 7;
			c = 7;
		}

		Minesweeper minesweep = new Minesweeper(r, c, b);
		minesweep.start();

	}

	Minesweeper(int Crows, int Ccolumns, int Cbombs)// to accept values for the
													// custom mode
	{
		this.Ccolumns = Ccolumns;
		this.Crows = Crows;
		this.Cbombs = Cbombs;

		rows = 0;
		columns = 0;
		bombs = 0;

		admin = false;
		scn = new Scanner(System.in);
	}

	private void start()// to start the program
	{
		admin = false;

		boolean play = true;
		String opt;
		while (play) {

			play = false;
			menu();
			runs++;
			
			System.out.println("\nPlay again?Y/N");
			opt = scn.next();
			if(opt.equalsIgnoreCase("Y"))
			{
				play = true;
			}
			else if(opt.equalsIgnoreCase("N"))
			{}
			else
			{
				System.out.println("Defaulting to No.");
			}
		}

		System.out.println("We hope you enjoyed your game.");
		System.out.println("See you again soon!" + (char) 61514);
		try {
			Thread.sleep(2500);
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}
	}

	private void menu() // to allow choice of the size of the grid and activate
						// admin mode
	{
		System.out.println("Welcome to Minesweeper!" + (char) 61517);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}

		System.out.println("Choose your difficulty level:\n"
				+ "1.Easy [5x5, 5 mines]\n" + "2.Medium [8x8, 16 mines]\n"
				+ "3.Hard [12x12, 48 mines]\n" + "4.Custom[" + Ccolumns + "x"
				+ Crows + ", " + Cbombs + " mines]\n"
				+ "Type in the option number below");

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
					System.out.println("Admin mode on");
					System.out.print("Runs:" + runs + " Wins:" + wins
							+ " Losses:" + losses);

				default:
					System.out.println("Please enter a valid number");
					valid = false;
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter a valid number");
				continue;
			}
		}

		grid = new boolean[columns][rows];
		answerGrid = new byte[columns][rows];
		gridFill();

		if (admin)
			peek();

		play();
	}

	private void gridGenerate(int rows, int columns, int bombs) {
		this.rows = rows;
		this.columns = columns;
		this.bombs = bombs;

		grid = new boolean[columns][rows];
		answerGrid = new byte[columns][rows];
		gridFill();
	}

	private void gridFill()// fills the grids with mines and numbers
	{

		int bombsPlaced = 0;
		int x = 0;
		int y = 0;
		while (bombsPlaced < bombs) {
			y = (int) (Math.random() * (rows));
			x = (int) (Math.random() * (columns));

			if (grid[y][x])
				continue;
			bombInsert(x, y);
			bombsPlaced++;
		}
	}

	private void bombInsert(int x, int y) {
		grid[y][x] = true; // 00 is the top corner, y increases downwards and x
							// increases right

		if (y != 0)// If it's not the top row
		{
			answerGrid[y - 1][x]++;
			if (x != 0) // If it's not the top left corner
			{
				answerGrid[y - 1][x - 1]++;
			}

			if (x != columns - 1)// If it's not the top right corner
			{
				answerGrid[y - 1][x + 1]++;

			}
		}

		if (x != 0)// If it's not the leftmost column
		{
			answerGrid[y][x - 1]++;

			if (y != rows - 1)// If it's not the bottom left corner
			{
				answerGrid[y + 1][x - 1]++;
			}
		}

		if (y != rows - 1)// If it's not the bottom row
		{
			answerGrid[y + 1][x]++;

			if (x != columns - 1)// If it's not the bottom right corner
			{
				answerGrid[y + 1][x + 1]++;
			}
		}

		if (x != columns - 1) // If it's not the rightmost column
		{
			answerGrid[y][x + 1]++;
		}
	}

	void peek() // allows admins to see the layout of the grids(ran every time
				// during alpha testing)
	{
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				System.out.print(answerGrid[i][j] + " ");
			}
			System.out.println();
		}
		String s = scn.next();
	}

	private void gridDisplay(byte[][] grid, boolean[][] revealed) {

		System.out.print("  ");
		for (int idx = 0; idx < grid.length; idx++) {
			System.out.print(idx + " ");
		}
		System.out.println();

		for (int i = 0; i < grid.length; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print((revealed[i][j] ? grid[i][j] : "?") + " ");
			}
			System.out.println();
		}
	}

	private void play()// the actual game coding
	{
		boolean[][] revealed = new boolean[grid.length][grid.length];
		boolean prize = true;
		int r = 0;
		int c = 0;
		for (int k = 0; k <= ((columns * rows) - bombs);)// makes the grid print multiple time
		{
			System.out.print("\f"); // clears the screen, which was cluttered up during alpha testing
			gridDisplay(answerGrid, revealed);

			System.out.println(columns * rows - k - bombs + " spaces remaining");// to show how many non-mine spaces are remaining(requested in beta testing)																	
			System.out.print("Enter the column number:");
			c = scn.nextInt();
			System.out.print("Enter the row number:");
			r = scn.nextInt();
			
			if (c > columns || r > rows || c < 0 || r < 0)// if the player gives invalid co-ordinates
			{
				System.out.println("Invalid co-ordinates");
				try {
					Thread.sleep(1750);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			} 
			else if (grid[r][c] == true)// when the player hits a mine
			{
				endLoss();
				prize = false;
				break;
			}				
			else if(!revealed[r][c]) // to show the number of mines next to that space from the next time onwards if it is not a mine
			{
				revealed[r][c] = true;
				k++; 
			}	 
			//else the space has been revealed already
		}
		if (prize)// if the player fills every non-mine space
		{
			endWin();
		}
	}

	private void endLoss() {
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
		losses++;
	}

	private void endWin() {

		System.out.println("\f");
		System.out.println((char) 61591 + " Congratulations !!" + (char) 61590);
		System.out.println("You won!!" + (char) 61505);
		wins++;
	}
}

