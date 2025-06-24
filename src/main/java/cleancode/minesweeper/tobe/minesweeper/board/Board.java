package cleancode.minesweeper.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.minesweeper.cell.Cell;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * 1. 게임레벨을 인자로 받아 Cell 배열을 초기화 할 수 있다. 1-1. 게임레벨을 인자로 받아 Cell 배열에 게임레벨의 지뢰개수만큼 LandMineCell을 할당할 수
 * 있다. 1-2. 나머지는 NormalCell을 할당하며, 주변 셀의 LandMineCell 수를 가진다. 2. 좌표를 입력받아 Cell을 open 할 수 있다. 2-1.
 * 지뢰라면 모든 셀을 열고 게임을 끝낸다. 2-2. 지뢰가 아니라면 주변 Cell 열기가 가능할 경우 함께 연다. 3. 문자열로 전환 시 level을 기반으로 행, 열 식별자를
 * 출력하고 각 Cell을 좌표에 맞게 출력한다 4. 클리어 여부를 판단할 수 있다.
 */
public class Board {

    public static final int[][] DELTAS = new int[][]{{-1, -1}, {-1, 0}, {-1, +1}, {0, -1}, {0, +1},
        {1, -1}, {1, 0}, {1, +1}};
    private final Cell[][] cells;
    private final int rowCount;
    private final int columnCount;
    private final int mineCount;
    private final BoardStringGenerator stringGenerator = new BoardStringGenerator();

    Board(int rowCount, int columnCount, int mineCount) {
        this.mineCount = mineCount;
        this.cells = new Cell[rowCount][columnCount];
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        initNormalCell();
        initMineCell();
    }

    static public Board withConfig(BoardConfig config) {
        return new Board(config.rowCount(), config.columnCount(), config.mineCount());
    }


    public void open(Coordinate coordinate) {
        Cell cell = get(coordinate);
        if (cell.cantOpen()) {
            return;
        }
        if (cell.isLandMine()) {
            openAllCells();
        } else {
            openWithAround(coordinate);
        }
    }

    public void toggleFlag(Coordinate coordinate) {
        Cell cell = get(coordinate);
        cell.toggleFlag();
    }


    public boolean isCleared() {
        return Arrays.stream(cells).flatMap(Arrays::stream).allMatch(Cell::isCleared);
    }

    public boolean isMineOpened() {
        return Arrays.stream(cells).flatMap(Arrays::stream)
            .anyMatch(cell -> cell.isLandMine() && cell.isOpened());
    }
    public void validateCoordinates(Coordinate coordinate) {
        if (isNotOver(coordinate)) return;
        throw new IllegalArgumentException("잘못된 번호를 선택하셨습니다.");
    }

    private void initNormalCell() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Coordinate coordinate = new Coordinate(row, column);
                assignCell(coordinate, Cell.normalCell());
            }
        }
    }

    private boolean isNotOver(Coordinate coordinate) {
        return coordinate.row() < rowCount && coordinate.column() < columnCount
            && coordinate.isNotMinus();
    }

    private void initMineCell() {
        for (int i = 0; i < mineCount; i++) {
            Coordinate coordinate = Coordinate.random(rowCount, columnCount);
            while (cells[coordinate.row()][coordinate.column()].isLandMine()) {
                coordinate = Coordinate.random(rowCount, columnCount);
            }
            cells[coordinate.row()][coordinate.column()] = Cell.mineCell();
        }
    }

    //입력받는 좌표값의 검증의 경우 최대치는 Board의 크기가 저장된 Config의 역할이고
//    Config은 Board가 들고 있으므로 Board에서 진행합니다.


    private void openAllCells() {
        Arrays.stream(cells).flatMap(Arrays::stream).forEach(Cell::open);
    }

    private void openWithAround(Coordinate firstTarget) {
        Deque<Coordinate> targets = new ArrayDeque<>();
        targets.push(firstTarget);
        while (!targets.isEmpty()) {
            Coordinate coordinate = targets.poll();
            Cell targetCell = get(coordinate);
            targetCell.open();
            if (cantAutoOpenAround(coordinate, targetCell)) {
                continue;
            }
            for (int[] delta : DELTAS) {
                Coordinate targetCoordinate = new Coordinate(coordinate.row() + delta[0],
                    coordinate.column() + delta[1]);
                if (isOver(targetCoordinate)) {
                    continue;
                }
                Cell cell = get(targetCoordinate);
                if (cell.cantAutoOpen()) {
                    continue;
                }
                targets.push(targetCoordinate);
            }
        }
    }

    private boolean isOver(Coordinate targetCoordinate) {
        return !isNotOver(targetCoordinate);
    }


    private boolean cantAutoOpenAround(Coordinate target, Cell cell) {
        return countAroundLandMine(target, cell) > 0;
    }

    private int countAroundLandMine(Coordinate coordinate, Cell cell) {
        int count = 0;
        for (int[] delta : DELTAS) {
            Coordinate targetCoordinates = new Coordinate(coordinate.row() + delta[0],
                coordinate.column() + delta[1]);
            if (targetCoordinates.isNotMinus() && isNotOver(targetCoordinates) && cell.isLandMine()) {
                count++;
            }
        }
        return count;
    }


    Cell get(Coordinate coordinate) {
        return cells[coordinate.row()][coordinate.column()];
    }

    private void assignCell(Coordinate coordinate, Cell cell) {
        cells[coordinate.row()][coordinate.column()] = cell;
    }

    @Override
    public String toString() {
        return stringGenerator.generate();
    }

    class BoardStringGenerator {

        public String generate() {
            StringBuilder sb = new StringBuilder();
            appendColumnDefinition(sb);
            for (int row = 0; row < rowCount; row++) {
                appendRowNumber(sb, row);
                for (int column = 0; column < columnCount; column++) {
                    Coordinate coordinate = new Coordinate(row, column);
                    appendCell(sb, coordinate);
                }
                sb.append("\n");
            }
            return sb.toString();
        }

        private void appendCell(StringBuilder sb, Coordinate coordinate) {
            sb.append(cellToString(coordinate)).append(" ");
        }

        private void appendRowNumber(StringBuilder sb, int row) {
            sb.append(String.format("%" + countRowIdentifierMaxSpace() + "d", row + 1)).append(" ");
        }

        private void appendColumnDefinition(StringBuilder sb) {
            sb.append(toColumnIdentifierString()).append("\n");
        }

        private String toColumnIdentifierString() {
            StringBuilder result = new StringBuilder();
            result.append(" ".repeat(Math.max(0, countRowIdentifierMaxSpace() + 1)));
            for (int i = 0; i < columnCount; i++) {
                result.append(Character.toString('A' + i));
                result.append(' ');
            }
            return result.toString();
        }


        private int countRowIdentifierMaxSpace() {
            int count = 0;
            int target = rowCount;
            while (target > 0) {
                count++;
                target /= 10;
            }
            return count;
        }

        private String cellToString(Coordinate coordinate) {
            Cell cell = get(coordinate);
            if (cell.isLandMine()) {
                return cell.toString();
            }
            if (cell.isClosed()) {
                return cell.toString();
            }
            int count = countAroundLandMine(coordinate, cell);
            if (count == 0) {
                return cell.toString();
            }
            return count + "";
        }
    }
}

