package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.CellAction;
import cleancode.minesweeper.tobe.minesweeper.board.Board;
import cleancode.minesweeper.tobe.minesweeper.board.Coordinate;

import java.util.Scanner;

public class IOHandler {
    private final InputHandler inputHandler = new InputHandler(new Scanner(System.in));
    private final OutputHandler outputHandler = new OutputHandler();

    public void printStart(Board board) {
        outputHandler.printStartMessage();
        outputHandler.printBoard(board);
    }

    public void printBoard(Board board) {
        outputHandler.printBoard(board);
    }
    public CellAction selectAction() {
        outputHandler.printInputActionMessage();
        return inputHandler.inputAction();
    }


    public Coordinate selectCellCoordinate() {
        outputHandler.printInputCoordinateMessage();
        return inputHandler.inputCoordinate();
    }

    public void printExceptionMessage(RuntimeException exception) {
        outputHandler.printExceptionMessage(exception);
    }

    public void printGameOverMessage() {
        outputHandler.printGameOverMessage();
    }

    public void printGameClearMessage() {
        outputHandler.printGameClearMessage();
    }
}
