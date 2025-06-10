package cleancode.minesweeper.tobe.minesweeper.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// 좌표값이 유효한지 검증할 수 있다.
class BoardConfigTest {

    @Nested
    @DisplayName("좌표값이 유효한지 검증할 수 있다.")
    class Validate {
        @Test
        @DisplayName("row가 설정값을 초과하면 false")
        void validateCoordinates() {
            //given
            Coordinate coordinate = new Coordinate(5, 1);
            BoardConfig config = new BoardConfig(4, 4, 4);
            //when
            boolean result = config.isNotOver(coordinate);
            //then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("column이 설정값을 초과하면 false")
        void validateCoordinatesa() {
            //given
            Coordinate coordinate = new Coordinate(2, 5);
            BoardConfig config = new BoardConfig(4, 4, 4);
            //when
            boolean result = config.isNotOver(coordinate);
            //then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("row, column 둘다 초과하지 않으면 true")
        void validateCoordinatesb() {
            //given
            Coordinate coordinate = new Coordinate(2, 2);
            BoardConfig config = new BoardConfig(4, 4, 4);
            //when
            boolean result = config.isNotOver(coordinate);
            //then
            assertThat(result).isTrue();
        }
    }


}