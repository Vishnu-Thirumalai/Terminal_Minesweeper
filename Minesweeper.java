import java.util.Scanner;

public class Minesweeper
{
    private int Crows;       // to store custom values 
    private int Ccolumns;    
    private int Cbombs;
    
    private int rows;        // to store the number of rows and columns each run       
    private int columns;      
    private int bombs;          
    
    static private int wins = 0;  // to store the number of runs, wins and losses across all objects
    static private int losses = 0;
    static private int runs = 0;
    
    private boolean grid[][];    //to store the locations of the mines
    private byte answerGrid[][]; //to show the number of mines in proximity to each space
    private double check;    //to provide a limit for the mines to be set
    private boolean admin;       //to activate admin mode
    private Scanner scn;
    
    
    public static void main(String args[]){
    	
    	int r = 0;//Rows
    	int c = 0;//Columns
    	int b = 0;//Bombs
    	
    	try{
    	    r = Integer.parseInt(args[0]);
    	    c = Integer.parseInt(args[1]);
    	    b = Integer.parseInt(args[2]);
    	    
    	    if(b >= r*c)
    	    {
    	        throw new Exception("Impossible layout");
    	    }
    	}
    	catch(Exception e)
    	{
    	    System.out.println("Given dimensions could not be used, using default values.");
    	    r = 7;
    	    b = 7;
    	    c = 7;
    	}
    	
    	Minesweeper minesweep = new Minesweeper(r,c,b);
    	minesweep.start();
    	
    }
    
    Minesweeper(int Crows,int Ccolumns,int Cbombs)//to accept values for the custom mode
    {
        this.Ccolumns=Ccolumns;
        this.Crows=Crows;
        this.Cbombs=Cbombs;
        
        rows = 0;          
        columns = 0;       
        bombs = 0;          
        
        check = 0.8;   
        admin = false;     
        scn = new Scanner(System.in); 
    }
    
    private void start()//to start the program
    {
        
        admin = false;
        menu();
        runs++;
        System.out.println("We hope you enjoyed your game.");
        System.out.println("See you again soon!"+(char)61514);
        try
        {
            Thread.sleep(2500);
        }
        catch(Exception e)
        {
           Thread.currentThread().interrupt();
        }
    }
    
    private void menu() //to allow choice of the size of the grid and activate admin mode
    {
            check=0.8; 
            
            System.out.println("Welcome to Minesweeper!"+(char)61517);
            try
            {
                Thread.sleep(2000);
            }
            catch(Exception e)
            {
                Thread.currentThread().interrupt();
            }
            System.out.println("Choose your difficulty level:");
            System.out.println("1.Easy [5x5, 5 mines]");
            System.out.println("2.Medium [8x8, 15 mines]");
            System.out.println("3.Hard [12x12, 30 mines]");
            System.out.println("4.Custom["+Ccolumns+"x"+Crows+", "+Cbombs+" mines]");
            System.out.println("Type in the option number below");
            int c= scn.nextInt();
            switch(c)
            {
                case 1:
                columns=5;
                rows=5;
                bombs=5;
                break;
                
                case 2:
                columns=8;
                rows=8;
                bombs=15;
                break;
                
                case 3:
                columns=12;
                rows=12;
                bombs=30;
                break;
                
                case 4:
                columns=Ccolumns;
                rows=Crows;
                bombs=Cbombs;
                check=0.5;
                break;
                
                case 63: //admin mode for easier testing
                admin=true;
                System.out.println("Admin mode on");
                System.out.println("Runs:"+runs);
                System.out.println("Wins:"+wins);
                System.out.println("Losses:"+losses);
                String s=scn.next();
                menu();
                
                default:
                System.out.println("Please enter a valid number\n\n");
                menu();
            }
            grid = new boolean[columns][rows];
            answerGrid = new byte[columns][rows];
            gridFill();
    }
        
    private void bombInsert(int x, int y)
    {
        grid[y][x] = true; // 00 is the top corner, y increases downwards and x increases right
        
        if(y!=0)// If it's not the top row
        {
            answerGrid[y-1][x]++;
            if(x!=0) //If it's not the top left corner
            {
               answerGrid[y-1][x-1]++;
            }
            
            if(x!=columns-1)//If it's not the top right corner
            {
               answerGrid[y-1][x+1]++;

            }
        }
          
        if(x!=0)// If it's not the leftmost column
        {
          answerGrid[y][x-1]++;
          
          if(y!=rows-1)//If it's not the bottom left corner
          {
            answerGrid[y+1][x-1]++;
          }
        }          
           
        if(y!=rows-1)// If it's not the bottom row
        {
          answerGrid[y+1][x]++;
          
          if(x!= columns-1)// If it's not the bottom right corner  
          {
              answerGrid[y+1][x+1]++;
          }          
        }
            
        if(x!=columns-1) // If it's not the rightmost column
        {
          answerGrid[y][x+1]++;      
        }
     }
    
