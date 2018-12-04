package alhassan.minesweeper.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
public class Cell {

    private CellType type;
    private CellFlag flag;
    private boolean hidden;

    public Cell(CellType type) {
        this(type, CellFlag.None, true);
    }

    private Cell(CellType type, CellFlag flag, boolean hidden) {
        this.type = type;
        this.flag = flag;
        this.hidden = hidden;
    }

    public Cell cycleFlag() {
        return new Cell(this.type, this.flag.cycle(), this.hidden);
    }

    public Cell withHidden() {
        return new Cell(this.type, this.flag.cycle(), false);
    }
}
