package cleancode.minesweeper.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 1. 일반 셀은 열렸을 때 주변에 지뢰개수를 표시해야한다.
 * 2. 일반 셀은 주변 지뢰가 없을 경우 열린상태를 표시해야한다.
 * 3. 일반 셀의 주변 지뢰가 없고 열린 상태가 아니고 깃발이 꽂힌 상태가 아닐 경우 주변 셀을 열 수 있다.
 * 4. 일반 셀의 클리어조건은 열린상태이다.
 * 5. 일반 셀은 지뢰가 아니다.
 */
class NormalCellTest {
    @Nested
    @DisplayName("일반 셀 출력 테스트")
    class ToString{
        @Test
        @DisplayName("일반 셀은 열렸을 때 주변에 지뢰개수를 표시해야한다.")
        void toString1() {
            //given
            int aroundMineCount = 2;
            NormalCell normalCell = new NormalCell(aroundMineCount);
            //when
            normalCell.open();
            //then
            assertThat(normalCell.toString()).isEqualTo(aroundMineCount + "");
        }
        @Test
        @DisplayName("일반 셀은 주변 지뢰가 없을 경우 열린상태를 표시해야한다.")
        void toString2() {
            //given
            int aroundMineCount = 0;
            NormalCell normalCell = new NormalCell(aroundMineCount);
            //when
            normalCell.open();
            //then
            assertThat(normalCell.toString()).isEqualTo(NormalCell.NORMAL_CELL_OPENED_SIGN);
        }
    }


    @Nested
    @DisplayName("일반 셀 주변 셀 열기 여부 테스트")
    class CanAutoOpenAroundThis{
        @Test
        @DisplayName("일반 셀의 주변 지뢰가 없고 열린 상태가 아니고 깃발이 꽂힌 상태가 아닐 경우 주변 셀을 열 수 있다.")
        void canAutoOpenAroundThis() {
            //given
            int aroundMineCount = 0;
            NormalCell normalCell = new NormalCell(aroundMineCount);
            //when
            //then
            assertThat(normalCell.canAutoOpenAroundThis()).isTrue();
        }

        @Test
        @DisplayName("일반 셀의 주변 지뢰가 있을 경우 주변 셀을 열 수 없다.")
        void canAutoOpenAroundThis2() {
            //given
            int aroundMineCount = 1;
            NormalCell normalCell = new NormalCell(aroundMineCount);
            //when
            //then
            assertThat(normalCell.canAutoOpenAroundThis()).isFalse();
        }

        @Test
        @DisplayName("열린 상태일 경우 주변 셀을 열 수 없다.")
        void canAutoOpenAroundThis3() {
            //given
            int aroundMineCount = 0;
            NormalCell normalCell = new NormalCell(aroundMineCount);
            //when
            normalCell.open();
            //then
            assertThat(normalCell.canAutoOpenAroundThis()).isFalse();
        }

        @Test
        @DisplayName("깃발이 꽂힌 상태일 경우 주변 셀을 열 수 없다.")
        void canAutoOpenAroundThis4() {
            //given
            int aroundMineCount = 0;
            NormalCell normalCell = new NormalCell(aroundMineCount);
            //when
            normalCell.toggleFlag();
            //then
            assertThat(normalCell.canAutoOpenAroundThis()).isFalse();
        }
    }

    @Nested
    @DisplayName("일반 셀 클리어조건 테스트")
    class IsCleared{
        @Test
        @DisplayName("일반 셀이 열렸을 때 클리어조건이 true다.")
        void isCleared() {
            //given
            NormalCell normalCell = new NormalCell(0);
            //when
            normalCell.open();
            //then
            assertThat(normalCell.isCleared()).isTrue();
        }

        @Test
        @DisplayName("일반 셀이 열리지 않았다면 클리어조건이 false다.")
        void isCleared2() {
            //given
            NormalCell normalCell = new NormalCell(0);
            //when
            //then
            assertThat(normalCell.isCleared()).isFalse();
        }
    }
}