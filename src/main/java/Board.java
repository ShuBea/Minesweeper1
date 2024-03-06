import java.util.Random;

public class Board {
    // instance variables
    int width, height;
    int numMines;
    int numUnknown;
    boolean[][] mines;
    int[][] board;
    public static final int UNKNOWN = -1;
    public static final int MINE = -2;


    //create a new game board -  this method is called during the minesweeper method
    public Board(int width, int height, int numMines) {
        //set up the size of the games board, the number os mines, the number of unknown cells,
        // the game board array and mines array
        this.width = width;
        this.height = height;
        this.numMines = numMines;
        this.numUnknown = width * height;
        mines = new boolean[width][height];
        board = new int[width][height];

        // Clear the game board before playing
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //start with no mine in all the cells
                mines[i][j] = false;
                //start with all the cells covered
                board[i][j] = UNKNOWN;
            }
        }

        //Randomly place mines in the mines array
        int count = 0;
        Random rand = new Random();
        while (count < numMines) {
            int randomRow = rand.nextInt(width-1);
            int randomCol = rand.nextInt(height-1);
            if (!mines[randomRow][randomCol]) {
                    mines[randomRow][randomCol] = true;
                    count++;
            }


        }

    }


    //reveal method
    public int reveal(int x, int y) {
        switch (board[x][y]) {
            case UNKNOWN:
                // if the cell is unknown, it decrements the counter of unknown cells
                // it also checks the element in the mines array
                numUnknown--;
                if (mines[x][y]) {
                    board[x][y] = MINE;
                } else {
                    // Looks at how many mines next door
                    board[x][y] = nearbyMines(x, y);
                }
                break;
        }
        // Return the revealed value
        return board[x][y];
    }


// REVEAL MORE METHOD
// If a neighbouring cell is unknown and does not have a mine it is revealed
// and then if that revealed cell is a zero then the method is called recursively on that cell too

    public void revealMore(int x, int y) {
        int minx, miny, maxx, maxy;
        // sets limits for edge of board
        minx = (x <= 0 ? 0 : x - 1);
        miny = (y <= 0 ? 0 : y - 1);
        maxx = (x >= width - 1 ? width : x + 2);
        maxy = (y >= height - 1 ? height : y + 2);

        // Loop surrounding cells
        // if there is no mine, and it is covered, then it will be revealed
        for (int i = minx; i < maxx; i++) {
            for (int j = miny; j < maxy; j++) {
                if (!mines[i][j] && board[i][j] == UNKNOWN) {
                    reveal(i, j);
                    //if the cell is 0 then it will do reveal more on itself
                    if (board[i][j] == 0) {
                        // Call itself recursively
                        revealMore(i, j);
                    }
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getMines() {
        return numMines;
    }
    public int getUnknown() {
        return numUnknown;
    }


    //nearby mines method - finds how many mines are in the neighbouring 8 cells
    public int nearbyMines(int x, int y) {
        int res = 0;
        int minx, miny, maxx, maxy;
        // sets limits for edge of board
        minx = (x <= 0 ? 0 : x - 1);
        miny = (y <= 0 ? 0 : y - 1);
        maxx = (x >= width - 1 ? width : x + 2);
        maxy = (y >= height - 1 ? height : y + 2);

        //loops all neighbouring cells and checks for mines
        for (int i = minx; i < maxx; i++) {
            for (int j = miny; j < maxy; j++) {
                if (mines[i][j]) {
                    res++;
                }
            }
        }
        // returns the number of mines in the surrounding 8 cells
        return res;
    }


    // Draws board for the user
    public void draw() {
        int rows = height; // Number of rows
        int cols = width; // Number of columns

        // Print column numbers
        System.out.print("  ");
        for (int j = 0; j < cols; j++) {
            System.out.print(String.format("%02d", j) + " ");
        }
        System.out.println();

        // Print rows with row numbers
        for (int i = 0; i < rows; i++) {
            System.out.print(String.format("%02d", i) + " ");
            for (int j = 0; j < cols; j++) {


                switch (board[i][j]) {
                    case UNKNOWN:
                        System.out.print("-  ");
                        break;
                    case MINE:
                        System.out.print("*  ");
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        System.out.print( board[i][j] + "  ");
                        break;
                    case 0:
                        System.out.print("0" + "  ");
                        break;
                }
            }

            System.out.println();
        }
    }


}
