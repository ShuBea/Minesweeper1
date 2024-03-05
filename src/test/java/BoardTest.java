import org.junit.jupiter.api.*;


public class BoardTest {
    @Test
    public void testBoardInitialisedCovered(){
       Board testBoard = new Board(5, 5, 25);
       Assertions.assertEquals(25, testBoard.getMines() ,"Mine is not present where it should be");
    }
}


