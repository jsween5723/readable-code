package cleancode.minesweeper.tobe;

public class Cell {
    public static final String CLOSED_CELL_MARK = "□";
    public static final String OPENED_CELL_MARK = "■";
    public static final String LAND_MINE_MARK = "☼";
    public static final String FLAG_MARK = "⚑";
    private boolean isLandMine = false;
    private boolean isFlag = false;
    private boolean isOpen = false;
    private Integer landMineCount = 0;
    public void open() {
        isOpen = true;
    }
    public void flag() {
        if (isOpen) return;
        isFlag = true;
    }

    public boolean isLandMine() {
        return isLandMine;
    }

    public void applyLandMineCount(int count) {
        landMineCount = count;
    }

    public boolean isChecked() {
        return isOpen || isFlag;
    }

    @Override
    public String toString() {
        if (isOpen) {
            if (landMineCount == 0) return OPENED_CELL_MARK;
            return landMineCount.toString();
        }
        if (isFlag) return FLAG_MARK;
        if (isLandMine) return LAND_MINE_MARK;
        return CLOSED_CELL_MARK;
    }

    private Cell(boolean isLandMine) {
        this.isLandMine = isLandMine;
    }

    public static Cell of(boolean isLandMine) {
        return new Cell(isLandMine);
    }
}
