package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.minesweeper.MineSweeperGameLevel;
import cleancode.minesweeper.tobe.minesweeper.MinesweeperGame;

public class GameApplication {
    public static void main(String[] args) {
        MinesweeperGame game = new MinesweeperGame(MineSweeperGameLevel.HARD);
        game.start();
    }
}
