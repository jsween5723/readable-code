package cleancode.minesweeper.tobe.minesweeper.cell;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCharSequence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 1. 셀은 모두 열 수 있어야한다. 2. 셀은 모두 깃발을 꽂고 회수할 수 있어야한다. 6. 셀은 닫혀 있을 때, 깃발이 꽂혔을 때 출력할 수 있어야한다.
 */
class CellTest {

    @Test
    @DisplayName("셀을 열면 열린다.")
    void notLandMineThenOpen() {
        //given
        Cell normalCell = Cell.normalCell();
        Cell landMineCell = Cell.mineCell();
        //when
        normalCell.open();
        landMineCell.open();
        //then
        assertThat(normalCell.isOpened()).isTrue();
        assertThat(normalCell.isClosed()).isFalse();
        assertThat(landMineCell.isOpened()).isTrue();
        assertThat(landMineCell.isClosed()).isFalse();
    }

    @Test
    @DisplayName("열린 셀을 열면 변화가 없다.")
    void alreadyOpenedCellThenThrow() {
        //given
        Cell normalCell = Cell.normalCell();
        Cell landMineCell = Cell.mineCell();
        //when
        normalCell.open();
        landMineCell.open();
        //when-then
        assertThat(normalCell.isOpened()).isTrue();
        assertThat(normalCell.isClosed()).isFalse();
        assertThat(landMineCell.isOpened()).isTrue();
        assertThat(landMineCell.isClosed()).isFalse();
    }


    @Test
    @DisplayName("열리지 않은 셀에 플래그를 달 수 있다.")
    void toggleFlag1() {
        //given
        Cell normalCell = Cell.normalCell();
        Cell landMineCell = Cell.mineCell();
        //when
        normalCell.toggleFlag();
        landMineCell.toggleFlag();
        //then
        assertThat(normalCell.isFlagged()).isTrue();
        assertThat(landMineCell.isFlagged()).isTrue();
    }

    @Test
    @DisplayName("플래그가 달린 셀에 시도하면 플래그를 뺄 수 있다.")
    void toggleFlag2() {
        //given
        Cell normalCell = Cell.normalCell();
        Cell landMineCell = Cell.mineCell();
        normalCell.toggleFlag();
        landMineCell.toggleFlag();
        //when
        normalCell.toggleFlag();
        landMineCell.toggleFlag();
        //then
        assertThat(normalCell.isFlagged()).isFalse();
        assertThat(landMineCell.isFlagged()).isFalse();
    }

    @Test
    @DisplayName("열린 셀에 시도하면 변화가 없다 (false)")
    void toggleFlag3() {
        //given
        Cell normalCell = Cell.normalCell();
        Cell landMineCell = Cell.mineCell();
        normalCell.open();
        landMineCell.open();
        //when
        normalCell.toggleFlag();
        landMineCell.toggleFlag();
        //then
        assertThat(normalCell.isFlagged()).isFalse();
        assertThat(landMineCell.isFlagged()).isFalse();
    }

    @Test
    @DisplayName("닫힌 상태를 출력할 수 있다.")
    void to_string() {
        //        given
        Cell normalCell = Cell.normalCell();
        Cell landMineCell = Cell.mineCell();
//        when-then
        assertThatCharSequence(normalCell.toString()).isEqualTo(CellSign.CLOSED.sign);
        assertThatCharSequence(landMineCell.toString()).isEqualTo(CellSign.CLOSED.sign);
    }

    @Test
    @DisplayName("깃발이 달린 상태를 출력할 수 있다.")
    void to_string2() {
        //        given
        Cell normalCell = Cell.normalCell();
        Cell landMineCell = Cell.mineCell();
//        when
        normalCell.toggleFlag();
        landMineCell.toggleFlag();
//            then
        assertThatCharSequence(normalCell.toString()).isEqualTo(CellSign.FLAGGED.sign);
        assertThatCharSequence(landMineCell.toString()).isEqualTo(CellSign.FLAGGED.sign);
    }
}