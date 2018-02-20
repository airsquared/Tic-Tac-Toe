package mygame;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


@SuppressWarnings("Convert2Diamond")
public class TicTacToeController {

    private boolean firstPlayer = true;
    private boolean gameOver;
    private boolean playingComputer;
    private boolean firstRun = true;
    private boolean middleEmpty;
    private Difficulty difficulty;
    @FXML private Button b1;
    @FXML private Button b2;
    @FXML private Button b3;
    @FXML private Button b4;
    @FXML private Button b5;
    @FXML private Button b6;
    @FXML private Button b7;
    @FXML private Button b8;
    @FXML private Button b9;
    @FXML private GridPane gameBoard;
    private FadeTransition ft1;
    private FadeTransition ft2;
    private FadeTransition ft3;

    private void afterTurn() {
        if (find3InARow()) {
            gameOver = true;
        }
        firstPlayer = !firstPlayer;
    }

    public void squareClickedHandler(ActionEvent evt) {
        Button clickedButton = (Button) evt.getTarget();


        if (!gameOver && clickedButton.getText().equals("") && firstPlayer) {
            clickedButton.setText("X");
            afterTurn();
            if (!gameOver && playingComputer) {
                computer();
            }
        } else if (!gameOver && clickedButton.getText().equals("") && !firstPlayer && !playingComputer) {
            clickedButton.setText("O");
            afterTurn();
        }
    }

    private boolean find3InARow() {
        //Row 1
        if (!"".equals(b1.getText()) && b1.getText().equals(b2.getText())
                && b2.getText().equals(b3.getText())) {
            highlightWinningCombo(b1, b2, b3);
            return true;
        }
        //Row 2
        if (!"".equals(b4.getText()) && b4.getText().equals(b5.getText())
                && b5.getText().equals(b6.getText())) {
            highlightWinningCombo(b4, b5, b6);
            return true;
        }
        //Row 3
        if (!"".equals(b7.getText()) && b7.getText().equals(b8.getText())
                && b8.getText().equals(b9.getText())) {
            highlightWinningCombo(b7, b8, b9);
            return true;
        }
        //Column 1
        if (!"".equals(b1.getText()) && b1.getText().equals(b4.getText())
                && b4.getText().equals(b7.getText())) {
            highlightWinningCombo(b1, b4, b7);
            return true;
        }
        //Column 2
        if (!"".equals(b2.getText()) && b2.getText().equals(b5.getText())
                && b5.getText().equals(b8.getText())) {
            highlightWinningCombo(b2, b5, b8);
            return true;
        }
        //Column 3
        if (!"".equals(b3.getText()) && b3.getText().equals(b6.getText())
                && b6.getText().equals(b9.getText())) {
            highlightWinningCombo(b3, b6, b9);
            return true;
        }
        //Diagonal 1
        if (!"".equals(b1.getText()) && b1.getText().equals(b5.getText())
                && b5.getText().equals(b9.getText())) {
            highlightWinningCombo(b1, b5, b9);
            return true;
        }
        //Diagonal 2
        if (!"".equals(b3.getText()) && b3.getText().equals(b5.getText())
                && b5.getText().equals(b7.getText())) {
            highlightWinningCombo(b3, b5, b7);
            return true;
        }
        return false;
    }

    private void highlightWinningCombo(Button B1, Button B2, Button B3) {
        B1.getStyleClass().add("winning-square");
        B2.getStyleClass().add("winning-square");
        B3.getStyleClass().add("winning-square");
        ft1 = fadeTransition(B1);
        ft2 = fadeTransition(B2);
        ft3 = fadeTransition(B3);
        firstRun = false;
        try {
            AudioClip audioClip = new AudioClip(TicTacToe.class.getResource("success.wav").toURI().toString());
            audioClip.play();
        } catch (URISyntaxException e) {
            System.out.println("Could not play audio clip: " + e.getMessage());
        }
    }

