import alhassan.minesweeper.model.Board;
import alhassan.minesweeper.model.CellFlag;
import com.google.common.collect.Range;
import io.vavr.control.Option;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class testBoard {

    @Test
    public void createBoard() {
        Board board = new Board(10, 10);
    }

    @Test
    public void test_getCell() {
        Board board = new Board(10, 10);
        assertTrue(board.getCell(0, 0).isDefined());
    }

    @Test
    public void test_getCell_getEmptyCell() {
        Board board = new Board(10, 10);
        assertEquals(Option.none(), board.getCell(0, 50000));
    }

    @Test
    public void test_flagBoard_for_all_State() {
        Board board = new Board(10, 10);
        assertEquals(CellFlag.None, board.getCell(0,0).get().getFlag());
        assertEquals(CellFlag.Flagged, board.flagCell(0,0).getCell(0,0).get().getFlag());
        assertEquals(CellFlag.Questioned, board.flagCell(0,0).getCell(0,0).get().getFlag().cycle());
    }

    @Test
    public void test_bombCount() {
        Board board = new Board(10, 10);
        assertEquals(10, board.bombCount(), Range.closed(5, 20).upperEndpoint());
    }


    /*
        Test exceptions
     */

    @Test(expected = IllegalArgumentException.class)
    public void Board_WithNegative_xDim() {
        Board board = new Board(-10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Board_WithNegative_yDim() {
        Board board = new Board(10, -10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Board_WithNegative_Dims() {
        Board board = new Board(-2, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Board_With_Zero_xDims() {
        Board board = new Board(0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Board_With_Zero_yDims() {
        Board board = new Board(10, 0);
    }
}
