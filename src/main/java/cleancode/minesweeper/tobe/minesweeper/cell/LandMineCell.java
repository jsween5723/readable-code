package cleancode.minesweeper.tobe.minesweeper.cell;

/**
 * 1. 지뢰 셀은 열렸을 때 지뢰표시를 해야한다.
 * 2. 지뢰 셀은 지뢰다.
 * 3. 지뢰 셀은 열렸을 때, 주변 셀을 열 수 없다.
 * 4. 지뢰 셀의 클리어 조건은 깃발이 꽂힌 상태다.
 */
class LandMineCell extends Cell {
    @Override
    public boolean isLandMine() {
        return true;
    }

    @Override
    public boolean isCleared() {
        return isFlagged();
    }

    @Override
    public String toString() {
        if (isOpened()) {
            return CellSign.MINE_OPENED.sign;
        }
        return super.toString();
    }
}
