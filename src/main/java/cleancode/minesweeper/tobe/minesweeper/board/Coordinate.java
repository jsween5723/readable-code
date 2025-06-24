package cleancode.minesweeper.tobe.minesweeper.board;

import java.util.Random;

public record Coordinate(int row, int column) {
    public Coordinate {
        if (!isNotMinus()) throw new IllegalArgumentException("잘못된 번호를 선택하셨습니다.");
    }

    boolean isNotMinus() {
        return row >= 0 && column >= 0;
    }

    static public Coordinate random(int rowCount, int columnCount) {
        int col = new Random().nextInt(columnCount);
        int row = new Random().nextInt(rowCount);
        return new Coordinate(row, col);
    }
}
