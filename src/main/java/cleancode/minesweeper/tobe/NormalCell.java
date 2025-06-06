package cleancode.minesweeper.tobe;

public class NormalCell extends Cell{
    @Override
    void open() {

    }

    @Override
    void toggleFlag() {

    }

    @Override
    boolean isLandMine() {
        return false;
    }

    @Override
    boolean canAutoOpen() {
        return false;
    }

    @Override
    boolean isOpened() {
        return false;
    }

    @Override
    boolean isFlagged() {
        return false;
    }
}
