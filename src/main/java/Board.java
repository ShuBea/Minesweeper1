import java.util.Random;
public abstract class Board {


    //instance variables
    protected int width, height;
    protected int numMines;
    protected int numMarked;
    protected int numUnknown;
    protected boolean[][] mines;
    protected int[][] board;
    public static final int UNKNOWN = -1;
    public static final int MARKED = -2;
    public static final int MINE = -3;


    //create a new game board
    public Board(int width, int height, int numMines) {

        // Initialise instance variables
        this.width = width;
        this.height = height;
        this.numMines = numMines;
        this.numMarked = 0;
        this.numUnknown = width * height;

        // Make arrays for game board and mines
        mines = new boolean[width][height];
        board = new int[width][height];

        // Clear the board
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //start with no mine in all the cells
                mines[i][j] = false;
                //start with all the cells covered
                board[i][j] = UNKNOWN;
            }
        }

        // Randomly place mines in the mines array.  The loop runs until numMines mines have
        // been placed. It tries again if the chosen cell already contains a mine.
        int cells = width * height;
        int temp = 0;
        Random rand = new Random();

        while (temp < numMines) {
            int cell = rand.nextInt();
            cell = (cell < 0 ? -cell : cell) % cells;
            if (!mines[cell % width][cell / width]) {
                mines[cell % width][cell / width] = true;
                temp++;
            }
        }




    }

    // PRINTS board for the user
    public abstract void draw();

    //reveals the cell (could be a mine or an integer) uses coordinate system
    //this only looks at cells which are still unknown
    public int reveal(int x, int y) {
        switch (board[x][y]) {
           // case MARKED:
                // If the cell was marked, it decrements the counter of marked cells
             //   numMarked--;
            case UNKNOWN:
                // if the cell was unknown, it decrements the counter of unknown cells
                // it also checks the element in the mines array
                numUnknown--;
                if (mines[x][y]) {
                    board[x][y] = MINE;
                } else {
                    // Looks at how many mines next door
                    board[x][y] = closeMines(x, y);
                }
                break;
        }
        // Return the revealed value
        return board[x][y];
    }

// REVEAL MORE METHOD
// If a neighbouring cell is unknown and does not have a mine it is revealed
// and then if that revealed cell is empty and has no neighbouring mines
// then the method is called recursively on that cell too

    public void revealMore(int x, int y) {
        int minx, miny, maxx, maxy;
        int result = 0;

        // Stops at edges of board
        minx = (x <= 0 ? 0 : x - 1);
        miny = (y <= 0 ? 0 : y - 1);
        maxx = (x >= width - 1 ? width : x + 2);
        maxy = (y >= height - 1 ? height : y + 2);

        // Loop over all surrounding cells
        for (int i = minx; i < maxx; i++) {
            for (int j = miny; j < maxy; j++) {
                if (!mines[i][j] && board[i][j] == UNKNOWN) {
                    reveal(i, j);
                    if (board[i][j] == 0) {
                        // Call itself recursively
                        revealMore(i, j);
                    }
                }
            }
        }
    }


    //MARK A CELL - i.e. FLAG A CELL
    public boolean mark(int x, int y) {
        if ((numMines - numMarked) > 0 && board[x][y] == UNKNOWN) {
            board[x][y] = MARKED;
            numMarked++;
            return true;
        } else {
            return false;
        }
    }

    //UNMARK A CELL

    public boolean unmark(int x, int y) {
        if (board[x][y] == MARKED) {
            board[x][y] = UNKNOWN;
            numMarked--;
            return true;
        } else {
            return false;
        }
    }

    // Return stuff about the game board, because they are all protected
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getMines() {
        return numMines;
    }
    public int getMarked() {
        return numMarked;
    }
    public int getUnknown() {
        return numUnknown;
    }

    private int closeMines(int x, int y) {
        int minx, miny, maxx, maxy;
        int result = 0;
        minx = (x <= 0 ? 0 : x - 1);
        miny = (y <= 0 ? 0 : y - 1);
        maxx = (x >= width - 1 ? width : x + 2);
        maxy = (y >= height - 1 ? height : y + 2);

        //check for mines
        for (int i = minx; i < maxx; i++) {
            for (int j = miny; j < maxy; j++) {
                if (mines[i][j]) {
                    result++;
                }
            }
        }
        return result;
    }


}
