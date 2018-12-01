package alhassan.minesweeper;

import alhassan.minesweeper.model.Board;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(10,10);
        System.out.println(board);
        System.out.println(board.bombCount());
        System.out.println(board.getCell(0, 0));


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(board.surroundingBombs(i,j));
            }
            System.out.print(" ");
        }



    }
}
