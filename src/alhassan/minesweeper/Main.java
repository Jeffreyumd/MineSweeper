package alhassan.minesweeper;

import alhassan.minesweeper.model.Board;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(10,10);
        System.out.println(board.getCell(0, 0));
        System.out.println(board.flagCell(0, 0).getCell(0, 0));
    }
}
