package tictactoe;

import java.util.Random;

/**
 *
 * @author Kenneth Tran
 */
public class Bot {
    
    Game game;
    Difficulty difficulty;
    
    String marker;
    
    public Bot(Game game, String marker, Difficulty difficulty) {
        this.game = game;
        this.difficulty = difficulty;
        this.marker = marker;
    }
    
    public void move() {
        if (difficulty == Difficulty.EASY) {
            Random random = new Random();
            int r = random.nextInt(9);
            int i = r / 3;
            int j = r % 3;
            
            while (!game.grid[i][j].getText().isEmpty()) {
                r = random.nextInt(9);
                i = r / 3;
                j = r % 3;
            }
            
            game.grid[i][j].setText(marker);
            game.playerTurn = true;
        } else if (difficulty == Difficulty.HARD) {
            int bestScore = Integer.MIN_VALUE;
            int nextI = 0, nextJ = 0;
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (game.grid[i][j].getText().isEmpty()) {
                        game.grid[i][j].setText(marker);
                        
                        int score = minimax(false);
                        
                        game.grid[i][j].setText("");
                        
                        if (score > bestScore) {
                            bestScore = score;
                            nextI = i;
                            nextJ = j;
                        }
                    }
                }
            }
            
            game.grid[nextI][nextJ].setText(marker);
            game.playerTurn = true;
        }
        
        // Check for a winner
        String winner = game.findWinner();

        if (winner != null) {
            if (winner.equals("tie")) {
                game.finished = true;
                game.lblInfo.setText("It was a tie!");
                return;
            } else if (winner.equals(marker)) {
                game.finished = true;
                game.lblInfo.setText("The bot wins!");
                return;
            }
        }

        game.lblInfo.setText("Your turn");
    }
    
    private int minimax(boolean isMaximizing) {
        String winner = game.findWinner();
        
        if (winner != null) {
            if (winner.equals(game.playerMarker)) {
                return -1;
            } else if (winner.equals(marker)) {
                return 1;
            } else if (winner.equals("tie")) {
                return 0;
            }
        }
        
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (game.grid[i][j].getText().isEmpty()) {
                        game.grid[i][j].setText(marker);
                        
                        int score = minimax(false);
                        
                        game.grid[i][j].setText("");
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (game.grid[i][j].getText().isEmpty()) {
                        game.grid[i][j].setText(game.playerMarker); // Player
                        
                        int score = minimax(true);
                        
                        game.grid[i][j].setText("");
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            
            return bestScore;
        }
    }
    
}
