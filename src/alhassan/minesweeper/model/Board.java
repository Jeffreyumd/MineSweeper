package alhassan.minesweeper.model;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Board {
    private final Vector<Vector<Cell>> rows;
    private static final double BOMB_FRACTION = 0.1;

    public Board(int xDim, int yDim) {
        if (xDim <= 0) {
            throw new IllegalArgumentException("The x dimension must be positive.");
        }
        if (yDim <= 0) {
            throw new IllegalArgumentException("The y dimension must be positive.");
        }

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

    public Option<Cell> getCell(int x, int y) {
        if (this.inBounds(x, y)) {
            return Option.of(this.rows.get(y).get(x));
        }
        else {
            return Option.none();
        }
    }

    private Board(Vector<Vector<Cell>> rows) {
        this.rows = rows;
    }

    private boolean inBounds(int x, int y) {
        return y < this.getHeight() && y >= 0 && x < this.getWidth() && x >= 0;
    }

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
        }
        else {
            return 0;
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vector<Cell> col : this.rows.reverse()) {
            for (Cell cell : col) {
                switch (cell.getType()) {
                    case Bomb:
                        sb.append("\uD83D\uDCA3");
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
