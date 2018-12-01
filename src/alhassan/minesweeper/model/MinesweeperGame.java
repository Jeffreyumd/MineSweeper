package alhassan.minesweeper.model;

import java.time.Duration;

public class MinesweeperGame {

    private enum GameState {
        Playing,
        Win,
        Lose
    }

    private GameState gameState;
    private Duration timeElapsed;

    private Board board;

    public MinesweeperGame(GameState gameState) {
        this.gameState = gameState;
    }

    boolean isGameOver() {
        return false;
    }



}
