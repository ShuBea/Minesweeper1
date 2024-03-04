import java.io.*;

public class Minesweeper {
    private Board board;
    private StreamTokenizer tok;
    private boolean done, quit, win;
    private int lastCell;

    public Minesweeper (int width, int height, int mines) {

        board = new TextBoard(width, height, mines);
        tok = new StreamTokenizer(new InputStreamReader(System.in));
        done = win = quit = false;

    }

    public void play() throws IOException {
        long startTime = System.currentTimeMillis();
        while (!done) {
            board.draw();
            System.out.print("Command: ");
            System.out.flush();
            tok.nextToken();

            switch (tok.ttype) {
                case StreamTokenizer.TT_WORD:
                    doCommand();
                    break;
                case StreamTokenizer.TT_EOL:
                    continue;
                case StreamTokenizer.TT_EOF:
                    done=quit=true;
                    break;

            }

            if (board.getUnknown() == board.getMines()) {
                done = win = true;
            }
            else if (lastCell == Board.MINE) {
                done = true;
            }

            System.in.skip(System.in.available());
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                board.reveal(i, j);
            }
        }

        board.draw();
        if (win) {
            System.out.println("Congratulations!");
        }
        else if (!quit) {
            System.out.println("Bad luck");
        }
        System.out.println("Total game time: "+(elapsedTime/1000)+" seconds");
    }

    private void doCommand() throws IOException {
        int x, y;
        if (tok.sval.equals("reveal")) {
            tok.nextToken();
            x = (int)tok.nval;
            tok.nextToken();
            y = (int)tok.nval;
            lastCell = board.reveal(x, y);
            if (lastCell == 0) {
                board.revealMore(x, y);
            }
        }
        else if (tok.sval.equals("mark")) {
            tok.nextToken();
            x = (int)tok.nval;
            tok.nextToken();
            y = (int)tok.nval;
            board.mark(x, y);
        }
        else if (tok.sval.equals("unmark")) {
            tok.nextToken();
            x = (int)tok.nval;
            tok.nextToken();
            y = (int)tok.nval;
            board.unmark(x, y);
        }
        else if (tok.sval.equals("quit")) {
            quit = done = true;
        }
    }

    public static void main(String[] args) throws IOException {

        Minesweeper game;
            int width = 10;
            int height = 10;
            int mines = 5;
            game = new Minesweeper(width, height, mines);
            game.play();

    }




}
