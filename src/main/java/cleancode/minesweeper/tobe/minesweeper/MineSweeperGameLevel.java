package cleancode.minesweeper.tobe.minesweeper;

import cleancode.minesweeper.tobe.minesweeper.board.BoardConfig;

public enum MineSweeperGameLevel {
    BEGINNER(new BoardConfig(4, 4, 5)),
    MIDDLE(new BoardConfig(8, 8, 20)),
    HARD(new BoardConfig(20, 20, 3));
    public final BoardConfig boardConfig;

    MineSweeperGameLevel(BoardConfig boardConfig) {
        this.boardConfig = boardConfig;
    }
}
