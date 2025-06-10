package cleancode.minesweeper.tobe;

/**
 * 1. 일반 셀은 열렸을 때 주변에 지뢰개수를 표시해야한다.
 * 2. 일반 셀은 주변 지뢰가 없을 경우 열린상태를 표시해야한다.
 * 3. 일반 셀의 주변 지뢰가 없고 열린 상태가 아니고 깃발이 꽂힌 상태가 아닐 경우 주변 셀을 열 수 있다.
 * 4. 일반 셀의 클리어조건은 열린상태이다.
 * 5. 일반 셀은 지뢰가 아니다.
 */
public class NormalCell extends Cell {
    public static final String NORMAL_CELL_OPENED_SIGN = "■";
    private final int aroundMineCount;

    private NormalCell(int aroundMineCount) {
        this.aroundMineCount = aroundMineCount;
    }

    static public NormalCell withAroundMineCount(int aroundMineCount) {
        return new NormalCell(aroundMineCount);
    }

    static public NormalCell withoutAroundMine() {
        return new NormalCell(0);
    }

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean canAutoOpenAroundThis() {
        return aroundMineCount == 0 && !isOpened() && !isFlagged();
    }

    @Override
    public boolean isCleared() {
        return isOpened();
    }

    @Override
    public String toString() {
        if (isOpened()) {
            if (aroundMineCount == 0) {
                return NORMAL_CELL_OPENED_SIGN;
            }
            return String.valueOf(aroundMineCount);
        }
        return super.toString();
    }
}
