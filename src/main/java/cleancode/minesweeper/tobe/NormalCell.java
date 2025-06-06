package cleancode.minesweeper.tobe;

public class NormalCell extends Cell {
    private boolean opened = false;
    private boolean flagged = false;

    @Override
    void open() {
        if (opened) throw new IllegalStateException("이미 열린 셀은 열 수 없습니다.");
        if (flagged) throw new IllegalStateException("플래그가 달린 셀은 열 수 없습니다.");
        opened = true;
    }

    @Override
    void toggleFlag() {
        if (opened) throw new IllegalStateException("열린 셀은 플래그를 달 수 없습니다.");
        flagged = !flagged;
    }

    @Override
    boolean isLandMine() {
        return false;
    }

    @Override
    boolean canAutoOpen() {
        return !opened && !flagged;
    }

    @Override
    boolean isOpened() {
        return opened;
    }

    @Override
    boolean isFlagged() {
        return flagged;
    }

    @Override
    boolean isCleared() {
        return opened && !flagged;
    }
}
