package cleancode.minesweeper.tobe.board.cell;

/**
 * 1. 셀은 모두 열 수 있어야한다.
 * 2. 셀은 모두 깃발을 꽂고 회수할 수 있어야한다.
 * 3. 셀은 지뢰인지 알 수 있어야한다.
 * 4. 셀은 주변 셀이 함께 열릴 수 있는지 알 수 있어야한다.
 * 5. 셀은 해당 셀이 클리어조건을 충족하고 있는지 알 수 있어야한다.
 * 6. 셀은 닫혀 있을 때, 깃발이 꽂혔을 때 출력할 수 있는 정보를 들고 있어야한다.
 */
public abstract class Cell {
    private boolean opened = false;
    private boolean flagged = false;
    static public Cell normalCellWithAroundMine(int aroundMineCount) {
        return new NormalCell(aroundMineCount);
    }
    static public Cell normalCellWithoutAroundMine() {
        return new NormalCell(0);
    }
    static public Cell mineCell() {
        return new LandMineCell();
    }

    public void open() {
        if (flagged) return;
        opened = true;
    }

    public void toggleFlag() {
        if (opened) return;
        flagged = !flagged;
    }

    public boolean isOpened() {
        return opened;
    }

    public boolean isFlagged() {
        return flagged;
    }
    public boolean cantAutoOpenAroundThis() {
        return !canAutoOpenAroundThis();
    }

    abstract public boolean isLandMine();

    abstract boolean canAutoOpenAroundThis();

    abstract public boolean isCleared();

    @Override
    public String toString() {
        if (isFlagged()) return CellSign.FLAGGED.sign;
        return CellSign.CLOSED.sign;
    }
}