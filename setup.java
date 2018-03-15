public class setup {

	public static void main(String args[]) {

		int r = 0;// Rows
		int c = 0;// Columns
		int b = 0;// Bombs

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
					r = 7;
					b = 7;
					c = 7;
				}
			}
		}
		


		Minesweeper minesweep = new Minesweeper(r, c, b);
		minesweep.start();

	}
}

