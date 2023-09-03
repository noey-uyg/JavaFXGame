package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainScreen {
	private Stage stage;
	
	@FXML
    void ClickerGame(MouseEvent event) throws IOException {
		Node button = (Node)(event.getSource());
    	stage = (Stage)(button.getScene().getWindow());
    	Parent root = FXMLLoader.load(getClass().getResource("ClickerUI.FXML"));
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void ReactionTest(MouseEvent event) throws IOException {
    	Node button = (Node)(event.getSource());
    	stage = (Stage)(button.getScene().getWindow());
    	Parent root = FXMLLoader.load(getClass().getResource("ReactionUI.FXML"));
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

    
    @FXML
    void shoot(MouseEvent event) throws IOException {
    	Node button = (Node)(event.getSource());
    	stage = (Stage)(button.getScene().getWindow());
    	Parent root = FXMLLoader.load(getClass().getResource("ShootUI.fxml"));
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    void Pingpong(MouseEvent event) throws IOException {
    	Node button = (Node)(event.getSource());
    	stage = (Stage)(button.getScene().getWindow());
    	Parent root = FXMLLoader.load(getClass().getResource("pingpongUI.fxml"));
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
    

}
