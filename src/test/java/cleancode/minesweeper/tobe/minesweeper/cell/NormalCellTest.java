package cleancode.minesweeper.tobe.minesweeper.cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 2. 일반 셀은 열린상태를 표시해야한다.
 * 3. 일반 셀의 깃발이 꽂힌 상태가 아닐 경우 주변 셀을 열 수 있다.
 * 4. 일반 셀의 클리어조건은 열린상태이다.
 * 5. 일반 셀은 지뢰가 아니다.
 */
class NormalCellTest {
    @Nested
    @DisplayName("일반 셀 출력 테스트")
    class ToString{
        @Test
        @DisplayName("일반 셀은 열린상태를 표시해야한다.")
        void toString2() {
            //given
            Cell normalCell = Cell.normalCell();
            //when
            normalCell.open();
            //then
            assertThat(normalCell.toString()).isEqualTo(CellSign.NORMAL_OPENED.sign);
        }
    }


    @Nested
    @DisplayName("일반 셀 주변 셀 열기 여부 테스트")
    class CanAutoOpenAroundThis{
        @Test
        @DisplayName("일반 셀에 깃발이 꽂힌 상태가 아닐 경우 주변 셀을 열 수 있다.")
        void canAutoOpenAroundThis() {
            //given
            Cell normalCell = Cell.normalCell();
            //when
            //then
            assertThat(normalCell.cantAutoOpenAroundThis()).isFalse();
        }

        @Test
        @DisplayName("깃발이 꽂힌 상태일 경우 주변 셀을 열 수 없다.")
        void canAutoOpenAroundThis4() {
            //given
            Cell normalCell = Cell.normalCell();
            //when
            normalCell.toggleFlag();
            //then
            assertThat(normalCell.cantAutoOpenAroundThis()).isTrue();
        }
    }

    @Nested
    @DisplayName("일반 셀 클리어조건 테스트")
    class IsCleared{
        @Test
        @DisplayName("일반 셀이 열렸을 때 클리어조건이 true다.")
        void isCleared() {
            //given
            Cell normalCell = Cell.normalCell();
            //when
            normalCell.open();
            //then
            assertThat(normalCell.isCleared()).isTrue();
        }

        @Test
        @DisplayName("일반 셀이 열리지 않았다면 클리어조건이 false다.")
        void isCleared2() {
            //given
            Cell normalCell = Cell.normalCell();
            //when
            //then
            assertThat(normalCell.isCleared()).isFalse();
        }
    }
}