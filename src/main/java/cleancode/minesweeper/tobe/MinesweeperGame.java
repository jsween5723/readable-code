package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {


    public static final int COLUMN_COUNT = 8;
    public static final int ROW_COUNT = 10;
    public static final int LAND_MINE_COUNT = 10;
    private static final InputHandler inputHandler = new InputHandler();
    private static final OutputHandler outputHandler = new OutputHandler();
    private static final Cell[][] board = new Cell[COLUMN_COUNT][ROW_COUNT];
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배


    public static void main(String[] args) {
        initializeBoard();
        gameStart();
    }

    private static void gameStart() {
        outputHandler.showStartMessage();
        while (true) {
            outputHandler.showBoard(board);
            if (isGameOver()) break;
            System.out.println();
            Coordinates coordinates = getCoordinates();
            getUserActionFor(coordinates);
        }
    }

    private static void initializeBoard() {
        for (int col = 0; col < COLUMN_COUNT; col++) {
            for (int row = 0; row < ROW_COUNT; row++) {
                board[col][row] = Cell.of(false);
            }
        }
        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(COLUMN_COUNT);
            int row = new Random().nextInt(ROW_COUNT);
            board[col][row] = Cell.of(true);
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
        String input2 = inputHandler.getUserActionInput();
        if (input2.equals("2")) {
            flagOn(coordinates);
        } else if (input2.equals("1")) {
            openAt(coordinates);
        } else {
            outputHandler.showInvalidInputError();
        }
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
        String input = inputHandler.getCoordinatesInput();
        int c = input.charAt(0) - 'a';
        char r = input.charAt(1);
        int row = Character.getNumericValue(r) - 1;
        return new Coordinates(c, row);
    }


    private record Coordinates(int col, int row) {
    }

    private static boolean isGameOver() {
        if (gameStatus == 1) {
            outputHandler.showGameClearMessage();
            return true;
        }
        if (gameStatus == -1) {
            outputHandler.showGameOverMessage();
            return true;
        }
        return false;
    }

    private static void openAt(int row, int col) {
        if (row < 0 || row >= ROW_COUNT || col < 0 || col >= COLUMN_COUNT) {
            return;
        }
        Cell cell = board[col][row];
        if (cell.isLandMine() || cell.isChecked()) return;
        cell.open();
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
