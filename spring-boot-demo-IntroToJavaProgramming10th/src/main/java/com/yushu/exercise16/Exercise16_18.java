package com.yushu.exercise16;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Exercise16_18 extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        FanPane fan = new FanPane();

        HBox hBox = new HBox(5);
        Button btPause = new Button("Pause");
        Button btResume = new Button("Resume");
        Button btReverse = new Button("Reverse");
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(btPause, btResume, btReverse);

        BorderPane pane = new BorderPane();
        pane.setCenter(fan);
        pane.setTop(hBox);

        Slider slSpeed = new Slider();
        slSpeed.setValue(10);
        pane.setBottom(slSpeed);

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 200, 200 + 20);
        primaryStage.setTitle("Exercise16_18"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        Timeline animation = new Timeline(
                new KeyFrame(Duration.millis(100), e -> fan.move()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play(); // Start animation

        scene.widthProperty().addListener(e -> fan.setW(fan.getWidth()));
        scene.heightProperty().addListener(e -> fan.setH(fan.getHeight()));

        btPause.setOnAction(e -> animation.pause());
        btResume.setOnAction(e -> animation.play());
        btReverse.setOnAction(e -> fan.reverse());

        slSpeed.setMax(20);
        animation.rateProperty().bind(slSpeed.valueProperty());
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

