package cleancode.minesweeper.tobe.minesweeper.board;

public class CoordinateFinder {
    public Coordinate findMineCoordinate(Board board) {
        for (int row = 0; row < board.config.rowCount(); row++) {
            for (int col = 0; col < board.config.columnCount(); col++) {
                Coordinate coordinate = new Coordinate(row, col);
                if (board.get(coordinate).isLandMine()) {
                    return coordinate;
                }
            }
        }
        throw new IllegalStateException("지뢰 개수가 0입니다.");
    }

    public Coordinate findCanAutoOpenAroundCellCoordinate(Board board) {
        for (int row = 0; row < board.config.rowCount(); row++) {
            for (int col = 0; col < board.config.columnCount(); col++) {
                Coordinate coordinate = new Coordinate(row, col);
                if (board.cantAutoOpenAround(coordinate)) continue;
                return coordinate;
            }
        }
        throw new IllegalStateException();
    }

}
