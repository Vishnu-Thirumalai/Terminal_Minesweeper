package model;
import display.*;
public class setup {

	public static void main(String args[]) {

		int r = 7;// Rows
		int c = 7;// Columns
		int b = 7;// Bombs

		Display d = null;
		
		for(int i=0; i < args.length; i++)
		{
			if(args[i].equalsIgnoreCase("-c"))
			{
				try {
					r = Integer.parseInt(args[i+1]);
					c = Integer.parseInt(args[i+2]);
					b = Integer.parseInt(args[i+3]);

					if (b >= r * c) {
						throw new Exception("Impossible layout");
					}
				} catch (Exception e) {
					System.out.println("Given dimensions could not be used, using default values.");
				}
			}
		}
		
		if(d==null){
			d = new ASCIIDisplay();
		}
		
		Minesweeper minesweep = new Minesweeper(r, c, b, d);
		d.setModel(minesweep);
		minesweep.start();

	}
}
