package display;

public class ASCIIDisplay extends Display{

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
				+ "1.Easy [5x5, 5 mines]\n" + "2.Medium [8x8, 16 mines]\n"
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

	
}

