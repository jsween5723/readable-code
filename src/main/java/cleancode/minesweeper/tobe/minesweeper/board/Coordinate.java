package cleancode.minesweeper.tobe.minesweeper.board;

import java.util.Random;

public record Coordinate(int row, int column) {
    boolean isNotMinus() {
        return row >= 0 && column >= 0;
    }

    static public Coordinate random(BoardConfig boardConfig) {
        int col = new Random().nextInt(boardConfig.columnCount());
        int row = new Random().nextInt(boardConfig.rowCount());
        return new Coordinate(row, col);
    }
}
