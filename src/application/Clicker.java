package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Clicker implements Initializable {
	
	private Stage stage;
    @FXML
    private ProgressBar hpBar;
    @FXML
    private ImageView VeNom;
    @FXML
    private Label damageLbl;

    @FXML
    private Label goldLbl;

    @FXML
    private Label hpLbl;
    
    @FXML
    private Button damgold;

    @FXML
    private Button goldupgradegold;

    @FXML
    private Label addgoldLbl;

    @FXML
    private Label gameOver;
    
    double venomMaxHP=50000000;
    double damageper=0.8;
	double goldper=1.3;
	double venomhp=venomMaxHP;
	double clickTogold=1;
	double myGold=0;
	double clickTodamage=clickTogold*damageper;
	double damUpgradeGold=1;
	double goldUpgradeGold=1;
	int goldcount=1;
	int count=0;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		hpBar.setStyle("-fx-accent: #ff4130;");
	}
	
	@FXML
	void damagaUpgrade(ActionEvent event) { //���ݷ� ���׷��̵�
		if(myGold>=damUpgradeGold) {
			myGold-=damUpgradeGold;
			damageper+=0.7;
			damUpgradeGold*=goldper;
			clickTodamage=clickTogold*damageper;
			Text();
		}
	}

	@FXML
	void goldUpgrade(ActionEvent event) { //���ȹ�淮 ���׷��̵�
		if(myGold>=goldUpgradeGold) {
			myGold-=goldUpgradeGold;
			clickTogold+=goldcount;
			goldUpgradeGold*=goldper;
			clickTodamage=clickTogold*damageper;
			goldcount++;
			Text();
		}
	}
    @FXML
    void Attack(MouseEvent event) {//Ŭ����
    	if(venomhp>=0) {
    		venomhp-=clickTodamage;
        	hpBar.setProgress(venomhp/venomMaxHP);
        	myGold+=clickTogold;
        	Text();
        	VeNom.setX(VeNom.getX()-25);
        	VeNom.setOpacity(0.5);
        	count++;
    	}
    	if(count/10==1) {
    		venomhp-=(clickTodamage*5);
    		goldcount+=2;
    	}
    }
    @FXML
    void Attacked(MouseEvent event) {//Ŭ����
    	VeNom.setX(VeNom.getX()+25);
    	VeNom.setOpacity(1);
    	if(venomhp<=0) {
    		hpLbl.setText("���� ���");
    		VeNom.setOpacity(0);
    		gameOver();
    	}
    }
    void Text() {
    	damageLbl.setText("���� ������ : "+(int)clickTodamage);
    	goldLbl.setText("���� ��� : "+(int)myGold);
    	hpLbl.setText(String.valueOf((int)venomhp));
    	damgold.setText("���ݷ� ���׷��̵� ��� : "+(int)damUpgradeGold);
    	goldupgradegold.setText("��� ���׷��̵� ��� : "+(int)goldUpgradeGold);
    	addgoldLbl.setText("Ŭ���� ��� ��� : "+(int)clickTogold);
    }
    
    void gameOver() {
    	gameOver.setText("���� ���̴µ� ������ Ƚ�� : "+count);
    }
    
    //�ڷΰ���
    @FXML
    void MainScreen(MouseEvent event) throws IOException {
    	Node button = (Node)(event.getSource());
    	stage = (Stage)(button.getScene().getWindow());
    	Parent root = FXMLLoader.load(getClass().getResource("MainScreenUI.FXML"));
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

}
