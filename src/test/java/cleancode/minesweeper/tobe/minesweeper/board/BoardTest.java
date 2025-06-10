package cleancode.minesweeper.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.minesweeper.MineSweeperGameLevel;
import cleancode.minesweeper.tobe.minesweeper.cell.Cell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 1. 게임레벨을 인자로 받아 Cell 배열을 초기화 할 수 있다.
 * 1-1. Cell 배열에 게임레벨의 지뢰개수만큼 LandMineCell을 할당할 수 있다.
 * 1-2. 나머지는 NormalCell을 할당하며, 주변 셀의 LandMineCell 수를 가진다.
 * 2. 좌표를 입력받아 Cell을 open 할 수 있다.
 * 2-1. 지뢰라면 모든 셀을 열고 게임을 끝낸다. (isCleared = false) (isMineOpened = true)
 * 2-2. 지뢰가 아니라면 주변 Cell 열기가 가능할 경우 함께 연다.
 * 4. 모든 셀의 클리어 여부를 판단할 수 있다.
 */
class BoardTest {

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
        @DisplayName("지뢰라면 모든 셀을 연다. (isCleared = false) (isAllOpened = true)")
        void openCellWithCoordinates() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = spy(Board.withConfig(config));
            Coordinate mineCoordinate = new Coordinate(1, 1);
            when(board.get(mineCoordinate)).thenReturn(Cell.mineCell());
            //when
            board.open(mineCoordinate);
            //then
            assertThat(board.isMineOpened()).isTrue();
            assertThat(board.isCleared()).isFalse();
        }

        @Test
        @DisplayName("깃발이 달렸거나 이미 열린 셀은 열리지 않는다.")
        void openCellWithCoordinatesn() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = spy(Board.withConfig(config));
            Coordinate flaggedCoordinate = new Coordinate(1, 1);
            Coordinate openedCoordinate = new Coordinate(1, 2);
            Cell flagCell = spy(Cell.normalCell());
            flagCell.toggleFlag();
            Cell openedCell = spy(Cell.normalCell());
            openedCell.open();
            when(board.get(flaggedCoordinate)).thenReturn(flagCell);
            when(board.get(openedCoordinate)).thenReturn(openedCell);
            //when
            board.open(flaggedCoordinate);
            board.open(openedCoordinate);
            //then
            verify(flagCell, never()).open();
            verify(openedCell, times(1)).open();
        }


        @Test
        @DisplayName("지뢰가 아니고 주변에 지뢰가 없다면, 깃발을 제외하고 함께 연다.")
        void openNormalCell() {
            //given
            BoardConfig config = MineSweeperGameLevel.HARD.boardConfig;
            Board board = spy(Board.withConfig(config));
            Coordinate targetCoordinate = new Coordinate(1, 1);
            int[][] deltas = {{-1, -1}, {-1, 0}, {-1, +1}, {0, -1}, {0, +1}, {1, -1}, {1, 0}, {1, +1}};
            Cell[] cells = IntStream.range(0, 8).mapToObj((i) -> Cell.normalCell()).toArray(Cell[]::new);
            Coordinate[] coordinates = Arrays.stream(deltas)
                    .map(delta -> new Coordinate(targetCoordinate.row() + delta[0], targetCoordinate.column() + delta[1]))
                    .toArray(Coordinate[]::new);
            for (int i = 0; i < cells.length; i++) {
                when(board.get(coordinates[i])).thenReturn(cells[i]);
            }
            //when
            board.open(targetCoordinate);
            //then
            for (Cell cell : cells) {
                assertThat(cell.isOpened()).isTrue();
            }
        }
    }

    @Test
    @DisplayName("좌표를 입력받아 깃발을 토글할 수 있다.")
    void flagCellWithCoordinates() {
        //given
        BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
        Board board = spy(Board.withConfig(config));
        Coordinate flaggedCoordinate = new Coordinate(1, 1);
        Cell flagCell = spy(Cell.normalCell());
        when(board.get(flaggedCoordinate)).thenReturn(flagCell);
        //when
        board.toggleFlag(flaggedCoordinate);
        //then
        verify(flagCell).toggleFlag();
    }

    @Nested
    @DisplayName("보드는 설정을 통해 좌표값이 유효한지 검증할 수 있다.")
    class ValidateCoordinates {
        @Test
        @DisplayName("범위에 적합하면 예외를 던지지 않는다.")
        void configCoordinateBalidate() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = Board.withConfig(config);
            Coordinate targetCoordinate = new Coordinate(1, 1);
            //when
            //then
            assertThatNoException().isThrownBy(() -> board.validateCoordinates(targetCoordinate));
        }

        @Test
        @DisplayName("좌표가 범위밖이면 예외를 던진다.")
        void configCoordinateBalidate2() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = Board.withConfig(config);
            Coordinate targetCoordinateRowOver = new Coordinate(config.rowCount() + 1, config.columnCount());
            Coordinate targetCoordinateColumnOver = new Coordinate(config.rowCount(), config.columnCount() + 1);
            Coordinate targetCoordinateRowUnder = new Coordinate(-1, config.columnCount());
            Coordinate targetCoordinateColumnUnder = new Coordinate(config.rowCount(), -1);
            //when
            //then
            assertThatThrownBy(() -> board.validateCoordinates(targetCoordinateRowOver))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> board.validateCoordinates(targetCoordinateColumnOver))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> board.validateCoordinates(targetCoordinateRowUnder))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> board.validateCoordinates(targetCoordinateColumnUnder))
                    .isInstanceOf(IllegalArgumentException.class);
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
            Board board = spy(Board.withConfig(config));
            Coordinate mineCoordinate = new Coordinate(1, 1);
            when(board.get(mineCoordinate)).thenReturn(Cell.mineCell());
            //when
            board.open(mineCoordinate);
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

    @Test
    @DisplayName("toString 테스트")
    void toStringTest() {
        //given
        BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
        Board board = spy(Board.withConfig(config));
        for (int row = 0; row < config.rowCount(); row++) {
            for (int col = 0; col < config.columnCount(); col++) {
                Coordinate coordinate = new Coordinate(row, col);
                when(board.get(coordinate)).thenReturn(switch (col % 4) {
                    case 0 -> {
                        Cell cell = Cell.normalCell();
                        cell.toggleFlag();
                        yield cell;
                    }
                    case 1 -> {
                        Cell cell = row == 0 ? Cell.mineCell() : Cell.normalCell();
                        cell.open();
                        yield cell;
                    }
                    case 2 -> Cell.normalCell();
                    case 3 -> {
                        Cell cell = Cell.mineCell();
                        cell.open();
                        yield cell;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + col);
                });
            }
        }
        //when
        String boardString = board.toString();
        //then     CLOSED("□"), FLAGGED("⚑"), NORMAL_OPENED("■"), MINE_OPENED("☼");
        assertThatCharSequence(boardString).isEqualToIgnoringWhitespace("""
                  A B C D
                  1 ⚑ ☼ □ ☼
                  2 ⚑ 1 □ ☼
                  3 ⚑ ■ □ ☼
                  4 ⚑ ■ □ ☼
                """);

    }
}