package mygame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    public static String[] appArgs;

    public static void main(String[] args) {
        launch(args);
        appArgs = args;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("tic-tac-toe.fxml"));
        primaryStage.setTitle("Tic-Tac-Toe");
        Scene scene = new Scene(root, 300, 275);
        scene.getStylesheets().add(getClass().getResource("tictactoe.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
