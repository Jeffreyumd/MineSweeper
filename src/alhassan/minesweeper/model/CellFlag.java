package alhassan.minesweeper.model;

public enum CellFlag {
    None,
    Flagged,
    Questioned;

    public CellFlag cycle() {
        switch (this) {
            case None:
                return CellFlag.Flagged;
            case Flagged:
                return CellFlag.Questioned;
            case Questioned:
                return CellFlag.None;
            default:
                throw new RuntimeException("Unhandled flag type.");
        }
    }

}