    private void gridFill()// fills the grids with mines and numbers
    {    
        /** TODO: Remove this code after testing the new code works
        
        int bombDefuse=0;  //to prevent more mines being set than the limit for that difficulty
        for(int i=0;i<rows;i++)
        {
          for(int j=0;j<columns;j++)
          {
             if(check<Math.random()) //for random placement of mines
             {
                bombInsert(i,j);
                bombDefuse++;
             }
             if(bombDefuse==bombs)// if the bomb limit is reached
             {
               break;
             }
          }
          if(bombDefuse==bombs)
          {
              break;
          }
        }
        **/
        
        int bombsPlaced = 0;
        int x = 0; 
        int y = 0;
        while(bombsPlaced < bombs)
        {
            y = (int)(Math.random() * rows);
            x = (int)(Math.random() * columns);
            if(grid[y][x])
                continue;
            bombInsert(x,y);
            bombsPlaced++;     
        }
        
        if(admin) // opens the special admin features
        {
            peek();
        }
        else // sends non-admins straight to the game
        {
            play();
        }
    }
    
    
    
    void peek() //allows admins to see the layout of the grids(ran every time during alpha testing)
    {
       for(int i=0;i<columns;i++)
       {
         for(int j=0;j<rows;j++)
         {
             System.out.print(grid[i][j]+" ");
         }
         System.out.println();
       }
       System.out.println();
       for(int i=0;i<columns;i++)
       {
         for(int j=0;j<rows;j++)
         {
             System.out.print(answerGrid[i][j]+" ");
         }
         System.out.println();
       }  
       String s= scn.next();
       play();
    }
    
    
    private void play()// the actual game coding
    {
        String gridDisplay[][]=new String [rows+2][columns+2];// the 2D matrix which is diplayed
        for(int i=0;i<rows+2;i++)// fills all non-playing areas with line numbers and seperators 
        {
           for(int j=0;j<columns+2;j++)
           {
             gridDisplay[i][j]=" ";
             if(j==1&&i==columns-1)
             {
                 gridDisplay[i][j]="|";
             }
             else if(j==0&&i!=columns)
             {
                 gridDisplay[i][j]=String.valueOf(i+1);
             }
             else if(i==columns&&j!=0&&j<rows+1)
             {
                 if(j==1)
                 {
                     gridDisplay[i][j]="    "+String.valueOf(j);
                 }
                 else
                 {
                     gridDisplay[i][j]=""+String.valueOf(j);
                 }
             }
             else if(j==1)
             {
                 gridDisplay[i][j]="|";
             }
             else if(i==rows-1&&j<columns+2)
             {
                 gridDisplay[i][j]="_";
             }
           }
        }  
        boolean prize=true;
        int r=0;
        int c=0;
        for(int k=0;k<=((columns*rows)-bombs);k++)//makes the grid print multiple times
        {
            System.out.print("\f"); //clears the screen, which was cluttered up during alpha testing
            for(int i=0;i<columns+1;i++)//diplays the screen
            {
                for(int j=0;j<rows+2;j++)
                {
                    if(j!=rows)
                    System.out.print(gridDisplay[i][j]+"   ");
                    else
                    System.out.print(gridDisplay[i][j]+"  ");
                }
                System.out.println("\n");
            }
            System.out.println(columns*rows-k-bombs+" spaces remaining");//to show how many non-mine spaces are remaining(requested in beta testing)
            System.out.print("Enter the column number:");
            c=scn.nextInt();
            System.out.print("Enter the row number:");
            r=scn.nextInt();
            if(c>columns||r>rows||c<=0||r<=0)//if the player gives invalid co-ordinates
            {
                System.out.println("Invalid co-ordinates");
                try
                {
                    Thread.sleep(1750);
                }
                catch(InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
                k--;
            }
            else if(grid[r-1][c-1]==true)//when the player hits a mine
            {
                System.out.print("\f");
                System.out.println("     "+(char)61613);
                System.out.println("  "+(char)61613+(char)61613+(char)61613+(char)61613+(char)61613);
                System.out.println(" "+(char)61613+(char)61613+(char)61613+(char)61517+(char)61613+(char)61613+(char)61613);
                System.out.println("  "+(char)61613+(char)61613+(char)61613+(char)61613+(char)61613);
                System.out.println("     "+(char)61613);
                System.out.println((char)61517+"KABOOM!!"+(char)61517);
                System.out.println((char)61518+"You lost."+(char)61518);
                System.out.println("Better luck next time!");
                losses++;
                prize=false;
                break;
            }        
            else //to show the number of mines next to that space from the next time onwards if it is not a mine
            {
                gridDisplay[r-1][c+1]=String.valueOf(answerGrid[r-1][c-1]);
            }
        }  
        if(prize)//if the player fills every non-mine space
        {
            System.out.println("\f");
            System.out.println((char)61591+" Congratulations !!"+(char)61590);
            System.out.println("You won!!"+(char)61505);
            wins++;
        }
    }
}


    
    



    


    

        

