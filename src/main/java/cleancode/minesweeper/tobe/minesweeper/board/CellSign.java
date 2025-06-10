package cleancode.minesweeper.tobe.minesweeper.board;

public enum CellSign {
    CLOSED("□"), FLAGGED("⚑"), NORMAL_OPENED("■"), MINE_OPENED("☼");
    public final String sign;

    CellSign(String sign) {
        this.sign = sign;
    }
}
