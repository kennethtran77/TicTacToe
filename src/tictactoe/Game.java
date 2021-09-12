package tictactoe;

import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Kenneth Tran
 */
public class Game {

    Scene gameScene;
    GridPane gameBoard;
    
    Label lblInfo;
    
    Bot bot;
    
    boolean finished;

    boolean playerTurn;
    
    Button[][] grid;
    
    String playerMarker;
    
    private boolean equals(String a, String b, String c) {
        return a.equals(b) && b.equals(c);
    }
    
    public String findWinner() {
        String winner = null;
        
        // Check horizontally
        for (int i = 0; i < 3; i++)
            if (equals(grid[i][0].getText(), grid[i][1].getText(), grid[i][2].getText()))
                winner = grid[i][0].getText();
        
        // Check vertically
        for (int i = 0; i < 3; i++)
            if (equals(grid[0][i].getText(), grid[1][i].getText(), grid[2][i].getText()))
                winner = grid[0][i].getText();
        
        // Check diagonally
        if (equals(grid[0][0].getText(), grid[1][1].getText(), grid[2][2].getText()))
            winner = grid[0][0].getText();
        
        if (equals(grid[0][2].getText(), grid[1][1].getText(), grid[2][0].getText()))
            winner = grid[0][2].getText();
        
        int openSpots = 0;
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j].getText().isEmpty()) {
                    openSpots++;
                }
            }
        }
        
        if (winner == null && openSpots == 0)
            return "tie";
        
        return winner;
    }

    public Game(TicTacToe main, Difficulty difficulty, boolean playerStarts) {
        bot = new Bot(this, playerStarts ? "O" : "X", difficulty);
        playerTurn = playerStarts;
        
        playerMarker = playerStarts ? "X" : "O";
        
        grid = new Button[3][3];
        
        // Back button
        Button btnBack = new Button("Quit Game");
        btnBack.setFocusTraversable(false);
        btnBack.setMinSize(125, 50);
        
        btnBack.setOnAction(e -> {
            if (finished) {
                main.primaryStage.setScene(main.titleScene);
                return;
            }
            
            Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to quit the game?", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.YES)
                main.primaryStage.setScene(main.titleScene);
        });
        
        // BorderPane
        BorderPane gameLayout = new BorderPane();        
        gameLayout.setBottom(btnBack);
        gameLayout.setPadding(new Insets(10, 10, 10, 10));
        
        // Scene
        gameScene = new Scene(gameLayout, 500, 500);
        gameScene.getStylesheets().add("tictactoe/styles.css");
        
        // GameBoard
        gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        
        lblInfo = new Label(playerStarts ? "Your turn" : "Waiting for the bot...");
        lblInfo.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, 30));
        GridPane.setHalignment(lblInfo, HPos.CENTER);
        gameBoard.add(lblInfo, 1, 0, 3, 1);
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button b = new Button();
                b.getStyleClass().add("board");
                b.setMinSize(100, 100);
                b.setMaxSize(100, 100);
                
                b.setFocusTraversable(false);
                
                b.setOnMouseClicked(e -> {
                    if (!playerTurn || finished || !b.getText().isEmpty())
                        return;

                    b.setText(playerMarker);
                    b.setStyle("-fx-border-color: black; -fx-background-color: white;");
                    
                    String winner = findWinner();
                    
                    if (winner != null) {
                        if (winner.equals("tie")) {
                            finished = true;
                            lblInfo.setText("It was a tie!");
                            return;
                        } else if (winner.equals(playerMarker)) {
                            finished = true;
                            lblInfo.setText("You win!");
                            return;
                        }
                    }
                    
                    playerTurn = false;
                    
                    lblInfo.setText("Waiting for the bot...");
                    
                    new Timeline(new KeyFrame(Duration.millis(500), f -> {
                        bot.move();
                    })).play();
                });
                
                b.setOnMouseEntered(e -> {
                    if (!playerTurn || finished || !b.getText().isEmpty()) {
                        b.setStyle("-fx-border-color: black; -fx-background-color: white;");
                        return;
                    }
                    
                    b.setStyle("-fx-border-color: black; -fx-background-color: #f2f2f2;");
                });
                
                b.setOnMouseExited(e -> {
                    b.setStyle("-fx-border-color: black; -fx-background-color: white;");
                });
                
                b.setOnMousePressed(e -> {
                    if (!playerTurn || finished || !b.getText().isEmpty())
                        return;
                    
                    b.setStyle("-fx-border-color: black; -fx-background-color: #b3b3b3;");
                });
                
                gameBoard.add(b, i + 1, j + 1);
                grid[i][j] = b;
            }
        }
        
        gameLayout.setCenter(gameBoard);
        
        if (!playerStarts) {
            bot.move();
        }
    }
    
}