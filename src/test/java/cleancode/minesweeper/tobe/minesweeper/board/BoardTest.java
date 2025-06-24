package cleancode.minesweeper.tobe.minesweeper.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cleancode.minesweeper.tobe.minesweeper.cell.Cell;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 1. 게임레벨을 인자로 받아 Cell 배열을 초기화 할 수 있다. 1-1. Cell 배열에 게임레벨의 지뢰개수만큼 LandMineCell을 할당할 수 있다. 1-2. 나머지는
 * NormalCell을 할당하며, 주변 셀의 LandMineCell 수를 가진다. 2. 좌표를 입력받아 Cell을 open 할 수 있다. 2-1. 지뢰라면 모든 셀을 열고
 * 게임을 끝낸다. (isCleared = false) (isMineOpened = true) 2-2. 지뢰가 아니라면 주변 Cell 열기가 가능할 경우 함께 연다. 4. 모든
 * 셀의 클리어 여부를 판단할 수 있다.
 */
class BoardTest {

    private Board board;
    private int rowCount;
    private int columnCount;
    private int mineCount;

    @BeforeEach
    void setUp() {
        rowCount = 4;
        columnCount = 4;
        mineCount = 5;
        board = spy(new Board(4, 4, 5));
    }

    @Nested
    @DisplayName("게임레벨을 인자로 받아 Cell 배열을 초기화 할 수 있다.")
    class InitMineSweeper {

        @Test
        @DisplayName("게임레벨을 인자로 받아 Cell 배열에 게임레벨의 지뢰개수만큼 LandMineCell을 할당할 수 있다.")
        void initMineSweeper() {
            //given
            int count = 0;
            //when
            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < columnCount; col++) {
                    if (board.get(new Coordinate(row, col)).isLandMine()) {
                        count++;
                    }
                }
            }
            //then
            assertThat(count).isEqualTo(mineCount);
        }

        @Test
        @DisplayName("나머지는 지뢰가 아니다.")
        void normalCell() {
            //given
            int count = 0;
            //when
            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < columnCount; col++) {
                    Cell cell = board.get(new Coordinate(row, col));
                    if (!cell.isLandMine()) {
                        count++;
                    }
                }
            }
            assertThat(count).isEqualTo(
                rowCount * columnCount - mineCount);
        }

    }

    @Test
    @DisplayName("지뢰라면 모든 셀을 연다. (isCleared = false) (isAllOpened = true)")
    void openCellWithCoordinates() {
        //given
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
        Coordinate flaggedCoordinate = new Coordinate(1, 1);
        Cell flagCell = spy(Cell.normalCell());
        flagCell.toggleFlag();
        when(board.get(flaggedCoordinate)).thenReturn(flagCell);

        Coordinate openedCoordinate = new Coordinate(1, 2);
        Cell openedCell = spy(Cell.normalCell());
        openedCell.open();
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
        Coordinate targetCoordinate = new Coordinate(1, 1);
        Cell[] cells = createCellsForAround();
        Coordinate[] coordinates = createCoordinatesForAround(targetCoordinate);
        for (int i = 0; i < coordinates.length; i++) {
            when(board.get(coordinates[i])).thenReturn(cells[i]);
        }
        //when
        board.open(targetCoordinate);
        //then
        for (Cell cell : cells) {
            verify(cell, atLeastOnce()).open();
        }
    }

    private Coordinate[] createCoordinatesForAround(Coordinate targetCoordinate) {
        int[][] deltas = {{0, -1}, {0, +1}, {1, -1}, {1, 0},
            {1, +1}, {-1, -1}, {-1, 0}, {-1, 1}};
        return Arrays.stream(deltas)
            .map(delta -> new Coordinate(targetCoordinate.row() + delta[0],
                targetCoordinate.column() + delta[1]))
            .toArray(Coordinate[]::new);
    }

    private Cell[] createCellsForAround() {
        return IntStream.range(0, 8).mapToObj((i) -> spy(Cell.normalCell()))
            .toArray(Cell[]::new);
    }


    @Test
    @DisplayName("좌표를 입력받아 깃발을 토글할 수 있다.")
    void flagCellWithCoordinates() {
        //given
        Coordinate flaggedCoordinate = new Coordinate(1, 1);
        Cell flagCell = spy(Cell.normalCell());
        when(board.get(flaggedCoordinate)).thenReturn(flagCell);
        //when
        board.toggleFlag(flaggedCoordinate);
        //then
        verify(flagCell).toggleFlag();
    }

    @Test
    @DisplayName("범위에 적합하면 예외를 던지지 않는다.")
    void configCoordinateBalidate() {
        //given
        Coordinate targetCoordinate = new Coordinate(1, 1);
        //when
        //then
        assertThatNoException().isThrownBy(() -> board.validateCoordinates(targetCoordinate));
    }

    static Stream<Arguments> serveIncorrectCoordinates() {
        int rowCount = 4;
        int columnCount = 4;
        return Stream.of(
            Arguments.of(rowCount + 1,
                columnCount),
            Arguments.of(rowCount,
                columnCount + 1),
            Arguments.of(-1, columnCount),
            Arguments.of(rowCount, -1)
        );
    }

    @DisplayName("좌표가 범위밖이면 예외를 던진다.")
    @ParameterizedTest()
    @MethodSource("serveIncorrectCoordinates")
    void configCoordinateBalidate2(int row, int column) {
        //given
        Coordinate targetCoordinate = new Coordinate(row, column);
        //when
        //then
        assertThatThrownBy(() -> board.validateCoordinates(targetCoordinate))
            .isInstanceOf(IllegalArgumentException.class);
    }


    @Nested
    @DisplayName("모든 셀의 클리어 여부를 판단할 수 있다.")
    class IsCleared {

        @Test
        @DisplayName("셀이 모두 열리지 않았을 경우 false다.")
        void isNotOpenClearedFalse() {
            //given
            //when
            //then
            assertThat(board.isCleared()).isFalse();
        }

        @Test
        @DisplayName("셀이 모두 열리더라도 하나라도 Cell.isCleared가 false라면 false다.")
        void isNotCellClearedThenClearedFalse() {
            //given
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
            //when
            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < columnCount; col++) {
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