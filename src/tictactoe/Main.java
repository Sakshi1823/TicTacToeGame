package tictactoe;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    Button[][] buttons = new Button[3][3];
    Label status = new Label();
    Label score = new Label("X: 0 | O: 0");

    GameLogic game = new GameLogic();
    boolean darkMode = false;

    // 🔑 SINGLE SOURCE OF TRUTH
    char nextStarter = 'X'; // 'X' or 'O'

    @Override
    public void start(Stage stage) {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Button btn = new Button("");
                btn.setFont(Font.font(36));
                btn.setPrefSize(120, 120);
                buttons[r][c] = btn;
                grid.add(btn, c, r);

                int row = r, col = c;
                btn.setOnAction(e -> handleMove(row, col));
            }
        }

        Button restart = new Button("Restart");
        restart.setOnAction(e -> startNewGame());

        Button theme = new Button("Toggle Theme");
        theme.setOnAction(e -> toggleTheme());

        VBox root = new VBox(12, score, status, grid, restart, theme);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 420, 520);
        stage.setTitle("Advanced Tic Tac Toe");
        stage.setScene(scene);
        stage.show();

        // 🔥 first game
        startNewGame();
    }

    private void handleMove(int r, int c) {
        if (game.board[r][c] != ' ') return;

        game.makeMove(r, c);
        buttons[r][c].setText(String.valueOf(game.currentPlayer));
        SoundPlayer.playClick();

        // 🏆 WIN
        if (game.checkWin()) {
            highlightWin();
            status.setText("Player " + game.currentPlayer + " Wins!");
            game.updateScore();
            updateScore();
            SoundPlayer.playWin();
            disableBoard();

            // winner starts next game
            nextStarter = game.currentPlayer;
            return;
        }

        // 🤝 DRAW
        if (game.isDraw()) {
            status.setText("It's a Draw!");

            // toggle starter ONLY on draw
            nextStarter = (nextStarter == 'X') ? 'O' : 'X';
            return;
        }

        game.switchPlayer();
        status.setText("Player " + game.currentPlayer + " Turn");

        // 🤖 AI move
        if (game.currentPlayer == 'O') {
            int[] move = AIPlayer.bestMove(game.board);
            handleMove(move[0], move[1]);
        }
    }

    private void startNewGame() {

        game.reset();

        for (Button[] row : buttons)
            for (Button b : row) {
                b.setText("");
                b.setDisable(false);
                b.setStyle("");
            }

        game.currentPlayer = nextStarter;

        if (nextStarter == 'X') {
            status.setText("Player X Turn");
        } else {
            status.setText("Player O (AI) Turn");

            // AI starts immediately
            int[] move = AIPlayer.bestMove(game.board);
            handleMove(move[0], move[1]);
        }
    }

    private void disableBoard() {
        for (Button[] row : buttons)
            for (Button b : row)
                b.setDisable(true);
    }

    private void updateScore() {
        score.setText("X: " + game.xScore + " | O: " + game.oScore);
    }

    private void highlightWin() {
        int[][] win = game.getWinCells();
        for (int[] cell : win)
            buttons[cell[0]][cell[1]]
                    .setStyle("-fx-background-color: lightgreen;");
    }

    private void toggleTheme() {
        darkMode = !darkMode;
        String style = darkMode
                ? "-fx-background-color: #2b2b2b; -fx-text-fill: white;"
                : "";
        status.setStyle(style);
        score.setStyle(style);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
