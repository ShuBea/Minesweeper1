import java.util.Scanner;
public class Minesweeper {
    Board board;
    boolean done;
    boolean quit;
    boolean win;
    int selectedCell;
    Scanner read = new Scanner(System.in);

    //creates a new game - constructor for the minesweeper class
    public Minesweeper(int width, int height, int mines) {
        board = new Board(width, height, mines);
        win = false;
        done = false;
        quit = false;
    }

    //plays the game and doesn't stop until quit or win or lose (aka done)
    public void play() {
        System.out.print("Welcome to MineSweeper! \n ");
        //starts timer
        long startTime = System.currentTimeMillis();
        //whilst not done...only done if quit
        while (!done) {
            board.draw();
            System.out.print("reveal or quit? ");
            String answer = read.nextLine();
            switch (answer) {
                case "reveal":
                    doCommand();
                    break;
                case "quit":
                    quit = true;
                    done = true;
                    break;
                default:
                    System.out.println("That wasn't a choice, let's try that again.");
            }


            //after every go checks to see if won or lost which redefines done or not done
            if (board.getUnknown() == board.getMines()) {
                done = win = true;
            } else if (selectedCell == board.MINE) {
                done = true;
            }
        }
        //timer ends
        long elapsedTime = System.currentTimeMillis() - startTime;

        //reveal everything when done
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                board.reveal(i, j);
            }
        }

        // final messages

        if (win) {
            board.draw();
            System.out.println("Winner!");
        } else if (quit) {
            board.draw();
            System.out.println("Have a go another time!");
        } else if (!quit) {
            board.draw();
            System.out.println("Loser!");
        }
        System.out.println(" ");
        System.out.println("Total game time: " + (elapsedTime / 1000) + " seconds");
    }


    //this does the reveal cell
    public void doCommand() {
        int x, y;

        int yCoord;
        do {
            System.out.print("Please write a positive x coordinate within the grid: ");
            while (!read.hasNextInt()) { //code prints 'That's not a positive integer' whilst the input is not an integer
                System.out.println("That's not a positive integer within the grid size, try again...");
                read.next();
            }
            yCoord = read.nextInt(); //defines y as the input if it is an integer
        } while (yCoord < 0 || yCoord >= board.getWidth()); //when it is negative or too big it will go back to the start
        y = yCoord;


        int xCoord;
        do {
            System.out.print("Please write a positive y coordinate within the grid: ");
            while (!read.hasNextInt()) { //code prints 'That's not a positive integer' whilst the input is not an integer
                System.out.println("That's not a positive integer within the grid size, try again...");
                read.next();
            }
            xCoord = read.nextInt(); //defines y as the input if it is an integer
        } while (xCoord < 0 || xCoord >= board.getWidth()); //when it is negative or too big it will go back to the start
        x = xCoord;


        selectedCell = board.reveal(x, y);
        if (selectedCell == 0) {
            board.revealMore(x, y);
        }
    }

    public static void main(String[] args) {

        //int width = 10;
        //int height = 10;
        //int mines = 15;

        Scanner read = new Scanner(System.in);
        int width; //defining width as an int
        int height;
        int mines;



            do {
                System.out.println("The board will be square. What width would you like the board to be?");
                while (!read.hasNextInt()) { //code prints 'That's not an integer' whilst not an integer
                    System.out.println("That's not a number!");
                    read.next();
                }
                width = height = read.nextInt(); //defines width and height as the input if it is a positive integer
            } while (width <= 0); //when it is negative it will go back to the start




            do {
                System.out.println("How many mines would you like?");
                while (!read.hasNextInt()) { //code prints 'That's not an integer' whilst not an integer
                    System.out.println("That's not a number!");
                    read.next();
                }
                mines = read.nextInt(); //defines x as the input if it is a float
            } while (mines <= 0); //when it is negative it will go back to the start



        Minesweeper game;
        game = new Minesweeper(width, height, mines);
        game.play();

    }
}





