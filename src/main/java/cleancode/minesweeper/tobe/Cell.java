package cleancode.minesweeper.tobe;

public abstract class Cell {
    abstract void open();

    abstract void toggleFlag();

    abstract boolean isLandMine();

    abstract boolean canAutoOpen();

    abstract boolean isOpened();
    abstract boolean isFlagged();
}