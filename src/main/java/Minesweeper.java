import java.util.Scanner;
public class Minesweeper {
    Board board;
    boolean done, quit, win;
    int selectedCell;
    Scanner read = new Scanner(System.in);

    //creates a new game - constructor for the minesweeper class
    public Minesweeper(int width, int height, int mines) {
        board = new Board(width, height, mines);
        win = false;
        done = false;
        quit=false;
    }

    //plays the game and doesn't stop until quit or win or lose (aka done)
    public void play(){
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
                    done =  true;
                    break;
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
        for(int i = 0; i<board.getWidth();i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                board.reveal(i, j);
            }
        }
        board.draw();

        // final messages
        if(win) {
            System.out.println("Winner!");
        }
        else if(!quit) {
            System.out.println("Loser");
        }
        System.out.println("Total game time: "+(elapsedTime/1000)+" seconds");
    }


    //this is for the reveal cell
    public void doCommand() {
        int x, y;
        System.out.print("x coordinate: ");
        int xCoord = read.nextInt();
        x = xCoord -1;
        System.out.print("y coordinate: ");
        int yCoord = read.nextInt();
        y = yCoord-1;
        selectedCell = board.reveal(x, y);
        if (selectedCell == 0) {
            board.revealMore(x, y);
        }
    }

    public static void main(String[] args){

        int width = 10;
        int height = 10;
        int mines = 10;

        Minesweeper game;
        game = new Minesweeper(width, height, mines);
        game.play();

    }


}

