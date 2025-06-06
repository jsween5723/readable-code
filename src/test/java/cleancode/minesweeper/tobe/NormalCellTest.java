package cleancode.minesweeper.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NormalCellTest {

    @Nested
    @DisplayName("일반 셀 열기 테스트")
    class open_test{
        @Test
        @DisplayName("지뢰가 아닌 셀을 열면 열린다.")
        void notLandMineThenOpen() {
            //given
            NormalCell normalCell = new NormalCell();
            //when
            normalCell.open();
            //then
            assertThat(normalCell.isOpened()).isTrue();
        }

        @Test
        @DisplayName("열린 셀을 열면 예외가 발생한다.")
        void alreadyOpenedCellThenThrow() {
            //given
            NormalCell normalCell = new NormalCell();
            normalCell.open();
            //when-then
            assertThatThrownBy(normalCell::open).isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("플래그가 달린 셀을 열면 예외가 발생한다.")
        void flaggedCellThenThrow() {
            //given
            NormalCell normalCell = new NormalCell();
            normalCell.toggleFlag();
            //when-then
            assertThatThrownBy(normalCell::open).isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    @DisplayName("일반 셀 플래그 토글 테스트")
    class toggle_flag_test{
        @Test
        @DisplayName("열리지 않은 셀에 플래그를 달 수 있다.")
        void toggleFlag1() {
            //given
            NormalCell normalCell = new NormalCell();
            //when
            normalCell.toggleFlag();
            assertThat(normalCell.isFlagged()).isTrue();
        }

        @Test
        @DisplayName("플래그가 달린 셀에 시도하면 플래그를 뺄 수 있다.")
        void toggleFlag2() {
            //given
            NormalCell normalCell = new NormalCell();
            normalCell.toggleFlag();
            //when
            normalCell.toggleFlag();
            assertThat(normalCell.isFlagged()).isFalse();
        }

        @Test
        @DisplayName("열린 셀에 시도하면 예외가 발생한다.")
        void toggleFlag3() {
            //given
            NormalCell normalCell = new NormalCell();
            normalCell.open();
            //when
            normalCell.toggleFlag();
            //then
            assertThatThrownBy(normalCell::toggleFlag).isInstanceOf(IllegalStateException.class);
        }
    }



    @Test
    @DisplayName("일반 셀은 지뢰가 아니다.")
    void isLandMine() {
        NormalCell normalCell = new NormalCell();
        assertThat(normalCell.isLandMine()).isFalse();
    }

    @Nested
    @DisplayName("일반 셀 자동열기 가능 여부 테스트")
    class can_auto_open_test{
        @Test
        @DisplayName("이미 열린 셀은 자동열기가 불가능하다.")
        void openedCantAutoOpen() {
            //given
            NormalCell normalCell = new NormalCell();
            //when
            normalCell.open();
            //then
            assertThat(normalCell.canAutoOpen()).isFalse();
        }

        @Test
        @DisplayName("플래그가 달린 셀은 자동열기가 불가능하다.")
        void flagCantAutoOpen() {
            //given
            NormalCell normalCell = new NormalCell();
            //when
            normalCell.toggleFlag();
            //then
            assertThat(normalCell.canAutoOpen()).isFalse();
        }

        @Test
        @DisplayName("아무 상태도 아닌 셀은 자동열기가 가능하다.")
        void normalCanAutoOpen() {
            //given
            NormalCell normalCell = new NormalCell();
            //when
            //then
            assertThat(normalCell.canAutoOpen()).isTrue();
        }
    }

}