    private FadeTransition fadeTransition(Button button) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(700), button);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.1);
        fadeTransition.setCycleCount(Timeline.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
        return fadeTransition;
    }

    public void play() {
        if (!firstRun) {
            ft1.stop();
            ft2.stop();
            ft3.stop();
        }
        ObservableList<Node> buttons = gameBoard.getChildren();
        buttons.forEach(btn -> {
            ((Button) btn).setText("");
            btn.getStyleClass().remove("winning-square");
            btn.setOpacity(1.0);
            btn.setDisable(false);
        });
        firstPlayer = true;
        gameOver = false;
        playingComputer = false;
    }

    /* -- Computer logic -- */

    public void playComputer(ActionEvent evt) {
        play();
        playingComputer = true;
        String menuText = ((MenuItem) evt.getTarget()).getText();
        switch (menuText) {
            case "Unbeatable":
                difficulty = Difficulty.UNBEATABLE;
                break;
            case "Hard":
                difficulty = Difficulty.HARD;
                break;
            case "Medium":
                difficulty = Difficulty.MEDIUM;
                break;
            case "Easy":
                difficulty = Difficulty.EASY;
                break;
        }
    }

    private Button findSingleEmpty(Button b1, Button b2, Button b3, String s) {
        int amtTaken = 0;
        Button returnValue = null;
        if (b1.getText().equals("")) {
            returnValue = b1;
        } else if (b1.getText().equals(s)) {
            amtTaken++;
        }
        if (b2.getText().equals("")) {
            returnValue = b2;
        } else if (b2.getText().equals(s)) {
            amtTaken++;
        }
        if (b3.getText().equals("")) {
            returnValue = b3;
        } else if (b3.getText().equals(s)) {
            amtTaken++;
        }
        if (amtTaken == 2) {
            return returnValue;
        }
        return null;
    }

    private Boolean placeSingleEmptyUnbeatable(String s) {
        Button emptyButton;

        emptyButton = findSingleEmpty(b1, b2, b3, s);
        if (emptyButton != null) {
            emptyButton.setText("O");
            return true;
        }
        emptyButton = findSingleEmpty(b4, b5, b6, s);
        if (emptyButton != null) {
            emptyButton.setText("O");
            return true;
        }
        emptyButton = findSingleEmpty(b7, b8, b9, s);
        if (emptyButton != null) {
            emptyButton.setText("O");
            return true;
        }
        emptyButton = findSingleEmpty(b1, b4, b7, s);
        if (emptyButton != null) {
            emptyButton.setText("O");
            return true;
        }
        emptyButton = findSingleEmpty(b2, b5, b8, s);
        if (emptyButton != null) {
            emptyButton.setText("O");
            return true;
        }
        emptyButton = findSingleEmpty(b3, b6, b9, s);
        if (emptyButton != null) {
            emptyButton.setText("O");
            return true;
        }
        emptyButton = findSingleEmpty(b1, b5, b9, s);
        if (emptyButton != null) {
            emptyButton.setText("O");
            return true;
        }
        emptyButton = findSingleEmpty(b3, b5, b7, s);
        if (emptyButton != null) {
            emptyButton.setText("O");
            return true;
        }
        return false;
    }

    private Boolean placeSingleEmpty(String s) {
        if (difficulty == Difficulty.HARD || difficulty == Difficulty.MEDIUM) {
            int t = ThreadLocalRandom.current().nextInt(0, 2);
            if (t == 0) {
                return placeSingleEmptyUnbeatable(s);
            }
        }
        return false;
    }

    private Boolean placeCorner() {
        ArrayList<Button> corners = new ArrayList<Button>();
        if (b1.getText().equals("")) {
            corners.add(b1);
        }
        if (b3.getText().equals("")) {
            corners.add(b3);
        }
        if (b7.getText().equals("")) {
            corners.add(b7);
        }
        if (b9.getText().equals("")) {
            corners.add(b9);
        }
        if (!b1.getText().equals("") && !b3.getText().equals("") && !b7.getText().equals("") && !b9.getText().equals("")) {
            return false;
        }
        corners.get(ThreadLocalRandom.current().nextInt(corners.size())).setText("O");
        return true;
    }

    private Boolean placeEdge() {
        ArrayList<Button> edges = new ArrayList<Button>();
        if (b2.getText().equals("")) {
            edges.add(b2);
        }
        if (b4.getText().equals("")) {
            edges.add(b4);
        }
        if (b6.getText().equals("")) {
            edges.add(b6);
        }
        if (b8.getText().equals("")) {
            edges.add(b8);
        }
        if (!b2.getText().equals("") && !b4.getText().equals("") && !b6.getText().equals("") && !b8.getText().equals("")) {
            return false;
        }
        edges.get(ThreadLocalRandom.current().nextInt(edges.size())).setText("O");
        return true;
    }

    private Boolean userTaken2Corners() {
        ArrayList<Button> corners = new ArrayList<Button>();
        corners.add(b1);
        corners.add(b3);
        corners.add(b7);
        corners.add(b9);
        return corners.stream().filter(b -> b.getText().equals("X")).count() == 2;

    }

    private Boolean placeMiddleEasy() {
        if (middleEmpty && (ThreadLocalRandom.current().nextInt(2) == 0)) {
            b5.setText("O");
            return true;
        }
        return false;
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    private void computer() {
        middleEmpty = b5.getText().equals("");
        try {
            if (difficulty == Difficulty.UNBEATABLE) {
                if (placeSingleEmptyUnbeatable("O")) {
                    return;
                }
                if (placeSingleEmptyUnbeatable("X")) {
                    return;
                }
                if (middleEmpty) {
                    b5.setText("O");
                    return;
                }
                if (userTaken2Corners() && placeEdge()) {
                    return;
                }
                if (placeCorner()) {
                    return;
                }
                if (placeEdge()) {
                    return;
                }
            } else if (difficulty == Difficulty.HARD) {
                if (placeSingleEmpty("O")) {
                    return;
                }
                if (placeSingleEmpty("O")) {
                    return;
                }
                if (placeSingleEmpty("X")) {
                    return;
                }
                if (middleEmpty) {
                    b5.setText("O");
                    return;
                }
                if (userTaken2Corners() && b5.getText().equals("O") && placeEdge()) {
                    return;
                }
                if (userTaken2Corners() && placeEdge()) {
                    return;
                }
                if (placeCorner()) {
                    return;
                }
                if (placeEdge()) {
                    return;
                }
            } else if (difficulty == Difficulty.MEDIUM) {
                if (placeSingleEmpty("O")) {
                    return;
                }
                if (placeSingleEmpty("X")) {
                    return;
                }
                if (userTaken2Corners() && b5.getText().equals("O") && placeEdge()) {
                    return;
                }
                if (placeCorner()) {
                    return;
                }
                if (placeEdge()) {
                    return;
                }
            } else if (difficulty == Difficulty.EASY) {
                if (placeSingleEmpty("O")) {
                    return;
                }
                if (placeSingleEmpty("X")) {
                    return;
                }
                if (placeMiddleEasy()) {
                    return;
                }
                if (placeCorner()) {
                    return;
                }
                if (placeEdge()) {
                    return;
                }
            }
        } finally {
            afterTurn();
        }

    }

    private enum Difficulty {UNBEATABLE, HARD, MEDIUM, EASY}
}
