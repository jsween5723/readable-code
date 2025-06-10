package cleancode.minesweeper.tobe.minesweeper.board;

public class CoordinateFinder {
    public Coordinate findMineCoordinate(Board board) {
        for (int row = 0; row < board.level.rowCount(); row++) {
            for (int col = 0; col < board.level.columnCount(); col++) {
                if (board.cells[row][col] instanceof LandMineCell) {
                    return new Coordinate(row, col);
                }
            }
        }
        throw new IllegalStateException("지뢰 개수가 0입니다.");
    }

    public Coordinate findNormalCoordinate(Board board) {
        for (int row = 0; row < board.level.rowCount(); row++) {
            for (int col = 0; col < board.level.columnCount(); col++) {
                if (board.cells[row][col] instanceof NormalCell) {
                    return new Coordinate(row, col);
                }
            }
        }
        throw new IllegalStateException("지뢰 개수가 0입니다.");
    }

}
