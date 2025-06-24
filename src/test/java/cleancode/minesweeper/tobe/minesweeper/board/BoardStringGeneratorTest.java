package cleancode.minesweeper.tobe.minesweeper.board;

import static org.assertj.core.api.Assertions.assertThatCharSequence;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import cleancode.minesweeper.tobe.minesweeper.cell.Cell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// 보드의 문자열을 생성할 수 있다.
class BoardStringGeneratorTest {

    @Test
    @DisplayName("toString 테스트")
    void generateTest() {
        //given
        int rowCount = 4;
        int columnCount = 4;
        int mineCount = 3;
        Board board = spy(new Board(rowCount, columnCount, mineCount));
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                Coordinate coordinate = new Coordinate(row, col);
                when(board.get(coordinate)).thenReturn(switch (col % 4) {
                    case 0 -> generateFlagCell();
                    case 1 -> generateOpenedCell();
                    case 2 -> Cell.normalCell();
                    case 3 -> generateOpenedMineCell();
                    default -> throw new IllegalStateException("Unexpected value: " + col);
                });
            }
        }
        //when
        String boardString = board.new BoardStringGenerator().generate();
        //then     CLOSED("□"), FLAGGED("⚑"), NORMAL_OPENED("■"), MINE_OPENED("☼");
        assertThatCharSequence(boardString).isEqualToIgnoringWhitespace("""
              A B C D
              1 ⚑ ■ □ ☼
              2 ⚑ ■ □ ☼
              3 ⚑ ■ □ ☼
              4 ⚑ ■ □ ☼
            """);

    }

    private static Cell generateOpenedMineCell() {
        Cell cell = Cell.mineCell();
        cell.open();
        return cell;
    }

    private static Cell generateOpenedCell() {
        Cell cell = Cell.normalCell();
        cell.open();
        return cell;
    }

    private Cell generateFlagCell() {
        Cell cell = Cell.normalCell();
        cell.toggleFlag();
        return cell;
    }
}