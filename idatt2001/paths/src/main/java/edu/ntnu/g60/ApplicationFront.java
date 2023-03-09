package edu.ntnu.g60;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

//TODO: make sure the mouse does not start in the textfield, this makes the prompt text unreadable
//TODO: make it so the user can input the email key with spaces or without. take input and remove spaces

import java.io.IOException;
import java.time.Duration;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ApplicationFront extends Application {
    
    private Stage stage;
    int gameSave; //TODO: change variabel to the right thing 
    int player; //TODO: change variabel to right thing

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        this.stage = stage;
        stage.setScene(newTalkingScene(false, null, null));
        stage.show();
    }

    public Scene openingScene(){
        Button continueButton = newButton("Continue", 514-193, 314-71, "black", "light grey", 158, 51, 24);
        continueButton.setOnAction(e -> {
            stage.setScene(continueScene());
        });
        
        Button newGameButton = newButton("New game", 514-193, 396-71, "black", "light grey", 158, 51, 24);
        newGameButton.setOnAction(e -> {
            stage.setScene(newGameScene());
        });

        Group root = new Group(continueButton, newGameButton);
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        return scene;

    }

    public Scene continueScene(){
        //TODO: set name of buttons to variabel names based on savefile names
        Button save1Button = newButton("Save 1", 514-193, 278-71, "black", "light grey", 158, 51, 24);
        Button save2Button = newButton("Save 2", 514-193, 345-71, "black", "light grey", 158, 51, 24);
        Button save3Button = newButton("Save 3", 514-193, 412-71, "black", "light grey", 158, 51, 24);
        Button backButton = newButton("Back", 903-193, 595-71, "black", "light grey", 74, 35, 15);
        backButton.setOnAction(e -> {
            stage.setScene(openingScene());
        });

        Group root = new Group(save1Button, save2Button, save3Button, backButton);
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        return scene;

    }

    public Scene loadingScene(){
        Text loadingText = newText("Loading...", 40, false, 508-193, 349-71);

        Group root = new Group(loadingText);
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        return scene;
    }

    public Scene deathScene(){
        Text deathText = newText("YOU DIED", 40, false, 508-193, 349-71);

        Group root = new Group(deathText);
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        return scene;
    }

    public Scene firstScene(){
        //TODO: set lvl x to variable based on the lvl the player is on
        Text lvlText = newText("LVL X", 40, false, 541-193, 349-71);

        Group root = new Group(lvlText);
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        return scene;
    }

    public Scene newGameScene(){
        Button startButton = newButton("Start", 514-193, 370-71, "black", "light grey", 158, 51, 24);
        startButton.setOnAction(e -> {
            stage.setScene(loadingScene());
            delay(2000, () -> stage.setScene(firstScene()));
            //TODO: add samme som over bare til riktig scene
        });

        Button backButton = newButton("Back", 903-193, 595-71, "black", "light grey", 74, 35, 15);
        backButton.setOnAction(e -> {
            stage.setScene(openingScene());
        });

        TextField saveNameTextField = newTextField("Savename..", 514-193, 327-71, "black", "light grey", 158, 27, 13);

        Group root = new Group(backButton, startButton, saveNameTextField);
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        return scene;
    }

    public Scene newFightScene(Image enemy, Image background, Image player){
        //TODO: add images
        
        Button fightButton = newButton("Fight", 209-193, 484-71, "black", "white", 373, 55, 40);
        Button healButton = newButton("Heal", 604-193, 484-71, "black", "white", 373, 55, 40);
        Button inventoryButton = newButton("Inventory", 209-193, 565-71, "black", "white", 373, 55, 40);
        Button runButton = newButton("Run", 604-193, 565-71, "black", "white", 373, 55, 40);

        String score = "1234";
        String gold = "4321";
        Group root = new Group(newRectangle(203-193, 82-71, 200, 38),
        newHealthBar(259-193, 306-71, 0.5), 
        newHealthBar(789-193, 137-71, 0.2),
        newText(score, 18, false, 242-193, 105-71),
        newText(gold, 18, false, 322-193, 105-71),
        fightButton, healButton, inventoryButton, runButton);
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        return scene;
    }
    
    public Scene newTalkingScene(boolean leftRight, Image player, Image Enemy) throws FileNotFoundException{
        //TODO: make one image bigger than other and add.
        //TODO: add triangle from rectangle
        Text textLineOne = newText("Test tekst", 30, false, 233-193, 470-71);
        Text textLineTwo = newText("Mere av det", 30, false, 233-193, 505-71);
        Text textLineThree = newText("Enda mere av det", 30, false, 233-193, 540-71);
        Text textLineFour = newText("", 30, false, 233-193, 575-71);
        
        ImageView imageviewCoinIcon = new ImageView();
        FileInputStream inputStream = new FileInputStream("src/main/java/edu/ntnu/g60/resources/images/icons/coin.png");
        Image coinIcon = new Image(inputStream);
        imageviewCoinIcon.setImage(coinIcon);
        imageviewCoinIcon.setX(200);
        imageviewCoinIcon.setY(200);
        imageviewCoinIcon.setFitHeight(24);
        imageviewCoinIcon.setFitWidth(24);

        String score = "1234";
        String gold = "4321";
        String health = "70%";
        Group root = new Group(newRectangle(203-193, 82-71, 293, 38),
        newText(score, 18, false, 242-193, 105-71),
        newText(gold, 18, false, 322-193, 105-71),
        newText(health, 18, false, 420-193, 105-71),
        newRectangle(227-193, 439-71, 739, 181),
        textLineOne, textLineTwo, textLineThree, textLineFour);
        Scene scene = new Scene(root, 800, 600, Color.WHITE);
        return scene;
    }

    public ProgressBar newHealthBar(int x, int y, double amount){
        ProgressBar health = new ProgressBar();
        health.setLayoutX(x);
        health.setLayoutY(y);
        health.setStyle("-fx-border-color: black;" + 
        "-fx-background-color: white;" +
        "-fx-pref-width: 100;" +
        "-fx-pref-height: 20;" +
        "-fx-accent: red;");
        health.setProgress(amount);
        return health;
    }

    public Rectangle newRectangle(int x, int y, int width, int height){
        Rectangle rectangle = new Rectangle();
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.WHITE);
        return rectangle;
    }

    public Button newButton(String text, int x, int y, String borderColor,
    String backgroundColor, int width, int height, int fontSize){
        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setStyle(setStyleString(borderColor, backgroundColor, width, height, fontSize));
        button.setOnMouseEntered(e -> button.setStyle(setStyleString(borderColor, "grey", width, height, fontSize)));
        button.setOnMouseExited(e -> button.setStyle(setStyleString(borderColor, backgroundColor, width, height, fontSize)));
        return button;
    }

    public TextField newTextField(String promptText, int x, int y, String borderColor,
    String backgroundColor, int width, int height, int fontSize){
        TextField textField = new TextField ();
        textField.setPromptText(promptText);
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setStyle(setStyleString(borderColor, backgroundColor, width, height, fontSize));
        return textField;
    }

    public Text newText(String title, int size, boolean underline, int x, int y){
        Text text = new Text(title);
        text.setFont(Font.font("Times New Roman", FontWeight.BOLD, size));
        text.setUnderline(underline);
        text.setX(x);
        text.setY(y);
        return text;
    }

    public ChoiceBox<String> newChoiceBox(String[] choices, String borderColor,
    String backgroundColor, int width, int height, int fontSize, int x, int y){
        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll(choices);
        choiceBox.setStyle(setStyleString(borderColor, backgroundColor, width, height, fontSize));
        choiceBox.setLayoutX(x);
        choiceBox.setLayoutY(y);
        return choiceBox;
    }
    
    public void alertBox(String title, String header, String content){
        Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
    }

    public String setStyleString(String borderColor,
    String backgroundColor, int width, int height, int fontSize){
        return "-fx-border-color: " + borderColor + ";" + 
        "-fx-background-color: " + backgroundColor + ";" +
        "-fx-pref-width: " + width + ";" +
        "-fx-pref-height: " + height + ";" +
        "-fx-font-size: " + fontSize + "px;";
    }
    
    public static void delay(long millis, Runnable continuation){
        Task<Void> sleeper = new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    public static void main(String[] args){launch();}
}
