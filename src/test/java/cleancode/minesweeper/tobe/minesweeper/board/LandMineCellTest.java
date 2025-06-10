package cleancode.minesweeper.tobe.minesweeper.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 1. 지뢰 셀은 열렸을 때 지뢰표시를 해야한다.
 * 2. 지뢰 셀은 지뢰다.
 * 3. 지뢰 셀은 주변 셀을 열 수 없다.
 * 4. 지뢰 셀의 클리어 조건은 깃발이 꽂힌 상태다.
 */
class LandMineCellTest {
    @Nested
    @DisplayName("지뢰 셀 출력 테스트")
    class ToString {
        @Test
        @DisplayName("지뢰 셀은 열렸을 때 지뢰마크를 표시해야한다.")
        void toString1() {
            //given
            Cell landMineCell = Cell.mineCell();
            //when
            landMineCell.open();
            //then
            assertThat(landMineCell.toString()).isEqualTo(CellSign.MINE_OPENED.sign);
        }
    }


    @Nested
    @DisplayName("지뢰 셀 주변 셀 열기 여부 테스트")
    class CanAutoOpenAroundThis {
        @Test
        @DisplayName("지뢰 셀은 주변 셀을 열 수 없다.")
        void canAutoOpenAroundThis() {
            //given
            Cell landMineCell = Cell.mineCell();
            //when
            //then
            assertThat(landMineCell.cantAutoOpenAroundThis()).isTrue();
        }
    }

    @Nested
    @DisplayName("지뢰 셀 클리어조건 테스트")
    class IsCleared {
        @Test
        @DisplayName("지뢰 셀에 깃발이 꽂히면 클리어 조건이 true다")
        void isCleared() {
            //given
            Cell landMineCell = Cell.mineCell();
            //when
            landMineCell.toggleFlag();
            //then
            assertThat(landMineCell.isCleared()).isTrue();
        }

        @Test
        @DisplayName("지뢰 셀에 깃발이 꽂히지 않았다면 클리어조건이 false다.")
        void isCleared2() {
            //given
            Cell landMineCell = Cell.mineCell();
            //when
            //then
            assertThat(landMineCell.isCleared()).isFalse();
        }
    }
}