package cleancode.minesweeper.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.minesweeper.MineSweeperGameLevel;
import cleancode.minesweeper.tobe.minesweeper.cell.Cell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 1. 게임레벨을 인자로 받아 Cell 배열을 초기화 할 수 있다.
 * 1-1. Cell 배열에 게임레벨의 지뢰개수만큼 LandMineCell을 할당할 수 있다.
 * 1-2. 나머지는 NormalCell을 할당하며, 주변 셀의 LandMineCell 수를 가진다.
 * 2. 좌표를 입력받아 Cell을 open 할 수 있다.
 * 2-1. 지뢰라면 모든 셀을 열고 게임을 끝낸다. (isCleared = false) (cells.forEach.isOpened = true)
 * 2-2. 지뢰가 아니라면 주변 Cell 열기가 가능할 경우 함께 연다.
 * 4. 모든 셀의 클리어 여부를 판단할 수 있다.
 */
class BoardTest {
    private final CoordinateFinder coordinateFinder = new CoordinateFinder();

    @Nested
    @DisplayName("게임레벨을 인자로 받아 Cell 배열을 초기화 할 수 있다.")
    class InitMineSweeper {
        @Test
        @DisplayName("게임레벨을 인자로 받아 Cell 배열에 게임레벨의 지뢰개수만큼 LandMineCell을 할당할 수 있다.")
        void initMineSweeper() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = Board.withConfig(config);
            int count = 0;
            //when
            for (int row = 0; row < config.rowCount(); row++) {
                for (int col = 0; col < config.columnCount(); col++) {
                    if (board.get(new Coordinate(row, col)).isLandMine()) count++;
                }
            }
            //then
            assertThat(count).isEqualTo(config.mineCount());
        }

        @Test
        @DisplayName("나머지는 지뢰가 아니다.")
        void normalCell() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = Board.withConfig(config);
            int count = 0;
            //when
            for (int row = 0; row < board.config.rowCount(); row++) {
                for (int col = 0; col < board.config.columnCount(); col++) {
                    Cell cell = board.get(new Coordinate(row, col));
                    if (!cell.isLandMine()) count++;
                }
            }
            assertThat(count).isEqualTo(config.rowCount() * config.columnCount() - config.mineCount());
        }

    }

    @Nested
    @DisplayName("좌표를 입력받아 Cell을 open 할 수 있다.")
    class OpenCellWithCoordinates {
        @Test
        @DisplayName("지뢰라면 모든 셀을 열고 게임을 끝낸다. (isCleared = false) (isAllOpened = true)")
        void openCellWithCoordinates() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = Board.withConfig(config);
            Coordinate mineCoordinate = coordinateFinder.findMineCoordinate(board);
            //when
            board.open(mineCoordinate);
            //then
            assertThat(board.isAllOpened()).isTrue();
            assertThat(board.isCleared()).isFalse();
        }


        @Test
        @DisplayName("지뢰가 아니고 주변에 지뢰가 없다면, 깃발을 제외하고 함께 연다.")
        void openNormalCell() {
            //given
            BoardConfig config = MineSweeperGameLevel.HARD.boardConfig;
            Board board = Board.withConfig(config);
            Coordinate normalCoordinate = coordinateFinder.findCanAutoOpenAroundCellCoordinate(board);
            //when
            board.open(normalCoordinate);
            //then
            int aroundOpenedCount = countAroundOpenedCells(board, normalCoordinate);
            int cantOpenCellCount = countAroundCantOpenCell(board, normalCoordinate);
            int expectedAroundCellCount = countAroundCells(board, normalCoordinate);
            assertThat(aroundOpenedCount + cantOpenCellCount).isEqualTo(expectedAroundCellCount);
        }

        private int countAroundCells(Board board, Coordinate coordinate) {
            int[][] deltas = {{-1, -1}, {-1, 0}, {-1, +1}, {0, -1}, {0, +1}, {1, -1}, {1, 0}, {1, +1}};
            int count = 0;
            for (int[] delta : deltas) {
                Coordinate targetCoordinates = new Coordinate(coordinate.row() + delta[0], coordinate.column() + delta[1]);
                if (targetCoordinates.isNotMinus() && board.config.isNotOver(targetCoordinates)) {
                    count++;
                }
            }
            return count;
        }

        private int countAroundOpenedCells(Board board, Coordinate coordinate) {
            int[][] deltas = {{-1, -1}, {-1, 0}, {-1, +1}, {0, -1}, {0, +1}, {1, -1}, {1, 0}, {1, +1}};
            int count = 0;
            for (int[] delta : deltas) {
                Coordinate targetCoordinates = new Coordinate(coordinate.row() + delta[0], coordinate.column() + delta[1]);
                if (targetCoordinates.isNotMinus() && board.config.isNotOver(targetCoordinates)) {
                    Cell cell = board.get(targetCoordinates);
                    if (cell.isFlagged()) count++;
                }
            }
            return count;
        }

        private int countAroundCantOpenCell(Board board, Coordinate coordinate) {
            int[][] deltas = {{-1, -1}, {-1, 0}, {-1, +1}, {0, -1}, {0, +1}, {1, -1}, {1, 0}, {1, +1}};
            int count = 0;
            for (int[] delta : deltas) {
                Coordinate targetCoordinates = new Coordinate(coordinate.row() + delta[0], coordinate.column() + delta[1]);
                if (targetCoordinates.isNotMinus() && board.config.isNotOver(targetCoordinates)) {
                    Cell cell = board.get(targetCoordinates);
                    if (!cell.isFlagged()) count++;
                }
            }
            return count;
        }

    }

    @Nested
    @DisplayName("모든 셀의 클리어 여부를 판단할 수 있다.")
    class IsCleared {
        @Test
        @DisplayName("셀이 모두 열리지 않았을 경우 false다.")
        void isNotOpenClearedFalse() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = Board.withConfig(config);
            //when
            //then
            assertThat(board.isCleared()).isFalse();
        }

        @Test
        @DisplayName("셀이 모두 열리더라도 하나라도 Cell.isCleared가 false라면 false다.")
        void isNotCellClearedThenClearedFalse() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = Board.withConfig(config);
            Coordinate landMineCell = coordinateFinder.findMineCoordinate(board);
            //when
            board.open(landMineCell);
            //then
            assertThat(board.isCleared()).isFalse();
        }

        @Test
        @DisplayName("NormalCell이 모두 열리고 LandMineCell이 모두 깃발이 꽂혔다면 true다.")
        void isCleared() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = Board.withConfig(config);
            //when
            for (int row = 0; row < board.config.rowCount(); row++) {
                for (int col = 0; col < board.config.columnCount(); col++) {
                    Cell cell = board.get(new Coordinate(row, col));
                    if (cell.isLandMine()) {
                        cell.toggleFlag();
                    } else {
                        cell.open();
                    }
                }
            }
            //then
            assertThat(board.isCleared()).isTrue();
        }
    }
}