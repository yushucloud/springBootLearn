package com.yushu.exercise34;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Exercise34_11 extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {    
    Pane pane = new Pane();    
    pane.setTranslateX(50);
    pane.setTranslateY(170);
      
    pane.getChildren().add(new Line(-20, 0, 420, 0));      
    pane.getChildren().add(new Line(400, -10, 420, 0));
    pane.getChildren().add(new Line(400, 10, 420, 0));
    pane.getChildren().add(new Text(410, 30, "x"));

    pane.getChildren().add(new Line(0, 55, 0, -155));
    pane.getChildren().add(new Line(10, -135, 0, -155));
    pane.getChildren().add(new Line(-10, -135, 0, -155));
    pane.getChildren().add(new Text(-20, -140, "y"));
                  
    Polyline polyline = new Polyline();
    double x1 = 0;
    double y1 = 0;
    for (double x2 = 1; x2 < 420; x2++) {
      double y2 = 50 * Math.log(x2);
      polyline.getPoints().addAll(x1, y1);
      x1 = x2;
      y1 = y2;
    }     
    
    polyline.setScaleX(1);
    polyline.setScaleY(-0.5);
    polyline.setTranslateX(0);
    polyline.setTranslateY(-210);
    pane.getChildren().add(polyline);
     
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, 300, 150);
    primaryStage.setTitle("Exercise34_11"); // Set the stage title
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
