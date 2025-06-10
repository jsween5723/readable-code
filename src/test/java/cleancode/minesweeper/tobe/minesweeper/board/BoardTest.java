package cleancode.minesweeper.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.minesweeper.MineSweeperGameLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCharSequence;

/**
 * 1. 게임레벨을 인자로 받아 Cell 배열을 초기화 할 수 있다.
 * 1-1. Cell 배열에 게임레벨의 지뢰개수만큼 LandMineCell을 할당할 수 있다.
 * 1-2. 나머지는 NormalCell을 할당하며, 주변 셀의 LandMineCell 수를 가진다.
 * 2. 좌표를 입력받아 Cell을 open 할 수 있다.
 * 2-1. 지뢰라면 모든 셀을 열고 게임을 끝낸다. (isCleared = false) (cells.forEach.isOpened = true)
 * 2-2. 지뢰가 아니라면 주변 Cell 열기가 가능할 경우 함께 연다.
 * 3. 문자열로 전환 시 level을 기반으로 행, 열 식별자를 문자열로 전환하고 각 Cell을 좌표에 맞게 문자열로 전환한다
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
                    if (board.get(new Coordinate(row, col)) instanceof LandMineCell) count++;
                }
            }
            //then
            assertThat(count).isEqualTo(config.mineCount());
        }

        @Test
        @DisplayName("나머지는 NormalCell을 할당하며, 주변 셀의 LandMineCell 수를 가진다.")
        void normalCell() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = Board.withConfig(config);
            //when
            for (int row = 0; row < board.config.rowCount(); row++) {
                for (int col = 0; col < board.config.columnCount(); col++) {
                    if (board.get(new Coordinate(row, col)) instanceof NormalCell normalCell) {
                        final int count = countAroundLandMine(board, new Coordinate(row, col));
                        //then
                        assertThat(normalCell.aroundMineCount).isEqualTo(count);
                    }
                }
            }
        }

        private int countAroundLandMine(Board board, Coordinate coordinate) {
            int count = 0;
            int[][] deltas = {{-1, -1}, {-1, 0}, {-1, +1}, {0, -1}, {0, +1}, {1, -1}, {1, 0}, {1, +1}};
            for (int[] delta : deltas) {
                Coordinate targetCoordinates = new Coordinate(coordinate.row() + delta[0], coordinate.column() + delta[1]);
                if (targetCoordinates.isNotMinus() && board.config.isNotOver(targetCoordinates) && board.get(targetCoordinates).isLandMine()) {
                    count++;
                }
            }
            return count;
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
        @DisplayName("지뢰가 아니라면 주변 Cell 열기가 가능할 경우 함께 연다.")
        void openNormalCell() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = Board.withConfig(config);
            Coordinate normalCoordinate = coordinateFinder.findNormalCoordinate(board);
            //when
            board.open(normalCoordinate);
            //then
            assertAroundNormalCellIsOpenedNotMineCell(board, normalCoordinate);
        }

        private void assertAroundNormalCellIsOpenedNotMineCell(Board board, Coordinate coordinate) {
            int[][] deltas = {{-1, -1}, {-1, 0}, {-1, +1}, {0, -1}, {0, +1}, {1, -1}, {1, 0}, {1, +1}};
            for (int[] delta : deltas) {
                Coordinate targetCoordinates = new Coordinate(coordinate.row() + delta[0], coordinate.column() + delta[1]);
                if (targetCoordinates.isNotMinus() && board.config.isNotOver(targetCoordinates)) {
                    Cell cell = board.get(targetCoordinates);
                    //then
                    assertThat(cell.isOpened()).isEqualTo(isExpectedOpen(cell));
                }
            }
        }

        private boolean isExpectedOpen(Cell cell) {
            if (cell.isLandMine()) return false;
            if (cell.isFlagged()) return false;
            if (cell instanceof NormalCell normalCell && normalCell.aroundMineCount > 0) return false;
            return true;
        }

    }

    @Nested
    @DisplayName("문자열로 전환 시 level을 기반으로 행, 열 식별자를 출력하고 각 Cell을 좌표에 맞게 문자열로 전한다")
    class ToString {
        @Test
        @DisplayName("비기너 행식별자 정상 문자열로 전환 테스트")
        void beginnerColumnTest() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = Board.withConfig(config);
            //when
            String string = board.toString();
            String column = string.lines().toList().get(0);
            //then
            assertThatCharSequence(column).containsIgnoringWhitespaces(IntStream.range(0, config.columnCount()).map((n) -> n + 'A').toString());
        }

        @Test
        @DisplayName("미들 행식별자 정상 문자열로 전환 테스트")
        void middleColumnTest() {
            //given
            BoardConfig config = MineSweeperGameLevel.MIDDLE.boardConfig;
            Board board = Board.withConfig(config);
            //when
            String string = board.toString();
            String column = string.lines().toList().get(0);
            //then
            assertThatCharSequence(column).containsIgnoringWhitespaces(IntStream.range(0, config.columnCount()).map((n) -> n + 'A').toString());
        }

        @Test
        @DisplayName("하드 행식별자 정상 문자열로 전환 테스트")
        void hardColumnTest() {
            //given
            BoardConfig config = MineSweeperGameLevel.HARD.boardConfig;
            Board board = Board.withConfig(config);
            //when
            String string = board.toString();
            String column = string.lines().toList().get(0);
            //then
            assertThatCharSequence(column).containsIgnoringWhitespaces(IntStream.range(0, config.columnCount()).map((n) -> n + 'A').toString());
        }

        @Test
        @DisplayName("비기너 열식별자 정상 정수로 전환 테스트")
        void beginnerRowTest() {
            //given
            BoardConfig config = MineSweeperGameLevel.BEGINNER.boardConfig;
            Board board = Board.withConfig(config);
            //when
            String string = board.toString();
            String row = string.lines().map((line) -> line.charAt(0)).toString();
            //then
            assertThatCharSequence(row).containsIgnoringWhitespaces().containsOnlyDigits();
        }

        @Test
        @DisplayName("미들 열식별자 정상 정수로 전환 테스트")
        void middleRowTest() {
            //given
            BoardConfig config = MineSweeperGameLevel.MIDDLE.boardConfig;
            Board board = Board.withConfig(config);
            //when
            String string = board.toString();
            String row = string.lines().map((line) -> line.charAt(0)).toString();
            //then
            assertThatCharSequence(row).containsIgnoringWhitespaces().containsOnlyDigits();
        }

        @Test
        @DisplayName("하드 열식별자 정상 정수로 전환 테스트")
        void hardRowTest() {
            //given
            BoardConfig config = MineSweeperGameLevel.HARD.boardConfig;
            Board board = Board.withConfig(config);
            //when
            String string = board.toString();
            String row = string.lines().map((line) -> line.charAt(0)).toString();
            //then
            assertThatCharSequence(row).containsIgnoringWhitespaces().containsOnlyDigits();
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
                    if (cell instanceof LandMineCell landMineCell) {
                        landMineCell.toggleFlag();
                    }
                    if (cell instanceof NormalCell normalCell) {
                        normalCell.open();
                    }
                }
            }
            //then
            assertThat(board.isCleared()).isTrue();
        }
    }
}