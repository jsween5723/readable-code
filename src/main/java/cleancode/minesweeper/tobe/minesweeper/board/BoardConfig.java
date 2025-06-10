package cleancode.minesweeper.tobe.minesweeper.board;

public record BoardConfig(int rowCount, int columnCount, int mineCount) {
    boolean isNotOver(Coordinate coordinate) {
        return coordinate.row() < rowCount && coordinate.column() < columnCount;
    }

    public String toColumnIdentifierString() {
        StringBuilder result = new StringBuilder();
        result.append(" ".repeat(Math.max(0, countRowIdentifierMaxSpace() + 1)));
        for (int i = 0; i < columnCount; i++) {
            result.append(Character.toString('A' + i));
            result.append(' ');
        }
        return result.toString();
    }


    public int countRowIdentifierMaxSpace() {
        int count = 0;
        int target = rowCount;
        while (target > 0) {
            count++;
            target /= 10;
        }
        return count;
    }
}
