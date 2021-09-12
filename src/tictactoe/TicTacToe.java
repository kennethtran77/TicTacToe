package tictactoe;

import java.util.Random;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author Kenneth Tran
 */
public class TicTacToe extends Application {
    
    Stage primaryStage;
    Scene titleScene;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Tic Tac Toe");
        
        // Difficulty Scene
        BorderPane difficultyLayout = new BorderPane();
        difficultyLayout.setPadding(new Insets(10, 10, 10, 10));
        
        // Difficulties Buttons
        VBox difficulties = new VBox(20);
        difficulties.setAlignment(Pos.CENTER);
        
        // Easy
        Button btnEasyDifficulty = new Button("Easy");
        btnEasyDifficulty.setMinSize(125, 50);
        
        btnEasyDifficulty.setOnAction(e -> {
            Game game = new Game(this, Difficulty.EASY, new Random().nextBoolean());
            primaryStage.setScene(game.gameScene);
        });
        //
        
        // Easy
        Button btnHardDifficulty = new Button("Hard");
        btnHardDifficulty.setMinSize(125, 50);
        
        btnHardDifficulty.setOnAction(e -> {
            Game game = new Game(this, Difficulty.HARD, new Random().nextBoolean());
            primaryStage.setScene(game.gameScene);
        });
        //
        
        difficulties.getChildren().addAll(btnEasyDifficulty, btnHardDifficulty);
        
        // Back Button
        Button btnBack = new Button("Back");
        btnBack.setMinSize(125, 50);
        btnBack.setOnAction(e -> {
            primaryStage.setScene(titleScene);
        });
        //
        
        difficultyLayout.setCenter(difficulties);
        difficultyLayout.setBottom(btnBack);
        
        Scene difficultyScene = new Scene(difficultyLayout, 500, 500);
        difficultyScene.getStylesheets().add("tictactoe/styles.css");
        
        // Title Scene
        
        // Quit Button
        Button btnQuit = new Button("Exit");
        btnQuit.setMinSize(125, 50);
        btnQuit.setOnAction(e -> Platform.exit());
        
        // Play Button
        Button btnPlay = new Button("Play");
        btnPlay.setMinSize(125, 50);
        btnPlay.setOnAction(e -> {
            primaryStage.setScene(difficultyScene);
        });
        
        // Title
        Label lblTitle = new Label("Tic Tac Toe");
        lblTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        
        VBox titleLayout = new VBox(20);
        titleLayout.getChildren().addAll(lblTitle, btnPlay, btnQuit);
        titleLayout.setAlignment(Pos.CENTER);
        
        titleScene = new Scene(titleLayout, 500, 500);
        titleScene.getStylesheets().add("tictactoe/styles.css");
        
        primaryStage.setScene(titleScene);
        primaryStage.show();
    }
    
}
