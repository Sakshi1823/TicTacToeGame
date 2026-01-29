package tictactoe;


public class GameLogic {

    char[][] board = new char[3][3];
    char currentPlayer = 'X';
    int xScore = 0, oScore = 0;
    int[][] winCells;

    public GameLogic() {
        reset();
    }

    void reset() {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                board[r][c] = ' ';
        currentPlayer = 'X';
    }

    void makeMove(int r, int c) {
        board[r][c] = currentPlayer;
    }

    void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    boolean checkWin() {
        for (int i = 0; i < 3; i++)
            if (board[i][0]==currentPlayer &&
                    board[i][1]==currentPlayer &&
                    board[i][2]==currentPlayer) {
                winCells = new int[][]{{i,0},{i,1},{i,2}};
                return true;
            }

        for (int i = 0; i < 3; i++)
            if (board[0][i]==currentPlayer &&
                    board[1][i]==currentPlayer &&
                    board[2][i]==currentPlayer) {
                winCells = new int[][]{{0,i},{1,i},{2,i}};
                return true;
            }

        if (board[0][0]==currentPlayer &&
                board[1][1]==currentPlayer &&
                board[2][2]==currentPlayer) {
            winCells = new int[][]{{0,0},{1,1},{2,2}};
            return true;
        }

        if (board[0][2]==currentPlayer &&
                board[1][1]==currentPlayer &&
                board[2][0]==currentPlayer) {
            winCells = new int[][]{{0,2},{1,1},{2,0}};
            return true;
        }

        return false;
    }

    boolean isDraw() {
        for (char[] row : board)
            for (char c : row)
                if (c == ' ') return false;
        return true;
    }

    void updateScore() {
        if (currentPlayer == 'X') xScore++;
        else oScore++;
    }

    int[][] getWinCells() {
        return winCells;
    }
}
