/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logika.Hra;
import logika.IHra;
import uiText.TextoveRozhrani;

/**
 *
 * @author spia00
 */
public class main extends Application {

    @Override
    public void start(Stage primaryStage) {
                IHra hra = new Hra();
                
                BorderPane borderPane = new BorderPane();
                
                TextArea centerText = new TextArea();
                centerText.setText(hra.vratUvitani());
                borderPane.setCenter(centerText);
                
                
                Label label = new Label("Zadej příkaz");
                label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                
                TextField zadejPrikazTextField = new TextField("Zadej příkaz sem");
                zadejPrikazTextField.setOnAction(new EventHandler<ActionEvent>(){
                
                @Override
                public void handle(ActionEvent event)
                {
                    String zadanyPrikaz = zadejPrikazTextField.getText();
                    String odpoved = hra.zpracujPrikaz(zadanyPrikaz);
                    
                    centerText.appendText("\n" + zadanyPrikaz + "\n");
                    centerText.appendText("\n" + odpoved + "\n");
                    
                    zadejPrikazTextField.setText("");
                }
                });
                
                FlowPane dolniPanel = new FlowPane();
                dolniPanel.setAlignment(Pos.CENTER);
                dolniPanel.getChildren().addAll(label);
                
                borderPane.setBottom(dolniPanel);
                
    
            
        Scene scene = new Scene(borderPane, 400, 350);

        primaryStage.setTitle("Adventura");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        } else {
            if (args[0].equals("-text")) {
                IHra hra = new Hra();
                TextoveRozhrani textoveRozhrani = new TextoveRozhrani(hra);
                textoveRozhrani.hraj();
            } else {
                System.out.println("Neplatný parametr");
                System.exit(1);
            }
        }
    }
}

