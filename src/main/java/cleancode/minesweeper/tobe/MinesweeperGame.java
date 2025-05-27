package cleancode.minesweeper.tobe;

import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {


    public static final int COLUMN_COUNT = 8;
    public static final int ROW_COUNT = 10;
    public static final int LAND_MINE_COUNT = 10;
    private static Cell[][] board = new Cell[COLUMN_COUNT][ROW_COUNT];
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeBoard();
        gameStart();
    }

    private static void gameStart() {
        showStartMessage();
        while (true) {
            showBoard();
            if (isGameOver()) break;
            System.out.println();
            Coordinates coordinates = getCoordinates();
            getUserActionFor(coordinates);
        }
    }

    private static void initializeBoard() {
        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(ROW_COUNT);
            int row = new Random().nextInt(COLUMN_COUNT);
            board[row][col] = Cell.of(true);
        }
        for (int col = 0; col < COLUMN_COUNT; col++) {
            for (int row = 0; row < ROW_COUNT; row++) {
                int count = 0;
                Cell cell = board[col][row];
                if (!cell.isLandMine()) {
                    if (col - 1 >= 0 && row - 1 >= 0 && board[col - 1][row - 1].isLandMine()) {
                        count++;
                    }
                    if (col - 1 >= 0 && board[col - 1][row].isLandMine()) {
                        count++;
                    }
                    if (col - 1 >= 0 && row + 1 < ROW_COUNT && board[col - 1][row + 1].isLandMine()) {
                        count++;
                    }
                    if (row - 1 >= 0 && board[col][row - 1].isLandMine()) {
                        count++;
                    }
                    if (row + 1 < ROW_COUNT && board[col][row + 1].isLandMine()) {
                        count++;
                    }
                    if (col + 1 < COLUMN_COUNT && row - 1 >= 0 && board[col + 1][row - 1].isLandMine()) {
                        count++;
                    }
                    if (col + 1 < COLUMN_COUNT && board[col + 1][row].isLandMine()) {
                        count++;
                    }
                    if (col + 1 < COLUMN_COUNT && row + 1 < ROW_COUNT && board[col + 1][row + 1].isLandMine()) {
                        count++;
                    }
                    cell.applyLandMineCount(count);
                }
            }
        }
    }


    private static void getUserActionFor(Coordinates coordinates) {
        String input2 = getUserActionInput();
        if (input2.equals("2")) {
            flagOn(coordinates);
        } else if (input2.equals("1")) {
            openAt(coordinates);
        } else {
            showInvalidInputError();
        }
    }

    private static void showInvalidInputError() {
        System.out.println("잘못된 번호를 선택하셨습니다.");
    }

    private static String getUserActionInput() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        String input2 = scanner.nextLine();
        return input2;
    }


    private static void openAt(Coordinates coordinates) {
        Cell cell = board[coordinates.row()][coordinates.col()];
        cell.open();
        if (cell.isLandMine()) {
            gameStatus = -1;
            return;
        } else {
            openAt(coordinates.row(), coordinates.col());
        }
        gameStatus = getWinningStatus();
    }

    private static void flagOn(Coordinates coordinates) {
        Cell cell = board[coordinates.row()][coordinates.col()];
        cell.flag();
        gameStatus = getWinningStatus();
    }

    private static int getWinningStatus() {
        for (int col = 0; col < COLUMN_COUNT; col++) {
            for (int row = 0; row < ROW_COUNT; row++) {
                if (board[col][row].isChecked()) {
                    return 0;
                }
            }
        }
        return 1;
    }


    private static Coordinates getCoordinates() {
        String input = getCoordinatesInput();
        int c = input.charAt(0) - 'a';
        char r = input.charAt(1);
        int row = Character.getNumericValue(r) - 1;
        return new Coordinates(c, row);
    }

    private static String getCoordinatesInput() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        return scanner.nextLine();
    }

    private record Coordinates(int col, int row) {
    }

    private static boolean isGameOver() {
        if (gameStatus == 1) {
            showGameClearMessage();
            return true;
        }
        if (gameStatus == -1) {
            showGameOverMessage();
            return true;
        }
        return false;
    }

    private static void showGameOverMessage() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    private static void showGameClearMessage() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    private static void showBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int i = 0; i < COLUMN_COUNT; i++) {
            System.out.printf("%d  ", i + 1);
            for (int j = 0; j < ROW_COUNT; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void showStartMessage() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void openAt(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= ROW_COUNT) {
            return;
        }
        board[row][col].open();
        openAt(row - 1, col - 1);
        openAt(row - 1, col);
        openAt(row - 1, col + 1);
        openAt(row, col - 1);
        openAt(row, col + 1);
        openAt(row + 1, col - 1);
        openAt(row + 1, col);
        openAt(row + 1, col + 1);
    }

}
