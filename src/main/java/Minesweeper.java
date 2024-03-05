import java.util.Scanner;

public class Minesweeper {
    Board board;
    boolean done, quit, win;
    int selectedCell;
    Scanner read = new Scanner(System.in);

    public Minesweeper(int width, int height, int mines) {
        board = new Board(width, height, mines);
        quit = false;
        win = false;
        done = false;
    }

    public void play(){
        long startTime = System.currentTimeMillis();
        while (!done) {
            board.draw();
            System.out.print("Command: ");
            String answer = read.nextLine();
            switch (answer) {
                case "reveal":
                    doCommand();
                    break;
                case "quit":
                    done = quit =  true;
                    break;
            }

            if (board.getUnknown() == board.getMines()) {
                done = win = true;
            } else if (selectedCell == board.MINE) {
                done = true;
            }
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        //reveal everything when finished
        for(int i = 0; i<board.getWidth();i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                board.reveal(i, j);
            }
        }
        board.draw();

        if(win) {
            System.out.println("Winner!");
        }
        else if(!quit) {
            System.out.println("Loser");
        }
        System.out.println("Total game time: "+(elapsedTime/1000)+" seconds");
    }



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

        Minesweeper game;
        int width = 10;
        int height = 10;
        int mines = 10;
        game = new Minesweeper(width, height, mines);
        game.play();

    }


}

