package alhassan.minesweeper.model;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import lombok.EqualsAndHashCode;

import static com.google.common.base.Preconditions.checkArgument;

@EqualsAndHashCode
public class Board implements GameBoard {
    private final Vector<Vector<Cell>> rows;
    private static final double BOMB_FRACTION = 0.1;
    private int xDim;
    private int yDim;

    public Board(int xDim, int yDim) {

        checkArgument(xDim > 0,
                "The x dimension must be positive.");
        checkArgument(yDim > 0,
                "The y dimension must be positive.");

        this.xDim = xDim;
        this.yDim = yDim;

        Vector<Vector<Cell>> cells = Vector.empty();

        for (int y = 0; y < yDim; y++) {
            Vector<Cell> row = Vector.empty();
            for (int x = 0; x < xDim; x++) {
                row = row.append(this.generateCell());
            }
            cells = cells.append(row);
        }

        this.rows = cells;
    }


    public int getxDim() { return this.xDim; }

    public int getyDim() { return this.yDim; }

    @Override
    public Option<Cell> getCell(int x, int y) {
        if (this.inBounds(x, y)) {
            return Option.of(this.rows.get(y).get(x));
        } else {
            return Option.none();
        }
    }

    private Board(Vector<Vector<Cell>> rows) {
        this.rows = rows;
    }

    /**
     * Checks if the given coordinates are in the bounds.
     * @param x The given x position
     * @param y The given y position
     * @return Boolean if the coordinates are in the bounds
     */
    private boolean inBounds(int x, int y) {
        return y < this.getHeight() && y >= 0 && x < this.getWidth() && x >= 0;
    }

    @Override
    public Board flagCell(int x, int y) {
        if (!inBounds(x, y)) {
            return this;
        }
        Cell newCell = this.rows.get(y).get(x).cycleFlag();
        return this.setCell(x, y, newCell);
    }

    private Board setCell(int x, int y, Cell cell) {
        return new Board(
                this.rows.update(y, row -> row.update(x, cell))
        );
    }

    private Cell generateCell() {
        return new Cell(this.shouldPlaceBomb() ? CellType.Bomb : CellType.Empty);
    }

    private boolean shouldPlaceBomb() {
        return Math.random() <= BOMB_FRACTION;
    }

    private int getHeight() {
        return this.rows.length();
    }

    private int getWidth() {
        return this.rows.get(0).length();
    }

    @Override
    public int surroundingBombs(int x, int y) {
        if (inBounds(x, y)) {
            Vector<Cell> cellsToCheck = Vector.of(
                    this.getCell(x, y + 1),
                    this.getCell(x, y - 1),
                    this.getCell(x - 1, y),
                    this.getCell(x - 1, y - 1),
                    this.getCell(x - 1, y + 1),

                    this.getCell(x + 1, y),
                    this.getCell(x + 1, y - 1),
                    this.getCell(x + 1, y + 1)
            ).filter(Option::isDefined).map(Option::get);

            int total = 0;
            for (Cell cellToCheck : cellsToCheck) {
                total += cellToCheck.getType() == CellType.Bomb ? 1 : 0;
            }
            return total;
        } else {
            return 0;
        }
    }


    public int BombCount() {
        int count = 0;
        for (int i = 0; i < getxDim(); i++) {
            for (int j = 0; j < getyDim(); j++) {
                if( rows.get(i).get(j).getType() == CellType.Bomb) {
                    count++;
                }
            }
        }
        return count;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vector<Cell> col : this.rows.reverse()) {
            for (Cell cell : col) {
                switch (cell.getType()) {
                    case Bomb:
                        sb.append("\uD83D\uDD25");
                        break;
                    case Empty:
                        sb.append("__");
                        break;
                    default:
                        throw new RuntimeException("Unhandled Cell type");
                }
            }
            sb.append('\n');
        }

        return sb.toString();
    }


}
