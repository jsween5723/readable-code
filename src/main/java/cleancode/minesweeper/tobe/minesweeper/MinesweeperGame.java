package cleancode.minesweeper.tobe.minesweeper;


import cleancode.minesweeper.tobe.minesweeper.board.Board;
import cleancode.minesweeper.tobe.minesweeper.board.Coordinate;
import cleancode.minesweeper.tobe.minesweeper.io.InputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.OutputHandler;

public class MinesweeperGame {
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final Board board;
    private GameStatus gameStatus = GameStatus.PROCESSING;

    public MinesweeperGame(MineSweeperGameLevel gameLevel) {
        this.board = Board.withConfig(gameLevel.boardConfig);
    }

    public void start() {
        outputHandler.printStartMessage();
        outputHandler.printBoard(board);
        while (isGameContinue()) {
            try {
                Coordinate coordinate = selectCellCoordinate();
                board.validateCoordinates(coordinate);
                CellAction actionNumber = selectAction();
                switch (actionNumber) {
                    case OPEN -> board.open(coordinate);
                    case TOGGLE_FLAG -> board.toggleFlag(coordinate);
                }
                outputHandler.printBoard(board);
                checkGameStatus();
            } catch (RuntimeException e) {
                outputHandler.printExceptionMessage(e);
            }
        }
    }

    private CellAction selectAction() {
        outputHandler.printInputActionMessage();
        return inputHandler.inputAction();
    }

    private Coordinate selectCellCoordinate() {
        outputHandler.printInputCoordinateMessage();
        return inputHandler.inputCoordinate();
    }


    private boolean isGameContinue() {
        return gameStatus == GameStatus.PROCESSING;
    }

    private void checkGameStatus() {
        if (board.isMineOpened()) {
            gameStatus = GameStatus.LOSE;
            outputHandler.printGameOverMessage();
        }
        if (board.isCleared()) {
            gameStatus = GameStatus.WIN;
            outputHandler.printGameClearMessage();
        }
    }

    enum GameStatus {
        WIN, LOSE, PROCESSING
    }
}
