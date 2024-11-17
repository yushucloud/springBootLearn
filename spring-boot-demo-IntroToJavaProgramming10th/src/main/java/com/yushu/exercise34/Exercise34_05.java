package com.yushu.exercise34;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public class Exercise34_05 extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {    
    Pane pane = new Pane();
    
    Circle circle = new Circle(100, 70, 50);
    Path path = new Path();
    path.getElements().add(new MoveTo(0, -50));
    path.getElements().add(new CubicCurveTo(50, -25, -50, 25, 0, 50));
    path.getElements().add(new CubicCurveTo(50, 25, -50, -25, 0, -50));
    path.setTranslateX(100);
    path.setTranslateY(70);
      
    path.setFill(Color.WHITE);
    
    pane.getChildren().addAll(circle, path);
        
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, 200, 150);
    primaryStage.setTitle("Exercise34_05"); // Set the stage title
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
