package alhassan.minesweeper.model;

import io.vavr.control.Option;

/**
 * Represents a game board for minesweeper
 */
public interface IBoard {

    /**
     * Return a cell in the board from the given coordinates,
     * return an vavr Option.none is the position is out of bounce.
     *
     * @param x The x position of the cell
     * @param y The y position of the cell
     * @return The cell at the given position
     */
    Option<Cell> getCell(int x, int y);

    /**
     * Return a new Board with the new Flagged cell. If the cell is already flagged, change it to a question mark.
     * If the cell is a question mark, change it to the normal cell.
     *
     * @param x The x position of the cell
     * @param y The y position of the cell
     * @return The board with the flagged updated flagged cell
     */
    Board flagCell(int x, int y);

    /**
     * Return a new Board with the reveled cell. If the cell has already being clicked do nothing.
     * @param x The x position of the cell
     * @param y The y position of the cell
     * @return The board with reveled cell
     */
    Board clickCell(int x, int y);

    /**
     * Count the number of surrounding bombs in each neighboring cell for the given coordinate.
     * add one if the is a bomb and add 0 if the is no bomb or it a invalid position.
     *
     * @param x The x position of the cell
     * @param y The y position of the cell
     * @return The number of bombs surrounding the given cell
     */
    int surroundingBombs(int x, int y);
}
