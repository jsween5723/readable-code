package cleancode.minesweeper.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.minesweeper.cell.Cell;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * 1. 게임레벨을 인자로 받아 Cell 배열을 초기화 할 수 있다.
 * 1-1. 게임레벨을 인자로 받아 Cell 배열에 게임레벨의 지뢰개수만큼 LandMineCell을 할당할 수 있다.
 * 1-2. 나머지는 NormalCell을 할당하며, 주변 셀의 LandMineCell 수를 가진다.
 * 2. 좌표를 입력받아 Cell을 open 할 수 있다.
 * 2-1. 지뢰라면 모든 셀을 열고 게임을 끝낸다.
 * 2-2. 지뢰가 아니라면 주변 Cell 열기가 가능할 경우 함께 연다.
 * 3. 문자열로 전환 시 level을 기반으로 행, 열 식별자를 출력하고 각 Cell을 좌표에 맞게 출력한다
 * 4. 클리어 여부를 판단할 수 있다.
 */
public class Board {
    public static final int[][] DELTAS = new int[][]{{-1, -1}, {-1, 0}, {-1, +1}, {0, -1}, {0, +1}, {1, -1}, {1, 0}, {1, +1}};
    private final Cell[][] cells;
    final BoardConfig config;

    private Board(Cell[][] cells, BoardConfig config) {
        this.cells = cells;
        this.config = config;
        initNormalCell();
        initMineCell();
    }

    private void initNormalCell() {
        for (int row = 0; row < config.rowCount(); row++) {
            for (int column = 0; column < config.columnCount(); column++) {
                Coordinate coordinate = new Coordinate(row, column);
                assignCell(coordinate, Cell.normalCell());
            }
        }
    }

    private void initMineCell() {
        for (int i = 0; i < config.mineCount(); i++) {
            Coordinate coordinate = Coordinate.random(config);
            while (cells[coordinate.row()][coordinate.column()].isLandMine()) {
                coordinate = Coordinate.random(config);
            }
            cells[coordinate.row()][coordinate.column()] = Cell.mineCell();
        }
    }


    static public Board withConfig(BoardConfig level) {
        Cell[][] cells = new Cell[level.rowCount()][level.columnCount()];
        return new Board(cells, level);
    }


    public void open(Coordinate coordinate) {
        Cell cell = get(coordinate);
        if (cell.isLandMine()) {
            openAllCells();
        } else {
            openWithAround(coordinate);
        }
    }

    private void openAllCells() {
        Arrays.stream(cells).flatMap(Arrays::stream).forEach(Cell::open);
    }


    private void openWithAround(Coordinate firstTarget) {
        Deque<Coordinate> targets = new ArrayDeque<>();
        targets.push(firstTarget);
        while (!targets.isEmpty()) {
            Coordinate coordinate = targets.pop();
            get(coordinate).open();
            if (cantAutoOpenAround(coordinate)) {
                return;
            }
            for (int[] delta : DELTAS) {
                Coordinate targetCoordinate = new Coordinate(firstTarget.row() + delta[0], firstTarget.column() + delta[1]);
                if (isNotOver(targetCoordinate)) {
                    Cell cell = get(targetCoordinate);
                    if (cell.cantAutoOpen()) {
                        continue;
                    }
                    targets.push(targetCoordinate);
                }
            }
        }
    }

    private boolean isNotOver(Coordinate targetCoordinates) {
        return targetCoordinates.isNotMinus() && config.isNotOver(targetCoordinates);
    }

    boolean cantAutoOpenAround(Coordinate target) {
        return countAroundLandMine(target) > 0 || get(target).cantAutoOpenAroundThis();
    }

    Cell get(Coordinate coordinate) {
        return cells[coordinate.row()][coordinate.column()];
    }

    private void assignCell(Coordinate coordinate, Cell cell) {
        cells[coordinate.row()][coordinate.column()] = cell;
    }

    public boolean isCleared() {
        return Arrays.stream(cells).flatMap(Arrays::stream).allMatch(Cell::isCleared);
    }

    public boolean isAllOpened() {
        return Arrays.stream(cells).flatMap(Arrays::stream).allMatch(Cell::isOpened);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(config.toColumnIdentifierString()).append("\n");
        for (int row = 0; row < config.rowCount(); row++) {
            sb.append(String.format("%" + config.countRowIdentifierMaxSpace() + "d", row + 1));
            for (int column = 0; column < config.columnCount(); column++) {
                Coordinate coordinate = new Coordinate(row, column);
                sb.append(cellToString(coordinate)).append(" ");
            }
        }
        return sb.toString();
    }

    private String cellToString(Coordinate coordinate) {
        Cell cell = get(coordinate);
        if (cell.isLandMine()) {
            return cell.toString();
        }
        int count = countAroundLandMine(coordinate);
        if (count == 0) return cell.toString();
        return count + "";
    }

    private int countAroundLandMine(Coordinate coordinate) {
        int count = 0;
        for (int[] delta : DELTAS) {
            Coordinate targetCoordinates = new Coordinate(coordinate.row() + delta[0], coordinate.column() + delta[1]);
            if (targetCoordinates.isNotMinus() && config.isNotOver(targetCoordinates) && get(targetCoordinates).isLandMine()) {
                count++;
            }
        }
        return count;
    }
}
