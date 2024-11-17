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

public class Exercise16_19 extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {       
    HBox hBox = new HBox(5);
    
    FanControl fan1 = new FanControl();
    FanControl fan2 = new FanControl();
    FanControl fan3 = new FanControl();
    fan1.setStyle("-fx-border-color: black");
    fan2.setStyle("-fx-border-color: black");
    fan3.setStyle("-fx-border-color: black");
    
    hBox.getChildren().addAll(fan1, fan2, fan3);
    
    BorderPane pane = new BorderPane();
    pane.setCenter(hBox);
    
    Button btStartAll = new Button("Start All");
    btStartAll.setOnAction(e -> {
      fan1.start(); fan2.start(); fan3.start();
    });
    
    Button btStopAll = new Button("Stop All");
    btStopAll.setOnAction(e -> {
      fan1.pause(); fan2.pause(); fan3.pause();
    });

    HBox hBox1 = new HBox(10);
    hBox1.setAlignment(Pos.CENTER);
    hBox1.getChildren().addAll(btStartAll, btStopAll);
    pane.setBottom(hBox1);
    
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, 650, 200 + 20);
    primaryStage.setTitle("Exercise16_19"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
} 

class FanControl extends BorderPane {
  private FanPane fan = new FanPane();
  private Timeline animation = new Timeline(
      new KeyFrame(Duration.millis(100), e -> fan.move()));
      
  public FanControl() {      
    HBox hBox = new HBox(5);
    Button btPause = new Button("Pause");
    Button btResume = new Button("Resume");
    Button btReverse = new Button("Reverse");
    hBox.setAlignment(Pos.CENTER);
    hBox.getChildren().addAll(btPause, btResume, btReverse);
    
    this.setCenter(fan);
    this.setTop(hBox);
    
    Slider slSpeed = new Slider();
    slSpeed.setValue(10);
    this.setBottom(slSpeed);
    
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.play(); // Start animation
        
    btPause.setOnAction(e -> animation.pause());
    btResume.setOnAction(e -> animation.play());
    btReverse.setOnAction(e -> fan.reverse());
    
    slSpeed.setMax(20);
    animation.rateProperty().bind(slSpeed.valueProperty());
    
    this.widthProperty().addListener(e -> fan.setW(fan.getWidth()));
    this.heightProperty().addListener(e -> fan.setH(fan.getHeight()));
  }  
  
  public void start() {
    animation.play();
  }
  
  public void pause() {
    animation.pause();
  }
  
  public void reverse() {
    fan.reverse();
  }
}
