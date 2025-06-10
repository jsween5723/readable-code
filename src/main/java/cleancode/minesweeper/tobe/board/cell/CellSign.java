package cleancode.minesweeper.tobe.board.cell;

public enum CellSign {
    CLOSED("□"), FLAGGED("⚑"), NORMAL_OPENED("■"), MINE_OPENED("☼");
    public final String sign;

    CellSign(String sign) {
        this.sign = sign;
    }
}
