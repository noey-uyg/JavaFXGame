package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Shoot implements Initializable{

	
    @FXML
    private AnchorPane Parent;
    private Stage stage;
    ArrayList<Rectangle> rects = new ArrayList<>();
    Rectangle rect;
    
    @FXML
    private Label scorelabel;
    
    @FXML
    private Rectangle player;
    
    
    boolean up,left,right,down,start=false,turn;
    int shootspeed=5;
    int count=1,score=0;
    
    Timeline t1 = new Timeline(new KeyFrame(Duration.millis(500), e -> draw()));
	Timeline t2 = new Timeline(new KeyFrame(Duration.millis(100), e -> Moving()));
	Timeline t3 = new Timeline(new KeyFrame(Duration.millis(10000), e -> turn()));
    @FXML
    void gamestart(MouseEvent event) {
    	start=true;
    	score=0;
    	Parent.getChildren().removeAll(rects);
    	player.setX(243);
    	player.setY(243);
    	
		t1.setCycleCount(Timeline.INDEFINITE);
		t1.play();
		t2.setCycleCount(Timeline.INDEFINITE);
		t2.play();
		t3.setCycleCount(Timeline.INDEFINITE);
		t3.play();
    }
    
    //뒤로가기
    @FXML
    void mainscreen(MouseEvent event) throws IOException {
    	Node button = (Node)(event.getSource());
    	stage = (Stage)(button.getScene().getWindow());
    	Parent root = FXMLLoader.load(getClass().getResource("MainScreenUI.FXML"));
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
    @FXML
    void KeyPressed(KeyEvent event) {//키보드 누름
    	if(start) {
    		switch(event.getCode()) {
        	case W:
        		up=true;
        		break;
        	case A:
        		left=true;
        		break;
        	case S:
        		down=true;
        		break;
        	case D:
        		right=true;
        		break;
    		default:
    			break;
        	}
    		KeyProcess();
    	}
    }

    @FXML
    void KeyReleased(KeyEvent event) {//키보드 뗌
    	if(start) {
    		switch(event.getCode()) {
        	case W:
        		up=false;
        		break;
        	case A:
        		left=false;
        		break;
        	case S:
        		down=false;
        		break;
        	case D:
        		right=false;
        		break;
        	default:
    			break;    		
        	}
    	}
    	
    }

    void KeyProcess() { //이동 조건
    	if(up&&player.getY()>0)player.setY(player.getY()-5);
    	if(left&&player.getX()>0)player.setX(player.getX()-5);
    	if(down&&player.getY()<483)player.setY(player.getY()+5);
    	if(right&&player.getX()<483)player.setX(player.getX()+5);
    }
    
    void draw() {//적 생성
    	if(start) {
    		rect = new Rectangle(5,5,Color.RED);
            rect.setX((Math.random()*500));
            rect.setY((Math.random()*500));
            rects.add(rect);
            Parent.getChildren().add(rect);
            update();
    	}
    }
    
    void Moving() {//적 이동
    	gameovercheck();
    	if(start) {
    		if(turn) {
    			for(int i = 0 ; i<rects.size() ; i++) {
                	rects.get(i).setX(rects.get(i).getX()+shootspeed);
                }
    		}
    		else if(!turn) {
    			for(int i = 0 ; i<rects.size() ; i++) {
                	rects.get(i).setX(rects.get(i).getX()-shootspeed);
                }
    		}
            
    	}
    }
    
    void gameovercheck() {//게임 오버 체크
    	for(int i = 0 ; i<rects.size() ; i++) {
        	if(player.getX()<=rects.get(i).getX()&&player.getX()+15>=rects.get(i).getX()
        			&&player.getY()<=rects.get(i).getY()&&player.getY()+15>=rects.get(i).getY()) {
        		start=false;
        		t1.stop();
        		t2.stop();
        		t3.stop();
        	}
        }
    }
    
    void turn() {// 적 이동 방향 바꿈
    	count++;
    	shootspeed++;
    	if(count%2==0) {
    		turn=true;
    	}
    	else {
    		turn=false;
    	}
    }


    void update() {//스코어 업데이트
    	score++;
    	scorelabel.setText("Score : "+score);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		scorelabel.setText("Score : "+score);
	}

}
