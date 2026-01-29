package tictactoe;

public class AIPlayer {

    static int[] bestMove(char[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[2];

        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[r][c] == ' ') {
                    board[r][c] = 'O';
                    int score = minimax(board, false);
                    board[r][c] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = r;
                        move[1] = c;
                    }
                }
        return move;
    }

    static int minimax(char[][] board, boolean isMax) {
        if (win(board,'O')) return 1;
        if (win(board,'X')) return -1;
        if (full(board)) return 0;

        int best = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[r][c] == ' ') {
                    board[r][c] = isMax ? 'O' : 'X';
                    int score = minimax(board, !isMax);
                    board[r][c] = ' ';
                    best = isMax ? Math.max(score, best)
                            : Math.min(score, best);
                }
        return best;
    }

    static boolean win(char[][] b, char p) {
        for (int i = 0; i < 3; i++)
            if ((b[i][0]==p && b[i][1]==p && b[i][2]==p) ||
                    (b[0][i]==p && b[1][i]==p && b[2][i]==p))
                return true;

        return (b[0][0]==p && b[1][1]==p && b[2][2]==p) ||
                (b[0][2]==p && b[1][1]==p && b[2][0]==p);
    }

    static boolean full(char[][] b) {
        for (char[] r : b)
            for (char c : r)
                if (c == ' ') return false;
        return true;
    }
}
