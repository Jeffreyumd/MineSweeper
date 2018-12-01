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

}
